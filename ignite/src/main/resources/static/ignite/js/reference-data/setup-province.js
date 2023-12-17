var provinceTable;
var somethingChangedInDialog = null;
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed

function initializeProvinceTable() {

	var columnsArray = [                      //Om 'n grid vol te maak.
     		 { data: "provinceId" }  
     		,{ data: "provinceCode" }           //0 MySql-TableName: VProvince
     		,{ data: "countryId" }              //1
     		,{ data: "countryNameAndCode" }         //2
     		,{ data: "name" }                     //3
     	];

     	var columnDefsArray = [
     		{
     			visible: false,
     			targets: [0, 2]
     		}
     	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editProvince(null);
			}
		},
		{
			attr: {
				id: "deleteProvinceBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteProvince();
			}
		}
	];

	provinceTable = initializeGenericTable(
		"provinceTable", 			// tableElementName 
		"/rest/ignite/v1/province/find-all-in-view",	// queryUrl
		columnsArray,				// columnsArray
		columnDefsArray,			// columnDefsArray
		buttonsArray,				// buttonArray
		function(roleSelector) {	// callbackMethod
			editProvince(roleSelector);
		},
		null,						// completeMethod
		35,							// pageLength
		[[2,"asc"]] 				// Order by column 0 ascending, normally defaults to column 1 ascending
	);


	provinceTable.off('deselect');
	provinceTable.on('deselect', function(e, dt, type, indexes) {
		updateProvinceToolbarButtons();
	});

	provinceTable.off('select');
	provinceTable.on('select', function(e, dt, type, indexes) {
		updateProvinceToolbarButtons();
	});
	
	

	// to initially set the buttons
	updateProvinceToolbarButtons();
}

function updateProvinceToolbarButtons() {
	var hasSelected = provinceTable.rows('.selected').data().length > 0;

	setTableButtonState(provinceTable, "deleteProvinceBtn", hasSelected);
}

function editProvince(rowSelector) {
	
	var provinceCode = "";
	var name = "";
	var data = {}; // Give it an empty object
	
	provinceTable.rows().deselect();
	console.log("Province Code TEst = "+ provinceCode)

	//  MySql-TableName: VProvince										   (js3Str)
	$("#spProvinceId").val(data.provinceId);                     //0
	$("#spProvinceCode").val(data.provinceCode);                     //0
	$("#spCountryId").val(data.countryId);                       //1

	$("#spCountryId_Name").val(data.countryNameAndCode);             //2
	$("#spName").val(data.name);                                     //3

	// Set the Save Button to disabled
	setElementEnabled("saveProvinceButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("provinceDialog");
}


function provinceDialogChanged() {
	setElementEnabled("saveProvinceButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}


function saveProvince() {
	
	var postUrl = "/rest/ignite/v1/province/new";
	var postData = {
		provinceId : $("#spProvinceId").val()             //0 MySql-TableName: Province
		,provinceCode : $("#spProvinceCode").val()             //0 MySql-TableName: Province
		,countryId : $("#spCountryId").val()                                    //1
		,name : $("#spName").val()                                                  //2
	};

	var errors = "";

	if ((postData.provinceCode == null) || (postData.provinceCode == "")) {
		errors += "A Province Code is required<br>";
	}

	if ((postData.countryId == null) || (postData.countryId == "")) {
		errors += "A Country is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Province Name is required<br>";
	}

	if (showFormErrors("spDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.provinceId != null) && (postData.provinceId != "")) {	
		// This is an update 
		postUrl = "/rest/ignite/v1/province";
	} else {
		postData.provinceId = null;  //empty string werk nie
	}	

	saveEntry(postUrl, postData, "provinceDialog", "The Province has been saved.", provinceTable);
}


function promptDeleteProvince() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected Province?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteProvince();
		}
	);
}

function deleteProvince() {
	var postUrl = "/rest/ignite/v1/province/delete";
	var row = provinceTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Province has been deleted.", provinceTable,
		function() {
			provinceTable.rows(".selected").nodes().to$().removeClass("selected");
			updateProvinceToolbarButtons();
		}
	);
}

function closeProvinceDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("spDlgErrorDiv", "none");
				closeModalDialog("provinceDialog");
			});
	} else {
		setDivVisibility("spDlgErrorDiv", "none");
		closeModalDialog("provinceDialog");
	}
}




function editSelectCountryCode() {
	selectCountry("spCountryId", "spCountryId_Name");
	provinceDialogChanged();
}

function selectCountry(targetId, targetName) {
	
	// var queryUrl="/rest/ignite/v1/expense-type";
	var queryUrl="/rest/ignite/v1/country/list-country-order-by-name"
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="countryId";
			var refColumnName="name";
			var columns = [
			       		{ data: "countryId", name: "countryId" }                       //0 MySql-TableName: Country
			       		,{ data: "countryNameAndCode", name: "Name" }                                     //1
			       	];

			       	var columnDefs = [
			       		{
			       			visible: false,
			       			targets: [0]
			       		}
			       	];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.countryId;
				var repName = row.name; 

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectCountry





// ***********************************************************************

$(document).ready(function() {
	initializeProvinceTable();
	showIgDeveloperOption();
});
