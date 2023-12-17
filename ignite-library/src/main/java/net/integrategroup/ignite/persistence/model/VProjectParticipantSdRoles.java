package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vProjectParticipantSdRoles")
public class VProjectParticipantSdRoles implements Serializable {

	private static final long serialVersionUID = -8764972733166151371L;

	@Id
	@Column(name = "ProjectParticipantSdRoleId")
	private Long projectParticipantSdRoleId;

	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@Column(name = "ProjectSdId")
	private Long projectSdId;

	@Column(name = "SdRoleId")
	private Long sdRoleId;

	@Column(name = "ParticipantIdBeneficiary")
	private Long participantIdBeneficiary;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	@Column(name = "SdId")
	private Long sdId;

	@Column(name = "SdCode")
	private String sdCode;

	@Column(name = "SdName")
	private String sdName;

	@Column(name = "SdAndRole")
	private String sdAndRole;

	@Column(name = "RoleOnAProjectId")
	private Long roleOnAProjectId;

	@Column(name = "RoleOnAProjectName")
	private String roleOnAProjectName;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "ProjectTitle")
	private String projectTitle;

	@Column(name = "ProjectParticipantIdLevel1")
	private Long projectParticipantIdLevel1;

	@Column(name = "ParticipantIdHost")
	private Long participantIdHost;

	@Column(name = "participantNameHost")
	private String participantNameHost;

	@Column(name = "ProjectNumberBigInt")
	private Long projectNumberBigInt;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;


	public Long getSdId() {
		return sdId;
	}

	public void setSdId(Long sdId) {
		this.sdId = sdId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getProjectParticipantSdRoleId() {
		return projectParticipantSdRoleId;
	}

	public void setProjectParticipantSdRoleId(Long projectParticipantSdRoleId) {
		this.projectParticipantSdRoleId = projectParticipantSdRoleId;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public Long getSdRoleId() {
		return sdRoleId;
	}

	public void setSdRoleId(Long sdRoleId) {
		this.sdRoleId = sdRoleId;
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

	public String getSdCode() {
		return sdCode;
	}

	public void setSdCode(String sdCode) {
		this.sdCode = sdCode;
	}

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}

	public String getSdAndRole() {
		return sdAndRole;
	}

	public void setSdAndRole(String sdAndRole) {
		this.sdAndRole = sdAndRole;
	}

	public Long getRoleOnAProjectId() {
		return roleOnAProjectId;
	}

	public void setRoleOnAProjectId(Long roleOnAProjectId) {
		this.roleOnAProjectId = roleOnAProjectId;
	}

	public String getRoleOnAProjectName() {
		return roleOnAProjectName;
	}

	public void setRoleOnAProjectName(String roleOnAProjectName) {
		this.roleOnAProjectName = roleOnAProjectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public Long getProjectParticipantIdLevel1() {
		return projectParticipantIdLevel1;
	}

	public void setProjectParticipantIdLevel1(Long projectParticipantIdLevel1) {
		this.projectParticipantIdLevel1 = projectParticipantIdLevel1;
	}

	public Long getParticipantIdHost() {
		return participantIdHost;
	}

	public void setParticipantIdHost(Long participantIdHost) {
		this.participantIdHost = participantIdHost;
	}

	public String getParticipantNameHost() {
		return participantNameHost;
	}

	public void setParticipantNameHost(String participantNameHost) {
		this.participantNameHost = participantNameHost;
	}

	public Long getProjectNumberBigInt() {
		return projectNumberBigInt;
	}

	public void setProjectNumberBigInt(Long projectNumberBigInt) {
		this.projectNumberBigInt = projectNumberBigInt;
	}

	public String getProjectNumberText() {
		return projectNumberText;
	}

	public void setProjectNumberText(String projectNumberText) {
		this.projectNumberText = projectNumberText;
	}

}
