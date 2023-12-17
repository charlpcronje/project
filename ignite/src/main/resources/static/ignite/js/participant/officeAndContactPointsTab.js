
var participantOfficeTable = null;
var contactPointTable = null;

//******************************************************************* //
//*********** Office and Contact points *********** //
//initializeOfficeTab -- Start
function initializeOfficeTab(participantId) {
	// TODO:  Add isDefault flag to front end: The default office is stored as a foreign key on the participant
	
	var columnsArray = [
		{ data: "participantOfficeId" },
		{ data: "participantId" },
		{ data: "name" },
		{ data: "description" },
		{ data: "costPerSeat" },
		{ data: "dateFrom" },
		{ data: "contactPointIdDefault" },
		{ data: "" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 5, 6]
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = timestampToString(data, false);
				}
				
				return html;
			},
			//width: "100px",
			targets: 5
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				return html;
			},
			targets: [4]
		},		
		{
			className: "dt-right",
			targets:4
		},
		{
			render: function (data, type, row){
				var name = "";
				if (row.hasOwnProperty("contactPointDefault")){
					if (row.contactPointDefault != null){
						var cp = row.contactPointDefault;
						name = cp.name;
					}
				}
				return name;
			},
			targets: [7]
		},
		
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editParticipantOffice(null);
			}
		},
		{
			attr: {
				id: "promptDeletepoBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteParticipantOffice();
			}
		}
	];

	var queryUrl = "/rest/ignite/v1/participant-office/" + participantId;
	
	participantOfficeTable = initializeGenericTable("participantOfficeTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editParticipantOffice(rowSelector);  //Double click
										},
			                            null,
			                            5
	);
	
	
	participantOfficeTable.off('deselect');
	participantOfficeTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyContactPointPanel();
		updatePoToolbarButtons();
	} );

	participantOfficeTable.off('select');
	participantOfficeTable.on('select', function (e, dt, type, indexes) {
		showContactPointTable(dt.data());
		updatePoToolbarButtons();
	} );	

	updatePoToolbarButtons();

}
//initializeOfficeTab -- End




function updatePoToolbarButtons() {
	var hasSelected = participantOfficeTable.rows('.selected').data().length > 0;

	setTableButtonState(participantOfficeTable, "promptDeletepoBtn", hasSelected);	
}
	
function promptDeleteParticipantOffice() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Participant Office?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteParticipantOffice(participantOfficeTable);
			   }
	);
}

function deleteParticipantOffice(tbl) {
	var postUrl = "/rest/ignite/v1/participant-office/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Participant Office has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updatePoToolbarButtons();
			});
	
}
	
function showEmptyContactPointPanel() {
	setDivVisibility("contactPointEmptyPanel", "block");
	setDivVisibility("contactPointPanel", "none");
}
	
// showContactPointTable -- Start
function showContactPointTable(row) {
	if (row == null) {
		return;
	}
		
	var participantOfficeId = row.participantOfficeId;
	var url =  "/rest/ignite/v1/contact-point/list-view-contact-point-for-participant-office/" + participantOfficeId;

	var columnsArray = [
    
     		 { data: "contactPointId" }           //0 MySql-TableName: VContactPoint
     		,{ data: "participantOfficeId" }      //1
     		,{ data: "participantOfficeId_Name" } //2
     		,{ data: "name" }                     //3 --
     		,{ data: "emailAddress" }             //4--
     		,{ data: "phoneNumber" }              //5--
     		,{ data: "addressLine1" }             //6
     		,{ data: "addressLine2" }             //7
     		,{ data: "addressLine3" }             //8
     		,{ data: "cityId" }                   //9
     		,{ data: "cityId_Name" }              //10 --
     		,{ data: "suburbId" }                 //11
     		,{ data: "suburbId_Name" }            //12 --
     		,{ data: "provinceId_Name" }          //13--
     		,{ data: "countryId_Name" }           //14--
     		,{ data: "provinceId" }          //15--
     		,{ data: "countryId" }           //16--
     	];

     	var columnDefsArray = [
     		{
     			visible: false,
     			targets: [0,1,2,6,7,8,9,11,15,16]
     		}
     	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editContactPoint(null, participantOfficeId);
			}
		},
		{
			attr: {
				id: "promptDeleteCPBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteContactPoint();
			}
		}
	];
	
	setDivVisibility("contactPointEmptyPanel", "none");
	setDivVisibility("contactPointPanel", "block");
	
	contactPointTable = initializeGenericTable("contactPointTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editContactPoint(rowSelector, participantOfficeId);
										},
										null,
										10,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	contactPointTable.off('deselect');
	contactPointTable.on('deselect', function (e, dt, type, indexes) {
		updateCPToolbarButtons();
	} );

	contactPointTable.off('select');
	contactPointTable.on('select', function (e, dt, type, indexes) {
		updateCPToolbarButtons();
	} );	

	updateCPToolbarButtons();
}
// showContactPointTable -- End


