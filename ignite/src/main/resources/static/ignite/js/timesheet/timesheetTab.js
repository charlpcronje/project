// var timesheetSummaryTable = null;
var timesheetTable = null;
var tRatesTable = null;
var participantIdSelected = null;
var resourceId = null;
var tsRowSelected = null;

var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

//var newDateRangeSelected = false;
//const now = new Date();
//const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
//const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day



//*********************************************************
// Timesheet Tab -- Start
//*********************************************************

////*********************************************************
////initializeTimesheetSummaryTable -- Start
//function initializeTimesheetSummaryTable(participantId) {
//
//	const now = new Date();
//	if (($("#tStartDate").val() == "") || ($("#tStartDate").val() == null)) {
//		const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Prev month's first day
//		$("#tStartDate").datepicker("setDate", new Date(firstDay));	
//	}
//	if (($("#tEndDate").val() == "") || ($("#tEndDate").val() == null)) {
//		const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day
//		$("#tEndDate").datepicker("setDate", new Date(lastDay));		
//	}	
//
//	var fd = getMsFromDatePicker("tStartDate");
//	var ld = getMsFromDatePicker("tEndDate");
//	queryUrl = "/rest/ignite/v1/timesheet/summary/" + participantId + "/" + fd + "/" + ld;
//
//
//    var columnsArray = [
//		{ data: "participantIdThatBookedTime" },	//0
//	    { data: "participantNameHost" },			//1 -- 
//	    { data: "projectNameText" },				//2 --
//		{ data: "sdAndRole" },						//3 --
//		{ data: "remunerationTypeName" },			//4 -- 
//		{ data: "unitTypeName" },					//5 --
//		{ data: "totalUnits" },						//6 --
//		{ data: "projectId" },						//7
//		{ data: "sdCode" },							//8
//		{ data: "sdRoleId" }						//9
//	];
//	
//	var columnDefsArray = [
//	               		{
//	               			visible: false,
//	               			targets: [0, 7, 8, 9]
//	               		}, 
//	               		{width: "15%",targets: 1},
//	               		{width: "30%",targets: 2},
//	               		{width: "25%",targets: 3},
//	               		{width: "10%",targets: 4},
//	               		{width: "10%",targets: 5},
//	               		{width: "10%",targets: 6}
//	               	];
//	var buttonsArray = [];
//	
//	
//
//	timesheetSummaryTable = initializeGenericTable("timesheetSummaryTable",
//										queryUrl,
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function(rowSelector) {
//										}
//	);
//	timesheetSummaryTable.off('deselect');
//	timesheetSummaryTable.on('deselect', function (e, dt, type, indexes) {
//		initializeTimesheetTable(null)
//	} );
//	
//	timesheetSummaryTable.off('select');
//	timesheetSummaryTable.on('select', function (e, dt, type, indexes) {
//		initializeTimesheetTable(dt.data());
//	} );	
//
//}
//// initializeTimesheetSummaryTable -- End
//// *********************************************************



//***********paymentMethodChanged()**********************************************
// initializeTimesheetTable -- Start

function initializeTimesheetTable(timesheetSummaryRow) {

	const now = new Date();
	if (($("#tStartDate").val() == "") || ($("#tStartDate").val() == null)) {
		const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Prev month's first day
		$("#tStartDate").datepicker("setDate", new Date(firstDay));	
	}
	if (($("#tEndDate").val() == "") || ($("#tEndDate").val() == null)) {
		const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day
		$("#tEndDate").datepicker("setDate", new Date(lastDay));		
	}	

	// Send dates through to controller as Long!
	var fd = getMsFromDatePicker("tStartDate");
	var ld = getMsFromDatePicker("tEndDate");
	
    var participantIdThatBookedTime = $("#tParticipantId").val();
    
	var participantName = $("#tParticipantName").val();
    
	if (timesheetSummaryRow == null) {
		// Show all timesheet entries for daterange
		queryUrl = "/rest/ignite/v1/timesheet/list/" + participantIdThatBookedTime + "/" + fd + "/" + ld;     //vTimeSheet
	} 
//	else {
//		var projectId = timesheetSummaryRow.projectId;
//		var sdCode = timesheetSummaryRow.sdCode;
//		// Show timesheet entries for daterange, Project, and sdCode
//		queryUrl = "/rest/ignite/v1/timesheet/list-per-proj/" + participantIdThatBookedTime + "/" + sdCode + "/" + projectId + "/" + dateFrom + "/" + dateTo;     //vTimeSheet
//		console.log(queryUrl);
//	}
	
	var columnsArray = [

	    { data: "timesheetId" },				//0
	    { data: "projectParticipantSdRoleId" },	//1
		{ data: "activityDate" },				//2 --
	    { data: "projectNameText" },			//3 --
		{ data: "" },							//4 --
		{ data: "participantIdHost" },			//5
		{ data: "description" },				//6 --
		{ data: "roleName" },					//7
		{ data: "systemNameThatBookedTime" },	//8
		{ data: "stageName" },					//9 --
		{ data: "remunerationTypeName" },		//10 
		{ data: "numberOfUnits" },				//11 --
		{ data: "sdName" },						//12 
		{ data: "projectParticipantIdThatBookedTime" },		//13
		{ data: "projectSdId" },				//14
		{ data: "unitTypeCode" },				//15 
		{ data: "unitTypeName" },				//16
		{ data: "participantIdHost" },		//17
	    { data: "timesheetId" },				//18
	    { data: "projectParticipantSdRoleId" },	//19
	    { data: "projBasedRemunTypeId" },		//20
	    { data: "participantIdHost" },		//21
		{ data: "participantNameHost" },	//22
		{ data: "sdRoleId" },					//23 
	    { data: "projBasedRemunTypeId" },		//24
		{ data: "activityDate" },				//25
		{ data: "remunerationTypeName" },		//26
		{ data: "numberOfUnits" },				//27
		{ data: "description" },				//28
		{ data: "projectParticipantIdThatBookedTime" },		//29
		{ data: "projectId" },					//30
		{ data: "sdCode" },						//31 
		{ data: "participantIdThatBookedTime" },	//32
		{ data: "roleOnAProjectId" },			//33
		{ data: "unitTypeCode" },				//34
		{ data: "unitTypeName" },				//35
		{ data: "assignmentId" },				//36
		{ data: "taskId" }						//37
			
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,5,7,8,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37]
		},
		{	width: "5%",	targets: [2]},	// activityDate
		{	width: "30%", 	targets: [3]},	// projectNameText
		{   width: "15%", 	targets: [4]},	// Sd and rolw
		{	width: "35%",	targets: [6]},	// description
		{	width: "10%",	targets: [9]},	// stageName
		{	width: "5%",	targets: [11]},	// numberOfUnits
 		{
 			render: function(data, type, row) {
 				r = "";
 				if (row.hasOwnProperty("sdName")) {
 					r = row.sdName;
 					if (row.hasOwnProperty("roleName")) {
 	 					r = r + " - "  + row.roleName;
 	 				}
 				}
 				return r;
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
			targets: [2]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				var tsRowSelected = null;
				timesheetTable.on('select', function (e, dt, type, indexes) {
					tsRowSelected = timesheetTable.rows('.selected').data()[0];
				});
				editTimesheet(null, tsRowSelected);
			}
		},
		{
			attr: {
				id: "promptDeleteTimesheetBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteTimesheet();
			}
		},
		{
			attr: {
				id: "thisWeekBtn"
			},
			titleAttr: "This Week",
			text: "<i class='far fa-calendar'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				thisWeek();
			}
		},
		{
			attr: {
				id: "previousWeekBtn"
			},
			titleAttr: "Previous Week",
			text: "<i class='fas fa-chevron-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				previousWeek();
			}
		},
		{
			attr: {
				id: "nextWeekBtn"
			},
			titleAttr: "Next Week",
			text: "<i class='fas fa-chevron-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				nextWeek();
			}
		},
		{
			attr: {
				id: "thisMonthBtn"
			},
			titleAttr: "This Month",
			text: "<i class='far fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				thisMonth();
			}
		},
		{
			attr: {
				id: "prevMonthBtn"
			},
			titleAttr: "Previous Month",
			text: "<i class='fas fa-angle-double-left'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				prevMonth();
			}
		},
		{
			attr: {
				id: "nextMonthBtn"
			},
			titleAttr: "Next Month",
			text: "<i class='fas fa-angle-double-right'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				newDateRangeSelected = true;
				nextMonth();
			}
		}
	];

	timesheetTable = initializeGenericTable("timesheetTable",
										queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editTimesheet(rowSelector, null);
											
										},
			                            null,
										25,
										//[2,"desc"] //Order by column 0 ascending, normally defaults to column 1 ascending
										[2, "desc"], // Order by column 2 (activityDate) descending
									    null, // tableHeightPixels
									    true, // showTableInfo
									    true, // showFilter
								        null // Remove the drawCallback parameter
								    );
						
								    // Bind the draw event
								    timesheetTable.on('draw.dt', function() {
								        // Calculate and display the sum after each draw
								        calculateAndDisplaySum(timesheetTable, columnsArray);
								    }
	);


	
	
	timesheetTable.off('deselect');
	timesheetTable.on('deselect', function (e, dt, type, indexes) {
		updateTimesheetTableToolbarButtons();
		tsRowSelected = null;
		// showEmptyRatePanel();
	} );

	timesheetTable.off('select');
	timesheetTable.on('select', function (e, dt, type, indexes) {
		updateTimesheetTableToolbarButtons();
		tsRowSelected = dt.data();
		//showTimesheetRatesTable(dt.data());

	} );
	updateTimesheetTableToolbarButtons();
