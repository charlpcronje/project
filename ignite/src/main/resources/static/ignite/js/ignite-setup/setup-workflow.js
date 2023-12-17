var workflowDefinitionId = null;
var workflowDefinitionTable = null;
var workflowDefinitionName = null;
var workflowDefinitionStatus = null;
var workflowDefinitionStepCount = 0;
var workflowDefinitionInUse = "N";
var workflowDefinitionList = {};
var workflowDefinitionRecordObjectName = null;

var graph = null;
var graphSvg = null;

function workflowDefinitionCompare(a, b) {
	if (a.workflowDefinitionName < b.workflowDefinitionName){
		return -1;
	}
	if (a.workflowDefinitionName > b.workflowDefinitionName){
		return 1;
	}
	
	return 0;
}

function initializeWorkflowDefinitions(selectedWorkflowDefinitionCode) {
	$.ajax({
		url: springUrl("/rest/ignite/v1/workflow/definition"),
		type: "GET",
		success: function(data) {
			workflowDefinitionList = data;
			// Sort the array of workflow definitions by name
			workflowDefinitionList = workflowDefinitionList.sort(workflowDefinitionCompare);

			filterWorkflowDefinitionList(selectedWorkflowDefinitionCode);
			
			// tell the drawing to refresh...
			loadWorkflowDefinition();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function resetGraph() {
	vizResetZoom("workflowDefinitionGraphSvgDiv", "workflowDefinitionGraphAnchorDiv");
}

function viewLeftRight() {
	setWorkflowGraph("LR");
}

function viewTopDown() {
	setWorkflowGraph("TD");
}

function setWorkflowGraph(direction) {
    // remove any previously created graphs...
    var element = document.getElementById("gd1");
    if (element != null) {
    	element.parentNode.removeChild(element);
    }
    
    // Deal with changing the direction 
    // ****************************************************************************
    if (typeof direction != "undefined") {
    	// search and replace direction
    	if (direction == "LR") {
    		graph = graph.replace("rankdir=TD", "rankdir=LR");
    	} else {
    		graph = graph.replace("rankdir=LR", "rankdir=TD");
    	}
    }
    // ****************************************************************************

    vizLoad("workflowDefinitionGraphSvgDiv", graph);
}

function downloadDiv(id_div, filename) {
	var mode = 1 // default 1 (save to image), mode 2 = save to canvas
	var node = document.getElementById(id_div);
    
	// get the div that will contain the canvas
	var canvas_out = document.getElementById('canvas_out');
    var canvas = document.createElement('canvas');
    
    canvas.width = node.scrollWidth;
    canvas.height = node.scrollHeight;

    // Source: https://github.com/tsayen/dom-to-image
    domtoimage.toPng(node).then(function (pngDataUrl) {
    	var img = new Image();
    	
        img.onload = function () {
        	var context = canvas.getContext('2d');
        	context.drawImage(img, 0, 0);
        };

        if (mode == 1) { // save to image
        	downloadURI(pngDataUrl, filename);
        } else if (mode == 2) { // save to canvas
        	img.src = pngDataUrl;
        	canvas_out.appendChild(img);
        }
    });
}

function downloadURI(uri, name) {
    var link = document.createElement("a");

    link.download = name;
    link.href = uri;
    document.body.appendChild(link);
    link.click();
    
    link.parentNode.removeChild(link);
}

function downloadGraph() {
	var filename = workflowDefinitionName + ".png";

	showGdiPleaseWait();
	downloadDiv("workflowDefinitionGraphSvgDiv", filename);
	hideGdiPleaseWait();
}

function editWorkflowDefinition() {
	if ((workflowDefinitionId == null) || (workflowDefinitionId == "")) {
		// No selected workflow...
		return;
	}

	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/" + workflowDefinitionId),
		type: "GET",
		success: function(data) {
			showWorkflowDefinitionDialog(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function loadWorkflowDefinition() {
	workflowDefinitionId = $("#workflowDefinitionSelect").val();
	workflowDefinitionName = $("#workflowDefinitionSelect option:selected").text();
	
	// Trigger the render of the graph...
	loadWorkflowGraph();
	resetGraph();
	
	if ((workflowDefinitionId == null) || (workflowDefinitionId == "")) {
		return;
	}
	
	// get our internal data
	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/" + workflowDefinitionId),
		type: "GET",
		success: function(data) {
			workflowDefinitionStepCount = data.workflowDefinitionStepCount;
			workflowDefinitionInUse = data.inUse;
			workflowDefinitionRecordObjectName = data.recordObjectName;
			
			updateWorkflowStatus();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function loadWorkflowGraph() {
	if ((workflowDefinitionId == null) || (workflowDefinitionId == "")) {
		setDivVisibility("workflowDefinitionGraphDiv", "none"); 
		return;
	}

	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/graph/" + workflowDefinitionId),
		type: "GET",
		success: function(data) {
			graph = data.graphText;
			
			if (graph == null) {
				// Hide the graph
				setDivVisibility("workflowDefinitionGraphDiv", "none"); 
			} else {
				setDivVisibility("workflowDefinitionGraphDiv", "inline-block");
				setWorkflowGraph();
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
		
function newWorkflowDefinition() {
	var data = {
			workflowDefinitionId: -1,
			workflowDefinitionCode: null,
			workflowDefinitionName: null,
			workflowDefinitionDescription : null,
			dataProviderId : null,
			slaMinutes: 10,
			failoverMailbox: null,
			recordObjectName: null
	};
	
	showWorkflowDefinitionDialog(data);
}

function showWorkflowDefinitionDialog(data) {
	$("#wdWorkflowDefinitionId").val(data.workflowDefinitionId);
	$("#wdWorkflowDefinitionCode").val(data.workflowDefinitionCode);
	$("#wdWorkflowDefinitionName").val(data.workflowDefinitionName);
	$("#wdWorkflowDefinitionDescription").val(data.workflowDefinitionDescription);
	$("#wdSlaMinutes").val(data.slaMinutes);
	$("#wdFailoverMailbox").val(data.failoverMailbox);

	var dataProviderName = data.dataProvider == null ? null : data.dataProvider.dataProviderName;
	refreshDataProviderList(dataProviderName);
	
	var ron = data.recordObjectName;
	var schemaName = null;
	var tableName = null;
	
	if ((ron != null) && (ron.indexOf(".") > 0)) {
		schemaName = ron.substring(0, ron.indexOf("."));
		tableName = ron.substring(ron.indexOf(".") + 1);
	} else {
		tableName = ron;
	}
	
	populateSelect("wdRecordObjectNameSchema", "/api/gdi/v1/schemas", "schemaName", schemaName, function () {
		// callback to populate the tablename
		populateSelect("wdRecordObjectNameTable", "/api/gdi/v1/tables/" + schemaName, "tableName", tableName);
	});
	
	// Hide delete button if this is a new record
	updateWorkflowDefinitionDialogButtons(data);
	
	showWorkflowErrors(null);
	$("#workflowDefinitionDialog").modal("show");
}

function updateWorkflowDefinitionDialogButtons(data) {
	setButtonEnabled('deleteWorkflowDefinitionBtn', (data.workflowDefinitionId != -1) && (workflowDefinitionInUse != "Y"));
	setButtonEnabled('saveWorkflowDefinitionBtn', (workflowDefinitionInUse != "Y"));
}

function updateWorkflowDefinitionStepDialogButtons(data) {
	setButtonEnabled('deleteWorkflowDefinitionStepBtn', (data.workflowDefinitionId != -1) && (workflowDefinitionInUse != "Y"));
	setButtonEnabled('saveWorkflowDefinitionStepBtn', (workflowDefinitionInUse != "Y"));
}

function updateRecordObjectNameTable() {
	var schemaName = $("#wdRecordObjectNameSchema").val();
	var tableName = null;

	populateSelect("wdRecordObjectNameTable", "/api/gdi/v1/tables/" + schemaName, "tableName", tableName);
}

function populateSelect(selectName, url, fieldName, selectedValue, callbackMethod) {
	$.ajax({
		url: springUrl(url),
		type: "GET",
		success: function(data) {
			$("#" + selectName).empty();
			$("#" + selectName).append("<option value=''></option>");
			
			for (var foo = 0; foo < data.length; foo++) {
				var row = data[foo];
				var name = row[fieldName];
				
				var selected = ((selectedValue != null) && (selectedValue == name)) ? "selected" : "";
				var option = "<option value='" + name + "' " + selected + ">" + name + "</option>";
				
				$("#" + selectName).append(option);
			}
			
			// if we have a callbackMethod...
			if ((typeof callbackMethod != "undefined") && (callbackMethod != null)) {
				callbackMethod();
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function refreshDataProviderList(dataProviderName) {
	var elName = "wdDataProviderId";
	
	$.ajax({
		url: springUrl("/api/gdi/v1/providers"),
		type: "GET",
		success: function(data) {
			var global = dataProviderName == null ? "selected" : "";
			
			$("#" + elName).empty();
			$("#" + elName).append("<option value='-1' " + global + ">Global</option>");
			
			for (var foo = 0; foo < data.length; foo++) {
				var row = data[foo];
				var id = row.dataProviderId;
				var name = row.dataProviderName;
				var selected = ((dataProviderName != null) && (dataProviderName == row.DataProviderName)) ? "selected" : "";
				var option = "<option value='" + id + "' " + selected + ">" + name + "</option>";
				
				$("#" + elName).append(option);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function selectWorkflowDefinitionStepType(type) {
	if (typeof type == "undefined") {
		type = $("#workflowDefinitionStepTypeSelect").val();
	}
	
	setDivVisibility("workflowDefinitionStep_Condition", (type != "condition") ? "none" : "block");
	setDivVisibility("workflowDefinitionStep_Mail", (type != "email") ? "none" : "block");
	setDivVisibility("workflowDefinitionStep_Sql", (type != "sql") ? "none" : "block");
	setDivVisibility("workflowDefinitionStep_TriggerWorkflow", (type != "workflow") ? "none" : "block");
	setDivVisibility("workflowDefinitionStep_MarkDiv", (type != "") ? "none" : "block");

	setDivVisibility("workflowDefinitionStep_Goto", (type == "") || (type == "workflow") || (type == "condition") ? "none" : "block");
	setDivVisibility("workflowDefinitionStep_Sla", (type == "condition") ? "none" : "block");
}

function initializeStepSelect(elementId, selectedStepNumber) {
	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/definition/" + workflowDefinitionId),
		type: "GET",
		success: function(data) {
			$("#" + elementId).empty();
			
			var nullSelected = (selectedStepNumber == null) ? "selected" : "";
			$("#" + elementId).append("<option value='' " + nullSelected + "></option>");		// add empty option
			
			for (var foo = 0; foo < data.length; foo++) {
				var row = data[foo];
				var stepNumber = row.workflowDefinitionStepNumber;
				var stepName = row.workflowDefinitionStepName;
				var selected = "";
				
				if ((selectedStepNumber != null) && (selectedStepNumber == stepNumber)) {
					selected = "selected";
				}
				
				var option = "<option value='" + stepNumber + "' " + selected + ">" + stepName + "</option>";
				$("#" + elementId).append(option);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function showWorkflowDefinitionStepDialog(data, allowSelect) {
	var pauseWorkflowFlag = data.pauseWorkflowFlag == null ?  false : data.pauseWorkflowFlag.toLowerCase() == "y";
	var markFailedFlag = data.markFailedFlag == null ?  false : data.markFailedFlag.toLowerCase() == "y";
	var markCompletedFlag = data.markCompletedFlag == null ?  false : data.markCompletedFlag.toLowerCase() == "y";
	var isStartStep = data.workflowDefinitionStepNumber == 0;
	
	if (typeof allowSelect === "undefined") {
		allowSelect = false;
	}
	
	if (isStartStep) {
		data.workflowDefinitionStepName = "Start";
	}
	
	$("#WorkflowDefinitionId").val(data.workflowDefinitionId);
	$("#WorkflowDefinitionStepNumberInput").val(data.workflowDefinitionStepNumber);
	$("#WorkflowDefinitionStepNameInput").val(data.workflowDefinitionStepName);	
	$("#TestFieldInput").val(data.testField);
	$("#TestValueInput").val(data.testValue);
	$("#IfTrueStepNumberSelect").val(data.ifTrueStepNumber);
	$("#IfFalseStepNumberSelect").val(data.ifFalseStepNumber);
	$("#MailboxInput").val(data.mailbox);
	$("#MailSubjectInput").val(data.mailSubject);
	$("#MailBodyTextTextArea").val(data.mailBodyText);
	$("#SqlTextTextArea").val(data.sqlText);
	$("#GotoStepNumberSelect").val(data.gotoStepNumber);
	$("#TriggerWorkflowInput").val(data.triggerWorkflowDefinitionCode);
	
	$("#PauseWorkflowFlagInput").prop("checked", pauseWorkflowFlag);
	$("#MarkFailedFlagInput").prop("checked", markFailedFlag);
	$("#MarkCompletedFlagInput").prop("checked", markCompletedFlag);
	
	var messageClass = null;
	var messageIcon = null;
	var messageText = null;	
	
	if (data.workflowDefinitionStepNumber == 0) {
		messageClass = "info";
		messageIcon = "fa-chevron-circle-right";
		messageText = "This step is the start of the workflow. It cannot be edited or removed.";
	}
	if (markFailedFlag) {
		messageClass = "danger";
		messageIcon = "fa-thumbs-down"; 
		messageText = "This step marks the workflow as failed and prevents any further processing on the workflow.";
	}
	if (markCompletedFlag) {
		messageClass = "success";
		messageIcon = "fa-thumbs-up";
		messageText = "This step marks the workflow as completed successfully and prevents any further processing on the workflow.";
	}

	$("#workflowDefinitionStep_Message").removeClass().addClass("alert alert-" + messageClass);
	$("#workflowDefinitionStep_Message").attr("role", messageClass);
	$("#workflowDefinitionMessageIcon").removeClass().addClass("fa " + messageIcon);
	$("#WorkflowDefinitionMessage").html(messageText);
	
	var type = "";
	if ((data.testField != null) && (data.testField.trim() != "")) {
		type = "condition";
	};
	
	if ((data.mailbox != null) && (data.mailbox.trim() != "")) {
		type = "email";
	} 
	
	if ((data.sqlText != null) && (data.sqlText.trim() != "")) {
		type = "sql";
	} 
	
	if ((data.triggerWorkflowDefinitionCode != null) && (data.triggerWorkflowDefinitionCode.trim() != "")) {
		type = "workflow";
	} 

	initializeStepSelect("IfTrueStepNumberSelect", data.ifTrueStepNumber);
	initializeStepSelect("IfFalseStepNumberSelect", data.ifFalseStepNumber);
	initializeStepSelect("GotoStepNumberSelect", data.gotoStepNumber);
	
	// This must be after the other setDivVisibilities so that the type selector can show the correct panel on startup
	setDivVisibility("workflowDefinitionStepTypeSelectDiv", allowSelect ? "block" : "none");
	if (allowSelect) {
		type = $("#workflowDefinitionStepTypeSelect").val();
	}
	selectWorkflowDefinitionStepType(type);
	
	updateWorkflowDefinitionStepDialogButtons(data);	
	$("#workflowDefinitionStepDialog").modal("show");
}

function confirmDeleteWorkflowDefinitionStep() {
	var stepName = $("#WorkflowDefinitionStepNameInput").val();
	var workflowDefinitionId = $("#WorkflowDefinitionId").val();
	var workflowDefinitionStepNumber = $("#WorkflowDefinitionStepNumberInput").val();
	
	showDialog("Delete Workflow Definition Step?", 
	           "Are you sure that you wish to delete the Workflow Definition Step: " + stepName + "?", 
	           DialogConstants.TYPE_CONFIRM, 
	           DialogConstants.ALERTTYPE_INFO, 
	           function (e) {
					deleteWorkflowDefinitionStep(workflowDefinitionId, workflowDefinitionStepNumber);
	           }
	);
}

function confirmDeleteWorkflowDefinition() {
	var workflowDefinitionId = $("#wdWorkflowDefinitionId").val();
	var workflowDefinitionName = $("#wdWorkflowDefinitionName").val();

	$.ajax({
		url: springUrl("/api/gdi/v1/workflow/count/" + workflowDefinitionId),
		type: "GET",
		success: function(data) {
			if (data.record_count > 0) {
				showDialog("Cannot Delete Workflow Definition", 
				           "The Workflow Definition has active Workflow Processes and cannot be deleted.", 
				           DialogConstants.TYPE_WARNING, 
				           DialogConstants.ALERTTYPE_WARNING
				);
				return;
			}
			
			showDialog("Delete Workflow Definition?", 
			           "Are you sure that you wish to delete the Workflow Definition: " + workflowDefinitionName + "?", 
			           DialogConstants.TYPE_CONFIRM, 
			           DialogConstants.ALERTTYPE_INFO, 
			           function (e) {
							deleteWorkflowDefinition(workflowDefinitionId);
			           }
			);

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function deleteWorkflowDefinition(workflowDefinitionId) {
	var deleteUrl = "/api/gdi/v1/workflow/definition";
	var deleteData = {
			workflowDefinitionId: workflowDefinitionId
	};
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: springUrl(deleteUrl),
		type: 'DELETE',
		data: JSON.stringify(deleteData),
		contentType: "application/json",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},		
		success: function(response, statusText, xhr) {
			$("#workflowDefinitionDialog").modal("hide");
			initializeWorkflowDefinitions(null);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function deleteWorkflowDefinitionStep(workflowDefinitionId, workflowDefinitionStepNumber) {
	var deleteUrl = "/api/gdi/v1/workflow/definition/step";
	var deleteData = {
			workflowDefinitionId: workflowDefinitionId,
			workflowDefinitionStepNumber: workflowDefinitionStepNumber
	};
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: springUrl(deleteUrl),
		type: 'DELETE',
		data: JSON.stringify(deleteData),
		contentType: "application/json",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},		
		success: function(response, statusText, xhr) {
			$("#workflowDefinitionStepDialog").modal("hide");
			loadWorkflowDefinition();
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function saveWorkflowDefinition() {
	var wdWorkflowDefinitionId = $("#wdWorkflowDefinitionId").val();
	var wdWorkflowDefinitionCode = $("#wdWorkflowDefinitionCode").val();
	var wdWorkflowDefinitionName = $("#wdWorkflowDefinitionName").val();
	var wdWorkflowDefinitionDescription = $("#wdWorkflowDefinitionDescription").val();
	var wdSlaMinutes = $("#wdSlaMinutes").val();
	var wdFailoverMailbox = $("#wdFailoverMailbox").val();
	var wdDataProviderId = $("#wdDataProviderId").val();
	var wdRecordObjectNameSchema = $("#wdRecordObjectNameSchema").val();
	var wdRecordObjectNameTable = $("#wdRecordObjectNameTable").val();
	
	var errors = "";
	
	if ((wdWorkflowDefinitionCode == null) || (wdWorkflowDefinitionCode.trim() == "")) {
		errors += "The Workflow Definition Code cannot be empty<br>";
	}
	
	if ((wdWorkflowDefinitionName == null) || (wdWorkflowDefinitionName.trim() == "")) {
		errors += "The Workflow Definition Name cannot be empty<br>";
	}
	
	if ((wdSlaMinutes == null) || (wdSlaMinutes.trim() == "")) {
		errors += "The SLA Minutes cannot be empty<br>";
	}
	
	if ((wdFailoverMailbox == null) || (wdFailoverMailbox.trim() == "")) {
		errors += "The Failover Mailbox cannot be empty<br>";
	}

	if ((wdRecordObjectNameSchema == null) || (wdRecordObjectNameSchema.trim() == "")) {
		errors += "The Table schema must be selected<br>";
	}

	if ((wdRecordObjectNameTable == null) || (wdRecordObjectNameTable.trim() == "")) {
		errors += "The Table name must be selected<br>";
	}

	if (errors.length > 0) {
		showWorkflowErrors(errors);
		return;
	}
	
	var postUrl = springUrl("/api/gdi/v1/workflow/definition");
	var postData = {
			workflowDefinitionId: wdWorkflowDefinitionId,
			workflowDefinitionCode: wdWorkflowDefinitionCode,
			workflowDefinitionName: wdWorkflowDefinitionName,
			workflowDefinitionDescription: wdWorkflowDefinitionDescription, 
			slaMinutes: wdSlaMinutes, 
			failoverMailbox: wdFailoverMailbox,
			dataProviderId : wdDataProviderId == -1 ? null : wdDataProviderId,
			recordObjectName: wdRecordObjectNameSchema + "." + wdRecordObjectNameTable
    };

	saveEntry(postUrl, postData, "workflowDefinitionDialog", "Workflow Definition saved.", null, function(aThis, response) {
		// select it to update the graph...
		initializeWorkflowDefinitions(wdWorkflowDefinitionCode);
	});
}

function showWorkflowErrors(errorMessage) {
	var display = ((errorMessage != null) && (errorMessage != ""));
	setDivVisibility("workflowDefinitionDialogErrorStateDiv", display ? "block" : "none");
	
	if (display) {
		$("#workflowDefinitionDialogErrorMsgDiv").html(errorMessage);
	}
}

function saveWorkflowDefinitionStep() {
	var workflowDefinitionId = $("#WorkflowDefinitionId").val();
	var workflowDefinitionStepNumber = $("#WorkflowDefinitionStepNumberInput").val();
	
	var postData = {
			workflowDefinitionId: workflowDefinitionId,
			workflowDefinitionStepName: $("#WorkflowDefinitionStepNameInput").val(),
			workflowDefinitionStepNumber: workflowDefinitionStepNumber,
			
			pauseWorkflowFlag: $("#PauseWorkflowFlagInput").prop("checked") ? "Y" : null,
			markFailedFlag: $("#MarkFailedFlagInput").prop("checked") ? "Y" : null,
			markCompletedFlag: $("#MarkCompletedFlagInput").prop("checked") ? "Y" : null,

			gotoStepNumber: $("#GotoStepNumberSelect").val(),
					
			// condition
			testField: $("#TestFieldInput").val(),
			testValue: $("#TestValueInput").val(),
			ifTrueStepNumber: $("#IfTrueStepNumberSelect").val(),
			ifFalseStepNumber: $("#IfFalseStepNumberSelect").val(),
			
			// mail
			mailbox: $("#MailboxInput").val(),
			mailSubject: $("#MailSubjectInput").val(),
			mailBodyText: $("#MailBodyTextTextArea").val(),
			
			// sql
			sqlText: $("#SqlTextTextArea").val(),
			
			// trigger workflow
			triggerWorkflowDefinitionCode: $("#TriggerWorkflowInput").val()
	}

	var postUrl = springUrl("/api/gdi/v1/workflow/definition/" + workflowDefinitionId + "/" + workflowDefinitionStepNumber);
	saveEntry(postUrl, postData, "workflowDefinitionStepDialog", "Workflow Definition Step saved.", null, function(aThis, response) {
		// select it to update the graph...
		loadWorkflowDefinition();
	});	
}

function newWorkflowDefinitionStep() {
	// set the next steps number to the last one plus 10 (to leave a gap for new steps)
	var stepNumber = workflowDefinitionStepCount + 10;
	
	var data = {
			workflowDefinitionId: workflowDefinitionId,
			workflowDefinitionStepNumber: stepNumber,
			
			// Defaults for items below this point
			ifTrueStepNumber: null,
			ifFalseStepNumber: null,
			MailBodyText: null,
			mailSubject: null,
			mailbox: null,
			markCompletedFlag: null,
			markFailedFlag: null,
			pauseWorkflowFlag: null,
			sqlText: null,
			testField: null,
			testValue: null,
			gotoStepNumber: null,
			
			isNew: true
	};

	if ((workflowDefinitionId == null) || (workflowDefinitionId == "") || (workflowDefinitionId == -1)) {
		// We cannot add steps if there is no workflow def...
		return;
	}
	
	if (workflowDefinitionInUse == "Y") {
		// We cannot add steps if the workflow is in use...		
		return;
	}
	
	// Additional flag to allow the user to change the step's type
	showWorkflowDefinitionStepDialog(data, true);
}

function workflowClick(nodeId) {
	var queryUrl = springUrl("/api/gdi/v1/workflow/definition/" + workflowDefinitionId + "/" + nodeId);
	
	$.ajax({
		url: queryUrl,
		type: "GET",
		success: function(data) {
			// if there is no first step in the DB fake it...
			if ((data == null) || (data == "")) {
				data = {
					workflowDefinitionId : workflowDefinitionId,
					workflowDefinitionStepNumber : 0,
					workflowDefinitionStepName: "Start"
				};
			}
			
			// Add a flag to indicate that this is not a new record
			data.isNew = false;
			
			showWorkflowDefinitionStepDialog(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function setComponentDisabled(element, disabled) {
	var el = $("#" + element);

	el.prop("disabled", disabled);
	el.prop("readonly", disabled);
}

function showWorkflowStatus() {
	var html = "The workflow definition is valid!";
	style = DialogConstants.ALERTTYPE_SUCCESS;
	
	if (workflowDefinitionStatus != null) {
		html = workflowDefinitionStatus.replaceAll("\\n", "<br>");
		style = DialogConstants.ALERTTYPE_DANGER;
	}
	
	showDialog("Workflow Status", 
			   html, 
			   DialogConstants.TYPE_ALERT, 
			   style);
}

function updateWorkflowStatus() {
	var queryUrl = springUrl("/api/gdi/v1/workflow/status/" + workflowDefinitionId);
	
	$.ajax({
		url: queryUrl,
		type: "GET",
		success: function(data) {
			workflowDefinitionStatus = data.status;
			
			var htmlSuccess = '<button class="dt-button ui-button ui-state-default ui-button-text-only btn btn-sm" \
				               onclick="showWorkflowStatus()">\
				               <i class="fa fa-thumbs-up alert-success" title="Workflow is valid"></i>\
				               </button>';
			var htmlFail = '<button class="dt-button ui-button ui-state-default ui-button-text-only btn btn-sm" \
				                    onclick="showWorkflowStatus()">\
				            <i class="fa fa-thumbs-down alert-danger" title="Workflow is invalid"></i>\
				            </button>';

			var isValid = (workflowDefinitionStatus  == null) || (workflowDefinitionStatus  == "");
			var el = $("#workflowStatusDiv");
			var html = "";
			
			if (workflowDefinitionInUse == "Y") {
				html = "<div class='btn btn-sm alert-danger mr-1'>" +
				       "  The workflow is use and cannot be edited!&nbsp;" +
				       "</div>";
			}
			
			if (isValid) {
				html += htmlSuccess;
			} else {
				html += htmlFail;
			}
			
			el.html(html);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function selectTrigger(targetElement) {
	var queryUrl = "/api/gdi/v1/workflow/definition";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			var options = new Array();
			
			for (var foo = 0; foo < data.length; foo++) {
				options.push(data[foo].workflowDefinitionCode);
			}

			showSelectOneDialog(targetElement, options, function(selected) {
				$("#" + targetElement).val(selected);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function selectRole(targetElement) {
	var queryUrl = "/api/gdi/v1/roles";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			var options = new Array();
			
			for (var foo = 0; foo < data.length; foo++) {
				options.push(data[foo].roleDescription);
			}

			showSelectOneDialog(targetElement, options, function(selected) {
				$("#" + targetElement).val(selected);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

function filterWorkflowDefinitionList(selectedWorkflowDefinitionCode) {
	var elName = "workflowDefinitionSelect";
	var filterType = $("#workflowTypeSelect").val();
	
	$("#" + elName).empty();
	
	if (typeof selectedWorkflowDefinitionCode == "undefined") {
		selectedWorkflowDefinitionCode = null;
	}

	var nullSelected = selectedWorkflowDefinitionCode == null ? "selected" : "";
	$("#" + elName).append("<option value='' " + nullSelected + "></option>");		// add empty option
	
	for (var foo = 0; foo < workflowDefinitionList.length; foo++) {
		var row = workflowDefinitionList[foo];
		var id = row.workflowDefinitionId;
		var name = row.workflowDefinitionCode;
		var count = row.workflowDefinitionStepCount == null ? 0 : row.workflowDefinitionStepCount;
		
		var selected = "";
		
		if ((selectedWorkflowDefinitionCode != null) && (selectedWorkflowDefinitionCode == name)) {
			selected = "selected";
		}
		
		var option = "<option value='" + id + "' " + selected + ">" + name + "</option>";
		
		if ((filterType == "all") || 
				((filterType == "with") && (count > 0)) || 
				((filterType == "without") && (count == 0))
				) {
			$("#" + elName).append(option);
		}
	}
}

function selectField(elementId, replaceAll) {
	var currentVal = $("#" + elementId).val();
	
	var schemaName = null;
	var tableName = workflowDefinitionRecordObjectName;
	
	if (typeof replaceAll == "undefined") {
		replaceAll = true;
	}
	
	if ((tableName != null) && (tableName.indexOf("."))) {
		schemaName = tableName.substring(0, tableName.indexOf("."));
		tableName = tableName.substring(tableName.indexOf(".") + 1);
	}
	
	var queryUrl = "/api/gdi/v1/" + schemaName + "/" + tableName;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			var options = new Array();
			
			for (var foo = 0; foo < data.length; foo++) {
				options.push(data[foo].columnName);
			}

			showSelectOneDialog(elementId, options, function(selected) {
				if (replaceAll) {
					$("#" + elementId).val(selected);
				} else {
					// We're in append mode so this must be a field template, surround the field name with "##" characters 
					var txt = $("#" + elementId).val();
					txt += "##" + selected + "##";
					$("#" + elementId).val(txt);
				}
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR.status + " - " + errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
	
	
	// TODO: get the workflow def's table 
	// TODO: get the fields for the table
	// TODO: let them select one (default select = curent element)
	// TODO: if selected update elementId
}

//**********************************************************************

$(document).ready(function() {
	initializeWorkflowDefinitions(null);
	
	window.setTimeout(function () {
		setDivVisibility("workflowDefinitionGraphDiv", "block");		
	}, 200);
	
});

