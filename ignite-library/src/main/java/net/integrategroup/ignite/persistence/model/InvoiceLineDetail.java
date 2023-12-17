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

/** ******* ********* ** 2023-09-04 14:34:56 ******** ** **/
/** ******* ********* ** Ingrid 2023-09-07 ******** ** **/

@Entity
@Table(schema = "ig_db", name = "InvoiceLineDetail")
public class InvoiceLineDetail implements Serializable {

	private static final long serialVersionUID = -8283301703354360795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "InvoiceLineDetailId")
	private Long invoiceLineDetailId;

	@Column(name = "InvoiceLineId")
	private Long invoiceLineId;

	@Column(name = "LineType")
	private String lineType;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActivityDate")
	private Date activityDate;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "StageName")
	private String stageName;

	@Column(name = "PartBookedTimeOrMadePurchase")
	private String partBookedTimeOrMadePurchase;

	@Column(name = "SdName")
	private String sdName;

	@Column(name = "SdAndRole")
	private String sdAndRole;

	@Column(name = "TheType")
	private String theType;

	@Column(name = "Description")
	private String description;

	@Column(name = "NumberOfUnits")
	private Double numberOfUnits;

	@Column(name = "RateForDate")
	private Double rateForDate;

	@Column(name = "LineTotal")
	private Double lineTotal;

	@Column(name = "RatesMissing")
	private Integer ratesMissing;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}

	public Long getInvoiceLineDetailId() {
		return invoiceLineDetailId;
	}

	public void setInvoiceLineDetailId(Long invoiceLineDetailId) {
		this.invoiceLineDetailId = invoiceLineDetailId;
	}

	public Long getInvoiceLineId() {
		return invoiceLineId;
	}

	public void setInvoiceLineId(Long invoiceLineId) {
		this.invoiceLineId = invoiceLineId;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getPartBookedTimeOrMadePurchase() {
		return partBookedTimeOrMadePurchase;
	}

	public void setPartBookedTimeOrMadePurchase(String partBookedTimeOrMadePurchase) {
		this.partBookedTimeOrMadePurchase = partBookedTimeOrMadePurchase;
	}

	public String getSdAndRole() {
		return sdAndRole;
	}

	public void setSdAndRole(String sdAndRole) {
		this.sdAndRole = sdAndRole;
	}

	public String getTheType() {
		return theType;
	}

	public void setTheType(String theType) {
		this.theType = theType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Integer getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Integer ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

}
