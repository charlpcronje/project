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
@Table(schema = "ig_db", name = "vStatement")
public class VStatement implements Serializable {

	private static final long serialVersionUID = 7881773110574032234L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "TransactionType")
	private String transactionType;

	@Column(name = "InvoiceId")
	private Long invoiceId;

	@Column(name = "BankTransactionId")
	private Long bankTransactionId;

	@Column(name = "TheOtherParticipantId")
	private Long theOtherParticipantId;

	@Column(name = "TheParticipantId")
	private Long theParticipantId;

	@Column(name = "TheNumber")
	private String theNumber;

	@Column(name = "TheAmount")
	private Double theAmount;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "TheDate")
	private Date theDate;

	@Column(name = "TheDescription")
	private String theDescription;

	@Column(name = "TheOtherParticipant")
	private String theOtherParticipant;

	@Column(name = "TheParticipant")
	private String theParticipant;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getBankTransactionId() {
		return bankTransactionId;
	}

	public void setBankTransactionId(Long bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public Long getTheOtherParticipantId() {
		return theOtherParticipantId;
	}

	public void setTheOtherParticipantId(Long theOtherParticipantId) {
		this.theOtherParticipantId = theOtherParticipantId;
	}

	public Long getTheParticipantId() {
		return theParticipantId;
	}

	public void setTheParticipantId(Long theParticipantId) {
		this.theParticipantId = theParticipantId;
	}

	public String getTheNumber() {
		return theNumber;
	}

	public void setTheNumber(String theNumber) {
		this.theNumber = theNumber;
	}

	public Double getTheAmount() {
		return theAmount;
	}

	public void setTheAmount(Double theAmount) {
		this.theAmount = theAmount;
	}

	public Date getTheDate() {
		return theDate;
	}

	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	}

	public String getTheDescription() {
		return theDescription;
	}

	public void setTheDescription(String theDescription) {
		this.theDescription = theDescription;
	}

	public String getTheOtherParticipant() {
		return theOtherParticipant;
	}

	public void setTheOtherParticipant(String theOtherParticipant) {
		this.theOtherParticipant = theOtherParticipant;
	}

	public String getTheParticipant() {
		return theParticipant;
	}

	public void setTheParticipant(String theParticipant) {
		this.theParticipant = theParticipant;
	}
}