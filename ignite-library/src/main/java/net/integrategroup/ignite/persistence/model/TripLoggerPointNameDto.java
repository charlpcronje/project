package net.integrategroup.ignite.persistence.model;

public class TripLoggerPointNameDto {
    private String tripStartingPointName;
    private String tripEndPointName;

    // Constructors

    // Default constructor (no arguments)
    public TripLoggerPointNameDto() {
    }

    // Constructor for selected columns
    public TripLoggerPointNameDto(String tripStartingPointName, String tripEndPointName) {
        this.tripStartingPointName = tripStartingPointName;
        this.tripEndPointName = tripEndPointName;
    }

    // Getters and setters for fields

    public String getTripStartingPointName() {
        return tripStartingPointName;
    }

    public void setTripStartingPointName(String tripStartingPointName) {
        this.tripStartingPointName = tripStartingPointName;
    }

    public String getTripEndPointName() {
        return tripEndPointName;
    }

    public void setTripEndPointName(String tripEndPointName) {
        this.tripEndPointName = tripEndPointName;
    }
}
