var expenseTypeParentTable = null;
var subTypeTable = null;
var somethingChangedInDialog = null;
var askToSaveDialog = null;

// Expense Type Parent - Start
function initializeExpenseTypeParentTable() {
	hideSubTypeTable();

	var columnArray = [
		{ data: "expenseTypeParentId" },
		{ data: "name" },
		{ data: "description" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,2]
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = shortenText(data, 25);
				}
				return data;
			},
			targets: 2
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editExpenseTypeParent(null);
			}
		},
		{
			attr: {
				id: "promptDeleteExpenseTypeParentBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteExpenseTypeParent();
			}
		}
	];

	expenseTypeParentTable = initializeGenericTable("expenseTypeParentTable",
		"/rest/ignite/v1/expense-type-parent",
		columnArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editExpenseTypeParent(rowSelector);
		},
		null,
		31,
		[1,"asc"],
		null,
		null,
		false
	);

	expenseTypeParentTable.off('deselect');
	expenseTypeParentTable.on('deselect', function() {
		hideSubTypeTable();
		updateExpenseTypeParentToolbarButtons();
	});

	expenseTypeParentTable.off('select');
	expenseTypeParentTable.on('select', function(e, dt, node, config) {
		//		console.log("Nou hier:initializeSubTypeTable");
		initializeSubTypeTable(dt.data());
		updateExpenseTypeParentToolbarButtons();
	});

	updateExpenseTypeParentToolbarButtons();
}


function updateExpenseTypeParentToolbarButtons() {
	var hasSelected = expenseTypeParentTable.rows('.selected').data().length > 0;

	setTableButtonState(expenseTypeParentTable, "promptDeleteExpenseTypeParentBtn", hasSelected);
}


function editExpenseTypeParent(rowSelector) {
	var data = {
		ExpenseTypeParentId: null
	};

	expenseTypeParentTable.rows().deselect();

	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = expenseTypeParentTable.row(rowSelector).data();
	}

	$("#etpDlgExpenseTypeParentId").val(data.expenseTypeParentId);
	$("#etpDlgName").val(data.name);
	$("#etpDlgDescription").val(data.description);

	// Set the Save Button to disabled
	setElementEnabled("saveExpenseTypeParentButton", false);
	var somethingChangedInDialog = false;
	var askToSaveDialog = false;

	showModalDialog("expenseTypeParentDialog");
}


function expenseTypeParentChanged() {
	setElementEnabled("saveExpenseTypeParentButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}


function closeExpenseTypeParent() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("expenseTypeParentDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("expenseTypeParentDialog");
	}
}


function saveExpenseTypeParent() {
	var postUrl = "/rest/ignite/v1/expense-type-parent/new";
	var postData = {
		expenseTypeParentId: $("#etpDlgExpenseTypeParentId").val().trim().toUpperCase(),
		name: $("#etpDlgName").val(),
		description: $("#etpDlgDescription").val(),
	};

	var errors = "";

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A name is required<br>";
	}

	// validation...
	if (showFormErrors("etpDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.expenseTypeParentId != null) && (postData.expenseTypeParentId != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/expense-type-parent";
	} else {
		postData.expenseTypeParentId = null;  //empty string werk nie
	}

	//saveEntry(postUrl, postData, "expenseTypeParentDialog", "The Expense Parent Type has been saved.", expenseTypeParentTable);
	//expenseTypeParentTable.rows(".selected").nodes().to$().removeClass("selected");
	//updateExpenseTypeParentToolbarButtons();

	saveEntry(postUrl, postData, "expenseTypeParentDialog", "The Expense Parent Type has been saved.", expenseTypeParentTable);
	// Select the newly saved record 
}

function promptDeleteExpenseTypeParent() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Expense Type Parent?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteExpenseTypeParent(expenseTypeParentTable);
		}
	);
}

function deleteExpenseTypeParent(tbl) {
	var postUrl = "/rest/ignite/v1/expense-type-parent/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Expense Type Parent has been deleted.", tbl,
		function() {
			tbl.rows().deselect();
			updateExpenseTypeParentToolbarButtons();
		}
	);
}

// Expense Type Parent - Start

// Expense Type (Sub Type) - Start

function hideSubTypeTable() {
	setDivVisibility("subTypeEmptyPanel", "block");
	setDivVisibility("subTypePanel", "none");
}



function initializeSubTypeTable(row) {

	setDivVisibility("subTypeEmptyPanel", "none");
	setDivVisibility("subTypePanel", "block");

	var columnArray = [
		{ data: "expenseTypeId" },            //0 MySql-TableName: ExpenseType
		{ data: "expenseTypeParentId" },    //1
		{ data: "unitTypeCode" },             //2
		{ data: "expenseTypeId" },          //3
		{ data: "name" },                     //4
		{ data: "description" },              //5
		{ data: "allowanceFlag" },            //6
		{ data: "allowHandlingPerc" },        //7
		{ data: "allowMaxAmtPerUnit" },       //8                 

	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3]
		},
		//		{
		//			render: function(data, type, row) {
		//				if (type == "display") {
		//					data = shortenText(data, 25);
		//				}
		//				return data;
		//			},
		//			targets: 2
		//		},

		{
			render: function(data, type, row) {
				if (type == "display") {
					if (row.unitType != null) {
						data = row.unitType.name;
					}
				}

				return data;
			},
			targets: 2
		},

		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowanceFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 6
		}, {
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowHandlingPerc == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 7
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowMaxAmtPerUnit == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 8
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editExpenseType(null, row.expenseTypeParentId);
			}
		},
		{
			attr: {
				id: "promptDeleteSubTypeBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteSubType();
			}
		}
	];

	console.dir("/rest/ignite/v1/expense-type/by-parent/" + row.expenseTypeParentId);
	
	subTypeTable = initializeGenericTable("subTypeTable",
		"/rest/ignite/v1/expense-type/by-parent/" + row.expenseTypeParentId,
		columnArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editExpenseType(rowSelector, null);
		},
		null,
		31,
		[1,"asc"]
	);

	subTypeTable.off('deselect');
	subTypeTable.on('deselect', function(e, dt, type, indexes) {
		updateSubTypeToolbarButtons();
	});

	subTypeTable.off('select');
	subTypeTable.on('select', function(e, dt, type, indexes) {
		updateSubTypeToolbarButtons();
	});

	updateSubTypeToolbarButtons();
}

