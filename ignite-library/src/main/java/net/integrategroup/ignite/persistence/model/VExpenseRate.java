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
@Table(schema = "ig_db", name = "vExpenseRate")
public class VExpenseRate implements Serializable {

	private static final long serialVersionUID = -3890058372795461155L;

	@Id
	@Column(name = "ExpenseRateId")
	private Long expenseRateId;

	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "SystemNamePayer")
	private String systemNamePayer;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "ExpenseTypeUnitName")
	private String expenseTypeUnitName;

	@Column(name = "ProjectParticipantIdPayer")
	private Long projectParticipantIdPayer;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "ProjectParticipantIdBeneficiary")
	private Long projectParticipantIdBeneficiary;

	@Column(name = "ParticipantIdBeneficiary")
	private Long participantIdBeneficiary;

	@Column(name = "ExpenseRatePerUnit")
	private Double expenseRatePerUnit;

	@Column(name = "ExpenseHandlingPerc")
	private Double expenseHandlingPerc;

	@Column(name = "MaxExpenseAmtPerUnit")
	private Double maxExpenseAmtPerUnit;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "ExpenseTypeUnitCode")
	private String expenseTypeUnitCode;

	public Long getExpenseRateId() {
		return expenseRateId;
	}

	public void setExpenseRateId(Long expenseRateId) {
		this.expenseRateId = expenseRateId;
	}

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
	}

	public String getSystemNamePayer() {
		return systemNamePayer;
	}

	public void setSystemNamePayer(String systemNamePayer) {
		this.systemNamePayer = systemNamePayer;
	}

	public String getSystemNameBeneficiary() {
		return systemNameBeneficiary;
	}

	public void setSystemNameBeneficiary(String systemNameBeneficiary) {
		this.systemNameBeneficiary = systemNameBeneficiary;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getExpenseTypeUnitName() {
		return expenseTypeUnitName;
	}

	public void setExpenseTypeUnitName(String expenseTypeUnitName) {
		this.expenseTypeUnitName = expenseTypeUnitName;
	}

	public Long getProjectParticipantIdPayer() {
		return projectParticipantIdPayer;
	}

	public void setProjectParticipantIdPayer(Long projectParticipantIdPayer) {
		this.projectParticipantIdPayer = projectParticipantIdPayer;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public Long getProjectParticipantIdBeneficiary() {
		return projectParticipantIdBeneficiary;
	}

	public void setProjectParticipantIdBeneficiary(Long projectParticipantIdBeneficiary) {
		this.projectParticipantIdBeneficiary = projectParticipantIdBeneficiary;
	}

	public Long getParticipantIdBeneficiary() {
		return participantIdBeneficiary;
	}

	public void setParticipantIdBeneficiary(Long participantIdBeneficiary) {
		this.participantIdBeneficiary = participantIdBeneficiary;
	}

	public Double getExpenseRatePerUnit() {
		return expenseRatePerUnit;
	}

	public void setExpenseRatePerUnit(Double expenseRatePerUnit) {
		this.expenseRatePerUnit = expenseRatePerUnit;
	}

	public Double getExpenseHandlingPerc() {
		return expenseHandlingPerc;
	}

	public void setExpenseHandlingPerc(Double expenseHandlingPerc) {
		this.expenseHandlingPerc = expenseHandlingPerc;
	}

	public Double getMaxExpenseAmtPerUnit() {
		return maxExpenseAmtPerUnit;
	}

	public void setMaxExpenseAmtPerUnit(Double maxExpenseAmtPerUnit) {
		this.maxExpenseAmtPerUnit = maxExpenseAmtPerUnit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseTypeUnitCode() {
		return expenseTypeUnitCode;
	}

	public void setExpenseTypeUnitCode(String expenseTypeUnitCode) {
		this.expenseTypeUnitCode = expenseTypeUnitCode;
	}

}