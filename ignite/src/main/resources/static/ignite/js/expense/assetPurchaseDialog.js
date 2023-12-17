// assetPurchaseDialog.js //
var pdProjectExpenseFiles = null;
var askToSaveDialog = false;
var	somethingChangedInDialog = false;

var enabled = false;
var disNuweEen = false;

//editAssetProjectExpense -- Start
function editAssetProjectExpense(rowSelector, participantIdPayer, participantPayerSystemName) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)

	$("#peParticipantIdPayer").val(participantIdPayer);
	$("#peParticipantPayerSystemName").val(participantPayerSystemName);
	
	if (rowSelector != null) {
		disNuweEen = false;
		data = axAssetExpenseTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		$("#peProjectId").val(data.projectId);
		$("#peProjectName").val(data.projectName);
		enabled = true;
	} else {
		disNuweEen = true;
		$("#peProjectId").val("");
		$("#peProjectName").val("");	
		data.expenseTypeId = null; 
		data.expenseTypeParentId = null;
		data.expenseTypeId = null;
		enabled = false;
	}

	axAssetExpenseTable.rows().deselect();
	
	
//	populateSelect("purCostExpenseType", //elementId, html select element that will be populated
//	    "/rest/ignite/v1/expense-type/not-allowance", //url, url where it must get the data (you can paste in browser window to see the data)
//	    "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
//	    "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
//	    data.expenseTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
//	    true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
//	    null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
//				
//	);
	
//	populateSelect("ipeExpenseType", //elementId, html select element that will be populated
//	    "/rest/ignite/v1/expense-type/by-parent/" + data.expenseTypeParentId, //url, url where it must get the data (you can paste in browser window to see the data)
//	    "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
//	    "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
//	    data.expenseTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
//	    true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
//	      null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
//	    function() {
//				displayVendorPanelOrNot();
//			}
//	);

	var selectedProjectId = data.projectId//Tells JQuery to fetch the value of the element id called npwBankId from the html page
	if ((selectedProjectId != null) && (selectedProjectId != null)) {  
		// var queryUrl = "/rest/ignite/v1/agreement-between-participants/distinct-recoverable-expense/" + selectedProjectId
		var queryUrl="/rest/ignite/v1/expense-type/not-allowance"
	
		populateSelect("purExpenseType", //elementId, html select element that will be populated
			       queryUrl, //url, url where it must get the data (you can paste in browser window to see the data)
			       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
			       "expenseTypeName", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
			       data.expenseTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
			       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	  	           null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
		);
	
	}

	
	$("#peProjectExpenseId").val(data.projectExpenseId);
	$("#peProjectParticipantId").val(data.projectParticipantIdPayer);
	
	$("#peProjectName").val(data.projectName);

	if (disNuweEen) {
		$("#peParticipantIdMadePurchase").val(participantIdPayer);
		$("#peParticipantMadePurchaseSystemName").val(participantPayerSystemName);	
	} else {
		$("#peParticipantIdMadePurchase").val(data.participantIdMadePurchase);
		$("#peParticipantMadePurchaseSystemName").val(data.participantMadePurchaseSystemName);
	}
	
	$("#peParticipantIdVendor").val(data.participantIdVendor);
	$("#peParticipantVendorSystemName").val(data.participantVendorSystemName);
	if (data.expenseTypeName == null) {
		$("#peExpenseTypeName").val("");
	} else {
		$("#peExpenseTypeName").val(data.expenseTypeName + ", " + data.expenseTypeDescription);
	}
	
	$("#peExpenseTypeId").val(data.expenseTypeId);
	$("#peUnitTypeCode").val(data.unitTypeCode);

	$("#peAssetId").val(data.assetId);
	if ($("#peAssetId").val() != "") {
		$("#peAllowAsset").val("Y");
		$('#pePurchaseDate').prop("disabled", true);   
		$('#peAmountPerUnit').prop("disabled", true);   
	} else {
		$("#peAllowAsset").val("N");
		$('#pePurchaseDate').prop("disabled", false);   
		$('#peAmountPerUnit').prop("disabled", false);  
	}
	
	if (data.unitTypeCode == null) {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Units *");
		$("#peNumberOfUnits").show();
		$("#labelAmountPerUnit").html("Amount per Unit *");
	} else if (data.unitTypeCode == "AMOUNT") {
		$("#labelNumberOfUnits").hide();
		$("#peNumberOfUnits").hide();
		$("#labelAmountPerUnit").html("Amount *");
	} else if (data.unitTypeCode == "PER_DAY") {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Days *");
		$("#peNumberOfUnits").show();
		$("#labelAmountPerUnit").html("Amount per Day *");
	} else if (data.unitTypeCode == "PER_HOUR") {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Hours *");
		$("#peNumberOfUnits").show();
		$("#labelAmountPerUnit").html("Amount per Hour *");
	} else if (data.unitTypeCode == "PER_KM") {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Kms *");
		$("#peNumberOfUnits").show();
		$("#labelAmountPerUnit").html("Amount per Km *");
	} else {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Units *");
		$("#peNumberOfUnits").show();
		$("#labelAmountPerUnit").html("Amount per Unit *")
	}
	
	if (data.assetId == null) {
		$("#peAssetLabel").hide();
		$("#peAssetName").hide();
		$("#assetBino").hide();
	} else {
		$("#peAssetId").val(data.assetId);
		$("#peAssetName").val( (data.assetDescription != null) && (data.assetDescription != "") ? data.assetDescription : data.vehicleName);
		$("#peAssetLabel").show();
		$("#peAssetName").show();
		$("#assetBino").show();
		
	}

	populateSelect("pePaymentMethodCode",
		       "/rest/ignite/v1/payment-method",
		       "paymentMethodCode",
		       "name",
		       data.paymentMethodCode,
		       true,
		       function() {            //completeMethod
					checkPaymentMethodType()
		       }
	);
	
	populateSelect("peBankCardIdUsed",                //name of html select element the will be populated
		       "/rest/ignite/v1/bank-card/participant/" + participantIdPayer,       //url
		       "bankCardId",                                                   //the value that must be saved
		       "description",                      //  "nameOnCard",            //shown to user
		       data.bankCardIdUsed,                                            //The selected one, if there is one
		       true,                                 //addEmpty, boolean: should we add empty object at the top
		       null                                  //completeMethod
	);	
	
	populateSelect("peTaxDeductableCategoryId",
		       "/rest/ignite/v1/tax-deductable-category",
		       "taxDeductableCategoryId",
		       "name",
		       data.taxDeductableCategoryId,
		       true,
		       null
	);		
	
	$("#pePaymentDescription").val(data.paymentDescription);
	
	$("#pePurchaseDate").datepicker("setDate", data.PurchaseDate == null? timestampToString(new Date(), false) : new Date(data.PurchaseDate));
	
	$("#peNumberOfUnits").val(data.numberOfUnits);

	$("#peAmountPerUnit").val((data.amountPerUnit != null) ? "R " + (data.amountPerUnit).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke

	$("#peNoteToAccountant").val(data.noteToAccountant);
	
	// Set the Save Button to disabled
	setElementEnabled("saveAssetPurchaseButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("assetPurchaseDialog");
	
	setElementEnabled("saveAssetPurchaseButton", false);
	setAdditionalTabStates(enabled);
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
	
}// editAssetProjectExpense -- End





function selectProjectForExpense() {
	var participantId = $("#peParticipantIdPayer").val();

	var queryUrl="/rest/ignite/v1/project-participant/list-only-my-projects-all/" + participantId ;

	console.dir(queryUrl);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="projectParticipantId";
			var columns = [
				{ data: "projectParticipantId", name: "ProjectParticipantId" },				// 0
				
				{ data: "projectId", name: "ProjectId" },									//1
				{ data: "participantIdHost",  name: "participantIdHost" },					//2
				{ data: "projectNameText", name: "Project" },								//3 --

				{ data: "participantNameHost", name: "Host Participant" },					//4 --
				{ data: "participantNameLevel1", name: "Top Level Participant" },			//5 --

				{ data: "individualNameProjectAdmin", name: "Contract Manager" },			//6 --
				{ data: "isActive", name: "Is Active" },									//7 --
				{ data: "flagSustenanceProject", name: "Sustenance proj" },					//8 -- 				
				
	
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0,1,2]
				},		
		   		{
		   			render: function(data, type, row) {
		   				a = leadingZeroPad(row.participantIdHost,4);
		   				return a;
		   			},
		   			targets: [1]
		   		},
				{
					render: function(data, type, row) {
						return "<input type='checkbox' readonly onclick='return false;' " + (row.isActive == "Y" ? " checked " : "") + ">";
					},
					className: "dt-center",
					targets: 7
				},
				{
					render: function(data, type, row) {
						return "<input type='checkbox' readonly onclick='return false;' " + (row.flagSustenanceProject == "Y" ? " checked " : "") + ">";
					},
					className: "dt-center",
					targets: 8
				}				
				
		   	];

				selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				
				$("#peProjectParticipantId").val(row.projectParticipantId);

				var projectName = row.projectNameText;
				$("#peProjectName").val(projectName);
				$("#peProjectId").val(row.projectId);
				
				// selectRecoverableExpensesForThisProject();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}


