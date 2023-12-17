
var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var newDateRangeSelected = false;
const now = new Date();
const firstDay = new Date(now.getFullYear(), now.getMonth()-36, 1); //Current month's first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day


function populateFields(data) {
	
	var heading = "Timesheet and Expense Details: " + data.systemName;
	$("#timesheetPageHeader").html(heading);
	$("#tParticipantId").val(data.participantId);
	$("#tParticipantSystemName").val(data.systemName);
	
	// initializeTimesheetSummaryTable(data.participantId);
	initializeTimesheetTable(null);
}

function initializeTimesheetWithParticipantId() {
	var queryUrl = "/rest/ignite/v1/participant/" + participantId;
	console.log(queryUrl);

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

	setActiveTab("timesheetTabLink");
	
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');

		participantId = $("#eIndParticipantId").val();
		resourceId = $("#eIndResourceId").val();
	    
		if (activeTab == "Timesheets") {
			currentTab = "Timesheets";
		}
	});
}

//***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTimesheetWithParticipantId();
	initializeNavTabs();
	showIgDeveloperOption();
	
});
