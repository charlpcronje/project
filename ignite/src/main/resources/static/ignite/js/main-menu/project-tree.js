var NODETYPE_PROJECT     = "P";
var NODETYPE_SUBPROJECT  = "S";
var NODETYPE_RESOURCES   = "R";
var NODETYPE_FILES       = "F"; 

var RESOURCES_TITLE      = "Resources";
var FILES_TITLE          = "Files";

var currentProject = null;

function removeNodeChildren(elementId, node) {
    if (node.children) {
        for (var i=node.children.length-1; i >= 0; i--) {
            var child = node.children[i];
            $("#" + elementId).tree('removeNode', child);
        }
    }
}

function getNodeBadge(node) {
	var badgeClass = "badge-danger";
	var badgeLabel = "Unknown";
	
	if (node.hasOwnProperty("projectStage")) {
		var status = node.projectStage;
		badgeLabel = status;

		// NONE means that the status has not been initialized in the database!!
		if (status == "NONE") {
			badgeClass = "badge-danger";
		}
		
		if (status == "CANCELLED") {
			badgeClass = "badge-dark";
		}
		
		if (status == "COMPLETED") {
			badgeClass = "badge-success";
		}

		if (
			(status == "PROPOSAL") || 
			(status == "TENDER") 
			) {
			badgeClass = "badge-warning";
		}
		
		if (
			(status == "DESIGN") || 
			(status == "CONSTRUCTION") 
			) {
			badgeClass = "badge-info";
		}
	}

	return "<span class='badge-right badge-pill " + badgeClass + "'>" + badgeLabel + "</span>";
}

function projectStageToValue(node) {
	var result = 0;
	
	if (node.hasOwnProperty("projectStage")) {
		var s = node.projectStage.toLowerCase();

		// the values returned determine the sort order of the textual status
		if (s == "active") { result = 20};
		if (s == "on-hold") { result = 80};
		if (s == "closed") { result = 100};
	}
	
	return result;
}

