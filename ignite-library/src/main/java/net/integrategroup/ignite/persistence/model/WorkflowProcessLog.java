package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="ig_db", name="WorkflowProcessLog")
public class WorkflowProcessLog implements Serializable {

	private static final long serialVersionUID = 6442437824751334172L;

	@Id
	@Column(name="WorkflowProcessLog_id")
	private Long workflowProcessLogId;

	@Column(name="WorkflowProcess_id")
	private Long workflowProcessId;

	@Column(name="WorkflowDefinitionStepNumber")
	private Integer workflowDefinitionStepNumber;

	@Column(name="WorkflowDefinitionStep_name")
	private String workflowDefinitionStepName;

	@Column(name="Log_Message")
	private String logMessage;

	@Column(name="Log_Timestamp")
	private Date logTimestamp;

	@Column(name="WorkflowProcessStatus")
	private String workflowProcessStatus;

	public Long getWorkflowProcessLogId() {
		return workflowProcessLogId;
	}

	public void setWorkflowProcessLogId(Long workflowProcessLogId) {
		this.workflowProcessLogId = workflowProcessLogId;
	}

	public Long getWorkflowProcessId() {
		return workflowProcessId;
	}

	public void setWorkflowProcessId(Long workflowProcessId) {
		this.workflowProcessId = workflowProcessId;
	}

	public Date getLogTimestamp() {
		return logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	public Integer getWorkflowDefinitionStepNumber() {
		return workflowDefinitionStepNumber;
	}

	public void setWorkflowDefinitionStepNumber(Integer workflowDefinitionStepNumber) {
		this.workflowDefinitionStepNumber = workflowDefinitionStepNumber;
	}

	public String getWorkflowDefinitionStepName() {
		return workflowDefinitionStepName;
	}

	public void setWorkflowDefinitionStepName(String workflowDefinitionStepName) {
		this.workflowDefinitionStepName = workflowDefinitionStepName;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String getWorkflowProcessStatus() {
		return workflowProcessStatus;
	}

	public void setWorkflowProcessStatus(String workflowProcessStatus) {
		this.workflowProcessStatus = workflowProcessStatus;
	}

}

