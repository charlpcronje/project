

function initializeTfsGeneralTree(completeMethod) {
	var url = "/rest/ignite/v1/typical-folder-structure/find-top-level-in-tree-view";  // Find the top level 
	console.log(url);
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			renderTfsGeneralTree(data);
			if (completeMethod !== undefined) {  // Doen hierdie om die node weer te vind na regen na 'n rekord gesave is.
				completeMethod();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});
}



function addSupportingNodesTfs(e) {

	// Ingrid commented this out... Don't think we need this...
		if (e.hasOwnProperty("anyChildren")) {
			
			if (e.anyChildren == "Y") {
				e.name = e.tfsName; 
			}
		}
		
	if (e.hasOwnProperty("children")) {
		// Add Sub structures to sub-projects
		var c = e.children;
		c.forEach(c => addSupportingNodesTfs(c));
	} else {
		// No children... (ie. no sub-projects...)
		e.children = [];
	}
	e.id = e.typicalFolderStructureId;
	e.name = e.tfsName
}



function renderTfsGeneralTree(data) {
	data.forEach(e => addSupportingNodesTfs(e));
	console.log(data);
	
	$('#tfsGeneralTree').tree('destroy');
	$('#sdTree').tree('destroy');
	var style=" style='100%'";
	var tree = $("#tfsGeneralTree").tree({
        data: data,
//        showEmptyFolder: true,
//        autoOpen: true,
//        closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
//        openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i>'),
        onCreateLi: function(node, $li, is_selected) {
        	if (node.perSdFlag == "Y") {
        		$li.find('.jqtree-title').after('<i class="jqtree-icon badge-pill badge-info" title= "Per Service Discipline">&</i>'); // &nbsp; : no break space - add a space that HTML will not ignore!
//        		$li.find('.jqtree-title').after('<i class="jqtree-icon fab fa-bitbucket">&nbsp;</i>'); // &nbsp; : no break space - add a space that HTML will not ignore!
        		// data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
//        		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" >&nbsp;</span>'); // no break space - add a space that HTML will not ignore!
//        		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" style="height:4px; width:4px;"> <img src="blank.gif" height="1px" width="1px"></span>'); 
			}
        }
    });

	$("#tfsGeneralTreePanel").css({
		overflow: "scroll"
	});
	
	$("#tfsGeneralTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#tfsGeneralTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);

		    	doubleClickNodeTfs(event.node);
		    }
    );
	
	$('#tfsGeneralTree').on(
		    'tree.select',
		    function(event) {
		        if (event.node) {
		            // node was selected
		            var node = event.node;
			    	showNodeTfs(event.node);
		        }
		        else {
		        	showEmptyNodeTfs();

		        	// event.node is null
		            // a node was deselected
		            // e.previous_node contains the deselected node
		        }
		    }
		);
	
//	$("#tfsGeneralTree").on(
//		    'tree.click',
//		    function(event) {
//		    	event.click_event.preventDefault();
//		    	showNodeTfs(event.node);
//		    }
//    );
	
	openActiveNodesTfs();
//	selectFirstNodeTfs();
	return tree;
} // renderTfsGeneralTree -- END



function openActiveNodesTfs() {
	var nodes = $("#tfsGeneralTree").tree("getTree");
	for (var foo = nodes.children.length; foo > 0; foo--) {
		node = nodes.children[foo - 1];
		
		//			if (node.hasOwnProperty("anyChildren")) {
		//			//			// By default don't open these projects
		//			//			var active = (node.projectStage != "CANCELLED") && 
		//			//			             (node.projectStage != "COMPLETED");
		//			//			             
		//			//			if ((node.nodeType == NODETYPE_PROJECT) && (active)) {
		//			//				$("#tfsGeneralTree").tree("openNode", node);
		//			//			}
		//			}
				}
} // openActiveNodesTfs -- END



function doubleClickNodeTfs(node) {
	var tree = $("#tfsGeneralTree").tree();

	clearTextSelection();
    tree.tree("selectNode", node);
    
    if (node.is_open) {
    	// only show the node if its opened
    	showNodeTfs(node);
    }
}



function selectFirstNodeTfs() {
	var data = $("#tfsGeneralTree").tree("getTree");

	if (data.hasOwnProperty("children")) {
		if (data.children.length > 0) {
			var node = data.children[0];
			doubleClickNodeTfs(node);
		}
	}
}