// updateCPToolbarButtons -- Start
function updateCPToolbarButtons() {
	var hasSelected = contactPointTable.rows('.selected').data().length > 0;

	setTableButtonState(contactPointTable, "promptDeleteCPBtn", hasSelected);	
}
//updateCPToolbarButtons -- End

// promptDeleteContactPoint -- Start
function promptDeleteContactPoint() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Contact Point?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteContactPoint(contactPointTable);
			   }
	);
}//promptDeleteContactPoint -- End

// deleteContactPoint -- Start
function deleteContactPoint(tbl) {
	var postUrl = "/rest/ignite/v1/contact-point/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Contact Point has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateCPToolbarButtons();
			});
}//deleteContactPoint -- Start

// editParticipantOffice -- Start
function editParticipantOffice(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = participantOfficeTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
	}
	
	$("#epoParticipantOfficeId").val(data.participantOfficeId);
	$("#epoName").val(data.name);
	$("#epoDescription").val(data.description);
	
	//$("#epoStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
	//$("#epoEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));


	if ((data.costPerSeat == "") || (data.costPerSeat == null)) {
		$("#epoCostPerSeat").val("");
	} else {
		$("#epoCostPerSeat").val(formatDollar(data.costPerSeat)) //       formatMoney(data.amount, 2, ".", " "));	
	}	
	
	
	if ((data.participantOfficeId != null) && (data.participantOfficeId != "")) {
		populateSelect("epoContactPointIdDefault",
			       "/rest/ignite/v1/contact-point/office/" + data.participantOfficeId,
			       "contactPointId",
			       "name",
			       data.contactPointIdDefault,
			       true,
			       null
		);
	}
	
	
	// Set the Save Button to disabled
	setElementEnabled("saveParticipantOfficeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("participantOfficeDialog");
}// editParticipantOffice -- End


// saveParticipantOffice -- Begin
function saveParticipantOffice() {
	
	
	var postUrl = "/rest/ignite/v1/participant-office/new";
	var postData = {
			participantId: $("#epParticipantId").val(),
			participantOfficeId: $("#epoParticipantOfficeId").val(),
			name: $("#epoName").val(),
			description: $("#epoDescription").val(),
			dateFrom: getMsFromDatePicker("epStartDate"),
			contactPointIdDefault:$("#epoContactPointIdDefault").val()
	};
	var errors = "";
	// validate
	if (postData.name == "") {
		errors += "An Office Name is required<br>";
	}

	
	
	if ((postData.costPerSeat == null) || (postData.costPerSeat == "")) {
//		errors += "An Amount is required.<br>";
	} else {	
		if (isNaN(postData.costPerSeat)){
			errors += "The Amount is invalid.<br>";
		}
		if (postData.costPerSeat > 9999999999){
			errors += "The Amount is too large!<br>";
		}	
	}
	
	
	
	if (showFormErrors("epoDlgErrorDiv", errors)) {
		return;
	}

	showEmptyContactPointPanel();
	if ((postData.participantOfficeId != null) && (postData.participantOfficeId != "")) {
		var postUrl = "/rest/ignite/v1/participant-office";
		
	}
	saveEntry(postUrl, postData, "participantOfficeDialog", "The Participant Office has been saved.", participantOfficeTable,
			function(){
				// Update the default Contact point info on General tab if it changed here.
				getParticipantOfficeAndBankDefaults;
			}
	);

	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());
			
}// saveParticipantOffice -- End



//participantOfficeDialogChanged -- Start
function participantOfficeDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveParticipantOfficeButton", true);
}
//participantOfficeDialogChanged -- End


//closeParticipantOfficeDialog -- Start
function closeParticipantOfficeDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("participantOfficeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("participantOfficeDialog");
	};
	somethingChangedInDialog = false;
}
//closeParticipantOfficeDialog -- End


// editContactPoint-- Start
function editContactPoint(rowSelector, participantOfficeId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = contactPointTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((participantOfficeId == undefined) || (participantOfficeId == null)){
			participantOfficeId = data.participantOfficeId;
		}
	}
	contactPointTable.rows().deselect();
	
	$("#eCPContactPointId").val(data.contactPointId);
	$("#eCPParticipantOfficeId").val(participantOfficeId);
	$("#eCPContactPointName").val(data.name);

	$("#eCPCityId").val(data.cityId);                                //3
	$("#eCPCityName").val(data.cityId_Name);                         //4
	
	$("#eCPSuburbId").val(data.suburbId);                            //5
	$("#eCPSuburbName").val(data.suburbId_Name);                     //6

	$("#eCPProvinceId").val(data.provinceId);
	$("#eCPProvinceName").val(data.provinceId_Name);
	
	$("#eCPCountryId").val(data.countryId);
	$("#eCPCountryName").val(data.countryId_Name);

	$("#eCPAddressLine1").val(data.addressLine1);                     //7
	$("#eCPAddressLine2").val(data.addressLine2);                     //8
	$("#eCPAddressLine3").val(data.addressLine3);                     //9
	$("#eCPEmailAddress").val(data.emailAddress);                     //11
	$("#eCPPhoneNumber").val(data.phoneNumber);                       //12
	
	// Set the Save Button to disabled
	setElementEnabled("saveOfficeContactPointBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("contactPointDialog");
}
// editContactPoint -- End



