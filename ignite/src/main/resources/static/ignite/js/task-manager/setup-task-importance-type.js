//var taskImportanceTypeTable;

function initializeTaskImportanceTypeTable() {
	var columnsArray = [
		{ data: "taskImportanceTypeId" },
		{ data: "importanceValue" },
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
				editTaskImportanceType(null);
			}
		},
		{
			attr : {
				id: "deleteTaskImportanceTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteTaskImportanceType();
			}
		}
	];
console.log("toets");
	taskImportanceTypeTable = initializeGenericTable("taskImportanceTypeTable",
			                            "/rest/ignite/v1/task-importance-type",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(roleSelector) {
											editTaskImportanceType(roleSelector);
										},
			                    		null,
			                    		31,
			                    		[1,"asc"]
	);

	taskImportanceTypeTable.off('deselect');
	taskImportanceTypeTable.on('deselect', function (e, dt, type, indexes) {
		updateTaskImportanceTypeToolbarButtons();
	} );

	taskImportanceTypeTable.off('select');
	taskImportanceTypeTable.on('select', function (e, dt, type, indexes) {
		updateTaskImportanceTypeToolbarButtons();
	} );

	// to initially set the buttons
	updateTaskImportanceTypeToolbarButtons();
}

function updateTaskImportanceTypeToolbarButtons() {
	var hasSelected = taskImportanceTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(taskImportanceTypeTable, "deleteTaskImportanceTypeBtn", hasSelected);
}

function editTaskImportanceType(rowSelector) {
	var data = {};

	if (rowSelector != null) {
		data = taskImportanceTypeTable.row(rowSelector).data();
	}
	taskImportanceTypeTable.rows().deselect();

	showTaskImportanceTypeDialog(data);
}




function showTaskImportanceTypeDialog(data) {

	$("#titDlgTaskImportanceTypeId").val(data.taskImportanceTypeId);
	$("#titDlgImportanceValue").val(data.importanceValue);
	$("#titDlgName").val(data.name);
	$("#titDlgDescription").val(data.description);

	showModalDialog("taskImportanceTypeDialog");
}

function promptDeleteTaskImportanceType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Task Importance Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteTaskImportanceType();
			   }
	);
}

function deleteTaskImportanceType() {
	var postUrl = "/rest/ignite/v1/task-importance-type/delete";
	var row = taskImportanceTypeTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Task Importance Type has been deleted.", taskImportanceTypeTable,
			function(){	
				taskImportanceTypeTable.rows(".selected").nodes().to$().removeClass("selected");
				updateTaskImportanceTypeToolbarButtons();
			}
	);
}

function saveTaskImportanceType() {
	var postUrl = "/rest/ignite/v1/task-importance-type/new"; //Insert new record
	var postData = {
			mode: $("#titDlgMode").val(),
			taskImportanceTypeId : $("#titDlgTaskImportanceTypeId").val().toUpperCase(),
			importanceValue : $("#titDlgImportanceValue").val(),
			name : $("#titDlgName").val(),
			description : $("#titDlgDescription").val()
	};
	
	var errors = "";

	if ((postData.taskImportanceTypeId == null) || (postData.taskImportanceTypeId == "")) {
		errors += "A Task Importance Type Code is required<br>";
	}

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Task Importance Type Name is required<br>";
	}

	if ((postData.importanceValue == null) || (postData.importanceValue == "")) {
		errors += "An Importance Value is required<br>";
	}	
	
	if ((postData.taskImportanceTypeId != null) && (postData.taskImportanceTypeId != "")) {	
		// This is an update 
		postUrl = "/rest/ignite/v1/task-importance-type";  //Update the record
	} else {
		postData.taskImportanceTypeId = null;  //empty string werk nie
	}		
	
	// validation...
	if (showFormErrors("titDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "taskImportanceTypeDialog", "The Task Importance Type has been saved.", taskImportanceTypeTable);
}
// ***********************************************************************

$(document).ready(function() {
	initializeTaskImportanceTypeTable();
	showIgDeveloperOption();
} );
