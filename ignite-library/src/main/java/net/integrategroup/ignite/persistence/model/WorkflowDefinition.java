package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="ig_db", name="v_WorkflowDefinition")
public class WorkflowDefinition implements Serializable {

	private static final long serialVersionUID = 7268205403594147895L;

	@Id
	@Column(name="WorkflowDefinition_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workflowDefinitionId;

	@Column(name="WorkflowDefinition_code")
	private String workflowDefinitionCode;

	@Column(name="RecordObjectName")
	private String recordObjectName;

	@Column(name="WorkflowDefinitionName")
	private String workflowDefinitionName;

	@Column(name="workflowDefinitionDescription")
	private String workflowDefinitionDescription;

	@Column(name="SlaMinutes")
	private Integer slaMinutes;

	@Column(name="FailoverMailbox")
	private String failoverMailbox;

	@Column(name="WorkflowDefinitionStep_count",
			insertable=false,
			updatable=false)
	private Integer workflowDefinitionStepCount;

	@Column(name="InUse_flag",
			insertable=false,
			updatable=false)
	private String inUse;

	public Long getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}

	public void setWorkflowDefinitionId(Long workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}

	public String getWorkflowDefinitionCode() {
		return workflowDefinitionCode;
	}

	public void setWorkflowDefinitionCode(String workflowDefinitionCode) {
		this.workflowDefinitionCode = workflowDefinitionCode;
	}

	public String getWorkflowDefinitionName() {
		return workflowDefinitionName;
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		this.workflowDefinitionName = workflowDefinitionName;
	}

	public String getWorkflowDefinitionDescription() {
		return workflowDefinitionDescription;
	}

	public void setWorkflowDefinitionDescription(String workflowDefinitionDescription) {
		this.workflowDefinitionDescription = workflowDefinitionDescription;
	}

	public Integer getSlaMinutes() {
		return slaMinutes;
	}

	public void setSlaMinutes(Integer slaMinutes) {
		this.slaMinutes = slaMinutes;
	}

	public String getFailoverMailbox() {
		return failoverMailbox;
	}

	public void setFailoverMailbox(String failoverMailbox) {
		this.failoverMailbox = failoverMailbox;
	}

	public Integer getWorkflowDefinitionStepCount() {
		return workflowDefinitionStepCount;
	}

	public void setWorkflowDefinitionStepCount(Integer workflowDefinitionStepCount) {
		this.workflowDefinitionStepCount = workflowDefinitionStepCount;
	}

	public String getInUse() {
		return inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getRecordObjectName() {
		return recordObjectName;
	}

	public void setRecordObjectName(String recordObjectName) {
		this.recordObjectName = recordObjectName;
	}
}
