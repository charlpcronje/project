var assetUserTable = null;
var participantAssetTable = null;
var assetTableRow = null;

//initializeParticipantAssetTable -- Start
function initializeParticipantAssetTable(participantId, dateFrom, dateTo) {

	var queryUrl = "/rest/ignite/v1/asset/participant/" + participantId + "/" + dateFrom + "/" + dateTo;

	var columnsArray = [
		{ data: "assetId" },    				// 	0	
		{ data: "assetTypeId" },    		// 	1	
		{ data: "assetTypeName" },             			  //	2
		{ data: "assetConditionId" },      // 	3	
		{ data: "assetConditionName" },             			  //	
		{ data: "assetStatusId" },    		// 	5	
		{ data: "assetStatusName" },             			  //	6
		{ data: "participantIdOriginalOwner" },    // 	7	
		{ data: "originalOwner" },             			  //	8
		{ data: "participantIdCurrentOwner" },    // 	9	
		{ data: "currentOwner" },             			//	10
		{ data: "participantIdSponsor" },    // 	11	
		{ data: "sponsor" },             		//	12
		{ data: "participantIdSoldTo" },     // 	13	
		{ data: "soldTo" },             	    //	14
		{ data: "vehicleId" },    		    // 	15	
		{ data: "vehicleName" },             		//	16
		{ data: "licenceNumber" },             		//	17
		{ data: "participantOfficeIdLocation" },    // 	18	
		{ data: "participantOfficeName" },             	//	19
		{ data: "assetNumber" },    		// 	20	
		{ data: "description" },    		// 	21	
		{ data: "brandOrMake" },    		// 	22	
		{ data: "serialNumber" },    		// 	23	
		{ data: "participantRightOfUse" },   //	24	

		{ data: "purchaseAmount" },    		// 	25	
		{ data: "guaranteePeriodEnd" },    	// 	26	

		{ data: "assetAquiredDate" },    	// 	27	
		{ data: "ownershipToSponsorDate" },    // 	28	
		{ data: "assetRemovedDate" },    		// 	29	
		{ data: "assetSoldAmount" }    			//	30	
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 5, 7, 9, 10, 11, 13, 14, 15, 16, 17, 18, 22, 23, 24,  26, 28, 29, 30]
		},
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [28, 31, 32, 33]//, 15, 17]
		},
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = valueToRand(data);
				}
				return html;
			},
			width: "10%",
			targets: [27, 34]
		},
		{
			className: "dt-right",
			targets: [27, 34]
		}


	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editAssets(null, assetTableRow);

			}
		}

		//		{                                                //Mag nie 'n asset delete nie.
		//			attr: {                                      //As dit verander, gaan maak deleteAsset.sql reg
		//				id: "promptDeleteAssetsBtn"
		//			},
		//			titleAttr: "Delete",
		//			text: "<i class='fas fa-minus'></i>",
		//			className: "btn btn-sm",
		//			action: function() {
		//				promptDeleteAssets();
		//			}
		//		}

	];

	participantAssetsTable = initializeGenericTable("participantAssetsTable",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editAssets(rowSelector, assetTableRow);  //Double click
		}
	);

	participantAssetsTable.off('deselect');
	participantAssetsTable.on('deselect', function(e, dt, type, indexes) {
		showEmptyAssetUserTable();
		//		updateAToolbarButtons();
	});

	participantAssetsTable.off('select');
	participantAssetsTable.on('select', function(e, dt, type, indexes) {

		if (type === 'row') {
			assetTableRow = participantAssetsTable.rows(indexes).data();

			initializeAssetUserTable(assetTableRow);
		}
		//		updateAToolbarButtons();
	});

	//	updateAToolbarButtons();

}//initializeParticipantAssetTable -- End