function showEmptyNodeTfs() {
	
	$("#selectedTfsId").val("");
	$("#tfsTypicalFolderStructureId").val("");
	$("#tfsServiceDisciplineIdIndustry").val("");
	$("#tfsName").val("");
	$("#tfsOrderNumber").val("");
	$("#tfsDescription").val("");
	$("#perSdFlag").prop("checked", false);
	$("#tfsParentId").val("");
	$("#tfsParentName").val("");
	
	// Set the Save Button to disabled
	// Disabel the  if update, enable if insert
	// $("#folderStructure").prop("readonly", false);
	setElementEnabled("savefolderStructureButton", false); //Disable Save button when initializing
	
	// $("#tfsGeneralTreeTabDlgErrorDiv").display(false);
	setDivVisibility("tfsGeneralTreeTabDlgErrorDiv", "none");
	
//	setDivVisibility("tfsServiceDisciplineTreeEmptyPanel", "block");
//	setDivVisibility("tfsServiceDisciplineTreePanel", "none");
//	tfsServiceDisciplineTreeEmptyPanel.innerText = "None Selected.";
//	
//	setDivVisibility("tfsRolesAvailableEmptyPanel", "block");
//	setDivVisibility("tfsRolesAvailablePanel", "none");
//	setDivVisibility("tfsRolesAvailableIndustryPanel", "none");
	
//	setDivVisibility("tfsGeneralTreeHeaderPanel", "block");
//	setDivVisibility("tfsRolesAvailableIndustryPanel", "none");
//	setDivVisibility("tfsGeneralTreeAvailableHeaderParentPanel", "none");
	
//	headerRolesAssigned.innerText = "";
//	headerRolesAvailable.innerText = "";
	$("#addFolderStructureButton").attr("title", "Select a Folder");
	
} // showEmptyNodeTfs -- End 



function showNodeTfs(node) {
	
	$("#addFolderStructureButton").attr("title", "Add New Folder below this level");
	
	$("#selectedTfsId").val(node.typicalFolderStructureId);
	$("#tfsTypicalFolderStructureId").val(node.typicalFolderStructureId);
	$("#tfsServiceDisciplineIdIndustry").val(node.serviceDisciplineIdIndustry);
	$("#tfsName").val(node.tfsName);
	$("#tfsOrderNumber").val(node.orderNumber);
	$("#tfsDescription").val(node.description);
	$("#perSdFlag").prop("checked", (node.perSdFlag == 'Y' ? true : false));
	$("#tfsParentId").val(node.typicalFolderStructureIdParent);
	$("#tfsParentName").val(node.parentName);
	
	// Set the Save Button to disabled
	// DisabLe the Id if update, enable if insert
//	$("#tfsTypicalFolderStructureId").prop("readonly", (node.tfsTypicalFolderStructureId != null) &&(node.tfsTypicalFolderStructureId != ""));
	setElementEnabled("savefolderStructureButton", false); //Disable Save button when initializing
	
	// $("#tfsGeneralTreeTabDlgErrorDiv").display(false);
	setDivVisibility("tfsGeneralTreeTabDlgErrorDiv", "none");
   	
} // showNodeTfs -- End 
//****************************************************************************** //
//************************** tfs Tree End ****************************** //




//folderStructureDialogChanged -- Start
function folderStructureDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("savefolderStructureButton", true);
}
//folderStructureDialogChanged -- End

function promptDeletefolderStructure() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Folder?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deletefolderStructure();
			   }
	);
}



function deletefolderStructure() {
	var postUrl = "/rest/ignite/v1/typical-folder-structure/delete";
	var tfsId = $("#tfsTypicalFolderStructureId").val();

	// Disable delete button when record has been deleted.
	var data = {
		typicalFolderStructureId:tfsId
	}
	
	//Get the parent of tfsId
	var currentNode = $('#tfsGeneralTree').tree('getSelectedNode');
	var parent = currentNode.parent;
	
	var id = parent.id;
	
	saveEntry(postUrl, data, null, "The Service Discipline has been deleted.", null,
			function(){
				initializeTfsGeneralTree(function() {
	
					if (id == null) {
				    	showEmptyNodeTfs();
				    	return;
					}
					var node = $('#tfsGeneralTree').tree('getNodeById', id);
					//Load the node,
	  				$('#tfsGeneralTree').tree('selectNode', node);
					$('#tfsGeneralTree').tree('scrollToNode', node);
				});
				$("#selectedtfsId").val(node.id);
				$("#tfsTypicalFolderStructureIdIndustry").val(node.folderStructureIdIndustry);

				$("#tfsParentId").val(node.parenttfsId);
				$("#tfsParentName").val(node.parenttfsName);
				$("#tfsTypicalFolderStructureId").val(node.folderStructureId);
				$("#tfsName").val(node.tfsName);

			});
} //  deletefolderStructure  -- END



