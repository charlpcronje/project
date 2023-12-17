// project.js //
var projectTable = null;
var showOnlyMyProjects = true;
var showOnlySusProjects = false;
var participantId = null;

var showSustenanceProj = false;

//*********************************************************
// initializeProjectTable -- Start

function initializeProjectTable() {

	var queryUrl;
	var columnsArray = [
		{ data: "projectId" },						//0
		{ data: "participantIdHost" },				//1
		{ data: "projectNameText" },				//2 --
		{ data: "projectNumberText" },				//3 
		{ data: "participantNameHost" },			//4 --
		{ data: "participantNameLevel1" },			//5 --
		{ data: "description" },					//6
		{ data: "projectIdParent" },				//7
		{ data: "subProjNumber" },					//8
		{ data: "individualIdProjectAdmin" },		//9
		{ data: "individualNameProjectAdmin" },		//10 --
		{ data: "isActive" },						//11 --
		{ data: "flagSustenanceProject" },			//12 -- 
		{ data: "startDate" },						//13
		{ data: "projectStageCurrent" },			//14
		{ data: "participantIdLevel1" },			//15 
		{ data: "projectParticipantIdLevel1" }		//16
	];

	var columnDefsArray = [
		{
			visible: false,
//			targets: [0, 1, 3, 5, 6, 8, 12, 13, 14]
			targets: [0, 1, 3, 6, 7, 8, 9, 13, 14, 15,16]
		},
		{
			width: "50%", //Project Title
			targets: 2
		},
		{
			width: "15%", //Host, Top Level participant
			targets: [4,5]
		},
		{
			width: "15%", //individualNameProjectAdmin
			targets: [10]
		},		
		{
			width: "5%", //isActive
			targets: [11]
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActive == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 11
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.flagSustenanceProject == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 12
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fa-solid fa-plus'></i>",
			
			// <i class="fa-brands fa-tiktok"></i>
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editProject(null);
			}
		},
		{
			attr: {
				id: "promptDeleteProjectBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fa fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteProject();
			}
		},
		{
			attr: {
				id: "cloneProjectBtn"
			},
			titleAttr: "Clone Project",
			text: "<i class='fa fa-clone'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				cloneProject();
			}
		},
		{
			attr: {
				id: "showMyProjectsBtn"
			},
			titleAttr: "Show all Projects",
			text: "<i class='fa fa-users'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showOnlyMyProjects = !showOnlyMyProjects;
				showMyProjectsOrNot(showOnlyMyProjects, showOnlySusProjects);
			}
		}
		,
		{
			attr: {
				id: "showMySustenanceProjectsBtn"
			},
			titleAttr: "Show Sustenance Projects",
			text: "<i class = 'fa-solid fa-user-gear'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showOnlySusProjects = !showOnlySusProjects;
				showMyProjectsOrNot(showOnlyMyProjects, showOnlySusProjects);
			}
		}
	];
	
	if (showOnlyMyProjects == false) {
		if (showOnlySusProjects == false) {
			queryUrl = "/rest/ignite/v1/project/all-non-sustenance-projects";
		} else {
			queryUrl = "/rest/ignite/v1/project/all-sustenance-projects";
		}
	} else {	
		if (showOnlySusProjects == false) {
			queryUrl = "/rest/ignite/v1/project/list-only-my-projects/" + participantId;
		} else {
			queryUrl = "/rest/ignite/v1/project/list-only-my-projects-sustenance/" + participantId;
		}
	}

	console.log(queryUrl);
	projectTable = initializeGenericTable("projectTable",
								queryUrl,
								columnsArray,
								columnDefsArray,
								buttonsArray,
								function(rowSelector) {
									editProject(rowSelector);
								},
								null,
								40,
								[[2, "asc"]], //Order by column 0 ascending, normally defaults to column 1 ascending
			        			null,			// tableHeightPixels (pageLength x 35)
			        			null,			// showTableInfo  // true by default
			        			true			// showFilter  // true by default
			        			//,"projectTable"	 //use tablename for the Toolbar.
			        			
	);

	if (showOnlyMyProjects == false) {
		if (showOnlySusProjects == false) {
			 $("#projectsPageHeader843").html('Non-Sustenance Projects');
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Non-Sustenance Projects</b></span>';	//Text for the Toolbar	
		} else {
			 $("#projectsPageHeader843").html('Sustenance Projects');
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Sustenance Projects</b></span>';	//Text for the Toolbar	
		}
	} else {	
		if (showOnlySusProjects == false) {
			 $("#projectsPageHeader843").html('Non-Sustenance Projects linked to ' + $("#projMyName").val());
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Non-Sustenance Projects linked to ' + $("#projMyName").val() + '</b></span>';	//Text for the Toolbar	
		} else {
			 $("#projectsPageHeader843").html('Sustenance Projects linked to ' + $("#projMyName").val());
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Sustenance Projects of ' + $("#projMyName").val() + '</b></span>';	//Text for the Toolbar	
		}
	}
	
