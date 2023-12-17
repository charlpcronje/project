package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class InvoiceOutLineTotalsDto {

	private Long participantIdContracting;
	private String participantContracting;
	private Long participantIdContracted;
	private String participantContracted;
	private Double sumNrOfUnits;
	private Double lineAmount;
	private Long ratesMissing;

	public InvoiceOutLineTotalsDto() {
		// nothing
	}

	public InvoiceOutLineTotalsDto
			(
				Long participantIdContracting,
				String participantContracting,
				Long participantIdContracted,
				String participantContracted,
				Double sumNrOfUnits,
				Double lineAmount,
				Long ratesMissing) {

		this();
		this.participantIdContracting = participantIdContracting;
		this.participantContracting = participantContracting;
		this.participantIdContracted = participantIdContracted;
		this.participantContracted = participantContracted;
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
