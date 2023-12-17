var dataTable = null;

function initializeTable() {
	var columnsArray = [
		{ data: "paymentTypeId" },
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
				editDetail(null);
			}
		},
		{
			attr : {
				id: "deleteBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDelete();
			}
		}
	];

	
	dataTable = initializeGenericTable("dataTable", 
			                            "/rest/ignite/v1/payment-type",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editDetail(rowSelector);
										});

	dataTable.off('deselect');
	dataTable.on('deselect', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	dataTable.off('select');
	dataTable.on('select', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	// to initially set the buttons
	updateToolbarButtons();
}

function updateToolbarButtons() {
	var hasSelected = dataTable.rows('.selected').data().length > 0;
	
	setTableButtonState(dataTable, "deleteBtn", hasSelected);
}

function editDetail(rowSelector) {
	var paymentTypeId = "";
	var name = "";
	var description = "";
	
	if (rowSelector != null) {
		var data = dataTable.row(rowSelector).data();
		paymentTypeId = data.paymentTypeId;
		name = data.name;
		description = data.description;
	}
	dataTable.rows().deselect();
	
	$("#dDlgPaymentTypeId").val(paymentTypeId);
	$("#dDlgName").val(name);
	$("#dDlgDescription").val(description);

	// Set the Save Button to disabled
	setElementEnabled("savePaymentTypeButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("detailDialog");
}

function promptDelete() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Payment Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRow();
			   }
	);
}

function deleteRow() {
	var postUrl = "/rest/ignite/v1/payment-type/delete";
	var row = dataTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Payment Type has been deleted.", dataTable,
		function(){	
			dataTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function saveRecord() {
	var postUrl = "/rest/ignite/v1/payment-type/new";
	var postData = {
			paymentTypeId: $("#dDlgPaymentTypeId").val(),
			name: $("#dDlgName").val(),
			description: $("#dDlgDescription").val()
		};
	var errors = "";
	
	// validate
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("dDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.paymentTypeId != null) && (postData.paymentTypeId != "")) {	
		// This is an update
		postUrl = "/rest/ignite/v1/payment-type";
	} else {
		postData.paymentTypeId = null;  //empty string werk nie
	}
	
	saveEntry(postUrl, postData, "detailDialog", "The Payment Type has been saved.", dataTable);
}

//paymentTypeDialogChanged -- Start
function paymentTypeDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("savePaymentTypeButton", true);
}
//paymentTypeDialogChanged -- End

//closePaymentTypeDialog -- Start
function closePaymentTypeDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("detailDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("detailDialog");
	}
}//closePaymentTypeDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTable();
} );
