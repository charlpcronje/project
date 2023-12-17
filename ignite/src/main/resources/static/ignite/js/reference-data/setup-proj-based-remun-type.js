var projBasedRemunTypeTable = null;
var somethingChangedInDialog = null;

// ************* initializeProjBasedRemunTypeTable Start ************* // 
function initializeProjBasedRemunTypeTable() {

	var columnsArray = [
	           		 { data: "projBasedRemunTypeId" }     //0 MySql-TableName: ProjBasedRemunType
	           		,{ data: "remunerationIntervalCode" } //1
	           		,{ data: "unitTypeCode" }             //2
	           		,{ data: "name" }                     //3
	           		,{ data: "description" }              //4
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
			action: function() {
				editProjBasedRemunType(null);
			}
		},
		{
			attr: {
				id: "promptDeleteProjBasedRemunTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteProjBaseRemunType();
			}
		}
	];

	console.log("/rest/ignite/v1/proj-based-remun-type");
	projBasedRemunTypeTable = initializeGenericTable("projBasedRemunTypeTable",
		"/rest/ignite/v1/proj-based-remun-type",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editProjBasedRemunType(rowSelector);
		},
		null,
		31,
		[1,"asc"]
	);

	projBasedRemunTypeTable.off('deselect');
	projBasedRemunTypeTable.on('deselect', function() {
		updateProjBasedRemunTypeToolbarButtons();
	});

	projBasedRemunTypeTable.off('select');
	projBasedRemunTypeTable.on('select', function(e, dt, node, config) {
		updateProjBasedRemunTypeToolbarButtons();
	});

	updateProjBasedRemunTypeToolbarButtons();
}

function updateProjBasedRemunTypeToolbarButtons() {
	var hasSelected = projBasedRemunTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(projBasedRemunTypeTable, "promptDeleteProjBasedRemunTypeBtn", hasSelected);
}

function editProjBasedRemunType(rowSelector) {
	console.log("editProjBasedRemunType");
	var data = {};

	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = projBasedRemunTypeTable.row(rowSelector).data();
	}
	projBasedRemunTypeTable.rows().deselect();

	$("#rProjBasedRemunTypeId").val(data.projBasedRemunTypeId);
	$("#rName").val(data.name);
	$("#rDescription").val(data.description);

	populateSelect("rRemunerationIntervalCode",
		"/rest/ignite/v1/remuneration-interval",
		"remunerationIntervalCode",
		"name",
		data.remunerationIntervalCode,
		false,
		null);

	populateSelect("rUnitTypeCode",
		"/rest/ignite/v1/unit-type",
		"unitTypeCode",
		"name",
		data.unitTypeCode,
		false,
		null);

	// Set the Save Button to disabled
	setElementEnabled("saveProjBasedRemunTypeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("projBasedRemunTypeDialog");
}

function promptDeleteProjBaseRemunType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected ProjBased Remunetion Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteProjBasedRemunType(projBasedRemunTypeTable);
				}
	);
}

function deleteProjBasedRemunType(tbl) {
	var postUrl = "/rest/ignite/v1/proj-based-remun-type/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Based Remuneration Type has been deleted.", tbl,
		function() {
			tbl.rows().deselect();
			updateProjBasedRemunTypeToolbarButtons();
		});
}

function saveProjBasedRemunType() {
	var postUrl = "/rest/ignite/v1/proj-based-remun-type/new";
	var postData = {
		projBasedRemunTypeId: $("#rProjBasedRemunTypeId").val().trim().toUpperCase(),
		unitTypeCode: $("#rUnitTypeCode").val(),
		remunerationIntervalCode: $("#rRemunerationIntervalCode").val(),
		name: $("#rName").val(),
		description: $("#rDescription").val()
	};

	var errors = "";

	// validation...
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	if ((postData.unitTypeCode == null) || (postData.unitTypeCode == "")) {
		errors += "A Unit Type is required<br>";
	}

	if ((postData.remunerationIntervalCode == null) || (postData.remunerationIntervalCode == "")) {
		errors += "A Remuneration Interval is required<br>";
	}

	if (showFormErrors("rDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.projBasedRemunTypeId != null) && (postData.projBasedRemunTypeId != "")) {	
		// This is an update
		postUrl = "/rest/ignite/v1/proj-based-remun-type";
	} else {
		postData.projBasedRemunTypeId = null;  //empty string werk nie
	}

	saveEntry(postUrl, postData, "projBasedRemunTypeDialog", "The ProjBased Remuneration Type has been saved.", projBasedRemunTypeTable);
}

//projBasedRemunTypeDialogChanged -- Start
function projBasedRemunTypeDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveProjBasedRemunTypeButton", true);
}
//projBasedRemunTypeDialogChanged -- Start


//closeProjBasedRemunTypeDialog -- Start
function closeProjBasedRemunTypeDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("projBasedRemunTypeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");		
		closeModalDialog("projBasedRemunTypeDialog");
	}
}//closeProjBasedRemunTypeDialog -- End

//		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
//		if (stayAndSaveFirst) {
//			//Moenie closeProjBasedRemunTypeDialog toemaak nie... save eers
//		} else {
//			closeModalDialog("projBasedRemunTypeDialog");
//		}
//	} else  {
//		closeModalDialog("projBasedRemunTypeDialog");
//	}
//}
//closeProjBasedRemunTypeDialog -- End
//************* rtProjBasedTab End ************* // 


// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeProjBasedRemunTypeTable();
	showIgDeveloperOption();

});
