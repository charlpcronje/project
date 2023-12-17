package net.integrategroup.ignite.model;

import java.util.Date;

public class MedicalDependantDto {

	private Long medicalDependantId;

	private long mainMemberMedicalInsuranceId;

	private Long individualIdDependant;

	private String description;

	private String displayName;

	private Date lastUpdateTimestamp;

	private String lastUpdateUserName;

	public MedicalDependantDto(
			Long medicalDependantId,
			Long mainMemberMedicalInsuranceId,
			Long individualIdDependant,
			String description,
			String displayName,
			Date lastUpdateTimestamp,
			String lastUpdateUserName) {
		this.medicalDependantId = medicalDependantId;
		this.mainMemberMedicalInsuranceId = mainMemberMedicalInsuranceId;
		this.individualIdDependant = individualIdDependant;
		this.description = description;
		this.displayName = displayName;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
		this.lastUpdateUserName = lastUpdateUserName;
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

	public Long getMedicalDependantId() {
		return medicalDependantId;
	}

	public void setMedicalDependantId(Long medicalDependantId) {
		this.medicalDependantId = medicalDependantId;
	}

	public long getMainMemberMedicalInsuranceId() {
		return mainMemberMedicalInsuranceId;
	}

	public void setMainMemberMedicalInsuranceId(long mainMemberMedicalInsuranceId) {
		this.mainMemberMedicalInsuranceId = mainMemberMedicalInsuranceId;
	}

	public Long getIndividualIdDependant() {
		return individualIdDependant;
	}

	public void setIndividualIdDependant(Long individualIdDependant) {
		this.individualIdDependant = individualIdDependant;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
