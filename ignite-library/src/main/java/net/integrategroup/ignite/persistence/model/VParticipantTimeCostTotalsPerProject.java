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
@Table(schema = "ig_db", name = "vParticipantTimeCostTotalsPerProject")
public class VParticipantTimeCostTotalsPerProject implements Serializable {

	private static final long serialVersionUID = -1866053570519612742L;

	@Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "SdName")
	private String sdName;

	@Column(name = "UnitTypeName")
	private String unitTypeName;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "AgreementBetween")
	private String agreementBetween;

	@Column(name = "AgreementParticipantIdPayer")
	private Long agreementParticipantIdPayer;

	@Column(name = "AgreementPayer")
	private String agreementPayer;

	@Column(name = "AgreementParticipantIdBeneficiary")
	private Long agreementParticipantIdBeneficiary;

	@Column(name = "AgreementBeneficiary")
	private String agreementBeneficiary;

	@Column(name = "ProjectSdId")
	private Long projectSdId;

	@Column(name = "RemunerationTypeName")
	private String remunerationTypeName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActivityDate")
	private Date activityDate;

	@Column(name = "SumNrOfUnits")
	private Double sumNrOfUnits;

	@Column(name = "LineAmount")
	private Double lineAmount;

	@Column(name = "RatesMissing")
	private Integer ratesMissing;

	public Integer getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Integer ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public String getAgreementBetween() {
		return agreementBetween;
	}

	public void setAgreementBetween(String agreementBetween) {
		this.agreementBetween = agreementBetween;
	}

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public String getRemunerationTypeName() {
		return remunerationTypeName;
	}

	public void setRemunerationTypeName(String remunerationTypeName) {
		this.remunerationTypeName = remunerationTypeName;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
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

	public Double getSumNrOfUnits() {
		return sumNrOfUnits;
	}

	public void setSumNrOfUnits(Double sumNrOfUnits) {
		this.sumNrOfUnits = sumNrOfUnits;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

}