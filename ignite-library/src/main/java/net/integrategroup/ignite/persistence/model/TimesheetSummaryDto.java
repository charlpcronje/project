package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class TimesheetSummaryDto {

	private Long participantIdThatBookedTime;
	private String participantNameHost;
	private Long projectId;
	private String sdCode;
	private Long sdRoleId;
	private String projectNameText;
	private String sdAndRole;
	private String remunerationTypeName;
	private String unitTypeName;
	private Double totalUnits;
	private String systemNameThatBookedTime;
//	private Double totalAmount;

	public TimesheetSummaryDto() {
		// nothing
	}

	public TimesheetSummaryDto
			(	Long participantIdThatBookedTime,
					String participantNameHost,
					Long projectId,
					String sdCode,
					Long sdRoleId,
					String projectNameText,
					String sdAndRole,
					String remunerationTypeName,
					String unitTypeName,
					Double totalUnits,
					String systemNameThatBookedTime) {
//				, Double totalAmount) {
		this();
		this.participantIdThatBookedTime = participantIdThatBookedTime;
		this.participantNameHost = participantNameHost;
		this.projectId = projectId;
		this.sdCode = sdCode;
		this.sdRoleId = sdRoleId;
		this.projectNameText = projectNameText;
		this.sdAndRole = sdAndRole;
		this.remunerationTypeName = remunerationTypeName;
		this.unitTypeName = unitTypeName;
		this.totalUnits = totalUnits;
		this.systemNameThatBookedTime = systemNameThatBookedTime;
//		this.totalAmount = totalAmount;
	}

	public Long getParticipantIdThatBookedTime() {
		return participantIdThatBookedTime;
	}

	public void setParticipantIdThatBookedTime(Long participantIdThatBookedTime) {
		this.participantIdThatBookedTime = participantIdThatBookedTime;
	}

	public String getParticipantNameHost() {
		return participantNameHost;
	}

	public void setParticipantNameHost(String participantNameHost) {
		this.participantNameHost = participantNameHost;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getSdCode() {
		return sdCode;
	}

	public void setSdCode(String sdCode) {
		this.sdCode = sdCode;
	}

	public Long getSdRoleId() {
		return sdRoleId;
	}

	public void setSdRoleId(Long sdRoleId) {
		this.sdRoleId = sdRoleId;
	}

	public String getProjectNameText() {
		return projectNameText;
	}

	public void setProjectNameText(String projectNameText) {
		this.projectNameText = projectNameText;
	}

	public String getSdAndRole() {
		return sdAndRole;
	}

	public void setSdAndRole(String sdAndRole) {
		this.sdAndRole = sdAndRole;
	}

	public String getRemunerationTypeName() {
		return remunerationTypeName;
	}

	public void setRemunerationTypeName(String remunerationTypeName) {
		this.remunerationTypeName = remunerationTypeName;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Double getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Double totalUnits) {
		this.totalUnits = totalUnits;
	}

	public String getSystemNameThatBookedTime() {
		return systemNameThatBookedTime;
	}

	public void setSystemNameThatBookedTime(String systemNameThatBookedTime) {
		this.systemNameThatBookedTime = systemNameThatBookedTime;
	}

}
