var somethingChangedInDialog = false;
var askToSaveDialog = false;

function showSoqTemplateDialog() {
	showModalDialog("soqTemplateDialog");
}

function initializeSoqTemplateDialog(templateData) {
	populateSoqTemplateFields(templateData);
	showModalDialog("soqTemplateDialog");
}

function populateSoqTemplateFields(templateData) {
	var insert = false; 
	var enabled = false; 
	var data = templateData;
	
	
 	
 	if (data.soqTemplateId == 0) {
		 insert = true;
		 data = {};
		 
	 } 
 	
	if (!insert) {
		
		enabled = true;
		$("#soqDlgName").val(data.scheduleName);
		$("#soqDlgDescription").val(data.description);
		$("#soqDlgPrimary").val(data.primaryHeading);
		$("#soqDlgSecondary").val(data.secondaryHeading);
		$("#soqDlgTertiary").val(data.tertiaryHeading);
		$("#soqDlgNumberColumn").val(data.numberColName);
		$("#soqDlgDescriptionColumn").val(data.descriptionColName);
		$("#soqDlgUnitColumn").val(data.unitColName);
		$("#soqDlgQuantityColumn").val(data.rateColName);
		$("#soqDlgRateColumn").val(data.qtyColName);
		$("#soqDlgAmountColumn").val(data.amountColName);
		
	} else { // New record 
		$("#soqDlgName").val("");
		$("#soqDlgDescription").val("");
		$("#soqDlgPrimary").val("");
		$("#soqDlgSecondary").val("");
		$("#soqDlgTertiary").val("");
		$("#soqDlgNumberColumn").val("");
		$("#soqDlgDescriptionColumn").val("");
		$("#soqDlgUnitColumn").val("");
		$("#soqDlgQuantityColumn").val("");
		$("#soqDlgRateColumn").val("");
		$("#soqDlgAmountColumn").val("");
	}
	
	// Set the Save Button to disabled
	/*setElementEnabled("saveGeneralTabButton", false);
	somethingChangedGeneralTab = false;*/
}	

//saveSoqTemplate -- Begin
function saveSoqTemplate() {
	
	var postUrl = "/rest/ignite/v1/soq-template/new";
	var postData = {
			 soqTemplateId					: $("#soqTemplateId").val(),
			 scheduleName					: $("#soqDlgName").val(),
			 description      				: $("#soqDlgDescription").val(),
			 primaryHeading      			: $("#soqDlgPrimary").val(),
			 secondaryHeading            	: $("#soqDlgSecondary").val(),
			 tertiaryHeading                : $("#soqDlgTertiary").val(),
			 numberColName                  : $("#soqDlgNumberColumn").val(),
			 descriptionColName             : $("#soqDlgDescriptionColumn").val(),
			 unitColName                 	: $("#soqDlgUnitColumn").val(),
			 rateColName   					: $("#soqDlgQuantityColumn").val(),
			 qtyColName      				: $("#soqDlgRateColumn").val(),
			 amountColName             		: $("#soqDlgAmountColumn").val()
	};

	var errors = "";


	// validate
	if ((postData.scheduleName == null) || (postData.scheduleName == "")) {
		errors += "Enter a Schedule Name.<br>";
	}
	if ((postData.description == null) || (postData.description == "")) {
		errors += "Enter a Description.<br>";
	}	
	if ((postData.primaryHeading == null) || (postData.primaryHeading == "")) {
		errors += "Enter a Primary Heading.<br>";
	}
	if ((postData.secondaryHeading == null) || (postData.secondaryHeading == "")) {
		errors += "Enter a Secondary Heading.<br>";
	}
	if ((postData.tertiaryHeading == null) || (postData.tertiaryHeading == "")) {
		errors += "Enter a Tertiary Heading.<br>";
	}	
	if ((postData.numberColName == null) || (postData.numberColName == "")) {
		errors += "Enter a Number Column Name.<br>";
	}	
	if ((postData.descriptionColName == null) || (postData.descriptionColName == "")) {
		errors += "Enter a Description Column Name.<br>";
	}	
	if ((postData.unitColName == null) || (postData.unitColName == "")) {
		errors += "Enter a Unit Column Name.<br>";
	}	
	if ((postData.rateColName == null) || (postData.rateColName == "")) {
		errors += "Enter a Rate Column Name.<br>";
	}	
	if ((postData.qtyColName == null) || (postData.qtyColName == "")) {
		errors += "Enter a Quantity Column Name.<br>";
	}	
	if ((postData.amountColName == null) || (postData.amountColName == "")) {
		errors += "Enter an Amount Column Name.<br>";
	}	
	
	if (showFormErrors("soqDlgErrorDiv", errors)) {
		return;
	};
	
	if ((postData.soqTemplateId != null) && (postData.soqTemplateId != "")) {   //update
		var postUrl = "/rest/ignite/v1/soq-template";
		
	}
	
	saveEntry(postUrl, postData, "soqTemplateDialog", "The Template has been saved.", soqTemplateTable, function(request, response) {
		
		soqTemplateTable.ajax.reload();

	});

}// saveSoqTemplate -- End

function closeSoqTemplateDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("soqTemplateDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("soqTemplateDialog");
	}
}