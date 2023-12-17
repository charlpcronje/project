package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-29 10:56:44 ******** *r* **/

@Entity
@Table(schema = "ig_db", name = "RemunerationInterval")
public class RemunerationInterval implements Serializable {


    private static final long serialVersionUID = 262996690052434186L; /** ID was Generated by Johannes **/


    @Id
	@Column(name = "RemunerationIntervalCode")
	private String remunerationIntervalCode;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public String getRemunerationIntervalCode() {
		return remunerationIntervalCode;
	}

	public void setRemunerationIntervalCode(String remunerationIntervalCode) {
		this.remunerationIntervalCode = remunerationIntervalCode;
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
		 { data: "remunerationIntervalCode" } //0 MySql-TableName: RemunerationInterval
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
		{ data: "remunerationIntervalCode", name: "RemunerationIntervalCode" } //0 MySql-TableName: RemunerationInterval
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


	var postUrl = "/rest/ignite/v1/remuneration-interval/new";
	var postData = {
		remunerationIntervalCode : $("#rRemunerationIntervalCode").val() //0 MySql-TableName: RemunerationInterval
		,name : $("#rName").val()                                                  //1
		,description : $("#rDescription").val()                                    //2
		,lastUpdateTimestamp : getMsFromDatePicker("rLastUpdateTimestamp")         //3
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: RemunerationInterval										   (js3Str)
		$("#rRemunerationIntervalCode").val(data.remunerationIntervalCode); //0
		$("#rName").val(data.name);                                     //1
		$("#rDescription").val(data.description);                       //2
		$("#rLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //3






/**  HTML  om 'n grid te populate
												<th>RemunerationIntervalCode</th>    <!--0  MySql-TableName: RemunerationInterval-->
												<th>Name</th>                        <!--1  Name-->
												<th>Description</th>                 <!--2  Description-->
												<th>Last Update Timestamp</th>       <!--3  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--4  LastUpdateUserName-->

*/