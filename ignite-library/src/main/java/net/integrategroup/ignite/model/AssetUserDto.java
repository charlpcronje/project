package net.integrategroup.ignite.model;

import java.util.Date;


public class AssetUserDto {

	private Long assetUserId;
	private Long assetId;
	private Long participantId;
	private String description;
	private Date lastUpdateTimestamp;
	private String lastUpdateUserName;
	private String systemName;
	private String firstName;
	private String lastName;
	private String initials;

	public AssetUserDto() {
		// nothing
	}

	public AssetUserDto(Long assetUserId,
			            Long assetId,
			            Long participantId,
			            String description,
			            Date lastUpdateTimestamp,
			            String lastUpdateUserName,
			            String systemName,
			            String firstName,
			            String lastName,
			            String initials) {

		this.assetUserId = assetUserId;
		this.assetId = assetId;
		this.participantId = participantId;
		this.description = description;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
		this.lastUpdateUserName = lastUpdateUserName;
		this.systemName = systemName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.initials = initials;
	}



	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getAssetUserId() {
		return assetUserId;
	}
	public void setAssetUserId(Long assetUserId) {
		this.assetUserId = assetUserId;
	}
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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
