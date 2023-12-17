var participantIdPayer = null;
var participantPayerSystemName = null;
//var alctAllowanceClaimSummaryTable = null;
var alctAllowanceClaimTable = null;

//const now = new Date();
//const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Prev month's first day
//const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day


//initializeParticipantExpenseTab -- Start

//******************* Participant Expense Summary information *******************// 

//function initializeAllowanceClaimTab(participantId, participantSystemName) {
//	
//	const now = new Date();
//	if (($("#alctStartDate").val() == "") || ($("#alctStartDate").val() == null)) {
//		const firstDay = new Date(now.getFullYear(), now.getMonth() - 36, 1); //Prev month's first day
//		$("#alctStartDate").datepicker("setDate", new Date(firstDay));	
//	}
//	if (($("#alctEndDate").val() == "") || ($("#alctEndDate").val() == null)) {
//		const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day
//		$("#alctEndDate").datepicker("setDate", new Date(lastDay));		
//	}	
//
//	// Send dates through to controller as Long!
//	var dagVan = getMsFromDatePicker("alctStartDate");
//    var dagTot= getMsFromDatePicker("alctEndDate");
//    
//    participantIdPayer = participantId;
//	$("#alctParticipantId").val(participantIdPayer);
//	$("#alctParticipantPayerSystemName").val(participantSystemName);
//	
//    var queryUrl="/rest/ignite/v1/project-expense/participant-allowance-summary/" + participantIdPayer + "/" + dagVan + "/" + dagTot; 
//	
//	var columnsArray = [
//		{ data: "projectId" },				//0
//		{ data: "projectName" },			//1
//		{ data: "subProjNumber" },			//2
//		{ data: "expenseTypeId" },			//3
//		{ data: "expenseTypeParentId" },	//4
//		{ data: "expenseTypeParentName" },	//5
//		{ data: "expenseTypeId" },		//6
//		{ data: "expenseTypeName" },		//7
//		{ data: "theSum" }					//8
//	];
//	
//	var columnDefsArray = [
//   		{
//			visible: seeFormName,
//			targets: [0]
//		},	
//	    {
//			visible: false,
//			targets: [3,4,6,7]
//		},	
////		{
////			render: function (data, type, row) {
////				var html = data;
////				
////				if (type == "display") {
////					html = valueToRand(data);
////				}
////				return html;
////			},
////			targets: [8]
////		},	
//		{
//			className: "dt-right",
//			targets: [8]
//		}
//	];
//	
//	var buttonsArray = [];
//
//	alctAllowanceClaimSummaryTable = initializeGenericTable("alctAllowanceClaimSummaryTable"
//												,queryUrl
//									            ,columnsArray
//									            ,columnDefsArray
//									            ,buttonsArray
//									            ,null
//												,null
//											    ,20        	//pageLength   used to set the table height (takes precedence over tableHeightPixels)
//											    ,[4,"asc"]  //orderArray: column, asc or desc
//											    ,null       //tableHeightPixels (ignored if you have pagelength)
//											    ,false    //showTableInfo   (Showing 0 to 5 etc....)
//											    ,false   //showFilter
//	);
//
//	alctAllowanceClaimSummaryTable.off('deselect');
//	alctAllowanceClaimSummaryTable.on('deselect', function (e, dt, type, indexes) {
//		initializeAllowanceClaimTable(null, participantId);
//	} );
//	
//	alctAllowanceClaimSummaryTable.off('select');
//	alctAllowanceClaimSummaryTable.on('select', function (e, dt, type, indexes) {
//		initializeAllowanceClaimTable(dt.data(), participantId);
//	} );	
//}
//
////initializeAllowanceClaimTab -- End

//***********************************************************************// 
//******************* Participant Allowance information *******************// 

