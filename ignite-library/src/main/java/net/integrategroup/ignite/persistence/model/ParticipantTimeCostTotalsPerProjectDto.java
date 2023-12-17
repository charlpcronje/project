package net.integrategroup.ignite.persistence.model;

// @author Ingrid Marais

public class ParticipantTimeCostTotalsPerProjectDto {

	private Long projectId;
	private String projectName;
	private String sdName;
	private String unitTypeName ;
	private Long agreementBetweenParticipantsId;
	private String agreementBetween;
	private Long agreementParticipantIdPayer;
	private String agreementPayer;
	private Long agreementParticipantIdBeneficiary;
	private String agreementBeneficiary;
	private Long projectSdId;
	private String remunerationTypeName;
	private Double sumNrOfUnits;
	private Double lineAmount;
	private Long ratesMissing;

	public ParticipantTimeCostTotalsPerProjectDto() {
		// nothing
	}

	public ParticipantTimeCostTotalsPerProjectDto
			(

			Long projectId,
			String projectName,
			String sdName,
			String unitTypeName,
			Long agreementBetweenParticipantsId,
			String agreementBetween,
			Long agreementParticipantIdPayer,
			String agreementPayer,
			Long agreementParticipantIdBeneficiary,
			String agreementBeneficiary,
			Long projectSdId,
			String remunerationTypeName,
			Double sumNrOfUnits,
			Double lineAmount,
			Long ratesMissing) {


		this();
		this.projectId = projectId;
		this.projectName = projectName;
		this.sdName = sdName;
		this.unitTypeName = unitTypeName;
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
		this.agreementBetween = agreementBetween;
		this.agreementParticipantIdPayer = agreementParticipantIdPayer;
		this.agreementPayer = agreementPayer;
		this.agreementParticipantIdBeneficiary = agreementParticipantIdBeneficiary;
		this.agreementBeneficiary = agreementBeneficiary;
		this.projectSdId = projectSdId;
		this.remunerationTypeName = remunerationTypeName;
		this.sumNrOfUnits = sumNrOfUnits;
		this.lineAmount = lineAmount;
		this.ratesMissing = ratesMissing;
	}

	public Long getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Long ratesMissing) {
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

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public String getAgreementBetween() {
		return agreementBetween;
	}

	public void setAgreementBetween(String agreementBetween) {
		this.agreementBetween = agreementBetween;
	}

	public Long getAgreementParticipantIdPayer() {
		return agreementParticipantIdPayer;
	}

	public void setAgreementParticipantIdPayer(Long agreementParticipantIdPayer) {
		this.agreementParticipantIdPayer = agreementParticipantIdPayer;
	}

	public String getAgreementPayer() {
		return agreementPayer;
	}

	public void setAgreementPayer(String agreementPayer) {
		this.agreementPayer = agreementPayer;
	}

	public Long getAgreementParticipantIdBeneficiary() {
		return agreementParticipantIdBeneficiary;
	}

	public void setAgreementParticipantIdBeneficiary(Long agreementParticipantIdBeneficiary) {
		this.agreementParticipantIdBeneficiary = agreementParticipantIdBeneficiary;
	}

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public String getRemunerationTypeName() {
		return remunerationTypeName;
	}

	public void setRemunerationTypeName(String remunerationTypeName) {
		this.remunerationTypeName = remunerationTypeName;
	}

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

	public String getAgreementBeneficiary() {
		return agreementBeneficiary;
	}

	public void setAgreementBeneficiary(String agreementBeneficiary) {
		this.agreementBeneficiary = agreementBeneficiary;
	}

}
