var somethingChangedInDialog = null;
var askToSaveDialog = null;

//initializeVehicleListTable -- Start
function initializeVehicleListTable(participantId) {
	
	var queryUrl="/rest/ignite/v1/vehicle/participant/" + participantId; 
	
	var columnsArray = [
	            		{ data:"vehicleId" },    					// 	0  VehicleId			  -->
	            		{ data:"vehicleModelId" },    				// 	1  VehicleModelId         -->
	            		{ data:"" },    							// 	2     VehicleMakeId       -->
	            		{ data:"" },    							// 	3     VehicleMakeName     -->
	            		{ data:"" },    							// 	4     VehicleModelName    -->
	            		{ data:"vehicleTyreAndRimTypeId" },    	// 	5  VehicleTyreAndRimType  -->
	            		{ data:"name" },    						// 	6  Name                   -->
	            		{ data:"licenceNumber" },    				// 	7  LicenceNumber          -->
	            		{ data:"registrationYear" },    			// 	8  RegistrationYear       -->
	            		{ data:"licenceExpiryDate" },    			// 	9  LicenceExpiryDate      -->
	            		{ data:"vehicleRegisterNumber" },    		// 	10 VehicleRegisterNumber  -->
	            		{ data:"vINNumber" },    					// 	11 VINNumber              -->
	            		{ data:"enginNumber" },    					// 	12 EnginNumber            -->
	            		
	            		{ data:"nextServiceDate" },    				// 	15 NextServiceDate        -->
	            		{ data:"nextServiceOdoReading" },    		// 	16 NextServiceOdoReading  -->	
		
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,5,10,11,12,13,14] //, 3, 4, 5, 10, 11, 12, 13, 15, 16]
		},

		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [15,9]//, 15, 17]
		},

		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("vehicleModel")) {
					if (row.vehicleModel != null) {
						data = row.vehicleModel.vehicleMakeId; 
					}
				}
				return data;
			},
			targets: 2
		},
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("vehicleModel")) {
					if (row.vehicleModel != null) {
						data = row.vehicleModel.name; 
					}
				}
				return data;
			},
			targets: 4
		},
		{	
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("vehicleModel")) {
					if (row.vehicleModel != null) {
						data = row.vehicleModel.vehicleMake.name; 
					}
				}
				return data;
			},
			targets: 3
		}		



	
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
					editVehicle(null);
			}
		}
	]
	
	participantVehiclesTable = initializeGenericTable("participantVehiclesTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										editVehicle(rowSelector);  //Double click
										}
	);

	participantVehiclesTable.off('deselect');
	participantVehiclesTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyInfoPanel();
	} );
	
	participantVehiclesTable.off('select');
	participantVehiclesTable.on('select', function (e, dt, type, indexes) {
		
		
		if ( type === "row" ) {
			var vehicleTableRow = participantVehiclesTable.rows( indexes ).data();
		}
		showInfoPanel(vehicleTableRow);
		
		/*if ( type === 'row' ) {
        	var vehicleTableRow = participantVehiclesTable.rows( indexes ).data();
			
			initializeRegularUserTable(vehicleTableRow);
		}*/
		
	} );
	
	/*if (type === "row") {
		var vehicleTableRow = participantVehiclesTable.rows(indexes).data();
	}	*/

	showEmptyInfoPanel();
	
}//initializeVehicleListTable -- End

//updateVToolbarButtons -- Start
function updateVToolbarButtons() {
	var hasSelected = participantVehiclesTable.rows('.selected').data().length > 0;
	if (hasSelected) {
		var row = participantVehiclesTable.rows('.selected').data()[0];
		$("#vSelectedVehicleId").val(row.vehicleId);
		initializeRegularUserTable(row);
		initializeMaintenanceListTable(row.vehicleId);
		initializeReadingListTable(row.vehicleId);
		initializeItemListTable(row.vehicleId);
	}
//	setTableButtonState(participantVehiclesTable, "promptDeleteVehiclesBtn", hasSelected);	
	
}//updateVToolbarButtons -- End

function showEmptyInfoPanel() {
	setDivVisibility("emptyVehicleInfoPanel", "block");
	setDivVisibility("vRegularUserPanel", "none");
	setDivVisibility("vMaintenancePanel", "none");
	setDivVisibility("vReadingPanel", "none");
	setDivVisibility("vItemPanel", "none");
}

function showInfoPanel() {
	updateVToolbarButtons();
	setDivVisibility("emptyVehicleInfoPanel", "none");
	setDivVisibility("vRegularUserPanel", "block");
	setDivVisibility("vMaintenancePanel", "block");
	setDivVisibility("vReadingPanel", "block");
	setDivVisibility("vItemPanel", "block");
}

//initializeRegularUserTable -- Start
function initializeRegularUserTable(vehicleTableRow) {
	
	var vehicleId = vehicleTableRow.vehicleId;
	
	var queryUrl = "/rest/ignite/v1/vehicle/user/" + vehicleId;

	var columnsArray = [
		{ data: "vehicleUserId" },    					
		{ data: "vehicleId" },
		{ data: "participantId" }, 
		{ data: "systemName" },
		{ data: "description" }, 
		{ data: "lastUpdateTimestamp" }, 
		{ data: "lastUpdateUserName" }			
	];

	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 5, 6]
		}
	];

	var buttonsArray = [
			{
				titleAttr: "New",
				text: "<i class='fas fa-plus'></i>",
				className: "btn btn-sm",
				action: function(e, dt, node, config) {
					editRegularUser(null, vehicleTableRow);
				}
			},
			{                                                //Mag nie 'n asset delete nie.
				attr: {                                      //As dit verander, gaan maak deleteAsset.sql reg
					id: "promptDeleteVehicleUserBtn"
				},
				titleAttr: "Delete",
				text: "<i class='fas fa-minus'></i>",
				className: "btn btn-sm",
				action: function() {
					promptDeleteVehicleUser();
				}
			}

	];

	vRegularUserTable = initializeGenericTable("vRegularUserTable",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editRegularUser(rowSelector, vehicleTableRow);
		}
	);

	vRegularUserTable.off('deselect');
	vRegularUserTable.on('deselect', function(e, dt, type, indexes) {
		updateVehicleUserButtons();
	});

	vRegularUserTable.off('select');
	vRegularUserTable.on('select', function(e, dt, type, indexes) {
		updateVehicleUserButtons();
	});

	updateVehicleUserButtons(); 
	
}//initializeRegularUserTable -- End

