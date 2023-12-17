var ppsdRoleTable = null;
// setElementEnabled("saveProjectSdButton", false);
var somethingChangedinDialog = null;

// **********************************
//Project ParticipantSds Tab -- Start

// initializePPTree -- Start
function initializePPTree(projectId) {

	//clear the tree first... Werk nie!
	// emptyPPTree();
    var ppTree = $("#ppTree"); //.jstree(true);
    $("#ppTree .jstree-leaf, .jstree-anchor").each(function(){
        ppTree.delete_node($(this).attr('id'));

    });
	
	var url = "/rest/ignite/v1/project/pp-tree/" + projectId ;  // Find the top level

	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			renderPPTree(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
		
	});
	
//	ppTree.redraw(full);
}

function addSupportingPPNodes(e) {

	//	if (e.hasOwnProperty("anyChildren")) {
	//		if (e.anyChildren == "Y") {
	//			var a = e.participantNameContracted 
	//   			e.name =  " > " + a; 
	//		} else {
	//			var a = e.participantNameContracted
	//		   e.name =  a; 
	//		}
	//	}
		
	var a = e.participantNameContracted
	e.name =  a; 

	if (e.hasOwnProperty("children")) {
		// Add Sub structures to sub-projects
		var c = e.children;
		c.forEach(c => addSupportingPPNodes(c));
	} else {
		// No children... (ie. no sub-projects...)
		e.children = [];
	}
	
}

function emptyPPTree()
{
    var ppTree = $("#ppTree"); //.jstree(true);
    $("#ppTree .jstree-leaf, .jstree-anchor").each(function(){
        ppTree.delete_node($(this).attr('id'));

    });
}

function renderPPTree(data) {
	data.forEach(e => addSupportingPPNodes(e));
	
	$('#ppTree').tree('destroy');
	
	var tree = $("#ppTree").tree({
        data: data,
        	//        showEmptyFolder: true,
        autoOpen: true,
			//        closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
			//        openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i'),
        onCreateLi: function(node, $li, is_selected) {
        	if (node.isIndividual == "Y") {
        		$li.find('.jqtree-title').before('<i <span class="jqtree-icon fas fa-user">&nbsp;</span></i>');
        		// $li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" >&nbsp;</span>'); // no break space - add a space that HTML will not ignore!
        		// $li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" style="height:4px; width:4px;"> <img src="blank.gif" height="1px" width="1px"></span>'); 
			}
        }
    });

	$("#ppTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#ppTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);

		    	doubleClickNode(event.node);
		    }
    );
	
	$("#ppTree").on(
		    'tree.click',
		    function(event) {
		    	event.click_event.preventDefault();
		    	showNode(event.node);
		    }
    );
	
	openActivePPNodes();
	selectFirstNode();
	
	return tree;
}

function openActivePPNodes() { //Niks gedoen nie...
	var nodes = $("#ppTree").tree("getTree");
	
	for (var foo = nodes.children.length; foo > 0; foo--) {
		node = nodes.children[foo - 1];
		
//		if (node.hasOwnProperty("nodeType") && node.hasOwnProperty("projectStage")) {
//			// By default don't open these projects
//			var active = (node.projectStage != "CANCELLED") && 
//			             (node.projectStage != "COMPLETED");
//			             
//			if ((node.nodeType == NODETYPE_PROJECT) && (active)) {
			$("#ppTree").tree("openNode", node);
//			}
//		}
	}
}

function doubleClickNode(node) {
	var tree = $("#pTree").tree();

	clearTextSelection();
    tree.tree("selectNode", node);
    
    if (node.is_open) {
    	// only show the node if its opened
    	showNode(node);
    }
}

function selectFirstNode() {
	var data = $("#ppTree").tree("getTree");

	if (data.hasOwnProperty("children")) {
		if (data.children.length > 0) {
			var node = data.children[0];
			doubleClickNode(node);
		}
	}
}

function showNode(node) {
	var path = "";
	if (node.isIndividual == "Y") {
		// Show the Service Disciplines and Role of that has been assigned to the individual
		initializePPSdRoleTable(node.projectParticipantIdContracted, node.name);
	} else {
		// Show all resources in the tree under this participant
		showPPResourcesTable(node, node.projectParticipantIdContracted, node.name);
	}
}

