// TODO: Add checkup option - check files on disk vs. files in db, check files in db vs files on disk

var reportTable = null;
var ReportParameterTable = null;

function initializeReportTable() {
	var columnsArray = [
		{ data: "reportId" },
		{ data: "reportFilename" },
		{ data: "reportName" },
		{ data: "reportDescription" },
		{ data: "permissionCodeRequired" },
		{ data: "allowExcelExportInd" },
		{ data: "" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0]
		},
		{
			width: "200px",
			targets: 2
		},
		{
			render: function (data, type, row) {
				if (type == "display") {
					data = shortenText(data, 40);
				}
				
				return data;
			},
			targets: 3
		},
		{
			render: function (data, type, row) {
				if (type == "display") {
					var checkedTxt = "";
					if (data == "Y") {
						checkedTxt = " checked ";
					}
					data = "<input type='checkbox'" + checkedTxt + "onclick='return false;'>";
				}
				return data;
			},
			width: "140px",
			targets: 5
		},
		{
			render: function (data, type, row) {
				var status = "Unknown";
				var badge = "danger";
				
				if (row["activeInd"] == "Y") {
					// active
					if (row["fileExistsInd"] == "N") {
						status = "Missing file";
						badge = "danger";
					} else {
						status = "Valid";
						badge = "success";
					}
				} else {
					// not active...
					if (row["fileExistsInd"] == "N") {
						status = "Missing file";
						badge = "warning";
					} else {
						status = "Inactive";
						badge = "light";
					}
				}
				
				if (type == "display") {
					return "<span class='badge badge-" + badge + "'>" + status + "</span>";
				}

				return status;	
			},
			className: "dt-center",
			targets: 6
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editReport(null);
			}
		},
		{
			attr : {
				id : "deleteReportBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteReport();
			}
		},
		{
			attr : {
				id : "reportParametersBtn"
			},
			titleAttr: "Report Parameters",
			text: "<i class='fas fa-cubes'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				showReportParameters();
			}
		}
	];

	reportTable = initializeGenericTable("reportTable",
			                            "/rest/ignite/v1/report",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editReport(aThis);
										}
	);

	reportTable.on( 'select', function ( e, dt, type, indexes ) {
		updateReportTableButtons();
	} );

	reportTable.on( 'deselect', function ( e, dt, type, indexes ) {
		updateReportTableButtons();
	} );

	updateReportTableButtons();
}

function updateReportTableButtons() {
	var hasSelected = reportTable.rows('.selected').data().length > 0;

	setTableButtonState(reportTable, "deleteReportBtn", hasSelected);
	setTableButtonState(reportTable, "reportParametersBtn", hasSelected);
}

function editReport(aThis) {
	var data = {
			activeInd: "Y"
	};
	
	if ((aThis !== undefined) && (aThis != null)) {
		data = reportTable.row(aThis).data();
	}
	reportTable.rows().deselect();

	showReportDialog(data);
}

function showReportDialog(data) {
	var errors = "";
	
	$("#rDlgReportId").val(data.reportId);
	$("#rDlgReportFilename").val(data.reportFilename);
	$("#rDlgReportName").val(data.reportName);
	$("#rDlgReportDescription").val(data.reportDescription);
	$("#rDlgPermissionCodeRequired").val(data.permissionCodeRequired);
	$("#rDlgAllowExcelExportInd").prop("checked", data.allowExcelExportInd == "Y");
	$("#rDlgActiveInd").prop("checked", data.activeInd == "Y");
	$("#rDlgFileExistsInd").val(data.fileExistsInd);

	if (data.fileExistsInd == "N") {
		errors += "The report file does not exist<br>";
	}
	
	showFormErrors("rDlgErrorDiv", errors);
	$("#reportDialog").modal("show");	
}

function saveReport() {
	var postUrl = "/rest/ignite/v1/report";
	var postData = {
			reportId: $("#rDlgReportId").val(),
			reportFilename: $("#rDlgReportFilename").val(),
			reportName: $("#rDlgReportName").val(),
			reportDescription: $("#rDlgReportDescription").val(),
			permissionCodeRequired: $("#rDlgPermissionCodeRequired").val(),
			allowExcelExportInd: $("#rDlgAllowExcelExportInd").is(":checked") ? "Y" : "N",
			activeInd: $("#rDlgActiveInd").is(":checked") ? "Y" : "N"
	};
	
	// validation
	var errors = validateReportDialog();

	saveEntry(postUrl, postData, "reportDialog", "The Report has been saved.", reportTable);
}

