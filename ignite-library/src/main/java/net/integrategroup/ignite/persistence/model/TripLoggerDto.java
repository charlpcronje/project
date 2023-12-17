package net.integrategroup.ignite.persistence.model;

public class TripLoggerDto {
    private long participantIdOnBehalfOf;
    private String behalfOf;

    // Constructors

    // Default constructor (no arguments)
    public TripLoggerDto() {
    }

    // Constructor for selected columns
    public TripLoggerDto(long participantIdOnBehalfOf, String behalfOf) {
        this.participantIdOnBehalfOf = participantIdOnBehalfOf;
        this.behalfOf = behalfOf;
    }

    // Getters and setters for fields

    public long getParticipantIdOnBehalfOf() {
        return participantIdOnBehalfOf;
    }

    public void setParticipantIdOnBehalfOf(long participantIdOnBehalfOf) {
        this.participantIdOnBehalfOf = participantIdOnBehalfOf;
    }

    public String getBehalfOf() {
        return behalfOf;
    }

    public void setBehalfOf(String behalfOf) {
        this.behalfOf = behalfOf;
    }
}
