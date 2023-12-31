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
/** ******* ********* ** 2023-10-25 18:51:44 ******** *xxx* **/

@Entity
@Table(schema = "ig_db", name = "ItemType")
public class ItemType implements Serializable {


    private static final long serialVersionUID = 589409200685670093L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ItemTypeId")
	private Long itemTypeId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(Long itemTypeId) {
		this.itemTypeId = itemTypeId;
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
		 { data: "itemTypeId" }               //0 MySql-TableName: ItemType
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
		{ data: "itemTypeId", name: "ItemTypeId" }                         //0 MySql-TableName: ItemType
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


	var postUrl = "/rest/ignite/v1/item-type/new";
	var postData = {
		itemTypeId : $("#xxxItemTypeId").val()               //0 MySql-TableName: ItemType
		,name : $("#xxxName").val()                                                  //1
		,description : $("#xxxDescription").val()                                    //2
		,lastUpdateTimestamp : getMsFromDatePicker("xxxLastUpdateTimestamp")         //3
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: ItemType										   (js3Str)
		$("#xxxItemTypeId").val(data.itemTypeId);                         //0
		$("#xxxName").val(data.name);                                     //1
		$("#xxxDescription").val(data.description);                       //2
		$("#xxxLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //3






/**  HTML  om 'n grid te populate
												<th>ItemTypeId</th>                  <!--0  MySql-TableName: ItemType-->
												<th>Name</th>                        <!--1  Name-->
												<th>Description</th>                 <!--2  Description-->
												<th>Last Update Timestamp</th>       <!--3  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--4  LastUpdateUserName-->

*/