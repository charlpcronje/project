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

/** @author Generated by Johannes Marais (JohannesSQL v4.8) **/
/** ******* ********* ** 2023-06-18 19:46:16 ******** ***** **/

@Entity
@Table(schema = "ig_db", name = "ProjectExpense")
public class ProjectExpense implements Serializable {


    /**
	 *
	 */
	private static final long serialVersionUID = -784537328262402531L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectExpenseId")
	private Long projectExpenseId;

	@Column(name = "ProjectParticipantIdPayer")
	private Long projectParticipantIdPayer;

/***	@OneToOne(targetEntity = ProjectParticipant.class)
	@JoinColumn(name = "ProjectParticipantIdPayer", referencedColumnName = "ProjectParticipantId", nullable = true, insertable = false, updatable = false)
	private ProjectParticipant projectParticipant; ***/

	@Column(name = "ParticipantIdMadePurchase")
	private Long participantIdMadePurchase;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantIdMadePurchase", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participant; ***/

	@Column(name = "ParticipantIdVendor")
	private Long participantIdVendor;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantIdVendor", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participant; ***/

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

/***	@OneToOne(targetEntity = ExpenseType.class)
	@JoinColumn(name = "ExpenseTypeId", referencedColumnName = "ExpenseTypeId", nullable = true, insertable = false, updatable = false)
	private ExpenseType expenseType; ***/

	@Column(name = "AssetId")
	private Long assetId;

/***	@OneToOne(targetEntity = Asset.class)
	@JoinColumn(name = "AssetId", referencedColumnName = "AssetId", nullable = true, insertable = false, updatable = false)
	private Asset asset; ***/

	@Column(name = "PaymentMethodCode")
	private String paymentMethodCode;

/***	@OneToOne(targetEntity = PaymentMethod.class)
	@JoinColumn(name = "PaymentMethodCode", referencedColumnName = "PaymentMethodCode", nullable = true, insertable = false, updatable = false)
	private PaymentMethod paymentMethod; ***/

	@Column(name = "BankCardIdUsed")
	private Long bankCardIdUsed;

/***	@OneToOne(targetEntity = BankCard.class)
	@JoinColumn(name = "BankCardIdUsed", referencedColumnName = "BankCardId", nullable = true, insertable = false, updatable = false)
	private BankCard bankCard; ***/

	@Column(name = "ParticipantBankDetailsIdUsed")
	private Long participantBankDetailsIdUsed;

/***	@OneToOne(targetEntity = ParticipantBankDetails.class)
	@JoinColumn(name = "ParticipantBankDetailsIdUsed", referencedColumnName = "ParticipantBankDetailsId", nullable = true, insertable = false, updatable = false)
	private ParticipantBankDetails participantBankDetails; ***/

	@Column(name = "TaxDeductableCategoryId")
	private Long taxDeductableCategoryId;

/***	@OneToOne(targetEntity = TaxDeductableCategory.class)
	@JoinColumn(name = "TaxDeductableCategoryId", referencedColumnName = "TaxDeductableCategoryId", nullable = true, insertable = false, updatable = false)
	private TaxDeductableCategory taxDeductableCategory; ***/

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

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;




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

/***	public ProjectParticipant getProjectParticipant() {
		return projectParticipant;
	}

	public void setProjectParticipant(ProjectParticipant projectParticipant) {
		this.projectParticipant = projectParticipant;
	} ***/

	public Long getParticipantIdMadePurchase() {
		return participantIdMadePurchase;
	}

	public void setParticipantIdMadePurchase(Long participantIdMadePurchase) {
		this.participantIdMadePurchase = participantIdMadePurchase;
	}

/***	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	} ***/

	public Long getParticipantIdVendor() {
		return participantIdVendor;
	}

	public void setParticipantIdVendor(Long participantIdVendor) {
		this.participantIdVendor = participantIdVendor;
	}

/***	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	} ***/

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

/***	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	} ***/

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

/***	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	} ***/

	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

/***	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	} ***/