function promptDeleteReport() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Expense?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteReport(reportTable);
			   }
	);
}

function deleteReport(tbl) {
	var postUrl = "/rest/ignite/v1/report/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Report has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateReportTableButtons();
			});
	
}

function validateReportDialog() {
	var errors = "";
	
	var reportFilename = $("#rDlgReportFilename").val();
	var reportName = $("#rDlgReportName").val();
	var reportDescription = $("#rDlgReportDescription").val();
	var fileExistsInd = $("#rDlgFileExistsInd").val();
	
	if ((reportFilename == null) || (reportFilename == "")) {
		errors += "A Filename is required<br>";
	}

	if ((reportName == null) || (reportName == "")) {
		errors += "A Name is required<br>";
	}
	
	if ((fileExistsInd == null) || (fileExistsInd == "N")) {
		errors += "The report file does not exist<br>";
	}

	return errors;
}

function showReportParameters() {
	var row = reportTable.rows('.selected').data()[0];
	var reportId = row.reportId;
	
	initializeReportParameterTable(reportId);
	showModalDialog("reportParameterDialog");
}

function initializeReportParameterTable(reportId) {
	var columnsArray = [
		{ data: "reportParameterId" },
		{ data: "reportId" },
		{ data: "parameterName" },
		{ data: "parameterLabel" },
		{ data: "parameterType" },
		{ data: "selectSql" },
		{ data: "orderNo" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 5]
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = "Text input";
					
					if (data == "c") { html = "Checkbox"; }
					if (data == "d") { html = "Date input"; }
					if (data == "n") { html = "Numeric input"; }
					if (data == "s") { html = "Select/Dropdown"; }
				}
				
				return html;
			},

			targets: 4
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editReportParameter(reportId, null);
			}
		},
		{
			attr : {
				id : "deleteReportParameterBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteReportParameter();
			}
		}
	];

	reportParameterTable = initializeGenericTable("reportParameterTable",
			                            "/rest/ignite/v1/report/parameter/" + reportId,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editReportParameter(reportId, rowSelector);
										}
	);

	reportParameterTable.on( 'select', function ( e, dt, type, indexes ) {
		updateReportParameterTableButtons();
	} );

	reportParameterTable.on( 'deselect', function ( e, dt, type, indexes ) {
		updateReportParameterTableButtons();
	} );

	updateReportParameterTableButtons();
}

function updateReportParameterTableButtons() {
	var hasSelected = reportParameterTable.rows('.selected').data().length > 0;

	setTableButtonState(reportTable, "deleteReportParameterBtn", hasSelected);
}

function editReportParameter(reportId, rowSelector) {
	var data = {
			parameterType : "i",
			reportId: reportId
	};
	
	if ((rowSelector !== undefined) && (rowSelector != null)) {
		data = reportParameterTable.row(rowSelector).data();
	}
	reportParameterTable.rows().deselect();

	showReportParameterDetailDialog(data);
}

function updateParameterType() {
	var type = $("#rpDlgParameterType").val();
	
	setDivVisibility("rpSelectSqlPanel", type == "s" ? "block" : "none");
}

function showReportParameterDetailDialog(data) {
	$("#rpDlgReportId").val(data.reportId);
	$("#rpDlgReportParameterId").val(data.reportParameterId);
	$("#rpDlgParameterName").val(data.parameterName);
	$("#rpDlgParameterLabel").val(data.parameterLabel);
	$("#rpDlgParameterType").val(data.parameterType);
	$("#rpDlgSelectSql").val(data.selectSql);
	$("#rpDlgOrderNo").val(data.orderNo);

	updateParameterType();

	showFormErrors("rpDlgErrorDiv", "");
	$("#reportParameterDetailDialog").modal("show");	
}

