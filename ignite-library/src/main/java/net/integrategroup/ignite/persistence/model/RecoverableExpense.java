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

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "RecoverableExpense")
public class RecoverableExpense implements Serializable {

	private static final long serialVersionUID = 3095025099856273210L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

//	@OneToOne(targetEntity = AgreementBetweenParticipants.class)
//	@JoinColumn(name = "AgreementBetweenParticipantsId", referencedColumnName = "AgreementBetweenParticipantsId", nullable = true, insertable = false, updatable = false)
//	private AgreementBetweenParticipants agreementBetweenParticipants;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

//	@OneToOne(targetEntity = ExpenseType.class)
//	@JoinColumn(name = "ExpenseTypeId", referencedColumnName = "ExpenseTypeId", nullable = true, insertable = false, updatable = false)
//	private ExpenseType expenseType;

	@Column(name = "Description")
	private String description;

	@Column(name = "ExpenseBudget")
	private Double expenseBudget;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}
//
//	public AgreementBetweenParticipants getAgreementBetweenParticipants() {
//		return agreementBetweenParticipants;
//	}
//
//	public void setAgreementBetweenParticipants(AgreementBetweenParticipants agreementBetweenParticipants) {
//		this.agreementBetweenParticipants = agreementBetweenParticipants;
//	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

//	public ExpenseType getExpenseType() {
//		return expenseType;
//	}
//
//	public void setExpenseType(ExpenseType expenseType) {
//		this.expenseType = expenseType;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getExpenseBudget() {
		return expenseBudget;
	}

	public void setExpenseBudget(Double expenseBudget) {
		this.expenseBudget = expenseBudget;
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


}
