var expenseParticipantTable = null;
var showOnlyMyParticipant = true;
var participantId = null;

var newDateRangeSelected = false;
//const now = new Date();
//const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
//const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day		

function initializeExpenseParticipantTable() {

	var queryUrl;
	var columnsArray = [
		{ data: "participantId" },					//0
		{ data: "systemName" },						//1 SystemName or FirstName (indiv)
		{ data: "" },					//2 Registered Name or Surname (indiv)
		{ data: "" },					//3
		{ data: "representativeIndividualId" },		//4
		{ data: "representative" },					//5
		{ data: "participantTypeCode" },			//6
		{ data: "participantTypeName" },			//7
		{ data: "isIndividual" },					//8
		{ data: "tapSubscriptionCode" },			//9
		{ data: "tapSubscriptionName" }			//10
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [ 4, 6, 9]

		},
		{
			render: function(data, type, row) {
				id = leadingZeroPad(data,4);
				return id;
			},
			targets: [0]
		},		
		
		{
			render: function(data, type, row) {
				var name = "";
				if (row.hasOwnProperty("isIndividual")) {
					if (row.isIndividual == "Y") {
						name = row.firstName;
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
				var name = "";
				if (row.hasOwnProperty("isIndividual")) {
					if (row.isIndividual == "Y") {
						name = row.lastName;
					} else {
						name = row.tradingName;
					}
				}
				return name;
			},
			targets: [3]
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 8
		}
	];

	var buttonsArray = [
		{
			attr: {
				id: "showMyExpenseBtn"
			},
			titleAttr: "Show all Participants",
			text: "<i class='fas fa-users'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showOnlyMyParticipant = !showOnlyMyParticipant;
				showMyParticipantOrNot(showOnlyMyParticipant);
			}
		}
	];

	if (showOnlyMyParticipant) { // Show only  linked to participant logged in.   // Only me and others Linked to me through a Project
		queryUrl = "/rest/ignite/v1/participant/only-me/" + participantId;
	} else { 
		queryUrl = "/rest/ignite/v1/participant/all-in-view";
	}	
	
	expenseParticipantTable = initializeGenericTable("expenseParticipantTable",
			queryUrl,
			columnsArray,
			columnDefsArray,
			buttonsArray,
			function(aThis) {
				editParticipantExpenseE(aThis);
			},
			function() {
			}  // This function will only fire when all data has been loaded successfully
			//										25
	);
}



function editParticipantExpenseE(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var participantId = -1; // -1 will indicate insert
	
	if (rowSelector != null) {
		
		data = expenseParticipantTable.row(rowSelector).data();
		participantId = data.participantId;
	}
	url = springUrl("/expense-edit?id=") + participantId;
	window.location = url;
}


function showMyParticipantOrNot(showOnlyMyParticipant) {

	var requestUrl;

	if (showOnlyMyParticipant == false) {
		requestUrl = "/rest/ignite/v1/participant/all-in-view";
		$("#showMyExpenseBtn").html("<i class='fas fa-user'></i>");	
		$("#showMyExpenseBtn").prop("title", "Show me only");	
	} else {
		requestUrl = "/rest/ignite/v1/participant/only-me/" + participantId;     // Only me and others Linked to me through a Project
		$("#showMyExpenseBtn").html("<i class='fas fa-users'></i>");	
		$("#showMyExpenseBtn").prop("title", "Show all Participants");	
	}

	expenseParticipantTable.ajax.url(springUrl(requestUrl)).load();  //NB Call springUrl to set the context path on the production server when accessing urls. (Locally will work)
	expenseParticipantTable.rows().deselect();
};


//Johannes
function getUserNameIndivIdParIdForExpense() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#exMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#exMyName").val(volleNaam);
			$("#exMyParticipantId").val(data.participantId);
			participantId = data.participantId;
			initializeExpenseParticipantTable();
			
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
	getUserNameIndivIdParIdForExpense();
		
});



