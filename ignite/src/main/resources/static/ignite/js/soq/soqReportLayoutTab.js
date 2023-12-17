
function showSoqTemplatePrintDialog() {
	showModalDialog("soqTemplatePrintDialog");
}

function showSoqTemplateSubScheduleDialog() {
	showModalDialog("soqTemplateSubScheduleDialog");
}

function showSoqTemplateItemDialog() {
	showModalDialog("soqTemplateItemDialog");
}

function initializeReportTemplateTable() {
	
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
	];
		
	soqReportLayoutTemplateTable = initializeGenericTable("soqReportLayoutTemplateTable",
		"/rest/ignite/v1/soq-template/all",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(e, dt, type, indexes) {
		},
		null
	);

	soqReportLayoutTemplateTable.off('deselect');
	soqReportLayoutTemplateTable.on('deselect', function(e, dt, type, indexes) {
	});

	soqReportLayoutTemplateTable.off('select');
	soqReportLayoutTemplateTable.on('select', function(e, dt, type, indexes) {
	});
	
}

/*$(document).ready(function() {
	// Any initialization
	
	var tree = $("#tfsTree").tree();

} );*/