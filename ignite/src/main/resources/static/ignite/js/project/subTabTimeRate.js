var rAgreementTable = null;
var rAgreementIndividualsTable = null;
var rRateTable = null;

//*********************************************************
//Remuneration Tab -- Start
//*********************************************************
function initializeTimeRelatedTab(projectId) {

	showEmptyAgreementIndividualsPanel();

	// Select all the agreements for this project
	var queryUrl = "/rest/ignite/v1/agreement-between-participants/remuneration/" + projectId;
	var columnsArray = [
		{ data: "agreementBetweenParticipantsId" },		// 0
		{ data: "projectId" },							// 1
		{ data: "projectParticipantId" },				// 2
		{ data: "participantIdPayer" },					// 3
		{ data: "systemNamePayer" },					// 4
		{ data: "participantIdBeneficiary" },			// 5
		{ data: "systemNameBeneficiary" },				// 6
		{ data: "" },									// 7
		{ data: "remunerationModelCode"},				// 8
		{ data: "remunerationModelName" },				// 9
		{ data: "description"},		// 10
		{ data: "description"},							// 11
		{ data: "level"},								// 12
		{ data: "agreementBudget" }						// 13
		
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 5, 6, 8, 9, 10,11,13]
		},
   		{
   			render: function(data, type, row) {
   				a = row.systemNamePayer + " - " + row.systemNameBeneficiary;
   				return a;
   			},
   			targets: 7
   		}
	];

	var buttonsArray = [];
	
	rAgreementTable = initializeGenericTable("rAgreementTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										25,
										[12,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
			                            
	);

	rAgreementTable.off('deselect')
	rAgreementTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyAgreementIndividualsPanel();
	} );

	rAgreementTable.off('select')
	rAgreementTable.on('select', function (e, dt, type, indexes) {
		initializeAgreementIndividualsTable(dt.data());
	} );
}

function showEmptyAgreementIndividualsPanel() {

	//rAgreementTable.rows().deselect();
	setDivVisibility("agreementIndividualsEmptyPanel", "block");
	setDivVisibility("rateEmptyPanel", "block");
	setDivVisibility("ratePanel", "none");
	setDivVisibility("agreementIndividualsPanel", "none");
	showEmptyRatePanel();

}

//Agreement Between Participants for Remuneration -- End
//*********************************************************

//*********************************************************

//*********************************************************
// Agreement Individuals Table  -- Start

function initializeAgreementIndividualsTable(agreementRow) {
//	if (agreementRow == null) {
//		return;
//	}

	showEmptyRatePanel();

	var agreementBetweenParticipantsId = agreementRow.agreementBetweenParticipantsId;
	
	var queryUrl = "/rest/ignite/v1/remuneration-rate-upline/agreement-individuals/" + agreementBetweenParticipantsId;

	var columnsArray = [
	                    
   		{ data: "rowNumber" },						// 0
		{ data: "agreementBetweenParticipantsId" },	// 1
		{ data: "remunerationModelCode" },			// 2
		{ data: "agreementDescription" },			// 3
		{ data: "participantIdContracting" },		// 4
		{ data: "participantNameContracting" },	// 5
		{ data: "projectId" },						// 6
		{ data: "projectParticipantIdContracting" },			// 7
		{ data: "level" },							// 8
		{ data: "participantIdContracted"},		// 9
		{ data: "participantNameContracted" },					// 10
		{ data: "agreementDescription" },					// 11
		{ data: "anyChildren" },					// 12
		{ data: "isIndividual" },					// 13
		{ data: "projectParticipantIdContracting" },	// 14
		{ data: "participantNameContracting" }			// 15
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,3,4,5,6,7,8,9,11,12,13,14,15]
		}
	];

	var buttonsArray = [
	];
	
	setDivVisibility("agreementIndividualsEmptyPanel", "none");
	setDivVisibility("agreementIndividualsPanel", "block");
	setDivVisibility("rateEmptyPanel", "block");
	setDivVisibility("ratePanel", "none");
	
	showEmptyRatePanel();

	rAgreementIndividualsTable = initializeGenericTable("rAgreementIndividualsTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										25,
										[10,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	rAgreementIndividualsTable.off('deselect')
	rAgreementIndividualsTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyRatePanel();
	} );

	rAgreementIndividualsTable.off('select')
	rAgreementIndividualsTable.on('select', function (e, dt, type, indexes) {
		initializeRateTable(dt.data(), agreementRow, false);
	} );
}

