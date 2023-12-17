var participantIdPayer = null;
var participantPayerSystemName = null;
var axExpenseSummaryTable = null;
var axAssetExpenseTable = null;


//***********************************************************************// 
//******************* Participant Expense information *******************// 

	function initializeAssetExpenseTable(participantId, participantSystemName) {
	
	    participantIdPayer = participantId;
	    participantPayerSystemName = participantSystemName;
		$("#axParticipantId").val(participantIdPayer);

		
		queryUrl = "/rest/ignite/v1/project-expense/asset-expenses-all/" + participantIdPayer;

		var columnsArray = [
	    		{ data: "projectExpenseId" },         			//0 MySql-TableName: VProjectExpense
	    		{ data: "projectParticipantIdPayer" }, 			//1
	    		{ data: "participantIdPayer" },       			//2
	    		{ data: "participantPayerSystemName" }, 		//3
	    		{ data: "projectId" },                			//4
	    		{ data: "projectName" },              			//5 --
	    		{ data: "subProjNumber" },              		//6 --
	    		{ data: "participantIdMadePurchase" }, 			//7
	    		{ data: "participantMadePurchaseSystemName" }, 	//8 
	    		{ data: "participantIdVendor" },      			//9	
	    		{ data: "participantVendorSystemName" }, 		//10
	    		{ data: "expenseTypeId" },            //11
	    		{ data: "expenseTypeId" },          //12
	    		{ data: "expenseTypeName" },          //13 --
	    		{ data: "expenseTypeDescription" },   //14
	    		{ data: "unitTypeCode" },             //15
	    		{ data: "expenseTypeParentId" },    //16
	    		{ data: "expenseTypeParentName" },    //17
	    		{ data: "assetId" },                 //18
	    		{ data: "vehicleId" },              //19
	    		{ data: "vehicleName" },           //20
	    		{ data: "assetDescription" },      //21
	    		{ data: "paymentMethodCode" },      //22
	    		{ data: "paymentMethodName" },       //23
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
				visible: false,
				targets: [0, 1, 2, 3, 4, 7,8, 9,10, 11, 12, 14, 15, 16, 17, 18,19,20,21, 22,24,25,26,27,28,33]
			},{
				width: "20%",
				targets: 5
			},{
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
				action: function( e, dt, node, config ) {
					axAssetExpenseTable.on('select', function (e, dt, type, indexes) {
						rowSelector = axAssetExpenseTable.rows('.selected').data()[0];
					});
					editAssetProjectExpense(null, participantIdPayer, participantPayerSystemName);
				}
			},
			{
				attr: {
					id: "deleteAssetExpenseBtn"
				},
				titleAttr: "Delete",
				text: "<i class='fas fa-minus'></i>",
				className: "btn btn-sm",
				action: function( e, dt, node, config ) {
					promptDeleteAssetExpense();
				}
			}		];
		

		axAssetExpenseTable = initializeGenericTable("axAssetExpenseTable",
											queryUrl,
				                            columnsArray,
				                            columnDefsArray,
				                            buttonsArray,
				                            function(rowSelector) {
												editAssetProjectExpense(rowSelector, participantIdPayer, participantPayerSystemName);

											},
											null,
											35,
											[[4,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
		);
		
		
		axAssetExpenseTable.off('deselect');
		axAssetExpenseTable.on('deselect', function (e, dt, type, indexes) {
			updateAssetExpenseTableToolbarButtons();
		} );

		axAssetExpenseTable.off('select');
		axAssetExpenseTable.on('select', function (e, dt, type, indexes) {
			updateAssetExpenseTableToolbarButtons();
			peRowSelected = axAssetExpenseTable.rows('.selected').data()[0];
			// initializeTimeCostDetailPanel(dt.data()); 
		} );
		updateAssetExpenseTableToolbarButtons();

	} //initializeParticipantProjectExpenseTable -- End


function updateAssetExpenseTableToolbarButtons() {
	var hasSelected = axAssetExpenseTable.rows('.selected').data().length > 0;

	setTableButtonState(axAssetExpenseTable, "deleteAssetExpenseBtn", hasSelected);	
}

function promptDeleteAssetExpense() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Expense?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteAssetExpense(axAssetExpenseTable);
			   }
	);
}

function deleteAssetExpense(tbl) {
	var postUrl = "/rest/ignite/v1/project-expense/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Project Expense has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateAssetExpenseTableToolbarButtons();
			});
	
}


//***********************************************************************// 

