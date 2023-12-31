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
/** ******* ********* ** 2023-10-25 18:50:24 ******** *xxx* **/

@Entity
@Table(schema = "ig_db", name = "GenProcedure")
public class GenProcedure implements Serializable {


    private static final long serialVersionUID = 183490850697832890L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GenProcedureId")
	private Long genProcedureId;

	@Column(name = "ServiceDisciplineId")
	private Long serviceDisciplineId;

/***	@OneToOne(targetEntity = ServiceDiscipline.class)
	@JoinColumn(name = "ServiceDisciplineId", referencedColumnName = "ServiceDisciplineId", nullable = true, insertable = false, updatable = false)
	private ServiceDiscipline serviceDiscipline; ***/

	@Column(name = "ProcedureStatusId")
	private Long procedureStatusId;

/***	@OneToOne(targetEntity = ProcedureStatus.class)
	@JoinColumn(name = "ProcedureStatusId", referencedColumnName = "ProcedureStatusId", nullable = true, insertable = false, updatable = false)
	private ProcedureStatus procedureStatus; ***/

	@Column(name = "ProcedureNumber")
	private Long procedureNumber;

	@Column(name = "Title")
	private String title;

	@Column(name = "Scope")
	private String scope;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "DateCreated")
	private Date dateCreated;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getGenProcedureId() {
		return genProcedureId;
	}

	public void setGenProcedureId(Long genProcedureId) {
		this.genProcedureId = genProcedureId;
	}

	public Long getServiceDisciplineId() {
		return serviceDisciplineId;
	}

	public void setServiceDisciplineId(Long serviceDisciplineId) {
		this.serviceDisciplineId = serviceDisciplineId;
	}

/***	public ServiceDiscipline getServiceDiscipline() {
		return serviceDiscipline;
	}

	public void setServiceDiscipline(ServiceDiscipline serviceDiscipline) {
		this.serviceDiscipline = serviceDiscipline;
	} ***/

	public Long getProcedureStatusId() {
		return procedureStatusId;
	}

	public void setProcedureStatusId(Long procedureStatusId) {
		this.procedureStatusId = procedureStatusId;
	}

/***	public ProcedureStatus getProcedureStatus() {
		return procedureStatus;
	}

	public void setProcedureStatus(ProcedureStatus procedureStatus) {
		this.procedureStatus = procedureStatus;
	} ***/

	public Long getProcedureNumber() {
		return procedureNumber;
	}

	public void setProcedureNumber(Long procedureNumber) {
		this.procedureNumber = procedureNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
		 { data: "genProcedureId" }           //0 MySql-TableName: GenProcedure
		,{ data: "serviceDisciplineId" }      //1
		,{ data: "procedureStatusId" }        //2
		,{ data: "procedureNumber" }          //3
		,{ data: "title" }                    //4
		,{ data: "scope" }                    //5
		,{ data: "dateCreated" }              //6
		,{ data: "lastUpdateTimestamp" }      //7
		,{ data: "lastUpdateUserName" }       //8
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 8]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [6, 7]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "genProcedureId", name: "GenProcedureId" }                 //0 MySql-TableName: GenProcedure
		,{ data: "serviceDisciplineId", name: "ServiceDisciplineId" }       //1
		,{ data: "procedureStatusId", name: "ProcedureStatusId" }           //2
		,{ data: "procedureNumber", name: "ProcedureNumber" }               //3
		,{ data: "title", name: "Title" }                                   //4
		,{ data: "scope", name: "Scope" }                                   //5
		,{ data: "dateCreated", name: "DateCreated" }                       //6
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //7
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //8
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 8]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [6, 7]
		}
	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/gen-procedure/new";
	var postData = {
		genProcedureId : $("#xxxGenProcedureId").val()           //0 MySql-TableName: GenProcedure
		,serviceDisciplineId : $("#xxxServiceDisciplineId").val()                    //1
		,procedureStatusId : $("#xxxProcedureStatusId").val()                        //2
		,procedureNumber : $("#xxxProcedureNumber").val()                            //3
		,title : $("#xxxTitle").val()                                                //4
		,scope : $("#xxxScope").val()                                                //5
		,dateCreated : getMsFromDatePicker("xxxDateCreated")                         //6
		,lastUpdateTimestamp : getMsFromDatePicker("xxxLastUpdateTimestamp")         //7
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: GenProcedure										   (js3Str)
		$("#xxxGenProcedureId").val(data.genProcedureId);                 //0
		$("#xxxServiceDisciplineId").val(data.serviceDisciplineId);       //1
		populateSelect("xxxServiceDisciplineId",                          //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",            //url
				"serviceDisciplineId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineId,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxProcedureStatusId").val(data.procedureStatusId);           //2
		populateSelect("xxxProcedureStatusId",                            //name of html select element that will be populated
				"/rest/ignite/v1/procedure-status/find-all",              //url
				"procedureStatusId",                                      //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.procedureStatusId,                                   //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxProcedureNumber").val(data.procedureNumber);               //3
		$("#xxxTitle").val(data.title);                                   //4
		$("#xxxScope").val(data.scope);                                   //5
		$("#xxxDateCreated").datepicker("setDate", data.dateCreated == null? timestampToString(new Date(), false) : new Date(data.dateCreated));                       //6
		$("#xxxLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //7






/**  HTML  om 'n grid te populate
												<th>GenProcedureId</th>              <!--0  MySql-TableName: GenProcedure-->
												<th>ServiceDisciplineId</th>         <!--1  ServiceDisciplineId-->
												<th>ProcedureStatusId</th>           <!--2  ProcedureStatusId-->
												<th>Procedure Number</th>            <!--3  ProcedureNumber-->
												<th>Title</th>                       <!--4  Title-->
												<th>Scope</th>                       <!--5  Scope-->
												<th>Date Created</th>                <!--6  DateCreated-->
												<th>Last Update Timestamp</th>       <!--7  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--8  LastUpdateUserName-->

*/