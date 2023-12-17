var remunerationModelTable = null;


function initializeTable() {
	var columnsArray = [
		{ data: "remunerationModelCode" },
		{ data: "name" },
		{ data: "description" }
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editRemunerationModel(null);
			}
		},{
			attr : {
				id: "deleteRemunerationModelBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteRemunerationModel();
			}
		}
	];

	var columnDefsArray = [];
	
	remunerationModelTable = initializeGenericTable("remunerationModelTable", 
			                            "/rest/ignite/v1/remuneration-model",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editRemunerationModel(rowSelector);
										});

	remunerationModelTable.off('deselect');
	remunerationModelTable.on('deselect', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	remunerationModelTable.off('select');
	remunerationModelTable.on('select', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	// to initially set the buttons
	updateToolbarButtons();
}



function updateToolbarButtons() {
	var hasSelected = remunerationModelTable.rows('.selected').data().length > 0;
	
	setTableButtonState (remunerationModelTable, "deleteRemunerationModelBtn", hasSelected);
}


function editRemunerationModel(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = remunerationModelTable.row(rowSelector).data();
	}
	remunerationModelTable.rows().deselect();

	$("#fsDlgRemunerationModelCode").val(data.remunerationModelCode);
	$("#fsDlgName").val(data.name);
	$("#fsDlgDescription").val(data.description);

	// disable the code if update, enable if insert
	$("#fsDlgRemunerationModelCode").prop("readonly", (data.remunerationModelCode != null) && (data.remunerationModelCode != ""));
		
	// Set the Save Button to disabled
	setElementEnabled("saveRemunerationModelButton", false);
	somethingChangedInDialog = false;
		
	showModalDialog("remunerationModelDialog");
}


function promptDeleteRemunerationModel() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Free Structure Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRemunerationModel();
			   }
	);
}


function deleteRemunerationModel() {
	var postUrl = "/rest/ignite/v1/remuneration-model/delete";
	var row = remunerationModelTable.rows('.selected').data()[0];
	
	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Remuneration Model has been deleted.", remunerationModelTable,
		function(){	
			remunerationModelTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}


function saveRemunerationModel() {
	var postUrl = "/rest/ignite/v1/remuneration-model/new";
	var postData = {
		remunerationModelCode: $("#fsDlgRemunerationModelCode").val().trim().toUpperCase(),
		name: $("#fsDlgName").val(),
		description: $("#fsDlgDescription").val()
	};
	var errors = "";
	
	if ((postData.remunerationModelCode == null) || (postData.remunerationModelCode == "")) {
		errors += "A Remuneration Model Code is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("fsDlgErrorDiv", errors)) {
		return;
	}
	//Is the key readonly?  If it is, then the record already exists.
	if ($("#fsDlgRemunerationModelCode").is("[readonly]")) {
		//This is an update
		postUrl="/rest/ignite/v1/remuneration-model";
	}
	saveEntry(postUrl, postData, "remunerationModelDialog", "The Remuneration Model has been saved.", remunerationModelTable);
	
}



function remunerationModelDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveRemunerationModelButton", true);
}  //remunerationModelDialogChanged -- End


//closeRemunerationModelDialog -- Start
function closeRemunerationModelDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("remunerationModelDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("remunerationModelDialog");
	}
}//closeRemunerationModelDialog -- End


// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTable();
} );
