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

@Entity
@Table(schema = "ig_db", name = "Bank")
public class Bank implements Serializable {

	private static final long serialVersionUID = 5267513116980702966L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BankId")
	private Long bankId;

	@Column(name = "BankCode")
	private String bankCode;

	@Column(name = "Name")
	private String name;

	@Column(name = "SwiftNumber")
	private String swiftNumber;

	@Column(name = "UniversalBranchCode")
	private String universalBranchCode;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

/***	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	} ***/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSwiftNumber() {
		return swiftNumber;
	}

	public void setSwiftNumber(String swiftNumber) {
		this.swiftNumber = swiftNumber;
	}

	public String getUniversalBranchCode() {
		return universalBranchCode;
	}

	public void setUniversalBranchCode(String universalBranchCode) {
		this.universalBranchCode = universalBranchCode;
	}

/***	public UniversalBranch getUniversalBranch() {
		return universalBranch;
	}

	public void setUniversalBranch(UniversalBranch universalBranch) {
		this.universalBranch = universalBranch;
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
		 { data: "bankId" }                   //0 MySql-TableName: Bank
		,{ data: "bankId" }                 //1
		,{ data: "name" }                     //2
		,{ data: "swiftNumber" }              //3
		,{ data: "universalBranchCode" }      //4
		,{ data: "lastUpdateTimestamp" }      //5
		,{ data: "lastUpdateUserName" }       //6
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 4, 6]
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
		{ data: "bankId", name: "BankId" }                                 //0 MySql-TableName: Bank
		,{ data: "bankId", name: "BankId" }                             //1
		,{ data: "name", name: "Name" }                                     //2
		,{ data: "swiftNumber", name: "SwiftNumber" }                       //3
		,{ data: "universalBranchCode", name: "UniversalBranchCode" }       //4
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //5
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //6
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 4, 6]
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


	var postUrl = "/rest/ignite/v1/bank/new";
	var postData = {
		bankId : $("#bBankId").val()                   //0 MySql-TableName: Bank
		,bankId : $("#bBankId").val()                                          //1
		,name : $("#bName").val()                                                  //2
		,swiftNumber : $("#bSwiftNumber").val()                                    //3
		,universalBranchCode : $("#bUniversalBranchCode").val()                    //4
		,lastUpdateTimestamp : getMsFromDatePicker("bLastUpdateTimestamp")         //5
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: Bank										   (js3Str)
		$("#bBankId").val(data.bankId);                                 //0
		$("#bBankId").val(data.bankId);                             //1
		populateSelect("bBankId",                                       //name of html select element that will be populated
				"/rest/ignite/v1/bank/find-all",                          //url
				"bankId",                                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.bankId,                                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#bName").val(data.name);                                     //2
		$("#bSwiftNumber").val(data.swiftNumber);                       //3
		$("#bUniversalBranchCode").val(data.universalBranchCode);       //4
		populateSelect("bUniversalBranchCode",                            //name of html select element that will be populated
				"/rest/ignite/v1/universal-branch/find-all",              //url
				"universalBranchCode",                                    //the value that must be saved (ReferencedColumnName)
				"kryDieKolomNaam",                                        //shown to user (usually a Name column)
				data.universalBranchCode,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#bLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //5






/**  HTML  om 'n grid te populate
												<th>BankId</th>                      <!--0  MySql-TableName: Bank-->
												<th>BankId</th>                    <!--1  BankId-->
												<th>Name</th>                        <!--2  Name-->
												<th>Swift Number</th>                <!--3  SwiftNumber-->
												<th>UniversalBranchCode</th>         <!--4  UniversalBranchCode-->
												<th>Last Update Timestamp</th>       <!--5  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--6  LastUpdateUserName-->

*/