function selectBankCardsAvailable() {
	populateSelect("peBankCardIdUsed",                	//name of html select element the will be populated
		       "/rest/ignite/v1/bank-card/participant/" + participantIdPayer,       //url
		       "bankCardId",                         	//the value that must be saved
		       "description",                      		//  "nameOnCard",            //shown to user
		       null,                                  	//The selected one, if there is one
		       true,                                 	//addEmpty, boolean: should we add empty object at the top
		       null                                  	//completeMethod
	);	
	console.log("/rest/ignite/v1/bank-card/participant/" + participantIdPayer);
	peDialogChanged();
}

function selectBankAccountsAvailable() {
	populateSelect("peBankAccountIdUsed",                	//name of html select element the will be populated
		       "/rest/ignite/v1/participant-bank-details/participant/" + participantIdPayer,       //url
		       "participantBankDetailsId",                         	//the value that must be saved
		       "accountDetails",                      		//  "nameOnCard",            //shown to user
		       null,                                  	//The selected one, if there is one
		       true,                                 	//addEmpty, boolean: should we add empty object at the top
		       null                                  	//completeMethod
	);	
	
	console.log("/rest/ignite/v1/participant-bank-details/participant/" + participantIdPayer);
	peDialogChanged();
}



function editSelectExpenseType() {
	selectExpenseType("peExpenseTypeId", "peExpenseTypeName", "peUnitTypeCode", "peAllowAsset");
	peDialogChanged();
}

function selectExpenseType(targetId, targetName, targetUtCode, targetAllowAsset) {
	
	// var queryUrl="/rest/ignite/v1/expense-type";
	var queryUrl="/rest/ignite/v1/expense-type/not-allowance"
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="expenseTypeId";
			var refColumnName="expenseTypeId";
			var columns = [
				{ data: "expenseTypeId", name: "expenseTypeId" },				// 0
				{ data: "expenseTypeParentId", name: "ParentCode" },			// 1
				{ data: "unitTypeCode", name: "Unit Type Code" },				// 2
				{ data: "expenseTypeId", name: "Expense Type Code" },			// 3
				{ data: "name", name: "Name" },									// 4
				{ data: "description", name: "Description" },					// 5
				{ data: "allowanceFlag", name: "Allowance" },					// 6
				{ data: "allowHandlingPerc", name: "Allow Handling Perc" },		// 7
				{ data: "allowMaxAmtPerUnit", name: "Allow MaxAmt PerUnit" },	// 8
				{ data: "allowAsset", name: "Allow Asset" }						// 9
			];
			
			var columnDefs = [
				{ 
					visible: false,
					targets: [0,1,2,3,5,6,7,8]
					},

         		    {
             			render: function (data, type, row) {
             				if (row.allowanceFlag == "Y") {
             					return "Allowance";
             				} else {
             					return "";
             				}
             			},
             			className: "dt-center",
             			targets: 6
             		},             		{
             			render: function (data, type, row) {
             				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowHandlingPerc == "Y" ? " checked " : "") + ">";
             			},
             			className: "dt-center",
             			targets: 7
             		},		
             		{
             			render: function (data, type, row) {
             				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowMaxAmtPerUnit == "Y" ? " checked " : "") + ">";
             			},
             			className: "dt-center",
             			targets: 8
             		},		
             		{
             			render: function (data, type, row) {
             				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowAsset == "Y" ? " checked " : "") + ">";
             			},
             			className: "dt-center",
             			targets: 9
             		}
			];

			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.expenseTypeId;
				var repName = row.name; //+ ", " + row.description;
				var utCode = row.unitTypeCode;
				var allowAsset = row.allowAsset;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetUtCode).val(utCode);
				$("#" + targetAllowAsset).val(allowAsset);
				
				if (($("#peAllowAsset").val() == "Y") && ($("#peParticipantIdPayer").val() != "")){
//					let aa1 = document.getElementById("peAssetLabel"); aa1.removeAttribute("hidden");
//					let aa2 = document.getElementById("peAssetName"); aa2.removeAttribute("hidden");
//					let aa3 = document.getElementById("assetBino"); aa3.removeAttribute("hidden");
					$("#peAssetLabel").show();
					$("#peAssetName").show();
					$("#assetBino").show();
				} else {
					$("#peAssetName").val("");
					$("#peAssetId").val("");
					$("#peAssetLabel").hide();
					$("#peAssetName").hide();
					$("#assetBino").hide();
				}
				
				if (utCode == "AMOUNT") {
					$("#labelNumberOfUnits").hide();
					$("#peNumberOfUnits").hide();
					$("#peNumberOfUnits").val("1");
					$("#labelAmountPerUnit").html("Amount *");
				} else if (utCode == "PER_DAY") {
					$("#labelNumberOfUnits").show();
					$("#labelNumberOfUnits").html("Number of Days *");
					$("#peNumberOfUnits").show();
					$("#labelAmountPerUnit").html("Amount per Day *");
				} else if (utCode == "PER_HOUR") {
					$("#labelNumberOfUnits").show();
					$("#labelNumberOfUnits").html("Number of Hours *");
					$("#peNumberOfUnits").show();
					$("#labelAmountPerUnit").html("Amount per Hour *");
				} else if (utCode == "PER_KM") {
					$("#labelNumberOfUnits").show();
					$("#labelNumberOfUnits").html("Number of Kms *");
					$("#peNumberOfUnits").show();
					$("#labelAmountPerUnit").html("Amount per Km *");
				} else {
					$("#labelNumberOfUnits").show();
					$("#labelNumberOfUnits").html("Number of Units *");
					$("#peNumberOfUnits").show();
					$("#labelAmountPerUnit").html("Amount per Unit *")
				}				
				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectExpenseType































