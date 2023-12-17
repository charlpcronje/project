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
/** ******* ********* ** 2023-10-19 07:20:02 ******** *roap* **/

@Entity
@Table(schema = "ig_db", name = "RoleOnAProject")
public class RoleOnAProject implements Serializable {

	private static final long serialVersionUID = 1545476095941018375L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RoleOnAProjectId")
	private Long roleOnAProjectId;

	@Column(name = "ServiceDisciplineIdIndustry")
	private Long serviceDisciplineIdIndustry;

	@Column(name = "Name")
	private String name;

	@Column(name = "Abbreviation")
	private String abbreviation;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getRoleOnAProjectId() {
		return roleOnAProjectId;
	}

	public void setRoleOnAProjectId(Long roleOnAProjectId) {
		this.roleOnAProjectId = roleOnAProjectId;
	}

	public Long getServiceDisciplineIdIndustry() {
		return serviceDisciplineIdIndustry;
	}

	public void setServiceDisciplineIdIndustry(Long serviceDisciplineIdIndustry) {
		this.serviceDisciplineIdIndustry = serviceDisciplineIdIndustry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
		 { data: "roleOnAProjectId" }       //0 MySql-TableName: RoleOnAProject
		,{ data: "serviceDisciplineIdIndustry" } //1
		,{ data: "name" }                     //2
		,{ data: "abbreviation" }             //3
		,{ data: "description" }              //4
		,{ data: "lastUpdateTimestamp" }      //5
		,{ data: "lastUpdateUserName" }       //6
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 6]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [5]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "roleOnAProjectId", name: "RoleOnAProjectId" }         //0 MySql-TableName: RoleOnAProject
		,{ data: "serviceDisciplineIdIndustry", name: "ServiceDisciplineIdIndustry" } //1
		,{ data: "name", name: "Name" }                                     //2
		,{ data: "abbreviation", name: "Abbreviation" }                     //3
		,{ data: "description", name: "Description" }                       //4
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //5
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //6
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 6]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [5]
		}
	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/role-on-a-project/new";
	var postData = {
		roleOnAProjectId : $("#roapRoleOnAProjectId").val()       //0 MySql-TableName: RoleOnAProject
		,serviceDisciplineIdIndustry : $("#roapServiceDisciplineIdIndustry").val()    //1
		,name : $("#roapName").val()                                                  //2
		,abbreviation : $("#roapAbbreviation").val()                                  //3
		,description : $("#roapDescription").val()                                    //4
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: RoleOnAProject										   (js3Str)
		$("#roapRoleOnAProjectId").val(data.roleOnAProjectId);         //0
		$("#roapServiceDisciplineIdIndustry").val(data.serviceDisciplineIdIndustry); //1
		populateSelect("roapServiceDisciplineIdIndustry",                 //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",            //url
				"serviceDisciplineId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineIdIndustry,                         //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#roapName").val(data.name);                                     //2
		$("#roapAbbreviation").val(data.abbreviation);                     //3
		$("#roapDescription").val(data.description);                       //4
		$("#roapLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //5






/**  HTML  om 'n grid te populate
												<th>RoleOnAProjectId</th>          <!--0  MySql-TableName: RoleOnAProject-->
												<th>ServiceDisciplineIdIndustry</th> <!--1  ServiceDisciplineIdIndustry-->
												<th>Name</th>                        <!--2  Name-->
												<th>Abbreviation</th>                <!--3  Abbreviation-->
												<th>Description</th>                 <!--4  Description-->
												<th>Last Update Timestamp</th>       <!--5  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--6  LastUpdateUserName-->

*/