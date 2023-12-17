package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.integrategroup.ignite.persistence.model.WorkflowProcessLog;

public interface WorkflowProcessLogRepository extends CrudRepository<WorkflowProcessLog, Long> {

	@Query("SELECT " +
			   "    wpl" +
				"  FROM " +
			    "    WorkflowProcessLog wpl" +
			    "  WHERE " +
			    "    wpl.workflowProcessId = ?1" +
			    "  ORDER BY " +
			    "    workflowProcessLogId DESC")
	List<WorkflowProcessLog> getWorkflowProcessLogEntries(long workflowProcessId);

}
