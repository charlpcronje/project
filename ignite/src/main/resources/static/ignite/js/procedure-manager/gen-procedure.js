var genProcedureTable = null;
var somethingChangedGeneralTab = null;
var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var nextGenProcedureNumber = null;

var newDateRangeSelected = false;
const now = new Date();

// stel hom gelyk aan 1:    $("#aGenProcedureNumber").val(1);
// wys hom vir ons:       console.log("aGenProcedureNumber: " + $("#aGenProcedureNumber").val());

//let element13 = document.getElementById("sdCodeBino"); 
//element13.setAttribute("Hidden", "hidden");



// *********************************************************
// initializeGenProcedureTable -- Start

function initializeGenProcedureTable() {
	


	var columnsArray = [

		{ data: "genProcedureId" },               // 0 
		{ data: "procedureStatusId" },       // 1 
		{ data: "serviceDisciplineCode" },     // 2

		{ data: "procedureNumber" },           //3
		{ data: "title" },                   //4
		{ data: "scope" },                   //5
		{ data: "dateCreated" },                   //6
     
	];
	
	var columnDefsArray = [
//		{
//			visible: false,
//			targets: [0]
//		},

		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [6]//, 15, 17]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editGenProcedure(null);	
				
			}
		},
		{
			attr: {
				id: "promptDeleteGenProcedureBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteGenProcedure();
			}
		},
		
	];
	
//	console.dir(data);
//	console.dir(columnsArray);
//	console.log ("Alle genProcedures:   " + "/rest/ignite/v1/gen-procedure");
	
	genProcedureTable = initializeGenericTable("genProcedureTable",
			                            "/rest/ignite/v1/gen-procedure",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editGenProcedure(rowSelector);
										},
			                            null,
			                            25,
			                            [[2,"asc"]] //Order by column 3 ascending, normally defaults to column 1 ascending
	);
	
	
	genProcedureTable.off('deselect');
	genProcedureTable.on('deselect', function (e, dt, type, indexes) {
		updateGenProcedureToolbarButtons();
	} );

	genProcedureTable.off('select');
	genProcedureTable.on('select', function (e, dt, type, indexes) {
		updateGenProcedureToolbarButtons();
	} );
	updateGenProcedureToolbarButtons();
	


	
}








function updateGenProcedureToolbarButtons() {
	var hasSelected = genProcedureTable.rows('.selected').data().length > 0;

	setTableButtonState(genProcedureTable, "promptDeleteGenProcedureBtn", hasSelected);	
}
	
function promptDeleteGenProcedure() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Procedure?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteGenProcedure(genProcedureTable);
			   }
	);
}

function deleteGenProcedure(tbl) {
	var postUrl = "/rest/ignite/v1/gen-procedure/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Procedure has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateGenProcedureToolbarButtons
//				selectNextGenProcedureNumber();
			}
	);
}

// initializeGenProcedureTable -- End
// *********************************************************



// *********************************************************
// editGenProcedure -- Start
// General Tab

