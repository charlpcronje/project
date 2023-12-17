package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Ingrid Marais */

@Entity
@Table(schema = "ig_db", name = "vRemunerationRateUpline")
public class VRemunerationRateUpline implements Serializable {

	private static final long serialVersionUID = -5631762356613959127L;
	
	@Id
	@Column(name = "RemunerationRateUplineId")
	private Long remunerationRateUplineId;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "ProjectParticipantSdRoleIdIndividual")
	private Long projectParticipantSdRoleIdIndividual;

	@Column(name = "ParticipantIdIndividual")
	private Long participantIdIndividual;

	@Column(name = "ProjBasedRemunTypeId")
	private Long projBasedRemunTypeId;
	
	@Column(name = "ProjectParticipantSdRoleId")
	private Long projectParticipantSdRoleId;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	@Column(name = "SdId")
	private Long sdId;

	@Column(name = "SdCode")
	private String sdCode;

	@Column(name = "SdName")
	private String sdName;

	@Column(name = "RoleOnAProjectId")
	private Long roleOnAProjectId;

	@Column(name = "RoleOnAProjectName")
	private String roleOnAProjectName;

	@Column(name = "ProjectSdId")
	private Long projectSdId;

	@Column(name = "ProjectId")
	private Long projectId;
	
	@Column(name = "ProjectNumberBigInt")
	private Long projectNumberBigInt;

	@Column(name = "ProjectTitle")
	private String projectTitle;

	@Column(name = "ProjBasedRemunTypeName")
	private String projBasedRemunTypeName;

	@Column(name = "ProjBasedRemunTypeUnitCode")
	private String projBasedRemunTypeUnitCode;

	@Column(name = "ProjBasedRemunTypeUnitName")
	private String projBasedRemunTypeUnitName;

	@Column(name = "RemunerationInterval")
	private String remunerationInterval;

	@Column(name = "RatePerUnit")
	private Double ratePerUnit;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	public Long getRemunerationRateUplineId() {
		return remunerationRateUplineId;
	}

	public void setRemunerationRateUplineId(Long remunerationRateUplineId) {
		this.remunerationRateUplineId = remunerationRateUplineId;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Long getProjectParticipantSdRoleIdIndividual() {
		return projectParticipantSdRoleIdIndividual;
	}

	public void setProjectParticipantSdRoleIdIndividual(Long projectParticipantSdRoleIdIndividual) {
		this.projectParticipantSdRoleIdIndividual = projectParticipantSdRoleIdIndividual;
	}

	public Long getParticipantIdIndividual() {
		return participantIdIndividual;
	}

	public void setParticipantIdIndividual(Long participantIdIndividual) {
		this.participantIdIndividual = participantIdIndividual;
	}

	public Long getProjBasedRemunTypeId() {
		return projBasedRemunTypeId;
	}

	public void setProjBasedRemunTypeId(Long projBasedRemunTypeId) {
		this.projBasedRemunTypeId = projBasedRemunTypeId;
	}

	public Long getProjectParticipantSdRoleId() {
		return projectParticipantSdRoleId;
	}

	public void setProjectParticipantSdRoleId(Long projectParticipantSdRoleId) {
		this.projectParticipantSdRoleId = projectParticipantSdRoleId;
	}

	public String getSystemNameBeneficiary() {
		return systemNameBeneficiary;
	}

	public void setSystemNameBeneficiary(String systemNameBeneficiary) {
		this.systemNameBeneficiary = systemNameBeneficiary;
	}

	public Long getSdId() {
		return sdId;
	}

	public void setSdId(Long sdId) {
		this.sdId = sdId;
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

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectNumberBigInt() {
		return projectNumberBigInt;
	}

	public void setProjectNumberBigInt(Long projectNumberBigInt) {
		this.projectNumberBigInt = projectNumberBigInt;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjBasedRemunTypeName() {
		return projBasedRemunTypeName;
	}

	public void setProjBasedRemunTypeName(String projBasedRemunTypeName) {
		this.projBasedRemunTypeName = projBasedRemunTypeName;
	}

	public String getProjBasedRemunTypeUnitCode() {
		return projBasedRemunTypeUnitCode;
	}

	public void setProjBasedRemunTypeUnitCode(String projBasedRemunTypeUnitCode) {
		this.projBasedRemunTypeUnitCode = projBasedRemunTypeUnitCode;
	}

	public String getProjBasedRemunTypeUnitName() {
		return projBasedRemunTypeUnitName;
	}

	public void setProjBasedRemunTypeUnitName(String projBasedRemunTypeUnitName) {
		this.projBasedRemunTypeUnitName = projBasedRemunTypeUnitName;
	}

	public String getRemunerationInterval() {
		return remunerationInterval;
	}

	public void setRemunerationInterval(String remunerationInterval) {
		this.remunerationInterval = remunerationInterval;
	}

	public Double getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(Double ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	
}