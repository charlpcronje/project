var assignmentTable = null;
var somethingChangedGeneralTab = null;
var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var nextAssignmentNumber = null;

var newDateRangeSelected = false;
const now = new Date();


// stel hom gelyk aan 1:    $("#aAssignmentNumber").val(1);
// wys hom vir ons:       console.log("aAssignmentNumber: " + $("#aAssignmentNumber").val());



// *********************************************************
// initializeAssignmentTable -- Start

function initializeAssignmentTable() {
	
	var dateFrom = new Date(getMsFromDatePicker("drStartDate"));
	var dateTo = new Date(getMsFromDatePicker("drEndDate"));
//	var individualRowSelected = individualTable.rows('.selected').data()[0];

//console.log("ddd1 " + dateFrom);
//console.log("getFullYear " + dateFrom.getFullYear());
	

	if (dateFrom.getFullYear() == "1970") {
		$("#rangeStartDate").datepicker("setDate", new Date(now.getFullYear(), now.getMonth(), now.getDate() - 7));
		$("#rangeEndDate").datepicker("setDate", new Date(now.getFullYear(), now.getMonth(), now.getDate() + 14));
		var dateFrom = new Date(getMsFromDatePicker("rangeStartDate"));
		var dateTo = new Date(getMsFromDatePicker("rangeEndDate"));		
	} else {
		$("#rangeStartDate").datepicker("setDate", new Date(dateFrom));
		$("#rangeEndDate").datepicker("setDate", new Date(dateTo));		
	}
	
//console.log("ddd2 " + dateFrom);

//const firstDay = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 7); //Current month's first day
//const lastDay = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 14); //Current month's last day

//var dateFrom = new Date($("#rangeStartDate").val());
//var dateTo = new Date($("#rangeEndDate").val());

//$("#tStartDate").datepicker("setDate", new Date(dateFrom));
//$("#tEndDate").datepicker("setDate", new Date(dateTo));
	
	var columnsArray = [

		{ data: "assignmentId" },                          // 0 
		{ data: "projectParticipantIdInstructor" },        // 1 
		{ data: "ppInstructorName" },                      // 2 
		{ data: "individualIdInstructor" },                // 3 
		{ data: "individualInstructorName" },              // 4 
		{ data: "projectParticipantIdIndivResp" },         // 5 
		{ data: "individualRespName" },                    // 6 
		{ data: "individualIdLogger" },                    // 7 
		{ data: "individualLoggerName" },                  // 8 
		{ data: "assignmentGroupId" },                     // 9 
		{ data: "assignmentGroupName" },                   //10 
		{ data: "assignmentNumber" },                      //11 
		{ data: "name" },                          		   //12 
		{ data: "description" },                           //13 
		{ data: "startDate" },                             //14 
		{ data: "timeOfDay" },                             //15 
		{ data: "repeatCode" },                            //16 
		{ data: "lastUpdateTimestamp" },                   //17 
		{ data: "lastUpdateUserName" },                    //18 
		{ data: "projectId" },                             //19 
		{ data: "projectAssignmentNumber" },               //20 
		{ data: "title" },                                 //21 
		{ data: "projectName" },                           //22 
		{ data: "completed" }                              //23 
						    
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 5, 7, 8, 9, 13, 15, 16, 17, 18, 19, 20, 21, 22]
		},

		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [14]//, 15, 17]
		}
	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editAssignment(null);
			}
		},
		{
			attr: {
				id: "promptDeleteAssignmentBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteAssignment();
			}
		},
		
		{
			attr: {
				id: "showAllAssignmentsBtn"
			},
			titleAttr: "All",
			text: "<i class='fas fa-calendar-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				 
				$("#pageHeaderAssignments").html("All Assignments");
				setTableButtonState(assignmentTable, "showNewSubsAssignmentsBtn", true);
				setTableButtonState(assignmentTable, "showCurrentAssignmentsBtn", true);	
				setTableButtonState(assignmentTable, "showAllAssignmentsBtn", false);	
				var requestUrl = "/rest/ignite/v1/assignment/list/" + dateFrom + "/" + dateTo;
				assignmentTable.ajax.url( springUrl(requestUrl) ).load()
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
				$("#pageHeaderAssignments").html("Current Assignments");
				setTableButtonState(assignmentTable, "showNewSubsAssignmentsBtn", true);
				setTableButtonState(assignmentTable, "showCurrentAssignmentsBtn", false);	
				setTableButtonState(assignmentTable, "showAllAssignmentsBtn", true);	
				var requestUrl = "/rest/ignite/v1/assignment/list-current/" + dateFrom + "/" + dateTo;
				assignmentTable.ajax.url( springUrl(requestUrl) ).load()
				}
		},
		
		{
			attr: {
				id: "showNewSubsAssignmentsBtn"
			},
			titleAttr: "With new Submissions",
			text: "<i class='fas fa-clipboard-list'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				$("#pageHeaderAssignments").html("Assignments with new Submissions");
				setTableButtonState(assignmentTable, "showNewSubsAssignmentsBtn", false);	
				setTableButtonState(assignmentTable, "showCurrentAssignmentsBtn", true);	
				setTableButtonState(assignmentTable, "showAllAssignmentsBtn", true);	
				var requestUrl = "/rest/ignite/v1/assignment/list-current-new/" + dateFrom + "/" + dateTo;
				assignmentTable.ajax.url( springUrl(requestUrl) ).load()
				}
		}		
		
			
		
	];

	console.log ("Alle assignments:   " + "rest/ignite/v1/assignment/list-current/" + dateFrom + "/" + dateTo);
	
	assignmentTable = initializeGenericTable("assignmentTable",
			                            "/rest/ignite/v1/assignment/list-current/" + dateFrom + "/" + dateTo,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editAssignment(rowSelector);
										},
			                            null,
			                            25,
			                            [[3,"asc"]] //Order by column 3 ascending, normally defaults to column 1 ascending
	);
		
	
	assignmentTable.off('deselect');
	assignmentTable.on('deselect', function (e, dt, type, indexes) {
		updateAssignmentToolbarButtons();
	} );

	assignmentTable.off('select');
	assignmentTable.on('select', function (e, dt, type, indexes) {
		updateAssignmentToolbarButtons();
	} );
	updateAssignmentToolbarButtons();
	

	setTableButtonState(assignmentTable, "showCurrentAssignmentsBtn", false);	
	setTableButtonState(assignmentTable, "showAllAssignmentsBtn", true);	
	setTableButtonState(assignmentTable, "showNewSubsAssignmentsBtn", true);
	
}


//selectNewDateRange -- Start
function selectNewDateRange() {

	var dateFrom = getMsFromDatePicker("rangeStartDate");
	var dateTo = getMsFromDatePicker("rangeEndDate");
	
	$("#drStartDate").datepicker("setDate", new Date(dateFrom));
	$("#drEndDate").datepicker("setDate", new Date(dateTo));
	
	showModalDialog("dateRangeDialog");
}
//selectNewDateRange -- End







function updateAssignmentToolbarButtons() {
	var hasSelected = assignmentTable.rows('.selected').data().length > 0;

	setTableButtonState(assignmentTable, "promptDeleteAssignmentBtn", hasSelected);	
}
	
function promptDeleteAssignment() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Assignment?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteAssignment(assignmentTable);
			   }
	);
}

function deleteAssignment(tbl) {
	var postUrl = "/rest/ignite/v1/assignment/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Assignment has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateAssignmentToolbarButtons
//				selectNextAssignmentNumber();
			}
	);
}

// initializeAssignmentTable -- End
// *********************************************************



// *********************************************************
// editAssignment -- Start
// General Tab