function editGenProcedure(rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	setAllSomethingChangedFlagsFalse();
	var header = "Procedure Detail";
	var genProcedureId
	
	if (rowSelector != null) {
//		let element13 = document.getElementById("aGenProcedureTypeDiv"); element13.setAttribute("Hidden", "hidden");
		data = genProcedureTable.row(rowSelector).data();
		enabled = true;
		header = "Procedure Detail: " + data.procedureNumber + " " + data.title;	
		
		let element13 = document.getElementById("sdCodeBino"); 
		element13.setAttribute("Hidden", "hidden");



		jQuery('#aRepeatCode').attr('value','');		
	} else { //set default values for new record
//		let element13 = document.getElementById("aGenProcedureTypeDiv"); element13.removeAttribute("hidden");
		
		$("#pServiceDisciplineName").val('');
		
		let element13 = document.getElementById("sdCodeBino"); 
		element13.removeAttribute("hidden");
	}
//	console.log("edit");
//	console.dir(data);
	genProcedureTable.rows().deselect();

	$("#genProcedureDialogHeader").html(header);	
	$("#pGenProcedureId").val(data.genProcedureId);                           		
	genProcedureId = document.getElementById('pGenProcedureId').value;
	$("#pProcedureStatusId").val(data.procedureStatusId);         			
	$("#pServiceDisciplineCode").val(data.serviceDisciplineCode);                       			
	$("#pProcedureNumber").val(data.procedureNumber);                      			
	$("#pTitle").val(data.title);                          		    	
	$("#pScope").val(data.scope);                           			
	$("#pDateCreated").datepicker("setDate", data.dateCreated == null? timestampToString(new Date(), false) : new Date(data.dateCreated)); //14 			
  
	if (enabled) {
		populateSelect("pProcedureStatusId", 
		       "/rest/ignite/v1/procedure-status", 
		       "procedureStatusId", 
		       "procedureStatusId", 
		       data.procedureStatusId, 
		       false,
		       null)
	} else {
		populateSelect("pProcedureStatusId", 
			       "/rest/ignite/v1/procedure-status", 
			       "procedureStatusId", 
			       "procedureStatusId", 
			       "Draft", 
			       false,
			       null)
	};
     

	
	showModalDialog("genProcedureDialog");

	

	

	setAdditionalTabStates(enabled);
	setElementEnabled("saveGeneralTabButton", false);
	somethingChangedGeneralTab = false;
	askToSaveTab = false;
	
	if (enabled) {
//		console.log("data.genProcedureId: " + data.genProcedureId);
		getServiceDisciplineName();
		
		initializeDefinitionTable(data.genProcedureId);
		initializeRoleTable(data.genProcedureId);
		initializeDocsTable(data.genProcedureId);
		
		initializePPTree(data.genProcedureId)
		
	} else {
		
//		$('#pDefsTable > tbody > tr').remove();
		$('#pDefsTable tr:not(:first)').remove();
		$('#pRoleTable tr:not(:first)').remove();
		$('#pDocsTable tr:not(:first)').remove();
		
//		console.log("enabled is false: ");
		setTableButtonState(pDefsTable, "newDefBtn", false);	
		setTableButtonState(pRoleTable, "newRoleBtn", false);	
		setTableButtonState(pDocsTable, "newDocsBtn", false);		
		
		
		
		document.getElementById("pProcedureStatusId").selectedIndex = "Draft";
//		console.log("hide");
	};
}
// editGenProcedure -- End




