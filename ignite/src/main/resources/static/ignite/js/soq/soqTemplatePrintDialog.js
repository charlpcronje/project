var somethingChangedInDialog = false;
var askToSaveDialog = false;

function closeSoqTemplatePrintDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("soqTemplatePrintDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("soqTemplatePrintDialog");
	}
}