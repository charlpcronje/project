package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class StatementTotalsDto {

	private Long theParticipantId;
	private String theParticipant;
	private Long theOtherParticipantId;
	private String theOtherParticipant;
	private Double theAmount;

	public StatementTotalsDto() {
		// nothing
	}

	public StatementTotalsDto
			(
				Long theParticipantId,
				String theParticipant,
				Long theOtherParticipantId,
				String theOtherParticipant,
				Double theAmount) {

		this();
		this.theParticipantId = theParticipantId;
		this.theParticipant = theParticipant;
		this.theOtherParticipantId = theOtherParticipantId;
		this.theOtherParticipant = theOtherParticipant;
		this.theAmount = theAmount;
	}

	public Long getTheParticipantId() {
		return theParticipantId;
	}

	public void setTheParticipantId(Long theParticipantId) {
		this.theParticipantId = theParticipantId;
	}

	public String getTheParticipant() {
		return theParticipant;
	}

	public void setTheParticipant(String theParticipant) {
		this.theParticipant = theParticipant;
	}

	public Long getTheOtherParticipantId() {
		return theOtherParticipantId;
	}

	public void setTheOtherParticipantId(Long theOtherParticipantId) {
		this.theOtherParticipantId = theOtherParticipantId;
	}

	public String getTheOtherParticipant() {
		return theOtherParticipant;
	}

	public void setTheOtherParticipant(String theOtherParticipant) {
		this.theOtherParticipant = theOtherParticipant;
	}

	public Double getTheAmount() {
		return theAmount;
	}

	public void setTheAmount(Double theAmount) {
		this.theAmount = theAmount;
	}

}
