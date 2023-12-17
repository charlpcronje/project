var assetConditionTable = null;
var	askToSaveDialog = null;
var somethingChangedInDialog = null;

function initializeAssetConditionTable() {
	var columnsArray = [
		{ data: "assetConditionId" },
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
			action: function( e, dt, node, config ) {
				editAssetCondition(null);
			}
		},
		{
			attr : {
				id: "deleteAssetConditionBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteAssetCondition();
			}
		}
	];

	assetConditionTable = initializeGenericTable("assetConditionTable", 
			                            "/rest/ignite/v1/asset-condition",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editAssetCondition(rowSelector);
										},
										null,
										31,
										[1,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
	
	
	);

	assetConditionTable.off('deselect');
	assetConditionTable.on('deselect', function (e, dt, type, indexes) {
		updateAssetConditionToolbarButtons();
	} );

	assetConditionTable.off('select');
	assetConditionTable.on('select', function (e, dt, type, indexes) {
		updateAssetConditionToolbarButtons();
	} );

	// to initially set the buttons
	updateAssetConditionToolbarButtons();
}

function updateAssetConditionToolbarButtons() {
	var hasSelected = assetConditionTable.rows('.selected').data().length > 0;
	
	setTableButtonState(assetConditionTable, "deleteAssetConditionBtn", hasSelected);
}


function editAssetCondition(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = assetConditionTable.row(rowSelector).data();
	}
	assetConditionTable.rows().deselect();
	
	$("#cDlgAssetConditionId").val(data.assetConditionId);
	$("#cDlgName").val(data.name);
	$("#cDlgDescription").val(data.description);
	
	// Set the Save Button to disabled
	setElementEnabled("saveAssetConditionButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
		
	showModalDialog("assetConditionDialog");
}


function promptDeleteAssetCondition() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Asset Condition?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteAssetCondition();
			   }
	);
}

function deleteAssetCondition() {
	var postUrl = "/rest/ignite/v1/asset-condition/delete";
	var row = assetConditionTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Asset Condition has been deleted.", assetConditionTable,
			function(){	
				assetConditionTable.rows(".selected").nodes().to$().removeClass("selected");
				updateAssetConditionToolbarButtons();
			}
	);
}

function saveAssetCondition() {
	var postUrl = "/rest/ignite/v1/asset-condition/new";
	var mode = $("#cDlgMode").val();
	var assetConditionId = $("#cDlgAssetConditionId").val().trim().toUpperCase();
	var name = $("#cDlgName").val();
	var description = $("#cDlgDescription").val();
	var errors = "";
	
	var postData = {
		mode: mode,
		assetConditionId : assetConditionId,
		name : name,
		description: description	
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.assetConditionId != null) && (postData.assetConditionId != "")) {
		//This is an update
		postUrl = "/rest/ignite/v1/asset-condition";
	} else {
		postData.assetConditionId = null;  //empty string werk nie
	}
	
	saveEntry(postUrl, postData, "assetConditionDialog", "The Asset Condition has been saved.", assetConditionTable);
}


//AssetConditionDialogChanged -- Start
function assetConditionDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveAssetConditionButton", true);
}
//AssetConditionDialogChanged -- End

//closeAssetConditionDialog -- Start
function closeAssetConditionDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("assetConditionDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("assetConditionDialog");
	}
}//closeAssetConditionDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeAssetConditionTable();
} );
