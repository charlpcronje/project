
function initializeTaskNotifications() {
	var	elementId = "taskNotificationBar";

}

function initializeInboxNotifications() {
	var	elementId = "inboxNotificationBar";

}

function initializeAlertNotifications() {
	var	elementId = "alertNotificationBar";
	
}

function initializeHomePage() {
	initializeTaskNotifications();
	initializeInboxNotifications();
	initializeAlertNotifications();
	
	
	if (typeof _igModel === "undefined") {
		return;
	}
	
	var msg = htmlDecode(_igModel.welcomeMessage);
	$("#welcomeMessage").html(msg);
}

// ***********************************************************************

$(document).ready(function() {
	initializeHomePage();
} );
