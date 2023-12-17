/*
Resource edit page needs to work
meeds start date and edn date options
need to check that there is no overlap
*/

var competencyLevelTable = null;
var somethingChangedInDialog = null;
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
	
function initializeCompetencyLevelTable() {
	var queryUrl="/rest/ignite/v1/competency-level" ;
	console.log(queryUrl);
	var columnsArray = [
		{ data: "competencyLevelId" }, //0
		{ data: "name" },                //1
		{ data: "description" }          //2
	];

	var columnDefsArray = [  
		{
			visible: false,
			targets: [0]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editCompetencyLevel(null);
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
				promptDeleteCompetencyLevel();
			}
		}
	];

	competencyLevelTable = initializeGenericTable("competencyLevelTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editCompetencyLevel(rowSelector);  //Double click
										},
										null,
										31,
										[1,"asc"]
	);
	
	competencyLevelTable.off('deselect');
	competencyLevelTable.on('deselect', function (e, dt, type, indexes) {
		updateClToolbarButtons();
	} );

	competencyLevelTable.off('select');
	competencyLevelTable.on('select', function (e, dt, type, indexes) {
		updateClToolbarButtons();
	} );	

	updateClToolbarButtons();

}
//initializeCompetencyLevelTable -- End


function updateClToolbarButtons() {
	var hasSelected = competencyLevelTable.rows('.selected').data().length > 0;

	setTableButtonState(competencyLevelTable, "promptDeleteclBtn", hasSelected);	
}
	

function promptDeleteCompetencyLevel() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Competency Level?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteCompetencyLevel(competencyLevelTable);
			   }
	);
}


function deleteCompetencyLevel(tbl) {
	var postUrl = "/rest/ignite/v1/competency-level/delete";
	var row = tbl.rows('.selected').data()[0];
console.log(postUrl);
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Competency Level has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateClToolbarButtons();
			});
	
}


// editCompetencyLevel -- Start
function editCompetencyLevel(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = competencyLevelTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	competencyLevelTable.rows().deselect();
	
	$("#eclCompetencyLevelId").val(data.competencyLevelId);
	$("#eclName").val(data.name);
	$("#eclDescription").val(data.description);
		
	// Set the Save Button to disabled
	setElementEnabled("saveCompetencyLevelButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("competencyLevelDialog");
}
// editCompetencyLevel -- End


// saveCompetencyLevel -- Begin
function saveCompetencyLevel() {
	var postUrl = "/rest/ignite/v1/competency-level/new";
	var postData = {
			competencyLevelId: $("#eclCompetencyLevelId").val().trim().toUpperCase(),
			name: $("#eclName").val(),
			description: $("#eclDescription").val()
	};
	
	console.log(postData);
	var errors = "";

	// validate

	if (postData.name == "") {
		errors += "A Competency Level Name is required<br>";
	}

	if (showFormErrors("clDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.competencyLevelId != null) && (postData.competencyLevelId != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/competency-level";
	} else {
		postData.competencyLevelId = null;  //empty string werk nie
	}
		
	console.log(postUrl);
	console.log(postData);
	
	saveEntry(postUrl, postData, "competencyLevelDialog", "The Competency Level has been saved.", competencyLevelTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
// saveCompetencyLevel -- End


//***********************************************************************
//Did anything change on the form?  Ask user to save or not.

//participantOfficeDialogChanged -- Start
function competencyLevelDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveCompetencyLevelButton", true);
}
//participantOfficeDialogChanged -- Start

//closeParticipantOfficeDialog -- Start
function closeCompetencyLevelDialog() {
  if (somethingChangedInDialog) {
    // Show a message about unsaved changes
    showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
    DialogConstants.TYPE_CONFIRM, 
    DialogConstants.ALERTTYPE_INFO, 
    function(e) {
      setDivVisibility("icDlgErrorDiv", "none");
      closeModalDialog("competencyLevelDialog");
      	somethingChangedInDialog = false;
    });
  } else {
    // Close the dialog without showing a message
    closeModalDialog("competencyLevelDialog");
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
	initializeCompetencyLevelTable();

} );
