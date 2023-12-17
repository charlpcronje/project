var countryTable;

function initializeCountryTable() {
	var columnsArray = [
	    { data: "countryId" },
		{ data: "countryCode" },
		{ data: "name" }
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
			action: function(e, dt, node, config) {
				editCountry(null);
			}
		},
		{
			attr: {
				id: "deleteCountryBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteCountry();
			}
		}
	];

	countryTable = initializeGenericTable(
		"countryTable", 			// tableElementName 
		"/rest/ignite/v1/country",	// queryUrl
		columnsArray,				// columnsArray
		columnDefsArray,			// columnDefsArray
		buttonsArray,				// buttonArray
		function(roleSelector) {	// callbackMethod
			editCountry(roleSelector);
		},
		null,						// completeMethod
		31,							// pageLength
		[[1,"asc"]] 				// Order by column 0 ascending, normally defaults to column 1 ascending
	);

	countryTable.off('deselect');
	countryTable.on('deselect', function(e, dt, type, indexes) {
		updateCountryToolbarButtons();
	});

	countryTable.off('select');
	countryTable.on('select', function(e, dt, type, indexes) {
		updateCountryToolbarButtons();
	});
	
	// to initially set the buttons
	updateCountryToolbarButtons();
}


function updateCountryToolbarButtons() {
	var hasSelected = countryTable.rows('.selected').data().length > 0;

	setTableButtonState(countryTable, "deleteCountryBtn", hasSelected);
}


function editCountry(rowSelector) {
	var countryId = "";
	var countryCode = "";
	var name = "";
	
	if (rowSelector != null) {
		data = countryTable.row(rowSelector).data();
		countryId = data.countryId;
		countryCode = data.countryCode;
		name = data.name;
	}
	countryTable.rows().deselect();
	console.log("Country Code TEst = "+ countryCode)

	$("#cDlgCountryId").val(countryId);
	$("#cDlgCountryCode").val(countryCode);
	$("#cDlgCountryName").val(name);

	// Set the Save Button to disabled
	setElementEnabled("saveCountryButton", false);
	var somethingChangedInDialog = false;
	var askToSaveDialog = false;

	showModalDialog("countryDialog");
}


function countryDialogChanged() {
	setElementEnabled("saveCountryButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}


function saveCountry() {
	var postUrl = "/rest/ignite/v1/country/new";
	var postData = {
		mode: $("#cDlgMode").val(),
		countryId: $("#cDlgCountryId").val(),
		countryCode: $("#cDlgCountryCode").val().toUpperCase(),
		name: $("#cDlgCountryName").val()
	};

	var errors = "";

	if ((postData.countryCode == null) || (postData.countryCode == "")) {
		errors += "A Country Code is required<br>";
	}

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Country Name is required<br>";
	}

	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.countryId != null) && (postData.countryId != "")) {	
		// This is an update 
		postUrl = "/rest/ignite/v1/country";
	} else {
		postData.countryId = null;  //empty string werk nie
	}	

	saveEntry(postUrl, postData, "countryDialog", "The Country has been saved.", countryTable);
}

function promptDeleteCountry() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Country?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteCountry();
		}
	);
}

function deleteCountry() {
	var postUrl = "/rest/ignite/v1/country/delete";
	var row = countryTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Country has been deleted.", countryTable,
		function() {
			countryTable.rows(".selected").nodes().to$().removeClass("selected");
			updateCountryToolbarButtons();
		}
	);
}

function closeCountryDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("cDlgErrorDiv", "none");
				closeModalDialog("countryDialog");
			});
	} else {
		setDivVisibility("cDlgErrorDiv", "none");
		closeModalDialog("countryDialog");
	}
}

// ***********************************************************************

$(document).ready(function() {
	initializeCountryTable();
});
