var somethingChangedInDialog = false;
var askToSaveDialog = false;

function closeSoqScheduleDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("soqScheduleDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("soqScheduleDialog");
	}
}