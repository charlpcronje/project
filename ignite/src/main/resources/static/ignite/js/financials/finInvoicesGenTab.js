var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var table1 = null;
var table2 = null;
var table3 = null;
var table4aTimeCost = null;
var table4aExpenseClaim = null;
var table5aTimeCost = null;
var table5aExpenseClaim = null;

function getInvoicesToGenerateForDateRange() {

	var participantId = $("#finParticipantId").val();
	var dateToGen = getMsFromDatePicker("igEndDate");
	initializeFinInvoiceGeneratorTab(participantId, dateToGen);
}

//--------------------------------------------------------------------------------------------//
// Invoice Generator
// Table 1
// Available invoices to generate for dateRange Table 1 (Rates missing also shown)

function initializeFinInvoiceGeneratorTab(participantId, dateToGen) {
	// Table 1
	showEmptyTable2Panel();

	var columnsArray = [

		{ data: "participantIdContracting" },		// 0 
		{ data: "participantContracting" },			// 1 --
		{ data: "participantIdContracted" },			// 2
		{ data: "participantContracted" },			// 3
		{ data: "sumNrOfUnits" },					// 4 --
		{ data: "lineAmount" },						// 5 --
		{ data: "ratesMissing" }					    // 6 --
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 2, 3]
		},
		{ width: "70%", targets: [1] },
		{ width: "20%", targets: [5] },
		{ width: "10%", targets: [4, 6] },
		{
			render: function(data, type, row) {
				data = row.lineAmount;
				if (type == "display") {
					if (data != 0) {
						data = valueToRand(data);
					} else {
						data = "none"
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 5
		},
		{
			render: function(data, type, row) {
				data = "";
				if (type == "display") {
					rm = row.ratesMissing;
					if (rm > 0) { // There are one or more rates missing
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 6
		}
	];

	var buttonsArray = [
		{
			attr: {
				id: "igGenerateDraftInvoiceBtn"
			},
			titleAttr: "Generate Draft Invoice",
			text: "<i class='fas fa-exclamation'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				generateDraftInvoice();
			}
		}
	];

	//	var queryUrl="/rest/ignite/v1/invoice/avail-inv-rel-to-gen-not-null/" + participantId + "/" + dateToGen ;
	var queryUrl = "/rest/ignite/v1/invoice/avail-inv-rel-to-gen/" + participantId + "/" + dateToGen;
	console.log("table1");
	console.log(queryUrl);

	table1 = initializeGenericTable("table1",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength, pageLength overides tableHeightPixels 
		[1, "asc"]			// orderArray, column 1 by default
		// null,				// tableHeightPixels, 620px by default  
		// true,				// showTableInfo, true by default
		// true				// showFilter, true by default
	);

	table1.off('deselect')
	table1.on('deselect', function(e, dt, type, indexes) {
		updateTable1ToolbarButtons(null);
		showEmptyTable2Panel();
	});

	table1.off('select')
	table1.on('select', function(e, dt, type, indexes) {
		updateTable1ToolbarButtons(dt.data());
		initializeTable2(dt.data());
	});

	updateTable1ToolbarButtons();
}

function updateTable1ToolbarButtons(rowSelector) {
	var hasSelected = table1.rows('.selected').data().length > 0;

	if (rowSelector != null) {
		if ((rowSelector.lineAmount == null) || (rowSelector.lineAmount <= 0)) {
			hasSelected = false;
		}
	}
	setTableButtonState(table1, "igGenerateDraftInvoiceBtn", hasSelected);
}


function showEmptyTable2Panel() {
	setDivVisibility("emptyTable2Panel", "block");
	setDivVisibility("table2Panel", "none");
	showEmptyTable3Panel();
}

// Table 2
// Available invoices to generate for dateRange per Project
function initializeTable2(rowSelector) {
	// Table 2
	if (rowSelector != null) {
		data = rowSelector;
		var participantIdContracting = data.participantIdContracting;
		var participantIdContracted = data.participantIdContracted;
		var dateToGen = getMsFromDatePicker("igEndDate");

	} else {
		return;
	}

	setDivVisibility("emptyTable2Panel", "none");
	setDivVisibility("table2Panel", "block");
	showEmptyTable3Panel();
	
	var columnsArray = [
		{ data: "projectName" },						// 0 --
		{ data: "participantIdContracting" },			// 1 
		{ data: "projectId" },							// 2 
		{ data: "participantIdContracted" },			// 3
		{ data: "participantContracted" },				// 4 
		{ data: "sumNrOfUnits" },						// 5 --
		{ data: "lineAmount" },							// 6 --
		{ data: "ratesMissing" }						// 7 --

	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [1, 2, 3, 4]
		},
		{ width: "65%", targets: [0] },
		{ width: "20%", targets: [6] },
		{ width: "10%", targets: [5] },
		{ width: "5%", targets: [7] },
		{
			render: function(data, type, row) {
				html = row.expenseType;
				if (type == "display") {
					if (html == "Previous Invoice(s) Sent") {
						data = "";
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 5
		},
		{
			render: function(data, type, row) {
				// data = row.lineAmount;
				if (type == "display") {
					if (data == 0) {
						// quantityFields[i].closest(".item-row").style.visibility = "collapse";
						data = "";

					} else {
						data = valueToRand(data);
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 6
		},
		{
			render: function(data, type, row) {
				data = "";
				if (type == "display") {
					rm = row.ratesMissing;
					if (rm > 0) { // There are one or more rates missing
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 7
		}
	];

	var buttonsArray = [

	];

	// var queryUrl="/rest/ignite/v1/invoice/avail-expense-rel-to-gen/" + participantIdContracted + "/" + participantIdContracting + "/" + dateToGen;
	var queryUrl = "/rest/ignite/v1/invoice/avail-project-rel-to-gen/" + participantIdContracted + "/" + participantIdContracting + "/" + dateToGen;
	console.log("table2");
	console.log(queryUrl);

	table2 = initializeGenericTable("table2",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength 
		[2, "asc"]			// orderArray, column 1 by default
		// null,				// tableHeightPixels, 620px by default  
		// false,				// showTableInfo, true by default
		// false				// showFilter, true by default
	);

	table2.off('deselect')
	table2.on('deselect', function(e, dt, type, indexes) {
		showEmptyTable3Panel(); 
	});

	table2.off('select')
	table2.on('select', function(e, dt, type, indexes) {
		initializeTable3(dt.data());
	});

}

function showEmptyTable3Panel() {
	setDivVisibility("emptyTable3Panel", "block");
	setDivVisibility("table3Panel", "none");
	showEmptyTable4aPanel();
	showEmptyTable4bPanel();
}

// Table 3
// Available invoices lines to generate for dateRange per Project per Expense Type
function initializeTable3(rowSelector) {
	// Table 3
	if (rowSelector != null) {
		data = rowSelector;
		var participantIdContracting = data.participantIdContracting;
		var participantIdContracted = data.participantIdContracted;
		var dateToGen = getMsFromDatePicker("igEndDate");
		var projectId = data.projectId;

	} else {
		return;
	}

	setDivVisibility("emptyTable3Panel", "none");
	setDivVisibility("table3Panel", "block");
	showEmptyTable4aPanel();
	
	var columnsArray = [
		{ data: "expenseType" },						// 0 --
		{ data: "participantIdContracting" },			// 1 
		{ data: "participantContracting" },				// 2 
		{ data: "participantIdContracted" },			// 3
		{ data: "participantContracted" },				// 4 
		{ data: "sumNrOfUnits" },						// 5 --
		{ data: "lineAmount" },							// 6 --
		{ data: "ratesMissing" },						// 7 --
		{ data: "projectId" }							// 8 

	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [1, 2, 3, 4, 8]
		},
		{ width: "60%", targets: [0] },
		{ width: "10%", targets: [5] },
		{ width: "30%", targets: [6] },
		{ width: "5%", targets: [7] },
		{
			render: function(data, type, row) {
				html = row.expenseType;
				if (type == "display") {
					if (html == "Previous Invoice(s) Sent") {
						data = "";
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 5
		},
		{
			render: function(data, type, row) {
				data = row.lineAmount;
				if (type == "display") {
					data = valueToRand(data);
				}
				return data;
			},
			className: "dt-right",
			targets: 6
		},
		{
			render: function(data, type, row) {
				data = "";
				if (type == "display") {
					rm = row.ratesMissing;
					if (rm > 0) { // There are one or more rates missing
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 7
		}
	];

	var buttonsArray = [
	];

	var queryUrl = "/rest/ignite/v1/invoice/avail-proj-expense-rel-to-gen/" + participantIdContracted + "/" + participantIdContracting + "/" + dateToGen + "/" + projectId;
	console.log("table3");
	console.log(queryUrl);

	table3 = initializeGenericTable("table3",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength 
		[2, "asc"]			// orderArray, column 1 by default
		// null,				// tableHeightPixels, 620px by default  
		// false,				// showTableInfo, true by default
		// false				// showFilter, true by default
	);

	table3.off('deselect')
	table3.on('deselect', function(e, dt, type, indexes) {
		showEmptyTable4aPanel();
	});

	table3.off('select')
	table3.on('select', function(e, dt, type, indexes) {
		initializeTable4(dt.data());
	});

}

function showEmptyTable4aPanel() {
	setDivVisibility("emptyTable4aPanel", "block");
	setDivVisibility("emptyTable4bPanel", "none");
	setDivVisibility("table4aTimeCostPanel", "none");
	setDivVisibility("table4bExpenseClaimPanel", "none");
	showEmptyTable5aPanel();
}

function showEmptyTable4bPanel() {
	setDivVisibility("emptyTable4aPanel", "none");
	setDivVisibility("emptyTable4bPanel", "block");
	setDivVisibility("table4aTimeCostPanel", "none");
	setDivVisibility("table4aExpenseClaimPanel", "none");
	showEmptyTable5bPanel();
}



//************************************** Time Cost Summary ************************************** // 
//************************************** Time Cost Summary ************************************** // 

function initializeTable4(relationshipRow) {

	if (relationshipRow == null) {
		return;
	} else {
		var expenseType = relationshipRow.expenseType;
		console.log (expenseType);
		if (expenseType == "Draft Invoice(s)") {
			console.log("binne");
			showEmptyTable4aPanel
			return;
		}
	}
	console.log("verder gegaan");
	var participantIdContracting = relationshipRow.participantIdContracting;
	var participantIdContracted = relationshipRow.participantIdContracted;
	var expenseType = relationshipRow.expenseType;
	var projectId = relationshipRow.projectId;

	if (expenseType == 'Time Related Cost') {
		initializeTable4aTimeCost(participantIdContracting, participantIdContracted, projectId);
	}
	if (expenseType == 'Expense(s) Incurred') {
		initializeTable4bExpenseClaim(participantIdContracting, participantIdContracted, projectId);
	}
	//	if (expenseType == 'Invoices Out') {
	//		// initializeLineTotalsInvoicesTable(participantIdContracting, participantIdContracted);
	//	}

}

function initializeTable4aTimeCost(participantIdContracting, participantIdContracted, projectId) {
	//table 4 - TimeCost
	setDivVisibility("emptyTable4aPanel", "none");
	setDivVisibility("emptyTable4bPanel", "none");
	setDivVisibility("table4aTimeCostPanel", "block");
	setDivVisibility("table4bExpenseClaimPanel", "none");
	showEmptyTable5aPanel();

	var dateToGen = getMsFromDatePicker("igEndDate");

	var columnsArray = [
		{ data: "projectId" },						// 0
		{ data: "projectName" },					// 1
		{ data: "sdName" },							// 2 --
		{ data: "unitTypeName" },					// 3
		{ data: "agreementBetweenParticipantsId" },	// 4
		{ data: "agreementBetween" },				// 5
		{ data: "agreementParticipantIdPayer" },	// 6
		{ data: "agreementPayer" },					// 7
		{ data: "agreementParticipantIdBeneficiary" },	// 8
		{ data: "agreementBeneficiary" },				// 9
		{ data: "projectSdId" },							// 10
		{ data: "remunerationTypeName" },				// 11
		{ data: "sumNrOfUnits" },						// 12 --
		{ data: "lineAmount" },							// 13 --
		{ data: "ratesMissing" }							// 14 --
	]

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11]
		},
		{ width: "50%", targets: [2] }, 	// sdName
		{ width: "20%", targets: [12] },	// sumNrOfUnits
		{ width: "20%", targets: [13] }, 	// lineAmount
		{ width: "10%", targets: [14] },		// ratesMissing
		{
			render: function(data, type, row) {
				data = row.lineAmount;
				if (type == "display") {
					data = valueToRand(data);
				}
				return data;
			},
			className: "dt-right",
			targets: 13
		},
		{
			render: function(data, type, row) {
				var html = "";
				if (type == "display") {
					//					rm = row.ratesMissing;
					if (data > 0) { // There are one or more rates missing
						html = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
					}
				}
				return html;
			},
			className: "dt-right",
			targets: 14
		}
	];

	var buttonsArray = [];

	// Table 4 - For Time Cost
	var queryUrl = "/rest/ignite/v1/invoice/inv-payer-ben-time-cost/" + participantIdContracting + "/" + participantIdContracted + "/" + dateToGen + "/" + projectId;
	console.log("table4a");
	console.log(queryUrl);

	table4aTimeCost = initializeGenericTable("table4aTimeCost",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength 
		[1, "asc"]			// orderArray, column 1 by default
		// null,				// tableHeightPixels, 620px by default  
		// false,				// showTableInfo, true by default
		// false				// showFilter, true by default
	);
	table4aTimeCost.off('deselect')
	table4aTimeCost.on('deselect', function(e, dt, type, indexes) {
		showEmptyTable5aPanel();
	});

	table4aTimeCost.off('select')
	table4aTimeCost.on('select', function(e, dt, type, indexes) {
		initializeTable5aTimeCost(dt.data());
	});

}

function showEmptyTable5aPanel() {
	setDivVisibility("emptyTable5aPanel", "block");
	setDivVisibility("emptyTable5bPanel", "none");
	setDivVisibility("table5aTimeCostPanel", "none");
	setDivVisibility("table5bExpenseClaimPanel", "none");
}


//Table 5a: details for Time Related Costs
function initializeTable5aTimeCost(table4aRow){
	if (table4aRow == null) {
		return;
	}

	var agreementBetweenParticipantsId = table4aRow.agreementBetweenParticipantsId;
	var projectSdId = table4aRow.projectSdId;
	var dateToGen = getMsFromDatePicker("igEndDate");

	setDivVisibility("emptyTable5aPanel", "none");
	setDivVisibility("emptyTable5bPanel", "none");
	setDivVisibility("table5aTimeCostPanel", "block");
	setDivVisibility("table5bExpenseClaimPanel", "none");
	
	var columnsArray = [
		{ data: "rowNumber" },							// 0 
		{ data: "level" },								// 1
		{ data: "activityDate" },						// 2 --
		{ data: "projectName" },						// 3 
		{ data: "agreementBetween" },					// 4 
		{ data: "systemNameThatBookedTime" },			// 5 --
		{ data: "sdAndRole" },							// 6 
		{ data: "stageName" },							// 7 
		{ data: "description" },						// 8 --
		{ data: "unitTypeName" },						// 9 --
		{ data: "numberOfUnits" },						// 10 --
		{ data: "rateForDate" },						// 11 --
		{ data: "" },									// 12 --
		{ data: "remunerationTypeName" },				// 13 
		{ data: "projectParticipantIdThatBookedTime" },	// 14 
		{ data: "" }										// 15 -- Rates missing?

	]

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 4, 6, 7, 13, 14]
			//		targets: [0, 1, 2, 3, 6, 7, 8, 12,13,14]
		},
		{ width: "5%", targets: [2] }, 		// Date
		{ width: "15%", targets: [5] },		// systemNameThatBookedTime
		{ width: "43%", targets: [8] },		// Description
		{ width: "10%", targets: [9] },		// unitTypeName		
		{ width: "5%", targets: [10] },	// numberOfUnits
		{ width: "7%", targets: [11] },	// rateForDate
		{ width: "10%", targets: [12] },	// line total
		{ width: "5%", targets: [15] },		// rates missing
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [2]
		},
		{
			render: function(data, type, row) {
				data = row.rateForDate;
				if (type == "display") {
					if (data < 0) {
						data = '&nbsp;';
					} else {
						data = valueToRand(data);
					}
				}
				return data;
			},
			className: "dt-right",
			targets: [11]
		},
		{
			render: function(data, type, row) {
				data = "";
				r = row.rateForDate;
				u = row.numberOfUnits;
				if (type == "display") {
					if ((r >= 0) && (u >= 0)) {
						if (type == "display") {
							data = valueToRand(row.numberOfUnits * row.rateForDate);
						}
					} else {
						data = '&nbsp;';
					}
				}
				return data;
			},
			className: "dt-right",
			targets: [12]
		},
		{
			render: function(data, type, row) {
				data = "";
				r = row.rateForDate;
				if (type == "display") {
					if (r < 0) {
						if (r == -4) {
							data = '<span class="badge badge-pill badge-info" title="No Agreement Setup" >A</span>';
						} else {  // This should only be -2 for rates missing in agreement
							data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
						}
					} else { // So, there is a rate setup
						data = '&nbsp;';
					}
				}
				return data;
			},
			className: "dt-right",
			targets: [15]
		}
	];

	var buttonsArray = [];

	// Table 5a : Time Cost Details 
	var queryUrl = "/rest/ignite/v1/agreement-between-participants/project-time-cost-detail/" + agreementBetweenParticipantsId + "/" + projectSdId + "/" + dateToGen;
	console.log("Table4a : TimeCost");
	console.log(queryUrl);

	table5aTimeCost = initializeGenericTable("table5aTimeCost",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		null,
		//			                            function(rowSelector) {
		//											editFinTimesheet(rowSelector);
		//										},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength 
		[2, "asc"]			// orderArray, column 1 by default
		// null,				// tableHeightPixels, 620px by default  
		// false,				// showTableInfo, true by default
		// false				// showFilter, true by default
	);
}




////************************************** Expense Claim Summary ************************************** // 
////************************************** Expense Claim Summary ************************************** // 

function initializeTable4bExpenseClaim(participantIdContracting, participantIdContracted, projectId) {
	//table 4b - ExpenseClaim

	setDivVisibility("emptyTable4aPanel", "none");
	setDivVisibility("emptyTable4bPanel", "none");
	setDivVisibility("table4aTimeCostPanel", "none");
	setDivVisibility("table4bExpenseClaimPanel", "block");
	showEmptyTable5bPanel();
	
	var dateToGen = getMsFromDatePicker("igEndDate");


	var columnsArray = [

		{ data: "projectId" },							// 0
		{ data: "projectName" },						// 1 
		{ data: "expenseTypeName" },					// 2 --
		{ data: "unitTypeName" },						// 3 
		{ data: "participantIdContracting" },			// 4
		{ data: "participantInAgreementContracting" },	// 5 
		{ data: "participantIdContracted" },			// 6
		{ data: "participantInAgreementContracted" },	// 7 
		{ data: "sumNrOfUnits" },						// 8 --
		{ data: "lineAmount" },							// 9 --
		{ data: "ratesMissing" },						// 10 --
		{ data: "expenseTypeId" },						// 11 
		{ data: "recoverableExpenseId" }				    // 12 
	]

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 4, 5, 6, 7, 11, 12]
		},

		{ width: "75%", targets: 	[2] },	// expenseTypeName
		{ width: "10%", targets: 	[8] },	// sumNrOfUnits
		{ width: "10%", targets: 	[9] },	// lineAmount
		{ width: "5%", targets: 	[10] },	// ratesMissing
		{
			render: function(data, type, row) {
				data = row.lineAmount;
	    		   if (type == "display") {
	    			   if (data > 0) { // There are one or more rates missing
	    				   data = valueToRand(data);
	    			   } else {
	    				   data = "";
	    			   }
	    		   }
				return data;
			},
			className: "dt-right",
			targets: 9
		},
		{
			render: function(data, type, row) {
				data = "";
				if (type == "display") {
					rm = row.ratesMissing;
					if (rm > 0) { // There are one or more rates missing
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
					}
				}
				return data;
			},
			className: "dt-right",
			targets: 10
		}
	];

	var buttonsArray = [];

	var queryUrl = "/rest/ignite/v1/invoice/inv-payer-ben-expense-claim/" + participantIdContracting + "/" + participantIdContracted + "/" + dateToGen+ "/" + projectId;
	console.log("Expense Claims table 4b");
	console.log(queryUrl);

	table4bExpenseClaim = initializeGenericTable(
		"table4bExpenseClaim",	// tableElementName 
		queryUrl,			//	queryUrl 
		columnsArray,		//	columnsArray 
		columnDefsArray,	//	columnDefsArray 
		buttonsArray,		//	buttonsArray
		function() {
		},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength 
		[1, "asc"]			// orderArray, column 1 by default
		// null,					// tableHeightPixels, 620px by default  
		// false,				// showTableInfo, true by default
		// false				// showFilter, true by default
	);

	table4bExpenseClaim.off('deselect')
	table4bExpenseClaim.on('deselect', function(e, dt, type, indexes) {
		showEmptyTable5bPanel();
	});

	table4bExpenseClaim.off('select')
	table4bExpenseClaim.on('select', function(e, dt, type, indexes) {
		initializeTable5bExpenseClaim(dt.data());
	});

}

function showEmptyTable5bPanel() {
	setDivVisibility("emptyTable5bPanel", "block");
	setDivVisibility("emptyTable5aPanel", "none");
	setDivVisibility("table5aTimeCostPanel", "none");
	setDivVisibility("table5bExpenseClaimPanel", "none");
}


//*********************************************************
//Expense Claims - Table 5b - Recoverable Expenses details -- Start
//*********************************************************

function initializeTable5bExpenseClaim(table4bRow) {
	if (table4bRow == null) {
		return;
	}
	var expenseTypeId = table4bRow.expenseTypeId;
	var participantIdContracting = table4bRow.participantIdContracting;
	var participantIdContracted = table4bRow.participantIdContracted;
	var dateToGen = getMsFromDatePicker("igEndDate");


	setDivVisibility("emptyTable5aPanel", "none");
	setDivVisibility("emptyTable5bPanel", "none");
	setDivVisibility("table5aTimeCostPanel", "none");
	setDivVisibility("table5bExpenseClaimPanel", "block");

	var columnsArray = [
		{ data: "rowNumber" },							// 0
		{ data: "purchaseDate" },						// 1 --
		{ data: "projectName" },						// 2 
		{ data: "participantMadePurchaseSystemName" },	// 3 --
		{ data: "expenseTypeName" },					// 4 
		{ data: "expenseHandlingPerc" },				// 5
		{ data: "maxExpenseAmtPerUnit" },				// 6
		{ data: "paymentDescription" },					// 7 --
		{ data: "numberOfUnits" },						// 8 --
		{ data: "amountPerUnit" },						// 9 --
		{ data: "expenseRateForDate" },					// 10 --
		{ data: "lineTotal" },							// 11 --
		{ data: "" }									// 12 --
	]

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 2,4, 5, 6]
		},
		{ width: "5%", targets: [1] }, 		// Date
		{ width: "25%", targets: [3] },		// Participant Name
		{ width: "25%", targets: [7] },		// Description
		{ width: "10%", targets: [8] },		// Number of Units
		{ width: "10%", targets: [9] },		// Amount Per Unit
		{ width: "10%", targets: [10] },	// Expense Rate For Date
		{ width: "10%", targets: [11] },	// Line Total
		{ width: "5%", targets: [12] },		// Rates Missing
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [1]
		},
		{
			render: function(data, type, row) {
				r = row.amountPerUnit;
				if (r > 0) {
					r = valueToRand(r);
				} else {
					r = '&nbsp;';
				}
				return r;
			},
			className: "dt-right",
			targets: [9]
		},
		{
			render: function(data, type, row) {
				r = row.expenseRateForDate;
				if (r > 0) {
					r = valueToRand(r);
				} else {
					r = '&nbsp;';
				}
				return r;
			},
			className: "dt-right",
			targets: [10]
		},
		{
			render: function(data, type, row) {
				data = "";
				l = row.lineTotal;
				if (l > 0) {
					if (type == "display") {
						data = valueToRand(l);
					}
				} else {
					data = '&nbsp;';
				}
				return data;
			},
			className: "dt-right",
			targets: [11]
		},
		{
			render: function(data, type, row) {
				r = row.expenseRateForDate;
				if (r < 0) {
					if (r == -4) {
						data = '<span class="badge badge-pill badge-info" title="No Agreement Setup" >A</span>';
					} else {  // This should only be -2 for rates missing in agreement
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
					}
				} else { // So, there is a rate setup
					data = '&nbsp;';
				}
				return data;
			},
			className: "dt-right",
			targets: [12]
		}

	];

	var buttonsArray = [];
	var dateFrom = 0;
	queryUrl = "/rest/ignite/v1/financials/contracting-contracted-expenses/" + expenseTypeId + "/" + dateFrom + "/" + dateToGen + "/" + participantIdContracting + "/" + participantIdContracted;
	console.log("Table 5b: Expense Claim");
	console.log(queryUrl);

	table5bExpenseClaim = initializeGenericTable("table5bExpenseClaim",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			showPurchaseDetails(rowSelector);
		},					//	callbackMethod 
		null,				//	completeMethod 
		10,					//	pageLength 
		[1, "asc"]			// orderArray, column 1 by default
		// null,				// tableHeightPixels, 620px by default  
		// false,				// showTableInfo, true by default
		// false				// showFilter, true by default
	);

}
















//generateDraftInvoice() -- Start
function generateDraftInvoice() {
	var row = table1.rows('.selected').data()[0];

	var postUrl = "/rest/ignite/v1/invoice/generate-draft-invoice/";

	var postData = {

		//****************************************************
		// ProjectParticipant table
		//****************************************************
		participantIdFrom: row.participantIdContracted,
		participantIdTo: row.participantIdContracting,
		invoiceNumber: "",
		invoiceDate: new Date().getTime(),
		description: "",
		invoiceTotalAmountInclTax: 0,
		invoiceUpUntilDate: getMsFromDatePicker("igEndDate")  // 1 ms before midnight
	}

	var errors = "";

	if ((postData.participantIdFrom == null) || (postData.participantIdFrom == "")) {
		errors += "A Contracted Participant is required<br>";
	}

	if ((postData.participantIdTo == null) || (postData.participantIdTo == "")) {
		errors += "A Contracting Participant is required<br>";
	}

	if (showFormErrors("igTabDlgErrorDiv", errors)) {
		return;
	}

	console.log(postUrl);
	console.log(postData);

	saveEntry(postUrl, postData, null, "The Invoice has been generated.", null, function(request, response) {

		var data = response;
		var invoiceId = data.invoiceId;
		console.log(invoiceId);
		// $("#eIndIndividualId").val(individualId);

		table1.rows().deselect();
		updateTable1ToolbarButtons()
		table1.ajax.reload();
	}
	);
}
