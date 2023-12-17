function initializeUnitsTable() {
	
	console.log("initializing units table")
	
	var columnsArray = [
		{ data: "soqUnitCode" },					//0
		{ data: "name" },							//1 
		{ data: "description" },					//2
		{ data: "lastUpdateTimestamp" },			//3
		{ data: "lastUpdateUserName" }				//4
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [ 3, 4]

		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fa fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showSoqTemplateDialog();
			}
		},
		{
			attr: {
				id: "promptDeleteUnitBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fa-solid fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				//promptDeleteParticipant();
			}
		}
	];
		
	soqUnitsTable = initializeGenericTable("soqUnitsTable",
		"/rest/ignite/v1/soq-unit/all",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function() {
		},
		function() {
			var count = soqUnitsTable.data().count();
		}  // This function will only fire when all data has been loaded successfully
	);

	soqUnitsTable.off('deselect');
	soqUnitsTable.on('deselect', function(e, dt, type, indexes) {
		updateUnitsToolbarButtons();
	});

	soqUnitsTable.off('select');
	soqUnitsTable.on('select', function(e, dt, type, indexes) {
		updateUnitsToolbarButtons();
	});

	updateUnitsToolbarButtons();

}

function updateUnitsToolbarButtons() {
	var hasSelected = soqUnitsTable.rows('.selected').data().length > 0;

	setTableButtonState(soqUnitsTable, "promptDeleteUnitBtn", hasSelected);
}


/************************************************/

/*$(document).ready(function() {
	// Any initialization
	initializeUnitsTable();

} );*/

