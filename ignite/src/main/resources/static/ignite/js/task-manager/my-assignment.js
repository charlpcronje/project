/*
Resource edit page needs to work
meeds start date and edn date options
need to check that there is no overlap
*/

var myAssignmentTable = null;
var participantOfficeTable = null;
var contactPointTable = null;
var humanResourceTable = null;

var somethingChangedGeneralTab = null;
var somethingChangedSSTab = null;
var somethingChangedOSDTab = null;
var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var currentOSD = null;
//var participantId = null;

function initializeMyAssignmentTable(){
	
	var columnsArray = [
        { data: "assignmentId" },                   //0
        { data: "projectParticipantIdIndivResp" },  //1
        { data: "individualRespName" },             //2
        { data: "assignmentGroupId" },              //3
        { data: "assignmentGroupName" },            //4
        { data: "assignmentNumber" },               //5
        { data: "name" },                 //6
        { data: "description" },          // 7
        { data: "startDate" },            //8
        { data: "timeOfDay" },            //9
        { data: "repeatCode" },           //10
        { data: "lastUpdateTimestamp" },  //11 
        { data: "lastUpdateUserName" },   //12
        { data: "individualId" },          //13
        { data: "completed" }          //14

	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3,  11, 12, 13]
			
		},

//		{
//			render: function (data, type, row) {
//				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
//			},
//			className: "dt-center",
//			targets: 9
//		},
		
		{
			render: function (data, type, row) {
				var html = data;
				var Rep = row.repeatCode.substring(0, 1);
				if (Rep == "D") {html = "Daily"};
				if (Rep == "W") {html = "Weekly"};
				if (Rep == "M") {html = "Monthly"};
				if (Rep == "Y") {html = "Yearly"};
				return html;
			},
			//width: "100px",
			targets: [10]//, 15, 17]
		},
		
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			//width: "100px",
			targets: [8, 11, 12]
		}			
		
	];
	
	var buttonsArray = [
		{
			attr: {
				id: "showAllAssignmentsBtn"
			},
			titleAttr: "All",
			text: "<i class='fas fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				 
				$("#pageHeaderAssignments").html("All my Assignments");
				setTableButtonState(myAssignmentTable, "showCurrentAssignmentsBtn", true);	
				setTableButtonState(myAssignmentTable, "showAllAssignmentsBtn", false);	
				var requestUrl = "/rest/ignite/v1/assignment/my-assignments/" + individualId;
				myAssignmentTable.ajax.url( springUrl(requestUrl) ).load()
				}
		},
		{
			attr: {
				id: "showCurrentAssignmentsBtn"
			},
			titleAttr: "Current",
			text: "<i class='fas fa-clock'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				$("#pageHeaderAssignments").html("My Current Assignments");
				setTableButtonState(myAssignmentTable, "showCurrentAssignmentsBtn", false);	
				setTableButtonState(myAssignmentTable, "showAllAssignmentsBtn", true);	
				var requestUrl = "/rest/ignite/v1/assignment/my-assignments-current/" + individualId;
				myAssignmentTable.ajax.url( springUrl(requestUrl) ).load()
				}
		}
	];
	
	
	var individualId = document.getElementById("aMyIndividualId").value;
	//console.log("Die individualID: " +  
	//console.dir("/rest/ignite/v1/assignment/my-assignments-current/" + individualId);
	
	myAssignmentTable = initializeGenericTable("myAssignmentTable", 
			                            "/rest/ignite/v1/assignment/my-assignments-current/" + individualId,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											viewTasks(rowSelector)
										},
			                            null,
			                            25,
			                            [3,"asc"]
										
	);

	setTableButtonState(myAssignmentTable, "showCurrentAssignmentsBtn", false);	
	setTableButtonState(myAssignmentTable, "showAllAssignmentsBtn", true);		

}
	





//editAssignment -- Start
function viewTasks(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	var header = "Assignment Detail";
	
	
	
	if (rowSelector != null) {
		data = myAssignmentTable.row(rowSelector).data();
		enabled = true;
		header = "Assignment Detail: " + data.assignmentNumber + " " + data.name;	
		var assignmentId = data.assignmentId
		console.log("viewtasks");
	};
	
	$("#viewTaskDialogHeader").html(header);
	
	
	myAssignmentTable.rows().deselect();
	setActiveTab("generalLink");
	setElementEnabled("saveGeneralTabButton", false); //Disable Save button when initializing
	
	
	var columnArray = [
	           		{ data : "taskId" },          			//0
	           		{ data : "assignmentId" },				//1
	           		{ data : "taskImportanceTypeId" },	
	           		{ data : "" },							//3
	           		{ data : "" },							//4	
	           		{ data : "orderNumber" },				//5
	           		{ data : "taskNumber" },				//6
	           		{ data : "name" },						//7
	           		{ data : "description" },				//8
	           		{ data : "startDate" },					//9
	           		{ data : "durationDays" },				//10
	           		{ data : "durationHours" },				//11
	           		{ data : "timeOfDay" },					//12
	           		{ data : "completed" }					//13
	           	];
	           	
   	var columnDefsArray = [
   	{
   		visible: false,
   		targets: [0, 1, 2, 3, 6, 8, 10, 11, 12]   //0, 1, 2
   	},
	           	{
	           		render: function (data, type, row) {
	           			var html = data;
	           			if (type == "display") {
	           				html = timestampToString(data, false);
	           			}
	           			return html;
	           		},
	           		//width: "100px",
	           		targets: [9]
	           	},
   	
   	{
   		render: function (data, type, row){
   			data = "";
   			if (row.hasOwnProperty("taskImportanceType")){
   				if (row.taskImportanceType != null){
   					data = row.taskImportanceType.importanceValue;
   				}
   			}
   			return data;
   		},
   		targets: [3],
   	},
   	{
   		render: function (data, type, row){
   			data = "";
   			if (row.hasOwnProperty("taskImportanceType")){
   				if (row.taskImportanceType != null){
   					data = row.taskImportanceType.name;
   				}
   			}
   			return data;
   		},
   		targets: [4]
   	}
   	
   	
   	];
   	
   	var buttonsArray = [
   		
   	];

//	           	console.log("HIER..../rest/ignite/v1/assignment/tasks/" + assignmentId); //127.0.0.1:8081/rest/ignite/v1/assignment/tasks/64
//	           	console.dir(columnArray);
   	
   	myTaskTable = initializeGenericTable("myTaskTable", 
   			                            "/rest/ignite/v1/assignment/tasks/" + assignmentId,
   			                            columnArray,
   			                            columnDefsArray,
   			                            buttonsArray,
   			                            null,
//   			                            function(rowSelector) {
//   										showRevisionTable(rowSelector);
//   			                			},
   										null,
   										10,
   										[5,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
   								);
   	
   	showModalDialog("viewTaskDialog");
   	
   	myTaskTable.off('deselect')
   	myTaskTable.on('deselect', function (e, dt, type, indexes) {
   		showEmptyRevisionPanel();
	} );

   	myTaskTable.off('select')
   	myTaskTable.on('select', function (e, dt, type, indexes) {
   		showRevisionTable(dt.data());
	} );

	
}
// editAssignment -- End

function showEmptyRevisionPanel() {
	setDivVisibility("revisionEmptyPanel", "block");
	setDivVisibility("revisionPanel", "none");
	document.getElementById('taskCompleted').value = ''	
	document.getElementById('revMaxRevision').value = ''		
	showEmptySubmissionPanel();
}

function showEmptySubmissionPanel() {
	setDivVisibility("submissionEmptyPanel", "block");
	setDivVisibility("submissionPanel", "none");
	document.getElementById('subPanelTaskRevisionId').value = ''	
	document.getElementById('revRevisionNumber').value = ''	
}




function showRevisionTable(taskRow) {
	
	console.log("showRevisionTable");
				
	if (taskRow == null) {
		return;
	}

	var taskId = taskRow.taskId;
	
	$("#taskCompleted").val(taskRow.completed);	
	
	setDivVisibility("revisionEmptyPanel", "none");
	setDivVisibility("revisionPanel", "block");
	showEmptySubmissionPanel();

	var queryUrl = "/rest/ignite/v1/task-revision/list/" + taskId;
	console.log("showRevisionTable:  " + queryUrl);

	var columnsArray = [

   		{ data: "taskRevisionId" },			// 0
		{ data: "taskId" },					// 1
		{ data: "revisionNumber" },			// 2
		{ data: "revisionDate" },			// 3
		{ data: "name" },					// 4
		{ data: "description" },			// 5
		{ data: "neededCompletionDate" },	// 6
		{ data: "expectedCompletionDate" }	// 7
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1]
		},

		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			//width: "100px",
			targets: [3, 6, 7]
		}	
	];

	var buttonsArray = [
	];
	
	myRevisionTable = initializeGenericTable("myRevisionTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            null,
										null,
										10,
										[2,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	myRevisionTable.off('deselect')
	myRevisionTable.on('deselect', function (e, dt, type, indexes) {
		showEmptySubmissionPanel();
	} );

	myRevisionTable.off('select')
	myRevisionTable.on('select', function (e, dt, type, indexes) {
		showSubmissionTable(dt.data());
	} );
	
	findMaxRevision(taskId);
}





function showSubmissionTable(revRow) {
//	console.log("showSubmissionTable");
				
	if (revRow == null) {
		return;
	}

	var taskRevisionId = revRow.taskRevisionId;
	var RevisionNumber = revRow.revisionNumber;
	
	setDivVisibility("submissionEmptyPanel", "none");
	setDivVisibility("submissionPanel", "block");
	$("#subPanelTaskRevisionId").val(taskRevisionId); 
	$("#revRevisionNumber").val(RevisionNumber); 
	//showEmptySubmissionPanel();

	var queryUrl = "/rest/ignite/v1/task-submission/list/" + taskRevisionId;
	console.log(queryUrl);

	var columnsArray = [

   		{ data: "taskSubmissionId" },	// 0
		{ data: "taskRevisionId" },	     // 1
		{ data: "submissionNumber" },	// 2
		{ data: "submissionDate" },		// 3
		{ data: "acceptedflag" },		// 4
		{ data: "comment" },			// 5
		{ data: "feedback" }			// 6
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1]
		},
		
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			//width: "100px",
			targets: [3]
		}			

	];

	var buttonsArray = [
	{
		attr: {
			id: "promptNewSubmissionBtn"
		},
		titleAttr: "New",
		text: "<i class='fas fa-plus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			editSubmission(null);
		}
	},
	{
		attr: {
			id: "promptDeleteSubmissionBtn"
		},
		titleAttr: "Delete",
		text: "<i class='fas fa-minus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			promptDeleteSubmission();
		}
	}
	];
	
	mySubmissionTable = initializeGenericTable("mySubmissionTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editSubmission(rowSelector);
			                			},
										null,
										10,
										[2,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	mySubmissionTable.off('deselect')
	mySubmissionTable.on('deselect', function (e, dt, type, indexes) {
		updateSubmissionToolbarButtons();
	} );

	mySubmissionTable.off('select')
	mySubmissionTable.on('select', function (e, dt, type, indexes) {
		updateSubmissionToolbarButtons();
	} );
	updateSubmissionToolbarButtons();
	

}


function promptDeleteSubmission() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Submission?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteSubmission(mySubmissionTable);
			   }
	);
}

function deleteSubmission(tbl) {
	var postUrl = "/rest/ignite/v1/task-submission/delete";
	var row = tbl.rows('.selected').data()[0];
	var errors = "";
	console.log(postUrl);

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Submission has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateSubmissionToolbarButtons(); // Create this ...
//				var projectId = $("#pdProjectId").val();
			}
	);
}


