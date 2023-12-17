package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "vRecoverableExpense")
public class VRecoverableExpense implements Serializable {

	private static final long serialVersionUID = -4747435300809825433L;


	@Id
	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "ExpenseBudget")
	private Double expenseBudget;

	@Column(name = "ExpenseAgreementDescription")
	private String expenseAgreementDescription;

	@Column(name = "SystemNamePayer")
	private String systemNamePayer;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	@Column(name = "AgreementDescription")
	private String agreementDescription;

	@Column(name = "ExpenseTypeParentId")
	private Long expenseTypeParentId;

	@Column(name = "ExpenseTypeParentName")
	private String expenseTypeParentName;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "ExpenseTypeUnitCode")
	private String expenseTypeUnitCode;

	@Column(name = "ExpenseTypeUnitName")
	private String expenseTypeUnitName;

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

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public Double getExpenseBudget() {
		return expenseBudget;
	}

	public void setExpenseBudget(Double expenseBudget) {
		this.expenseBudget = expenseBudget;
	}

	public String getExpenseAgreementDescription() {
		return expenseAgreementDescription;
	}

	public void setExpenseAgreementDescription(String expenseAgreementDescription) {
		this.expenseAgreementDescription = expenseAgreementDescription;
	}

	public String getSystemNamePayer() {
		return systemNamePayer;
	}

	public void setSystemNamePayer(String systemNamePayer) {
		this.systemNamePayer = systemNamePayer;
	}

	public String getSystemNameBeneficiary() {
		return systemNameBeneficiary;
	}

	public void setSystemNameBeneficiary(String systemNameBeneficiary) {
		this.systemNameBeneficiary = systemNameBeneficiary;
	}

	public String getAgreementDescription() {
		return agreementDescription;
	}

	public void setAgreementDescription(String agreementDescription) {
		this.agreementDescription = agreementDescription;
	}

	public Long getExpenseTypeParentId() {
		return expenseTypeParentId;
	}

	public void setExpenseTypeParentId(Long expenseTypeParentId) {
		this.expenseTypeParentId = expenseTypeParentId;
	}

	public String getExpenseTypeParentName() {
		return expenseTypeParentName;
	}

	public void setExpenseTypeParentName(String expenseTypeParentName) {
		this.expenseTypeParentName = expenseTypeParentName;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getExpenseTypeUnitCode() {
		return expenseTypeUnitCode;
	}

	public void setExpenseTypeUnitCode(String expenseTypeUnitCode) {
		this.expenseTypeUnitCode = expenseTypeUnitCode;
	}

	public String getExpenseTypeUnitName() {
		return expenseTypeUnitName;
	}

	public void setExpenseTypeUnitName(String expenseTypeUnitName) {
		this.expenseTypeUnitName = expenseTypeUnitName;
	}

}