//****************************** Project Participant Tree End ****************************** //


//Participant Sd -- Start
function showPPSdEmptyPanel() {
	setDivVisibility("ppsdEmptyPanel", "block");
	setDivVisibility("ppResourceSdsPanel", "none");
	setDivVisibility("ppsdResourceMainPanel", "block");
}

function showRoleStageEmptyPanel() {
	setDivVisibility("ppsdRoleStageEmptyPanel", "block");
	setDivVisibility("ppsdRoleStageGridPanel", "none");
	setDivVisibility("ppsdRoleStageMainPanel", "block");
}

function showRoleStagePanel() {
	setDivVisibility("ppsdRoleStageEmptyPanel", "none");
	setDivVisibility("ppsdRoleStageGridPanel", "block");
	setDivVisibility("ppsdRoleStageMainPanel", "block");
}

function addDataToField(elementId, data) {
	
	if (data === undefined) {
		data = null;
	}
	var hasData = ((data != null) && (data != ""));

//	var html = "<div class='alert alert-danger' role='alert'>" +
	
	var html = "<div class='table table-striped table-advance table-hover'>" +
	           "	<table width='100%'>" +
	           "		<tr>" +
//	           "            <td width='50px' valign='top'>style='font-size:28pt !important'></i></td>" +
//	           "            <td width='50px' valign='top'><i class='fas fa-exclamation-triangle' style='font-size:28pt !important'></i></td>" +
	           "            <td id='" + elementId + "_formErrors' valign='top' width='*'>" +
	           "                " + (data == null ? "" : data) +
	           "            </td>" +
	           "        </tr>" +
	           "    </table>" +
	           "</div>";

	$("#" + elementId).html(data == null ? "" : html);
	setDivVisibility(elementId, hasData ? "block" : "none");

	return hasData;
}


function findNodesUnderneath(e) {

	if (e.isIndividual == "Y") {
		resourceList += e.name + "<br>";
	}

	if (e.hasOwnProperty("children")) {
		// Add Sub structures to sub-projects
		var c = e.children;
		c.forEach(c => findNodesUnderneath(c));
	} else {
		// No children... (ie. no sub-projects...)
		e.children = [];
	}
	
}


// Show all resources in the tree under this non individual participant
function showPPResourcesTable(node, projectParticipantId, participantName) {
	if (projectParticipantId == null) {
		return;
	}

	setDivVisibility("ppsdEmptyPanel", "none");
	setDivVisibility("ppResourceSdsPanel", "none");
	setDivVisibility("ppsdResourceMainPanel", "block");
	
	setDivVisibility("ppResourcesPanel", "block");
	setDivVisibility("ppResourcesListDiv", "block");

	var ppsdHeading = participantName + ": Resources"; 
	$("#ppsdHeader").html(ppsdHeading);
	$("#ppTheName").val(participantName);
	showRoleStageEmptyPanel()


	resourceList = "";
	findNodesUnderneath(node);
	if (addDataToField("ppResourcesListDiv", resourceList)) {
		return;
	}
}


