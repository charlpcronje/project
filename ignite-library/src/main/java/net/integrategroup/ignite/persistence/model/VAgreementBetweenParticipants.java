package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "vAgreementBetweenParticipants")
public class VAgreementBetweenParticipants implements Serializable {

	private static final long serialVersionUID = 8194681108525071004L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "AgreementName")
	private String agreementName;

	@Column(name = "AgreementBudget")
	private Double agreementBudget;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;

	@Column(name = "ProjectTitle")
	private String projectTitle;

	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@Column(name = "Level")
	private Long level;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "SystemNamePayer")
	private String systemNamePayer;

	@Column(name = "ParticipantIdBeneficiary")
	private Long participantIdBeneficiary;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	@Column(name = "RemunerationModelCode")
	private String remunerationModelCode;

	@Column(name = "RemunerationModelName")
	private String remunerationModelName;

	@Column(name = "Description")
	private String description;

	@Column(name = "FSPreSplitContractingExpDeduct")
	private String fsPreSplitContractingExpDeduct;

	@Column(name = "FSPreSplitContractingThirdPDeduct")
	private String fsPreSplitContractingThirdPDeduct;

	@Column(name = "FSContractedExpensesAdded")
	private String fsContractedExpensesAdded;

	@Column(name = "FSPreSplitOtherPartInvoices")
	private String fsPreSplitOtherPartInvoices;


	public String getAgreementName() {
		return agreementName;
	}

	public void setAgreementName(String agreementName) {
		this.agreementName = agreementName;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Double getAgreementBudget() {
		return agreementBudget;
	}

	public void setAgreementBudget(Double agreementBudget) {
		this.agreementBudget = agreementBudget;
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

	public String getProjectNumberText() {
		return projectNumberText;
	}

	public void setProjectNumberText(String projectNumberText) {
		this.projectNumberText = projectNumberText;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
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

	public String getRemunerationModelCode() {
		return remunerationModelCode;
	}

	public void setRemunerationModelCode(String remunerationModelCode) {
		this.remunerationModelCode = remunerationModelCode;
	}

	public String getRemunerationModelName() {
		return remunerationModelName;
	}

	public void setRemunerationModelName(String remunerationModelName) {
		this.remunerationModelName = remunerationModelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFsPreSplitContractingExpDeduct() {
		return fsPreSplitContractingExpDeduct;
	}

	public void setFsPreSplitContractingExpDeduct(String fsPreSplitContractingExpDeduct) {
		this.fsPreSplitContractingExpDeduct = fsPreSplitContractingExpDeduct;
	}

	public String getFsPreSplitContractingThirdPDeduct() {
		return fsPreSplitContractingThirdPDeduct;
	}

	public void setFsPreSplitContractingThirdPDeduct(String fsPreSplitContractingThirdPDeduct) {
		this.fsPreSplitContractingThirdPDeduct = fsPreSplitContractingThirdPDeduct;
	}

	public String getFsContractedExpensesAdded() {
		return fsContractedExpensesAdded;
	}

	public void setFsContractedExpensesAdded(String fsContractedExpensesAdded) {
		this.fsContractedExpensesAdded = fsContractedExpensesAdded;
	}

	public String getFsPreSplitOtherPartInvoices() {
		return fsPreSplitOtherPartInvoices;
	}

	public void setFsPreSplitOtherPartInvoices(String fsPreSplitOtherPartInvoices) {
		this.fsPreSplitOtherPartInvoices = fsPreSplitOtherPartInvoices;
	}

}
