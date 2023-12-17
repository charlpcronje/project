// ****************************************************************************** //
// ************************** tfs Tree Start ****************************** //

var tfsRolesAssignedTable = null;
var tfsRolesAvailableTable = null;
var tfsRolesAvailableIndustryTable = null;


function initializeTfsTree(completeMethod) {
	var url = "/rest/ignite/v1/typical-folder-structure/find-top-level-in-tree-view";  // Find the top level 
	console.log(url);
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			renderTfsTree(data);
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



function renderTfsTree(data) {
	data.forEach(e => addSupportingNodesTfs(e));
	
	$('#tfsTree').tree('destroy');
	var style=" style='100%'";
	console.dir(data);
	var tree = $("#tfsTree").tree({
        data: data,
//        showEmptyFolder: true,
//        autoOpen: true,
//        closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
//        openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i>'),
        onCreateLi: function(node, $li, is_selected) {
        	if (node.perSdFlag == "Y") {
        		$li.find('.jqtree-title').before('<i class="jqtree-icon fab fa-bitbucket">&nbsp;</i>'); // &nbsp; : no break space - add a space that HTML will not ignore!
//        		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" >&nbsp;</span>'); // no break space - add a space that HTML will not ignore!
//        		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" style="height:4px; width:4px;"> <img src="blank.gif" height="1px" width="1px"></span>'); 
			}
        }
    });

	$("#tfsTreePanel").css({
		overflow: "scroll"
	});
	
	$("#tfsTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#tfsTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);

		    	doubleClickNodeTfs(event.node);
		    }
    );
	
	$('#tfsTree').on(
		    'tree.select',
		    function(event) {
		        if (event.node) {
		            // node was selected
		            var node = event.node;
			    	showEmptyNodeTfs(event.node);
		        }
		        else {
			    	selectFirstNodeTfs();

		        	// event.node is null
		            // a node was deselected
		            // e.previous_node contains the deselected node
		        }
		    }
		);
	
//	$("#tfsTree").on(
//		    'tree.click',
//		    function(event) {
//		    	event.click_event.preventDefault();
//		    	showNodeTfs(event.node);
//		    }
//    );
	
	openActiveNodesTfs();
//	selectFirstNodeTfs();
	return tree;
} // renderTfsTree -- END



function openActiveNodesTfs() {
	var nodes = $("#tfsTree").tree("getTree");
	for (var foo = nodes.children.length; foo > 0; foo--) {
		node = nodes.children[foo - 1];
		
		//			if (node.hasOwnProperty("anyChildren")) {
		//			//			// By default don't open these projects
		//			//			var active = (node.projectStage != "CANCELLED") && 
		//			//			             (node.projectStage != "COMPLETED");
		//			//			             
		//			//			if ((node.nodeType == NODETYPE_PROJECT) && (active)) {
		//			//				$("#tfsTree").tree("openNode", node);
		//			//			}
		//			}
				}
} // openActiveNodesTfs -- END



function doubleClickNodeTfs(node) {
	var tree = $("#tfsTree").tree();

	clearTextSelection();
    tree.tree("selectNode", node);
    
    if (node.is_open) {

    	// only show the node if its opened
    	showNodeTfs(node);
    }
}



