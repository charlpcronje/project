


var industryTable = null;
var roleOnAProjectTable = null;
var somethingChangedInDialog = null;
	

function initializeIndustryTable() {

	showEmptyRoleOnAProjectPanel();
	
	var queryUrl="/rest/ignite/v1/service-discipline/level0" ;
//	console.log(queryUrl);
	
	var columnsArray = [
		{ data: "serviceDisciplineId" }, //0
		{ data: "serviceDisciplineCode" },     
		{ data: "serviceDisciplineName" },    
		{ data: "orderNumber" }   
	];

	var columnDefsArray = [  
		{
			visible: false,
			targets: [0]
		}
	];

	var buttonsArray = [

	];

	industryTable = initializeGenericTable("industryTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editIndustry(rowSelector);  //Double click
										},
										null,
										31, 			// pageLength  
										[[3, "asc"]]	// ordering

	);
	
		industryTable.off('deselect');
		industryTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyRoleOnAProjectPanel();
	} );

		industryTable.off('select');
		industryTable.on('select', function (e, dt, type, indexes) {
		intializeRoleOnAProjectTable(dt.data());
	} );	



} //initializeIndustryTable -- End


function showEmptyRoleOnAProjectPanel() {
	setDivVisibility("emptyRoleOnAProjectPanel", "block");
	setDivVisibility("roleOnAProjectPanel", "none");
	$("#selectedIndustryId").val("");	
	$("#selectedIndustryName").val("");	
}
















//***********************************************************************
//RoleOnAProject

//start
function intializeRoleOnAProjectTable(ropData) {
	
	setDivVisibility("emptyRoleOnAProjectPanel", "none");
	setDivVisibility("roleOnAProjectPanel", "block");

	
	var industryId = ropData.serviceDisciplineIdIndustry;
	$("#selectedIndustryId").val(ropData.serviceDisciplineIdIndustry);	
	$("#selectedIndustryName").val(ropData.serviceDisciplineName);

	$("#pageHeaderRoleOnAProject").html("<i class=\"fas fa-id-badge\"></i> Setup: Role on a Project: " + $("#selectedIndustryName").val());
	
	var queryUrl="/rest/ignite/v1/role-on-a-project/list-view-role-on-a-project-for-service-discipline/" + industryId; 
	
	var columnsArray = [
		{ data: "roleOnAProjectId" }, //0
		{ data: "serviceDisciplineIdIndustry" },          //1
		{ data: "serviceDisciplineIdIndustry_Name" },    //2   
		{ data: "name" },                //3
		{ data: "abbreviation" },        //4
		{ data: "description" }          //5
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2]
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editRoleOnAProject(null);
			}
		},
		{
			attr: {
				id: "deleteropBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteRoleOnAProject();
			}
		}	                    
	]         		
          		
	
	roleOnAProjectTable = initializeGenericTable("roleOnAProjectTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editRoleOnAProject(rowSelector);
										},
										null,
										31,
										[[3,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);

		roleOnAProjectTable.off('deselect');
		roleOnAProjectTable.on('deselect', function (e, dt, type, indexes) {
			updateRoleOnAProjectToolbarButtons();
	} );

		roleOnAProjectTable.off('select');
		roleOnAProjectTable.on('select', function (e, dt, type, indexes) {
			updateRoleOnAProjectToolbarButtons();
	} );	

	updateRoleOnAProjectToolbarButtons();
	
}



//editRoleOnAProject -- Start
function editRoleOnAProject(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = roleOnAProjectTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	roleOnAProjectTable.rows().deselect();
	
	//  MySql-TableName: RoleOnAProject										   (js3Str)
	$("#ropRoleOnAProjectId").val(data.roleOnAProjectId); //0
	$("#ropName").val(data.name);                                     //1
	$("#ropAbbreviation").val(data.abbreviation);                       //2
	$("#ropDescription").val(data.description);                       //2

	// Set the Save Button to disabled
	setElementEnabled("saveRoleOnAProjectButton", false);
	somethingChangedInDialog = false;
	$("#roleOnAProjectDialogHeader").html("Role on a Project: " + $("#selectedIndustryName").val());
	showModalDialog("roleOnAProjectDialog");
}
//editRoleOnAProject -- End


//saveRoleOnAProject -- Begin
function saveRoleOnAProject() { 
	
	var postUrl = "/rest/ignite/v1/role-on-a-project/new";
	var postData = {
		roleOnAProjectId : $("#ropRoleOnAProjectId").val()  //0 MySql-TableName: RoleOnAProject
		,serviceDisciplineIdIndustry : $("#selectedIndustryId").val()
		,name : $("#ropName").val()                                                  //1
		,abbreviation : $("#ropAbbreviation").val()                                  //2
		,description : $("#ropDescription").val()                                    
	};

	var errors = "";
	
	// validate
	if ((postData.name == "") || (postData.name == "")) {
		errors += "A Role Name is required<br>";
	}

	if (showFormErrors("ropRoleOnAProjectDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.roleOnAProjectId != null) && (postData.roleOnAProjectId != "")) {	
		//This is an update
		postUrl = "/rest/ignite/v1/role-on-a-project";
	} else {
		postData.roleOnAProjectId = null;  //empty string werk nie
	}
		
//	console.log(postUrl);
//	console.log(postData);
	
	saveEntry(postUrl, postData, "roleOnAProjectDialog", "The Qualification has been saved.", roleOnAProjectTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
//saveRoleOnAProject -- End




function updateRoleOnAProjectToolbarButtons() {
	var hasSelected = roleOnAProjectTable.rows('.selected').data().length > 0;

	setTableButtonState(roleOnAProjectTable, "deleteropBtn", hasSelected);	
}
	
function promptDeleteRoleOnAProject() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Role?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteRoleOnAProject();
			   }
	);
}

function deleteRoleOnAProject() {
	var postUrl = "/rest/ignite/v1/role-on-a-project/delete";
	var row = roleOnAProjectTable.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Role has been deleted.", roleOnAProjectTable,
			function(){	
				roleOnAProjectTable.rows().deselect();
				updateRoleOnAProjectToolbarButtons();
			});
	
}



function closeRoleOnAProjectDialog() {
	if (somethingChangedInDialog) {
	  // Show a message about unsaved changes
	  showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
	  DialogConstants.TYPE_CONFIRM, 
	  DialogConstants.ALERTTYPE_INFO, 
	  function() {
	    setDivVisibility("ropRoleOnAProjectDlgErrorDiv", "none");
	    closeModalDialog("roleOnAProjectDialog");
	    	somethingChangedInDialog = false;
	  });
	} else {
	  // Close the dialog without showing a message
	  setDivVisibility("ropRoleOnAProjectDlgErrorDiv", "none");
	  closeModalDialog("roleOnAProjectDialog");
	}
} //closeRoleOnAProjectDialog -- End


function roleOnAProjectDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveRoleOnAProjectButton", true);
} //roleOnAProjectDialogChanged -- END






// ***********************************************************************
$(document).ready(function() {
	
	// Any initialization
	initializeIndustryTable();
	showIgDeveloperOption();

} );
