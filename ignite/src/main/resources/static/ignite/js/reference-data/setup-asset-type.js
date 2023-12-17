var assetTypeTable = null;
var askToSaveDialog = null;
var somethingChangedInDialog = null;

function initializeAssetTypeTable() {
	var columnsArray = [
		{ data: "assetTypeId" },
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
				editAssetType(null);
			}
		},
		{
			attr : {
				id: "deleteAssetTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteAssetType();
			}
		}
	];
	
	assetTypeTable = initializeGenericTable("assetTypeTable", 
			                            "/rest/ignite/v1/asset-type",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editAssetType(rowSelector);
										},
			                    		null,
			                    		31,
			                    		[1,"asc"]);

	assetTypeTable.off('deselect');
	assetTypeTable.on('deselect', function (e, dt, type, indexes) {
		updateAssetTypeToolbarButtons();
	} );

	assetTypeTable.off('select');
	assetTypeTable.on('select', function (e, dt, type, indexes) {
		updateAssetTypeToolbarButtons();
	} );

	// to initially set the buttons
	updateAssetTypeToolbarButtons();
}

function updateAssetTypeToolbarButtons() {
	var hasSelected = assetTypeTable.rows('.selected').data().length > 0;
	
	setTableButtonState(assetTypeTable, "deleteAssetTypeBtn", hasSelected);
}


function editAssetType(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = assetTypeTable.row(rowSelector).data();
	}
	assetTypeTable.rows().deselect();
	
	$("#cDlgAssetTypeId").val(data.assetTypeId);
	$("#cDlgName").val(data.name);
	$("#cDlgDescription").val(data.description);
	
	// Set the Save Button to disabled
	setElementEnabled("saveAssetTypeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("assetTypeDialog");
}


function promptDeleteAssetType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Asset Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteAssetType();
			   }
	);
}

function deleteAssetType() {
	var postUrl = "/rest/ignite/v1/asset-type/delete";
	var row = assetTypeTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Asset Type has been deleted.", assetTypeTable,
			function(){	
				assetTypeTable.rows(".selected").nodes().to$().removeClass("selected");
				updateAssetTypeToolbarButtons();
			}
	);
}

function saveAssetChanged() {
	setElementEnabled("saveAssetTypeButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function saveAssetType() {
	var postUrl = "/rest/ignite/v1/asset-type/new";
	var mode = $("#cDlgMode").val();
	var assetTypeId = $("#cDlgAssetTypeId").val().trim().toUpperCase();
	var name = $("#cDlgName").val();
	var description = $("#cDlgDescription").val();
	var errors = "";
	
	var postData = {
		mode: mode,
		assetTypeId : assetTypeId,
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
	
	if ((postData.assetTypeId != null) && (postData.assetTypeId != "")) {
		//This is an update
		postUrl = "/rest/ignite/v1/asset-type";
	} else {
		postData.assetTypeId = null;  //empty string werk nie
	}
	
	saveEntry(postUrl, postData, "assetTypeDialog", "The Asset Type has been saved.", assetTypeTable);
}

//closeProjBasedRemunTypeDialog -- Start
function closeAssetTypeDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("assetTypeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("assetTypeDialog");
	}
}//closeProjBasedRemunTypeDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeAssetTypeTable();
} );
