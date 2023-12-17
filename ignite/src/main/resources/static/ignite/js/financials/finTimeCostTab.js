var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var participantRelationshipTable = null;

var showDefaultFinProj;

//*********************************************************
//CostPerAgreement Tab -- Start
//*********************************************************

function getTimeCostForDateRange() {
	
	var participantId = $("#finParticipantId").val();
    var dateFromFin = getMsFromDatePicker("tcStartDate");
    var dateToFin= getMsFromDatePicker("tcEndDate");
	initializeFinTimeCostTab(participantId, dateFromFin, dateToFin);

}

function initializeFinTimeCostTab(participantId, dateFrom, dateTo) {

	showEmptyBeneficiaryCostPanel();	
	
	var columnsArray = [
			{ data: "agreementParticipantIdPayer" },		// 0 
			{ data: "agreementPayer" },						// 1
			{ data: "agreementParticipantIdBeneficiary" },	// 2
			{ data: "agreementBeneficiary"},				// 3
			{ data: "sumNrOfUnits"},						// 4
			{ data: "lineAmount"}							// 5
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2]
		},
		{
			width: "45%",
			targets: [3]
		},
		{
			width: "35%",
			targets: [5]
		},
		{
			width: "20%",
			targets: [4]
		},
		{
			render: function(data, type, row) {
				data = row.lineAmount;
				if (type == "display") {
					rm = row.ratesMissing;
					if (rm > 0 ) { // There are one or more rates missing
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
					} else {
						data = valueToRand(data);
					}
				}
				return data;
			},
			className:"dt-right",
			targets: 5
		}
//		,
//		{
//			render: function(data, type, row) {  //Add badge to table
//				data = row.lineAmount;
//
//				if (type == "display") {  // Should be used when printing or changing the UI
//					if (data == null) {
//						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
//					} else {
//						data = '&nbsp;';
//					}
//				}
//				return data;
//			},
//			className:"dt-center",
//			targets: 6
//		}
	
	];
	
	var buttonsArray = [
		
	];

	var queryUrl="/rest/ignite/v1/financials/relationships-unique/" + participantId + "/" + dateFrom + "/" + dateTo;
	console.log(queryUrl);
	
	participantRelationshipTable = initializeGenericTable("participantRelationshipTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function() {
										},
										null,
										25,
										[3,"asc"] //Order by column 5 ascending, normally defaults to column 1 ascending
	);
	
		participantRelationshipTable.off('deselect')
		participantRelationshipTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyBeneficiaryCostPanel();
	} );
	
		participantRelationshipTable.off('select')
		participantRelationshipTable.on('select', function (e, dt, type, indexes) {
		initializeBeneficiaryCostPanel(dt.data(), dateFrom, dateTo);
	} );
	
}

function showEmptyBeneficiaryCostPanel() {
	setDivVisibility("emptyBeneficiaryCostPanel", "block");
	setDivVisibility("beneficiaryCostPanel", "none");
	showEmptyTimeCostDetailPanel();
}

function initializeBeneficiaryCostPanel(relationshipRow, dateFrom, dateTo) {

	if (relationshipRow == null) {
		return;
	}
	
	var participantIdPayer = relationshipRow.agreementParticipantIdPayer;
	var participantIdBeneficiary = relationshipRow.agreementParticipantIdBeneficiary;
	
	setDivVisibility("emptyBeneficiaryCostPanel", "none");
	setDivVisibility("beneficiaryCostPanel", "block");
	showEmptyTimeCostDetailPanel();

	var columnsArray = [
		{ data: "projectId" },						// 0
		{ data: "projectName" },					// 1 --
		{ data: "sdName"},							// 2 --
		{ data: "unitTypeName" },					// 3
		{ data: "agreementBetweenParticipantsId" },	// 4
		{ data: "agreementBetween"},				// 5
		{ data: "agreementParticipantIdPayer" },	// 6
		{ data: "agreementPayer" },					// 7
		{ data: "agreementParticipantIdBeneficiary" },	// 8
		{ data: "agreementBeneficiary"},				// 9
		{ data: "projectSdId"},							// 10
		{ data: "remunerationTypeName"},				// 11
		{ data: "sumNrOfUnits"},						// 12 --
		{ data: "lineAmount"}							// 13 --
		]
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,3,4,5,6,7,8,9,10,11]
		},
		{
			width: "50%",
			targets: [1]
		},
		{
			width: "25%",
			targets: [2]
		},
		{
			width: "10%",
			targets: [12]
		},
		{
			width: "15%",
			targets: [13]
		},
		{
			render: function(data, type, row) {
				l = row.lineAmount;
				rm = row.ratesMissing;
				if (rm > 0 ) {
					l = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
				} else {
					l = valueToRand(data);
				}
				return l;
			},
			className:"dt-right",
			targets: [13]
		}
