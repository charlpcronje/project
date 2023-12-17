package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class ParticipantExpenseCostTotalsPerProjectDto {

	private Long projectId;
	private String projectName;
	private Long recoverableExpenseId;
	private Long expenseTypeId;
	private String expenseTypeName;
	private String unitTypeCode ;
	private String unitTypeName ;
	private Long participantIdContracting;
	private String participantInAgreementContracting;
	private Long participantIdContracted;
	private String participantInAgreementContracted;
//	private Double expenseRateForDate;
	private Double sumNrOfUnits;
	private Double lineAmount;
	private Long ratesMissing;

	public ParticipantExpenseCostTotalsPerProjectDto() {
		// nothing
	}

	public ParticipantExpenseCostTotalsPerProjectDto
			(
			Long projectId,
			String projectName,
			Long recoverableExpenseId,
			Long expenseTypeId,
			String expenseTypeName,
			String unitTypeCode ,
			String unitTypeName ,
			Long participantIdContracting,
			String participantInAgreementContracting,
			Long participantIdContracted,
			String participantInAgreementContracted,
//			Double expenseRateForDate,
			Double sumNrOfUnits,
			Double lineAmount,
			Long ratesMissing
			) {

		this();
		this.projectId = projectId;
		this.projectName = projectName;
		this.recoverableExpenseId = recoverableExpenseId;
		this.expenseTypeId = expenseTypeId;
		this.expenseTypeName = expenseTypeName;
		this.unitTypeCode = unitTypeCode;
		this.unitTypeName = unitTypeName;
		this.participantIdContracting = participantIdContracting;
		this.participantInAgreementContracting = participantInAgreementContracting;
		this.participantIdContracted = participantIdContracted;
		this.participantInAgreementContracted = participantInAgreementContracted;
//		this.expenseRateForDate = expenseRateForDate;
		this.sumNrOfUnits = sumNrOfUnits;
		this.lineAmount = lineAmount;
		this.ratesMissing = ratesMissing;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
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

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantInAgreementContracting() {
		return participantInAgreementContracting;
	}

	public void setParticipantInAgreementContracting(String participantInAgreementContracting) {
		this.participantInAgreementContracting = participantInAgreementContracting;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantInAgreementContracted() {
		return participantInAgreementContracted;
	}

	public void setParticipantInAgreementContracted(String participantInAgreementContracted) {
		this.participantInAgreementContracted = participantInAgreementContracted;
	}

//	public Double getExpenseRateForDate() {
//		return expenseRateForDate;
//	}
//
//	public void setExpenseRateForDate(Double expenseRateForDate) {
//		this.expenseRateForDate = expenseRateForDate;
//	}

	public Double getSumNrOfUnits() {
		return sumNrOfUnits;
	}

	public void setSumNrOfUnits(Double sumNrOfUnits) {
		this.sumNrOfUnits = sumNrOfUnits;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

	public Long getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Long ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

}
