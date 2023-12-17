var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var invoiceOutTable = null;
var invoiceOutLineTable = null;
var invoiceId = null;
var reportFileName = null;

var showDefaultFinProj;

//*********************************************************
//CostPerAgreement Tab -- Start
//*********************************************************

function getInvoicesOutForDateRange() {
	
	var participantId = $("#finParticipantId").val();
    var dateFromFin = getMsFromDatePicker("ioStartDate");
    var dateToFin= getMsFromDatePicker("ioEndDate");
    initializeFinInvoiceOutTab(participantId, dateFromFin, dateToFin);

}

function initializeFinInvoiceOutTab(participantId, dateFrom, dateTo) {

	showEmptyInvoiceOutDetailPanel();	
	
	var columnsArray = [
	           		 { data: "invoiceId" }                		//0 MySql-TableName: Invoice
	           		,{ data: "participantIdFrom" }        		//1
	           		,{ data: "participantIdTo" }          		//2
	           		,{ data: "flagDraft" }             	  		//3
	           		,{ data: "invoiceDate" }              		//4   
	           		,{ data: "invoiceNumber" }            		//5 -- 
	           		,{ data: "upUntilGenerateDate" }      		//6 --
	           		,{ data: "toParticipantName" }        		//7 --
	           		,{ data: "description" }              		//8 --
	           		,{ data: "invoiceAmountExclTax" }           //9 --
	           		,{ data: "invoiceTaxAmount" }            	//10 --
	           		,{ data: "invoiceTotalAmountInclTax" }      //11 --
	           	];

	var columnDefsArray = [
	           		{
	           			visible: false,
	           			targets: [0, 1, 2, 3, 4]
	           		}
	           		,{       //for Date columns
	           			render: function (data, type, row) {
	           				var html = data;
	           				if (type == "display") {
	           					html = timestampToString(data, false);
	           				}
	           				return html;
	           			},
	           			targets: [4, 6]
	           		}
	           		,{        //for Amounts
	           			render: function (data, type, row) {
	           				var html = data;
	           				if (type == "display") {
	           					html = valueToRand(data);
	           				}
	           				return html;
	           			},
	           			className: "dt-right",
	           			targets: [9,10,11]
	           		},
               		{	width: "10%", targets: [5]},	// Invoice Number
               		{	width: "10%", targets: [6]},	// up Until Gen Date
               		{	width: "30%", targets: [7]},	// To Participant
               		{	width: "20%", targets: [8]},	// description
               		{	width: "10%", targets: [9]},	// invoiceAmountExclTax Number
               		{	width: "10%", targets: [10]},	// invoiceTaxAmount Number
               		{	width: "10%", targets: [11]}	// invoiceTotalAmountInclTax Number
	           		
	           	];
	
	var buttonsArray = [
//		{
//        	titleAttr: "New",
//			text: "<i class='fas fa-plus'></i>",
//			className: "btn btn-sm",
//			action: function(e, dt, node, config) {
//        		editInvoiceOut(null,invoiceOutTable); 
//        	}		                    
//		},
		{
			attr: {
				id: "promptDeleteInvoiceOutBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteInvoiceOut();
			}
		},
		{
			attr: {
				id: "ioThisMonthBtn"
			},
			titleAttr: "This Month",
			text: "<i class='far fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				ioThisMonth();
			}
		},
		{
			attr: {
				id: "ioPrevMonthBtn"
			},
			titleAttr: "Previous Month",
			text: "<i class='fas fa-angle-double-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				ioPrevMonth();
			}
		},
		{
			attr: {
				id: "ioNextMonthBtn"
			},
			titleAttr: "Next Month",
			text: "<i class='fas fa-angle-double-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				ioNextMonth();
			}
		},
		{
			attr: {
				id: "ioPrintInvoiceBtn"
			},
			titleAttr: "View Invoice",
			text: "<i class='fas fa-file'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				displayInvoice();
			}
		}
	];

	var queryUrl="/rest/ignite/v1/invoice/invoices-out/" + participantId + "/" + dateFrom + "/" + dateTo;
	console.log(queryUrl);
	
	invoiceOutTable = initializeGenericTable("invoiceOutTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function() {
											// editInvoiceOut(rowSelector,invoiceOutTable);
										},
										null,
										25,
										[4,"asc"] //Order by this column ascending, normally defaults to column 1 ascending
	);
	
	invoiceOutTable.off('deselect')
	invoiceOutTable.on('deselect', function (e, dt, type, indexes) {
		updateInvoiceOutTableToolbarButtons();
		ioRowSelected = null;
		resetInvoiceOutPreview();	
	} );
	
	invoiceOutTable.off('select')
	invoiceOutTable.on('select', function (e, dt, type, indexes) {
		updateInvoiceOutTableToolbarButtons();
		previewInvoiceOutFile(dt.data());
	} );
	
	updateInvoiceOutTableToolbarButtons();
}


function showEmptyInvoiceOutDetailPanel() {
	setDivVisibility("emptyInvoiceOutDetailPanel", "block");
	setDivVisibility("invoiceOutDetailPanel", "none");
}