function selectServiceDisciplineCode() {
//	console.log("selectServiceDisciplineCode:    /rest/ignite/v1/service-discipline/all");
	var queryUrl="/rest/ignite/v1/service-discipline/all";
	
	var targetId = "pServiceDisciplineCode";
	var targetName = "pServiceDisciplineName";
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="serviceDisciplineCode";
			var refColumnName="serviceDisciplineCode";
			var columns = [
				{ data: "serviceDisciplineCode", name: "Code" },
				{ data: "serviceDisciplineName", name: "Parent Code" },
				{ data: "level", name: "Level" },
				{ data: "name", name: "Name" },
				{ data: "description", name: "Description" },
				{ data: "serviceDisciplineCodeParent", name: "Parent Code" },
				{ data: "anyChildren", name: "Children" },
				{ data: "rowOrderNo", name: "Row Order No" },
				{ data: "orderNumber", name: "orderNumber" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 1
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.serviceDisciplineCode;
				var dieNaam = row.name; 

				$("#" + targetId).val(id);
				$("#" + targetName).val(dieNaam);
				
//				console.log("getNextGenProcedureNumber vir: " + document.getElementById("pServiceDisciplineCode").value);
				
				getNextGenProcedureNumber()				
				
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


function getNextGenProcedureNumber() {
	
	var queryUrl;
	var serviceDisciplineCode = jQuery('#pServiceDisciplineCode').attr('value');
	
	queryUrl = "/rest/ignite/v1/gen-procedure/next-gen-procedure-number/" + serviceDisciplineCode;
	
//	console.log("/////  rest/ignite/v1/gen-procedure/next-gen-procedure-number/" + serviceDisciplineCode);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#pProcedureNumber").val(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
////	var nextNumber = document.getElementById('aGenProcedureNumber').value;	    //Dit werk nie
//	var nextNumber = jQuery('#pProcedureNumber').attr('value');                      //Dit werk nie
//																					 //Dit werk nie
//
//	if ((nextNumber == null) || (nextNumber == "")) {                               //Dit werk nie
////		console.log("nextNumberrr: " + nextNumber);                              //Dit werk nie

////		document.getElementById('aGenProcedureNumber').value = '1';
//		$("#pProcedureNumber").val(1);
//		
////		console.log("111111aGenProcedureNumber: " + $("#aGenProcedureNumber").val());
////	}
};




function getServiceDisciplineName() {
	
	var queryUrl;
	var serviceDisciplineCode = jQuery('#pServiceDisciplineCode').attr('value');
	
	queryUrl = "/rest/ignite/v1/service-discipline/by-code/" + serviceDisciplineCode;
	
//	console.log("//SDC naam///  rest/ignite/v1/service-discipline/by-code/" + serviceDisciplineCode);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#pServiceDisciplineName").val(data.name);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
};




function selectDefinition(genProcedureId) {
	
//	console.log("selectDefinition:    /rest/ignite/v1/definition/not-linked/" + genProcedureId);
	var queryUrl="/rest/ignite/v1/definition/not-linked/" + genProcedureId;
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="definitionId";
			var refColumnName="definitionId";
			var columns = [
				{ data: "definitionId", name: "definitionId" },
				{ data: "name", name: "Name" },
				{ data: "description", name: "Description" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];
		
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.definitionId;

//				$("#" + targetId).val(id);
//				$("#" + targetName).val(dieNaam);
				addDefinition(id);
				
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



function selectRole(genProcedureId) {
	
//	console.log("selectRole:    /rest/ignite/v1/role-on-a-project/not-linked/" + genProcedureId);
	var queryUrl="/rest/ignite/v1/role-on-a-project/not-linked/" + genProcedureId;
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="roleOnAProjectId";
			var refColumnName="roleOnAProjectId";
			var columns = [
				{ data: "roleOnAProjectId", name: "roleOnAProjectId" },
				{ data: "name", name: "Name" },
				{ data: "abbreviation", name: "Abbreviation" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];
		
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.roleOnAProjectId;

//				$("#" + targetId).val(id);
//				$("#" + targetName).val(dieNaam);
				addRole(id);
				
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

function selectDocs(genProcedureId) {
	
//	console.log("selectRole:    /rest/ignite/v1/related-docs/not-linked/" + genProcedureId);
	var queryUrl="/rest/ignite/v1/related-docs/not-linked/" + genProcedureId;
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="relatedDocsId";
			var refColumnName="relatedDocsId";
			var columns = [
				{ data: "relatedDocsId", name: "relatedDocsId" },
				{ data: "fileName", name: "File Name" },
				{ data: "description", name: "Description" },
				{ data: "folderPath", name: "Folder Path" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];
		
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.relatedDocsId;

//				$("#" + targetId).val(id);
//				$("#" + targetName).val(dieNaam);
				addRelatedDocs(id);
				
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






//addRole -- Begin
function addRole(roleCode) {
	
	var postUrl = "/rest/ignite/v1/person-responsible/new";
	
	var postData = {
			personResponsibleId:  null,		
			genProcedureId:	$("#pGenProcedureId").val(),  									
			roleOnAProjectId:	roleCode  				
	
	};
//	console.dir(postData);
	
	var errors = "";

	// validate
	
	if (postData.genProcedureId == "") {
		errors += "Please save the Procedure before adding Roles<br>";
	}
	if (postData.genProcedureId == null) {
		errors += "Please save the Procedure before adding Roles<br>";
	}
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, null, "The Role was added.", pRoleTable, function(request, response){
	
	});
	
}// addRole -- End



//addDefinition -- Begin
function addDefinition(defId) {
	
	var postUrl = "/rest/ignite/v1/procedure-definition/new";
	
	var postData = {
			procedureDefinitionId:  null,		
			genProcedureId:	$("#pGenProcedureId").val(),  									
			definitionId:	defId  				
	
	};
//	console.dir(postData);
	
	var errors = "";

	// validate
	
	if (postData.genProcedureId == "") {
		errors += "Please save the Procedure before adding Definitions<br>";
	}
	if (postData.genProcedureId == null) {
		errors += "Please save the Procedure before adding Definitions<br>";
	}
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, null, "The Definition was added.", pDefsTable, function(request, response){
	
	});
	
}// addDefinition -- End



//addRelatedDocs -- Begin
function addRelatedDocs(defId) {
	
	var postUrl = "/rest/ignite/v1/procedure-related-docs/new";
	
	var postData = {
			procedureRelatedDocsId:  null,		
			genProcedureId:	$("#pGenProcedureId").val(),  									
			relatedDocsId:	defId  				
	
	};
//	console.log("addRelatedDocs: ")
//	console.dir(postData);
	
	var errors = "";

	// validate
	
	if (postData.genProcedureId == "") {
		errors += "Please save the Procedure before adding Docs<br>";
	}
	if (postData.genProcedureId == null) {
		errors += "Please save the Procedure before adding Docs<br>";
	}
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, null, "The Document was added.", pDocsTable, function(request, response){
	
	});
	
}// addRelatedDocs -- End










//initializePPTree -- Start
function initializePPTree(projectId) {

	//clear the tree first... Werk nie!
	// emptyPPTree();
 var ppTree = $("#ppTree"); //.jstree(true);
 $("#ppTree .jstree-leaf, .jstree-anchor").each(function(){
     ppTree.delete_node($(this).attr('id'));

 });
	
	var url = "/rest/ignite/v1/project/pp-tree/" + projectId ;  // Find the top level 
//	var url = "/rest/ignite/v1/osd/tree"; //Find the top level OSDLevel1

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
	
	var tree = $("#ppTree").tree({
        data: data,
        showEmptyFolder: true,
        closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
        openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i'),
        onCreateLi: function(node, $li, is_selected) {
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
		initializePPSdRoleTable(node.projectParticipantId, node.name);
	} else {
		// Show all resources in the tree under this participant
		showPPResourcesTable(node, node.projectParticipantId, node.name);
	}
}















function paddy(num, padlen, padchar) {
    var pad_char = typeof padchar !== 'undefined' ? padchar : '0';
    var pad = new Array(1 + padlen).join(pad_char);
    return (pad + num).slice(-pad.length);
}



// saveGenProcedure -- Begin
function saveGenProcedureGeneral() {
	
	var disnNuweEen = false;
	
	if (($("#pProcedureNumber").val() == null) || ($("#pProcedureNumber").val() == "")) {
		$("#pProcedureNumber").val(1)
	}
	
	var postUrl = "/rest/ignite/v1/gen-procedure";
	
	var postData = {
			genProcedureId: $("#pGenProcedureId").val() == ""? null: $("#pGenProcedureId").val(),		
			procedureStatusId:	$("#pProcedureStatusId").val(),  									
			serviceDisciplineCode:	$("#pServiceDisciplineCode").val(),  				
			procedureNumber:	$("#pProcedureNumber").val(),  					
			title:	$("#pTitle").val(),                          					
			scope:	$("#pScope").val(),   					
			dateCreated: getMsFromDatePicker("pDateCreated")	
            				
	
	};
//	console.dir(postData);
	
	var errors = "";

	// validate
	if ((postData.dateCreated == "") || (postData.dateCreated == null)){
		// Add Today's date without time
		$("#pDateCreated").val(timestampToString(new Date(), false));		
		postData.dateCreated = getMsFromDatePicker("pDateCreated");
	}


	
	if (postData.serviceDisciplineCode == "") {
		errors += "Please select a Service Discipline<br>";
	}
	if (postData.procedureStatusId == "") {
		errors += "Please select a Procedure Status<br>";
	}
	if (postData.title == "") {
		errors += "Please enter a Title<br>";
	}
	
	
	
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	if (postData.genProcedureId == null) {
		postUrl = "/rest/ignite/v1/gen-procedure/new";
		disnNuweEen = true
	}
	
	
	saveEntry(postUrl, postData, null, "The Procedure has been saved.", genProcedureTable, function(request, response){
		var data = response;
		//console.dir(data);
		var genProcedureId = data.genProcedureId;
		$("#pGenProcedureId").val(genProcedureId);
		
		$("#genProcedureDialogHeader").html("Procedure Detail: " + data.ProcedureNumber + " " + data.title);
		
		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful
//		setElementEnabled("saveGeneralTabButton", false);
		somethingChangedGeneralTab = false;
		askToSaveTab = false;
//		selectNextGenProcedureNumber();
		
		let element13 = document.getElementById("sdCodeBino"); 
		element13.setAttribute("Hidden", "hidden");

		initializeDefinitionTable(genProcedureId);
		initializeRoleTable(genProcedureId);
		initializeDocsTable(genProcedureId)

	});
	
}// saveGenProcedure -- End













function setAdditionalTabStates(enabled) {
	
	setDivVisibility("pLineTabLink", enabled? "block":"none");
//	setDivVisibility("pdGenProcedureParticipantsTabLink", enabled? "block":"none");
//	setDivVisibility("pdGenProcedureStagesTabLink", enabled? "block":"none");
	
	setActiveTab("aGeneralTabAnchor");
}




//*********************************************************


//generalTabChanged -- Start
function generalTabChanged() {
	currentTab = "General";
	askToSaveTab = true;
	somethingChangedGeneralTab = true;
	setElementEnabled("saveGeneralTabButton", true);
}
//generalTabChanged -- End

//taskTabChanged -- Start
function taskTabChanged() {
	currentTab = "Tasks";
	askToSaveTab = true;
	somethingChangedTaskTab = true;
//	setElementEnabled("blouSaveTaskTabButton", true);
	
	document.getElementById("blouSaveTaskTabButton").disabled = false;
}
//taskTabChanged -- End

//taskRevisionTabChanged -- Start
function taskRevisionTabChanged() {
	currentTabRevision = "TaskRevision";
	askToSaveTabRevision = true;
	somethingChangedTaskRevisionTab = true;
//	setElementEnabled("blouSaveTaskTabButton", true);
	
	document.getElementById("blouSaveTaskRevisionTabButton").disabled = false;
}
//taskRevisionTabChanged -- End

function sAcceptClicked() {
	var checkBox = document.getElementById("sAccept");
	if (checkBox.checked == true){
		document.getElementById("sReject").checked = false;
		document.getElementById("sAcceptedflag").value = 'Y';
	} else {
		document.getElementById("sAcceptedflag").value = 'N';
	};
	taskSubmissionTabChanged();
}

function sRejectClicked() {
	var checkBox = document.getElementById("sReject");
	if (checkBox.checked == true){
		document.getElementById("sAccept").checked = false;
		document.getElementById("sAcceptedflag").value = 'R';
	} else {
		document.getElementById("sAcceptedflag").value = 'N';
	};
	taskSubmissionTabChanged();
}


//taskSubmissionTabChanged -- Start
function taskSubmissionTabChanged() {
	currentTabSubmission = "TaskSubmission";
	askToSaveTabSubmission = true;
	somethingChangedTaskSubmissionTab = true;
//	setElementEnabled("blouSaveTaskTabButton", true);
	
	document.getElementById("blouSaveTaskSubmissionTabButton").disabled = false;
}
//taskRevisionTabChanged -- End



//anyUnsavedData -- Start
function anyUnsavedData() {
	showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {

				closeModalDialog("genProcedureDialog");
			}
	) 
}
//anyUnsavedData -- End

//setAllSomethingChangedFlags -- Start
function setAllSomethingChangedFlagsFalse() {

	somethingChangedGeneralTab = false;
	somethingChangedInDialog = false;
	askToSaveTab = false;
	askToSaveDialog = false;
}
//setAllSomethingChangedFlags -- End

// ***********************************************************************



function updateDefToolbarButtons() {
	var hasSelected = pDefsTable.rows('.selected').data().length > 0;
	setTableButtonState(pDefsTable, "promptDeleteDefBtn", hasSelected);	
	
	
	
//	console.log("id hier " + $("#pGenProcedureId").val());
//	
//	if (( $("#pGenProcedureId").val() == "") || ($("#pGenProcedureId").val() == null)){
//		console.log("id is null");
//		setTableButtonState(pDefsTable, "newDefBtn", false);	
//	}
	
	
	
	
	
}

function updateRoleToolbarButtons() {
	var hasSelected = pRoleTable.rows('.selected').data().length > 0;
	setTableButtonState(pRoleTable, "promptDeleteRoleBtn", hasSelected);	
}


function updateDocsToolbarButtons() {
	var hasSelected = pDocsTable.rows('.selected').data().length > 0;
	setTableButtonState(pDocsTable, "promptDeleteDocsBtn", hasSelected);	
}




function promptDeleteDef() {
	showDialog("Confirm?",
		       "Are you sure that you wish to remove the selected Definition?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteDef(pDefsTable);
			   }
	);
}

function promptDeleteRole() {
	showDialog("Confirm?",
		       "Are you sure that you wish to remove the selected Role?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteRole(pRoleTable);
			   }
	);
}

function promptDeleteDocs() {
	showDialog("Confirm?",
		       "Are you sure that you wish to remove the selected Document?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteDocs(pDocsTable);
			   }
	);
}





function deleteDef(tbl) {
	var postUrl = "/rest/ignite/v1/procedure-definition/delete";
	var row = tbl.rows('.selected').data()[0];
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Definition has been removed.", tbl,
			function(){	
				tbl.rows().deselect();
				updateDefToolbarButtons
			}
	);
}

function deleteRole(tbl) {
	var postUrl = "/rest/ignite/v1/person-responsible/delete";
	var row = tbl.rows('.selected').data()[0];
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Role has been removed.", tbl,
			function(){	
				tbl.rows().deselect();
				updateRoleToolbarButtons
			}
	);
}

function deleteDocs(tbl) {
	var postUrl = "/rest/ignite/v1/procedure-related-docs/delete";
	var row = tbl.rows('.selected').data()[0];
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Document has been removed.", tbl,
			function(){	
				tbl.rows().deselect();
				updateDocsToolbarButtons
			}
	);
}











function aUnitsKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('aUnits').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('aUnits').value = ''
		return;
	};
	
}	











