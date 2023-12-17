package net.integrategroup.ignite.controller.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.WorkflowDefinition;
import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;
import net.integrategroup.ignite.persistence.model.WorkflowProcess;
import net.integrategroup.ignite.persistence.model.WorkflowProcessLog;
import net.integrategroup.ignite.persistence.repository.WorkflowDefinitionStepsToGraphRepository;
import net.integrategroup.ignite.persistence.service.WorkflowDefinitionService;
import net.integrategroup.ignite.persistence.service.WorkflowDefinitionStepService;
import net.integrategroup.ignite.persistence.service.WorkflowProcessLogService;
import net.integrategroup.ignite.persistence.service.WorkflowProcessService;
import net.integrategroup.ignite.security.RoleConstants;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;
import net.integrategroup.ignite.workflow.WorkflowProcessToGenericDialog;

@RestController
@RequestMapping("/rest/ignite/v1/workflow")
public class WorkflowController {

	@Autowired
	SecurityUtils securityUtils;
	
	@Autowired
	WorkflowProcessService workflowProcessService;

	@Autowired
	WorkflowProcessLogService workflowProcessLogService;

	@Autowired
	WorkflowDefinitionService workflowDefinitionService;

	@Autowired
	WorkflowDefinitionStepService workflowDefinitionStepService;

	@Autowired
	WorkflowDefinitionStepsToGraphRepository workflowDefinitionStepsToGraphRepository;

	@Autowired
	WorkflowProcessToGenericDialog workflowProcessToGenericDialog;

	private int getAsInt(String ymdString, int start, int end) {
		int result = 1;

		try {
			result = new Integer(ymdString.substring(start, end));
		} catch (Exception e) {
			// Nothing
		}
		return result;
	}

	private Date ymdToDate(String ymdString) {
		Date result = new Date();

		if ((ymdString != null) && (ymdString.length() == 8)) {
			int y = getAsInt(ymdString, 0, 4);
			int m = getAsInt(ymdString, 4, 6) - 1;		// Calendar months are 0 offset
			int d = getAsInt(ymdString, 6, 8);

			Calendar cal = Calendar.getInstance();
			cal.set(y, m, d);
			result = cal.getTime();
		}

		return result;
	}

	@GetMapping(path = "/{workflowDefinitionId}")
	public WorkflowDefinition getWorkflowDefinition(@PathVariable("workflowDefinitionId") long workflowDefinitionId) {
		return workflowDefinitionService.findByWorkflowDefinitionId(workflowDefinitionId);
	}

	@GetMapping(path = "/{startYmdString}/{endYmdString}/{onlyActive}")
	public List<WorkflowProcess> getWorkflows(@PathVariable(name="startYmdString") String startYmdString,
			 									@PathVariable(name="endYmdString") String endYmdString,
			 									@PathVariable(name="onlyActive") String onlyActive) {
		List<WorkflowProcess> result = new ArrayList<>();
		Long dataProviderId = null;

		// determine the date range
		Date startDate = ymdToDate(startYmdString);
		Date endDate = ymdToDate(endYmdString);

		if ("active".equalsIgnoreCase(onlyActive)) {
			result = workflowProcessService.findByStartDateEndDateAndActiveFlag(startDate, endDate, "Y");
		} else {
			result = workflowProcessService.findByStartDateAndEndDate(startDate, endDate);
		}

		return result;
	}

