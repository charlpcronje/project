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
@Table(schema = "ig_db", name = "TripLogger")
public class TripLogger implements Serializable {

	private static final long serialVersionUID = 4444196303149588615L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TripLoggerId")
	private Long tripLoggerId;

//	@ManyToOne(targetEntity = Vehicle.class, fetch = FetchType.EAGER)
//	@JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId",updatable = false, insertable = false)
//	private Vehicle vehicleId;
//
//	@OneToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
//	@JoinColumn(name = "projectId", referencedColumnName = "projectId", updatable = false, insertable = false)
//	private Project projectId;

	@Column(name = "ParticipantIdGaveInstruction", nullable = true)
	private Long participantIdGaveInstruction;

	@Column(name = "ParticipantIdOnBehalfOf")
	private Long participantIdOnBehalfOf;

	@Column(name = "ParticipantIdDriver")
	private Long participantIdDriver;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "TripStartCoordinates")
	private Long tripStartCoordinates;

	@Column(name = "TripEndCoordinates")
	private Long tripEndCoordinates;

	@Column(name = "TripStartKm")
	private Long tripStartKm;

	@Column(name = "TripEndKm")
	private Long tripEndKm;

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

	@Column(name = "TripStartingPointName")
	private String tripStartingPointName;

	@Column(name = "TripEndPointName")
	private String tripEndPointName;

	@Column(name = "TripDistance")
	private Long tripDistance;

	@Column(name = "TravelTimeMinutes")
	private Long travelTimeMinutes;

	@Column(name = "Description")
	private String description;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	public Long getTripLoggerId() {
		return tripLoggerId;
	}

	public void setTripLoggerId(Long tripLoggerId) {
		this.tripLoggerId = tripLoggerId;
	}

	public Long getParticipantIdGaveInstruction() {
		return participantIdGaveInstruction;
	}

	public void setParticipantIdGaveInstruction(Long participantIdGaveInstruction) {
		this.participantIdGaveInstruction = participantIdGaveInstruction;
	}

	public Long getParticipantIdOnBehalfOf() {
		return participantIdOnBehalfOf;
	}

	public void setParticipantIdOnBehalfOf(Long participantIdOnBehalfOf) {
		this.participantIdOnBehalfOf = participantIdOnBehalfOf;
	}

	public Long getParticipantIdDriver() {
		return participantIdDriver;
	}

	public void setParticipantIdDriver(Long participantIdDriver) {
		this.participantIdDriver = participantIdDriver;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

}
