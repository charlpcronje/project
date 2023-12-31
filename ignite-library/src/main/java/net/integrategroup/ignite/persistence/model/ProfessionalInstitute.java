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
/** ******* ********* ** 2023-10-16 19:51:57 ******** ** **/

@Entity
@Table(schema = "ig_db", name = "ProfessionalInstitute")
public class ProfessionalInstitute implements Serializable {


    private static final long serialVersionUID = 416533584511545296L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProfessionalInstituteId")
	private Long professionalInstituteId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getProfessionalInstituteId() {
		return professionalInstituteId;
	}

	public void setProfessionalInstituteId(Long professionalInstituteId) {
		this.professionalInstituteId = professionalInstituteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		 { data: "professionalInstituteId" }  //0 MySql-TableName: ProfessionalInstitute
		,{ data: "name" }                     //1
		,{ data: "description" }              //2
		,{ data: "lastUpdateTimestamp" }      //3
		,{ data: "lastUpdateUserName" }       //4
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 4]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [3]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "professionalInstituteId", name: "ProfessionalInstituteId" } //0 MySql-TableName: ProfessionalInstitute
		,{ data: "name", name: "Name" }                                     //1
		,{ data: "description", name: "Description" }                       //2
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //3
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //4
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 4]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [3]
		}
	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/professional-institute/new";
	var postData = {
		professionalInstituteId : $("#ProfessionalInstituteId").val()  //0 MySql-TableName: ProfessionalInstitute
		,name : $("#Name").val()                                                  //1
		,description : $("#Description").val()                                    //2
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: ProfessionalInstitute										   (js3Str)
		$("#ProfessionalInstituteId").val(data.professionalInstituteId); //0
		$("#Name").val(data.name);                                     //1
		$("#Description").val(data.description);                       //2





/**  HTML  om 'n grid te populate
												<th>ProfessionalInstituteId</th>     <!--0  MySql-TableName: ProfessionalInstitute-->
												<th>Name</th>                        <!--1  Name-->
												<th>Description</th>                 <!--2  Description-->
												<th>Last Update Timestamp</th>       <!--3  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--4  LastUpdateUserName-->

*/