var somethingChangedGeneralTab = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var nextProjectNumberBigInt = null;
var dateRangeUnique = false;

const now = new Date();
const firstDayOfMonth = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
const lastDayOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

var showDefaultFinProj;

// *********************************************************
// editProject -- Start
// General Tab

function editProjectGeneralTab(projectData) {

	var data = {};
	var insert = true;
	var enabled = false;
	setAllSomethingChangedFlagsFalse();
	var header = "Project Detail";
	projectHeader = "";

//	//++++ when using a new page, rather than a dialog
//	if ((data === undefined) || (data === null)) {
//		data = {}; // Give it an empty object (so, need to add a new record)
//	}
 	
 	if (projectId > 0) {
		 insert = false;
		 data = projectData;
	 }
	//++++

	if (!insert) { //+--- {
		enabled = true;
		header = "Project: " + data.projectNameText;
		$("#projectHeader ").html("<i class='fa fa-info-circle'></i> " + header);

		// Hide binos
		document.getElementById("gHostBinos").hidden = true;
		document.getElementById("glevel1Binos").hidden = true;
		setDivVisibility("gpProjectPanel", "block");

	} else { //set default values for new record
		data.isActive = "Y"; //Set new Project as an active project by default
		data.flagSustenanceProject = "N";
		// Show binos
		document.getElementById("gHostBinos").hidden = false;
		document.getElementById("glevel1Binos").hidden = false;
		setDivVisibility("gpProjectPanel", "none");

		//		function setDivVisibility(elName, displayStyle) {
		//			var el = document.getElementById(elName);
		//			
		//			if (el != null) {
		//				el.style.display = displayStyle;
		//			}
		//		}
		//	    setDivVisibility("gToplevelBinos", "block");
	}

	
	$("#pdTabName").val("--generalProjectTab--");
	
	$("#projectDialogHeader").html(header);
	$("#pdProjectIdParent").val(data.projectIdParent);
	$("#pdProjectId").val(data.projectId);
	$("#gParticipantIdLevel1").val(data.participantIdLevel1);
	$("#gProjectParticipantIdLevel1").val(data.projectParticipantIdLevel1);
	$("#gParticipantNameLevel1").val(data.participantNameLevel1);
	$("#gParticipantIdHost").val(data.participantIdHost);
	$("#gParticipantIdHostText").val(data.participantIdHost == null ? "" : paddy(data.participantIdHost, 4));
	$("#gParticipantNameHost").val(data.participantNameHost);
	$("#gProjectNumberBigInt").val(data.projectNumberBigInt == null ? "" : data.projectNumberBigInt);
	$("#gProjectNumberText").val(data.projectNumberText);
	$("#pdProjectTitle").val(data.title);
	$("#gSubProjNumber").val(data.subProjNumber);
	$("#pdProjectDescription").val(data.description);
	$("#pdProjectParentName").val(data.projectParentNumberAndTitle);
	$("#pdIndividualIdProjectAdmin").val(data.individualIdProjectAdmin);
	$("#pdIndividualProjectAdminName").val(data.individualNameProjectAdmin);
	$("#pdIsActive").prop("checked", data.isActive == "Y");
	$("#pdSustenanceProject").prop("checked", data.flagSustenanceProject == "Y");
	$("#pdProjectStartDate").datepicker("setDate", data.startDate == null ? timestampToString(new Date(), false) : new Date(data.startDate));
	$("#pdProjectCompletionDate").datepicker("setDate", data.completionDate == null ? null : new Date(data.completionDate));
	$("#pdProjectStageTypeCodeCurrent").val(data.projectStageCurrent);

	setAdditionalTabStates(enabled);
	setElementEnabled("saveGeneralTabButton", false);
	somethingChangedGeneralTab = false;
	askToSaveTab = false;

//	showModalDialog("projectDialog");
}
// editProject -- End

function getUserNameIndivId() {
	var queryUrl;

	queryUrl = "/rest/ignite/v1/individual/user-name-id";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#aUserName").val(data.individualId);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function paddy(num, padlen, padchar) {
	var pad_char = typeof padchar !== 'undefined' ? padchar : '0';
	var pad = new Array(1 + padlen).join(pad_char);
	return (pad + num).slice(-pad.length);
}

