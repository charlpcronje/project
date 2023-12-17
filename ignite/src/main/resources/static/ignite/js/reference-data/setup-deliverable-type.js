var industryTable = null;
var deliverableTypeTable = null;
var askToSaveDialog = null;
var somethingChangedInDialog = null;

function initializeDeliverableTypeTable() {
	showEmptyDeliverableTypePanel();
	
	var queryUrl="/rest/ignite/v1/service-discipline/level0" ;
//	console.log(queryUrl);
	
	var columnsArray = [
		{ data: "serviceDisciplineId" }, //0
		{ data: "serviceDisciplineCode" },//1      
		{ data: "serviceDisciplineName" },//2    
		{ data: "orderNumber" } //3 
	];

	var columnDefsArray = [  
		{
			visible: false,
			targets: [0,3]
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
										[[1, "asc"]]	// ordering

	);
	
		industryTable.off('deselect');
		industryTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyDeliverableTypePanel();
	} );

		industryTable.off('select');
		industryTable.on('select', function (e, dt, type, indexes) {
		intializeDeliverableTypeTable(dt.data());
	} );	
} //initializeDeliverableTypeTable -- End


function showEmptyDeliverableTypePanel() {
	setDivVisibility("emptyDeliverableTypePanel", "block");
	setDivVisibility("deliverableTypePanel", "none");
	$("#selectedDeliverableTypeId").val("");	
	$("#selectedDeliverableTypeName").val("");	
}

//********************************************************************************************************
//DeliverableType

//start
function intializeDeliverableTypeTable(iqData) {
	
	setDivVisibility("emptyDeliverableTypePanel", "none");
	setDivVisibility("deliverableTypePanel", "block");
//console.dir(iqData)
	var industryId = iqData.serviceDisciplineIdIndustry;
	$("#selectedIndustryId").val(iqData.serviceDisciplineIdIndustry);	
	$("#selectedIndustryName").val(iqData.serviceDisciplineName);
	
	$("#pageHeaderDeliverableType").html("<i class=\"fas fa-id-badge\"></i> Setup: Deliverable Type: " + $("#selectedIndustryName").val());
	
	
	var queryUrl="/rest/ignite/v1/deliverable-type/serviceDisciplineIdIndustry/" + industryId; 
	
	var columnsArray = [
		{ data: "deliverableTypeId" }, //0
		{ data: "serviceDisciplineIdIndustry" }, //1
		{ data: "name" },                //2
		{ data: "formatType" },          //3
		{ data: "description" } ,        //4
		
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,4]
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editDeliverableType(null);
			}
		},
		{
			attr: {
				id: "deleteiqBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteDeliverableType();
			}
		}	                    
	]         		
          		
	
	deliverableTypeTable = initializeGenericTable("deliverableTypeTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editDeliverableType(rowSelector);
										},
										null,
										31,
										[[1,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);

		deliverableTypeTable.off('deselect');
		deliverableTypeTable.on('deselect', function (e, dt, type, indexes) {
			updateDeliverableTypeToolbarButtons();
	} );

		deliverableTypeTable.off('select');
		deliverableTypeTable.on('select', function (e, dt, type, indexes) {
			updateDeliverableTypeToolbarButtons();
	} );	

	updateDeliverableTypeToolbarButtons();
	
}



//editDeliverableType -- Start
function editDeliverableType(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = deliverableTypeTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);
	}
	deliverableTypeTable.rows().deselect();
	
	//  MySql-TableName: DeliverableType		
	$("#dtIndustryName").val($("#selectedIndustryName").val()); //0								   (js3Str)
	$("#dtDeliverableTypeId").val(data.deliverableTypeId); //1
	$("#dtName").val(data.name);                                     //2
	$("#dtDescription").val(data.description);                       //3


		
	// Set the Save Button to disabled
	setElementEnabled("saveDeliverableTypeButton", false);
	somethingChangedInDialog = false;
	$("#deliverableTypeDialogHeader").html("DeliverableType: " + $("#selectedDeliverableTypeName").val());
	showModalDialog("deliverableTypeDialog");
}
//editDeliverableType -- End


//saveDeliverableType -- Begin
function saveDeliverableType() { 
	
	var postUrl = "/rest/ignite/v1/deliverable-type/new";
	var postData = {
		deliverableTypeId : $("#dtDeliverableTypeId").val()  //0 MySql-TableName: DeliverableType
		,serviceDisciplineIdIndustry : $("#selectedIndustryId").val()
		,name : $("#dtName").val() 
		,formatType : $("#dtFormatType").val()                                                            //1
		,description : $("#dtDescription").val()                                    //2
	};

	var errors = "";
	

	// validate
	if ((postData.name == "") || (postData.name == null)) {
		errors += "A Deliverable Type Name is required<br>";
	}
	
	if ((postData.formatType == "") || (postData.formatType == null)) {
		errors += "A Deliverable Type Format is required<br>";
	}

	if (showFormErrors("dtDeliverableTypeDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.deliverableTypeId != "") && (postData.deliverableTypeId != null)) {
		//This is an update
		postUrl = "/rest/ignite/v1/deliverable-Type";
	}else {
		postData.deliverableTypeId = null;
	}
	
	saveEntry(postUrl, postData, "deliverableTypeDialog", "The Deliverable Type has been saved.", deliverableTypeTable);

}
//saveDeliverableType -- End




function updateDeliverableTypeToolbarButtons() {
	var hasSelected = deliverableTypeTable.rows('.selected').data().length > 0;

	setTableButtonState(deliverableTypeTable, "deletedtBtn", hasSelected);	
}
	
function promptDeleteDeliverableType() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Deliverable Type?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteDeliverableType();
			   }
	);
}

function deleteDeliverableType() {
	var postUrl = "/rest/ignite/v1/deliverable-type/delete";
	var row = deliverableTypeTable.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Deliverable Type has been deleted.", deliverableTypeTable,
			function(){	
				deliverableTypeTable.rows().deselect();
				updateDeliverableTypeToolbarButtons();
			});
	
}



function closeDeliverableTypeDialog() {
	if (somethingChangedInDialog) {
	  // Show a message about unsaved changes
	  showDialog("Confirm?", "You have unsaved changes - are you sure you wish to cancel?", 
	  DialogConstants.TYPE_CONFIRM, 
	  DialogConstants.ALERTTYPE_INFO, 
	  function() {
	    setDivVisibility("dtDeliverableTypeDlgErrorDiv", "none");
	    closeModalDialog("deliverableTypeDialog");
	    	somethingChangedInDialog = false;
	  });
	} else {
	  // Close the dialog without showing a message
	  setDivVisibility("dtDeliverableTypeDlgErrorDiv", "none");
	  closeModalDialog("deliverableTypeDialog");
	}
} //closeDeliverableTypeDialog -- End


function deliverableTypeDialogChanged() {
	somethingChangedInDialog = true;
	setElementEnabled("saveDeliverableTypeButton", true);
} //deliverableTypeDialogChanged -- END






// ***********************************************************************
$(document).ready(function() {
	
	// Any initialization
	initializeDeliverableTypeTable();
	showIgDeveloperOption();

} );