function updateVehicleUserButtons() {
	var hasSelected = vRegularUserTable.rows('.selected').data().length > 0;
	
	setTableButtonState(vRegularUserTable, "promptDeleteVehicleUserBtn", hasSelected);
}

function promptDeleteVehicleUser() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected user?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteVehicleUser(vRegularUserTable);
			   }
	);
}

function editRegularUser(rowSelector, vehicleTableRow) {
	
	var data = {};
	var errors = "";
	var vehicleId = vehicleTableRow.vehicleId;
	var vehicleMakeId = vehicleTableRow.vehicleMakeId
	var vehicleDescription = vehicleTableRow.description;
	
	if (rowSelector != null) {
		data = vRegularUserTable.row(rowSelector).data();
	}
	
	vRegularUserTable.rows().deselect();
	$("#pvVehicleMakeId").val(data.vehicleMakeId);
	$("#pvVehicleId").val(vehicleId);
	$("#pvVehicleUserId").val(data.vehicleUserId);
	$("#pvParticipantId").val(data.participantId);
	$("#pvRegularUser").val(data.systemName);
	$("#pvVehicleDescription").val(data.description);
	
	
	//Set the Save Button to disabled
	setElementEnabled("saveVehicleUserDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("vehicleUserDialog");
}

function deleteVehicleUser(tbl) {
	var postUrl = "/rest/ignite/v1/vehicle/user/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The User has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				//updateAssetUserButtons();
			});
} 

function saveVehicleUserDialog() {
	
	var postUrl = "/rest/ignite/v1/vehicle/user/new";
	var postData = {
			vehicleUserId: $("#pvVehicleUserId").val(),
			vehicleId: $("#pvVehicleId").val(),
			participantId: $("#pvParticipantId").val(),
			description: $("#pvVehicleDescription").val()
	}
	
	var errors = "";
	
	if ((postData.participantId == null) || (postData.participantId == "")) {
		errors += "A Vehicle User is required. <br>"
	}
	
	if (showFormErrors("pvDlvErrorDiv", errors)) {
		return;
	}
	
	var theDialog = "vehicleUserDialog";
	var theSentence = "The User has been saved.";
	
	if ((postData.vehicleUserId != null) && (postData.vehicleUserId != "")) {
		var postUrl = "/rest/ignite/v1/vehicle/user"
	}
	
	saveEntry(postUrl, postData, theDialog, theSentence, vRegularUserTable);
}

function closeVehicleUserDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("pvDlgErrorDiv", "none");
				closeModalDialog("vehicleUserDialog");
			});
	} else {
		setDivVisibility("pvDlgErrorDiv", "none");
		closeModalDialog("vehicleUserDialog");
	}
}


function vehicleUserDialogChanged() {
	setElementEnabled("saveVehicleUserDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

//initializeMaintenanceListTable -- Start
function initializeMaintenanceListTable(vehicleId) {
	
	var queryUrl="/rest/ignite/v1/vehicle-maintenance/per-vehicle/" + vehicleId; 
	
	var columnsArray = [

	            		{ data:"vehicleMaintenanceId" },    		// 	0  vehicleMaintenanceId			  -->
	            		{ data:"vehicleId" },    					// 	1  vehicleId         -->
	            		{ data:"maintenanceTypeId" },    			// 	2  maintenanceTypeId       -->
	            		{ data:"" },    					// 	3  maintenanceTypeId     -->
	            		{ data:"description" },    					// 	4  description     -->
	            		{ data:"maintenanceDate" },    				// 	5  maintenanceDate    -->
	            		{ data:"kilometerReading" }    			// 	6  kilometerReading  -->
	
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2] //, 3, 4, 5, 10, 11, 12, 13, 15, 16]
		},
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("maintenanceType")) {
					if (row.maintenanceType != null) {
						data = row.maintenanceType.name; 
					}
				}
				return data;
			},
			targets: 3
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [5]//, 15, 17]
		}		
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
					editMaintenance(null);
			}
		},
		{                                                
			attr: {                                      
				id: "promptDeleteMaintenanceBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteMaintenance();
			}
		}
	]
	
	vMaintenanceTable = initializeGenericTable("vMaintenanceTable",
	                            queryUrl,
	                            columnsArray,
	                            columnDefsArray,
	                            buttonsArray,
	                            function(rowSelector) {
								editMaintenance(rowSelector);  //Double click
								},
	                            null,
	                            10,
								[5,"desc"] //Order by column 2 ascending, normally defaults to column 1 ascending
	);

	vMaintenanceTable.off('deselect');
	vMaintenanceTable.on('deselect', function (e, dt, type, indexes) {
		updateVMToolbarButtons();
	} );
	
	vMaintenanceTable.off('select');
	vMaintenanceTable.on('select', function (e, dt, type, indexes) {
		updateVMToolbarButtons();
	} );	

	updateVMToolbarButtons();
	
}//initializeMaintenanceListTable -- End


function updateVMToolbarButtons() {
	var hasSelected = vMaintenanceTable.rows('.selected').data().length > 0;
	setTableButtonState(vMaintenanceTable, "promptDeleteMaintenanceBtn", hasSelected);	
}

function promptDeleteMaintenance() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected record?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteVehicleMaintenance(vMaintenanceTable);
			   }
	);
}