function selectFirstNodeTfs() {
	var data = $("#tfsTree").tree("getTree");

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
	
	// $("#tfsTreeTabDlgErrorDiv").display(false);
	setDivVisibility("tfsTreeTabDlgErrorDiv", "none");
	
	setDivVisibility("tfsServiceDisciplineTreeEmptyPanel", "block");
	setDivVisibility("tfsServiceDisciplineTreePanel", "none");
	tfsServiceDisciplineTreeEmptyPanel.innerText = "None Selected.";
	
	setDivVisibility("tfsRolesAvailableEmptyPanel", "block");
	setDivVisibility("tfsRolesAvailablePanel", "none");
	setDivVisibility("tfsRolesAvailableIndustryPanel", "none");
	
	setDivVisibility("tfsTreeHeaderPanel", "block");
	setDivVisibility("tfsRolesAvailableIndustryPanel", "none");
	setDivVisibility("tfsTreeAvailableHeaderParentPanel", "none");
	
//	headerRolesAssigned.innerText = "";
//	headerRolesAvailable.innerText = "";
	$("#addFolderStructureButton").attr("title", "Add New Industry");
	
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

   	if (node.perSdFlag == "Y") {
   		// Can not have roles - folder only - wys die Group se available roles
   	
   		setDivVisibility("tfsServiceDisciplineTreeEmptyPanel", "none");
   		setDivVisibility("tfsServiceDisciplineTreePanel", "block");
   		
//   		setDivVisibility("tfsRolesAvailableEmptyPanel", "block");
//   		setDivVisibility("tfsRolesAvailablePanel", "none");
//   		setDivVisibility("tfsRolesAvailableIndustryPanel", "none");
//   		setDivVisibility("tfsTreeAssignedHeaderPanel", "none");
//		setDivVisibility("tfsTreeEmptyHeaderPanel", "none");
//		setDivVisibility("tfsTreeAvailableHeaderPanel", "none");
//		setDivVisibility("tfsRolesAvailableIndustryPanel", "none");
//		setDivVisibility("tfsTreeAvailableHeaderParentPanel", "none");
		
//		headerRolesAvailable.innerText = "";
//		headerRolesAssigned.innerText = ""; 
//		tfsRolesAssignedEmptyPanel.innerText = "No roles allowed. This is a Folder Only entry.";
		// showtfsRolesAvailablePerIndustry(node.tfsTypicalFolderStructureIdIndustry);		
		
   	} else {
   		
   		setDivVisibility("tfsServiceDisciplineTreeEmptyPanel", "block");
   		setDivVisibility("tfsServiceDisciplineTreePanel", "none");
   		tfsServiceDisciplineTreeEmptyPanel.innerText = "Not per Sevice Discipline.";
   		tfsServiceDisciplineTreeEmptyPanel.style = "text-align: center; color: #777777";
   		
   		
 
   	}
   	



	
	// Set the Save Button to disabled
	// DisabLe the Id if update, enable if insert
//	$("#tfsTypicalFolderStructureId").prop("readonly", (node.tfsTypicalFolderStructureId != null) &&(node.tfsTypicalFolderStructureId != ""));
	setElementEnabled("savefolderStructureButton", false); //Disable Save button when initializing
	
	// $("#tfsTreeTabDlgErrorDiv").display(false);
	setDivVisibility("tfsTreeTabDlgErrorDiv", "none");
   	
} // showNodeTfs -- End 
//****************************************************************************** //
//************************** tfs Tree End ****************************** //




// ************************** SD Tree Start ****************************** //
// ****************************************************************************** //
function initializeSdTree(completeMethod) {
	var url = "/rest/ignite/v1/service-discipline-tree";  // Find the top level 
	console.log(url);
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			renderSdTree(data);
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



function addSupportingNodes(e) {

	// Ingrid commented this out... Don't think we need this...
		if (e.hasOwnProperty("anyChildren")) {
			
			if (e.anyChildren == "Y") {
				e.name = e.name; 
			}
		}
		
	if (e.hasOwnProperty("children")) {
		// Add Sub structures to sub-projects
		var c = e.children;
		c.forEach(c => addSupportingNodes(c));
	} else {
		// No children... (ie. no sub-projects...)
		e.children = [];
	}
	e.id = e.serviceDisciplineId;
	e.name = e.name
}



function renderSdTree(data) {
	data.forEach(e => addSupportingNodes(e));
	
	$('#sdTree').tree('destroy');
	var style=" style='100%'";
	var tree = $("#sdTree").tree({
        data: data,
//        showEmptyFolder: true,
//        autoOpen: true,
//        closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
//        openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i>'),
        onCreateLi: function(node, $li, is_selected) {
        	if (node.folderOnly == "Y") {
        		$li.find('.jqtree-title').before('<i class="jqtree-icon far fa-folder">&nbsp;</i>'); // &nbsp; : no break space - add a space that HTML will not ignore!
//        		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" >&nbsp;</span>'); // no break space - add a space that HTML will not ignore!
//        		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" style="height:4px; width:4px;"> <img src="blank.gif" height="1px" width="1px"></span>'); 
			}
        }
    });

	$("#sdTreePanel").css({
		overflow: "scroll"
	});
	
	$("#sdTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#sdTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);

		    	doubleClickNode(event.node);
		    }
    );
	
	$('#sdTree').on(
		    'tree.select',
		    function(event) {
		        if (event.node) {
		            // node was selected
		            var node = event.node;
			    	showNode(event.node);
		        }
		        else {
			    	showEmptyNode();

		        	// event.node is null
		            // a node was deselected
		            // e.previous_node contains the deselected node
		        }
		    }
		);
	
//	$("#sdTree").on(
//		    'tree.click',
//		    function(event) {
//		    	event.click_event.preventDefault();
//		    	showNode(event.node);
//		    }
//    );
	
	openActiveNodes();
//	selectFirstNode();
	return tree;
} // renderSdTree -- END



