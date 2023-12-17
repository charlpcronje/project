var healthTable = null;
var parameterTable = null;

function initializeParameterTable() {
	var columnsArray = [
    	{ data: "keyName" },
    	{ data: "value" }
    ];
	
	var columnDefsArray = [
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = htmlEncode(data);
					data = shortenText(data, 60);
				}
				
				return data;
			},
			targets: 1
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editParameter(null);
			}
		},
		{
			attr : {
				id: "deleteParameterBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteParameter();
			}
		}
	];

	parameterTable = initializeGenericTable("parameterTable", 
			                            "/rest/ignite/v1/parameter",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editParameter(aThis);
										}
	);

	parameterTable.off('deselect');
	parameterTable.on('deselect', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	parameterTable.off('select');
	parameterTable.on('select', function (e, dt, type, indexes) {
		updateToolbarButtons();
	} );

	// to initially set the buttons
	updateToolbarButtons();
}

function updateToolbarButtons() {
	var hasSelected = parameterTable.rows('.selected').data().length > 0;
	
	setTableButtonState(parameterTable, "deleteParameterBtn", hasSelected);
}

function editParameter(aThis) {
	var keyName = "";
	var value = "";

	if (aThis != null) {
		// get values from table
		var data = parameterTable.row(aThis).data();
		keyName = data.keyName;
		value = data.value;
	}
	parameterTable.rows().deselect();
	
	$("#pDlgKeyName").val(keyName);
	$("#pDlgValue").val(value);

	// disable the key if update, enable if insert
	$("#pDlgKeyName").prop("readonly", (keyName != null) && (keyName != ""));
		
	showModalDialog("parameterDialog");
}

function promptDeleteParameter() {
	var rows = parameterTable.rows('.selected').data();
	var row = rows[0];
	
	showDialog("Delete?",
		       "Do you wish to delete the selected Parameter?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
		           deleteParameter(row);
			   }
	);
}

function deleteParameter(row) {
	var postUrl = "/rest/ignite/v1/parameter/delete";
    
	var postData = {
			keyName: row.keyName
	};

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, postData, null, "The Parameter has been deleted.", parameterTable,
		function(){	
			parameterTable.rows(".selected").nodes().to$().removeClass("selected");
			updateToolbarButtons();
		}
	);
}

function saveParameter() {
	var postUrl = "/rest/ignite/v1/parameter";
    var keyName = $("#pDlgKeyName").val();
    var value = $("#pDlgValue").val();
    
	var errors = "";

	if ((keyName == "") || (keyName.trim() == "")) { errors += "A Key is required<br>"; }
	
	if (showFormErrors("pDlgErrorDiv", errors)) {
		return;
	}
	
	var postData = {
			keyName: keyName,
			value: value
	};

	saveEntry(postUrl, postData, "parameterDialog", "The Parameter has been saved.", parameterTable);
}

function makeReadOnly(elementId, isReadOnly) {
	$("#" + elementId).prop("readonly", isReadOnly);
	if (isReadOnly) {
		$("#" + elementId).addClass("disabled");
	} else {
		$("#" + elementId).removeClass("disabled");
	}
}

function updateMailOptions() {
	var isEnabled = $("#mailEnabledCheckbox").is(":checked");
	var isProxy = $("#mailUseProxyCheckbox").is(":checked");

	makeReadOnly("mailServerNameInput", !isEnabled);
	makeReadOnly("mailServerPortInput", !isEnabled);

	makeReadOnly("smtpUsernameInput", !isEnabled);
	makeReadOnly("smtpPasswordInput", !isEnabled);

	makeReadOnly("mailUseProxyCheckbox", !isEnabled),
	makeReadOnly("mailProxyServerNameInput", (!isEnabled) && (!isProxy) );
	makeReadOnly("mailProxyServerPortInput", (!isEnabled) && (!isProxy) );
}

function saveMailSettings() {
	var postUrl = "/rest/ignite/v1/parameter/mail";
	var postData = {
			mailEnabled: $("#mailEnabledCheckbox").is(":checked") ? "Y" : "N",
			mailServerName: $("#mailServerNameInput").val(),
			mailServerPort: $("#mailServerPortInput").val(),
			
			mailSmtpUsername: $("#smtpUsernameInput").val(),
			mailSmtpPassword: $("#smtpPasswordInput").val(),
			
			mailProxyEnabled: $("#mailUseProxyCheckbox").is(":checked") ? "Y" : "N",
			mailProxyServerName: $("#mailProxyServerNameInput").val(),
			mailProxyServerPort: $("#mailProxyServerPortInput").val()
	};
	
	saveEntry(postUrl, postData, null, "The mail settings have been saved.", null);
}

