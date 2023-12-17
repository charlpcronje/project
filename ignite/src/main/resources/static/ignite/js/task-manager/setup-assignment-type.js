var assignmnentTable = null;
var taskTypeTable = null;

// Assignment Type  - Start
function initializeAssignmentTypeTable() {
	hideTaskTypeTable();
	
	var columnArray = [
		{ data : "assignmentTypeId" },
		{ data : "name" },
		{ data : "description" }
	];
	
	var columnDefsArray = [
		{
			visible : false,
			targets: 0
		},
	
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = shortenText(data, 50);
				}
				return data;
			},
			targets: 1
		}
		
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editAssignmentType(null);
			}
		},
		{
			attr: {
				id: "promptDeleteAssignmentTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteAssignmentType();
			}
		}
	];

	assignmentTypeTable = initializeGenericTable("assignmentTypeTable", 
			                            "/rest/ignite/v1/assignment-type",
			                            columnArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editAssignmentType(rowSelector);
										},
										null,
										25,
										[[1,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
								);

	assignmentTypeTable.off('deselect');
	assignmentTypeTable.on('deselect', function () {
		hideTaskTypeTable();
		updateAssignmentTypeToolbarButtons();
	} );

	assignmentTypeTable.off('select');
	assignmentTypeTable.on('select', function ( e, dt, node, config) {
		initializeTaskTypeTable(dt.data());
		updateAssignmentTypeToolbarButtons();
	} );

	updateAssignmentTypeToolbarButtons();
	//repeatCheckClicked()
}     //function initializeAssignmentTypeTable() {


function updateAssignmentTypeToolbarButtons() {
	var hasSelected = assignmentTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(assignmentTypeTable, "promptDeleteAssignmentTypeBtn", hasSelected);	
}

function editAssignmentType(rowSelector) {
	var data = {};
			
	if ((rowSelector != undefined) && (rowSelector != null)) {
		data = assignmentTypeTable.row(rowSelector).data();
	};
	
	assignmentTypeTable.rows().deselect();
	
	$("#aDlgAssignmentTypeId").val(data.assignmentTypeId);
	$("#aDlgName").val(data.name);
	$("#aDlgDescription").val(data.description);

	$("#aDlgAssignmentTypeId").prop("readonly", true);
	
	showModalDialog("assignmentTypeDialog");
}


function saveAssignmentType() {
	var postUrl = "/rest/ignite/v1/assignment-type/new";
	var postData = {
			assignmentTypeId: $("#aDlgAssignmentTypeId").val(),
			name: $("#aDlgName").val(),
			description: $("#aDlgDescription").val()
	};
	
	var errors = "";
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A name is required<br>";
	}
	
	// validation...
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	//Is the code readonly? If true: records already exists
	if ((postData.assignmentTypeId != null) && (postData.assignmentTypeId != "")) {
		//This is an update
		postUrl = "/rest/ignite/v1/assignment-type";
	}

	//saveEntry(postUrl, postData, "expenseTypeParentDialog", "The Expense Parent Type has been saved.", expenseTypeParentTable);
	//expenseTypeParentTable.rows(".selected").nodes().to$().removeClass("selected");
	//updateExpenseTypeParentToolbarButtons();
	
	saveEntry(postUrl, postData, "assignmentTypeDialog", "The Assignment Type has been saved.", assignmentTypeTable);
	// Select the newly saved record 
}

function promptDeleteAssignmentType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Assignment Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteAssignmentType(assignmentTypeTable);
			   }
	);
}

function deleteAssignmentType(tbl) {
	var postUrl = "/rest/ignite/v1/assignment-type/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Assignment Type has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateAssignmentTypeToolbarButtons();
			}
	);
}

// Assignment Type - End






// Task Type - Start

function hideTaskTypeTable() {
	setDivVisibility("taskTypeEmptyPanel", "block");
	setDivVisibility("taskTypePanel", "none");
}

