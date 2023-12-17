var humanResourceTable = null;

//********************** Human Resource *********************** //
//initializeHumanResourceTable -- Start

function initializeHumanResourceTable(participantId){

	showEmptyRemunerationPanel();
	showEmptyCompetencyPanel();
	$("#selectedIndividualId").val("");
	
	var queryUrl="/rest/ignite/v1/non-project-related-agreement/participant/" + participantId; 
	
	var columnsArray = [
		{ data: "nonProjectRelatedAgreementId" },	//0
		{ data: "participantIdPayer" },				//1
		{ data: "systemNamePayer" },			//2
		{ data: "participantIdBeneficiary" },	//3
		{ data: "systemNameBeneficiary" },		//4
		{ data: "resourceTypeId" },			//5
		{ data: "resourceTypeName" },			//6
		{ data: "description" },				//7
		{ data: "startDate" },					//8
		{ data: "endDate" },					//9
		{ data: "individualId" }				//10
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 5, 7, 8, 9, 10]
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
			targets: [8, 9]
		}
		/*,
		{
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 9
		}
		*/
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editHumanResource(participantId, null);
			}
		},
		{
			attr: {
				id: "promptDeleteHumanResourceBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteHumanResource();
			}
		}
	];
	humanResourceTable = initializeGenericTable("humanResourceTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editHumanResource(participantId, aThis);
										}
										,null     //completeMethod
									    ,14      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[2,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,true   //showFilter
	);

	humanResourceTable.off('deselect');
	humanResourceTable.on('deselect', function (e, dt, type, indexes) {
		
		showEmptyRemunerationPanel();
		showEmptyCompetencyPanel();
		updateHumanResourceToolbarButtons();
		
	} );

	humanResourceTable.off('select');
	humanResourceTable.on('select', function (e, dt, type, indexes) {
		showRemunerationTable(dt.data());
		showCompetencyTable(dt.data());
		updateHumanResourceToolbarButtons();
	} );
	
	updateHumanResourceToolbarButtons();
}
//initializeHumanResourceTable -- Start

function updateHumanResourceToolbarButtons() {
	var hasSelected = humanResourceTable.rows('.selected').data().length > 0;

	setTableButtonState(humanResourceTable, "promptDeleteHumanResourceBtn", hasSelected);	
}

function showEmptyRemunerationPanel() {
	setDivVisibility("resourceRemunerationEmptyPanel", "block");
	setDivVisibility("resourceRemunerationPanel", "none");
}

function showEmptyCompetencyPanel() {
	setDivVisibility("resourceCompetencyEmptyPanel", "block");
	setDivVisibility("resourceCompetencyPanel", "none");
}


function promptDeleteHumanResource() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Human Resource?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteHumanResource(humanResourceTable);
			   }
			);
}

function deleteHumanResource(tbl) {
	var postUrl = "/rest/ignite/v1/non-project-related-agreement/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Human Resource has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateHumanResourceToolbarButtons();
			});
}