function editAssignment(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	setAllSomethingChangedFlagsFalse();
	var header = "Assignment Detail";
	var assignmentId
	
	if (rowSelector != null) {
		let element13 = document.getElementById("aAssignmentTypeDiv"); element13.setAttribute("Hidden", "hidden");
		data = assignmentTable.row(rowSelector).data();
		enabled = true;
		header = "Assignment Detail: " + data.assignmentNumber + " " + data.name;	
		
		document.getElementById('aRepeatDiv').hidden = true;
		document.getElementById('repeatOptionsDWMY').hidden = true;

		jQuery('#ttDaily').attr('disabled',true);
		jQuery('#ttWeekly').attr('disabled',true);
		jQuery('#ttMonthly').attr('disabled',true);
		jQuery('#ttYearly').attr('disabled',true);
		
		jQuery('#ttDaily').attr('checked',false);
		jQuery('#ttWeekly').attr('checked',false);
		jQuery('#ttMonthly').attr('checked',false);
		jQuery('#ttYearly').attr('checked',false);

		document.getElementById('repeatOptionsDWMY').hidden = true;
		document.getElementById('repeatUnits').hidden = true;
		document.getElementById('weeklyOptions').hidden = true;
		document.getElementById('monthlyOptions').hidden = true;
		document.getElementById('yearlyOptions').hidden = true;

		jQuery('#aRepeatCode').attr('value','');		
	} else { //set default values for new record
		let element13 = document.getElementById("aAssignmentTypeDiv"); element13.removeAttribute("hidden");
		
		getUserNameIndivId();
//		console.log ('editAssignment');
//		data.isActive = "Y"; //Set new Assignment as an active assignment by default
//		data.code = $("#pNextAssignmentNumber").val();
		document.getElementById('aTimeOfDayH').value = ''
		document.getElementById('aTimeOfDayM').value = ''
		document.getElementById('aRepeatDiv').hidden = false;
		document.getElementById('repeatOptionsDWMY').hidden = false;
		document.getElementById("aCheckRepeat").checked = false;
	}
//	console.log("edit");
//	console.dir(data);
	assignmentTable.rows().deselect();

	$("#assignmentDialogHeader").html(header);	
	$("#aAssignmentId").val(data.assignmentId);                           		
	assignmentId = document.getElementById('aAssignmentId').value;
	$("#aProjectParticipantIdInstructor").val(data.projectParticipantIdInstructor);         			
	$("#aPpInstructorName").val(data.ppInstructorName);                      
	$("#aIndividualIdInstructor").val(data.individualIdInstructor);                 			
	$("#aIndividualInstructorName").val(data.individualInstructorName);               			
	$("#aProjectParticipantIdIndivResp").val(data.projectParticipantIdIndivResp);          			
	$("#aIndividualRespName").val(data.individualRespName);                    
	$("#aIndividualIdLogger").val(data.individualIdLogger);                     			
	$("#aIndividualLoggerName").val(data.individualLoggerName);                  		
//	$("#aAssignmentGroupId").val(data.assignmentGroupId);                     	
//	$("#aAssignmentGroupName").val(data.assignmentGroupName);                   			
	$("#aAssignmentNumber").val(data.assignmentNumber);                      			
	$("#aName").val(data.name);                          		    	
	$("#aDescription").val(data.description);                           			
	$("#aStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate)); //14 			
	$("#aTimeOfDay").val(data.timeOfDay);    

	$("#aRepeatCode").val(data.repeatCode);  
	
//	console.log('Rep: ' + jQuery('#aRepeatCode').attr('value'));	
	
//	$("#aLastUpdateTimestamp").val(data.lastUp;dateTimestamp);                   			
//	$("#aLastUpdateUserName").val(data.lastUpdateUserName);                    			
	$("#aProjectId").val(data.projectId);                              			
	$("#aProjectAssignmentNumber").val(data.projectAssignmentNumber);               			
	$("#aTitle").val(data.title);                                 		
	$("#aProjectName").val(data.projectName);     
	$("#aCompleted").prop("checked", data.completed == "Y");
	
//	console.log("startdatum: " + data.startDate);
//	console.log("startdatum2: " + timestampToString("2022-07-30", false));
//	setAdditionalTabStates(enabled);
	
	populateSelect("aAssignmentGroup", 
		       "/rest/ignite/v1/assignment-group", 
		       "assignmentGroupId", 
		       "name", 
		       data.assignmentGroupId, 
		       true,
		       null
     );
     
     populateSelect("aAssignmentType", 
		       "/rest/ignite/v1/assignment-type", 
		       "assignmentTypeId", 
		       "name", 
		       data.assignmentTypeId, 
		       true,
		       null 		       
	 );
	
	showModalDialog("assignmentDialog");
	if (jQuery('#aRepeatCode').attr('value') !== '') {
		document.getElementById("aCheckRepeat").checked = true;
	}
	if (!enabled) {
		repeatCheckClicked();
	}
	
	if (document.getElementById('aTimeOfDay').value != '') {
		var tyd = jQuery('#aTimeOfDay').attr('value');
//		console.log('tyd:' + tyd)
		document.getElementById('aTimeOfDayH').value = tyd.substring(11, 13);
		document.getElementById('aTimeOfDayM').value = tyd.substring(14, 16);
//		console.log(tyd.substring(11, 13))
	}
	
	if (!enabled) {
		var letter;
		letter = jQuery('#aRepeatCode').attr('value');
	//	console.log ('letter: ' + letter);
		
		if (letter.charAt(0) != '') {
			document.getElementById("aCheckRepeat").checked = true;
		}else {
			document.getElementById("aCheckRepeat").checked = false;
		}
		
		if (letter.charAt(0) == 'D') {
			jQuery('#ttDaily').attr('checked',true);
			document.getElementById('weeklyOptions').hidden = true;
			document.getElementById('monthlyOptions').hidden = true;
			document.getElementById('yearlyOptions').hidden = true;
		}
		if (letter.charAt(0) == 'W') {
			jQuery('#ttWeekly').attr('checked',true);
			document.getElementById('weeklyOptions').hidden = false;
			document.getElementById('monthlyOptions').hidden = true;
			document.getElementById('yearlyOptions').hidden = true;
			
			jQuery('#ttCheckSu').attr('checked',((letter.indexOf('Su') !== -1) ? true : false));
			jQuery('#ttCheckMo').attr('checked',((letter.indexOf('Mo') !== -1) ? true : false));
			jQuery('#ttCheckTu').attr('checked',((letter.indexOf('Tu') !== -1) ? true : false));
			jQuery('#ttCheckWe').attr('checked',((letter.indexOf('We') !== -1) ? true : false));
			jQuery('#ttCheckTh').attr('checked',((letter.indexOf('Th') !== -1) ? true : false));
			jQuery('#ttCheckFr').attr('checked',((letter.indexOf('Fr') !== -1) ? true : false));
			jQuery('#ttCheckSa').attr('checked',((letter.indexOf('Sa') !== -1) ? true : false))
					
		}
			
		if (letter.charAt(0) == 'M') {
			jQuery('#ttMonthly').attr('checked',true);
			document.getElementById('weeklyOptions').hidden = true;
			document.getElementById('monthlyOptions').hidden = false;
			document.getElementById('yearlyOptions').hidden = true;
					
	//		const $select = document.querySelector('#monthly1stDropdown');
			
			if(letter.indexOf('Date') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 1};
			if(letter.indexOf('First') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 2};
			if(letter.indexOf('Second') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 3};
			if(letter.indexOf('Third') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 4};
			if(letter.indexOf('Last') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 5};
			if(letter.indexOf('2nd Last') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 6};
			if(letter.indexOf('3rd Last') !== -1) {document.getElementById("monthly1stDropdown").selectedIndex = 7};
			
			var dieIndeks = document.getElementById('monthly1stDropdown').selectedIndex;
	//		
	//console.log ('indeks ' + dieIndeks);		
	
			if (dieIndeks == 1) {
				document.getElementById('ttMonthlyDropdown2').hidden = true;
				document.getElementById('ttMonthlyDropdown3').hidden = true;
				document.getElementById('ttMonthlyText').hidden = false;
				document.getElementById('everyMonthLabel').hidden = false;
				
				document.getElementById('ttDate').value = letter.substring(11, 13);
			}
			
			if ((dieIndeks > 1) && (dieIndeks < 6)) {
				document.getElementById('ttMonthlyDropdown2').hidden = false;
				document.getElementById('ttMonthlyDropdown3').hidden = true;
				document.getElementById('monthly3rdDropdown').value = 0
				document.getElementById('ttMonthlyText').hidden = true;
				document.getElementById('everyMonthLabel').hidden = false;
				document.getElementById('ttDate').value = '';
	
				if(letter.indexOf('-Day') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 1};
				if(letter.indexOf('Workday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 2};
				if(letter.indexOf('Sunday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 3};
				if(letter.indexOf('Monday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 4};
				if(letter.indexOf('Tuesday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 5};
				if(letter.indexOf('Wednesday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 6};
				if(letter.indexOf('Thursday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 7};
				if(letter.indexOf('Friday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 8};
				if(letter.indexOf('Saturday') !== -1) {document.getElementById("monthly2ndDropdown").selectedIndex = 9};
			}	
	
			if ((dieIndeks == 6) || (dieIndeks == 7)) {
				document.getElementById('ttMonthlyDropdown2').hidden = true;
				document.getElementById('ttMonthlyDropdown3').hidden = false;
				document.getElementById('monthly2ndDropdown').value = 0
				document.getElementById('ttMonthlyText').hidden = true;
				document.getElementById('everyMonthLabel').hidden = false;
				document.getElementById('ttDate').value = '';
	
				if(letter.indexOf('-Day') !== -1) {document.getElementById("monthly3rdDropdown").selectedIndex = 1};
				if(letter.indexOf('Workday') !== -1) {document.getElementById("monthly3rdDropdown").selectedIndex = 2};			
			}
			
	//		$select.value = ((letter.indexOf('First') !== -1) ? 2 : null);
	//		$select.value = ((letter.indexOf('Second') !== -1) ? 'Second' : null);
	//		$select.value = ((letter.indexOf('Third') !== -1) ? 'Third' : null);
	//		console.log ("Hier " + (letter.indexOf('Date')));
			
		}	
		if (letter.charAt(0) == 'Y') {
			jQuery('#ttYearly').attr('checked',true);
			document.getElementById('weeklyOptions').hidden = true;
			document.getElementById('monthlyOptions').hidden = true;
			document.getElementById('yearlyOptions').hidden = false;
			
			document.getElementById('ttDateY').value = letter.substring(2, 4);
	//		console.log (letter.substring(2, 4));		
			
			if(letter.indexOf('Jan') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 1};
			if(letter.indexOf('Feb') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 2};
			if(letter.indexOf('Mar') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 3};
			if(letter.indexOf('Apr') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 4};
			if(letter.indexOf('May') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 5};
			if(letter.indexOf('Jun') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 6};
			if(letter.indexOf('Jul') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 7};
			if(letter.indexOf('Aug') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 8};
			if(letter.indexOf('Sep') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 9};	
			if(letter.indexOf('Oct') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 10};
			if(letter.indexOf('Nov') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 11};
			if(letter.indexOf('Dec') !== -1) {document.getElementById("ttMonthDropdown").selectedIndex = 12};	
		}	
	}
	setAdditionalTabStates(enabled);
	setElementEnabled("saveGeneralTabButton", false);
	somethingChangedGeneralTab = false;
	askToSaveTab = false;
	
	if (enabled) {
//		console.log("wys");
		initializeTaskTable(assignmentId);
		wysVerkykers();
	} else {
//		console.log("hide");
		hideVerkykers();
	};
}
// editAssignment -- End



//*********************************************************
//editTask -- Start
//General Tab
function editTask(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
//	var enabled = false;
//	setAllSomethingChangedFlagsFalse();
	var header = "Task Detail";
	var assignmentId
	
	assignmentId = document.getElementById('aAssignmentId').value;

	
	
	
	if (rowSelector != null) {
		data = tTaskTable.row(rowSelector).data();
		enabled = true;
		header = "Task Detail: " + data.taskNumber + " " + data.name;	
	} else { //set default values for new record
		
//		console.log ('editTask::::');
		document.getElementById('tTimeOfDayH').value = ''
		document.getElementById('tTimeOfDayM').value = ''
	}
//	console.log("edit");
//	console.dir(data);
	tTaskTable.rows().deselect();

	$("#taskDialogHeader").html(header);	
	$("#tTaskId").val(data.taskId);                           		
	
	$("#tOrderNumber").val(data.orderNumber);         			
	$("#tTaskNumber").val(data.taskNumber);                                   			
	$("#tName").val(data.name);               			
	$("#tDescription").val(data.description);          			          			
	$("#tStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate)); //14 			
	$("#tTimeOfDay").val(data.timeOfDay);      
	
	$("#tDurationDays").val(data.DurationDays); 
	$("#tDurationHours").val(data.DurationHours);
	$("#tCompleted").prop("checked", data.completed == "Y");
	
                   			            
//	setAdditionalTabStates(enabled);
	
  
  populateSelect("tTaskImportanceType", 
		       "/rest/ignite/v1/task-importance-type", 
		       "taskImportanceTypeId", 
		       "name", 
		       data.taskImportanceTypeId,   //data.taskImportanceTypeId, 
		       true,
		       null 		       
	 );
	
	
	
	
	showModalDialog("taskDialog");

	if (document.getElementById('tTimeOfDay').value != '') {
		tTime = document.getElementById('tTimeOfDay').value;
		if (onlyDigits(tTime)) {
//			console.log("Dis 'n datum");	
//			console.log(prettyDate2(tTime));
//			var d = new Date();
			var aMpm
			var minuut;
			var  uur;
			var NuweTyd = veranderNaDatum(tTime).toString();	
			aMpm = NuweTyd.substr(NuweTyd.length -2);
			var y = NuweTyd.indexOf(':00');
			sonderSekondes =  NuweTyd.substring(0, y);
			minuut = sonderSekondes.substr(sonderSekondes.length -2);
					
//			console.log("aMpm: " + aMpm);
//			console.log("sonderSekondes: " + sonderSekondes);
//			console.log("minuut: " + minuut);

			var z = sonderSekondes.indexOf(':');
			uur =  sonderSekondes.substring(0, z);
			if (aMpm == "PM")
//			console.log("uur: " + uur);
			
			//uur ? aMpm == "PM" : uur + 12
			if (aMpm = "PM") {
				uur = parseInt(uur) + 12
			}
//			console.log("uur: " + uur);
			
			document.getElementById('tTimeOfDayH').value = uur;
			document.getElementById('tTimeOfDayM').value = minuut;
			

			
//			if(minute > 0)  
//			     alert(hour+"."+minute);                  //const before = str.substring(0, str.indexOf(':'));
//			else                                          //str.substring(0, str.lastIndexOf('_'));
//			     alert(hour);
		} else {
			var tyd = jQuery('#tTimeOfDay').attr('value');
//			console.log('tyd:' + tyd)
			document.getElementById('tTimeOfDayH').value = tyd.substring(11, 13);
			document.getElementById('tTimeOfDayM').value = tyd.substring(14, 16);
//			console.log(tyd.substring(11, 13))
		}
	}
	
	document.getElementById("blouSaveTaskTabButton").disabled = true;
//	setAdditionalTabStates(enabled);
//	setElementEnabled("saveGeneralTabButton", false);
//	somethingChangedGeneralTab = false;
//	askToSaveTab = false;

}
//editTask -- End


function editTaskRevision(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
//	var enabled = false;
//	setAllSomethingChangedFlagsFalse();
	var header = "Task Revision Detail";
	var assignmentId
	
	assignmentId = document.getElementById('aAssignmentId').value;
	
	if (rowSelector != null) {
		data = taskRevisionTable.row(rowSelector).data();
		enabled = true;
		header = "Task Detail: " + data.taskNumber + " " + data.name;	
	} else { //set default values for new record
		enabled = false

	}
//	console.log("edit");
//	console.dir(data);
	taskRevisionTable.rows().deselect();

	$("#taskRevisionDialogHeader").html(header);	
	$("#rTaskRevisionId").val(data.taskRevisionId);  
	$("#rTaskId").val(document.getElementById('revPanelTaskId').value);         //revPanelTaskId                         		
	
	$("#rRevisionNumber").val(data.revisionNumber);    
	
	$("#rRevisionDate").datepicker("setDate", data.revisionDate == null? timestampToString(new Date(), false) : new Date(data.revisionDate)); //
	$("#rRevisionName").val(data.name);               			
	$("#rRevisionDescription").val(data.description);    
	
	$("#rNeededCompletionDate").datepicker("setDate", data.neededCompletionDate == null? timestampToString(new Date(), false) : new Date(data.neededCompletionDate)); //
	$("#rExpectedCompletionDate").datepicker("setDate", data.expectedCompletionDate == null? timestampToString(new Date(), false) : new Date(data.expectedCompletionDate)); //
			
                   			            
//	setAdditionalTabStates(enabled);

	
	
	
	
	showModalDialog("taskRevisionDialog");

	if (enabled == false) {
//		console.log("enabled is vals")
		getNextRevisionNumber();
	}
	
	
	document.getElementById("blouSaveTaskRevisionTabButton").disabled = true;
//	setAdditionalTabStates(enabled);
//	setElementEnabled("saveGeneralTabButton", false);
//	somethingChangedGeneralTab = false;
//	askToSaveTab = false;

}
//editTaskRevision -- End


function getNextRevisionNumber() {
	
	var queryUrl;
	var taskId = $("#revPanelTaskId").val()
	
	queryUrl = "/rest/ignite/v1/task-revision/next-revision-number/" + taskId;
	
//	console.log("/////rest/ignite/v1/task-submission/next-submission-number/" + taskRevisionId);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
//			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#rRevisionNumber").val(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
	var nextNumber = document.getElementById('rRevisionNumber').value;	

//	if ((nextNumber == null) || (nextNumber == "")) {
////		console.log("nextNumberrr: " + nextNumber);
//		document.getElementById("rRevisionNumber").value = '1'; 
////		console.log(document.getElementById("sSubmissionNumber").value);
//	}
};




function editTaskSubmission(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
//	var enabled = false;
//	setAllSomethingChangedFlagsFalse();
	var header = "Task Submission Detail";
	var assignmentId
	
	assignmentId = document.getElementById('aAssignmentId').value;
	
	if (rowSelector != null) {
		data = taskSubmissionTable.row(rowSelector).data();
		enabled = true;
		header = "Task Submission Detail: " + data.taskNumber + " " + data.name;	
	} else { //set default values for new record
		
	}
//	console.log("editTASKsubmission");
//	console.dir(data);
	taskSubmissionTable.rows().deselect();

	$("#taskRevisionDialogHeader").html(header);
	$("#sTaskSubmissionId").val(data.taskSubmissionId); 
	
	
	$("#sTaskRevisionId").val(data.taskRevisionId);                          		
	
	$("#sSubmissionNumber").val(data.submissionNumber);    
	
	$("#sSubmissionDate").datepicker("setDate", data.submissionDate == null? timestampToString(new Date(), false) : new Date(data.submissionDate)); //

	$("#sAcceptedflag").val(data.acceptedflag);
	
		$("#sAccept").prop("checked", data.acceptedflag == "Y");
		$("#sReject").prop("checked", data.acceptedflag == "R");
	
	$("#sComment").val(data.comment);  
	$("#sFeedback").val(data.feedback); 
                   			            
//	setAdditionalTabStates(enabled);

	
	
	
	
	showModalDialog("taskSubmissionDialog");

	
	
	
	document.getElementById("blouSaveTaskSubmissionTabButton").disabled = true;
//	setAdditionalTabStates(enabled);
//	setElementEnabled("saveGeneralTabButton", false);
//	somethingChangedGeneralTab = false;
//	askToSaveTab = false;

}
//editTaskRevision -- End



function veranderNaDatum(time){
    var date = new Date(parseInt(time));
    var localeSpecificTime = date.toLocaleTimeString();
//console.log(localeSpecificTime.replace(/:\d+ /, ' '))    ;
    return localeSpecificTime//.replace(/:\d+ /, ' ');
}

function onlyDigits(s) {
	  for (let i = s.length - 1; i >= 0; i--) {
	    const d = s.charCodeAt(i);
	    if (d < 48 || d > 57) return false
	  }
	  return true
}


function selectProjectId() {
	console.log("hierCode:    /rest/ignite/v1/project/list");
	var queryUrl="/rest/ignite/v1/project/list";
	
	var targetId = "aProjectId";
	var targetName = "aProjectName";
	 
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectId";
			var refColumnName="projectId";
			var columns = [
				{ data: "projectId", name: "Id" },
				{ data: "projectNumberBigInt", name: "Project Number" },
				{ data: "projectNumberText", name: "Project Number" },
				{ data: "title", name: "Title" },
				{ data: "subProjNumber", name: "Sub Project" },
				{ data: "description", name: "Description" },
				{ data: "isActive", name: "Is Active" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0,1]
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectId;
				var project = leadingZeroPad(row.projectNumberBigInt, 4) + " " + row.title;

				$("#" + targetId).val(id);
				$("#" + targetName).val(project);
				
				wysVerkykers();
				getNextAssignmentNumber()				
				//console.log("2aAssignmentNumber: " + document.getElementById("aAssignmentNumber").value);
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


function getNextAssignmentNumber() {
	
	var queryUrl;
	var projectId = jQuery('#aProjectId').attr('value');
	
	queryUrl = "/rest/ignite/v1/assignment/next-assignment-number/" + projectId;
	
	console.log("/////  rest/ignite/v1/assignment/next-assignment-number/" + projectId);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			console.dir(data);
//			console.log('abc');
//			console.log('DieID: ' + data.individualId);
			$("#aAssignmentNumber").val(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	
//	var nextNumber = document.getElementById('aAssignmentNumber').value;	
	var nextNumber = jQuery('#aAssignmentNumber').attr('value');

	if ((nextNumber == null) || (nextNumber == "")) {
//		console.log("nextNumberrr: " + nextNumber);
		
//		document.getElementById("aAssignmentNumber").value = "1"; 
//		document.getElementById('aAssignmentNumber').value = '1';
		$("#aAssignmentNumber").val(1);
		
//		console.log("111111aAssignmentNumber: " + $("#aAssignmentNumber").val());
	}
};


function selectProjectParticipantIdInstructor() {
	
	var projectId = $("#aProjectId").val();
	var queryUrl="/rest/ignite/v1/assignment/project-participants/" + projectId;
	
	var targetId = "aProjectParticipantIdInstructor";
	var targetName = "aPpInstructorName";
	var targetId2 = "aIndividualIdInstructor";
	var targetName2 = "aIndividualInstructorName";
			
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
//console.dir(data);		
			
			var columnName="projectParticipantId";
			var refColumnName="systemNamePayer";
			var columns = [
				{ data: "projectParticipantId", name: "ppId" },
				{ data: "systemNamePayer", name: "Name" },
				{ data: "representativeIndividualId", name: "representativeIndividualId" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "", name: "Representative" },
				{ data: "isIndividual", name: "isIndividual" },
				{ data: "individualId", name: "individualId" },
				{ data: "firstName2", name: "First Name2" },
				{ data: "lastName2", name: "Last Name2" }
			];
			
			var columnDefs = [
//			   { 
////					visible: false,
////					targets: [0, 2, 3, 4, 6, 7, 8, 9]
//			   },
			   
			   {
					render: function (data, type, row) {
						var data = "";
						
						if ((row.firstName != null) && (row.lastName != null)){//(row.hasOwnProperty("firstName") && row.hasOwnProperty("lastName")){
							data = row.firstName + " " + row.lastName;
						}else {
							if ((row.firstName2 != null) && (row.lastName2 != null)){
								data = row.firstName2 + " " + row.lastName2;
							}
						}
						return data;
					},
					targets: 5
				}		   
			];

			var id2;
			var RepName;

									
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectParticipantId;
				var projectP = row.systemNamePayer;
				if (row.representativeIndividualId != null) {
					id2 = row.representativeIndividualId;
					RepName = row.lastName == null ? "" : row.firstName + " " + row.lastName;
				}else {
					if (row.individualId != null) {
						id2 = row.individualId;
						RepName = row.lastName2 == null ? "" : row.firstName2 + " " + row.lastName2;
					}
				};
				
				$("#" + targetId).val(id);
				$("#" + targetName).val(projectP);
				$("#" + targetId2).val(id2);
				$("#" + targetName2).val(RepName);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
	generalTabChanged();
}



function selectIndividualInstructor() {
	
	var queryUrl="/rest/ignite/v1/individual/list";
	var targetId = "aIndividualIdInstructor";
	var targetName = "aIndividualInstructorName";
	
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
	generalTabChanged();
}

function selectResponsiblePerson() {
	
	var projectId = $("#aProjectId").val();
	var queryUrl  = "/rest/ignite/v1/assignment/project-participants-indiv/" + projectId;
	var targetId  = "aProjectParticipantIdIndivResp";
	var targetName = "aIndividualRespName";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="lastName2";
			var columns = [
				{ data: "projectParticipantId", name: "Id" },
				{ data: "firstName2", name: "First Name" },
				{ data: "lastName2", name: "Last Name" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: 0
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectParticipantId;
				var repName = row.firstName2 + " " + row.lastName2;

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
	generalTabChanged();
}


function wysVerkykers() {
	let element1 = document.getElementById("ppIdInstructorBino");        	element1.removeAttribute("hidden");
	let element2 = document.getElementById("individualIdInstructorBino");	element2.removeAttribute("hidden");
	let element3 = document.getElementById("ppIdIndivRespBino");         	element3.removeAttribute("hidden");
}

function hideVerkykers() {
	let element1 = document.getElementById("ppIdInstructorBino");        	element1.setAttribute("hidden", "hidden");
	let element2 = document.getElementById("individualIdInstructorBino");	element2.setAttribute("hidden", "hidden");
	let element3 = document.getElementById("ppIdIndivRespBino");         	element3.setAttribute("hidden", "hidden");
}



function paddy(num, padlen, padchar) {
    var pad_char = typeof padchar !== 'undefined' ? padchar : '0';
    var pad = new Array(1 + padlen).join(pad_char);
    return (pad + num).slice(-pad.length);
}



// saveAssignment -- Begin
function saveAssignmentGeneral() {
	
	var disnNuweEen = false;
	
	if (($("#aAssignmentNumber").val() == null) || ($("#aAssignmentNumber").val() == "")) {
		$("#aAssignmentNumber").val(1)
	}
	
	var postUrl = "/rest/ignite/v1/assignment";
	
	var postData = {
			assignmentId: $("#aAssignmentId").val() == ""? null: $("#aAssignmentId").val(),						
			projectParticipantIdInstructor:	$("#aProjectParticipantIdInstructor").val(),  					
			individualIdInstructor:	$("#aIndividualIdInstructor").val(),  					
			projectParticipantIdIndivResp:	$("#aProjectParticipantIdIndivResp").val(), 					
			individualIdLogger:	$("#aIndividualIdLogger").val(),  					
			assignmentGroupId:	$("#aAssignmentGroup").val(),     					
			assignmentNumber:	$("#aAssignmentNumber").val(),  					
			name:	$("#aName").val(),                          					
			description:	$("#aDescription").val(),   					
			startDate: getMsFromDatePicker("aStartDate"),						
			timeOfDay:	$("#aTimeOfDay").val(),                  					
			repeatCode:	$("#aRepeatCode").val(),
			completed : $("#aCompleted").is(":checked") ? "Y" : "N"			
            				
	
	};
//	console.dir(postData);
	
	var errors = "";

	// validate
	if ((postData.startDate == "") || (postData.startDate == null)){
		// Add Today's date without time
		$("#aStartDate").val(timestampToString(new Date(), false));		
		postData.startDate = getMsFromDatePicker("aStartDate");
	}


	
	if (postData.projectParticipantIdInstructor == "") {
		errors += "Please select a Project and the Project Participant Instructor<br>";
	}
	if (postData.individualIdInstructor == "") {
		errors += "Please select the Individual Instructor<br>";
	}
	if (postData.projectParticipantIdIndivResp == "") {
		errors += "Please select the Responsible Person<br>";
	}
	
	
	
	if (postData.name == "") {
		errors += "An Assignment Name is required<br>";
	}
	if (postData.assignmentNumber == "") {
		errors += "An Assignment Number is required<br>";
	}
	
	if (document.getElementById('aCheckRepeat').checked){
		if ((document.getElementById('ttDaily').checked == false) && (document.getElementById('ttWeekly').checked == false) && (document.getElementById('ttMonthly').checked == false) && (document.getElementById('ttYearly').checked == false) ) {
			errors += "If you want the Assignment repeated, please select Daily, Weekly, Monthly or Yearly<br>";
		}
		if (($("#aUnits").val() == "") || ($("#aUnits").val() == null)) {
			if ((document.getElementById('ttDaily').checked) || (document.getElementById('ttWeekly').checked) || (document.getElementById('ttMonthly').checked) || (document.getElementById('ttYearly').checked) ) {
				errors += "Please enter the number of repetitions you need<br>";
			}
		}
	}
	
	
	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	if (postData.assignmentId == null) {
		postUrl = "/rest/ignite/v1/assignment/new";
		disnNuweEen = true
	}
	
	

	
	
	//console.log (postUrl);
	//console.log("Hier: " + document.getElementById('aAssignmentType').value);
	var assignmentTypeId = document.getElementById('aAssignmentType').value;
	
	saveEntry(postUrl, postData, null, "The Assignment has been saved.", assignmentTable, function(request, response){
		var data = response;
		//console.dir(data);
		var assignmentId = data.assignmentId;
		$("#aAssignmentId").val(assignmentId);
		
		$("#assignmentDialogHeader").html("Assignment Detail: " + data.assignmentNumber + " " + data.name);
		
		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful
//		setElementEnabled("saveGeneralTabButton", false);
		somethingChangedGeneralTab = false;
		askToSaveTab = false;
//		selectNextAssignmentNumber();
			
		let element13 = document.getElementById("aAssignmentTypeDiv"); 
		element13.setAttribute("Hidden", "hidden");
		
		if ((assignmentTypeId == null) || (assignmentTypeId == '')) {
			assignmentTypeId = 0
		}
		
	
//		myFunctionToSaveTaskTypes(assignmentTypeId, assignmentId);	

		var postUrl = "/rest/ignite/v1/assignment/insert-tasks"    //+ assignmentTypeId + "/" + assignmentId;
				
		var postData = {assignmentTypeId,
						assignmentId
				}
		
		if (disnNuweEen == true) {
			saveEntry(postUrl, postData, null, "Tasks has been saved.", null,  function(request, response){
			
	//			projectParticipantTable.rows().deselect();
	//			var projectId = $("#pdProjectId").val();
				
	//			var requestUrl="/rest/ignite/v1/assignment/tasks/" + assignmentId;			    
	//			tTaskTable.ajax.url( springUrl(requestUrl) ).load();	
				initializeTaskTable(assignmentId);
				
			});
		}
	});
	
}// saveAssignment -- End





function saveTask() {
	
	var postUrl = "/rest/ignite/v1/task";
	
	var postData = {
			taskId: $("#tTaskId").val() == ""? null: $("#tTaskId").val(),		
			assignmentId: $("#aAssignmentId").val(),				  	
			taskImportanceTypeId:	$("#tTaskImportanceType").val() == ""? null: $("#tTaskImportanceType").val(),	
			orderNumber: $("#tOrderNumber").val(),  					
			taskNumber:	$("#tTaskNumber").val(), 										
			name:	$("#tName").val(),                          					
			description:	$("#tDescription").val(),   					
			startDate: getMsFromDatePicker("tStartDate"),			
			durationDays:	$("#tDurationDays").val(),   									
			durationHours:	$("#tDurationHours").val(), 					
			timeOfDay:	$("#tTimeOfDay").val(),
			completed : $("#tCompleted").is(":checked") ? "Y" : "N" 			  			 				
	};
	
//	console.dir("postData hier");
//	console.dir(postData);

	var errors = "";

	// validate
	if ((postData.startDate == "") || (postData.startDate == null)){
		// Add Today's date without time
		$("#tStartDate").val(timestampToString(new Date(), false));		
		postData.startDate = getMsFromDatePicker("tStartDate");
	}

	if (postData.name == "") {
		errors += "A Task Name is required<br>";
	}

	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	if (postData.taskId == null) {
		postUrl = "/rest/ignite/v1/task/new";
	}
	
	//console.log (postUrl);

	
	saveEntry(postUrl, postData, "taskDialog", "The Task has been saved.", tTaskTable); //, function(request, response));
//		var data = response;
//		//console.dir(data);
//		var taskId = data.taskId;
//		$("#tTaskId").val(taskId);
//		
//		$("#taskDialogHeader").html("Task Detail: " + data.orderNumber + " " + data.name);
//		
////		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful
//////		setElementEnabled("saveGeneralTabButton", false);
////		somethingChangedGeneralTab = false;
////		askToSaveTab = false;
//		
//		
//		
//	});
	


}
// saveTask -- End



function saveTaskRevision() {
	
	var postUrl = "/rest/ignite/v1/task-revision";
	
	var postData = {
			taskRevisionId: $("#rTaskRevisionId").val() == ""? null: $("#rTaskRevisionId").val(),		
			taskId: $("#rTaskId").val(),			
			
			revisionNumber: $("#rRevisionNumber").val(),  				
			revisionDate: getMsFromDatePicker("rRevisionDate"),										
			name:	$("#rRevisionName").val(),                          					
			description:	$("#rRevisionDescription").val(),   
			
			neededCompletionDate: getMsFromDatePicker("rNeededCompletionDate"),							
			expectedCompletionDate: getMsFromDatePicker("rExpectedCompletionDate"),			  			 				
	};
	
//	console.dir("postData hier");
//	console.dir(postData);

	var errors = "";

	// validate
	if ((postData.revisionDate == "") || (postData.revisionDate == null)){
		// Add Today's date without time
		$("#rRevisionDate").val(timestampToString(new Date(), false));		
		postData.revisionDate = getMsFromDatePicker("rRevisionDate");
	}

	if (postData.name == "") {
		errors += "A Revision Name is required<br>";
	}

	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

	if (postData.taskRevisionId == null) {
		postUrl = "/rest/ignite/v1/task-revision/new";
	}
	
	//console.log (postUrl);

	
	saveEntry(postUrl, postData, "taskRevisionDialog", "The Task Revision has been saved.", taskRevisionTable); //, function(request, response));
//		var data = response;
//		//console.dir(data);
//		var taskId = data.taskId;
//		$("#tTaskId").val(taskId);
//		
//		$("#taskDialogHeader").html("Task Detail: " + data.orderNumber + " " + data.name);
//		
////		setAdditionalTabStates(true);  // Executes this anonymous function after the Save was successful
//////		setElementEnabled("saveGeneralTabButton", false);
////		somethingChangedGeneralTab = false;
////		askToSaveTab = false;
//		
//		
//		
//	});
	


}
// saveTaskRevision -- End




function saveTaskSubmission() {
	
	var postUrl = "/rest/ignite/v1/task-submission";
	
	var postData = {
			taskSubmissionId: $("#sTaskSubmissionId").val() == ""? null: $("#sTaskSubmissionId").val(),		
			taskRevisionId: $("#sTaskRevisionId").val(),			
			
			submissionNumber: $("#sSubmissionNumber").val(),  				
			submissionDate: getMsFromDatePicker("sSubmissionDate"),	
			
			acceptedflag:	$("#sAcceptedflag").val(),      
			comment:		$("#sComment").val(),  
			feedback:		$("#sFeedback").val()		  			 				
	};
	
//	console.dir("postData hier");
//	console.dir(postData);

	var errors = "";

	// validate

	if (showFormErrors("aDlgErrorDiv", errors)) {
		return;
	}

//	if (postData.taskSubmissionId == null) {
//		postUrl = "/rest/ignite/v1/task-submission/new";
//	}
		
	//console.log (postUrl);
	
	saveEntry(postUrl, postData, "taskSubmissionDialog", "The Task Submission has been saved.", taskSubmissionTable); //, function(request, response));
	
	if (jQuery('#sAcceptedflag').attr('value') == 'Y') {
		markTaskAsCompleted()
	};
	
	
	
}
// saveTaskSubmission -- End



function markTaskAsCompleted() {

	var taskId = jQuery('#revPanelTaskId').attr('value');
	var assignmentId = jQuery('#aAssignmentId').attr('value');
			
	
console.log("taskId: " + taskId)	;		

	var postUrl = "/rest/ignite/v1/task-submission/set-task-complete"    //+ assignmentTypeId + "/" + assignmentId;
		
		var postData = {
				taskId
		}
		
		if ((postData.taskId == "") || (postData.taskId == null)) {
			console.log("Fout 2903<br>")
			errors += "Fout 2903<br>";
		} else {
			console.log("postData.taskId: " + postData.taskId)
		}
			
	//console.log("atID: " + assignmentTypeId + "    Aid: " + assignmentId);
	//console.log("urlgepos: " + postUrl);
	//console.dir("postData: " + postData);

		saveEntry(postUrl, postData, "detailDialog", "The Task has been marked as completed.", null);
		
		console.log("assignmentId: " + assignmentId);
		initializeTaskTable(assignmentId);
		initializeAssignmentTable()

}//markTaskAsCompleted --End



function setAdditionalTabStates(enabled) {
	
	setDivVisibility("tTaskTabLink", enabled? "block":"none");
//	setDivVisibility("pdAssignmentParticipantsTabLink", enabled? "block":"none");
//	setDivVisibility("pdAssignmentStagesTabLink", enabled? "block":"none");
	
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
	if ((somethingChangedGeneralTab) || (askToSaveTab)) {
		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
		if (stayAndSaveFirst) {
			//Moenie toemaak nie... save eers
			askToSaveTab = false;
		} else {
			setActiveTab("generalLink");
			setAllSomethingChangedFlagsFalse();
			closeModalDialog("assignmentDialog");
			//selectRowThatWasEdited(assignmentTable, $("#aAssignmentId").val());
		}
	} else  {
		setActiveTab("generalLink");
		setAllSomethingChangedFlagsFalse();
		closeModalDialog("assignmentDialog");
		//selectRowThatWasEdited(assignmentTable, $("#aAssignmentId").val());
	} 
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




function updateTaskToolbarButtons() {
	var hasSelected = tTaskTable.rows('.selected').data().length > 0;
	setTableButtonState(tTaskTable, "promptDeleteTaskBtn", hasSelected);	
}

function updateTaskRevisionToolbarButtons() {
	var hasSelected = taskRevisionTable.rows('.selected').data().length > 0;
	setTableButtonState(taskRevisionTable, "promptDeleteTaskRevisionBtn", hasSelected);	
}



function promptDeleteTask() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Task?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteTask(tTaskTable);
			   }
	);
}


function promptDeleteTaskRevision() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Task Revision?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteTaskRevision(taskRevisionTable);
			   }
	);
}



function deleteTask(tbl) {
	var postUrl = "/rest/ignite/v1/task/delete";
	var row = tbl.rows('.selected').data()[0];
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Task has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateTaskToolbarButtons
			}
	);
}

function deleteTaskRevision(tbl) {
	var postUrl = "/rest/ignite/v1/task-revision/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Task Revision has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateTaskRevisionToolbarButtons
			}
	);
}





function repeatCheckClicked() {
	
	var checkBox = document.getElementById("aCheckRepeat");
	
	if (checkBox.checked == true){
		jQuery('#ttDaily').attr('disabled',false);
		jQuery('#ttWeekly').attr('disabled',false);
		jQuery('#ttMonthly').attr('disabled',false);
		jQuery('#ttYearly').attr('disabled',false);
		
		document.getElementById('repeatOptionsDWMY').hidden = false;
		
	  } else {
		jQuery('#ttDaily').attr('disabled',true);
		jQuery('#ttWeekly').attr('disabled',true);
		jQuery('#ttMonthly').attr('disabled',true);
		jQuery('#ttYearly').attr('disabled',true);
		
		jQuery('#ttDaily').attr('checked',false);
		jQuery('#ttWeekly').attr('checked',false);
		jQuery('#ttMonthly').attr('checked',false);
		jQuery('#ttYearly').attr('checked',false);

		document.getElementById('repeatOptionsDWMY').hidden = true;
		document.getElementById('repeatUnits').hidden = true;
		document.getElementById('weeklyOptions').hidden = true;
		document.getElementById('monthlyOptions').hidden = true;
		document.getElementById('yearlyOptions').hidden = true;

		jQuery('#aRepeatCode').attr('value','');
	  };
	generalTabChanged();
} //repeatCheckClicked





function checkDateAgainstMonth() {
	
	var dieMaand = document.getElementById('ttMonthDropdown').value;
	var dieDag = +document.getElementById('ttDateY').value;
	
	if ((dieMaand != "0") && (dieDag != 0)) {

		var maand31 = ["January", "March", "May", "July", "August", "October", "December"];
		var maand30 = ["April", "June", "September", "November"];

		var maandHet31 = maand31.includes(dieMaand);
		var maandHet30 = maand30.includes(dieMaand);
	
		if ((!maandHet31) && (!maandHet30)) {var maandHet28 = true}
		
		if ((maandHet31) && (dieDag > 31)) { document.getElementById('ttDateY').value = '31'}
		
		if ((maandHet30) && (dieDag > 30)) { document.getElementById('ttDateY').value = '30'}
		
		if ((maandHet28) && (dieDag > 28)) { document.getElementById('ttDateY').value = '28'}

		var dieNommerFinaal
		var element = document.getElementById('ttDateY');  //$("#aTimeOfDayH").val();   //document.getElementById('aTimeOfDayH');  //
	    var dieLengte = element.value.length;
	    var dieNommer = element.value;
	    if (dieLengte == 1) {
	    	dieNommerFinaal = '0' + dieNommer
	    }else {
	    	dieNommerFinaal = dieNommer
	    };
		
		document.getElementById('aRepeatCode').value = 'Y-' + dieNommerFinaal + '-' + dieMaand
		
	}	
}



function dagVanDieMaandKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('ttDate').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDate').value = '';
		document.getElementById('aRepeatCode').value = '';
		return;
		
	} else {
		if (dieNommer > 31) {
			document.getElementById('ttDate').value = '';
			document.getElementById('aRepeatCode').value = ''
			return;
		}
	}
	getRepeatCodeForMonth()
}	



function dagVanDieMaandYearlyKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('ttDateY').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDateY').value = ''
		document.getElementById('aRepeatCode').value = ''
		return;
	} else {
		if (dieNommer > 31) {
			document.getElementById('ttDateY').value = '';
			document.getElementById('aRepeatCode').value = ''
			return;
		}
	};
	
	checkDateAgainstMonth(document.getElementById('ttMonthDropdown').value);

}	


function aUnitsKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('aUnits').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('aUnits').value = ''
		return;
	};
	
}	



function durationDaysKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('ttDlgDurationDays').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDlgDurationDays').value = ''
//				console.log('hier')
	};
	if (dieNommer > 0) {
		document.getElementById('ttDlgDurationHours').value = ''
	}
}	


function durationHoursKeyup() {	
	var dieNommer;
	dieNommer = +document.getElementById('ttDlgDurationHours').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('ttDlgDurationHours').value = ''
	};
	if (dieNommer > 0) {
		document.getElementById('ttDlgDurationDays').value = ''
	}
}	




function getWeeklyDayOptions() {	
	
	document.getElementById('aRepeatCode').value = 'W-'
	
	var checkBoxSu = document.getElementById("ttCheckSu");
	var checkBoxMo = document.getElementById("ttCheckMo");
	var checkBoxTu = document.getElementById("ttCheckTu");
	var checkBoxWe = document.getElementById("ttCheckWe");
	var checkBoxTh = document.getElementById("ttCheckTh");
	var checkBoxFr = document.getElementById("ttCheckFr");
	var checkBoxSa = document.getElementById("ttCheckSa");
	
	if (checkBoxSu.checked == true){document.getElementById('aRepeatCode').value += 'Su-'};
	if (checkBoxMo.checked == true){document.getElementById('aRepeatCode').value += 'Mo-'};
	if (checkBoxTu.checked == true){document.getElementById('aRepeatCode').value += 'Tu-'};
	if (checkBoxWe.checked == true){document.getElementById('aRepeatCode').value += 'We-'};
	if (checkBoxTh.checked == true){document.getElementById('aRepeatCode').value += 'Th-'};
	if (checkBoxFr.checked == true){document.getElementById('aRepeatCode').value += 'Fr-'};
	if (checkBoxSa.checked == true){document.getElementById('aRepeatCode').value += 'Sa-'};
	generalTabChanged()
			
}	

function timeOfDayKeyupH() {
	var dieNommer;
	var dieUur;
	var dieMin;
	var dieUurString;
	var dieMinString;
	
	dieNommer = +document.getElementById('aTimeOfDayH').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('aTimeOfDayH').value = ''
	}else{
		if (dieNommer > 23) {
			document.getElementById('aTimeOfDayH').value = ''
		}
	};	
	if ((document.getElementById('aTimeOfDayH').value != "") && (document.getElementById('aTimeOfDayM').value != "")) {
		dieUur = +document.getElementById('aTimeOfDayH').value;
		if (dieUur<10) {
			dieUurString = "0" + dieUur
		} else {
			dieUurString = dieUur
		}
		dieMin = +document.getElementById('aTimeOfDayM').value;
		if (dieMin<10) {
			dieMinString = "0" + dieMin
		} else {
			dieMinString = dieMin
		}
		document.getElementById('aTimeOfDay').value = '1970-01-01T' + dieUurString + ":" + dieMinString + ":00"
	} else {
		document.getElementById('aTimeOfDay').value = ''
	}
}

function timeOfDayKeyupM() {
	var dieNommer;
	var dieUur;
	var dieMin;
	var dieUurString;
	var dieMinString;
	dieNommer = +document.getElementById('aTimeOfDayM').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('aTimeOfDayM').value = ''
	}else{
		if (dieNommer > 59) {
			document.getElementById('aTimeOfDayM').value = ''
		}
	};	
	if ((document.getElementById('aTimeOfDayH').value != "") && (document.getElementById('aTimeOfDayM').value != "")) {
		dieUur = +document.getElementById('aTimeOfDayH').value;
		if (dieUur<10) {
			dieUurString = "0" + dieUur
		} else {
			dieUurString = dieUur
		}
		dieMin = +document.getElementById('aTimeOfDayM').value;
		if (dieMin<10) {
			dieMinString = "0" + dieMin
		} else {
			dieMinString = dieMin
		}
		document.getElementById('aTimeOfDay').value = '1970-01-01T' + dieUurString + ":" + dieMinString + ":00"
	} else {
		document.getElementById('aTimeOfDay').value = ''
	}
}


function tTimeOfDayKeyupH() {
	var dieNommer;
	var dieUur;
	var dieMin;
	var dieUurString;
	var dieMinString;
	
	dieNommer = +document.getElementById('tTimeOfDayH').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('tTimeOfDayH').value = ''
	}else{
		if (dieNommer > 23) {
			document.getElementById('tTimeOfDayH').value = ''
		}
	};	
	if ((document.getElementById('tTimeOfDayH').value != "") && (document.getElementById('tTimeOfDayM').value != "")) {
		dieUur = +document.getElementById('tTimeOfDayH').value;
		if (dieUur<10) {
			dieUurString = "0" + dieUur
		} else {
			dieUurString = dieUur
		}
		dieMin = +document.getElementById('tTimeOfDayM').value;
		if (dieMin<10) {
			dieMinString = "0" + dieMin
		} else {
			dieMinString = dieMin
		}
		document.getElementById('tTimeOfDay').value = '1970-01-01T' + dieUurString + ":" + dieMinString + ":00.0000"
	} else {
		document.getElementById('tTimeOfDay').value = ''
	}
	taskTabChanged()
}