function initializeTaskTypeTable(row) {
	setDivVisibility("taskTypeEmptyPanel", "none");
	setDivVisibility("taskTypePanel", "block");
	
	var columnArray = [
		{ data : "taskTypeId" },          //0
		{ data : "assignmentTypeId" },
		{ data : "taskOrderNumber" },
		{ data : "name" },
		{ data : "description" },
		{ data : "durationDays" },
		{ data : "durationHours" },
		{ data : "timeOfDay" }
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1]
		},
		
	];

	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editTaskType(null, row.assignmentTypeId);
			}
		},
		{
			attr: {
				id: "promptDeleteTaskTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteTaskType();
			}
		}
	];

//	console.log("/rest/ignite/v1/task-type/by-parent/" + row.assignmentTypeId);
	
	taskTypeTable = initializeGenericTable("taskTypeTable", 
			                            "/rest/ignite/v1/task-type/by-parent/" + row.assignmentTypeId,
			                            columnArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editTaskType(rowSelector, row.assignmentTypeId);
										},
										null,
										25,
										[[2,"asc"]] //Order by column 2 ascending, normally defaults to column 1 ascending
								);
								
	taskTypeTable.off('deselect');
	taskTypeTable.on('deselect', function (e, dt, type, indexes) {
		updateTaskTypeToolbarButtons();
	} );

	taskTypeTable.off('select');
	taskTypeTable.on('select', function (e, dt, type, indexes) {
		updateTaskTypeToolbarButtons();
	} );
	
	updateTaskTypeToolbarButtons();
}   //function initializeTaskTypeTable(row) {


function updateTaskTypeToolbarButtons() {
	var hasSelected = taskTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(taskTypeTable, "promptDeleteTaskTypeBtn", hasSelected);	
}

function promptDeleteTaskType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Task Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteTaskType(taskTypeTable);
			   }
	);
} //promptDeleteTaskType


function deleteTaskType(tbl) {
	var postUrl = "/rest/ignite/v1/task-type/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Task Type has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateTaskTypeToolbarButtons();
			}
	);
} //deleteTaskType


function editTaskType(rowSelector, currentAssignmentTypeId) {
	
	var data = {};

	if ((rowSelector !== undefined) && (rowSelector != null)) {
			data = taskTypeTable.row(rowSelector).data();
	}
	
	taskTypeTable.rows().deselect();

	$("#ttDlgTaskTypeId").val(data.taskTypeId);
	$("#ttDlgAssignmentTypeId").val(currentAssignmentTypeId);
	$("#ttDlgTaskOrderNumber").val(data.taskOrderNumber);
	$("#ttDlgName").val(data.name);
	$("#ttDlgDescription").val(data.description);
	
//	$("#ttCheckbox").prop("checked", ((data.repeatCode != null) && (data.repeatCode != '')));

	$("#ttDlgDurationDays").val(data.durationDays);	
	$("#ttDlgDurationHours").val(data.durationHours);	
	$("#ttDlgTimeOfDay").val(data.timeOfDay);	
	
	if ((data.timeOfDay != null) && (data.timeOfDay != '')) {
		document.getElementById('ttDlgTimeOfDayH').value = data.timeOfDay.substring(11, 13);
		document.getElementById('ttDlgTimeOfDayM').value = data.timeOfDay.substring(14, 16);
	}else {
		document.getElementById('ttDlgTimeOfDayH').value = '';
		document.getElementById('ttDlgTimeOfDayM').value = '';
	}
	
// Maak hierdie true	
	$("#ttDlgTaskTypeId").prop("readonly", true);
	$("#ttDlgAssignmentTypeId").prop("readonly", true);
	$("#ttDlgTimeOfDay").prop("readonly", true);        

	showModalDialog("taskTypeDialog");
	
}

