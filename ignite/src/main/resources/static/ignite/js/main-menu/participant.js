/*
Resource edit page needs to work
needs start date and end date options
need to check that there is no overlap
*/

var participantTable = null;
var newDateRangeSelected = false;
const now = new Date();
const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day		

function initializeParticipantTable() {
	
	var columnsArray = [
		{ data: "participantId" },					//0
		{ data: "systemName" },						//1 
		{ data: "nickName" },						//2 Nickname (indiv)
		{ data: "" },								//3 Registered Name or Surname (indiv)
		{ data: "" },								//4
		{ data: "representativeIndividualId" },		//5
		{ data: "representative" },					//6
		{ data: "participantTypeCode" },			//7
		{ data: "participantTypeName" },			//8
		{ data: "isIndividual" },					//9
		{ data: "tapSubscriptionCode" },			//10
		{ data: "tapSubscriptionName" }			//11
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [ 5, 7, 10]

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
			targets: [3]
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
			targets: [4]
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 9
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fa fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editParticipant(null);
			}
		},
		{
			attr: {
				id: "promptDeleteParticipantBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fa-solid fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteParticipant();
			}
		}
	];

	participantTable = initializeGenericTable("participantTable",
		"/rest/ignite/v1/participant/all-in-view",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(aThis) {
			editParticipant(aThis);
		},
		function() {
			var count = participantTable.data().count();
		}  // This function will only fire when all data has been loaded successfully
	);

	participantTable.off('deselect');
	participantTable.on('deselect', function(e, dt, type, indexes) {
		updateParticipantToolbarButtons();
	});

	participantTable.off('select');
	participantTable.on('select', function(e, dt, type, indexes) {
		updateParticipantToolbarButtons();
	});

	updateParticipantToolbarButtons();

	//	window.setTimeout(function(){
	////		// var count = rowCount(participantTable);
	//		var count = participantTable.data().count();
	//		 
	////		if ( ! table.data().count() ) {
	////		    alert( 'Empty table' );
	////		}
	////		
	//	}, 500
	//	);

}



function editParticipant(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var participantId = -1; // -1 will indicate insert
	
	if (rowSelector != null) {
		
		data = participantTable.row(rowSelector).data();
		participantId = data.participantId;
	}
	url = springUrl("/participant-edit?id=") + participantId;
	window.location = url;
}

function updateParticipantToolbarButtons() {
	var hasSelected = participantTable.rows('.selected').data().length > 0;

	setTableButtonState(participantTable, "promptDeleteParticipantBtn", hasSelected);
}

function promptDeleteParticipant() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Participant?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function() {
			deleteParticipant();
		}
	);
}

function deleteParticipant() {
	var postUrl = "/rest/ignite/v1/participant/delete";
	var row = participantTable.rows('.selected').data()[0];

	if (row != null) {
		if (row.isIndividual == "Y") {
			postUrl = "/rest/ignite/v1/individual/delete";
		}
	}

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Participant has been deleted.", participantTable,
		function() {
			participantTable.rows(".selected").nodes().to$().removeClass("selected");
			updateParticipantToolbarButtons();
		}
	);
}

//Johannes
function getUserNameIndivIdParIdForParticipant() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#parMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#parMyName").val(volleNaam);
			$("#parMyParticipantId").val(data.participantId);
			participantId = data.participantId;
			initializeParticipantTable();
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
	getUserNameIndivIdParIdForParticipant();
});



