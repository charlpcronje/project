
// -----------------------------------------------------------------------

function initializeNavTabsRatesMultipleTabs() {

	setActiveTab("prTimeRelatedTabLink");

	var currentProjectId = null;
	currentProjectId = $("#pdProjectId").val();

	// This occurs when a tab is made visible, and we therefor want to populate the data
	// react to tab change and load appropriate data
	$('.nav-tabs a').on('show.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');

		// var projectId = $("#pdProjectId").val();

		if (activeTab == "Time Related") {
			currentTab = "Time Related";
			$("#prTabName").val("--subTabTimeRate--");
			currentProjectId = $("#pdProjectId").val();
			initializeTimeRelatedTab(currentProjectId);
		}

		if (activeTab == "Recoverable Expense Rates") {
			currentProjectId = $("#pdProjectId").val();
			
			initializeExpenseRateTab(currentProjectId);
			$("#pdTabName").val("--subTabExpenseRate--");
		}
	});
}

$(document).ready(function() {
	// Any initialization
	initializeNavTabsRatesMultipleTabs();
	showIgDeveloperOption();
	
} );