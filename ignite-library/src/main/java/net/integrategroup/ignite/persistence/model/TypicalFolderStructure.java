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
/** ******* ********* ** 2023-11-01 10:26:07 ******** ** **/

@Entity
@Table(schema = "ig_db", name = "TypicalFolderStructure")
public class TypicalFolderStructure implements Serializable {


    private static final long serialVersionUID = 784144813152353189L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TypicalFolderStructureId")
	private Long typicalFolderStructureId;

	@Column(name = "ServiceDisciplineIdIndustry")
	private Long serviceDisciplineIdIndustry;

/***	@OneToOne(targetEntity = ServiceDiscipline.class)
	@JoinColumn(name = "ServiceDisciplineIdIndustry", referencedColumnName = "ServiceDisciplineId", nullable = true, insertable = false, updatable = false)
	private ServiceDiscipline serviceDisciplineIndustry; ***/

	@Column(name = "TypicalFolderStructureIdParent")
	private Long typicalFolderStructureIdParent;

/***	@OneToOne(targetEntity = TypicalFolderStructure.class)
	@JoinColumn(name = "TypicalFolderStructureIdParent", referencedColumnName = "TypicalFolderStructureId", nullable = true, insertable = false, updatable = false)
	private TypicalFolderStructure typicalFolderStructureParent; ***/

	@Column(name = "OrderNumber")
	private Long orderNumber;

	@Column(name = "PerSdFlag")
	private String perSdFlag;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getTypicalFolderStructureId() {
		return typicalFolderStructureId;
	}

	public void setTypicalFolderStructureId(Long typicalFolderStructureId) {
		this.typicalFolderStructureId = typicalFolderStructureId;
	}

	public Long getServiceDisciplineIdIndustry() {
		return serviceDisciplineIdIndustry;
	}

	public void setServiceDisciplineIdIndustry(Long serviceDisciplineIdIndustry) {
		this.serviceDisciplineIdIndustry = serviceDisciplineIdIndustry;
	}

/***	public ServiceDiscipline getServiceDisciplineIndustry() {
		return serviceDisciplineIndustry;
	}

	public void setServiceDisciplineIndustry(ServiceDiscipline serviceDisciplineIndustry) {
		this.serviceDisciplineIndustry = serviceDisciplineIndustry;
	} ***/

	public Long getTypicalFolderStructureIdParent() {
		return typicalFolderStructureIdParent;
	}

	public void setTypicalFolderStructureIdParent(Long typicalFolderStructureIdParent) {
		this.typicalFolderStructureIdParent = typicalFolderStructureIdParent;
	}

/***	public TypicalFolderStructure getTypicalFolderStructureParent() {
		return typicalFolderStructureParent;
	}

	public void setTypicalFolderStructureParent(TypicalFolderStructure typicalFolderStructureParent) {
		this.typicalFolderStructureParent = typicalFolderStructureParent;
	} ***/

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPerSdFlag() {
		return perSdFlag;
	}

	public void setPerSdFlag(String perSdFlag) {
		this.perSdFlag = perSdFlag;
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
		 { data: "typicalFolderStructureId" } //0 MySql-TableName: TypicalFolderStructure
		,{ data: "serviceDisciplineIdIndustry" } //1
		,{ data: "typicalFolderStructureIdParent" } //2
		,{ data: "orderNumber" }              //3
		,{ data: "perSdFlag" }                //4
		,{ data: "name" }                     //5
		,{ data: "description" }              //6
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
			targets: [7]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.perSdFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[4]
		}

	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "typicalFolderStructureId", name: "TypicalFolderStructureId" } //0 MySql-TableName: TypicalFolderStructure
		,{ data: "serviceDisciplineIdIndustry", name: "ServiceDisciplineIdIndustry" } //1
		,{ data: "typicalFolderStructureIdParent", name: "TypicalFolderStructureIdParent" } //2
		,{ data: "orderNumber", name: "OrderNumber" }                       //3
		,{ data: "perSdFlag", name: "PerSdFlag" }                           //4
		,{ data: "name", name: "Name" }                                     //5
		,{ data: "description", name: "Description" }                       //6
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
			targets: [7]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.perSdFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[4]
		}


	];







Vir 'n Save Data function                        (js2Str)


	var postUrl = "/rest/ignite/v1/typical-folder-structure/new";
	var postData = {
		typicalFolderStructureId : $("#TypicalFolderStructureId").val() //0 MySql-TableName: TypicalFolderStructure
		,serviceDisciplineIdIndustry : $("#ServiceDisciplineIdIndustry").val()    //1
		,typicalFolderStructureIdParent : $("#TypicalFolderStructureIdParent").val() //2
		,orderNumber : $("#OrderNumber").val()                                    //3
		,perSdFlag : $("#PerSdFlag").val()                                        //4    use one of these
		,perSdFlag : $("#PerSdFlag").is(":checked") ? "Y" : "N"                 //4    use one of these
		,name : $("#Name").val()                                                  //5
		,description : $("#Description").val()                                    //6
		,lastUpdateTimestamp : getMsFromDatePicker("LastUpdateTimestamp")         //7
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: TypicalFolderStructure										   (js3Str)
		$("#TypicalFolderStructureId").val(data.typicalFolderStructureId); //0
		$("#ServiceDisciplineIdIndustry").val(data.serviceDisciplineIdIndustry); //1
		populateSelect("ServiceDisciplineIdIndustry",                     //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",            //url
				"serviceDisciplineId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineIdIndustry,                         //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#TypicalFolderStructureIdParent").val(data.typicalFolderStructureIdParent); //2
		populateSelect("TypicalFolderStructureIdParent",                  //name of html select element that will be populated
				"/rest/ignite/v1/typical-folder-structure/find-all",      //url
				"typicalFolderStructureId",                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.typicalFolderStructureIdParent,                      //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#OrderNumber").val(data.orderNumber);                       //3
		$("#PerSdFlag").val(data.perSdFlag);                           //4    use one of these
		$("#PerSdFlag").prop("checked", data.perSdFlag == "Y");        //4    use one of these
		$("#Name").val(data.name);                                     //5
		$("#Description").val(data.description);                       //6
		$("#LastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //7






/**  HTML  om 'n grid te populate
												<th>TypicalFolderStructureId</th>    <!--0  MySql-TableName: TypicalFolderStructure-->
												<th>ServiceDisciplineIdIndustry</th> <!--1  ServiceDisciplineIdIndustry-->
												<th>TypicalFolderStructureIdParent</th> <!--2  TypicalFolderStructureIdParent-->
												<th>Order Number</th>                <!--3  OrderNumber-->
												<th>Per Sd</th>                      <!--4  PerSdFlag-->
												<th>Name</th>                        <!--5  Name-->
												<th>Description</th>                 <!--6  Description-->
												<th>Last Update Timestamp</th>       <!--7  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--8  LastUpdateUserName-->

*/