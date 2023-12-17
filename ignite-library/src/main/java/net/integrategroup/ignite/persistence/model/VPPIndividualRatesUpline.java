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
@Table(schema = "ig_db", name = "vPPIndividualRatesUpline")
public class VPPIndividualRatesUpline implements Serializable {

	private static final long serialVersionUID = 2701869154739883558L;

	@Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "Level")
	private Long level;

	@Column(name = "ProjectName")
	private String projectName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActivityDate")
	private Date activityDate;

	@Column(name = "Description")
	private String description;

	@Column(name = "UnitTypeName")
	private String unitTypeName;

	@Column(name = "NumberOfUnits")
	private Double numberOfUnits;

	@Column(name = "RateForDate")
	private Double rateForDate;

	@Column(name = "SystemNameThatBookedTime")
	private String systemNameThatBookedTime;

	@Column(name = "AgreementBetween")
	private String agreementBetween;

	@Column(name = "SdAndRole")
	private String sdAndRole;
	@Column(name = "TimesheetId")
	private Long timesheetId;

	@Column(name = "ProjectParticipantSdRoleId")
	private Long projectParticipantSdRoleId;

	@Column(name = "RemunerationTypeName")
	private String remunerationTypeName;

	@Column(name = "ProjectParticipantIdThatBookedTime")
	private Long projectParticipantIdThatBookedTime;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ParticipantIdThatBookedTime")
	private Long participantIdThatBookedTime;

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "AgreementParticipantIdPayer")
	private Long agreementParticipantIdPayer;

	@Column(name = "AgreementPayer")
	private String agreementPayer;

	@Column(name = "AgreementParticipantIdBeneficiary")
	private Long agreementParticipantIdBeneficiary;

	@Column(name = "AgreementBeneficiary")
	private String agreementBeneficiary;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "ParticipantNameContracting")
	private String participantNameContracting;

	@Column(name = "RemunerationModelCode")
	private String remunerationModelCode;

	@Column(name = "SdCode")
	private String sdCode;

	@Column(name = "SdName")
	private String sdName;

	@Column(name = "RoleOnAProjectId")
	private Long roleOnAProjectId;

	@Column(name = "ProjectSdId")
	private Long projectSdId;

	@Column(name = "StageName")
	private String stageName;

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Double getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Double numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public Double getRateForDate() {
		return rateForDate;
	}

	public void setRateForDate(Double rateForDate) {
		this.rateForDate = rateForDate;
	}

	public String getSystemNameThatBookedTime() {
		return systemNameThatBookedTime;
	}

	public void setSystemNameThatBookedTime(String systemNameThatBookedTime) {
		this.systemNameThatBookedTime = systemNameThatBookedTime;
	}

	public String getAgreementBetween() {
		return agreementBetween;
	}

	public void setAgreementBetween(String agreementBetween) {
		this.agreementBetween = agreementBetween;
	}

	public String getSdAndRole() {
		return sdAndRole;
	}

	public void setSdAndRole(String sdAndRole) {
		this.sdAndRole = sdAndRole;
	}

	public Long getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}

	public Long getProjectParticipantSdRoleId() {
		return projectParticipantSdRoleId;
	}

	public void setProjectParticipantSdRoleId(Long projectParticipantSdRoleId) {
		this.projectParticipantSdRoleId = projectParticipantSdRoleId;
	}

	public String getRemunerationTypeName() {
		return remunerationTypeName;
	}

	public void setRemunerationTypeName(String remunerationTypeName) {
		this.remunerationTypeName = remunerationTypeName;
	}

	public Long getProjectParticipantIdThatBookedTime() {
		return projectParticipantIdThatBookedTime;
	}

	public void setProjectParticipantIdThatBookedTime(Long projectParticipantIdThatBookedTime) {
		this.projectParticipantIdThatBookedTime = projectParticipantIdThatBookedTime;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getParticipantIdThatBookedTime() {
		return participantIdThatBookedTime;
	}

	public void setParticipantIdThatBookedTime(Long participantIdThatBookedTime) {
		this.participantIdThatBookedTime = participantIdThatBookedTime;
	}

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Long getAgreementParticipantIdPayer() {
		return agreementParticipantIdPayer;
	}

	public void setAgreementParticipantIdPayer(Long agreementParticipantIdPayer) {
		this.agreementParticipantIdPayer = agreementParticipantIdPayer;
	}

	public String getAgreementPayer() {
		return agreementPayer;
	}

	public void setAgreementPayer(String agreementPayer) {
		this.agreementPayer = agreementPayer;
	}

	public Long getAgreementParticipantIdBeneficiary() {
		return agreementParticipantIdBeneficiary;
	}

	public void setAgreementParticipantIdBeneficiary(Long agreementParticipantIdBeneficiary) {
		this.agreementParticipantIdBeneficiary = agreementParticipantIdBeneficiary;
	}

	public String getAgreementBeneficiary() {
		return agreementBeneficiary;
	}

	public void setAgreementBeneficiary(String agreementBeneficiary) {
		this.agreementBeneficiary = agreementBeneficiary;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getParticipantNameContracting() {
		return participantNameContracting;
	}

	public void setParticipantNameContracting(String participantNameContracting) {
		this.participantNameContracting = participantNameContracting;
	}

	public String getRemunerationModelCode() {
		return remunerationModelCode;
	}

	public void setRemunerationModelCode(String remunerationModelCode) {
		this.remunerationModelCode = remunerationModelCode;
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

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

}