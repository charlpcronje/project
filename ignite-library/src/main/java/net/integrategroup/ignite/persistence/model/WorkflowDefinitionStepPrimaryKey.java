package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WorkflowDefinitionStepPrimaryKey implements Serializable {

	private static final long serialVersionUID = -6261621368081677146L;

	@Column(name="WorkflowDefinition_id")
	private Long workflowDefinitionId;

	@Column(name="WorkflowDefinitionStepNumber")
	private Integer workflowDefinitionStepNumber;

	public Long getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}

	public void setWorkflowDefinitionId(Long workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}

	public Integer getWorkflowDefinitionStepNumber() {
		return workflowDefinitionStepNumber;
	}

	public void setWorkflowDefinitionStepNumber(Integer workflowDefinitionStepNumber) {
		this.workflowDefinitionStepNumber = workflowDefinitionStepNumber;
	}

}