//	document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>All Animals for </b></span>';	//Text for the Toolbar		
	
	projectTable.off('deselect')
	projectTable.on('deselect', function(e, dt, type, indexes) {
		updateProjectToolbarButtons();
	});

	projectTable.off('select')
	projectTable.on('select', function(e, dt, type, indexes) {
		updateProjectToolbarButtons();
	});
	updateProjectToolbarButtons();
}


//++++ when using a new page, rather than a dialog
function editProject(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var projectId = -1; // -1 will indicate insert
	
	if (rowSelector != null) {
		
		data = projectTable.row(rowSelector).data();
		projectId = data.projectId;
	}
	// Add the following to AppController
	url = springUrl("/project-edit?id=") + projectId;
	window.location = url;
}
//++++


function updateProjectToolbarButtons() {
	var hasSelected = projectTable.rows('.selected').data().length > 0;

	setTableButtonState(projectTable, "promptDeleteProjectBtn", hasSelected);
	setTableButtonState(projectTable, "cloneProjectBtn", hasSelected);
	
}

function promptDeleteProject() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Project?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteProject(projectTable);
		}
	);
}

function deleteProject(tbl) {
	var postUrl = "/rest/ignite/v1/project/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project has been deleted.", tbl,
		function() {
			tbl.rows().deselect();
			updateProjectToolbarButtons
		}
	);
};




function showMyProjectsOrNot(showOnlyMyProjects, showOnlySusProjects) {

	var requestUrl;

	if (showOnlyMyProjects == false) {
		$("#showMyProjectsBtn").html("<i class='fas fa-user'></i>");	
		$("#showMyProjectsBtn").prop("title", "Show Projects I am linked to");
		if (showOnlySusProjects == false) {
			requestUrl = "/rest/ignite/v1/project/all-non-sustenance-projects";
			$("#showMySustenanceProjectsBtn").html("<i class='fa-solid fa-user-gear'></i>");	
			$("#showMySustenanceProjectsBtn").prop("title", "Show Sustenance Projects");
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Non-Sustenance Projects</b></span>';
			$("#projectsPageHeader843").html('Non-Sustenance Projects');
		} else {
			requestUrl = "/rest/ignite/v1/project/all-sustenance-projects";
			$("#showMySustenanceProjectsBtn").html("<i class='fa-solid fa-chalkboard-user'></i>");	
			$("#showMySustenanceProjectsBtn").prop("title", "Show Non-Sustenance Projects");	
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Sustenance Projects</b></span>';
			$("#projectsPageHeader843").html('Sustenance Projects');
		}
	} else {	
		$("#showMyProjectsBtn").html("<i class='fas fa-users'></i>");	
		$("#showMyProjectsBtn").prop("title", "Show all Projects");
		if (showOnlySusProjects == false) {
			requestUrl = "/rest/ignite/v1/project/list-only-my-projects/" + participantId;
			$("#showMySustenanceProjectsBtn").html("<i class='fa-solid fa-user-gear'></i>");		
			$("#showMySustenanceProjectsBtn").prop("title", "Show Sustenance Projects");	
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Non-Sustenance Projects linked to ' + $("#projMyName").val() + '</b></span>';
			$("#projectsPageHeader843").html('Non-Sustenance Projects linked to ' + $("#projMyName").val());
		} else {
			requestUrl = "/rest/ignite/v1/project/list-only-my-projects-sustenance/" + participantId;
			$("#showMySustenanceProjectsBtn").html("<i class='fa-solid fa-chalkboard-user'></i>");
			$("#showMySustenanceProjectsBtn").prop("title", "Show Non-Sustenance Projects");	
			//document.querySelector('div.projectTable').innerHTML = '<span style="float:left"><b>Sustenance Projects of ' + $("#projMyName").val() + '</b></span>';
			$("#projectsPageHeader843").html('Sustenance Projects of ' + $("#projMyName").val());
		}
	}

	projectTable.ajax.url(springUrl(requestUrl)).load();  //NB Call springUrl to set the context path on the production server when accessing urls. (Locally will work)
	projectTable.rows().deselect();
};



