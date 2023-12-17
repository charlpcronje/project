package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="ig_db", name="WorkflowProcess")
public class WorkflowProcess implements Serializable {

	private static final long serialVersionUID = 5526961907396874497L;

	@Id
	@Column(name="WorkflowProcess_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workflowProcessId;

	@Column(name="WorkflowDefinition_id",
			updatable=false,
			insertable=false)
	private Long workflowDefinitionId;

	@OneToOne(targetEntity=WorkflowDefinition.class)
	@JoinColumn(name="WorkflowDefinition_id",
	            referencedColumnName="WorkflowDefinition_id")
	private WorkflowDefinition workflowDefinition;

	@Column(name="RecordObjectName")
	private String recordObjectName;

	@Column(name="RecordWhereClause")
	private String recordWhereClause;

	@Column(name="LastStep")
	private Integer lastStep;

	@Column(name="NextStep")
	private Integer nextStep;

	@Column(name="StartDateTime")
	private Date startDateTime;

	@Column(name="ExecuteDateTime")
	private Date executeDateTime;

	@Column(name="Active_flag")
	private String activeFlag;

	@Column(name="Status_code")
	private String statusCode;

	public Long getWorkflowProcessId() {
		return workflowProcessId;
	}

	public void setWorkflowProcessId(Long workflowProcessId) {
		this.workflowProcessId = workflowProcessId;
	}

	public Long getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}

	public void setWorkflowDefinitionId(Long workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}

	public String getRecordObjectName() {
		return recordObjectName;
	}

	public void setRecordObjectName(String recordObjectName) {
		this.recordObjectName = recordObjectName;
	}

	public String getRecordWhereClause() {
		return recordWhereClause;
	}

	public void setRecordWhereClause(String recordWhereClause) {
		this.recordWhereClause = recordWhereClause;
	}

	public Integer getLastStep() {
		return lastStep;
	}

	public void setLastStep(Integer lastStep) {
		this.lastStep = lastStep;
	}

	public Integer getNextStep() {
		return nextStep;
	}

	public void setNextStep(Integer nextStep) {
		this.nextStep = nextStep;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getExecuteDateTime() {
		return executeDateTime;
	}

	public void setExecuteDateTime(Date executeDateTime) {
		this.executeDateTime = executeDateTime;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(WorkflowDefinition workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

}