//Johannes
function getUserNameIndivId() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#aIndividualIdLogger").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#aIndividualLoggerName").val(volleNaam);
//			$("#aIndividualLoggerName").val(data.individualId);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
};




function initializeDefinitionTable(genProcedureId) 
{
	
//console.log("begin");	
	var columnArray = [
		{ data : "procedureDefinitionId" },   		//0
		{ data : "genProcedureId" },				//1
		{ data : "definitionId" },	             //2
		{ data : "name" },						//3
		{ data : "description" }				//4
	];
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0, 1, 2]
	}

	];
	
	var buttonsArray = [
	{
		attr: {
			id: "newDefBtn"
		},
		titleAttr: "New",
		text: "<i class='fas fa-plus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			selectDefinition(genProcedureId);
		}
	},
	{
		attr: {
			id: "promptDeleteDefBtn"
		},
		titleAttr: "Delete",
		text: "<i class='fas fa-minus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			promptDeleteDef();
		}
	},
	];

//	console.log("HIER..../rest/ignite/v1/gen-procedure/pd-list-procedure/" + genProcedureId); //127.0.0.1:8081/rest/ignite/v1/genProcedure/tasks/64
//
//	console.dir(columnArray);
	
	pDefsTable = initializeGenericTable("pDefsTable", 
			                            "/rest/ignite/v1/gen-procedure/pd-list-procedure/" + genProcedureId,
			                            columnArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            //function(rowSelector) {
			                			null, //	editTask(rowSelector);
			                			//},
										null,
										10,
										[1,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
								);
	
	//console.dir(data);
	
	
	pDefsTable.off('deselect')
	pDefsTable.on('deselect', function (e, dt, type, indexes) {
		updateDefToolbarButtons();
	} );

	pDefsTable.off('select')
	pDefsTable.on('select', function (e, dt, type, indexes) {
		updateDefToolbarButtons();
	} );

	updateDefToolbarButtons();
	
//	console.log("HIER...2");
							
}   //function initializeDefinitionTable(genProcedureId)