function tTimeOfDayKeyupM() {
	var dieNommer;
	var dieUur;
	var dieMin;
	var dieUurString;
	var dieMinString;
	dieNommer = +document.getElementById('tTimeOfDayM').value;
	if (Number.isInteger(dieNommer) == false) {
		document.getElementById('tTimeOfDayM').value = ''
	}else{
		if (dieNommer > 59) {
			document.getElementById('tTimeOfDayM').value = ''
		}
	};	
	if ((document.getElementById('tTimeOfDayH').value != "") && (document.getElementById('tTimeOfDayM').value != "")) {
		dieUur = +document.getElementById('tTimeOfDayH').value;
		if (dieUur<10) {
			dieUurString = "0" + dieUur
		} else {
			dieUurString = dieUur
		}
		dieMin = +document.getElementById('tTimeOfDayM').value;
		if (dieMin<10) {
			dieMinString = "0" + dieMin
		} else {
			dieMinString = dieMin
		}
		document.getElementById('tTimeOfDay').value = '1970-01-01T' + dieUurString + ":" + dieMinString + ":00.0000"
	} else {
		document.getElementById('tTimeOfDay').value = ''
	}
	taskTabChanged()
}




function ttDailyClicked() {

	document.getElementById('repeatUnits').hidden = false;
	$("#aUnitsName").html("days");
	document.getElementById('aRepeatCode').value = 'D-'
	document.getElementById('weeklyOptions').hidden = true;
	document.getElementById('monthlyOptions').hidden = true;
	document.getElementById('yearlyOptions').hidden = true;
	generalTabChanged()
}

