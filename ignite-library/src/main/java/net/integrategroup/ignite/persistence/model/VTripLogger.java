package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;


@Entity
@Table(schema = "ig_db", name = "vTripLogger")
public class VTripLogger implements Serializable {

	private static final long serialVersionUID = 5324396330237346634L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TripLoggerId")
	private Long tripLoggerId;

	@Column(name = "ParticipantIdOnBehalfOf")
	private Long participantIdOnBehalfOf;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectNameText")
	private String projectNameText;

	@Column(name = "TripDistance")
	private Long tripDistance;

	@Column(name = "TravelTimeMinutes")
	private Long travelTimeMinutes;

	@Column(name = "InstructedBy")
	private String instructedBy;

	@Column(name = "Driver")
	private String driver;

	@Column(name = "BehalfOf")
	private String behalfOf;

	@Column(name = "TripStartingPointName")
	private String tripStartingPointName;

	@Column(name = "TripEndPointName")
	private String tripEndPointName;

	@Column(name = "VehicleName")
	private String vehicleName;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "LicenceNumber")
	private String licenceNumber;

	@Column(name = "ModelName")
	private String modelName;

	@Column(name = "MakeName")
	private String makeName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "TripOrderDate")
	private Date tripOrderDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "TripDate")
	private Date tripDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "TripStartTime")
	private Date tripStartTime;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "TripEndTime")
	private Date tripEndTime;

	@Column(name = "TripStartCoordinates")
	private Long tripStartCoordinates;

	@Column(name = "TripEndCoordinates")
	private Long tripEndCoordinates;

	@Column(name = "OwnerId")
	private Long ownerId;

	@Column(name = "OwnerName")
	private String ownerName;

	@Column(name = "ParticipantIdDriver")
	private Long participantIdDriver;

	@Column(name = "ParticipantIdGaveInstruction")
	private Long participantIdGaveInstruction;

	@Column(name = "OdometerReading")
	private Long odometerReading;

	@Column(name = "Description")
	private String description;

	@Column(name = "TripStartKm")
	private Long tripStartKm;

	@Column(name = "TripEndKm")
	private Long tripEndKm;

	public Long getTripLoggerId() {
		return tripLoggerId;
	}

	public void setTripLoggerId(Long tripLoggerId) {
		this.tripLoggerId = tripLoggerId;
	}

	public Long getParticipantIdOnBehalfOf() {
		return participantIdOnBehalfOf;
	}

	public void setParticipantIdOnBehalfOf(Long participantIdOnBehalfOf) {
		this.participantIdOnBehalfOf = participantIdOnBehalfOf;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectNameText() {
		return projectNameText;
	}

	public void setProjectNameText(String projectNameText) {
		this.projectNameText = projectNameText;
	}

	public Long getTripDistance() {
		return tripDistance;
	}

	public void setTripDistance(Long tripDistance) {
		this.tripDistance = tripDistance;
	}

	public Long getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	public void setTravelTimeMinutes(Long travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}

	public String getInstructedBy() {
		return instructedBy;
	}

	public void setInstructedBy(String instructedBy) {
		this.instructedBy = instructedBy;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getBehalfOf() {
		return behalfOf;
	}

	public void setBehalfOf(String behalfOf) {
		this.behalfOf = behalfOf;
	}

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

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public Date getTripOrderDate() {
		return tripOrderDate;
	}

	public void setTripOrderDate(Date tripOrderDate) {
		this.tripOrderDate = tripOrderDate;
	}

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public Date getTripStartTime() {
		return tripStartTime;
	}

	public void setTripStartTime(Date tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public Date getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(Date tripEndTime) {
		this.tripEndTime = tripEndTime;
	}

	public Long getTripStartCoordinates() {
		return tripStartCoordinates;
	}

	public void setTripStartCoordinates(Long tripStartCoordinates) {
		this.tripStartCoordinates = tripStartCoordinates;
	}

	public Long getTripEndCoordinates() {
		return tripEndCoordinates;
	}

	public void setTripEndCoordinates(Long tripEndCoordinates) {
		this.tripEndCoordinates = tripEndCoordinates;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getParticipantIdDriver() {
		return participantIdDriver;
	}

	public void setParticipantIdDriver(Long participantIdDriver) {
		this.participantIdDriver = participantIdDriver;
	}

	public Long getParticipantIdGaveInstruction() {
		return participantIdGaveInstruction;
	}

	public void setParticipantIdGaveInstruction(Long participantIdGaveInstruction) {
		this.participantIdGaveInstruction = participantIdGaveInstruction;
	}

	public Long getOdometerReading() {
		return odometerReading;
	}

	public void setOdometerReading(Long odometerReading) {
		this.odometerReading = odometerReading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTripStartKm() {
		return tripStartKm;
	}

	public void setTripStartKm(Long tripStartKm) {
		this.tripStartKm = tripStartKm;
	}

	public Long getTripEndKm() {
		return tripEndKm;
	}

	public void setTripEndKm(Long tripEndKm) {
		this.tripEndKm = tripEndKm;
	}


}
