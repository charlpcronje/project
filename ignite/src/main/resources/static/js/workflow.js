var workflowTable = null;
var workflowInfoTable = null; 
var graph = null;
var graphSvg = null;

function dateToYmdString(str) {
	var result = "";
	
	if ((str != null) && (str != "")) {
		if (str.indexOf("/") > -1) {
			var strParts = str.split("/");
			if (strParts.length == 3) {
				var d = strParts[0];
				var m = strParts[1];
				var y = strParts[2];
				
				result += y;

				if (m.length < 2) { result += "0"; }
				result += m;

				if (d.length < 2) { result += "0"; }
				result += d;
			} 
		}
	}
	
	return result;
}

function confirmTerminateSelectedWorkflows() {
	var selectedCount = workflowTable.rows('.selected').data().length;

	if (selectedCount != 1) {
		showDialog("Error", 
		   "A workflow must be selected.", 
		   DialogConstants.TYPE_ALERT,
		   DialogConstants.ALERTTYPE_ERROR);
		return;
	}
	
	var rows = workflowTable.rows('.selected').data();
	var workflowProcessId = rows[0].workflowProcessId;
	
	showDialog("Delete workflow?", 
	           "Are you sure that you wish to delete the selected workflow?", 
	           DialogConstants.TYPE_CONFIRM, 
	           DialogConstants.ALERTTYPE_INFO, 
	           function (e) {
			   		terminateSelectedWorkflows(workflowProcessId);
	           }
	);
}

function terminateSelectedWorkflows(workflowProcessId) {
	var url = "/api/gdi/v1/workflow/process";
	
	var postData = {
		workflowProcessId: workflowProcessId
	};
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: springUrl(url),
		type: 'DELETE',
		data: JSON.stringify(postData),
		contentType: "application/json",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},		
		success: function(response, statusText, xhr) {
			searchWorkflows();
			updateWorkflowBadgeCount();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function viewWorkflow(aThis) {
	var data = workflowTable.row(aThis).data();
	var queryUrl = "/api/gdi/v1/workflow/history/" + data.workflowProcessId;

	$("#workflowInfoDialog").modal("show");
	
	var columnsArray = [
		{ data: "workflowProcessLogId" },
		{ data: "workflowProcessId" },
		{ data: "logTimestamp" },
		{ data: "workflowDefinitionStepNumber" },
		{ data: "workflowDefinitionStepName" },
		{ data: "logMessage" },
		{ data: "workflowProcessStatus" }
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3]
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					return timestampToString(row["logTimestamp"]);
				} else {
					return row["logTimestamp"];
				}
			 },
			targets: 2
		},
		{
			render: function(data, type, row) {
				var txt = row["workflowDefinitionStepName"];
				return shortenText(txt, 40);
			},
			targets: 4
		},
		{
			render: function(data, type, row) {
				var txt = row["logMessage"];
				return shortenText(txt, 60);
			},
			targets: 5
		}
	];

	var buttonsArray = [
		{
			titleAttr: "Refresh",
			text: "<i class='fas fa-sync-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				try {
					workflowInfoTable.ajax.reload();
				} catch (e) {
					// nothing
				}
			}
		}
	];
	
	workflowInfoTable = initializeGenericTable("workflowInfoTable",  
			                                       queryUrl,
			                                       columnsArray,
			                                       columnDefsArray,
			                                       buttonsArray,
			                                       null);
}