function ttWeeklyClicked() {

	document.getElementById('repeatUnits').hidden = false;
	$("#aUnitsName").html("weeks");
	document.getElementById('aRepeatCode').value = 'W-'
	document.getElementById('weeklyOptions').hidden = false;
	document.getElementById('monthlyOptions').hidden = true;
	document.getElementById('yearlyOptions').hidden = true;
	$("#ttCheckSu").prop("checked", false);	
	$("#ttCheckMo").prop("checked", false);	
	$("#ttCheckTu").prop("checked", false);	
	$("#ttCheckWe").prop("checked", false);	
	$("#ttCheckTh").prop("checked", false);	
	$("#ttCheckFr").prop("checked", false);	
	$("#ttCheckSa").prop("checked", false);	
	generalTabChanged()
	
}

function ttMonthlyClicked() {

	document.getElementById('repeatUnits').hidden = false;
	$("#aUnitsName").html("months");
	document.getElementById('aRepeatCode').value = 'M-'
	document.getElementById('weeklyOptions').hidden = true;
	document.getElementById('monthlyOptions').hidden = false;
	document.getElementById('yearlyOptions').hidden = true;

	document.getElementById('monthly1stDropdown').value = 0;
	document.getElementById('monthly2ndDropdown').value = 0;
	document.getElementById('monthly3rdDropdown').value = 0;
	document.getElementById('ttMonthlyDropdown2').hidden = true;
	document.getElementById('ttMonthlyDropdown3').hidden = true;
	document.getElementById('ttMonthlyText').hidden = true;
	document.getElementById('ttDate').value = '';
	document.getElementById('everyMonthLabel').hidden = true;
	generalTabChanged()
	
//	document.getElementById('monthly1stDropdown').value = 0;
	
}