function updateSubmissionToolbarButtons() {
	
	//var data = {};
	var dieVlag = null
	
	var hasSelected = mySubmissionTable.rows('.selected').data().length > 0;

	if (hasSelected) {

		dieVlag = mySubmissionTable.rows('.selected').data().pluck('acceptedflag');
		
		if (dieVlag[0] != 'N') {
			setTableButtonState(mySubmissionTable, "promptDeleteSubmissionBtn", false);	
		} else {
			setTableButtonState(mySubmissionTable, "promptDeleteSubmissionBtn", true);	
		}
		
	} else {
		setTableButtonState(mySubmissionTable, "promptDeleteSubmissionBtn", false);
	};

	
	var taskCompleted = document.getElementById('taskCompleted').value;	
//console.log("TaskKompleet: " + taskCompleted);

	if (taskCompleted == 'Y') {
		setTableButtonState(mySubmissionTable, "promptNewSubmissionBtn", false);
		setTableButtonState(mySubmissionTable, "promptDeleteSubmissionBtn", false);
	} else {
		setTableButtonState(mySubmissionTable, "promptNewSubmissionBtn", true);
	}
	
	var revRevisionNumber = jQuery('#revRevisionNumber').attr('value');
	var revMaxRevision = jQuery('#revMaxRevision').attr('value');
	
	if (revRevisionNumber < revMaxRevision) {
		setTableButtonState(mySubmissionTable, "promptNewSubmissionBtn", false);
		setTableButtonState(mySubmissionTable, "promptDeleteSubmissionBtn", false);
		console.log("kleiner een")
	} 	
		
}




function editSubmission(rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
//	var enabled = false;
//	setAllSomethingChangedFlagsFalse();
	var header = "Submission Detail";

//	console.log("YofN1: " + document.getElementById('taskCompleted').value)
	
	if (document.getElementById('taskCompleted').value == 'Y') {
		var TaskKompleet = true
	} else {
		var TaskKompleet = false
	}
//	var TaskKompleet = (document.getElementById('taskCompleted').value = 'Y') ? true : false;			
//	var assignmentId
//	console.log("YofN2: " + document.getElementById('taskCompleted').value)
//	assignmentId = document.getElementById('aAssignmentId').value;
	
	if (rowSelector != null) {
		data = mySubmissionTable.row(rowSelector).data();
		enabled = true;
		header = "Submission Detail: " + data.taskNumber + " " + data.name;	
	} else { //set default values for new record
		
		$("#sTaskRevisionId").val($("#subPanelTaskRevisionId").val());
		
		
		enabled = false
//		console.log("hierrrr");
//		getNextSubmissionNumber();
		
//		console.log ('editSubmission::::');
//		document.getElementById('tTimeOfDayH').value = ''
//		document.getElementById('tTimeOfDayM').value = ''
	}
	
//	console.log("edit");
//	console.dir(data);
//	console.dir("rye: " + mySubmissionTable.rows());
	
	mySubmissionTable.rows().deselect();

	$("#sTaskSubmissionId").val(data.taskSubmissionId);                           		
	
	$("#sTaskRevisionId").val($("#subPanelTaskRevisionId").val());  //$("#sTaskRevisionId").val(data.taskRevisionId);   
	
	$("#sSubmissionNumber").val(data.submissionNumber);          			          			
	$("#submissionDate").datepicker("setDate", data.submissionDate == null? timestampToString(new Date(), false) : new Date(data.submissionDate)); //14 			

	$("#sComment").val(data.comment);
	$("#sFeedback").val(data.feedback);
	
//	console.log(data);
	
	$("#sAccepted").prop("checked", data.acceptedflag == "Y");   //Y means accepted, N means not accepted
	$("#sRejected").prop("checked", data.acceptedflag == "R");   //R means rejected
	
                   			            
	showModalDialog("submissionDialog");

	if (enabled == false) {
		getNextSubmissionNumber();
		document.getElementById("submissionDate").disabled = false;
		document.getElementById("sComment").disabled = false;
	} else {
		if ((data.acceptedflag == 'Y') || (data.acceptedflag == 'R') || (TaskKompleet == true)) {
			document.getElementById("submissionDate").disabled = true;
			document.getElementById("sComment").disabled = true;
		} else {
			document.getElementById("submissionDate").disabled = false;
			document.getElementById("sComment").disabled = false;
		}
	}

	var revRevisionNumber = jQuery('#revRevisionNumber').attr('value');
	var revMaxRevision = jQuery('#revMaxRevision').attr('value');
	
	if (revRevisionNumber < revMaxRevision) {
		document.getElementById("submissionDate").disabled = true;
		document.getElementById("sComment").disabled = true;
//		console.log("kleiner een")
	}	
	
	document.getElementById("blouSaveSubmissionButton").disabled = true;
//	setAdditionalTabStates(enabled);
//	setElementEnabled("saveGeneralTabButton", false);
//	somethingChangedGeneralTab = false;
//	askToSaveTab = false;

}
//editSubmission -- End


function getNextSubmissionNumber() {
	
	var queryUrl;
	var taskRevisionId = $("#subPanelTaskRevisionId").val()
	
	queryUrl = "/rest/ignite/v1/task-submission/next-submission-number/" + taskRevisionId;
	
//	console.log("/////rest/ignite/v1/task-submission/next-submission-number/" + taskRevisionId);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#sSubmissionNumber").val(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
	var nextNumber = document.getElementById('sSubmissionNumber').value;	

	if ((nextNumber == null) || (nextNumber == "")) {
//		console.log("nextNumberrr: " + nextNumber);
		document.getElementById("sSubmissionNumber").value = '1'; 
//		console.log(document.getElementById("sSubmissionNumber").value);
	}
};


function findMaxRevision(taskId) {
	
	var queryUrl;
	
	queryUrl = "/rest/ignite/v1/task/max-revision/" + taskId;
	
//	console.log("/////    rest/ignite/v1/task/max-revision/" + taskId);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#revMaxRevision").val(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
};


//submissionChanged -- Start
function submissionChanged() {
//	currentTab = "Tasks";
	askToSaveTab = true;
	somethingChangedTaskTab = true;
//	setElementEnabled("blouSaveTaskTabButton", true);
	
	document.getElementById("blouSaveSubmissionButton").disabled = false;
}
//submissionChanged -- End



function saveSubmissionDialog() {

	var nextNumber = document.getElementById('sSubmissionNumber').value;	

	if ((nextNumber == null) || (nextNumber == "")) {
		document.getElementById("sSubmissionNumber").value = '1'; 
	}	
	
	var tSid = document.getElementById("sTaskSubmissionId").value;
	if ((tSid == null) || (tSid == "")) {
		var postUrl = "/rest/ignite/v1/task-submission/new";
	} else {
		var postUrl = "/rest/ignite/v1/task-submission";
	}
	
console.log(postUrl);

		var postData = {
			taskSubmissionId: $("#sTaskSubmissionId").val(),
			taskRevisionId: $("#sTaskRevisionId").val(),
			submissionNumber: $("#sSubmissionNumber").val(),
			submissionDate: getMsFromDatePicker("submissionDate"),
			acceptedflag : "N",
			comment: $("#sComment").val(),
			feedback: $("#sFeedback").val(),
	};

	var errors = "";
	
	// validate

	
	
	if ((postData.comment == null) || (postData.comment == "")) {
		errors += "Please add a Comment.<br>";
	}
	
	if ((postData.submissionDate == null) || (postData.submissionDate == "")) {
		// Add Today's date without time
		$("#submissionDate").datepicker("setDate", postData.submissionDate == null? timestampToString(new Date(), false) : new Date(postData.submissionDate));
		postData.submissionDate = getMsFromDatePicker("submissionDate");
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "submissionDialog", "The Submission has been saved.", mySubmissionTable);
}
//saveSubmissionDialog -- End







// saveGeneralTab -- Start
function saveGeneralTab() {
	var postUrl = "/rest/ignite/v1/participant/new";
	var postData = {
			participantId : $("#epParticipantId").val(),
			participantTypeCode : $("#epParticipantTypeCode").val(),
			tapSubscriptionCode : $("#epTapSubscriptionCode").val(),
			representativeIndividualId : $("#epRepresentativeIndividualId").val(),
			marketingIndividualId : $("#epMarketingIndividualId").val(),
			participantOfficeIdDefault : $("#epParticipantOfficeIdDefault").val(),
			participantBankDetailsIdDefault : $("#epParticipantBankDetailsIdDefault").val(),
			registeredName : $("#epRegisteredName").val(),
			tradingName : $("#epTradingName").val(),
			systemName : $("#epSystemName").val(),
			companyRegistrationNumber : $("#epCompanyRegistrationNumber").val(),
			vatNumber : $("#epVATNumber").val(),
			latestProjectNumber : $("#epLatestProjectNumber").val(),
			defaultInvoiceDay : $("#epDefaultInvoiceDay").val(),
			isIndividual : "N"
	};

	var errors = "";

	if ((postData.registeredName == null) || (postData.registeredName == "")) {
		errors += "A Registered Name is required<br>";
	}

	if ((postData.tradingName == null) || (postData.tradingName == "")) {
		errors += "A Trading Name is required<br>";
	}

	if ((postData.systemName == null) || (postData.systemName == "")) {
		errors += "A System Name is required<br>";
	}

	if ((postData.participantTypeCode == null) || (postData.participantTypeCode == "")) {
		errors += "A Participant Type is required<br>";
	}
	
//	if ((postData.tapSubscriptionCode == null) || (postData.tapSubscriptionCode == "")) {
//		errors += "A TAP Subscription Code is required<br>";
//	}
	if (showFormErrors("epDlgErrorDiv", errors)) {
		return;
	}


	if ((postData.participantId != null) && (postData.participantId != "")) {
		var postUrl = "/rest/ignite/v1/participant";
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, null, "The General Participant information has been saved.", myAssignmentTable, function(request, response){
		var data = response;
		var participantId = data.participantId;
		$("#epParticipantId").val(data.participantId);
		
		$("#epParticipationDialogHeader").html("Participant Detail: " + data.registeredName);

		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful

		// Set the Save Button to disabled
		setElementEnabled("saveGeneralTabButton", false);
		somethingChangedGeneralTab = false;
		askToSaveTab = false;
	});
}
// saveGeneral -- End


function editSelectMarketingIndividual() {
	selectMarketingIndividual("epMarketingIndividualId", "epMarketingIndividualName");
	generalTabChanged();
}

function editSelectRepresentativeIndividual() {
	selectRepresentativeIndividual("epRepresentativeIndividualId", "epRepresentativeIndividualName");
	generalTabChanged();
}

function selectMarketingIndividual(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="marketingIndividualId";
			var refColumnName="individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" }
			];		
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.individualId;
				var markName = row.firstName + " " + row.lastName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(markName);
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

