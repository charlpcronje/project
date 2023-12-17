//initializeBankDetailsTab -- Start
function initializeBankTransactionTab(participantId) {
	
	showEmptyBankTransactionPanel();

	var columnsArray = [
		{ data: "participantBankDetailsId" },  //0
		{ data: "participantIdOwner" },        //1
		{ data: "bankId" },                  //2
		{ data: "bankName" },                  //3 --
		{ data: "accountTypeId" },           //4
		{ data: "accountName" },               //5 -- 
		{ data: "branchId" },                //6
		{ data: "name" },                      //7 -- 
		{ data: "description" },               //8
		{ data: "accountHolderName" },         //9 --
		{ data: "accountNumber" },	           // 10 --
		{ data: "flagDefault" }	               // 11
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1,2,4,6,8,11]
		},
		{
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.flagDefault == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 11
		},			
	];

	
	var buttonsArray = [
	];
	
	var queryUrl="/rest/ignite/v1/participant-bank-details/participant/" + participantId; 
	console.log(queryUrl);
	btBankAccountTable = initializeGenericTable("btBankAccountTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										// editParticipantBankDetails(rowSelector);  //Double click
										}
	);
  
  
		btBankAccountTable.off('deselect');
		btBankAccountTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyBankTransactionPanel();			
		// updateBDToolbarButtons();
	} );
	
		btBankAccountTable.off('select');
		btBankAccountTable.on('select', function (e, dt, type, indexes) {
		
		initializeBankTransactionTable(dt.data())
		// updateBDToolbarButtons();
	} );	

	// updateBDToolbarButtons();
}//initializeBankDetailsTab -- End

function showEmptyBankTransactionPanel() {
	setDivVisibility("emptyBankTransactionPanel", "block");
	setDivVisibility("bankTransactionPanel", "none");
}


//Bank Transactions List 1 -- Start
function initializeBankTransactionTable(bankDetailsRow) {
	
	if (bankDetailsRow == null) {
		return;
	} else {
		data = bankDetailsRow;
	}
		
	var fromDate = getMsFromDatePicker("btStartDate");
	var toDate = getMsFromDatePicker("btEndDate");
	var participantBankDetailsId = data.participantBankDetailsId;

	setDivVisibility("emptyBankTransactionPanel", "none");
	setDivVisibility("bankTransactionPanel", "block");

	
	var columnsArray = [
		{ data: "bankTransactionId" },           //0 
		{ data: "transactionDate" },             //1 --
		{ data: "participantBankDetailsId" },    //2
		{ data: "bankStatementId" },             //3 
		{ data: "statementNumber" },             //4 
		{ data: "participantIdOnTransaction" },  //5
		{ data: "linkedParticipant" },           //6 --
		{ data: "description1" },                //7 --
		{ data: "description2" },                //8 --
		{ data: "fullyLinked" },                 //9
		{ data: "amount" },		                 //10 --
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,2,3,4,5,9]
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			width: "5%",  //"40px",    //maw so klein as moontlik
			targets: [1]
		},	
		{	width: "15%", targets: 6},
		{	width: "45%", targets: 7	},
		{	width: "25%", targets: 8},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = valueToRand(data);

					// html = "<b style='font-family:Courier'>" + nrToCurrency(data) + "</b>";    //"<font face='Courier New'><b>" + nrToCurrency(data) + "</b></font>"
				}
				return (html);  
			},
			width: "10%",  targets: [10] }		

//		,{
//			render: function(data, type, row) {
//				return "<input type='checkbox' readonly onclick='return false;' " + (row.fullyLinked == "Y" ? " checked " : "") + ">";
//			},
//			className: "dt-center",
//			targets: 9
//		}		
		
		
	];

	var buttonsArray = [
		
	];
	
	var queryUrl="/rest/ignite/v1/bank-transaction/list-participant/" + participantBankDetailsId + "/" + fromDate + "/" + toDate;
	console.log(queryUrl);

	btBankTransactionTable = initializeGenericTable("btBankTransactionTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editBtdBankTransaction(rowSelector);  //Double click
										},
			                            null,
										25,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

		btBankTransactionTable.off('deselect')
		btBankTransactionTable.on('deselect', function (e, dt, type, indexes) {
//		updateBankTransactionsToolbarButtons();
	} );

		btBankTransactionTable.off('select')
		btBankTransactionTable.on('select', function (e, dt, type, indexes) {
//		updateBankTransactionsToolbarButtons();
	} );
	