function saveTaskType() {

	
	var element = document.getElementById('ttDlgTimeOfDayH');  //$("#ttDlgTimeOfDayH").val();   //document.getElementById('ttDlgTimeOfDayH');  //
    var dieLengte = element.value.length;
    var dieNommer = element.value;
    if (dieLengte == 1) {
    	document.getElementById('ttDlgTimeOfDayH').value = '0' + dieNommer
    }
    var element2 = document.getElementById('ttDlgTimeOfDayM');  //$("#ttDlgTimeOfDayH").val();   //document.getElementById('ttDlgTimeOfDayH');  //
    var dieLengte2 = element2.value.length;
    var dieNommer2 = element2.value;
    if (dieLengte2 == 1) {
    	document.getElementById('ttDlgTimeOfDayM').value = '0' + dieNommer2
    }
	
    var dieUur = jQuery('#ttDlgTimeOfDayH').attr('value');
    var dieMinute = jQuery('#ttDlgTimeOfDayM').attr('value');
    
//    console.log('dieUur: ' + dieUur);
//    console.log('dieMinute: ' + dieMinute)
    
	if (dieUur == '') {
		document.getElementById('ttDlgTimeOfDay').value = '';
		document.getElementById('ttDlgTimeOfDayM').value = '';
	} else {
		if (dieMinute == ''){
			document.getElementById('ttDlgTimeOfDayM').value = '00';
		}
		document.getElementById('ttDlgTimeOfDay').value =  '1970-01-01T' + document.getElementById('ttDlgTimeOfDayH').value + ":" + document.getElementById('ttDlgTimeOfDayM').value + ':00.000+0000'
		
		
	};
	
	
	
	var postUrl = "/rest/ignite/v1/task-type/new";
	var postData = {
			taskTypeId: $("#ttDlgTaskTypeId").val(),
			assignmentTypeId: $("#ttDlgAssignmentTypeId").val(),
			taskOrderNumber: $("#ttDlgTaskOrderNumber").val(),
			name: $("#ttDlgName").val(),
			description: $("#ttDlgDescription").val(),
			durationDays: $("#ttDlgDurationDays").val(),
			durationHours: $("#ttDlgDurationHours").val(),
			timeOfDay: $("#ttDlgTimeOfDay").val()
			
	};
	
	var errors = "";
	
	if ((postData.taskOrderNumber == null) || (postData.taskOrderNumber == "")) {
		errors += "A Task Order Number is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Task Name is required<br>";
	}

	// validation...
	if (showFormErrors("eDlgErrorDiv", errors)) {
		return;
	}

	//Is the taskTypeId readonly? If it is, then the record already exists.
	if ((postData.taskTypeId != null) && (postData.taskTypeId != "")) {
		// This is an update
		
		postUrl = "/rest/ignite/v1/task-type";
	}
//	if ((isTopLevel === undefined) || (isTopLevel == null)) {
//		isTopLevel = true;
//	}
	console.log('posturl: ' + postUrl);
	//	var tbl = isTopLevel ? expenseTypeTable : taskTypeTable;
	saveEntry(postUrl, postData, "taskTypeDialog", "The Task Type has been saved.", taskTypeTable);
	
}  //saveTaskType

// Task Type (Sub Type) - End

function durationDaysKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('ttDlgDurationDays').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDlgDurationDays').value = ''
				console.log('hier')
	};
	if (dieNommer > 0) {
		document.getElementById('ttDlgDurationHours').value = ''
	}
}	


function durationHoursKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('ttDlgDurationHours').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDlgDurationHours').value = ''
	};
	if (dieNommer > 0) {
		document.getElementById('ttDlgDurationDays').value = ''
	}
}	


function timeOfDayKeyupH() {
	var dieNommer;
	dieNommer = +document.getElementById('ttDlgTimeOfDayH').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDlgTimeOfDayH').value = ''
	}else{
		if (dieNommer > 23) {
			document.getElementById('ttDlgTimeOfDayH').value = ''
		}
	};	
}


function timeOfDayKeyupM() {
	var dieNommer;
	dieNommer = +document.getElementById('ttDlgTimeOfDayM').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDlgTimeOfDayM').value = ''
	}else{
		if (dieNommer > 59) {
			document.getElementById('ttDlgTimeOfDayM').value = ''
		}
	};	
}





// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeAssignmentTypeTable();
	
} );
