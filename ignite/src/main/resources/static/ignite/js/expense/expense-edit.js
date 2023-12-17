// expense-edit //
var currentTab = null;
var localSystemName = null;
const now = new Date();
const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Three years ago first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day


function populateFields(data) {

	$("#expenseHeader").html("<i class='fa fa-info-circle'></i> Participant Expenses Incurred: " + data.systemName);
	
	localSystemName = data.systemName;
	initializeProjectExpenseTab(data.participantId, data.systemName);
}

function initializeExpenseWithParticipantId() {
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

	setActiveTab("projectExpenseTabLink");
	
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');

		if (activeTab == "Expenses Paid") {
			currentTab = "Expenses Paid";
		}
		if (activeTab == "Allowance Claims") {
			currentTab = "Allowance Claims";
			initializeAllowanceClaimTable(participantId, localSystemName);
		}
		if (activeTab == "Asset Purchase") {
			currentTab = "Asset Purchase";
			initializeAssetExpenseTable(participantId, localSystemName);
		}
		
		
		
		
	});
}

//***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeExpenseWithParticipantId();
	initializeNavTabs();
	showIgDeveloperOption();
	
});
