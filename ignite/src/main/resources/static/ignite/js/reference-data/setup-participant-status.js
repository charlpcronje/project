var participantStatusTable = null;

function showCodeDescriptionDialog(title, code, name, description) {
	// Set the title
	$("#cdDlgTitle").text(title);

	// set our inputs
	$("#cdDlgCode").val(code);
	$("#cdDlgName").val(name);
	$("#cdDlgDescription").val(description);

	// disable code if this is an update
	$("#cdDlgCode").prop("readonly", (code != null) && (code != ""));

	// remove previous onclick events
	$('#cdDlgSaveButton').unbind('click');

	showFormErrors("cdDlgErrorDiv");

	// show the dialog
	showModalDialog("codeDescriptionDialog");
}

function initializeParticipantStatusTable() {
	var columnsArray = [
		{ data: "tapSubscriptionCode" },
		{ data: "name" },
		{ data: "description" }
	];

	var columnDefsArray = [
		{
			width: "200px",
			targets: 0
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = shortenText(data, 40);
				}

				return data;
			},
			targets: 2
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editParticipantStatus(null);
			}
		},
		{
			attr: {
				id: "deleteBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDelete();
			}
		}
	];

	participantStatusTable = initializeGenericTable("participantStatusTable",
		"/rest/ignite/v1/participant-status",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editParticipantStatus(rowSelector);
		},
		null,
		30
	);

	participantStatusTable.off('deselect');
	participantStatusTable.on('deselect', function(e, dt, type, indexes) {
		updateToolbarButtons();
	});

	participantStatusTable.off('select');
	participantStatusTable.on('select', function(e, dt, type, indexes) {
		updateToolbarButtons();
	});

	updateToolbarButtons();
}

function updateToolbarButtons() {
	var hasSelected = participantStatusTable.rows('.selected').data().length > 0;

	setTableButtonState(participantStatusTable, "deleteBtn", hasSelected);
}

function promptDelete() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Participant Status?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteParticipantStatus();
		}
	);
}

function deleteParticipantStatus() {
	var postUrl = "/rest/ignite/v1/participant-status/delete";
	var row = participantStatusTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Participant Status has been deleted.", participantStatusTable,
		function() {
			participantStatusTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function editParticipantStatus(rowSelector) {
	var code = "";
	var name = "";
	var description = "";

	if (rowSelector != null) {
		// get values from table
		var data = participantStatusTable.row(rowSelector).data();
		code = data.tapSubscriptionCode;
		name = data.name;
		description = data.description;
	}
	participantStatusTable.rows().deselect();

	// Set the Save Button to disabled
	setElementEnabled("cdDlgSaveButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showCodeDescriptionDialog("Participant Status", code, name, description);
}

function participantStatusChanged() {
	setElementEnabled("cdDlgSaveButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function saveParticipantStatus() {
	var postUrl = "/rest/ignite/v1/participant-status/new";  //For insert
	var postData = {
			tapSubscriptionCode: $("#cdDlgCode").val().trim().toUpperCase(),
		name: $("#cdDlgName").val(),
		description: $("#cdDlgDescription").val()
	};
	var errors = "";

	// validation
	if (postData.tapSubscriptionCode == "") {
		errors += "A TAP Subcription Code is required<br>";
	}

	if (postData.name == "") {
		errors += "A Name is required<br>";
	}

	if (showFormErrors("cdDlgErrorDiv", errors)) {
		return;
	}

	//Is the code readonly?  If it is, then the record already exists
	if ($("#cdDlgCode").is("[readonly]")) {
		postUrl = "/rest/ignite/v1/participant-status";  //Update the record
	}

	saveEntry(postUrl, postData, "codeDescriptionDialog", "The Participant Status has been saved.", participantStatusTable);
}

function closeParticipantStatus() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("codeDescriptionDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("codeDescriptionDialog");
	}
}


// ***********************************************************************

$(document).ready(function() {
	initializeParticipantStatusTable();
});
