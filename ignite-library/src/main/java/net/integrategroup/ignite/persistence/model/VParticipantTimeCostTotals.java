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
@Table(schema = "ig_db", name = "vParticipantTimeCostTotals")
public class VParticipantTimeCostTotals implements Serializable {

	private static final long serialVersionUID = 7543831527542082350L;

	@Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "AgreementParticipantIdPayer")
	private Long agreementParticipantIdPayer;

	@Column(name = "AgreementPayer")
	private String agreementPayer;

	@Column(name = "AgreementParticipantIdBeneficiary")
	private Long agreementParticipantIdBeneficiary;

	@Column(name = "AgreementBeneficiary")
	private String agreementBeneficiary;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActivityDate")
	private Date activityDate;

	@Column(name = "SumNrOfUnits")
	private Double sumNrOfUnits;

	@Column(name = "LineAmount")
	private Double lineAmount;

	@Column(name = "RatesMissing")
	private Integer ratesMissing;

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Integer getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Integer ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getAgreementParticipantIdPayer() {
		return agreementParticipantIdPayer;
	}

	public void setAgreementParticipantIdPayer(Long agreementParticipantIdPayer) {
		this.agreementParticipantIdPayer = agreementParticipantIdPayer;
	}

	public String getAgreementPayer() {
		return agreementPayer;
	}

	public void setAgreementPayer(String agreementPayer) {
		this.agreementPayer = agreementPayer;
	}

	public Long getAgreementParticipantIdBeneficiary() {
		return agreementParticipantIdBeneficiary;
	}

	public void setAgreementParticipantIdBeneficiary(Long agreementParticipantIdBeneficiary) {
		this.agreementParticipantIdBeneficiary = agreementParticipantIdBeneficiary;
	}

	public String getAgreementBeneficiary() {
		return agreementBeneficiary;
	}

	public void setAgreementBeneficiary(String agreementBeneficiary) {
		this.agreementBeneficiary = agreementBeneficiary;
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

}