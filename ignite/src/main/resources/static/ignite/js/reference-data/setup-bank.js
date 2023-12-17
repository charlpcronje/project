var bankTable = null;
var bBranchesTable = null;
var askToSaveDialog = null;
var somethingChangedInDialog = null;

function initializeBankTable() {
	
	var columnsArray = [
	    { data: "bankId" },
    	{ data: "bankCode" },
    	{ data: "name" },
    	{ data: "swiftNumber" },  //SWIFT
    	{ data: "universalBranchCode" }
    	// TODO: we need an active flag in case a bank closes
    	// TODO: we also need to identify anyone that uses this bank if the status is changed to inactive...
    ];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0]
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editBank(null);
			}
		},
		{
			attr : {
				id : "deleteBankBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteBank();
			}
		}
	];

	setDivVisibility("bankPanel", "block");
	setDivVisibility("branchPanel", "none");

	bankTable = initializeGenericTable("bankTable",
			                            "/rest/ignite/v1/bank",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editBank(aThis);
										},
			                            null,  //completeMethod
			                            31,     //pageLength
			                            [1,"asc"],  //orderArray
			                            null,  //tableHeightPixels (ignored if you have pagelength)
			                            true, //showTableInfo   (Showing 0 to 5 etc....)
			                            true   //showFilter
	);

	bankTable.off('deselect');
	bankTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyBranchesPanel(dt.data());
		$("#bBankId").val("");
		$("#bBankName").val("");
		updateBankToolbarButtons();
	} );

	bankTable.off('select');
	bankTable.on('select', function (e, dt, type, indexes) {
		$("#bBankId").val(dt.data.bankId);
		$("#bBankName").val(dt.data.name);
		showBranchesTable(dt.data());
//		showBankAccountTypesTable(dt.data());
		updateBankToolbarButtons();
	} );

	// to initially set the buttons
	updateBankToolbarButtons();
} // initializeBankTable -- END



function showBranchesTable(parentRow) {
	
	if (parentRow == null)  {
		return;
	} 

	$("#bBankId").val(parentRow.bankId);
	$("#bBankName").val(parentRow.name);

	enabled = true;
	
	var columnsArray = [        
	    { data: "branchId" }, 	//0 
		{ data: "branchCode" }, 	//1
		{ data: "bankId" }, 		//2
		{ data: "name" } 			//3
	];

	var columnDefsArray = [
	            			{
	            				visible: false,
	            				targets: [0,2]
	            			}		

	            	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function() {
				editBranch(null, $("#bBankId").val(), $("#bBankName").val(), "bBranchesTable");
			}
		},
		{
			attr: {
				id: "deleteBranchBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteBranch(bBranchesTable);
			}
		}
	];
	
	setDivVisibility("branchesEmptyPanel", "none");
	setDivVisibility("branchesPanel", "block");
	
//console.log("rest/ignite/v1/branch/by-bank/" + $("#bBankId").val());

	bBranchesTable = initializeGenericTable("bBranchesTable",
			                            "/rest/ignite/v1/branch/by-bank/" + $("#bBankId").val(),
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editBranch(rowSelector, $("#bBankId").val(), $("#bBankName").val(), "bBranchesTable");
										},
			                            null,  //completeMethod
			                            31,     //pageLength
			                            null,  //orderArray
			                            null,  //tableHeightPixels (ignored if you have pagelength)
			                            true,  //showTableInfo   (Showing 0 to 5 etc....)
			                            true   //showFilter
	);
	
	bBranchesTable.off('deselect');
	bBranchesTable.on('deselect', function (e, dt, type, indexes) {
		updateBranchToolbarButtons();
	} );

	bBranchesTable.off('select');
	bBranchesTable.on('select', function (e, dt, type, indexes) {
		updateBranchToolbarButtons();
		
	} );	
	
	updateBranchToolbarButtons();
	
}//  showBranchesTable  --  End



