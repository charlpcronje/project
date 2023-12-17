var apiTable = null;

function initializeApiTable() {
	var columnsArray = [
		{ data: "apiId" },
		{ data: "applicationName" },
		{ data: "apiKey" },
		{ data: "activeFlag"}
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: 0
		},
		{
			width: "200px",
			targets: 1
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					var checkedStatus = data == "Y" ? " checked " : "";
					data = "<input type='checkbox' " + checkedStatus + " readonly onclick='return false;'>";
				}
				return data;
			},
			width: "100px",
			className: "dt-center",
			targets: 3
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editApi(null);
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
				promptDeleteApi();
			}
		}
	];

	apiTable = initializeGenericTable("apiTable", 
			                            "/rest/ignite/v1/api",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editApi(aThis);
										}
	);

	apiTable.off('deselect');
	apiTable.on('deselect', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	apiTable.off('select');
	apiTable.on('select', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );
	
	updateToolbarButtons();
}

function updateToolbarButtons() {
	var hasSelected = apiTable.rows('.selected').data().length > 0;

	setTableButtonState(apiTable, "deleteBtn", hasSelected);	
}

function editApi(aThis) {
	var data = {};
	
	if (aThis != null) {
		// get values from table
		data = apiTable.row(aThis).data();
	}
	apiTable.rows().deselect();
	
	$("#aDlgApiId").val(data.apiId);
	$("#aDlgApiKey").val(data.apiKey);
	$("#aDlgApplicationName").val(data.applicationName);
	$("#aDlgSecret").val(data.secret);  //TODO: obfuscate this...
	$("#aDlgActiveFlag").prop("checked", data.activeFlag == "Y");
	
	// disable the key if update, enable if insert
	$("#aDlgApiKey").prop("readonly", (data.apiKey != null) && (data.apiKey != ""));

	showModalDialog("apiDialog");
}

function saveApi() {
	var postUrl = "/rest/ignite/v1/api";
	var postData = {
			apiId : $("#aDlgApiId").val(),
			apiKey : $("#aDlgApiKey").val(),
			applicationName: $("#aDlgApplicationName").val(),
			secret: $("#aDlgSecret").val(),
			activeFlag: ($("#aDlgActiveFlag").is(":checked") ? "Y" : "N")
	};
	var errors = "";
	
	// TODO: validation...
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}
	
	saveEntry(postUrl, postData, "apiDialog", "The API has been saved.", apiTable);
}

function newGuid() {
	var sGuid="";
	for (var i=0; i<32; i++) {
    	sGuid+=Math.floor(Math.random()*0xF).toString(0xF);
	}
	return sGuid;
}

function generateRandomKey() {
	var newKey = newGuid();

	$("#aDlgSecret").val(newKey);
}

function promptDeleteApi() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected API?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteApi();
			   }
	);
}

function deleteApi() {
	var postUrl = "/rest/ignite/v1/api/delete";
	var row = apiTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The API has been deleted.", apiTable,
			function(){	
				apiTable.rows(".selected").nodes().to$().removeClass("selected");
				updateToolbarButtons();
			}
	);
}


// ***********************************************************************

$(document).ready(function() {
	initializeApiTable();
} );