function updateSubTypeToolbarButtons() {
	var hasSelected = subTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(subTypeTable, "promptDeleteSubTypeBtn", hasSelected);
}

function promptDeleteSubType() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Sub Type?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteExpenseType(subTypeTable);
		}
	);
}


function allowanceClicked() {

	var chk = document.getElementById("eDlgAllowanceFlag").checked;

	if (chk == true) {
		$("#eDlgAllowHandlingPerc").prop('checked', false);
		$("#eDlgAllowMaxAmtPerUnit").prop('checked', false);
	} else {
		$("#eDlgAllowHandlingPerc").prop('checked', true);
		$("#eDlgAllowMaxAmtPerUnit").prop('checked', true);
	}

}

function editExpenseType(rowSelector, currentParentCode) {
	var data = {
		ExpenseTypeParentId: null
	};

	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = subTypeTable.row(rowSelector).data();
	}

	subTypeTable.rows().deselect();

	var isNew = (data.expenseTypeId == null) || (data.expenseTypeId == "");

	$("#eDlgExpenseTypeId").val(data.expenseTypeId);
	$("#eDlgExpenseTypeId").val(data.expenseTypeId);
	$("#eDlgName").val(data.name);
	$("#eDlgDescription").val(data.description);
	$("#eDlgAllowanceFlag").prop("checked", data.allowanceFlag == "Y");
	$("#eDlgAllowHandlingPerc").prop("checked", data.allowHandlingPerc == "Y");
	$("#eDlgAllowMaxAmtPerUnit").prop("checked", data.allowMaxAmtPerUnit == "Y");

	if (isNew) {
		$("#eDlgAllowanceFlag").prop('checked', false);
		$("#eDlgAllowHandlingPerc").prop('checked', true);
		$("#eDlgAllowMaxAmtPerUnit").prop('checked', true);
	}

	populateSelect("eDlgExpenseTypeParentId",
		"/rest/ignite/v1/expense-type-parent",
		"expenseTypeParentId",
		"name",
		(isNew ? currentParentCode : data.expenseTypeParentId),
		true,
		null);

	populateSelect("eDlgUnitTypeCode",
		"/rest/ignite/v1/unit-type",
		"unitTypeCode",
		"name",
		data.unitTypeCode,
		true,
		null);
	
	// Set the Save Button to disabled
	setElementEnabled("saveExpenseSubTypeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("expenseTypeDialog");
}

function expenseSubTypeChanged() {
	// Set the Save Button to disabled
	setElementEnabled("saveExpenseSubTypeButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}

function promptDeleteExpenseType() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Expense Type?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteExpenseType(expenseTypeTable);
		}
	);
}

function closeExpenseSubType() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("expenseTypeDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("expenseTypeDialog");
	}
}


function deleteExpenseType(tbl) {
	var postUrl = "/rest/ignite/v1/expense-type/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Expense Type has been deleted.", tbl,
		function() {
			tbl.rows().deselect();
			updateSubTypeToolbarButtons();
		}
	);
}

function saveExpenseType() {
	var postUrl = "/rest/ignite/v1/expense-type/new";
	var postData = {
		expenseTypeId: $("#eDlgExpenseTypeId").val(),
		expenseTypeId: $("#eDlgExpenseTypeId").val().trim().toUpperCase(),
		expenseTypeParentId: $("#eDlgExpenseTypeParentId").val().trim().toUpperCase(),
		name: $("#eDlgName").val(),
		description: $("#eDlgDescription").val(),
		unitTypeCode: $("#eDlgUnitTypeCode").val(),
		allowanceFlag: $("#eDlgAllowanceFlag").is(":checked") ? "Y" : "N",
		allowHandlingPerc: $("#eDlgAllowHandlingPerc").is(":checked") ? "Y" : "N",
		allowMaxAmtPerUnit: $("#eDlgAllowMaxAmtPerUnit").is(":checked") ? "Y" : "N"
	};

	var errors = "";

	// validate

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Name is required<br>";
	}

	if ((postData.expenseTypeParentId == null) || (postData.expenseTypeParentId == "")) {
		errors += "An Expense Type Parent Code is required<br>";
	}

	if ((postData.unitTypeCode == null) || (postData.unitTypeCode == "")) {
		errors += "A Unit Type is required<br>";
	}

	// validation...
	if (showFormErrors("eDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.expenseTypeId != null) && (postData.expenseTypeId != "")) {
		// This is an update
		postUrl = "/rest/ignite/v1/expense-type";
	} else {
		postData.expenseTypeId = null;  //empty string werk nie
	}
	//	if ((isTopLevel === undefined) || (isTopLevel == null)) {
	//		isTopLevel = true;
	//	}
	console.log(postUrl);
	//	var tbl = isTopLevel ? expenseTypeTable : subTypeTable;
	saveEntry(postUrl, postData, "expenseTypeDialog", "The Expense Type has been saved.", subTypeTable);
}

// Expense Type (Sub Type) - End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeExpenseTypeParentTable();
	showIgDeveloperOption();
});