function initializeAllowanceClaimTable(participantId, participantSystemName) {
	
	if (($("#alctStartDate").val() == "") || ($("#alctStartDate").val() == null)) {
		$("#alctStartDate").datepicker("setDate", new Date(firstDay));	
	}
	if (($("#alctEndDate").val() == "") || ($("#alctEndDate").val() == null)) {
		$("#alctEndDate").datepicker("setDate", new Date(lastDay));		
	}	

	// Send dates through to controller as Long!
	var dagVan = getMsFromDatePicker("alctStartDate");
    var dagTot= getMsFromDatePicker("alctEndDate");
	
    participantIdPayer = participantId;
    participantPayerSystemName = participantSystemName;
	$("#alctParticipantId").val(participantIdPayer);
	$("#alctParticipantPayerSystemName").val(participantSystemName);

//	if (allowanceSummaryRow == null) {
		queryUrl = "/rest/ignite/v1/project-expense/participant-allowance-claims-all/" + dagVan + "/" + dagTot + "/" + participantId;
//	} else {
//		var projectId = allowanceSummaryRow.projectId;
//		var expenseTypeId = allowanceSummaryRow.expenseTypeId;
//		queryUrl = "/rest/ignite/v1/project-expense/participant-expenses/" + projectId + "/" + dagVan + "/" + dagTot + "/" + expenseTypeId + "/" + participantId;
//	}
	
		var columnsArray = [
	    		{ data: "projectExpenseId" },         			//0 MySql-TableName: VProjectExpense
	    		{ data: "projectParticipantIdPayer" }, 			//1
	    		{ data: "participantIdPayer" },       			//2
	    		{ data: "participantPayerSystemName" }, 		//3
	    		{ data: "projectId" },                			//4
	    		{ data: "projectName" },              			//5
	    		{ data: "subProjNumber" },              		//6
	    		{ data: "participantIdMadePurchase" }, 			//7
	    		{ data: "participantMadePurchaseSystemName" }, 	//8
	    		{ data: "participantIdVendor" },      			//9	
	    		{ data: "participantVendorSystemName" }, 		//10
	    		{ data: "expenseTypeId" },            //11
	    		{ data: "expenseTypeId" },          //12
	    		{ data: "expenseTypeName" },          //13
	    		{ data: "expenseTypeDescription" },   //14
	    		{ data: "unitTypeCode" },             //15
	    		{ data: "expenseTypeParentId" },    //16
	    		{ data: "expenseTypeParentName" },    //17
	    		{ data: "assetId" },                  //18
	    		{ data: "vehicleId" },                //19
	    		{ data: "vehicleName" },              //20
	    		{ data: "assetDescription" },         //21
	    		{ data: "paymentMethodCode" },        //22
	    		{ data: "paymentMethodName" },        //23
	    		{ data: "bankCardIdUsed" },           //24
	    		{ data: "bankCardNumber" },           //25
	    		{ data: "bankCardNameOnCard" },       //26
	    		{ data: "taxDeductableCategoryId" }, //27
	    		{ data: "taxDeductableCategoryName" }, //28
	    		{ data: "paymentDescription" },       //29
	    		{ data: "purchaseDate" },             //30
	    		{ data: "numberOfUnits" },            //31
	    		{ data: "amountPerUnit" },            //32
	    		{ data: "noteToAccountant" }         //33
		];

		var columnDefsArray = [
   			{
				visible: seeFormName,
				targets: [0]
			},	{
				width: "5%",
				targets: 0
			}	                       
			,{
				visible: false,
				targets: [ 1, 2, 3, 4, 7, 9,10, 11, 12, 13, 14, 15, 16, 17, 18,19,20,21, 22,23,24,25,26,27,28,32,33]
			},{
				width: "15%",
				targets: 5
			}
			,{
				width: "10%",
				targets: 6
			},{
				width: "15%",
				targets: 8
			},{
				width: "10%",
				targets: 23
			},{
				width: "20%",
				targets: 29
			},{
				width: "10%",
				targets: 30
			},{
				width: "5%",
				targets: 31
			},{
				width: "10%",
				targets: 32
			},
			{
				render: function (data, type, row) {
					var html = data;
					if (type == "display") {
						html = timestampToString(data, false);
					}
					return html;
				},
				targets: [30]
			},
			{
				render: function (data, type, row) {
					var html = data;
					if (type == "display") {
						html = valueToRand(data);
					}
					return html;
				},
				targets: [32]
			},	
			{
				className: "dt-right",
				targets: [32]
			}		
		];

		var buttonsArray = [
		     {
	        	titleAttr: "New",
				text: "<i class='fas fa-plus'></i>",
				className: "btn btn-sm",
				action: function(e, dt, node, config) {
					editAllowanceClaim(null, participantIdPayer, participantPayerSystemName); 
	        	}		                    
			},
			{
				attr: {
					id: "deleteAllowanceClaimBtn"
				},
				titleAttr: "Delete",
				text: "<i class='fas fa-minus'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					promptDeleteAllowanceClaimBtn();
				}
			},
			{
				attr: {
					id: "thisWeekBtnAlct"
				},
				titleAttr: "This Week",
				text: "<i class='far fa-calendar'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					thisWeekAlct();
				}
			},
			{
				attr: {
					id: "previousWeekBtnAlct"
				},
				titleAttr: "Previous Week",
				text: "<i class='fas fa-chevron-left'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					previousWeekAlct();
				}
			},
			{
				attr: {
					id: "nextWeekBtnAlct"
				},
				titleAttr: "Next Week",
				text: "<i class='fas fa-chevron-right'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					nextWeekAlct();
				}
			},
			{
				attr: {
					id: "thisMonthBtnAlct"
				},
				titleAttr: "This Month",
				text: "<i class='far fa-calendar-alt'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					thisMonthAlct();
				}
			},
			{
				attr: {
					id: "prevMonthBtnAlct"
				},
				titleAttr: "Previous Month",
				text: "<i class='fas fa-angle-double-left'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					newDateRangeSelected = true;
					prevMonthAlct();
				}
			},
			{
				attr: {
					id: "nextMonthBtnAlct"
				},
				titleAttr: "Next Month",
				text: "<i class='fas fa-angle-double-right'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					newDateRangeSelected = true;
					nextMonthAlct();
				}
			}
		];
		
		

		alctAllowanceClaimTable = initializeGenericTable("alctAllowanceClaimTable",
											queryUrl,
				                            columnsArray,
				                            columnDefsArray,
				                            buttonsArray,
				                            function(rowSelector) {
												editAllowanceClaim(rowSelector, participantIdPayer, participantPayerSystemName);
											},
											null,
											25,
											[[4,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);
		
		
		
		alctAllowanceClaimTable.off('deselect')
		alctAllowanceClaimTable.on('deselect', function (e, dt, type, indexes) {
			updateAlctAllowanceClaimTableToolbarButtons();
		} );

		alctAllowanceClaimTable.off('select')
		alctAllowanceClaimTable.on('select', function (e, dt, type, indexes) {
			updateAlctAllowanceClaimTableToolbarButtons();
		} );
		updateAlctAllowanceClaimTableToolbarButtons();

	} //initializeAllowanceClaimTable -- End


function updateAlctAllowanceClaimTableToolbarButtons() {
	var hasSelected = alctAllowanceClaimTable.rows('.selected').data().length > 0;

	setTableButtonState(alctAllowanceClaimTable, "deleteAllowanceClaimBtn", hasSelected);	
}

function promptDeleteAllowanceClaimBtn() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Expense?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteAllowanceClaim(alctAllowanceClaimTable);
			   }
	);
}

