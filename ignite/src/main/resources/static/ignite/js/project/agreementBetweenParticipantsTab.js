var agreementTable = null;
var recoverableExpenseTable = null;

//*********************************************************

function initializeAgreementsTab(projectId) {

	var queryUrl="/rest/ignite/v1/project-participant/relationships/" + projectId;
	
	var columnsArray = [
	                    
		{ data: "level" },								// 0
		{ data: "projectParticipantIdContracted" },		// 1
		{ data: "participantIdContracting" },			// 2
		{ data: "participantNameContracting" },					// 3
		{ data: "participantIdContracted" },			// 4
		{ data: "participantNameContracted" },				// 5
		{ data: "" },					// 6
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [1,2,3,4,5]
		},
		{
			render: function(data, type, row) {
				payer = "";
				ben = "";
				relationship = "";
				if (row.hasOwnProperty("participantNameContracting")) {
					payer = row.participantNameContracting;
					if (payer != null) {
						payer = row.participantNameContracting + " - ";
						if (row.hasOwnProperty("participantNameContracted")) {
							ben = row.participantNameContracted;
							if (ben != null) {
								relationship = payer + ben;
							}
						}
					}
				}
				return relationship;
			},
			targets: 6
		},
		{
			width: "10%",
			targets: [0]
		}		
	];
	

	var buttonsArray = [];
	
	projectRelationshipTable = initializeGenericTable("projectRelationshipTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										34,
										[0,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	projectRelationshipTable.off('deselect')
	projectRelationshipTable.on('deselect', function (e, dt, type, indexes) {
		ShowRemunerationModelsEmptyPanel();
	} );

	projectRelationshipTable.off('select')
	projectRelationshipTable.on('select', function (e, dt, type, indexes) {
		initializeAgreementsTable(dt.data());
	} );
	
}


function ShowRemunerationModelsEmptyPanel() {

	setDivVisibility("remunerationModelsEmptyPanel", "block");
	setDivVisibility("remunerationModelsPanel", "none");
	showEmptyRecoverableExpensePanel();

}






//Agreement between Participants Tab -- Start

function initializeAgreementsTable(relationshipRow) {


	if (relationshipRow == null) {
		return;
	}
	
	var projectParticipantId = relationshipRow.projectParticipantIdContracted;

	setDivVisibility("remunerationModelsEmptyPanel", "none");
	setDivVisibility("remunerationModelsPanel", "block");
	showEmptyRecoverableExpensePanel();

	var queryUrl="/rest/ignite/v1/agreement-between-participants/project-participant/" + projectParticipantId;
	
	var columnsArray = [

		{ data: "agreementBetweenParticipantsId" },		// 0
		{ data: "projectParticipantId" },				// 1
		{ data: "participantIdPayer" },					// 2
		{ data: "systemNamePayer" },					// 3
		{ data: "participantIdBeneficiary" },			// 4
		{ data: "systemNameBeneficiary" },				// 5
		{ data: "" },									// 6
		{ data: "remunerationModelCode"},				// 7
		{ data: "remunerationModelName" },				// 8
		{ data: "description"},		// 9
		{ data: "description"},							// 10
		{ data: "projectId" },							// 11
		{ data: "fsPreSplitContractingExpDeduct" },		// 12
		{ data: "fsPreSplitContractingThirdPDeduct" },	// 13
		{ data: "fsPreSplitOtherPartInvoices" },		// 14
		{ data: "fsContractedExpensesAdded" },			// 15
		{ data: "level" },								// 16
		{ data: "agreementBudget" }						// 17
		
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,3,4,5,7,9,10,11,12,13,14,15,16,17]
		},
   		{
   			render: function(data, type, row) {
   				var a = row.systemNamePayer + " - " + row.systemNameBeneficiary;
   				return a;
   			},
   			targets: 6
   		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				
				return html;
			},
			targets: [17]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editAgreement(relationshipRow, null);
			}
		},
		{
			attr: {
				id: "promptDeleteAgreementBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteAgreement();
			}
		}
		
	];
	
	agreementTable = initializeGenericTable("agreementTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editAgreement(relationshipRow, rowSelector);  //Double click
										},
										null,
										25,
										[16,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	agreementTable.off('deselect')
	agreementTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyRecoverableExpensePanel();
		updateAgreementToolbarButtons();
	} );

	agreementTable.off('select')
	agreementTable.on('select', function (e, dt, type, indexes) {
		initializeAgreementDetailsPanel(dt.data());
		updateAgreementToolbarButtons();
	} );
	
	updateAgreementToolbarButtons();
}