function openActiveNodes() {
	var nodes = $("#sdTree").tree("getTree");
	for (var foo = nodes.children.length; foo > 0; foo--) {
		node = nodes.children[foo - 1];
		
		//			if (node.hasOwnProperty("anyChildren")) {
		//			//			// By default don't open these projects
		//			//			var active = (node.projectStage != "CANCELLED") && 
		//			//			             (node.projectStage != "COMPLETED");
		//			//			             
		//			//			if ((node.nodeType == NODETYPE_PROJECT) && (active)) {
		//			//				$("#sdTree").tree("openNode", node);
		//			//			}
		//			}
				}
} // openActiveNodes -- END



function doubleClickNode(node) {
	var tree = $("#sdTree").tree();

	clearTextSelection();
    tree.tree("selectNode", node);
    
    if (node.is_open) {
    	// only show the node if its opened
    	showNode(node);
    }
}



function selectFirstNode() {
	var data = $("#sdTree").tree("getTree");

	if (data.hasOwnProperty("children")) {
		if (data.children.length > 0) {
			var node = data.children[0];
			doubleClickNode(node);
		}
	}
}



function showEmptyNode() {
	$("#sdParentCode").val("");
	$("#sdParentName").val("");
	$("#serviceDisciplineId").val("");
	$("#serviceDisciplineCode").val("");
	$("#serviceDisciplineName").val("");
	$("#serviceDisciplineDescription").val("");
	$("#orderNumber").val("");
	$("#svdServiceDisciplineIdIndustry").val("");
	$("#svdServiceDisciplineId").val("");
	$("#svdServiceDisciplineIdParent").val("");
	$("#serviceDisciplineAllowRoles").prop("checked", false)
	
	// Set the Save Button to disabled
	// Disabel the code if update, enable if insert
	// $("#serviceDisciplineCode").prop("readonly", false);
	setElementEnabled("saveServiceDisciplineButton", false); //Disable Save button when initializing
	
	// $("#sdtreeTabDlgErrorDiv").display(false);
	setDivVisibility("sdtreeTabDlgErrorDiv", "none");
	
	setDivVisibility("sdRolesAssignedEmptyPanel", "block");
	setDivVisibility("sdRolesAssignedPanel", "none");
	setDivVisibility("sdRolesAvailableEmptyPanel", "block");
	setDivVisibility("sdRolesAvailablePanel", "none");
	setDivVisibility("sdRolesAvailableIndustryPanel", "none");
	
	setDivVisibility("sdTreeHeaderPanel", "block");
	setDivVisibility("sdRolesAvailableIndustryPanel", "none");
	setDivVisibility("sdTreeAvailableHeaderParentPanel", "none");
	headerRolesAssigned.innerText = "";
	headerRolesAvailable.innerText = "";
	$("#addServiceDisciplineButton").attr("title", "Add New Industry");

} // showEmptyNode -- End 



