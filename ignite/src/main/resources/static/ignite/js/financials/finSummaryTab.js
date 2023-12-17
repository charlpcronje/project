//*********************************************************
//CostPerAgreement Tab -- Start
//*********************************************************
function initializeFinSummaryTab(data) {
	var insert = true; //+
	var enabled = false; 
	
	if (data.isIndividual == "Y") {
		$("#financialsParticipantHeader").html("Financial Detail: " + data.systemName);
		setDivVisibility("epIndividualInfoPanel", "block");
		setDivVisibility("epParticipantInfoPanel","none");
	} else {
		$("#financialsParticipantHeader").html("Financial Detail: " + data.systemName);
		setDivVisibility("epIndividualInfoPanel", "none");
		setDivVisibility("epParticipantInfoPanel","block");
	}
	setDivVisibility("epOverlapInfoPanel", "block");
	$("#finParticipantType").val(data.participantTypeName);
	
	setActiveTab("finSummaryTabLink");
	
	///////// Overlapping details  ///////////////
	
	$("#epSystemName").val(data.systemName);

	// Default participant Office and Contact point info
//	$("#epParticipantOfficeName").val(data.participantOfficeName);
//	$("#epParticipantOfficeDescription").val(data.participantOfficeDescription); 
	$("#epContactPointName").val(data.contactPointName);
	$("#epEmailAddress").val(data.emailAddress);
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
	$("#epTradingName").val(data.tradingName);
	$("#epRegistrationNumber").val(data.registrationNumber);
	$("#epVATNumber").val(data.vatNumber);

	///////// Participant details  ///////////////

	///////// Individual details  ///////////////
	$("#eIndIndividualId").val(data.individualId);
	$("#eIndSystemMemberId").val(data.systemMemberId);
	$("#eIndFirstName").val(data.firstName);
	$("#eIndLastName").val(data.lastName);
	$("#eIndInitials").val(data.initials);
	var IdNo = data.idNumber

	if (IdNo != null) {
		IdNo = [IdNo.slice(0, 6), " ", IdNo.slice(6)].join('');
		IdNo = [IdNo.slice(0, 11), " ", IdNo.slice(11)].join('');
	} else {
		IdNo = "";
	}
	$("#eIndIdNumber").val(IdNo)	
			
	$("#eIndPassportNumber").val(data.passportNumber);
	$("#eIndIncomeTaxNumber").val(data.incomeTaxNumber);

	///////// Individual details  ///////////////
	
}	
