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

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-25 18:42:25 ******** *bc* **/

@Entity
@Table(schema = "ig_db", name = "BankCard")
public class BankCard implements Serializable {


    private static final long serialVersionUID = 841009030510856074L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BankCardId")
	private Long bankCardId;

	@Column(name = "ParticipantBankDetailsId")
	private Long participantBankDetailsId;

/***	@OneToOne(targetEntity = ParticipantBankDetails.class)
	@JoinColumn(name = "ParticipantBankDetailsId", referencedColumnName = "ParticipantBankDetailsId", nullable = true, insertable = false, updatable = false)
	private ParticipantBankDetails participantBankDetails; ***/

	@Column(name = "IndividualIdMainCardUser")
	private Long individualIdMainCardUser;

/***	@OneToOne(targetEntity = Individual.class)
	@JoinColumn(name = "IndividualIdMainCardUser", referencedColumnName = "IndividualId", nullable = true, insertable = false, updatable = false)
	private Individual individualMainCardUser; ***/

	@Column(name = "CardTypeId")
	private Long cardTypeId;

/***	@OneToOne(targetEntity = CardType.class)
	@JoinColumn(name = "CardTypeId", referencedColumnName = "CardTypeId", nullable = true, insertable = false, updatable = false)
	private CardType cardTypeMainCardUser; ***/

	@Column(name = "CardNumber")
	private String cardNumber;

	@Column(name = "NameOnCard")
	private String nameOnCard;

	@Column(name = "PersonalCard")
	private String personalCard;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}

	public Long getParticipantBankDetailsId() {
		return participantBankDetailsId;
	}

	public void setParticipantBankDetailsId(Long participantBankDetailsId) {
		this.participantBankDetailsId = participantBankDetailsId;
	}

/***	public ParticipantBankDetails getParticipantBankDetails() {
		return participantBankDetails;
	}

	public void setParticipantBankDetails(ParticipantBankDetails participantBankDetails) {
		this.participantBankDetails = participantBankDetails;
	} ***/

	public Long getIndividualIdMainCardUser() {
		return individualIdMainCardUser;
	}

	public void setIndividualIdMainCardUser(Long individualIdMainCardUser) {
		this.individualIdMainCardUser = individualIdMainCardUser;
	}

/***	public Individual getIndividualMainCardUser() {
		return individualMainCardUser;
	}

	public void setIndividualMainCardUser(Individual individualMainCardUser) {
		this.individualMainCardUser = individualMainCardUser;
	} ***/

	public Long getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

/***	public CardType getCardTypeMainCardUser() {
		return cardTypeMainCardUser;
	}

	public void setCardTypeMainCardUser(CardType cardTypeMainCardUser) {
		this.cardTypeMainCardUser = cardTypeMainCardUser;
	} ***/

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getPersonalCard() {
		return personalCard;
	}

	public void setPersonalCard(String personalCard) {
		this.personalCard = personalCard;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

/**  javascript      vir grid population        (jsStr)

	var columnsArray = [
		 { data: "bankCardId" }               //0 MySql-TableName: BankCard
		,{ data: "participantBankDetailsId" } //1
		,{ data: "individualIdMainCardUser" } //2
		,{ data: "cardTypeId" }               //3
		,{ data: "cardNumber" }               //4
		,{ data: "nameOnCard" }               //5
		,{ data: "personalCard" }             //6
		,{ data: "description" }              //7
		,{ data: "lastUpdateTimestamp" }      //8
		,{ data: "lastUpdateUserName" }       //9
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 9]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [8]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.personalCard == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[6]
		}

	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "bankCardId", name: "BankCardId" }                         //0 MySql-TableName: BankCard
		,{ data: "participantBankDetailsId", name: "ParticipantBankDetailsId" } //1
		,{ data: "individualIdMainCardUser", name: "IndividualIdMainCardUser" } //2
		,{ data: "cardTypeId", name: "CardTypeId" }                         //3
		,{ data: "cardNumber", name: "CardNumber" }                         //4
		,{ data: "nameOnCard", name: "NameOnCard" }                         //5
		,{ data: "personalCard", name: "PersonalCard" }                     //6
		,{ data: "description", name: "Description" }                       //7
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //8
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //9
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 9]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [8]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.personalCard == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[6]
		}


	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/bank-card/new";
	var postData = {
		bankCardId : $("#bcBankCardId").val()               //0 MySql-TableName: BankCard
		,participantBankDetailsId : $("#bcParticipantBankDetailsId").val()          //1
		,individualIdMainCardUser : $("#bcIndividualIdMainCardUser").val()          //2
		,cardTypeId : $("#bcCardTypeId").val()                                      //3
		,cardNumber : $("#bcCardNumber").val()                                      //4
		,nameOnCard : $("#bcNameOnCard").val()                                      //5
		,personalCard : $("#bcPersonalCard").val()                                  //6    use one of these
		,personalCard : $("#bcPersonalCard").is(":checked") ? "Y" : "N"           //6    use one of these
		,description : $("#bcDescription").val()                                    //7
		,lastUpdateTimestamp : getMsFromDatePicker("bcLastUpdateTimestamp")         //8
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: BankCard										   (js3Str)
		$("#bcBankCardId").val(data.bankCardId);                         //0
		$("#bcParticipantBankDetailsId").val(data.participantBankDetailsId); //1
		populateSelect("bcParticipantBankDetailsId",                      //name of html select element that will be populated
				"/rest/ignite/v1/participant-bank-details/find-all",      //url
				"participantBankDetailsId",                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.participantBankDetailsId,                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#bcIndividualIdMainCardUser").val(data.individualIdMainCardUser); //2
		populateSelect("bcIndividualIdMainCardUser",                      //name of html select element that will be populated
				"/rest/ignite/v1/individual/find-all",                    //url
				"individualId",                                           //the value that must be saved (ReferencedColumnName)
				"firstName",                                              //shown to user (usually a Name column)
				data.individualIdMainCardUser,                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#bcCardTypeId").val(data.cardTypeId);                         //3
		populateSelect("bcCardTypeId",                                    //name of html select element that will be populated
				"/rest/ignite/v1/card-type/find-all",                     //url
				"cardTypeId",                                             //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.cardTypeId,                                          //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#bcCardNumber").val(data.cardNumber);                         //4
		$("#bcNameOnCard").val(data.nameOnCard);                         //5
		$("#bcPersonalCard").val(data.personalCard);                     //6    use one of these
		$("#bcPersonalCard").prop("checked", data.personalCard == "Y");  //6    use one of these
		$("#bcDescription").val(data.description);                       //7
		$("#bcLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //8






/**  HTML  om 'n grid te populate
												<th>BankCardId</th>                  <!--0  MySql-TableName: BankCard-->
												<th>ParticipantBankDetailsId</th>    <!--1  ParticipantBankDetailsId-->
												<th>IndividualIdMainCardUser</th>    <!--2  IndividualIdMainCardUser-->
												<th>CardTypeId</th>                  <!--3  CardTypeId-->
												<th>Card Number</th>                 <!--4  CardNumber-->
												<th>Name On Card</th>                <!--5  NameOnCard-->
												<th>Personal Card</th>               <!--6  PersonalCard-->
												<th>Description</th>                 <!--7  Description-->
												<th>Last Update Timestamp</th>       <!--8  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--9  LastUpdateUserName-->

*/