function selectRecoverableExpensesForThisProject() {
	
	var selectedProjectId = $("#ipeProjectId").val(); //Tells JQuery to fetch the value of the element id called npwBankId from the html page
	var queryUrl = "/rest/ignite/v1/agreement-between-participants/distinct-recoverable-expense/" + selectedProjectId
	$("#ipeExpenseTypeId").val("");
	$("#ipeUnitTypeName").val("");
	$("#ipeUnitTypeCode").val("");

	populateSelect("ipeRecoverableExpenseAgrProject", //elementId, html select element that will be populated
		       queryUrl, //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "expenseTypeName", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
  	           null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	
}

function populateExpenseTypeChildren() {
	var selectedExpenseTypeParent = $("#ipeExpenseTypeParent").val(); //Tells JQuery to fetch the value of the element id called npwBankId from the html page
	$("#ipeExpenseTypeId").val("");
	$("#ipeUnitTypeName").val("");
	$("#ipeUnitTypeCode").val("");
	
	populateSelect("ipeExpenseType", //elementId, html select element that will be populated
		       "/rest/ignite/v1/expense-type/by-parent/" + selectedExpenseTypeParent, //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
  	           null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	
}

function populateExpenseUnitTypeCode() {
	// Haal een rekord van die db

	// Find Unit Type for the selectedExpenseTypeId
	var queryUrl;
	// var selectedExpenseTypeId = $("#ipeExpenseType").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  
	var selectedExpenseTypeId = $("#ipeRecoverableExpenseAgrProject").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  
	queryUrl = "/rest/ignite/v1/expense-type/unit/" + selectedExpenseTypeId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#ipeUnitTypeCode").val(data.unitTypeCode);
			$("#ipeUnitTypeName").val(data.name);
			if (data.unitTypeCode == "AMOUNT") {
				setDivVisibility("ipeAmountPerUnitPanel", "block" );
			} else {  	
				setDivVisibility("ipeAmountPerUnitPanel", "none" );
				$("#ipeAmountPerUnit").val("");
			}
			return data;
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}










//saveAssetPurchase -- Begin
function saveAssetPurchase() {
	
	console.log("saveAssetPurchase");
	
	$("#peAmountPerUnit").val($("#peAmountPerUnit").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	
	var enabled = false;
	
	var postUrl = "/rest/ignite/v1/project-expense/new";
	var postData = {
			 projectExpenseId				: $("#peProjectExpenseId").val(),
			 projectParticipantIdPayer      : $("#peProjectParticipantId").val(),
			 participantIdMadePurchase      : $("#peParticipantIdMadePurchase").val(),
			 participantIdVendor            : $("#peParticipantIdVendor").val(),
			 expenseTypeId                  : $("#peExpenseTypeId").val(),
			 assetId                        : $("#peAssetId").val(),
			 paymentMethodCode              : $("#pePaymentMethodCode").val(),
			 bankCardIdUsed                 : $("#peBankCardIdUsed").val(),
			 participantBankDetailsIdUsed   : $("#peBankAccountIdUsed").val(),
			 taxDeductableCategoryId      : $("#peTaxDeductableCategoryId").val(),
			 paymentDescription             : $("#pePaymentDescription").val(),
			 purchaseDate                   : getMsFromDatePicker("pePurchaseDate"),
			 numberOfUnits                  : $("#peNumberOfUnits").val(),
			 amountPerUnit                  : $("#peAmountPerUnit").val(),

			 noteToAccountant               : $("#peNoteToAccountant").val(),
			 fullyLinked                    : $("#peFullyLinked").is(":checked") ? "Y" : "N",
			 bankReference                  : $("#peBankReference").val()
	};

	var errors = "";


	
	// validate
	if ((postData.projectParticipantIdPayer == null) || (postData.projectParticipantIdPayer == "")) {
		errors += "Select a payer for the purchase.<br>";
	}
	if ((postData.participantIdMadePurchase == null) || (postData.participantIdMadePurchase == "")) {
		errors += "Select a person that made the purchase.<br>";
	}	
	if ((postData.expenseTypeId == null) || (postData.expenseTypeId == "")) {
		errors += "An Expense Type is required.<br>";
	}
	if ((postData.purchaseDate == null) || (postData.purchaseDate == "")) {
		errors += "A Purchase Date is required.<br>";
	}
	if ((postData.numberOfUnits == null) || (postData.numberOfUnits == "")) {
		errors += "The Number of Units is required.<br>";
	}	
	if ((postData.amountPerUnit == null) || (postData.amountPerUnit == "")) {
		errors += "An Amount is required.<br>";
	}	

	if ((postData.paymentMethodCode == null) || (postData.paymentMethodCode == "")) {
		errors += "A Payment Method is required.<br>";
	}		
	if ((postData.paymentDescription == null) || (postData.paymentDescription == "")) {
		errors += "A Payment Description is required.<br>";
	}	
	
	if (postData.paymentMethodCode == "EFT") {
		if ((postData.participantBankDetailsIdUsed == null) || (postData.participantBankDetailsIdUsed == "")) {
			errors += "Please select the Bank Account that was used.<br>";
		}		
	}

	if (postData.paymentMethodCode == "BANK_CARD") {
		if ((postData.bankCardIdUsed == null) || (postData.bankCardIdUsed == "")) {
			errors += "Please select the Bank Card that was used.<br>";
		}			
	}
	
	
	if (showFormErrors("peDlgErrorDiv", errors)) {
		return;
	};

	if (postData.paymentMethodCode == "") { 
		postData.paymentMethodCode = null;
	}
	if (postData.taxDeductableCategoryId == "") { 
		postData.taxDeductableCategoryId = null;
	}
	
	if ((postData.projectExpenseId != null) && (postData.projectExpenseId != "")) {   //update
		var postUrl = "/rest/ignite/v1/project-expense";
		
	}
	
	console.log(postUrl);
	console.log(postData);
	saveEntry(postUrl, postData, null, "The Project Expense has been saved.", axAssetExpenseTable, function(request, response) {
		
		$("#peProjectExpenseId").val(response.projectExpenseId);
		// pexExpenseSummaryTable.ajax.reload();
		setDivVisibility("peDlgErrorDiv", "none");
		setAdditionalTabStates(enabled);

	});

	enabled = true;
	askToSaveDialog = false;
	somethingChangedInDialog = false;
	setElementEnabled("saveAssetPurchaseButton", false);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}// saveAssetPurchase -- End



//closePurchaseDialog -- Start
function closePurchaseDialog() {
	if ((askToSaveDialog == true) || (somethingChangedInDialog == true)) {
		showDialog("Confirm?",
				"You have unsaved changes - are you sure you wish to cancel?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					setDivVisibility("peDlgErrorDiv", "none");
					closeModalDialog("projectExpenseDialog");
				}
		)
	} else {
		setDivVisibility("peDlgErrorDiv", "none");
		closeModalDialog("projectExpenseDialog");
	}
} //closePurchaseDialog -- End


//closeAssetPurchaseDialog -- Start
function closeAssetPurchaseDialog() {
	
	if ((askToSaveDialog == true) || (somethingChangedInDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("apAssetDlgErrorDiv", "none");
				closeModalDialog("assetPurchaseDialog");
			});
	} else {
		setDivVisibility("apAssetDlgErrorDiv", "none");
		closeModalDialog("assetPurchaseDialog");
	};
	somethingChangedInDialog = false;
} //closeAssetPurchaseDialog -- End




function editSelectParticipantMadePurchase() {
	selectParticipantIndividual("peParticipantIdMadePurchase", "peParticipantMadePurchaseSystemName");
	peDialogChanged();
}


function editSelectParticipantVendor() {
	selectParticipant("peParticipantIdVendor", "peParticipantVendorSystemName");
	peDialogChanged();
}




function editEnterAsset() {
	var peAssetId = "peAssetId" //$("#peAssetId").val();
	var peAssetName = "peAssetName" //$("#peAssetName").val();
	selectAsset(peAssetId, peAssetName);
}


//peDialogChanged -- Start
function apeDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveAssetPurchaseButton", true);
}
//peDialogChanged -- End