function showNode(node) {
	
	$("#addServiceDisciplineButton").attr("title", "Add New Service Discipline below this level");
	$("#selectedSdId").val(node.id);
	$("#svdServiceDisciplineIdIndustry").val(node.serviceDisciplineIdIndustry);
	$("#svdServiceDisciplineId").val(node.serviceDisciplineId);
	$("#svdServiceDisciplineIdParent").val(node.serviceDisciplineIdParent);
	$("#sdParentCode").val(node.parentSdCode);
	$("#sdParentName").val(node.parentSdName);
	$("#serviceDisciplineCode").val(node.serviceDisciplineCode);
	$("#serviceDisciplineName").val(node.serviceDisciplineName);
	$("#serviceDisciplineAllowRoles").prop("checked", node.allowRoles == "Y")
	
   	if (node.folderOnly == "Y") {
   		// Can not have roles - folder only - wys die Group se available roles
   	
   		setDivVisibility("sdRolesAssignedEmptyPanel", "block");
   		setDivVisibility("sdRolesAssignedPanel", "none");
   		setDivVisibility("sdRolesAvailableEmptyPanel", "block");
   		setDivVisibility("sdRolesAvailablePanel", "none");
   		setDivVisibility("sdRolesAvailableIndustryPanel", "none");
   		
   		setDivVisibility("sdTreeAssignedHeaderPanel", "none");
		setDivVisibility("sdTreeEmptyHeaderPanel", "none");
		setDivVisibility("sdTreeAvailableHeaderPanel", "none");
		setDivVisibility("sdRolesAvailableIndustryPanel", "none");
		setDivVisibility("sdTreeAvailableHeaderParentPanel", "none");
		
		headerRolesAvailable.innerText = "";
		headerRolesAssigned.innerText = ""; 
		sdRolesAssignedEmptyPanel.innerText = "No roles allowed. This is a Folder Only entry.";
		// showSdRolesAvailablePerIndustry(node.serviceDisciplineIdIndustry);		
		
   	} else {
   		if (node.parentSdName == null) {

   	   		// Can not have roles - Hierdie is die Group - wys die Group se available roles
   	   		setDivVisibility("sdRolesAssignedEmptyPanel", "block");
   	   		setDivVisibility("sdRolesAssignedPanel", "none");
   	   		setDivVisibility("sdRolesAvailableEmptyPanel", "none");
   	   		setDivVisibility("sdRolesAvailablePanel", "none");
   	   		setDivVisibility("sdRolesAvailableIndustryPanel", "none");

   	   		setDivVisibility("sdTreeAssignedHeaderPanel", "none");
			setDivVisibility("sdTreeEmptyHeaderPanel", "block");
			setDivVisibility("sdTreeAvailableHeaderPanel", "none");
			setDivVisibility("sdRolesAvailableIndustryPanel", "block");
			setDivVisibility("sdTreeAvailableHeaderParentPanel", "block");
			
			headerRolesAvailable.innerText = "Roles linked to " + node.serviceDisciplineName + " Industry";
			headerRolesAssigned.innerText = ""; //No Roles assigned to Industry, it only serves as a grouping for children
			sdRolesAssignedEmptyPanel.innerText = "";
			
			showSdRolesAvailablePerIndustry(node.serviceDisciplineIdIndustry);
   			
   		} else if (node.anyChildren == "Y") {
   	   		// Can not have roles - want daar is children - wys die Group se available roles
   	   		
   	   		setDivVisibility("sdRolesAssignedEmptyPanel", "block");
   	   		setDivVisibility("sdRolesAssignedPanel", "none");
   	   		setDivVisibility("sdRolesAvailableEmptyPanel", "block");
   	   		setDivVisibility("sdRolesAvailablePanel", "none");
   	   		setDivVisibility("sdRolesAvailableIndustryPanel", "none");

   	   		setDivVisibility("sdTreeAssignedHeaderPanel", "none");
			setDivVisibility("sdTreeEmptyHeaderPanel", "none");
			setDivVisibility("sdTreeAvailableHeaderPanel", "none");
			setDivVisibility("sdRolesAvailableIndustryPanel", "none");
			setDivVisibility("sdTreeAvailableHeaderParentPanel", "none");
			
			headerRolesAvailable.innerText = "";
			headerRolesAssigned.innerText = ""; // If Node hase children, it may not have roles assigned
			sdRolesAssignedEmptyPanel.innerText = "No roles allowed. This entry has sub Service Disciplines.";
			headerRolesAvailable.innerText = "";
			
			// showSdRolesAvailablePerIndustry(node.serviceDisciplineIdIndustry);
			
		} else {

	   		// Can  have roles 
			// Show roles linked to the sd
	   		setDivVisibility("sdRolesAssignedEmptyPanel", "none");
	   		setDivVisibility("sdRolesAssignedPanel", "block");
	   		setDivVisibility("sdRolesAvailableEmptyPanel", "none");
	   		setDivVisibility("sdRolesAvailablePanel", "block");
	   		setDivVisibility("sdRolesAvailableIndustryPanel", "none");
			
			setDivVisibility("sdRoleEmptyPanel", "none");
			setDivVisibility("sdFolderOnlyPanel", "none");
			setDivVisibility("sdHasChildrenPanel", "none");
			setDivVisibility("sdRolePanel", "block");
			setDivVisibility("sdRolesAvailableEmptyPanel", "none");
			setDivVisibility("sdRolesAvailablePanel", "block");
			setDivVisibility("sdTreeAssignedHeaderPanel", "block");
			setDivVisibility("sdTreeEmptyHeaderPanel", "none");
			setDivVisibility("sdTreeAvailableHeaderPanel", "block");
			setDivVisibility("sdRolesAvailableIndustryPanel", "none");
			setDivVisibility("sdTreeAvailableHeaderParentPanel", "none");

			// Show Roles assigned
			headerRolesAvailable.innerText = "Roles Available";
			headerRolesAssigned.innerText = "Roles Assigned to " + node.name;
			sdRolesAssignedEmptyPanel.innerText = "";
			
			showSdRolesAssignedTable(node.serviceDisciplineId);
			showSdRolesAvailableForSd(node.serviceDisciplineId, node.serviceDisciplineIdIndustry);
		}
   	}
   	
	$("#orderNumber").val(node.orderNumber);
	$("#sdParent").val(node.serviceDisciplineName);			
//	$("#serviceDisciplineCodeParent").val(node.serviceDisciplineCodeParent);
	$("#nodeId").val(node.serviceDisciplineCodeParent);
	
	$("#serviceDisciplineCode").val(node.serviceDisciplineCode);
	$("#serviceDisciplineName").val(node.serviceDisciplineName);
	$("#serviceDisciplineDescription").val(node.description);
	
	// Set the Save Button to disabled
	// DisabLe the code if update, enable if insert
//	$("#serviceDisciplineCode").prop("readonly", (node.serviceDisciplineCode != null) &&(node.serviceDisciplineCode != ""));
	setElementEnabled("saveServiceDisciplineButton", false); //Disable Save button when initializing
	
	// $("#sdtreeTabDlgErrorDiv").display(false);
	setDivVisibility("sdtreeTabDlgErrorDiv", "none");
   	
} // showNode -- End 

