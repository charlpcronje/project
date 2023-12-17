var projectSdTabTable = null;  
var sdAssignedResourcesTable = null;  
var projectSdStageTable = null;  
//var projectSdTable = null;  
//var sdsNotSelectedTable = null;  

// *********************************************************
// Project Service Disciplines Start
function initializeProjectSdTab(projectId) {

	showEmptySdAssignedResourcesPanel();
	showEmptyProjectSdStagePanel();

	var queryUrl="/rest/ignite/v1/project/sd/list/" + projectId;

	var columnsArray = [
    		{ data: "rowNumber" },						//0
    		{ data: "projectSdId" },					//1
    		{ data: "serviceDisciplineIdIndustry" },	//2
    		{ data: "industrySdName" },					//3 --
    		{ data: "parentName" },						//4 --
    		{ data: "level" },							//5 
    		{ data: "serviceDisciplineCode" },							//6 --
    		{ data: "serviceDisciplineName" },							//7 --
       		{ data: "serviceDisciplineId" }							//8
	];
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 5, 8]
		}
//		,
//		{
//			width: "25%",
//			targets: 3
//		}
	];
	
	var buttonsArray = [
	            		{
	            			titleAttr: "Edit Service Disciplines",
	            			text: "<i class='fas fa-ellipsis-h'></i>",
	            			className: "btn btn-sm",
	            			action: function( e, dt, node, config ) {
	            				editProjectSds();
	            			}
	            		}
	            		]
	
	projectSdTabTable = initializeGenericTable("projectSdTabTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										30,
										[[0,"asc"]], //Order by column 0 ascending, normally defaults to column 1 ascending
										"",
										true,
										true
	);

	projectSdTabTable.off('deselect')
	projectSdTabTable.on('deselect', function (e, dt, type, indexes) {
		showEmptySdAssignedResourcesPanel();
		showEmptyProjectSdStagePanel();
	} );

	projectSdTabTable.off('select')
	projectSdTabTable.on('select', function (e, dt, type, indexes) {
		intializeSdResourcesTable(dt.data());
		intializeProjectSdStage(dt.data());
	} );
	
}

//Participant Children -- Start
function showEmptySdAssignedResourcesPanel() {
	setDivVisibility("emptySdAssignedResourcesPanel", "block");
	setDivVisibility("sdAssignedResourcesPanel", "none");
}

	
function intializeSdResourcesTable(projectSdRow) {
	if (projectSdRow == null) {
		return;
	}
	
	var projectSdId = projectSdRow.projectSdId;
	
	setDivVisibility("emptySdAssignedResourcesPanel", "none");
	setDivVisibility("sdAssignedResourcesPanel", "block");

	var columnsArray = [
		{ data: "projectParticipantId" },			//0
		{ data: "projectParticipantSdRoleId" },		//1
		{ data: "participantIdBeneficiary" },		//2
		{ data: "systemNameBeneficiary" },			//3
		{ data: "sdCode" },							//4
		{ data: "sdName" },							//5
		{ data: "roleOnAProjectId" },				//6
		{ data: "roleOnAProjectName" },				//7
		{ data: "projectSdId" }						//8
	];
	
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0, 1, 2, 4, 6, 8]
	}
	
	];

	var buttonsArray = [];

	var queryUrl =  "/rest/ignite/v1/project/sd/assigned-resources/" + projectSdId;

	sdAssignedResourcesTable = initializeGenericTable("sdAssignedResourcesTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
			                            null,
			                            30
	);
	
}

function showEmptyProjectSdStagePanel() {
	setDivVisibility("emptyProjectSdStagePanel", "block");
	setDivVisibility("projectSdStagePanel", "none");
}

