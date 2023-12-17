var assignmentGroupTable = null;
var individualId;

function initializeAssignmentGroupTable() {

//	getUserNameIndivId()
//	var individualId;
	
//	let individualId = document.getElementById('aIndividualIdforList').value;
//	var individualId = $("#aIndividualIdforList").val();
//  var individualId = document.querySelector('#aIndividualIdforList').value;	
	
	//var individualId = getUserNameIndivId()
	
//console.log('individualId: ' + individualId);	



	var columnsArray = [
	    { data: "assignmentGroupId" },
	    { data: "projectId" },
	    { data: "" },
		{ data: "assignmentGroupNumber" },
		{ data: "name" },
		{ data: "description" }
	];
	
	var columnDefsArray = [
	  				{ 
	  					visible: false,
	  					targets: 0
	  				},
            		{
            			render: function (data, type, row) {
            				var p = "";
//            				console.log(row.projectId);
            				if (row.hasOwnProperty("project")){
            					p = row.project.title;
            				}
            				return p;
            			},
            			targets: 2
            		}
	  			];	
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editAssignmentGroup(null);
			}
		},
		{
			attr : {
				id: "deleteAssignmentGroupBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteAssignmentGroup();
			}
		}
	];

//	console.log("/rest/ignite/v1/assignment-group/for-individual/" + individualId);
	assignmentGroupTable = initializeGenericTable("assignmentGroupTable", 
			                            "/rest/ignite/v1/assignment-group/for-individual/" + individualId,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editAssignmentGroup(rowSelector);
										});

	assignmentGroupTable.off('deselect');
	assignmentGroupTable.on('deselect', function (e, dt, type, indexes) {
		updateAssignmentGroupToolbarButtons();
	} );

	assignmentGroupTable.off('select');
	assignmentGroupTable.on('select', function (e, dt, type, indexes) {
		updateAssignmentGroupToolbarButtons();
	} );

	// to initially set the buttons
	updateAssignmentGroupToolbarButtons();
}


function getUserNameIndivId() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			individualId = data.individualId;
			$("#aIndividualIdforList").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
//			console.log(data.individualId);
			$("#aIndividualNameList").val(volleNaam);
			initializeAssignmentGroupTable();
//			return data.individualId
//			$("#aIndividualLoggerName").val(data.individualId);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}


function getUserNameIndivIdForEdit() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			individualId = data.individualId;
			$("#aIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
//			console.log(data.individualId);
			$("#aIndividualName").val(volleNaam);

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}

function updateAssignmentGroupToolbarButtons() {
	var hasSelected = assignmentGroupTable.rows('.selected').data().length > 0;
	
	setTableButtonState(assignmentGroupTable, "deleteAssignmentGroupBtn", hasSelected);
}


function editAssignmentGroup(rowSelector) {
	
	console.log('Edit hier');
	getUserNameIndivIdForEdit()
	var data = {};
	document.getElementById('aProjectId').value = ''
	document.getElementById('aProjectName').value = ''
			
	if (rowSelector != null) {
		data = assignmentGroupTable.row(rowSelector).data();
	}
	assignmentGroupTable.rows().deselect();
//	console.dir(data);
	$("#aDlgAssignmentGroupId").val(data.assignmentGroupId);
	$("#aProjectId").val(data.projectId);
	if (rowSelector != null) { $("#aProjectName").val(data.project.title)};
	$("#aDlgAssignmentGroupNumber").val(data.assignmentGroupNumber);
	$("#aDlgName").val(data.name);
	$("#aDlgDescription").val(data.description);
//console.dir(data);
	// Disabel the code if update, enable if insert
	$("#aDlgAssignmentGroupNumber").prop("readonly", (data.assignmentGroupNumber != null) &&(data.assignmentGroupNumber != ""));	
	
//	console.log('Edit hier2');
	showModalDialog("assignmentGroupDialog");
}

function promptDeleteAssignmentGroup() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Assignment Group?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteAssignmentGroup();
			   }
	);
}

function deleteAssignmentGroup() {
	var postUrl = "/rest/ignite/v1/assignment-group/delete";
	var row = assignmentGroupTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Assignment Group has been deleted.", assignmentGroupTable,
			function(){	
				assignmentGroupTable.rows(".selected").nodes().to$().removeClass("selected");
				updateAssignmentGroupToolbarButtons();
			}
	);
}

function saveAssignmentGroup() {
	
	var postUrl = "/rest/ignite/v1/assignment-group/new";
	var mode = $("#aDlgMode").val();
	
	var assignmentGroupId = $("#aDlgAssignmentGroupId").val();
	var projectId = $("#aProjectId").val();
	var assignmentGroupNumber = $("#aDlgAssignmentGroupNumber").val().trim().toUpperCase();
	var name = $("#aDlgName").val();
	var description = $("#aDlgDescription").val();
	var errors = "";

	var postData = {
		mode: mode,
		assignmentGroupId: assignmentGroupId,
		projectId: projectId,
		assignmentGroupNumber: assignmentGroupNumber,
		name: name,
		description: description	
	}

	if ((postData.assignmentGroupNumber == null) || (postData.assignmentGroupNumber == "")) {
		errors += "A Assignment Group Number is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {

		errors += "A Name is required<br>";
	}


	
	// validation...
	if (showFormErrors("agDlgErrorDiv", errors)) {
		return;
	}
	// Is the code readonly?  If it is, then the record already exists.
	if ($("#aDlgAssignmentGroupNumber").is("[readonly]")) {
		//This is an update
		postUrl = "/rest/ignite/v1/assignment-group";
	}
	console.dir(postData);
	console.dir(postUrl);
	
	saveEntry(postUrl, postData, "assignmentGroupDialog", "The Assignment Group has been saved.", assignmentGroupTable);
}


function getProjectName() {

	let projectId = document.getElementById('aProjectId').value;
	var queryUrl="/rest/ignite/v1/project/" + projectId;
	
	var targetName = "aProjectName";
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectId";
			var refColumnName="projectId";
			var columns = [
				{ data: "projectId", name: "Id" },
				{ data: "code", name: "Code" },
				{ data: "title", name: "Title" },
				{ data: "description", name: "Description" },
				{ data: "isActive", name: "Is Active" }
			];


			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {

				var project = row.code + " " + row.title;


				$("#" + targetName).val(project);
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




function selectProjectId() {

	let individualId = document.getElementById('aIndividualId').value;
	var queryUrl="/rest/ignite/v1/assignment-group/project-list/" + individualId;
	
	var targetId = "aProjectId";
	var targetName = "aProjectName";
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectId";
			var refColumnName="projectId";
//console.dir(data)			
			var columns = [
				{ data: "projectId", name: "Id" },
				{ data: "projectId", name: "Used to be Code" },
				{ data: "title", name: "Title" },
				{ data: "description", name: "Description" },
				{ data: "isActive", name: "Is Active" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectId;

				var project = row.title;

				$("#" + targetId).val(id);
				$("#" + targetName).val(project);
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


// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	getUserNameIndivId();
	
//	initializeAssignmentGroupTable();
} );