//************************** SD Tree End ****************************** //
//****************************************************************************** //












// ****************************************************************************** //
// ************************** Service Discipline Info Start ****************************** //

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
	var currentNode = $('#tfsTree').tree('getSelectedNode');
	var parent = currentNode.parent;
	
	var id = parent.id;
	
	saveEntry(postUrl, data, null, "The Service Discipline has been deleted.", null,
			function(){
				initializeTfsTree(function() {
	
					if (id == null) {
				    	showEmptyNodeTfs();
				    	return;
					}
					var node = $('#tfsTree').tree('getNodeById', id);
					//Load the node,
	  				$('#tfsTree').tree('selectNode', node);
  					$('#tfsTree').tree('scrollToNode', node);
				});
				$("#selectedtfsId").val(node.id);
				$("#tfsTypicalFolderStructureIdIndustry").val(node.folderStructureIdIndustry);

				$("#tfsParentId").val(node.parenttfsId);
				$("#tfsParentName").val(node.parenttfsName);
				$("#tfsTypicalFolderStructureId").val(node.folderStructureId);
				$("#tfsName").val(node.tfsName);

			});
} //  deletefolderStructure  -- END



//function perSdClicked() {
//	
//	var checkBox = document.getElementById("perSdFlag");
//	
////	if (checkBox.checked == true){
////		jQuery('#folderStructureAllowRoles').attr('checked',false);
////	  } else {
////	  };
//	  folderStructureDialogChanged();
//	  
//} //perSdClicked



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

	if (showFormErrors("tfsTreeTabDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.typicalFolderStructureId != null) && (postData.typicalFolderStructureId != "")) {
		//This is an update
        postUrl = "/rest/ignite/v1/typical-folder-structure"; 
	} else {
		postData.typicalFolderStructureId = null;  //empty string werk nie
	} 
	
	console.log(postUrl);

  	saveEntry(postUrl, postData, "folderStructureDialog", "The Folder has been saved.", null,
  			function(request, response) {
  				var nodeId =  response.typicalFolderStructureId;
  				console.log("NodeId: ", nodeId);

  				initializeTfsTree(function(){						// Dis die completeMethod in initializeTfsTree... 
  																	// Sodat tree eers heeltemal laai, dan select ons die korrekte rekord
	  				var node = $('#tfsTree').tree('getNodeById', nodeId);
	  				$('#tfsTree').tree('selectNode', node);
	  				$('#tfsTree').tree('scrollToNode', node);
  				});
  				
  				// Set the Save Button to disabled
  				setElementEnabled("savefolderStructureButton", false);
				somethingChangedInDialog = false;
  			});
}  //savefolderStructureDialog -- End