//function editSelectParticipantForProject()  {
//	
//	selectParticipantForProject("peProjectParticipantIdPayer", "peProjectName", "peParticipantIdPayer");
//	peDialogChanged();
//}

function selectProjectForParticipant(targetId, targetName, targetParticipantId) {
	
	projectId =  $("#peProjectId").val();
	
	var queryUrl="/rest/ignite/v1/project-participant/list/" + projectId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="projectParticipantId";
			var columns = [
				{ data: "projectParticipantId", name: "ProjectParticipantId" },
				{ data: "systemNamePayer", name: "System Name" },
				{ data: "participantIdPayer", name: "ParticipantIdPayer" }
			];
			var columnDefs = [
//				{ 
//					visible: false,
//					targets: [1,3]
//					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectParticipantId;
				var markName = row.systemNamePayer;
				var pId = row.participantIdPayer;

				$("#" + targetId).val(id);
				$("#" + targetName).val(markName);
				$("#" + targetParticipantId).val(pId);
				
				
				if (($("#peAllowAsset").val() == "Y") && ($("#peParticipantIdPayer").val() != "")){
//					let aa1 = document.getElementById("peAssetLabel"); aa1.removeAttribute("hidden");
//					let aa2 = document.getElementById("peAssetName"); aa2.removeAttribute("hidden");
//					let aa3 = document.getElementById("assetBino"); aa3.removeAttribute("hidden");
					$("#peAssetLabel").show();
					$("#peAssetName").show();
					$("#assetBino").show();
				} else {
					$("#peAssetName").val("");
					$("#peAssetId").val("");
					$("#peAssetLabel").hide();
					$("#peAssetName").hide();
					$("#assetBino").hide();
				}				
				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}

function selectParticipantForProject(targetId, targetName, targetParticipantId) {
	
	projectId =  $("#peProjectId").val();
	
	var queryUrl="/rest/ignite/v1/project-participant/list/" + projectId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="projectParticipantId";
			var columns = [
				{ data: "projectParticipantIdContracted", name: "projectParticipantIdContracted" },
				{ data: "participantNameContracted", name: "participantNameContracted" },
				{ data: "participantIdContracted", name: "participantIdContracted" }
			];
			var columnDefs = [
//				{ 
//					visible: false,
//					targets: [1,3]
//					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectParticipantIdContracted;
				var markName = row.participantNameContracted;
				var pId = row.participantIdContracted;

				$("#" + targetId).val(id);
				$("#" + targetName).val(markName);
				$("#" + targetParticipantId).val(pId);
				
				
				if (($("#peAllowAsset").val() == "Y") && ($("#peParticipantIdPayer").val() != "")){
//					let aa1 = document.getElementById("peAssetLabel"); aa1.removeAttribute("hidden");
//					let aa2 = document.getElementById("peAssetName"); aa2.removeAttribute("hidden");
//					let aa3 = document.getElementById("assetBino"); aa3.removeAttribute("hidden");
					$("#peAssetLabel").show();
					$("#peAssetName").show();
					$("#assetBino").show();
				} else {
					$("#peAssetName").val("");
					$("#peAssetId").val("");
					$("#peAssetLabel").hide();
					$("#peAssetName").hide();
					$("#assetBino").hide();
				}				
				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
} //  selectParticipantForProject  --  END





//checkPaymentMethodType -- Start
function checkPaymentMethodType() {
			
	if ($('#pePaymentMethodCode').val() == "BANK_CARD") {
		let element1 = document.getElementById("peBankCardIdUsed"); element1.removeAttribute("hidden");
		let element2 = document.getElementById("peBankCardIdUsedLabel"); element2.removeAttribute("hidden");
		$("#peBankAccountIdUsed")[0].selectedIndex = 0			
		$("#peBankAccountIdUsed").attr("hidden",true);
		$("#peBankAccountIdUsedLabel").attr("hidden",true);
		$("#peBankReference").attr("hidden",true);
		$("#peBankReferenceLabel").attr("hidden",true);
		selectBankCardsAvailable();
	} else if ($('#pePaymentMethodCode').val() == "EFT") {
		let element1 = document.getElementById("peBankAccountIdUsed"); element1.removeAttribute("hidden");
		let element2 = document.getElementById("peBankAccountIdUsedLabel"); element2.removeAttribute("hidden");
		let element3 = document.getElementById("peBankReference"); element3.removeAttribute("hidden");
		let element4 = document.getElementById("peBankReferenceLabel"); element4.removeAttribute("hidden");
		$("#peBankCardIdUsed")[0].selectedIndex = 0			
		$("#peBankCardIdUsed").attr("hidden",true);
		$("#peBankCardIdUsedLabel").attr("hidden",true);
		selectBankAccountsAvailable();		
	} else {
		$("#peBankCardIdUsed")[0].selectedIndex = 0			
		$("#peBankCardIdUsed").attr("hidden",true);
		$("#peBankCardIdUsedLabel").attr("hidden",true);
		$("#peBankAccountIdUsed")[0].selectedIndex = 0			
		$("#peBankAccountIdUsed").attr("hidden",true);
		$("#peBankAccountIdUsedLabel").attr("hidden",true);
		$("#peBankReference").attr("hidden",true);
		$("#peBankReferenceLabel").attr("hidden",true);
	}
}  //checkPaymentMethodType -- End

//function initializeStartDate() {
//	// TODO: we might want to get this from a parameter on the page (could be linked from an invoice, for example)
//	startDate = getPreviousMonday();
//	
//	$("#ppeStartDate").val(dateToString(startDate));
//	
//}




function selectParticipant(targetId, targetName) {
	
	var queryUrl="/rest/ignite/v1/participant/all-in-view";
	
	console.log("In projectExpenseDialog.js  selectParticipant:  targetId:" + targetId + "  targetName:" + targetName + "   queryUrl:" + queryUrl);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="participantId";
			var refColumnName="participantId";
			var columns = [
				{ data: "participantId", name: "ParticipantId" },
				{ data: "systemName", name: "System Name" },
				{ data: "registeredName", name: "Registered Name" },
				{ data: "tradingName", name: "Trading Name" },
				{ data: "isIndividual", name: "Is Individual" },
//				{ data: "isBU", name: "Is BU" },
				{ data: "registrationNumber", name: "Registration Number" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0]
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var repName = row.systemName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				peDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectParticipant




function selectAsset(targetId, targetName) {
	
	var queryUrl="/rest/ignite/v1/asset/all-vasset";
	
	console.log("In projectExpenseDialog.js  selectAsset:  targetId:" + targetId + "  targetName:" + targetName + "   queryUrl:" + queryUrl);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="assetId";
			var refColumnName="assetId";
			var columns = [
				{ data: "assetId", name: "AssetId" },
				{ data: "assetTypeName", name: "Asset Type" },
				{ data: "currentOwner", name: "Current Owner" },
				{ data: "assetNumber", name: "Asset Number" },
				{ data: "description", name: "Description" },
				{ data: "vehicleName", name: "Vehicle" }
			];
			var columnDefs = [
				{ 
					visible: seeFormName,   
					targets: [0]
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.assetId;
				var repName 
				if (row.vehicleName == null) {
					repName = row.description
				} else {
					repName = row.vehicleName
				}
				

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				peDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectAsset





//assetPurchaseDialogChanged -- Start
function assetPurchaseDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveAssetProjectButton", true);
}
//assetPurchaseDialogChanged -- End


//--editAssetProject-- Start
function editAssetProject(theAssetId) {

	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;
	var queryUrl;

	$("#apAssetId").val(theAssetId);
	
	if (($("#apAssetId").val() == null) || ($("#apAssetId").val() == "")){
		disNuweEen = true;
		document.getElementById('apAssetTypeId').disabled = false;
//		let elementAb = document.getElementById("apAssetNumberButton"); elementAb.removeAttribute("hidden");		
	} else {
		disNuweEen = false;
		document.getElementById('apAssetTypeId').disabled = true;
		queryUrl = "/rest/ignite/v1/asset/by-assetId/" + theAssetId;		
	}
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {	
	
			$("#apAssetId").val(data.assetId);        						// 	0
			$("#apAssetTypeId").val(data.assetTypeId);        			// 	1
		
			$("#apAssetConditionId").val(data.assetConditionId);        	// 	2
			$("#apAssetStatusId").val(data.assetStatusId);        		// 	3
		
			$("#apParticipantIdOriginalOwner").val($("#peParticipantIdPayer").val());
			$("#apParticipantIdOriginalOwnerName").val($("#peProjectName").val());
			$("#apParticipantIdCurrentOwner").val($("#peParticipantIdPayer").val());
			$("#apParticipantIdCurrentOwnerName").val($("#peProjectName").val());
		
			getNextAssetProjectNumber();
		
		
			$("#apParticipantIdSponsor").val(data.participantIdSponsor);
			$("#apParticipantIdSponsorName").val(data.sponsor);
		
			$("#apParticipantIdSoldTo").val(data.participantIdSoldTo);
			$("#apParticipantIdSoldToName").val(data.soldTo);
		
			$("#apVehicleId").val(data.vehicleId);        								// 	
			$("#apVehicleIdName").val(data.vehicleName);                       //data.vehicle.name
			$("#apVehicleLicenceNumber").val(data.licenceNumber);     //data.vehicle.licencNumber
		
			$("#apParticipantOfficeIdLocation").val(data.participantOfficeIdLocation);        // 	
			$("#apParticipantOfficeIdLocationName").val(data.participantOfficeName);
		
			$("#apAssetNumber").val(data.assetNumber);        							// 	
			$("#apDescription").val(data.description);        							// 	
			$("#apBrandOrMake").val(data.brandOrMake);        							// 	
			$("#apSerialNumber").val(data.serialNumber);        							// 	
			$("#apParticipantRightOfUse").val(data.participantRightOfUse);        		//	
		
			$("#apPurchaseAmount").val((data.purchaseAmount != null) ? "R " + (data.purchaseAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
		
			$("#apGuaranteePeriodEnd").datepicker("setDate", data.guaranteePeriodEnd == null ? null : new Date(data.guaranteePeriodEnd));
		
			$("#apAssetAquiredDate").datepicker("setDate", data.assetAquiredDate == null ? null : new Date(data.assetAquiredDate));
			$("#apOwnershipToSponsorDate").datepicker("setDate", data.ownershipToSponsorDate == null ? null : new Date(data.ownershipToSponsorDate));
			$("#apAssetRemovedDate").datepicker("setDate", data.assetRemovedDate == null ? null : new Date(data.assetRemovedDate));
		
			$("#apAssetSoldAmount").val((data.assetSoldAmount != null) ? "R " + (data.assetSoldAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke	
		
			/* function populateSelect(
			elementId, html select element that will be populated 
			url, url where it must get the data (you can paste in browser window to see the data)
			idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
			displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
			selectedId, variable of the value that must be selected (null or default when new record)  or current value  
			addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
			completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
			*/
		
		
			if (disNuweEen == true) {
				populateSelect("apAssetTypeId",
					"/rest/ignite/v1/asset-type/other",     //Al die ander, maw NIE Vehicles nie, geen selection
					"assetTypeId",
					"name",
					null,
					true,
					null)
			} else {
				populateSelect("apAssetTypeId",
					"/rest/ignite/v1/asset-type",         //Al die asset-types, select die regte een
					"assetTypeId",
					"name",
					data.assetTypeId,
					true,
					null)
			};
		
			populateSelect("apAssetConditionId",
				"/rest/ignite/v1/asset-condition",
				"assetConditionId",
				"name",
				data.assetConditionId,
				true,
				null
			);
		
			populateSelect("apAssetStatusId",
				"/rest/ignite/v1/asset-status",
				"assetStatusId",
				"name",
				data.assetStatusId,
				true,
				null
			);
		
			populateSelect("vVehicleMake",
				"/rest/ignite/v1/vehicle-make",
				"vehicleMakeId",
				"name",
				data.vehicleMakeId,
				true,
				null
			);

			// Set the Save Button to disabled
			setElementEnabled("saveAssetProjectButton", false);
			somethingChangedInDialog = false;
			askToSaveDialog = false;			
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});			
			
	showModalDialog("assetPurchaseDialog");

}
//editAssetProject -- End



//saveAssetProject -- Begin
function saveAssetProject() {

	$("#apPurchaseAmount").val($("#apPurchaseAmount").val().replace('R', '').replace(/ /g, '')); //remove spaces (thousand separator) and R-symbol
	$("#apAssetSoldAmount").val($("#apAssetSoldAmount").val().replace('R', '').replace(/ /g, '')); //remove spaces (thousand separator) and R-symbol	

	
	if ($("#apAssetId").val() == "") {
		var postUrl = "/rest/ignite/v1/asset/new";
	} else {
		var postUrl = "/rest/ignite/v1/asset";
	}
	
	
	var postData = {
		assetId: $("#apAssetId").val(),
		assetTypeId: $("#apAssetTypeId").val(),
		assetConditionId: $("#apAssetConditionId").val(),
		assetStatusId: $("#apAssetStatusId").val(),
		participantIdOriginalOwner: $("#apParticipantIdOriginalOwner").val(),
		participantIdCurrentOwner: $("#apParticipantIdCurrentOwner").val(),
		participantIdSponsor: $("#apParticipantIdSponsor").val(),
		participantIdSoldTo: $("#apParticipantIdSoldTo").val(),
		vehicleId: $("#apVehicleId").val(),
		participantOfficeIdLocation: $("#apParticipantOfficeIdLocation").val(),
		assetNumber: $("#apAssetNumber").val(),
		description: $("#apDescription").val(),
		brandOrMake: $("#apBrandOrMake").val(),
		serialNumber: $("#apSerialNumber").val(),
		participantRightOfUse: $("#apParticipantRightOfUse").val(),

		purchaseAmount: $("#apPurchaseAmount").val(),
		guaranteePeriodEnd: getMsFromDatePicker("apGuaranteePeriodEnd"),

		assetAquiredDate: getMsFromDatePicker("apAssetAquiredDate"),
		ownershipToSponsorDate: getMsFromDatePicker("apOwnershipToSponsorDate"),      // As jy nie die datePicker hier gebruik nie, add hy 2 ure as hy save na die db.  (MAW hierdie werk nie:      ownershipToSponsorDate: $("#aOwnershipToSponsorDate").val(),)

		assetRemovedDate: getMsFromDatePicker("apAssetRemovedDate"),
		assetSoldAmount: $("#apAssetSoldAmount").val()
	};

	var errors = "";

	// validate

	if ((postData.assetTypeId == null) || (postData.assetTypeId == "")) {
		errors += "An Asset Type is required.<br>";
	}

	if ((postData.assetConditionId == null) || (postData.assetConditionId == "")) {
		errors += "An Asset Condition is required.<br>";
	}

	if ((postData.assetStatusId == null) || (postData.assetStatusId == "")) {
		errors += "An Asset Status is required.<br>";
	}

	if ((postData.participantIdOriginalOwner == null) || (postData.participantIdOriginalOwner == "")) {
		errors += "An Original Owner is required.<br>";
	}

	if ((postData.participantIdCurrentOwner == null) || (postData.participantIdCurrentOwner == "")) {
		errors += "A Current Owner is required.<br>";
	}

	if ((postData.description == null) || (postData.description == "")) {
		errors += "A Description is required.<br>";
	}


	if ((postData.assetAquiredDate == null) || (postData.assetAquiredDate == "")) {
		errors += "An Asset Aquired Date is required.<br>";
	}


	if ((postData.purchaseAmount == null) || (postData.purchaseAmount == "")) {
		errors += "Please enter a valid Purchase Amount.<br>";
	} else {
		if (isNaN(postData.purchaseAmount)) {
			errors += "Please enter a valid Purchase Amount.<br>";
		}
	}

	if ((postData.assetSoldAmount == null) || (postData.assetSoldAmount == "")) {
	} else {
		if (isNaN(postData.assetSoldAmount)) {
			errors += "Please enter a valid Sold Amount.<br>";
		}
	}

	if (showFormErrors("apAssetDlgErrorDiv", errors)) {
		return;
	}

	var theDialog = "assetPurchaseDialog";
	var theSentence = "The Asset has been saved.";


	saveEntry(postUrl, postData, theDialog, theSentence, null, function(request, response) {
		var data = response;
		var assetId = data.assetId;
		var assetDescription = data.description;
		var theAmount = data.purchaseAmount;
		var theDate = data.assetAquiredDate;

		$("#peAssetId").val(assetId);
		$("#peAssetName").val(assetDescription);
		
		$("#peAmountPerUnit").val((theAmount != null) ? "R " + (theAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
		
		$("#pePurchaseDate").datepicker("setDate", new Date(theDate));
		

		$('#pePurchaseDate').prop("disabled", true);   
		$('#peAmountPerUnit').prop("disabled", true);  
		
		setDivVisibility("apAssetDlgErrorDiv", "none");
		closeModalDialog("assetPurchaseDialog");		

	});

}// saveAssetProject -- End


function getNextAssetProjectNumber() {

	var queryUrl;
	var participantId = jQuery('#apParticipantIdCurrentOwner').attr('value');

	queryUrl = "/rest/ignite/v1/asset/next-asset-number/" + participantId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			$("#apAssetNumber").val(data);

			if (($("#apAssetNumber").val() == null) || ($("#apAssetNumber").val() == "")) {
				$("#apAssetNumber").val(1001);
			}

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}; //getNextAssetProjectNumber -- END



function editApSelectParticipantIdOriginalOwner() {
	selectApParticipantId("apParticipantIdOriginalOwner", "apParticipantIdOriginalOwnerName");
	assetPurchaseDialogChanged();
}

function editApSelectParticipantIdCurrentOwner() {
	selectApParticipantId("apParticipantIdCurrentOwner", "apParticipantIdCurrentOwnerName");
	assetPurchaseDialogChanged();
}

function editApSelectParticipantIdSponsor() {
	selectApParticipantId("apParticipantIdSponsor", "apParticipantIdSponsorName");
	assetPurchaseDialogChanged();
}

function editApSelectParticipantOfficeIdLocation() {
	selectApParticipantOfficeIdLocation("apParticipantOfficeIdLocation", "apParticipantOfficeIdLocationName", "aParticipantIdCurrentOwner");
	assetPurchaseDialogChanged();
}

function editApSelectParticipantIdSoldTo() {
	selectApParticipantId("apParticipantIdSoldTo", "apParticipantIdSoldToName");
	assetPurchaseDialogChanged();
}



function selectApParticipantId(targetId, targetName) {
	var queryUrl = "/rest/ignite/v1/participant/all-in-view";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "systemName";
			var columns = [
				{ data: "participantId", name: "participantId" },
				{ data: "systemName", name: "System Name" },
				{ data: "registeredName", name: "Registered Name" },
				{ data: "tradingName", name: "Trading Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: 0
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var repName = row.systemName;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetName).trigger("change");
			}); 
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
} //selectParticipantId -- END



function selectParticipantIndividual(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="participantId";
			var refColumnName="participantId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" },
				{ data: "participantId", name: "participantId" }
				
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;  
				var markName = row.firstName + " " + row.lastName;
				$("#" + targetId).val(id);
				$("#" + targetName).val(markName);				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
}  // selectParticipantIndividual  --  End

function populateCardTypeDialogFields(data) {
	$("#cDlgCardTypeCode").val(data.cardTypeCode);
	$("#cDlgName").val(data.name);
	$("#cDlgDescription").val(data.description);
	
	// Disable the code if update, enable if insert
	$("#cDlgCardTypeCode").prop("readonly", (data.cardTypeCode != null) &&(data.cardTypeCode != ""));	
}

function showCardTypeDialog(data, saveCallbackMethod) {
	if (saveCallbackMethod === undefined) {
		saveCallbackMethod = null;
	}
	
	populateCardTypeDialogFields(data);
	showModalDialog("cardTypeDialog", saveCallbackMethod);
}

function saveCardType(tableId) {
	var postUrl = "/rest/ignite/v1/card-type/new";
	var mode = $("#cDlgMode").val();
	var cardTypeCode = $("#cDlgCardTypeCode").val().trim().toUpperCase();
	var name = $("#cDlgName").val();
	var description = $("#cDlgDescription").val();
	var errors = "";
	var table = null;
	
	if (tableId != null) {
		// does it exist
		if ($("#" + tableId).length) {
			// it exists...
			table = $("#" + tableId).DataTable();
		}
	}
	
	var postData = {
		mode: mode,
		cardTypeCode : cardTypeCode,
		name : name,
		description: description	
	}
	
	if ((postData.cardTypeCode == null) || (postData.cardTypeCode == "")) {
		errors += "A Card Type Code is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}
	// Is the code readonly?  If it is, then the record already exists
	if ($("#cDlgCardTypeCode").is("[readonly]")) {
		//This is an update
		postUrl = "/rest/ignite/v1/card-type";
	}
	
	// this fails because cardTypeTable is not known
	saveEntry(postUrl, postData, "cardTypeDialog", "The Card Type has been saved.", table);
}

//
////getExpenseTypeId -- Start
//function getExpenseTypeId(rowSelector) {
//
//var dateFromPE = new Date(getMsFromDatePicker("ppeStartDate"));
//var dateToPE = new Date(getMsFromDatePicker("ppeEndDate"));	
//
//if (rowSelector != null) {
//	$("#peExpenseTypeIdtemp").val(rowSelector.expenseTypeId);
//	initializeParticipantProjectExpenseTable(dateFromPE, dateToPE, rowSelector.expenseTypeId)
//} else {
//	initializeParticipantProjectExpenseTable(dateFromPE, dateToPE, null)
//}
////resourceRemunerationTable.rows().deselect();
//
//}
////getExpenseTypeId -- End


function showPurchaseUploadDialog() {
	// Set flags to indicate Purchase documents are uploaded
	$("#fileDlgUploadType").val("projectExpense");
	$("#fileDlgPrimaryKey").val("projectExpenseId");
	$("#fileDlgPrimaryKeyValue").val($("#peProjectExpenseId").val());
	$("#sDlgUploadFileInput").val("")
	$("#fileDlgFileDescription").val("")
	showModalDialog("fileUploadDialog");
};

//  	          

function initializeAssetFiles() {
//	setElementEnabled("promptDeleteFileBtn", false);
	var projectExpenseId = $("#peProjectExpenseId").val();
	var queryUrl = "/rest/ignite/v1/project-expense/files/" + projectExpenseId;
	console.log(queryUrl);
	
	var columnArray = [
		{ data: "projectExpenseFileId" },
		{ data: "projectExpenseId" },
		{ data: "fileName" },
		{ data: "originalFileName" },
		{ data: "fileType" },
		{ data: "fileSize" },
		{ data: "uploadDate" },
		{ data: "description" },
		{ data: "lastUpdateUserName" },
		{ data: "lastUpdateTimestamp" }
	];

	var columnDefsArray = [
		
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = timestampToString(data, false);
				}
				return data;
			},
			targets: [6, 9]
		},
		{
			visible: false,
			targets: [0, 1, 2, 3, 5, 8, 9]
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					if (row.fileType == "Directory") {
					data = "<a href='#' onclick='changeDirectory(\"" + row.id + "\")'>" +
					"<i class='document-manager fas fa-folder' style='color: #f5c400'></i> " + row.name +
					"</a>";
					} else {
						// set as default file
						data = "<i class='document-manager far fa-file'></i> " + row.name;
						// depending on what we have select an icon
						if ((row.fileType != null) && (row.fileType != "")) {
							if (row.fileType.indexOf(".pdf") > -1) { data = "<i class='document-manager far fa-file-pdf' title='PDF File'></i> "; }
							if (row.fileType.indexOf("Powerpoint") > -1) { data = "<i class='document-manager far fa-file-powerpoint'></i> " + row.name; }
							if (row.fileType.indexOf("Word") > -1) { data = "<i class='document-manager far fa-file-word'></i> " + row.name; }
							if (row.fileType.indexOf(".xlsx") > -1) { data = "<i class='document-manager far fa-file-excel' title='xlsx'></i> "; }
							if (row.fileType.indexOf(".csv") > -1) { data = "<i class='document-manager fas fa-file-excel' title='.csv'></i>"; }
							if (row.fileType.indexOf(".jpg") > -1) { data = "<i class='document-manager far fa-file-image' title='.jpg'></i> "; }
							if (row.fileType.indexOf(".png") > -1) { data = "<i class='document-manager far fa-file-image' title='.png'></i> "; }
							if (row.fileType.indexOf(".txt") > -1) { data = "<i class='document-manager far fa-file-alt' title='.txt'></i> "; }
							if (row.fileType.indexOf("Compressed") > -1) { data = "<i class='document-manager far fa-file-archive'></i> " + row.name; }
							// there are a lot of other types, incl. contract, invoice, etc....
							// https://fontawesome.com/icons?d=gallery&q=file%20&s=solid&m=free
							// https://fontawesome.com/icons?d=gallery&q=file&s=regular&m=free
						}
					}
				}
				return data;
				},
			targets: 4
		}

		/*{
			render: function(data, type, row) {
				if (type == "display") {
					data = shortenText(data, 25);
				}
				return data;
			},
			targets: 2
		}*/
	];

	var buttonsArray = [
		{
			titleAttr: "Upload",
			text: "<i class='fas fa-folder-open'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showPurchaseUploadDialog();
			}
		},
		{
			attr: {
				id: "promptDeleteFileBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteFile();
			}
		}
	];

	pdProjectExpenseFiles = initializeGenericTable("pdProjectExpenseFiles",
		queryUrl,
		columnArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			// no dbl click at this point
		},
		null,
		30
	);
	
	pdProjectExpenseFiles.off('deselect');
	pdProjectExpenseFiles.on('deselect', function() {
		
		hideDocumentDisplayer();
		resetPreview();
		updateProjectExpenseFileToolbar();
	});

	pdProjectExpenseFiles.off('select');
	pdProjectExpenseFiles.on('select', function(e, dt, node, config) {
		
		showDocumentDisplayer();
		previewFile(dt.data());
		updateProjectExpenseFileToolbar();
	});
	
	hideDocumentDisplayer();
	updateProjectExpenseFileToolbar();
};

function showDocumentDisplayer() {
	setDivVisibility("emptyDocumentDisplayer", "none");
	setDivVisibility("documentDisplayer", "block");
};

function hideDocumentDisplayer() {
	setDivVisibility("emptyDocumentDisplayer", "block");
	setDivVisibility("documentDisplayer", "none");
};

function updateProjectExpenseFileToolbar() {
	var hasSelected = pdProjectExpenseFiles.rows('.selected').data().length > 0;
	
	setTableButtonState(pdProjectExpenseFiles, "promptDeleteFileBtn", hasSelected);
};

function promptDeleteFile() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected file?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteFile(pdProjectExpenseFiles);
			   }
	);
};