//		,
//		{
//			render: function(data, type, row) {  //Add badge to table
//				data = row.ratesMissing;
//
//				if (type == "display") {  // Should be used when printing or changing the UI
//					if (data > 0 ) {
//						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
//					} else {
//						data = '&nbsp;';
//					}
//				}
//				return data;
//			},
//			className:"dt-center",
//			targets: 14
//		}
//
//	,
//	{
//		render: function(data, type, row) {
//			data = row.rateForDate;
//			
//			
//			if (data < 0) {
//				data = "Rates missing"; 
//			} else {
//				if (type == "display") {
//					data = valueToRand(row.numberOfUnits * row.rateForDate);
//				}
//			}
//			return data;
//		},
//		targets: [11]
//	}
//	
//	{
//		width: "10%",
//		targets: 1
//	}
	
	];

	var buttonsArray = [];

	var queryUrl="/rest/ignite/v1/financials/payer-ben-time-cost/" + participantIdPayer + "/" + participantIdBeneficiary + "/" + dateFrom + "/" + dateTo;
	
	console.log(queryUrl);
	
	beneficiaryCostTable = initializeGenericTable("beneficiaryCostTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function() {
										}
	);

	beneficiaryCostTable.off('deselect')
	beneficiaryCostTable.on('deselect', function (e, dt, type, indexes) {
	showEmptyTimeCostDetailPanel();
} );

	beneficiaryCostTable.off('select')
	beneficiaryCostTable.on('select', function (e, dt, type, indexes) {
	initializeTimeCostDetailPanel(dt.data());
} );
	
}

function showEmptyTimeCostDetailPanel() {
	setDivVisibility("emptyTimeCostDetailPanel", "block");
	setDivVisibility("timeCostDetailPanel", "none");
}


function initializeTimeCostDetailPanel(agreementTimeCostRow) {
	if (agreementTimeCostRow == null) {
		return;
	}
	
	var agreementBetweenParticipantsId = agreementTimeCostRow.agreementBetweenParticipantsId;
	var projectSdId = agreementTimeCostRow.projectSdId;
	
	setDivVisibility("emptyTimeCostDetailPanel", "none");
	setDivVisibility("timeCostDetailPanel", "block");

	var columnsArray = [
		{ data: "rowNumber" },					// 0 
		{ data: "level" },						// 1
		{ data: "projectName" },				// 2
		{ data: "agreementBetween"},			// 3
		{ data: "activityDate" },				// 4 --
		{ data: "systemNameThatBookedTime" },	// 5 --
		{ data: "sdAndRole"},					// 6
		{ data: "description" },				// 7
		{ data: "unitTypeName" },				// 8
		{ data: "numberOfUnits" },				// 9 --
		{ data: "rateForDate"},					// 10 --
		{ data: ""},								// 11 --
		{ data: "stageName"},							// 12
		{ data: "remunerationTypeName"},					// 13
		{ data: "projectParticipantIdThatBookedTime"}	// 14 
		
		]
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0, 1, 2, 3, 6, 7, 8, 12,13,14]
	},
	{
		render: function (data, type, row) {
			var html = data;
			if (type == "display") {
				html = timestampToString(data, false);
			}
			return html;
		},
		targets: [4]
	},
	{
		render: function(data, type, row) {
			data = row.rateForDate;
			if (data < 0) {
				data = '&nbsp;';
			} else {
				if (type == "display") {
					data = valueToRand(data);
				}
			}
			return data;
		},
		className:"dt-right",
		targets: [10]
	},
	{
		render: function(data, type, row) {
			data = row.rateForDate;
			if (data < 0) {
				data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
			} else {
				if (type == "display") {
					data = valueToRand(row.numberOfUnits * row.rateForDate);
				}
			}
			return data;
		},
		className:"dt-right",
		targets: [11]
	}
	