	@GetMapping(path = "/count/{workflowDefinitionId}")
	public ResponseEntity<?> getWorkflowProcessesCount(@PathVariable("workflowDefinitionId") long workflowDefinitionId) {
		ResponseEntity<?> result = ResponseEntity.badRequest().build();

		try {
			int count = workflowDefinitionService.countWorkflowProcessesByWorkflowDefinitionId(workflowDefinitionId);

			JSONObject jo = new JSONObject();
			jo.put("record_count", count);
			result = ResponseEntity.ok().body(jo.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping(path = "/status/{workflowDefinitionId}")
	public ResponseEntity<?> getWorkflowDefinitionStatus(@PathVariable("workflowDefinitionId") long workflowDefinitionId) {
		ResponseEntity<?> result = ResponseEntity.badRequest().build();

		try {
			String status = workflowDefinitionService.getWorkflowDefinitionStatus(workflowDefinitionId);
			JSONObject jo = new JSONObject();
			jo.put("status", status);
			result = ResponseEntity.ok().body(jo.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping(path = "/definition")
	public List<WorkflowDefinition> getWorkflowDefinitions() {
		return workflowDefinitionService.findAll();
	}

	@GetMapping(path = "/definition/{workflowDefinitionId}")
	public List<WorkflowDefinitionStep> getWorkflowDefinitionSteps(@PathVariable("workflowDefinitionId") long workflowDefinitionId) {
		return workflowDefinitionStepService.findByWorkflowDefinitionId(workflowDefinitionId);
	}

	@PostMapping(path = "/definition")
	public WorkflowDefinition saveWorkflowDefinition(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();
		Long workflowDefinitionId = mu.getAsLongOrNull(data, "workflowDefinitionId");
		String workflowDefinitionCode = mu.getAsStringOrNull(data, "workflowDefinitionCode");
		String workflowDefinitionName = mu.getAsStringOrNull(data, "workflowDefinitionName");
		String workflowDefinitionDescription = mu.getAsStringOrNull(data, "workflowDefinitionDescription");
		Integer slaMinutes = mu.getAsIntegerOrNull(data, "slaMinutes");
		String failoverMailbox = mu.getAsStringOrNull(data, "failoverMailbox");
		String recordObjectName = mu.getAsStringOrNull(data, "recordObjectName");

		WorkflowDefinition wd;
		if ((workflowDefinitionId == null) || (workflowDefinitionId == -1)) {
			wd = new WorkflowDefinition();
		} else {
			wd = workflowDefinitionService.findByWorkflowDefinitionId(workflowDefinitionId);
		}

		wd.setWorkflowDefinitionCode(workflowDefinitionCode);
		wd.setWorkflowDefinitionName(workflowDefinitionName);
		wd.setWorkflowDefinitionDescription(workflowDefinitionDescription);
		wd.setSlaMinutes(slaMinutes);
		wd.setFailoverMailbox(failoverMailbox);
		wd.setRecordObjectName(recordObjectName);

		return workflowDefinitionService.save(wd);
	}

	@GetMapping(path = "/definition/{workflowDefinitionId}/{workflowDefinitionStepNumber}")
	public WorkflowDefinitionStep getWorkflowDefinitionStep(@PathVariable("workflowDefinitionId") Long workflowDefinitionId,
			 														@PathVariable("workflowDefinitionStepNumber") Integer workflowDefinitionStepNumber) {
		return workflowDefinitionService.findWorkflowDefinitionStep(workflowDefinitionId, workflowDefinitionStepNumber);
	}

	@GetMapping(path = {"/graph/{workflowDefinitionId}",
						"/graph/{workflowDefinitionId}/{workflowState}/{nextStep}"})
	public Map<String, String> getWorkflowDefinitionGraph(@PathVariable("workflowDefinitionId") Long workflowDefinitionId,
			 												@PathVariable(name="workflowState", required=false) String workflowState,
			 												@PathVariable(name="nextStep", required=false) Integer nextStep) {
		String graphText = workflowDefinitionStepsToGraphRepository.
				 						workflowDefinitionStepsToGraph(workflowDefinitionId, workflowState, nextStep);
		return Collections.singletonMap("graphText", graphText);
	}

	@DeleteMapping(path = "/definition")
	public void deleteWorkflowDefinition(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();
		Long workflowDefinitionId = mu.getAsLongOrNull(data, "workflowDefinitionId");
		if (workflowDefinitionId != null) {
			workflowDefinitionService.delete(workflowDefinitionId);
		}
	}

	@DeleteMapping(path = "/definition/step")
	public void deleteWorkflowDefinitionStep(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();
		Long workflowDefinitionId = mu.getAsLongOrNull(data, "workflowDefinitionId");
		Integer workflowDefinitionStepNumber = mu.getAsIntegerOrNull(data, "workflowDefinitionStepNumber");
		if ((workflowDefinitionId != null) && (workflowDefinitionStepNumber != null)) {
			workflowDefinitionStepService.delete(workflowDefinitionId, workflowDefinitionStepNumber);
		}
	}

	@PostMapping(path = "/definition/{workflowDefinitionId}/{workflowDefinitionStepNumber}")
	public WorkflowDefinitionStep saveWorkflowDefinitionStep(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();
		Long workflowDefinitionId = mu.getAsLongOrNull(data, "workflowDefinitionId");
		String workflowDefinitionStepName = mu.getAsStringOrNull(data, "workflowDefinitionStepName");
		Integer workflowDefinitionStepNumber = mu.getAsIntegerOrNull(data, "workflowDefinitionStepNumber");
		String pauseWorkflowFlag = mu.getAsStringOrNull(data, "pauseWorkflowFlag");
		String markFailedFlag = mu.getAsStringOrNull(data, "markFailedFlag");
		String markCompletedFlag = mu.getAsStringOrNull(data, "markCompletedFlag");

		// condition
		String testField = mu.getAsStringOrNull(data, "testField");
		String testValue = mu.getAsStringOrNull(data, "testValue");
		Integer ifTrueStepNumber = mu.getAsIntegerOrNull(data, "ifTrueStepNumber");
		Integer ifFalseStepNumber = mu.getAsIntegerOrNull(data, "ifFalseStepNumber");
		Integer gotoStepNumber = mu.getAsIntegerOrNull(data, "gotoStepNumber");

		// mail
		String mailbox = mu.getAsStringOrNull(data, "mailbox");
		String mailSubject = mu.getAsStringOrNull(data, "mailSubject");
		String mailBodyText = mu.getAsStringOrNull(data, "mailBodyText");

		// sql
		String sqlText = mu.getAsStringOrNull(data, "sqlText");

		// Workflow
		String triggerWorkflowDefinitionCode = mu.getAsStringOrNull(data, "triggerWorkflowDefinitionCode");

		WorkflowDefinitionStep workflowDefinitionStep = workflowDefinitionStepService.
															findByWorkflowDefinitionIdAndWorkflowDefinitionStepNumber(workflowDefinitionId,
																														workflowDefinitionStepNumber);

		if (workflowDefinitionStep == null) {
			workflowDefinitionStep = new WorkflowDefinitionStep();

			workflowDefinitionStep.setWorkflowDefinitionId(workflowDefinitionId);
			workflowDefinitionStep.setWorkflowDefinitionStepNumber(workflowDefinitionStepNumber);
		}

		workflowDefinitionStep.setWorkflowDefinitionStepName(workflowDefinitionStepName);
		workflowDefinitionStep.setWorkflowDefinitionStepNumber(workflowDefinitionStepNumber);

		workflowDefinitionStep.setPauseWorkflowFlag(pauseWorkflowFlag);
		workflowDefinitionStep.setMarkFailedFlag(markFailedFlag);
		workflowDefinitionStep.setMarkCompletedFlag(markCompletedFlag);

		// condition
		workflowDefinitionStep.setTestField(testField);
		workflowDefinitionStep.setTestValue(testValue);
		workflowDefinitionStep.setIfTrueStepNumber(ifTrueStepNumber);
		workflowDefinitionStep.setIfFalseStepNumber(ifFalseStepNumber);
		workflowDefinitionStep.setGotoStepNumber(gotoStepNumber);

		// mail
		workflowDefinitionStep.setMailbox(mailbox);
		workflowDefinitionStep.setMailSubject(mailSubject);
		workflowDefinitionStep.setMailBodyText(mailBodyText);

		// sql
		workflowDefinitionStep.setSqlText(sqlText);

		// workflow
		workflowDefinitionStep.setTriggerWorkflowDefinitionCode(triggerWorkflowDefinitionCode);

		return workflowDefinitionStepService.save(workflowDefinitionStep);
	}

	@DeleteMapping(path = "/process")
	public ResponseEntity<?> terminateWorkflow(@RequestBody Map<String, Object> data) {
		ResponseEntity<?> result = ResponseEntity.badRequest().build();

		String role = securityUtils.getRole();
		if (
				(RoleConstants.ROLE_MONITOR.equals(role)) ||
				(RoleConstants.ROLE_MONITOR_APPROVE.equals(role))
			) {
			return result;
		}

		MapUtils mu = new MapUtils();
		Long workflowProcessId = mu.getAsLongOrNull(data, "workflowProcessId");

		if (workflowProcessId != null) {
			workflowProcessService.terminateWorkflowProcess(workflowProcessId, securityUtils.getUsername());
			result = ResponseEntity.ok().build();
		}

		return result;
	}

	@GetMapping(path = "/dialog/{workflowProcessId}")
	public ResponseEntity<?> getWorkflowProcessDialog(@PathVariable("workflowProcessId") long workflowProcessId) {
		ResponseEntity<?> result = ResponseEntity.badRequest().build();

		try {
			String html = workflowProcessToGenericDialog.generate(workflowProcessId);

			JSONObject jo = new JSONObject();
			jo.put("dialogHtml", html);
			result = ResponseEntity.ok().body(jo.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping(path="/history/{workflowProcessId}")
	public List<WorkflowProcessLog> getWorkflowProcessLogEntries(@PathVariable("workflowProcessId") long workflowProcessId) {
		return workflowProcessLogService.getWorkflowProcessLogEntries(workflowProcessId);
	}
}