package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class AgreementRelationshipDto {

	private Long level;
	private Long projectParticipantId;
	private Long participantIdPayer;
	private String systemNamePayer;
	private Long participantIdBeneficiary;
//	private Long projectId;
//	private String projectName;
	private String systemNameBeneficiary;
	private String relationshipName;

	public AgreementRelationshipDto() {
		// nothing
	}

	public AgreementRelationshipDto
				   (
				    Long level,
				    Long projectParticipantId,
					Long participantIdPayer,
					String systemNamePayer,
					Long participantIdBeneficiary,
					String systemNameBeneficiary,
//					Long projectId,
//					String projectName,
					String relationshipName) {

		this();
		this.level = level;
		this.projectParticipantId = projectParticipantId;
		this.participantIdPayer = participantIdPayer;
		this.systemNamePayer = systemNamePayer;
		this.participantIdBeneficiary = participantIdBeneficiary;
		this.systemNameBeneficiary = systemNameBeneficiary;
//		this.projectId = projectId;
//		this.projectName= projectName;
		this.relationshipName = relationshipName;
	}

//	public Long getProjectId() {
//		return projectId;
//	}
//
//	public void setProjectId(Long projectId) {
//		this.projectId = projectId;
//	}
//
//	public String getProjectName() {
//		return projectName;
//	}
//
//	public void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getSystemNamePayer() {
		return systemNamePayer;
	}

	public void setSystemNamePayer(String systemNamePayer) {
		this.systemNamePayer = systemNamePayer;
	}

	public Long getParticipantIdBeneficiary() {
		return participantIdBeneficiary;
	}

	public void setParticipantIdBeneficiary(Long participantIdBeneficiary) {
		this.participantIdBeneficiary = participantIdBeneficiary;
	}

	public String getSystemNameBeneficiary() {
		return systemNameBeneficiary;
	}

	public void setSystemNameBeneficiary(String systemNameBeneficiary) {
		this.systemNameBeneficiary = systemNameBeneficiary;
	}

}
