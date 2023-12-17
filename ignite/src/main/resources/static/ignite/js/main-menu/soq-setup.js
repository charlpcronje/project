var currentTab = null;



function showSoqTemplateItemDialog() {
	showModalDialog("soqTemplateItemDialog");
}

function initializeNavTabs() {

	setActiveTab("unitsTabLink");
	
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');
		
		if (activeTab == "Units") {
			currentTab = "Units";
			initializeUnitsTable();
		}
		if (activeTab == "Report Layout") {
			currentTab = "Report Layout";
			initializeReportTemplateTable();
		}
		if (activeTab == "Template Setup") {
			currentTab = "Template Setup";
			initializeTemplateTable();
		}
	});
}

//***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeNavTabs();
	initializeUnitsTable();
	console.log("Initializing page")
	
});
