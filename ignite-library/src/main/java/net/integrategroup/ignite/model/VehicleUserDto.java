package net.integrategroup.ignite.model;

import java.util.Date;

public class VehicleUserDto {

	private Long vehicleUserId;
	private Long vehicleId;
	private Long participantId;
	private String description;
	private Date lastUpdateTimestamp;
	private String lastUpdateUserName;
	private String systemName;

	public VehicleUserDto() {
		//nothing
	}

	public VehicleUserDto(Long vehicleUserId,
							Long vehicleId,
							Long participantId,
							String description,
							Date lastUpdateTimestamp,
							String lastUpdateUserName,
							String systemName) {

		this.vehicleUserId = vehicleUserId;
		this.vehicleId = vehicleId;
		this.participantId = participantId;
		this.description = description;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
		this.lastUpdateUserName = lastUpdateUserName;
		this.systemName = systemName;
	}



	public Long getVehicleUserId() {
		return vehicleUserId;
	}
	public void setVehicleUserId(Long vehicleUserId) {
		this.vehicleUserId = vehicleUserId;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Long getParticipantId() {
		return participantId;
	}
	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}


}