//savefolderStructure -- Start
function savefolderStructure() {
	var postUrl = "/rest/ignite/v1/typical-folder-structure/new"; // New record
	
	var postData = {};
	postData = {

			typicalFolderStructureId : $("#tfsTypicalFolderStructureId").val(),      //0 MySql-TableName: folderStructure
			typicalFolderStructureIdParent : $("#tfsParentId").val(),        //1
			typicalFolderStructureIdIndustry : $("#tfsTypicalFolderStructureIdIndustry").val(),    //2 hier
			
			orderNumber : $("#tfsOrderNumber").val(), 
			name : $("#tfsName").val(), 
			description : $("#tfsDescription").val(), 
			perSdFlag : $("#perSdFlag").is(":checked") ? "Y" : "N"
	}
	

	var errors = "";
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Folder Name is required<br>";
	}

//	if ((postData.orderNumber == null) || (postData.orderNumber == "")) {
//		errors += "An order number is required<br>";
//	} else {
//		if (!isNumber(postData.orderNumber)) {
//			errors += "A numeric order number is required<br>";
//		}
//	}

	if (showFormErrors("tfsGeneralTreeTabDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.typicalFolderStructureId != null) && (postData.typicalFolderStructureId != "")) {
		//This is an update
      postUrl = "/rest/ignite/v1/typical-folder-structure"; 
	} else {
		postData.typicalFolderStructureId = null;  //empty string werk nie
	} 
	
	console.log(postUrl);
	console.log(postData);
	
	saveEntry(postUrl, postData, "folderStructureDialog", "The Folder has been saved.", null,
			function(request, response) {
				var nodeId =  response.typicalFolderStructureId;
				console.log("NodeId: ", nodeId);

				initializeTfsGeneralTree(function(){						// Dis die completeMethod in initializeTfsGeneralTree... 
																	// Sodat tree eers heeltemal laai, dan select ons die korrekte rekord
	  				var node = $('#tfsGeneralTree').tree('getNodeById', nodeId);
	  				$('#tfsGeneralTree').tree('selectNode', node);
	  				$('#tfsGeneralTree').tree('scrollToNode', node);
				});
				
				// Set the Save Button to disabled
				setElementEnabled("savefolderStructureButton", false);
				somethingChangedInDialog = false;
			});
}  //savefolderStructureDialog -- End


function newfolderStructure() {
	
//	tfsTypicalFolderStructureId</span><input readonly id="tfsTypicalFolderStructureId" style="background-color: #ffff99 !important; color: Black; width: 60px; height: 20px;">
//	<span  style="font-size: 80%">&nbsp;&nbsp;&nbsp;tfsTypicalFolderStructureIdParent</span><input readonly id="tfsTypicalFolderStructureIdParent" style="background-color: #ffff99 !important; color: Black; width: 60px; height: 20px;">
//	<span  style="font-size: 80%">&nbsp;&nbsp;&nbsp;tfsTypicalFolderStructureIdIndustry
	
	$("#tfsParentName").val($("#tfsName").val());
	$("#tfsParentId").val($("#tfsTypicalFolderStructureId").val());
	
	$("#tfsTypicalFolderStructureId").val("");

	$("#tfsServiceDisciplineIdIndustry").val("");
	$("#tfsName").val("");
	$("#tfsDescription").val("");
	$("#tfsOrderNumber").val("");
    $("#perSdFlag").prop("checked", false);
	// $("#tfstfsTypicalFolderStructureIdGroup").val($("#tfstfsTypicalFolderStructureIdGroup").val());
	
	// Set the Save Button to disabled
	// Disabel the Id if update, enable if insert
	$("#tfsTypicalFolderStructureId").prop("readonly", false);
	setElementEnabled("savefolderStructureButton", false); //Disable Save button when initializing
	
	// $("#tfsGeneralTreeTabDlgErrorDiv").display(false);
	setDivVisibility("tfsGeneralTreeTabDlgErrorDiv", "none");
 	
} // newfolderStructure -- END






/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//*************************************************************************************************************************************************//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
