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
/** ******* ********* ** 2023-10-19 07:21:49 ******** *psd* **/

@Entity
@Table(schema = "ig_db", name = "ProjectSd")
public class ProjectSd implements Serializable {


    private static final long serialVersionUID = 265895076483952884L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectSdId")
	private Long projectSdId;

	@Column(name = "ProjectId")
	private Long projectId;

/***	@OneToOne(targetEntity = Project.class)
	@JoinColumn(name = "ProjectId", referencedColumnName = "ProjectId", nullable = true, insertable = false, updatable = false)
	private Project project; ***/

	@Column(name = "ServiceDisciplineId")
	private Long serviceDisciplineId;

/***	@OneToOne(targetEntity = ServiceDiscipline.class)
	@JoinColumn(name = "ServiceDisciplineId", referencedColumnName = "ServiceDisciplineId", nullable = true, insertable = false, updatable = false)
	private ServiceDiscipline serviceDiscipline; ***/

	@Column(name = "ProjectSdIdClonedFrom")
	private Long projectSdIdClonedFrom;

/***	@OneToOne(targetEntity = ProjectSd.class)
	@JoinColumn(name = "ProjectSdIdClonedFrom", referencedColumnName = "ProjectSdId", nullable = true, insertable = false, updatable = false)
	private ProjectSd projectSdClonedFrom; ***/

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

/***	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	} ***/

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

	public Long getProjectSdIdClonedFrom() {
		return projectSdIdClonedFrom;
	}

	public void setProjectSdIdClonedFrom(Long projectSdIdClonedFrom) {
		this.projectSdIdClonedFrom = projectSdIdClonedFrom;
	}

/***	public ProjectSd getProjectSdClonedFrom() {
		return projectSdClonedFrom;
	}

	public void setProjectSdClonedFrom(ProjectSd projectSdClonedFrom) {
		this.projectSdClonedFrom = projectSdClonedFrom;
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
		 { data: "projectSdId" }              //0 MySql-TableName: ProjectSd
		,{ data: "projectId" }                //1
		,{ data: "serviceDisciplineId" }      //2
		,{ data: "projectSdIdClonedFrom" }    //3
		,{ data: "lastUpdateTimestamp" }      //4
		,{ data: "lastUpdateUserName" }       //5
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 5]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [4]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "projectSdId", name: "ProjectSdId" }                       //0 MySql-TableName: ProjectSd
		,{ data: "projectId", name: "ProjectId" }                           //1
		,{ data: "serviceDisciplineId", name: "ServiceDisciplineId" }       //2
		,{ data: "projectSdIdClonedFrom", name: "ProjectSdIdClonedFrom" }   //3
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //4
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //5
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 5]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [4]
		}
	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/project-sd/new";
	var postData = {
		projectSdId : $("#psdProjectSdId").val()              //0 MySql-TableName: ProjectSd
		,projectId : $("#psdProjectId").val()                                        //1
		,serviceDisciplineId : $("#psdServiceDisciplineId").val()                    //2
		,projectSdIdClonedFrom : $("#psdProjectSdIdClonedFrom").val()                //3
		,lastUpdateTimestamp : getMsFromDatePicker("psdLastUpdateTimestamp")         //4
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: ProjectSd										   (js3Str)
		$("#psdProjectSdId").val(data.projectSdId);                       //0
		$("#psdProjectId").val(data.projectId);                           //1
		populateSelect("psdProjectId",                                    //name of html select element that will be populated
				"/rest/ignite/v1/project/find-all",                       //url
				"projectId",                                              //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.projectId,                                           //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#psdServiceDisciplineId").val(data.serviceDisciplineId);       //2
		populateSelect("psdServiceDisciplineId",                          //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",            //url
				"serviceDisciplineId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineId,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#psdProjectSdIdClonedFrom").val(data.projectSdIdClonedFrom);   //3
		populateSelect("psdProjectSdIdClonedFrom",                        //name of html select element that will be populated
				"/rest/ignite/v1/project-sd/find-all",                    //url
				"projectSdId",                                            //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.projectSdIdClonedFrom,                               //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#psdLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //4






/**  HTML  om 'n grid te populate
												<th>ProjectSdId</th>                 <!--0  MySql-TableName: ProjectSd-->
												<th>ProjectId</th>                   <!--1  ProjectId-->
												<th>ServiceDisciplineId</th>         <!--2  ServiceDisciplineId-->
												<th>Project Sd Id Cloned From</th>   <!--3  ProjectSdIdClonedFrom-->
												<th>Last Update Timestamp</th>       <!--4  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--5  LastUpdateUserName-->

*/