//function showBankAccountTypesTable(parentRow) {
//
//	if (parentRow == null) {
//		return;
//	}
//
//	enabled = true;
//
//	var columnsArray = [
//		{ data: "bankAccountTypeId" }, 	//0
//		{ data: "bankId" }, 		//1
//		{ data: "accountTypeId" }, 		//1
//		{ data: "name" } 			//2
//	];
//
//	var columnDefsArray = [
//		{
//			visible: false,
//			targets: [0, 1]
//		}
//	];
//
//	var buttonsArray = [
//		{
//			titleAttr: "New",
//			text: "<i class='fas fa-plus'></i>",
//			className: "btn btn-sm",
//			action: function() {
//				editBankAccountType(null, $("#bBankId").val(), $("#bBankName").val(), "bBankAccountTypesTable");
//			}
//		},
//		{
//			attr: {
//				id: "deleteBankAccountTypeBtn"
//			},
//			titleAttr: "Delete",
//			text: "<i class='fas fa-minus'></i>",
//			className: "btn btn-sm",
//			action: function() {
//				promptDeleteBankAccountType(bBankAccountTypesTable);
//			}
//		}
//	];
//
//	setDivVisibility("bankAccountTypesEmptyPanel", "none");
//	setDivVisibility("bankAccountTypesPanel", "block");


