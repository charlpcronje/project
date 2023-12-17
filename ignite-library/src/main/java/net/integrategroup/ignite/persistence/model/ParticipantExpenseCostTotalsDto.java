package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class ParticipantExpenseCostTotalsDto {

	private Long participantIdContracting;
	private String participantInAgreementContracting;
	private Long participantIdContracted;
	private String participantInAgreementContracted;
	private Double sumNrOfUnits;
	private Double lineAmount;
	private Long ratesMissing;

	public ParticipantExpenseCostTotalsDto() {
		// nothing
	}

	public ParticipantExpenseCostTotalsDto
			(
				Long participantIdContracting,
				String participantInAgreementContracting,
				Long participantIdContracted,
				String participantInAgreementContracted,
				Double sumNrOfUnits,
				Double lineAmount,
				Long ratesMissing) {

		this();
		this.participantIdContracting = participantIdContracting;
		this.participantInAgreementContracting = participantInAgreementContracting;
		this.participantIdContracted = participantIdContracted;
		this.participantInAgreementContracted = participantInAgreementContracted;
		this.sumNrOfUnits = sumNrOfUnits;
		this.lineAmount = lineAmount;
		this.ratesMissing = ratesMissing;
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

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantInAgreementContracted() {
		return participantInAgreementContracted;
	}

	public void setParticipantInAgreementContracted(String participantInAgreementContracted) {
		this.participantInAgreementContracted = participantInAgreementContracted;
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

	public Long getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Long ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

}
