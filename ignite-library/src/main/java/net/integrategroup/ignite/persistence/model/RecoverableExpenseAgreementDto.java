package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class RecoverableExpenseAgreementDto {

	private Long expenseTypeId;
	private String expenseTypeName;

	public RecoverableExpenseAgreementDto() {
		// nothing
	}

	public RecoverableExpenseAgreementDto
			(Long expenseTypeId, String expenseTypeName) {

		this();
		this.expenseTypeId = expenseTypeId;
		this.expenseTypeName = expenseTypeName;
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

}
