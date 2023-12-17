var bankAccountTypeTable;
var askToSaveDialog = null;
var somethingChangedInDialog = null;

function initializeAccountTypeTable() {
	var columnsArray = [
		{ data: "accountTypeId" },
		{ data: "name" }
	];

   	var columnDefsArray = [
       		{
       			visible: false,
       			targets: [ 0]
       		}
       	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editAccountType(null);
			}
		},
		{
			attr: {
				id: "deleteAccountTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteAccountType();
			}
		}
	];

	accountTypeTable = initializeGenericTable("accountTypeTable",
		"/rest/ignite/v1/account-type",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(roleSelector) {
			editAccountType(roleSelector);
		},
		null,
		31,
		[1,"asc"]
	);

	accountTypeTable.off('deselect');
	accountTypeTable.on('deselect', function(e, dt, type, indexes) {
		updateAccountTypeToolbarButtons();
	});

	accountTypeTable.off('select');
	accountTypeTable.on('select', function(e, dt, type, indexes) {
		updateAccountTypeToolbarButtons();
	});

	// to initially set the buttons
	updateAccountTypeToolbarButtons();
}



function updateAccountTypeToolbarButtons() {
	var hasSelected = accountTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(accountTypeTable, "deleteAccountTypeBtn", hasSelected);
}



function editAccountType(rowSelector) {
	var data = {};

	if (rowSelector != null) {
		data = accountTypeTable.row(rowSelector).data();
	}
	accountTypeTable.rows().deselect();

	setElementEnabled("saveBankAccountTypeButton", false);
	var askToSaveDialog = false;
	var somethingChangedInDialog = false;

	showAccountTypeDialog(data);
}



function accountTypeDialogChanged() {
	setElementEnabled("saveBankAccountTypeButton", true);
	askToSaveDialog = true;
	somethingChangedInDialog = true;
}



function showAccountTypeDialog(data) {

	$("#batDlgAccountTypeId").val(data.accountTypeId);
	$("#batDlgName").val(data.name);

	showModalDialog("accountTypeDialog");
}



function promptDeleteAccountType() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Account Type?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteAccountType();
		}
	);
}


function deleteAccountType() {
	var postUrl = "/rest/ignite/v1/account-type/delete";
	var row = accountTypeTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Account Type has been deleted.", accountTypeTable,
		function() {
			accountTypeTable.rows(".selected").nodes().to$().removeClass("selected");
			updateAccountTypeToolbarButtons();
		}
	);
}



function saveAccountType() {
	
	var postUrl = "/rest/ignite/v1/account-type/new"; //Insert new record
	var postData = {
		//			mode: $("#batDlgMode").val(),
		accountTypeId: $("#batDlgAccountTypeId").val().toUpperCase(),
		name: $("#batDlgName").val()
	};

	var errors = "";

	if ((postData.name == null) || (postData.name == "")) {
		errors += "An Account Type Name is required<br>";
	}
	
	if ((postData.accountTypeId != null) && (postData.accountTypeId != "")) {	
		postUrl = "/rest/ignite/v1/account-type";  //Update the record
	} else {
		postData.accountTypeId = null;  //empty string werk nie
	}

	// validation...
	if (showFormErrors("batDlgErrorDiv", errors)) {
		return;
	}

	//console.dir(postData)	;

	saveEntry(postUrl, postData, "accountTypeDialog", "The Account Type has been saved.", accountTypeTable);
}



function closeBankAccountTypeDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("accountTypeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("accountTypeDialog");
	}
}


// ***********************************************************************

$(document).ready(function() {
	initializeAccountTypeTable();
});
