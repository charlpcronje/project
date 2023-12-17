var paymentMethodTable = null;

function initializePaymentMethodTable() {
	var columnsArray = [
		{ data: "paymentMethodCode" },
		{ data: "name" }
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
				editPaymentMethod(null);
			}
		},
		{
			attr : {
				id: "deletePaymentMethodBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeletePaymentMethod();
			}
		}
	];
	
	paymentMethodTable = initializeGenericTable("paymentMethodTable", 
			                            "/rest/ignite/v1/payment-method",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editPaymentMethod(rowSelector);
										});

	paymentMethodTable.off('deselect');
	paymentMethodTable.on('deselect', function (e, dt, type, indexes) {
		updatePaymentMethodToolbarButtons();
	} );

	paymentMethodTable.off('select');
	paymentMethodTable.on('select', function (e, dt, type, indexes) {
		updatePaymentMethodToolbarButtons();
	} );

	// to initially set the buttons
	updatePaymentMethodToolbarButtons();
}


function updatePaymentMethodToolbarButtons() {
	var hasSelected = paymentMethodTable.rows('.selected').data().length > 0;
	
	setTableButtonState(paymentMethodTable, "deletePaymentMethodBtn", hasSelected);
}


function editPaymentMethod(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = paymentMethodTable.row(rowSelector).data();
	}
	paymentMethodTable.rows().deselect();
	
	$("#cDlgPaymentMethodCode").val(data.paymentMethodCode);
	$("#cDlgName").val(data.name);
	
	// Set the Save Button to disabled
	setElementEnabled("savePaymentMethodButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("paymentMethodDialog");
}


function paymentMethodChanged() {
	setElementEnabled("savePaymentMethodButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}


function promptDeletePaymentMethod() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Payment Method?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deletePaymentMethod();
			   }
	);
}



function deletePaymentMethod() {
	var postUrl = "/rest/ignite/v1/payment-method/delete";
	var row = paymentMethodTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Payment Method has been deleted.", paymentMethodTable,
			function(){	
				paymentMethodTable.rows(".selected").nodes().to$().removeClass("selected");
				updatePaymentMethodToolbarButtons();
			}
	);
}



function savePaymentMethod() {
	var postUrl = "/rest/ignite/v1/payment-method/new";
	var mode = $("#cDlgMode").val();
	var paymentMethodCode = $("#cDlgPaymentMethodCode").val();
	var name = $("#cDlgName").val();

	var errors = "";
	
	var postData = {
		mode: mode,
		paymentMethodCode : paymentMethodCode,
		name : name	
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}
	if ((postData.paymentMethodCode != null) && (postData.paymentMethodCode != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/payment-method";
	} else {
		postData.paymentMethodCode = null;  //empty string werk nie
	}
	
	saveEntry(postUrl, postData, "paymentMethodDialog", "The Payment Method has been saved.", paymentMethodTable);
}



function closePaymentMethod() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("paymentMethodDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("paymentMethodDialog");
	}
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializePaymentMethodTable();
	showIgDeveloperOption();
} );