function selectRepresentativeIndividual(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="representativeIndividualId";
			var refColumnName="individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.individualId;
				var repName = row.firstName + " " + row.lastName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
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

function setAdditionalTabStates(enabled) {
//	setDivVisibility("epOfficeAndContactPointsTabLink", enabled? "block":"none");
//	setDivVisibility("epBankDetailsTabLink", enabled? "block":"none");
//	setDivVisibility("epSupportServicesTabLink", enabled? "block":"none");
//	setDivVisibility("epOSDTabLink", enabled? "block":"none");
//	setDivVisibility("epResourceRemunerationTabLink", enabled? "block":"none");
//	setDivVisibility("epResourceCompetencyTabLink", enabled? "block":"none");
//
//	setActiveTab("generalLink");
}
// editParticipant - End

//*********** Participant Table and General info *********** //
//****************************************************************** //


//****************************************************************** //
//*********** Office and Contact points *********** //
//initializeOfficeTab -- Start
function initializeOfficeTab(participantId) {
	var queryUrl="/rest/ignite/v1/participant-office/" + participantId;
	
	var columnsArray = [
		{ data: "participantOfficeId" },
		{ data: "participantId" },
		{ data: "name" },
		{ data: "description" },
		{ data: "costPerSeat" },
		{ data: "dateFrom" },
		{ data: "contactPointIdDefault" },
		{ data: "" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 5, 6]
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = timestampToString(data, false);
				}
				
				return html;
			},
			//width: "100px",
			targets: 4
		},
		{
			className: "dt-right",
			targets:4
		},
		{
			render: function (data, type, row){
				var name = "";
				if (row.hasOwnProperty("contactPointDefault")){
					if (row.contactPointDefault != null){
						var cp = row.contactPointDefault;
						name = cp.contactPointName;
					}
				}
				return name;
			},
			targets: [7]
		},
		
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editParticipantOffice(null);
			}
		},
		{
			attr: {
				id: "promptDeletepoBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteParticipantOffice();
			}
		}
	];

	participantOfficeTable = initializeGenericTable("participantOfficeTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editParticipantOffice(rowSelector);  //Double click
										}
	);
	
	participantOfficeTable.off('deselect')
	participantOfficeTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyContactPointPanel();
		updatePoToolbarButtons();
	} );

	participantOfficeTable.off('select')
	participantOfficeTable.on('select', function (e, dt, type, indexes) {
		showContactPointTable(dt.data());
		updatePoToolbarButtons();
	} );	

	updatePoToolbarButtons();

}
//initializeOfficeTab -- End


function updatePoToolbarButtons() {
	var hasSelected = participantOfficeTable.rows('.selected').data().length > 0;

	setTableButtonState(participantOfficeTable, "promptDeletepoBtn", hasSelected);	
}
	
function promptDeleteParticipantOffice() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Participant Office?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteParticipantOffice(participantOfficeTable);
			   }
	);
}

function deleteParticipantOffice(tbl) {
	var postUrl = "/rest/ignite/v1/participant-office/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Participant Office has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updatePoToolbarButtons();
			});
	
}
	
function showEmptyContactPointPanel() {
	setDivVisibility("contactPointEmptyPanel", "block");
	setDivVisibility("contactPointPanel", "none");
}
	
// showContactPointTable -- Start
function showContactPointTable(row) {
	if (row == null) {
		return;
	}
		
	var participantOfficeId = row.participantOfficeId;
	var url =  "/rest/ignite/v1/contact-point/office/" + participantOfficeId;

	var columnsArray = [
	                    
		{data:"contactPointId"},
		{data:"participantOfficeId"},
		{data:"contactPointName"},
		{data:"startDate"},
		{data:"emailAddress"}

//	hier kort iets 
		];
		
	var columnDefsArray = [
		{visible:false, targets:[0,1,3,5,6]},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = timestampToString(data, false);
				}
				
				return html;
			},
			//width: "100px",
			targets: 3
		},
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editContactPoint(null, participantOfficeId);
			}
		},
		{
			attr: {
				id: "promptDeleteCPBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteContactPoint();
			}
		}
	];
	
	setDivVisibility("contactPointEmptyPanel", "none");
	setDivVisibility("contactPointPanel", "block");
	
	contactPointTable = initializeGenericTable("contactPointTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editContactPoint(rowSelector, participantOfficeId);
										},
										null,
										10,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	contactPointTable.off('deselect')
	contactPointTable.on('deselect', function (e, dt, type, indexes) {
		updateCPToolbarButtons();
	} );

	contactPointTable.off('select')
	contactPointTable.on('select', function (e, dt, type, indexes) {
		updateCPToolbarButtons();
	} );	

	updateCPToolbarButtons();
}
// showContactPointTable -- End

// editParticipantOffice -- Start
function editParticipantOffice(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = participantOfficeTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
	}
	participantOfficeTable.rows().deselect();
	
	$("#epoParticipantOfficeId").val(data.participantOfficeId);
	$("#epoName").val(data.name);
	$("#epoDescription").val(data.description);
	$("#epoStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
	//$("#epoEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	$("#epoCostPerSeat").val(data.costPerSeat);
	
	if ((data.participantOfficeId != null) && (data.participantOfficeId != "")) {
		populateSelect("epoContactPointIdDefault",
			       "/rest/ignite/v1/contact-point/office/" + data.participantOfficeId,
			       "contactPointId",
			       "contactPointName",
			       data.contactPointIdDefault,
			       true,
			       null
		);
	}
	
	// Set the Save Button to disabled
	setElementEnabled("saveParticipantOfficeButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("participantOfficeDialog");
}
// editParticipantOffice -- End

// saveParticipantOffice -- Begin
function saveParticipantOffice() {
	var postUrl = "/rest/ignite/v1/participant-office/new";
	var postData = {
			participantId: $("#epParticipantId").val(),
			participantOfficeId: $("#epoParticipantOfficeId").val(),
			name: $("#epoName").val(),
			description: $("#epoDescription").val(),
			costPerSeat: $("#epoCostPerSeat").val(),
			dateFrom: getMsFromDatePicker("epoStartDate"),
			contactPointIdDefault:$("#epoContactPointIdDefault").val()
	};

	var errors = "";

	// validate
	if (postData.name == "") {
		errors += "An Office Name is required<br>";
	}

	if (showFormErrors("epoDlgErrorDiv", errors)) {
		return;
	}

	showEmptyContactPointPanel();
	if ((postData.participantOfficeId != null) && (postData.participantOfficeId != "")) {
		var postUrl = "/rest/ignite/v1/participant-office";
		
	}
	saveEntry(postUrl, postData, "participantOfficeDialog", "The Participant Office has been saved.", participantOfficeTable);
	//selectRowThatWasEdited(participantOfficeTable, $("#epoParticipantOfficeId").val());

}
// saveParticipantOffice -- End

// updateCPToolbarButtons -- Start
function updateCPToolbarButtons() {
	var hasSelected = contactPointTable.rows('.selected').data().length > 0;

	setTableButtonState(contactPointTable, "promptDeleteCPBtn", hasSelected);	
}
//updateCPToolbarButtons -- End

// promptDeleteContactPoint -- Start
function promptDeleteContactPoint() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Contact Point?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteContactPoint(contactPointTable);
			   }
	);
}
//promptDeleteContactPoint -- End

// deleteContactPoint -- Start
function deleteContactPoint(tbl) {
	var postUrl = "/rest/ignite/v1/contact-point/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Contact Point has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateCPToolbarButtons();
			});
}
//deleteContactPoint -- Start

// editContactPoint-- Start
function editContactPoint(rowSelector, participantOfficeId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = contactPointTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((participantOfficeId == undefined) || (participantOfficeId == null)){
			participantOfficeId = data.participantOfficeId;
		}
	}
	contactPointTable.rows().deselect();
	
	$("#eCPContactPointId").val(data.contactPointId);
	$("#eCPParticipantOfficeId").val(participantOfficeId);
	$("#eCPContactPointName").val(data.contactPointName);

	$("#eCPEmailAddress").val(data.emailAddress);
	
//  hier kort iets
	
	// Set the Save Button to disabled
	setElementEnabled("saveOfficeContactPointBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("contactPointDialog");
}
// editContactPoint -- End

// saveOfficeContactPoint -- Begin

function saveOfficeContactPoint() {
	var postUrl = "/rest/ignite/v1/contact-point/new";
	
	var postData = {
		contactPointId: $("#eCPContactPointId").val(),
		participantOfficeId: $("#eCPParticipantOfficeId").val(),
		contactPointName: $("#eCPContactPointName").val(),

		emailAddress:$("#eCPEmailAddress").val(), 
		
// hier kort iets		
		
		};

	var errors = "";
	
	// validate
	// Was any contact point data filled in?
	if ((postData.contactPointName != "")  || (postData.emailAddress != "") || (postData.phoneNumber != "") ) {

		if (postData.contactPointName =="") {
			errors += "A Contact Point Name is required<br>";
		}
		if (postData.startDate == null) {
			// Add Today's date without time
			$("#eCPStartDate").val(timestampToString(new Date(), false));
			postData.startDate = getMsFromDatePicker("eCPStartDate");
		}
	}
	
	if (showFormErrors("eCPDlgErrorDiv", errors)) {
		return;
	}

	//Does record exist? 

	if ((postData.contactPointId != null) && (postData.contactPointId != "")) {  
            // This is an update 
    	postUrl = "/rest/ignite/v1/contact-point";
     }

	saveEntry(postUrl, postData, "contactPointDialog", "The Office Contact Point has been saved.", contactPointTable);
}
//saveOfficeContactPoint -- End
//*********** Office and Contact points *********** //
//****************************************************************** //

//****************************************************************** //
//*********** Support Services *********** //

//initializeParticipantSupportServices Start
function initializeParticipantSupportServices(){
	var participantId = $("#epParticipantId").val();
	var queryUrl="/rest/ignite/v1/support-service/list/" + participantId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			var html = "";
			var index = 0;
			var mustOpen = true;
			var mustClose = false;
			var lastLevel = 0;

			if (data.hasOwnProperty("supportServices")) {
				data = data.supportServices;
			}

			$("#essSupportServicesCount").val(data.length);
			while (index < data.length) {
				var record = data[index];
				var inputId = "iSs_" + (index + 1);

				if (mustClose) {
					html += "</div>";
					mustClose = false;
				}

				if (record.hasChildren == "Y") {
					html += "<div class='row'><div class='offset-md-1 col-md-3'><b>" + record.displayName + "</b></div></div>";
					lastLevel = record.level;
					
					mustOpen = true;
					mustClose = false;
				} else {
					if (record.level != lastLevel) {
						html += "</div>";
						mustClose = false;
						mustOpen = true;

						lastLevel = record.level;
						
						continue;  // move back and process again to add detail row
					}
				
					if (mustOpen) {
						html += "<div class='row'><div class='offset-md-1'></div>";
						mustClose = true;
					}
					
					var indent = "col-md-6";
					if (lastLevel > 0) {
						indent = "offset-md-1 col-md-8";
					}
					
					var checked = "";
					if (record.isUsed == "Y") {
						checked = " checked";
					}
					
					html += "<div class='" + indent + "'>" +
					         "    <input type='hidden' id='" + inputId + "_Code' value='" + record.supportServiceCode + "'>" +
			                 "    <input type='checkbox' id='" + inputId + "'" + checked + " onchange=\"supportServiceTabChanged()\">" +
			                 "    <label class='col-form-label' for='" + inputId + "'>" + record.displayName + "</label>" +
			                 "</div>";	
				}

				index++;
			}
			
			if (mustClose) {
				html += "</div>";
				mustClose = false;
			}
				
		    $("#essSupportServicesPanel").html(html);
		},
		
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}
//initializeParticipantSupportServices End

//saveSupportServicesTab Start

