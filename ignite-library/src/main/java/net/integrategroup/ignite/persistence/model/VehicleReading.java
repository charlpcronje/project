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

/** @author Generated by Johannes Marais (JohannesSQL) */

@Entity
@Table(schema = "ig_db", name = "VehicleReading")
public class VehicleReading implements Serializable {



	private static final long serialVersionUID = -5878607091088376821L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VehicleReadingId")
	private Long vehicleReadingId;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ReadingDate")
	private Date readingDate;

	@Column(name = "OdometerReading")
	private Long odometerReading;

	@Column(name = "ThreadDepthmm")
	private Long threadDepthmm;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getVehicleReadingId() {
		return vehicleReadingId;
	}

	public void setVehicleReadingId(Long vehicleReadingId) {
		this.vehicleReadingId = vehicleReadingId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	public Long getOdometerReading() {
		return odometerReading;
	}

	public void setOdometerReading(Long odometerReading) {
		this.odometerReading = odometerReading;
	}

	public Long getThreadDepthmm() {
		return threadDepthmm;
	}

	public void setThreadDepthmm(Long threadDepthmm) {
		this.threadDepthmm = threadDepthmm;
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