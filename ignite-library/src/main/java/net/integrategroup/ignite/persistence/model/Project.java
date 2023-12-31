package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-11-20 10:00:34 ******** *xxx* **/

@Entity
@Table(schema = "ig_db", name = "Project")
public class Project implements Serializable {


    private static final long serialVersionUID = 243774352416954506L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectIdParent")
	private Long projectIdParent;

/***	@OneToOne(targetEntity = Project.class)
	@JoinColumn(name = "ProjectIdParent", referencedColumnName = "ProjectId", nullable = true, insertable = false, updatable = false)
	private Project projectParent; ***/

	@Column(name = "ParticipantIdHost")
	private Long participantIdHost;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantIdHost", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participantHost; ***/

	@Column(name = "ProjectParticipantIdLevel1")
	private Long projectParticipantIdLevel1;

/***	@OneToOne(targetEntity = ProjectParticipant.class)
	@JoinColumn(name = "ProjectParticipantIdLevel1", referencedColumnName = "ProjectParticipantId", nullable = true, insertable = false, updatable = false)
	private ProjectParticipant projectParticipantLevel1; ***/

	@Column(name = "IndividualIdProjectAdmin")
	private Long individualIdProjectAdmin;

/***	@OneToOne(targetEntity = Individual.class)
	@JoinColumn(name = "IndividualIdProjectAdmin", referencedColumnName = "IndividualId", nullable = true, insertable = false, updatable = false)
	private Individual individualProjectAdmin; ***/

	@Column(name = "FlagSustenanceProject")
	private String flagSustenanceProject;

	@Column(name = "ProjectNumberBigInt")
	private Long projectNumberBigInt;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;

	@Column(name = "ProjectNameText")
	private String projectNameText;

	@Column(name = "Title")
	private String title;

	@Column(name = "SubProjNumber")
	private String subProjNumber;

	@Column(name = "Description")
	private String description;

	@Column(name = "IsActive")
	private String isActive;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "CompletionDate")
	private Date completionDate;

	@Column(name = "ProjectIdClonedFrom")
	private Long projectIdClonedFrom;

/***	@OneToOne(targetEntity = Project.class)
	@JoinColumn(name = "ProjectIdClonedFrom", referencedColumnName = "ProjectId", nullable = true, insertable = false, updatable = false)
	private Project projectClonedFrom; ***/

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectIdParent() {
		return projectIdParent;
	}

	public void setProjectIdParent(Long projectIdParent) {
		this.projectIdParent = projectIdParent;
	}

/***	public Project getProjectParent() {
		return projectParent;
	}

	public void setProjectParent(Project projectParent) {
		this.projectParent = projectParent;
	} ***/

	public Long getParticipantIdHost() {
		return participantIdHost;
	}

	public void setParticipantIdHost(Long participantIdHost) {
		this.participantIdHost = participantIdHost;
	}

/***	public Participant getParticipantHost() {
		return participantHost;
	}

	public void setParticipantHost(Participant participantHost) {
		this.participantHost = participantHost;
	} ***/

	public Long getProjectParticipantIdLevel1() {
		return projectParticipantIdLevel1;
	}

	public void setProjectParticipantIdLevel1(Long projectParticipantIdLevel1) {
		this.projectParticipantIdLevel1 = projectParticipantIdLevel1;
	}

/***	public ProjectParticipant getProjectParticipantLevel1() {
		return projectParticipantLevel1;
	}

	public void setProjectParticipantLevel1(ProjectParticipant projectParticipantLevel1) {
		this.projectParticipantLevel1 = projectParticipantLevel1;
	} ***/

	public Long getIndividualIdProjectAdmin() {
		return individualIdProjectAdmin;
	}

	public void setIndividualIdProjectAdmin(Long individualIdProjectAdmin) {
		this.individualIdProjectAdmin = individualIdProjectAdmin;
	}

