
function initializeSoqScheduleTable() {
	
	var columnsArray = [
		{ data: "scheduleOfQuantitiesId" },					//0
		{ data: "soqTemplateIdUsed" },						//1
		{ data: "projectId" },								//2
		{ data: "projectParticipantIdCreatedBy" },			//3
		{ data: "soqModeCode" },							//4
		{ data: "scheduleName" },							//5
		{ data: "description" },							//6
		{ data: "tenderNumber" },							//7
		{ data: "primaryHeading" },							//8
		{ data: "secondaryHeading" },						//9
		{ data: "tertiaryHeading" },						//10
		{ data: "startDate" },								//11
		{ data: "numberColName" },							//12
		{ data: "descriptionColName" },						//13
		{ data: "unitColName" },							//14
		{ data: "rateColName" },							//15
		{ data: "qtyColName" },								//16
		{ data: "amountColName" },							//17
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [ 0, 1, 2, 3, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]

		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fa fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showSoqScheduleDialog();
			}
		},
		{
			attr: {
				id: "promptDeleteTemplateBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fa-solid fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				//promptDeleteParticipant();
			}
		}
	];
		
	soqScheduleTable = initializeGenericTable("soqScheduleTable",
		"/rest/ignite/v1/soq-utility/all",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},
		function() {
			var count = soqScheduleTable.data().count();
		}  // This function will only fire when all data has been loaded successfully
	);

	soqScheduleTable.off('deselect');
	soqScheduleTable.on('deselect', function(e, dt, type, indexes) {
		updateScheduleToolbarButtons();
	});

	soqScheduleTable.off('select');
	soqScheduleTable.on('select', function(e, dt, type, indexes) {
		updateScheduleToolbarButtons();
	});

	updateScheduleToolbarButtons();

}

function updateScheduleToolbarButtons() {
	var hasSelected = soqScheduleTable.rows('.selected').data().length > 0;

	setTableButtonState(soqScheduleTable, "promptDeleteScheduleBtn", hasSelected);
}

function loadTemplate() {
	var data = [
		{
			anyChildren: "Y",
			description: "",
			id: 1,
			level: 1,
			name: "CONTRACT ABC",
			orderNumber: 1,
			parentName: null,
			children: [
				{
					anyChildren: "Y",
					description: "",
					id: 1,
					level: 1,
					name: "SCHEDULE A",
					orderNumber: 1,
					parentName: null,
					children : [
						{
							anyChildren: "Y",
							description: "",
							id: 1,
							level: 1,
							name: "GENERAL",
							orderNumber: 1,
							parentName: null,
						},
						{
							anyChildren: "Y",
							description: "",
							id: 1,
							level: 1,
							name: "SITE CLEARANCE",
							orderNumber: 1,
							parentName: null,
						},
						{
							anyChildren: "Y",
							description: "",
							id: 1,
							level: 1,
							name: "EARTHWORKS",
							orderNumber: 1,
							parentName: null,
						}
					]
				}
			]
		}
	];
	
	var tree = $("#tfsTree").tree({
		data: data
	});
}

function showSoqScheduleDialog() {
	showModalDialog("soqScheduleDialog");
}

function showSoqSubScheduleDialog() {
	showModalDialog("soqSubScheduleDialog");
}

function showSoqItemDialog() {
	showModalDialog("soqItemDialog");
}


// ***********************************************************************

$(document).ready(function() {
	
	initializeSoqScheduleTable();

});