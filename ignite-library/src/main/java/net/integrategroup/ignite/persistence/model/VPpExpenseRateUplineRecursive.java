package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL v5.9) **/
/** ******* ********* ** 2023-08-10 15:15:15 ******** ***** **/

@Entity
@Table(schema = "ig_db", name = "vPpExpenseRateUplineRecursive")
public class VPpExpenseRateUplineRecursive implements Serializable {


    private static final long serialVersionUID = -1591810527606145169L; /** ID was NOT Generated by Johannes **/


    @Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "ProjectExpenseId")
	private Long projectExpenseId;

	@Column(name = "ProjectParticipantIdMadeOrigPayment")
	private Long projectParticipantIdMadeOrigPayment;

	@Column(name = "ParticipantIdMadeOrigPayment")
	private Long participantIdMadeOrigPayment;

	@Column(name = "ParticipantMadeOrigPayment")
	private String participantMadeOrigPayment;

	@Column(name = "ParticipantInAgreementContracting")
	private String participantInAgreementContracting;

	@Column(name = "ParticipantInAgreementContracted")
	private String participantInAgreementContracted;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "ExpenseRateForDate")
	private Double expenseRateForDate;

	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "ExpenseHandlingPerc")
	private Double expenseHandlingPerc;

	@Column(name = "MaxExpenseAmtPerUnit")
	private Double maxExpenseAmtPerUnit;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "PaymentDescription")
	private String paymentDescription;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PurchaseDate")
	private Date purchaseDate;

	@Column(name = "NumberOfUnits")
	private Double numberOfUnits;

	@Column(name = "AmountPerUnit")
	private Double amountPerUnit;

	@Column(name = "ParticipantIdMadePurchase")
	private Long participantIdMadePurchase;

	@Column(name = "ParticipantMadePurchaseSystemName")
	private String participantMadePurchaseSystemName;

	@Column(name = "ExpenseTypeParentId")
	private Long expenseTypeParentId;

	@Column(name = "ExpenseTypeParentName")
	private String expenseTypeParentName;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

	@Column(name = "UnitTypeName")
	private String unitTypeName;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "ProjectParticipantIdAboveMe")
	private Long projectParticipantIdAboveMe;

	@Column(name = "ProjectParticipantIdContracting")
	private Long projectParticipantIdContracting;

	@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ProjectParticipantIdContracted")
	private Long projectParticipantIdContracted;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "LineTotal")
	private Double lineTotal;

	@Column(name = "ParticipantIdVendor")
	private Long participantIdVendor;

	@Column(name = "ParticipantVendorSystemName")
	private String participantVendorSystemName;

	@Column(name = "AssetId")
	private Long assetId;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "VehicleName")
	private String vehicleName;

	@Column(name = "AssetDescription")
	private String assetDescription;

	@Column(name = "PaymentMethodCode")
	private String paymentMethodCode;

	@Column(name = "PaymentMethodName")
	private String paymentMethodName;

	@Column(name = "BankCardIdUsed")
	private Long bankCardIdUsed;

	@Column(name = "BankCardNumber")
	private String bankCardNumber;

	@Column(name = "BankCardNameOnCard")
	private String bankCardNameOnCard;

	@Column(name = "BankCardDescription")
	private String bankCardDescription;

	@Column(name = "ParticipantBankDetailsIdUsed")
	private Long participantBankDetailsIdUsed;

	@Column(name = "AccountNumber")
	private String accountNumber;

	@Column(name = "ParticipantBankDetailsName")
	private String participantBankDetailsName;

	@Column(name = "ParticipantBankDetailsDescription")
	private String participantBankDetailsDescription;

	@Column(name = "TaxDeductableCategoryId")
	private Long taxDeductableCategoryId;

	@Column(name = "TaxDeductableCategoryName")
	private String taxDeductableCategoryName;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getProjectExpenseId() {
		return projectExpenseId;
	}

	public void setProjectExpenseId(Long projectExpenseId) {
		this.projectExpenseId = projectExpenseId;
	}

	public Long getProjectParticipantIdMadeOrigPayment() {
		return projectParticipantIdMadeOrigPayment;
	}

	public void setProjectParticipantIdMadeOrigPayment(Long projectParticipantIdMadeOrigPayment) {
		this.projectParticipantIdMadeOrigPayment = projectParticipantIdMadeOrigPayment;
	}

	public Long getParticipantIdMadeOrigPayment() {
		return participantIdMadeOrigPayment;
	}

	public void setParticipantIdMadeOrigPayment(Long participantIdMadeOrigPayment) {
		this.participantIdMadeOrigPayment = participantIdMadeOrigPayment;
	}

	public String getParticipantMadeOrigPayment() {
		return participantMadeOrigPayment;
	}

	public void setParticipantMadeOrigPayment(String participantMadeOrigPayment) {
		this.participantMadeOrigPayment = participantMadeOrigPayment;
	}

	public String getParticipantInAgreementContracting() {
		return participantInAgreementContracting;
	}

	public void setParticipantInAgreementContracting(String participantInAgreementContracting) {
		this.participantInAgreementContracting = participantInAgreementContracting;
	}

	public String getParticipantInAgreementContracted() {
		return participantInAgreementContracted;
	}

	public void setParticipantInAgreementContracted(String participantInAgreementContracted) {
		this.participantInAgreementContracted = participantInAgreementContracted;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public Double getExpenseRateForDate() {
		return expenseRateForDate;
	}

	public void setExpenseRateForDate(Double expenseRateForDate) {
		this.expenseRateForDate = expenseRateForDate;
	}

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
	}

	public Double getExpenseHandlingPerc() {
		return expenseHandlingPerc;
	}

	public void setExpenseHandlingPerc(Double expenseHandlingPerc) {
		this.expenseHandlingPerc = expenseHandlingPerc;
	}

	public Double getMaxExpenseAmtPerUnit() {
		return maxExpenseAmtPerUnit;
	}

	public void setMaxExpenseAmtPerUnit(Double maxExpenseAmtPerUnit) {
		this.maxExpenseAmtPerUnit = maxExpenseAmtPerUnit;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
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

	public Double getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Double numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public Double getAmountPerUnit() {
		return amountPerUnit;
	}

	public void setAmountPerUnit(Double amountPerUnit) {
		this.amountPerUnit = amountPerUnit;
	}

	public Long getParticipantIdMadePurchase() {
		return participantIdMadePurchase;
	}

	public void setParticipantIdMadePurchase(Long participantIdMadePurchase) {
		this.participantIdMadePurchase = participantIdMadePurchase;
	}

	public String getParticipantMadePurchaseSystemName() {
		return participantMadePurchaseSystemName;
	}

	public void setParticipantMadePurchaseSystemName(String participantMadePurchaseSystemName) {
		this.participantMadePurchaseSystemName = participantMadePurchaseSystemName;
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

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Long getProjectParticipantIdAboveMe() {
		return projectParticipantIdAboveMe;
	}

	public void setProjectParticipantIdAboveMe(Long projectParticipantIdAboveMe) {
		this.projectParticipantIdAboveMe = projectParticipantIdAboveMe;
	}

	public Long getProjectParticipantIdContracting() {
		return projectParticipantIdContracting;
	}

	public void setProjectParticipantIdContracting(Long projectParticipantIdContracting) {
		this.projectParticipantIdContracting = projectParticipantIdContracting;
	}

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public Long getProjectParticipantIdContracted() {
		return projectParticipantIdContracted;
	}

	public void setProjectParticipantIdContracted(Long projectParticipantIdContracted) {
		this.projectParticipantIdContracted = projectParticipantIdContracted;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Long getParticipantIdVendor() {
		return participantIdVendor;
	}

	public void setParticipantIdVendor(Long participantIdVendor) {
		this.participantIdVendor = participantIdVendor;
	}

	public String getParticipantVendorSystemName() {
		return participantVendorSystemName;
	}

	public void setParticipantVendorSystemName(String participantVendorSystemName) {
		this.participantVendorSystemName = participantVendorSystemName;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public Long getBankCardIdUsed() {
		return bankCardIdUsed;
	}

	public void setBankCardIdUsed(Long bankCardIdUsed) {
		this.bankCardIdUsed = bankCardIdUsed;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public String getBankCardNameOnCard() {
		return bankCardNameOnCard;
	}

	public void setBankCardNameOnCard(String bankCardNameOnCard) {
		this.bankCardNameOnCard = bankCardNameOnCard;
	}

	public String getBankCardDescription() {
		return bankCardDescription;
	}

	public void setBankCardDescription(String bankCardDescription) {
		this.bankCardDescription = bankCardDescription;
	}

	public Long getParticipantBankDetailsIdUsed() {
		return participantBankDetailsIdUsed;
	}

	public void setParticipantBankDetailsIdUsed(Long participantBankDetailsIdUsed) {
		this.participantBankDetailsIdUsed = participantBankDetailsIdUsed;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getParticipantBankDetailsName() {
		return participantBankDetailsName;
	}

	public void setParticipantBankDetailsName(String participantBankDetailsName) {
		this.participantBankDetailsName = participantBankDetailsName;
	}

	public String getParticipantBankDetailsDescription() {
		return participantBankDetailsDescription;
	}

	public void setParticipantBankDetailsDescription(String participantBankDetailsDescription) {
		this.participantBankDetailsDescription = participantBankDetailsDescription;
	}

	public Long getTaxDeductableCategoryId() {
		return taxDeductableCategoryId;
	}

	public void setTaxDeductableCategoryId(Long taxDeductableCategoryId) {
		this.taxDeductableCategoryId = taxDeductableCategoryId;
	}

	public String getTaxDeductableCategoryName() {
		return taxDeductableCategoryName;
	}

	public void setTaxDeductableCategoryName(String taxDeductableCategoryName) {
		this.taxDeductableCategoryName = taxDeductableCategoryName;
	}



}