function initializeRoleTable(genProcedureId) 
{
	
//console.log("begin");	
	var columnArray = [
		{ data : "personResponsibleId" },   		//0
		{ data : "genProcedureId" },				//1
		{ data : "roleOnAProjectId" },	             //2
		{ data : "name" }, 							//3
		{ data : "abbreviation" },						//4
		{ data : "description" }				//5
	];
		
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0, 1, 2, 5]
	}

	];
	
	var buttonsArray = [
	{
		attr: {
			id: "newRoleBtn"
		},
		titleAttr: "New",
		text: "<i class='fas fa-plus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			selectRole(genProcedureId);
		}
	},
	{
		attr: {
			id: "promptDeleteRoleBtn"
		},
		titleAttr: "Delete",
		text: "<i class='fas fa-minus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			promptDeleteRole();
		}
	},
	];

//	console.log("HIER..../rest/ignite/v1/gen-procedure/pr-list-procedure/" + genProcedureId); //127.0.0.1:8081/rest/ignite/v1/genProcedure/tasks/64
//
//	console.dir(columnArray);
	
	pRoleTable = initializeGenericTable("pRoleTable", 
			                            "/rest/ignite/v1/gen-procedure/pr-list-procedure/" + genProcedureId,
			                            columnArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            //function(rowSelector) {
			                			null, //	editTask(rowSelector);
			                			//},
										null,
										10,
										[1,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
								);
	
	//console.dir(data);
	
	
	pRoleTable.off('deselect')
	pRoleTable.on('deselect', function (e, dt, type, indexes) {
		updateRoleToolbarButtons();
	} );

	pRoleTable.off('select')
	pRoleTable.on('select', function (e, dt, type, indexes) {
		updateRoleToolbarButtons();
	} );

	updateRoleToolbarButtons();
	
//	console.log("HIER...2");
							
}   //function initializeRoleTable(genProcedureId)





