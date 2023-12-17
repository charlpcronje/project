/*
 * TODO:
 *
 * list of reports that the user has permission to access
 * Dynamic selection boxes to select info for a report
 * How about an option to: schedule - big reports can be scheduled and then run on the server. they will be emailed once completed
 *
 * Considerations:
 *
 * Will need table for dynamic report properties
 * Might need scheduler to be able to run specific reports at a specific time
 * How would this work for invoices and statements etc?
 *
 */
var reportTable = null;

function initializeReportTable() {
	var columnsArray = [
		{ data: "reportId" },
		{ data: "reportFilename" },
		{ data: "reportName" },
		{ data: "reportDescription" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1]
		},
		{
			size: "200px",
			targets: 2
		}
	];

	var buttonsArray = [
		{
			attr: {
				id : "viewReportBtn"
			},
			titleAttr: "View Report",
			text: "<i class='fas fa-book-reader'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptRunReport("Y");
			}
		},
		{
			attr: {
				id : "downloadReportBtn"
			},
			titleAttr: "Download Report",
			text: "<i class='fas fa-file-download'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptRunReport("N");
			}
		}
	];

	reportTable = initializeGenericTable("reportTable",
			                            "/rest/ignite/v1/report/active",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											// Nothing
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

	setTableButtonState(reportTable, "viewReportBtn", hasSelected);
	setTableButtonState(reportTable, "downloadReportBtn", hasSelected);
}

function promptRunReport(inline) {
	var row = reportTable.rows('.selected').data()[0];
	var reportId = row.reportId;

	var url = "/rest/ignite/v1/report/parameter/html/" + reportId + "/" + (inline == "Y");

	$.ajax({
		url: springUrl(url), 
		type: "GET",
		success: function(data) {
			if (data.html == null) {
				var parameterData = {};

				// run report with no parameters
				if (inline == "Y") {
					parameterData["generationType"] = "inline";
				}

				generateReport(reportId, parameterData);
			} else {
				// prompt for parameters
				promptForReportParameters(reportId, inline, data.html);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function promptForReportParameters(reportId, inline, html) {
	$("#rpReportId").val(reportId);
	$("#rpInline").val(inline);
	$("#reportParametersContent").html(html);

	$("#reportParameterDialog").modal("show");
}

function processReportParameters() {
	var reportId = $("#rpReportId").val();
	var inline = $("#rpInline").val();
	var errors = "";

	// serialize the form reportParametersForm
	var parameterData = {};

	if (inline == "Y") {
		parameterData["generationType"] = "inline";
	}
	
	for (var foo =  0; foo < 999; foo++) {
		var name = $("#rp" + foo + "_name").val();

		if ((name != null) && (name != "")) {
			var value = $("#rp" + foo).val();

			if ((value == null) || (value == "")) {
				errors = "All Report Parameters must be captured<br>";
			}

			// Added this way to use the variable's value for the name
			parameterData[name] = value;
		}
	}
	
	if (showFormErrors("rpDlgErrorDiv", errors)) {
		return;
	}

	$("#reportParameterDialog").modal("hide");
	generateReport(reportId, parameterData);
}

function generateReport(reportId, parameterData) {
	var url = "/rest/ignite/v1/report/run/" + reportId;
	var idx = 0;

	Object.keys(parameterData).forEach(function(key) {
		var value = parameterData[key];

		if (idx == 0) {
    		url += "?";
    	} else {
    		url += "&";
    	}

		url += key + "=" + value;

		idx++;
	});

	window.open(springUrl(url));
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeReportTable();
} );

