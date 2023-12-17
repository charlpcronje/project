package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(schema = "ig_db", name = "vVehicle")
public class VVehicle implements Serializable {


	private static final long serialVersionUID = 6449892407216912589L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "VehicleName")
	private String vehicleName;

	@Column(name = "ModelName")
	private String modelName;

	@Column(name = "VehicleModelId")
	private Long vehicleModelId;

	@Column(name = "MakeName")
	private String makeName;

	@Column(name = "VehicleMakeId")
	private Long vehicleMakeId;

	@Column(name = "OwnerId")
	private Long ownerId;

	@Column(name = "OwnerName")
	private String ownerName;

	@Column(name = "LicenceNumber")
	private String licenceNumber;

	@Column(name = "OdometerReading")
	private Long odometerReading;

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public Long getVehicleMakeId() {
		return vehicleMakeId;
	}

	public void setVehicleMakeId(Long vehicleMakeId) {
		this.vehicleMakeId = vehicleMakeId;
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

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public Long getOdometerReading() {
		return odometerReading;
	}

	public void setOdometerReading(Long odometerReading) {
		this.odometerReading = odometerReading;
	}



}