function showEmptyRecoverableExpensePanel() {

	setDivVisibility("recoverableExpenseEmptyPanel", "block");
	setDivVisibility("recoverableExpensePanel", "none");

}

function updateAgreementToolbarButtons() {
	var hasSelected = agreementTable.rows('.selected').data().length > 0;

	setTableButtonState(agreementTable, "promptDeleteAgreementBtn", hasSelected);	
}
	
function promptDeleteAgreement() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Agreement between Participants?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteAgreement(agreementTable);
			   }
	);
}

function deleteAgreement(tbl) {
	var postUrl = "/rest/ignite/v1/agreement-between-participants/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Agreement Between Participants has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateAgreementToolbarButtons();
			}
	);
}

//*********************************************************
// Expense Agreement between Participants Table -- Start
function initializeAgreementDetailsPanel(agreementRow) {

	if (agreementRow == null) {
		return;
	}


	var remunerationModelCode = agreementRow.remunerationModelCode;

	if (remunerationModelCode == "RECOVERABLE_EXPENSE"){
		$("#abpAgreementDetailsHeader").html("Recoverable Expense Agreements");
		initializeRecoverableExpenseTable(agreementRow)
	} else {
		showEmptyRecoverableExpensePanel();
	}
}	


function initializeRecoverableExpenseTable(agreementRow) {
	if (agreementRow == null) {
		return;
	}

	var agreementBetweenParticipantsId = agreementRow.agreementBetweenParticipantsId;
	var queryUrl="/rest/ignite/v1/recoverable-expense/" + agreementBetweenParticipantsId;
	
	setDivVisibility("recoverableExpenseEmptyPanel", "none");
	setDivVisibility("recoverableExpensePanel", "block");

	
	var columnsArray = [
		{ data: "recoverableExpenseId"},			// 0
		{ data: "agreementBetweenParticipantsId"},	// 1
		{ data: "expenseTypeParentName"},			// 2
		{ data: "expenseTypeId"},					// 3
		{ data: "expenseTypeName"},					// 4
		{ data: "expenseAgreementDescription"},		// 5
		{ data: "expenseBudget"},					// 6
		{ data: "systemNamePayer"},					// 7
		{ data: "systemNameBeneficiary"},			// 8
		{ data: "agreementDescription"},			// 9
		{ data: "expenseTypeParentId"},			// 10
		{ data: "expenseTypeUnitCode"},				// 11
		{ data: "expenseTypeUnitName"},				// 12
		{ data: "expenseTypeId"}					// 13
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,3,5,6,7,8,9,10,11,12,13]
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				
				return html;
			},
			targets: [6]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editRecoverableExpense(agreementRow,null);
			}
		},
		{
			attr: {
				id: "promptDeleteRecoverableExpenseBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteRecoverableExpense();
			}
		}
	];
	
	recoverableExpenseTable = initializeGenericTable("recoverableExpenseTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editRecoverableExpense(agreementRow,rowSelector);  //Double click
										}
	);

	recoverableExpenseTable.off('deselect')
	recoverableExpenseTable.on('deselect', function (e, dt, type, indexes) {
		updateRecoverableExpenseToolbarButtons();
	} );

	recoverableExpenseTable.off('select')
	recoverableExpenseTable.on('select', function (e, dt, type, indexes) {
		updateRecoverableExpenseToolbarButtons();
	} );
	
	updateRecoverableExpenseToolbarButtons();
}

		////editRecoverableExpense -- Start
		//function editRecoverableExpense (agreementRow, recoverableExpenseRow) {
		//
		//	var data = {}; 
		//	var errors = "";
		//
		//	if (recoverableExpenseRow != null) {
		//		data = recoverableExpenseTable.row(recoverableExpenseRow).data();
		//		//selectedExpenseTypeId = data.expenseTypeId;
		//	} else {
		////		selectedParentCode = null;
		////		selectedExpenseTypeId = null;
		//	}
		//
		//	recoverableExpenseTable.rows().deselect();
		//	
		//	populateSelect("eaExpenseTypeParent", //elementId, html select element that will be populated
		//		       "/rest/ignite/v1/expense-type-parent", //url, url where it must get the data (you can paste in browser window to see the data)
		//		       "expenseTypeParentId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		//		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		//		       data.expenseTypeParentId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		//		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		//		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
		//					
		//	);
		//	
		//	populateSelect("eaExpenseType", //elementId, html select element that will be populated
		//		       "/rest/ignite/v1/expense-type/by-parent/" + data.expenseTypeParentId, //url, url where it must get the data (you can paste in browser window to see the data)
		//		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		//		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		//		       data.expenseTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		//		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		//		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
		//	);
		//	
		//	$("#eaPayerName").val(agreementRow.systemNamePayer);
		//	$("#eaBeneficiaryName").val(agreementRow.systemNameBeneficiary);
		//	$("#eaAgreementDescription").val(agreementRow.description);
		//	$("#eaAgreementBetweenParticipantsId").val(agreementRow.agreementBetweenParticipantsId);
		//	
		//
		//	$("#eaRecoverableExpenseId").val(data.expenseAgreementDescription);
		//
		//	$("#eaDescription").val(data.expenseAgreementDescription);
		//	$("#eaExpenseBudget").val(data.expenseBudget);
		//	$("#eaRecoverableExpenseId").val(data.recoverableExpenseId);
		//	
		//	showModalDialog("recoverableExpenseDialog");
		//}
		////editRecoverableExpense -- End
		//
		//function populateExpenseTypeChildren() {
		//	var selectedExpenseTypeParent = $("#eaExpenseTypeParent").val(); //Tells JQuery to fetch the value of the element id called npwBankId from the html page  
		//
		//	populateSelect("eaExpenseType", //elementId, html select element that will be populated
		//		       "/rest/ignite/v1/expense-type/by-parent/" + selectedExpenseTypeParent, //url, url where it must get the data (you can paste in browser window to see the data)
		//		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		//		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		//		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		//		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		//		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
		//	);
		//}
		//
			//function populateExpenseUnitTypeAndHandling() {
			//	var selectedExpenseTypeId = $("#eaExpenseType").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  
			//	
			//	var queryUrl;
			//	queryUrl = "/rest/ignite/v1/expense-type/unit/" + selectedExpenseTypeId;
			//	
			//	$.ajax({
			//		url: springUrl(queryUrl),
			//		type: "GET",
			//		success: function(data) {
			//			$("#eaUnitTypeName").val(data.name);
			//			return data;
			//		},
			//		error: function(jqXHR, textStatus, errorThrown) {
			//			ajaxError(jqXHR, textStatus, errorThrown);
			//		},
			//		complete: function(html) {
			//			ajaxSuccess(html.status);
			//		}  
			//	});
			//
			//	var queryUrl;
			//	queryUrl = "/rest/ignite/v1/expense-type/" + selectedExpenseTypeId;
			//	
			//	$.ajax({
			//		url: springUrl(queryUrl),
			//		type: "GET",
			//		success: function(data) {
			//			if ((data.allowHandlingPerc)=="Y") {
			//				setElementEnabled("eaPercHandlingCharge", true);
			//				$("#eaAllowHandlingPerc").val("Yes");
			//			} else {
			//				$("#eaPercHandlingCharge").val("");
			//				setElementEnabled("eaPercHandlingCharge", false);
			//				$("#eaAllowHandlingPerc").val("No");
			//			}
			//			return data;
			//		},
			//		error: function(jqXHR, textStatus, errorThrown) {
			//			ajaxError(jqXHR, textStatus, errorThrown);
			//		},
			//		complete: function(html) {
			//			ajaxSuccess(html.status);
			//		}  
			//	});
			//
			//}
			//
			////saveRecoverableExpense -- Begin
			//function saveRecoverableExpense() {
			//	var postUrl = "/rest/ignite/v1/recoverable-expense/new";
			//
			//	var postData = {
			//			recoverableExpenseId: $("#eaRecoverableExpenseId").val() == ""? null: $("#eaRecoverableExpenseId").val(), 
			//			agreementBetweenParticipantsId: $("#eaAgreementBetweenParticipantsId").val(), 
			//			expenseTypeId: $("#eaExpenseType").val(),
			//			description: $("#eaDescription").val(),
			//			expenseBudget: $("#eaExpenseBudget").val()
			//	};
			//	
			//	var errors = "";
			//
			//	// validate
			//	if (postData.expenseTypeId == "") {
			//		errors += "An Expense Type is required<br>";
			//	}
			//	
			//	if ((postData.expenseBudget != "") && (postData.expenseBudget != null)){
			//		if (isNaN(postData.expenseBudget)) {
			//			errors += "The Expense Agreement Budget has to be numeric<br>";
			//		}
			//	}
			//
			//	
			//	if (showFormErrors("eaDlgErrorDiv", errors)) {
			//		return;
			//	}
			//	
			//	if (postData.recoverableExpenseId != null) {
			//		var postUrl = "/rest/ignite/v1/recoverable-expense";
			//	}
			//
			//	
			//	saveEntry(postUrl, postData, "recoverableExpenseDialog", "The Expense Agreement has been saved.", recoverableExpenseTable);
			//}
			////saveRecoverableExpense -- End