function deleteFile(tbl) {
	var postUrl = "/rest/ignite/v1/project-expense/file/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The file has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateProjectExpenseFileToolbar();
			});
};

function previewFile(row) {
	if (row === undefined) {
		return;
	}
	var fileName = row.fileName;
	var fileType = row.fileType;
	var originalFileName = row.originalFileName;
	var projectExpenseFileId = row.projectExpenseFileId;
	
	var url = springUrl("/getfile?t=ProjectExpenseFile&id=" + projectExpenseFileId);
	console.log("/getfile?t=ProjectExpenseFile&id=" + projectExpenseFileId);
	$("#displayerFrame").attr("src", url);
	$("#displayerFrame").attr("title", fileType);
	$("#displayerFrame").attr("style", "border-color: #dae2e7c4; border-style: solid"); 
};

function resetPreview() {
	$("#displayerFrame").attr("src", "");
};

function initializeAsset() {
	$("#assetPurchaseDialog").on('show.bs.modal', function () {
    	//nothing
    	
  	});
  	
  	initializeFileUploadDialogSubmitHandler(
		  function() {
			// success
	        uploadFile(pdProjectExpenseFiles);
		},
		function(errorMessage) {
			// failure
			showDialog("Error", errorMessage);
		}
	  );
	    
  	
  	setActiveTab("generalTabLink");
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		
		var target = $(event.target);
		var activeTab = target.text();

		if (activeTab == "Attachments") {
			initializeAssetFiles();
		}
	});
};

function setAdditionalTabStates(enabled) {

	setDivVisibility("attachmentsTabLink", enabled ? "block" : "none");

	if (enabled) {
		setActiveTab("attachmentsTabLink");
	}
}

//***********************************************************************

document.addEventListener("DOMContentLoaded", function(event) { 
	initializeProjectExpense();
});

//$(document).ready(function() {
	// Any initialization
//	initializeProjectExpense();
//});
