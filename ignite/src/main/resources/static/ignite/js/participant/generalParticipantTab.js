  var usernameUniqueGpt = null;
  var currentRowSelector = null;
  var officeStartDate = null;
  var askToSaveDialog = false;
  var somethingChangedGeneralTab = false;
  var somethingChangedPassword = false;

  
function eIndUpdateAllowLogin() {
	
	var allowLogin = $("#eIndAllowLoginFlag").is(":checked");
	generalTabChanged();
	setDivVisibility("eIndLoginInfoPanel", allowLogin ? "block" : "none");
	if( $('#eIndAllowLoginFlag').is(':checked') ){
		if ($("#epOriginalAllowLoginFlag").val() == 'N') {
			document.getElementById("editPasswordButton").style.display = "none";
			document.getElementById("eIndPassword").disabled = false;
			$("#sdUserName2").val($("#gptEmailAddress").val());
			$("#eIndPassword").val("");
			passwordChanged();
		} else {
			document.getElementById("editPasswordButton").style.display = "block";
			document.getElementById("eIndPassword").disabled = true;
			$("#eIndPassword").val("*****");
		}
	} else {
		$("#eIndPassword").val("*****");
	}
//	if (($("#sdUserName2").val() == "") && (allowLogin)){
////		$("#sdUserName2").val($("#gptEmailAddress").val());
//		console.log("In eIndUpdateAllowLogin sdUserName2=" + $("#sdUserName2").val())
//	}
//	if (($("#sdUserName2").val() != "") && (allowLogin)){
//		console.log("In eIndUpdateAllowLogin   net voor checkUsernameUniquenessIndividual   " + $("#sdUserName2").val())
//		checkUsernameUniquenessIndividual();
//	}

}		    	



function setInvDateToLastDay() {
	var lastDayOfTheMonthChecked  = $("#epLastDayOfTheMonth").is(":checked");
	generalTabChanged();
	$("#epDefaultInvoiceDay").val(lastDayOfTheMonthChecked ? 31 : 1);
	setElementEnabled("epDefaultInvoiceDay", $("#epDefaultInvoiceDay").val() > 30 ? false: true); 
}		    	


function eSetInvDateToLastDay() {
	var lastDayOfTheMonthChecked  = $("#eLastDayOfTheMonth").is(":checked");
	generalTabChanged();
	$("#epDefaultInvoiceDay").val(lastDayOfTheMonthChecked ? 31 : 1);
	setElementEnabled("epDefaultInvoiceDay", $("#epDefaultInvoiceDay").val() > 30 ? false: true); 
}



function eCheckForLastDay() {
	var invoiceDayOfTheMonth  = $("#epDefaultInvoiceDay").val();
	generalTabChanged();

	if 	($("#epDefaultInvoiceDay").val(lastDayOfTheMonthChecked) ? 31 : 1);
}		    	

//app.directive('onlyInteger', onlyInteger);
//}());



function checkUsernameUniquenessIndividual() {
	var usernameIndividual;
	var allowLoginFlag;
	var queryUrl;
	
	usernameUniqueGpt = false;
	
	
	
	//generalTabChanged();
	usernameIndividual = $("#sdUserName2").val();
	allowLoginFlag = $("#eIndAllowLoginFlag").is(":checked") ? "Y" : "N";
	var indId = $("#eIndIndividualId").val();
	
	if (indId == "") {
		// To fix the unique check for a new individual we send through a 0 ID for the individualId
		indId = "0";
	}

	queryUrl = "/rest/ignite/v1/individual/unique/" + indId + "/" + usernameIndividual;

	console.log("In checkUsernameUniquenessIndividual queryUrl:=" + queryUrl + "  allowLoginFlag:=" + allowLoginFlag);
	
	// set our initial state
	usernameUniqueGpt = false;

	if (allowLoginFlag == "N") {
		return;
	}
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			usernameUniqueGpt = data.unique;
			console.log("unique = " + usernameUniqueGpt);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}



// saveGeneralTab -- Start
function saveGeneralTab() {

	var isIndividual = $("#epIsIndividual").val();

	if (isIndividual == "Y") {
		saveIndividual();
	} else {
		saveParticipantNonIndividual();
	}
}
	