function saveSupportServicesTab() {
	var postUrl = "/rest/ignite/v1/participant/support-services";
	
	var postData = {
		participantId: $("#epParticipantId").val(),
	};
	
	// we push the dynamic data, such as Support services, into the postData object
	var ssCount = $("#essSupportServicesCount").val();
	
	var ssSelected = [];
	var hasSs = false;  //Use in the validation - Are any Support Service Disciplines selected?

	// Note: the components start at id 1, not 0, so we cater for this in our for statement
	for (var foo = 1; foo <= ssCount; foo++) {
		var code = $("#iSs_" + foo + "_Code").val();
		var checked = $("#iSs_" + foo).is(":checked") ? "Y" : "N";
		if (checked == "Y") {
			hasSs = true;
		} 
		
		ssSelected.push({
			code: code,
			selected: checked
		});
	}
	postData.supportServicesCount = ssCount;
	postData.supportServicesSelected = ssSelected;
	
	var errors = "";

	// validate - Nothing at the moment
	// They can select no services at this stage
	// If we want to ensure that they have a iSs selected, we can check the hasiSs
			
	if (showFormErrors("epDlgErrorDiv", errors)) {
		return;
	}
	
	saveEntry(postUrl, postData, null, "The Support Services have been saved.", myAssignmentTable);
	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	
	setElementEnabled("saveSSTabButton", false);
	somethingChangedSSTab = false;
	askToSaveTab = false;
}

//saveSupportServicesTab End
//*********** Support Services *********** //
//****************************************************************** //

//****************************************************************** //
//****************************** OSD Start ****************************** //
function initializeOSdTree() {
	var participantId = $("#epParticipantId").val();
	var url = "/rest/ignite/v1/osd/tree";  // /list/+ participantId; // Find the top level OSDLevel1
	
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			renderOSdTree(data);
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
//	e.children.push({
//	            			projectId: e.projectId,
//	            			name: FILES_TITLE,
//	            			nodeType: NODETYPE_FILES
//	            	});
//	e.children.push({
//	            			projectId: e.projectId,
//	            			name: RESOURCES_TITLE,
//	            			nodeType: NODETYPE_RESOURCES
//	            	});
}


function renderOSdTree(data) {
	participantId = $("#epParticipantId").val();
	data.forEach(e => addSupportingNodes(e));
	
	var tree = $("#osdTree").tree({
      data: data,
      showEmptyFolder: true,
      closedIcon: $('<i class="jqtree-icon far fa-folder"></i>'),
      openedIcon: $('<i class="jqtree-icon far fa-folder-open"></i>'),
      onCreateLi: function(node, $li, is_selected) {
      }
  });
	
	$("#osdTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#osdTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);

		    	doubleClickNode(event.node);
		    }
  );
	
	$("#osdTree").on(
		    'tree.click',
		    function(event) {
		    	event.click_event.preventDefault();
		    	//Do we need to save something?
		    	if ((currentTab == "OSD") && (somethingChangedOSDTab) && (askToSaveTab)) {
					var stayAndSaveFirst = confirm("You did not save your Competency and Role changes. Do you want to check your changes and save?");
					if (stayAndSaveFirst) {
						//event.preventDefault();
						// Even if user does not save his/her work, we will not ask only once after a change.
						askToSaveTab = false;
					} 
				} else {
					showNode(event.node); 
				}
		    }
  );
	
	openAllNodes($("#osdTree").tree("getTree").children);
	selectFirstNode();
	
	return tree;
};

function removeNodeChildren(elementId, node) {
  if (node.children) {
      for (var i=node.children.length-1; i >= 0; i--) {
          var child = node.children[i];
          $("#" + elementId).tree('removeNode', child);
      }
  }
}

function openAllNodes(nodes) {
	
	for (var foo = 0; foo < nodes.length; foo++) {
		node = nodes[foo];
		$("#osdTree").tree("openNode", node);
		if (node.hasOwnProperty("children")) {
			if (node.children.length >0) {
				openAllNodes(node.children);
			}
		}
	}
}

function doubleClickNode(node) {
	var tree = $("#osdTree").tree();

	clearTextSelection();
  tree.tree("selectNode", node);
  
  if (node.is_open) {
  	// only show the node if its opened
  	showNode(node);
  }
}
function selectFirstNode() {
	var data = $("#osdTree").tree("getTree");

	if (data.hasOwnProperty("children")) {
		if (data.children.length > 0) {
			var node = data.children[0];
			doubleClickNode(node);
		}
	}
}

function showNode(node) {
	var path = "";

//	// initialize our current osd
//	currentOSD = {
//			rowNumber: null,
//			level: null,
//			osdCode: null,
//			osdName: null,
//			osdCodeParent: null,
//			competencyLevelId: null
//	};
	
	level1OSD = {
			rowNumber: null,
			level: null,
			osdCode: null,
			osdName: null,
			osdCodeParent: null,
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
	currentOSD = n;
	
	var l1Name = "";
	var l2Name = "";
	var l3Name = "";
	
	// walk backwards through the tree
	while (n.parent != null) {
		if (path != "") {
			path = " &gt; " + path;
		}
		
		// find parent details
		if (n.level == "2") {
			l1Name = n.parent.name;
		}
		if (n.level == "3") {
			l2Name = n.parent.name;
		}
		if (n.level == "4") {
			l3Name = n.parent.name;
		}
	
		path = n.name + path;
		n = n.parent;
	}

	//$("#osdHeader").html(path);
	loadLevelData(data, l1Name, l2Name, l3Name);
	loadRolesForLevel(data);
	
	setDivVisibility("level1Panel", data.level == "1" ? "block" : "none");
	setDivVisibility("level2Panel", data.level == "2" ? "block" : "none");
	setDivVisibility("level3Panel", data.level == "3" ? "block" : "none");
	setDivVisibility("level4Panel", data.level == "4" ? "block" : "none");
	
	somethingChangedInTab = false;
	askToSaveTab = false;
}



function loadLevelData(currentNode, l1Name, l2Name, l3Name) {
	participantId = $("#epParticipantId").val();
	queryUrl = "/rest/ignite/v1/osd/tree/participant-linked/" + participantId + "/" + currentNode.osdCode + "/" + currentNode.level;
	
	// set our initial state
	var compLevelCode = null;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#eParticipantOSDId").val(data.participantOSDId);
			if (data.hasOwnProperty("competencyLevel")) {
				compLevelCode = data.competencyLevelId;
			}
			if (currentNode.level == 1){
				$("#osdLevel1Code").val(currentNode.osdCode);
				$("#osdLevel1Name").val(currentNode.osdName);
				$("#osdLevel1").val(currentNode.name);
				
				populateSelect("osdLevel1CompetencyLevel", 
					       "/rest/ignite/v1/competency-level", 
					       "competencyLevelId", 
					       "name", 
					       compLevelCode, 
					       true,
					       null
			    );
			}
			if (currentNode.level == 2){
				$("#osdL2Level1").val(l1Name);
				$("#osdLevel2Code").val(currentNode.osdCode);
				$("#osdLevel2Name").val(currentNode.osdName);
				$("#osdLevel2").val(currentNode.name);

				populateSelect("osdLevel2CompetencyLevel", 
					       "/rest/ignite/v1/competency-level", 
					       "competencyLevelId", 
					       "name", 
					       compLevelCode, 
					       true,
					       null
			    );
			}
			if (currentNode.level == 3){
				$("#osdL3Level1").val(l1Name);
				$("#osdL3Level2").val(l2Name);

				$("#osdLevel3Code").val(currentNode.osdCode);
				$("#osdLevel3Name").val(currentNode.osdName);
				$("#osdLevel3").val(currentNode.name);
				
				populateSelect("osdLevel3CompetencyLevel", 
					       "/rest/ignite/v1/competency-level", 
					       "competencyLevelId", 
					       "name", 
					       compLevelCode, 
					       true,
					       null
			    );
			}
			if (currentNode.level == 4){
				$("#osdL4Level1").val(l1Name);
				$("#osdL4Level2").val(l2Name);
				$("#osdL4Level3").val(l3Name);

				$("#osdLevel4Code").val(currentNode.osdCode);
				$("#osdLevel4Name").val(currentNode.osdName);
				$("#osdLevel4").val(currentNode.name);

				populateSelect("osdLevel4CompetencyLevel", 
					       "/rest/ignite/v1/competency-level", 
					       "competencyLevelId", 
					       "name", 
					       compLevelCode, 
					       true,
					       null
			    );
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}


//****************************** OSD End ****************************** //

//*********** Roles Start *********** //

//loadRolesForLevel Start
function loadRolesForLevel(currentNode){
	var participantId = $("#epParticipantId").val();
	var queryUrl="/rest/ignite/v1/participant-osd-role/list/" + participantId + "/" + currentNode.level + "/" + currentNode.osdCode;
	var osdLevel = currentNode.level;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			var html = "";
			var index = 0;
			var mustOpen = true;
			var mustClose = false;
			var lastLevel = 0;

			if (data.hasOwnProperty("roleOnAProjectUsed")) {
				data = data.roleOnAProjectUsed;
			}

			if (osdLevel == 1) {
				$("#l1RolesCount").val(data.length);
			}
			if (osdLevel == 2) {
				$("#l2RolesCount").val(data.length);
			}
			if (osdLevel == 3) {
				$("#l3RolesCount").val(data.length);
			}
			if (osdLevel == 4) {
				$("#l4RolesCount").val(data.length);
			}
			
			while (index < data.length) {
				var record = data[index];
				var inputId = "iOsd_Level"  +  osdLevel + "_" + (index + 1);

				if (mustClose) {
					html += "</div>";
					mustClose = false;
				}

				if (record.hasChildren == "Y") {
					html += "<div class='row'><div class='offset-md-1 col-md-3'><b>" + record.name + "</b></div></div>";
					lastLevel = record.level;
					
					mustOpen = true;
					mustClose = false;
				} else {
					if (record.level != lastLevel) {
						html += "</div>";
						mustClose = false;
						mustOpen = true;

						lastLevel = record.level;
						
						continue;  // move back and process again to add detail row
					}
				
					if (mustOpen) {
						html += "<div class='row'><div class='offset-md-1'></div>";
						mustClose = true;
					}
					
					var indent = "offset-md-2 col-md-6";
					if (lastLevel > 0) {
						indent = "offset-md-1 col-md-8";
					}
					
					var checked = "";
					if (record.isUsed == "Y") {
						checked = " checked";
					}
					
					html += "<div class='" + indent + "'>" +
					         "    <input type='hidden' id='" + inputId + "_Code' value='" + record.roleOnAProjectId + "'>" +
			                 "    <input type='checkbox' id='" + inputId + "'" + checked + " onchange=\"level" +  osdLevel + "Changed()\">" +
			                 "    <label class='col-form-label' for='" + inputId + "'>" + record.name + "</label>" +
			                 "</div>";	
				}

				index++;
			}
			
			if (mustClose) {
				html += "</div>";
				mustClose = false;
			}

			if (osdLevel == 1) {
			    $("#osdLevel1Roles").html(html);
				setElementEnabled("saveLevel1Button", false);
			}
			if (osdLevel == 2) {
			    $("#osdLevel2Roles").html(html);
				setElementEnabled("saveLevel2Button", false);
			}
			if (osdLevel == 3) {
			    $("#osdLevel3Roles").html(html);
				setElementEnabled("saveLevel3Button", false);
}
			if (osdLevel == 4) {
			    $("#osdLevel4Roles").html(html);
				setElementEnabled("saveLevel4Button", false);
			}
		},
		
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});

}
//initializeRoles End

//saveRoles Start

