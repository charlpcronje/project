package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class InvoiceLineTimeCostTotalsPerProjectDto {

	private Long projectId;
	private String sdName;
	private String theType;
	private Double sumNrOfUnits;
	private Double lineTotal;
	private Long ratesMissing;

	public InvoiceLineTimeCostTotalsPerProjectDto() {
		// nothing
	}

	public InvoiceLineTimeCostTotalsPerProjectDto
			(
				Long projectId,
				String sdName,
				String theType,
				Double sumNrOfUnits,
				Double lineTotal,
				Long ratesMissing) {

		this();
		this.projectId = projectId;
		this.sdName = sdName;
		this.theType = theType;
		this.sumNrOfUnits = sumNrOfUnits;
		this.lineTotal = lineTotal;
		this.ratesMissing = ratesMissing;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}

	public String getTheType() {
		return theType;
	}

	public void setTheType(String theType) {
		this.theType = theType;
	}

	public Double getSumNrOfUnits() {
		return sumNrOfUnits;
	}

	public void setSumNrOfUnits(Double sumNrOfUnits) {
		this.sumNrOfUnits = sumNrOfUnits;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Long getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Long ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

}