function initializeDocsTable(genProcedureId) 
{
	
//console.log("begin");	
	var columnArray = [
		{ data : "procedureRelatedDocsId" },   		//0
		{ data : "genProcedureId" },				//1
		{ data : "relatedDocsId" },	             //2
		{ data : "fileName" }, 							//3
		{ data : "description" },						//4
		{ data : "folderPath" }				//5
	];
		
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0, 1, 2, 5]
	}

	];
	
	var buttonsArray = [
	{
		attr: {
			id: "newDocsBtn"
		},
		titleAttr: "New",
		text: "<i class='fas fa-plus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			selectDocs(genProcedureId);
		}
	},
	{
		attr: {
			id: "promptDeleteDocsBtn"
		},
		titleAttr: "Delete",
		text: "<i class='fas fa-minus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			promptDeleteDocs();
		}
	},
	];

//	console.log("initializeDocsTable:   " + genProcedureId);
//	console.log("/rest/ignite/v1/gen-procedure/prd-list-procedure/" + genProcedureId);
//	console.dir(columnArray);
	
	pDocsTable = initializeGenericTable("pDocsTable", 
			                            "/rest/ignite/v1/gen-procedure/prd-list-procedure/" + genProcedureId,
			                            columnArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            //function(rowSelector) {
			                			null, //	editTask(rowSelector);
			                			//},
										null,
										10,
										[1,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
								);
	
	//console.dir(data);
	
	
	pDocsTable.off('deselect')
	pDocsTable.on('deselect', function (e, dt, type, indexes) {
		updateDocsToolbarButtons();
	} );

	pDocsTable.off('select')
	pDocsTable.on('select', function (e, dt, type, indexes) {
		updateDocsToolbarButtons();
	} );

	updateDocsToolbarButtons();
	
//	console.log("HIER...2");
							
}   //function initializeRoleTable(genProcedureId)















$(document).ready(function() {
	
	// Any initialization
	// selectNextGenProcedureNumber();

//	$("#rangeStartDate").datepicker("setDate", new Date(firstDay));
//	$("#rangeEndDate").datepicker("setDate", new Date(lastDay));	
//	
//	console.log("firstDay: " + firstDay);
	
	initializeGenProcedureTable();

} );