function showEmptyRatePanel() {

	//rateTable.rows().deselect();
	setDivVisibility("rateEmptyPanel", "block");
	setDivVisibility("ratePanel", "none");

}

// Agreement Individuals Table -- End
//*********************************************************

//*********************************************************
//Rate Table  -- Start

function initializeRateTable(agreementIndividualsRow, agreementRow, allRecords) {
//	if (agreementIndividualsRow == null) {
//		return;
//	}

	var projectParticipantId = agreementIndividualsRow.projectParticipantIdContracted; //The individual that the rate is setup for.  Per agreement between 2 participants
	var agreementBetweenParticpantsId = agreementIndividualsRow.agreementBetweenParticipantsId; // The agreement Id
	var queryUrl;
	
	if (allRecords) {
		var queryUrl="/rest/ignite/v1/remuneration-rate-upline/rates/" + agreementBetweenParticpantsId + "/" + projectParticipantId;
	} else {  //Show current Records only
		var queryUrl="/rest/ignite/v1/remuneration-rate-upline/rates/current/" + agreementBetweenParticpantsId + "/" + projectParticipantId;
	}

	setDivVisibility("rateEmptyPanel", "none");
	setDivVisibility("ratePanel", "block");
	
	$("#rRemunerationRatesHeading").html("Current Remuneration Rates");
	
	var columnsArray = [
	                    
		{ data: "remunerationRateUplineId" },			// 0
		{ data: "agreementBetweenParticipantsId" },		// 1
		{ data: "projectParticipantSdRoleIdIndividual" },	// 2
		{ data: "participantIdIndividual" },			// 3
		{ data: "sdName" },								// 4
		{ data: "roleOnAProjectName" },				// 5
		{ data: "projBasedRemunTypeId" },			// 6
		{ data: "projBasedRemunTypeName" },			// 7 Remun Type
		{ data: "projBasedRemunTypeUnitCode" },	    // 8 Remun Unit Type Code
		{ data: "projBasedRemunTypeUnitName" },		// 9 Remun Unit Type
		{ data: "ratePerUnit" },					// 10
		{ data: "description" },					// 11
		{ data: "startDate" },						// 12
		{ data: "endDate" },						// 13
		{ data: "remunerationInterval" }			// 14
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,3,6,8,9,11,14]
		},
		{
			width: "40%",
			targets: 4
		},
		{
			width: "15%",
			targets: [5,9]
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				
				return html;
			},
			width:"10%",
			targets: [10]
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			width: "10%",
			targets: [12,13]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editRemunerationRateUpline(null, agreementIndividualsRow, agreementRow);
							
				//document.getElementById('rProjBasedRemunType').value = 'HOURLY_RATE_WORK';
				
			}
		},
		{
			attr: {
				id: "promptDeleteRateBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteRate();
			}
		},
		{
			attr: {
				id: "showAllRatesBtn"
			},
			titleAttr: "All",
			text: "<i class='fas fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				$("#rRemunerationRatesHeading").html("All Remuneration Rates");
				var requestUrl="/rest/ignite/v1/remuneration-rate-upline/rates/" + agreementBetweenParticpantsId + "/" + projectParticipantId;
				rRateTable.ajax.url( springUrl(requestUrl) ).load();
			}
		},
		{
			attr: {
				id: "showCurrentRatesBtn"
			},
			titleAttr: "Current",
			text: "<i class='fas fa-clock'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				$("#rRemunerationRatesHeading").html("Current Remuneration Rates");
				var requestUrl="/rest/ignite/v1/remuneration-rate-upline/rates/current/" + agreementBetweenParticpantsId + "/" + projectParticipantId;
				//rRateTable.rows().deselect();
				rRateTable.ajax.url( springUrl(requestUrl) ).load();
			}
		}
	];
	
	rRateTable = initializeGenericTable("rRateTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editRemunerationRateUpline(rowSelector, agreementIndividualsRow, agreementRow);  //Double click
										},
										null,
										25,
										[10,"desc"] //Order by column 0 ascending, normally defaults to column 1 ascending

	);

	rRateTable.off('deselect')
	rRateTable.on('deselect', function (e, dt, type, indexes) {
		updateRateToolbarButtons();
	} );

	rRateTable.off('select')
	rRateTable.on('select', function (e, dt, type, indexes) {
		updateRateToolbarButtons();
	} );
	
	updateRateToolbarButtons();
}


