package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Ingrid Marais */

@Entity
@Table(schema = "ig_db", name = "vParticipantExpenseCostTotalsPerProject")
public class VParticipantExpenseCostTotalsPerProject implements Serializable {

	private static final long serialVersionUID = 1634117236250426532L;

	@Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

	@Column(name = "UnitTypeName")
	private String unitTypeName;

	@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ParticipantInAgreementContracting")
	private String participantInAgreementContracting;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "ParticipantInAgreementContracted")
	private String participantInAgreementContracted;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PurchaseDate")
	private Date purchaseDate;

	@Column(name = "ExpenseRateForDate")
	private Double expenseRateForDate;

	@Column(name = "SumNrOfUnits")
	private Double sumNrOfUnits;

	@Column(name = "LineAmount")
	private Double lineAmount;

	@Column(name = "RatesMissing")
	private Integer ratesMissing;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;


	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
	}

	public Long getRxpenseTypeId() {
		return expenseTypeId;
	}

	public void setRxpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
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

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public Double getExpenseRateForDate() {
		return expenseRateForDate;
	}

	public void setExpenseRateForDate(Double expenseRateForDate) {
		this.expenseRateForDate = expenseRateForDate;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantInAgreementContracting() {
		return participantInAgreementContracting;
	}

	public void setParticipantInAgreementContracting(String participantInAgreementContracting) {
		this.participantInAgreementContracting = participantInAgreementContracting;
	}

	public String getParticipantInAgreementContracted() {
		return participantInAgreementContracted;
	}

	public void setParticipantInAgreementContracted(String participantInAgreementContracted) {
		this.participantInAgreementContracted = participantInAgreementContracted;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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

}