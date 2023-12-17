/*
Resource edit page needs to work
meeds start date and edn date options
need to check that there is no overlap
*/

var procedureStatusTable = null;
var somethingChangedInDialog = null;
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
	
function initializeProcedureStatusTable() {
	var queryUrl="/rest/ignite/v1/procedure-status" ;
	console.log(queryUrl);
	var columnsArray = [
		{ data: "procedureStatusId" }, //0
		{ data: "name" },                //1
		{ data: "description" }          //2
	];

	var columnDefsArray = [  
		{
			visible: true,
			targets: [0]
		}

		];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editProcedureStatus(null);
			}
		},
		{
			attr: {
				id: "promptDeleteCLBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteProcedureStatus();
			}
		}
	];

	procedureStatusTable = initializeGenericTable("procedureStatusTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editProcedureStatus(rowSelector);  //Double click
										},
			                    		null,
			                    		31,
			                    		[1,"asc"]
	);
	
	procedureStatusTable.off('deselect');
	procedureStatusTable.on('deselect', function (e, dt, type, indexes) {
		updateClToolbarButtons();
	} );

	procedureStatusTable.off('deselect');
	procedureStatusTable.on('select', function (e, dt, type, indexes) {
		updateClToolbarButtons();
	} );	

	updateClToolbarButtons();

}
//initializeProcedureStatusTable -- End


function updateClToolbarButtons() {
	var hasSelected = procedureStatusTable.rows('.selected').data().length > 0;

	setTableButtonState(procedureStatusTable, "promptDeleteclBtn", hasSelected);	
}
	
function promptDeleteProcedureStatus() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Procedure Status?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteProcedureStatus(procedureStatusTable);
			   }
	);
}

function deleteProcedureStatus(tbl) {
	var postUrl = "/rest/ignite/v1/procedure-status/delete";
	var row = tbl.rows('.selected').data()[0];
console.log(postUrl);
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Procedure Status has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateClToolbarButtons();
			});
	
}

// editProcedureStatus -- Start
function editProcedureStatus(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = procedureStatusTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	procedureStatusTable.rows().deselect();
	
	$("#eclProcedureStatusId").val(data.procedureStatusId);
	$("#eclName").val(data.name);
	$("#eclDescription").val(data.description);
				
	// Set the Save Button to disabled
	setElementEnabled("saveProcedureStatusButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("procedureStatusDialog");
}
// editProcedureStatus -- End

// saveProcedureStatus -- Begin
function saveProcedureStatus() {
	var postUrl = "/rest/ignite/v1/procedure-status/new";
	var postData = {
			procedureStatusId: $("#eclProcedureStatusId").val(),
			name: $("#eclName").val(),
			description: $("#eclDescription").val()
	};
	
	console.log(postData);
	var errors = "";

	// validate
	if (postData.name == "") {
		errors += "A Procedure Status Name is required<br>";
	}

	if (showFormErrors("clDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.procedureStatusId != null) && (postData.procedureStatusId != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/procedure-status";
	} else {
		postData.procedureStatusId = null;  //empty string werk nie
	}
		
	console.log(postUrl);
	console.log(postData);
	
	saveEntry(postUrl, postData, "procedureStatusDialog", "The Procedure Status has been saved.", procedureStatusTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
// saveProcedureStatus -- End


//***********************************************************************
//Did anything change on the form?  Ask user to save or not.

//participantOfficeDialogChanged -- Start
function procedureStatusDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveProcedureStatusButton", true);
}
//participantOfficeDialogChanged -- Start

//closeParticipantOfficeDialog -- Start
function closeProcedureStatusDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
			//Moenie participantOfficeDialog toemaak nie... save eers
		} else {
			closeModalDialog("procedureStatusDialog");
		}
	} else  {
		closeModalDialog("procedureStatusDialog");
	}
}
//closeParticipantOfficeDialog -- End


//setAllSomethingChangedFlags -- Start
function setAllSomethingChangedFlagsFalse() {

	somethingChangedInDialog = false;
	askToSaveDialog = false;
}
//setAllSomethingChangedFlags -- End
//***********************************************************************

// ***********************************************************************
$(document).ready(function() {
	
	// Any initialization
	initializeProcedureStatusTable();
	showIgDeveloperOption();

} );