//deletetfsRoleAssigned -- Start
function deletetfsRoleAssigned(rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector == null) { 
		data = tfsRolesAssignedTable.rows('.selected').data()[0];
	} else {
		data = tfsRolesAssignedTable.row(rowSelector).data();
	}	

	var roleOnAProjectId = data.roleOnAProjectId;
	var folderStructureId = $("#selectedtfsId").val();

	tfsRolesAssignedTable.rows().deselect();	
	
	var postUrl = "/rest/ignite/v1/tfs-role/delete2/"  // + folderStructureId + "/" + roleOnAProjectId;
	var postData = {
			folderStructureId : folderStructureId,
			roleOnAProjectId :    roleOnAProjectId
	};

	saveEntry(postUrl, postData, "folderStructureDialog", "The Service Discipline has been removed.", tfsRolesAvailableTable,
			function() {

		showtfsRolesAssignedTable(folderStructureId);
				
			});
}  //deletetfsRoleAssigned -- End



//savetfsRole -- Start
function savetfsRole(tfsRolesAvailableTable, rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector == null) { 
		data = tfsRolesAvailableTable.rows('.selected').data()[0];
	} else {
		data = tfsRolesAvailableTable.row(rowSelector).data();
	}		


	var roleOnAProjectId = data.roleOnAProjectId;
	var folderStructureId = $("#selectedtfsId").val();	
	
	tfsRolesAvailableTable.rows().deselect();	
	
	var postUrl = "/rest/ignite/v1/tfs-role/new";
	var postData = {
			folderStructureId : folderStructureId,
			roleOnAProjectId :    roleOnAProjectId
	};
		
	saveEntry(postUrl, postData, "folderStructureDialog", "The Service Discipline has been linked.", tfsRolesAvailableTable,
			function() {

		showtfsRolesAssignedTable(folderStructureId);
				
			});
}
//savetfsRole -- End

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
	
	// $("#tfsTreeTabDlgErrorDiv").display(false);
	setDivVisibility("tfsTreeTabDlgErrorDiv", "none");
   	
} // newfolderStructure -- END



// ************************** Service tfsiscipline Info End ****************************** //
// ****************************************************************************** //


// ****************************************************************************** //
// ******************************* Roles Start ********************************** //

// Roles Tables Start 

function showtfsRolesAssignedTable(tfsId) {
	
	var rowSelector = null;
	var queryUrl="/rest/ignite/v1/role-on-a-project/for-tfs/" + tfsId;
	
	var columnsArray = [
		{ data: "roleOnAProjectId"}, 			// 0 
		{ data: "tfsTypicalFolderStructureIdIndustry"}, // 1
		{ data: "name"} 						// 2
	];

	var columnDefsArray = [
    	{
    		visible: false,
    		targets: [0,1]
    	}
	];
	
	var buttonsArray = [
		{
			attr: {
				id: "DeletetfsRoleBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-arrow-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				
				deletetfsRoleAssigned(rowSelector); 
			}
		}
	];

	tfsRolesAssignedTable = initializeGenericTable("tfsRolesAssignedTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											deletetfsRoleAssigned(rowSelector) 
										},
										null,
										30,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	tfsRolesAssignedTable.off('deselect')
	tfsRolesAssignedTable.on('deselect', function (e, dt, type, indexes) {
		updatetfsRolesAssignedToolbarButtons();
	} );

	tfsRolesAssignedTable.off('select')
	tfsRolesAssignedTable.on('select', function (e, dt, type, indexes) {
		updatetfsRolesAssignedToolbarButtons();
	} );
	
	updatetfsRolesAssignedToolbarButtons();
} //showtfsRolesAssignedTable -- END


