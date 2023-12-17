var resourceRemunTypeTable = null;
var somethingChangedInDialog = null;

// ************* initializeResourceRemunTypeTable Start ************* // 
function initializeResourceRemunTypeTable() {

	var columnsArray = [
	           		 { data: "resourceRemunTypeId" }      //0 MySql-TableName: ResourceRemunType
	           		,{ data: "remunerationIntervalCode" } //1
	           		,{ data: "unitTypeCode" }             //2
	           		,{ data: "name" }                     //3
	           		,{ data: "description" }              //4

	           	];

	           	var columnDefsArray = [
	           		{
	           			visible: false,
	           			targets: [0 ]
	           		}
	           	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function() {
				editResourceRemunType(null);
			}
		},
		{
			attr: {
				id: "promptDeleteResourceRemunTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteResourceRemunType();
			}
		}
	];

	
	console.log("/rest/ignite/v1/resource-remun-type");
	resourceRemunTypeTable = initializeGenericTable("resourceRemunTypeTable",
		"/rest/ignite/v1/resource-remun-type",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editResourceRemunType(rowSelector);
		},
		null,
		31,
		[3,"asc"]
	);

	
	resourceRemunTypeTable.off('deselect');
	resourceRemunTypeTable.on('deselect', function() {
		updateResourceRemunTypeToolbarButtons();
	});

	
	resourceRemunTypeTable.off('select');
	resourceRemunTypeTable.on('select', function(e, dt, node, config) {
		updateResourceRemunTypeToolbarButtons();
	});

	updateResourceRemunTypeToolbarButtons();
}


function updateResourceRemunTypeToolbarButtons() {
	var hasSelected = resourceRemunTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(resourceRemunTypeTable, "promptDeleteResourceRemunTypeBtn", hasSelected);
}


function editResourceRemunType(rowSelector) {
	console.log("editResourceRemunType");
	var data = {};

	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = resourceRemunTypeTable.row(rowSelector).data();
	}
	resourceRemunTypeTable.rows().deselect();

	$("#rResourceRemunTypeId").val(data.resourceRemunTypeId);
	$("#rName").val(data.name);
	$("#rDescription").val(data.description);

	populateSelect("rRemunerationIntervalCode",
		"/rest/ignite/v1/remuneration-interval",
		"remunerationIntervalCode",
		"name",
		data.remunerationIntervalCode,
		true,
		null);

	populateSelect("rUnitTypeCode",
		"/rest/ignite/v1/unit-type",
		"unitTypeCode",
		"name",
		data.unitTypeCode,
		true,
		null);

	// Set the Save Button to disabled
	setElementEnabled("saveResourceRemunTypeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("resourceRemunTypeDialog");
}

function promptDeleteResourceRemunType() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Resource Remuneration Type?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			if (e) {
				deleteResourceRemunType(resourceRemunTypeTable);
			}
		}
	);
}

function deleteResourceRemunType(tbl) {
	var postUrl = "/rest/ignite/v1/resource-remun-type/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Resource Remuneration Type has been deleted.", tbl,
		function() {
			tbl.rows().deselect();
			updateResourceRemunTypeToolbarButtons();
		});
}

function saveResourceRemunType() {
	var postUrl = "/rest/ignite/v1/resource-remun-type/new";
	var postData = {
		resourceRemunTypeId: $("#rResourceRemunTypeId").val().trim().toUpperCase(),
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

	if ((postData.resourceRemunTypeId != null) && (postData.resourceRemunTypeId != "")) {	
		// This is an update
		postUrl = "/rest/ignite/v1/resource-remun-type";
	} else {
		postData.resourceRemunTypeId = null;  //empty string werk nie
	}

	saveEntry(postUrl, postData, "resourceRemunTypeDialog", "The Resource Remuneration Type has been saved.", resourceRemunTypeTable);
}

//resourceRemunTypeDialogChanged -- Start
function resourceRemunTypeDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveResourceRemunTypeButton", true);
}
//resourceRemunTypeDialogChanged -- End


//closeResourceRemunTypeDialog -- Start
function closeResourceRemunTypeDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("resourceRemunTypeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");		closeModalDialog("resourceRemunTypeDialog");
	}
}
//closeResourceRemunTypeDialog -- End

//************* rtProjBasedTab End ************* // 

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
	initializeResourceRemunTypeTable();
	showIgDeveloperOption();

});