//	showEmptyRatePanel();

	showModalDialog("timesheetDialog");
}






function calculateAndDisplaySum(table, columnsArray) {
    let sum = 0;

    table.rows({ search: 'applied' }).every(function (rowIdx, tableLoop, rowLoop) {
        let data = this.data();

        let value = data.numberOfUnits;

        if (!isNaN(value)) {
            sum += parseFloat(value);
        }
    });

    document.getElementById('tTotalHoursDisplayed').value = sum;
}




// tTotalHoursDisplayed






function getTotalHours() {
	var tot=0;
	timesheetTable.rows({ filter : 'applied'}).every(function(){
       tot += this.data().numberOfUnits
	});
	document.getElementById('tTotalHoursDisplayed').value = (tot);   //input
//	document.getElementById("mySpan").textContent = (tot);           //span
}

function clearTotalHours() {
	document.getElementById('tTotalHoursDisplayed').value = "";
//	document.getElementById('mySpan').textContent = "--0--";
}



//function getTotalHours() {
//	timesheetTable.on( 'draw', function () {
//    console.log( 'Redraw occurred at: '+new Date().getTime() );
//    var myCount = 0;
//    var totalSum = 0;
//    timesheetTable.rows( { filter : 'applied'} ).every(function (rowIdx, tableLoop, rowLoop) {
//            var data = this.data();
//            
//            console.log('num1: ' + data.numberOfUnits );
//            
//            if (data.numberOfUnits !== '') {             
//                myCount += 1;
//                totalSum += parseInt(data.numberOfUnits); 
//            }
//            
//        });
//        console.log('myCount: ' + myCount + ' totalSum: ' + totalSum);
//} );
//}









