package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="ig_db", name="WorkflowDefinitionStep")
public class WorkflowDefinitionStep implements Serializable {

	private static final long serialVersionUID = -3263288564763663093L;

	@EmbeddedId
	private WorkflowDefinitionStepPrimaryKey workflowDefinitionStepPrimaryKey;

	@Column(name="WorkflowDefinition_id",
			updatable = false,
			insertable = false)
	private Long workflowDefinitionId;

	@Column(name="WorkflowDefinitionStep_name")
	private String workflowDefinitionStepName;

	@Column(name="WorkflowDefinitionStepNumber",
			updatable = false,
			insertable = false)
	private Integer workflowDefinitionStepNumber;

	@Column(name="Mailbox")
	private String mailbox;

	@Column(name="TestField")
	private String testField;

	@Column(name="TestValue")
	private String testValue;

	@Column(name="IfTrueStepNumber")
	private Integer ifTrueStepNumber;

	@Column(name="IfFalseStepNumber")
	private Integer ifFalseStepNumber;

	@Column(name="MarkFailed_flag")
	private String markFailedFlag;

	@Column(name="MarkCompleted_flag")
	private String markCompletedFlag;

	@Column(name="PauseWorkflow_flag")
	private String pauseWorkflowFlag;

	@Column(name="MailSubject")
	private String mailSubject;

	@Column(name="MailBody_text")
	private String mailBodyText;

	@Column(name="Sql_text")
	private String sqlText;

	@Column(name="GotoStepNumber")
	private Integer gotoStepNumber;

	@Column(name="TriggerWorkflowDefinitionCode")
	private String triggerWorkflowDefinitionCode;

	public WorkflowDefinitionStep() {
		workflowDefinitionStepPrimaryKey = new WorkflowDefinitionStepPrimaryKey();
	}

	public Long getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}

	public void setWorkflowDefinitionId(Long workflowDefinitionId) {
		this.workflowDefinitionStepPrimaryKey.setWorkflowDefinitionId(workflowDefinitionId);
		this.workflowDefinitionId = workflowDefinitionId;
	}

	public Integer getWorkflowDefinitionStepNumber() {
		return workflowDefinitionStepNumber;
	}

	public void setWorkflowDefinitionStepNumber(Integer workflowDefinitionStepNumber) {
		this.workflowDefinitionStepPrimaryKey.setWorkflowDefinitionStepNumber(workflowDefinitionStepNumber);
		this.workflowDefinitionStepNumber = workflowDefinitionStepNumber;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getTestField() {
		return testField;
	}

	public void setTestField(String testField) {
		this.testField = testField;
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public Integer getIfTrueStepNumber() {
		return ifTrueStepNumber;
	}

	public void setIfTrueStepNumber(Integer ifTrueStepNumber) {
		this.ifTrueStepNumber = ifTrueStepNumber;
	}

	public Integer getIfFalseStepNumber() {
		return ifFalseStepNumber;
	}

	public void setIfFalseStepNumber(Integer ifFalseStepNumber) {
		this.ifFalseStepNumber = ifFalseStepNumber;
	}

	public String getMarkFailedFlag() {
		return markFailedFlag;
	}

	public void setMarkFailedFlag(String markFailedFlag) {
		this.markFailedFlag = markFailedFlag;
	}

	public String getMarkCompletedFlag() {
		return markCompletedFlag;
	}

	public void setMarkCompletedFlag(String markCompletedFlag) {
		this.markCompletedFlag = markCompletedFlag;
	}

	public String getPauseWorkflowFlag() {
		return pauseWorkflowFlag;
	}

	public void setPauseWorkflowFlag(String pauseWorkflowFlag) {
		this.pauseWorkflowFlag = pauseWorkflowFlag;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBodyText() {
		return mailBodyText;
	}

	public void setMailBodyText(String mailBodyText) {
		this.mailBodyText = mailBodyText;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public String getWorkflowDefinitionStepName() {
		return workflowDefinitionStepName;
	}

	public void setWorkflowDefinitionStepName(String workflowDefinitionStepName) {
		this.workflowDefinitionStepName = workflowDefinitionStepName;
	}

	public Integer getGotoStepNumber() {
		return gotoStepNumber;
	}

	public void setGotoStepNumber(Integer gotoStepNumber) {
		this.gotoStepNumber = gotoStepNumber;
	}

	public String getTriggerWorkflowDefinitionCode() {
		return triggerWorkflowDefinitionCode;
	}

	public void setTriggerWorkflowDefinitionCode(String triggerWorkflowDefinitionCode) {
		this.triggerWorkflowDefinitionCode = triggerWorkflowDefinitionCode;
	}

	public WorkflowDefinitionStepPrimaryKey getWorkflowDefinitionStepPrimaryKey() {
		return workflowDefinitionStepPrimaryKey;
	}

	public void setWorkflowDefinitionStepPrimaryKey(WorkflowDefinitionStepPrimaryKey workflowDefinitionStepPrimaryKey) {
		this.workflowDefinitionStepPrimaryKey = workflowDefinitionStepPrimaryKey;
	}
}
