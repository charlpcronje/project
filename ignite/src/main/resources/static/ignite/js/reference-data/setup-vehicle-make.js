
    	// TODO: we need an active flag in case a vehicle-make closes
    	// TODO: we also need to identify anyone that uses this vehicle-make if the status is changed to inactive...

var vehicleMakeTable = null;
var vehicleModelTable = null;
var somethingChangedInDialog = null;
	

function initializeVehicleMakeTable() {

	showEmptyVehicleModelPanel();
	
	var queryUrl="/rest/ignite/v1/vehicle-make" ;
//	console.log(queryUrl);
	
	var columnsArray = [
		{ data: "vehicleMakeId" }, //0
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
				editVehicleMake(null);
			}
		},
		{
			attr: {
				id: "deletevvmBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteVehicleMake();
			}
		}
	];

	vehicleMakeTable = initializeGenericTable("vehicleMakeTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editVehicleMake(rowSelector);  //Double click
										},
										null,
										31, 			// pageLength  
										[[1, "asc"]]	// ordering

	);
	
		vehicleMakeTable.off('deselect');
		vehicleMakeTable.on('deselect', function (e, dt, type, indexes) {
		updateVehicleMakeToolbarButtons();
		showEmptyVehicleModelPanel();
	} );

		vehicleMakeTable.off('select');
		vehicleMakeTable.on('select', function (e, dt, type, indexes) {
		updateVehicleMakeToolbarButtons();
		intializeVehicleModelTable(dt.data());
	} );	

	updateVehicleMakeToolbarButtons();

} //initializeVehicleMakeTable -- End


function showEmptyVehicleModelPanel() {
	setDivVisibility("emptyVehicleModelPanel", "block");
	setDivVisibility("vehicleModelPanel", "none");
	$("#selectedVehicleMakeId").val("");	
	$("#selectedVehicleMakeName").val("");	
}


function updateVehicleMakeToolbarButtons() {
	var hasSelected = vehicleMakeTable.rows('.selected').data().length > 0;

	setTableButtonState(vehicleMakeTable, "deletevvmBtn", hasSelected);	
}
	

function promptDeleteVehicleMake() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Vehicle Make?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteVehicleMake();
			   }
	);
}

function deleteVehicleMake() {
	var postUrl = "/rest/ignite/v1/vehicle-make/delete";
	var row = vehicleMakeTable.rows('.selected').data()[0];
console.log(postUrl);
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Vehicle Make has been deleted.", vehicleMakeTable,
			function(){	
				vehicleMakeTable.rows().deselect();
				updateVehicleMakeToolbarButtons();
			});
	
}

// editVehicleMake -- Start
function editVehicleMake(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = vehicleMakeTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	vehicleMakeTable.rows().deselect();
	
	//  MySql-TableName: VehicleMake										   (js3Str)
	$("#vvmVehicleMakeId").val(data.vehicleMakeId); //0
	$("#vvmName").val(data.name);                                     //1
	$("#vvmDescription").val(data.description);                       //2


		
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleMakeButton", false);
	somethingChangedInDialog = false;
	showModalDialog("vehicleMakeDialog");
}
// editVehicleMake -- End

// saveVehicleMake -- Begin
function saveVehicleMake() { 
	
	var postUrl = "/rest/ignite/v1/vehicle-make/new";
	var postData = {
		vehicleMakeId : $("#vvmVehicleMakeId").val()  //0 MySql-TableName: VehicleMake
		,name : $("#vvmName").val()                                                  //1
		,description : $("#vvmDescription").val()                                    //2
	};

	var errors = "";
	

	// validate
	
	if ((postData.name == "") || (postData.name == "")) {
		errors += "A Vehicle Make Name is required<br>";
	}

	if (showFormErrors("vvmVehicleMakeDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.vehicleMakeId != "") && (postData.vehicleMakeId != null)) {
		//This is an update
		postUrl = "/rest/ignite/v1/vehicle-make";
	}
		
//	console.log(postUrl);
//	console.log(postData);
	
	saveEntry(postUrl, postData, "vehicleMakeDialog", "The Vehicle Make has been saved.", vehicleMakeTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
// saveVehicleMake -- End


//***********************************************************************
//Did anything change on the form?  Ask user to save or not.


function vehicleMakeDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleMakeButton", true);
} //vehicleMakeDialogChanged -- END


//closeParticipantOfficeDialog -- Start
function closeVehicleMakeDialog() {
  if (somethingChangedInDialog) {
    // Show a message about unsaved changes
    showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
    DialogConstants.TYPE_CONFIRM, 
    DialogConstants.ALERTTYPE_INFO, 
    function() {
      setDivVisibility("vvmVehicleMakeDlgErrorDiv", "none");
      closeModalDialog("vehicleMakeDialog");
      	somethingChangedInDialog = false;
    });
  } else {
    // Close the dialog without showing a message
    setDivVisibility("vvmVehicleMakeDlgErrorDiv", "none");
    closeModalDialog("vehicleMakeDialog");
  }
}
//closeParticipantOfficeDialog -- End



//********************************************************************************************************
//VehicleModel

//start
function intializeVehicleModelTable(iqData) {
	
	setDivVisibility("emptyVehicleModelPanel", "none");
	setDivVisibility("vehicleModelPanel", "block");
//console.dir(iqData)
	var vehicleMakeId = iqData.vehicleMakeId;
	$("#selectedVehicleMakeId").val(vehicleMakeId);	
	$("#selectedVehicleMakeName").val(iqData.name);
	
	var queryUrl="/rest/ignite/v1/vehicle-make/vehicle-model/" + vehicleMakeId; 
	
	var columnsArray = [
		{ data: "vehicleModelId" }, //0
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
				editVehicleModel(null);
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
				promptDeleteVehicleModel();
			}
		}	                    
	]         		
          		
	
	vehicleModelTable = initializeGenericTable("vehicleModelTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editVehicleModel(rowSelector);
										},
										null,
										31,
										[[1,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);

		vehicleModelTable.off('deselect');
		vehicleModelTable.on('deselect', function (e, dt, type, indexes) {
			updateVehicleModelToolbarButtons();
	} );

		vehicleModelTable.off('select');
		vehicleModelTable.on('select', function (e, dt, type, indexes) {
			updateVehicleModelToolbarButtons();
	} );	

	updateVehicleModelToolbarButtons();
	
}