function updateRecoverableExpenseToolbarButtons() {
	var hasSelected = recoverableExpenseTable.rows('.selected').data().length > 0;

	setTableButtonState(recoverableExpenseTable, "promptDeleteRecoverableExpenseBtn", hasSelected);	
}
	
function promptDeleteRecoverableExpense() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Expense Agreement?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRecoverableExpense(recoverableExpenseTable);
			   }
	);
}

function deleteRecoverableExpense(tbl) {
	var postUrl = "/rest/ignite/v1/recoverable-expense/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Expense Agreement has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateRecoverableExpenseToolbarButtons();
			}
	);
}

// Expense Agreements Table -- End
//*********************************************************


//editAgreement -- Start
function editAgreement (relationshipRow, rowSelector) {

	var data = {}; 
	
	if (relationshipRow != null) {
		$("#aRelationshipName").val(relationshipRow.participantNameContracting + " (Payer) - " + relationshipRow.participantNameContracted + " (Beneficiary)");
		$("#aProjectParticipantId").val(relationshipRow.projectParticipantIdContracted);
	} else {
		return;
	}

	if (rowSelector != null) {
		data = agreementTable.row(rowSelector).data();
	} else {
		$("#aRemunerationModel").empty();
	}

	$("#aAgreementBetweenParticipantsId").val(data.agreementBetweenParticipantsId);
	$("#fsPreSplitContractingExpDeduct").prop("checked", data.fsPreSplitContractingExpDeduct == "Y");
	$("#fsPreSplitContractingThirdPDeduct").prop("checked", data.fsPreSplitContractingThirdPDeduct == "Y");
	$("#fsPreSplitOtherPartInvoices").prop("checked", data.fsPreSplitOtherPartInvoices == "Y");
	$("#fsContractedExpensesAdded").prop("checked", data.fsContractedExpensesAdded == "Y");
	
	updateRemunerationModelSelection(rowSelector);
	
	if (data.remunerationModelCode == "FEE_SPLIT") {
		setDivVisibility("aFeeSplitInfoPanel", "block" );
	} else {
		setDivVisibility("aFeeSplitInfoPanel","none");
	}
	
	$("#aDescription").val(data.description);
	$("#aAgreementBudget").val(data.agreementBudget);
	
	// Set the Save Button to disabled
	setElementEnabled("saveAgreementDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("agreementDialog");
}


function updateRemunerationModelSelection(agreementRow) {

	var agreementData = {}; 
	var errors = "";

	if (agreementRow != null) {
		agreementData = agreementTable.row(agreementRow).data(); 
	} else {
		agreementRow = null;
	}
	
	var projectParticipantId = $("#aProjectParticipantId").val();
	populateSelect("aRemunerationModel", //elementId, html select element that will be populated
				"/rest/ignite/v1/remuneration-model", //url, url where it must get the data (you can paste in browser window to see the data)
		       "remunerationModelCode", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       agreementData.remunerationModelCode, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       function(){	//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
					checkForFeeSplit();
				}
	);	
}

function checkForFeeSplit() {
	var remunerationModelCode = $("#aRemunerationModel").val();
	if (remunerationModelCode == "FEE_SPLIT") {
		setDivVisibility("aFeeSplitInfoPanel", "block" );
	} else {
		setDivVisibility("aFeeSplitInfoPanel","none");
	}
}		    	

//editAgreement -- End

//saveAgreement -- Begin
function saveAgreement() {

	var postUrl = "/rest/ignite/v1/agreement-between-participants/new";

	var postData = {
			agreementBetweenParticipantsId: $("#aAgreementBetweenParticipantsId").val() == ""? null: $("#aAgreementBetweenParticipantsId").val(),
			projectParticipantId: $("#aProjectParticipantId").val(),
			remunerationModelCode: $("#aRemunerationModel").val(),
			description: $("#aDescription").val(),
			agreementBudget: $("#aAgreementBudget").val(),
			fsPreSplitContractingExpDeduct: $("#fsPreSplitContractingExpDeduct").is(":checked") ? "Y" : "N",
			fsPreSplitContractingThirdPDeduct: $("#fsPreSplitContractingThirdPDeduct").is(":checked") ? "Y" : "N",
			fsPreSplitOtherPartInvoices: $("#fsPreSplitOtherPartInvoices").is(":checked") ? "Y" : "N",
			fsContractedExpensesAdded: $("#fsContractedExpensesAdded").is(":checked") ? "Y" : "N",
	};
	
	var errors = "";

	// validate
	if (postData.projectParticipantId == "") {
		errors += "A Project Relationship is required<br>";
	}

	if (postData.remunerationModelCode == "") {
		errors += "A Remuneration Model is required<br>";
	}
	
	if ((postData.agreementBudget != "") && (postData.agreementBudget != null)){
		if (isNaN(postData.agreementBudget)) {
			errors += "The Agreement Budget has to be numeric<br>";
		}
	}

	if (postData.remunerationModelCode != "FEE_SPLIT") {
		postData.fsPreSplitContractingExpDeduct = null;
		postData.fsPreSplitContractingThirdPDeduct= null;
		postData.fsPreSplitOtherPartInvoices= null;
		postData.fsContractedExpensesAdded= null;
	} 
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}
	
	if (postData.agreementBetweenParticipantsId != null) {
		var postUrl = "/rest/ignite/v1/agreement-between-participants";
	}

	saveEntry(postUrl, postData, "agreementDialog", "The Agreement between Participants has been saved.", agreementTable,
			function(){
				setDivVisibility("pdCostPerAgreementTabLink", "block");
			}
			);
}
//saveAgreement -- End