function saveOSDLevel1() {
	var levelCode =$("#osdLevel1Code").val();
	// saveLevel1OSD();
	competencyLevelId =  $("#osdLevel1CompetencyLevel").val(),
	saveRoles(1, levelCode, competencyLevelId); 

	setElementEnabled("saveLevel1Button", false);
	somethingChangedOSDTab = false;
	askToSaveTab = false;
}
function saveOSDLevel2() {
	var levelCode =$("#osdLevel2Code").val();
	saveRoles(2, levelCode); 

	somethingChangedOSDTab = false;
	askToSaveTab = false;
}
function saveOSDLevel3() {
	var levelCode =$("#osdLevel3Code").val();
	saveRoles(3, levelCode); 

	somethingChangedOSDTab = false;
	askToSaveTab = false;
}
function saveOSDLevel4() {
	var levelCode =$("#osdLevel4Code").val();
	saveRoles(4, levelCode); 

	somethingChangedOSDTab = false;
	askToSaveTab = false;
}

function saveRoles(osdLevel, osdCode) {
	var postUrl = "/rest/ignite/v1/participant-osd-role/";
	var compLevelCode = null;
	var osdCount = null;
	
	
	var postData = {
		participantId: $("#epParticipantId").val(),
		osdLevel: osdLevel,
		osdCode: osdCode,
		competencyLevelId: compLevelCode
	};
	
	// we push the dynamic data, such as Support services, into the postData object
	
	if (osdLevel == 1) {
		compLevelCode = $("#osdLevel1CompetencyLevel").val();
		osdCount = $("#l1RolesCount").val();
	}
	if (osdLevel == 2) {
		compLevelCode = $("#osdLevel2CompetencyLevel").val();
		osdCount = $("#l2RolesCount").val();
	}
	if (osdLevel == 3) {
		compLevelCode = $("#osdLevel3CompetencyLevel").val();
		osdCount = $("#l3RolesCount").val();
	}
	if (osdLevel == 4) {
		compLevelCode = $("#osdLevel4CompetencyLevel").val();
		osdCount = $("#l4RolesCount").val();
	}
	var osdSelected = [];
	var hasOsd = false;  //Use in the validation - Are any Support Service Disciplines selected?

	// Note: the components start at id 1, not 0, so we cater for this in our for statement
	for (var foo = 1; foo <= osdCount; foo++) {
		var code = $("#iOsd_Level"  +  osdLevel + "_" + foo + "_Code").val();
		var checked = $("#iOsd_Level"  +  osdLevel + "_" + foo).is(":checked") ? "Y" : "N";
		if (checked == "Y") {
			hasOsd = true;
		} 
		
		osdSelected.push({
			code: code,
			selected: checked
		});
	}
	postData.osdCount = osdCount;
	postData.osdSelected = osdSelected;
	postData.competencyLevelId = compLevelCode;
	
	var errors = "";

	// validate 
	if (hasOsd) {
		if ((postData.competencyLevelId == null) || (postData.competencyLevelId == "")) {
			errors += "A Competency Level is required<br>";
		}
	} 
	else {
		if ((postData.competencyLevelId != null) && (postData.competencyLevelId != "")) {
			errors += "One or more Roles are required<br>";
		}
	}
		

	if (showFormErrors("eOSDDlgErrorDiv", errors)) {
		return;
	}

	if (hasOsd) {
		saveEntry(postUrl, postData, null, "Level " + osdLevel + " Role details have been saved.");
		// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	} else {
		var participantOSDId = $("#eParticipantOSDId").val();
		if ((participantOSDId != null) && (participantOSDId != "")) {
			// Delete the participantOSD record, because, no roles or competency level linked to it
			postUrl = "/rest/ignite/v1/participant-osd/delete";
			saveEntry(postUrl, {"participantOSDId":participantOSDId}, null, "Associated Competency Level and Roles deleted");
		}
	}
}

//saveRoles End
//************************** Roles End *********************** //



//****************************************************************** //


//****************************************************************** //
//*********** Bank details *********** //
//initializeBankDetailsTab -- Start
function initializeBankDetailsTab(participantId) {
	var queryUrl="/rest/ignite/v1/participant-bank-details/participant/" + participantId; 

	var columnsArray = [
		{ data: "participantBankDetailsId" },
		{ data: "participantId" },
		{ data: "name" },
		{ data: "description" },
		{ data: ""},
		{ data: "branchId" },
		{ data: "bankAccountTypeId" },
		{ data: "accountHolderName" },
		{ data: "accountNumber" }
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3]
		},
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("branch")) {
					data = row.branch.bankId; 
				}
				return data;
			},
			targets: 4
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editParticipantBankDetails(null);
			}
		},
		{
			attr: {
				id: "promptDeleteBankDetailsBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteBankDetails();
			}
		}
	];
	
	participantBankDetailsTable = initializeGenericTable("participantBankDetailsTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editParticipantBankDetails(rowSelector);  //Double click
										}
	);

	participantBankDetailsTable.off('deselect');
	participantBankDetailsTable.on('deselect', function (e, dt, type, indexes) {
		updateBDToolbarButtons();
	} );

	participantBankDetailsTable.off('select');
	participantBankDetailsTable.on('select', function (e, dt, type, indexes) {
		updateBDToolbarButtons();
	} );	

	updateBDToolbarButtons();
}

//initializeBankDetailsTab -- End

//updateBDToolbarButtons -- Start
function updateBDToolbarButtons() {
	var hasSelected = participantBankDetailsTable.rows('.selected').data().length > 0;

	setTableButtonState(participantBankDetailsTable, "promptDeleteBankDetailsBtn", hasSelected);	
}
//updateBDToolbarButtons -- End

//promptDeleteBankDetails -- Start
function promptDeleteBankDetails() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Bank Details?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteBankDetails(participantBankDetailsTable);
			   }
	);
}
//promptDeleteBankDetails -- End

//deleteBankDetails -- Start
function deleteBankDetails(tbl) {
	var postUrl = "/rest/ignite/v1/participant-bank-details/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Bank Details has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateBDToolbarButtons();
			});
}
//deleteBankDetails -- Start

//editParticipantBankDetails-- Start
function editParticipantBankDetails(rowSelector, participantBankDetailsId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = participantBankDetailsTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((participantBankDetailsId == undefined) || (participantBankDetailsId == null)){
			participantBankDetailsId = data.participantBankDetailsId;
		}
	}
	participantBankDetailsTable.rows().deselect();
	
	$("#ebdParticipantBankDetailsId").val(participantBankDetailsId);
	$("#ebdParticipantBankDetailsName").val(data.name);
	$("#ebdParticipantBankDetailsDescription").val(data.description);
//	$("#ebdBankId").val(data.bankId);
//	$("#ebdBranchId").val(data.branchId);
//	$("#ebdBankAccountTypeId").val(data.bankAccountTypeId);
	$("#ebdAccountHolderName").val(data.accountHolderName);
	$("#ebdAccountNumber").val(data.accountNumber);
	$("#ebdBankId").val(null);
	$("#ebdBranchId").val(null);
	
	populateSelect("ebdBankId", 
		       "/rest/ignite/v1/bank", 
		       "bankId", 
		       "name", 
		       data.hasOwnProperty("branch") ? data.branch.bankId : null, 
		       true,
		       function() {
					var selectedBankId;
					
					if (data.hasOwnProperty("branch")) {
						selectedBankId = data.branch.bankId;
					} else {
						return;
					}
					
					populateSelect("ebdBranchId", 
				       "/rest/ignite/v1/bank/branch/" + selectedBankId, 
				       "branchId", 
				       "name", 
				       data.branchId, 
				       true,
				       null 
					);
		       }
	);

	populateSelect("ebdBankAccountTypeId", 
		       "/rest/ignite/v1/bank-account-type", 
		       "bankAccountTypeId", 
		       "name", 
		       data.bankAccountTypeId, 
		       true,
		       null
 );

	// Set the Save Button to disabled
	setElementEnabled("saveParticipantBDButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("maParticipantBankDetailsDialog");
}
//editParticipantBankDetails -- End



//saveParticipantBankDetails -- Begin
function saveParticipantBankDetails() {
	var postUrl = "/rest/ignite/v1/participant-bank-details/new";   // Daar is nou 'n procedure: "/rest/ignite/v1/participant-bank-details/new-participant-bank-details" -- Sien BankdetailsTab. (en een vir update)
	
	var postData = {	
			participantBankDetailsId : $("#ebdParticipantBankDetailsId").val(),
			participantId : $("#epParticipantId").val(),
			name : $("#ebdParticipantBankDetailsName").val(),
			description : $("#ebdParticipantBankDetailsDescription").val(),
			branchId : $("#ebdBranchId").val(),
			bankAccountTypeId : $("#ebdBankAccountTypeId").val(),
			accountHolderName : $("#ebdAccountHolderName").val(),
			accountNumber : $("#ebdAccountNumber").val()
		};

	var errors = "";

	var bankId = $("#ebdBankId").val();
	
	// Was any bank detail data filled in? If so, all fields must be completed
	if ((bankId != "" ) || 
			(postData.bankAccountTypeId != "") || 
			(postData.accountHolderName != "") || 
			(postData.accountNumber != "") || 
			(postData.name != "") || 
			(postData.description!="")) {
		if ((postData.name == "") ||  (postData.name == null)) {
			errors += "Please enter a Bank Details Name<br>";
		}
		if (bankId =="") {
			errors += "Please select a Bank<br>";
		}
		if ((postData.branchId == "") ||  (postData.branchId == null)) {
			postData.branchId = null;
		}
		if ((postData.bankAccountTypeId =="")  ||  (postData.bankAccountTypeId == null)) {
			postData.bankAccountTypeId = null
		}
		if (postData.accountHolderName =="") {
			errors += "Please enter an Account Holder Name<br>";
		}
		if (postData.accountNumber =="") {
			errors += "Please enter an Account Number<br>";
		}
		if (postData.participantBankDetailsName =="") {
			 $("#ebdParticipantBankDetailsName").val("Default Bank Details");
		}
	}
	
	if (showFormErrors("ebdDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.participantBankDetailsId != null) && (postData.participantBankDetailsId != "")) {  
        // This is an update 
		postUrl = "/rest/ignite/v1/participant-bank-details";
	}
	saveEntry(postUrl, postData, "maParticipantBankDetailsDialog", "The Participant Bank Details have been saved.", participantBankDetailsTable);
}
//saveParticipantBankDetails -- End

function selectBank(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/bank";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="name";
			var refColumnName="bankId";
			var columns = [
				{ data: "bankId", name: "Bank Code" },
				{ data: "name", name: "Bank Name" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.bankId;
				var bankName = row.bankId + " " + row.name;

				$("#" + targetId).val(bankId);
				$("#" + targetName).val(name);
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

function populateBankBranchesForEdit() {
	var selectedBankId = $("#ebdBankId").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  

	populateSelect("ebdBranchId", 
			       "/rest/ignite/v1/bank/branch/" + selectedBankId, 
			       "branchId", 
			       "name", 
			       null, 
			       true,
			       null 
	);
}

//*********** Bank details *********** //

//*********** Bank card *********** //

//initializeBankCardTable -- Start
function initializeBankCardTable(participantId) {

	var url =  "/rest/ignite/v1/bank-card/participant/" + participantId;
	console.log (url);
  
	var columnsArray = [
	                    
		{data:"bankCardId"},				//0
		{data:"participantId"},				//1
		{data:"participantBankDetailsId"},	//2
		{data:""},							//3
		{data:"cardTypeCode"},				//4
		{data:""},							//5
		{data:"cardNumber"},				//6
		{data:"nameOnCard"},				//7
		{data:"personalCard"},				//8
		{data:"description"}				//9
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0,1,2,4]
		},
		{
			render: function (data, type, row) {
				console.log(row.participantBankDetailsId);
				if (row.participantBankDetailsId == null) {
					return "";
				} else {
					var pbd = row.participantBankDetails;
					var txt = pbd.name;
					return txt;
				}
			},
			targets: 3
		},
		{
			render: function (data, type, row) {
				if (row.cardType == null) {
					return "";
				} else {
					var ct = row.cardType;
					var txt = ct.name;
					return txt;
				}
			},
			targets: 5
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editBankCard(null, participantId);
			}
		},
		{
			attr: {
				id: "promptDeleteBankCardBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteBankCard();
			}
		}
	];
	
	participantBankCardTable = initializeGenericTable("participantBankCardTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editBankCard(rowSelector, participantId);
										}
	);

	participantBankCardTable.off('deselect');
	participantBankCardTable.on('deselect', function (e, dt, type, indexes) {
		updateBankCardToolbarButtons();
	} );

	participantBankCardTable.off('select');
	participantBankCardTable.on('select', function (e, dt, type, indexes) {
		updateBankCardToolbarButtons();
	} );	

	updateBankCardToolbarButtons();
}
//showBankCardTable -- End

