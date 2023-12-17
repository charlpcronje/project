// projectExpenseTab.js //
var participantIdPayer = null;
var participantPayerSystemName = null;
var pexExpenseSummaryTable = null;
var pexParticipantExpenseTable = null;

//initializeParticipantExpenseTab -- Start

//***********************************************************************// 
//******************* Participant Expense information *******************// 

function initializeProjectExpenseTab(participantId, participantSystemName) {
	
		//		const now = new Date();
		if (($("#pexStartDate").val() == "") || ($("#pexStartDate").val() == null)) {
		//			const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Prev month's first day
			$("#pexStartDate").datepicker("setDate", new Date(firstDay));	
		}
		if (($("#pexEndDate").val() == "") || ($("#pexEndDate").val() == null)) {
		//			const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day
			$("#pexEndDate").datepicker("setDate", new Date(lastDay));		
		}	
		
	    participantIdPayer = participantId;
	    participantPayerSystemName = participantSystemName;
		$("#pexParticipantId").val(participantIdPayer);

		var dagVan = getMsFromDatePicker("pexStartDate");
	    var dagTot= getMsFromDatePicker("pexEndDate");

		queryUrl = "/rest/ignite/v1/project-expense/non-asset-expenses-all/" + participantIdPayer;        // VProjectExpenseMin
		console.log("In initializeProjectExpenseTab --  queryUrl:= " + queryUrl)

		var columnsArray = [
		                    
	    		{ data: "projectExpenseId" },    		//0         
	    		{ data: "purchaseDate" },        		//1 --
	    		{ data: "projectParticipantIdPayer" }, 	//2
	    		{ data: "participantIdPayer" },       	//3 
	    		{ data: "participantPayerSystemName" }, //4 
	    		{ data: "projectId" },                	//5
	    		{ data: "projectName" },              	//6 --
	    		{ data: "expenseTypeId" },          	//7
	    		{ data: "expenseTypeName" },          	//8 --
	    		{ data: "unitTypeCode" },             	//9
	    		{ data: "unitTypeName" },             	//10 --
	    		{ data: "assetId" },                  	//11
	    		{ data: "vehicleId" },                	//12
	    		{ data: "vehicleName" },              	//13
	    		{ data: "assetDescription" },         	//14 
	    		{ data: "paymentDescription" },       	//15 --
	    		{ data: "numberOfUnits" },            	//16 --
	    		{ data: "amountPerUnit" },            	//17 --
	    		{ data: "lineTotal" } 		           	//18 --
		];

		var columnDefsArray = [
			{
				visible: false,
				targets: [0,2,3,4,5,7,9,11,12,13,14]       //[0,4,7,9,11,12,13,14]   //[0,2,3,4,5,7,9,11,12,13,14]       //
			},{
				width: "5%", // purchaseDate
				targets: 1
			},{
				width: "25%", // projectName
				targets: 6
			},{
				width: "25%", // expenseTypeName
				targets: 8
			},{
				width: "5%", // Unit
				targets: 10
			},{
				width: "15%", // paymentDescription
				targets: 15
			},{
				width: "5%",
				targets: [16]
			},{
				width: "10%",
				targets: [17]
			},{
				width: "10%",
				targets: [18]
			},
			{
				render: function (data, type, row) {
					var html = data;
					if (type == "display") {
						html = timestampToString(data, false);
					}
					return html;
				},
				targets: [1]
			},
			{
				render: function (data, type, row) {
					var html = data;
					if (type == "display") {
						html = valueToRand(data);
					}
					return html;
				},
				targets: [17,18]
			},	
			{
				className: "dt-right",
				targets: [17,18]
			}		
		];

		var buttonsArray = [
			{
				titleAttr: "New",
				text: "<i class='fas fa-plus'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					pexParticipantExpenseTable.on('select', function (e, dt, type, indexes) {
						rowSelector = pexParticipantExpenseTable.rows('.selected').data()[0];
					});
					editProjectExpense(null, participantIdPayer, participantPayerSystemName);
				}
			},
			{
				attr: {
					id: "deleteParticipantExpenseBtn"
				},
				titleAttr: "Delete",
				text: "<i class='fas fa-minus'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					promptDeleteParticipantExpense();
				}
			},
			{
				attr: {
					id: "thisWeekBtnPex"
				},
				titleAttr: "This Week",
				text: "<i class='far fa-calendar'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					thisWeekPex();
				}
			},
			{
				attr: {
					id: "previousWeekBtnPex"
				},
				titleAttr: "Previous Week",
				text: "<i class='fas fa-chevron-left'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					previousWeekPex();
				}
			},
			{
				attr: {
					id: "nextWeekBtnPex"
				},
				titleAttr: "Next Week",
				text: "<i class='fas fa-chevron-right'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					nextWeekPex();
				}
			},
			{
				attr: {
					id: "thisMonthBtnPex"
				},
				titleAttr: "This Month",
				text: "<i class='far fa-calendar-alt'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					thisMonthPex();
				}
			},
			{
				attr: {
					id: "prevMonthBtnPex"
				},
				titleAttr: "Previous Month",
				text: "<i class='fas fa-angle-double-left'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					newDateRangeSelected = true;
					prevMonthPex();
				}
			},
			{
				attr: {
					id: "nextMonthBtnPex"
				},
				titleAttr: "Next Month",
				text: "<i class='fas fa-angle-double-right'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					newDateRangeSelected = true;
					nextMonthPex();
				}
			}
		];

		pexParticipantExpenseTable = initializeGenericTable("pexParticipantExpenseTable",
											queryUrl,
				                            columnsArray,
				                            columnDefsArray,
				                            buttonsArray,
				                            function(rowSelector) {
												editProjectExpense(rowSelector, participantIdPayer, participantPayerSystemName);
											},
											null,
											35,
											[[1,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);
		
		
		pexParticipantExpenseTable.off('deselect');
		pexParticipantExpenseTable.on('deselect', function (e, dt, type, indexes) {
			updatePexParticipantExpenseTableToolbarButtons();
		} );

		pexParticipantExpenseTable.off('select');
		pexParticipantExpenseTable.on('select', function (e, dt, type, indexes) {
			updatePexParticipantExpenseTableToolbarButtons();
			peRowSelected = pexParticipantExpenseTable.rows('.selected').data()[0];
			// initializeTimeCostDetailPanel(dt.data()); 
		} );
		updatePexParticipantExpenseTableToolbarButtons();

} //initializeParticipantProjectExpenseTable -- End



function updatePexParticipantExpenseTableToolbarButtons() {
	var hasSelected = pexParticipantExpenseTable.rows('.selected').data().length > 0;

	setTableButtonState(pexParticipantExpenseTable, "deleteParticipantExpenseBtn", hasSelected);	
}



function promptDeleteParticipantExpense() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Expense?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteParticipantExpense(pexParticipantExpenseTable);
			   }
	);
}





