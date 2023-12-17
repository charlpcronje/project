package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "vRecoverableExpenseAgreement")
public class VRecoverableExpenseAgreement implements Serializable {

	private static final long serialVersionUID = -8979615686245431518L;

	@Id
	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "SystemNamePayer")
	private String systemNamePayer;

	@Column(name = "ParticipantIdBeneficiary")
	private Long participantIdBeneficiary;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	@Column(name = "AgreementBudget")
	private Double agreementBudget;

	@Column(name = "Agreement")
	private String agreement;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "ExpenseAgreementDescription")
	private String expenseAgreementDescription;

	@Column(name = "ExpenseBudget")
	private Double expenseBudget;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "AllowHandlingPerc")
	private String allowHandlingPerc;

	@Column(name = "AllowMaxAmtPerUnit")
	private String allowMaxAmtPerUnit;

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

	@Column(name = "UnitName")
	private String unitName;

	@Column(name = "Level")
	private String level;

	@Column(name = "AllowForAllParticipantTypes")
	private String allowForAllParticipantTypes;

	public String getAllowForAllParticipantTypes() {
		return allowForAllParticipantTypes;
	}

	public void setAllowForAllParticipantTypes(String allowForAllParticipantTypes) {
		this.allowForAllParticipantTypes = allowForAllParticipantTypes;
	}

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getSystemNamePayer() {
		return systemNamePayer;
	}

	public void setSystemNamePayer(String systemNamePayer) {
		this.systemNamePayer = systemNamePayer;
	}

	public Long getParticipantIdBeneficiary() {
		return participantIdBeneficiary;
	}

	public void setParticipantIdBeneficiary(Long participantIdBeneficiary) {
		this.participantIdBeneficiary = participantIdBeneficiary;
	}

	public String getSystemNameBeneficiary() {
		return systemNameBeneficiary;
	}

	public void setSystemNameBeneficiary(String systemNameBeneficiary) {
		this.systemNameBeneficiary = systemNameBeneficiary;
	}

	public Double getAgreementBudget() {
		return agreementBudget;
	}

	public void setAgreementBudget(Double agreementBudget) {
		this.agreementBudget = agreementBudget;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseAgreementDescription() {
		return expenseAgreementDescription;
	}

	public void setExpenseAgreementDescription(String expenseAgreementDescription) {
		this.expenseAgreementDescription = expenseAgreementDescription;
	}

	public Double getExpenseBudget() {
		return expenseBudget;
	}

	public void setExpenseBudget(Double expenseBudget) {
		this.expenseBudget = expenseBudget;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getAllowHandlingPerc() {
		return allowHandlingPerc;
	}

	public void setAllowHandlingPerc(String allowHandlingPerc) {
		this.allowHandlingPerc = allowHandlingPerc;
	}

	public String getAllowMaxAmtPerUnit() {
		return allowMaxAmtPerUnit;
	}

	public void setAllowMaxAmtPerUnit(String allowMaxAmtPerUnit) {
		this.allowMaxAmtPerUnit = allowMaxAmtPerUnit;
	}

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
