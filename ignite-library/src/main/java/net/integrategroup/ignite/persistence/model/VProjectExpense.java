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
@Table(schema = "ig_db", name = "vProjectExpense")
public class VProjectExpense implements Serializable {

	private static final long serialVersionUID = -6655714654018969889L;

	@Id
	@Column(name = "ProjectExpenseId")
	private Long projectExpenseId;

	@Column(name = "ProjectParticipantIdPayer")
	private Long projectParticipantIdPayer;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "ParticipantPayerSystemName")
	private String participantPayerSystemName;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "SubProjNumber")
	private String subProjNumber;

	@Column(name = "ParticipantIdMadePurchase")
	private Long participantIdMadePurchase;

	@Column(name = "ParticipantMadePurchaseSystemName")
	private String participantMadePurchaseSystemName;

	@Column(name = "ParticipantIdVendor")
	private Long participantIdVendor;

	@Column(name = "ParticipantVendorSystemName")
	private String participantVendorSystemName;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "ExpenseTypeDescription")
	private String expenseTypeDescription;

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

	@Column(name = "UnitTypeName")
	private String unitTypeName;

	@Column(name = "ExpenseTypeParentId")
	private Long expenseTypeParentId;

	@Column(name = "ExpenseTypeParentName")
	private String expenseTypeParentName;

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

	@Column(name = "PaymentDescription")
	private String paymentDescription;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PurchaseDate")
	private Date purchaseDate;

	@Column(name = "NumberOfUnits")
	private Double numberOfUnits;

	@Column(name = "AmountPerUnit")
	private Double amountPerUnit;



	@Column(name = "NoteToAccountant")
	private String noteToAccountant;

	@Column(name = "FullyLinked")
	private String fullyLinked;

	@Column(name = "BankReference")
	private String bankReference;

	@Column(name = "AllowanceFlag")
	private String allowanceFlag;

	@Column(name = "LineTotal")
	private Double lineTotal;	
	
	
	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getAllowanceFlag() {
		return allowanceFlag;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public void setAllowanceFlag(String allowanceFlag) {
		this.allowanceFlag = allowanceFlag;
	}

	public String getSubProjNumber() {
		return subProjNumber;
	}

	public void setSubProjNumber(String subProjNumber) {
		this.subProjNumber = subProjNumber;
	}

	public Long getProjectExpenseId() {
		return projectExpenseId;
	}

	public void setProjectExpenseId(Long projectExpenseId) {
		this.projectExpenseId = projectExpenseId;
	}

	public Long getProjectParticipantIdPayer() {
		return projectParticipantIdPayer;
	}

	public void setProjectParticipantIdPayer(Long projectParticipantIdPayer) {
		this.projectParticipantIdPayer = projectParticipantIdPayer;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getParticipantPayerSystemName() {
		return participantPayerSystemName;
	}

	public void setParticipantPayerSystemName(String participantPayerSystemName) {
		this.participantPayerSystemName = participantPayerSystemName;
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

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getExpenseTypeDescription() {
		return expenseTypeDescription;
	}

	public void setExpenseTypeDescription(String expenseTypeDescription) {
		this.expenseTypeDescription = expenseTypeDescription;
	}

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
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



	public String getNoteToAccountant() {
		return noteToAccountant;
	}

	public void setNoteToAccountant(String noteToAccountant) {
		this.noteToAccountant = noteToAccountant;
	}

	public String getFullyLinked() {
		return fullyLinked;
	}

	public void setFullyLinked(String fullyLinked) {
		this.fullyLinked = fullyLinked;
	}

	public String getBankReference() {
		return bankReference;
	}

	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}


}

/**  javascript
		{ data: "projectExpenseId" },         //0 MySql-TableName: VProjectExpense
		{ data: "projectParticipantIdPayer" }, //1
		{ data: "participantIdPayer" },       //2
		{ data: "participantPayerSystemName" }, //3
		{ data: "projectId" },                //4
		{ data: "projectName" },              //5
		{ data: "participantIdMadePurchase" }, //6
		{ data: "participantMadePurchaseSystemName" }, //7
		{ data: "participantIdVendor" },      //8
		{ data: "participantVendorSystemName" }, //9
		{ data: "expenseTypeId" },            //10
		{ data: "expenseTypeId" },          //11
		{ data: "expenseTypeName" },          //12
		{ data: "expenseTypeDescription" },   //13
		{ data: "unitTypeCode" },             //14
		{ data: "expenseTypeParentId" },    //15
		{ data: "expenseTypeParentName" },    //16
		{ data: "assetId" },                  //17
		{ data: "vehicleId" },                //18
		{ data: "vehicleName" },              //19
		{ data: "assetDescription" },         //20
		{ data: "paymentMethodCode" },        //21
		{ data: "paymentMethodName" },        //22
		{ data: "bankCardIdUsed" },           //23
		{ data: "bankCardNumber" },           //24
		{ data: "bankCardNameOnCard" },       //25
		{ data: "bankCardDescription" },      //26
		{ data: "participantBankDetailsIdUsed" }, //27
		{ data: "accountNumber" },            //28
		{ data: "participantBankDetailsName" }, //29
		{ data: "participantBankDetailsDescription" }, //30
		{ data: "taxDeductableCategoryId" }, //31
		{ data: "taxDeductableCategoryName" }, //32
		{ data: "paymentDescription" },       //33
		{ data: "purchaseDate" },             //34
		{ data: "numberOfUnits" },            //35
		{ data: "amountPerUnit" },            //36
		
		{ data: "noteToAccountant" },         //37
		{ data: "fullyLinked" },              //38
		{ data: "bankReference" },            //39

/**  html
										<th>ProjectExpenseId</th>         <!--0  MySql-TableName: VProjectExpense-->
										<th>ProjectParticipantIdPayer</th> <!--1  ProjectParticipantIdPayer-->
										<th>ParticipantIdPayer</th>       <!--2  ParticipantIdPayer-->
										<th>ParticipantPayerSystemName</th> <!--3  ParticipantPayerSystemName-->
										<th>ProjectId</th>                <!--4  ProjectId-->
										<th>ProjectName</th>              <!--5  ProjectName-->
										<th>ParticipantIdMadePurchase</th> <!--6  ParticipantIdMadePurchase-->
										<th>ParticipantMadePurchaseSystemName</th> <!--7  ParticipantMadePurchaseSystemName-->
										<th>ParticipantIdVendor</th>      <!--8  ParticipantIdVendor-->
										<th>ParticipantVendorSystemName</th> <!--9  ParticipantVendorSystemName-->
										<th>ExpenseTypeId</th>            <!--10  ExpenseTypeId-->
										<th>ExpenseTypeId</th>          <!--11  ExpenseTypeId-->
										<th>ExpenseTypeName</th>          <!--12  ExpenseTypeName-->
										<th>ExpenseTypeDescription</th>   <!--13  ExpenseTypeDescription-->
										<th>UnitTypeCode</th>             <!--14  UnitTypeCode-->
										<th>ExpenseTypeParentId</th>    <!--15  ExpenseTypeParentId-->
										<th>ExpenseTypeParentName</th>    <!--16  ExpenseTypeParentName-->
										<th>AssetId</th>                  <!--17  AssetId-->
										<th>VehicleId</th>                <!--18  VehicleId-->
										<th>VehicleName</th>              <!--19  VehicleName-->
										<th>AssetDescription</th>         <!--20  AssetDescription-->
										<th>PaymentMethodCode</th>        <!--21  PaymentMethodCode-->
										<th>PaymentMethodName</th>        <!--22  PaymentMethodName-->
										<th>BankCardIdUsed</th>           <!--23  BankCardIdUsed-->
										<th>BankCardNumber</th>           <!--24  BankCardNumber-->
										<th>BankCardNameOnCard</th>       <!--25  BankCardNameOnCard-->
										<th>BankCardDescription</th>      <!--26  BankCardDescription-->
										<th>ParticipantBankDetailsIdUsed</th> <!--27  ParticipantBankDetailsIdUsed-->
										<th>AccountNumber</th>            <!--28  AccountNumber-->
										<th>ParticipantBankDetailsName</th> <!--29  ParticipantBankDetailsName-->
										<th>ParticipantBankDetailsDescription</th> <!--30  ParticipantBankDetailsDescription-->
										<th>TaxDeductableCategoryId</th> <!--31  TaxDeductableCategoryId-->
										<th>TaxDeductableCategoryName</th> <!--32  TaxDeductableCategoryName-->
										<th>PaymentDescription</th>       <!--33  PaymentDescription-->
										<th>PurchaseDate</th>             <!--34  PurchaseDate-->
										<th>NumberOfUnits</th>            <!--35  NumberOfUnits-->
										<th>AmountPerUnit</th>            <!--36  AmountPerUnit-->
										
										<th>NoteToAccountant</th>         <!--37  NoteToAccountant-->
										<th>FullyLinked</th>              <!--38  FullyLinked-->
										<th>BankReference</th>            <!--39  BankReference-->

*/