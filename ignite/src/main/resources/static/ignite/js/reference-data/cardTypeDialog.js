var somethingChangedInDialog = null;
var askToSaveDialog = null;

function populateCardTypeDialogFields(data) {
	
	$("#cDlgCardTypeId").val(data.cardTypeId);
	$("#cDlgCardTypeCode").val(data.cardTypeCode);
	$("#cDlgName").val(data.name);
	$("#cDlgDescription").val(data.description);

	setElementEnabled("saveCardTypeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
}


function showCardTypeDialog(data, saveCallbackMethod) {
	if (saveCallbackMethod === undefined) {
		saveCallbackMethod = null;
	}
	
	populateCardTypeDialogFields(data);
	showModalDialog("cardTypeDialog", saveCallbackMethod);
}


function saveCardType(tableId) {
	
	var postUrl = "/rest/ignite/v1/card-type/new";
	
	var mode = $("#cDlgMode").val();
	var cardTypeId = $("#cDlgCardTypeId").val();
	var cardTypeCode = $("#cDlgCardTypeCode").val().trim().toUpperCase();
	var name = $("#cDlgName").val();
	var description = $("#cDlgDescription").val();
	var errors = "";
	var table = null;
	
	if (tableId != null) {
		// does it exist
		if ($("#" + tableId).length) {
			// it exists...
			table = $("#" + tableId).DataTable();
		}
	}
	
	var postData = {
		mode: mode,
		cardTypeId : cardTypeId,
		cardTypeCode : cardTypeCode,
		name : name,
		description: description	
	}
	
	if ((postData.cardTypeCode == null) || (postData.cardTypeCode == "")) {
		errors += "A Card Type Code is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.cardTypeId != null) && (postData.cardTypeId != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/card-type";
	} else {
		postData.cardTypeId = null;  //empty string werk nie
	}
	
	// this fails because cardTypeTable is not known
	saveEntry(postUrl, postData, "cardTypeDialog", "The Card Type has been saved.", table);
}



//cardTypeChanged -- Start
function cardTypeChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveCardTypeButton", true);
} // cardTypeChanged -- End



function closeCardTypeDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("cardTypeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("cardTypeDialog");
	}
}


$(document).ready(function() {
	// Any initialization

	showIgDeveloperOption();
} );

