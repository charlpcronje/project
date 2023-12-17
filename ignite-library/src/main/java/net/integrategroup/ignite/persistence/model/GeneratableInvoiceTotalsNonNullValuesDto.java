package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class GeneratableInvoiceTotalsNonNullValuesDto {

//	private Long projectId;
//	private String projectName;
	private Long participantIdContracting;
	private String participantContracting;
	private Long participantIdContracted;
	private String participantContracted;
	private Double lineAmount;

	public GeneratableInvoiceTotalsNonNullValuesDto() {
		// nothing
	}

	public GeneratableInvoiceTotalsNonNullValuesDto
			(
//			Long projectId,
//			String projectName,
			Long participantIdContracting,
			String participantContracting,
			Long participantIdContracted,
			String participantContracted,
			Double lineAmount
			) {

		this();
//		this.projectId = projectId;
//		this.projectName = projectName;
		this.participantIdContracting = participantIdContracting;
		this.participantContracting = participantContracting;
		this.participantIdContracted = participantIdContracted;
		this.participantContracted = participantContracted;
		this.lineAmount = lineAmount;
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

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantContracting() {
		return participantContracting;
	}

	public void setParticipantContracting(String participantContracting) {
		this.participantContracting = participantContracting;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantContracted() {
		return participantContracted;
	}

	public void setParticipantContracted(String participantContracted) {
		this.participantContracted = participantContracted;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

}