function ttYearlyClicked() {
	
	document.getElementById('repeatUnits').hidden = false;
	$("#aUnitsName").html("years");
	document.getElementById('aRepeatCode').value = 'Y-'
	document.getElementById('weeklyOptions').hidden = true;
	document.getElementById('monthlyOptions').hidden = true;
	document.getElementById('yearlyOptions').hidden = false;
	document.getElementById('ttDateY').value = '';
	document.getElementById('ttMonthDropdown').value = 0;
	generalTabChanged()
	
	
}

function whichMonthDropdownOrDate() {
	var keuse
	keuse = document.getElementById('monthly1stDropdown').value
	if (keuse == '0') {
		document.getElementById('ttMonthlyDropdown2').hidden = true;
		document.getElementById("monthly2ndDropdown").selectedIndex = "0"; 
		document.getElementById('ttMonthlyDropdown3').hidden = true;
		document.getElementById("monthly3rdDropdown").selectedIndex = "0"; 
		document.getElementById('ttMonthlyText').hidden = true;
		document.getElementById('ttDate').value = '';
		document.getElementById('everyMonthLabel').hidden = true;
	} else {
		if (keuse == 'Date') {
			document.getElementById('ttMonthlyDropdown2').hidden = true;
			document.getElementById('ttMonthlyDropdown3').hidden = true;
			document.getElementById('ttMonthlyText').hidden = false;
			document.getElementById('everyMonthLabel').hidden = false;
		}
		if (keuse == 'First' || keuse == 'Second' ||  keuse == 'Third' ||  keuse == 'Last') {
			document.getElementById('ttMonthlyDropdown2').hidden = false;
			document.getElementById('ttMonthlyDropdown3').hidden = true;
			document.getElementById('monthly3rdDropdown').value = 0
			document.getElementById('ttMonthlyText').hidden = true;
			document.getElementById('everyMonthLabel').hidden = false;
			document.getElementById('ttDate').value = '';
		}
		if (keuse == '2nd Last' ||  keuse == '3rd Last') {
			document.getElementById('ttMonthlyDropdown2').hidden = true;
			document.getElementById('ttMonthlyDropdown3').hidden = false;
			document.getElementById('monthly2ndDropdown').value = 0
			document.getElementById('ttMonthlyText').hidden = true;
			document.getElementById('everyMonthLabel').hidden = false;
			document.getElementById('ttDate').value = '';
		}
	}
	getRepeatCodeForMonth()
	
}

