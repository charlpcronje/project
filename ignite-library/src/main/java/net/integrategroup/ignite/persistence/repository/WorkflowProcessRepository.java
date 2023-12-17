package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import net.integrategroup.ignite.persistence.model.WorkflowProcess;

public interface WorkflowProcessRepository extends CrudRepository<WorkflowProcess, Long> {

	@Query("SELECT " +
		   "    wp" +
			"  FROM " +
		    "    WorkflowProcess wp" +
		    "  WHERE " +
		    "    wp.workflowProcessId = ?1")
	WorkflowProcess findByWorkflowProcessId(Long workflowProcessId);

	@Query("SELECT " +
		       "    wp" +
			   "  FROM " +
		       "    WorkflowProcess wp" +
		       "  WHERE " +
		       "    wp.startDateTime BETWEEN ?1 AND ?2" +
		       "  ORDER BY " +
		       "    wp.workflowProcessId DESC")
	List<WorkflowProcess> findByStartDateAndEndDate(Date startDate,
				                                    Date endDate);

	@Query("SELECT " +
	       "    wp" +
		   "  FROM " +
	       "    WorkflowProcess wp" +
	       "  WHERE " +
	       "    wp.startDateTime BETWEEN ?1 AND ?2" +
	       "    AND wp.activeFlag = ?3")
	List<WorkflowProcess> findByStartDateEndDateAndActiveFlag(Date startDate, Date endDate, String activeFlag);

	@Transactional
	@Procedure(procedureName="wflow.WorkflowProcessTerminate")
	void terminateWorkflowProcess(@Param("WorkflowProcessId") Long workflowProcessId,
			                      @Param("Username") String username);

	@Query("SELECT " +
	       "    count(1) " +
		   "  FROM" +
	       "     WorkflowProcess wp" +
		   "  WHERE " +
	       "     activeFlag = 'Y'")
	Integer getActiveWorkflowCount();

}
