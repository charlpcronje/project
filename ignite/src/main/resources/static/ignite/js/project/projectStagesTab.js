var unselectedStagesTable = null;
var selectedStagesTable = null;
var projectSdStageTable = null;

// *********************************************************
// Project Stages Start
function initializeProjectStagesTab(projectId) { 

	showEmptyProjectStagesSdPanel();

	var columnsArray = [
   		{ data: "projectStageId" },		 //0
		{ data: "projectId" },			 //1
		{ data: "orderNumber" },		 //2
		{ data: "stageName" },			 //3
		{ data: "description"},			 //4
		{ data: "startDate"},			 //5
		{ data: "endDate"}			 	 //6
	];
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 4]
		},
		{
		render: function(data, type, row) {
			var html = data;
			if (type == "display") {
				html = timestampToString(data, false);
			}
			return html;
		},
			targets: [5, 6]
		},

		{
			width: "50%",
			targets: [0]
		},
		{
			width: "50%",
			targets: [3]
		},
	];
	
	var buttonsArray = [
	            		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editProjectStage(null);
			}
		},
		{
			attr: {
				id: "promptDeleteProjectStageBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteProjectStage();
			}
		}
	]
	
	var queryUrl="/rest/ignite/v1/project/project-stages/" + projectId;
	
	projectStageTable = initializeGenericTable("projectStageTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editProjectStage(rowSelector);
										},
										null,
										25,
										[[2,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	projectStageTable.off('deselect')
	projectStageTable.on('deselect', function (e, dt, type, indexes) {
		updateProjectStageToolbarButtons();
		showEmptyProjectStagesSdPanel();

		// showEmpty??Panel();
		// showEmpty??Panel();
	} );

	projectStageTable.off('select')
	projectStageTable.on('select', function (e, dt, type, indexes) {
		updateProjectStageToolbarButtons();
		//intialize??UnselectedStageTable(dt.data());
		
		intializeProjectSdStagesTable(dt.data());
	} );
	updateProjectStageToolbarButtons();
}


function showEmptyProjectStagesSdPanel() {
	setDivVisibility("emptyProjectStagesSdPanel", "block");
	setDivVisibility("projectStagesSdPanel", "none");
}


function updateProjectStageToolbarButtons() {
	var hasSelected = projectStageTable.rows('.selected').data().length > 0;
	
	setTableButtonState(projectStageTable, "promptDeleteProjectStageBtn", hasSelected);	
}

function promptDeleteProjectStage() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Project Stage?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteProjectStage(projectStageTable);
			   }
	);
}

function deleteProjectStage(tbl) {
	var postUrl = "/rest/ignite/v1/project-stage/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Stage has been deleted.", tbl,
			function(){	
			},
			updateProjectStageToolbarButtons()
	);

}


// Project Stage Tab -- End
// ***********************************************************************
// ***********************************************************************


// ***********************************************************************
// ***********************************************************************
// Project Stage Dialog -- Start


var somethingChangedInDialog = null;
var askToSaveDialog = null;

//editProjectStage -- Start
function editProjectStage(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var projectId = $("#pdProjectId").val();
	var errors = "";
	
	if (rowSelector != null) {
		data = projectStageTable.row(rowSelector).data();
	} 
	
	projectStageTable.rows().deselect();
	
	$("#psProjectId").val(projectId);
	
	$("#psOrderNumber").val(data.orderNumber);
	$("#psProjectStageId").val(data.projectStageId);
	$("#psStageName").val(data.stageName);
	
	
	var projectStartDate = getMsFromDatePicker("pdProjectStartDate");
	$("#psStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(projectStartDate), false) : new Date(data.startDate));
	$("#psEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	
	// Set the Save Button to disabled
	setElementEnabled("saveProjectStageButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("projectStageDialog");
	
}

//editProjectStage -- End

function projectStageDialogChanged() {
	setElementEnabled("saveProjectStageButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function closeProjectStageDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("projectStageDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("projectStageDialog");
	}
}

//saveProjectStage -- Begin
function saveProjectStage() {
	
	var postUrl = "/rest/ignite/v1/project-stage/new";
	var postData = {
			projectStageId: $("#psProjectStageId").val(), 
			projectId: $("#pdProjectId").val(),
			orderNumber: $("#psOrderNumber").val(),
			stageName: $("#psStageName").val(), 
			description: $("#psStageDescription").val(),
			startDate: getMsFromDatePicker("psStartDate"),
			endDate: getMsFromDatePicker("psEndDate"),
	};
	
	var errors = "";
	
	// validate
	if (postData.stageName == "") {
		errors += "A Stage is required<br>";
	}
	
	if (showFormErrors("psDlgErrorDiv", errors)) {
		return;
	}
	
	
	if ((postData.projectStageId != null) && (postData.projectStageId != "")){
		var postUrl = "/rest/ignite/v1/project-stage";
	}
	
	saveEntry(postUrl, postData, "projectStageDialog", "The Project Stage has been saved.", projectStageTable);
	
}
//saveProjectStage -- End


//start
function intializeProjectSdStagesTable(projectStageData) {
	
	setDivVisibility("emptyProjectStagesSdPanel", "none");
	setDivVisibility("projectStagesSdPanel", "block");

	var projectStageId = projectStageData.projectStageId;
	
	var queryUrl="/rest/ignite/v1/project/project-sds/" + projectStageId; 
	
	var columnsArray = [
   		{ data: "serviceDisciplineId" },		//0
   		{ data: "industrySdName" },		 		//1
   		{ data: "parentName" },		 			//2
   		{ data: "serviceDisciplineCode" },		//3
		{ data: "sdName" }						//4
	];
	
	var columnDefsArray = [
	    {
			visible: false,
			targets: [0]
		}
	    /* ,
		{
			width: "50%",
			targets: [1, 2]
		} */
	];
	
	var buttonsArray = [
	]         		
            		
	
	projectStagesSdTable = initializeGenericTable("projectStagesSdTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										},
										null,
										25,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);
	
}

