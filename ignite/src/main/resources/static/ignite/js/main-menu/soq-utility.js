var currentTab = null;

function showSoqScheduleDialog() {
	showModalDialog("soqScheduleDialog");
}

function showSoqSubScheduleDialog() {
	showModalDialog("soqSubScheduleDialog");
}

function showSoqItemDialog() {
	showModalDialog("soqItemDialog");
}

function initializeNavTabs() {
	console.log("test 1")

	setActiveTab("soqAdminTabLink");
	
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');

		if (activeTab == "SOQ Admin") {
			currentTab = "SOQ Admin";
			initializeSoqScheduleTable();
		}
		if (activeTab == "Report Layout") {
			currentTab = "Report Layout";
			initializeReportTable();
		}
	});
}

//***********************************************************************


$(document).ready(function() {
	// Any initialization
	initializeNavTabs();
	initializeSoqScheduleTable();
	console.log("Initializing page")
	
});