// project-edit.js //
var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

//anyUnsavedData -- Start
function anyUnsavedData() {
	if ((askToSaveTab == true) || (askToSaveDialog == true))
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				closeProjectDialog();
			}
		);
	else {
		closeProjectDialog();
	}
}

//anyUnsavedData -- End

function closeProjectDialog() {
	setActiveTab("generalProjectTabAnchor");
	closeModalDialog("projectDialog");
}

//setAllSomethingChangedFlags -- Start
function setAllSomethingChangedFlagsFalse() {

	somethingChangedGeneralTab = false;
	somethingChangedInDialog = false;
	askToSaveTab = false;
	askToSaveDialog = false;
}
//setAllSomethingChangedFlags -- End

function projectDialogChanged() {
	somethingChangedGeneralTab = true;
	somethingChangedInDialog = true;
	askToSaveTab = true;
	askToSaveDialog = true;
}

function populateFields(data) {

	editProjectGeneralTab(data); 
}


function initializeWithProjectId() {
	var queryUrl = "/rest/ignite/v1/project/" + projectId;
	console.log(queryUrl);

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
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

//	setActiveTab("generalTabLink");
//
//	//	// Any initialization
//	showDefaultFinProj = false;
//	initializeProjectTable();
//
//	// This occurs before a tab is shown, and we can prevent switching to that tab.
//	$('.nav-tabs a').on('show.bs.tab', function(event) {
//
//		// if ((currentTab == "General") && (somethingChangedGeneralTab) && (askToSaveTab)) {
//		if ((currentTab == "General") && (somethingChangedGeneralTab)) {
//			showDialog("Confirm?",
//				"<b>You have unsaved changes</b><br></br>"
//				+ "Are you sure you wish to cancel the changes on the general tab?<br></br>"
//				+ "Cancelling now will reset data.",
//				DialogConstants.TYPE_CONFIRM,
//				DialogConstants.ALERTTYPE_INFO,
//				function(e) {
//					event.preventDefault();
//					editProject(currentRowSelector);
//				}
//			);
//		}
//	});

	// This occurs when a tab is made visible, and we therefor want to populate the data
	// react to tab change and load appropriate data
	$('.nav-tabs a').on('show.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');

		var projectId = $("#pdProjectId").val();

		if (activeTab == "General") {
			currentTab = "General";
//			tabName = "--generalProjectTab--";
			$("#pdTabName").val("--generalProjectTab--");
		}

		if (activeTab == "Stages") {
			initializeProjectStagesTab(projectId);
			$("#pdTabName").val("--projectStagesTab--");
		}

		if (activeTab == "Service Disciplines") {
			initializeProjectSdTab(projectId);
			$("#pdTabName").val("--projectSdTab--");
		}

		if (activeTab == "Participants") {
			initializeProjectParticipantsTab();
			$("#pdTabName").val("--projectParticipantsTab--");
		}

		if (activeTab == "Participant Services") {

			$("#ppTreePanel").resizable({
				handles: "e",
				alsoResizesReverse: "ppTreeDetail"
			})
			initializePPTree(projectId);
			$("#pdTabName").val("--projectParticipantSdTab--");
		}

		// aaa
		if (activeTab == "Remuneration Models") {
			initializeAgreementsTab(projectId);
			$("#pdTabName").val("--agreementBetweenParticipantsTab--");
		}

		if (activeTab == "Remuneration Rates") {
			initializeTimeRelatedTab(projectId);
			$("#pdTabName").val("--ratesUplineTab--");
		}

		if ($("#cpaStartDate").val() == "") {
			projectStartDate = getMsFromDatePicker("pdProjectStartDate");

			$("#cpaStartDate").datepicker("setDate", new Date(projectStartDate));
			$("#cpaEndDate").datepicker("setDate", new Date(now));
		}
	});
}

function setAdditionalTabStates(enabled) {

	setDivVisibility("projectSdTabLink", enabled ? "block" : "none");
	setDivVisibility("projectParticipantsTabLink", enabled ? "block" : "none");
	setDivVisibility("projectStagesTabLink", enabled ? "block" : "none");
	setDivVisibility("projectParticipantSdTabLink", enabled ? "block" : "none");
	setDivVisibility("agreementBetweenParticipantsTabLink", enabled ? "block" : "none");
	setDivVisibility("projectStagesTabLink", enabled ? "block" : "none");
	setDivVisibility("ratesMultipleTabsLink", enabled ? "block" : "none");
	setDivVisibility("expenseUplineTabLink", enabled ? "block" : "none");
	setDivVisibility("paymentScheduleLink", enabled ? "block" : "none");
	setDivVisibility("expenseRatesUplineTabLink", enabled ? "block" : "none");

	setActiveTab("generalProjectTabAnchor");
}

//***********************************************************************

$(document).ready(function() {
	// Any initialization
	showIgDeveloperOption();
	$("#pdProjectId").val(projectId);

	initializeNavTabs();
	initializeWithProjectId();
//	initializeNavTabsRatesMultipleTabs();
	
} );