function initializePPSdRoleTable(projectParticipantId, participantName) {
	if (projectParticipantId == null) {
		return;
	}

	setDivVisibility("ppsdEmptyPanel", "none");
	setDivVisibility("ppResourceSdsPanel", "block");
	setDivVisibility("ppsdResourceMainPanel", "block");
	
	setDivVisibility("ppResourcesPanel", "none");
	setDivVisibility("ppResourcesListDiv", "none");

	var ppsdHeading = participantName + ": Service Disciplines"; 
	var queryUrl =  "/rest/ignite/v1/project-participant/sd-role/" + projectParticipantId;
	
	setDivVisibility("ppsdEmptyPanel", "none");
	setDivVisibility("ppsdPanel", "block");

	$("#ppsdTabDlgErrorDiv").val("");
	$("#ppsdHeader").html(ppsdHeading);
	$("#ppTheName").val(participantName);
	showRoleStageEmptyPanel()
	
	var columnsArray = [
	                    
		{ data: "projectParticipantSdRoleId" },		//0
		{ data: "projectParticipantId" },			//1
		{ data: "participantIdBeneficiary" },		//2
		{ data: "systemNameBeneficiary" },			//3
		{ data: "sdCode" },							//4
		{ data: "sdName" },							//5
		{ data: "roleOnAProjectId" },			//6
		{ data: "roleOnAProjectName" },			//7
		{ data: "projectSdId" },					//8
		{ data: "sdRoleId" }						//9
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 6, 8, 9]     //[0, 1, 2, 3, 6, 8, 9]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editPPSdRole(null, projectParticipantId, participantName);
			}
		},
		{
			attr: {
				id: "promptDeletePPSdRoleBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeletePPSdRole();
			}
		}
	];
	
	ppsdRoleTable = initializeGenericTable("ppsdRoleTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray
//			                            ,
//			                            function() {
//											// We do not allow updates.  Delete or ad a new record only
//										  alert("Record can only be deleted, or new record added. No updates allowed");
//										}

	);
	
	ppsdRoleTable.off('deselect')
	ppsdRoleTable.on('deselect', function (e, dt, type, indexes) {
		updatePPSdToolbarButtons();
		showRoleStageEmptyPanel();
	} );

	
	ppsdRoleTable.off('select')
	ppsdRoleTable.on('select', function (e, dt, type, indexes) {
		updatePPSdToolbarButtons();
		showRoleStages(dt.data());
	} );
	
	setElementEnabled("saveProjectSdButton", false);
	somethingChangedinDialog = false;
	
	updatePPSdToolbarButtons();
}



function showRoleStages(rowSelector) {
		
	if (rowSelector == null) {
		showRoleStageEmptyPanel();
		return;
	}
	
	var projectParticipantSdRoleId = rowSelector.projectParticipantSdRoleId;
	var sdName = rowSelector.sdName;
	var roleOnAProjectName = rowSelector.roleOnAProjectName;
	var projectSdId = rowSelector.projectSdId;
	
	showRoleStagePanel();
	
	var columnsArray = [
	            		{ data: "ppSdRoleStageId" },          //0 MySql-TableName: VPpSdRoleStage
	            		{ data: "projectParticipantSdRoleId" }, //1
	            		{ data: "projectSdStageId" },         //2
	            		{ data: "projectParticipantId" },     //3
	            		{ data: "projectId" },                //4
	            		{ data: "participantId" },            //5
	            		{ data: "projectSdId" },              //6
	            		{ data: "projectStageId" },           //7
	            		{ data: "orderNumber" },              //8
	            		{ data: "stageName" },                //9
	            		{ data: "description" },              //10
	            		{ data: "startDate" },                //11
	            		{ data: "endDate" },                  //12
	            	];
	            	
	
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0,1,2,3,4,5,6,7,10,11,12]
	},{
		width: "40%",  // "30px",    //maw so klein as moontlik     BAnk Statement
		targets: 8
	},{
		width: "60%",  // "30px",    //maw so klein as moontlik     BAnk Statement
		targets: 9
	}	];
	
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				console.log("linkProjectSdStage");
				linkProjectSdStage(null, projectParticipantSdRoleId, sdName, roleOnAProjectName, projectSdId);
			}
		},
		{
			attr: {
				id: "deletePpSdStageBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeletePpSdStage();
			}
		}
		
	];
	

	var queryUrl =  "/rest/ignite/v1/pp-sd-role-stage/vppsd-role/" + projectParticipantSdRoleId;
	ppsdRoleStageTable = initializeGenericTable("ppsdRoleStageTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											linkProjectSdStage(rowSelector, projectParticipantSdRoleId, sdName, roleOnAProjectName, projectSdId);
										},
			                            null,
			                            15,
			                            6,
										"",
										true,
										false
	);
	
	ppsdRoleStageTable.off('deselect')
	ppsdRoleStageTable.on('deselect', function (e, dt, type, indexes) {
		updatePPSdRoleStageToolbarButtons();
		
	} );

	ppsdRoleStageTable.off('select')
	ppsdRoleStageTable.on('select', function (e, dt, type, indexes) {
		updatePPSdRoleStageToolbarButtons();
		
	} );
	
	updatePPSdRoleStageToolbarButtons();	



	
}