function updateRateToolbarButtons() {
	var hasSelected = rRateTable.rows('.selected').data().length > 0;

	setTableButtonState(rRateTable, "promptDeleteRateBtn", hasSelected);	
}
	
function promptDeleteRate() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Rate?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRemunerationRateUpline(rRateTable);
			   }
	);
}

function deleteRemunerationRateUpline(tbl) {
	var postUrl = "/rest/ignite/v1/remuneration-rate-upline/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Rate has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateRateToolbarButtons();
			}
	);
}

//Rate Table  -- End
//*********************************************************

//*********************************************************
//Rates Upline Tab -- End
//*********************************************************
//*********************************************************

//editRate -- Start
function editRemunerationRateUpline (rateRow, agreementIndividualsRow, agreementRow) {

	var data = {}; 
	var errors = "";
	var header = "";
	var nuweEen

	if (rateRow != null) {
		data = rRateTable.row(rateRow).data();
		nuweEen = false
	} else {
		selectedProjBasedRemunTypeId = null;
		//$("#rUnitTypeCode").val();
		$("#rUnitTypeName").val();
		// $("#rRemunerationIntervalId").val();
		$("#rRemunerationIntervalName").val();
		nuweEen = true
	}
	
	header = "Agreement Remuneration Rate - " + agreementIndividualsRow.individualNameContracted; // The individual name;
	rRateTable.rows().deselect();
	
	var selectedProjBasedRemunTypeId = data.projBasedRemunTypeId;
	if (selectedProjBasedRemunTypeId == null) {           //Sit die Default in.  SIEN HIERONDER OOK. 
		selectedProjBasedRemunTypeId = 7; // Fix this Ingrid!!! "HOURLY_RATE_WORK"			
	}
	
	var selectedProjectParticipantSdRoleId = data.projectParticipantSdRoleIdIndividual;
	if (selectedProjectParticipantSdRoleId == null) {
		
//		if ((jqXHR.status == 400) && (jqXHR.responseText.indexOf("ConstraintViolation") > 0)) {
//			jqXHR = {
//					status: "Error"
//			};
//			errorThrown = "Cannot save this record because of a database constraint.";
//		}
//		
//		ajaxError(jqXHR, textStatus, errorThrown);

//		return
	}
	var projectStartDate = getMsFromDatePicker("pdProjectStartDate");

	$("#remunRateDialogHeader").html(header);
	$("#rRemunerationRateUplineId").val(data.remunerationRateUplineId);
	$("#rAgreementBetweenParticipantsId").val(agreementRow.agreementBetweenParticipantsId);
	$("#rProjectParticipantId").val(agreementIndividualsRow.projectParticipantId);
	$("#rParticipantIdIndividual").val(agreementIndividualsRow.participantIdContracted);
	$("#rIndividualName").val(agreementIndividualsRow.individualNameContracted);
	
	$("#rAgreementParticipants").val(agreementRow.systemNamePayer + " - " + agreementRow.systemNameBeneficiary);
	$("#rAgreementDescription").val(agreementRow.description);
	
	populateSelect("rSdAndRole", //elementId, html select element that will be populated
		       "/rest/ignite/v1/project-participant/sd-role/" + agreementIndividualsRow.projectParticipantIdContracted, //url, url where it must get the data (you can paste in browser window to see the data)
		       "projectParticipantSdRoleId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "sdAndRole", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       selectedProjectParticipantSdRoleId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       false, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	populateSelect("rProjBasedRemunType", //elementId, html select element that will be populated
		       "/rest/ignite/v1/proj-based-remun-type/time-based", //url, url where it must get the data (you can paste in browser window to see the data)
		       "projBasedRemunTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       selectedProjBasedRemunTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);

	$("#rUnitTypeName").val(data.projBasedRemunTypeUnitName);
	$("#rRemunerationIntervalName").val(data.remunerationInterval);
	$("#rRatePerUnit").val(data.ratePerUnit);
	
	if (selectedProjBasedRemunTypeId == 7 ) { // "HOURLY_RATE_WORK"){           //Sit die Default in.  SIEN HIERBO OOK.
		$("#rRemunerationIntervalName").val("Per Hour");
		$("#rUnitTypeName").val("Monthly");				
	}	
	
	
	$("#rDescription").val(data.description);
	$("#rStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(projectStartDate), false) : new Date(data.startDate));
	$("#rEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	
	// Set the Save Button to disabled
	setElementEnabled("saveRemunRateDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	

	
	showModalDialog("remunRateDialog");
	
	
	
	
}
//editRate -- End

//saveRemunRate -- Begin
function saveRemunRate() {
	var postUrl = "/rest/ignite/v1/remuneration-rate-upline/save-rate"; // Here we do not use /new since a stored procedure is called that inserts or updates

	var postData = {
			remunerationRateUplineId: $("#rRemunerationRateUplineId").val() == ""? null: $("#rRemunerationRateUplineId").val(),
			agreementBetweenParticipantsId: $("#rAgreementBetweenParticipantsId").val(),
			projectParticipantSdRoleIdForRate: $("#rSdAndRole").val(),
			projectParticipantIdForExpense: null,
			participantIdIndividual: $("#rParticipantIdIndividual").val(), 
			projBasedRemunTypeId: $("#rProjBasedRemunType").val(),
			recoverableExpenseId: null, 
			expenseHandlingPerc: null, 
			ratePerUnit: $("#rRatePerUnit").val(), 
			description: $("#rDescription").val(), 
			startDate: getMsFromDatePicker("rStartDate"),
			endDate: getMsFromDatePicker("rEndDate")
	};
	
	var errors = "";

	// validate
	if (postData.projBasedRemunTypeId == "") {

		errors += "A Remuneration Type is required<br>";
	}
	
	if ((postData.ratePerUnit != "") && (postData.ratePerUnit != null)){
		if (isNaN(postData.ratePerUnit)) {
			errors += "The Rate has to be numeric<br>";
		}
	} else {
		errors += "The Rate is required<br>";
	}
	
	if ((postData.startDate == "") || (postData.startDate== null)){
		errors += "A Start Date is required<br>";
	} 
	
	if (postData.endDate != null) {
		if (postData.endDate < postData.startDate) {
			errors += "The End Date should be after the Start Date<br>";
		}
	}

	if (showFormErrors("rDlgErrorDiv", errors)) {
		return;
	}
	
	saveEntry(postUrl, postData, "remunRateDialog", "The Rate has been saved.", rRateTable, function(){	
	});
}
//saveRemunRate -- End



function populateRemunIntervalAndUnitType() {
	var selectedRemunTypeCode = $("#rProjBasedRemunType").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  

	var queryUrl;
	queryUrl = "/rest/ignite/v1/proj-based-remun-type/" + selectedRemunTypeCode;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			if (data.hasOwnProperty("remunerationInterval")) {
				//$("#rRemunerationIntervalId").val(data.remunerationInterval.remunerationIntervalId);
				$("#rRemunerationIntervalName").val(data.remunerationInterval.name);
			}
			if (data.hasOwnProperty("unitType")) {
				//$("#rUnitTypeCode").val(data.unitType.unitTypeCode);
				$("#rUnitTypeName").val(data.unitType.name);
			}
			return data;
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}

function remunRateChanged() {
	setElementEnabled("saveRemunRateDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeRemunRateDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("remunRateDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("remunRateDialog");
	}
}




// -----------------------------------------------------------------------