function deleteParticipantExpense(tbl) {
	var postUrl = "/rest/ignite/v1/project-expense/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Expense has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updatePexParticipantExpenseTableToolbarButtons();
			});
	
}



function refreshParticipantExpense() {
	
//	var dagVan = getMsFromDatePicker("pexStartDate");
//    var dagTot= getMsFromDatePicker("pexEndDate");

//    var requestUrl="/rest/ignite/v1/project-expense/participant-expense-summary/" + participantIdPayer + "/" + dagVan + "/" + dagTot; 
//	pexExpenseSummaryTable.ajax.url(springUrl(requestUrl)).load();

//	requestUrl = "/rest/ignite/v1/project-expense/participant-expenses-all/" + dagVan + "/" + dagTot + "/" + participantIdPayer;
//	pexParticipantExpenseTable.ajax.url(springUrl(requestUrl)).load();

	var participantIdPayer = $("#pexParticipantId").val();
	
	// Send dates through to controller as Long!
	var dagVan = getMsFromDatePicker("pexStartDate");
    var dagTot= getMsFromDatePicker("pexEndDate");	
	
 //var queryUrl="/rest/ignite/v1/project-expense/participant-expense-summary/" + participantIdPayer + "/" + dagVan + "/" + dagTot;
//    pexExpenseSummaryTable.ajax.url( springUrl(queryUrl) ).load();
    
    var queryUrl = "/rest/ignite/v1/project-expense/participant-expenses-all/" + dagVan + "/" + dagTot + "/" + participantIdPayer;
    console.log("In refreshParticipantExpense --  queryUrl:= " + queryUrl)
    pexParticipantExpenseTable.ajax.url( springUrl(queryUrl) ).load();
    
}







//***********************************************************************// 
//*******************      Date range functions       *******************// 


function thisWeekPex() {
	var prevSunday = getPreviousSunday(new Date());
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateTo.setDate(dateFrom.getDate() + 6);
	$("#pexStartDate").datepicker("setDate", new Date(dateFrom));
	$("#pexEndDate").datepicker("setDate", new Date(dateTo));
	refreshParticipantExpense();	
}

function previousWeekPex() {
	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("pexStartDate")));
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() - 7);
	dateTo.setDate(dateTo.getDate() - 1);
	$("#pexStartDate").datepicker("setDate", new Date(dateFrom));
	$("#pexEndDate").datepicker("setDate", new Date(dateTo));
	refreshParticipantExpense();	
} 

function nextWeekPex() {
	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("pexEndDate")));
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() + 7);
	dateTo.setDate(dateTo.getDate() + 13);
	$("#pexStartDate").datepicker("setDate", new Date(dateFrom));
	$("#pexEndDate").datepicker("setDate", new Date(dateTo));
	refreshParticipantExpense();	
}

function thisMonthPex() {
	const now = new Date();
	const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
	const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

	$("#pexStartDate").datepicker("setDate", new Date(firstDay));
	$("#pexEndDate").datepicker("setDate", new Date(lastDay));
	refreshParticipantExpense();	
}

function nextMonthPex() {
	const currentEndDate = new Date(getMsFromDatePicker("pexEndDate"));
	const firstDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 1, 1); //Next month's first day
	const lastDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 2, 0); //Next month's last day
	
	$("#pexStartDate").datepicker("setDate", new Date(firstDay));
	$("#pexEndDate").datepicker("setDate", new Date(lastDay));
	refreshParticipantExpense();	
}

function prevMonthPex() {

	const currentStartDate = new Date(getMsFromDatePicker("pexStartDate"));
	const firstDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth() - 1, 1); //Prev month's first day
	const lastDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth(), 0); //Prev month's last day

	$("#pexStartDate").datepicker("setDate", new Date(firstDay));
	$("#pexEndDate").datepicker("setDate", new Date(lastDay));
	refreshParticipantExpense();	
}

//***********************************************************************// 

//*******************      Date range functions  End      *******************// 





