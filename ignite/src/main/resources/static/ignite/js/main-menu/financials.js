var participantTable = null;
var showOnlyMyParticipant = true;
var participantId = null;

function initializeParticipantFinancialsTable() {

	var queryUrl;
	var columnsArray = [
		{ data: "participantId" },					//0
		{ data: "systemName" },						//1 SystemName or FirstName (indiv)
		{ data: "" },								//2 Registered Name or Surname (indiv)
		{ data: "" },								//3
		{ data: "representativeIndividualId" },		//4
		{ data: "representative" },					//5
		{ data: "participantTypeCode" },			//6
		{ data: "participantTypeName" },			//7
		{ data: "isIndividual" },					//8
		{ data: "tapSubscriptionCode" },			//9
		{ data: "tapAdministered" }			//10
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
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.tapAdministered == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 10
		}

	];

	var buttonsArray = [
		{
			attr: {
				id: "showMyFinanceBtn"
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

	if (showOnlyMyParticipant) { // Show only  linked to participant logged in        //me, and Participants where I am Representative or Marketing
		queryUrl = "/rest/ignite/v1/participant/only-me-financial/" + participantId;
	} else { 
		queryUrl = "/rest/ignite/v1/participant/all-in-view";
	}	
	
	participantTable = initializeGenericTable("participantTable",
									queryUrl,
									columnsArray,
									columnDefsArray,
									buttonsArray,
									function(aThis) {
										editParticipantFins(aThis);
									},
									function() {
										var count = participantTable.data().count();
									}  // This function will only fire when all data has been loaded successfully
									//										25
	);

	participantTable.off('deselect');
   	participantTable.on('deselect', function(e, dt, type, indexes) {
//		updateParticipantToolbarButtons();
	});

	participantTable.off('select');
	participantTable.on('select', function(e, dt, type, indexes) {
//		updateParticipantToolbarButtons();
	});

//	updateParticipantToolbarButtons();

}

function editParticipantFins(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var participantId = -1; // -1 will indicate insert
	
	if (rowSelector != null) {
		
		data = participantTable.row(rowSelector).data();
		participantId = data.participantId;
	}
	url = springUrl("/financials-participant?id=") + participantId;
	window.location = url;
}



function showMyParticipantOrNot(showOnlyMyParticipant) {

	var requestUrl;

	if (showOnlyMyParticipant == false) {
		requestUrl = "/rest/ignite/v1/participant/all-in-view";
		$("#showMyFinanceBtn").html("<i class='fas fa-user'></i>");	
		$("#showMyFinanceBtn").prop("title", "Show only Participants linked to Me");	
	} else {
		requestUrl = "/rest/ignite/v1/participant/only-me/" + participantId;
		$("#showMyFinanceBtn").html("<i class='fas fa-users'></i>");	
		$("#showMyFinanceBtn").prop("title", "Show all Participants");	
	}

	participantTable.ajax.url(springUrl(requestUrl)).load();  //NB Call springUrl to set the context path on the production server when accessing urls. (Locally will work)
	participantTable.rows().deselect();
};



//Johannes
function getUserNameIndivIdParIdForFinancials() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#finMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#finMyName").val(volleNaam);
			$("#finMyParticipantId").val(data.participantId);
			participantId = data.participantId;
			initializeParticipantFinancialsTable();
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
	getUserNameIndivIdParIdForFinancials();
});



