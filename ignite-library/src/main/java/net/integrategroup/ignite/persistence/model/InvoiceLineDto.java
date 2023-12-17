package net.integrategroup.ignite.persistence.model;

import java.util.Date;

// @author Ingrid Marais

public class InvoiceLineDto {

	private Long invoiceId;
	private Long participantIdFrom;
	private Long participantIdTo;
	private String invoiceNumber;
	private Double invoiceAmount;
	private Date invoiceDate ;
	private Long projectId;
	private String projectNameText ;
	private String lineType;
	private Double totalUnits;
	private Double lineAmount;

	public InvoiceLineDto() {
		// nothing
	}

	public InvoiceLineDto
			(
				Long invoiceId,
				Long participantIdFrom,
				Long participantIdTo,
				String invoiceNumber,
				Double invoiceAmount,
				Date invoiceDate,
				Long projectId,
				String projectNameText,
				String lineType,
				Double totalUnits,
				Double lineAmount
			) {

		this();

		this.invoiceId = invoiceId;
		this.participantIdFrom = participantIdFrom;
		this.participantIdTo = participantIdTo;
		this.invoiceNumber = invoiceNumber;
		this.invoiceAmount = invoiceAmount;
		this.invoiceDate = invoiceDate;
		this.projectId = projectId;
		this.projectNameText = projectNameText;
		this.lineType = lineType;
		this.totalUnits = totalUnits;
		this.lineAmount = lineAmount;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getParticipantIdFrom() {
		return participantIdFrom;
	}

	public void setParticipantIdFrom(Long participantIdFrom) {
		this.participantIdFrom = participantIdFrom;
	}

	public Long getParticipantIdTo() {
		return participantIdTo;
	}

	public void setParticipantIdTo(Long participantIdTo) {
		this.participantIdTo = participantIdTo;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectNameText() {
		return projectNameText;
	}

	public void setProjectNameText(String projectNameText) {
		this.projectNameText = projectNameText;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public Double getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Double totalUnits) {
		this.totalUnits = totalUnits;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

}