//initializeAssetUserTable -- Start
function initializeAssetUserTable(assetTableRow) {

	var assetId = assetTableRow[0].assetId;

	var queryUrl = "/rest/ignite/v1/asset/user/" + assetId;

	var columnsArray = [
		{ data: "assetUserId" },
		{ data: "assetId" },
		{ data: "participantId" },
		{ data: "systemName" },
		{ data: "firstName" },
		{ data: "initials"},
		{ data: "lastName" },
		{ data: "description" },
		{ data: "lastUpdateTimestamp" },
		{ data: "lastUpdateUserName" }
	];

	setDivVisibility("emptyAssetUserTablePanel", "none");
	setDivVisibility("assetUserTablePanel", "block");

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 8, 9]
		},
		{
		width: "10%",
			targets: [5, 6]
		},
		{
		width: "15%",
			targets: [4]
		}
	];



	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editAssetUser(null, assetTableRow);
			}
		},
		{                                                //Mag nie 'n asset delete nie.
			attr: {                                      //As dit verander, gaan maak deleteAsset.sql reg
				id: "promptDeleteAssetUserBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteAssetUser();
			}
		}

	];

	assetUserTable = initializeGenericTable("assetUserTable",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editAssetUser(rowSelector, assetTableRow);
			//no edit
		}
	);

	assetUserTable.off('deselect');
	assetUserTable.on('deselect', function(e, dt, type, indexes) {
		updateAssetUserButtons();
		//		updateAToolbarButtons();
	});

	assetUserTable.off('select');
	assetUserTable.on('select', function(e, dt, type, indexes) {
		updateAssetUserButtons();
		//		updateAToolbarButtons();
	});

	updateAssetUserButtons();
	//	updateAToolbarButtons();

}//initializeAssetUserTable -- End

function selectAssetUser(targetId, targetName) {
	var queryUrl = "/rest/ignite/v1/individual/list";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "participantId";
			var columns = [
				{ data: "participantId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: 0
				}
			];
			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var name = row.firstName + " " + row.lastName;
				
				$("#" + targetId).val(id);
				$("#" + targetName).val(name);
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

function editAssetUserName() {
	selectAssetUser("patAssetUserNameId", "patAssetUserName");
	assetUserDialogChanged();
}

function editAssetUser(rowSelector, assetTableRow) {

	var data = {};
	var errors = "";
	var assetId = assetTableRow[0].assetId;
	var assetDescription = assetTableRow[0].description;
	var assetCurrentOwner = assetTableRow[0].currentOwner;
	var assetTypeName = assetTableRow[0].assetTypeName;

	if (rowSelector != null) {
		data = assetUserTable.row(rowSelector).data();
	}

	assetUserTable.rows().deselect();
	$("#patAssetUserId").val(data.assetUserId);
	$("#patAssetId").val(assetId);
	$("#patAssetTypeName").val(assetTypeName);
	$("#patAssetCurrentOwner").val(assetCurrentOwner);
	$("#patAssetDescription").val(assetDescription);
	$("#patAssetUserDescription").val(data.description);
	$("#patAssetUserName").val(data.systemName);
	$("#patParticipantId").val(data.participantId);

	//Set the Save Button to disabled
	setElementEnabled("saveAssetUserDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("assetUserDialog");

}

function saveAssetUserDialog() {

	var postUrl = "/rest/ignite/v1/asset/user/new";
	var postData = {
		assetUserId: $("#patAssetUserId").val(),
		assetId: $("#patAssetId").val(),
		participantId: $("#patParticipantId").val(),
		description: $("#patAssetUserDescription").val()
	}

	var errors = "";

	if ((postData.participantId == null) || (postData.participantId == "")) {
		errors += "An Asset User is required.<br>";
	}

	if (showFormErrors("patDlgErrorDiv", errors)) {
		return;
	}

	var theDialog = "assetUserDialog";
	var theSentence = "The User has been saved.";

	if ((postData.assetUserId != null) && (postData.assetUserId != "")) {
		var postUrl = "/rest/ignite/v1/asset/user";
	}
	saveEntry(postUrl, postData, theDialog, theSentence, assetUserTable);
}

function promptDeleteAssetUser() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected user?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function() {
			deleteAssetUser();
		}
	);
}

function deleteAssetUser() {
	var postUrl = "/rest/ignite/v1/asset/user/delete";
	var row = assetUserTable.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The User has been deleted.", assetUserTable,
		function() {
			assetUserTable.rows().deselect();
			updateAssetUserButtons();
		});
}

function updateAssetUserButtons() {
	var hasSelected = assetUserTable.rows('.selected').data().length > 0;

	setTableButtonState(assetUserTable, "promptDeleteAssetUserBtn", hasSelected);
}

function assetUserDialogChanged() {
	setElementEnabled("saveAssetUserDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeAssetUserDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("patDlgErrorDiv", "none");
				closeModalDialog("assetUserDialog");
			});
	} else {
		setDivVisibility("patDlgErrorDiv", "none");
		closeModalDialog("assetUserDialog");
	}
}