//console.log("rest/ignite/v1/bank-account-type/by-bank/" + $("#bBankId").val());	
//	
//	bBankAccountTypesTable = initializeGenericTable("bBankAccountTypesTable",
//			                            "/rest/ignite/v1/bank-account-type/by-bank/" + $("#bBankId").val(),
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function(rowSelector) {
//										editBankAccountType(rowSelector, $("#bBankId").val(), $("#bBankName").val(), "bBankAccountTypesTable");
//										},
//			                            null,  //completeMethod
//			                            7,     //pageLength
//			                            null,  //orderArray
//			                            null,  //tableHeightPixels (ignored if you have pagelength)
//			                            true,  //showTableInfo   (Showing 0 to 5 etc....)
//			                            true   //showFilter
//	);
//	
//	bBankAccountTypesTable.off('deselect');
//	bBankAccountTypesTable.on('deselect', function (e, dt, type, indexes) {
//		updateBankAccountTypeToolbarButtons();
//	} );
//
//	bBankAccountTypesTable.off('select');
//	bBankAccountTypesTable.on('select', function (e, dt, type, indexes) {
//		updateBankAccountTypeToolbarButtons();
//		
//	} );	
//	
//	updateBankAccountTypeToolbarButtons();
//	
//}  showBankAccountTypesTable  --  End

function showEmptyBranchesPanel() {
	setDivVisibility("branchesEmptyPanel", "block");
	setDivVisibility("branchesPanel", "none");
}


function updateBankToolbarButtons() {
	var hasSelected = bankTable.rows('.selected').data().length > 0;

	setTableButtonState(bankTable, "showBranchesBtn", hasSelected);
	setTableButtonState(bankTable, "deleteBankBtn", hasSelected);
}


function editBank(aThis) {

	if (aThis != null) {
		// get values from table
		var data = bankTable.row(aThis).data();

		$("#bDlgBankId").val(data.bankId);
		$("#bDlgBankCode").val(data.bankCode);
		$("#bDlgName").val(data.name);
		$("#bDlgSwiftNumber").val(data.swiftNumber);
		$("#bDlgUniversalBranchCode").val(data.universalBranchCode);		
	} else {
		$("#bDlgBankId").val("");
		$("#bDlgBankCode").val("");
		$("#bDlgName").val("");
		$("#bDlgSwiftNumber").val("");
		$("#bDlgUniversalBranchCode").val("");	
	}
	bankTable.rows().deselect();

	// Set the Save Button to disabled
	setElementEnabled("saveBankDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("bankDialog");
}


function bankDialogChanged() {
	setElementEnabled("saveBankDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}



function saveBank() {
	var postUrl = "/rest/ignite/v1/bank/new";
	var postData = {
			bankId: $("#bDlgBankId").val().trim(),
			bankCode: $("#bDlgBankCode").val().trim().toUpperCase(),
			name: $("#bDlgName").val().trim(),
			swiftNumber: $("#bDlgSwiftNumber").val().trim(),
			universalBranchCode: $("#bDlgUniversalBranchCode").val().trim()
	};
	var errors = "";

	// validate
	if ((postData.bankCode == null) || (postData.bankCode == "")) {	
		errors += "A Bank Code is required<br>";
	}
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	if (showFormErrors("bDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.bankId != null) && (postData.bankId != "")) {	 
            // This is an update 
            postUrl = "/rest/ignite/v1/bank";
        } else {
    		postData.bankId = null;  //empty string werk nie
    	}	

	saveEntry(postUrl, postData, "bankDialog", "The Bank has been saved.", bankTable);
}

function closeBankDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("bankDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("bankDialog");
	}
}

function backToBanks() {
	$("#pageHeader").html("<i class='fa fa-university'></i> Setup: Banks");

	setDivVisibility("bankPanel", "block");
	setDivVisibility("branchPanel", "none");
}

function updateBranchToolbarButtons() {
	var hasSelected = bBranchesTable.rows('.selected').data().length > 0;
	setTableButtonState(bankTable, "deleteBranchBtn", hasSelected);
}

function updateBankAccountTypeToolbarButtons() {
	var hasSelected = bBankAccountTypesTable.rows('.selected').data().length > 0;
	setTableButtonState(bankTable, "deleteBankAccountTypeBtn", hasSelected);
}

function editBranch(aThis) {

	if (aThis != null) {
		// get values from table
		var data = bBranchesTable.row(aThis).data();
		console.dir(data)
		$("#bbDlgBranchId").val(data.branchId);
		$("#bbDlgBankId").val(data.bankId);
		$("#bbDlgBankName").val($("#bBankName").val());
		$("#bbDlgBranchCode").val(data.branchCode);
		$("#bbDlgName").val(data.name);
	} else {
		$("#bbDlgBranchId").val("");
		$("#bbDlgBankId").val($("#bBankId").val());
		$("#bbDlgBankName").val($("#bBankName").val());
		$("#bbDlgName").val("");
		$("#bbDlgBranchCode").val("");
	}
	bBranchesTable.rows().deselect();

	// Set the Save Button to disabled
	setElementEnabled("saveBranchDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("branchDialog");
}

function branchDialogChanged() {
	setElementEnabled("saveBranchDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

//function editBankAccountType(aThis) {
//
//	$("#bbatDlgBankId").val($("#bBankId").val());
//	$("#bbatDlgBankName").val($("#bBankName").val());
//
//	if (aThis != null) {
//		console.log("ou een");
//		// get values from table
//		var data = bBankAccountTypesTable.row(aThis).data();
//		
//		$("#bbatDlgBankAccountTypeId").val(data.bankAccountTypeId);
//		$("#bbatAccountTypeId").val(data.accountTypeId);
//		$("#bbatDlgName").val(data.name);
//	} else {
//		$("#bbatDlgBankAccountTypeId").val("");
//		$("#bbatAccountTypeId").val("");
//		$("#bbatDlgName").val("");
//	};
//
//	//console.log("rest/ignite/v1/account-type")	
//	
//	if (($("#bbatDlgBankAccountTypeId").val() == "") || ($("#bbatDlgBankAccountTypeId").val() == null)) {
//	
//		populateSelect("bbatAccountTypeId", 
//			       "/rest/ignite/v1/account-type",
//			       "accountTypeId", 
//			       "name", 
//			       null, 
//			       true,
//			       null)	
//	} else {
//	
//		populateSelect("bbatAccountTypeId", 
//		       "/rest/ignite/v1/account-type",
//		       "accountTypeId", 
//		       "name", 
//		       data.accountTypeId, 
//		       true,
//		       null)	
//	}
//	
//	bBankAccountTypesTable.rows().deselect();
//
//	showModalDialog("bankAccountTypeDialog");
//} // editBankAccountType -- END

function saveBranch() {
	var postUrl = "/rest/ignite/v1/bank/branch/new";
	var postData = {
			branchId : $("#bbDlgBranchId").val(),
			bankId : $("#bbDlgBankId").val(),
			branchCode : $("#bbDlgBranchCode").val().trim().toUpperCase(),
			name: $("#bbDlgName").val()
	};
	var errors = "";

	// validate
	if ((postData.branchCode == null) || (postData.branchCode == "")) {
		errors += "A Branch Code is required<br>";
	}
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	if (showFormErrors("bbDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.branchId != null) && (postData.branchId != "")) {	 
            // This is an update 
            postUrl = "/rest/ignite/v1/bank/branch";
    } else {
    		postData.branchId = null;  //empty string werk nie
    }	

	saveEntry(postUrl, postData, "branchDialog", "The Branch has been saved.", bBranchesTable);
}

//function saveBankAccountType() {
//	
//	var postUrl = "/rest/ignite/v1/bank-account-type/new";
//	var postData = {
//			bankAccountTypeId : $("#bbatDlgBankAccountTypeId").val(),
//			bankId : $("#bbatDlgBankId").val(),
//			accountTypeId : $("#bbatAccountTypeId").val(),
//			name: $("#bbatDlgName").val()
//	};
//	var errors = "";
//	
//	console.dir(postData);
//	
//	// validate
//	if (postData.accountTypeId == "") {
//		errors += "An Account Type is required<br>";
//	}
//	if (postData.name == "") {
//		errors += "A Name is required<br>";
//	}
//	
//	if (showFormErrors("bbatDlgErrorDiv", errors)) {
//		return;
//	}
//	
//	//Is the key readonly?  If it is, then the record already exists. 
//    if (($("#bbatDlgBankAccountTypeId").val() != "") && ($("#bbatDlgBankAccountTypeId").val() != null)) {  
//            // This is an update 
//            postUrl = "/rest/ignite/v1/bank-account-type";
//        }
//    
//	saveEntry(postUrl, postData, "bankAccountTypeDialog", "The Bank Account Type has been saved.", bBankAccountTypesTable);
//}

function promptDeleteBank() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Bank?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteBank();
			   }
	);
}

function closeBranchDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("branchDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("branchDialog");
	}
}


function deleteBank() {
	var postUrl = "/rest/ignite/v1/bank/delete";
	var row = bankTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Bank has been deleted.", bankTable,
			function(){	
				bankTable.rows(".selected").nodes().to$().removeClass("selected");
				updateBankToolbarButtons();
			}
	);
}

function promptDeleteBranch() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Branch?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteBranch();
			   }
	);
}

