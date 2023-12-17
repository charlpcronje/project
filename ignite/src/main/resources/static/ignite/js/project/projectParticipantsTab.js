var projectParticipantTable = null;
var participantChildrenTable = null;
var ppsdRoleTable = null;

//**********************************
//Project Participants Tab -- Start
function initializeProjectParticipantsTab() {

	showEmptyParticipantChildrenPanel();

	var projectId = $("#pdProjectId").val();
	
	var queryUrl="/rest/ignite/v1/project-participant/list/" + projectId;

	
	var columnsArray = [
    		{ data: "projectParticipantIdContracted" },		//0
    		{ data: "level" },								//1
    		{ data: "participantIdContracted" },			//2
    		{ data: "participantNameContracted" },			//3 -----
    		{ data: "projectParticipantIdContracting" },	//4
    		{ data: "participantNameContracting" },			//5 -----
    		{ data: "projectId" },							//6
    		{ data: "projectName" },						//7
    		{ data: "contractedStartDate" },				//8
    		{ data: "contractedEndDate" },					//9
    		{ data: "participantIdContracting" }			//10
      	];
            	
		var columnDefsArray = [
	            		{
	            			visible: false,
	            			targets: [0,2,4,6,7,8,9,10]
	            		}
	
	]
	
	var buttonsArray = [
	];
	
	projectParticipantTable = initializeGenericTable("projectParticipantTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editProjectParticipant(rowSelector);
										},
										null,
										34,
										[1,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);
	
	projectParticipantTable.off('deselect')
	projectParticipantTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyParticipantChildrenPanel();
		updateProjectParticipantToolbarButtons();
	} );
	
	projectParticipantTable.off('select')
	projectParticipantTable.on('select', function (e, dt, type, indexes) {
		initializeParticipantChildrenTable(dt.data());
		updateProjectParticipantToolbarButtons();
	} );
	
	updateProjectParticipantToolbarButtons(null);
}

function updateProjectParticipantToolbarButtons() {
	var hasSelected = projectParticipantTable.rows('.selected').data().length > 0;
	setTableButtonState(projectParticipantTable, "promptDeleteProjectParticipantBtn", hasSelected);	
}

function promptDeleteProjectParticipant() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Project Participant?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteProjectParticipant(projectParticipantTable);
			   }
	);
}

function deleteProjectParticipant(tbl) {
	var postUrl = "/rest/ignite/v1/project-participant/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Participant has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateProjectParticipantToolbarButtons(); // Create this ...
				var projectId = $("#pdProjectId").val();
			}
	);
}

//Participant Children -- Start
function showEmptyParticipantChildrenPanel() {
	setDivVisibility("participantChildrenEmptyPanel", "block");
	setDivVisibility("participantChildrenPanel", "none");
}


function initializeParticipantChildrenTable(projectParticipantRow) {
	if (projectParticipantRow == null) {
		return;
	}
	
	var projectParticipantId = projectParticipantRow.projectParticipantIdContracted;
	var queryUrl =  "/rest/ignite/v1/project-participant/children/" + projectParticipantId;
	
	setDivVisibility("participantChildrenEmptyPanel", "none");
	setDivVisibility("participantChildrenPanel", "block");
	
	//$("#ppTabDlgErrorDiv").val("");
	
	var columnsArray = [

    	{ data: "projectParticipantIdContracted" },		//0
		{ data: "projectName" },						//1
		{ data: "projectName" },						//2
		{ data: "participantIdContracted" },			//3
		{ data: "participantNameContracted" },			//4 
		{ data: "projectParticipantIdContracting" },	//5
		{ data: "participantNameContracting" },			//6
		{ data: "projectId" },							//7
		{ data: "contractedStartDate" },				//8
		{ data: "contractedEndDate" },					//9
		{ data: "participantIdContracting" }			//10
		
	];
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0, 1, 2, 3, 5, 6, 7,8,9,10]
	}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editParticipantChild(null, projectParticipantRow);
			}
		},
		{
			attr: {
				id: "promptDeleteParticipantChildBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteParticipantChild();
			}
		}
	];
	
	participantChildrenTable = initializeGenericTable("participantChildrenTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editParticipantChild(rowSelector, projectParticipantRow);
										},
										null,
										25,
										[4,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);
	
	participantChildrenTable.off('deselect')
	participantChildrenTable.on('deselect', function (e, dt, type, indexes) {
		updateParticipantChildrenToolbarButtons();
	} );
	
	participantChildrenTable.off('select')
	participantChildrenTable.on('select', function (e, dt, type, indexes) {
		updateParticipantChildrenToolbarButtons();
	} );
	
	updateParticipantChildrenToolbarButtons();
}