function intializeProjectSdStage(projectSdRow) {
	if (projectSdRow == null) {
		return;
	}
	
	var projectSdId = projectSdRow.projectSdId;
	
	setDivVisibility("emptyProjectSdStagePanel", "none");
	setDivVisibility("projectSdStagePanel", "block");

	var columnsArray = [
		{ data: "projectSdStageId" },		//0
		{ data: "projectId" },				//1
		{ data: "projectNameText" },		//2
		{ data: "projectSdId" },			//3
		{ data: "serviceDisciplineCode" },	//4
		{ data: "sdName" },					//5
		{ data: "orderNumber" },			//6
		{ data: "stageName" }				//7
	];
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0,1,2,3,4,5]
	},
	
	{
		width: "20%",  // "30px",    //maw so klein as moontlik     BAnk Statement
		targets: 6
	}	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editProjectSdStage(null, projectSdRow);
			}
		},
		{
			attr: {
				id: "promptDeleteProjectSdStageBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteProjectSdStage();
			}
		}
	];
	
	var queryUrl =  "/rest/ignite/v1/project/sd/project-stages/" + projectSdId;

	projectSdStageTable = initializeGenericTable(
										"projectSdStageTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) { // callbackMethod
											editProjectSdStage(rowSelector, projectSdRow);
										},
			                            null, 		// completedMethod
			                            30, 		// pageLength
			                            [6,"asc"],		// orderArray
										null,		// tableHeightPixels
										true,		// showTableInfo
										false		// showFilter
	);
	
	projectSdStageTable.off('deselect')
	projectSdStageTable.on('deselect', function (e, dt, type, indexes) {
		updateProjectSdStageToolbarButtons();
		
	} );

	projectSdStageTable.off('select')
	projectSdStageTable.on('select', function (e, dt, type, indexes) {
		updateProjectSdStageToolbarButtons();
		
	} );
	
	updateProjectSdStageToolbarButtons();
	
}

function updateProjectSdStageToolbarButtons() {
	var hasSelected = projectSdStageTable.rows('.selected').data().length > 0;

	setTableButtonState(projectSdStageTable, "promptDeleteProjectSdStageBtn", hasSelected);	
}
	
function promptDeleteProjectSdStage() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Stage for the Service Discipline?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteProjectSdStage(projectSdStageTable);
			   }
	);
}

function deleteProjectSdStage(tbl) {
	var postUrl = "/rest/ignite/v1/project-sd-stage/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Service Discipline Stage has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateProjectSdStageToolbarButtons();
			}
	);
}



// Project Sd Tab -- End
// ***********************************************************************
// ***********************************************************************



