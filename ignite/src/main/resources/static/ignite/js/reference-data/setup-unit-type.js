var dataTable = null;

function initializeTable() {
	var columnsArray = [
		{ data: "unitTypeCode" },
		{ data: "name" },
		{ data: "description" }
	];
   	var columnDefsArray = [
//		{
//			visible: false,
//			targets: [ 0]
//		}
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
			                            "/rest/ignite/v1/unit-type",
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
	$("#dDlgUnitTypeCode").val(data.unitTypeCode);
	$("#dDlgName").val(data.name);
	$("#dDlgDescription").val(data.description);

	// disable code if this is an update
	$("#dDlgUnitTypeCode").prop("readonly", (rowSelector != null) && (rowSelector != ""));	
	
	// Set the Save Button to disabled
	setElementEnabled("saveUnitTypeButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("detailDialog");
}


function promptDelete() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Unit Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRow();
			   }
	);
}


function deleteRow() {
	var postUrl = "/rest/ignite/v1/unit-type/delete";
	var row = dataTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Unit Type has been deleted.", dataTable,
		function(){	
			dataTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}


function saveRecord() {
	var errors = "";
	var postUrl = "/rest/ignite/v1/unit-type/new";
	
	var postData = {
		unitTypeCode : $("#dDlgUnitTypeCode").val(),
		name: $("#dDlgName").val(),
		description: $("#dDlgDescription").val()
	}

	if ((postData.unitTypeCode == null) || (postData.unitTypeCode == "")) {
		errors += "A Code is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("dDlgErrorDiv", errors)) {
		return;
	}

	if ($("#dDlgUnitTypeCode").is("[readonly]")) {
            // This is an update 
            postUrl = "/rest/ignite/v1/unit-type";
     } 

	saveEntry(postUrl, postData, "detailDialog", "The Unit Type has been saved.", dataTable);
}

//unitTypeDialogChanged -- Start
function unitTypeDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveUnitTypeButton", true);
}
//unitTypeDialogChanged -- End

//closeUnitTypeDialog -- Start
function closeUnitTypeDialog() {
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
}//closeUnitTypeDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTable();
	showIgDeveloperOption();
} );
