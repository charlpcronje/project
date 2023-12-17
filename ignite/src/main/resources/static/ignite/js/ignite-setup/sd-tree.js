// ****************************************************************************** //
// ************************** SD Tree Start ****************************** //

var sdRolesAssignedTable = null;
var sdRolesAvailableTable = null;
var sdRolesAvailableIndustryTable = null;


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

// ************************** SD Tree End ****************************** //
// ****************************************************************************** //




// ****************************************************************************** //
// ************************** Service Discipline Info Start ****************************** //

//serviceDisciplineDialogChanged -- Start
function serviceDisciplineDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveServiceDisciplineButton", true);
}
//serviceDisciplineDialogChanged -- End

function promptDeleteServiceDiscipline() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Service Discipline?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteServiceDiscipline();
			   }
	);
}

function deleteServiceDiscipline() {
	var postUrl = "/rest/ignite/v1/service-discipline/delete";
	var sdId = $("#selectedSdId").val();

	// Disable delete button when record has been deleted.
	var data = {
		serviceDisciplineId:sdId
	}
	
	//Get the parent of sdId
	var currentNode = $('#sdTree').tree('getSelectedNode');
	var parent = currentNode.parent;
	
//	if ((parent === undefined) || (parent === null)){
//		//You cannot delete the top-most node.
//		return;
//	}
	
	//var node = $('#sdTree').tree('getNodeById', parent.id);
	
	var id = parent.id;
	
	saveEntry(postUrl, data, null, "The Service Discipline has been deleted.", null,
			function(){
				initializeSdTree(function() {
	
					if (id == null) {
				    	showEmptyNode();
				    	return;
					}
					// Find the parent node, and select it
					var node = $('#sdTree').tree('getNodeById', id);
					//Load the node,
	  				$('#sdTree').tree('selectNode', node);
  					$('#sdTree').tree('scrollToNode', node);
				});
				$("#selectedSdId").val(node.id);
				$("#svdServiceDisciplineIdIndustry").val(node.serviceDisciplineIdIndustry);

				$("#sdParentCode").val(node.parentSdCode);
				$("#sdParentName").val(node.parentSdName);
				$("#serviceDisciplineCode").val(node.serviceDisciplineCode);
				$("#serviceDisciplineName").val(node.serviceDisciplineName);

			});
}



//saveServiceDiscipline -- Start
function saveServiceDiscipline() {
	var postUrl = "/rest/ignite/v1/service-discipline/new"; // New record
	
	var postData = {};
	postData = {

			serviceDisciplineId : $("#svdServiceDisciplineId").val(),      //0 MySql-TableName: ServiceDiscipline
			serviceDisciplineIdParent : $("#svdServiceDisciplineIdParent").val(),        //1
			serviceDisciplineIdIndustry : $("#svdServiceDisciplineIdIndustry").val(),    //2 hier
			
			serviceDisciplineCode : $("#serviceDisciplineCode").val(),
			orderNumber : $("#orderNumber").val(), 
			name : $("#serviceDisciplineName").val(), 
			description : $("#serviceDisciplineDescription").val(), 
			
	}
	

	var errors = "";
	
	if ((postData.serviceDisciplineCode == null) || (postData.serviceDisciplineCode == "")) {
		errors += "A Code is required<br>";
	}

	if ((postData.name == null) || (postData.name == "")) {
		errors += "A Service Discipline Name is required<br>";
	}

	if ((postData.orderNumber == null) || (postData.orderNumber == "")) {
		errors += "An order number is required<br>";
	} else {
		if (!isNumber(postData.orderNumber)) {
			errors += "A numeric order number is required<br>";
		}
	}

	if (showFormErrors("sdtreeTabDlgErrorDiv", errors)) {
		return;
	}
	
	//Is the code readonly?  If it is, then the record already exists.
	
	if ((postData.serviceDisciplineId != null) && (postData.serviceDisciplineId != "")) {
          postUrl = "/rest/ignite/v1/service-discipline";
          
	} 
	  
	console.log(postUrl);

  	saveEntry(postUrl, postData, null, "The Service Discipline has been saved.", null,
  			function(request, response) {
  		
  				var nodeId =  response;
				
  				initializeSdTree(function(){						// Dis die completeMethod in initializeSdTree... 
  																	// Sodat tree eers heeltemal laai, dan select ons die korrekte rekord
	  				var node = $('#sdTree').tree('getNodeById', nodeId);
	  				$('#sdTree').tree('selectNode', node);
	  				$('#sdTree').tree('scrollToNode', node);
  				});
  				
  				// Set the Save Button to disabled
  				setElementEnabled("saveServiceDisciplineButton", false);
				somethingChangedInDialog = false;
  			});
}
//saveServiceDisciplineDialog -- End


