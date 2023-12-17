var assetStatusTable = null;
var askToSaveDialog = null;
var somethingChangedInDialog = null;

function initializeAssetStatusTable() {
	var columnsArray = [
		{ data: "assetStatusId" },
		{ data: "name" },
		{ data: "description" }
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
				editAssetStatus(null);
			}
		},
		{
			attr: {
				id: "deleteAssetStatusBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteAssetStatus();
			}
		}
	];

	assetStatusTable = initializeGenericTable("assetStatusTable",
		"/rest/ignite/v1/asset-status",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editAssetStatus(rowSelector);
		},
		null,
		30,
		[1, "asc"] //Order by column 2 ascending, normally defaults to column 1 ascending


	);

	assetStatusTable.off('deselect');
	assetStatusTable.on('deselect', function(e, dt, type, indexes) {
		updateAssetStatusToolbarButtons();
	});

	assetStatusTable.off('select');
	assetStatusTable.on('select', function(e, dt, type, indexes) {
		updateAssetStatusToolbarButtons();
	});

	// to initially set the buttons
	updateAssetStatusToolbarButtons();
}

function updateAssetStatusToolbarButtons() {
	var hasSelected = assetStatusTable.rows('.selected').data().length > 0;

	setTableButtonState(assetStatusTable, "deleteAssetStatusBtn", hasSelected);
}


function editAssetStatus(rowSelector) {
	var data = {};

	if (rowSelector != null) {
		data = assetStatusTable.row(rowSelector).data();
	}
	assetStatusTable.rows().deselect();

	$("#cDlgAssetStatusId").val(data.assetStatusId);
	$("#cDlgName").val(data.name);
	$("#cDlgDescription").val(data.description);


	// Set the Save Button to disabled
	setElementEnabled("saveAssetStatusDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("assetStatusDialog");
}


function promptDeleteAssetStatus() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Asset Status?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteAssetStatus();
		}
	);
}

function deleteAssetStatus() {
	var postUrl = "/rest/ignite/v1/asset-status/delete";
	var row = assetStatusTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Asset Status has been deleted.", assetStatusTable,
		function() {
			assetStatusTable.rows(".selected").nodes().to$().removeClass("selected");
			updateAssetStatusToolbarButtons();
		}
	);
}

function saveAssetStatus() {
	var postUrl = "/rest/ignite/v1/asset-status/new";
	var mode = $("#cDlgMode").val();
	var assetStatusId = $("#cDlgAssetStatusId").val().trim().toUpperCase();
	var name = $("#cDlgName").val();
	var description = $("#cDlgDescription").val();
	var errors = "";

	var postData = {
		mode: mode,
		assetStatusId: assetStatusId,
		name: name,
		description: description
	}

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.assetStatusId != null) && (postData.assetStatusId != "")) {
		//This is an update
		postUrl = "/rest/ignite/v1/asset-status";
	} else {
		postData.assetStatusId = null;  //empty string werk nie
	}
	
	saveEntry(postUrl, postData, "assetStatusDialog", "The Asset Status has been saved.", assetStatusTable);
}

function assetStatusChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveAssetStatusDialogButton", true);
}


//closeProjBasedRemunTypeDialog -- Start
function closeAssetStatusDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("assetStatusDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("assetStatusDialog");
	}
}//closeProjBasedRemunTypeDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeAssetStatusTable();
});
