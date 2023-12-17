var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var iiInvoicesInTable = null;

var showDefaultFinProj;

//*********************************************************
//CostPerAgreement Tab -- Start
//*********************************************************

function getStatementRelationshipsForDateRange() {
	
	var participantId = $("#finParticipantId").val();
    var dateFromFin = getMsFromDatePicker("stStartDate");
    var dateToFin= getMsFromDatePicker("stEndDate");
    initializeStatementTab(participantId, dateFromFin, dateToFin);

}

function initializeStatementTab(participantId, dateFrom, dateTo) {

	showEmptyStatementDetailPanel();	
	
	var columnsArray = [
			{ data: "theParticipantId" },			// 0 
			{ data: "theParticipant" },				// 1
			{ data: "theOtherParticipantId" },		// 2
			{ data: "theOtherParticipant"},			// 3 -- 
			{ data: "theAmount"}					// 4 --
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2]
		},
		{
			width: "65%",
			targets: [3]
		},
		{
			width: "35%",
			targets: [4]
		},
		{
			render: function(data, type, row) {
				data = row.theAmount;
				if (type == "display") {
						data = valueToRand(data);
				}
				return data;
			},
			className:"dt-right",
			targets: 4
		}
	];
	
	var buttonsArray = [
	            		{
	            			attr: {
	            				id: "stThisMonthBtn"
	            			},
	            			titleAttr: "This Month",
	            			text: "<i class='far fa-calendar-alt'></i>",
	            			className: "btn btn-sm",
	            			action: function( e, dt, node, config ) {
	            				newDateRangeSelected = true;
	            				stThisMonth();
	            			}
	            		},
	            		{
	            			attr: {
	            				id: "stPrevMonthBtn"
	            			},
	            			titleAttr: "Previous Month",
	            			text: "<i class='fas fa-angle-double-left'></i>",
	            			className: "btn btn-sm",
	            			action: function( e, dt, node, config ) {
	            				newDateRangeSelected = true;
	            				stPrevMonth();
	            			}
	            		},
	            		{
	            			attr: {
	            				id: "stNextMonthBtn"
	            			},
	            			titleAttr: "Next Month",
	            			text: "<i class='fas fa-angle-double-right'></i>",
	            			className: "btn btn-sm",
	            			action: function( e, dt, node, config ) {
	            				newDateRangeSelected = true;
	            				stNextMonth();
	            			}
	            		}
		
	];

	var queryUrl="/rest/ignite/v1/statement/relationships-unique/" + participantId + "/" + dateFrom + "/" + dateTo;
	console.log(queryUrl);
	
	statementRelationshipTable = initializeGenericTable("statementRelationshipTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function() {
										},
										null,
										25,
										[3,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);
	
		statementRelationshipTable.off('deselect')
		statementRelationshipTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyStatementDetailPanel();
	} );
	
		statementRelationshipTable.off('select')
		statementRelationshipTable.on('select', function (e, dt, type, indexes) {
		initializeStatementDetailPanel(dt.data(), dateFrom, dateTo);
	} );
	
}

function showEmptyStatementDetailPanel() {
	setDivVisibility("emptyStatementDetailPanel", "block");
	setDivVisibility("statementDetailPanel", "none");
}

function initializeStatementDetailPanel(relationshipRow, dateFrom, dateTo) {

	if (relationshipRow == null) {
		return;
	}
	
	var theParticipantId = relationshipRow.theParticipantId;
	var theOtherParticipantId = relationshipRow.theOtherParticipantId;

	setDivVisibility("emptyStatementDetailPanel", "none");
	setDivVisibility("statementDetailPanel", "block");

	
	var columnsArray = [
   			{ data: "theDate"},					// 0 --
			{ data: "transactionType" },		// 1 -- 
			{ data: "invoiceId"},				// 2 
			{ data: "bankTransactionId" },		// 3
			{ data: "theParticipantId" },		// 4
			{ data: "theParticipant"},			// 5
			{ data: "theOtherParticipantId"},	// 6
			{ data: "theOtherParticipant"},		// 7 --
			{ data: "theNumber"},				// 8 --
			{ data: "theDescription"},			// 9 --
			{ data: "theAmount"}				// 10 --
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [2,3,4,5,6]
		},
		{	width: "12%",targets: [0,1,7,8,10]},		
		{	width: "40%",targets: [9]},		
		
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [0]
		},
		{
			render: function(data, type, row) {
				data = row.theAmount;
				if (type == "display") {
						data = valueToRand(data);
				}
				return data;
			},
			className:"dt-right",
			targets: [10]
		}
	];
	
	var buttonsArray = [
	];

	var queryUrl="/rest/ignite/v1/statement/per-relationship/" + theParticipantId + "/" + theOtherParticipantId + "/" + dateFrom + "/" + dateTo;
	console.log(queryUrl);
	
	statementDetailTable = initializeGenericTable("statementDetailTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										25,
										[0,"asc"] //Order by this column ascending, normally defaults to column 1 ascending
	);