//deleteSdRoleAssigned -- Start
function deleteSdRoleAssigned(rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector == null) { 
		data = sdRolesAssignedTable.rows('.selected').data()[0];
	} else {
		data = sdRolesAssignedTable.row(rowSelector).data();
	}	

	var roleOnAProjectId = data.roleOnAProjectId;
	var serviceDisciplineId = $("#selectedSdId").val();

	sdRolesAssignedTable.rows().deselect();	
	
	var postUrl = "/rest/ignite/v1/sd-role/delete2/"  // + serviceDisciplineCode + "/" + roleOnAProjectId;
	var postData = {
			serviceDisciplineId : serviceDisciplineId,
			roleOnAProjectId :    roleOnAProjectId
	};

	saveEntry(postUrl, postData, "serviceDisciplineDialog", "The Service Discipline has been removed.", sdRolesAvailableTable,
			function() {

		showSdRolesAssignedTable(serviceDisciplineId);
				
			});
}
//deleteSdRoleAssigned -- End



//saveSdRole -- Start
function saveSdRole(sdRolesAvailableTable, rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector == null) { 
		data = sdRolesAvailableTable.rows('.selected').data()[0];
	} else {
		data = sdRolesAvailableTable.row(rowSelector).data();
	}		


	var roleOnAProjectId = data.roleOnAProjectId;
	var serviceDisciplineId = $("#selectedSdId").val();	
	
	sdRolesAvailableTable.rows().deselect();	
	
	var postUrl = "/rest/ignite/v1/sd-role/new";
	var postData = {
			serviceDisciplineId : serviceDisciplineId,
			roleOnAProjectId :    roleOnAProjectId
	};
		
	saveEntry(postUrl, postData, "serviceDisciplineDialog", "The Service Discipline has been linked.", sdRolesAvailableTable,
			function() {

		showSdRolesAssignedTable(serviceDisciplineId);
				
			});
}
//saveSdRole -- End

function newServiceDiscipline() {
	
//	svdServiceDisciplineId</span><input readonly id="svdServiceDisciplineId" style="background-color: #ffff99 !important; color: Black; width: 60px; height: 20px;">
//	<span  style="font-size: 80%">&nbsp;&nbsp;&nbsp;svdServiceDisciplineIdParent</span><input readonly id="svdServiceDisciplineIdParent" style="background-color: #ffff99 !important; color: Black; width: 60px; height: 20px;">
//	<span  style="font-size: 80%">&nbsp;&nbsp;&nbsp;svdServiceDisciplineIdIndustry
	$("#svdServiceDisciplineIdParent").val($("#selectedSdId").val());
	$("#svdServiceDisciplineId").val("");
	$("#sdParentCode").val($("#serviceDisciplineCode").val());
	$("#sdParentName").val($("#serviceDisciplineName").val());
	$("#serviceDisciplineCode").val("");
	$("#serviceDisciplineName").val("");
	$("#serviceDisciplineDescription").val("");
	$("#orderNumber").val("");
	// $("#sdServiceDisciplineCodeGroup").val($("#sdServiceDisciplineCodeGroup").val());
	
	// Set the Save Button to disabled
	// Disabel the code if update, enable if insert
	$("#serviceDisciplineCode").prop("readonly", false);
	setElementEnabled("saveServiceDisciplineButton", false); //Disable Save button when initializing
	
	// $("#sdtreeTabDlgErrorDiv").display(false);
	setDivVisibility("sdtreeTabDlgErrorDiv", "none");
   	
} // newServiceDiscipline -- END



// ************************** Service sDiscipline Info End ****************************** //
// ****************************************************************************** //


// ****************************************************************************** //
// ******************************* Roles Start ********************************** //

// Roles Tables Start 

