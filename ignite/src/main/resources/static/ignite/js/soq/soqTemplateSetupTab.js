var templateId;
var row = null;


function initializeTemplateTable() {
	
	var columnsArray = [
		{ data: "soqTemplateId" },					//0
		{ data: "scheduleName" },					//1 
		{ data: "description" },					//2 
		{ data: "primaryHeading" },					//3 
		{ data: "secondaryHeading" },				//4
		{ data: "tertiaryHeading" },				//5
		{ data: "numberColName" },					//6
		{ data: "descriptionColName" },				//7
		{ data: "unitColName" },					//8
		{ data: "rateColName" },					//9
		{ data: "qtyColName" },						//10
		{ data: "amountColName" },					//11
		{ data: "lastUpdateTimestamp" },			//12
		{ data: "lastUpdateUserName" }				//13
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [ 0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]

		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fa fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				initializeSoqTemplateDialog(row);
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
				promptDeleteTemplate();
			}
		}
	];
		
	soqTemplateTable = initializeGenericTable("soqTemplateTable",
		"/rest/ignite/v1/soq-template/all",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(e, dt, type, indexes) {
			initializeSoqTemplateDialog(row);
		},
		null
	);

	soqTemplateTable.off('deselect');
	soqTemplateTable.on('deselect', function(e, dt, type, indexes) {
		row = {};
		setDivVisibility("emptySubScheduleDiv", "block");
		setDivVisibility("subScheduleDiv", "none");
		updateTemplateToolbarButtons();
	});

	soqTemplateTable.off('select');
	soqTemplateTable.on('select', function(e, dt, type, indexes) {
		row = dt.data();
		initializeSubScheduleTable(row);
		populateSoqTemplateFields(row);
		setDivVisibility("emptySubScheduleDiv", "none");
		setDivVisibility("subScheduleDiv", "block");
		updateTemplateToolbarButtons();
	});

	updateTemplateToolbarButtons();
	
}

function promptDeleteTemplate() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Template?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function() {
			deleteTemplate();
		}
	);
}

function deleteTemplate() {
	var postUrl = "/rest/ignite/v1/soq-template/delete";
	var row = soqTemplateTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Template has been deleted.", soqTemplateTable,
		function() {
			soqTemplateTable.rows(".selected").nodes().to$().removeClass("selected");
			updateTemplateToolbarButtons();
		}
	);
}

function initializeSubScheduleTable(row) {
	
	var data = null;
	
	if (row == null) {
		return
	}
	
	var columnsArray = [
		{ data: "soqTemplSubScheduleId" },			//0
		{ data: "soqTemplateId" },					//1 
		{ data: "name" },							//2 
		{ data: "description" },					//3 
		{ data: "orderNumber" },					//4
		{ data: "subReportHeader1" },				//5
		{ data: "subReportHeader2" },				//6
		{ data: "pageNumberPrefix" },				//7
		{ data: "pageNumberPostFix" },				//8
		{ data: "startPageNumber" },				//9
		{ data: "lastUpdateTimestamp" },			//10
		{ data: "lastUpdateUserName" }				//11
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [ 0, 1, 4, 5, 6, 7, 8, 9, 10, 11]

		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fa fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				initializeSoqTemplateSubScheduleDialog(data);
			}
		},
		{
			attr: {
				id: "promptDeleteSubScheduleBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fa-solid fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteTemplateSubSchedule();
			}
		}
	];
		
	soqTemplateSubScheduleTable = initializeGenericTable("soqTemplateSubScheduleTable",
		"/rest/ignite/v1/soq-template-subschedule/info/" + row.soqTemplateId,
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},
		function() {
			var count = soqTemplateSubScheduleTable.data().count();
		}  // This function will only fire when all data has been loaded successfully
	);

	soqTemplateSubScheduleTable.off('deselect');
	soqTemplateSubScheduleTable.on('deselect', function(e, dt, type, indexes) {
		data = null;
		updateTemplateSubScheduleToolbarButtons();
	});

	soqTemplateSubScheduleTable.off('select');
	soqTemplateSubScheduleTable.on('select', function(e, dt, type, indexes) {
		data = dt.data()
		populateSoqTemplateSubScheduleFields(data);
		updateTemplateSubScheduleToolbarButtons();
	});

	// TODO: Find solution for broken table header width

	updateTemplateSubScheduleToolbarButtons();

}

function promptDeleteTemplateSubSchedule() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Sub Schedule?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function() {
			deleteTemplateSubSchedule();
		}
	);
}

function deleteTemplateSubSchedule() {
	var postUrl = "/rest/ignite/v1/soq-template-subschedule/delete";
	var row = soqTemplateSubScheduleTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Sub Schedule has been deleted.", soqTemplateSubScheduleTable,
		function() {
			soqTemplateSubScheduleTable.rows(".selected").nodes().to$().removeClass("selected");
			updateTemplateSubScheduleToolbarButtons();
		}
	);
}

function updateTemplateSubScheduleToolbarButtons() {
	var hasSelected = soqTemplateSubScheduleTable.rows('.selected').data().length > 0;

	setTableButtonState(soqTemplateSubScheduleTable, "promptDeleteSubScheduleBtn", hasSelected);
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

function showSoqTemplateDialog() {
	showModalDialog("soqTemplateDialog");
}



function showSoqTemplateItemDialog() {
	showModalDialog("soqTemplateItemDialog");
}

/*$(document).ready(function() {
	// Any initialization
	initializeTemplateTable();

} );*/