//updateBankCardToolbarButtons -- Start
function updateBankCardToolbarButtons() {
	var hasSelected = participantBankCardTable.rows('.selected').data().length > 0;

	setTableButtonState(participantBankCardTable, "promptDeleteBankCardBtn", hasSelected);	
}
//updateBankCardToolbarButtons -- End

//promptDeleteBankCard -- Start
function promptDeleteBankCard() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Bank Card?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteBankCard(participantBankCardTable);
			   }
	);
}
//promptDeleteBankCard -- End

//deleteBankCard -- Start
function deleteBankCard(tbl) {
	var postUrl = "/rest/ignite/v1/bank-card/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Bank Card has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateBankCardToolbarButtons();
			});
}
//deleteBankCard -- Start

//editBankCard -- Start
function editBankCard(rowSelector, participantId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = participantBankCardTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((participantId == undefined) || (participantId == null)){
			participantId = data.participantId;
		}
	}
	participantBankCardTable.rows().deselect();

	$("#bcBankCardId").val(data.bankCardId);
	$("#bcParticipantId").val(participantId);

	//$("#rrResourceRemunTypeId").val(data.resourceRemunTypeId);
	
	/* function populateSelect(
	elementId, html select element that will be populated 
	url, url where it must get the data (you can paste in browser window to see the data)
	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
	displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	*/	
	

	console.log("/rest/ignite/v1/participant-bank-details/participant/" + participantId);
	populateSelect("bcParticipantBankDetailsId", 
				"/rest/ignite/v1/participant-bank-details/participant/" + participantId,
		       "participantBankDetailsId", 
		       "name", 
		       data.participantBankDetailsId, 
		       true,
		       null 
	);

	populateSelect("bcCardTypeCode", 
		       "/rest/ignite/v1/card-type", 
		       "cardTypeCode", 
		       "name", 
		       data.cardTypeCode, 
		       true,
		       null 
	);
	
	$("#bcCardNumber").val(data.cardNumber);
	$("#bcNameOnCard").val(data.nameOnCard);

	$("#bcPersonalCard").prop("checked", data.personalCard == "Y");
	$("#bcDescription").val(data.description);
	
	// Set the Save Button to disabled
	setElementEnabled("saveBankCardBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("maBankCardDialog");
}
//editBankCard -- End

//saveBankCardDialog -- Begin

function saveBankCardDialog() {
	var postUrl = "/rest/ignite/v1/bank-card/new";
		var postData = {
			bankCardId: $("#bcBankCardId").val(),
			participantId: $("#bcParticipantId").val(),
			participantBankDetailsId: $("#bcParticipantBankDetailsId").val(),
			cardTypeCode: $("#bcCardTypeCode").val(),
			cardNumber: $("#bcCardNumber").val(),
			nameOnCard: $("#bcNameOnCard").val(),
			personalCard : $("#bcPersonalCard").is(":checked") ? "Y" : "N",
			description: $("#bcDescription").val(),
	};

	var errors = "";
	
	// validate

	if ((postData.cardTypeCode == null) || (postData.cardTypeCode == "")) {
		errors += "A Card Type is required.<br>";
	}

	if ((postData.cardNumber == null) || (postData.cardNumber == "")) {
		errors += "A Card Number is required.<br>";
	}
	if ((postData.nameOnCard == null) || (postData.nameOnCard == "")) {
		errors += "A Name on the Card is required.<br>";
	}
	
	if ((postData.description == null) || (postData.description == "")) {
		errors += "A Description is required.<br>";
	}

	if (showFormErrors("bcDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.bankCardId != null) && (postData.bankCardId != "")) {
		var postUrl = "/rest/ignite/v1/bank-card";
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "maBankCardDialog", "The Bank Card has been saved.", participantBankCardTable);
}
//saveBankCardDialog -- End

//Did anything change on the form?  Ask user to save or not.

//bankCardDialogChanged -- Start
function bankCardDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveBankCardBtn", true);
}
//bankCardDialogChanged -- Start


//closeBankCardDialog -- Start
function closeBankCardDialog() {
	
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your Bank Card changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
			//Moenie humanResourceDialog toemaak nie... save eers
		} else {
			closeModalDialog("maBankCardDialog");
		}
	} else  {
		closeModalDialog("maBankCardDialog");
	}
}
//closeBankCardDialog -- End

//****************************************************************** //



//****************************************************************** //
//*********** Resources *********** //
// ***************************************************************** //
//********************** Human Resource ********************** //
//initializeHumanResourceTable -- Start

function initializeHumanResourceTable(participantId){
	var queryUrl="/rest/ignite/v1/human-resource/" + participantId; 
	
	var columnsArray = [
		{ data: "humanResourceId" },		//0
		{ data: "participantIdPayer" },			//1
		{ data: "" },							//2
		{ data: "individualIdBeneficiary" },	//3
		{ data: "" },							//4
		{ data: "resourceTypeId" },			//5
		{ data: "" },							//6
		{ data: "description" },				//7
		{ data: "startDate" },					//8
		{ data: "endDate" }						//9
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2 , 3, 5, 7, 9]
		},
		{
			render: function (data, type, row) {
				if (row.participantIdPayer == null) {
					return "";
				} else {
					var e = row.participantPayer;
					var txt = e.systemName;
					
					return txt;
				}
			},
			targets: 2
		},
		{
			render: function (data, type, row) {
				if (row.individualIdBeneficiary == null) {
					return "";
				} else {
					var e = row.individualBeneficiary;
					var txt = e.firstName + " " + e.lastName;
					return txt;
				}
			},
			targets: 4
		},
		{
			render: function (data, type, row) {
				if (row.resourceTypeId == null) {
					return "";
				} else {
					var r = row.resourceType;
					var txt = r.name;
					
					return txt;
				}
			},
			targets: 6
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			//width: "100px",
			targets: [8, 9]
		}
		/*,
		{
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 9
		}
		*/
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editHumanResource(participantId, null);
			}
		},
		{
			attr: {
				id: "promptDeleteHumanResourceBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteHumanResource();
			}
		}
	];
	humanResourceTable = initializeGenericTable("humanResourceTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editHumanResource(participantId, aThis);
										}
	);

	humanResourceTable.off('deselect');
	humanResourceTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyRemunerationPanel();
		updateHumanResourceToolbarButtons();
		
	} );

	humanResourceTable.off('select');
	humanResourceTable.on('select', function (e, dt, type, indexes) {
		showRemunerationTable(dt.data());
		updateHumanResourceToolbarButtons();
	} );
	
	updateHumanResourceToolbarButtons();
}
//initializeHumanResourceTable -- Start


function promptDeleteHumanResource() {
showDialog("Confirm?",
	       "Are you sure that you wish to delete the selected Human Resource?",
	       DialogConstants.TYPE_CONFIRM, 
	       DialogConstants.ALERTTYPE_INFO, 
	       function () {
				deleteHumanResource(humanResourceTable);
		   }
);
}

function deleteHumanResource(tbl) {
var postUrl = "/rest/ignite/v1/human-resource/delete";
var row = tbl.rows('.selected').data()[0];

// Disable delete button when record has been deleted.
saveEntry(postUrl, row, null, "The Human Resource has been deleted.", tbl,
		function(){	
			tbl.rows().deselect();
			updateHumanResourceToolbarButtons();
		});
}

