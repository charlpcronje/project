function initializeLoginForm() {
}


function showTimeoutIfNecessary() {
	var timeout = getCookie("timeout");
	var showTimeout = ((timeout != null) && (timeout != ""));
	
	deleteCookie("timeout");
	
	// Only show if we don't have a message
	var id = document.getElementById("messageDiv");
	if (id != null) {
		showTimeout = false;
	}
	
	setDivVisibility("timeoutMsgDiv", showTimeout ? "block" : "none");
}

// *******************************************************************************

$(document).ready(function() {
	showTimeoutIfNecessary();
	initializeLoginForm();
} );