//saveIndividual() -- START 
// /new-participant 		--1.--saveNewIndividualParticipant.sql  (in IndividualController) <--insert
// /existing-participant	--2.--saveIndividualParticipant.sql 	(in IndividualController) <--update
function saveIndividual() {	
	
	$("#eIndIdNumber").val($("#eIndIdNumber").val().replace(/ /g,'')); //remove spaces 	
	
	$("#eIndFirstName").val(columnNameToTitle($("#eIndFirstName").val()))
	$("#eIndSecondName").val(columnNameToTitle($("#eIndSecondName").val()))
	$("#eIndThirdName").val(columnNameToTitle($("#eIndThirdName").val()))
	$("#eIndLastName").val(columnNameToTitle($("#eIndLastName").val()))
	$("#eIndNickName").val(columnNameToTitle($("#eIndNickName").val()))

//	if (($("#epOriginalAllowLoginFlag").val() == 'N') && ($("#eIndAllowLoginFlag").is(":checked"))) {
//		checkUsernameUniquenessIndividual();
//		console.log("gecheck")
//	}
	
	
	
	var postUrl = "/rest/ignite/v1/individual/new-participant";
	
	var postData = {

			//****************************************************
			// ProjectParticipant table
			//****************************************************
			projectParticipantId : $("#epProjectParticipantId").val(),
			participantId : $("#epParticipantId").val(),

			//****************************************************
			// Individual table
			//****************************************************
			individualId : $("#eIndIndividualId").val(),
			firstName : $("#eIndFirstName").val(), 
			secondName : $("#eIndSecondName").val(), 
			thirdName : $("#eIndThirdName").val(), 
			lastName : $("#eIndLastName").val(), 
			nickName : $("#eIndNickName").val(), 
			
			tradingName : $("#indTradingName").val(), 
			
			initials : $("#eIndInitials").val(),  //getInitials(columnNameToTitle($("#eIndFirstName").val()), secondName, thirdName).trim().toUpperCase(),
			
			idNumber : $("#eIndIdNumber").val(),
			passportNumber : $("#eIndPassportNumber").val(),
			countryId : $("#eIndCountryId").val(),
			incomeTaxNumber : $("#eIndIncomeTaxNumber").val(),
			allowLoginFlag : $("#eIndAllowLoginFlag").is(":checked") ? "Y" : "N",
			isActiveMember : $("#eIndIsActiveMember").is(":checked") ? "Y" : "N",
			userName : $("#sdUserName2").val(),
			pass : $("#eIndPassword").val(),
			roleCode: $("#eIndRoleCode").val(),

			//****************************************************
			//Participant table
			//****************************************************
			projectIdSustenance: $("#epProjectIdSustenance").val(),
			
			tapAdministered : $("#epTapAdministered").is(":checked") ? "Y" : "N",
								
			tapSubscriptionCode : $("#epTapSubscriptionCode").val(),
			participantTypeCode : "INDIVIDUAL", 
			representativeIndividualId : null ,
			marketingIndividualId : null,
			registeredName: null, //$("#eIndFirstName").val() + " " + $("#eIndLastName").val(),
			
			isIndividual : "Y",
			registrationNumber : null,
			vatNumber : null,
			projectPrefix : $("#epProjectPrefix").val(),
			latestProjectNumber : $("#epLatestProjectNumber").val(),
			projectPostfix : $("#epProjectPostfix").val(), 
			// If LastDayOfMonth is checked = 31, if non numeric set to day 1, else use day entered
			defaultInvoiceDay : $("#eLastDayOfTheMonth").is(":checked") ? 31 : 
												(isNumber($("#epDefaultInvoiceDay").val()) ? $("#epDefaultInvoiceDay").val():1),
			systemName: $("#epSystemName").val(),
			

			invoicePrefix: $("#epInvoicePrefix").val().trim(),
			latestInvoiceNumber: $("#epLatestInvoiceNumber").val(),
			invoiceNumberFormat: $("#epInvoiceNumberFormat").val(),

			//****************************************************
			//SystemMember table
			//****************************************************
			systemMemberId : $("#eIndSystemMemberId").val() == "" ? null : $("#eIndSystemMemberId").val(),  // Set to null if empty,

			startDate: getMsFromDatePicker("epStartDate"),
			endDate: getMsFromDatePicker("epEndDate"),
			
			//****************************************************
			//ContactPoint table
			//****************************************************
			contactPointId : $("#epContactPointIdDefault").val() == "" ? null : $("#epContactPointIdDefault").val(),  // Set to null if empty,
			
			cityId : $("#epCityId").val(),
			suburbId : $("#epSuburbId").val(),
			addressLine1 : $("#epAddressLine1").val(),
			addressLine2 : $("#epAddressLine2").val(),
			addressLine3 : $("#epAddressLine3").val(),
			contactPointName : $("#epName").val(),
			emailAddress : $("#gptEmailAddress").val(),
			phoneNumber : $("#epPhoneNumber").val(),

			//****************************************************
			//ParticipantOffice table
			//****************************************************
			participantOfficeId : $("#epParticipantOfficeIdDefault").val() == "" ? null : $("#epParticipantOfficeIdDefault").val(),  // Set to null if empty,
			participantOfficeName : $("#epParticipantOfficeName").val(),
			participantOfficeDescription : $("#epParticipantOfficeDescription").val(), 

			//****************************************************
			//Project table
			//****************************************************
	
			projectNumberBigInt : $("#epLatestProjectNumber").val(), 
	
	};
	
	$("#eIndInitials").val(postData.initials )

	var errors = "";
	
	if ((postData.userName == null) || (postData.userName == "")) {
		postData.userName = postData.emailAddress;
	}	
	if ((postData.firstName == null) || (postData.firstName == "")) {
		errors += "A First Name is required<br>";
	}
	if ((postData.nickName == null) || (postData.nickName == "")) {
		errors += "A Nickname is required<br>";
	}	
	if ((postData.emailAddress == null) || (postData.emailAddress == "") || (postData.emailAddress.indexOf('@') < 0)) {
		errors += "A valid Email Address is required<br>";
	}	
	
	if ((postData.phoneNumber == null) || (postData.phoneNumber == "")) {
		errors += "A Phone Number is required<br>";
	}		

	if ((postData.lastName == null) || (postData.lastName == "")) {
		errors += "A Last Name is required<br>";
	}
	
	if ((postData.idNumber != null) && (postData.idNumber != "")) {
		if ((postData.idNumber.substring(2, 3) != "0") && (postData.idNumber.substring(2, 3) != "1")) {
			errors += "The ID Number is not valid<br>";
		}
		if ((postData.idNumber.substring(4, 5) != "0") && (postData.idNumber.substring(4, 5) != "1") && (postData.idNumber.substring(4, 5) != "2") && (postData.idNumber.substring(4, 5) != "3")) {
			errors += "The ID Number is not valid<br>";
		}
		if (postData.idNumber.length != 13) {
			errors += "The ID Number is not valid<br>";
		}	
		if ((postData.idNumber.substring(2, 4) > 12) || (postData.idNumber.substring(2, 4) == "00") || (postData.idNumber.substring(4, 6) > 31)) {
			errors += "The ID Number is not valid<br>";
		}	
	}
	
//console.dir(postData);

	if ((postData.systemName == null) || (postData.systemName == "")) {
		postData.systemName = postData.firstName + " " + postData.lastName;
		setElementEnabled("epSystemName", true); 
	}
		
	postData.contactPointName = postData.systemName + " contact details";
	postData.contactPointDescription = ""; 
	
	postData.participantOfficeName = "Default Office";
	postData.participantOfficeDescription =""; 
	
	postData.registeredName = ""; 
	
	$("#epSystemName").val(postData.systemName);
	
	$("#epProjectIdSustenance").val(leadingZeroPad(postData.participantId,4) + " - "+ leadingZeroPad(postData.latestProjectNumber,4) + " - Sustenance");
	
	$("#eIndContactPointName").val(postData.contactPointName);
	$("#epContactPointDescription").val(postData.contactPointDescription); 

	$("#epParticipantOfficeName").val(postData.participantOfficeName);
	$("#epParticipantOfficeDescription").val(postData.participantOfficeDescription);

//	$("#eIndTradingName").val(postData.tradingName);
	$("#eIndRegisteredName").val(postData.registeredName);


	if ((postData.systemName == null) || (postData.systemName == "")) {
		errors += "A Unique System Name is required<br>";
	}

	if ((postData.tapAdministered == 'Y') && (postData.tapSubscriptionCode == "")) {
		errors += "Please select an IG Subscription<br>";
	}	
	//if (postData.countryCode == "") {
	//	postData.countryCode = null;
	//}

	if ((postData.passportNumber != null) && (postData.passportNumber != "")) {
		if (postData.countryId== null) {
			errors += "A Country of Issue is required<br>";
		}
	}

	if ((postData.startDate == null) || (postData.startDate == "")) {
		// Add Today's date without time
		$("#epStartDate").val(timestampToString(new Date(), false));
	}
	postData.startDate = getMsFromDatePicker("epStartDate");

	if ((postData.roleCode == null) || (postData.roleCode == "")) {
		postData.roleCode = "ROLE_USER";
	}

	if ((postData.allowLoginFlag == 'Y')){
		if ((postData.userName == null) || (postData.userName == "")) {
			errors += "A User Name is required<br>";
		}
		console.log("usernameUniqueGpt= " + usernameUniqueGpt)
		if ((usernameUniqueGpt != null) && (!usernameUniqueGpt)) {
			errors += " The User Name must be unique, please choose another User Name<br>";
		}
	} 
	
	if ((postData.allowLoginFlag == 'Y') && (somethingChangedPassword) ){   //|| $("#epOriginalAllowLoginFlag").val() == "N")
		if ((postData.pass == null) || (postData.pass == "") || (postData.pass == "*****")) {
			errors += "A Valid Password is required<br>";
		}	
	} 
	
	if (showFormErrors("epDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.individualId != null) && (postData.individualId != "")) {
		var postUrl = "/rest/ignite/v1/individual/existing-participant";
	}
	
	var IdNo = 	postData.idNumber
	
	saveEntry(postUrl, postData, null, "The Individual has been saved.", null, function(request, response){
		var data = response;
		var individualId = data.individualId;
		var projectParticipantId = data.projectParticipantId;
		var participantId = data.participantId;
		var contactPointId = data.contactPointId;
		var participantOfficeId = data.participantOfficeId;
		var systemMemberId = data.systemMemberId;
		
		$("#eIndIndividualId").val(individualId);
		$("#epProjectParticipantId").val(projectParticipantId);
		$("#epParticipantId").val(participantId);
		$("#eIndSystemMemberId").val(systemMemberId);
		$("#epContactPointId").val(contactPointId);
		$("#epParticipantOfficeIdDefault").val(participantOfficeId);
		
		$("#epParticipationDialogHeader").html("Individual Participant Detail: " + postData.systemName);
		$("#epdParticipantName").val(postData.systemName);
		

		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful

		// Set the Save Button to disabled
		setElementEnabled("saveGeneralTabButton", false);
		setElementEnabled("saveSystemDetailsTabButton", false);
		somethingChangedGeneralTab = false;
		somethingChangedPassword = false;
		
		// askToSaveTab = false;
	});

	IdNo = [IdNo.slice(0, 6), " ", IdNo.slice(6)].join('');
	IdNo = [IdNo.slice(0, 11), " ", IdNo.slice(11)].join('');	
	$("#eIndIdNumber").val(IdNo);
	setElementEnabled("saveGeneralTabButton", false);
	setElementEnabled("saveSystemDetailsTabButton", false);
	somethingChangedGeneralTab = false;
	
	
	
	if ((postData.allowLoginFlag == 'Y') && ($("#epOriginalAllowLoginFlag").val() == "N")){
		savePass();
	} else if ((postData.allowLoginFlag == 'N') && ($("#epOriginalAllowLoginFlag").val() == "Y")){
		saveRemovePass();
	} else if ((postData.allowLoginFlag == 'Y') && ($("#epOriginalAllowLoginFlag").val() == "Y") && (somethingChangedPassword)) {
		savePass();
	}
	somethingChangedPassword = false;
	
} //saveIndividual() -- END 



function savePass() {	
	var postUrl = "/rest/ignite/v1/individual/save-login" ;
	var postData = {
			individualId : $("#eIndIndividualId").val(),
			pass : $("#eIndPassword").val(),
	};
	saveEntry(postUrl, postData, null, "The Login details have been saved.", null, function(request, response){
		var data = response;
		$("#epOriginalAllowLoginFlag").val("Y")
		somethingChangedPassword = false;
		document.getElementById("editPasswordButton").style.display = "block";
		setElementEnabled("editPasswordButton", true);
		document.getElementById("eIndPassword").disabled = true;
		$("#eIndPassword").val("*****");
	});
} //savePass() -- END 


function saveRemovePass() {	
	var postUrl = "/rest/ignite/v1/individual/save-remove-login" ;
	var postData = {
			individualId : $("#eIndIndividualId").val(),
	};
	saveEntry(postUrl, postData, null, "The Login details have been removed.", null, function(request, response){
		var data = response;
		$("#epOriginalAllowLoginFlag").val("N")
		setElementEnabled("editPasswordButton", false);
		somethingChangedPassword = false;
	});
} //saveRemovePass() -- END 


function editPassword() {

	document.getElementById("editPasswordButton").style.display = "none";
	document.getElementById("eIndPassword").disabled = false;
	$("#eIndPassword").val("");
	document.getElementById("eIndPassword").focus();
}

//saveParticipantNonIndividual -- START 
// /new-non-individual 		--3.--saveNewNonIndivParticipant.sql  (in ParticipantController) <--insert
// /existing-non-individual	--4.--saveNonIndivParticipant.sql 	  (in ParticipantController) <--update
function saveParticipantNonIndividual() {

	var postUrl = "/rest/ignite/v1/participant/new-non-individual";
	
	var postData = {

			//****************************************************
			//ProjectParticipant table
			//****************************************************

			projectParticipantId : $("#epProjectParticipantId").val(),
			participantId : $("#epParticipantId").val(),

			//****************************************************
			//Participant table
			//****************************************************
			projectIdSustenance: $("#epProjectIdSustenance").val(),
			participantIdBuParent: null, // Implement BU's later
			participantTypeCode : $("#epParticipantTypeCode").val(),
			tapSubscriptionCode : $("#epTapSubscriptionCode").val(),
			representativeIndividualId : $("#epRepresentativeIndividualId").val(),
			marketingIndividualId : $("#epMarketingIndividualId").val(),
			participantOfficeIdDefault : $("#epParticipantOfficeIdDefault").val(),
			systemName: $("#epSystemName").val(),
			registeredName : $("#epRegisteredName").val(),
			tradingName : $("#epTradingName").val(),
			registrationNumber : $("#epRegistrationNumber").val(),
			vatNumber : $("#epVATNumber").val(),
			projectPrefix : $("#epProjectPrefix").val(),
			latestProjectNumber : $("#epLatestProjectNumber").val() == "" ? 1: $("#epLatestProjectNumber").val() ,  // Set default value
			projectPostfix : $("#epProjectPostfix").val(), 

			invoicePrefix: $("#epInvoicePrefix").val().trim(),
			latestInvoiceNumber: $("#epLatestInvoiceNumber").val(),
			invoiceNumberFormat: $("#epInvoiceNumberFormat").val(),
			
			// If LastDayOfMonth is checked = 31, if non numeric set to day 1, else use day entered
			defaultInvoiceDay : $("#eLastDayOfTheMonth").is(":checked") ? 31 : 
												(isNumber($("#epDefaultInvoiceDay").val()) ? $("#epDefaultInvoiceDay").val():1),

			startDate : getMsFromDatePicker("epStartDate"),
			endDate : getMsFromDatePicker("epEndDate"),
			isIndividual : "N",
			isBu : "N", // Implement this later
			
			//****************************************************
			//ContactPoint table
			//****************************************************
			contactPointId : $("#epContactPointIdDefault").val() == "" ? null : $("#epContactPointIdDefault").val(),  // Set to null if empty,
			
			cityId : $("#epCityId").val(),
			suburbId : $("#epSuburbId").val(),
			addressLine1 : $("#epAddressLine1").val(),
			addressLine2 : $("#epAddressLine2").val(),
			addressLine3 : $("#epAddressLine3").val(),
			contactPointName : $("#epName").val(),
			emailAddress : $("#gptEmailAddress").val(),
			phoneNumber : $("#epPhoneNumber").val(),

			
			//****************************************************
			//ParticipantOffice table
			//****************************************************

			tapAdministered : $("#epTapAdministered").is(":checked") ? "Y" : "N",
					
					
			participantOfficeId : $("#epParticipantOfficeIdDefault").val() == "" ? null : $("#epParticipantOfficeIdDefault").val(),  // Set to null if empty,
			participantOfficeName : $("#epParticipantOfficeName").val(),
			participantOfficeDescription : $("#epParticipantOfficeDescription").val(), 
			contactPointDescription : $("#epContactPointDescription").val(), 

			//****************************************************
			//Project table
			//****************************************************
	
			projectNumberBigInt : $("#epLatestProjectNumber").val(), 
			
	};
	var errors = "";

	if ((postData.registeredName == null) || (postData.registeredName == "")) {
		errors += "A Registered Name is required<br>";
	} else {
		if ((postData.tradingName == null) || (postData.tradingName == "")) {
			postData.tradingName = postData.registeredName;
		}
		if ((postData.systemName == null) || (postData.systemName == "")) {
			postData.systemName = postData.registeredName;
		}
	}

	if ((postData.tradingName == null) || (postData.tradingName == "")) {
		errors += "A Trading Name is required<br>";
	}

	if ((postData.tapAdministered == 'Y') && (postData.tapSubscriptionCode == "")) {
		errors += "Please select an IG Subscription<br>";
	}
	
	
	if ((postData.latestInvoiceNumber == null) || (postData.latestInvoiceNumber == "")) {
		$("#epLatestInvoiceNumber").val("0");
		postData.latestInvoiceNumber = 0;
	}
	
	if ((postData.systemName == null) || (postData.systemName == "")) {
		postData.systemName = postData.tradingName;
		setElementEnabled("epSystemName", true); 
	}

	if ((postData.systemName == null) || (postData.systemName == "")) {
		errors += "A System Name is required<br>";
	}

	if ((postData.participantTypeCode == null) || (postData.participantTypeCode == "")) {
		errors += "A Participant Type is required<br>";
	}
	
//	if ((postData.tapSubscriptionCode == null) || (postData.tapSubscriptionCode == "")) {
//		errors += "A TAP Subscription Code is required<br>";
//	}

	if ((postData.startDate == null) || (postData.startDate == "")) {
		// Add Today's date without time
		$("#epStartDate").val(timestampToString(new Date(), false));
	}
	postData.startDate = getMsFromDatePicker("epStartDate");

	if (showFormErrors("epDlgErrorDiv", errors)) {
		return;
	}

	postData.contactPointName = postData.systemName + " contact details";
	postData.participantOfficeName = "Default Office";
	
	$("#epSystemName").val(postData.systemName);
	
	$("#epProjectIdSustenance").val(leadingZeroPad(postData.participantId,4) + " - " 
								+ leadingZeroPad(postData.latestProjectNumber,4) + " - Sustenance");
	
	$("#eIndContactPointName").val(postData.contactPointName);
	$("#epContactPointDescription").val(postData.contactPointDescription); 

	$("#epParticipantOfficeName").val(postData.participantOfficeName);
	$("#epParticipantOfficeDescription").val(postData.participantOfficeDescription);

//	$("#eIndTradingName").val(postData.tradingName);
	$("#eIndRegisteredName").val(postData.registeredName);

	if (showFormErrors("epDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.participantId != null) && (postData.participantId != "")) {
		var postUrl = "/rest/ignite/v1/participant/existing-non-individual";
	}
//
	saveEntry(postUrl, postData, null, "The Participant has been saved.", null, function(request, response){
		var data = response;
		var individualId = data.individualId;
		var projectParticipantId = data.projectParticipantId;
		var participantId = data.participantId;
		var contactPointId = data.contactPointId;
		var participantOfficeId = data.participantOfficeId;
		var systemMemberId = data.systemMemberId;
		
		$("#eIndIndividualId").val(individualId);
		$("#epProjectParticipantId").val(projectParticipantId);
		$("#epParticipantId").val(participantId);
		$("#eIndSystemMemberId").val(systemMemberId);
		$("#epContactPointId").val(contactPointId);
		$("#epParticipantOfficeIdDefault").val(participantOfficeId);
		
		$("#epParticipationDialogHeader").html("Participant Detail: " + postData.systemName);
		$("#epdParticipantName").val(postData.systemName);

		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful

		// Set the Save Button to disabled
		setElementEnabled("saveGeneralTabButton", false);
		setElementEnabled("saveSystemDetailsTabButton", false);
		somethingChangedGeneralTab = false;
		// askToSaveTab = false;
	});
}  //saveParticipantNonIndividual -- END --


function participantTypeCodeSelected() { 
	var participantTypeCode = $("#epParticipantTypeCode").val();

	if (participantTypeCode == "") {
		setDivVisibility("epIndividualInfoPanel", "none");
		setDivVisibility("epIndividualInfoSystemPanel", "none");
		setDivVisibility("epParticipantInfoPanel","none");
		setDivVisibility("epOverlapInfoPanel", "none");
		setDivVisibility("epOverlapInfoSystemPanel", "none");
		setDivVisibility("epParticipantTypeCode", "block");
		setDivVisibility("epParticipantType", "none");
		setElementEnabled("epParticipantTypeCode", true); //Enable Participant Type when existing... Can only select at creation
		setDivVisibility("epAllowLoginFlagPanel", "none");
		setDivVisibility("epAllowLoginFlagPanel", "none");
		setDivVisibility("humanResourceOfPanel", "none");
	} else {
		if (participantTypeCode == "INDIVIDUAL") {
			setDivVisibility("epIndividualInfoPanel", "block");
			setDivVisibility("epIndividualInfoSystemPanel", "block");
			setDivVisibility("epParticipantInfoPanel","none");
			setDivVisibility("epAllowLoginFlagPanel", "block");
			setDivVisibility("humanResourceOfPanel", "block");
	
			$("#epIsIndividual").val("Y");
			
		} else {
			setDivVisibility("epIndividualInfoPanel", "none");
			setDivVisibility("epIndividualInfoSystemPanel", "none");
			setDivVisibility("epParticipantInfoPanel","block");
			setDivVisibility("epAllowLoginFlagPanel", "none");
			$("#epIsIndividual").val("N");
		}
		setDivVisibility("epOverlapInfoPanel", "block");
		setDivVisibility("epOverlapInfoSystemPanel", "block");
		generalTabChanged();
	}
	initializeAllowLogin();
}		    	

function editSelectMarketingIndividual() {
	selectMarketingIndividual("epMarketingIndividualId", "epMarketingIndividualName");
	generalTabChanged();
}

function editSelectRepresentativeIndividual() {
	selectRepresentativeIndividual("epRepresentativeIndividualId", "epRepresentativeIndividualName");
	generalTabChanged();
}

function selectMarketingIndividual(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="marketingIndividualId";
			var refColumnName="individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.individualId;
				var markName = row.firstName + " " + row.lastName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(markName);
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

function selectRepresentativeIndividual(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="representativeIndividualId";
			var refColumnName="individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.individualId;
				var repName = row.firstName + " " + row.lastName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
} // editParticipant  --  END



//*********** Participant General info *********** //
//****************************************************************** //


function generalTabChanged() {
	currentTab = "General";
	// askToSaveTab = true;
	somethingChangedGeneralTab = true;
	setElementEnabled("saveGeneralTabButton", true);
	setElementEnabled("saveSystemDetailsTabButton", true);
}  //generalTabChanged  --  End



function copyToNickName() {
	$("#eIndNickName").val($("#eIndFirstName").val())
};


function copyToTradingAs() {
	$("#indTradingName").val( $("#eIndFirstName").val() + " " + $("#eIndLastName").val())
};




//function onlyNumberAndSpaceKey(evt) {  // 000000 0000 000
//    
//    // Only ASCII character in that range allowed	   add to the input:   onkeypress="return onlyNumberAndSpaceKey(event)"	net integers en spasie
//    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
//
//    if ((ASCIICode > 31) && (ASCIICode != 32) && (ASCIICode < 48 || ASCIICode > 57))
//        return false;
//    return true;
//};
//
//function onlyTelephoneNumberKey(evt) {  // (+00) 00 000 0000
//    
//    // Only ASCII character in that range allowed	   add to the input:   onkeypress="return onlyTelephoneNumberKey(event)"	net integers en ( en ) en + en spasie
//    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
//
//    if ((ASCIICode > 31) && (ASCIICode != 40) && (ASCIICode != 41) && (ASCIICode != 43) && (ASCIICode != 32) && (ASCIICode < 48 || ASCIICode > 57))
//        return false;
//    return true;
//};

function getInitials() {
	
	var firstName = $("#eIndFirstName").val();
	var secondName = $("#eIndSecondName").val();
	var thirdName = $("#eIndThirdName").val();
	
	firstName = firstName.charAt(0).toUpperCase() + firstName.slice(1);
	secondName = secondName.charAt(0).toUpperCase() + secondName.slice(1);
	thirdName = thirdName.charAt(0).toUpperCase() + thirdName.slice(1);
		
	let letter = firstName.charAt(0) + secondName.charAt(0) + thirdName.charAt(0);
	
	$("#eIndInitials").val(letter)
}

//function editSelectCityForContactPoint() {
//	var queryUrl="/rest/ignite/v1/city/find-all-in-view";
//	
//	$.ajax({
//		url: springUrl(queryUrl),
//		type: "GET",
//		success: function(data) {
//		
//			var columnName="cityId";
//			var refColumnName="cityId";
//			var columns = [                                                         // vir grid SelectFromGridDialog
//        		{ data:  "cityId", name: "CityId" }                                 //0 MySql-TableName: City
//        		,{ data: "countryNameAndCode", name: "Country" }                           //1
//        		,{ data: "provinceCodeAndName", name: "Province" }                         //2
//        		,{ data: "name", name: "City/Town" }                                //3
//        	];
//			var columnDefs = [
//				{ 
//					visible: false,
//					targets: 0
//					}
//			];
//
//			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
//				var id = row.cityId;
//				var cityName = row.name;
//
//				$("#epCityId").val(id);
//				$("#epCityName").val(cityName);
//			});
//		},
//		error: function(jqXHR, textStatus, errorThrown) {
//			ajaxError(jqXHR, textStatus, errorThrown);
//		},
//		complete: function(html) {
//			ajaxSuccess(html.status);
//		}  
//	});
//}

function editSelectCountryForContactPoint() {
	
	var queryUrl="/rest/ignite/v1/country/list-country-order-by-name";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="countryId";
			var refColumnName="countryId";

			var columns = [
			       		{ data: "countryId", name: "countryId" }         			//0 
			       		,{ data: "countryCode",    name: "Code" }                    //1
			       		,{ data: "name",  name: "Name" }                    //2
			       	];

			       	var columnDefs = [
			       		{
			       			visible: false,
			       			targets: [0]
			       		}
			       	];			
			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.countryId;
				var markName = row.name;

				$("#epCountryId").val(id);
				$("#epCountryName").val(markName);
				$("#epProvinceId").val("");
				$("#epProvinceName").val("");		
				$("#epCityId").val("");
				$("#epCityName").val("");
				$("#epSuburbId").val("");
				$("#epSuburbName").val("");
				cpDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}  //  editSelectCountryForContactPoint  --  END



function editSelectProvinceForContactPoint() {
	
	if ($("#epCountryId").val() == "") {
		alert("Please select a Country.");
		return;
	}
	var queryUrl="/rest/ignite/v1/province/list-view-province-for-country/" + $("#epCountryId").val();
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="provinceId";
			var refColumnName="provinceId";

			var columns = [
			       		{ data: "provinceId", name: "provinceId" }         	//0 
			       		,{ data: "provinceCode",    name: "Code" }          //1
			       		,{ data: "name",  name: "Name" }                    //2
			       	];

			       	var columnDefs = [
			       		{
			       			visible: false,
			       			targets: [0]
			       		}
			       	];			
			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.provinceId;
				var markName = row.name;

				$("#epProvinceId").val(id);
				$("#epProvinceName").val(markName);	
				$("#epCityId").val("");
				$("#epCityName").val("");
				$("#epSuburbId").val("");
				$("#epSuburbName").val("");
				cpDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
} // editSelectProvinceForContactPoint -- END



function editSelectCityForContactPoint() {
	
	if ($("#epProvinceId").val() == "") {
		alert("Please select a Province.");
		return;
	}
	var queryUrl="/rest/ignite/v1/city/list-view-city-for-province-min/" + $("#epProvinceId").val();
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="cityId";
			var refColumnName="cityId";

			var columns = [
			       		{ data: "cityId", name: "cityId" }         	//0 
			       		,{ data: "name",  name: "Name" }            //1
			       	];

			       	var columnDefs = [
			       		{
			       			visible: false,
			       			targets: [0]
			       		}
			       	];			
			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.cityId;
				var markName = row.name;

				$("#epCityId").val(id);
				$("#epCityName").val(markName);
				$("#epSuburbId").val("");
				$("#epSuburbName").val("");
				cpDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}  //editSelectCityForContactPoint  --  END



function editSelectSuburbForContactPoint() {
	
	if ($("#epCityId").val() == "") {
		alert("Please select a City.");
		return;
	}	
	var queryUrl="/rest/ignite/v1/suburb/list-view-suburb-for-city-min/" + $("#epCityId").val();
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="suburbId";
			var refColumnName="suburbId";

			var columns = [
			       		{ data: "suburbId", name: "SuburbId" }         			//0 
			       		,{ data: "name",    name: "Suburb" }                    //1
			       	];

			       	var columnDefs = [
			       		{
			       			visible: false,
			       			targets: [0]
			       		}
			       	];			
			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.suburbId;
				var markName = row.name;

				$("#epSuburbId").val(id);
				$("#epSuburbName").val(markName);
				cpDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}  //  editSelectSuburbForContactPoint  --  END



//When returning to the General Tab, update the default Bank, Office and Contact Point info
function getParticipantOfficeAndBankDefaults() {
	var participantId;
	
	participantId = $("#epParticipantId").val();
	
	queryUrl = "/rest/ignite/v1/participant/view-info/" + participantId ;
	console.log(queryUrl);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			console.dir(data);
			var msg = "";
			// Default participant Office and Contact point info
			$("#epParticipantOfficeName").val(data.participantOfficeName);
			$("#epParticipantOfficeDescription").val(data.participantOfficeDescription); 
			$("#epContactPointName").val(data.contactPointName);

			$("#gptEmailAddress").val(data.emailAddress);
			$("#epAddressLine1").val(data.addressLine1);
			$("#epAddressLine2").val(data.addressLine2);
			$("#epAddressLine3").val(data.addressLine3);
			$("#epPhoneNumber").val(data.phoneNumber);
			
			$("#epSuburbId").val(data.contactPointSuburbId);
			$("#epSuburbName").val(data.contactPointSuburbName);
			$("#epCityId").val(data.contactPointCityId);
			$("#epCityName").val(data.contactPointCityName);
			
			$("#epProvinceId").val(data.provinceId);
			$("#epProvinceName").val(data.provinceId_Name);
			
			$("#epCountryId").val(data.countryId);
			$("#epCountryName").val(data.countryId_Name);	
			

			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}



function hideParticipantLogo() {
	var participantTypeCode = $("#epParticipantType").val();
	
	if (participantTypeCode == "Natural Person") {
		//setDivVisibility("participantLogoDiv", "none")
		$("#participantLogoLabel").html("Profile Picture (.jpg only)");
	}
	else {
		//setDivVisibility("participantLogoDiv", "block");
		$("#participantLogoLabel").html("Logo (.jpg only)");
	}
}

function previewParticipantLogo() {
	var url = springUrl("/display-blob?id=" + $("#epParticipantId").val());
	
	hideParticipantLogo();
	
	$.ajax({
		url: springUrl(url),
		type: "GET",
		success: function(data) {
			if ((data == null) || (data == "")) {
				// load no image graphic			
				setDivVisibility("participantLogoImage", "none");
				//$("#participantLogo").attr("src", springUrl("/img/LogoPlaceholder.jpg"));
				setElementEnabled("deleteParticicantLogoButton", false);
			} else {
				// we receive a base64 string, convert it to an image
				// Source: https://stackoverflow.com/questions/21227078/convert-base64-to-image-in-javascript-jquery
				setDivVisibility("participantLogoImage", "block");
				$("#participantLogo").attr("src", "data:image/png;base64," + data);
				$("#participantLogo").attr("style", "border-color: #dae2e7c4; border-style: solid"); 
				setElementEnabled("deleteParticicantLogoButton", true);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function editParticipantLogo() {
	$("#fileDlgPrimaryKeyValue").val($("#epParticipantId").val());
	$("#fileDlgUploadType").val("participant-logo");
	showModalDialog("logoUploadDialog");
}

function initializeUploadHandler() {
 	$("#participantLogoForm").on('submit', function(e) {
		showPleaseWait();
		
		// e.preventDefault();
	    $.ajax(/*{
	        type: $(this).prop('method'),
	        url : $(this).prop('action'),
	        data: $(this).serialize()
	    }*/)
	    .done(function(data) {
			window.setTimeout(function() {
				var iframe = document.getElementById('fileUploadDialogIframe');
				var iframeUrl = iframe.contentWindow.location.href;
				
				if (iframeUrl.indexOf("upload-success") > -1) {
					// success
					
					hidePleaseWait();
					$("#logoUploadDialog").modal("hide");
					
					showSuccessToast("The Logo has been uploaded successfully.");
					
					resetUploadDialog();
					previewParticipantLogo();
				} else {
					// failure... get the message
					hidePleaseWait();

					var error = "An unknown error occurred";
					if (iframeUrl.indexOf("?e=") > -1) {
						error = decodeURI(iframeUrl.substring(iframeUrl.indexOf("?e=") + 3));
					}

					showDialog("Error", error);
				}
		    }, 2000);  // give it two seconds to upload and show the page
	    })
	    .fail(function(jqXHR, textStatus, errorThrown) {
			failureMethod(textStatus);
		});
	});
}

function promptDeleteParticipantLogo() {
	showDialog("Confirm?",
				"Are you sure you wish to delete this logo?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					deleteParticipantLogo();
				}
	)
}

function deleteParticipantLogo() {
	var postUrl = "/rest/ignite/v1/participant/logo/clear";
	var row = {
		participantId: $("#epParticipantId").val() 
	};

	saveEntry(postUrl, row, null, "The logo has been cleared.", null,
		function() {
			previewParticipantLogo();
		}
	);
}

function logoUploadDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("fileUploadFileImportButton", true);
}

function resetUploadDialog() {
	askToSaveDialog = false;
	somethingChangedInDialog = false;
	setElementEnabled("fileUploadFileImportButton", false);
	$("#sDlgUploadFileInput").val("");
}

function closeLogoUploadDialog() {
	if ((askToSaveDialog == true) || (somethingChangedInDialog == true)) {
		showDialog("Confirm?",
				"The file has not been uploaded yet - are you sure you wish to cancel?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					setDivVisibility("errorMsgDiv", "none");
					closeModalDialog("logoUploadDialog");
					resetUploadDialog()
				}
		)
	} else {
		setDivVisibility("errorMsgDiv", "none");
		closeModalDialog("logoUploadDialog");
		resetUploadDialog()
	}
}


// *************************************************************************************************

$(document).on('change', ':file', function() {
    var fileSelector = $(this);
    var filenameInput = $("#sDlgUploadFileInput");

    var label = fileSelector.val().replace(/\\/g, '/').replace(/.*\//, '');
    filenameInput.val(label);
    
    if ((filenameInput.val() != null) && (filenameInput.val() != "")) {
		document.getElementById("sDlgUploadFileInput").onchange();
	}
});

initializeUploadHandler();