// editHumanResourceDialog -- Start
function editHumanResource(participantId, rowSelector) {
var data = {}; // Give it an empty object (so, need to add a new record)
var enabled = false;
var header = "Human Resource Detail";

if (rowSelector != null) {
	data = humanResourceTable.row(rowSelector).data();
	enabled = true;
}
humanResourceTable.rows().deselect();
//setActiveTab("generalLink");
setElementEnabled("saveHumanResourceButton", false); //Disable Save button when initializing

$("#erHumanResourceDialogHeader").html(header);

$("#erHumanResourceId").val(data.humanResourceId);
$("#erParticipantIdPayer").val(participantId);
$("#erParticipantNamePayer").val($("#epSystemName").val());

var benName = "";
if (data.hasOwnProperty("individualBeneficiary")) {
	if (data.individualIdBeneficiary != null) {
		b = data.individualBeneficiary;

		if (b.hasOwnProperty("individual")) {
			if (b.individual != null) {
				benName = data.individual.firstName + " " + data.individual.lastName;
			}
		}
	}
}
$("#erParticipantIdBeneficiary").val(data.participantIdBeneficiary);
$("#erIndividualNameBeneficiary").val(benName);

populateSelect("erResourceTypeId", 
	       "/rest/ignite/v1/resource-type", 
	       "resourceTypeId", 
	       "name", 
	       data.resourceTypeId, 
	       true,
	       null 
);
$("#erDescription").val(data.description);
$("#erStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
$("#erEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));

// Do not allow updates if there are Remunerations linked to this human resource
var hasRemunerationsLinked = false;

if (hasRemunerationsLinked) {
	setElementEnabled("erBeneficiarySearchBtn", false);
} else {
	setElementEnabled("erBeneficiarySearchBtn", true);
}

// Set the Save Button to disabled
setElementEnabled("saveHumanResourceButton", false);
somethingChangedInDialog = false;
askToSaveDialog = false;
showModalDialog("humanResourceDialog");
}
//humanResourceDialog -- End

//saveHumanResourceDialog -- Start
function saveHumanResourceDialog() {
var postUrl = "/rest/ignite/v1/human-resource/new";
var postData = {
		humanRresourceId : $("#erHumanResourceId").val(),
		participantIdPayer : $("#erParticipantIdPayer").val(),
		individualIdBeneficiary : $("#erIndividualIdBeneficiary").val(),
		resourceTypeId : $("#erResourceTypeId").val(),
		description : $("#erDescription").val(),
		startDate: getMsFromDatePicker("erStartDate"),
		endDate: getMsFromDatePicker("erEndDate")
};

var errors = "";

if ((postData.individualIdBeneficiary == null) || (postData.individualIdBeneficiary == "")) {
	errors += "A Beneficiary is required as the human resource<br>";
}

if ((postData.resourceTypeId == null) || (postData.resourceTypeId == "")) {
	errors += "A Resource Type is required<br>";
}

if (showFormErrors("erDlgErrorDiv", errors)) {
	return;
}

if ((postData.humanResourceId != null) && (postData.humanResourceId != "")) {
	var postUrl = "/rest/ignite/v1/humen-resource";
}
// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
saveEntry(postUrl, postData, "humanResourceDialog", "The Human Resource information has been saved.", humanResourceTable);
}
//saveHumanResourceId -- End

function editSelectBeneficiary() {
selectBeneficiary("erIndividualIdBeneficiary", "erIndividualNameBeneficiary");
humanResourceDialogChanged();
}

function selectBeneficiary(targetId, targetName) {
var queryUrl="/rest/ignite/v1/individual/list";
$.ajax({
	url: springUrl(queryUrl),
	type: "GET",
	success: function(data) {
	
		var columnName="erIndividualIdBeneficiary";
		var refColumnName="individualId";
		var columns = [
			{ data: "individualId", name: "Id" },
			{ data: "firstName", name: "First Name" },
			{ data: "lastName", name: "Last Name" }
		];
		var columnDefs = [
			{ 
				visible: false,
				targets: 0
				}
		];

		selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
			var id = row.individualId;
			var name = row.firstName + " " + row.lastName;

			$("#" + targetId).val(id);
			$("#" + targetName).val(name);
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

//********************** Human Resource ********************** //
//****************************************************************** //


// ***************************************************************** //
//******************* Resource Remunerations ******************* //

function showEmptyRemunerationPanel() {
	setDivVisibility("resourceRemunerationEmptyPanel", "block");
	setDivVisibility("resourceRemunerationPanel", "none");
}

function updateHumanResourceToolbarButtons() {
	var hasSelected = humanResourceTable.rows('.selected').data().length > 0;

	setTableButtonState(humanResourceTable, "promptDeleteHumanResourceBtn", hasSelected);	
}

//showResourceRemunerationTable -- Start
function showRemunerationTable(row) {
	if (row == null) {
		return;
	}
		
	var humanResourceId = row.humanResourceId;
	var url =  "/rest/ignite/v1/resource-remuneration/" + humanResourceId;
	console.log (url);
    
	var columnsArray = [
	                    
		{data:"resourceRemunerationId"},	//0
		{data:"humanResourceId"},	//1
		{data:"description"},				//2
		{data:"resourceRemunTypeId"},		//3
		{data:""},							//4
		{data:"startDate"},					//5
		{data:"endDate"},					//6
		{data:"amount"},					//7
		{data:"allowExpenseDeductions"}		//8
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0,1,3,6,8]
		},
		{
			render: function (data, type, row) {
				if (row.resourceRemunType == null) {
					return "";
				} else {
					var e = row.resourceRemunType;
					var txt = e.name;
					return txt;
				}
			},
			targets: 4
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			//width: "100px",
			targets: [5, 6]
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editResourceRemuneration(null, humanResourceId);
			}
		},
		{
			attr: {
				id: "promptDeleteResourceRemunerationBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteResourceRemuneration();
			}
		}
	];
	
	setDivVisibility("resourceRemunerationEmptyPanel", "none");
	setDivVisibility("resourceRemunerationPanel", "block");
	
	resourceRemunerationTable = initializeGenericTable("resourceRemunerationTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editResourceRemuneration(rowSelector, humanResourceId);
										},
										null,
										10,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	resourceRemunerationTable.off('deselect');
	resourceRemunerationTable.on('deselect', function (e, dt, type, indexes) {
		updateResourceRemunerationToolbarButtons();
	} );

	resourceRemunerationTable.off('select');
	resourceRemunerationTable.on('select', function (e, dt, type, indexes) {
		updateResourceRemunerationToolbarButtons();
	} );	

	updateResourceRemunerationToolbarButtons();
}
//showResourceRemunerationTable -- End

//updateResourceRemunerationToolbarButtons -- Start
function updateResourceRemunerationToolbarButtons() {
	var hasSelected = resourceRemunerationTable.rows('.selected').data().length > 0;

	setTableButtonState(resourceRemunerationTable, "promptDeleteResourceRemunerationBtn", hasSelected);	
}
//updateResourceRemunerationToolbarButtons -- End

//promptDeleteResourceRemuneration -- Start
function promptDeleteResourceRemuneration() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Resource Remuneration?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteResourceRemuneration(resourceRemunerationTable);
			   }
	);
}
//promptDeleteResourceRemuneration -- End

//deleteResourceRemuneration -- Start
function deleteResourceRemuneration(tbl) {
	var postUrl = "/rest/ignite/v1/resource-remuneration/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Resource Remuneration has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateResourceRemunerationToolbarButtons();
			});
}
//deleteResourceRemuneration -- Start

//editResourceRemuneration -- Start
function editResourceRemuneration(rowSelector, humanResourceId) {
	console.log("editResourceRemuneration");
	console.dir(humanResourceId);
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = resourceRemunerationTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		console.dir(data);

		if ((humanResourceId == undefined) || (humanResourceId == null)){
			humanResourceId = data.humanResourceId;
		}
	}
	resourceRemunerationTable.rows().deselect();
	
	$("#rrResourceRemunerationId").val(data.resourceRemunerationId);
	$("#rrHumanResourceId").val(humanResourceId);
	$("#rrDescription").val(data.description);
	
	/* function populateSelect(
	elementId, html select element that will be populated 
	url, url where it must get the data (you can paste in browser window to see the data)
	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
	displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	*/	
	$("#rrResourceRemunTypeId").val(data.resourceRemunTypeId);
	populateSelect("rrResourceRemunTypeId", 
		       "/rest/ignite/v1/resource-remun-type", 
		       "resourceRemunTypeId", 
		       "name", 
		       data.resourceRemunTypeId, 
		       true,
		       null 
	);
	
	$("#rrStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));
	$("#rrEndDate").datepicker("setDate", data.endDate == null? null : new Date(data.endDate));
	$("#rrAmount").val(data.amount);
	$("#rrAllowExpenseDeductions").prop("checked", data.allowExpenseDeductions == "Y");
	
	// Set the Save Button to disabled
	setElementEnabled("saveResourceRemunerationBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("resourceRemunerationDialog");
}
//editResourceRemunerationt -- End

//saveResourceRemunerationDialog -- Begin

function saveResourceRemunerationDialog() {
	var postUrl = "/rest/ignite/v1/resource-remuneration/new";
	
	var postData = {
			resourceRemunerationId: $("#rrResourceRemunerationId").val(),
			humanResourceId: $("#rrHumanResourceId").val(),
			description: $("#rrDescription").val(),
			resourceRemunTypeId: $("#rrResourceRemunTypeId").val(),
			startDate: getMsFromDatePicker("rrStartDate"),
			endDate: getMsFromDatePicker("rrEndDate"),
			amount:$("#rrAmount").val(), 
			allowExpenseDeductions : $("#rrAllowExpenseDeductions").is(":checked") ? "Y" : "N",
	};

	var errors = "";
	
	// validate
	// Was any contact point data filled in?
	if ((postData.description != "") || (postData.startDate != null)) {

		if (postData.startDate == null) {
			// Add Today's date without time
			$("#renStartDate").val(timestampToString(new Date(), false));
			postData.startDate = getMsFromDatePicker("renStartDate");
		}

		if ((postData.resourceRemunTypeId == null) || (postData.resourceRemunTypeId == "")) {
			errors += "A Remuneration Type is required.<br>";
		}
		
	}
	
	if (showFormErrors("rrCPDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.resourceRemunerationId != null) && (postData.resourceRemunerationId != "")) {
		var postUrl = "/rest/ignite/v1/resource-remuneration";
	}

	console.log(postUrl);
	console.dir(postData);
	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "resourceRemunerationDialog", "The Human Resource Remuneration has been saved.", resourceRemunerationTable);
}
//saveResourceRemunerationDialog -- End

//***************************************************************** //

//***********************************************************************
//Did anything change on the form?  Ask user to save or not.

// humanResourcepDialogChanged -- Start
function humanResourceDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveHumanResourceButton", true);
}
// humanResourceDialogChanged -- Start


//closeHumanResourceDialog -- Start
function closeHumanResourceDialog() {
	
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your Human Resource changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
			//Moenie humanResourceDialog toemaak nie... save eers
		} else {
			closeModalDialog("humanResourceDialog");
		}
	} else  {
		closeModalDialog("humanResourceDialog");
	}
}
// closeHumanResourceDialog -- End

//resourceRemunerationDialogChanged -- Start
function resourceRemunerationDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveResourceRemunerationBtn", true);
}
//resourceRemunerationDialogChanged -- Start

//closeResoureceRemunerationDialog -- Start
function closeResourceRemunerationDialog() {
	
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your Remuneration changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
			//Moenie resourceRemunerationDialog toemaak nie... save eers
		} else {
			closeModalDialog("resourceRemunerationDialog");
		}
	} else  {
		closeModalDialog("resourceRemunerationDialog");
	}
}
//resourceRemunerationDialog -- End

//***********************************************************************

//********************** Resource List ********************** //
//initializeHumanResourceListTable -- Start

function initializeHumanResourceListTable(participantId){
	var queryUrl="/rest/ignite/v1/human-resource/" + participantId; 
	
	var columnsArray = [
		{ data: "humanResourceId" },		//0
		{ data: "participantIdPayer" },			//1
		{ data: "" },							//2
		{ data: "participantIdBeneficiary" },	//3
		{ data: "" },							//4
		{ data: "resourceTypeId" },			//5
		{ data: "" },							//6
		{ data: "description" },				//7
		{ data: "startDate" },					//8
		{ data: "endDate" }						//9
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2 , 3, 5, 6, 7, 8, 9]
		},
		{
			render: function (data, type, row) {
				if (row.participantIdPayer == null) {
					return "";
				} else {
					var e = row.participantPayer;
					var txt = e.systemName;
					
					return txt;
				}
			},
			targets: 2
		},
		{
			render: function (data, type, row) {
				if (row.participantIdBeneficiary == null) {
					return "";
				} else {
					var e = row.participantBeneficiary;
					var txt = e.systemName;
					
					return txt;
				}
			},
			targets: 4
		},
		{
			render: function (data, type, row) {
				if (row.resourceTypeId == null) {
					return "";
				} else {
					var r = row.resourceType;
					var txt = r.name;
					
					return txt;
				}
			},
			targets: 6
		},
		{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			//width: "100px",
			targets: [8, 9]
		}
	];
	
	var buttonsArray = [
	];
	humanResourceListTable = initializeGenericTable("humanResourceListTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											//editHumanResource(participantId, aThis);
										}
	);

	humanResourceListTable.off('deselect');
	humanResourceListTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyCompetencyPanel();
//		updateResourceListToolbarButtons();
		
	} );

	humanResourceListTable.off('select');
	humanResourceListTable.on('select', function (e, dt, type, indexes) {
		showCompetencyTable(dt.data());
		showEmptyRolesPanel();
//		updateResourceListToolbarButtons();
	} );
	
//	updateResourceListToolbarButtons();
}
//initializeResourceListTable -- Start

//********************** Resource List ********************** //
//****************************************************************** //


//***************************************************************** //
//******************* Resource Competency ******************* //

function showEmptyCompetencyPanel() {
	setDivVisibility("resourceCompetencyEmptyPanel", "block");
	setDivVisibility("resourceCompetencyPanel", "none");
	showEmptyRolesPanel();
}

function showEmptyRolesPanel() {
	setDivVisibility("resourceRolesEmptyPanel", "block");
	setDivVisibility("resourceRolesPanel", "none");
}