//function promptDeleteBankAccountType() {
//	showDialog("Confirm?",
//		       "Are you sure that you wish to delete the selected Bank Account Type?",
//		       DialogConstants.TYPE_CONFIRM, 
//		       DialogConstants.ALERTTYPE_INFO, 
//		       function (e) {
//					deleteBankAccountType();
//			   }
//	);
//}// promptDeleteBankAccountType -- END

function deleteBranch() {
	var postUrl = "/rest/ignite/v1/bank/branch/delete";
	var row = bBranchesTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Branch has been deleted.", bBranchesTable,
			function(){	
				bBranchesTable.rows(".selected").nodes().to$().removeClass("selected");
				updateBranchToolbarButtons();
			}
	);
}

//function deleteBankAccountType() {
//	var postUrl = "/rest/ignite/v1/bank-account-type/delete";
//	var row = bBankAccountTypesTable.rows('.selected').data()[0];
//
//	// Disable delete button after record has been deleted.
//	saveEntry(postUrl, row, null, "The Bank Account Type has been deleted.", bBankAccountTypesTable,
//			function(){	
//				bBankAccountTypesTable.rows(".selected").nodes().to$().removeClass("selected");
//				updateBankAccountTypeToolbarButtons();
//			}
//	);
//}


// ***********************************************************************

$(document).ready(function() {
	initializeBankTable();
	showIgDeveloperOption();
} );
