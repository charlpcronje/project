var somethingChangedInDialog = false;
var askToSaveDialog = false;

function showSoqTemplateSubScheduleDialog() {
	showModalDialog("soqTemplateSubScheduleDialog");
}

function initializeSoqTemplateSubScheduleDialog(templateData) {
	console.dir(templateData);
	populateSoqTemplateSubScheduleFields(templateData);
	showModalDialog("soqTemplateSubScheduleDialog");
}

function populateSoqTemplateSubScheduleFields(templateData) {
	var insert = false; 
	var enabled = false; 
	var data = {};
	
	
 	
 	if (data.soqTemplSubScheduleId == 0 || templateData == null) {
		 insert = true;
	 } 
    else {
		data = templateData;
	}
 	
	if (!insert) {
		
		enabled = true;
		$("#soqsDlgTempName").val("");
		$("#soqsDlgName").val(data.name);
		$("#soqsDlgDescription").val(data.description);
		$("#soqsDlgSubHeader1").val(data.subReportHeader1);
		$("#soqsDlgSubHeader2").val(data.subReportHeader2);
		$("#soqsDlgPageNumPrefix").val(data.pageNumberPrefix);
		$("#soqsDlgPageNumPostfix").val(data.pageNumberPostFix);
		$("#soqsDlgStartPageNum").val(data.startPageNumber);
		
	} else { // New record 
		$("#soqsDlgTempName").val("");
		$("#soqsDlgName").val("");
		$("#soqsDlgDescription").val("");
		$("#soqsDlgSubHeader1").val("");
		$("#soqsDlgSubHeader2").val("");
		$("#soqsDlgPageNumPrefix").val("");
		$("#soqsDlgPageNumPostfix").val("");
		$("#soqsDlgStartPageNum").val("");
	}
	
	// Set the Save Button to disabled
	/*setElementEnabled("saveGeneralTabButton", false);
	somethingChangedGeneralTab = false;*/
}	

//saveSoqTemplate -- Begin
function saveSoqTemplateSubSchedule() {
	
	var postUrl = "/rest/ignite/v1/soq-template-subschedule/new";
	var postData = {
			 soqTemplateId					: /*$("#soqsDlgTempName").val()*/1,
			 name							: $("#soqsDlgName").val(),
			 description      				: $("#soqsDlgDescription").val(),
			 subReportHeader1      			: $("#soqsDlgSubHeader1").val(),
			 subReportHeader2            	: $("#soqsDlgSubHeader2").val(),
			 pageNumberPrefix               : $("#soqsDlgPageNumPrefix").val(),
			 pageNumberPostFix              : $("#soqsDlgPageNumPostfix").val(),
			 startPageNumber             	: $("#soqsDlgStartPageNum").val()
	};

	var errors = "";


	// validate
	/*if ((postData.scheduleName == null) || (postData.scheduleName == "")) {
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
	};*/
	
	if ((postData.soqTemplateId != null) && (postData.soqTemplateId != "")) {   //update
		var postUrl = "/rest/ignite/v1/soq-template-subschedule";
		
	}
	
	saveEntry(postUrl, postData, "soqTemplateSubScheduleDialog", "The Sub Schedule has been saved.", soqTemplateSubScheduleTable, function(request, response) {
		
		soqTemplateSubScheduleTable.ajax.reload();

	});

}// saveSoqTemplate -- End

function closeSoqTemplateSubScheduleDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("soqTemplateSubScheduleDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("soqTemplateSubScheduleDialog");
	}
}