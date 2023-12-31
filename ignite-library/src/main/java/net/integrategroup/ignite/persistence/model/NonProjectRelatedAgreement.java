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
/** ******* ********* ** 2023-10-25 18:52:06 ******** *xxx* **/

@Entity
@Table(schema = "ig_db", name = "NonProjectRelatedAgreement")
public class NonProjectRelatedAgreement implements Serializable {


    private static final long serialVersionUID = 910919345978691358L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NonProjectRelatedAgreementId")
	private Long nonProjectRelatedAgreementId;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantIdPayer", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participantPayer; ***/

	@Column(name = "ParticipantIdBeneficiary")
	private Long participantIdBeneficiary;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantIdBeneficiary", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participantBeneficiary; ***/

	@Column(name = "ResourceTypeId")
	private Long resourceTypeId;

/***	@OneToOne(targetEntity = ResourceType.class)
	@JoinColumn(name = "ResourceTypeId", referencedColumnName = "ResourceTypeId", nullable = true, insertable = false, updatable = false)
	private ResourceType resourceTypeBeneficiary; ***/

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getNonProjectRelatedAgreementId() {
		return nonProjectRelatedAgreementId;
	}

	public void setNonProjectRelatedAgreementId(Long nonProjectRelatedAgreementId) {
		this.nonProjectRelatedAgreementId = nonProjectRelatedAgreementId;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

/***	public Participant getParticipantPayer() {
		return participantPayer;
	}

	public void setParticipantPayer(Participant participantPayer) {
		this.participantPayer = participantPayer;
	} ***/

	public Long getParticipantIdBeneficiary() {
		return participantIdBeneficiary;
	}

	public void setParticipantIdBeneficiary(Long participantIdBeneficiary) {
		this.participantIdBeneficiary = participantIdBeneficiary;
	}

/***	public Participant getParticipantBeneficiary() {
		return participantBeneficiary;
	}

	public void setParticipantBeneficiary(Participant participantBeneficiary) {
		this.participantBeneficiary = participantBeneficiary;
	} ***/

	public Long getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

/***	public ResourceType getResourceTypeBeneficiary() {
		return resourceTypeBeneficiary;
	}

	public void setResourceTypeBeneficiary(ResourceType resourceTypeBeneficiary) {
		this.resourceTypeBeneficiary = resourceTypeBeneficiary;
	} ***/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
		 { data: "nonProjectRelatedAgreementId" } //0 MySql-TableName: NonProjectRelatedAgreement
		,{ data: "participantIdPayer" }       //1
		,{ data: "participantIdBeneficiary" } //2
		,{ data: "resourceTypeId" }           //3
		,{ data: "description" }              //4
		,{ data: "startDate" }                //5
		,{ data: "endDate" }                  //6
		,{ data: "lastUpdateTimestamp" }      //7
		,{ data: "lastUpdateUserName" }       //8
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 8]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [5, 6, 7]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "nonProjectRelatedAgreementId", name: "NonProjectRelatedAgreementId" } //0 MySql-TableName: NonProjectRelatedAgreement
		,{ data: "participantIdPayer", name: "ParticipantIdPayer" }         //1
		,{ data: "participantIdBeneficiary", name: "ParticipantIdBeneficiary" } //2
		,{ data: "resourceTypeId", name: "ResourceTypeId" }                 //3
		,{ data: "description", name: "Description" }                       //4
		,{ data: "startDate", name: "StartDate" }                           //5
		,{ data: "endDate", name: "EndDate" }                               //6
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //7
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //8
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 8]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [5, 6, 7]
		}
	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/non-project-related-agreement/new";
	var postData = {
		nonProjectRelatedAgreementId : $("#xxxNonProjectRelatedAgreementId").val() //0 MySql-TableName: NonProjectRelatedAgreement
		,participantIdPayer : $("#xxxParticipantIdPayer").val()                      //1
		,participantIdBeneficiary : $("#xxxParticipantIdBeneficiary").val()          //2
		,resourceTypeId : $("#xxxResourceTypeId").val()                              //3
		,description : $("#xxxDescription").val()                                    //4
		,startDate : getMsFromDatePicker("xxxStartDate")                             //5
		,endDate : getMsFromDatePicker("xxxEndDate")                                 //6
		,lastUpdateTimestamp : getMsFromDatePicker("xxxLastUpdateTimestamp")         //7
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: NonProjectRelatedAgreement										   (js3Str)
		$("#xxxNonProjectRelatedAgreementId").val(data.nonProjectRelatedAgreementId); //0
		$("#xxxParticipantIdPayer").val(data.participantIdPayer);         //1
		populateSelect("xxxParticipantIdPayer",                           //name of html select element that will be populated
				"/rest/ignite/v1/participant/find-all",                   //url
				"participantId",                                          //the value that must be saved (ReferencedColumnName)
				"systemName",                                             //shown to user (usually a Name column)
				data.participantIdPayer,                                  //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxParticipantIdBeneficiary").val(data.participantIdBeneficiary); //2
		populateSelect("xxxParticipantIdBeneficiary",                     //name of html select element that will be populated
				"/rest/ignite/v1/participant/find-all",                   //url
				"participantId",                                          //the value that must be saved (ReferencedColumnName)
				"systemName",                                             //shown to user (usually a Name column)
				data.participantIdBeneficiary,                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxResourceTypeId").val(data.resourceTypeId);                 //3
		populateSelect("xxxResourceTypeId",                               //name of html select element that will be populated
				"/rest/ignite/v1/resource-type/find-all",                 //url
				"resourceTypeId",                                         //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.resourceTypeId,                                      //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxDescription").val(data.description);                       //4
		$("#xxxStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));                           //5
		$("#xxxEndDate").datepicker("setDate", data.endDate == null? timestampToString(new Date(), false) : new Date(data.endDate));                               //6
		$("#xxxLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //7






/**  HTML  om 'n grid te populate
												<th>NonProjectRelatedAgreementId</th> <!--0  MySql-TableName: NonProjectRelatedAgreement-->
												<th>ParticipantIdPayer</th>          <!--1  ParticipantIdPayer-->
												<th>ParticipantIdBeneficiary</th>    <!--2  ParticipantIdBeneficiary-->
												<th>ResourceTypeId</th>              <!--3  ResourceTypeId-->
												<th>Description</th>                 <!--4  Description-->
												<th>Start Date</th>                  <!--5  StartDate-->
												<th>End Date</th>                    <!--6  EndDate-->
												<th>Last Update Timestamp</th>       <!--7  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--8  LastUpdateUserName-->

*/