var dataTable = null;
var somethingChangedInDialog = false;

function initializeTable() {
	var columnsArray = [
		{ data: "maintenanceTypeId" },
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
				editDetail(null);
			}
		},
		{
			attr : {
				id: "deleteBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDelete();
			}
		}
	];
	
	dataTable = initializeGenericTable("dataTable", 
			                            "/rest/ignite/v1/maintenance-type",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editDetail(rowSelector);
										},
			                    		null,
			                    		31,
			                    		[1,"asc"]
			);

	dataTable.off('deselect');
	dataTable.on('deselect', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	dataTable.off('select');
	dataTable.on('select', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	// to initially set the buttons
	updateToolbarButtons();
}

function updateToolbarButtons() {
	var hasSelected = dataTable.rows('.selected').data().length > 0;
	
	setTableButtonState(dataTable, "deleteBtn", hasSelected);
}

function editDetail(rowSelector) {
	var data = {};
	var isNew = true;
	
	if (rowSelector != null) {
		data = dataTable.row(rowSelector).data();
		isNew = false;
	}
	dataTable.rows().deselect();

	$("#dDlgMode").val(isNew ? "i" : "u");
	$("#dDlgMaintenanceTypeId").val(data.maintenanceTypeId);
	$("#dDlgName").val(data.name);
	$("#dDlgDescription").val(data.description);
	
	console.log("hello");
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleMaintenaceButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("detailDialog");
}

function promptDelete() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Maintenance Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRow();
			   }
	);
}

function deleteRow() {
	var postUrl = "/rest/ignite/v1/maintenance-type/delete";
	var row = dataTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Maintenance Type has been deleted.", dataTable,
		function(){	
			dataTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function saveRecord() {
	var postUrl = "/rest/ignite/v1/maintenance-type/new";
	
	var postData = {
		maintenanceTypeId : $("#dDlgMaintenanceTypeId").val(),
		name: $("#dDlgName").val(),
		description: $("#dDlgDescription").val()
	}

	var errors = "";
		
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("dDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.maintenanceTypeId != null) && (postData.maintenanceTypeId != "")) {	
            // This is an update 
            postUrl = "/rest/ignite/v1/maintenance-type";
    } else {
		postData.maintenanceTypeId = null;  //empty string werk nie
	}	
	
	saveEntry(postUrl, postData, "detailDialog", "The Maintenance Type has been saved.", dataTable);
}

//VehicleMaintenanceDialogChanged -- Start
function vehicleMaintenanceDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleMaintenaceButton", true);
}
//VehicleMaintenanceDialogChanged -- End

//closeVehicleMaintenanceDialog -- Start
function closeVehicleMaintenanceDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("detailDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("detailDialog");
	}
}//closeVehicleMaintenanceDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTable();
} );