//linkProjectSdStage -- Start
function linkProjectSdStage (rowSelector, projectParticipantSdRoleId, sdName, roleOnAProjectName, projectSdId) {

	var data = {}; 
	var errors = "";
		
	if (rowSelector != null) {
		data = rowSelector;
	} else {
		data.ppSdRoleStageId = null;
		data.projectParticipantSdRoleId = projectParticipantSdRoleId;
		data.projectSdStageId = null;
	}
	
	ppsdRoleStageTable.rows().deselect();
	
	$("#psds2ProjectSdId").val(projectSdId);
	$("#pParticipantName").val($("#ppTheName").val()); 
	$("#ppSdRoleName").val(sdName + " : " + roleOnAProjectName); 
	
	$("#pProjectParticipantSdRoleId").val(data.projectParticipantSdRoleId); 
	
	
	$("#pSdStageName").val(""),
	$("#pProjectSdStageId").val("")	
	
	// Set the Save Button to disabled
	setElementEnabled("saveProjectSdStageDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("ppSdRoleStageDialog");
}
//linkProjectSdStage -- End

function updatePPSdRoleStageToolbarButtons() {
	var hasSelected = ppsdRoleStageTable.rows('.selected').data().length > 0;
	setTableButtonState(ppsdRoleStageTable, "deletePpSdStageBtn", hasSelected);	
}


function updatePPSdToolbarButtons() {
	var hasSelected = ppsdRoleTable.rows('.selected').data().length > 0;
	setTableButtonState(ppsdRoleTable, "promptDeletePPSdRoleBtn", hasSelected);	
}
	
function promptDeletePPSdRole() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Project Participant's Service Discipline link?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deletePPSdRole(ppsdRoleTable);
			   }
	);
}


function promptDeletePpSdStage() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected record?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deletePPSdRoleStage(ppsdRoleStageTable);
			   }
	);
}



function deletePPSdRoleStage(tbl) {
	var postUrl = "/rest/ignite/v1/pp-sd-role-stage/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Participant's linked Service Discipline and Role has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updatePPSdToolbarButtons();
			}
	);
}



function deletePPSdRole(tbl) {
	var postUrl = "/rest/ignite/v1/project-participant-sd-role/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Participant's linked Service Discipline and Role has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updatePPSdToolbarButtons();
			}
	);
}

function projectSdDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveProjectSdButton", true);
}

function closeprojectSdDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("participantChildDialog");
				resetParticipantChildChanges();
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("participantChildDialog");
		resetParticipantChildChanges();
	}
}




function closeppSdRoleStageDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("ppSdRoleStageDlgErrorDiv", "none");
				closeModalDialog("ppSdRoleStageDialog");
			});
	} else {
		setDivVisibility("ppSdRoleStageDlgErrorDiv", "none");
		closeModalDialog("ppSdRoleStageDialog");
	}
} // closeppSdRoleStageDialog  --  END





//Project Participant Sds Tab -- End
//**********************************