function showEmptyAssetUserTable() {
	setDivVisibility("emptyAssetUserTablePanel", "block");
	setDivVisibility("assetUserTablePanel", "none");
}

function selectNewAssetDateRange() {

	var dateFrom = getMsFromDatePicker("aAssetStartDate");
	var dateTo = getMsFromDatePicker("aAssetEndDate");

	$("#drAssetStartDate").datepicker("setDate", new Date(dateFrom));
	$("#drAssetEndDate").datepicker("setDate", new Date(dateTo));

	showModalDialog("dateRangeAssetDialog");
}
//selectNewAssetDateRange -- End



//updateAToolbarButtons -- Start
function updateAToolbarButtons() {
	var hasSelected = participantAssetsTable.rows('.selected').data().length > 0;

	setTableButtonState(participantAssetsTable, "promptDeleteAssetsBtn", hasSelected);

}//updateAToolbarButtons -- End

////promptDeleteAssets -- Start
//function promptDeleteAssets() {
//	showDialog("Confirm?",
//		       "Are you sure that you wish to delete the selected Asset?",
//		       DialogConstants.TYPE_CONFIRM, 
//		       DialogConstants.ALERTTYPE_INFO, 
//		       function () {
//					deleteAsset(participantAssetsTable);
//			   }
//	);
//}
////promptDeleteAssets -- End
//
////deleteAsset -- Start
//function deleteAsset(tbl) {
//	var postUrl = "/rest/ignite/v1/asset/delete";
//	var row = tbl.rows('.selected').data()[0];
//	
//	// Disable delete button when record has been deleted.
//	saveEntry(postUrl, row, null, "The Asset has been deleted.", tbl,
//			function(){	
//				tbl.rows().deselect();
//				updateAToolbarButtons();
//			});
//}
////deleteAsset -- End