// ***********************************************************************
// ***********************************************************************
//editProjectSdStage -- Start
function editProjectSdStage (rowSelector, projectSdRow) {

	var data = {}; 
	var errors = "";
		
	if (rowSelector != null) {
		data = projectSdStageTable.row(rowSelector).data();
	} else {
		data.serviceDisciplineCode = projectSdRow.sdCode;
		data.sdName = projectSdRow.sdName;
		data.projectSdId = projectSdRow.projectSdId;
		data.projectId = projectSdRow.projectId;
	}
	
	projectSdStageTable.rows().deselect();
	//$("#psdsSdName").val(data.serviceDisciplineCode + " " + data.sdName); 
	$("#psdsSdName").val(data.sdName); 
//	$("#psdsSdCode").val(data.serviceDisciplineCode); 
	$("#psdsProjectStageId").val(data.projectStageId);
	$("#psdsStageName").val(data.stageName);
	$("#psdsProjectSdId").val(data.projectSdId);
	$("#psdsProjectId").val(data.projectId);

	
	// Set the Save Button to disabled
	setElementEnabled("saveProjectSdStageDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("projectSdStageDialog");
}
//editProjectSdStage -- End


//editSelectProjectStage -- Start
function editSelectProjectStage() {
	
	var projectId = $("#psdsProjectId").val();
	var projectSdId = $("#psdsProjectSdId").val();
	
	var queryUrl =  "/rest/ignite/v1/project/project-stages-remaining/" + projectId + "/" + projectSdId;

	var targetId = "psdsProjectStageId";
	var targetName = "psdsStageName";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName = "projectStageId";
			var refColumnName = "projectStageId";
			var columns = [
				{ data: "projectStageId", name: "Id" },
				{ data: "projectId", name: "Project Id" },
				{ data: "orderNumber", name: "Order Number" },
				{ data: "stageName", name: "Stage Name" },
				{ data: "description", name: "Description" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: 1
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectStageId;
				var stageName = row.stageName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(stageName);
				$("#" + targetName).trigger("change");
			}, "projectSdStageDialog", targetId, targetName, "IP");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectProjectStage -- End



//saveProjectSdStageDialog -- Start
function saveProjectSdStageDialog() {

	var postUrl = "/rest/ignite/v1/project-sd-stage/new";

	var postData = {
			projectSdStageId: $("#psdsProjectSdStageId").val(),
			projectSdId: $("#psdsProjectSdId").val(),
			projectStageId: $("#psdsProjectStageId").val()

	};
	var errors = "";

	if ((postData.projectStageId == null) || (postData.projectStageId == "")) {
		errors += "A Project Stage is required.<br>";
	}
	
	if (showFormErrors("psdsDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.projectSdStageId != null) && (postData.projectSdStageId != "")){
		var postUrl = "/rest/ignite/v1/project-sd-stage";
	}

	saveEntry(postUrl, postData, "projectSdStageDialog", "The Project SD Stage has been saved.", projectSdStageTable);
}
//saveProjectSdStageDialog -- End




function psdsDialogChanged() {
	setElementEnabled("saveProjectSdStageDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeProjectSdStageDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("projectSdStageDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("projectSdStageDialog");
	}
}
//**********************************************************

var projectSdTable = null;
var sdsNotSelectedTable = null;

// *********************************************************
// Project Service Disciplines Start
// edit Project Sds -- Start

function editProjectSds() {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	var header = "Service Disciplines: " + projectHeader;

	var projectId = $("#pdProjectId").val();

	initializeProjectSdTable(projectId);
	initializeSdsNotSelectedTable(projectId);
	projectSdTabTable.rows().deselect();

	// Set the Save Button to disabled
	setElementEnabled("saveProjectSdButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("projectSdDialog");
}
// edit Project Sds -- End

//close Project Sds -- End



// Project Sd Table -- Start
function initializeProjectSdTable(projectId) {
	var queryUrl = "/rest/ignite/v1/project/sd/list/" + projectId;

	console.log(queryUrl);

	var columnsArray = [
	                    
		{ data: "rowNumber" },						//0
		{ data: "projectSdId" },					//1
		{ data: "serviceDisciplineIdIndustry" },	//2
		{ data: "industrySdName" },	//3 --
		{ data: "parentName" },						//4 --
		{ data: "level" },							//5 
		{ data: "serviceDisciplineCode" },							//6 --
		{ data: "serviceDisciplineName" },							//7 --
   		{ data: "serviceDisciplineId" }							//8
		
	                    
	];
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,5,8]
		}
//		{
//			width: "45%",
//			targets: 1
//		},
//		{
//			width: "10%",
//			targets: 2
//		},
//		{
//			width: "45%",
//			targets: 4
//		}
	];

	var buttonsArray = [
		{
			attr: {
				id: "promptDeleteProjectSdBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-arrow-right'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				// promptDeleteProjectSd();  We do not prompt, just remove the Sd if possible
				deleteProjectSd(projectSdTable, null);
			}
		}
	];


	projectSdTable = initializeGenericTable("projectSdTable",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			//promptDeleteProjectSd();
			deleteProjectSd(projectSdTable, rowSelector)
		},
		null,
		30,
		[[0, "asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	projectSdTable.off('deselect')
	projectSdTable.on('deselect', function(e, dt, type, indexes) {
		updateProjectSdToolbarButtons();
	});

	projectSdTable.off('select')
	projectSdTable.on('select', function(e, dt, type, indexes) {
		updateProjectSdToolbarButtons();
	});

	updateProjectSdToolbarButtons();
}

function updateProjectSdToolbarButtons() {
	var hasSelected = projectSdTable.rows('.selected').data().length > 0;

	setTableButtonState(projectSdTable, "promptDeleteProjectSdBtn", hasSelected);
}

function deleteProjectSd(tbl, rowSelector) {
	var postUrl = "/rest/ignite/v1/project/delete-sd";
	var errors = "";

	var row = {};
	if (rowSelector == null) {
		row = projectSdTable.rows('.selected').data()[0];
	} else {
		row = projectSdTable.row(rowSelector).data();
	}

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Service Discipline has been removed.", tbl,
		function() {
			tbl.rows().deselect();
			updateProjectSdToolbarButtons();
			//Reload sdsNotSelectedTable also
			if (sdsNotSelectedTable != null) {
				sdsNotSelectedTable.ajax.reload();
				sdsNotSelectedTable.rows().deselect();
				updateSdsNotSelectedToolbarButtons();
			}
		}
	);
}
// * Project Sds table End


// * Sd's not selected for the project yet Start
function initializeSdsNotSelectedTable(projectId) {
	var queryUrl = "/rest/ignite/v1/project/sd/not-selected/" + projectId;

	console.log(queryUrl);
	var columnsArray = [

		{ data: "serviceDisciplineIdIndustry" },	//0
		{ data: "industrySdName" },	//1
		{ data: "parentSdName" },					//2
		{ data: "level" },							//3
		{ data: "serviceDisciplineCode" },			//4
		{ data: "serviceDisciplineName" },			//5
		{ data: "rowOrderNo" },						//6
		{ data: "serviceDisciplineId" }				//7
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,3, 6, 7]
		}
//		{
//			width: "45%",
//			targets: 0
//		},
//		{
//			width: "10%",
//			targets: 1
//		},
//		{
//			width: "45%",
//			targets: 3
//		}
	];


	var buttonsArray = [
		{
			attr: {
				id: "addSdToProjectBtn"
			},
			titleAttr: "New",
			text: "<i class='fas fa-arrow-left'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				addSdToProject(sdsNotSelectedTable, projectId, null);
			}
		}
	];

	sdsNotSelectedTable = initializeGenericTable("sdsNotSelectedTable",
		queryUrl,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			addSdToProject(sdsNotSelectedTable, projectId, rowSelector);
		},
		null,
		30,
		[[4, "asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	sdsNotSelectedTable.off('deselect')
	sdsNotSelectedTable.on('deselect', function(e, dt, type, indexes) {
		updateSdsNotSelectedToolbarButtons();
	});

	sdsNotSelectedTable.off('select')
	sdsNotSelectedTable.on('select', function(e, dt, type, indexes) {
		updateSdsNotSelectedToolbarButtons();
	});

	updateSdsNotSelectedToolbarButtons();
}

function updateSdsNotSelectedToolbarButtons() {
	var hasSelected = sdsNotSelectedTable.rows('.selected').data().length > 0;

	setTableButtonState(projectSdTable, "addSdToProjectBtn", hasSelected);
}

function addSdToProject(sdsNotSelectedTable, projectId, rowSelector) {
	var postUrl = "/rest/ignite/v1/project/sd/new";
	var data = {};
	if (rowSelector == null) {
		data = sdsNotSelectedTable.rows('.selected').data()[0];
	} else {
		data = sdsNotSelectedTable.row(rowSelector).data();
	}
	var errors = "";

	var postData = {
		projectId: projectId,
		serviceDisciplineId: data.serviceDisciplineId
	};
	saveEntry(postUrl, postData, null, "The Sd has been added to the Project.", projectSdTable,
		function() {
			projectSdTable.rows().deselect();
			updateProjectSdToolbarButtons();
			//Reload sdsNotSelectedTable also
			if (sdsNotSelectedTable != null) {
				sdsNotSelectedTable.ajax.reload();
				sdsNotSelectedTable.rows().deselect();
				updateSdsNotSelectedToolbarButtons();
			}
		}
	);
}

function projectSdDialogChanged() {
	setElementEnabled("saveProjectSdButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeProjectSdDialog() {
	var projectIdLocal =  $("#pdProjectId").val();

	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("projectSdDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("projectSdDialog");
	}
	var requestUrl="/rest/ignite/v1/project/sd/list/" + projectIdLocal;
	console.log (requestUrl);
	projectSdTabTable.ajax.url(springUrl(requestUrl)).load(); 
}

//close Project Sds -- End



// * Project Sds table End
// edit Project Sds End
// **********************************************************************