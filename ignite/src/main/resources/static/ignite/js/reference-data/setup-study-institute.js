



var studyInstituteTable = null;
var instituteQualificationTable = null;
var somethingChangedInDialog = null;
	

function initializeStudyInstituteTable() {

	showEmptyInstituteQualificationPanel();
	
	var queryUrl="/rest/ignite/v1/study-institute/find-all" ;
//	console.log(queryUrl);
	
	var columnsArray = [
		{ data: "studyInstituteId" }, //0
		{ data: "name" },                //1
		{ data: "shortName" },           //
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
				editStudyInstitute(null);
			}
		},
		{
			attr: {
				id: "deletessiBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteStudyInstitute();
			}
		}
	];

	studyInstituteTable = initializeGenericTable("studyInstituteTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editStudyInstitute(rowSelector);  //Double click
										},
										null,
										31, 			// pageLength  
										[[1, "asc"]]	// ordering

	);
	
		studyInstituteTable.off('deselect');
		studyInstituteTable.on('deselect', function (e, dt, type, indexes) {
		updateStudyInstituteToolbarButtons();
		showEmptyInstituteQualificationPanel();
	} );

		studyInstituteTable.off('select');
		studyInstituteTable.on('select', function (e, dt, type, indexes) {
		updateStudyInstituteToolbarButtons();
		intializeInstituteQualificationTable(dt.data());
	} );	

	updateStudyInstituteToolbarButtons();

} //initializeStudyInstituteTable -- End


function showEmptyInstituteQualificationPanel() {
	setDivVisibility("emptyInstituteQualificationPanel", "block");
	setDivVisibility("instituteQualificationPanel", "none");
	$("#selectedStudyInstituteId").val("");	
	$("#selectedStudyInstituteName").val("");	
}


function updateStudyInstituteToolbarButtons() {
	var hasSelected = studyInstituteTable.rows('.selected').data().length > 0;

	setTableButtonState(studyInstituteTable, "deletessiBtn", hasSelected);	
}
	

function promptDeleteStudyInstitute() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Study Institute?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteStudyInstitute();
			   }
	);
}

function deleteStudyInstitute() {
	var postUrl = "/rest/ignite/v1/study-institute/delete";
	var row = studyInstituteTable.rows('.selected').data()[0];
console.log(postUrl);
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Study Institute has been deleted.", studyInstituteTable,
			function(){	
				studyInstituteTable.rows().deselect();
				updateStudyInstituteToolbarButtons();
			});
	
}

// editStudyInstitute -- Start
function editStudyInstitute(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = studyInstituteTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	studyInstituteTable.rows().deselect();
	
	//  MySql-TableName: StudyInstitute										   (js3Str)
	$("#ssiStudyInstituteId").val(data.studyInstituteId); //0
	$("#ssiName").val(data.name);                                     //1
	$("#ssiDescription").val(data.description);                       //2


		
	// Set the Save Button to disabled
	setElementEnabled("saveStudyInstituteButton", false);
	somethingChangedInDialog = false;
	showModalDialog("studyInstituteDialog");
}
// editStudyInstitute -- End

// saveStudyInstitute -- Begin
function saveStudyInstitute() { 
	
	var postUrl = "/rest/ignite/v1/study-institute/new";
	var postData = {
		studyInstituteId : $("#ssiStudyInstituteId").val()  //0 MySql-TableName: StudyInstitute
		,name : $("#ssiName").val()                                                  //1
		,shortName : $("#ssiShortName").val()                                                  //1
		,description : $("#ssiDescription").val()                                    //2
	};

	var errors = "";
	

	// validate
	
	if ((postData.name == "") || (postData.name == "")) {
		errors += "A Study Institute Name is required<br>";
	}

	if (showFormErrors("ssiStudyInstituteDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.studyInstituteId != "") && (postData.studyInstituteId != null)) {
		//This is an update
		postUrl = "/rest/ignite/v1/study-institute";
	}
		
//	console.log(postUrl);
//	console.log(postData);
	
	saveEntry(postUrl, postData, "studyInstituteDialog", "The Study Institute has been saved.", studyInstituteTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
// saveStudyInstitute -- End


//***********************************************************************
//Did anything change on the form?  Ask user to save or not.


function studyInstituteDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveStudyInstituteButton", true);
} //studyInstituteDialogChanged -- END


//closeParticipantOfficeDialog -- Start
function closeStudyInstituteDialog() {
  if (somethingChangedInDialog) {
    // Show a message about unsaved changes
    showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
    DialogConstants.TYPE_CONFIRM, 
    DialogConstants.ALERTTYPE_INFO, 
    function() {
      setDivVisibility("ssiStudyInstituteDlgErrorDiv", "none");
      closeModalDialog("studyInstituteDialog");
      	somethingChangedInDialog = false;
    });
  } else {
    // Close the dialog without showing a message
    setDivVisibility("ssiStudyInstituteDlgErrorDiv", "none");
    closeModalDialog("studyInstituteDialog");
  }
}
//closeParticipantOfficeDialog -- End



//***********************************************************************
//InstituteQualification

//start
function intializeInstituteQualificationTable(iqData) {
	
	setDivVisibility("emptyInstituteQualificationPanel", "none");
	setDivVisibility("instituteQualificationPanel", "block");
//console.dir(iqData)
	var studyInstituteId = iqData.studyInstituteId;
	$("#selectedStudyInstituteId").val(studyInstituteId);	
	$("#selectedStudyInstituteName").val(iqData.name);

	$("#pageHeaderInstituteQualification").html("<i class=\"fas fa-user-graduate\"></i> Setup: Qualification: " + $("#selectedStudyInstituteName").val());
	
	var queryUrl="/rest/ignite/v1/institute-qualification/list-view-institute-qualification-for-study-institute/" + studyInstituteId; 
	
	var columnsArray = [
		{ data: "instituteQualificationId" }, //0
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
				editInstituteQualification(null);
			}
		},
		{
			attr: {
				id: "deleteiqBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteInstituteQualification();
			}
		}	                    
	]         		
          		
	
	instituteQualificationTable = initializeGenericTable("instituteQualificationTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editInstituteQualification(rowSelector);
										},
										null,
										31,
										[[1,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);

		instituteQualificationTable.off('deselect');
		instituteQualificationTable.on('deselect', function (e, dt, type, indexes) {
			updateInstituteQualificationToolbarButtons();
	} );

		instituteQualificationTable.off('select');
		instituteQualificationTable.on('select', function (e, dt, type, indexes) {
			updateInstituteQualificationToolbarButtons();
	} );	

	updateInstituteQualificationToolbarButtons();
	
}