//function getTotalHours() {
//var dataArray = timesheetTable('td:nth-child(11)', {"filter": "applied"});
//var sum = 0;
//for (var i=0, len=dataArray.length; i < len; i++) {
//sum += +dataArray[i];
//}
//$("table.display tfoot th:nth-child(11)").html(sum);
//}


//function getTotalHours(){
//	   var arr = document.querySelectorAll("#timesheetTable td:nth-child(11)");
//	  
//	   var tot=0;
//
//	   for(var i=0;i<arr.length;i++){
//		   if(parseInt(arr[i].innerText))
//		     tot += parseInt(arr[i].innerText);
//		  }
//	   document.getElementById('tTotalHoursDisplayed').value = parseInt(tot);
//	 }








//var rows = $( 'input[type=checkbox]:selected', table.rows( { order: 'applied' ).nodes() );
//
//rows.each( function () {
//  var tr = $(this).closest('tr');
//  var data = table.row( tr ).data();
// 
//  console.log( data );
//} );

//function showEmptyRatePanel() {
//
//	$("#tTimesheetRateHeading").html("Agreement Rates");
//	setDivVisibility("tEmptyRatesPanel", "block");
//	setDivVisibility("tRatesPanel", "none");
//
//}

function updateTimesheetTableToolbarButtons() {
	var hasSelected = timesheetTable.rows('.selected').data().length > 0;

	setTableButtonState(timesheetTable, "promptDeleteTimesheetBtn", hasSelected);	
}
	