//	statementTable.off('deselect');
//	statementTable.on('deselect', function (e, dt, type, indexes) {
//		updateInvoicesInTableToolbarButtons();
//		iiRowSelected = null;
//		showEmptyInvoiceDetailPanel();	
//	} );
//	
//	statementTable.off('select');
//	iiInvoicesInTable.on('select', function (e, dt, type, indexes) {
//		updateInvoicesInTableToolbarButtons();
//		initializeInvoiceDetailPanel(dt.data());
//	} );
//	
//	updateInvoicesInTableToolbarButtons();
}


//function showEmptyStatementPanel() {
//	setDivVisibility("emptyStatmentPanel", "block");
//	setDivVisibility("statementPanel", "none");
//}
//
//function updateInvoicesInTableToolbarButtons() {
//	var hasSelected = iiInvoicesInTable.rows('.selected').data().length > 0;
//
//	setTableButtonState(iiInvoicesInTable, "promptDeleteInvoiceInBtn", hasSelected);	
//}
//	
//function promptDeleteInvoiceIn() {
//	showDialog("Confirm?",
//		       "Are you sure that you wish to delete the selected Invoice In?",
//		       DialogConstants.TYPE_CONFIRM, 
//		       DialogConstants.ALERTTYPE_INFO, 
//		       function (e) {
//					deleteInvoiceIn(iiInvoicesInTable);
//			   }
//	);
//}
//
//function deleteInvoiceIn(tbl) {
//	var postUrl = "/rest/ignite/v1/invoice/delete";
//	var row = tbl.rows('.selected').data()[0];
//
//	// Disable delete button when record has been deleted.
//	saveEntry(postUrl, row, null, "The Invoice In Entry has been deleted.", tbl,
//			function(){	
//				tbl.rows().deselect();
//				updateInvoicesInTableToolbarButtons()
//				iiInvoicesInTable.ajax.reload();
//			}
//	);
//}
//
//
//function iiThisWeek() {
//
//	var prevSunday = getPreviousSunday(new Date());
//	
//	var dateFrom = new Date(prevSunday);
//	var dateTo = new Date(prevSunday);
//
//	dateTo.setDate(dateFrom.getDate() + 6);
//
//	$("#iiStartDate").datepicker("setDate", new Date(dateFrom));
//	$("#iiEndDate").datepicker("setDate", new Date(dateTo));
//
//	getInvoicesInForDateRange();	
//
//}
//
//function iiPreviousWeek() {
//	
//	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("iiStartDate")));
//	
//	var dateFrom = new Date(prevSunday);
//	var dateTo = new Date(prevSunday);
//
//	dateFrom.setDate(dateFrom.getDate() - 7);
//	dateTo.setDate(dateTo.getDate() - 1);
//
//	$("#iiStartDate").datepicker("setDate", new Date(dateFrom));
//	$("#iiEndDate").datepicker("setDate", new Date(dateTo));
//
//	getInvoicesInForDateRange();	
//} 
//
//function iiNextWeek() {
//	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("iiEndDate")));
//	
//	var dateFrom = new Date(prevSunday);
//	var dateTo = new Date(prevSunday);
//
//	dateFrom.setDate(dateFrom.getDate() + 7);
//	dateTo.setDate(dateTo.getDate() + 13);
//
//	$("#iiStartDate").datepicker("setDate", new Date(dateFrom));
//	$("#iiEndDate").datepicker("setDate", new Date(dateTo));
//
//	getInvoicesInForDateRange();	
//}

function stThisMonth() {
	const now = new Date();
	const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
	const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

	$("#stStartDate").datepicker("setDate", new Date(firstDay));
	$("#stEndDate").datepicker("setDate", new Date(lastDay));

	getStatementRelationshipsForDateRange();	
}

function stNextMonth() {
	
	const currentEndDate = new Date(getMsFromDatePicker("stEndDate"));
	const firstDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 1, 1); //Next month's first day
	const lastDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 2, 0); //Next month's last day
	
	$("#stStartDate").datepicker("setDate", new Date(firstDay));
	$("#stEndDate").datepicker("setDate", new Date(lastDay));

	getStatementRelationshipsForDateRange();	
}

function stPrevMonth() {

	const currentStartDate = new Date(getMsFromDatePicker("stStartDate"));
	const firstDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth() - 1, 1); //Prev month's first day
	const lastDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth(), 0); //Prev month's last day

	$("#stStartDate").datepicker("setDate", new Date(firstDay));
	$("#stEndDate").datepicker("setDate", new Date(lastDay));

	getStatementRelationshipsForDateRange();	
}