function checkUsernameUniqueness() {
	var username;
	var allowLoginFlag;
	var queryUrl;

	//generalTabChanged();
	username = $("#eIndUserName").val();
	allowLoginFlag = $("#eIndAllowLoginFlag").is(":checked") ? "Y" : "N";
	var indId = $("#eIndIndividualId").val();
	queryUrl = "/rest/ignite/v1/individual/unique/" + indId + "/" + username;

	// set our initial state
	usernameUnique = false;

	if (allowLoginFlag == "N") {
		return;
	}

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			var msg = "";

			usernameUnique = data.unique;
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

//function updateProjectCurrentStatus(projectId) {
//	var queryUrl;
//	
//	queryUrl = "/rest/ignite/v1/project/" + projectId;
//	
//	$.ajax({
//		url: springUrl(queryUrl),
//		type: "GET",
//		success: function(data) {
//			$("#pdProjectStageTypeCodeCurrent").val(data.projectStage == null? "": data.projectStage);
//			projectTable.ajax.reload();
//		},
//		error: function(jqXHR, textStatus, errorThrown) {
//			ajaxError(jqXHR, textStatus, errorThrown);
//		},
//		complete: function(html) {
//			ajaxSuccess(html.status);
//		}  
//	});
//}

// saveProjectGeneral -- Begin
function saveProjectGeneral() {
	var postData = {
		projectId: $("#pdProjectId").val(),
		projectIdParent: $("#pdProjectIdParent").val(),
		participantIdLevel1: $("#gParticipantIdLevel1").val(),
		projectParticipantIdLevel1: $("#gProjectParticipantIdLevel1").val(),
		participantIdHost: $("#gParticipantIdHost").val(),
		projectNumberBigInt: $("#gProjectNumberBigInt").val(),
		projectNumberText: $("#gProjectNumberText").val(),
		projectNameText: (paddy($("#gProjectNumberText").val(), 4) + " - " + $("#gProjectNumberText").val() + " - " + $("#pdProjectTitle").val() + " - " + $("#gSubProjNumber").val()),
		title: $("#pdProjectTitle").val(),
		subProjNumber: $("#gSubProjNumber").val(),
		description: $("#pdProjectDescription").val(),
		individualIdProjectAdmin: $("#pdIndividualIdProjectAdmin").val(),
		isActive: $("#pdIsActive").is(":checked") ? "Y" : "N",
		flagSustenanceProject: $("#pdSustenanceProject").is(":checked") ? "Y" : "N",						
		startDate: getMsFromDatePicker("pdProjectStartDate"),
		completionDate: getMsFromDatePicker("pdProjectCompletionDate")
	};

	var errors = "";
	var postUrl = "";

	// validate
	if (postData.participantIdLevel1 == "") {
		errors += "Please select the level 1 Contracting Participant<br>";
	}

	if (postData.participantIdHost == "") {
		errors += "Please select the Host Participant<br>";
	}
if ((postData.startDate == "") || (postData.startDate == null)) {
		// Add Today's date without time
		$("#pdProjectStartDate").datepicker("setDate", data.startDate == null ? timestampToString(new Date(), false) : new Date(data.startDate));
		postData.startDate = getMsFromDatePicker("pdProjectStartDate");
	}
	if ((postData.completionDate == "") || (postData.completionDate == null)) {
		postData.completionDate = null;
	}

//	if ((postData.projectNumberBigInt != "") && (postData.projectNumberBigInt != null)) {
//		if (isNaN(postData.projectNumberBigInt)) {
//			errors += "The Project Number has to be numeric<br>";
//		}
//	} else {
//		errors += "The Project Number is required<br>";
//	}

	if ((postData.projectNumberText != "") && (postData.projectNumberText != null)) {
		if (isNumber(postData.projectNumberText)) {
			postData.projectNumberBigInt = postData.projectNumberText ;
		}
	}
	
	if ((postData.projectNumberText == "") || (postData.projectNumberText == null)) {
		errors += "The Project Number is required<br>";
	}

	if (postData.title == "") {
		errors += "A Project Title is required<br>";
	}

	if (postData.subProjNumber == "") {
		errors += "A Sub Project is required<br>";
	}

	if (showFormErrors("pdDlgErrorDiv", errors)) {
		return;
	}

	postUrl = "/rest/ignite/v1/project/save-parent-and-sub-project";
	console.log(postUrl);
	console.log(postData);
	saveEntry(postUrl, postData, null, "The Project has been saved.", null, function(request, response) {
		var data = response;
		var projectId = data.projectId;
		var projectIdParent = data.projectIdParent;
		var projectParticipantIdLevel1 = data.projectParticipantIdLevel1;


		$("#pdProjectId").val(projectId);
		$("#gProjectParticipantIdLevel1").val(projectParticipantIdLevel1),
		$("#pdProjectIdParent").val(projectIdParent);
		$("#projectDialogHeader").html("Project Detail: " + data.projectNumberText + " " + data.title + " - " + data.subProjNumber);

		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful
		document.getElementById("gHostBinos").hidden = true;
		document.getElementById("glevel1Binos").hidden = true;
		setElementEnabled("saveGeneralTabButton", false);
		somethingChangedGeneralTab = false;
		askToSaveTab = false;
	});
}
// saveProjectGeneral -- End

function selectParticipantIdGenProjTab(whichTab) {

	var queryUrl = "/rest/ignite/v1/participant/all-in-view";

	if (whichTab == "generalTabHost") {
		var targetId = "gParticipantIdHost";
		var targetName = "gParticipantNameHost";
	}
	if (whichTab == "generalTabLevel1") {
		var targetId = "gParticipantIdLevel1";
		var targetName = "gParticipantNameLevel1";
	}
//	if (whichTab == "ppTab") {
//		var targetId = "ppParticipantId";
//		var targetName = "ppParticipantName";
//	}

	selectParticipantGenProjTab(queryUrl, targetId, targetName);

}


function selectParticipantGenProjTab(queryUrl, targetId, targetName) {
	// Use this example to select from grid : Participant NB! NB!
	// Ingrid Default search from grid 

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "participantId";
			var columns = [
				{ data: "participantId", name: "Participant Id" },					//0 --
				{ data: "systemName", name: "System Name" },						//1 -- 
				{ data: "individualName", name: "Name / Registered Name" },			//2 -- 
				{ data: "nonIndivName", name: "Non Natural Person" },				//3 
				{ data: "latestProjectNumber", name: "Latest Project Number" },		//4 
				{ data: "registeredName", name: "Registered Name" },				//5 
				{ data: "tradingName", name: "Trading Name" },						//6
				{ data: "isIndividual", name: "Is Individual" },					//7 --
				{ data: "projectPrefix", name: "Project Prefix" },					//8
				{ data: "projectPostfix", name: "Project Postfix" },				//9
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [3,4,5,6,8,9]
				},
				{
					render: function(data, type, row) {
						p = "";
						if (data != null) {
							p = paddy(data, 4);
						}
						return p;
					},
					targets: 0
				},
				{
					render: function(data, type, row) {
						var name = "";
						if (row.hasOwnProperty("isIndividual")) {
							if (row.isIndividual == "Y") {
								name = row.individualName;
							} else {
								name = row.registeredName;
							}
						}
						return name;
					},
					targets: [2]
				},				
				{
					render: function(data, type, row) {
						return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
					},
					className: "dt-center",
					targets: 7
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var partName = row.systemName;
				var nextProjectNumberBigInt = row.latestProjectNumber + 1;
				var currentSubProjNumber = $("#gSubProjNumber").val();

				$("#" + targetId).val(id);
				$("#" + targetName).val(partName);
				$("#" + targetName).trigger("change");
				
				if (targetId == "gParticipantIdHost") {
					
					$("#" + targetId).val(paddy(id, 4));
					$("#gProjectNumberBigInt").val(nextProjectNumberBigInt);
					$("#gProjectNumberText").val(row.projectPrefix + paddy(nextProjectNumberBigInt, 4) + row.projectPostfix);
					$("#gParticipantIdHostText").val(paddy(row.participantId,4));

					if ($("#gParticipantIdLevel1").val() == "") {
						$("#gParticipantIdLevel1").val(id);
						$("#gParticipantNameLevel1").val(partName);
					}
					
					if (currentSubProjNumber == "") {
						$("#gSubProjNumber").val("A Initial");
					}
				}
				if (($("#gSubProjNumber").val() != "") && ($("#gParticipantIdLevel1").val() != "")){
					setDivVisibility("gpProjectPanel", "block");
				}	

				
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


function selectIndividualProjectAdmin() {
	var queryUrl = "/rest/ignite/v1/individual/list";

	var targetId = "pdIndividualIdProjectAdmin";
	var targetName = "pdIndividualProjectAdminName";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "individualIdProjectAdmin";
			var refColumnName = "individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "nickName", name: "Nick Name" },
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
				var projectAdminName = row.nickName + " " + row.lastName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(projectAdminName);
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

function selectProjectIdParent() {
	var queryUrl = "/rest/ignite/v1/project/list"

	var targetId = "pdProjectIdParent";
	var targetName = "pdProjectParentName";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "projectIdParent";
			var refColumnName = "projectId";
			var columns = [
				{ data: "projectId", name: "Id" },
				{ data: "projectNumberBigInt", name: "Number" },
				{ data: "projectNumberText", name: "Number" },
				{ data: "subProjNumber", name: "Sub Project" },
				{ data: "title", name: "Title" },
				{ data: "description", name: "Description" },
				{ data: "isActive", name: "Is Active" },
				{ data: "flagSustenanceProject", name: "Sustenace Project" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: [0,1]
				},
				{
					render: function(data, type, row) {
						pn = "";
						if (row.hasOwnProperty("projectNumberBigInt")) {
							pn = row.projectNumberBigInt;
							if (pn != null) {
								pn = paddy(pn, 4);
							}
						}
						return pn;
					},
					targets: 1
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectId;
				var parentProject = paddy(row.projectNumberBigInt, 4) + " " + row.title;

				$("#" + targetId).val(id);
				$("#" + targetName).val(parentProject);
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
// editProject -- End
//*********************************************************


// generalTabChanged -- Start
function generalTabChanged() {
	currentTab = "General";
	askToSaveTab = true;
	somethingChangedGeneralTab = true;
	setElementEnabled("saveGeneralTabButton", true);
}
// generalTabChanged -- End


