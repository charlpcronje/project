package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL v4.8) **/
/** ******* ********* ** 2023-06-18 16:06:24 ******** ***** **/

@Entity
@Table(schema = "ig_db", name = "vBankTransaction")
public class VBankTransaction implements Serializable {

	private static final long serialVersionUID = 5287205742419880568L;

	@Id
	@Column(name = "BankTransactionId")
	private Long bankTransactionId;

	@Column(name = "ParticipantBankDetailsId")
	private Long participantBankDetailsId;

	@Column(name = "BankStatementId")
	private Long bankStatementId;

		@Column(name = "Description1")
	private String description1;

	@Column(name = "Description2")
	private String description2;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "TransactionDate")
	private Date transactionDate;

	@Column(name = "Amount")
	private Double amount;

	@Column(name = "ParticipantIdOwner")
	private Long participantIdOwner;

	@Column(name = "OwnerSystemName")
	private String ownerSystemName;

	@Column(name = "ParticipantIdOnTransaction")
	private Long participantIdOnTransaction;

	@Column(name = "LinkedParticipant")
	private String linkedParticipant;

	@Column(name = "StatementNumber")
	private String statementNumber;

	@Column(name = "FullyLinked")
	private String fullyLinked;

	public Long getBankTransactionId() {
		return bankTransactionId;
	}

	public void setBankTransactionId(Long bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public Long getParticipantBankDetailsId() {
		return participantBankDetailsId;
	}

	public void setParticipantBankDetailsId(Long participantBankDetailsId) {
		this.participantBankDetailsId = participantBankDetailsId;
	}

	public Long getBankStatementId() {
		return bankStatementId;
	}

	public void setBankStatementId(Long bankStatementId) {
		this.bankStatementId = bankStatementId;
	}

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getParticipantIdOwner() {
		return participantIdOwner;
	}

	public void setParticipantIdOwner(Long participantIdOwner) {
		this.participantIdOwner = participantIdOwner;
	}

	public String getOwnerSystemName() {
		return ownerSystemName;
	}

	public void setOwnerSystemName(String ownerSystemName) {
		this.ownerSystemName = ownerSystemName;
	}

	public Long getParticipantIdOnTransaction() {
		return participantIdOnTransaction;
	}

	public void setParticipantIdOnTransaction(Long participantIdOnTransaction) {
		this.participantIdOnTransaction = participantIdOnTransaction;
	}

	public String getLinkedParticipant() {
		return linkedParticipant;
	}

	public void setLinkedParticipant(String linkedParticipant) {
		this.linkedParticipant = linkedParticipant;
	}

	public String getStatementNumber() {
		return statementNumber;
	}

	public void setStatementNumber(String statementNumber) {
		this.statementNumber = statementNumber;
	}

	public String getFullyLinked() {
		return fullyLinked;
	}

	public void setFullyLinked(String fullyLinked) {
		this.fullyLinked = fullyLinked;
	}

}