//showResourceRemunerationTable -- Start
function showRemunerationTable(row) {
	if (row == null) {
		return;
	}
		
	var nonProjectRelatedAgreementId = row.nonProjectRelatedAgreementId;
	var url =  "/rest/ignite/v1/resource-remuneration/" + nonProjectRelatedAgreementId;
    
	var columnsArray = [
	                    
		{data:"resourceRemunerationId"},	//0
		{data:"nonProjectRelatedAgreementId"},	//1
		{data:"description"},				//2
		{data:"resourceRemunTypeId"},		//3
		{data:"resourceRemunTypeId"},		//4
		{data:"startDate"},					//5
		{data:"endDate"},					//6
		{data:"amount"},					//7
		{data:"allowExpenseDeductions"}		//8
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0,1,2,3,8]
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
			targets: [5, 6]
		},
		
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				return html;
			},
			targets: [7]
		},	
		{
			className: "dt-right",
			targets: [7]
		}		
		
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editResourceRemuneration(null, nonProjectRelatedAgreementId);
			}
		},
		{
			attr: {
				id: "promptDeleteResourceRemunerationBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteResourceRemuneration();
			}
		}
	];
	
	setDivVisibility("resourceRemunerationEmptyPanel", "none");
	setDivVisibility("resourceRemunerationPanel", "block");
	
	resourceRemunerationTable = initializeGenericTable("resourceRemunerationTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editResourceRemuneration(rowSelector, nonProjectRelatedAgreementId);
										},
										null,
										10,
										[[4,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,true   //showFilter
	);

	
	resourceRemunerationTable.off('deselect');
	resourceRemunerationTable.on('deselect', function (e, dt, type, indexes) {
		updateResourceRemunerationToolbarButtons();
	} );

	resourceRemunerationTable.off('select');
	resourceRemunerationTable.on('select', function (e, dt, type, indexes) {
		updateResourceRemunerationToolbarButtons();
	} );	

	updateResourceRemunerationToolbarButtons();
}
//showResourceRemunerationTable -- End

//updateResourceRemunerationToolbarButtons -- Start
function updateResourceRemunerationToolbarButtons() {
	var hasSelected = resourceRemunerationTable.rows('.selected').data().length > 0;

	setTableButtonState(resourceRemunerationTable, "promptDeleteResourceRemunerationBtn", hasSelected);	
}
//updateResourceRemunerationToolbarButtons -- End

//promptDeleteResourceRemuneration -- Start
function promptDeleteResourceRemuneration() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Resource Remuneration?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteResourceRemuneration(resourceRemunerationTable);
			   }
	);
}
//promptDeleteResourceRemuneration -- End

//deleteResourceRemuneration -- Start
function deleteResourceRemuneration(tbl) {
	var postUrl = "/rest/ignite/v1/resource-remuneration/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Resource Remuneration has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateResourceRemunerationToolbarButtons();
			});
}
//deleteResourceRemuneration -- End


//showCompetencyTable -- Start
function showCompetencyTable(row) {
		
	if (row == null) {
		return;
	}
		
	var participantId = row.participantIdBeneficiary;
	var individualId = row.individualId
	$("#selectedIndividualId").val(row.individualId);
		
	var url =  "/rest/ignite/v1/individual-sd-role/participant/" + participantId;
		
	var columnsArray = [
	               		{data:"individualSdRoleId"},          //  0 individualSdRoleId      
	               		{data:"individualId"},                //  1 individualId            
	               		{data:"sdRoleId"},                    //  2 sdRoleId                
	               		{data:"competencyLevelId"},         //  3 competencyLevelId     
	               		{data:"participantId"},               //  4 participantId        	
	               		{data:"combinedName"},				  //  5 combinedName		   
	               		{data:"competencyLevelName"},         //  6 competencyLevelName       
	               		{data:"yearExperience"},            //  7      
	               		
	               		{data:"serviceDisciplineId"},         //  8 serviceDisciplineId  
	               		{data:"serviceDisciplineCode"},       //  9 serviceDisciplineCode   
	               		{data:"serviceDisciplineName"},       //  10 serviceDisciplineName   
	               		{data:"roleOnAProjectName"}		      //  11 roleOnAProjectName	
	            		,{data: "sdParentName" }             //12
	            		,{data: "sdGrandParentName" }        //13
	            		,{data: "sdIndustryName" }           //14
			];
			
		var columnDefsArray = [
			{
				visible:false, 
				targets:[0, 1, 2, 3, 4,  8, 9, 10, 11, 12, 13, 14]
			}
		];

	
	var buttonsArray = [
    		{
    			titleAttr: "New",
    			text: "<i class='fas fa-plus'></i>",
    			className: "btn btn-sm",
    			action: function( e, dt, node, config ) {
    				editResourceCompetency(null, individualId);
    			}
    		},
    		{
    			attr: {
    				id: "promptDeleteResourceCompetencyBtn"
    			},
    			titleAttr: "Delete",
    			text: "<i class='fas fa-minus'></i>",
    			className: "btn btn-sm",
    			action: function() {
    				promptDeleteResourceCompetency();
    			}
    		}
    	];
	
	setDivVisibility("resourceCompetencyEmptyPanel", "none");
	setDivVisibility("resourceCompetencyPanel", "block");
	resourceCompetencyTable = initializeGenericTable("resourceCompetencyTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editResourceCompetency(rowSelector, individualId);
										},
										null,
										10,
										[[9,"asc"]], //Order by column 0 ascending, normally defaults to column 1 ascending
										null,       //tableHeightPixels (ignored if you have pagelength)
									    false,    //showTableInfo   (Showing 0 to 5 etc....)
										true   //showFilter												
	);

	
	resourceCompetencyTable.off('deselect');
	resourceCompetencyTable.on('deselect', function (e, dt, type, indexes) {
		updateResourceCompetencyToolbarButtons();
	} );
	
	resourceCompetencyTable.off('select');
	resourceCompetencyTable.on('select', function (e, dt, type, indexes) {
		updateResourceCompetencyToolbarButtons();
	} );	

	updateResourceCompetencyToolbarButtons();	
}
//showCompetencyTable -- End

