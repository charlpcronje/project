var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var draftTable1 = null;
var draftTable2 = null;
var invoiceId = null;

var showDefaultFinProj;

//*********************************************************
// Draft Invoice Tab -- Start
//*********************************************************
// Table 1
//*********************************************************

function initializeFinDraftInvoiceTab(participantId) {

	showEmptyDraftTable2Panel();	

	var columnsArray = [
	                    { data: "invoiceId" }                //0 Invoice
	                    ,{ data: "participantIdFrom" }        //1
	                    ,{ data: "participantIdTo" }          //2
	                    ,{ data: "flagDraft" }                //3
	                    ,{ data: "invoiceDate" }      		  //4
	                    ,{ data: "upUntilGenerateDate" }      //5 -- 
	                    ,{ data: "invoiceNumber" }            //6 
	                    ,{ data: "toParticipantName" }             //7 -- 
	                    ,{ data: "description" }              //8 
	                    ,{ data: "invoiceTotalAmountInclTax" }            //9 --
	            		,{ data: "ratesMissing" }			  //10 --

	                    ];

	var columnDefsArray = [
	                       {
	                    	   visible: false,
	                    	   targets: [0, 1, 2, 3, 4,6,8]
	                       },
	                       {   width: "20%", targets: [5]},	// upUntilGenerateDate
	                       {	width: "60%", targets: [7]},	// systemNameTo
	                       {	width: "20%", targets: [9]},	// invoiceTotalAmountInclTax
	                       {       //for Date columns
	                    	   render: function (data, type, row) {
	                    		   var html = row.upUntilGenerateDate;
	                    		   if (type == "display") {
	                    			   html = timestampToString(data, false);
	                    		   }
	                    		   return html;
	                    	   },
	                    	   targets: [5]
	                       }
	                       ,{        //for checkboxes
	                    	   render: function (data, type, row) {
	                    		   return "<input type='checkbox' readonly onclick='return false;' " + (row.flagDraft == "Y" ? " checked " : "") + ">";
	                    	   },
	                    	   className: "dt-center",
	                    	   targets:[3]
	                       }
	                       ,{        //for Amounts
	                    	   render: function (data, type, row) {
	                    		    var html = row.invoiceTotalAmountInclTax;
	                    		   // var html;
	                    		   if (type == "display") {
	                    			   if (html == null) {
	                    				   html ='&nbsp;';
	                    			   } else {
	                    				   html = valueToRand(data);
	                    			   }
	                    		   }
	                    		   return html;
	                    	   },
	                    	   className: "dt-right",
	                    	   targets: [9]
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

	var buttonsArray = [
	                    {
	                    	attr: {
	                    		id: "createInvoiceOutBtn"
	                    	},
	                    	titleAttr: "Generate Invoice",
	                    	text: "<i class='fas fa-bolt'></i>",

	                    	className: "btn btn-sm",
	                    	action: function( e, dt, node, config ) {
	                    		var row = draftTable1.rows('.selected').data()[0];
	                    		editInvoiceOut(row, draftTable1); 
	                    	}
	                    },		
	                    {
	                    	attr: {
	                    		id: "promptDeleteDraftInvoiceBtn"
	                    	},
	                    	titleAttr: "Delete",
	                    	text: "<i class='fas fa-minus'></i>",
	                    	className: "btn btn-sm",
	                    	action: function( e, dt, node, config ) {
	                    		promptDeleteDraftInvoice();
	                    	}
	                    }		
	                    ];

	var queryUrl="/rest/ignite/v1/invoice/draft-invoices/" + participantId;
	console.log("Draft Table 1");
	console.log(queryUrl);

	draftTable1 = initializeGenericTable("draftTable1",
			queryUrl,
			columnsArray,
			columnDefsArray,
			buttonsArray,
			null,
			null,
			25,
			[5,"asc"] //Order by this column ascending, normally defaults to column 1 ascending
			);

	draftTable1.off('deselect');
	draftTable1.on('deselect', function (e, dt, type, indexes) {
		updateDraftTable1ToolbarButtons();
		diRowSelected = null;
		showEmptyDraftTable2Panel();	
	} );

	draftTable1.off('select');
	draftTable1.on('select', function (e, dt, type, indexes) {
		updateDraftTable1ToolbarButtons();
		initializeDraftTable2Panel(dt.data());
	} );

	updateDraftTable1ToolbarButtons();
}


function showEmptyDraftTable2Panel() {
	setDivVisibility("emptyDraftTable2Panel", "block");
	setDivVisibility("draftTable2Panel", "none");
	showEmptyDraftTable3Panel();
}

function updateDraftTable1ToolbarButtons() {
	var hasSelected = draftTable1.rows('.selected').data().length > 0;

	setTableButtonState(draftTable1, "promptDeleteDraftInvoiceBtn", hasSelected);
	setTableButtonState(draftTable1, "createInvoiceOutBtn", hasSelected);

}

function promptDeleteDraftInvoice() {
	showDialog("Confirm?",
			"Are you sure that you wish to delete the selected Draft Invoice?",
			DialogConstants.TYPE_CONFIRM, 
			DialogConstants.ALERTTYPE_INFO, 
			function (e) {
		deleteDraftInvoice(draftTable1);
	}
			);
}

function deleteDraftInvoice(tbl) {
	var postUrl = "/rest/ignite/v1/invoice/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Draft Invoice Entry has been deleted.", tbl,
			function(){
		updateDraftTable1ToolbarButtons()
		showEmptyDraftTable2Panel();
	}
			);
}


//*********************************************************
// Draft invoice
// Table 2
//*********************************************************
function initializeDraftTable2Panel(rowSelector) {

	if (rowSelector != null) {
		data = rowSelector;
		invoiceId = data.invoiceId;
		participantIdFrom = data.participantIdFrom;
		participantIdTo = data.participantIdTo;
	} else {
		return;
	}

	console.log(rowSelector);

	setDivVisibility("emptyDraftTable2Panel", "none");
	setDivVisibility("draftTable2Panel", "block");
	showEmptyDraftTable3Panel();
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
	                    				   data = "do not show me";

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

	var buttonsArray = [];

	var queryUrl="/rest/ignite/v1/invoice/line-totals-per-project/" + invoiceId;
	console.log("Draft Table 2");
	console.log(queryUrl);

	draftTable2 = initializeGenericTable("draftTable2",
			queryUrl,
			columnsArray,
			columnDefsArray,
			buttonsArray,
			null,
			null,
			10,
			[4,"asc"] //Order by column 4 ascending, normally defaults to column 1 ascending
			);

	draftTable2.off('deselect');
	draftTable2.on('deselect', function (e, dt, type, indexes) {
		showEmptyDraftTable3Panel();
	} );

	draftTable2.off('select');
	draftTable2.on('select', function (e, dt, type, indexes) {
		initializeDraftTable3(invoiceId, dt.data());
	} );
}

function showEmptyDraftTable3Panel() {
	setDivVisibility("emptyDraftTable3Panel", "block");
	setDivVisibility("draftTable3Panel", "none");
	showEmptyDraftTable4aPanel();
	showEmptyDraftTable4bPanel();
}


//*********************************************************
// Draft invoice 
// Table 3
//*********************************************************
//Table 3
//Available invoices lines to generate for dateRange per Project per Expense Type
function initializeDraftTable3(invoiceId, rowSelector) {
	// Table 3
	if (rowSelector != null) {
		data = rowSelector;
		var projectId = data.projectId;
	} else {
		return;
	}

	setDivVisibility("emptyDraftTable3Panel", "none");
	setDivVisibility("draftTable3Panel", "block");
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
	var buttonsArray = [];

	var queryUrl = "/rest/ignite/v1/invoice/line-totals-per-project-per-expense/" + invoiceId + "/" + projectId;
	console.log("draftTable3");
	console.log(queryUrl);

	draftTable3 = initializeGenericTable("draftTable3",
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

	draftTable3.off('deselect')
	draftTable3.on('deselect', function(e, dt, type, indexes) {
		showEmptyDraftTable4aPanel();
	});

	draftTable3.off('select')
	draftTable3.on('select', function(e, dt, type, indexes) {
		initializeDraftTable4(invoiceId, dt.data());
	});

}

function showEmptyDraftTable4aPanel() {
	setDivVisibility("emptyDraftTable4aPanel", "block");
	setDivVisibility("emptyDraftTable4bPanel", "none");
	setDivVisibility("draftTable4aTimeCostPanel", "none");
	setDivVisibility("draftTable4bExpenseClaimPanel", "none");
	showEmptyDraftTable5aPanel();
}

function showEmptyDraftTable4bPanel() {
	setDivVisibility("emptyDraftTable4aPanel", "none");
	setDivVisibility("emptyDraftTable4bPanel", "block");
	setDivVisibility("draftTable4aTimeCostPanel", "none");
	setDivVisibility("draftTable4aExpenseClaimPanel", "none");
	showEmptyDraftTable5bPanel();
}

//************************************** Time Cost Summary ************************************** //
// Table 4a
//************************************** Time Cost Summary ************************************** // 

function initializeDraftTable4(invoiceId, draftTable3Row) {

	if (draftTable3Row == null) {
		return;
	} else {
		var lineType = draftTable3Row.expenseType;
		if (lineType == "Draft Invoice(s)") {
			showEmptyDraftTable4aPanel
			return;
		}
	}
	var projectId = draftTable3Row.projectId;

	if (lineType == 'Time Related Cost') {
		initializeDraftTable4aTimeCost(invoiceId, projectId, lineType);
	}
	if (lineType == 'Expense(s) Incurred') {
		initializeDraftTable4bExpenseClaim(invoiceId, projectId, lineType);
	}
	//	if (expenseType == 'Invoices Out') {
	//		// initializeLineTotalsInvoicesTable(participantIdContracting, participantIdContracted);
	//	}

}

function initializeDraftTable4aTimeCost(invoiceId, projectId, lineType) {
	//table 4 - TimeCost
	setDivVisibility("emptyDraftTable4aPanel", "none");
	setDivVisibility("emptyDraftTable4bPanel", "none");
	setDivVisibility("draftTable4aTimeCostPanel", "block");
	setDivVisibility("draftTable4bExpenseClaimPanel", "none");
	showEmptyDraftTable5aPanel();


	var columnsArray = [
	                    { data: "projectId" },							// 0
	                    { data: "sdName" },								// 1 --
	                    { data: "sumNrOfUnits" },						// 2 --
	                    { data: "lineTotal" },							// 3 --
	                    { data: "ratesMissing" }						// 4 --
	                    ]

	   var columnDefsArray = [
           {
        	   visible: false,
        	   targets: [0]
           },
           { width: "50%", targets: [1] }, 	// sdName
	       { width: "20%", targets: [2] },	// sumNrOfUnits
	       { width: "20%", targets: [3] }, 	// lineAmount
	       { width: "10%", targets: [4] },	// ratesMissing
	       {
    	   render: function(data, type, row) {
	    		   // data = row.lineTotal;  Not needed data = column 3
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
    	   	targets: 3
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
    	   targets: 4
       }
    ];

	var buttonsArray = [];
	
	// Table 4 - For Time Cost
	var queryUrl = "/rest/ignite/v1/invoice/line-totals-per-project-line-type/" + invoiceId + "/" + projectId + "/" + lineType;
	console.log("Draft Table 4a");
	console.log(queryUrl);

	draftTable4aTimeCost = initializeGenericTable("draftTable4aTimeCost",
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
	draftTable4aTimeCost.off('deselect')
	draftTable4aTimeCost.on('deselect', function(e, dt, type, indexes) {
		showEmptyDraftTable5aPanel();
	});

	draftTable4aTimeCost.off('select')
	draftTable4aTimeCost.on('select', function(e, dt, type, indexes) {
		initializeDraftTable5aTimeCost(invoiceId, projectId, lineType, dt.data());
	});

}

function showEmptyDraftTable5aPanel() {
	setDivVisibility("emptyDraftTable5aPanel", "block");
	setDivVisibility("emptyDraftTable5bPanel", "none");
	setDivVisibility("draftTable5aTimeCostPanel", "none");
	setDivVisibility("draftTable5bExpenseClaimPanel", "none");
}


//Table 5a: details for Time Related Costs
function initializeDraftTable5aTimeCost(invoiceId, projectId, lineType, draftTable4aRow){
	if (draftTable4aRow == null) {
		return;
	}
	setDivVisibility("emptyDraftTable5aPanel", "none");
	setDivVisibility("emptyDraftTable5bPanel", "none");
	setDivVisibility("draftTable5aTimeCostPanel", "block");
	setDivVisibility("draftTable5bExpenseClaimPanel", "none");

	var columnsArray = [
	                    { data: "invoiceLineDetailId" },			// 0
	                    { data: "invoiceLineId" },					// 1
	                    { data: "lineType" },						// 2
	                    { data: "activityDate" },					// 3 --
	                    { data: "projectName" },					// 4
	                    { data: "partBookedTimeOrMadePurchase" },	// 5 --
	                    { data: "sdAndRole"},						// 6 
	                    { data: "stageName" },						// 7
	                    { data: "description"},						// 8 --
	                    { data: "theType" },						// 9 --
	                    { data: "numberOfUnits"},					// 10 --
	                    { data: "rateForDate"},						// 11 --
	                    { data: "lineTotal"},						// 12 --
	                    { data: "rateForDate"}						// 13 --
	                    ]

	 var columnDefsArray = [
       {
    	   visible: false,
    	   targets: [0,1,2,4,6, 7]
       },
       {   width: "5%",	targets: [3]}, 		// Date
       {	width: "20%", 	targets: [5]},		// Participant Name
       {	width: "35%", 	targets: [8]},		// Desription
       {	width: "15%", 	targets: [9]},		// The Type
       {	width: "5%", 	targets: [10]},     // Number of units
       {	width: "5%", 	targets: [11]},		// Rate
       {	width: "10%", 	targets: [12]},		// Line Total
       {	width: "5%", 	targets: [13]},		// Badge

       {
    	   render: function (data, type, row) {
    		   var html = row.activityDate;
    		   if (type == "display") {
    			   html = timestampToString(data, false);
    		   }
    		   return html;
    	   },
    	   targets: [3]
       },
       {
    	   render: function(data, type, row) {
    		   r = row.rateForDate;
    		   if (type == "display") {
    			   if (r > 0) {
    				   r = valueToRand(r);
    			   } else {
    				   r = '&nbsp;';
    			   }
    		   }
    		   return r;
    	   },
    	   className:"dt-right",
    	   targets: [11]
       },
       {
    	   render: function(data, type, row) {
    		   data = row.lineTotal;
    		   if (type == "display") {
    			   if (data > 0) {
    				   data = valueToRand(data);
    			   } else {
    				   data = '&nbsp;';
    			   }
    		   }
    		   return data;
    	   },
    	   className:"dt-right",
    	   targets: 12
       },
       {
    	   render: function(data, type, row) {
    		   data = " ";
    		   l = row.lineTotal;
    		   r = row.rateForDate;
    		   if (type == "display") {
    			   if (l < 0) {
    				   if (r == -4) {
    					   data = '<span class="badge badge-pill badge-info" title="No Agreement Setup" >A</span>'; 
    				   } else {  // This should only be -2 for rates missing in agreement
    					   data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';	
    				   }
    			   } else {
    				   data = '&nbsp;';
    			   }
    		   }
    		   return data;
    	   },
    	   className:"dt-right",
    	   targets: [13]
       }
    ];

	var buttonsArray = [];
	
	// Draft Table 5a : Time Cost Details 
	queryUrl = "/rest/ignite/v1/invoice/invoice-line-detail/" + invoiceId + "/" + projectId + "/" + lineType;
	console.log("Draft Table 5a : TimeCost");
	console.log(queryUrl);

	draftTable5aTimeCost = initializeGenericTable("draftTable5aTimeCost",
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
//Table 4b
////************************************** Expense Claim Summary ************************************** // 

function initializeDraftTable4bExpenseClaim(invoiceId, projectId, lineType) {
	//table 4b - ExpenseClaim

	setDivVisibility("emptyDraftTable4aPanel", "none");
	setDivVisibility("emptyDraftTable4bPanel", "none");
	setDivVisibility("draftTable4aTimeCostPanel", "none");
	setDivVisibility("draftTable4bExpenseClaimPanel", "block");
	showEmptyDraftTable5bPanel();

	var columnsArray = [
	    { data: "projectId" },							// 0
        { data: "theType" },							// 1 --
        { data: "sumNrOfUnits" },						// 2 --
        { data: "lineTotal" },							// 3 --
        { data: "ratesMissing" }						// 4 --
        ]

var columnDefsArray = [
       {
    	   visible: false,
    	   targets: [0]
       },

       { width: "75%", targets: 	[1] },	// expenseTypeName
       { width: "10%", targets: 	[2] },	// sumNrOfUnits
       { width: "10%", targets: 	[3] },	// lineTotal
       { width: "5%", targets: 		[4] },	// ratesMissing
       {
    	   render: function(data, type, row) {
    		   //data = row.lineAmount;
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
    	   targets: 3
       },{
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
    	   targets: 4
       }
       ];

	var buttonsArray = [];

	var queryUrl = "/rest/ignite/v1/invoice/line-totals-per-project-line-type/" + invoiceId + "/" + projectId + "/" + lineType;
	console.log("Expense Claims Draft Table 4b");
	console.log(queryUrl);

	draftTable4bExpenseClaim = initializeGenericTable("draftTable4bExpenseClaim",	// tableElementName 
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

	draftTable4bExpenseClaim.off('deselect')
	draftTable4bExpenseClaim.on('deselect', function(e, dt, type, indexes) {
		showEmptyDraftTable5bPanel();
	});

	draftTable4bExpenseClaim.off('select')
	draftTable4bExpenseClaim.on('select', function(e, dt, type, indexes) {
		initializeDraftTable5bExpenseClaim(invoiceId, projectId, dt.data());
	});

}

function showEmptyDraftTable5bPanel() {
	setDivVisibility("emptyDraftTable5bPanel", "block");
	setDivVisibility("emptyDraftTable5aPanel", "none");
	setDivVisibility("draftTable5aTimeCostPanel", "none");
	setDivVisibility("draftTable5bExpenseClaimPanel", "none");
}


//*********************************************************
//Expense Claims - Draft Table 5b - Recoverable Expenses details -- Start
//*********************************************************

function initializeDraftTable5bExpenseClaim(invoiceId, projectId, draftTable4bRow) {
	if (draftTable4bRow == null) {
		return;
	} else {
		var theExpenseType = draftTable4bRow.theType;
	}

	setDivVisibility("emptyDraftTable5aPanel", "none");
	setDivVisibility("emptyDraftTable5bPanel", "none");
	setDivVisibility("draftTable5aTimeCostPanel", "none");
	setDivVisibility("draftTable5bExpenseClaimPanel", "block");

	var columnsArray = [
	                    { data: "invoiceLineDetailId" },			// 0
	                    { data: "invoiceLineId" },					// 1
	                    { data: "lineType" },						// 2
	                    { data: "activityDate" },					// 3 --
	                    { data: "projectName" },					// 4
	                    { data: "partBookedTimeOrMadePurchase" },	// 5 --
	                    { data: "sdAndRole"},						// 6 
	                    { data: "stageName" },						// 7 
	                    { data: "description"},						// 8 --
	                    { data: "theType" },						// 9 --
	                    { data: "numberOfUnits"},					// 10 --
	                    { data: "rateForDate"},						// 11 --
	                    { data: "lineTotal"},						// 12 --
	                    { data: "rateForDate"}						// 13 --
	                    ]

	 var columnDefsArray = [
       {
    	   visible: false,
    	   targets: [0,1,2,4,6,7]
       },
       {   width: "5%",	targets: [3]}, 			// Date
       {	width: "20%", 	targets: [5]},		// Participant Name
       {	width: "25%", 	targets: [8]},		// Desription
       {	width: "25%", 	targets: [9]},		// The Type
       {	width: "5%", 	targets: [10]},     // Number of units
       {	width: "5%", 	targets: [11]},		// Rate
       {	width: "10%", 	targets: [12]},		// Line Total
       {	width: "5%", 	targets: [13]},		// Badge

       {
    	   render: function (data, type, row) {
    		   var html = row.activityDate;
    		   if (type == "display") {
    			   html = timestampToString(data, false);
    		   }
    		   return html;
    	   },
    	   targets: [3]
       },
       {
    	   render: function(data, type, row) {
    		   r = row.rateForDate;
    		   if (type == "display") {
    			   if (r > 0) {
    				   r = valueToRand(r);
    			   } else {
    				   r = '&nbsp;';
    			   }
    		   }
    		   return r;
    	   },
    	   className:"dt-right",
    	   targets: [11]
       },
       {
    	   render: function(data, type, row) {
    		   data = row.lineTotal;
    		   if (type == "display") {
    			   if (data > 0) {
    				   data = valueToRand(data);
    			   } else {
    				   data = '&nbsp;';
    			   }
    		   }
    		   return data;
    	   },
    	   className:"dt-right",
    	   targets: 12
       },
       {
    	   render: function(data, type, row) {
    		   data = " ";
    		   l = row.lineTotal;
    		   r = row.rateForDate;
    		   if (type == "display") {
    			   if (l < 0) {
    				   if (r == -4) {
    					   data = '<span class="badge badge-pill badge-info" title="No Agreement Setup" >A</span>'; 
    				   } else {  // This should only be -2 for rates missing in agreement
    					   data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';	
    				   }
    			   } else {
    				   data = '&nbsp;';
    			   }
    		   }
    		   return data;
    	   },
    	   className:"dt-right",
    	   targets: [13]
       }
    ];

	var buttonsArray = [];
	
	// Draft Table 5b : Expense Claim Details 
	queryUrl = "/rest/ignite/v1/invoice/invoice-line-expense-detail/" + invoiceId + "/" + projectId + "/" + theExpenseType;
	console.log("Draft Table 5b : Expenses");
	console.log(queryUrl);

	draftTable5bExpenseClaim = initializeGenericTable("draftTable5bExpenseClaim",
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
