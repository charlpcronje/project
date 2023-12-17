var purchaseTable = null;

var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var newDateRangeSelected = false;
const now = new Date();
const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

//const now = new Date();
//const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Three years ago first day
//const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

$(document).ready(function() {

//	setActiveTab("tTimesheetTabAnchor");

	// Any initialization
	newDateRangeSelected = false;
	
	getUserNameIndivId();
	
	initializePurchaseTable();
	
});


//Johannes
function getUserNameIndivId() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#pMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#pMyName").val(volleNaam);
			$("#pMyParticipantId").val(data.participantId);
//			$("#aIndividualLoggerName").val(data.individualId);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
};


// ***********************************************************************