//editInstituteQualification -- Start
function editInstituteQualification(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = instituteQualificationTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	instituteQualificationTable.rows().deselect();
	
	//  MySql-TableName: InstituteQualification										   (js3Str)
	$("#qiInstituteQualificationId").val(data.instituteQualificationId); //0
	$("#qiName").val(data.name);                                     //1
	$("#qiDescription").val(data.description);                       //2


		
	// Set the Save Button to disabled
	setElementEnabled("saveInstituteQualificationButton", false);
	somethingChangedInDialog = false;
	$("#instituteQualificationDialogHeader").html("Institute Qualification: " + $("#selectedStudyInstituteName").val());
	showModalDialog("instituteQualificationDialog");
}
//editInstituteQualification -- End


//saveInstituteQualification -- Begin
function saveInstituteQualification() { 
	
	var postUrl = "/rest/ignite/v1/institute-qualification/new";
	var postData = {
		instituteQualificationId : $("#qiInstituteQualificationId").val()  //0 MySql-TableName: InstituteQualification
		,studyInstituteId : $("#selectedStudyInstituteId").val()
		,name : $("#qiName").val()                                                  //1
		,description : $("#qiDescription").val()                                    //2
	};

	var errors = "";
	

	// validate
	if ((postData.name == "") || (postData.name == "")) {
		errors += "A Qualification Name is required<br>";
	}

	if (showFormErrors("qiInstituteQualificationDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.instituteQualificationId != "") && (postData.instituteQualificationId != null)) {
		//This is an update
		postUrl = "/rest/ignite/v1/institute-qualification";
	}
		
//	console.log(postUrl);
//	console.log(postData);
	
	saveEntry(postUrl, postData, "instituteQualificationDialog", "The Qualification has been saved.", instituteQualificationTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
//saveInstituteQualification -- End




function updateInstituteQualificationToolbarButtons() {
	var hasSelected = instituteQualificationTable.rows('.selected').data().length > 0;

	setTableButtonState(instituteQualificationTable, "deleteqiBtn", hasSelected);	
}
	
function promptDeleteInstituteQualification() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Qualification?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteInstituteQualification();
			   }
	);
}

function deleteInstituteQualification() {
	var postUrl = "/rest/ignite/v1/institute-qualification/delete";
	var row = instituteQualificationTable.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Qualification has been deleted.", instituteQualificationTable,
			function(){	
				instituteQualificationTable.rows().deselect();
				updateInstituteQualificationToolbarButtons();
			});
	
}



function closeInstituteQualificationDialog() {
	if (somethingChangedInDialog) {
	  // Show a message about unsaved changes
	  showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
	  DialogConstants.TYPE_CONFIRM, 
	  DialogConstants.ALERTTYPE_INFO, 
	  function() {
	    setDivVisibility("qiInstituteQualificationDlgErrorDiv", "none");
	    closeModalDialog("instituteQualificationDialog");
	    	somethingChangedInDialog = false;
	  });
	} else {
	  // Close the dialog without showing a message
	  setDivVisibility("qiInstituteQualificationDlgErrorDiv", "none");
	  closeModalDialog("instituteQualificationDialog");
	}
} //closeInstituteQualificationDialog -- End


function instituteQualificationDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveInstituteQualificationButton", true);
} //instituteQualificationDialogChanged -- END






// ***********************************************************************
$(document).ready(function() {
	
	// Any initialization
	initializeStudyInstituteTable();
	showIgDeveloperOption();

} );