//	updateBankTransactionsToolbarButtons();
}//initializeBankTransactionsList


//editBankTransaction-- Start
function editBtdBankTransaction(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	// $("#btlLinkedCount").val("");

	console.log(rowSelector);
	
	if (rowSelector != null) {
		data = btBankTransactionTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

	}
	btBankTransactionTable.rows().deselect();
	
	$("#btdBankTransactionId").val(data.bankTransactionId);
	$("#btdParticipantBankDetailsId").val(data.participantBankDetailsId);
	$("#btdBankStatementId").val(data.bankStatementId);
	$("#btdParticipantIdOnTransaction").val(data.participantIdOnTransaction);
	$("#btdBankStatement").val(data.statementNumber);
	$("#btdLinkedParticipantName").val(data.linkedParticipant);
	$("#btdDescription1").val(data.description1);
	$("#btdDescription2").val(data.description2);
	
	$("#btdAmount").val(data.amount);
	// $("#btdFullyLinked").prop("checked", data.fullyLinked == "Y");
	
	if ((data.amount == "") || (data.amount == null)) {
		$("#btdAmountShow").val("");
	} else {
		$("#btdAmountShow").val(formatDollar(data.amount)) //       formatMoney(data.amount, 2, ".", " "));	
	}	
	
//	$("#btTransactionDateShow").datepicker("setDate", data.transactionDate == null? null : new Date(data.transactionDate));
	$("#btdTransactionDateShow").val(timestampToString(data.transactionDate, false));
	
	$("#btdTransactionDate").val(data.transactionDate);
	
	// Set the Save Button to disabled
	setElementEnabled("saveBtdBankTransactionButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("btdBankTransactionDialog");
}
//editBankTransaction -- End

function closeBtdBankTransactionDialog() {
	if (somethingChangedInDialog == true)  {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("btdDlgErrorDiv", "none");
				closeModalDialog("btdBankTransactionDialog");
			});
	} else {
		setDivVisibility("btdDlgErrorDiv", "none");
		closeModalDialog("btdBankTransactionDialog");
	}
}//closeBtdBankTransactionDialog -- End


function saveBtdBankTransaction() {
	var postUrl = "/rest/ignite/v1/bank-transaction";
		var postData = {
				
			bankTransactionId: $("#btdBankTransactionId").val(),
			participantBankDetailsId: $("#btdParticipantBankDetailsId").val(),
			bankStatementId: $("#btdBankStatementId").val(),
			participantIdOnTransaction: $("#btdParticipantIdOnTransaction").val(),
			description1: $("#btdDescription1").val(),
			description2: $("#btdDescription2").val(),
			transactionDate: $("#btdTransactionDate").val(),
			amount: $("#btdAmount").val(),
			// fullyLinked: $("#btdFullyLinked").is(":checked") ? "Y" : "N"
			
	};

	var errors = "";
	
	console.log(postData);
	console.log(postUrl);
//validate

//	if ((postData.cardTypeCode == null) || (postData.cardTypeCode == "")) {
//		errors += "A Card Type is required.<br>";
//	}

	if (showFormErrors("btdDlgErrorDiv", errors)) {
		return;
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "btdBankTransactionDialog", "The Transaction has been saved.", btBankTransactionTable);
}//saveBankTransaction -- End



//btdBankTransactionDialogChanged -- Start
function btdBankTransactionDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveBtdBankTransactionButton", true);
}//btdBankTransactionDialogChanged -- End


function selectParticipantBankTransaction() {
	
	targetId = "btdParticipantIdOnTransaction";
	targetName = "btdLinkedParticipantName";
	
	var queryUrl="/rest/ignite/v1/participant/all-in-view";
	console.log(queryUrl);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="participantId";
			var refColumnName="participantId";
			var columns = [
				{ data: "participantId", name: "ParticipantId" },
				{ data: "systemName", name: "System Name" },
				{ data: "registeredName", name: "Registered Name" },
				{ data: "tradingName", name: "Trading Name" },
				{ data: "isIndividual", name: "Is Individual" },
//				{ data: "isBU", name: "Is BU" },
				{ data: "registrationNumber", name: "Registration Number" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0]
					}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var repName = row.systemName;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				btdBankTransactionDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectParticipant
