
//************************** SD Tree Start ****************************** //
//****************************************************************************** //
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
//     showEmptyFolder: true,
//     autoOpen: true,
//     closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
//     openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i>'),
     onCreateLi: function(node, $li, is_selected) {
     	if (node.folderOnly == "Y") {
     		$li.find('.jqtree-title').before('<i class="jqtree-icon far fa-folder">&nbsp;</i>'); // &nbsp; : no break space - add a space that HTML will not ignore!
//     		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" >&nbsp;</span>'); // no break space - add a space that HTML will not ignore!
//     		$li.find('.jqtree-title').after('<span class="badge badge-pill badge-success" title="warning" style="height:4px; width:4px;"> <img src="blank.gif" height="1px" width="1px"></span>'); 
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
// );
	
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
			

			headerRolesAssigned.innerText = ""; // If Node hase children, it may not have roles assigned
			sdRolesAssignedEmptyPanel.innerText = "No roles allowed. This entry has sub Service Disciplines.";

			
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