function deleteVehicleMaintenance(tbl) {
	var postUrl = "/rest/ignite/v1/vehicle-maintenance/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Record has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateVMToolbarButtons();
			});
}


//initializeReadingListTable -- Start
function initializeReadingListTable(vehicleId) {
	
	var queryUrl="/rest/ignite/v1/vehicle-reading/per-vehicle/" + vehicleId; 
	
	var columnsArray = [

	            		{ data:"vehicleReadingId" },    	// 	0  			  -->
	            		{ data:"vehicleId" },    			// 	1           -->
	            		{ data:"description" },    			// 	2         -->
	            		{ data:"readingDate" },    			// 	3       -->
	            		{ data:"odometerReading" },    		// 	4      -->
	            		{ data:"threadDepthmm" }    		// 	5    -->
	
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1] //, 3, 4, 5, 10, 11, 12, 13, 15, 16]
		},

		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [3]//, 15, 17]
		}		
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
					editReading(null);
			}
		},
		{                                                
			attr: {                                      
				id: "promptDeleteReadingBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteReading();
			}
		}
	]
	
			vReadingTable = initializeGenericTable("vReadingTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										editReading(rowSelector);  //Double click
										},
			                            null,
			                            10,
										[3,"desc"] //Order by column 2 ascending, normally defaults to column 1 ascending
	);

	vReadingTable.off('deselect');
	vReadingTable.on('deselect', function (e, dt, type, indexes) {
		updateVRToolbarButtons();
	} );
	
	vReadingTable.off('select');
	vReadingTable.on('select', function (e, dt, type, indexes) {
		updateVRToolbarButtons();
	} );	

	updateVRToolbarButtons();
	
}//initializeReadingListTable -- End

function updateVRToolbarButtons() {
	var hasSelected = vReadingTable.rows('.selected').data().length > 0;
	setTableButtonState(vReadingTable, "promptDeleteReadingBtn", hasSelected);	
}

function promptDeleteReading() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Reading?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteVehicleReading(vReadingTable);
			   }
	);
}

function deleteVehicleReading(tbl) {
	var postUrl = "/rest/ignite/v1/vehicle-reading/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Reading has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateVRToolbarButtons();
			});
}



//initializeItemListTable -- Start
function initializeItemListTable(vehicleId) {
	
	var queryUrl="/rest/ignite/v1/vehicle-item/per-vehicle/" + vehicleId; 
	
	var columnsArray = [

	            		{ data:"vehicleItemId" },    	// 	0  			  -->
	            		{ data:"vehicleId" },    			// 	1           -->
	            		{ data:"itemTypeId" },    			// 	2         -->
	            		{ data:"" },    			// 	3         -->
	            		{ data:"description" }
	
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2] //, 3, 4, 5, 10, 11, 12, 13, 15, 16]
		},
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("itemType")) {
					if (row.itemType != null) {
						data = row.itemType.name; 
					}
				}
				return data;
			},
			targets: 3
		}		
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
					editItem(null);
			}
		},

		{                                                
			attr: {                                      
				id: "promptDeleteItemBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteItem();
			}
		}
	]
	
			vItemTable = initializeGenericTable("vItemTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										editItem(rowSelector);  //Double click
										},
			                            null,
			                            10,
										[2,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
	);

	vItemTable.off('deselect');
	vItemTable.on('deselect', function (e, dt, type, indexes) {
		updateVIToolbarButtons();
	} );
	
	vItemTable.off('select');
	vItemTable.on('select', function (e, dt, type, indexes) {
		updateVIToolbarButtons();
	} );	

	updateVIToolbarButtons();
	
}//initializeItemListTable -- End

function updateVIToolbarButtons() {
	var hasSelected = vItemTable.rows('.selected').data().length > 0;
	setTableButtonState(vItemTable, "promptDeleteItemBtn", hasSelected);	
}

function promptDeleteItem() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Item?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteVehicleItem(vItemTable);
			   }
	);
}

function deleteVehicleItem(tbl) {
	var postUrl = "/rest/ignite/v1/vehicle-item/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Item has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateVIToolbarButtons();
			});
}