function initializeProjectTree() {
	//	var url = "/rest/ignite/v1/osd/tree"; //Find the top level OSDLevel1
	//	console.log(url);

	var url = "/rest/ignite/v1/project/tree";
	console.log(url);
	
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			renderProjectTree(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function addSupportingNodes(e) {
	if (e.hasOwnProperty("children")) {
		// Add Sub structures to sub-projects
		var c = e.children;
		c.forEach(c => addSupportingNodes(c));
	} else {
		// No children... (ie. no sub-projects...)
		e.children = [];
	}
	
	// Add Any nodes that we may require for UI purposes - note that these are not data related
	// Note: We pass the projectId in so that we know which project we're working with
	e.children.push({
	            			projectId: e.projectId,
	            			name: FILES_TITLE,
	            			nodeType: NODETYPE_FILES
	            	});
	e.children.push({
	            			projectId: e.projectId,
	            			name: RESOURCES_TITLE,
	            			nodeType: NODETYPE_RESOURCES
	            	});
}

function renderProjectTree(data) {
	console.dir(data);
	data.forEach(e => addSupportingNodes(e));
	
	var tree = $("#projectTree").tree({
        data: data,
        closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
        openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i>'),
        onCreateLi: function(node, $li, is_selected) {
			if (node.hasOwnProperty("projectTitle")) {
				// Indicate the status on Projects and Sub-projects
				if (node.hasOwnProperty("nodeType")) {
					var nodeType = node.nodeType;
					
					if (
							(nodeType == NODETYPE_PROJECT) ||
							(nodeType == NODETYPE_SUBPROJECT)
						) {
						$li.find(".jqtree-title").after(getNodeBadge(node));
					};
				}
			}
			
			// render "special" types of nodes
			var type = node.nodeType;
			
			if (type == NODETYPE_RESOURCES) {
		        $li.find(".jqtree-title").before("<span class='jqtree-title-icon'><i class='fas fa-users'></i></span>");
			}
			if (type == NODETYPE_FILES) {
		        $li.find(".jqtree-title").before("<span class='jqtree-title-icon'><i class='fas fa-archive'></i></span>");
			}
		} 
    });

	$("#projectTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#projectTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);

		    	doubleClickNode(event.node);
		    }
    );
	
	$("#projectTree").on(
		    'tree.click',
		    function(event) {
		    	event.click_event.preventDefault();
		    	console.dir(event.node);
		    	showNode(event.node);
		    }
    );
	
	openActiveNodes();
	selectFirstNode();
	return tree;
}

function openActiveNodes() {
	var nodes = $("#projectTree").tree("getTree");
	for (var foo = nodes.children.length; foo > 0; foo--) {
		node = nodes.children[foo - 1];
		
		if (node.hasOwnProperty("nodeType") && node.hasOwnProperty("projectStage")) {
			// By default don't open these projects
			var active = (node.projectStage != "CANCELLED") && 
			             (node.projectStage != "COMPLETED");
			             
			if ((node.nodeType == NODETYPE_PROJECT) && (active)) {
				$("#projectTree").tree("openNode", node);
			}
		}
	}
}

function doubleClickNode(node) {
	var tree = $("#projectTree").tree();

	clearTextSelection();
    tree.tree("selectNode", node);
    
    if (node.is_open) {
    	// only show the node if its opened
    	showNode(node);
    }
}
function selectFirstNode() {
	var data = $("#projectTree").tree("getTree");

	if (data.hasOwnProperty("children")) {
		if (data.children.length > 0) {
			var node = data.children[0];
			doubleClickNode(node);
		}
	}
}

function showNode(node) {
	var path = "";

	// initialize our current project
	currentProject = {
			projectId: null,
			projectName: null,
			subProjectName: null,
			nodeType : null,
			projectStage: null
	};
	
	var data = node.getData(true);
	
	if (data.length > 0) {
		data = data[0];
	} else {
		// this will force all panels to be invisible
		data = { 
			nodeType: null 
		};
	}
	
	// walk backwards up the path so that we can build a breadcrumb
	var n = node;
	currentProject.projectId = n.projectId;
	currentProject.nodeType = n.nodeType;
	currentProject.projectStage = n.projectStage;
	
	// walk backwards through the tree
	while (n.parent != null) {
		if (path != "") {
			path = " &gt; " + path;
		}
		
		// if we find a project or sub project get its details as the parent
		if (n.nodeType == NODETYPE_PROJECT) {
			currentProject.projectName = n.name;
			if (currentProject.projectId == null) {
				currentProject.projectId = n.projectId;
				currentProject.projectStage = n.projectStage;
			}
		}
		
		if (n.nodeType == NODETYPE_SUBPROJECT) {
			currentProject.subProjectName = n.name;
			if (currentProject.projectId == null) {
				currentProject.projectId = n.projectId;
				currentProject.projectStage = n.projectStage;
			}
		}

		path = n.name + path;
		n = n.parent;
	}

	$("#projectHeader").html(path);
	
	if (data.nodeType == NODETYPE_PROJECT) {
		loadProjectData();
	}
	if (data.nodeType == NODETYPE_FILES) {
		loadFileManager();
	}
	// TODO: we need to save this data so that we know what project we're working on so that "sub" operations can work with the selected project
	
	setDivVisibility("projectPanel", data.nodeType == NODETYPE_PROJECT ? "block" : "none");
	setDivVisibility("subProjectPanel", data.nodeType == NODETYPE_SUBPROJECT ? "block" : "none");
	setDivVisibility("resourcesPanel", data.nodeType == NODETYPE_RESOURCES ? "block" : "none");
	setDivVisibility("filesPanel", data.nodeType == NODETYPE_FILES ? "block" : "none");
}

function loadProjectData() {
	var url = "/rest/ignite/v1/project/" + currentProject.projectId;

	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			var startDate = null;
			
			if ((data.hasOwnProperty("startDate")) && (data.startDate != null)) {
				startDate = new Date(data.startDate);
			}
			
			$("#prjProjecNumber").val(data.projectNumberBigInt);
			populateSelect("prjParticipantIdClient", "/rest/ignite/v1/participant", "participantId", "tradingName", data.participantIdClient, false);
			populateSelect("prjIndividualIdProjectAdmin", "/rest/ignite/v1/individual", "individualId", "firstName", data.individualIdProjectAdmin, true);
		
			$("#prjProjectTitle").val(data.projectTitle);
			$("#prjProjectDescription").val(data.description);
			$("#prjStartDate").datepicker("setDate", startDate);
			$("#prjProjectStage").val(data.projectStage);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function initializeClients() {
	populateSelect("clientFilter", "/rest/ignite/v1/participant/all-in-view", "participantId", "systemName", null, "All clients");
}

function newProject() {
	// TODO: Maybe a Wizard is needed here for all the project information as it may affect several tables
	
	// TODO: prompt for client 
	// TODO: prompt for project defaults
	// TODO: create required directory structures
	// TODO: add resources?
	
	npwActivePage = 1;
	
	// Reset of form values to initial state 
	$("#npwPage1Form")[0].reset();
	$("#npwPage2Form")[0].reset();
	$("#npwPage3Form")[0].reset();
	$("#npwPage4Form")[0].reset();
	$("#npwPage5Form")[0].reset();

	addDirtyHandlerForms("newParticipantWizardDialog", [
		"npwPage1Form",
		"npwPage2Form",
		"npwPage3Form",
		"npwPage4Form",
		"npwPage5Form",
		"npwPageFinish"
	]);
	
	// Can initialize <SELECT's here

	updateProjectWizardButtons();
	
	showModalDialog("newProjectWizardDialog");
}

function filterByClient() {
	// TODO: complete
	var clientName = $("#clientFilter").val();
	
	// Filter the data tree....	
}

function loadFileManager() {
//	var url = "/rest/ignite/v1/project/file-manager?project=" + currentProject.projectId;
	var url = "/rest/ignite/v1/project/file-manager/" + currentProject.projectId;

	initializeFileManager("fileManagerPanel", "/", url);
}

function closeWizard() {
	deleteDirtyHandlerForms("newProjectWizardDialog", [
		"npwPage1Form",
		"npwPage2Form",
		"npwPage3Form",
		"npwPage4Form",
		"npwPage5Form",
		"npwPageFinish"
		]);
	$("#newProjectWizardDialog").modal("hide");
}

function npwGoBack() {
	if (wizardPageContainsErrors(npwActivePage)) {
		return;
	}

	npwActivePage--;
	if (npwActivePage < 1) {
		npwActivePage = 1;
	}
	
	updateProjectWizardButtons();
}

function npwGoNext() {
	if (wizardPageContainsErrors(npwActivePage)) {
		return;
	}
	
	npwActivePage++;
	if (npwActivePage > 7) {
		npwActivePage = 7;
	}
	
	updateProjectWizardButtons();
}

function updateProjectWizardButtons() {
	setDivVisibility("npwPage1", npwActivePage == 1 ? "block" : "none");
	setDivVisibility("npwPage2", npwActivePage == 2 ? "block" : "none");
	setDivVisibility("npwPage3", npwActivePage == 3 ? "block" : "none");
	setDivVisibility("npwPage4", npwActivePage == 4 ? "block" : "none");
	setDivVisibility("npwPage5", npwActivePage == 5 ? "block" : "none");
	setDivVisibility("npwFinish", npwActivePage == 6 ? "block" : "none");
	
	setElementEnabled("npwBackBtn", npwActivePage > 1);
	setElementEnabled("npwNextBtn", npwActivePage < 6);
	setElementEnabled("npwFinishBtn", npwActivePage == 6);

	if (npwActivePage == 1) {
		// initialize any selects? 
	}
	
	if (npwActivePage == 6) {
		// Show the final/confirmation page
	}
}

function wizardPageContainsErrors(page) {
	// TODO: complete
	return false;	
}

function saveWizardProject() {
	// TODO: complete	
}

// ***********************************************************************


$(document).ready(function() {
	// Any initialization
	
	$("#projectsTreePanel").resizable({
		handles: "e",
		alsoResizesReverse: "projectsTreeDetail"
	})
	
	initializeClients();
	// initializeProjectTree();
} );
