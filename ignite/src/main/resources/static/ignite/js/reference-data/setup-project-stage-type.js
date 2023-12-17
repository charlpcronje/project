var dataTable = null;

function initializeTable() {
	var columnsArray = [
		{ data: "projectStageTypeId" },
		{ data: "projectStageTypeCode" },
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
	
	dataTable = initializeGenericTable(
								"dataTable", 
	                            "/rest/ignite/v1/project-stage-type",
	                            columnsArray,
	                            columnDefsArray,
	                            buttonsArray,
	                            function(rowSelector) {
									editDetail(rowSelector);
								},
	                    		null,						// completeMethod
	                    		31,							// pageLength
	                    		[[1,"asc"]] 				// Order by column 0 ascending, normally defaults to column 1 ascending
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
	$("#dDlgProjectStageTypeId").val(data.projectStageTypeId);
	$("#dDlgCode").val(data.projectStageTypeCode);
	$("#dDlgName").val(data.name);
	$("#dDlgDescription").val(data.description);
		
	// Set the Save Button to disabled
	setElementEnabled("saveProjectStageTypeButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("detailDialog");
}

function promptDelete() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Project Stage Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRow();
			   }
	);
}

function deleteRow() {
	var postUrl = "/rest/ignite/v1/project-stage-type/delete";
	var row = dataTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Project Stage Type has been deleted.", dataTable,
		function(){	
			dataTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function saveRecord() {
	var postUrl = "/rest/ignite/v1/project-stage-type/new";
	var postData = {
			projectStageTypeId: $("#dDlgProjectStageTypeId").val(),
			projectStageTypeCode: $("#dDlgCode").val().trim().toUpperCase(),
			name: $("#dDlgName").val(),
			description: $("#dDlgDescription").val()
	};
	var errors = "";
	
	// validate
	if ((postData.projectStageTypeCode == null) || (postData.projectStageTypeCode == "")) {
		errors += "A Project Stage Type Code is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	if (showFormErrors("dDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.projectStageTypeId != null) && (postData.projectStageTypeId != "")) {	
		// This is an update
			postUrl = "/rest/ignite/v1/project-stage-type";
	} else {
		postData.projectStageTypeId = null;  //empty string werk nie
	}
	saveEntry(postUrl, postData, "detailDialog", "The Project Stage Type has been saved.", dataTable);
}

//projectStageTypeChanged -- Start
function projectStageTypeChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveProjectStageTypeButton", true);
}
//projectStageTypeChanged -- End

//closeProjectStageTypeDialog -- Start
function closeProjectStageTypeDialog() {
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
}//closeProjectStageTypeDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTable();
} );