/***	public Individual getIndividualProjectAdmin() {
		return individualProjectAdmin;
	}

	public void setIndividualProjectAdmin(Individual individualProjectAdmin) {
		this.individualProjectAdmin = individualProjectAdmin;
	} ***/

	public String getFlagSustenanceProject() {
		return flagSustenanceProject;
	}

	public void setFlagSustenanceProject(String flagSustenanceProject) {
		this.flagSustenanceProject = flagSustenanceProject;
	}

	public Long getProjectNumberBigInt() {
		return projectNumberBigInt;
	}

	public void setProjectNumberBigInt(Long projectNumberBigInt) {
		this.projectNumberBigInt = projectNumberBigInt;
	}

	public String getProjectNumberText() {
		return projectNumberText;
	}

	public void setProjectNumberText(String projectNumberText) {
		this.projectNumberText = projectNumberText;
	}

	public String getProjectNameText() {
		return projectNameText;
	}

	public void setProjectNameText(String projectNameText) {
		this.projectNameText = projectNameText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubProjNumber() {
		return subProjNumber;
	}

	public void setSubProjNumber(String subProjNumber) {
		this.subProjNumber = subProjNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Long getProjectIdClonedFrom() {
		return projectIdClonedFrom;
	}

	public void setProjectIdClonedFrom(Long projectIdClonedFrom) {
		this.projectIdClonedFrom = projectIdClonedFrom;
	}

/***	public Project getProjectClonedFrom() {
		return projectClonedFrom;
	}

	public void setProjectClonedFrom(Project projectClonedFrom) {
		this.projectClonedFrom = projectClonedFrom;
	} ***/

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
		 { data: "projectId" }                //0 MySql-TableName: Project
		,{ data: "projectIdParent" }          //1
		,{ data: "participantIdHost" }        //2
		,{ data: "projectParticipantIdLevel1" } //3
		,{ data: "individualIdProjectAdmin" } //4
		,{ data: "flagSustenanceProject" }           //5
		,{ data: "projectNumberBigInt" }      //6
		,{ data: "projectNumberText" }        //7
		,{ data: "projectNameText" }          //8
		,{ data: "title" }                    //9
		,{ data: "subProjNumber" }            //10
		,{ data: "description" }              //11
		,{ data: "isActive" }                 //12
		,{ data: "startDate" }                //13
		,{ data: "completionDate" }           //14
		,{ data: "projectIdClonedFrom" }      //15
		,{ data: "lastUpdateTimestamp" }      //16
		,{ data: "lastUpdateUserName" }       //17
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 15, 17]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [13, 14, 16]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.flagSustenanceProject == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[5]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActive == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[12]
		}

	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "projectId", name: "ProjectId" }                           //0 MySql-TableName: Project
		,{ data: "projectIdParent", name: "ProjectIdParent" }               //1
		,{ data: "participantIdHost", name: "ParticipantIdHost" }           //2
		,{ data: "projectParticipantIdLevel1", name: "ProjectParticipantIdLevel1" } //3
		,{ data: "individualIdProjectAdmin", name: "IndividualIdProjectAdmin" } //4
		,{ data: "flagSustenanceProject", name: "FlagSustenanceProject" }                 //5
		,{ data: "projectNumberBigInt", name: "ProjectNumberBigInt" }       //6
		,{ data: "projectNumberText", name: "ProjectNumberText" }           //7
		,{ data: "projectNameText", name: "ProjectNameText" }               //8
		,{ data: "title", name: "Title" }                                   //9
		,{ data: "subProjNumber", name: "SubProjNumber" }                   //10
		,{ data: "description", name: "Description" }                       //11
		,{ data: "isActive", name: "IsActive" }                             //12
		,{ data: "startDate", name: "StartDate" }                           //13
		,{ data: "completionDate", name: "CompletionDate" }                 //14
		,{ data: "projectIdClonedFrom", name: "ProjectIdClonedFrom" }       //15
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //16
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //17
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 15, 17]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [13, 14, 16]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.flagSustenanceProject == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[5]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActive == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[12]
		}


	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/project/new";
	var postData = {
		projectId : $("#xxxProjectId").val()                //0 MySql-TableName: Project
		,projectIdParent : $("#xxxProjectIdParent").val()                            //1
		,participantIdHost : $("#xxxParticipantIdHost").val()                        //2
		,projectParticipantIdLevel1 : $("#xxxProjectParticipantIdLevel1").val()      //3
		,individualIdProjectAdmin : $("#xxxIndividualIdProjectAdmin").val()          //4
		,flagSustenanceProject : $("#xxxFlagSustenanceProject").val()                              //5    use one of these
		,flagSustenanceProject : $("#xxxFlagSustenanceProject").is(":checked") ? "Y" : "N"       //5    use one of these
		,projectNumberBigInt : $("#xxxProjectNumberBigInt").val()                    //6
		,projectNumberText : $("#xxxProjectNumberText").val()                        //7
		,projectNameText : $("#xxxProjectNameText").val()                            //8
		,title : $("#xxxTitle").val()                                                //9
		,subProjNumber : $("#xxxSubProjNumber").val()                                //10
		,description : $("#xxxDescription").val()                                    //11
		,isActive : $("#xxxIsActive").val()                                          //12    use one of these
		,isActive : $("#xxxIsActive").is(":checked") ? "Y" : "N"                   //12    use one of these
		,startDate : getMsFromDatePicker("xxxStartDate")                             //13
		,completionDate : getMsFromDatePicker("xxxCompletionDate")                   //14
		,projectIdClonedFrom : $("#xxxProjectIdClonedFrom").val()                    //15
		,lastUpdateTimestamp : getMsFromDatePicker("xxxLastUpdateTimestamp")         //16
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: Project										   (js3Str)
		$("#xxxProjectId").val(data.projectId);                           //0
		$("#xxxProjectIdParent").val(data.projectIdParent);               //1
		populateSelect("xxxProjectIdParent",                              //name of html select element that will be populated
				"/rest/ignite/v1/project/find-all",                       //url
				"projectId",                                              //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.projectIdParent,                                     //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxParticipantIdHost").val(data.participantIdHost);           //2
		populateSelect("xxxParticipantIdHost",                            //name of html select element that will be populated
				"/rest/ignite/v1/participant/find-all",                   //url
				"participantId",                                          //the value that must be saved (ReferencedColumnName)
				"systemName",                                             //shown to user (usually a Name column)
				data.participantIdHost,                                   //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxProjectParticipantIdLevel1").val(data.projectParticipantIdLevel1); //3
		populateSelect("xxxProjectParticipantIdLevel1",                   //name of html select element that will be populated
				"/rest/ignite/v1/project-participant/find-all",           //url
				"projectParticipantId",                                   //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.projectParticipantIdLevel1,                          //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxIndividualIdProjectAdmin").val(data.individualIdProjectAdmin); //4
		populateSelect("xxxIndividualIdProjectAdmin",                     //name of html select element that will be populated
				"/rest/ignite/v1/individual/find-all",                    //url
				"individualId",                                           //the value that must be saved (ReferencedColumnName)
				"firstName",                                              //shown to user (usually a Name column)
				data.individualIdProjectAdmin,                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxFlagSustenanceProject").val(data.flagSustenanceProject);                 //5    use one of these
		$("#xxxFlagSustenanceProject").prop("checked", data.flagSustenanceProject == "Y"); //5    use one of these
		$("#xxxProjectNumberBigInt").val(data.projectNumberBigInt);       //6
		$("#xxxProjectNumberText").val(data.projectNumberText);           //7
		$("#xxxProjectNameText").val(data.projectNameText);               //8
		$("#xxxTitle").val(data.title);                                   //9
		$("#xxxSubProjNumber").val(data.subProjNumber);                   //10
		$("#xxxDescription").val(data.description);                       //11
		$("#xxxIsActive").val(data.isActive);                             //12    use one of these
		$("#xxxIsActive").prop("checked", data.isActive == "Y");          //12    use one of these
		$("#xxxStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));                           //13
		$("#xxxCompletionDate").datepicker("setDate", data.completionDate == null? timestampToString(new Date(), false) : new Date(data.completionDate));                 //14
		$("#xxxProjectIdClonedFrom").val(data.projectIdClonedFrom);       //15
		populateSelect("xxxProjectIdClonedFrom",                          //name of html select element that will be populated
				"/rest/ignite/v1/project/find-all",                       //url
				"projectId",                                              //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.projectIdClonedFrom,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#xxxLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //16






/**  HTML  om 'n grid te populate
												<th>ProjectId</th>                   <!--0  MySql-TableName: Project-->
												<th>ProjectIdParent</th>             <!--1  ProjectIdParent-->
												<th>Participant Id Host</th>         <!--2  ParticipantIdHost-->
												<th>ProjectParticipantIdLevel1</th>  <!--3  ProjectParticipantIdLevel1-->
												<th>IndividualIdProjectAdmin</th>    <!--4  IndividualIdProjectAdmin-->
												<th>Sustenance</th>                  <!--5  FlagSustenanceProject-->
												<th>Project Number Big Int</th>      <!--6  ProjectNumberBigInt-->
												<th>Project Number Text</th>         <!--7  ProjectNumberText-->
												<th>Project Name Text</th>           <!--8  ProjectNameText-->
												<th>Title</th>                       <!--9  Title-->
												<th>Sub Proj Number</th>             <!--10  SubProjNumber-->
												<th>Description</th>                 <!--11  Description-->
												<th>Is Active</th>                   <!--12  IsActive-->
												<th>Start Date</th>                  <!--13  StartDate-->
												<th>Completion Date</th>             <!--14  CompletionDate-->
												<th>Project Id Cloned From</th>      <!--15  ProjectIdClonedFrom-->
												<th>Last Update Timestamp</th>       <!--16  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--17  LastUpdateUserName-->

*/