function testMailSettings() {
	var postUrl = "/rest/ignite/v1/parameter/mail/test";
	var postData = {
			mailEnabled: $("#mailEnabledCheckbox").is(":checked") ? "Y" : "N",
			mailServerName: $("#mailServerNameInput").val(),
			mailServerPort: $("#mailServerPortInput").val(),
			
			mailSmtpUsername: $("#smtpUsernameInput").val(),
			mailSmtpPassword: $("#smtpPasswordInput").val(),
			
			mailProxyEnabled: $("#mailUseProxyCheckbox").is(":checked") ? "Y" : "N",
			mailProxyServerName: $("#mailProxyServerNameInput").val(),
			mailProxyServerPort: $("#mailProxyServerPortInput").val()
	};

	// TODO: we need some sort of please wait message here... and then it must be cleared by the save entry
	saveEntry(postUrl, postData, null, "A test mail has been sent.", null);
}

function initializeMailOptions() {
	// get from settings
	$("#mailEnabledCheckbox").prop("checked", _igModel.mailEnabled),
	$("#mailServerNameInput").val(_igModel.mailServerName);
	$("#mailServerPortInput").val(_igModel.mailServerPort);
	
	$("#smtpUsernameInput").val(_igModel.mailSmtpUsername);
	$("#smtpPasswordInput").val(_igModel.mailSmtpPassword);

	$("#mailUseProxyCheckbox").prop("checked", _igModel.mailProxyEnabled),
	$("#mailProxyServerNameInput").val(_igModel.mailProxyServerName);
	$("#mailProxyServerPortInput").val(_igModel.mailProxyServerPort);
	
	// So that our inputs show the right state
	updateMailOptions();
	updateProxyOptions();
}

function updateProxyOptions() {
	var isEnabled = $("#mailUseProxyCheckbox").is(":checked");

	makeReadOnly("mailProxyServerNameInput", !isEnabled);
	makeReadOnly("mailProxyServerPortInput", !isEnabled);
}

function initializeHealthTable() {
	var columnsArray = [
		{ data: "componentName" },
		{ data: "description" },
		{ data: "suggestedAction" },
		{ data: "lastUpdateTimestamp" },
		{ data: "lastUpdateUserName" },
		{ data: "ragStatus" }
	];
	
	var columnDefsArray = [
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = htmlEncode(data);
					data = shortenText(data, 50);
				}
				
				return data;
			},
			width: "300px",
			targets: [1, 2]
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = timestampToString(data, true);
				}
				
				return data;
			},
			targets: 3
		},
		{
			visible: false,
			targets: 4
		},
		{
			render: function(data, type, row) {
				if ((type == "display") || (type == "filter")) {
					var html = "<span class='badge badge-success'>Ok</span>";

					if ((data == "a") || (data == "A")) {
						var html = "<span class='badge badge-warning'>Warning(s) detected</span>";
					}
					if ((data == "r") || (data == "R")) {
						var html = "<span class='badge badge-danger'>Issue(s) detected</span>";
					}
					
					return html;
				} else {
					// Render as a value so that we can sort the table according to the status first
					var result = 100;
					
					if ((data == "r") || (data == "R")) { result = 0; }
					if ((data == "a") || (data == "A")) { result = 50; }
						
					return result;
				}
				
				return data;
			},
			className: "dt-center",
			targets: 5
		}
	];

	var buttonsArray = [
		{
			titleAttr: "Run Health Monitor",
			text: "<i class='fas fa-play'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				executeHealthMonitor();
			}
		},
	];

	healthTable = initializeGenericTable("healthTable", 
		                            "/rest/ignite/v1/health",
		                            columnsArray,
		                            columnDefsArray,
		                            buttonsArray,
		                            null,
		                            null,
		                            25,
		                            [[5, "asc"], [0, "asc"]]
	);
}

function executeHealthMonitor() {
	var postUrl = "/rest/ignite/v1/health";
	var postData = {};
	
	showPleaseWait();
	saveEntry(postUrl, postData, null, "The Health Monitor has been run.", healthTable);
}

// ***********************************************************************

$(document).ready(function() {
	setActiveTab("healthTab");
	initializeHealthTable();
	
	// react to tab change and load appropriate data
	$('.nav-tabs a').on('shown.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');
		
		if (activeTab == "Mail") {
			initializeMailOptions();
		}
		
		if (activeTab == "Parameters") {
			initializeParameterTable();
		}
	} );
} );
