var expenseAgreementTable = null;
var expenseAgreementParticipantsTable = null;
var expenseRateTable = null;

//*********************************************************
//*********************************************************
//Expense Rate Tab -- Start
//*********************************************************
function initializeExpenseRateTab(projectId) {

	showEmptyExpenseRatePanel();

	// Select all the agreements for this project
	var queryUrl = "/rest/ignite/v1/agreement-between-participants/recoverable-expense/" + projectId;
	var columnsArray = [
	                    
	    { data: "recoverableExpenseId" },			// 0
		{ data: "agreementBetweenParticipantsId" },	// 1
		{ data: "projectId" },						// 2
		{ data: "projectParticipantId" },			// 3
		{ data: "participantIdPayer" },				// 4
		{ data: "systemNamePayer" },				// 5 --
		{ data: "participantIdBeneficiary" },		// 6
		{ data: "systemNameBeneficiary" },			// 7 --
		{ data: "agreement" },						// 8
		{ data: "expenseTypeId" },					// 9
		{ data: "expenseAgreementDescription" },	// 10
		{ data: "expenseTypeName"},					// 11 --
		{ data: "allowHandlingPerc" },				// 12
		{ data: "allowMaxAmtPerUnit" },				// 13
		{ data: "unitTypeCode"},					// 14
		{ data: "unitName"},						// 15 --
		{ data: "level"}							// 16
		];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,3,4,6,8,9,10,12,13,14,16]
		},
		{
			width: "25%",
			targets:[5,7]
		},
		{
			width: "40%",
			targets:11
		},		
		{
			width: "10%",
			targets:15
		}

	];

	var buttonsArray = [];
	
		
	expenseAgreementTable = initializeGenericTable("expenseAgreementTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										25,
										[5,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
			                            
	);

	expenseAgreementTable.off('deselect')
	expenseAgreementTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyExpenseRatePanel();
	} );

	expenseAgreementTable.off('select')
	expenseAgreementTable.on('select', function (e, dt, type, indexes) {
		initializeExpenseRateTable(dt.data(), false);
	} );
}

//Agreement Between Participants for Expense Agreements -- End
//*********************************************************

//*********************************************************

function showEmptyExpenseRatePanel() {

	setDivVisibility("expenseRateEmptyPanel", "block");
	setDivVisibility("expenseRatePanel", "none");

}

//Expense Agreement Individuals Table -- End
//*********************************************************

//*********************************************************
//Expense Rate Table  -- Start