	public Long getBankCardIdUsed() {
		return bankCardIdUsed;
	}

	public void setBankCardIdUsed(Long bankCardIdUsed) {
		this.bankCardIdUsed = bankCardIdUsed;
	}

/***	public BankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
	} ***/

	public Long getParticipantBankDetailsIdUsed() {
		return participantBankDetailsIdUsed;
	}

	public void setParticipantBankDetailsIdUsed(Long participantBankDetailsIdUsed) {
		this.participantBankDetailsIdUsed = participantBankDetailsIdUsed;
	}

/***	public ParticipantBankDetails getParticipantBankDetails() {
		return participantBankDetails;
	}

	public void setParticipantBankDetails(ParticipantBankDetails participantBankDetails) {
		this.participantBankDetails = participantBankDetails;
	} ***/

	public Long getTaxDeductableCategoryId() {
		return taxDeductableCategoryId;
	}

	public void setTaxDeductableCategoryId(Long taxDeductableCategoryId) {
		this.taxDeductableCategoryId = taxDeductableCategoryId;
	}

/***	public TaxDeductableCategory getTaxDeductableCategory() {
		return taxDeductableCategory;
	}

	public void setTaxDeductableCategory(TaxDeductableCategory taxDeductableCategory) {
		this.taxDeductableCategory = taxDeductableCategory;
	} ***/

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

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}


}

/**  javascript
		{ data: "projectExpenseId" },         //0 MySql-TableName: ProjectExpense
		{ data: "projectParticipantIdPayer" }, //1
		{ data: "participantIdMadePurchase" }, //2
		{ data: "participantIdVendor" },      //3
		{ data: "expenseTypeId" },            //4
		{ data: "assetId" },                  //5
		{ data: "paymentMethodCode" },        //6
		{ data: "bankCardIdUsed" },           //7
		{ data: "participantBankDetailsIdUsed" }, //8
		{ data: "taxDeductableCategoryId" }, //9
		{ data: "paymentDescription" },       //10
		{ data: "purchaseDate" },             //11
		{ data: "numberOfUnits" },            //12
		{ data: "amountPerUnit" },            //13
		
		{ data: "noteToAccountant" },         //14
		{ data: "fullyLinked" },              //15
		{ data: "bankReference" },            //16
		{ data: "lastUpdateUserName" },       //17
		{ data: "lastUpdateTimestamp" },      //18

/**  html
										<th>ProjectExpenseId</th>         <!--0  MySql-TableName: ProjectExpense-->
										<th>ProjectParticipantIdPayer</th> <!--1  ProjectParticipantIdPayer-->
										<th>ParticipantIdMadePurchase</th> <!--2  ParticipantIdMadePurchase-->
										<th>ParticipantIdVendor</th>      <!--3  ParticipantIdVendor-->
										<th>ExpenseTypeId</th>            <!--4  ExpenseTypeId-->
										<th>AssetId</th>                  <!--5  AssetId-->
										<th>PaymentMethodCode</th>        <!--6  PaymentMethodCode-->
										<th>BankCardIdUsed</th>           <!--7  BankCardIdUsed-->
										<th>ParticipantBankDetailsIdUsed</th> <!--8  ParticipantBankDetailsIdUsed-->
										<th>TaxDeductableCategoryId</th> <!--9  TaxDeductableCategoryId-->
										<th>PaymentDescription</th>       <!--10  PaymentDescription-->
										<th>PurchaseDate</th>             <!--11  PurchaseDate-->
										<th>NumberOfUnits</th>            <!--12  NumberOfUnits-->
										<th>AmountPerUnit</th>            <!--13  AmountPerUnit-->
										
										<th>NoteToAccountant</th>         <!--14  NoteToAccountant-->
										<th>FullyLinked</th>              <!--15  FullyLinked-->
										<th>BankReference</th>            <!--16  BankReference-->
										<th>LastUpdateUserName</th>       <!--17  LastUpdateUserName-->
										<th>LastUpdateTimestamp</th>      <!--18  LastUpdateTimestamp-->

*/