function getRepeatCodeForMonth() {
	
	var dieDagFinaal
	var keuse = document.getElementById('monthly1stDropdown').value
	var keuse2 = document.getElementById('monthly2ndDropdown').value
	var	keuse3 = document.getElementById('monthly3rdDropdown').value
	var dieDag = +document.getElementById('ttDate').value;
	
	var element = document.getElementById('ttDate'); 
    var dieLengte = element.value.length;
//    var dieNommer = element.value;
    if (dieLengte == 1) {
    	dieDagFinaal = '0' + document.getElementById('ttDate').value;
    }else {
    	dieDagFinaal = document.getElementById('ttDate').value;
    }
	
	

	if ((keuse != '0') && ((keuse2 != 0) || (keuse3 != 0) || (dieDag != 0))){
		
		if ((keuse == 'Date') && (Number.isInteger(dieDag) == true)){
			document.getElementById('aRepeatCode').value = 'M-Date-----' + dieDagFinaal;
		} else {
			document.getElementById('aRepeatCode').value = 'M-' + keuse.padEnd(9,'-');
			if (keuse2 != 0) {
				document.getElementById('aRepeatCode').value += keuse2
			};
			if (keuse3 != 0) {
				document.getElementById('aRepeatCode').value += keuse3
			};
		}
	} else {
		document.getElementById('aRepeatCode').value = '';
	}
	
//	console.log('keuse:=' + keuse + ' keuse2:=' + keuse2 + ' keuse3:=' + keuse3 + ' dieDag:=' + dieDag);	
};




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