function showSdRolesAssignedTable(sdId) {
	
	var rowSelector = null;
	var queryUrl="/rest/ignite/v1/role-on-a-project/for-sd/" + sdId;
	
	var columnsArray = [
		{ data: "roleOnAProjectId"}, 			// 0 
		{ data: "serviceDisciplineIdIndustry"}, // 1
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
				id: "DeleteSdRoleBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-arrow-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				
				deleteSdRoleAssigned(rowSelector); 
			}
		}
	];

	sdRolesAssignedTable = initializeGenericTable("sdRolesAssignedTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											deleteSdRoleAssigned(rowSelector) 
										},
										null,
										30,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	sdRolesAssignedTable.off('deselect')
	sdRolesAssignedTable.on('deselect', function (e, dt, type, indexes) {
		updateSdRolesAssignedToolbarButtons();
	} );

	sdRolesAssignedTable.off('select')
	sdRolesAssignedTable.on('select', function (e, dt, type, indexes) {
		updateSdRolesAssignedToolbarButtons();
	} );
	
	updateSdRolesAssignedToolbarButtons();
} //showSdRolesAssignedTable -- END



function showSdRolesAvailableForSd(sdId, sdIdIndustry) {
	
	var rowSelector = null;
	var queryUrl="/rest/ignite/v1/role-on-a-project/for-sd-available/" + sdId + "/" + sdIdIndustry;  

	var columnsArray = [
	                    
						{ data: "roleOnAProjectId"}, 			// 
						{ data: "serviceDisciplineIdIndustry"}, 	// 
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
				id: "addSdRoleBtn"
			},
			titleAttr: "Add",
			text: "<i class='fas fa-arrow-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				
				saveSdRole(sdRolesAvailableTable, rowSelector)
				
			}
		}
	];
	
	sdRolesAvailableTable = initializeGenericTable("sdRolesAvailableTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											saveSdRole(sdRolesAvailableTable, rowSelector);
										},
										null,
										26,
										[[2,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	sdRolesAvailableTable.off('deselect')
	sdRolesAvailableTable.on('deselect', function (e, dt, type, indexes) {
		updateSdRolesAvailableToolbarButtons();
	} );

	sdRolesAvailableTable.off('select')
	sdRolesAvailableTable.on('select', function (e, dt, type, indexes) {
		updateSdRolesAvailableToolbarButtons();
	} );
	
	updateSdRolesAvailableToolbarButtons();
} //showSdRolesAvailableForSd -- END



function showSdRolesAvailablePerIndustry(sdIdIndustry) {
	
	var queryUrl="/rest/ignite/v1/role-on-a-project/level0/" + sdIdIndustry; 

	var columnsArray = [
	                    
						{ data: "roleOnAProjectId"}, 			// 
						{ data: "serviceDisciplineIdIndustry"}, // 
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

	sdRolesAvailableIndustryTable = initializeGenericTable("sdRolesAvailableIndustryTable",
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

	sdRolesAvailableIndustryTable.off('deselect')
	sdRolesAvailableIndustryTable.on('deselect', function (e, dt, type, indexes) {
//		updateSdRolesAvailableToolbarButtons();   there are no buttons
	} );

	sdRolesAvailableIndustryTable.off('select')
	sdRolesAvailableIndustryTable.on('select', function (e, dt, type, indexes) {
//		updateSdRolesAvailableToolbarButtons();  there are no buttons
	} );
	
//	updateSdRolesAvailableToolbarButtons();  there are no buttons
} //showSdRolesAvailablePerIndustry -- END





//updateSdRolesAssignedToolbarButtons
function updateSdRolesAssignedToolbarButtons() {
	var hasSelected = sdRolesAssignedTable.rows('.selected').data().length > 0;
	
	setTableButtonState(sdRolesAssignedTable, "DeleteSdRoleBtn", hasSelected);	
}//updateSdRolesAssignedToolbarButtons -- End


//updateSdRolesAvailableToolbarButtons
function updateSdRolesAvailableToolbarButtons() {
	var hasSelected = sdRolesAvailableTable.rows('.selected').data().length > 0;
	
	setTableButtonState(sdRolesAvailableTable, "addSdRoleBtn", hasSelected);	
}//updateSdRolesAvailableToolbarButtons -- End



// Role Tables -- End
// *********************************************************
// ***********************************************************************

//*************************** Sd Tree End ****************************** //



// ****************************************************************************** //
// ****************************************************************************** //

$(document).ready(function() {
	// Any initialization
	
	$("#sdTreePanel").resizable({
		handles: "e",
		alsoResizesReverse: "sdTreeDetail"
	})
	showIgDeveloperOption();	
	initializeSdTree();
} );
