package net.integrategroup.ignite.persistence.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import net.integrategroup.ignite.persistence.model.WorkflowProcess;

public interface WorkflowProcessInitializeRepository extends CrudRepository<WorkflowProcess, Long> {

	@Transactional
	@Procedure(procedureName="wflow.WorkflowInitialize")
	void workflowInitialize(@Param("className") String className,
			                @Param("processName") String processName,
			                @Param("sourceEntity") String sourceEntity,
			                @Param("sourceWhereClause") String sourceWhereClause);

}
