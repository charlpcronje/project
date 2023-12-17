var selectedMic = -1;
var selectedMip = -1;
var medicalDependantTable = null;

var somethingChangedInDialog = false;
var askToSave = false;
	
function initializeMedicalInsuranceCompanyAndPlans(data) {
	var mainMemberMedicalInsuranceId = data.mainMemberMedicalInsuranceId;
	var individualId = data.individualId;
	var medicalInsuranceCompanyId = data.medicalInsuranceCompanyId;
	var medicalInsurancePlanId = data.medicalInsurancePlanId;
	
	// Defaults
	selectedMic = -1;
	selectedMip = -1;

	if (medicalInsuranceCompanyId !== undefined) {
		selectedMic = medicalInsuranceCompanyId;
	}
	if (medicalInsurancePlanId !== undefined) {
		selectedMip = medicalInsurancePlanId;
	}
	
	$("#maMainMemberMedicalInsuranceId").val(mainMemberMedicalInsuranceId);
	$("#maIndividualId").val(individualId);
	$("#maDescription").val(data.description);
	$("#maInsuranceNumber").val(data.insuranceNumber);
	
	populateSelect("maMedicalInsuranceCompany", 
		       "/rest/ignite/v1/medical-insurance-company", 
		       "medicalInsuranceCompanyId", 
		       "name", 
		       selectedMic, 
		       true,
		       function() {
			       refreshMedicalInsurancePlans();
		       }
	);
	
	initializeDependants(individualId, mainMemberMedicalInsuranceId);
}

function refreshMedicalInsurancePlans() {
	selectedMic = $("#maMedicalInsuranceCompany").val();
	if (selectedMic == "") {
		selectedMic = -1;
	}

	populateSelect("maMedicalInsurancePlan", 
		       "/rest/ignite/v1/medical-insurance-company/plans/" + selectedMic, 
		       "medicalInsurancePlanId", 
		       "name", 
		       selectedMip, 
		       true
	);
}

function formChanged(hasChanged) {
	if (hasChanged === undefined) {
		hasChanged = true;
	}
	
	somethingChangedInDialog = hasChanged;
	askToSave = hasChanged;
	
	refreshButtons();
}

function refreshButtons() {
	setElementEnabled("saveMedicalTabButton", somethingChangedInDialog);
}

// This is our starting point - initialized by participant-edit.js
function initializeMedicalInsuranceCompany(individualId) {
	var queryUrl = "/rest/ignite/v1/individual-medical/" + individualId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			if ((data == undefined) || (data == null) || data == "") {
				// this means that there is NO MainMemberMedicalInsurance record...
				data = {
					individualId: individualId,
					mainMemberMedicalInsuranceId: -1
				}
			}
		
			initializeMedicalInsuranceCompanyAndPlans(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}

function initializeDependants(individualId, mainMemberMedicalInsuranceId) {
	if (mainMemberMedicalInsuranceId == "-1") {
		setDivVisibility("dependantsPanel", "none");
		
		return;
	}

	setDivVisibility("dependantsPanel", "block");

	var url = "/rest/ignite/v1/individual-medical/dependants/" + individualId;

	var columnsArray = [
		{ data: "medicalDependantId" },
		{ data: "mainMemberMedicalInsuranceId" },
		{ data: "individualIdDependant" },
		{ data: "displayName"},
		{ data: "description" },
		{ data: "lastUpdateUserName" },
		{ data: "lastUpdateTimestamp" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2]
		},
		{
			render: function (data, type, row) {
				return shortenText(data, 30);
			},
			className: "dt-center",
			targets: 4
		},
		{
			render: function (data, type, row) {
				if (type == "display") {
					data = timestampToString(data, true);
				}
				
				return data;	
			},
			targets: 6
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editMedicalDependant(individualId, null);
			}
		},
		{
			attr: {
				id: "promptDeleteDependantBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteDependant();
			}
		}
	];
	
	medicalDependantTable = initializeGenericTable("medicalDependantTable",
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editMedicalDependant(individualId, rowSelector);  //Double click
										}
	);
  
  
	medicalDependantTable.off('deselect');
	medicalDependantTable.on('deselect', function (e, dt, type, indexes) {
		updateDependantToolbarButtons();
	} );
	
	medicalDependantTable.off('select');
	medicalDependantTable.on('select', function (e, dt, type, indexes) {
		updateDependantToolbarButtons();
	} );	

	updateDependantToolbarButtons();
}

function updateDependantToolbarButtons() {
	var hasSelected = medicalDependantTable.rows('.selected').data().length > 0;
	
	setTableButtonState(medicalDependantTable, "promptDeleteDependantBtn", hasSelected);	
}

function saveMedicalTab() {
	var postUrl = "/rest/ignite/v1/individual-medical";
	
	var postData = {
		individualId: $("#maIndividualId").val(),
		medicalInsuranceCompanyId: $("#maMedicalInsuranceCompany").val(),
		medicalInsurancePlanId: $("#maMedicalInsurancePlan").val(),
		insuranceNumber: $("#maInsuranceNumber").val(),
		description: $("#maDescription").val()
	}
	
	saveEntry(postUrl, postData, null, "The Medical Insurance Company information has been saved.", null,
			function(){
				// reload the page
				initializeMedicalInsuranceCompany($("#maIndividualId").val());
				
				formChanged(false);
			}
	);
}

function promptDeleteDependant() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Dependant?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function() {
			deleteMedicalDependant();
		}
	);
}

function deleteMedicalDependant() {
	var postUrl = "/rest/ignite/v1/individual-medical/dependant/delete";
	var row = medicalDependantTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Dependant has been deleted.", medicalDependantTable,
		function() {
			medicalDependantTable.rows(".selected").nodes().to$().removeClass("selected");
			updateDependantToolbarButtons();
		}
	);
}

function editMedicalDependant(individualId, rowSelector) {
	var data = {
		individualId: individualId
	};
	
	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = medicalDependantTable.row(rowSelector).data();
	}
	
	$("#maDlgMedicalDependantId").val(data.medicalDependantId);
	$("#mainMemberMedicalInsuranceId").val(data.individualId);
	$("#mdDlgDescription").val(data.description);

	showModalDialog("medicalDependantDialog");
}

function closeMedicalDependantDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("mdDlgErrorDiv", "none");
				closeModalDialog("medicalDependantDialog");
			});
	} else {
		setDivVisibility("mdDlgErrorDiv", "none");
		closeModalDialog("medicalDependantDialog");
	};
	somethingChangedInDialog = false;
}

function selectDependant(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="individualIdDependant";
			var refColumnName="individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.individualId;
				var repName = row.firstName + " " + row.lastName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}

function editDependantIndividualId() {
	selectDependant("mdDlgDependantIndividualId", "mdDlgDependantIndividualName");
	formChanged();
}

function saveMedicalDependant() {
	var postUrl = "/rest/ignite/v1/individual-medical/dependant/save";
	var postData = {
		medicalDependantId : $("#maDlgMedicalDependantId").val(),
 		mainMemberMedicalInsuranceId: $("#maMainMemberMedicalInsuranceId").val(),
		individualIdDependant: $("#mdDlgDependantIndividualId").val(),
		description: $("#mdDlgDescription").val()
	};
	
	saveEntry(postUrl, postData, "medicalDependantDialog", "The Dependant has been saved.", medicalDependantTable,
		function(){
			// reload the table
			// initializeDependants($("#maIndividualId").val(), data.mainMemberMedicalInsuranceId);
			formChanged(false);
		}
	);
}

// *************************************************************
