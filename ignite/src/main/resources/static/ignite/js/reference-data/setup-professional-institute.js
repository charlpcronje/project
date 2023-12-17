
var professionalInstituteTable = null;
var somethingChangedInDialog = null;
	
function initializeProfessionalInstituteTable() {
	
	var queryUrl="/rest/ignite/v1/professional-institute/find-all" ;
//	console.log(queryUrl);
	
	var columnsArray = [
		{ data: "professionalInstituteId" }, //0
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
				editProfessionalInstitute(null);
			}
		},
		{
			attr: {
				id: "deletespiBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteProfessionalInstitute();
			}
		}
	];

	professionalInstituteTable = initializeGenericTable("professionalInstituteTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editProfessionalInstitute(rowSelector);  //Double click
										},
										null,
										31
	);
	
		professionalInstituteTable.off('deselect');
		professionalInstituteTable.on('deselect', function (e, dt, type, indexes) {
		updateProfessionalInstituteToolbarButtons();
	} );

		professionalInstituteTable.off('select');
		professionalInstituteTable.on('select', function (e, dt, type, indexes) {
		updateProfessionalInstituteToolbarButtons();
	} );	

	updateProfessionalInstituteToolbarButtons();

}
//initializeProfessionalInstituteTable -- End


function updateProfessionalInstituteToolbarButtons() {
	var hasSelected = professionalInstituteTable.rows('.selected').data().length > 0;

	setTableButtonState(professionalInstituteTable, "deletespiBtn", hasSelected);	
}
	
function promptDeleteProfessionalInstitute() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Professional Institute?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteProfessionalInstitute();
			   }
	);
}

function deleteProfessionalInstitute() {
	var postUrl = "/rest/ignite/v1/professional-institute/delete";
	var row = professionalInstituteTable.rows('.selected').data()[0];
console.log(postUrl);
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Professional Institute has been deleted.", professionalInstituteTable,
			function(){	
				professionalInstituteTable.rows().deselect();
				updateProfessionalInstituteToolbarButtons();
			});
	
}

// editProfessionalInstitute -- Start
function editProfessionalInstitute(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = professionalInstituteTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	professionalInstituteTable.rows().deselect();
	
	//  MySql-TableName: ProfessionalInstitute										   (js3Str)
	$("#spiProfessionalInstituteId").val(data.professionalInstituteId); //0
	$("#spiName").val(data.name);                                     //1
	$("#spiDescription").val(data.description);                       //2


		
	// Set the Save Button to disabled
	setElementEnabled("saveProfessionalInstituteButton", false);
	somethingChangedInDialog = false;
	showModalDialog("professionalInstituteDialog");
}
// editProfessionalInstitute -- End

// saveProfessionalInstitute -- Begin
function saveProfessionalInstitute() { 
	
	var postUrl = "/rest/ignite/v1/professional-institute/new";
	var postData = {
		professionalInstituteId : $("#spiProfessionalInstituteId").val()  //0 MySql-TableName: ProfessionalInstitute
		,name : $("#spiName").val()                                                  //1
		,description : $("#spiDescription").val()                                    //2
	};

	var errors = "";
	

	// validate
	
	if ((postData.name == "") || (postData.name == "")) {
		errors += "A Professional Institute Name is required<br>";
	}

	if (showFormErrors("spiProfessionalInstituteDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.professionalInstituteId != "") && (postData.professionalInstituteId != null)) {
		//This is an update
		postUrl = "/rest/ignite/v1/professional-institute";
	}
		
//	console.log(postUrl);
//	console.log(postData);
	
	saveEntry(postUrl, postData, "professionalInstituteDialog", "The Professional Institute has been saved.", professionalInstituteTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
// saveProfessionalInstitute -- End


//***********************************************************************
//Did anything change on the form?  Ask user to save or not.

//participantOfficeDialogChanged -- Start
function professionalInstituteDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveProfessionalInstituteButton", true);
}
//participantOfficeDialogChanged -- Start


//closeParticipantOfficeDialog -- Start
function closeProfessionalInstituteDialog() {
  if (somethingChangedInDialog) {
    // Show a message about unsaved changes
    showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
    DialogConstants.TYPE_CONFIRM, 
    DialogConstants.ALERTTYPE_INFO, 
    function() {
      setDivVisibility("spiProfessionalInstituteDlgErrorDiv", "none");
      closeModalDialog("professionalInstituteDialog");
      	somethingChangedInDialog = false;
    });
  } else {
    // Close the dialog without showing a message
    setDivVisibility("spiProfessionalInstituteDlgErrorDiv", "none");
    closeModalDialog("professionalInstituteDialog");
  }
}
//closeParticipantOfficeDialog -- End



//***********************************************************************

// ***********************************************************************
$(document).ready(function() {
	
	// Any initialization
	initializeProfessionalInstituteTable();
	showIgDeveloperOption();

} );