function promptDeleteTimesheet() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Timesheet Entry?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteTimesheet(timesheetTable);
			   }
	);
}

function deleteTimesheet(tbl) {
	var postUrl = "/rest/ignite/v1/timesheet/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Timesheet Entry has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateTimesheetTableToolbarButtons
				// timesheetSummaryTable.ajax.reload();
			}
	);
}

// initializeTimesheetTable -- End
// *********************************************************






////*********************************************************
////Rate Table  -- Start
////*********************************************************
//
//function showTimesheetRatesTable(timesheetRow) {
//	if (timesheetRow == null) {
//		return;
//	}
//	var timesheetId = timesheetRow.timesheetId;
//	var heading = timesheetRow.remunerationTypeName;
//	var queryUrl;
//	$("#tTimesheetRateHeading").html("Agreement Rates: " + heading);
//
//	var queryUrl="/rest/ignite/v1/remuneration-rate-upline/rates-upline/" + timesheetId;
//	
//	setDivVisibility("tEmptyRatesPanel", "none");
//	setDivVisibility("tRatesPanel", "block");
//	
//	var columnsArray = [
//		{ data: "rowNumber" },						// 0
//		{ data: "timesheetId" },					// 1
//		{ data: "agreementBetweenParticipantsId" },	// 2
//		{ data: "level" },							// 3
//		{ data: "agreementBetween" },				// 4
//		{ data: "rateForDate" } 					// 5
//
//	];
//
//	var columnDefsArray = [
//		{
//			visible: false,
//			targets: [0,1,2]
//		},
// 		{
// 			render: function(data, type, row) {
// 				r = "";
// 				if (row.hasOwnProperty("rateForDate")) {
// 					r = row.rateForDate;
// 					if (r == -1) {
// 						r = "Rates overlap!"
// 					}
// 					if (r == -2) {
// 						r = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';
// 					}
// 					if (r > 0) {
// 						if (type == "display") {
// 							r = valueToRand(r);
// 						}
// 					}
// 				}
// 				return r;
// 			},
// 			targets: 5
// 		}
//	];
//
//	var buttonsArray = [];
//	
//	tRatesTable = initializeGenericTable("tRatesTable",
//			                            queryUrl,
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function(rowSelector) {
//										},
//										null,
//										25,
//										[3,"asc"] //Order by column 0 ascending, normally defaults to column 1 ascending
//
//	);
//
//}
//
////*********************************************************
////Rate Table  -- End
////*********************************************************
//*********************************************************
//Timesheet Tab -- End
//*********************************************************

//*********************************************************
//timesheet Date range functions -- End
//*********************************************************

// *********************************************************
// editTimesheet -- Start
function editTimesheet(rowSelector) {
	
	var errors = "";
	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	// var selectedProjBasedRemunTypeId = null;
	var selectedProjBasedRemunTypeId = 7; // Fix this Ingrid!!! "HOURLY_RATE_WORK"
	
	var participantName = $("#tParticipantSystemName").val();
	var participantId = $("#tParticipantId").val();
	$("#tParticipantName").val(participantName);
	
	$("#editTimesheetDialogHeader").html("<i class='fa fa-info-circle'></i> Timesheet Entry Details: " + participantName);
	
	var projectParticipantId = $("#tProjectParticipantId").val();
	
//	{ data: "participantIdThatBookedTime" },	//0
//    { data: "participantNameHost" },			//1
//    { data: "projectNameText" },				//2
//	{ data: "sdAndRole" },						//3
//	{ data: "remunerationTypeName" },			//4
//	{ data: "unitTypeName" },					//5
//	{ data: "totalUnits" }						//6

	
	if (rowSelector != null) {
		data = timesheetTable.row(rowSelector).data();
		enabled = true;
		$("#tProjectNameText").val(data.projectNameText); 
		$("#tSDAndRole").val(data.sdName + " - " + data.roleName);
		selectedProjBasedRemunTypeId = data.projBasedRemunTypeId
		$("#tUnitTypeName").val(data.unitTypeName);
		$("#tProjectParticipantId").val(data.projectParticipantIdThatBookedTime);
		fillDescriptionSelect($("#tProjectParticipantId").val());
		fillProjectStageSelect(data.projectParticipantSdRoleId, data.ppSdRoleStageId);
		$("#tDescription").val(data.description);
		$("#tProjectParticipantSdRoleId").val(data.projectParticipantSdRoleId);
	} else if (tsRowSelected != null) {
		enabled = true;
		$("#tProjectNameText").val(tsRowSelected.projectNameText); 
		$("#tSDAndRole").val(tsRowSelected.sdName + " - " + tsRowSelected.roleName);
		selectedProjBasedRemunTypeId = tsRowSelected.projBasedRemunTypeId
		$("#tUnitTypeName").val(tsRowSelected.unitTypeName);
		$("#tProjectParticipantId").val(tsRowSelected.projectParticipantIdThatBookedTime);
		fillDescriptionSelect($("#tProjectParticipantId").val());
		fillProjectStageSelect(tsRowSelected.projectParticipantSdRoleId, tsRowSelected.ppSdRoleStageId);
		$("#tDescription").val(tsRowSelected.description);
		$("#tProjectParticipantSdRoleId").val(tsRowSelected.projectParticipantSdRoleId);
	} else if (tsRowSelected != null) {
		$("#tDescriptionSelect").empty();      //remove all values from select object
		$("#tProjectNameText").val("");
		$("#tSDAndRole").val("");
		$("#tUnitTypeName").val("Per Hour");
		$("#tProjectParticipantId").val("");
		$("#tProjectStage").empty();
	}
	timesheetTable.rows().deselect();
	
	$("#tTimesheetId").val(data.timesheetId);
	
	populateSelect("tProjBasedRemunType", //elementId, html select element that will be populated
		       "/rest/ignite/v1/proj-based-remun-type/time-based", //url, url where it must get the data (you can paste in browser window to see the data)
		       "projBasedRemunTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       selectedProjBasedRemunTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
		       null  //completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	
	populateRemunIntervalAndUnitType();

	$("#tNumberOfUnits").val(data.numberOfUnits);
	$("#tActivityDate").datepicker("setDate", data.activityDate == null? timestampToString(new Date(), false) : new Date(data.activityDate));
	
	//setElementEnabled("saveTimesheetButton", false);
	
	showModalDialog("editTimesheetDialog");
}
// editTimesheet -- End

function fillProjectStageSelect(projectParticipantSdRoleId, ppSdRoleStageId) {
	
	if ((projectParticipantSdRoleId != null) && (projectParticipantSdRoleId != "")) {

		populateSelect("tProjectStage",      //name of html select element the will be populated
				"/rest/ignite/v1/pp-sd-role-stage/vppsd-role/" + projectParticipantSdRoleId,     //url
			    "ppSdRoleStageId",             //the value that must be saved
			    "stageName",                 //shown to user
			    ppSdRoleStageId,          //The selected one, if there is one
			    true,                   //addEmpty, boolean: should we add empty object at the top
			    null					//completeMethod, javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
								//existingData, the existing data to be inspected
								//compareField, the field to test which will result in values not being added if they already exist
								//compareMethod, a method to be called which returns true or false to perform the comparison on more complex matches
							    //"topDescriptionsUsedId"
			);			
		
	}
}






function  populatetDescription() {
	
	$("#tDescription").val($("#tDescriptionSelect").val());
	
//	$('#tDescriptionSelect').change(function(){
//	    $("#tDescription").val($(this).val());
//	})
	
}

//saveTimesheet -- Begin
function saveTimesheet() {
	var postUrl = "/rest/ignite/v1/timesheet/new";
	var postData = {
			
			timesheetId: $("#tTimesheetId").val(), 
			projectParticipantSdRoleId: $("#tProjectParticipantSdRoleId").val(), 
			projBasedRemunTypeId: $("#tProjBasedRemunType").val(), 
			activityDate: getMsFromDatePicker("tActivityDate"),
			numberOfUnits: $("#tNumberOfUnits").val(), 
			description: $("#tDescription").val(),
			ppSdRoleStageId: $("#tProjectStage").val()
	};

	var errors = "";

	// validate

	if ((postData.projectParticipantSdRoleId == "") || (postData.projectParticipantSdRoleId== null)){
		errors += "A Project is required<br>";
	}
	
	if ((postData.activityDate == "") || (postData.activityDate == null)){
		// Add Today's date without time
		$("#tActivityDate").datepicker("setDate", postData.activityDate == null? timestampToString(new Date(), false) : new Date(postData.activityDate));
		postData.activityDate = getMsFromDatePicker("tActivityDate");
		postData.ratePerUnit = $("#tRatePerUnit").val(); 

	}

	if ((postData.numberOfUnits == "") || (postData.numberOfUnits== null)){
		errors += "Number of Units are required<br>";
	} else {
		if (isNaN(postData.numberOfUnits)) {
			errors += "The Number of Units has to be numeric<br>";
		}
	}

	if ((postData.description == "") || (postData.description == null)){
		errors += "A Description is required<br>";
	}

//
//	if ((postData.ratePerUnit == "") || (postData.ratePerUnit== null)){
//		errors += "There is no Rate setup for this project and date combination<br>";
//	} else {
//		if (isNaN(postData.ratePerUnit)) {
//			errors += "The Rate per Unit has to be numeric<br>";
//		}
//	}
	
	if (showFormErrors("tDlgErrorDiv", errors)) {
		return;
	}
	
	if ((postData.timesheetId != null) && (postData.timesheetId != "")) {
		postUrl = "/rest/ignite/v1/timesheet";
	}
	
	saveEntry(postUrl, postData, "editTimesheetDialog", "The Timesheet entry has been saved.", timesheetTable, function(){	
		// timesheetSummaryTable.ajax.reload();
	});

}

//saveTimesheet -- End

function populateRemunIntervalAndUnitType() {
	var selectedRemunTypeCode = $("#tProjBasedRemunType").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page
	if (selectedRemunTypeCode == null) {
		selectedRemunTypeCode = 7; // "HOURLY_RATE_WORK";
	}

	var queryUrl;
	queryUrl = "/rest/ignite/v1/proj-based-remun-type/" + selectedRemunTypeCode;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			if (data.hasOwnProperty("remunerationInterval")) {
//				//$("#rRemunerationIntervalId").val(data.remunerationInterval.remunerationIntervalId);
//				$("#RemunerationIntervalName").val(data.remunerationInterval.name);
//			}
			if (data.hasOwnProperty("unitType")) {
				//$("#rUnitTypeCode").val(data.unitType.unitTypeCode);
				$("#tUnitTypeName").val(data.unitType.name);
			}
			return data;
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}

function selectProjectSdAndRoles(){

	var participantId = $("#tParticipantId").val();

	var queryUrl="/rest/ignite/v1/project-participant/project-sd-role/" + participantId ;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="projectParticipantId";
			var columns = [
				{ data: "projectParticipantId", name: "ProjectParticipantId" },			// 0
				{ data: "projectParticipantSdRoleId", name: "ProjectParticipantSdRoleId" },		// 1
				{ data: "participantIdBeneficiary", name: "ParticipantIdBeneficiary" },	// 2
				{ data: "systemNameBeneficiary", name: "SystemNameBeneficiary" },		// 3
				{ data: "sdCode", name: "SDCode" },										// 4
				{ data: "participantIdHost", name: "Top Level Participant Number" },// 5
				{ data: "projectNumberBigInt", name: "Project Number" },						// 6
				{ data: "projectName", name: "Project" }, 								// 7
				{ data: "sdName", name: "Service Discipline" },							// 8
				{ data: "roleOnAProjectId", name: "RoleOnAProjectId" },				// 9
				{ data: "roleOnAProjectName", name: "Role On The Project" },			// 10
				{ data: "projectSdId", name: "ProjectSdId" }, 							// 11
				{ data: "projectId", name: "ProjectId" } 								// 12
				
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0,1,2,3,4,5,6,9,11,12]
				}		
			];

				selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				
				$("#tProjectParticipantId").val(row.projectParticipantId);
				$("#tProjectParticipantSdRoleId").val(row.projectParticipantSdRoleId);
				$("#tProjectNameText").val(row.projectName);
				$("#tSDAndRole").val(row.sdName + " - " + row.roleOnAProjectName);

				fillDescriptionSelect($("#tProjectParticipantId").val());
				fillProjectStageSelect(row.projectParticipantSdRoleId, null)
				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
			
		}  
	});
}  //selectProjectSdAndRoles -- END