function saveReportParameter() {
	var postUrl = "/rest/ignite/v1/report/parameter";
	var postData = {
			reportParameterId: $("#rpDlgReportParameterId").val(),
			reportId: $("#rpDlgReportId").val(),
			parameterName : $("#rpDlgParameterName").val(),
			parameterLabel : $("#rpDlgParameterLabel").val(),
			parameterType: $("#rpDlgParameterType").val(),
			selectSql: $("#rpDlgSelectSql").val(),
			orderNo : $("#rpDlgOrderNo").val()
	};
	var errors = "";
	
	// validation...
	if ((postData.parameterName == null) || (postData.parameterName == "")) {
		errors += "A Parameter Name is required<br>";
	}
	
	if ((postData.parameterLabel == null) || (postData.parameterLabel == "")) {
		errors += "A Parameter Label is required<br>";
	}
	
	if (postData.parameterType == "s") {
		if ((postData.selectSql == null) || (postData.selectSql == "")) {
			errors += "A Select SQL statement is required when the Parameter Type is Select<br>";
		}
	}
	
	if ((postData.orderNo == null) || (postData.orderNo == "")) {
		errors += "An Order Number is required<br>";
	}
	
	if (showFormErrors("rpDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "reportParameterDetailDialog", "The Report Parameter has been saved.", reportParameterTable);
}

function promptDeleteReportParameter() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Report Parameter?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteReportParameter();
			   }
	);
}

function deleteReportParameter() {
	var row = reportParameterTable.rows('.selected').data()[0];

	var postUrl = "/rest/ignite/v1/report/parameter/delete";
	var postData = {
			reportParameterId: row.reportParameterId
	};
	
	// Disable delete button after record has been deleted.
	saveEntry(postUrl, postData, null, "The Report Parameter has been deleted.", reportParameterTable,
		function(){	
			reportParameterTable.rows(".selected").nodes().to$().removeClass("selected");
			updateReportParameterTableButtons();
		}
	);
}

function loadFileManager() {
	var url = "/rest/ignite/v1/report/file-manager";

	initializeFileManager("fileManagerPanel", "/", url);
}

function initializeReportUploadDialogSubmitHandler(successMethod, failureMethod) {
 	$("#uploadReportForm").on('submit', function(e) {
		showPleaseWait();
		console.log("success");
		// e.preventDefault();
	    $.ajax(/*{
	        type: $(this).prop('method'),
	        url : $(this).prop('action'),
	        data: $(this).serialize()
	    }*/)
	    .done(function(data) {
			window.setTimeout(function() {
				var iframe = document.getElementById('ruUploadDialogIframe');
				var iframeUrl = iframe.contentWindow.location.href;
				
				if (iframeUrl.indexOf("upload-success") > -1) {
					// success
					
					hidePleaseWait();
					$("#fileManagerUploadDialog").modal("hide");
					
					successMethod();
				} else {
					// failure... get the message
					hidePleaseWait();

					var error = "An unknown error occurred";
					if (iframeUrl.indexOf("?e=") > -1) {
						error = decodeURI(iframeUrl.substring(iframeUrl.indexOf("?e=") + 3));
					}

					failureMethod(error);
				}
		    }, 2000);  // give it two seconds to upload and show the page
	    })
	    .fail(function(jqXHR, textStatus, errorThrown) {
			failureMethod(textStatus);
		});
	});
}

function initializeSubmitHandlers() {
	initializeReportUploadDialogSubmitHandler(
		function() {
			// success
			fileManagerTable.ajax.reload();		
		},
		function(errorMessage) {
			// failure
			showDialog("Error", errorMessage);
		}
	);
}

// ***********************************************************************
 
$(document).ready(function() {
	// Any initialization
	setActiveTab("reportTab");
	initializeReportTable();
	initializeSubmitHandlers();
	
	$(document).on('change', ':file', function() {
	    var fileSelector = $(this);
		var filenameInput = $("#ruDlgUploadFileInput");

		var label = fileSelector.val().replace(/\\/g, '/').replace(/.*\//, '');
		filenameInput.val(label);
	});

	// react to tab change and load appropriate data
	$('.nav-tabs a').on('shown.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');

		if (activeTab == "File Manager") {
			loadFileManager();
		}
	});
} );
