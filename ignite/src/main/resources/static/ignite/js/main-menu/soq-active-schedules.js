var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var newDateRangeSelected = false;
const now = new Date();
const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day
var isUpdatingTime = false;
var nextSoqId = 1;
var amountCol = 8;


//*********** initializeSoqTable*********** //
function initializeSoqTable() {
	var columnsArray = [
		{ data: "scheduleOfQuantitiesId" },	 	 		//0
		{ data: "soqTemplateIdUsed" }, 	 				//1
		{ data: "projectId" },							//2
		{ data: "projectParticipantIdCreatedBy" },		//3
		{ data: "soqModeCode" },						//4
		{ data: "scheduleName" },						//5
		{ data: "description" },						//6
		{ data: "tenderNumber" },						//7
		{ data: "primaryHeading" },						//8
		{ data: "secondaryHeading" },					//9
		{ data: "tertiaryHeading" },					//10
		{ data: "startDate" },							//11
		{ data: "numberColName" },     					//12
		{ data: "numberColName" },      				//13
		{ data: "numberColName" },      				//14
		{ data: "descriptionColName" },					//15
		{ data: "unitColName" },						//16
		{ data: "rateColName" },						//17
		{ data: "qtyColName" },							//18
		{ data: "amountColName" },						//19
	]

	var columnDefsArray = [
		{
			visible: false,
			targets: [, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,]
		},
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				addEmptyRow();
			}
		},
		{
			titleAttr: "Save All",
			text: "<i class='fas fa-save'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				saveAllChanges();
			}
		},
		/*{
			titleAttr: "Toggle Edit",
			text: "<i class='fas fa-lock'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				var selectedRow = dt.rows({ selected: true }).nodes()[0];

				if (selectedRow) {
					var rowData = dt.row(selectedRow).data();
					rowData.isLocked = !rowData.isLocked;

					// Update the row in the DataTable
					dt.row(selectedRow).data(rowData).draw();

					// Toggle editability based on the isLocked property
					toggleRowEditability(selectedRow, !rowData.isLocked);
				}
			}
		},*/
		{
			attr: {
				id: "promptDeleteSoqBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteSoq();
			}
		}

	];

	soqTable = initializeGenericTable("soqTable",
		"/rest/ignite/v1/soq-capture/all-in-view",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			//editTripLogger(rowSelector);
		},
		null,
		40,
		[[0, "asc"]]
		
	);
	
}
// initializeSoqTable -- End

function saveAllChanges() {
	$('#soqTable tbody tr').each(function() {
		var row = soqTable.row(this);
		//if (row.child.isShown()) {

		//}
		saveChanges(row, row.data());
	});

	console.log('All changes saved.');
}


function addEmptyRow() {

	var scheduleOfQuantitiesId = nextSoqId++;

	var emptyRow = {
		scheduleOfQuantitiesId: scheduleOfQuantitiesId,
		soqTemplateIdUsed: null,
		projectId: '50',
		projectParticipantIdCreatedBy: '50',
		soqModeCode: '50',
		scheduleName: 'test',
		description: null,
		tenderNumber: null,
		primaryHeading: null,
		secondaryHeading: null,
		tertiaryHeading: null,
		startDate: null,
		numberColName: null,
		descriptionColName: null,
		unitColName: null,
		rateColName: null,
		qtyColName: null,
		amountColName: null,
		isLocked: false
	};

	soqTable.row.add(emptyRow).draw();

	makeCellsEditable();
}


// Function to add a new row with test data to the table
function addTestData() {
	var scheduleOfQuantitiesId = nextSoqId++;
	var testData = {
		scheduleOfQuantitiesId: scheduleOfQuantitiesId,
		soqTemplateIdUsed: '1',
		projectId: '50',
		projectParticipantIdCreatedBy: '50',
		soqModeCode: '50',
		scheduleName: '50',
		description: '50',
		tenderNumber: '50',
		primaryHeading: '50',
		secondaryHeading: '50',
		tertiaryHeading: '50',
		startDate: '50',
		numberColName: 'Hello World',
		descriptionColName: 20,
		unitColName: 30,
		rateColName: 172,
		qtyColName: 100,
		amountColName: 100,
		isLocked: false
	};

	// Add the new row to the table
	soqTable.row.add(testData).draw();

	makeCellsEditable();
}