function  fillDescriptionSelect(projectParticipantId) {

	populateSelect("tDescriptionSelect",      //name of html select element the will be populated
	    "/rest/ignite/v1/timesheet/last-ten-descriptions/" + projectParticipantId,     //url
	    "description",             //the value that must be saved
	    "description",                 //shown to user
	    null,          //The selected one, if there is one
	    true,                   //addEmpty, boolean: should we add empty object at the top
	    null,
	    "topDescriptionsUsedId"
	);	

}

//*********************************************************
//timesheet Date range functions -- Start
//*********************************************************


function refreshTimesheetTablesForDateRange() {
	
	var dateFrom = getMsFromDatePicker("tStartDate");
    var dateTo= getMsFromDatePicker("tEndDate");

    // var requestUrl = "/rest/ignite/v1/timesheet/summary/" + participantIdSelected + "/" + dateFrom + "/" + dateTo;
	//timesheetSummaryTable.ajax.url(springUrl(requestUrl)).load();

    var participantIdThatBookedTime = $("#tParticipantId").val();

    
	queryUrl = "/rest/ignite/v1/timesheet/list/" + participantIdThatBookedTime + "/" + dateFrom + "/" + dateTo;     //vTimeSheet
	timesheetTable.ajax.url(springUrl(queryUrl)).load();
	// showEmptyRatePanel();
}


