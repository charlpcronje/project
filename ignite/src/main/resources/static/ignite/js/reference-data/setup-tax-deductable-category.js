var taxDeductableCategoryTable = null;

function initializeTaxDeductableCategoryTable() {
	var columnsArray = [
		{ data: "taxDeductableCategoryId" },
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
				editTaxDeductableCategory(null);
			}
		},
		{
			attr : {
				id: "deleteTaxDeductableCategoryBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteTaxDeductableCategory();
			}
		}
	];
	
	taxDeductableCategoryTable = initializeGenericTable("taxDeductableCategoryTable", 
			                            "/rest/ignite/v1/tax-deductable-category",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editTaxDeductableCategory(rowSelector);
										},
			                    		null,
			                    		31,
			                    		[1,"asc"]
		);

	taxDeductableCategoryTable.off('deselect');
	taxDeductableCategoryTable.on('deselect', function (e, dt, type, indexes) {
		updateTaxDeductableCategoryToolbarButtons();
	} );

	taxDeductableCategoryTable.off('select');
	taxDeductableCategoryTable.on('select', function (e, dt, type, indexes) {
		updateTaxDeductableCategoryToolbarButtons();
	} );

	// to initially set the buttons
	updateTaxDeductableCategoryToolbarButtons();
}

function updateTaxDeductableCategoryToolbarButtons() {
	var hasSelected = taxDeductableCategoryTable.rows('.selected').data().length > 0;
	
	setTableButtonState(taxDeductableCategoryTable, "deleteTaxDeductableCategoryBtn", hasSelected);
}


function editTaxDeductableCategory(rowSelector) {
	var data = {};
	
	if (rowSelector != null) {
		data = taxDeductableCategoryTable.row(rowSelector).data();
	}
	taxDeductableCategoryTable.rows().deselect();
	
	$("#cDlgTaxDeductableCategoryId").val(data.taxDeductableCategoryId);
	$("#cDlgName").val(data.name);
	$("#cDlgDescription").val(data.description);
	
	// Set the Save Button to disabled
	setElementEnabled("saveTaxDeductableCategoryButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("taxDeductableCategoryDialog");
}


function promptDeleteTaxDeductableCategory() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Tax Deductable Category?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteTaxDeductableCategory();
			   }
	);
}


function deleteTaxDeductableCategory() {
	var postUrl = "/rest/ignite/v1/tax-deductable-category/delete";
	var row = taxDeductableCategoryTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Tax Deductable Category has been deleted.", taxDeductableCategoryTable,
			function(){	
				taxDeductableCategoryTable.rows(".selected").nodes().to$().removeClass("selected");
				updateTaxDeductableCategoryToolbarButtons();
			}
	);
}


function saveTaxDeductableCategory() {
	var postUrl = "/rest/ignite/v1/tax-deductable-category/new";
	var mode = $("#cDlgMode").val();
	var taxDeductableCategoryId = $("#cDlgTaxDeductableCategoryId").val();
	var name = $("#cDlgName").val();
	var description = $("#cDlgDescription").val();
	var errors = "";
	
	var postData = {
		mode: mode,
		taxDeductableCategoryId : taxDeductableCategoryId,
		name : name,
		description: description	
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}
	
	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}
	if ((postData.taxDeductableCategoryId != null) && (postData.taxDeductableCategoryId != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/tax-deductable-category";
	} else {
		postData.taxDeductableCategoryId = null;  //empty string werk nie
	}
	saveEntry(postUrl, postData, "taxDeductableCategoryDialog", "The Tax Deductable Category has been saved.", taxDeductableCategoryTable);
}

//taxDeductableCategoryDialogChanged -- Start
function taxDeductableCategoryDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveTaxDeductableCategoryButton", true);
}
//taxDeductableCategoryDialogChanged -- End

//closeTaxDeductableCategoryDialog -- Start
function closeTaxDeductableCategoryDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("taxDeductableCategoryDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("taxDeductableCategoryDialog");
	}
}//closeTaxDeductableCategoryDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeTaxDeductableCategoryTable();
	showIgDeveloperOption();
} );