//function updateResourceListToolbarButtons() {
//	var hasSelected = resourceListTable.rows('.selected').data().length > 0;
//
//	setTableButtonState(resourceListTable, "promptDeleteHumanResourceBtn", hasSelected);	
//}

//showResourceCompetencyTable -- Start
function showCompetencyTable(row) {
	if (row == null) {
		return;
	}

	var humanResourceId = row.humanResourceId;
	var individualId = row.individualIdBeneficiary;
	var url =  "/rest/ignite/v1/participant-osd--- individual-sd/competency/" + participantId;
  
	var columnsArray = [
   		{data:"IndividualSdId"},	//0
		{data:"individualId"},		//1
		{data:"individual"},		//2
		{data:"sdLevel"},			//3
		{data:"sdCode"},			//4
		{data:"sdName"},			//5
		{data:"competencyLevel"}	//6
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0, 1, 2, 3, 4]
		}
	];
	
	var buttonsArray = [
	];
	
	setDivVisibility("resourceCompetencyEmptyPanel", "none");
	setDivVisibility("resourceCompetencyPanel", "block");
	resourceCompetencyTable = initializeGenericTable("resourceCompetencyTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											showResourceRolesTable(rowSelector);
										},
										null,
										10,
										[[3,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	resourceCompetencyTable.off('deselect');
	resourceCompetencyTable.on('deselect', function (e, dt, type, indexes) {
		// updateResourceRemunerationToolbarButtons();
		showEmptyRolesPanel();
	} );

	resourceCompetencyTable.off('select');
	resourceCompetencyTable.on('select', function (e, dt, type, indexes) {
		showResourceRolesTable(dt.data());
		//updateResourceRemunerationToolbarButtons();
	} );	
//
//	updateResourceRemunerationToolbarButtons();
}
//showResourceCompetencyTable -- End

//***********************************************************************

//showResourceRolesTable -- Start
function showResourceRolesTable(row) {
	console.dir(row);
	if (row == null) {
		return;
	}

	var participantOSDId = row.participantOSDId;
	var url =  "/rest/ignite/v1/participant-osd-role/selected/" + participantOSDId;
	console.log(url);

	var columnsArray = [
		{data:"roleOnAProjectId"},	//0
		{data:"name"},					//1
		{data:"description"}			//2 
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0, 2]
		}
	];
	
	var buttonsArray = [
	];
	
	setDivVisibility("resourceRolesEmptyPanel", "none");
	setDivVisibility("resourceRolesPanel", "block");
	console.log(url);	
	resourceRolesTable = initializeGenericTable("resourceRolesTable", 
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function() {
											//showResourceRoles(rowSelector);
										},
										null,
										10,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

//	resourceCompetencyTable.on('deselect', function (e, dt, type, indexes) {
//		// updateResourceRemunerationToolbarButtons();
//		showEmptyRolesPanel();
//	} );
//
//	resourceCompetencyTable.on('select', function (e, dt, type, indexes) {
//		showResourceRoles(rowselector)
//		//updateResourceRemunerationToolbarButtons();
//	} );	
//
}
//showResourceRolesTable -- End






//***********************************************************************
//Did anything change on the form?  Ask user to save or not.

//generalTabChanged -- Start
function generalTabChanged() {
	currentTab = "General";
	askToSaveTab = true;
	somethingChangedGeneralTab = true;
	setElementEnabled("saveGeneralTabButton", true);
}
//generalTabChanged -- End

//participantOfficeDialogChanged -- Start
function participantOfficeDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveParticipantOfficeButton", true);
}
//participantOfficeDialogChanged -- Start

//supportServiceTabChanged -- Start
function supportServiceTabChanged() {
	currentTab = "Support Services";
	askToSaveTab = true;
	somethingChangedSSTab = true;
	setElementEnabled("saveSSTabButton", true);
}
//supportServiceTabChanged -- End

//participantOSDTabChanged -- Start
function participantOSDTabChanged() {
	currentTab = "Operational";
	askToSaveTab = true;
	somethingChangedOSDTab = true;
	setElementEnabled("saveOSDTabButton", true);
}
//participantOSDTabChanged -- End


//closeParticipantOfficeDialog -- Start
function closeParticipantOfficeDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
			//Moenie participantOfficeDialog toemaak nie... save eers
		} else {
			closeModalDialog("participantOfficeDialog");
		}
	} else  {
		closeModalDialog("participantOfficeDialog");
	}
}
//closeParticipantOfficeDialog -- End

// ContactPointDialogChanged -- Start
function cpDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveOfficeContactPointBtn", true);
}
// ContactPointDialogChanged  -- Start


// resourceDialogChanged -- Start
function resourceDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveResourceButton", true);
}
// resourceDialogChanged  -- End

//closeResourceDialog -- Start
function closeResourceDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your Resource changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
		} else {
			closeModalDialog("resourceDialog");
		}
	} else  {
		closeModalDialog("resourceDialog");
	}
}
//closeResourceDialog -- End




//closeCPDialog -- Start
function closeCPDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your Contact Point changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
		} else {
			closeModalDialog("contactPointDialog");
		}
	} else  {
		closeModalDialog("contactPointDialog");
	}
}
//closeCPDialog -- End

//participantBDDialogChanged -- Start
function participantBDDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveParticipantBDButton", true);
}
//participantBDDialogChanged -- Start

//closeParticipantBankDetailsDialog -- Start
function closeParticipantBankDetailsDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		var stayAndSaveFirst = confirm("You did not save your Bank Detail changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
		} else {
			closeModalDialog("maParticipantBankDetailsDialog");
		}
	} else  {
		closeModalDialog("maParticipantBankDetailsDialog");
	}
}
//closeParticipantBankDetailsDialog -- End


//anyUnsavedData -- Start
function anyUnsavedData() {
	
//	console.log("anyUnsavedData");
	
		setActiveTab("generalLink");
		setAllSomethingChangedFlagsFalse();
		closeModalDialog("viewTaskDialog");

}
//anyUnsavedData -- End

//setAllSomethingChangedFlags -- Start
function setAllSomethingChangedFlagsFalse() {
	
	somethingChangedGeneralTab = false;
	somethingChangedSSTab = false;
	somethingChangedOSDTab = false;
	somethingChangedInDialog = false;
	askToSaveTab = false;
	askToSaveDialog = false;
}
//setAllSomethingChangedFlags -- End
//***********************************************************************

// ***********************************************************************




function myFunction() {
    thisFunctionFirst(thisFunctionSecond);
}

function thisFunctionFirst(callback) {
	getUserNameIndivId();
    setTimeout(function() {
//        alert('first function finished');
        if(typeof callback == 'function')
            callback();
    }, 3000);
};

function thisFunctionSecond() {
	initializeMyAssignmentTable();
    setTimeout('', 200);
};	


//Johannes
function getUserNameIndivId() {
	var queryUrl;
	var volleNaam;
	
	queryUrl = "/rest/ignite/v1/individual/user-name-id";
	
	console.log("/rest/ignite/v1/individual/user-name-id");
	
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#aMyIndividualId").val(data.individualId);
			volleNaam = (data.firstName + ' ' + data.lastName);
			$("#aMyName").val(volleNaam);
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

function wysHiddenGoedMy() {
	
	let element01 = document.getElementById("aMyIndividualIdLabel"); element01.removeAttribute("hidden");
	let element03 = document.getElementById("aMyIndividualIdDiv"); element03.removeAttribute("hidden");
	let element04 = document.getElementById("aMyNameLabel"); element04.removeAttribute("hidden");
	let element05 = document.getElementById("aMyNameDiv"); element05.removeAttribute("hidden");
	let element06 = document.getElementById("taskCompletedLabel"); element06.removeAttribute("hidden");
	let element07 = document.getElementById("subPanelTaskRevisionIdLabel"); element07.removeAttribute("hidden");
	let element08 = document.getElementById("sTaskSubmissionIdLabel"); element08.removeAttribute("hidden");
	let element09 = document.getElementById("revisionNumberLabel"); element09.removeAttribute("hidden");
	let element10 = document.getElementById("revisionNumberLabel2"); element10.removeAttribute("hidden");
//	let element11 = document.getElementById("iiiiii"); element11.removeAttribute("hidden");
//	let element12 = document.getElementById("iiiiii"); element12.removeAttribute("hidden");
//	let element13 = document.getElementById("iiiiii"); element13.removeAttribute("hidden");
//	let element14 = document.getElementById("iiiiii"); element14.removeAttribute("hidden");
//	let element15 = document.getElementById("iiiiii"); element15.removeAttribute("hidden");
	
}  //wysHiddenGoed

$(document).ready(function() {
	
	setActiveTab("generalLink");
	
	myFunction();
	// Any initialization
	

	// This occurs before a tab is shown, and we can prevent switching to that tab.
	$('.nav-tabs a').on('show.bs.tab', function(event){

		// Should we save values?
		
		if ((currentTab == "General") && (somethingChangedGeneralTab) && (askToSaveTab)) {
			var stayAndSaveFirst = confirm("You did not save your Participant changes. Do you want to check your changes and save?");
			if (stayAndSaveFirst) {
				event.preventDefault();
			}
			// Even if user does not save his/her work, we will not ask only once after a change.
			
			askToSaveTab = false;
		}
		if ((currentTab == "Support Services") && (somethingChangedSSTab) && (askToSaveTab)) {
			var stayAndSaveFirst = confirm("You did not save your Support Services Tab changes. Do you want to check your changes and save?");
			if (stayAndSaveFirst) {
				event.preventDefault();
			}
			// Even if user does not save his/her work, we will not ask only once after a change.
			askToSaveTab = false;
		}
		if ((currentTab == "Operational") && (somethingChangedOSDTab) && (askToSaveTab)) {
			var stayAndSaveFirst = confirm("You did not save your Operational Service Disciplines Tab changes. Do you want to check your changes and save?");
			if (stayAndSaveFirst) {
				event.preventDefault();
			}
			// Even if user does not save his/her work, we will not ask only once after a change.
			askToSaveTab = false;
		}
	});
	
	// This occurs when a tab is made visible, and we therefor want to populate the data
	// react to tab change and load appropriate data
	$('.nav-tabs a').on('shown.bs.tab', function(event){
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');
		var participantId = $("#epParticipantId").val();
		var resourceId = $("#epResourceId").val();
		
		if (activeTab == "General") {
			currentTab = "General";
			populateOfficesAndBankDetailsForDefaults();
		}
		
		if (activeTab == "Offices") {
			currentTab = "Offices";
			initializeOfficeTab(participantId);
			
		}
		if (activeTab == "Bank Details") {
			currentTab = "Bank Details";
			initializeBankDetailsTab(participantId);	
			initializeBankCardTable(participantId);
		}
		if (activeTab == "Support Services") {
			currentTab = "Support Services";
			if (!somethingChangedSSTab) {
				initializeParticipantSupportServices();
			}
		}
		if (activeTab == "Operational") {
			currentTab = "Operational";
			if (!somethingChangedOSDTab) {
				$("#osdTreePanel").resizable({
					handles: "e",
					alsoResizesReverse: "osdTreeDetail"
				})
				initializeOSdTree();
			}
		}
		if (activeTab == "Human Resources") {
			currentTab = "Human Resources";
			initializeHumanResourceTable(participantId);
		}
		if (activeTab == "Resource Competency") {
			currentTab = "Resource Competency";
			initializeResourceListTable(participantId);
		}

		} );
} );






