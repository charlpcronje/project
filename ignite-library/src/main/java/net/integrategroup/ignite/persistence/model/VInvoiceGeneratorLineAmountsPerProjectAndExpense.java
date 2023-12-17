package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema = "ig_db", name = "vInvoiceGeneratorLineAmountsPerProjectAndExpense")
public class VInvoiceGeneratorLineAmountsPerProjectAndExpense implements Serializable {

	private static final long serialVersionUID = -4104650469827821486L;

	@Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "ExpenseType")
	private String expenseType;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ParticipantContracting")
	private String participantContracting;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "ParticipantContracted")
	private String participantContracted;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActivityDate")
	private Date activityDate;

	@Column(name = "SumNrOfUnits")
	private Double sumNrOfUnits;

	@Column(name = "LineAmount")
	private Double lineAmount;

	@Column(name = "RatesMissing")
	private Integer ratesMissing;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long AgreementBetweenParticipantsId;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
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

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantContracting() {
		return participantContracting;
	}

	public void setParticipantContracting(String participantContracting) {
		this.participantContracting = participantContracting;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantContracted() {
		return participantContracted;
	}

	public void setParticipantContracted(String participantContracted) {
		this.participantContracted = participantContracted;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Double getSumNrOfUnits() {
		return sumNrOfUnits;
	}

	public void setSumNrOfUnits(Double sumNrOfUnits) {
		this.sumNrOfUnits = sumNrOfUnits;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

	public Integer getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Integer ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

	public Long getAgreementBetweenParticipantsId() {
		return AgreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		AgreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

}