//closeTripLoggerDialog -- Start
function closeTripLoggerDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("tripLoggerDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("tripLoggerDialog");
	}
	somethingChangedInDialog = false;
}
//closeTripLoggerDialog -- End

//tripLoggerDialogChanged -- Start
function tripLoggerDialogChanged() {
	setElementEnabled("saveTripLoggerDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;

}
//tripLoggerDialogChanged -- End

//makeCellsEditable -- start
function makeCellsEditable() {
	$('#soqTable tbody').on('click', 'tr td', function(e) {
		if ($(this).hasClass('editing') || $(this).index() === 7 /* index of the amount column */) {
			return; // Already in editing mode or clicking on the amount column, do nothing
		}

		$(this).addClass('editing'); // Add a class to mark that the cell is in editing mode

		var cell = $(this);
		var originalContent = cell.text().trim();

		// Get the row data
		var rowData = soqTable.row(cell.closest('tr')).data();

		// Check if the row is locked
		if (rowData.isLocked) {
			return;
		}

		// Create an input element with the current content
		var input = $('<input type="text" class="form-control" value="' + originalContent + '">');

		// Set specific width and height for the input field
		input.css({
			'width': '100px',
			'height': '25px'
		});

		// Store the original content in a data attribute
		cell.data('originalContent', originalContent);

		// Replace the cell content with the input
		cell.html(input);

		// Focus on the input
		input.focus();

		// Set the cursor at the end of the input value
		var inputValLength = input.val().length;
		input[0].setSelectionRange(inputValLength, inputValLength);

		// Attach a focusout event to the input for handling when it loses focus
		input.on('focusout', function() {

			saveChanges(cell.closest('tr'), input);

			// Find the input element within the cell
			var inputElement = cell.find('input');

			// Remove the input and restore original content
			var newContent = input.val().trim();
			cell.html(newContent);
			input.remove();
			cell.removeClass('editing');

		});

		// Attach a click event to the document to handle removing the input when clicking outside of it
		$(document).one('click', function(event) {
			if (!cell.is(event.target) && cell.has(event.target).length === 0) {
				// Clicked outside the input, trigger the focusout event
				input.trigger('focusout');
			}
		});

		e.stopPropagation(); // Stop the event from propagating
	});
}
//makeCellsEditable -- End


//saveChanges -- Start
function saveChanges(row, editedInput) {

	var rowData = soqTable.row(row).data();

	var editedValue = editedInput.val().trim();
	var editedFieldName = getFieldName(editedInput);

	console.log(editedFieldName);

	// Update the corresponding field in the rowData
	rowData[editedFieldName] = editedValue;

	// Send the updated data to the database for updating
	

	console.log('Updated row data:', rowData);
}
//saveChanges -- End


// getFiedName -- Start
function getFieldName(cell) {

	var dataTable = $('#soqTable').DataTable();

	// Get the column index of the clicked cell
	var columnIndex = cell.closest('td').index();

	// Get the DataTable column by index
	var column = dataTable.column(columnIndex);

	// Extract the field name from the column
	var fieldName = column.dataSrc();

	return fieldName;
}
// getFiedName -- End


// toggleRowEditability -- Start
function toggleRowEditability(row, isEditable) {
	// Toggle the ability to edit cells in the row
	$(row).find('td:not(:nth-child(' + amountCol + '))').each(function() {
		var cell = $(this);

		if (isEditable) {
			// Make cells editable
			cell.html('<input type="text" class="form-control" value="' + cell.text().trim() + '">');
			console.log("isEditable")

		} else {
			// Restore original content and remove input
			var newContent = cell.find('input').val();
			cell.html(newContent);
			console.log(newContent)
		}
	});

	// Toggle the "editing" class based on the isEditable parameter
	$(row).toggleClass('editing', isEditable);
}
// toggleRowEditability -- End







//**********************************************************

// ***********************************************************************


$(document).ready(function() {

	initializeSoqTable();
	makeCellsEditable();

});

$(document).ready(function() {
	// Handle the click event of the Add Test Data button
	$('#addTestDataBtn').on('click', function() {
		addTestData();
	});
});


