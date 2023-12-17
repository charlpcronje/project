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
@Table(schema = "ig_db", name = "SoqTemplate")
public class SoqTemplate implements Serializable {
	
	private static final long serialVersionUID = -2378686168046808013L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SoqTemplateId")
	private Long soqTemplateId;
	
	@Column(name = "ScheduleName")
	private String scheduleName;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "PrimaryHeading")
	private String primaryHeading;
	
	@Column(name = "SecondaryHeading")
	private String secondaryHeading;
	
	@Column(name = "TertiaryHeading")
	private String tertiaryHeading;
	
	@Column(name = "NumberColName")
	private String numberColName;
	
	@Column(name = "DescriptionColName")
	private String descriptionColName;
	
	@Column(name = "UnitColName")
	private String unitColName;
	
	@Column(name = "RateColName")
	private String rateColName;
	
	@Column(name = "QtyColName")
	private String qtyColName;
	
	@Column(name = "AmountColName")
	private String amountColName;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;
	
	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getSoqTemplateId() {
		return soqTemplateId;
	}

	public void setSoqTemplateId(Long soqTemplateId) {
		this.soqTemplateId = soqTemplateId;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrimaryHeading() {
		return primaryHeading;
	}

	public void setPrimaryHeading(String primaryHeading) {
		this.primaryHeading = primaryHeading;
	}

	public String getSecondaryHeading() {
		return secondaryHeading;
	}

	public void setSecondaryHeading(String secondaryHeading) {
		this.secondaryHeading = secondaryHeading;
	}

	public String getTertiaryHeading() {
		return tertiaryHeading;
	}

	public void setTertiaryHeading(String tertiaryHeading) {
		this.tertiaryHeading = tertiaryHeading;
	}

	public String getNumberColName() {
		return numberColName;
	}

	public void setNumberColName(String numberColName) {
		this.numberColName = numberColName;
	}

	public String getDescriptionColName() {
		return descriptionColName;
	}

	public void setDescriptionColName(String descriptionColName) {
		this.descriptionColName = descriptionColName;
	}

	public String getUnitColName() {
		return unitColName;
	}

	public void setUnitColName(String unitColName) {
		this.unitColName = unitColName;
	}

	public String getRateColName() {
		return rateColName;
	}

	public void setRateColName(String rateColName) {
		this.rateColName = rateColName;
	}

	public String getQtyColName() {
		return qtyColName;
	}

	public void setQtyColName(String qtyColName) {
		this.qtyColName = qtyColName;
	}

	public String getAmountColName() {
		return amountColName;
	}

	public void setAmountColName(String amountColName) {
		this.amountColName = amountColName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