//
//function showSustenanceProjects(showSustenanceProj) {
//
//	var requestUrl;
//
//	if (showSustenanceProj == false) {
//		requestUrl = "/rest/ignite/v1/project/list-all-non-sus-projects";
//	} else {
//		requestUrl = "/rest/ignite/v1/project/list-only-sustenance-projects";
//	}
//
//	projectTable.ajax.url(springUrl(requestUrl)).load();  //NB Call springUrl to set the context path on the production server when accessing urls. (Locally will work)
//	projectTable.rows().deselect();
//};

// initializeProjectTable -- End
// *********************************************************

// *********************************************************
// cloneProject -- Start
var somethingChangedInDialog = null;
var askToSaveDialog = null;


function getLatestProjectNumber(participantHostId) {
	var queryUrl;
	queryUrl = "/rest/ignite/v1/project/next-project-number/" + participantHostId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#cloneProjectNumberText").val(leadingZeroPad(data + 1,4));
			$("#cloneProjectNumberBigInt").val(data + 1);
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}



function cloneSubOrParentChanged() {

	var cloneParentProjectToo = $("#pcCloneSubProjectOnly").is(":checked") ? false : true;

	// pcCloneExplain
	
	$("#cloneProjectNumberText").prop("readonly", !cloneParentProjectToo );	
	$("#cloneProjectTitle").prop("readonly", !cloneParentProjectToo );	
	
	if (cloneParentProjectToo) {
		var hostId = $("#pcParticipantIdHost").val();
		var nextProjectNumber = null; 
		nextProjectNumber = getLatestProjectNumber(hostId);
		$("#cloneProjectNumberText").val(nextProjectNumber); 
		$("#cloneSubProjNumber").val($("#pcSubProjNumber").val());
		$("#pcCloneExplain").html("New Parent and Sub Project will be created");	
		
	} else {
		$("#cloneProjectNumberText").val($("#pcProjectNumberText").val());
		$("#cloneProjectTitle").val($("#pcProjectTitle").val());
		$("#cloneProjectNumberBigInt").val($("#pcProjectNumberText").val()); 
		$("#cloneSubProjNumber").val("");
		$("#pcCloneExplain").html("Sub-Project will be created under the current Parent Project Number");	
		
	}
}






//	var postData = {
//		projectId: $("#pcProjectId").val(),
//		projectIdParent: $("#pdProjectIdParent").val(),
//		participantIdLevel1: $("#gParticipantIdLevel1").val(),
//		projectParticipantIdLevel1: $("#gProjectParticipantIdLevel1").val(),
//		participantIdHost: $("#gParticipantIdHost").val(),
//		projectNumberBigInt: $("#gProjectNumberBigInt").val(),
//		projectNumberText: $("#gProjectNumberText").val(),
//		projectNameText: (paddy($("#gProjectNumberText").val(), 4) + " - " + $("#gProjectNumberText").val() + " - " + $("#pdProjectTitle").val() + " - " + $("#gSubProjNumber").val()),
//		title: $("#pdProjectTitle").val(),
//		subProjNumber: $("#gSubProjNumber").val(),
//		description: $("#pdProjectDescription").val(),
//		individualIdProjectAdmin: $("#pdIndividualIdProjectAdmin").val(),
//		isActive: $("#pdIsActive").is(":checked") ? "Y" : "N",
//		startDate: getMsFromDatePicker("pdProjectStartDate"),
//		completionDate: getMsFromDatePicker("pdProjectCompletionDate")
//	};
//
//	var errors = "";
//	var postUrl = "";
//
//	// validate
//	if (postData.participantIdLevel1 == "") {
//		errors += "Please select the level 1 Contracting Participant<br>";
//	}
//
//	if (postData.participantIdHost == "") {
//		errors += "Please select the Host Participant<br>";
//	}
//if ((postData.startDate == "") || (postData.startDate == null)) {
//		// Add Today's date without time
//		$("#pdProjectStartDate").datepicker("setDate", data.startDate == null ? timestampToString(new Date(), false) : new Date(data.startDate));
//		postData.startDate = getMsFromDatePicker("pdProjectStartDate");
//	}
//	if ((postData.completionDate == "") || (postData.completionDate == null)) {
//		postData.completionDate = null;
//	}
//
////	if ((postData.projectNumberBigInt != "") && (postData.projectNumberBigInt != null)) {
////		if (isNaN(postData.projectNumberBigInt)) {
////			errors += "The Project Number has to be numeric<br>";
////		}
////	} else {
////		errors += "The Project Number is required<br>";
////	}
//
//	if ((postData.projectNumberText != "") && (postData.projectNumberText != null)) {
//		if (isNumber(postData.projectNumberText)) {
//			postData.projectNumberBigInt = postData.projectNumberText ;
//		}
//	}
//	
//	if ((postData.projectNumberText == "") || (postData.projectNumberText == null)) {
//		errors += "The Project Number is required<br>";
//	}
//
//	if (postData.title == "") {
//		errors += "A Project Title is required<br>";
//	}
//
//	if (postData.subProjNumber == "") {
//		errors += "A Sub Project is required<br>";
//	}
//
//	if (showFormErrors("pdDlgErrorDiv", errors)) {
//		return;
//	}
//
//