function initializeTaskTable(assignmentId) 
{
	
//console.log("begin");	
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
		targets: [0, 1, 2, 3, 6, 8, 10, 11, 12]
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
			r = "";
			if (row.hasOwnProperty("taskImportanceType")){
				r = row.taskImportanceType;
				if (r != null) {
					r = row.taskImportanceType.importanceValue;
				}
			}
			return r;
		},
		targets: 3
	},
	{
		render: function (data, type, row){
			r = "";
			if (row.hasOwnProperty("taskImportanceType")){
				r = row.taskImportanceType;
				if (r != null) {
					r = row.taskImportanceType.name;
				}
			}
			return r;
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
			editTask(null);
		}
	},
	{
		attr: {
			id: "promptDeleteTaskBtn"
		},
		titleAttr: "Delete",
		text: "<i class='fas fa-minus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			promptDeleteTask();
		}
	},
	];

	
//	console.log("HIER..../rest/ignite/v1/assignment/tasks/" + assignmentId); //127.0.0.1:8081/rest/ignite/v1/assignment/tasks/64
	
//	console.dir(columnArray);
	
	tTaskTable = initializeGenericTable("tTaskTable", 
			                            "/rest/ignite/v1/assignment/tasks/" + assignmentId,
			                            columnArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
			                				editTask(rowSelector);
			                			},
										null,
										10,
										[5,"asc"] //Order by column 2 ascending, normally defaults to column 1 ascending
								);
	
	tTaskTable.off('deselect')
	tTaskTable.on('deselect', function (e, dt, type, indexes) {

		updateTaskToolbarButtons();
   		showEmptyRevisionPanel();
	} );

	tTaskTable.off('select')
	tTaskTable.on('select', function (e, dt, type, indexes) {

		updateTaskToolbarButtons();
   		showRevisionTable(dt.data());
	} );


	updateTaskToolbarButtons();
	showEmptyRevisionPanel();
	

	
	