//vehicleDialog -- Start
//--editVehicle-- Start
function editVehicle(rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;

	if (rowSelector != null) { //hy bestaan reeds, ons gaan hom edit.
		data = participantVehiclesTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		disNuweEen = false;
		assetId = data.assetId;
//		document.getElementById('aAssetTypeId').disabled = true
	} else {  //dis 'n nuwe een.
//		document.getElementById('aAssetTypeId').disabled = false;
		disNuweEen = true;
		$("#vVehicleTyreAndRimTypeId").val("");
		$("#vVehicleTyreAndRimTypeIdName").val("");
		$("#vVehicleModel").empty();

		$("#vParticipantIdSponsor").val("");
		$("#vParticipantIdSponsorName").val("");

		$("#vParticipantIdSoldTo").val("");
		$("#vParticipantIdSoldToName").val("");
		
		$("#vParticipantOfficeIdLocation").val("");	
		$("#vParticipantOfficeIdLocationName").val("");
		
		$("#vParticipantRightOfUse").val("");
		
		$("#vPurchaseAmount").val("");
		
		$("#vGuaranteePeriodEnd").val("");
		
		$("#vAssetAquiredDate").val("");
		$("#vOwnershipToSponsorDate").val("");
		$("#vAssetRemovedDate").val("");
		
		$("#vAssetSoldAmount").val("");
		
		
		
		
		
	}
	
	participantVehiclesTable.rows().deselect();
	
	
	$("#vVehicleId").val(data.vehicleId);  			                  // 	0  
//	$("#vVehicleModelId").val(data.vehicleModelId);  
//	if (disNuweEen == false) {
//		$("#vVehicleMakeId").val(data.vehicleModel.vehicleMakeId); 
//	}
//	$("#vVehicleMakeName").val(data.vehicleMakeName);    		          // 	3  
//	$("#vVehicleModelName").val(data.vehicleModelName);    	          // 	4  
	$("#vVehicleTyreAndRimTypeId").val(data.vehicleTyreAndRimTypeId);    // 	5  
	$("#vVehicleTyreAndRimTypeIdName").val(data.vehicleTyreAndRimTypeId);    // 	5  
	$("#vName").val(data.name);    				                      // 	6  
	$("#vLicenceNumber").val(data.licenceNumber);    		              // 	7  
	$("#vRegistrationYear").val(data.registrationYear);    				          // 	8   
	$("#vLicenceExpiryDate").datepicker("setDate", data.licenceExpiryDate == null? null : new Date(data.licenceExpiryDate)); // 	9  
	$("#vVehicleRegisterNumber").val(data.vehicleRegisterNumber);        // 	10 
	$("#vVINNumber").val(data.vINNumber);    			                  // 	11 
	$("#vEnginNumber").val(data.enginNumber);    			              // 	12 
	
	$("#vNextServiceDate").datepicker("setDate", data.nextServiceDate == null? null : new Date(data.nextServiceDate)); // 	13 
	$("#vNextServiceOdoReading").val(data.nextServiceOdoReading);        // 	14 
	
	
	if (disNuweEen == false) {
		populateSelect("vVehicleMake", 
			       "/rest/ignite/v1/vehicle-make", 
			       "vehicleMakeId", 
			       "name", 
			       data.vehicleModel.vehicleMakeId, 
			       false,
			       null
		);
	} else {
		populateSelect("vVehicleMake", 
			       "/rest/ignite/v1/vehicle-make", 
			       "vehicleMakeId", 
			       "name", 
			       null, 
			       true,
			       null
		);
	}
	
	if (disNuweEen == false) {
		
		populateSelect("vVehicleModel", 
			       "/rest/ignite/v1/vehicle-make/vehicle-model/" + data.vehicleModel.vehicleMakeId, 
			       "vehicleModelId", 
			       "name", 
			       data.vehicleModelId, 
			       false,
			       null
		);
	};
	
//Asset info	

	if (disNuweEen == true) {
		
		$("#vParticipantIdOriginalOwner").val($("#epParticipantId").val());	
		$("#vParticipantIdOriginalOwnerName").val($("#epSystemName").val());		
		$("#vParticipantIdCurrentOwner").val($("#epParticipantId").val());	
		$("#vParticipantIdCurrentOwnerName").val($("#epSystemName").val());		
		
		populateSelect("vAssetConditionId", 
			       "/rest/ignite/v1/asset-condition", 
			       "assetConditionId", 
			       "name", 
			       null, 
			       true,
			       null
		);
		
		populateSelect("vAssetStatusId", 
			       "/rest/ignite/v1/asset-status", 
			       "assetStatusId", 
			       "name", 
			       null, 
			       true,
			       null
		);			
		
	} else {	
		getAssetInfoForVehicle()
	}
	
	window.setTimeout(function(){
		disableSaveButton();
	},200);
	
	
	
	showModalDialog("vehicleDialog");	
}//editVehicle -- End

function disableSaveButton(){
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
}



function editSaveVehicle() {
	
	if ($("#vVehicleId").val() == "") {
		saveNewVehicle()
	} else {
		saveVehicle()
	}
}



