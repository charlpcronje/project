var medicalInsuranceCompanyTable = null;
var medicalInsurancePlanTable = null;

function initializeMedicalInsuranceCompanyTable() {
	var columnsArray = [
		{ data: "medicalInsuranceCompanyId" },
		{ data: "name" },
		{ data: "schemeType" },
		{ data: "phoneNumber" },
		{ data: "registrationDate" },
		{ data: "lastUpdateTimestamp" },
		{ data: "lastUpdateUserName" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: 0
		},
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			className: "dt-right",
			targets: 4
		},
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, true);
				}
				return html;
			},
			className: "dt-right",
			targets: 5
		},
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editMedicalInsuranceCompany(null);
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
				promptDeleteMedicalInsuranceCompany();
			}
		},
		{
			attr: {
				id: "plansBtn"
			},
			titleAttr: "Plans",
			text: "<i class='fas fa-prescription-bottle-alt'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showPlans();
			}
		}
	];

	medicalInsuranceCompanyTable = initializeGenericTable(
		"medicalInsuranceCompanyTable", 			// tableElementName 
		"/rest/ignite/v1/medical-insurance-company",	// queryUrl
		columnsArray,				// columnsArray
		columnDefsArray,			// columnDefsArray
		buttonsArray,				// buttonArray
		function(roleSelector) {	// callbackMethod
			editMedicalInsuranceCompany(roleSelector);
		},
		null,						// completeMethod
		35,							// pageLength
		[[0,"asc"]] 				// Order by column 0 ascending, normally defaults to column 1 ascending
	);

	medicalInsuranceCompanyTable.off('deselect');
	medicalInsuranceCompanyTable.on('deselect', function(e, dt, type, indexes) {
		updateToolbarButtons();
	});

	medicalInsuranceCompanyTable.off('select');
	medicalInsuranceCompanyTable.on('select', function(e, dt, type, indexes) {
		updateToolbarButtons();
	});
	
	// to initially set the buttons
	updateToolbarButtons();
}

function updateToolbarButtons() {
	var hasSelected = medicalInsuranceCompanyTable.rows('.selected').data().length > 0;

	setTableButtonState(medicalInsuranceCompanyTable, "deleteBtn", hasSelected);
	setTableButtonState(medicalInsuranceCompanyTable, "plansBtn", hasSelected);
}

function editMedicalInsuranceCompany(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = medicalInsuranceCompanyTable.row(rowSelector).data();
	}

	$("#micDlgMode").val(rowSelector == null ? "i" : "u");
	$("#micDlgMedicalInsuranceCompanyId").val(data.medicalInsuranceCompanyId);
	$("#micDlgName").val(data.name);
	$("#micDlgschemeType").val(data.schemeType);
	$("#micDlgPhoneNumber").val(data.phoneNumber);
	$("#micDlgRegistrationDate").val(timestampToString(data.registrationDate, false));

	// Set the Save Button to disabled
	setElementEnabled("saveButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("medicalInsuranceCompanyDialog");
}

function micDialogChanged() {
	setElementEnabled("saveButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function saveMedicalInsuranceCompany() {
	var postUrl = "/rest/ignite/v1/medical-insurance-company/new";
	var postData = {
		mode: $("#micDlgMode").val(),
		medicalInsuranceCompanyId: $("#micDlgMedicalInsuranceCompanyId").val(),
		name: $("#micDlgName").val().toUpperCase(),
		schemeType: $("#micDlgschemeType").val(),
		phoneNumber: $("#micDlgPhoneNumber").val(),
		registrationDate : getMsFromDatePicker("micDlgRegistrationDate")
	};

	var errors = "";

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	if ((postData.schemeType == null) || (postData.schemeType == "")) {
		errors += "A Schema Type is required<br>";
	}

	if (showFormErrors("micDlgErrorDiv", errors)) {
		return;
	}

	//Is the key readonly?  If it is, then the record already exists. 
	if ($("#micDlgMode").val() == "u") {
		// This is an update 
		postUrl = "/rest/ignite/v1/medical-insurance-company";
	}
	
	saveEntry(postUrl, postData, "medicalInsuranceCompanyDialog", "The Medical Insurance Company has been saved.", medicalInsuranceCompanyTable);
}

function promptDeleteMedicalInsuranceCompany() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Medical Insurance Company?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteMedicalInsuranceCompany();
		}
	);
}

function deleteMedicalInsuranceCompany() {
	var postUrl = "/rest/ignite/v1/medical-insurance-company/delete";
	var row = medicalInsuranceCompanyTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Medical Insurance Company has been deleted.", medicalInsuranceCompanyTable,
		function() {
			medicalInsuranceCompanyTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function closeMicDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("micDlgErrorDiv", "none");
				closeModalDialog("medicalInsuranceCompanyDialog");
			});
	} else {
		setDivVisibility("micDlgErrorDiv", "none");
		closeModalDialog("medicalInsuranceCompanyDialog");
	}
}

