var usernameUnique = null;
var currentRowSelector = null;
var officeStartDate = null;
var passwordData = {};

//initializeSystemDetailsTab -- Start
function initializeSystemDetailsTab(participantId) {

}//initializeSystemDetailsTab -- End


function showHideTapSubscription() {
	var mySelect = document.getElementById("epTapSubscriptionCode");
	var tapAdministered = $("#epTapAdministered").is(":checked");
	systemDetailsTabChanged();
	setDivVisibility("epTapSubscriptionDiv", tapAdministered ? "block" : "none");
	if (!tapAdministered) {
		mySelect.selectedIndex = 0;
//		$("#epTapSubscriptionDiv").val(null)
	}
}	



// saveParticipantChangePassword -- Begin
function saveParticipantChangePassword() {
	var currentPassword = passwordData.currentPassword;
	console.log(currentPassword);
	
	var postUrl = "/rest/ignite/v1/participant"; //get the url for the participant
	var postData = {
			pass: $("#pcpCurrentPassword").val(),
			//participantId: $("#epoParticipantOfficeId").val(),
			
	};
	var errors = "";
	// validate
	
	if (showFormErrors("pcpDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "participantChangePasswordDialog", "The Password has been saved.");
	
}
// saveParticipantChangePassword -- End



//participantChangePasswordChanged -- Start
function participantChangePasswordChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveParticipantChangePasswordButton", true);
}
//participantChangePasswordChanged -- End



//closeParticipantChangePasswordDialog -- Start
function closeParticipantChangePasswordDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("pcpDlgErrorDiv", "none");
				closeModalDialog("participantChangePasswordDialog");
			});
	} else {
		setDivVisibility("pcpDlgErrorDiv", "none");
		closeModalDialog("participantChangePasswordDialog");
	};
	somethingChangedInDialog = false;
}
//closeParticipantChangePasswordDialog -- End



//closeParticipantAllowLoginDialog -- Start
function closeParticipantAllowLoginDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("alDlgErrorDiv", "none");
				closeModalDialog("participantAllowLoginDialog");
			});
	} else {
		setDivVisibility("alDlgErrorDiv", "none");
		closeModalDialog("participantAllowLoginDialog");
	};
	somethingChangedInDialog = false;
}
//closeParticipantAllowLoginDialog -- End



//saveSystemDetailsTab -- Start
function saveSystemDetailsTab() {

	var isIndividual = $("#epIsIndividual").val();
	
	if (isIndividual == "Y") {
		saveIndividual();
	} else {
		saveParticipantNonIndividual();
	}
}





//systemDetailsTabChanged -- Start
function systemDetailsTabChanged() {
	currentTab = "System Details";
	// askToSaveTab = true;
	somethingChangedGeneralTab = true;
	setElementEnabled("saveSystemDetailsTabButton", true);
}



//passwordChanged -- Start
function passwordChanged() {
	currentTab = "System Details";
	// askToSaveTab = true;
	somethingChangedPassword = true;
	setElementEnabled("saveSystemDetailsTabButton", true);
}