// cloneProject -- Begin

function cloneProject() {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	//clearDialog(projectCloneDialog);
	var enabled = false;
	setAllSomethingChangedFlagsFalse();
	var header = "Clone Project";
	projectHeader = "";
	
	var data = projectTable.rows('.selected').data()[0];
	enabled = true;
	header = "Clone Project: " + data.projectNameText;
	projectHeader = data.projectNameText;

	projectTable.rows().deselect();
	
	$("#pcDialogHeader").html(header);
	$("#pcProjectIdParent").val(data.projectIdParent);
	$("#pcProjectId").val(data.projectId);
	$("#pcParticipantIdHost").val(data.participantIdHost);
	$("#pcPaddyParticipantIdHost").val(data.participantIdHost == null ? "" : leadingZeroPad(data.participantIdHost, 4));
	$("#clonePaddyParticipantIdHost").val(data.participantIdHost == null ? "" : leadingZeroPad(data.participantIdHost, 4));
	$("#pcParticipantNameHost").val(data.participantNameHost);
	$("#pcParticipantIdLevel1").val(data.participantIdLevel1);
	$("#pcParticipantNameLevel1").val(data.participantNameLevel1);
	$("#pcProjectNumberBigInt").val(data.parentProjectNumberBigInt == null ? "" : data.parentProjectNumberBigInt);
	$("#cloneProjectNumberBigInt").val(data.parentProjectNumberBigInt == null ? "" : data.parentProjectNumberBigInt);
	$("#pcProjectNumberText").val(data.projectNumberText);
	$("#cloneProjectNumberText").val(data.projectNumberText);
	$("#pcProjectTitle").val(data.title);
	$("#cloneProjectTitle").val(data.title);
	$("#pcSubProjNumber").val(data.subProjNumber);
	$("#cloneSubProjNumber").val("");
	$("#pcProjectDescription").val("");
	$("#pcProjectParentName").val(data.projectParentNumberAndTitle);
	$("#pcIndividualIdProjectAdmin").val(data.individualIdProjectAdmin);
	$("#pcIndividualProjectAdminName").val(data.individualNameProjectAdmin);
	$("#pcCloneSubProjectOnly").prop("checked", true);	
	$("#pcCloneStages").prop("checked", true);
	$("#pcCloneSds").prop("checked", true);
	$("#pcCloneParticipants").prop("checked", true);
	$("#pcCloneParticipantServices").prop("checked", true);
	$("#pcCloneRemunerationModels").prop("checked", true);
	$("#pcCloneRemunerationRates").prop("checked", true);
	
	$("#pcProjectStartDate").datepicker("setDate", data.startDate == null ? timestampToString(new Date(), false) : new Date(data.startDate));
	$("#pcProjectStageTypeCodeCurrent").val(data.projectStageCurrent);

	cloneSubOrParentChanged();
	setElementEnabled("saveProjectCloneButton", true);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("projectCloneDialog");
}
// cloneProject -- End

//saveProjectClone -- Begin
function saveProjectClone() {
	var postUrl = "/rest/ignite/v1/project/clone-project";
	
	var cloneParentProjectToo = $("#pcCloneSubProjectOnly").is(":checked") ? 0 : 1;
	
	var cloneLevel=  $("#pcCloneStages").is(":checked") ? 1 : 0;
	cloneLevel = $("#pcCloneSds").is(":checked") ? 2 : cloneLevel;
	cloneLevel = $("#pcCloneParticipants").is(":checked") ? 3 : cloneLevel;
	cloneLevel =  $("#pcCloneParticipantServices").is(":checked") ? 4 : cloneLevel;
	cloneLevel =  $("#pcCloneRemunerationModels").is(":checked") ? 5 : cloneLevel;
	cloneLevel = $("#pcCloneRemunerationRates").is(":checked") ? 6: cloneLevel;
	
	var postData = {
			projectIdToBeCloned: $("#pcProjectId").val(),
			subProjNumber: $("#cloneSubProjNumber").val(), 
			description: $("#pcProjectDescription").val(),
			startDate: getMsFromDatePicker("pcProjectStartDate"),
			cloneLevel: cloneLevel,
			cloneParentProjectToo: cloneParentProjectToo,
			projectNumberBigInt: $("#cloneProjectNumberBigInt").val(),
			projectNumberText: $("#cloneProjectNumberText").val(),
			title: $("#cloneProjectTitle").val()

	};
	
	var errors = "";
	
	if (isNumber(postData.projectNumberText)) {
		postData.projectNumberBigInt = postData.projectNumberText ;
	}

	
	// validate
	if (postData.subProjNumber == "") {
		errors += "A Sub Project Number is required<br>";
	}
	
	if (showFormErrors("pcDlgErrorDiv", errors)) {
		return;
	}
	
//	if (postData.projectStageId != null) {
//		var postUrl = "/rest/ignite/v1/project-stage";
//	}
	
	saveEntry(postUrl, postData, "projectCloneDialog", "The Project has been cloned.", projectTable);
	
}
//saveProjectClone -- End