function closeMpDialog() {
	setDivVisibility("mpDlgErrorDiv", "none");
	closeModalDialog("medicalPlansDialog");
}

function initializePlansTable(medicalInsuranceCompanyId) {
	var columnsArray = [
		{ data: "medicalInsurancePlanId" },
		{ data: "medicalInsuranceCompanyId" },
		{ data: "name" },
		{ data: "description" },
		{ data: "lastUpdateTimestamp" },
		{ data: "lastUpdateUserName" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1]
		},
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = shortenText(data, 40);
				}
				return html;
			},
			targets: 3
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, true);
				}
				return html;
			},
			targets: 4
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editMedicalInsurancePlan(null);
			}
		},
		{
			attr: {
				id: "deleteMipBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteMedicalInsurancePlan();
			}
		}
	];
	
	var url = "/rest/ignite/v1/medical-insurance-company/plans/" + medicalInsuranceCompanyId;

	medicalInsurancePlanTable = initializeGenericTable(
		"medicalInsurancePlanTable", 			// tableElementName 
		url,						// queryUrl
		columnsArray,				// columnsArray
		columnDefsArray,			// columnDefsArray
		buttonsArray,				// buttonArray
		function(roleSelector) {	// callbackMethod
			editMedicalInsurancePlan(roleSelector);
		},
		null,						// completeMethod
		35,							// pageLength
		[[0,"asc"]] 				// Order by column 0 ascending, normally defaults to column 1 ascending
	);
	
	
	medicalInsurancePlanTable.off('deselect');
	medicalInsurancePlanTable.on('deselect', function(e, dt, type, indexes) {
		updateMipToolbarButtons();
	});

	medicalInsurancePlanTable.off('select');
	medicalInsurancePlanTable.on('select', function(e, dt, type, indexes) {
		updateMipToolbarButtons();
	});
	
	// to initially set the buttons
	updateMipToolbarButtons();
}

function updateMipToolbarButtons() {
	var hasSelected = medicalInsurancePlanTable.rows('.selected').data().length > 0;

	setTableButtonState(medicalInsurancePlanTable, "deleteMipBtn", hasSelected);
}

function showPlans() {
	setDivVisibility("mpDlgErrorDiv", "none");

	// a row MUST be selected
	var row = medicalInsuranceCompanyTable.rows('.selected').data()[0];
	var id = row["medicalInsuranceCompanyId"];

	$("#mpDlgMedicalInsuranceCompanyId").val(id);

	initializePlansTable(id);
	
	showModalDialog("medicalPlansDialog");
}

function closeMipDialog() {
	setDivVisibility("mipDlgErrorDiv", "none");
	closeModalDialog("medicalPlanDialog");	
}

function promptDeleteMedicalInsurancePlan() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Medical Insurance Plan?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteMedicalInsurancePlan();
		}
	);
}

function editMedicalInsurancePlan(rowSelector) {
	var medicalInsuranceCompanyId = $("#mpDlgMedicalInsuranceCompanyId").val();
	var data = {
		medicalInsuranceCompanyId: medicalInsuranceCompanyId
	};
		
	if (rowSelector != null) {
		data = medicalInsurancePlanTable.row(rowSelector).data();
	}

	$("#mipDlgMode").val(rowSelector == null ? "i" : "u");
	$("#mipDlgMedicalInsurancePlanId").val(data.medicalInsurancePlanId);
	$("#mipDlgMedicalInsuranceCompanyId").val(data.medicalInsuranceCompanyId);
	$("#mipDlgName").val(data.name);
	$("#mipDlgDescription").val(data.description);
	
	showModalDialog("medicalPlanDialog");
}

function deleteMedicalInsurancePlan() {
	var postUrl = "/rest/ignite/v1/medical-insurance-company/plan/delete";
	var row = medicalInsurancePlanTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Medical Insurance Plan has been deleted.", medicalInsurancePlanTable,
		function() {
			medicalInsurancePlanTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function saveMedicalInsurancePlan() {
	var postUrl = "/rest/ignite/v1/medical-insurance-company/plan/new";
	var postData = {
		mode: $("#mipDlgMode").val(),
		medicalInsurancePlanId: $("#mipDlgMedicalInsurancePlanId").val(),
		medicalInsuranceCompanyId: $("#mipDlgMedicalInsuranceCompanyId").val(),
		name: $("#mipDlgName").val().toUpperCase(),
		description: $("#mipDlgDescription").val()
	};

	var errors = "";

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	if (showFormErrors("mipDlgErrorDiv", errors)) {
		return;
	}

	//Is the key readonly?  If it is, then the record already exists. 
	if (postData.mode == "u") {
		// This is an update 
		postUrl = "/rest/ignite/v1/medical-insurance-company/plan";
	}

	saveEntry(postUrl, postData, "medicalPlanDialog", "The Medical Insurance Plan has been saved.", medicalInsurancePlanTable);
}

// ***********************************************************************

$(document).ready(function() {
	initializeMedicalInsuranceCompanyTable();
});
