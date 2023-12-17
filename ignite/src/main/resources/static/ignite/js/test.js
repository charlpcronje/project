function initialize() {
	initializeCardTypeSelect(null);
	
	// This method sets up the buttons and displays the dialog as needed
	initializeDynamicInsertUpdate("cardTypeSelect",             // The element to attach the controls to
	                              "cardTypeDialog",             // the dialog box to popup on insert/edit
	                              "/rest/ignite/v1/card-type",  // the endpoint to get data, note there must be a fetch all endpoint and an endpoint/key to get a singlar item
	                              "cardTypeCode",               // the key field name 
	                              "name",                       // the display fieldname
	                              "insertCardTypeCrud",         // display the insert method name, use null to omit
	                              "editCardTypeCrud"            // display the update method name, use null to omit
	);
}

// populate the card type drop down (also used to refresh the dropdown) 
function initializeCardTypeSelect(selectedId) {
	populateSelect("cardTypeSelect", 
		       "/rest/ignite/v1/card-type", 
		       "cardTypeCode", 
		       "description", 
		       selectedId, 
		       true,
		       null
	);
}

// the edit method, needs to get the required data, then populates and show the dialog
// also forces a refresh of the select after datas been saved
function editCardTypeCrud(dialogId, inputId, fetchUrl, keyField, displayField) {
	var selectedVal = $("#" + inputId).val();
	
	if ((selectedVal == null) || (selectedVal == "")) {
		showToast("Warning", "A value must be selected", DialogConstants.ALERTTYPE_WARNING);
		return;
	}
	
	// We get the specific record to edit by calling it's GET method
	var queryUrl = fetchUrl + "/" + selectedVal;
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			populateCardTypeDialogFields(data);
			
			showModalDialog(dialogId, function(savedObject) {
				// after the save, re populate the list and select the captured
				initializeCardTypeSelect(savedObject.cardTypeCode);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

// the insert method, passes default data then populates and show the dialog
// also forces a refresh of the select after datas been saved
function insertCardTypeCrud(dialogId, inputId, fetchUrl, keyField, displayField) {
	var data = {};  // Any default values could be specified here
	clearDialog(dialogId);
	
	showCardTypeDialog(data, function(savedObject) {
		// after the save, re populate the list and select the captured
		initializeCardTypeSelect(savedObject.cardTypeCode);
	});
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initialize();
} );