// cloneProject -- End
//*********************************************************
//

function cloneDialogChanged(whichCheckbox) {
	setElementEnabled("saveProjectStageButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
	
	  switch(whichCheckbox) {
	    case 6: // If Remuneration rates are checked, all others must be selected  
				$("#pcCloneStages").prop("checked", true);
				$("#pcCloneSds").prop("checked", true);
				$("#pcCloneParticipants").prop("checked", true);
				$("#pcCloneParticipantServices").prop("checked", true);
				$("#pcCloneRemunerationModels").prop("checked", true);
				// $("#pcCloneRemunerationRates").prop("checked", false);
			break;
	    case 5: 
				$("#pcCloneStages").prop("checked", true);
				$("#pcCloneSds").prop("checked", true);
				$("#pcCloneParticipants").prop("checked", true);
				$("#pcCloneParticipantServices").prop("checked", true);
				// $("#pcCloneRemunerationModels").prop("checked", true);
				$("#pcCloneRemunerationRates").prop("checked", false);
			break;
	    case 4: 
				$("#pcCloneStages").prop("checked", true);
				$("#pcCloneSds").prop("checked", true);
				$("#pcCloneParticipants").prop("checked", true);
				// $("#pcCloneParticipantServices").prop("checked", true);
				$("#pcCloneRemunerationModels").prop("checked", false);
				$("#pcCloneRemunerationRates").prop("checked", false);
			break;
	    case 3: 
				$("#pcCloneStages").prop("checked", true);
				$("#pcCloneSds").prop("checked", true);
				//$("#pcCloneParticipants").prop("checked", true);
				$("#pcCloneParticipantServices").prop("checked", false);
				$("#pcCloneRemunerationModels").prop("checked", false);
				$("#pcCloneRemunerationRates").prop("checked", false);
			break;
	    case 2: 
				$("#pcCloneStages").prop("checked", true);
				// $("#pcCloneSds").prop("checked", true);
				$("#pcCloneParticipants").prop("checked", false);
				$("#pcCloneParticipantServices").prop("checked", false);
				$("#pcCloneRemunerationModels").prop("checked", false);
				$("#pcCloneRemunerationRates").prop("checked", false);
			break;
	    case 1: 
				//$("#pcCloneStages").prop("checked", true);
				$("#pcCloneSds").prop("checked", false);
				$("#pcCloneParticipants").prop("checked", false);
				$("#pcCloneParticipantServices").prop("checked", false);
				$("#pcCloneRemunerationModels").prop("checked", false);
				$("#pcCloneRemunerationRates").prop("checked", false);
			break;
//	    default:
	  }
  	setElementEnabled("saveProjectCloneButton", true);

}

function closeProjectCloneDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("pcDlgErrorDiv", "none");
				closeModalDialog("projectCloneDialog");
			});
	} else {
		setDivVisibility("pcDlgErrorDiv", "none");
		closeModalDialog("projectCloneDialog");
	}
}

// ***********************************************************************

function setAllSomethingChangedFlagsFalse() {

	somethingChangedGeneralTab = false;
	somethingChangedInDialog = false;
	askToSaveTab = false;
	askToSaveDialog = false;
}
//setAllSomethingChangedFlags -- End

//Johannes
function getUserNameIndivIdParIdForProject() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#projMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#projMyName").val(volleNaam);
			$("#projMyParticipantId").val(data.participantId);
			participantId = data.participantId;
			initializeProjectTable();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
};

$(document).ready(function() {
	showIgDeveloperOption();
	getUserNameIndivIdParIdForProject(); // Initialize table in this function
});
