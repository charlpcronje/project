package net.integrategroup.ignite.workflow;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import net.integrategroup.ignite.persistence.model.WorkflowProcess;
import net.integrategroup.ignite.persistence.service.WorkflowProcessService;

@Component
public class WorkflowProcessToGenericDialog {

	@Autowired
	WorkflowProcessService workflowProcessService;

	@Autowired
	EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	public String generate(Long workflowProcessId) {
		String sql;

		WorkflowProcess workflowProcess = workflowProcessService.findByWorkflowProcessId(workflowProcessId);

		// TODO: need to be able to execute direct sql here....
		sql = "SELECT * " +
		      "  FROM " +
			  workflowProcess.getRecordObjectName() +
		      "  WHERE " +
		      workflowProcess.getRecordWhereClause();

		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

		String html = "";

		if (result.size() == 0) {
			html += "<div class='row'>" +
					"	<label class='col-md-12 col-form-label'>The source record could not be found." +
					"   <br><br>" +
					"   This could mean that the record could have been deleted or that one of the record " +
					"   criteria has change and is therefore no longer selectable with " +
					"   the given record identifier." +
					"   <br><br>" +
					("N".equals(workflowProcess.getActiveFlag()) ? "The workflow has been closed." : "The workflow will be closed.") +
					"   </label>" +
					"</div>";
		}

		for (int foo = 0; foo < result.size(); foo++) {
			Map<String, Object> record = result.get(foo);

			for (Entry<String, Object> entry: record.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				String valueString = value == null ? "" : value.toString();
				String keyInputName = key.replaceAll(" ", "_") + "_Input";
				String keyDisplayName = keyToDisplayName(key);
				String fieldType = "text";
				String colWidth = "9";

				// dont show blob's in the record viewer
				if (value instanceof Blob) {
					continue;
				}

				if ((value instanceof Integer) || (value instanceof Long)) {
					fieldType = "number";
					colWidth = "5";
				}
				if (value instanceof Date) {
					fieldType = "date";
					colWidth = "5";

					// Does it have a time
					if ((value != null) && (value.toString().length() > 10)) {
						fieldType = "datetime";
						colWidth = "6";
					}
				}

				html += "<div class='row'>" +
				        "	<label class='col-md-3 col-form-label' for='" + keyInputName + "'>" + keyDisplayName + "</label>" +
						"	<div class='col-md-" + colWidth + "'>" +
				        "		<div class='form-group'>" +
						"			<input id='" + keyInputName + "' type='" + fieldType  + "' class='form-control' value='" + valueString + "' readonly />" +
				        "		</div>" +
				        "	</div>" +
				        "</div>";

				if (foo > 0) {
					// todo: add divider because we received more than one record...
					html += "<div class='form-group col-12'>" +
							"	<hr>" +
							"</div>";
				}
			}
		}

		return html;
	}

	private String splitCamelCase(String s) {
		return s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      " "
		   );
	}

	private String keyToDisplayName(String key) {
		key = key.replaceAll("_", " ");
		key = splitCamelCase(key);
		return key;
	}
}