function updateInvoiceOutTableToolbarButtons() {
	var hasSelected = invoiceOutTable.rows('.selected').data().length > 0;

	setTableButtonState(invoiceOutTable, "promptDeleteInvoiceOutBtn", hasSelected);
	setTableButtonState(invoiceOutTable, "ioPrintInvoiceBtn", hasSelected);
}
	
function promptDeleteInvoiceOut() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Invoice Out?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteInvoiceOut(invoiceOutTable);
			   }
	);
}


//
//function displayInvoice() {
//	var row = invoiceOutTable.rows('.selected').data()[0];
//	var invoiceId = row.invoiceId;
//
//	var url = "/rest/ignite/v1/invoice/create-invoice-report/" + invoiceId;
//	console.log(url);
//
//	window.open(springUrl(url));
//}
//





function deleteInvoiceOut(tbl) {
	var postUrl = "/rest/ignite/v1/invoice/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Invoice In Entry has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateInvoiceOutTableToolbarButtons()
			}
	);
}

function ioThisMonth() {
	const now = new Date();
	const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
	const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

	$("#ioStartDate").datepicker("setDate", new Date(firstDay));
	$("#ioEndDate").datepicker("setDate", new Date(lastDay));

	getInvoicesOutForDateRange();	
}

function ioNextMonth() {
	
	const currentEndDate = new Date(getMsFromDatePicker("ioEndDate"));
	const firstDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 1, 1); //Next month's first day
	const lastDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 2, 0); //Next month's last day
	
	$("#ioStartDate").datepicker("setDate", new Date(firstDay));
	$("#ioEndDate").datepicker("setDate", new Date(lastDay));

	getInvoicesOutForDateRange();	
}

function ioPrevMonth() {

	const currentStartDate = new Date(getMsFromDatePicker("ioStartDate"));
	const firstDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth() - 1, 1); //Prev month's first day
	const lastDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth(), 0); //Prev month's last day

	$("#ioStartDate").datepicker("setDate", new Date(firstDay));
	$("#ioEndDate").datepicker("setDate", new Date(lastDay));

	getInvoicesOutForDateRange();	
}

function previewInvoiceOutFile(row) {
	if (row === undefined) {
		return;
	}
	
	console.log ("previewInvoiceOutFile");
	var fileName = row.fileName;
	var fileType = row.fileType;
	// var originalFileName = row.originalFileName;
	var invoiceId = row.invoiceId;
	
	var url = springUrl("/getfile?t=InvoiceReportGenerated&id=" + invoiceId);
	console.log("/getfile?t=InvoiceReportGenerated&id=" + invoiceId);
	$("#invoiceOutDisplayerFrame").attr("src", url);
	$("#invoiceOutDisplayerFrame").attr("title", fileType);
	$("#invoiceOutDisplayerFrame").attr("style", "border-color: #dae2e7c4; border-style: solid"); 
};

function resetInvoiceOutPreview() {
	$("#invoiceOutDisplayerFrame").attr("src", "");
};



//function initializeInvoiceOutDetailPanel(rowSelector) {
//
//	if (rowSelector != null) {
//		data = rowSelector;
//		invoiceId = data.invoiceId;
//		participantIdFrom = data.participantIdFrom;
//		participantIdTo = data.participantIdTo;
//	} else {
//		return;
//	}
//	
//	console.log(rowSelector);
//	
//	setDivVisibility("emptyInvoiceOutDetailPanel", "none");
//	setDivVisibility("invoiceOutDetailPanel", "block");
//	
//	var columnsArray = [
//			{ data: "invoiceLineId" },			// 0 
//			{ data: "projectId" },				// 1 
//			{ data: "lineType" },				// 2 --
//			{ data: "projectNameText" },		// 3 --
//			{ data: "lineTotal" },				// 4 --
//			{ data: "invoiceId" },				// 5
//			{ data: "participantIdFrom"},		// 6
//			{ data: "participantIdTo"}			// 7
//	];
//	
//	var columnDefsArray = [
//	               		{
//	               			visible: false,
//	               			targets: [0,1,5,6,7]
//	               		},
//	               		{	width: "20%", targets: [2]},
//	               		{	width: "60%", targets: [3]},
//	               		{	width: "20%", targets: [4]},
//						{
//							render: function(data, type, row) {
//							data = row.lineAmount;
//							if (type == "display") {
//								if (data != 0) {
//									data = valueToRand(data);
//								} else {
//									data = ""
//								}
//							}
//							return data;
//							},
//							className:"dt-right",
//							targets: 4
//						}
//	];
//	               		
//	var buttonsArray = [
//	];
//	
//	var queryUrl="/rest/ignite/v1/invoice/lines/" + invoiceId;
//	console.log(queryUrl);
//	
//	invoiceOutLineTable = initializeGenericTable("invoiceOutLineTable",
//			                            queryUrl,
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function() {
//										},
//										null,
//										25,
//										[3,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
//	);
//}


function displayInvoice() {
	var row = invoiceOutTable.rows('.selected').data()[0];
	var invoiceId = row.invoiceId;
	
	var url = "/rest/ignite/v1/invoice/create-invoice-report/" + invoiceId;
	console.log(url);

	window.open(springUrl(url));
}
