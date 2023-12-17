// finInvoiceInTab.js //
var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var iiInvoicesInTable = null;

var showDefaultFinProj;

//*********************************************************
//CostPerAgreement Tab -- Start
//*********************************************************

function getInvoicesInForDateRange() {
	
	var participantId = $("#finParticipantId").val();
    var dateFromFin = getMsFromDatePicker("iiStartDate");
    var dateToFin= getMsFromDatePicker("iiEndDate");
    initializeFinInvoiceInTab(participantId, dateFromFin, dateToFin);

}

function initializeFinInvoiceInTab(participantId, dateFrom, dateTo) {

	showEmptyInvoiceDetailPanel();	
	
	var columnsArray = [
			{ data: "invoiceId" },				// 0 
			{ data: "invoiceDate"},				// 1 --
			{ data: "participantIdFrom" },		// 2
			{ data: "fromParticipantName"},		// 3 --
			{ data: "participantIdTo" },		 // 4
			{ data: "invoiceNumber"},			  // 5 --
			{ data: "invoiceTotalAmountInclTax"}, // 6 --
			{ data: "description"},				 // 7 --
			{ data: "toParticipantName"}		// 8
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,2,4,8]
		},
		{
			render: function (data, type, row) {
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
				data = row.invoiceTotalAmountInclTax;
				if (data < 0) {
					data = '&nbsp;';
				} else {
					if (type == "display") {
						data = valueToRand(data);
					}
				}
				return data;
			},
			className:"dt-right",
			targets: [6]
		}
	];
	
	var buttonsArray = [
		{
        	titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
        		editInvoiceIn(null); 
        	}		                    
		},
		{
			attr: {
				id: "promptDeleteInvoiceInBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteInvoiceIn();
			}
		},
		{
			attr: {
				id: "iiThisWeekBtn"
			},
			titleAttr: "This Week",
			text: "<i class='far fa-calendar'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				iiThisWeek();
			}
		},
		{
			attr: {
				id: "iiPreviousWeekBtn"
			},
			titleAttr: "Previous Week",
			text: "<i class='fas fa-chevron-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				iiPreviousWeek();
			}
		},
		{
			attr: {
				id: "iiNextWeekBtn"
			},
			titleAttr: "Next Week",
			text: "<i class='fas fa-chevron-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				iiNextWeek();
			}
		},
		{
			attr: {
				id: "iiThisMonthBtn"
			},
			titleAttr: "This Month",
			text: "<i class='far fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				iiThisMonth();
			}
		},
		{
			attr: {
				id: "iiPrevMonthBtn"
			},
			titleAttr: "Previous Month",
			text: "<i class='fas fa-angle-double-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				iiPrevMonth();
			}
		},
		{
			attr: {
				id: "iiNextMonthBtn"
			},
			titleAttr: "Next Month",
			text: "<i class='fas fa-angle-double-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				iiNextMonth();
			}
		}
	];

	var queryUrl="/rest/ignite/v1/invoice/invoices-in/" + participantId + "/" + dateFrom + "/" + dateTo;
	console.log("queryUrl:=" + queryUrl);
	
	iiInvoicesInTable = initializeGenericTable("iiInvoicesInTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editInvoiceIn(rowSelector);
										},
										null,
										25,
										[1,"asc"] //Order by this column ascending, normally defaults to column 1 ascending
	);
	
	iiInvoicesInTable.off('deselect');
	iiInvoicesInTable.on('deselect', function (e, dt, type, indexes) {
		updateInvoicesInTableToolbarButtons();
		iiRowSelected = null;
		showEmptyInvoiceDetailPanel();	
	} );
	
	iiInvoicesInTable.off('select');
	iiInvoicesInTable.on('select', function (e, dt, type, indexes) {
		updateInvoicesInTableToolbarButtons();
		initializeInvoiceDetailPanel(dt.data());
	} );
	
	updateInvoicesInTableToolbarButtons();
}


function showEmptyInvoiceDetailPanel() {
	setDivVisibility("emptyInvoiceDetailPanel", "block");
	setDivVisibility("invoiceDetailPanel", "none");
}

function updateInvoicesInTableToolbarButtons() {
	var hasSelected = iiInvoicesInTable.rows('.selected').data().length > 0;

	setTableButtonState(iiInvoicesInTable, "promptDeleteInvoiceInBtn", hasSelected);	
}
	



function promptDeleteInvoiceIn() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Invoice In?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteInvoiceIn(iiInvoicesInTable);
			   }
	);
}

function deleteInvoiceIn(tbl) {
	var postUrl = "/rest/ignite/v1/invoice/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Invoice In Entry has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateInvoicesInTableToolbarButtons()
//				iiInvoicesInTable.ajax.reload();
			}
	);
}


function iiThisWeek() {

	var prevSunday = getPreviousSunday(new Date());
	
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateTo.setDate(dateFrom.getDate() + 6);

	$("#iiStartDate").datepicker("setDate", new Date(dateFrom));
	$("#iiEndDate").datepicker("setDate", new Date(dateTo));

	getInvoicesInForDateRange();	

}

function iiPreviousWeek() {
	
	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("iiStartDate")));
	
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() - 7);
	dateTo.setDate(dateTo.getDate() - 1);

	$("#iiStartDate").datepicker("setDate", new Date(dateFrom));
	$("#iiEndDate").datepicker("setDate", new Date(dateTo));

	getInvoicesInForDateRange();	
} 

function iiNextWeek() {
	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("iiEndDate")));
	
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() + 7);
	dateTo.setDate(dateTo.getDate() + 13);

	$("#iiStartDate").datepicker("setDate", new Date(dateFrom));
	$("#iiEndDate").datepicker("setDate", new Date(dateTo));

	getInvoicesInForDateRange();	
}

function iiThisMonth() {
	const now = new Date();
	const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
	const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

	$("#iiStartDate").datepicker("setDate", new Date(firstDay));
	$("#iiEndDate").datepicker("setDate", new Date(lastDay));

	getInvoicesInForDateRange();	
}

function iiNextMonth() {
	
	const currentEndDate = new Date(getMsFromDatePicker("iiEndDate"));
	const firstDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 1, 1); //Next month's first day
	const lastDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 2, 0); //Next month's last day
	
	$("#iiStartDate").datepicker("setDate", new Date(firstDay));
	$("#iiEndDate").datepicker("setDate", new Date(lastDay));

	getInvoicesInForDateRange();	
}

function iiPrevMonth() {

	const currentStartDate = new Date(getMsFromDatePicker("iiStartDate"));
	const firstDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth() - 1, 1); //Prev month's first day
	const lastDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth(), 0); //Prev month's last day

	$("#iiStartDate").datepicker("setDate", new Date(firstDay));
	$("#iiEndDate").datepicker("setDate", new Date(lastDay));

	getInvoicesInForDateRange();	
}



function initializeInvoiceDetailPanel(invoiceRow) {

	if (invoiceRow == null) {
		return;
	}
	
}