//	,
//	{
//		render: function(data, type, row) {  //Add badge to table
//			data = row.rateForDate;
//
//			if (type == "display") {  // Should be used when printing or changing the UI
//				if (data < 0 ) {
//					data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
//				} else {
//					data = '&nbsp;';
//				}
//			}
//			return data;
//		},
//		className:"dt-center",
//		targets: 12
//	}

//	
//	{
//		width: "10%",
//		targets: 1
//	}
	
	
	];

	var buttonsArray = [];

	var queryUrl="/rest/ignite/v1/agreement-between-participants/project-time-cost-detail/" + agreementBetweenParticipantsId + "/" + projectSdId;
	
	timeCostDetailTable = initializeGenericTable("timeCostDetailTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editFinTimesheet(rowSelector);
										}
	);
	
}


function closeEditFinTimesheetDialog() {
	setActiveTab("finTimeCostTab");
	closeModalDialog("editFinTimesheetDialog");
}



//--editFinTimesheet-- Start
function editFinTimesheet(rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = timeCostDetailTable.row(rowSelector).data(); //Will give us the data of the double-clicked row
	} else {
		return;
	}

	timeCostDetailTable.rows().deselect();

	$("#finTimesheetId").val(data.timesheetId);
	$("#finProjectParticipantIdThatBookedTime").val(data.projectParticipantIdThatBookedTime);
	$("#finProjectParticipantSdRoleId").val(data.projectParticipantSdRoleId); //1
	$("#finParticipantName").val(data.systemNameThatBookedTime); 
	$("#finProjectNameText").val(data.projectName); //3
	
	$("#finSDAndRole").val(data.sdAndRole);
	
	$("#finProjectStage").val(data.stageName);
	