function initializeExpenseRateTable(agreementRow, allRecords) {
	if (agreementRow == null) {
		return;
	}
	
	var recoverableExpenseId = agreementRow.recoverableExpenseId; // The recoverable expense in the agreement
	var allowHandlingPerc = agreementRow.allowHandlingPerc;
	var allowMaxAmtPerUnit = agreementRow.allowMaxAmtPerUnit;
	var unitTypeCode = agreementRow.unitTypeCode;
	
	var queryUrl;
	
	if (allRecords) {
		queryUrl="/rest/ignite/v1/expense-rate/expense-rates/" + recoverableExpenseId;
	} else {  //Show current Records only
		queryUrl="/rest/ignite/v1/expense-rate/expense-rates/current/" + recoverableExpenseId;
	}
		
	setDivVisibility("expenseRateEmptyPanel", "none");
	setDivVisibility("expenseRatePanel", "block");
	
	var columnsArray = [
                   
		{ data: "expenseRateId" },			// 0
		{ data: "recoverableExpenseId" },			// 1
		{ data: "expenseTypeId" },					// 2 
		{ data: "expenseTypeName" },	    		// 3 
		{ data: "expenseTypeUnitCode" },			// 4 
		{ data: "expenseTypeUnitName" },			// 5 --
		{ data: "expenseRatePerUnit" },				// 6 --
		{ data: "expenseHandlingPerc" },			// 7 
		{ data: "maxExpenseAmtPerUnit" },			// 8
		{ data: "description" },					// 9
		{ data: "startDate" },						// 10 --
		{ data: "endDate" }							// 11
	];

	var targetArray = [0,1,2,3,4,7,8,9]

	if (allowHandlingPerc != "Y") {
		targetArray.push(7);
	}
	if (allowMaxAmtPerUnit != "Y") {
		targetArray.push(8);
	}

	if (unitTypeCode == "AMOUNT") {
		targetArray.push(6);
	}

	var columnDefsArray = [
		{
			visible: false,
			targets: targetArray
					
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToPercentage(data);
				}
				
				return html;
			},
			targets: [7]
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				
				return html;
			},
			width: "20%",
			targets: [6,8]
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [10,11]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editExpenseRate(null, agreementRow);
			}
		},
		{
			attr: {
				id: "promptDeleteExpenseRateBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteExpenseRate();
			}
		},
		{
			attr: {
				id: "showAllExpenseRatesBtn"
			},
			titleAttr: "Show All Expense Rates",
			text: "<i class='fas fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
					$("#expenseRemunerationRatesHeading").html("All Expense Rates");
					var requestUrl="/rest/ignite/v1/expense-rate/expense-rates/" + recoverableExpenseId;
					expenseRateTable.rows().deselect();
					expenseRateTable.ajax.url( springUrl(requestUrl) ).load();
			}
		},
		{
			attr: {
				id: "showCurrentExpenseRatesBtn"
			},
			titleAttr: "Show Current Expense Rates",
			text: "<i class='fas fa-clock'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				$("#expenseRemunerationRatesHeading").html("Current Expense Rates");
				var requestUrl="/rest/ignite/v1/expense-rate/expense-rates/current/" + recoverableExpenseId;
				expenseRateTable.rows().deselect();
				expenseRateTable.ajax.url( springUrl(requestUrl) ).load();
			}
		}
	];
	
	expenseRateTable = initializeGenericTable("expenseRateTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editExpenseRate(rowSelector, agreementRow);  //Double click
										},
										null,
										25,
										[10,"desc"] //Order by column 0 ascending, normally defaults to column 1 ascending

	);

	expenseRateTable.off('deselect')
	expenseRateTable.on('deselect', function (e, dt, type, indexes) {
		updateExpenseRateToolbarButtons();
	} );

	expenseRateTable.off('select')
	expenseRateTable.on('select', function (e, dt, type, indexes) {
		updateExpenseRateToolbarButtons();
	} );
	
	updateExpenseRateToolbarButtons();
}

function updateExpenseRateToolbarButtons() {
	var hasSelected = expenseRateTable.rows('.selected').data().length > 0;

	setTableButtonState(expenseRateTable, "promptDeleteExpenseRateBtn", hasSelected);	
}
	
function promptDeleteExpenseRate() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Expense Rate?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteExpenseRate(expenseRateTable);
			   }
	);
}

function deleteExpenseRate(tbl) {
	var postUrl = "/rest/ignite/v1/expense-rate/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Expense Rate has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateRateToolbarButtons();
			}
	);
}

//Rate Table  -- End
//*********************************************************

//*********************************************************
//Expense Rates Tab -- End
//*********************************************************
//*********************************************************

