var individualTable = null;
var showOnlyMyParticipant = true;
var participantId = null;
var resourceId = null;

var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var newDateRangeSelected = false;
const now = new Date();

const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Current month's first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day


//*********** Initialize Individual Table*********** //
function initializeIndividualTable() {

	var queryUrl;
	var columnsArray = [
	                    
		{ data: "individualId" }, 			//0
		{ data: "participantId" }, 			//1 --
		{ data: "systemName" },				//2 --
		{ data: "nickName" }, 				//3 --
		{ data: "lastName" },				//4 --
		{ data: "initials" },				//5 
		{ data: "idNumber" },				//6
		{ data: "phoneNumber" },			//7 --
		{ data: "emailAddress" },			//8 --
		{ data: "isActiveMember" },			//9 --
		{ data: "allowLoginFlag" }			//10 --
		
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,5,6]
		},
		{
			render: function(data, type, row) {
				id = leadingZeroPad(data,4);
				return id;
			},
			targets: [1]
		},		
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActiveMember == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 9
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowLogin == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 10
		}
	];

	var buttonsArray = [
	            		{
	            			attr: {
	            				id: "showMyIndividualBtn"
	            			},
	            			titleAttr: "Show all Individuals",
	            			text: "<i class='fas fa-users'></i>",
	            			className: "btn btn-sm",
	            			action: function(e, dt, node, config) {
	            				showOnlyMyParticipant = !showOnlyMyParticipant;
	            				showMyParticipantOrNot(showOnlyMyParticipant);
	            			}
	            		}
	            	];
	
	if (showOnlyMyParticipant) { // Show only  linked to participant logged in
		queryUrl = "/rest/ignite/v1/participant/individuals-only-me/" + participantId;
	} else { 
		queryUrl = "/rest/ignite/v1/participant/individuals-in-view";
	}	
	
	individualTable = initializeGenericTable("individualTable",
					queryUrl,
                    columnsArray,
                    columnDefsArray,
                    buttonsArray,
                    function(rowSelector) {
						// initializeTimesheetTable(rowSelector, firstDay, lastDay, false);
						editIndividualTimesheet(rowSelector) 
					}
	);
}// initializeIndividualTable -- End

function editIndividualTimesheet(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var participantId = -1; // -1 will indicate insert
	
	if (rowSelector != null) {
		
		data = individualTable.row(rowSelector).data();
		participantId = data.participantId;
	}

	url = springUrl("/timesheet-edit?id=") + participantId;
	console.log(url);
	window.location = url;
}


function showMyParticipantOrNot(showOnlyMyParticipant) {

	var requestUrl;

	if (showOnlyMyParticipant == false) {
		requestUrl = "/rest/ignite/v1/participant/individuals-in-view";
		$("#showMyIndividualBtn").html("<i class='fas fa-user'></i>");	
		$("#showMyIndividualBtn").prop("title", "Show me only");	
	} else {
		requestUrl = "/rest/ignite/v1/participant/individuals-only-me/" + participantId;
		$("#showMyIndividualBtn").html("<i class='fas fa-users'></i>");	
		$("#showMyIndividualBtn").prop("title", "Show all Individuals");	
	}

	individualTable.ajax.url(springUrl(requestUrl)).load();  //NB Call springUrl to set the context path on the production server when accessing urls. (Locally will work)
	individualTable.rows().deselect();
};

//*********************************************************
//*********** Initialize Individual Table End *********** //
//*********************************************************


//Johannes
function getUserNameIndivIdParIdForTimesheet() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#timMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#timMyName").val(volleNaam);
			$("#timMyParticipantId").val(data.participantId);
			participantId = data.participantId;
			initializeIndividualTable();
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
	getUserNameIndivIdParIdForTimesheet();
});