function saveNewVehicle() {

	$("#vPurchaseAmount").val($("#vPurchaseAmount").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	$("#vAssetSoldAmount").val($("#vAssetSoldAmount").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol		
	
	var postUrl = "/rest/ignite/v1/vehicle/new";
	var postData = {	
		vehicleModelId : $("#vVehicleModel").val(),
		vehicleTyreAndRimTypeId : $("#vVehicleTyreAndRimTypeId").val(),
		name : $("#vName").val(),
		licenceNumber : $("#vLicenceNumber").val(),
		registrationYear :($("#vRegistrationYear").val() == "" ? 'null' :  $("#vRegistrationYear").val()),

		licenceExpiryDate : ($("#vLicenceExpiryDate").val() == "" ? null : $("#vLicenceExpiryDate").val()),
		vehicleRegisterNumber : $("#vVehicleRegisterNumber").val(),
		vINNumber : $("#vVINNumber").val(),
		enginNumber : $("#vEnginNumber").val(),

		nextServiceDate : ($("#vNextServiceDate").val() == "" ? null : $("#vNextServiceDate").val()),
		
		nextServiceOdoReading : ($("#vNextServiceOdoReading").val() == "" ? null : $("#vNextServiceOdoReading").val()),

		assetConditionId : $("#vAssetConditionId").val(),
		assetStatusId : $("#vAssetStatusId").val(),
		participantIdOriginalOwner : $("#vParticipantIdOriginalOwner").val(),
		participantIdCurrentOwner : $("#vParticipantIdCurrentOwner").val(),
		participantIdSponsor : $("#vParticipantIdSponsor").val(),
		participantIdSoldTo : $("#vParticipantIdSoldTo").val(),

		participantOfficeIdLocation : $("#vParticipantOfficeIdLocation").val(),

		participantRightOfUse : $("#vParticipantRightOfUse").val(),
		purchaseAmount : $("#vPurchaseAmount").val(),

		guaranteePeriodEnd : ($("#vGuaranteePeriodEnd").val() == "" ? null : $("#vGuaranteePeriodEnd").val()),

		assetAquiredDate : ($("#vAssetAquiredDate").val() == "" ? null : $("#vAssetAquiredDate").val()),

		ownershipToSponsorDate : ($("#vOwnershipToSponsorDate").val() == "" ? null : $("#vOwnershipToSponsorDate").val()),

		assetRemovedDate : ($("#vAssetRemovedDate").val() == "" ? null : $("#vAssetRemovedDate").val()),
		assetSoldAmount : $("#vAssetSoldAmount").val()
	}
	
	var errors = "";

	// validate
	
	if ((postData.vehicleModelId == null) || (postData.vehicleModelId == "")) {
		errors += "A Vehicle Model is required.<br>";
	}	
	
	if ((postData.vehicleTyreAndRimTypeId == null) || (postData.vehicleTyreAndRimTypeId == "")) {
		errors += "A Tyre size is required.<br>";
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

	if ((postData.assetAquiredDate == null) || (postData.assetAquiredDate == "")) {
		errors += "An Asset Acquired Date is required.<br>";
	}		
	
	if ((postData.registrationYear != null) && (postData.registrationYear != "")) {
		 if(isNaN(postData.registrationYear)){
			 errors += "Please enter a valid Registration Year.<br>";
		 } else {
			 if ((postData.registrationYear < 1920) || (postData.registrationYear > 2028)){
				 errors += "Please enter a valid Registration Year.<br>";
			 }
		 }
	} else {
		 errors += "Please enter a Registration Year.<br>";
	}

	if (($("#vPurchaseAmount").val() != null) && ($("#vPurchaseAmount").val() != "")) {
		 if(isNaN($("#vPurchaseAmount").val())){
			 errors += "Please enter a valid Purchase Amount.<br>";
		 } 
	} else {
		 errors += "Please enter a Purchase Amount.<br>";
	}	
	
	
	if (showFormErrors("vDlgErrorDiv", errors)) {
		return;
	}	
	
	saveEntry(postUrl, postData, "vehicleDialog", "The New Vehicle has been saved.", participantVehiclesTable, function(request, response){
		var data = response;
		var vehicleId = data.vehicleId;
		$("#vSelectedVehicleId").val(vehicleId);

		initializeMaintenanceListTable(vehicleId);
		initializeReadingListTable(vehicleId);
		initializeItemListTable(vehicleId);
		
		// Set the Save Button to disabled
		setElementEnabled("saveVehicleButton", false);
		somethingChangedInDialog = false;
		askToSaveTab = false;
	});	
		
}// saveNewVehicle -- End








//saveVehicle -- Begin     This is to update, not to insert.  maw dis nie 'n Nuwe Een nie
function saveVehicle() {
	
	$("#vPurchaseAmount").val($("#vPurchaseAmount").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	$("#vAssetSoldAmount").val($("#vAssetSoldAmount").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol	
	
	
	var postUrl = "/rest/ignite/v1/vehicle";
	var postData = {
			
			vehicleId: $("#vVehicleId").val(),			    				// 	0 
			vehicleModelId: $("#vVehicleModel").val(),         			// 	1 
//			vehicleMakeId: $("#vVehicleMakeId").val(),       				// 	2 
//			vehicleMakeName: $("#vVehicleMakeName").val(),     				// 	3 
//			vehicleModelName: $("#vVehicleModelName").val(),    			// 	4 
			vehicleTyreAndRimTypeId: $("#vVehicleTyreAndRimTypeId").val(),  // 	5 
			name: $("#vName").val(),                   						// 	6 
			licenceNumber: $("#vLicenceNumber").val(),          			// 	7 
			registrationYear: $("#vRegistrationYear").val(),       			// 	8 
			licenceExpiryDate: getMsFromDatePicker("vLicenceExpiryDate"),   // 	9 
			vehicleRegisterNumber: $("#vVehicleRegisterNumber").val(),  	// 	10
			vINNumber: $("#vVINNumber").val(),              				// 	11
			enginNumber: $("#vEnginNumber").val(),            				// 	12
			
			nextServiceDate: getMsFromDatePicker("vNextServiceDate"),       // 	13  As jy nie die datePicker hier gebruik nie, add hy 2 ure as hy save na die db.  (MAW hierdie werk nie:      ownershipToSponsorDate: $("#aOwnershipToSponsorDate").val(),)
			nextServiceOdoReading: $("#vNextServiceOdoReading").val(),  	// 	14			
				 
	};

	
	
	var errors = "";

	// validate
	
	if ((postData.vehicleModelId == null) || (postData.vehicleModelId == "")) {
		errors += "A Vehicle Model is required.<br>";
	}	
	
	if ((postData.vehicleTyreAndRimTypeId == null) || (postData.vehicleTyreAndRimTypeId == "")) {
		errors += "A Tyre size is required.<br>";
	}		

	
	if (($("#vAssetAquiredDate").val() == null) || ($("#vAssetAquiredDate").val() == "")) {
		errors += "An Asset Aquired Date is required.<br>";
	}

	if ((postData.registrationYear != null) && (postData.registrationYear != "")) {
		 if(isNaN(postData.registrationYear)){
			 errors += "Please enter a valid Registration Year.<br>";
		 } else {
			 if ((postData.registrationYear < 1920) || (postData.registrationYear > 2028)){
				 errors += "Please enter a valid Registration Year.<br>";
			 }
		 }
	} else {
		 errors += "Please enter a Registration Year.<br>";
	}
	
	
	if (($("#vPurchaseAmount").val() != null) && ($("#vPurchaseAmount").val() != "")) {
		 if(isNaN($("#vPurchaseAmount").val())){
			 errors += "Please enter a valid Purchase Amount.<br>";
		 } 
	} else {
		 errors += "Please enter a Purchase Amount.<br>";
	}
	
	

	if (showFormErrors("vDlgErrorDiv", errors)) {
		return;
	}


	saveEntry(postUrl, postData, null, null, null);

	var postUrl = "/rest/ignite/v1/asset";
	var postData = {
			assetId: $("#vAssetId").val(),
			 assetTypeId: "VEHICLE",
			 assetConditionId: $("#vAssetConditionId").val(),
			 assetStatusId: $("#vAssetStatusId").val(),
			 participantIdOriginalOwner: $("#vParticipantIdOriginalOwner").val(),
			 participantIdCurrentOwner: $("#vParticipantIdCurrentOwner").val(),
			 participantIdSponsor: $("#vParticipantIdSponsor").val(),
			 participantIdSoldTo: $("#vParticipantIdSoldTo").val(),
			 vehicleId: $("#vVehicleId").val(),
			 participantOfficeIdLocation: $("#vParticipantOfficeIdLocation").val(),
			 assetNumber:  null,
			 description:  null,
			 brandOrMake:  null,
			 serialNumber: null,
			 participantRightOfUse: $("#vParticipantRightOfUse").val(),
			 purchaseAmount: $("#vPurchaseAmount").val(),
			 guaranteePeriodEnd: getMsFromDatePicker("vGuaranteePeriodEnd"),
			 
			 assetAquiredDate: getMsFromDatePicker("vAssetAquiredDate"),
			 ownershipToSponsorDate: getMsFromDatePicker("vOwnershipToSponsorDate"),      // As jy nie die datePicker hier gebruik nie, add hy 2 ure as hy save na die db.  (MAW hierdie werk nie:      ownershipToSponsorDate: $("#aOwnershipToSponsorDate").val(),)
			 assetRemovedDate: getMsFromDatePicker("vAssetRemovedDate"),
			 assetSoldAmount: $("#vAssetSoldAmount").val()
			 
	};	
	
	
	saveEntry(postUrl, postData, "vehicleDialog", "The Vehicle has been saved.", participantVehiclesTable);
	
}// saveVehicle -- End

//closeVehicleDialog -- Start
function closeVehicleDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("vehicleDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("vehicleDialog");
	};
	somethingChangedInDialog = false;
}
//closeVehicleDialog -- End

function populateVehicleModel() {
	
let theString = "/rest/ignite/v1/vehicle-make/vehicle-model/" + $("#vVehicleMake").val()

	populateSelect("vVehicleModel", 
				theString, 
		       "vehicleModelId", 
		       "name", 
		       null,
		       true,
		       null
	);
	
}

function editSelectParticipantIdCurrentOwnerV() {
	selectParticipantId("vParticipantIdCurrentOwner", "vParticipantIdCurrentOwnerName");
	generalTabChanged();
}

function editSelectParticipantIdOriginalOwnerV() {
	selectParticipantId("vParticipantIdOriginalOwner", "vParticipantIdOriginalOwnerName");
	generalTabChanged();
}

function editSelectParticipantIdSponsorV() {
	selectParticipantId("vParticipantIdSponsor", "vParticipantIdSponsorName");
	generalTabChanged();
}

function editSelectParticipantIdSoldToV() {
	selectParticipantId("vParticipantIdSoldTo", "vParticipantIdSoldToName");
	generalTabChanged();
}

function editSelectVehicleTyreAndRimTypeId() {
	SelectVehicleTyreAndRimTypeId("vVehicleTyreAndRimTypeId", "vVehicleTyreAndRimTypeIdName");
	generalTabChanged();
}

function SelectVehicleTyreAndRimTypeId(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/vehicle-tyre-and-rim-type/";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="vehicleTyreAndRimTypeId";
			var refColumnName="name";
			var columns = [
				{ data: "vehicleTyreAndRimTypeId", name: "Code" },
				{ data: "name", name: "Name" },
				{ data: "description", name: "Description" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.vehicleTyreAndRimTypeId;
				var repName = row.name;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetName).trigger("change");
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

function selectVehicleUser(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="participantId";
			var refColumnName="participantId";
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

//vDialogChanged -- Start
function vDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleButton", true);
	
}
//vDialogChanged -- End

function editSelectParticipantOfficeIdLocationV() {
	selectParticipantOfficeIdLocation("vParticipantOfficeIdLocation", "vParticipantOfficeIdLocationName", "vParticipantIdCurrentOwner");
	generalTabChanged();
}


function selectParticipantOfficeIdLocation(targetId, targetName, ownerId) {
	var queryUrl="/rest/ignite/v1/participant-office/" + $("#" + ownerId).val();
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="participantId";
			var refColumnName="systemName";
			var columns = [
				{ data: "participantOfficeId", name: "participantOfficeId" },
				{ data: "name", name: "Name" },
				{ data: "description", name: "Description" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantOfficeId;
				var repName = row.name;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetName).trigger("change");
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

function getAssetInfoForVehicle() {
	
	var queryUrl;
//	var participantId = jQuery('#vParticipantIdCurrentOwner').attr('value');
	var participantId = $("#epParticipantId").val();
	var vehicleId     = jQuery('#vVehicleId').attr('value');
	
	queryUrl = "/rest/ignite/v1/asset/asset-info-for-vehicle/" + participantId + "/" + vehicleId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			$("#vAssetId").val(data.assetId);        						// 	0

			$("#vAssetConditionId").val(data.assetConditionId);        	// 	2
			$("#vAssetStatusId").val(data.assetStatusId);        		// 	3			
			
			$("#vParticipantIdOriginalOwner").val(data.participantIdOriginalOwner);        // 	4
			$("#vParticipantIdOriginalOwnerName").val(data.originalOwner);
			$("#vParticipantIdCurrentOwner").val(data.participantIdCurrentOwner);        	// 	
			$("#vParticipantIdCurrentOwnerName").val(data.currentOwner);			

			$("#vParticipantIdSponsor").val(data.participantIdSponsor);
			$("#vParticipantIdSponsorName").val(data.sponsor);

			$("#vParticipantIdSoldTo").val(data.participantIdSoldTo);  	
			$("#vParticipantIdSoldToName").val(data.soldTo);
			
			$("#vVehicleId").val(data.vehicleId);        								// 	
			
			$("#vParticipantOfficeIdLocation").val(data.participantOfficeIdLocation);        // 	
			$("#vParticipantOfficeIdLocationName").val(data.participantOfficeName);
			
			$("#vParticipantRightOfUse").val(data.participantRightOfUse);        		//	
			$("#vPurchaseAmount").val((data.purchaseAmount != null) ? "R " + (data.purchaseAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
			
			$("#vGuaranteePeriodEnd").datepicker("setDate", data.guaranteePeriodEnd == null? null : new Date(data.guaranteePeriodEnd));
			$("#vAssetAquiredDate").datepicker("setDate", data.assetAquiredDate == null? null : new Date(data.assetAquiredDate));	
			$("#vOwnershipToSponsorDate").datepicker("setDate", data.ownershipToSponsorDate == null? null : new Date(data.ownershipToSponsorDate));	
			$("#vAssetRemovedDate").datepicker("setDate", data.assetRemovedDate == null? null : new Date(data.assetRemovedDate));
			                			//	
			$("#vAssetSoldAmount").val((data.assetSoldAmount != null) ? "R " + (data.assetSoldAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
			

			populateSelect("vAssetConditionId", 
				       "/rest/ignite/v1/asset-condition", 
				       "assetConditionId", 
				       "name", 
				       data.assetConditionId, 
				       true,
				       null
			);
			
			populateSelect("vAssetStatusId", 
				       "/rest/ignite/v1/asset-status", 
				       "assetStatusId", 
				       "name", 
				       data.assetStatusId, 
				       true,
				       null
			);				
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});

}; //getAssetInfoForVehicle



function getAssetForDateRange() {
	
	var dateFrom = new Date(getMsFromDatePicker("drAssetStartDate"));
	var dateTo = new Date(getMsFromDatePicker("drAssetEndDate"));
	
	if (getMsFromDatePicker("drAssetEndDate") < getMsFromDatePicker("drAssetStartDate")) {
		dateFrom = dateTo;
	}
	
	$("#aAssetStartDate").datepicker("setDate", new Date(dateFrom));
	$("#aAssetEndDate").datepicker("setDate", new Date(dateTo));	

	initializeAssetListTable($("#epParticipantId").val(), dateFrom, dateTo)
	
}
//vehicleDialog -- End

//vehicleMaintenanceDialog -- start
//--editMaintenance-- Start
function editMaintenance(rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;
	

	if (rowSelector != null) { //hy bestaan reeds, ons gaan hom edit.
		data = vMaintenanceTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		disNuweEen = false;
	} else {  //dis 'n nuwe een.
		disNuweEen = true
	}
	

	vMaintenanceTable.rows().deselect();
	
	$("#vmVehicleMaintenanceId").val(data.vehicleMaintenanceId);        						// 	0

	$("#vmMaintenanceTypeId").val(data.maintenanceTypeId);
	$("#vmDescription").val(data.description);
	$("#vmMaintenanceDate").datepicker("setDate", new Date(data.maintenanceDate));	
	$("#vmKilometerReading").val(data.kilometerReading);   
	
//	$("#vrDescription").val(data.description);
//	$("#vrReadingDate").datepicker("setDate", data.readingDate == null? null : new Date(data.readingDate));
//	$("#vrOdometerReading").val(data.odometerReading);        	// 	2
//	$("#vrThreadDepthmm").val(data.threadDepthmm);        		// 	3

	populateSelect("vmMaintenanceTypeId", 
		       "/rest/ignite/v1/maintenance-type",     //
		       "maintenanceTypeId", 
		       "name", 
		       data.maintenanceTypeId, 
		       true,
		       null)
	
	showModalDialog("vehicleMaintenanceDialog");
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleMaintenanceButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
	
}
//editMaintenance -- End

//saveVehicleMaintenance -- Begin
function saveVehicleMaintenance() {

	var postUrl = "/rest/ignite/v1/vehicle-maintenance/new";
	var postData = {
			 vehicleMaintenanceId: $("#vmVehicleMaintenanceId").val(),
			 vehicleId: $("#vSelectedVehicleId").val(),
			 maintenanceTypeId: $("#vmMaintenanceTypeId").val(),
			 description: $("#vmDescription").val(),

			 maintenanceDate: getMsFromDatePicker("vmMaintenanceDate"),
			 kilometerReading: $("#vmKilometerReading").val()
			 
	};

	var errors = "";

	// validate
	
	if ((postData.maintenanceTypeId == null) || (postData.maintenanceTypeId == "")) {
		errors += "A Maintenance Type is required.<br>";
	}	

	if ((postData.maintenanceDate == null) || (postData.maintenanceDate == "")) {
		errors += "A Date is required.<br>";
	}	
	
	if ((postData.kilometerReading == null) || (postData.kilometerReading == "")) {
		errors += "A Kilometer Reading is required.<br>";
	} else {	
		 if(isNaN(postData.kilometerReading)){
			 errors += "Please enter a valid Kilometer Reading.<br>";
			 $("#vmKilometerReading").val("");
		 }
	}
	

	if (showFormErrors("vmDlgErrorDiv", errors)) {
		return;
	}


	var theDialog = "vehicleMaintenanceDialog";
	var theSentence = "The Maintenance record has been saved.";
	
	if ((postData.vehicleMaintenanceId != null) && (postData.vehicleMaintenanceId != "")) {
		var postUrl = "/rest/ignite/v1/vehicle-maintenance";	
	} 

	saveEntry(postUrl, postData, theDialog, theSentence, vMaintenanceTable);


}// saveVehicleMaintenance -- End


//closeVehicleMaintenanceDialog -- Start
function closeVehicleMaintenanceDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("vehicleMaintenanceDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("vehicleMaintenanceDialog");
	};
	somethingChangedInDialog = false;
}
//closeVehicleMaintenanceDialog -- End
//vmVehicleMaintenanceChanged -- Start
function vmVehicleMaintenanceChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleMaintenanceButton", true);
}//vmVehicleMaintenanceChanged -- End
//vehicleMaintenanceDialog -- Ends

//vehicleReadingDialog -- Start
//--editReading-- Start
function editReading(rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;
	
	if (rowSelector != null) { //hy bestaan reeds, ons gaan hom edit.
		data = vReadingTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		disNuweEen = false;
	} else {  //dis 'n nuwe een.
		disNuweEen = true
	}
	
	vReadingTable.rows().deselect();
	
	$("#vrVehicleReadingId").val(data.vehicleReadingId);        						// 	0
	
	$("#vrDescription").val(data.description);
	$("#vrReadingDate").datepicker("setDate", data.readingDate == null? new Date() : new Date(data.readingDate));
	$("#vrOdometerReading").val(data.odometerReading);        	// 	2
	$("#vrThreadDepthmm").val(data.threadDepthmm);        		// 	3
//	if (disNuweEen) {
//		// Add Today's date without time
//		$("#vrReadingDate").val(timestampToString(new Date(), false));		
//	}
	showModalDialog("vehicleReadingDialog");
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleReadingButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
	
}
//editReading -- End

//saveVehicleReading -- Begin
function saveVehicleReading() {

	var postUrl = "/rest/ignite/v1/vehicle-reading/new";
	var postData = {
			 vehicleReadingId: $("#vrVehicleReadingId").val(),
			 vehicleId: $("#vSelectedVehicleId").val(),
			 description: $("#vrDescription").val(),

			 readingDate: getMsFromDatePicker("vrReadingDate"),
			 odometerReading: $("#vrOdometerReading").val(),
			 threadDepthmm: $("#vrThreadDepthmm").val()
			 
	};

	var errors = "";

	// validate


	if ((postData.readingDate == null) || (postData.readingDate == "")) {
		errors += "A Date is required.<br>";
	}	
	
	if ((postData.odometerReading == null) || (postData.odometerReading == "")) {
		errors += "A Kilometer Reading is required.<br>";
	} else {	
		 if(isNaN(postData.odometerReading)){
			 errors += "Please enter a valid Kilometer Reading.<br>";
			 $("#vrOdometerReading").val("");
		 }
	}
	

	if (showFormErrors("vrDlgErrorDiv", errors)) {
		return;
	}


	var theDialog = "vehicleReadingDialog";
	var theSentence = "The Reading has been saved.";
	
	if ((postData.vehicleReadingId != null) && (postData.vehicleReadingId != "")) {
		var postUrl = "/rest/ignite/v1/vehicle-reading";	
	} 

	saveEntry(postUrl, postData, theDialog, theSentence, vReadingTable);


}// saveVehicleReading -- End

//closeVehicleReadingDialog -- Start
function closeVehicleReadingDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("vehicleReadingDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("vehicleReadingDialog");
	};
	somethingChangedInDialog = false;
}
//closeVehicleReadingDialog -- End

//vrVehicleReadingChanged -- Start
function vrVehicleReadingChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleReadingButton", true);
}//vrVehicleReadingChanged -- End
//vehicleReadingDialog -- End

//vehicleItemDialog -- Start
//--editItem-- Start
function editItem(rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;
	var vehicleId = $("#vSelectedVehicleId").val()    //hier iewers

	if (rowSelector != null) { //hy bestaan reeds, ons gaan hom edit.
		data = vItemTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		disNuweEen = false;
	} else {  //dis 'n nuwe een.
		disNuweEen = true
	}
	
	vItemTable.rows().deselect();
	
	$("#viVehicleItemId").val(data.vehicleItemId);        						// 	0

	$("#viItemTypeId").val(data.itemTypeId);
	$("#viDescription").val(data.description);
	if (disNuweEen) {
		populateSelect("viItemTypeId",   //name of html select element the will be populated
			       "/rest/ignite/v1/item-type/not-linked/" + vehicleId,        //",     //
			       "itemTypeId",         //the value that must be saved
			       "name",                 //shown to user
			       null,                   //The selected one, if there is one
			       true,                   //addEmpty, boolean: should we add empty object at the top
			       null)                   //completeMethod
		
	} else {
		populateSelect("viItemTypeId", 
		       "/rest/ignite/v1/item-type",        //
		       "itemTypeId", 
		       "name", 
		       data.itemTypeId, 
		       true,
		       null)
	}
	
	showModalDialog("vehicleItemDialog");
	// Set the Save Button to disabled
	setElementEnabled("saveVehicleItemButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
	
}
//editItem -- End

//saveVehicleItem -- Begin
function saveVehicleItem() {

	var postUrl = "/rest/ignite/v1/vehicle-item/new";
	var postData = {
			 vehicleItemId: $("#viVehicleItemId").val(),
			 vehicleId: $("#vSelectedVehicleId").val(),
			 itemTypeId: $("#viItemTypeId").val(),
			 description: $("#viDescription").val() 
	};

	var errors = "";

	// validate
	
	if ((postData.itemTypeId == null) || (postData.itemTypeId == "")) {
		errors += "A Maintenance Type is required.<br>";
	}	

	if (showFormErrors("viDlgErrorDiv", errors)) {
		return;
	}

	var theDialog = "vehicleItemDialog";
	var theSentence = "The Item has been saved.";
	
	if ((postData.vehicleItemId != null) && (postData.vehicleItemId != "")) {
		var postUrl = "/rest/ignite/v1/vehicle-item";	
	} 

	saveEntry(postUrl, postData, theDialog, theSentence, vItemTable);

}// saveVehicleItem -- End

//closeVehicleItemDialog -- Start
function closeVehicleItemDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("vehicleItemDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("vehicleItemDialog");
	};
	somethingChangedInDialog = false;
}
//closeVehicleItemDialog -- End

//viVehicleItemChanged -- Start
function viVehicleItemChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveVehicleItemButton", true);
}//viVehicleItemChanged -- End
//vehicleItemDialog -- End