//editPPSd  -- Start
function editPPSdRole (rowSelector, projectParticipantId, participantName) {
	console.log("editPPSdRole");
	console.log (rowSelector, projectParticipantId, participantName);
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var errors = "";
	
	if (rowSelector != null) {
		data = ppsdRoleTable.row(rowSelector).data();
	} else { 
		data.projectParticipantId = projectParticipantId;
		data.projectParticipantSdRoleId = null;
		$("#ppsdRoleOnAProject").val();
	} 
	ppsdRoleTable.rows().deselect();
	
	resetDialog();
	$("#ppsdProjectParticipantSdRoleId").val(data.projectParticipantSdRoleId);
	$("#ppsdProjectParticipantId").val(projectParticipantId);
	$("#ppsdProjectParticipantName").val(participantName);
	$("#ppsdCode").val(data.sdCode);

	$("#ppsdRoleId").val();
	$("#ppsdProjectSdId").val(data.projectSdId);
	
	var projectId = $("#pdProjectId").val();
	$("#ppsdRoleOnAProject").val();

	var queryUrl="/rest/ignite/v1/project/sd/list/" + projectId;  
	console.log(queryUrl);

	populateSelect("ppsdProjectSdId", //elementId, html select element that will be populated
		       queryUrl, //url, url where it must get the data (you can paste in browser window to see the data)
		       "projectSdId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "sdCodeAndName", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
//		       function() { //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
//					showRolesForSelectedSd();
//		
//				}
	);
	
	// Set the Save Button to disabled
	setElementEnabled("savePPSdRolenButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("ppsdRoleDialog");
}
//editPPSd -- End

// Show Roles for the Service discipline -- Start
function showRolesForSelectedSd() {
	
	var projectSdIdSelected = $("#ppsdProjectSdId").val();
	if (projectSdIdSelected == null) {
		$("#ppsdRoleOnAProject").val();
		return;
	}
	
	var projectParticipantIdSelected = $("#ppsdProjectParticipantId").val();
	var queryUrl="/rest/ignite/v1/sd-role/list-for-project-sd/available/" + projectSdIdSelected +"/" + projectParticipantIdSelected;
	populateSelect(
				"ppsdRoleOnAProject", //elementId, html select element that will be populated
				queryUrl, //url, url where it must get the data (you can paste in browser window to see the data)
				"roleOnAProjectId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
				"name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
				null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
				true //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	);
	
}
//Show Roles for the Service discipline -- End

//savePPSd -- Begin
function savePPSdRole() {
	// Only allow adding of new records
	var postUrl = "/rest/ignite/v1/project-participant-sd-role/new";
	console.log(postUrl);
	
	var postData = {
			
			projectParticipantId: $("#ppsdProjectParticipantId").val(),
			projectSdId: $("#ppsdProjectSdId").val(),
			roleOnAProjectId: $("#ppsdRoleOnAProject").val()
	};
	
	var errors = "";

	// validate
	if (postData.projectSdId == "") {
		errors += "A Service Discipline Code is required<br>";
	}
	
	if (postData.roleOnAProjectId == "") {
		errors += "A Role is required<br>";
	}
	
	if (showFormErrors("ppsdDlgErrorDiv", errors)) {
		return;
	}

	//	// Is the code readonly?  If it is, then the record already exists.
	//	if ((postData.projectParticipantSdRoleId != null) || (postData.projectParticipantSdRoleId != "")) {
	//		//This is an update
	//		postUrl = "/rest/ignite/v1/project-participant-sd";
	//	}
	
	saveEntry(postUrl, postData, "ppsdRoleDialog", "The Project Participant Service and Role has been saved.", ppsdRoleTable);

}
// savePPSd -- End





//saveppSdRoleStageDialog -- Begin
function saveppSdRoleStageDialog() {
	// Only allow adding of new records
	var postUrl = "/rest/ignite/v1/pp-sd-role-stage/new";
	
	var postData = {
			
			ppSdRoleStageId: null,
			projectParticipantSdRoleId: $("#pProjectParticipantSdRoleId").val(),
			projectSdStageId: $("#pProjectSdStageId").val()
	};
	
	var errors = "";

	// validate
	if (postData.projectSdStageId == "") {
		errors += "A Stage is required<br>";
	}
	
	if (showFormErrors("ppSdRoleStageDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "ppSdRoleStageDialog", "The Project Stage was linked to the Participant.", ppsdRoleStageTable);

}
//saveppSdRoleStageDialog -- End





function ppsdRoleDialogChanged() {
	setElementEnabled("savePPSdRolenButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}	

function resetDialog() {
	$("#ppsdRoleOnAProject").val("");
}

function closeppsdRoleDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("ppsdRoleDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		resetDialog();
		closeModalDialog("ppsdRoleDialog");
	}
}




//editSelectProjectStageForP -- Start
function editSelectProjectStageForP() {
	
	var projectSdId = $("#psds2ProjectSdId").val();
	var projectParticipantSdRoleId = $("#pProjectParticipantSdRoleId").val();
	
	var queryUrl =  "/rest/ignite/v1/project/sd/project-stages-remaining/" + projectSdId + "/" + projectParticipantSdRoleId;

	var targetId = "pProjectSdStageId";
	var targetName = "pSdStageName";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName = "projectSdStageId";
			var refColumnName = "projectSdStageId";
			var columns = [
				{ data: "projectSdStageId", name: "Id" },
				{ data: "orderNumber", name: "Stage Number" },
				{ data: "stageName", name: "Stage Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: 0
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectSdStageId;
				var stageName = row.stageName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(stageName);
				$("#" + targetName).trigger("change");
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectProjectStageForP -- End

