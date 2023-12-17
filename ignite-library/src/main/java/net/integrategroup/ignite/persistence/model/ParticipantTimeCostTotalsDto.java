package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class ParticipantTimeCostTotalsDto {

	private Long agreementParticipantIdPayer;
	private String agreementPayer;
	private Long agreementParticipantIdBeneficiary;
	private String agreementBeneficiary;
	private Double sumNrOfUnits;
	private Double lineAmount;
	private Long ratesMissing;

	public ParticipantTimeCostTotalsDto() {
		// nothing
	}

	public ParticipantTimeCostTotalsDto
			(
				Long agreementParticipantIdPayer,
				String agreementPayer,
				Long agreementParticipantIdBeneficiary,
				String agreementBeneficiary,
				Double sumNrOfUnits,
				Double lineAmount,
				Long ratesMissing) {

		this();
		this.agreementParticipantIdPayer = agreementParticipantIdPayer;
		this.agreementPayer = agreementPayer;
		this.agreementParticipantIdBeneficiary = agreementParticipantIdBeneficiary;
		this.agreementBeneficiary = agreementBeneficiary;
		this.sumNrOfUnits = sumNrOfUnits;
		this.lineAmount = lineAmount;
		this.ratesMissing = ratesMissing;
	}

	public Long getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Long ratesMissing) {
		this.ratesMissing = ratesMissing;
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
