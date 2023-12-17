package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema = "ig_db", name = "vProjectBankExpenses")
public class VProjectBankExpenses implements Serializable {
	
	private static final long serialVersionUID = -5638887796015278109L;

	@Id
	@Column(name="ProjectExpenseId")
	private Long projectExpenseId;
	
	@Column(name="BankCardIdUsed")
	private Long bankCardIdUsed;

	@Column(name="PaymentDescription")
	private String paymentDescription;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PurchaseDate")
	private Date purchaseDate;

	@Column(name="NumberOfUnits")
	private Integer numberOfUnits;

	@Column(name="AmountPerUnit")
	private Double amountPerUnit;

	@Column(name="NoteToAccountant")
	private String noteToAccountant;

	@Column(name="BankCardId")
	private Long bankCardId;

	@Column(name="CardNumber")
	private String cardNumber;

	@Column(name="ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name="ExpenseTypeName")
	private String expenseTypeName;

	@Column(name="IndividualId")
	private Long individualId;

	@Column(name="ParticipantId")
	private Long participantId;

	@Column(name="ProjectTitle")
	private String projectTitle;

	@Column(name="UserName")
	private String userName;

	public Long getProjectExpenseId() {
		return projectExpenseId;
	}

	public void setProjectExpenseId(Long projectExpenseId) {
		this.projectExpenseId = projectExpenseId;
	}

	public Long getBankCardIdUsed() {
		return bankCardIdUsed;
	}

	public void setBankCardIdUsed(Long bankCardIdUsed) {
		this.bankCardIdUsed = bankCardIdUsed;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Integer getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Integer numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public Double getAmountPerUnit() {
		return amountPerUnit;
	}

	public void setAmountPerUnit(Double amountPerUnit) {
		this.amountPerUnit = amountPerUnit;
	}

	public String getNoteToAccountant() {
		return noteToAccountant;
	}

	public void setNoteToAccountant(String noteToAccountant) {
		this.noteToAccountant = noteToAccountant;
	}

	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
