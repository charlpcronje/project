// financials-participant.js //
var newDateRangeSelected = false;
const now = new Date();
const firstDayThreeYearsAgo = new Date(now.getFullYear(), now.getMonth() -36, 1); //Three months back: first day
const lastDayThisMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day	
const firstDayPrevMonth = new Date(now.getFullYear(), now.getMonth() - 1, 1); //Prev month's first day
const lastDayPrevMonth = new Date(now.getFullYear(), now.getMonth(), 0); //Prev month's last day

var dateFromFin = new Date();
var dateToFin= new Date();

//var participantId = null;


function populateFields(finData) {
	
	//++++ when using a new page, rather than a dialog: See participant-edit
	var data = {};
	
 	if (participantId > 0) {
		 data = finData;
	 }
	//++++
	initializeFinSummaryTab(data);

	$("#finParticipantId").val(data.participantId);
	$("#finParticipantSystemName").val(data.systemName);
	$("#finParticipantVat").val(data.vatNumber);
	
}

function initializeWithFinParticipantId() {
	var queryUrl = "/rest/ignite/v1/participant/" + participantId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			populateFields(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function initializeNavTabs() {

	initializeFinSummaryTab(participantId);

	
	$('.nav-tabs a').on('show.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');
		var participantId = $("#finParticipantId").val();
		
//	  	if ($("#aAssetStartDate").val() == "") {
//
//    		$("#aAssetStartDate").val(getEarlierDate(365));    
//	    	// $("#aAssetStartDate").datepicker("setDate", new Date());   //vandag se datum
//	    	$("#aAssetEndDate").datepicker("setDate", new Date());     //vandag se datum
//	    }	
//	    if ($("#ppeStartDate").val() == "") {
//
//    		$("#ppeStartDate").val(getEarlierDate(365));    
//
//	    	// $("#ppeStartDate").datepicker("setDate", new Date(firstDay));
//	    	$("#ppeEndDate").datepicker("setDate", new Date(lastDay));
//	    }

		if (activeTab == "Summary") {
			currentTab = "Summary";
			
		}
		if (activeTab == "Bank Transactions") {
			currentTab = "Bank Transactions";
			
		    var dateFromFin = getMsFromDatePicker("btStartDate");
		    var dateToFin= getMsFromDatePicker("btEndDate");
			
			initializeBankTransactionTab(participantId);
//			initializeBankTransactionTab(participantId, dateFromFin, dateToFin);
		}
		if (activeTab == "Statements") {
			currentTab = "Statements";
			
		    var dateFromFin = getMsFromDatePicker("stStartDate");
		    var dateToFin= getMsFromDatePicker("stEndDate");
			
			initializeStatementTab(participantId, dateFromFin, dateToFin);
		}
		if (activeTab == "Time Cost") {
			currentTab = "Time Cost";
			
		    var dateFromFin = getMsFromDatePicker("tcStartDate");
		    var dateToFin= getMsFromDatePicker("tcEndDate");
			
			initializeFinTimeCostTab(participantId, dateFromFin, dateToFin);
		}
		if (activeTab == "Expense Claims") {
			currentTab = "Expense Claims";
		    
			var dateFromFin = getMsFromDatePicker("fecStartDate");
		    var dateToFin= getMsFromDatePicker("fecEndDate");

		    initializeFinExpenseClaimTab(participantId, dateFromFin, dateToFin);
			
		}
		if (activeTab == "Invoices In") {
			currentTab = "Invoices In";
			var dateFromFin = getMsFromDatePicker("iiStartDate");
		    var dateToFin= getMsFromDatePicker("iiEndDate");

		    initializeFinInvoiceInTab(participantId, dateFromFin, dateToFin);

		}
		if (activeTab == "Invoices Out") {
			currentTab = "Invoices Out";
			var dateFromFin = getMsFromDatePicker("ioStartDate");
		    var dateToFin= getMsFromDatePicker("ioEndDate");

		    initializeFinInvoiceOutTab(participantId, dateFromFin, dateToFin);
		}

		if (activeTab == "Invoice Generator") {
			currentTab = "Invoice Generator";
			
			// var dateFrom = getMsFromDatePicker("igStartDate");
		    var dateTo= getMsFromDatePicker("igEndDate");

		    // initializeFinInvoiceGeneratorTab(participantId, dateFrom, dateTo);
		    initializeFinInvoiceGeneratorTab(participantId, dateTo);
		}

		if (activeTab == "Draft Invoices") {
			currentTab = "Draft Invoices";
			
			// var dateFrom = getMsFromDatePicker("igStartDate");
		    // var dateTo= getMsFromDatePicker("igEndDate");

		    // initializeFinInvoiceGeneratorTab(participantId, dateFrom, dateTo);
		    initializeFinDraftInvoiceTab(participantId);
		}

	});
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization

	$("#tcStartDate").datepicker("setDate", new Date(firstDayThreeYearsAgo));
	$("#tcEndDate").datepicker("setDate", new Date(lastDayThisMonth));
	$("#btStartDate").datepicker("setDate", new Date(firstDayThreeYearsAgo));
	$("#btEndDate").datepicker("setDate", new Date(lastDayThisMonth));
	$("#stStartDate").datepicker("setDate", new Date(firstDayThreeYearsAgo));
	$("#stEndDate").datepicker("setDate", new Date(lastDayThisMonth));
	$("#fecStartDate").datepicker("setDate", new Date(firstDayThreeYearsAgo));
	$("#fecEndDate").datepicker("setDate", new Date(lastDayThisMonth));
	$("#iiStartDate").datepicker("setDate", new Date(firstDayThreeYearsAgo));
	$("#iiEndDate").datepicker("setDate", new Date(lastDayThisMonth));
	$("#ioStartDate").datepicker("setDate", new Date(firstDayPrevMonth));
	$("#ioEndDate").datepicker("setDate", new Date(lastDayThisMonth));
	// $("#igStartDate").datepicker("setDate", new Date(firstDayThreeYearsAgo));
	$("#igEndDate").datepicker("setDate", new Date(lastDayPrevMonth));

	initializeWithFinParticipantId();
	initializeNavTabs();
	showIgDeveloperOption();
	
} );