//editVehicleModel -- Start
function editVehicleModel(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = vehicleModelTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	vehicleModelTable.rows().deselect();
	
	//  MySql-TableName: VehicleModel										   (js3Str)
	$("#vmVehicleModelId").val(data.vehicleModelId); //0
	$("#vmName").val(data.name);                                     //1
	$("#vmDescription").val(data.description);                       //2


		
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleModelButton", false);
	somethingChangedInDialog = false;
	$("#vehicleModelDialogHeader").html("Vehicle Model: " + $("#selectedVehicleMakeName").val());
	showModalDialog("vehicleModelDialog");
}
//editVehicleModel -- End


//saveVehicleModel -- Begin
function saveVehicleModel() { 
	
	var postUrl = "/rest/ignite/v1/vehicle-make/vehicle-model/new";
	var postData = {
		vehicleModelId : $("#vmVehicleModelId").val()  //0 MySql-TableName: VehicleModel
		,vehicleMakeId : $("#selectedVehicleMakeId").val()
		,name : $("#vmName").val()                                                  //1
		,description : $("#vmDescription").val()                                    //2
	};

	var errors = "";
	

	// validate
	if ((postData.name == "") || (postData.name == "")) {
		errors += "A Model Name is required<br>";
	}

	if (showFormErrors("vmVehicleModelDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.vehicleModelId != "") && (postData.vehicleModelId != null)) {
		//This is an update
		postUrl = "/rest/ignite/v1/vehicle-make/vehicle-model";
	}
		
//	console.log(postUrl);
//	console.log(postData);
	
	saveEntry(postUrl, postData, "vehicleModelDialog", "The Model has been saved.", vehicleModelTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
//saveVehicleModel -- End




function updateVehicleModelToolbarButtons() {
	var hasSelected = vehicleModelTable.rows('.selected').data().length > 0;

	setTableButtonState(vehicleModelTable, "deletevmBtn", hasSelected);	
}
	
function promptDeleteVehicleModel() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Vehicle Model?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteVehicleModel();
			   }
	);
}

function deleteVehicleModel() {
	var postUrl = "/rest/ignite/v1/vehicle-make/vehicle-model/delete";
	var row = vehicleModelTable.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Model has been deleted.", vehicleModelTable,
			function(){	
				vehicleModelTable.rows().deselect();
				updateVehicleModelToolbarButtons();
			});
	
}



function closeVehicleModelDialog() {
	if (somethingChangedInDialog) {
	  // Show a message about unsaved changes
	  showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
	  DialogConstants.TYPE_CONFIRM, 
	  DialogConstants.ALERTTYPE_INFO, 
	  function() {
	    setDivVisibility("vmVehicleModelDlgErrorDiv", "none");
	    closeModalDialog("vehicleModelDialog");
	    	somethingChangedInDialog = false;
	  });
	} else {
	  // Close the dialog without showing a message
	  setDivVisibility("vmVehicleModelDlgErrorDiv", "none");
	  closeModalDialog("vehicleModelDialog");
	}
} //closeVehicleModelDialog -- End


function vehicleModelDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleModelButton", true);
} //vehicleModelDialogChanged -- END






// ***********************************************************************
$(document).ready(function() {
	
	// Any initialization
	initializeVehicleMakeTable();
	showIgDeveloperOption();

} );