function updateParticipantChildrenToolbarButtons() {
	var hasSelected = participantChildrenTable.rows('.selected').data().length > 0;
	
	setTableButtonState(participantChildrenTable, "promptDeleteParticipantChildBtn", hasSelected);	
}

function promptDeleteParticipantChild() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Project Participants?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteParticipantChild(participantChildrenTable);
			   }
	);
}

function deleteParticipantChild(tbl) {
	var postUrl = "/rest/ignite/v1/project-participant/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Participants has been deleted.", tbl,
			function(){	
				projectParticipantTable.rows().deselect();
				var projectId = $("#pdProjectId").val();
				var requestUrl="/rest/ignite/v1/project-participant/list/" + projectId;	
				projectParticipantTable.ajax.url(springUrl(requestUrl)).load();
			}
	);

}

//Project Participants -- End

//**********************************


var somethingChangedInDialog = null;
var askToSaveDialog = null;

//editProjectParticipant -- Start
function editProjectParticipant(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var projectId = $("#pdProjectId").val();
	var errors = "";
	
	if (rowSelector != null) {
		data = projectParticipantTable.row(rowSelector).data();
	} 
	// projectParticipantTable.rows().deselect();
	
	
	$("#ppProjectParticipantId").val(data.projectParticipantIdContracted);
	
	$("#ppParticipantId").val(data.participantIdContracted);
	$("#ppParticipantName").val(data.participantNameContracted);
	var projectStartDate = getMsFromDatePicker("pdProjectStartDate");
	$("#ppStartDate").datepicker("setDate", data.contractedStartDate == null? timestampToString(new Date(projectStartDate), false) : new Date(data.contractedStartDate));
	$("#ppEndDate").datepicker("setDate", data.contractedEndDate == null? null : new Date(data.contractedEndDate));
	
	// Set the Save Button to disabled
	setElementEnabled("saveProjectParticipantButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("projectParticipantDialog");
	
}
//editProjectParticipant -- End

function selectParticipantIdForProject(targetId, targetName) {
	var queryUrl = "/rest/ignite/v1/participant/all-in-view";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "systemName";
			var columns = [
				{ data: "participantId", name: "participantId" },
				{ data: "systemName", name: "System Name" },
				{ data: "registeredName", name: "Registered Name" },
				{ data: "tradingName", name: "Trading Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: 0
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var repName = row.systemName;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetNamme).trigger("change");
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
} //selectParticipantId -- END


//saveProjectParticipant -- Begin
function saveProjectParticipant() {
	var postUrl = "/rest/ignite/v1/project-participant";
	var postData = {
			projectParticipantId: $("#ppProjectParticipantId").val(),
			projectId: $("#pdProjectId").val(), 
			participantId: $("#ppParticipantId").val(), 
			projectParticipantIdAboveMe: null,
			startDate: getMsFromDatePicker("ppStartDate"),
			endDate: getMsFromDatePicker("ppEndDate"),
	};
	
	var errors = "";
	
	// validate
	if (postData.participantId == "") {
		errors += "A Participant is required<br>";
	}
	
	if (showFormErrors("ppDlgErrorDiv", errors)) {
		return;
	}
	saveEntry(postUrl, postData, "projectParticipantDialog", "The Project Participant has been saved.", projectParticipantTable);
	//var projectId = $("#pdProjectId").val();
	
}

function projectParticipantChanged() {
	setElementEnabled("saveProjectParticipantButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeProjectParticipantDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("projectParticipantDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("projectParticipantDialog");
	}
}
//saveProjectParticipant -- End

//editParticipantChild  -- Start
function editParticipantChild (rowSelector, projectParticipantRow) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var errors = "";
		
	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = participantChildrenTable.row(rowSelector).data();
	} else {
		data.projectParticipantIdContracting = projectParticipantRow.projectParticipantIdContracted;
		data.participantNameContracting = projectParticipantRow.participantNameContracted;
	} 
	participantChildrenTable.rows().deselect();
	
	$("#pcProjectParticipantIdAboveMe").val(data.projectParticipantIdContracting); 
	$("#pcProjectParticipantAboveMe").val(data.participantNameContracting);
	$("#pcProjectParticipantId").val(data.projectParticipantIdContracted);
	$("#pcParticipantName").val(data.participantNameContracted); 
	$("#pcParticipantId").val(data.participantIdContracted);
	
	var projectStartDate = getMsFromDatePicker("pdProjectStartDate");
	$("#pcStartDate").datepicker("setDate", data.contractedStartDate == null? timestampToString(new Date(projectStartDate), false) : new Date(data.contractedStartDate));
	$("#pcEndDate").datepicker("setDate", data.contractedEndDate == null? null : new Date(data.contractedEndDate));
	
	// Set the Save Button and Binoculars to disabled 
	setElementEnabled("selectParticipantChildIdButton", rowSelector == null ? true : false);
	setElementEnabled("saveprojectParticipantChildButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	setDivVisibility("pcDlgErrorDiv", "none");
	showModalDialog("participantChildDialog");
}
//editParticipantChild -- End

function selectParticipantChildId() {
	var queryUrl = "/rest/ignite/v1/participant/all-in-view";
	console.log(queryUrl);
	
	var targetId = "pcParticipantId";
	var targetName = "pcParticipantName";

	selectParticipantGenProjTab(queryUrl, targetId, targetName);
}


//saveParticipantChild -- Begin
function saveParticipantChild() {
	var postUrl = "/rest/ignite/v1/project-participant";
	var postData = {
			projectParticipantId: $("#pcProjectParticipantId").val(),
			projectId: $("#pdProjectId").val(), 
			participantId: $("#pcParticipantId").val(), 
			projectParticipantIdAboveMe: $("#pcProjectParticipantIdAboveMe").val(),
			description: $("#pcDescription").val(),
			startDate: getMsFromDatePicker("pcStartDate"),
			endDate: getMsFromDatePicker("pcEndDate"),
	};

	var errors = "";
	
	// validate
	if (postData.participantId == "") {
		errors += "A Participant is required<br>";
	}
	
	if (showFormErrors("pcDlgErrorDiv", errors)) {
		return;
	}
	saveEntry(postUrl, postData, "participantChildDialog", "The Participant child has been saved.", participantChildrenTable,
		function(){	
	//			if (projectParticipantTable != null) {
				projectParticipantTable.rows().deselect();
				var projectId = $("#pdProjectId").val();
				var requestUrl="/rest/ignite/v1/project-participant/list/" + projectId;	
				projectParticipantTable.ajax.url(springUrl(requestUrl)).load();
		
	//				updateProjectParticipantToolbarButtons();
	//			}
		});
	}
	//saveProjectParticipantChild -- End
	
function projectParticipantChildChanged() {
	setElementEnabled("saveprojectParticipantChildButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeProjectParticipantChildDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("participantChildDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("participantChildDialog");
	}
}
//Project Participants -- End
//**********************************