//editExpenseRate -- Start
function editExpenseRate (expenseRateRow, agreementRow) {

	var data = {}; 
	var errors = "";
	var header = "";
	var projectStartDate = getMsFromDatePicker("pdProjectStartDate");

	if (expenseRateRow != null) {
		data = expenseRateTable.row(expenseRateRow).data();
	} else {
		//$("#rUnitTypeCode").val();
		$("#eUnitTypeName").val();
		// $("#rRemunerationIntervalId").val();
		$("#eRemunerationIntervalName").val();
	}
	
	header = "Agreement Expense Rate - " + agreementRow.participantNameBeneficiary; // The Beneficiary name;
	
	expenseRateTable.rows().deselect();
	$("#expenseRateDialogHeader").html(header);
		
	$("#expenseRateId").val(data.expenseRateId);
	$("#eAgreementBetweenParticipantsId").val(agreementRow.agreementBetweenParticipantsId);
	$("#eProjectParticipantIdBeneficiary").val(agreementRow.projectParticipantIdBeneficiary);
	$("#eRecoverableExpenseId").val(agreementRow.recoverableExpenseId);
	$("#eParticipantPayer").val(agreementRow.systemNamePayer);
	$("#eParticipantBeneficiary").val(agreementRow.systemNameBeneficiary);
	$("#eExpenseTypeId").val(agreementRow.expenseTypeId);
	$("#eExpenseTypeName").val(agreementRow.expenseTypeName);
	$("#eUnitName").val(agreementRow.unitName);
	
	if (agreementRow.unitTypeCode == 'AMOUNT') {
		setDivVisibility("expenseRatePerUnitPanel", "none");
		$("#expenseRatePerUnit").val("N/A");
	} else {
		setDivVisibility("expenseRatePerUnitPanel", "block");
		$("#expenseRatePerUnit").val(data.expenseRatePerUnit);
	}
	
	$("#expenseHandlingPerc").val(data.expenseHandlingPerc);
	
	if (agreementRow.allowHandlingPerc == 'Y') {
		setDivVisibility("expenseHandlingPercPanel", "block");
	} else {
		setDivVisibility("expenseHandlingPercPanel", "none");
	}
	
	$("#eMaxExpenseAmtPerUnit").val(data.maxExpenseAmtPerUnit);
	if (agreementRow.allowMaxAmtPerUnit == 'Y') {
		setDivVisibility("eMaxExpenseAmtPerUnitPanel", "block");
	} else {
		setDivVisibility("eMaxExpenseAmtPerUnitPanel", "none");
	}
	
	$("#eDescription").val(data.description);
	$("#eStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(projectStartDate), false) : new Date(data.startDate));

	$("#eEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	
	// Set the Save Button to disabled
	setElementEnabled("saveExpenseRateDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
		
	showModalDialog("expenseRateDialog");
}
//editExpenseRate -- End

//saveExpenseRate -- Begin
function saveExpenseRate() {
	
	var postUrl = "/rest/ignite/v1/expense-rate/save-expense-rate"; // Here we do not use /new since a stored procedure is called that inserts or updates
	var postData = {
			expenseRateId: $("#expenseRateId").val() == ""? null: $("#expenseRateId").val(),
			projBasedRemunTypeId: null,
			recoverableExpenseId: $("#eRecoverableExpenseId").val(), 
			expenseRatePerUnit: $("#expenseRatePerUnit").val(), 
			expenseHandlingPerc: $("#expenseHandlingPerc").val(),
			maxExpenseAmtPerUnit: $("#eMaxExpenseAmtPerUnit").val(),
			description: $("#eDescription").val(), 
			startDate: getMsFromDatePicker("eStartDate"),
			endDate: getMsFromDatePicker("eEndDate")
	};
	var errors = ""; 

	// validate
	
	if (postData.expenseRatePerUnit == "N/A"){
		postData.expenseRatePerUnit = null;
	} else {
		if ((postData.expenseRatePerUnit != "") && (postData.expenseRatePerUnit != null)){
			if (isNaN(postData.expenseRatePerUnit)) {
				errors += "The Expense Rate has to be numeric<br>";
			}
		} else {
			errors += "The Expense Rate is required<br>";
		}
	}

	if ((postData.expenseHandlingPerc != "") && (postData.expenseHandlingPerc != null)){
		if (isNaN(postData.expenseHandlingPerc)) {
			errors += "The Handling Percentage has to be numeric<br>";
		}
	} 

	if ((postData.maxExpenseAmtPerUnit != "") && (postData.maxExpenseAmtPerUnit != null)){
		if (isNaN(postData.maxExpenseAmtPerUnit)) {
			errors += "The Max Expense Amount Per Unit has to be numeric<br>";
		}
	} 
	if ((postData.startDate == "") || (postData.startDate== null)){
		errors += "A Start Date is required<br>";
	} 
	
	if (postData.endDate != null) {
		if (postData.endDate < postData.startDate) {
			errors += "The End Date should be after the Start Date<br>";
		}
	}

	if (showFormErrors("eDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "expenseRateDialog", "The Expense Rate has been saved.", expenseRateTable);
}
//saveExpenseRate -- End

function expenseRateDialogChanged() {
	setElementEnabled("saveExpenseRateDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeExpenseRateDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("expenseRateDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("expenseRateDialog");
	}
}











