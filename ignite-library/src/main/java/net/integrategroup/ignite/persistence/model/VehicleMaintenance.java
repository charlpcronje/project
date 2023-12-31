package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL) */

@Entity
@Table(schema = "ig_db", name = "VehicleMaintenance")
public class VehicleMaintenance implements Serializable {



	private static final long serialVersionUID = -6568059666302608293L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VehicleMaintenanceId")
	private Long vehicleMaintenanceId;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "MaintenanceTypeId")
	private Long maintenanceTypeId;

	@OneToOne(targetEntity = MaintenanceType.class)
	@JoinColumn(name = "MaintenanceTypeId", referencedColumnName = "MaintenanceTypeId", nullable = true, insertable = false, updatable = false)
	private MaintenanceType maintenanceType;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "MaintenanceDate")
	private Date maintenanceDate;

	@Column(name = "KilometerReading")
	private Long kilometerReading;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public MaintenanceType getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(MaintenanceType maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public Long getVehicleMaintenanceId() {
		return vehicleMaintenanceId;
	}

	public void setVehicleMaintenanceId(Long vehicleMaintenanceId) {
		this.vehicleMaintenanceId = vehicleMaintenanceId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getMaintenanceTypeId() {
		return maintenanceTypeId;
	}

	public void setMaintenanceTypeId(Long maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public Long getKilometerReading() {
		return kilometerReading;
	}

	public void setKilometerReading(Long kilometerReading) {
		this.kilometerReading = kilometerReading;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}


}