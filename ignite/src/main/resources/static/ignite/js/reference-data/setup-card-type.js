var cardTypeTable = null;

function initializeCardTypeTable() {
	
	var columnsArray = [
		{ data: "cardTypeId" },
		{ data: "cardTypeCode" },
		{ data: "name" },
		{ data: "description" }
	];

   	var columnDefsArray = [
		{
			visible: false,
			targets: [ 0]
		}
	];	
   	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				setupEditCardType(null);
			}
		},
		{
			attr : {
				id: "deleteCardTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteCardType();
			}
		}
	];

	cardTypeTable = initializeGenericTable("cardTypeTable", 
			                            "/rest/ignite/v1/card-type",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											setupEditCardType(rowSelector);
										},
										null,
										31,
										[1,"asc"]);

	cardTypeTable.off('deselect');
	cardTypeTable.on('deselect', function (e, dt, type, indexes) {
		updateCardTypeToolbarButtons();
	} );

	cardTypeTable.off('select');
	cardTypeTable.on('select', function (e, dt, type, indexes) {
		updateCardTypeToolbarButtons();
	} );

	// to initially set the buttons
	updateCardTypeToolbarButtons();
}


function updateCardTypeToolbarButtons() {
	var hasSelected = cardTypeTable.rows('.selected').data().length > 0;
	
	setTableButtonState(cardTypeTable, "deleteCardTypeBtn", hasSelected);
}


function setupEditCardType(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = cardTypeTable.row(rowSelector).data();
	}
	cardTypeTable.rows().deselect();
	
	// Set the Save Button to disabled
	setElementEnabled("saveCardTypeButton", false);
	var somethingChangedInDialog = false;
	var askToSaveDialog = false;
	
	showCardTypeDialog(data);
}


function cardTypeChanged() {
	setElementEnabled("saveCardTypeButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}


function promptDeleteCardType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Card Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteCardType();
			   }
	);
}


function deleteCardType() {
	var postUrl = "/rest/ignite/v1/card-type/delete";
	var row = cardTypeTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Card Type has been deleted.", cardTypeTable,
			function(){	
				cardTypeTable.rows(".selected").nodes().to$().removeClass("selected");
				updateCardTypeToolbarButtons();
			}
	);
}




// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	
	// load our dialog...
//	$("#cardTypeDialog-placeholder").load("cardTypeDialog.html");

	initializeCardTypeTable();
	showIgDeveloperOption();
} );