//--editAssets-- Start
function editAssets(rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;

	if (rowSelector != null) { //hy bestaan reeds, ons gaan hom edit.
		data = participantAssetsTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		disNuweEen = false;
		assetId = data.assetId;
		let elementAb = document.getElementById("aAssetNumberButton"); elementAb.setAttribute("Hidden", "hidden");
		document.getElementById('aAssetTypeId').disabled = true
	} else {  //dis 'n nuwe een.
		document.getElementById('aAssetTypeId').disabled = false;
		disNuweEen = true
		let elementAb = document.getElementById("aAssetNumberButton"); elementAb.removeAttribute("hidden");
	}

	participantAssetsTable.rows().deselect();

	$("#aAssetId").val(data.assetId);        						// 	0
	$("#aAssetTypeId").val(data.assetTypeId);        			// 	1

	$("#aAssetConditionId").val(data.assetConditionId);        	// 	2
	$("#aAssetStatusId").val(data.assetStatusId);        		// 	3

	if (disNuweEen == true) {

		$("#aParticipantIdOriginalOwner").val($("#epParticipantId").val());
		$("#aParticipantIdOriginalOwnerName").val($("#epSystemName").val());
		$("#aParticipantIdCurrentOwner").val($("#epParticipantId").val());
		$("#aParticipantIdCurrentOwnerName").val($("#epSystemName").val());

		getNextAssetNumber();

	} else {
		$("#aParticipantIdOriginalOwner").val(data.participantIdOriginalOwner);        // 	4
		$("#aParticipantIdOriginalOwnerName").val(data.originalOwner);
		$("#aParticipantIdCurrentOwner").val(data.participantIdCurrentOwner);        	// 	
		$("#aParticipantIdCurrentOwnerName").val(data.currentOwner);
	}

	$("#aParticipantIdSponsor").val(data.participantIdSponsor);
	$("#aParticipantIdSponsorName").val(data.sponsor);

	$("#aParticipantIdSoldTo").val(data.participantIdSoldTo);
	$("#aParticipantIdSoldToName").val(data.soldTo);

	$("#aVehicleId").val(data.vehicleId);        								// 	
	$("#aVehicleIdName").val(data.vehicleName);                       //data.vehicle.name
	$("#aVehicleLicenceNumber").val(data.licenceNumber);     //data.vehicle.licencNumber

	$("#aParticipantOfficeIdLocation").val(data.participantOfficeIdLocation);        // 	
	$("#aParticipantOfficeIdLocationName").val(data.participantOfficeName);

	$("#aAssetNumber").val(data.assetNumber);        							// 	
	$("#aDescription").val(data.description);        							// 	
	$("#aBrandOrMake").val(data.brandOrMake);        							// 	
	$("#aSerialNumber").val(data.serialNumber);        							// 	
	$("#aParticipantRightOfUse").val(data.participantRightOfUse);        		//	

	$("#aPurchaseAmount").val((data.purchaseAmount != null) ? "R " + (data.purchaseAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke

	$("#aGuaranteePeriodEnd").datepicker("setDate", data.guaranteePeriodEnd == null ? null : new Date(data.guaranteePeriodEnd));

	$("#aAssetAquiredDate").datepicker("setDate", data.assetAquiredDate == null ? null : new Date(data.assetAquiredDate));
	$("#aOwnershipToSponsorDate").datepicker("setDate", data.ownershipToSponsorDate == null ? null : new Date(data.ownershipToSponsorDate));
	$("#aAssetRemovedDate").datepicker("setDate", data.assetRemovedDate == null ? null : new Date(data.assetRemovedDate));

	$("#aAssetSoldAmount").val((data.assetSoldAmount != null) ? "R " + (data.assetSoldAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke	

	/* function populateSelect(
	elementId, html select element that will be populated 
	url, url where it must get the data (you can paste in browser window to see the data)
	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
	displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	*/


	if (disNuweEen == true) {
		populateSelect("aAssetTypeId",
			"/rest/ignite/v1/asset-type/other",     //Al die ander, maw NIE Vehicles nie, geen selection
			"assetTypeId",
			"name",
			null,
			true,
			null)
	} else {
		populateSelect("aAssetTypeId",
			"/rest/ignite/v1/asset-type",         //Al die asset-types, select die regte een
			"assetTypeId",
			"name",
			data.assetTypeId,
			true,
			null)
	};

	populateSelect("aAssetConditionId",
		"/rest/ignite/v1/asset-condition",
		"assetConditionId",
		"name",
		data.assetConditionId,
		true,
		null
	);

	populateSelect("aAssetStatusId",
		"/rest/ignite/v1/asset-status",
		"assetStatusId",
		"name",
		data.assetStatusId,
		true,
		null
	);

	populateSelect("vVehicleMake",
		"/rest/ignite/v1/vehicle-make",
		"vehicleMakeId",
		"name",
		data.vehicleMakeId,
		true,
		null
	);

	showModalDialog("assetDialog");

	// Set the Save Button to disabled
	setElementEnabled("saveAssetButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

}
//editAssets -- End

//saveAsset -- Begin
function saveAsset() {

	$("#aPurchaseAmount").val($("#aPurchaseAmount").val().replace('R', '').replace(/ /g, '')); //remove spaces (thousand separator) and R-symbol
	$("#aAssetSoldAmount").val($("#aAssetSoldAmount").val().replace('R', '').replace(/ /g, '')); //remove spaces (thousand separator) and R-symbol	

	var postUrl = "/rest/ignite/v1/asset/new";
	var postData = {
		assetId: $("#aAssetId").val(),
		assetTypeId: $("#aAssetTypeId").val(),
		assetConditionId: $("#aAssetConditionId").val(),
		assetStatusId: $("#aAssetStatusId").val(),
		participantIdOriginalOwner: $("#aParticipantIdOriginalOwner").val(),
		participantIdCurrentOwner: $("#aParticipantIdCurrentOwner").val(),
		participantIdSponsor: $("#aParticipantIdSponsor").val(),
		participantIdSoldTo: $("#aParticipantIdSoldTo").val(),
		vehicleId: $("#aVehicleId").val(),
		participantOfficeIdLocation: $("#aParticipantOfficeIdLocation").val(),
		assetNumber: $("#aAssetNumber").val(),
		description: $("#aDescription").val(),
		brandOrMake: $("#aBrandOrMake").val(),
		serialNumber: $("#aSerialNumber").val(),
		participantRightOfUse: $("#aParticipantRightOfUse").val(),
		purchaseAmount: $("#aPurchaseAmount").val(),
		guaranteePeriodEnd: getMsFromDatePicker("aGuaranteePeriodEnd"),

		assetAquiredDate: getMsFromDatePicker("aAssetAquiredDate"),
		ownershipToSponsorDate: getMsFromDatePicker("aOwnershipToSponsorDate"),      // As jy nie die datePicker hier gebruik nie, add hy 2 ure as hy save na die db.  (MAW hierdie werk nie:      ownershipToSponsorDate: $("#aOwnershipToSponsorDate").val(),)

		assetRemovedDate: getMsFromDatePicker("aAssetRemovedDate"),
		assetSoldAmount: $("#aAssetSoldAmount").val()
	};

	var errors = "";

	// validate

	if ((postData.assetTypeId == null) || (postData.assetTypeId == "")) {
		errors += "An Asset Type is required.<br>";
	}

	if ((postData.assetConditionId == null) || (postData.assetConditionId == "")) {
		errors += "An Asset Condition is required.<br>";
	}

	if ((postData.assetStatusId == null) || (postData.assetStatusId == "")) {
		errors += "An Asset Status is required.<br>";
	}

	if ((postData.participantIdOriginalOwner == null) || (postData.participantIdOriginalOwner == "")) {
		errors += "An Original Owner is required.<br>";
	}

	if ((postData.participantIdCurrentOwner == null) || (postData.participantIdCurrentOwner == "")) {
		errors += "A Current Owner is required.<br>";
	}

	if ((postData.description == null) || (postData.description == "")) {
		errors += "A Description is required.<br>";
	}


	if ((postData.assetAquiredDate == null) || (postData.assetAquiredDate == "")) {
		errors += "An Asset Acquired Date is required.<br>";
	}


	if ((postData.purchaseAmount == null) || (postData.purchaseAmount == "")) {
		errors += "Please enter a valid Purchase Amount.<br>";
	} else {
		if (isNaN(postData.purchaseAmount)) {
			errors += "Please enter a valid Purchase Amount.<br>";
		}
	}

	if ((postData.assetSoldAmount == null) || (postData.assetSoldAmount == "")) {
	} else {
		if (isNaN(postData.assetSoldAmount)) {
			errors += "Please enter a valid Sold Amount.<br>";
		}
	}

	if (showFormErrors("aAssetDlgErrorDiv", errors)) {
		return;
	}

	var theDialog = "assetDialog";
	var theSentence = "The Asset has been saved.";

	if ((postData.assetId != null) && (postData.assetId != "")) {
		var postUrl = "/rest/ignite/v1/asset";
	}

	saveEntry(postUrl, postData, theDialog, theSentence, participantAssetsTable);

}// saveAsset -- End

//closeAssetDialog -- Start
function closeAssetDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("assetDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("assetDialog");
	};
	somethingChangedInDialog = false;

}
//closeAssetDialog -- End

//assetDialogChanged -- Start
function assetDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveAssetButton", true);
}
//assetDialogChanged -- End

function editSelectParticipantIdOriginalOwner() {
	selectParticipantId("aParticipantIdOriginalOwner", "aParticipantIdOriginalOwnerName");
	generalTabChanged();
}

function editSelectParticipantIdCurrentOwner() {
	selectParticipantId("aParticipantIdCurrentOwner", "aParticipantIdCurrentOwnerName");
	generalTabChanged();
}

function editSelectParticipantIdSponsor() {
	selectParticipantId("aParticipantIdSponsor", "aParticipantIdSponsorName");
	generalTabChanged();
}

function editSelectParticipantOfficeIdLocation() {
	selectParticipantOfficeIdLocation("aParticipantOfficeIdLocation", "aParticipantOfficeIdLocationName", "aParticipantIdCurrentOwner");
	generalTabChanged();
}

function editSelectParticipantIdSoldTo() {
	selectParticipantId("aParticipantIdSoldTo", "aParticipantIdSoldToName");
	generalTabChanged();
}

function selectParticipantId(targetId, targetName) {
	var queryUrl = "/rest/ignite/v1/participant/all-in-view";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "systemName";
			var columns = [
				{ data: "participantId", name: "participantId" },
				{ data: "systemName", name: "System Name" },
				{ data: "registeredName", name: "Registered Name" },
				{ data: "tradingName", name: "Trading Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: 0
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var repName = row.systemName;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetNamme).trigger("change");
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
} //selectParticipantId -- END


function getNextAssetNumber() {

	var queryUrl;
	var participantId = jQuery('#aParticipantIdCurrentOwner').attr('value');

	queryUrl = "/rest/ignite/v1/asset/next-asset-number/" + participantId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			$("#aAssetNumber").val(data);

			if (($("#aAssetNumber").val() == null) || ($("#aAssetNumber").val() == "")) {
				$("#aAssetNumber").val(1001);
			}

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
};


