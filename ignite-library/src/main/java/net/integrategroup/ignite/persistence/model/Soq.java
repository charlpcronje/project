package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema = "ig_db", name = "ScheduleOfQuantities")
public class Soq implements Serializable {

	private static final long serialVersionUID = 7631043141957949185L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ScheduleOfQuantitiesId")
	private Long scheduleOfQuantitiesId;
	
	@Column(name = "SoqTemplateIdUsed")
	private Long soqTemplateIdUsed;
	
	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectParticipantIdCreatedBy")
	private String projectParticipantIdCreatedBy;

	@Column(name = "SoqModeCode")
	private Long soqModeCode;

	@Column(name = "ScheduleName")
	private Long scheduleName;

	@Column(name = "Description")
	private Long description;

	@Column(name = "TenderNumber")
	private Long tenderNumber;

	@Column(name = "PrimaryHeading")
	private Long primaryHeading;

	@Column(name = "SecondaryHeading")
	private Long secondaryHeading;

	@Column(name = "TertiaryHeading")
	private Long tertiaryHeading;

	@Column(name = "StartDate")
	private Long startDate;
	
	@Column(name = "NumberColName")
	private Long numberColName;

	@Column(name = "DescriptionColName")
	private Long descriptionColName;

	@Column(name = "UnitColName")
	private Long unitColName;

	@Column(name = "RateColName")
	private Long rateColName;
	
	@Column(name = "QtyColName")
	private Long qtyColName;

	@Column(name = "AmountColName")
	private Long amountColName;

	@Column(name = "LastUpdateTimestamp")
	private Long lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private Long lastUpdateUserName;

	public Long getScheduleOfQuantitiesId() {
		return scheduleOfQuantitiesId;
	}

	public void setScheduleOfQuantitiesId(Long scheduleOfQuantitiesId) {
		this.scheduleOfQuantitiesId = scheduleOfQuantitiesId;
	}

	public Long getSoqTemplateIdUsed() {
		return soqTemplateIdUsed;
	}

	public void setSoqTemplateIdUsed(Long soqTemplateIdUsed) {
		this.soqTemplateIdUsed = soqTemplateIdUsed;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectParticipantIdCreatedBy() {
		return projectParticipantIdCreatedBy;
	}

	public void setProjectParticipantIdCreatedBy(String projectParticipantIdCreatedBy) {
		this.projectParticipantIdCreatedBy = projectParticipantIdCreatedBy;
	}

	public Long getSoqModeCode() {
		return soqModeCode;
	}

	public void setSoqModeCode(Long soqModeCode) {
		this.soqModeCode = soqModeCode;
	}

	public Long getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(Long scheduleName) {
		this.scheduleName = scheduleName;
	}

	public Long getDescription() {
		return description;
	}

	public void setDescription(Long description) {
		this.description = description;
	}

	public Long getTenderNumber() {
		return tenderNumber;
	}

	public void setTenderNumber(Long tenderNumber) {
		this.tenderNumber = tenderNumber;
	}

	public Long getPrimaryHeading() {
		return primaryHeading;
	}

	public void setPrimaryHeading(Long primaryHeading) {
		this.primaryHeading = primaryHeading;
	}

	public Long getSecondaryHeading() {
		return secondaryHeading;
	}

	public void setSecondaryHeading(Long secondaryHeading) {
		this.secondaryHeading = secondaryHeading;
	}

	public Long getTertiaryHeading() {
		return tertiaryHeading;
	}

	public void setTertiaryHeading(Long tertiaryHeading) {
		this.tertiaryHeading = tertiaryHeading;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getNumberColName() {
		return numberColName;
	}

	public void setNumberColName(Long numberColName) {
		this.numberColName = numberColName;
	}

	public Long getDescriptionColName() {
		return descriptionColName;
	}

	public void setDescriptionColName(Long descriptionColName) {
		this.descriptionColName = descriptionColName;
	}

	public Long getUnitColName() {
		return unitColName;
	}

	public void setUnitColName(Long unitColName) {
		this.unitColName = unitColName;
	}

	public Long getRateColName() {
		return rateColName;
	}

	public void setRateColName(Long rateColName) {
		this.rateColName = rateColName;
	}

	public Long getQtyColName() {
		return qtyColName;
	}

	public void setQtyColName(Long qtyColName) {
		this.qtyColName = qtyColName;
	}

	public Long getAmountColName() {
		return amountColName;
	}

	public void setAmountColName(Long amountColName) {
		this.amountColName = amountColName;
	}

	public Long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public Long getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(Long lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	

}