function showGenericRecord(workflowProcessId) {
	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/dialog/" + workflowProcessId),
		type: "GET",
		success: function(data) {
			$("#genericRecordBody").html(data.dialogHtml);
			$("#genericRecordDialog").modal("show");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function initializeSearchDates() {
	// set start end date to last 2 weeks
	var today = new Date();
	var twoWeeksAgo = new Date();
	twoWeeksAgo.setDate(today.getDate() - 14);

	$("#startDateInput").val(dateToDDMMYYYY(twoWeeksAgo));
	$("#endDateInput").val(dateToDDMMYYYY(today));
	$("#onlyActiveChk").prop("checked", true);
}

function searchWorkflows() {
	// defaults to getting active workflows only
	var queryUrl = "/api/gdi/v1/workflow/provider/" + _g_provider;
	
	if ((_g_provider == null) || (_g_provider == "")) {
		queryUrl = "/api/gdi/v1/workflow/provider/_none_";
	}

	var startDate = $("#startDateInput").val();
	var endDate = $("#endDateInput").val();
	var onlyActive = $("#onlyActiveChk").is(":checked") ? "active" : "inactive";

	queryUrl = queryUrl + 
	           "/" + dateToYmdString(startDate) +
	           "/" + dateToYmdString(endDate) + 
	           "/" + onlyActive;

	var callbackMethod = function(aThis) {
		viewWorkflow(aThis);
	};
	
	var columnsArray = [
    	{ data: "workflowProcessId" },
    	{ data: "startDateTime" },
    	{ data: "workflowDefinitionId" },
    	{ data: "workflowDefinition.workflowDefinitionName"},
    	{ data: "recordObjectName" },
    	{ data: "recordWhereClause" },
    	{ data: "lastStep" },
    	{ data: "nextStep" },
    	{ data: "startDateTime" },
    	{ data: "executeDateTime" },
    	{ data: "statusCode" },
    	{ data: "activeFlag" }
    ];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 6, 7]
		},
		{
			render: function(data, type, row) {
				var workflowProcessId = row["workflowProcessId"];
				var s = row["recordWhereClause"];
				
				s = s +
				    "&nbsp;" +
				    "<a href='#' onclick='showGenericRecord(" + workflowProcessId + ")'><i class='fa fa-external-link-alt'></i></a>";

				return s;
			},
			targets: 5
		},
		{
			 render: function(data, type, row) {
				 if (type == "display") {
					 return timestampToString(row["startDateTime"]);
				 } else {
					 return row["startDateTime"];
				 }
			 },
			 targets: 8
		},
		{
			 render: function(data, type, row) {
				 if (type == "display") {
					 return timestampToString(row["executeDateTime"]);
				 } else {
					 return row["executeDateTime"];
				 }
			 },
			 targets: 9
		},
		{
			 render: function(data, type, row) {
				 var val = row["activeFlag"];

				 if (val == "Y") {
					 return "Yes";
				 } 
				 if (val == "N") {
					 return "No";
				 }

				 return "&nbsp;";
			 },
			 targets: 11
		}
	];
	
	var buttonsArray = [
		{
			text: "<i class='fas fa-sync-alt' title='Refresh'></i>",
			titleAttr: "Refresh",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				searchWorkflows();
			}
		},
		{
			text: "<i class='far fa-hand-paper' title='Terminate'></i>",
			titleAttr: "Terminate",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				confirmTerminateSelectedWorkflows();
			}
		},
		{
			text: "<i class='icon icon-workflow' title='View workflow'></i>",
			titleAttr: "View workflow",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				viewSelectedWorkflows();
			}
		}
	];

	if (_g_role.indexOf('_MONITOR') > 0) {
		buttonsArray = [
			{
				text: "<i class='fas fa-sync-alt' title='Refresh'></i>",
				titleAttr: "Refresh",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					searchWorkflows();
				}
			},
			{
				text: "<i class='icon icon-workflow' title='View workflow'></i>",
				titleAttr: "View workflow",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					viewSelectedWorkflows();
				}
			}
		];
	}
	
	workflowTable = initializeGenericTable("workflowTable", queryUrl, columnsArray, columnDefsArray, buttonsArray, callbackMethod);
}

function workflowClick() {
	// Do nothing
}

function viewSelectedWorkflows() {
	var selectedCount = workflowTable.rows('.selected').data().length;

	if (selectedCount != 1) {
		showDialog("Error", 
		   "A workflow must be selected.", 
		   DialogConstants.TYPE_ALERT,
		   DialogConstants.ALERTTYPE_ERROR);
		return;
	}
	
	var rows = workflowTable.rows('.selected').data();
	var workflowProcessId = rows[0].workflowProcessId;
	var workflowDefinitionId = rows[0].workflowDefinitionId;
	var workflowDefinitionName = rows[0].workflowDefinition.workflowDefinitionName;
	var nextStep = rows[0].nextStep == null ? "0" : rows[0].nextStep;
	var statusCode = rows[0].statusCode;
	
	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/graph/" + workflowDefinitionId + "/" + statusCode + "/" + nextStep),
		type: "GET",
		success: function(data) {
			graph = data.graphText;
			
			if (graph == null) {
				// Hide the graph
				setDivVisibility("wddWorkflowDefinitionBody", "none"); 
			} else {
				setDivVisibility("wddWorkflowDefinitionBody", "inline-block");
			}

			// set the header and image
			$("#wddWorkflowDefinitionName").html(workflowDefinitionName);
			$("#wddWorkflowDefinitionBody").html(graph);
			
			setWorkflowGraph();

			// show the dialog
			$("#workflowDiagramDialog").modal("show");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function initializeMermaid() {
	mermaid.initialize({
		startOnLoad: false,
		theme: "default",
		flowchart: {
			useMaxWidth: true,
			securityLevel: "loose"
		}
	});
}

function setWorkflowGraph() {
    // remove any previously created graphs...
    var element = document.getElementById("gd1");
    if (element != null) {
    	element.parentNode.removeChild(element);
    }
    
    var callbackMethod = function(svgGraph) {
    	graphSvg = svgGraph;  // save to our global variable
    	$("#wddWorkflowDefinitionBody").html(svgGraph);
	};
	
    vizLoad("workflowDefinitionGraphSvgDiv", graph);
}

// ***************************************************************************

$(document).ready(function() {
	initializeSearchDates();
	searchWorkflows();
} );