//	$("#finRateForDate").val(data.rateForDate);  
//console.log("data.rateForDate: " + data.rateForDate)

	if (data.rateForDate == -2) {
		$("#RatesMissingIconSpan").attr("hidden",false);	
		$("#NoAgreementSetupIconSpan").attr("hidden",true);
		$("#finRateForDate").val("Rates missing"); // + <span class="badge badge-pill badge-warning" title="Rates missing" >!</span>)
//		$("#finRateForDate").html(<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>);
		$("#finRateForDateCost").val("");
	} else if (data.rateForDate == -4) {
		$("#RatesMissingIconSpan").attr("hidden",true);
		$("#NoAgreementSetupIconSpan").attr("hidden",false);
		$("#finRateForDate").val("No Agreement Setup");
		$("#finRateForDateCost").val("");
	} else if (data.rateForDate != null) {
		$("#RatesMissingIconSpan").attr("hidden",true);
		$("#NoAgreementSetupIconSpan").attr("hidden",true);
		$("#finRateForDate").val("R " + (data.rateForDate).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& '));  // Sit spasie in as thousand separator en 2 desimale plekke
		$("#finRateForDateCost").val((data.numberOfUnits != null) ? "R " + (data.rateForDate * data.numberOfUnits).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
	};
	
	$("#finProjBasedRemunType").val(data.remunerationTypeName);   

	$("#finUnitTypeName").val(data.unitTypeName);     
	$("#finActivityDate").datepicker("setDate", data.activityDate == null? null : new Date(data.activityDate));                     //6
	$("#finNumberOfUnits").val(data.numberOfUnits);                   //7
	$("#finDescription").val(data.description);                       //8

	
	showModalDialog("editFinTimesheetDialog");


	somethingChangedInDialog = false;
	askToSaveDialog = false;

}
//editFinTimesheet -- End







////*********************************************************
////initializeTimesheetSummaryTable -- Start
//function initializeCostTimesheetSummaryTable(projectIdSelected, dateFrom , dateTo) {
//	
//	var data = {}; // Give it an empty object (so, need to add a new record)
//
//	
//	var columnsArray = [
//		{ data: "participantIdIndividual" },	//0
//	    { data: "hostParticipant" },		//1
//	    { data: "individualSystemName" },		//2
//		{ data: "sdAndRoleOnAProject" },		//3
//		{ data: "projBasedRemunTypeName" },		//4
//		{ data: "unitTypeName" },				//5
//		{ data: "totalUnits" }					//6
//	];
//	
//
//	var columnDefsArray = [
//	               		{
//	               			visible: false,
//	               			targets: [0]
//	               		},
//	               		{
//	               			width: "10%",
//	               			targets: [1,5,6]
//	               		},
//	               		{
//	               			width: "25%",
//	               			targets: [2,3]
//	               		},
//	               		{
//	               			width: "20%",
//	               			targets: 4
//	               		}
//	               	];
//	var buttonsArray = [];
//	var queryUrl = "/rest/ignite/v1/timesheet/per-project/summary/" + projectIdSelected + "/" + dateFrom + "/" + dateTo;
//
//	projectTimesheetSummaryTable = initializeGenericTable("projectTimesheetSummaryTable",
//										queryUrl,
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function(rowSelector) {
//										}
//	);
//}
////initializeTimesheetSummaryTable -- End
////*********************************************************
//
////*********************************************************
////initializeTimesheetTable -- Start
//
//function initializeProjectTimesheetTable(projectIdSelected, dateFrom, dateTo, newDateRangeSelected) {
//
//	var data = {}; // Give it an empty object (so, need to add a new record)
//	var header = "Timesheet and Expense Detail";
//	
//	setActiveTab("tTimesheetTabAnchor");
//
////	participantId = $("#eIndParticipantId").val();
////	resourceId = $("#eIndResourceId").val();
//
//	if (!newDateRangeSelected) {
//		$("#tStartDate").datepicker("setDate", new Date(dateFrom));
//		$("#tEndDate").datepicker("setDate", new Date(dateTo));
//	
////		if (individualRow != null) {
////			data = individualTable.row(individualRow).data();
////			enabled = true;
////			header = "Timesheet and Expense Detail: " + data.firstName + " " + data.lastName;
////			
////			participantName = data.firstName + " " + data.lastName;
////			participantIdSelected = data.participantId;
////			$("#selectedParticipantId").val(participantIdSelected);
////			$("#selectedParticipantName").val(participantName);
////		} else {
////			participantIdSelected = $("#selectedParticipantId").val();
////			participantName = $("#selectedParticipantName").val(participantName);
////			header = "Timesheet and Expense Detail: " + participantName;
////		}
////		
////		$("#timesheetHeader").html(header);
//	} else {
//		enabled = true;
////		participantIdSelected = $("#selectedParticipantId").val();
////		participantName = $("#selectedParticipantName").val();
////		header = "Timesheet Detail: " + participantName;
//	}
//	
//	var columnsArray = [
//
//	    { data: "timesheetId" },				//0
//	    { data: "projectParticipantSdRoleId" },		//1
//	    { data: "projBasedRemunTypeId" },		//2
//		{ data: "individualSystemName" },				//3
//		{ data: "individualSystemName" },		//4
//		{ data: "serviceDisciplineName" },		//5 
//		{ data: "roleOnAProjectName" },			//6
//		{ data: "individualSystemName" },		//7
//		{ data: "activityDate" },				//8
//		{ data: "remunerationTypeName" },		//9
//		{ data: "numberOfUnits" },				//10
//		{ data: "description" },				//11
//		{ data: "projectParticipantId" },		//12
//		{ data: "projectNumber" },				//13
//		{ data: "serviceDisciplineCode" },		//14 
//		{ data: "participantIdIndividual" },	//15
//		{ data: "activityName" },				//16
//		{ data: "unitTypeCode" },				//17
//		{ data: "unitTypeName" },				//18
//		{ data: "participantIdHost" }			//19
//		
//	];
//	
//	var columnDefsArray = [
//		{
//			visible: false,
//			targets: [0,1,2,3,7,11,12,13,14,15,16,17,18,19]
//		},		
//		{
//			width: "25%",
//			targets: 4
//		},
//		{
//			width: "30%",
//			targets: 5
//		},
//		{
//			width: "10%",
//			targets:[6,8,9,18]
//		},
//		{
//			width: "5%",
//			targets:[10]
//		},
//		{
//			render: function (data, type, row) {
//				var html = data;
//				if (type == "display") {
//					html = timestampToString(data, false);
//				}
//				return html;
//			},
//			width: "10%",
//			targets: [8]
//		}
//	];
//
//	var buttonsArray = [
//		{
//			attr: {
//				id: "thisWeekBtn"
//			},
//			titleAttr: "This Week",
//			text: "<i class='far fa-calendar'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				newDateRangeSelected = true;
//				thisWeek();
//			}
//		},
//		{
//			attr: {
//				id: "previousWeekBtn"
//			},
//			titleAttr: "Previous Week",
//			text: "<i class='fas fa-chevron-left'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				newDateRangeSelected = true;
//				previousWeek();
//			}
//		},
//		{
//			attr: {
//				id: "nextWeekBtn"
//			},
//			titleAttr: "Next Week",
//			text: "<i class='fas fa-chevron-right'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				newDateRangeSelected = true;
//				nextWeek();
//			}
//		},
//		{
//			attr: {
//				id: "thisMonthBtn"
//			},
//			titleAttr: "This Month",
//			text: "<i class='far fa-calendar-alt'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				newDateRangeSelected = true;
//				thisMonth();
//			}
//		},
//		{
//			attr: {
//				id: "prevMonthBtn"
//			},
//			titleAttr: "Previous Month",
//			text: "<i class='fas fa-angle-double-left'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				newDateRangeSelected = true;
//				prevMonth();
//			}
//		},
//		{
//			attr: {
//				id: "nextMonthBtn"
//			},
//			titleAttr: "Next Month",
//			text: "<i class='fas fa-angle-double-right'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				newDateRangeSelected = true;
//				nextMonth();
//			}
//		}
//	];
//
//	initializeProjectTimesheetSummaryTable(projectIdSelected, dateFrom, dateTo);
//
//	var queryUrl = "/rest/ignite/v1/timesheet/project-list/" + projectIdSelected + "/" + dateFrom + "/" + dateTo;
//	
//	projectTimesheetTable = initializeGenericTable("projectTimesheetTable",
//										queryUrl,
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function(rowSelector) {
//										},
//										null,
//										25,
//										[08,"asc"] //Order by column 0 ascending, normally defaults to column 1 ascending
//	);
//	
//	projectTimesheetTable.off('deselect');
//	projectTimesheetTable.on('deselect', function (e, dt, type, indexes) {
//		updateTimesheetTableToolbarButtons();
//		tsRowSelected = null;
//		showEmptyRatePanel();
//	} );
//
//	projectTimesheetTable.off('select');
//	projectTimesheetTable.on('select', function (e, dt, type, indexes) {
//		updateTimesheetTableToolbarButtons();
//		tsRowSelected = timesheetTable.rows('.selected').data()[0];
//		showTimesheetRatesTable(dt.data());
//
//	} );
//	updateTimesheetTableToolbarButtons();
//
//	showModalDialog("timesheetDialog");
//}
//
//function showEmptyRatePanel() {
//
//	$("#tTimesheetRateHeading").html("Agreement Rates");
//	setDivVisibility("tEmptyRatesPanel", "block");
//	setDivVisibility("tRatesPanel", "none");
//
//}
//
//function updateTimesheetTableToolbarButtons() {
//	var hasSelected = projectTimesheetTable.rows('.selected').data().length > 0;
//
//	setTableButtonState(projectTimesheetTable, "promptDeleteTimesheetBtn", hasSelected);	
//}
//	
//function promptDeleteTimesheet() {
//	showDialog("Confirm?",
//		       "Are you sure that you wish to delete the selected Timesheet Entry?",
//		       DialogConstants.TYPE_CONFIRM, 
//		       DialogConstants.ALERTTYPE_INFO, 
//		       function (e) {
//					deleteTimesheet(timesheetTable);
//			   }
//	);
//}
//
//function deleteTimesheet(tbl) {
//	var postUrl = "/rest/ignite/v1/timesheet/delete";
//	var row = tbl.rows('.selected').data()[0];
//
//	// Disable delete button when record has been deleted.
//	saveEntry(postUrl, row, null, "The Timesheet Entry has been deleted.", tbl,
//			function(){	
//				tbl.rows().deselect();
//				updateTimesheetTableToolbarButtons
//				timesheetSummaryTable.ajax.reload();
//			}
//	);
//}
//
////initializeTimesheetTable -- End
////*********************************************************

//*********************************************************
//costPerAgreement Tab -- End
//*********************************************************
