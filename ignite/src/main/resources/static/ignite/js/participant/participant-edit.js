function populateFields(participantData) {
	var insert = true; //+
	var enabled = false; 
	var header = "Participant Detail";
	var data = {};
	
	//++++ when using a new page, rather than a dialog
 	if (participantId > 0) {  // We get this participantId from the HTML file on line 106
		 insert = false;
		 data = participantData;
		 
	 } 
	//++++
 	
 	
	setElementEnabled("epParticipantBankDetailsDefault", false); //Disable Participant Type when existing... Can not change type
	setElementEnabled("epProjectIdSustenance", false); //Disable Default Project for financial administration... Can not change type

	
	if (!insert) { //+---
		
		$("#participantHeader").html("<i class='fa fa-info-circle'></i> Participant Detail: " + data.systemName);

		setElementEnabled("epSystemName", true); 

		enabled = true;
		if (data.isIndividual == "Y") {
			$("#epParticipationDialogHeader").html("Individual Participant Detail: " + data.systemName);
			setDivVisibility("epIndividualInfoPanel", "block");
			setDivVisibility("epParticipantInfoPanel","none");
			setDivVisibility("epAllowLoginFlagPanel", "block");
			
		} else {
			$("#epParticipationDialogHeader").html("Participant Detail: " + data.systemName);
			setDivVisibility("epIndividualInfoPanel", "none");
			setDivVisibility("epParticipantInfoPanel","block");
			setDivVisibility("epAllowLoginFlagPanel", "none");
		}
		setDivVisibility("epOverlapInfoPanel", "block");
		setDivVisibility("epParticipantTypeCode", "none");
		setDivVisibility("epParticipantType", "block");
		$("#epParticipantType").val(data.participantTypeName);
		
		setElementEnabled("epParticipantType", false); //Disable Participant Type when existing... Can not change type
		
	} else { // New record 
		$("#epParticipationDialogHeader").html("Participant Detail: " );
//		setDivVisibility("epIndividualInfoPanel", allowLogin ? "block" : "none");
		setDivVisibility("epIndividualInfoPanel", "none");
		setDivVisibility("epParticipantInfoPanel","none");
		setDivVisibility("epOverlapInfoPanel", "none");
		setDivVisibility("epParticipantTypeCode", "block");
		setDivVisibility("epParticipantType", "none");
		setElementEnabled("epParticipantTypeCode", true); //Enable Participant Type when existing... Can only select at creation
		data.tapSubscriptionCode = null ;
		data.isActiveMember = "Y";
		data.latestProjectNumber = 1;

		data.participantOfficeName = "Default Office";
		data.participantOfficeDescription =""; 
		data.contactPointName = "Default Contact Point"; 
		data.contactPointDescription = ""; 
		data.tradingName = ""; 
		data.registeredName = ""; 

		// Default Sustenance Project data
		data.projectNumberText = "0001";
		data.projectNumberBigInt = 1;	
		
		setElementEnabled("epSystemName", false); 
	}

	setActiveTab("generalParticipantTabLink");
	setElementEnabled("saveGeneralTabButton", false); //Disable Save button when initializing
	///////// Overlapping details  ///////////////
	
	$("#epIsIndividual").val(data.isIndividual);
	$("#epParticipantId").val(data.participantId);

	$("#epStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
	$("#epEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	
	
	$("#epSystemName").val(data.systemName);
	$("#epProjectPrefix").val(data.projectPrefix);
	$("#epLatestProjectNumber").val(data.latestProjectNumber);
	$("#epProjectPostfix").val(data.projectPostfix);
	$("#epProjectIdSustenance").val(data.projectIdSustenance);
	$("#epProjectIdSustenance").val(data.projectIdSustenance);
	
	if (data.defaultInvoiceDay == 31) {
		
		$("#epDefaultInvoiceDay").val("31");
		$("#epLastDayOfTheMonth").prop("checked", data.defaultInvoiceDay == 31);
		
	} else {
		$("#epDefaultInvoiceDay").val(data.defaultInvoiceDay);
		$("#epLastDayOfTheMonth").prop("checked", false);
	}
	setElementEnabled("epDefaultInvoiceDay", $("#epDefaultInvoiceDay").val() > 30 ? false: true); 

	
//	$("#epLastDayFlag").prop("checked", data.lastDayOfTheMonth == "Y");
//	if (data.lastDayOfTheMonth == "Y") {
//		$("#epDefaultInvoiceDay").val("");
//	} else {
//		$("#epDefaultInvoiceDay").val(data.defaultInvoiceDay);
//	}
	

	populateSelect("epParticipantTypeCode", 
//		       "/rest/ignite/v1/participant-type/non-indiv", 
		       "/rest/ignite/v1/participant-type", 
		       "participantTypeCode", 
		       "typeName", 
		       data.participantTypeCode, 
		       true,
		       null 
	);

	
	populateSelect("epTapSubscriptionCode", 
		       "/rest/ignite/v1/tap-subscription/find-all", 
		       "tapSubscriptionCode", 
		       "name", 
		       data.tapSubscriptionCode, 
		       true,
		       null 
	);


	if (data.tapAdministered == "Y") {
		$("#epTapAdministered").prop("checked", true);
		setDivVisibility("epTapSubscriptionDiv", true ? "block" : "none");
	} else {
		$("#epTapAdministered").prop("checked", false);
		setDivVisibility("epTapSubscriptionDiv", false ? "block" : "none");
	}
	
	// Default participant Office and Contact point info
	$("#epParticipantOfficeName").val(data.participantOfficeName);
	$("#epParticipantOfficeDescription").val(data.participantOfficeDescription); 
	$("#epContactPointName").val(data.contactPointName);
	
	$("#epCityId").val(data.contactPointCityId);
	$("#epCityName").val(data.contactPointCityName);
	$("#epSuburbId").val(data.contactPointSuburbId);
	$("#epSuburbName").val(data.contactPointSuburbName);
		
	$("#epProvinceId").val(data.contactPointProvinceId); 
	$("#epCountryId").val(data.contactPointCountryId);
	$("#epProvinceName").val(data.contactPointProvinceName); 
	$("#epCountryName").val(data.contactPointCountryName);
	
	$("#gptEmailAddress").val(data.emailAddress);
	$("#epAddressLine1").val(data.addressLine1);
	$("#epAddressLine2").val(data.addressLine2);
	$("#epAddressLine3").val(data.addressLine3);
	$("#epPhoneNumber").val(data.phoneNumber);
	
	// Default bank details
	$("#epParticipantBankDetailsDefault").val(data.bankDetails);
	// Default financial admin project
	$("#epProjectIdSustenance").val(data.projectIdSustenance);
	
	///////// Overlapping details  ///////////////
	
	///////// Participant details  ///////////////
	$("#epRegisteredName").val(data.registeredName);
	$("#epTradingName").val(data.tradingName);
	$("#epRegistrationNumber").val(data.registrationNumber);
	$("#epVATNumber").val(data.vatNumber);

	$("#epRepresentativeIndividualName").val(data.representative);
	$("#epRepresentativeIndividualId").val(data.representativeIndividualId);
	
	$("#epMarketingIndividualName").val(data.marketer);
	$("#epMarketingIndividualId").val(data.marketingIndividualId);
	
	$("#epInvoicePrefix").val(data.invoicePrefix);
	$("#epLatestInvoiceNumber").val(data.latestInvoiceNumber);
	$('#epInvoiceNumberFormat').val(data.invoiceNumberFormat);
	///////// Participant details  ///////////////

	///////// Individual details  ///////////////
	$("#eIndIndividualId").val(data.individualId);
	$("#eIndSystemMemberId").val(data.systemMemberId);
	$("#eIndFirstName").val(data.firstName);
	$("#eIndSecondName").val(data.secondName);
	$("#eIndThirdName").val(data.thirdName);
	$("#eIndLastName").val(data.lastName);
	$("#eIndNickName").val(data.nickName);
	$("#eIndInitials").val(data.initials);
	
	$("#indTradingName").val(data.tradingName);
	$("#epTradingName").val(data.tradingName);
	
	
	var IdNo = data.idNumber

	if (IdNo != null) {
		IdNo = [IdNo.slice(0, 6), " ", IdNo.slice(6)].join('');
		IdNo = [IdNo.slice(0, 11), " ", IdNo.slice(11)].join('');
	} else {
		IdNo = "";
	}
	$("#eIndIdNumber").val(IdNo)	
			
//	$("#eIndIdNumber").val(data.idNumber);	
	$("#eIndPassportNumber").val(data.passportNumber);
	$("#eIndIncomeTaxNumber").val(data.incomeTaxNumber);
	$("#eIndIsActiveMember").prop("checked", data.isActiveMember == "Y");

	populateSelect("eIndCountryId", 
		       "/rest/ignite/v1/country/list-country-order-by-name", 
		       "countryId", 
		       "countryNameAndCode", 
		       data.countryId, 
		       true,
		       null 
	);
	
	$("#eIndAllowLoginFlag").prop("checked", data.allowLoginFlag == "Y");
	
	setDivVisibility("eIndLoginInfoPanel", (data.allowLoginFlag == "Y") ? "block" : "none");
	if (data.allowLoginFlag == "Y") {
		document.getElementById("editPasswordButton").style.display = "block";
		document.getElementById("eIndPassword").disabled = true;
		setElementEnabled("editPasswordButton", true);
		$("#epOriginalAllowLoginFlag").val("Y");
		$("#sdUserName2").val(data.userName);
//		$("#eIndPassword").val(data.pass);		
	} else {
		$("#epOriginalAllowLoginFlag").val("N");
		$("#sdUserName2").val($("#gptEmailAddress").val());
//		$("#eIndPassword").val("*****");
	}

	$("#eIndPassword").val("*****");	
	
//	eIndUpdateAllowLogin();
	
	$("#eIndDlgLastLoginTimestamp").val(timestampToString(data.lastLoginTimestamp, true));
	
	populateSelect("eIndRoleCode",
			       "/rest/ignite/v1/role",
			       "roleCode",
			       "name",
			       data.roleCode,
			       true,
			       null
	);
	///////// Individual details  ///////////////
	
	
//	/* function populateSelect(
//	elementId, 
//	html select element that will be populated 
//	url, url where it must get the data (you can paste in browser window to see the data)
//	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
//			displayField,  name of the field in the result from url - java object field - variable, that 
//	must be displayed to the user
//	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
//	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
//	completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
//				)
//	 */	
	
	// TODO: what about resetting the password?
	// TODO: what about the users role?
	setAdditionalTabStates(enabled);

	previewParticipantLogo();
	
	// Set the Save Button to disabled
	setElementEnabled("saveGeneralTabButton", false);
	somethingChangedGeneralTab = false;
	// askToSaveTab = false;
}	//  populateFields  --  END




function initializeWithParticipantId() {
	var queryUrl = "/rest/ignite/v1/participant/" + participantId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			// if this is an individual, hide the medical aid tab
			setElementVisibility("medicalAidTabLink", data.isIndividual == "Y");
			populateFields(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}



function initializeNavTabs() {
	$('.nav-tabs a').on('show.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');
		var participantId = $("#epParticipantId").val();
		var individualId = $("#eIndIndividualId").val();
		var resourceId = $("#eResourceId").val();
		
	  	if ($("#aAssetStartDate").val() == "") {

    		$("#aAssetStartDate").val(getEarlierDate(365));    
	    	// $("#aAssetStartDate").datepicker("setDate", new Date());   //vandag se datum
	    	$("#aAssetEndDate").datepicker("setDate", new Date());        //vandag se datum
	    }	
	    if ($("#ppeStartDate").val() == "") {

    		$("#ppeStartDate").val(getEarlierDate(365));    

	    	// $("#ppeStartDate").datepicker("setDate", new Date(firstDay));
	    	$("#ppeEndDate").datepicker("setDate", new Date(lastDay));
	    }

		if (activeTab == "General") {
			currentTab = "General";
			console.log("getParticipantOfficeAndBankDefaults()");
			getParticipantOfficeAndBankDefaults();
		}
		if (activeTab == "System Details") {
			currentTab = "System Details";
			initializeSystemDetailsTab(participantId);
		}
		if (activeTab == "Links") {
			currentTab = "Links";
			if (individualId == '') {
				individualId = -1;
			}
			initializeLinkedToTab(participantId, individualId);
		}
		if (activeTab == "Work Locations") {
			currentTab = "Work Locations";
			initializeOfficeTab(participantId);
		}
		if (activeTab == "Bank Details") {
			currentTab = "Bank Details";
			initializeBankDetailsTab(participantId);
		}
		if (activeTab == "Medical Aid") {
			currentTab = "Medical Aid";
			// This tab is only available to individuals
			initializeMedicalInsuranceCompany(individualId);
		}
		if (activeTab == "Human Resources") {
			currentTab = "Human Resources";
			initializeHumanResourceTable(participantId);
		}
		if (activeTab == "Competency") {
			currentTab = "Competency";
			initializeIndividualCompetencyListTable(participantId, individualId);
			initializeIndividualQualificationListTable(participantId, individualId);
			initializeIndividualProfRegistrationListTable(participantId, individualId);
		}
		if (aAssetStartDate == '') {
			var dateFrom = new Date();
			$("#aAssetStartDate").datepicker("setDate", new Date(dateFrom));
		} else {
			var dateFrom = new Date($("#aAssetStartDate").val())
		}
		if (aAssetEndDate == '') {
			var dateTo = new Date();
			$("#aAssetEndDate").datepicker("setDate", new Date(dateTo));
		} else {
			var dateTo = new Date($("#aAssetEndDate").val())
		}

		if (activeTab == "Assets") {
			currentTab = "Assets";
			initializeParticipantAssetTable(participantId, dateFrom, dateTo);
		}
		if (activeTab == "Vehicles") {
			currentTab = "Vehicles";
			initializeVehicleListTable(participantId);
		}

		if (activeTab == "Project Expenses") {
			currentTab = "Project Expenses";

			var dateFromPE = new Date(getMsFromDatePicker("ppeStartDate"));
			var dateToPE = new Date(getMsFromDatePicker("ppeEndDate"));

			//initializeProjectExpenseTab();
			initializeParticipantProjectExpenseTable(dateFromPE, dateToPE, null);
		}

	});
}  //initializeNavTabs  --  END




function setAdditionalTabStates(enabled) {
	setDivVisibility("systemDetailsTabLink", enabled? "block":"none");
	setDivVisibility("linkedToTabLink", enabled? "block":"none");
	setDivVisibility("officeAndContactPointsTabLink", enabled? "block":"none");
	setDivVisibility("bankDetailsTabLink", enabled? "block":"none");
	setDivVisibility("humanResourceTabLink", enabled? "block":"none");

	if ($("#eIndIndividualId").val() == "")  {
//		setDivVisibility("medicalAidTabLink", "none");
		setDivVisibility("competencyTabLink", "none");
	} else {
//		setDivVisibility("medicalAidTabLink", enabled? "block":"none");
		setDivVisibility("competencyTabLink", enabled? "block":"none");
	}

	setDivVisibility("assetTabLink", enabled? "block":"none");
	setDivVisibility("vehicleTabLink", enabled? "block":"none");
	setDivVisibility("projectExpenseTabLink", enabled? "block":"none");

	//setActiveTab("generalParticipantTab");
}  // setAdditionalTabStates  --  END



// For Logo upload
function selectFile() {
	$("#sDlgUploadFile").click();
}




// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeWithParticipantId();
	initializeNavTabs();
	showIgDeveloperOption();
} );