// saveOfficeContactPoint -- Begin

function saveOfficeContactPoint() {
	
	var postUrl = "/rest/ignite/v1/contact-point/new";
	
	
	var postData = {
		contactPointId: $("#eCPContactPointId").val()
		,participantOfficeId: $("#eCPParticipantOfficeId").val()
		,suburbId : $("#eCPSuburbId").val()                                          //3
		,addressLine1 : $("#eCPAddressLine1").val()                                  //4
		,addressLine2 : $("#eCPAddressLine2").val()                                  //5
		,addressLine3 : $("#eCPAddressLine3").val()                                  //6
		,name : $("#eCPContactPointName").val()                                      //7
		,emailAddress : $("#eCPEmailAddress").val()                                  //8
		,phoneNumber : $("#eCPPhoneNumber").val()                                    //9
	};

	var errors = "";	
	

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Contact Point Name is required<br>";
	}
	if ((postData.emailAddress == null) || (postData.emailAddress == "")) {
		errors += "An Email Address is required<br>";
	}	
	if ((postData.phoneNumber == null) || (postData.phoneNumber == "")) {
		errors += "A Phone Number is required<br>";
	}		

	if (((postData.suburbId == null) || (postData.suburbId == "")) && (($("#eCPCityId").val() != "") || ($("#eCPProvinceId").val() != "") || ($("#eCPCountryId").val() != ""))){
		alert("As you did not select a Suburb;  Country, Province and City will not be saved");
	}	
	
	
	if (showFormErrors("eCPDlgErrorDiv", errors)) {
		return;
	}

	//Does record exist? 

	if ((postData.contactPointId != null) && (postData.contactPointId != "")) {  
            // This is an update 
    	postUrl = "/rest/ignite/v1/contact-point";
     }

	saveEntry(postUrl, postData, "contactPointDialog", "The Office Contact Point has been saved.", contactPointTable );
}
//saveOfficeContactPoint -- End



// ContactPointDialogChanged -- Start
function cpDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveOfficeContactPointBtn", true);
}
// ContactPointDialogChanged  -- Start



//closeCPDialog -- Start
function closeCPDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("contactPointDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("contactPointDialog");
	};
	somethingChangedInDialog = false;

}//closeCPDialog -- End


//function editSelectCityForContactPointDialog() {
//	
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
//        		,{ data: "countryId", name: "Country" }                           //1
//        		,{ data: "provinceCode", name: "Province" }                         //2
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
//				var markName = row.name;
//
//				$("#eCPCityId").val(id);
//				$("#eCPCityName").val(markName);
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


function editSelectCountryForContactPointDialog() {
	
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

				$("#eCPCountryId").val(id);
				$("#eCPCountryName").val(markName);
				$("#eCPProvinceId").val("");
				$("#eCPProvinceName").val("");		
				$("#eCPCityId").val("");
				$("#eCPCityName").val("");
				$("#eCPSuburbId").val("");
				$("#eCPSuburbName").val("");
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
}  //  editSelectCountryForContactPointDialog  --  END



function editSelectProvinceForContactPointDialog() {
	
	if ($("#eCPCountryId").val() == "") {
		alert("Please select a Country.");
		return;
	}
	var queryUrl="/rest/ignite/v1/province/list-view-province-for-country/" + $("#eCPCountryId").val();
	
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

				$("#eCPProvinceId").val(id);
				$("#eCPProvinceName").val(markName);	
				$("#eCPCityId").val("");
				$("#eCPCityName").val("");
				$("#eCPSuburbId").val("");
				$("#eCPSuburbName").val("");
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
} // editSelectProvinceForContactPointDialog -- END



function editSelectCityForContactPointDialog() {
	
	if ($("#eCPProvinceId").val() == "") {
		alert("Please select a Province.");
		return;
	}
	var queryUrl="/rest/ignite/v1/city/list-view-city-for-province-min/" + $("#eCPProvinceId").val();
	
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

				$("#eCPCityId").val(id);
				$("#eCPCityName").val(markName);
				$("#eCPSuburbId").val("");
				$("#eCPSuburbName").val("");
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
}  //editSelectCityForContactPointDialog  --  END



function editSelectSuburbForContactPointDialog() {
	
	if ($("#eCPCityId").val() == "") {
		alert("Please select a City.");
		return;
	}	
	var queryUrl="/rest/ignite/v1/suburb/list-view-suburb-for-city-min/" + $("#eCPCityId").val();
	
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

				$("#eCPSuburbId").val(id);
				$("#eCPSuburbName").val(markName);
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
}  //  editSelectSuburbForContactPointDialog  --  END