//	console.log("HIER...2");
							
}   //function initializeTempTaskTypeTable(assignmentTypeId)


function showEmptyRevisionPanel() {
	setDivVisibility("revisionEmptyPanel", "block");
	setDivVisibility("revisionPanel", "none");
	document.getElementById('revPanelTaskId').value = ''
	showEmptySubmissionPanel();
}

function showEmptySubmissionPanel() {
	setDivVisibility("submissionEmptyPanel", "block");
	setDivVisibility("submissionPanel", "none");

	document.getElementById('subPanelRevId').value = ''
	document.getElementById('subPanelTaskSubId').value = ''		
}



function showRevisionTable(taskRow) {
//	console.log("showRevisionTable");
			
			
	if (taskRow == null) {
		return;
	}

	var taskId = taskRow.taskId;

	$("#revPanelTaskId").val(taskRow.taskId); 	
	
	$("#taskCompleted").val(taskRow.completed);	
	
	
	setDivVisibility("revisionEmptyPanel", "none");
	setDivVisibility("revisionPanel", "block");
	showEmptySubmissionPanel();

	var queryUrl = "/rest/ignite/v1/task-revision/list/" + taskId;
//	console.log(queryUrl);

	var columnsArray = [

   		{ data: "taskRevisionId"},			// 0
		{ data: "taskId"},					// 1
		{ data: "revisionNumber"},			// 2
		{ data: "revisionDate"},			// 3
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
	{
		titleAttr: "New",
		text: "<i class='fas fa-plus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			editTaskRevision(null);
		}
	},
	{
		attr: {
			id: "promptDeleteTaskRevisionBtn"
		},
		titleAttr: "Delete",
		text: "<i class='fas fa-minus'></i>",
		className: "btn btn-sm",
		action: function( e, dt, node, config ) {
			promptDeleteTaskRevision();
		}
	}
	

	
	];
	
	taskRevisionTable = initializeGenericTable("taskRevisionTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
			                				editTaskRevision(rowSelector);
			                			},
										null,
										10,
										[2,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	taskRevisionTable.off('deselect')
	taskRevisionTable.on('deselect', function (e, dt, type, indexes) {
		updateTaskRevisionToolbarButtons();
		showEmptySubmissionPanel();
	} );

	taskRevisionTable.off('select')
	taskRevisionTable.on('select', function (e, dt, type, indexes) {
		updateTaskRevisionToolbarButtons();
		showSubmissionTable(dt.data());
	} );

	
//	var lastRow = taskRevisionTable.rows[ taskRevisionTable.rows.length - 1 ];
//	console.log("lastRow: " + lastRow);
//	var table = document.getElementById("taskRevisionTable");
//	$('table tr:last')
	
} //showRevisionTable(taskRow)  --END



function showSubmissionTable(revRow) {
//	console.log("showSubmissionTable");
				
	if (revRow == null) {
		return;
	}

	var taskRevisionId = revRow.taskRevisionId;
	
	setDivVisibility("submissionEmptyPanel", "none");
	setDivVisibility("submissionPanel", "block");
	$("#subPanelRevId").val(taskRevisionId); 
	//showEmptySubmissionPanel();

	var queryUrl = "/rest/ignite/v1/task-submission/list/" + taskRevisionId;
	console.log(queryUrl);

	var columnsArray = [

   		{ data: "taskSubmissionId" },			// 0
		{ data: "taskRevisionId" },				// 1
		{ data: "submissionNumber" },			// 2
		{ data: "submissionDate" },				// 3
		{ data: "acceptedflag" },				// 4
		{ data: "comment" },					// 5
		{ data: "feedback" }					// 6
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
					html = timestampToString(data, false, false);
				}
				return html;
			},
			//width: "100px",
			targets: [3]//, 15, 17]
		}		
		
	];

	var buttonsArray = [
	
	];
	
	taskSubmissionTable = initializeGenericTable("taskSubmissionTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
			                				editTaskSubmission(rowSelector);
			                			},
										null,
										10,
										[2,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);

	taskSubmissionTable.off('deselect')
	taskSubmissionTable.on('deselect', function (e, dt, type, indexes) {
		$("#subPanelTaskSubId").val("")
	} );

	taskSubmissionTable.off('select')
	taskSubmissionTable.on('select', function (e, dt, type, indexes) {
		showSubmissionId(dt.data());
	} );

	

}


function showSubmissionId(revRow) {
//	console.log("showSubmissionTable");
				
	if (revRow == null) {
		return;
	}

	var taskSubmissionId = revRow.taskSubmissionId;
	
	$("#subPanelTaskSubId").val(taskSubmissionId); 
}









function saveTaskTypesToTask(assignmentTypeId, assignmentId) 

{

	
}


function myFunctionToSaveTaskTypes(assignmentTypeId, assignmentId) {
    longfunctionfirst(shortfunctionsecond(assignmentId), assignmentTypeId, assignmentId);
}

function longfunctionfirst(callback, assignmentTypeId, assignmentId) {
	var postUrl = "/rest/ignite/v1/assignment/insert-tasks"    //+ assignmentTypeId + "/" + assignmentId;
			
			var postData = {
					assignmentTypeId,
					assignmentId
			}

			saveEntry(postUrl, postData, null, "Tasks has been saved.", null);
	
    setTimeout(function() {
//        alert('first function finished');
        if(typeof callback == 'function')
            callback();
    }, 2000);
};

function shortfunctionsecond(assignmentId) {
	initializeTaskTable(assignmentId);
    setTimeout(null, 200);
};









$(document).ready(function() {
	
	// Any initialization
	// selectNextAssignmentNumber();

//	$("#rangeStartDate").datepicker("setDate", new Date(firstDay));
//	$("#rangeEndDate").datepicker("setDate", new Date(lastDay));	
//	
//	console.log("firstDay: " + firstDay);
	
	initializeAssignmentTable();

} );