function deleteAllowanceClaim(tbl) {
	var postUrl = "/rest/ignite/v1/project-expense/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Allowance Claim has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateAlctAllowanceClaimTableToolbarButtons();
			});
	
}

function refreshAllowanceClaim() {
	
	var participantIdPayer = $("#alctParticipantId").val()
			
	// Send dates through to controller as Long!
	var dagVan = getMsFromDatePicker("alctStartDate");
    var dagTot= getMsFromDatePicker("alctEndDate");	
	
    var requestUrl="/rest/ignite/v1/project-expense/participant-allowance-summary/" + participantIdPayer + "/" + dagVan + "/" + dagTot; 

    // alctAllowanceClaimSummaryTable.ajax.url(springUrl(requestUrl)).load(); 
    //NB Call springUrl to set the context path on the production server when accessing urls. (Locally will work)
    
    requestUrl = "/rest/ignite/v1/project-expense/participant-allowance-claims-all/" + dagVan + "/" + dagTot + "/" + participantIdPayer;
    alctAllowanceClaimTable.ajax.url( springUrl(requestUrl)).load();    
    
    
	
}

//***********************************************************************// 

//*******************      Date range functions       *******************// 

function thisWeekAlct() {
	var prevSunday = getPreviousSunday(new Date());
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateTo.setDate(dateFrom.getDate() + 6);
	$("#alctStartDate").datepicker("setDate", new Date(dateFrom));
	$("#alctEndDate").datepicker("setDate", new Date(dateTo));
	refreshAllowanceClaim();	
}

function previousWeekAlct() {
	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("alctStartDate")));
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() - 7);
	dateTo.setDate(dateTo.getDate() - 1);
	$("#alctStartDate").datepicker("setDate", new Date(dateFrom));
	$("#alctEndDate").datepicker("setDate", new Date(dateTo));
	refreshAllowanceClaim();	
} 

function nextWeekAlct() {
	var prevSunday = getPreviousSunday(new Date(getMsFromDatePicker("alctEndDate")));
	var dateFrom = new Date(prevSunday);
	var dateTo = new Date(prevSunday);

	dateFrom.setDate(dateFrom.getDate() + 7);
	dateTo.setDate(dateTo.getDate() + 13);
	$("#alctStartDate").datepicker("setDate", new Date(dateFrom));
	$("#alctEndDate").datepicker("setDate", new Date(dateTo));
	refreshAllowanceClaim();	
}

function thisMonthAlct() {
	const now = new Date();
	const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
	const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day

	$("#alctStartDate").datepicker("setDate", new Date(firstDay));
	$("#alctEndDate").datepicker("setDate", new Date(lastDay));
	refreshAllowanceClaim();	
}

function nextMonthAlct() {
	
	const currentEndDate = new Date(getMsFromDatePicker("alctEndDate"));
	const firstDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 1, 1); //Next month's first day
	const lastDay = new Date(currentEndDate.getFullYear(), currentEndDate.getMonth() + 2, 0); //Next month's last day
	
	$("#alctStartDate").datepicker("setDate", new Date(firstDay));
	$("#alctEndDate").datepicker("setDate", new Date(lastDay));
	
	refreshAllowanceClaim();	
}

function prevMonthAlct() {

	const currentStartDate = new Date(getMsFromDatePicker("alctStartDate"));
	const firstDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth() - 1, 1); //Prev month's first day
	const lastDay = new Date(currentStartDate.getFullYear(), currentStartDate.getMonth(), 0); //Prev month's last day

	$("#alctStartDate").datepicker("setDate", new Date(firstDay));
	$("#alctEndDate").datepicker("setDate", new Date(lastDay));
	refreshAllowanceClaim();	
}

//***********************************************************************// 

//*******************      Date range functions  End      *******************// 