function previousWeek() {
  var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("tStartDate")));
	
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() - 7);
	dateTo.setDate(dateTo.getDate() - 1);

	$("#tStartDate").datepicker("setDate", new Date(dateFrom));
	$("#tEndDate").datepicker("setDate", new Date(dateTo));

	refreshTimesheetTablesForDateRange();
} 

function nextWeek() {
  var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("tEndDate")));
	
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() + 7);
	dateTo.setDate(dateTo.getDate() + 13);

	$("#tStartDate").datepicker("setDate", new Date(dateFrom));
	$("#tEndDate").datepicker("setDate", new Date(dateTo));

	refreshTimesheetTablesForDateRange();
}

function thisWeek() {

  var prevSunday = getPreviousSunday(new Date());
	
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateTo.setDate(dateFrom.getDate() + 6);

	$("#tStartDate").datepicker("setDate", new Date(dateFrom));
	$("#tEndDate").datepicker("setDate", new Date(dateTo));

	refreshTimesheetTablesForDateRange();
}
function thisMonth() {
	const now = new Date();
	const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
	const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

	$("#tStartDate").datepicker("setDate", new Date(firstDay));
	$("#tEndDate").datepicker("setDate", new Date(lastDay));

	refreshTimesheetTablesForDateRange();
}

function nextMonth() {
	
	const currentEndDate = new Date(getMsFromDatePicker("tEndDate"));
	const firstDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 1, 1); //Next month's first day
	const lastDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 2, 0); //Next month's last day
	
	$("#tStartDate").datepicker("setDate", new Date(firstDay));
	$("#tEndDate").datepicker("setDate", new Date(lastDay));

	refreshTimesheetTablesForDateRange();
}

function prevMonth() {

	const currentStartDate = new Date(getMsFromDatePicker("tStartDate"));
	const firstDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth() - 1, 1); //Prev month's first day
	const lastDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth(), 0); //Prev month's last day

	$("#tStartDate").datepicker("setDate", new Date(firstDay));
	$("#tEndDate").datepicker("setDate", new Date(lastDay));

	refreshTimesheetTablesForDateRange();
}

