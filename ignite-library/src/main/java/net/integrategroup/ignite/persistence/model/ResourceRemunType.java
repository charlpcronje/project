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
/** ******* ********* ** 2023-10-29 11:11:25 ******** *rrt* **/

@Entity
@Table(schema = "ig_db", name = "ResourceRemunType")
public class ResourceRemunType implements Serializable {


    private static final long serialVersionUID = 851148402661677399L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ResourceRemunTypeId")
	private Long resourceRemunTypeId;

	@Column(name = "RemunerationIntervalCode")
	private String remunerationIntervalCode;

/***	@OneToOne(targetEntity = RemunerationInterval.class)
	@JoinColumn(name = "RemunerationIntervalCode", referencedColumnName = "RemunerationIntervalCode", nullable = true, insertable = false, updatable = false)
	private RemunerationInterval remunerationInterval; ***/

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

/***	@OneToOne(targetEntity = UnitType.class)
	@JoinColumn(name = "UnitTypeCode", referencedColumnName = "UnitTypeCode", nullable = true, insertable = false, updatable = false)
	private UnitType unitType; ***/

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getResourceRemunTypeId() {
		return resourceRemunTypeId;
	}

	public void setResourceRemunTypeId(Long resourceRemunTypeId) {
		this.resourceRemunTypeId = resourceRemunTypeId;
	}

	public String getRemunerationIntervalCode() {
		return remunerationIntervalCode;
	}

	public void setRemunerationIntervalCode(String remunerationIntervalCode) {
		this.remunerationIntervalCode = remunerationIntervalCode;
	}

/***	public RemunerationInterval getRemunerationInterval() {
		return remunerationInterval;
	}

	public void setRemunerationInterval(RemunerationInterval remunerationInterval) {
		this.remunerationInterval = remunerationInterval;
	} ***/

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}

/***	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	} ***/

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
		 { data: "resourceRemunTypeId" }      //0 MySql-TableName: ResourceRemunType
		,{ data: "remunerationIntervalCode" } //1
		,{ data: "unitTypeCode" }             //2
		,{ data: "name" }                     //3
		,{ data: "description" }              //4
		,{ data: "lastUpdateTimestamp" }      //5
		,{ data: "lastUpdateUserName" }       //6
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 6]
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
		{ data: "resourceRemunTypeId", name: "ResourceRemunTypeId" }       //0 MySql-TableName: ResourceRemunType
		,{ data: "remunerationIntervalCode", name: "RemunerationIntervalCode" } //1
		,{ data: "unitTypeCode", name: "UnitTypeCode" }                     //2
		,{ data: "name", name: "Name" }                                     //3
		,{ data: "description", name: "Description" }                       //4
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //5
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //6
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 6]
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


	var postUrl = "/rest/ignite/v1/resource-remun-type/new";
	var postData = {
		resourceRemunTypeId : $("#rrtResourceRemunTypeId").val()      //0 MySql-TableName: ResourceRemunType
		,remunerationIntervalCode : $("#rrtRemunerationIntervalCode").val()          //1
		,unitTypeCode : $("#rrtUnitTypeCode").val()                                  //2
		,name : $("#rrtName").val()                                                  //3
		,description : $("#rrtDescription").val()                                    //4
		,lastUpdateTimestamp : getMsFromDatePicker("rrtLastUpdateTimestamp")         //5
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: ResourceRemunType										   (js3Str)
		$("#rrtResourceRemunTypeId").val(data.resourceRemunTypeId);       //0
		$("#rrtRemunerationIntervalCode").val(data.remunerationIntervalCode); //1
		populateSelect("rrtRemunerationIntervalCode",                     //name of html select element that will be populated
				"/rest/ignite/v1/remuneration-interval/find-all",         //url
				"remunerationIntervalCode",                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.remunerationIntervalCode,                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#rrtUnitTypeCode").val(data.unitTypeCode);                     //2
		populateSelect("rrtUnitTypeCode",                                 //name of html select element that will be populated
				"/rest/ignite/v1/unit-type/find-all",                     //url
				"unitTypeCode",                                           //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.unitTypeCode,                                        //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#rrtName").val(data.name);                                     //3
		$("#rrtDescription").val(data.description);                       //4
		$("#rrtLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //5






/**  HTML  om 'n grid te populate
												<th>ResourceRemunTypeId</th>         <!--0  MySql-TableName: ResourceRemunType-->
												<th>RemunerationIntervalCode</th>    <!--1  RemunerationIntervalCode-->
												<th>UnitTypeCode</th>                <!--2  UnitTypeCode-->
												<th>Name</th>                        <!--3  Name-->
												<th>Description</th>                 <!--4  Description-->
												<th>Last Update Timestamp</th>       <!--5  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--6  LastUpdateUserName-->

*/