//updateResourceCompetencyToolbarButtons 
function updateResourceCompetencyToolbarButtons() {
	var hasSelected = resourceCompetencyTable.rows('.selected').data().length > 0;

	setTableButtonState(resourceCompetencyTable, "promptDeleteResourceCompetencyBtn", hasSelected);	
}
//updateResourceCompetencyToolbarButtons -- End

//promptDeleteResourceCompetency -- Start
function promptDeleteResourceCompetency() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Resource Competency?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteResourceCompetency(resourceCompetencyTable);
			   }
	);
}
//promptDeleteResourceCompetency -- End

//deleteResourceCompetency
function deleteResourceCompetency(tbl) {
	var postUrl = "/rest/ignite/v1/individual-sd-role/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Resource Competency has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateResourceCompetencyToolbarButtons();
			});
}
//deleteResourceCompetency -- End

//humanResourceDialog -- Start
// editHumanResource -- Start
function editHumanResource(participantId, rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	var header = "Human Resource Detail";
	
	if (rowSelector != null) {
		data = humanResourceTable.row(rowSelector).data();
		enabled = true;
	}
	humanResourceTable.rows().deselect();
	//setActiveTab("generalLink");
	setElementEnabled("saveHumanResourceButton", false); //Disable Save button when initializing
	
	$("#erHumanResourceDialogHeader").html(header);
	
	$("#erNonProjectRelatedAgreementId").val(data.nonProjectRelatedAgreementId);
	$("#erParticipantIdPayer").val(participantId);
	$("#erParticipantNamePayer").val($("#epSystemName").val());
	
	var benName = "";
	if (data.hasOwnProperty("participantBeneficiary")) {
		if (data.participantBeneficiary != null) {
			benName = data.participantBeneficiary.systemName;
	
//			if (b.hasOwnProperty("individual")) {
//				if (b.individual != null) {
//					benName = data.individual.firstName + " " + data.individual.lastName;
//				}
//			}
		}
	}
	$("#erParticipantIdBeneficiary").val(data.participantIdBeneficiary);
	$("#erIndividualNameBeneficiary").val(benName);
	
	populateSelect("erResourceTypeId", 
		       "/rest/ignite/v1/resource-type", 
		       "resourceTypeId", 
		       "name", 
		       data.resourceTypeId, 
		       true,
		       null 
	);
	$("#erDescription").val(data.description);
	$("#erStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
	$("#erEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	
	// Do not allow updates if there are Remunerations linked to this human resource
	var hasRemunerationsLinked = false;
	
	if (hasRemunerationsLinked) {
		setElementEnabled("erBeneficiarySearchBtn", false);
	} else {
		setElementEnabled("erBeneficiarySearchBtn", true);
	}
	
	// Set the Save Button to disabled
	setElementEnabled("saveHumanResourceButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("humanResourceDialog");
	
}
//editHumanResource -- End

//saveHumanResourceDialog -- Start
function saveHumanResourceDialog() {
	
	var postUrl = "/rest/ignite/v1/non-project-related-agreement/new";
	var postData = {
			nonProjectRelatedAgreementId : $("#erNonProjectRelatedAgreementId").val(),
			participantIdPayer : $("#erParticipantIdPayer").val(),
			participantIdBeneficiary : $("#erParticipantIdBeneficiary").val(),
			resourceTypeId : $("#erResourceTypeId").val(),
			description : $("#erDescription").val(),
			startDate: getMsFromDatePicker("erStartDate"),
			endDate: getMsFromDatePicker("erEndDate")
	};
	
	var errors = "";
	
	if ((postData.participantIdBeneficiary == null) || (postData.participantIdBeneficiary == "")) {
		errors += "A Beneficiary is required as the human resource<br>";
	}
	
	if ((postData.resourceTypeId == null) || (postData.resourceTypeId == "")) {
		errors += "A Resource Type is required<br>";
	}
	
	if (showFormErrors("erDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.nonProjectRelatedAgreementId != null) && (postData.nonProjectRelatedAgreementId != "")) {
		var postUrl = "/rest/ignite/v1/non-project-related-agreement";
	}
	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	
	console.log(postUrl);
	console.log(postData);
	saveEntry(postUrl, postData, "humanResourceDialog", "The Human Resource information has been saved.", humanResourceTable);
}
//saveHumanResourceId -- End

// humanResourcepDialogChanged -- Start
function humanResourceDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveHumanResourceButton", true);
}
// humanResourceDialogChanged -- Start


//closeHumanResourceDialog -- Start
function closeHumanResourceDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("humanResourceDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("humanResourceDialog");
	};
	somethingChangedInDialog = false;

}
// closeHumanResourceDialog -- End

function editSelectBeneficiary() {
	selectBeneficiary("erParticipantIdBeneficiary", "erIndividualNameBeneficiary");
	humanResourceDialogChanged();
}



function selectBeneficiary(targetId, targetName) {
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
//humanResourceDialog -- Enda

//resourceRemunerationDialog -- Start
//editResourceRemuneration -- Start
function editResourceRemuneration(rowSelector, nonProjectRelatedAgreementId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = resourceRemunerationTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((nonProjectRelatedAgreementId == undefined) || (nonProjectRelatedAgreementId == null)){
			nonProjectRelatedAgreementId = data.nonProjectRelatedAgreementId;
		}
	}
	resourceRemunerationTable.rows().deselect();
	
	$("#rrResourceRemunerationId").val(data.resourceRemunerationId);
	$("#rrNonProjectRelatedAgreementId").val(nonProjectRelatedAgreementId);
	$("#rrDescription").val(data.description);
	
	/* function populateSelect(
	elementId, html select element that will be populated 
	url, url where it must get the data (you can paste in browser window to see the data)
	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
	displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	*/	
	$("#rrResourceRemunTypeId").val(data.resourceRemunTypeId);
	populateSelect("rrResourceRemunTypeId", 
		       "/rest/ignite/v1/resource-remun-type", 
		       "resourceRemunTypeId", 
		       "name", 
		       data.resourceRemunTypeId, 
		       true,
		       null 
	);
	
	$("#rrStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
	$("#rrEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));

	if ((data.amount == "") || (data.amount == null)) {
		$("#rrAmount").val("");
	} else {
		$("#rrAmount").val(formatDollar(data.amount)) //       formatMoney(data.amount, 2, ".", " "));	
	}
	
	
	$("#rrAllowExpenseDeductions").prop("checked", data.allowExpenseDeductions == "Y");
	
	// Set the Save Button to disabled
	setElementEnabled("saveResourceRemunerationBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("resourceRemunerationDialog");
}
//editResourceRemuneration -- End



//saveResourceRemunerationDialog -- Begin
function saveResourceRemunerationDialog() {
	
	$("#rrAmount").val($("#rrAmount").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	
	var postUrl = "/rest/ignite/v1/resource-remuneration/save-sp";
	
	var postData = {
			resourceRemunerationId: $("#rrResourceRemunerationId").val(),
			nonProjectRelatedAgreementId: $("#rrNonProjectRelatedAgreementId").val(),
			description: $("#rrDescription").val(),
			resourceRemunTypeId: $("#rrResourceRemunTypeId").val(),
			startDate: getMsFromDatePicker("rrStartDate"),
			endDate: getMsFromDatePicker("rrEndDate"),
			amount: $("#rrAmount").val(), 
			allowExpenseDeductions : $("#rrAllowExpenseDeductions").is(":checked") ? "Y" : "N",
	};

	var errors = "";
	
	// validate


	if (postData.startDate == null) {
		// Add Today's date without time
		$("#rrStartDate").val(timestampToString(new Date(), false));
		postData.startDate = getMsFromDatePicker("rrStartDate");
	}

	if ((postData.resourceRemunTypeId == null) || (postData.resourceRemunTypeId == "")) {
		errors += "A Remuneration Type is required.<br>";
	}
	
	if ((postData.amount == null) || (postData.amount == "")) {
		errors += "An Amount is required.<br>";
	}	

	if (isNaN(postData.amount)){
		errors += "The Amount is invalid.<br>";
	}
	if (postData.amount > 9999999999){
		errors += "The Amount is too large!<br>";
	}
	
	if (showFormErrors("rrDlgErrorDiv", errors)) {
		return;
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "resourceRemunerationDialog", "The Human Resource Remuneration has been saved.", resourceRemunerationTable);
}
//saveResourceRemunerationDialog -- End
//closeResoureceRemunerationDialog -- Start
function closeResourceRemunerationDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("resourceRemunerationDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("resourceRemunerationDialog");
	};
	somethingChangedInDialog = false;


}
//resourceRemunerationDialog -- End

//resourceRemunerationDialogChanged -- Start
function resourceRemunerationDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveResourceRemunerationBtn", true);
}
//resourceRemunerationDialogChanged -- End




//editResourceCompetency -- Start
function editResourceCompetency(rowSelector, individualId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = resourceCompetencyTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((individualId == undefined) || (individualId == null)){
			individualId = data.individualId;
		}
	}
	resourceCompetencyTable.rows().deselect();
	
	$("#isdrIndividualSdRoleId").val(data.individualSdRoleId);
	$("#isdrIndividualId").val(individualId);
	
	$("#isdrSdRoleId").val(data.sdRoleId);
	$("#isdrYearExperience").val(data.yearExperience);

	if (data.sdParentName == data.sdIndustryName) {
		$("#isdrSdParentName").val("");
	} else {
		$("#isdrSdParentName").val(data.sdParentName);
	}
	if (data.sdGrandParentName == data.sdIndustryName) {
		$("#isdrSdGrandParentName").val("");
	} else {
		$("#isdrSdGrandParentName").val(data.sdGrandParentName);
	}
	$("#isdrSdIndustryName").val(data.sdIndustryName);		
	
	$("#isdrSdRoleName").val(data.combinedName);
	
	/* function populateSelect(
	elementId, html select element that will be populated 
	url, url where it must get the data (you can paste in browser window to see the data)
	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
	displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	*/	
	$("#isdrCompetencyLevel").val(data.competencyLevelId);
	populateSelect("isdrCompetencyLevel", 
		       "/rest/ignite/v1/competency-level", 
		       "competencyLevelId", 
		       "name", 
		       data.competencyLevelId, 
		       true,
		       null 
	);
	

	// Set the Save Button to disabled
	setElementEnabled("saveResourceCompetencyBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("resourceCompetencyDialog");
}
//editResourceCompetency -- End

//saveResourceCompetencyDialog -- Begin
function saveResourceCompetencyDialog() {
	
	var postUrl = "/rest/ignite/v1/individual-sd-role/new";
	
	var postData = {
			individualSdRoleId: $("#isdrIndividualSdRoleId").val(),
			individualId: $("#isdrIndividualId").val(),
			sdRoleId: $("#isdrSdRoleId").val(),
			competencyLevelId: $("#isdrCompetencyLevel").val(),
			yearExperience: $("#isdrYearExperience").val()
	};

	var errors = "";
	
	// validate
	if ((postData.sdRoleId == null) || (postData.sdRoleId == "")) {
		errors += "Please select a Service Discipline Role.<br>";
	}
	
	if ((postData.competencyLevelId == null) || (postData.competencyLevelId == "")) {
		errors += "A Competency Level is required.<br>";
	}
	
	if ((postData.yearExperience == null) || (postData.yearExperience == "")) {
		errors += "Please enter the number of years Experience.<br>";
	} else {
		if (isNaN(postData.yearExperience)) {
			errors += "Please enter a valid number for years Experience.<br>";
		} else {
			if ((postData.yearExperience > 60) || (postData.yearExperience < 0)) {
				errors += "Please enter a valid number for years Experience.<br>";
			};
			if (postData.yearExperience % 1 === 0){
				   // yes it's an integer.
			} else {
				errors += "Please use an Integer for years Experience.<br>";
			};
			
		}
	}	
	
	if (showFormErrors("rcDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.individualSdRoleId != null) && (postData.individualSdRoleId != "")) {
		var postUrl = "/rest/ignite/v1/individual-sd-role";
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "resourceCompetencyDialog", "The Resource Competency has been saved.", resourceCompetencyTable);
}
//saveResourceCompetencyDialog -- End


//closeResourceCompetencyDialog
function closeResourceCompetencyDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("resourceCompetencyDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("resourceCompetencyDialog");
	};
	somethingChangedInDialog = false; 

}
//closeResourceCompetencyDialog -- End


function editSelectSdRole() {
	selectSdRole("isdrSdRoleId", "isdrSdRoleName", "isdrSdParentName", "isdrSdGrandParentName", "isdrSdIndustryName");
}


function selectSdRole(targetId, targetName, targetParentName, targetGrandParentName, targetIndustryName) {    //This is also called from competencyTab
	
	var queryUrl="/rest/ignite/v1/sd-role/all-from-view";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="sdRoleId";
			var refColumnName="combinedName";
			var columns = [
				{ data: "sdRoleId", name: "sdRoleId" },
				{ data: "sdIndustryName", name: "Industry" },
				{ data: "sdParentName", name: "" },
				{ data: "combinedName", name: "SD and Role" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.sdRoleId;
				var repName = row.combinedName;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				
				if (row.sdParentName == row.sdIndustryName) {
					$("#" + targetParentName).val("");
				} else {
					$("#" + targetParentName).val(row.sdParentName);
				}
				if (row.sdGrandParentName == row.sdIndustryName) {
					$("#" + targetGrandParentName).val("");
				} else {
					$("#" + targetGrandParentName).val(row.sdGrandParentName);
				}
				$("#" + targetIndustryName).val(row.sdIndustryName);
				
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
} //selectSdRole  --  End

//resourceCompetencyDialogChanged -- Start
function resourceCompetencyDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveResourceCompetencyBtn", true);
}
//resourceCompetencyDialogChanged -- End
//resourceCompetencyDialog -- End