function editSelectProjectParticipantRelationship() {
	selectProjectRelationship("aProjectParticipantId", "aRelationshipName");
	//dialogChanged();
}

function selectProjectRelationship(targetId, targetName) {
	var projectId = $("#pdProjectId").val();
	var queryUrl="/rest/ignite/v1/project-participant/view/" + projectId;
	
		$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="projectParticipantId";
			var columns = [
				{ data: "projectId", name: "projectId" },
				{ data: "projectParticipantId", name: "projectParticipantId" },
				{ data: "systemNamePayer", name: "Payer" },
				{ data: "systemNameBeneficiary", name: "Beneficiary" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0, 1]
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectParticipantId;
				var desc = row.systemNamePayer + " (Contracting) - " + row.systemNameBeneficiary + " (Contracted)";

				$("#" + targetId).val(id);
				$("#" + targetName).val(desc);
				$("#" + targetName).trigger("change");
				updateRemunerationModelSelection(null);
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


function agreementDialogChanged() {
	setElementEnabled("saveAgreementDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeAgreementDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("agreementDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("agreementDialog");
	}
}


//editRecoverableExpense -- Start
function editRecoverableExpense (agreementRow, recoverableExpenseRow) {

	var data = {}; 
	var errors = "";

	if (recoverableExpenseRow != null) {
		data = recoverableExpenseTable.row(recoverableExpenseRow).data();
		//selectedExpenseTypeId = data.expenseTypeId;
	} else {
//		selectedParentCode = null;
//		selectedExpenseTypeId = null;
	}

	recoverableExpenseTable.rows().deselect();
	
	populateSelect("eaExpenseTypeParent", //elementId, html select element that will be populated
		       "/rest/ignite/v1/expense-type-parent", //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeParentId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       data.expenseTypeParentId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
					
	);
	
	populateSelect("eaExpenseType", //elementId, html select element that will be populated
		       "/rest/ignite/v1/expense-type/by-parent/" + data.expenseTypeParentId, //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       data.expenseTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	
	$("#eaPayerName").val(agreementRow.systemNamePayer);
	$("#eaBeneficiaryName").val(agreementRow.systemNameBeneficiary);
	$("#eaAgreementDescription").val(agreementRow.description);
	$("#eaAgreementBetweenParticipantsId").val(agreementRow.agreementBetweenParticipantsId);
	

	$("#eaRecoverableExpenseId").val(data.expenseAgreementDescription);

	$("#eaDescription").val(data.expenseAgreementDescription);
	$("#eaExpenseBudget").val(data.expenseBudget);
	$("#eaRecoverableExpenseId").val(data.recoverableExpenseId);
	
	// Set the Save Button to disabled
	setElementEnabled("saveRecoverableExpenseDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("recoverableExpenseDialog");
}
//editRecoverableExpense -- End

function populateExpenseTypeChildren() {
	var selectedExpenseTypeParent = $("#eaExpenseTypeParent").val(); //Tells JQuery to fetch the value of the element id called npwBankId from the html page  

	populateSelect("eaExpenseType", //elementId, html select element that will be populated
		       "/rest/ignite/v1/expense-type/by-parent/" + selectedExpenseTypeParent, //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
}

function populateExpenseUnitTypeAndHandling() {
	var selectedExpenseTypeId = $("#eaExpenseType").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  
	
	var queryUrl;
	queryUrl = "/rest/ignite/v1/expense-type/unit/" + selectedExpenseTypeId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#eaUnitTypeName").val(data.name);
			return data;
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});

	var queryUrl;
	queryUrl = "/rest/ignite/v1/expense-type/" + selectedExpenseTypeId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			if ((data.allowHandlingPerc)=="Y") {
				setElementEnabled("eaPercHandlingCharge", true);
				$("#eaAllowHandlingPerc").val("Yes");
			} else {
				$("#eaPercHandlingCharge").val("");
				setElementEnabled("eaPercHandlingCharge", false);
				$("#eaAllowHandlingPerc").val("No");
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

//saveRecoverableExpense -- Begin
function saveRecoverableExpense() {
	var postUrl = "/rest/ignite/v1/recoverable-expense/new";

	var postData = {
			recoverableExpenseId: $("#eaRecoverableExpenseId").val() == ""? null: $("#eaRecoverableExpenseId").val(), 
			agreementBetweenParticipantsId: $("#eaAgreementBetweenParticipantsId").val(), 
			expenseTypeId: $("#eaExpenseType").val(),
			description: $("#eaDescription").val(),
			expenseBudget: $("#eaExpenseBudget").val()
	};
	
	var errors = "";

	// validate
	if (postData.expenseTypeId == "") {
		errors += "An Expense Type is required<br>";
	}
	
	if ((postData.expenseBudget != "") && (postData.expenseBudget != null)){
		if (isNaN(postData.expenseBudget)) {
			errors += "The Expense Agreement Budget has to be numeric<br>";
		}
	}

	
	if (showFormErrors("eaDlgErrorDiv", errors)) {
		return;
	}
	
	if (postData.recoverableExpenseId != null) {
		var postUrl = "/rest/ignite/v1/recoverable-expense";
	}

	
	saveEntry(postUrl, postData, "recoverableExpenseDialog", "The Expense Agreement has been saved.", recoverableExpenseTable);
}
//saveRecoverableExpense -- End

function recoverableExpenseChanged() {
	setElementEnabled("saveRecoverableExpenseDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeRecoverableExpenseDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("recoverableExpenseDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("recoverableExpenseDialog");
	}
}