function showtfsRolesAvailableFortfs(tfsId, tfsIdIndustry) {
	
	var rowSelector = null;
	var queryUrl="/rest/ignite/v1/role-on-a-project/for-tfs-available/" + tfsId + "/" + tfsIdIndustry;  

	var columnsArray = [
	                    
						{ data: "roleOnAProjectId"}, 			// 
						{ data: "tfsTypicalFolderStructureIdIndustry"}, 	// 
						{ data: "name"}, 						// 
	            	];

	var columnDefsArray = [
    	{
    		visible: false,
    		targets: [0,1]
    	}		
	];
	
	var buttonsArray = [
		{
			attr: {
				id: "addtfsRoleBtn"
			},
			titleAttr: "Add",
			text: "<i class='fas fa-arrow-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				
				savetfsRole(tfsRolesAvailableTable, rowSelector)
				
			}
		}
	];
	
	tfsRolesAvailableTable = initializeGenericTable("tfsRolesAvailableTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											savetfsRole(tfsRolesAvailableTable, rowSelector);
										},
										null,
										26,
										[[2,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	tfsRolesAvailableTable.off('deselect')
	tfsRolesAvailableTable.on('deselect', function (e, dt, type, indexes) {
		updatetfsRolesAvailableToolbarButtons();
	} );

	tfsRolesAvailableTable.off('select')
	tfsRolesAvailableTable.on('select', function (e, dt, type, indexes) {
		updatetfsRolesAvailableToolbarButtons();
	} );
	
	updatetfsRolesAvailableToolbarButtons();
} //showtfsRolesAvailableFortfs -- END



function showtfsRolesAvailablePerIndustry(tfsIdIndustry) {
	
	var queryUrl="/rest/ignite/v1/role-on-a-project/level0/" + tfsIdIndustry; 

	var columnsArray = [
	                    
						{ data: "roleOnAProjectId"}, 			// 
						{ data: "tfsTypicalFolderStructureIdIndustry"}, // 
						{ data: "name"} 						// 
	            	];

	            	var columnDefsArray = [
		            	{
		            		visible: false,
		            		targets: [0,1]
		            	}	  				
	            	];
	
	var buttonsArray = [

	];

	tfsRolesAvailableIndustryTable = initializeGenericTable("tfsRolesAvailableIndustryTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            null
										,null     //completeMethod
									    ,26      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[2,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);

	tfsRolesAvailableIndustryTable.off('deselect')
	tfsRolesAvailableIndustryTable.on('deselect', function (e, dt, type, indexes) {
//		updatetfsRolesAvailableToolbarButtons();   there are no buttons
	} );

	tfsRolesAvailableIndustryTable.off('select')
	tfsRolesAvailableIndustryTable.on('select', function (e, dt, type, indexes) {
//		updatetfsRolesAvailableToolbarButtons();  there are no buttons
	} );
	
//	updatetfsRolesAvailableToolbarButtons();  there are no buttons
} //showtfsRolesAvailablePerIndustry -- END





//updatetfsRolesAssignedToolbarButtons
function updatetfsRolesAssignedToolbarButtons() {
	var hasSelected = tfsRolesAssignedTable.rows('.selected').data().length > 0;
	
	setTableButtonState(tfsRolesAssignedTable, "DeletetfsRoleBtn", hasSelected);	
}//updatetfsRolesAssignedToolbarButtons -- End


//updatetfsRolesAvailableToolbarButtons
function updatetfsRolesAvailableToolbarButtons() {
	var hasSelected = tfsRolesAvailableTable.rows('.selected').data().length > 0;
	
	setTableButtonState(tfsRolesAvailableTable, "addtfsRoleBtn", hasSelected);	
}//updatetfsRolesAvailableToolbarButtons -- End



// Role Tables -- End
// *********************************************************
// ***********************************************************************

//*************************** tfs Tree End ****************************** //



// ****************************************************************************** //
// ****************************************************************************** //

$(document).ready(function() {
	// Any initialization
	
	$("#tfsTreePanel").resizable({
		handles: "e",
		alsoResizesReverse: "tfsTreeDetail"
	})
	showIgDeveloperOption();	
	initializeTfsTree();	
	initializeSdTree();

} );
