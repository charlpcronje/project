var participantBankDetailsTable = null;
var participantBankCardTable = null;

//initializeBankDetailsTab -- Start
function initializeBankDetailsTab(participantId) {
	
	var queryUrl="/rest/ignite/v1/participant-bank-details/participant/" + participantId; 
	console.log(queryUrl);

	var columnsArray = [
		{ data: "participantBankDetailsId" },  	//0
		{ data: "participantIdOwner" },        	//1
		{ data: "bankId" },                  	//2
		{ data: "bankCode" },                  	//3 --
		{ data: "bankName" },                  	//4 --
		{ data: "accountTypeId" },           	//5
		{ data: "accountName" },               	//6 -- 
		{ data: "branchId" },                	//7
		{ data: "name" },                      	//8 --
		{ data: "description" },               	//9 
		{ data: "accountHolderName" },         	//10 --
		{ data: "accountNumber" },	          	// 11 --
		{ data: "flagDefault" }	               	// 12 --
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2,5,7,9]
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
		},
		{
			attr: {
				id: "loadCsvBtn"
			},
			titleAttr: "Import transactions from CSV-files",
			text: "<i class='fas fa-file-import'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				loadImportCsvDialogDialog(participantBankDetailsTable);
			}
		},
		{
			attr: {
				id: "loadParLinkBtn"
			},
			titleAttr: "View Transactions",
			text: "<i class='fas fa-list'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				loadBankTransactionsDialog1(participantBankDetailsTable);
			}
		},
		{
			attr: {
				id: "loadBankStatementsBtn"
			},
			titleAttr: "View Bank Statements",
			text: "<i class='fas fa-file-alt'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				loadBankStatementsDialog(participantBankDetailsTable);
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
		showEmptyBankCardPanel();
		updateBDToolbarButtons();
	} );
	
	participantBankDetailsTable.off('select');
	participantBankDetailsTable.on('select', function (e, dt, type, indexes) {
		initializeBankCardTable(participantId, dt.data())
		updateBDToolbarButtons();
	} );	

	updateBDToolbarButtons();
}//initializeBankDetailsTab -- End

function showEmptyBankCardPanel() {
	setDivVisibility("bankCardEmptyPanel", "block");
	setDivVisibility("bankCardPanel", "none");
}



//updateBDToolbarButtons -- Start
function updateBDToolbarButtons() {
	var hasSelected = participantBankDetailsTable.rows('.selected').data().length > 0;
	setTableButtonState(participantBankCardTable, "addBankCardBtn", hasSelected);	
	setTableButtonState(participantBankDetailsTable, "promptDeleteBankDetailsBtn", hasSelected);	
	setTableButtonState(participantBankDetailsTable, "loadCsvBtn", hasSelected);  
	setTableButtonState(participantBankDetailsTable, "loadParLinkBtn", hasSelected);  
	setTableButtonState(participantBankDetailsTable, "loadBankStatementsBtn", hasSelected); 
	
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
}//deleteBankDetails -- End


//initializeBankCardTable 
function initializeBankCardTable(participantId, row) {

	if (row == null) {
		return;
	}

	console.log(row);
	var participantBankDetailsId = row.participantBankDetailsId;	
	var participantBankDetailsName = row.name;	
	
	var url =  "/rest/ignite/v1/bank-card/participant-bank-details/" + participantBankDetailsId;

	$("#pbdParticipantBankDetailsId").val(participantBankDetailsId);
	$("#pbdParticipantBankDetailsName").val(participantBankDetailsName);

	setDivVisibility("bankCardEmptyPanel", "none");
	setDivVisibility("bankCardPanel", "block");

	
	var columnsArray = [
        
		{data:"bankCardId"},				//0
		{data:"participantBankDetailsId"},	//1
		{data:"individualIdMainCardUser"},	//2 
		{data:"mainCardUser"},				//3 --
		{data:"cardTypeId"},				//4 
		{data:"cardTypeId_Name"},			//5 --
		{data:"cardNumber"},				//6 --
		{data:"nameOnCard"},				//7 --
		{data:"personalCard"},				//8 --
		{data:"description"}				//9 --
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0,1,2,4]
		},

		{
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.personalCard == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 8
		}
	];
	
	var buttonsArray = [
		{	attr: {
				id: "addBankCardBtn"
			},
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
//initializeBankCardTable -- End


//updateBankCardToolbarButtons -- Start
function updateBankCardToolbarButtons() {
	var hasSelected = participantBankCardTable.rows('.selected').data().length > 0;
	var hasSelected2 = participantBankDetailsTable.rows('.selected').data().length > 0;

	setTableButtonState(participantBankCardTable, "promptDeleteBankCardBtn", hasSelected);	
	setTableButtonState(participantBankCardTable, "addBankCardBtn", hasSelected2);
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
	
	
	$("#ebdBankId").val(data.bankName);
	$("#ebdAccountTypeId").val(data.accountTypeId);
	$("#ebdBranchId").val(data.branchId);
	$("#ebdParticipantBankDetailsName").val(data.name);
//	$("#ebdParticipantBankDetailsDescription").val(data.description);
	$("#ebdAccountHolderName").val(data.accountHolderName);
	$("#ebdAccountNumber").val(data.accountNumber);
	
	$("#ebdDefaultBankDetails").prop("checked", data.flagDefault == "Y");
	
	if (data.flagDefault == "Y") {
		document.getElementById("ebdDefaultBankDetails").disabled = true;
//		$("ebdDefaultBankDetails").attr('disabled','disabled');
	} else {
		document.getElementById("ebdDefaultBankDetails").disabled = false;
//		$("ebdDefaultBankDetails").removeAttr('disabled');
	}	
//	ebdDefaultBankDetails
	
	/* function populateSelect(
	elementId, html select element that will be populated 
	url, url where it must get the data (you can paste in browser window to see the data)
	idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
	displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
	selectedId, variable of the value that must be selected (null or default when new record)  or current value  
	addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
	completeMethod) java method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	*/	
	
	populateSelect("ebdBankId", 
		       "/rest/ignite/v1/bank", 
		       "bankId", 
		       "name", 
		       data.bankId, 
		       true,
		       function() {

					if ((data.bankId != undefined) && (data.bankId != null)){
						console.log("daar is 'n bank");
						var url = "/rest/ignite/v1/branch/by-bank/" + data.bankId;
						console.log("url");
					    
						
						populateSelect("ebdBranchId", 
					       url, 
					       "branchId", 
					       "name", 
					       data.branchId, 
					       true,
					       null 
						);
					}
		       }
	);
	populateSelect("ebdAccountTypeId", 
		       "/rest/ignite/v1/account-type", 
		       "accountTypeId", 
		       "name", 
		       data.accountTypeId, 
		       true,
		       null
	);
	
	// Set the Save Button to disabled
	setElementEnabled("saveParticipantBDButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("participantBankDetailsDialog");
}
//editParticipantBankDetails -- End


//saveParticipantBankDetails -- Begin
function saveParticipantBankDetails() {
	
	var postUrl = "/rest/ignite/v1/participant-bank-details"; // Used for both update and insert... Stored Procedure
	
	var postData = {	
			participantBankDetailsId : $("#ebdParticipantBankDetailsId").val(),
			participantIdOwner : $("#epParticipantId").val(),
			bankId : $("#ebdBankId").val(),
			branchId : (($("#ebdBranchId").val() == "") ? null : $("#ebdBranchId").val()),
			accountTypeId : $("#ebdAccountTypeId").val(),
			
			name : $("#ebdParticipantBankDetailsName").val(),
			description : $("#ebdParticipantBankDetailsDescription").val(),

			accountHolderName : $("#ebdAccountHolderName").val(),
			accountNumber : $("#ebdAccountNumber").val(),
					
			flagDefault : $("#ebdDefaultBankDetails").is(":checked") ? "Y" : "N",
		};

	var errors = "";

	if ((postData.name == "") ||  (postData.name == null)) {
		errors += "Please enter a Bank Details Name<br>";
	}
	if (postData.bankId =="") {
		errors += "Please select a Bank<br>";
	}
	
	if ((postData.branchId == "") ||  (postData.branchId == null)) {
	
		postData.branchId = null
	}
	
	if ((postData.accountTypeId =="") ||  (postData.accountTypeId == null)) {
		postData.accountTypeId = null
			errors += "Please select an Account Type<br>";
	}

	if (postData.accountHolderName == "") {
		errors += "Please enter an Account Holder Name<br>";
	}
	
	if (postData.accountNumber == "") {
		errors += "Please enter an Account Number<br>";
	}
	
	if (postData.name =="") {
		 $("#ebdParticipantBankDetailsName").val("");
	}

	
	if (showFormErrors("ebdDlgErrorDiv", errors)) {
		return;
	}
	
	console.log(postUrl);
	console.log(postData);
	saveEntry(postUrl, postData, "participantBankDetailsDialog", "The Participant Bank Details have been saved.", participantBankDetailsTable);
}
//saveParticipantBankDetails -- End




//linkBankTransactionsToProjectExpenses -- Begin
function linkBankTransactionsToProjectExpenses() {
	
	var participantBankDetailsId = $("#allParticipantBankDetailsId").val();
	var fromDate =  new Date(getMsFromDatePicker("tranStartDate"));
	var toDate = new Date(getMsFromDatePicker("tranEndDate"));
	
	var postUrl = "/rest/ignite/v1/bank-transaction-link/do-links/" + participantBankDetailsId + "/" + fromDate + "/" + toDate;
	
	var postData = {};
//
//			participantBankDetailsId: $("#allParticipantBankDetailsId").val(),
//		    dateFrom:  new Date(getMsFromDatePicker("tranStartDate")),
//		    dateTo:   new Date(getMsFromDatePicker("tranEndDate"))
//			
//	};
	var errors = "";
//
//	if ((postData.dateTo == null) || (postData.dateTo == "")) {
//		// Add Today's date without time
//		$("#tranEndDate").val(timestampToString(new Date(), false));
//		postData.dateTo = new Date(getMsFromDatePicker("tranEndDate"));
//	}


	if (showFormErrors("tBankTransactionsDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, null, "An attempt was made to link Expenses to this bank account.", allBankTransactionsTable, function(request, response){
		var data = response;

		var linkedCount = data;
		
		$("#btlLinkedCount").val("No of Expenses linked: " + linkedCount);

		// Set the Save Button to disabled
		//setElementEnabled("btLinkProjectExpenses", false);
	});

}
//linkBankTransactionsToProjectExpenses -- End













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
    var url = "/rest/ignite/v1/branch/by-bank/" + selectedBankId;
    console.log(url);

	console.log(selectedBankId);
	

	populateSelect("ebdBranchId", 
		       url, 
		       "branchId", 
		       "name", 
		       null, 
		       true,
		       null 
			);

}

//*********** Bank details *********** //

//participantBDDialogChanged -- Start
function participantBDDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveParticipantBDButton", true);
}//participantBDDialogChanged -- Start


//closeParticipantBankDetailsDialog -- Start
function closeParticipantBankDetailsDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("participantBankDetailsDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("participantBankDetailsDialog");
	};
	somethingChangedInDialog = false;
}
//closeParticipantBankDetailsDialog -- End



//editBankCard -- Begin
function editBankCard(rowSelector, participantId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = participantBankCardTable.row(rowSelector).data(); //Will give us the data of the double-clicked row
	} 

	$("#bcBankCardId").val(data.bankCardId);
	$("#bcParticipantId").val(participantId);
	
	$("#__igdev_bcIndividualIdMainCardUser").val(data.individualIdMainCardUser);
	$("#bcIndividualIdMainCardUserName").val(data.mainCardUser);
	
	populateSelect("bcParticipantBankDetailsId", 
				"/rest/ignite/v1/participant-bank-details/participant/" + participantId,
		       "participantBankDetailsId", 
		       "name", 
		       data.participantBankDetailsId, 
		       true,
		       null 
	);

	populateSelect("bcCardTypeId", 
		       "/rest/ignite/v1/card-type", 
		       "cardTypeId", 
		       "name", 
		       data.cardTypeId, 
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
	showModalDialog("bankCardDialog");
}
//editBankCard -- End




//saveBankCardDialog -- Begin
function saveBankCardDialog() {
	var postUrl = "/rest/ignite/v1/bank-card/new";
		
		var postData = {
			bankCardId: $("#bcBankCardId").val(),
			participantBankDetailsId: $("#bcParticipantBankDetailsId").val(),
			individualIdMainCardUser: $("#__igdev_bcIndividualIdMainCardUser").val(),		
			cardTypeId: $("#bcCardTypeId").val(),
			cardNumber: $("#bcCardNumber").val(),
			nameOnCard: $("#bcNameOnCard").val(),
			personalCard : $("#bcPersonalCard").is(":checked") ? "Y" : "N",
			description: $("#bcDescription").val(),
	};

	var errors = "";
	
	// validate

	
	if ((postData.participantBankDetailsId == null) || (postData.participantBankDetailsId == "")) {
		errors += "The Card needs to be linked to a Bank Account.<br>";
	}

	if ((postData.individualIdMainCardUser == null) || (postData.individualIdMainCardUser == "")) {
		errors += "You need to specify a Main Card User.<br>";
	}
	
	if ((postData.cardTypeId == null) || (postData.cardTypeId == "")) {
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
	saveEntry(postUrl, postData, "bankCardDialog", "The Bank Card has been saved.", participantBankCardTable);
}//saveBankCardDialog -- End

//closeBankCardDialog -- Start
function closeBankCardDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("bankCardDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("bankCardDialog");
	};
	somethingChangedInDialog = false;
}
//closeBankCardDialog -- End

function editSelectCardUser() {
	selectCardUser("__igdev_bcIndividualIdMainCardUser", "bcIndividualIdMainCardUserName");
	bankCardDialogChanged();
}

function selectCardUser(targetId, targetName) {
	var queryUrl="/rest/ignite/v1/individual/list";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="individualId";
			var refColumnName="individualId";
			var columns = [
				{ data: "individualId", name: "Id" },
				{ data: "participantId", name: "participantId" },
				{ data: "firstName", name: "First Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "idNumber", name: "ID Number" },
				{ data: "userName", name: "Username" }
			];
			var columnDefs = [
//				{ 
//					visible: false,
//					targets: 0
//					}
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

//Did anything change on the form?  Ask user to save or not.

//bankCardDialogChanged -- Start
function bankCardDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveBankCardBtn", true);
}//bankCardDialogChanged -- End

//bankCardDialog -- End



//loadBankTransactionsDialog1-- Start   ...List of Transactions...
function loadBankTransactionsDialog1(tbl) {
	
	$("#tranEndDate").val(getTodaysDate());    
	$("#tranStartDate").val(getEarlierDate(365));    
	var row = tbl.rows('.selected').data()[0];
	$("#allParticipantBankDetailsId").val(row.participantBankDetailsId);
	
	$("#bankTransactionsDialogHeader").html( "Bank Transactions: " +  $("#epdParticipantName").val() + ": " +  $("#pbdParticipantBankDetailsName").val());
			
	initializeBankTransactionsList(row.participantBankDetailsId)
	showModalDialog("bankTransactionsDialog");
	
}//loadBankTransactionsDialog1 -- End



//Bank Transactions List 1 -- Start
function initializeBankTransactionsList(participantBankDetailsId) {
	
	$("#btlLinkedCount").val("");
	var fromDate = getMsFromDatePicker("tranStartDate");
	var toDate = getMsFromDatePicker("tranEndDate");
	
	var queryUrl="/rest/ignite/v1/bank-transaction/list-participant/" + participantBankDetailsId + "/" + fromDate + "/" + toDate;

	var columnsArray = [
		{ data: "bankTransactionId" },           //0
		{ data: "transactionDate" },             //1
		{ data: "amount" },		                 //2
		{ data: "participantBankDetailsId" },    //3
		{ data: "bankStatementId" },             //4
		{ data: "statementNumber" },             //5
		{ data: "participantIdOnTransaction" },  //6
		{ data: "linkedParticipant" },           //7
		{ data: "description1" },                //8
		{ data: "description2" },                 //9
		{ data: "fullyLinked" },                 //10
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: btOnsigbaar  //[0, 3, 4, 6]
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
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = "<b style='font-family:Courier'>" + nrToCurrency(data) + "</b>";    //"<font face='Courier New'><b>" + nrToCurrency(data) + "</b></font>"
				}
				return (html);  
			},
			width: "5%",  targets: [2]
		},		
		{
			width: "5%",  targets: 5
		},
		{
			width: "18%", targets: 7
		},
		{
			width: "49%", targets: 8
		},
		{
			width: "15%", targets: 9
		},
		{
			width: "3%", targets: 10
		}

		,{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.fullyLinked == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 10
		}		
		
		
	];

	var buttonsArray = [
		
	];
	
	allBankTransactionsTable = initializeGenericTable("allBankTransactionsTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editBankTransaction(rowSelector);  //Double click
										},
			                            null,
										25,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);

	allBankTransactionsTable.off('deselect')
	allBankTransactionsTable.on('deselect', function (e, dt, type, indexes) {
//		updateAllBankTransactionsToolbarButtons();
	} );

	allBankTransactionsTable.off('select')
	allBankTransactionsTable.on('select', function (e, dt, type, indexes) {
//		updateAllBankTransactionsToolbarButtons();
	} );
	
//	updateAllBankTransactionsToolbarButtons();
}//initializeBankTransactionsList
//bankCardDialog -- END



//bankTransactionDialog -- Start
//editBankTransaction-- Start
function editBankTransaction(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	$("#btlLinkedCount").val("");
	
	
	if (rowSelector != null) {
		data = allBankTransactionsTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

//		if ((participantBankDetailsId == undefined) || (participantBankDetailsId == null)){
//			participantBankDetailsId = data.participantBankDetailsId;
//		}
	}
	allBankTransactionsTable.rows().deselect();
	
	$("#btBankTransactionId").val(data.bankTransactionId);
	$("#btParticipantBankDetailsId").val(data.participantBankDetailsId);
	$("#btBankStatementId").val(data.bankStatementId);
	$("#btParticipantIdOnTransaction").val(data.participantIdOnTransaction);
	$("#btBankStatement").val(data.statementNumber);
	$("#btLinkedParticipantName").val(data.linkedParticipant);
	$("#btDescription1").val(data.description1);
	$("#btDescription2").val(data.description2);
	
	$("#btAmount").val(data.amount);
	$("#btFullyLinked").prop("checked", data.fullyLinked == "Y");
	
	if ((data.amount == "") || (data.amount == null)) {
		$("#btAmountShow").val("");
	} else {
		$("#btAmountShow").val(formatDollar(data.amount)) //       formatMoney(data.amount, 2, ".", " "));	
	}	
	
//	$("#btTransactionDateShow").datepicker("setDate", data.transactionDate == null? null : new Date(data.transactionDate));
	$("#btTransactionDateShow").val(timestampToString(data.transactionDate, false));
	
	$("#btTransactionDate").val(data.transactionDate);
	
	populateBankTransactionLinkTable(data.bankTransactionId);

	// Set the Save Button to disabled
	setElementEnabled("saveBankTransactionButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("bankTransactionDialog");
}
//editBankTransaction -- End





//bankTransactionLinkDialog -- Start
//editBankTransactionLink-- Start
function editBankTransactionLink(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)
	
	if (rowSelector != null) {
		data = bankTransactionLinkTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

//		if ((participantBankDetailsId == undefined) || (participantBankDetailsId == null)){
//			participantBankDetailsId = data.participantBankDetailsId;
//		}
	}
	bankTransactionLinkTable.rows().deselect();
	
	$("#btlBankTransactionId").val(data.bankTransactionId);
	$("#btlBankTransactionLinkId").val(data.bankTransactionLinkId);
	
	$("#btlInvoiceId").val(data.invoiceId);
	$("#btlInvoiceIdName").val(data.invoiceId);
	
	$("#btlProjectExpenseId").val(data.projectExpenseId);
	$("#btlProjectExpenseIdName").val(data.projectExpenseId);
	
	$("#btlParticipantIdApprovedLink").val(data.participantIdApprovedLink);
	$("#btlParticipantIdApprovedLinkName").val(data.approvedBy);
	
	$("#btlLinkAmount").val(data.linkAmount);
	$("#btlAutomaticCursorLinkCreated").val(timestampToString(data.automaticCursorLinkCreated, false));
	$("#btlLinkApproved").val(timestampToString(data.linkApproved, false));	
	$("#btlDescription").val(data.description);
	
		
//	populateLinkedProjectExpenseTable(data.bankTransactionLinkId);

	// Set the Save Button to disabled
	setElementEnabled("saveBankTransactionLinkDialogBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("bankTransactionLinkDialog");
}
//editBankTransactionLink -- End






function populateBankTransactionLinkTable(bankTransactionId) {
	
	var queryUrl="/rest/ignite/v1/bank-transaction-link/list-bank-transaction/" + bankTransactionId; 
	
	var columnsArray = [
	            		{ data: "bankTransactionLinkId" },    //0 MySql-TableName: VBankTransactionLink
	            		{ data: "bankTransactionId" },        //1
	            		{ data: "invoiceId" },                //2
	            		{ data: "projectExpenseId" },         //3
	            		{ data: "participantIdApprovedLink" }, //4
	            		{ data: "linkAmount" },                 //5
	            		{ data: "automaticCursorLinkCreated" }, //6
	            		{ data: "linkApproved" },               //7
	            		{ data: "description" },              //8
	            		
	            		{ data: "participantBankDetailsId" }, //9
	            		{ data: "bankStatementId" },             //10
	            		{ data: "participantIdOnTransaction" },  //11
	            		{ data: "description1" },                //12
	            		{ data: "description2" },             //13
	            		{ data: "transactionDate" },          //14
	            		{ data: "amount" },                   //15
	            		{ data: "fullyLinkedBt" },            //16
	            		
	            		{ data: "participantIdMadePurchase" }, //17
	            		{ data: "participantIdVendor" },      //18
	            		{ data: "expenseTypeId" },            //19
	            		{ data: "assetId" },                  //20
	            		{ data: "paymentMethodCode" },        //21
	            		{ data: "bankCardIdUsed" },           //22
	            		{ data: "participantBankDetailsIdUsed" }, //23
	            		{ data: "taxDeductableCategoryId" }, //24
	            		{ data: "paymentDescription" },       //25
	            		{ data: "purchaseDate" },             //26
	            		{ data: "numberOfUnits" },            //27
	            		{ data: "amountPerUnit" },            //28

	            		{ data: "noteToAccountant" },         //29
	            		{ data: "fullyLinkedPe" },            //30
	            		{ data: "bankReference" },            //31
	            		{ data: "approvedBy" },               //32
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1,2,3,4,  8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [6,7,14,26]
		}
		
		,{
			render: function (data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = valueToRand(data);
				}
				return html;
			},
			className: "dt-right",
			targets: [5,15,28]
		}	
		
		,{
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.flagDefault == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 11
		},			
	];

	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editBankTransactionLink(null);
			}
		},
		{
			attr: {
				id: "deleteBankTransactionLinkBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteBankTransactionLink();
			}
		},
		{
			attr: {
				id: "approveLinkBtn"
			},
			titleAttr: "Approve Link",
			text: "<i class='fas fa-link'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				approveTheLink(bankTransactionLinkTable);
			}
		}
	];
	
	bankTransactionLinkTable = initializeGenericTable("bankTransactionLinkTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
										editBankTransactionLink(rowSelector);  //Double click
										}
	);
  
  
	bankTransactionLinkTable.off('deselect');
	bankTransactionLinkTable.on('deselect', function (e, dt, type, indexes) {
		updateBankTransactionLinkToolbarButtons();
	} );
	
	bankTransactionLinkTable.off('select');
	bankTransactionLinkTable.on('select', function (e, dt, type, indexes) {
		
		updateBankTransactionLinkToolbarButtons();
	} );	

	updateBankTransactionLinkToolbarButtons();
}//populateBankTransactionLinkTable -- End



//promptDeleteBankTransactionLink -- Start
function promptDeleteBankTransactionLink() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Link?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteBankTransactionLink(bankTransactionLinkTable);
			   }
	);
}
//promptDeleteBankTransactionLink -- End


//deleteBankTransactionLink -- Start
function deleteBankTransactionLink(tbl) {

	
	var row = tbl.rows('.selected').data()[0];
	var postUrl = "/rest/ignite/v1/bank-transaction-link/delete";
	
	saveEntry(postUrl, row, null, "The Link has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateBankTransactionLinkToolbarButtons();  // Disable  buttons when record has been deleted/approved.
			});
}
//deleteBankTransactionLink -- End


//approveTheLink -- Start
function approveTheLink(tbl) {
	
	var partId = $("#parMyParticipantId").val();
	var row = tbl.rows('.selected').data()[0];
	var postUrl = "/rest/ignite/v1/bank-transaction-link/approve/" + partId;
	
	saveEntry(postUrl, row, null, "The Link has been approved.", tbl,
			function(){	
				tbl.rows().deselect();
				updateBankTransactionLinkToolbarButtons();  // Disable  buttons when record has been deleted/approved.
			});
}
//approveTheLink -- End




//updateBankTransactionLinkToolbarButtons -- Start
function updateBankTransactionLinkToolbarButtons() {
	var hasSelected = bankTransactionLinkTable.rows('.selected').data().length > 0;

	setTableButtonState(bankTransactionLinkTable, "deleteBankTransactionLinkBtn", hasSelected);	
	setTableButtonState(bankTransactionLinkTable, "approveLinkBtn", hasSelected);	
}
//updateBankTransactionLinkToolbarButtons -- End



function saveBankTransaction() {
	var postUrl = "/rest/ignite/v1/bank-transaction";
		var postData = {
				
			bankTransactionId: $("#btBankTransactionId").val(),
			participantBankDetailsId: $("#btParticipantBankDetailsId").val(),
			bankStatementId: $("#btBankStatementId").val(),
			participantIdOnTransaction: $("#btParticipantIdOnTransaction").val(),
			description1: $("#btDescription1").val(),
			description2: $("#btDescription2").val(),
			transactionDate: $("#btTransactionDate").val(),
			amount: $("#btAmount").val(),
			fullyLinked: $("#btFullyLinked").is(":checked") ? "Y" : "N"
			
	};

	var errors = "";
	
//validate


	if (showFormErrors("btDlgErrorDiv", errors)) {
		return;
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "bankTransactionDialog", "The Transaction has been saved.", allBankTransactionsTable);
}//saveBankTransaction -- End

//bankTransactionDialogChanged -- Start
function bankTransactionDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveBankTransactionButton", true);
}//bankTransactionDialogChanged -- End


function selectBankStatement() {
	
	var fromDate = new Date("1970-01-01");
	var toDate = new Date("2030-01-01");	
	
	var pbdId = $("#btParticipantBankDetailsId").val();
	
	var queryUrl="/rest/ignite/v1/bank-statement/list-all/" + pbdId + "/" + fromDate + "/" + toDate;
	var targetId = "btBankStatementId";
	var targetName = "btBankStatement";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="bankStatementId";
			var refColumnName="bankStatementId";
			var columns = [
			               		               
				{ data: "bankStatementId", name: "BankStatementId" },
				{ data: "participantBankDetailsId", name: "ParticipantBankDetailsId" },
				{ data: "description", name: "Description" },
				{ data: "statementNumber", name: "Statement Number" },
				{ data: "statementDate", name: "Statement Date" },
				{ data: "transactionDateFrom", name: "Transaction Date From" },
				{ data: "transactionDateTo", name: "Transaction Date To" }
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0,1,2]
				},
				{
					render: function (data, type, row) {
						var html = data;
						if (type == "display") {
							html = timestampToString(data, false);
						}
						return html;
					},
					width: "100px",
					targets: [4,5,6]
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.bankStatementId;
				var repName = row.statementNumber;

				document.getElementById('btBankStatementId').value = id;
				document.getElementById('btBankStatement').value = repName;

//				$("#" + btBankStatementId).val(id);
//				$("#" + btBankStatement).val(repName);


				bankTransactionDialogChanged();
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectBankStatement





function closeBankTransactionDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("btDlgErrorDiv", "none");
				closeModalDialog("bankTransactionDialog");
			});
	} else {
		setDivVisibility("btDlgErrorDiv", "none");
		closeModalDialog("bankTransactionDialog");
	}
}//closeBankTransactionDialog -- End



function editSelectParticipantBankTransaction() {
	selectParticipantBt("btParticipantIdOnTransaction", "btParticipantName");
	peDialogChanged();
}

function selectParticipantBt(targetId, targetName) {
	
	var queryUrl="/rest/ignite/v1/participant/all-in-view";

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
				bankTransactionDialogChanged();
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

//bankTransactionDialog -- End

//importCsvDialog -- Start
var arrayFromDB = [];
var arrayFromFileFinal = [];

var btOnsigbaar = [0, 3, 4, 6]


//loadImportCsvDialogDialog-- Start  ...This is where you import CSV files...
function loadImportCsvDialogDialog(tbl) {
	
	var row = tbl.rows('.selected').data()[0];
	let element1 = document.getElementById("myProgressBar"); element1.setAttribute("hidden","");
			
	$("#csvParticipantBankDetailsId").val(row.participantBankDetailsId);
	$("#csvBankId").val(row.bankId);
	$("#csvAccountTypeId").val(row.accountTypeId);
	$("#csvAccountNumber").val(row.accountNumber);
	
	// Set the Save Button to disabled
	setElementEnabled("importButton", false);
	setElementEnabled("uploadfile", true);	
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	$("#uploadfile").val("");
	$("#csvTextarea").val("");
	$("#csvAccNrFromFileName").val("");

	arrayFromDB.length = 0;
	arrayFromFileFinal.length = 0;
	$("#arrayFromDBinput").val("0");
	$("#arrayFromFileFinalinput").val("0");
	showModalDialog("importCsvDialog");
	
	GetLastTransaction();
	
}//loadImportCsvDialogDialog -- End


function GetLastTransaction() {
	
	var queryUrl;
	var pBDId = $("#csvParticipantBankDetailsId").val();
	var inskrywing;
	
	queryUrl = "/rest/ignite/v1/bank-transaction/last-bank-transaction/" + pBDId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var d = new Date(data.transactionDate);
			var dateFrom = [     //Format die datum na yyyy-mm-dd
			  d.getFullYear(),
			  ('0' + (d.getMonth() + 1)).slice(-2),
			  ('0' + d.getDate()).slice(-2)
			].join('-');			
			if (data.amount == null) {
				$("#csvLastTransaction").val("");
			} else {
				inskrywing = dateFrom + "  " + data.amount.toFixed(2) + "  " + data.description1;									
				$("#csvLastTransaction").val(inskrywing);	
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//GetLastTransaction


//closeImportCsvDialog -- Start
function closeImportCsvDialog() {
	
	setDivVisibility("csvDlgErrorDiv", "none");
	closeModalDialog("importCsvDialog");
		
}//closeImportCsvDialog -- End


function showFileInTextArea() {

	const eenLyn = [];
	arrayFromDB.length = 0;
	arrayFromFileFinal.length = 0;
	$("#arrayFromDBinput").val("0");
	$("#arrayFromFileFinalinput").val("0");
	
			
	var dieLyne;
	var bankKode = $("#csvBankCode").val();
	var rekTipe =  $("#csvBankAccountTypeId").val();
	var accNrFromDb = $("#csvAccountNumber").val();
			
	Papa.parse(document.getElementById('uploadfile').files[0],
	{
		download: true,
		header: false,
		skipEmptyLines: true,
		complete: function(results){
			for (i = 0; i < 10; i++) {
				eenLyn.push(results.data[i]);
			}
			dieLyne = results.data[0];
			dieLyne += "\n" + results.data[1];
			dieLyne += "\n" + results.data[2];
			dieLyne += "\n" + results.data[3];
			dieLyne += "\n" + results.data[4];					
			dieLyne += "\n" + results.data[5];				
			dieLyne += "\n" + results.data[6];				
			dieLyne += "\n" + results.data[7];			
			dieLyne += "\n" + results.data[8];			
			dieLyne += "\n" + "....";			
			$("#csvTextarea").val(dieLyne);
		}
	})
	
	var fileName = $("#uploadfile").val();
	var fileName2 = fileName.substring(12);         	//remove "C:\fakepath\"
	var fileName3 = fileName2.slice(0, -4);					//remove ".csv"
	var fileName4 = fileName3.replace(/\((.+?)\)/g, "()");    //remove special chars
	var fileName5 = fileName4.replace(/[^0-9 ]/g, '');    //remove everything except Numeric chars
	var fileName6 = $.trim(fileName5);						//remove leading and traling spaces

	let fileLen = fileName6.length;
	if (fileLen == 18){
		var fileName7 = fileName6.substring(0, 10);
	} else {
		var fileName7 = fileName6;
	}
	
	$("#csvAccNrFromFileName").val(fileName7);
	var accNrFromFile = $("#csvAccNrFromFileName").val();
	
	console.log(bankKode);
	switch(bankKode) {
	  case "SBSA":  //Standard bank
		  if (accNrFromFile == accNrFromDb) {
			  setElementEnabled("importButton", true);
		  } else {
			  setElementEnabled("importButton", false);
		  }
		  break;
	  case "FNB":  //First National bank
		  if (accNrFromFile == accNrFromDb) {
			  setElementEnabled("importButton", true);
		  } else {
			  setElementEnabled("importButton", false);
		  }
		  break;
		  
	  case "ABSA": //ABSA      console.log("ABSA switch statement");
		  
		  setElementEnabled("importButton", true);
		  
//		  const myAnchor = document.getElementById("importButton") 
//		  let text = myAnchor.getAttribute("disabled");		 
//		  console.log("importButton: " + text);
		  
		  break;
		  
	  case "CBL":  //Capitec
		  break;
	  case "IBL":  //Investec
		  break;
	  case "NL":   //Nedbank    console.log("Nedbank switch statement");
		  
		  if (accNrFromFile == accNrFromDb) {
			  setElementEnabled("importButton", true);
		  } else {
			  setElementEnabled("importButton", false);
		  }
		  break;

	  default:
//	    console.log(bankKode);
	} 		

	fillArrayOfDataOnDB();
	fillArrayOfDataInFile();

	let element1 = document.getElementById("myProgressBar"); element1.setAttribute("hidden","");
	setDivVisibility("csvDlgErrorDiv", "none");
}//showFileInTextArea


function fillArrayOfDataOnDB() {
	
	$.ajax({
	       type: 'GET',
	        url: '/rest/ignite/v1/bank-transaction/list-all/' +  $("#csvParticipantBankDetailsId").val(),
	        sync: true,
	        success: function(data1){
	    	   data1.sort((a, b) => a.bankTransactionId - b.bankTransactionId);	    	   
//	    	   data.sort()
//	    	   console.dir(data1);
//	    	   console.log(data1.length)
	    	   
	    	   arrayFromDB = data1;
	    	   $("#arrayFromDBinput").val(arrayFromDB.length);
//	    	   return data1
	       }
	 });	
//	return data1
}

function fillArrayOfDataInFile() {
	
	Papa.parse(document.getElementById('uploadfile').files[0],
	{
		download: true,
		header: false,
		skipEmptyLines: true,
		complete: function(results){
			//console.log("van File Array-grootte: " + results.data.length);
			
			switch( $("#csvBankCode").val()) {
			  case "SBSA":  //Standard bank
				  importToArraySBSA(results.data, $("#csvBankAccountTypeId").val());
				  break;
			  case "FNB": 
				  importToArrayFNB(results.data);
				  break;
			  case "ABSA": //ABSA
				  importToArrayABSA(results.data)
				  break;
				  
			  case "CBL":  //Capitec
			  case "IBL":  //Investec
			  case "NL":   //Nedbank
				  importToArrayNL(results.data);
				  break;
//			  default:
			    // code block
			} 			
		}
	});
}


function importCsvFile() {

	setElementEnabled("importButton", false);
	setElementEnabled("uploadfile", false);	
	setElementEnabled("theCancelButton", false);
	
	element1 = document.getElementById("myProgressBar"); 
	element1.removeAttribute("hidden");
	
	compareArrays();
	GetLastTransaction();
	setElementEnabled("uploadfile", true);	
	setElementEnabled("theCancelButton", true);	
} 


async function compareArrays() {     //async
	
	var datum1 =arrayFromFileFinal[0].transactionDate;
	var datum2 = arrayFromFileFinal[1].transactionDate;
	var datum3 = arrayFromFileFinal[2].transactionDate;
	var bedrag1 = arrayFromFileFinal[0].amount;
	var bedrag2 = arrayFromFileFinal[1].amount;
	var bedrag3 = arrayFromFileFinal[2].amount;
	var desc1 = arrayFromFileFinal[0].description1;
	var desc2 = arrayFromFileFinal[1].description1;
	var desc3 = arrayFromFileFinal[2].description1;
	
	var dbCounter = 0;

  for(var db = 0; db < arrayFromDB.length; db++){ //find starting point in DB data, to compare file with
  	if ((arrayFromDB[db].transactionDate == datum1) && (arrayFromDB[db].description1 == desc1) && (arrayFromDB[db].amount == bedrag1)) {
  		if ((arrayFromDB[db+1].transactionDate == datum2) && (arrayFromDB[db+1].description1 == desc2) && (arrayFromDB[db+1].amount == bedrag2)) {
  			if ((arrayFromDB[db+2].transactionDate == datum3) && (arrayFromDB[db+2].description1 == desc3) && (arrayFromDB[db+2].amount == bedrag3)) {
//  				console.log(arrayFromDB[db].amount + " ||| " + arrayFromDB[db].description1 + "  db:" + db + " begin hier op die DB vergelyk: " + arrayFromDB[db].transactionDate + "  " + arrayFromDB[db].amount + " " + arrayFromDB[db].description1);
  				dbCounter = db;
  				break;
  			}	
  		}	
  	}
  }		
	
	
	if ((arrayFromDB.length > 0) && (dbCounter == 0)){
		
		var errors = "";
		errors += "No entry point found, ie. the first three transactions on the file are not in the Database.<br>";
		errors += "Are you sure the file you are trying to import, is for this account?<br>";
				
		if (showFormErrors("csvDlgErrorDiv", errors)) {
			let element1 = document.getElementById("myProgressBar"); element1.setAttribute("hidden","");
			return;
		}

			
	} else {
		
		for(var fi = 0; fi < arrayFromFileFinal.length; fi++){
			
			if ((dbCounter < arrayFromDB.length) && (dbCounter > 0) //skip, hy is klaar in die DB
				&& (arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi].amount)  
				&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi].description1)
				&& (arrayFromDB[dbCounter].transactionDate == arrayFromFileFinal[fi].transactionDate)) {
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + "  klaar in DB.");
						
						
			} else if (((dbCounter < arrayFromDB.length) && (dbCounter > 0)) //iets stem nie ooreen nie.
				&& ((arrayFromDB[dbCounter].amount != arrayFromFileFinal[fi].amount) 
				|| (arrayFromDB[dbCounter].transactionDate != arrayFromFileFinal[fi].transactionDate)
				|| (arrayFromDB[dbCounter].description1 != arrayFromFileFinal[fi].description1))) {
//console.log("XXXXXXXXXXXXX iets stem nie ooreen nie XXXXXXXXXXXXXXXXX  bankTransactionId: " + arrayFromDB[dbCounter].bankTransactionId);

//console.log("[dbCounter+1].amount " + arrayFromDB[dbCounter+1].amount + "  [fi].amount " + arrayFromFileFinal[fi].amount);
//console.log("[dbCounter+1].description1 " + arrayFromDB[dbCounter+1].description1 + "  [fi].description1 " + arrayFromFileFinal[fi].description1);
//console.log("[dbCounter+1].transactionDate " + arrayFromDB[dbCounter+1].transactionDate + "  [fi].transactionDate " + arrayFromFileFinal[fi].transactionDate);
//console.log("[dbCounter+2].amount " + arrayFromDB[dbCounter+2].amount + "  [fi+1].amount " + arrayFromFileFinal[fi+1].amount);
//console.log("[dbCounter+2].description1 " + arrayFromDB[dbCounter+2].description1 + "  [fi+1].description1 " + arrayFromFileFinal[fi+1].description1);
//console.log("[dbCounter+2].transactionDate " + arrayFromDB[dbCounter+2].transactionDate + "  [fi+1].transactionDate " + arrayFromFileFinal[fi+1].transactionDate);
		
	
					if ((dbCounter + 1) == arrayFromDB.length) { // laaste rekord in DB
//console.log("laaste rekord in DB (iets verskil)  bankTransactionId: " + arrayFromDB[dbCounter].bankTransactionId);	
	
						if ((arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi].amount) //net datum verskil, so maak die datum reg
								&& (arrayFromDB[dbCounter].transactionDate != arrayFromFileFinal[fi].transactionDate)
								&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi].description1)) {
							const result01 = await saveBankTransactionUpdate(arrayFromDB[dbCounter].bankTransactionId, arrayFromDB[dbCounter].bankStatementId, 
									arrayFromDB[dbCounter].participantIdOnTransaction, arrayFromDB[dbCounter].description2, arrayFromFileFinal[fi].transactionDate, 
									arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + " .01.  laaste rekord in DB datum reggemaak.");
	
						} else if ((fi + 1) == arrayFromFileFinal.length) {		// laaste rekord in DB, en laaste rekord in File oorskryf
							const result02 = await saveBankTransactionUpdate(arrayFromDB[dbCounter].bankTransactionId, null, null, null, arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + " .02.  iets verskil (laaste rekord in DB en laaste rekord in File); oorskryf.");
	
						} else if ((arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi+1].amount) //db stem ooreen met volgende rekord op file, so insert, en skip volgende rekord op file
								&& (arrayFromDB[dbCounter].transactionDate == arrayFromFileFinal[fi+1].transactionDate)
								&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi+1].description1)){   
							const result03 = await saveBankTransactionInsert(arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: --  fi:" + fi + "  .03.  ekstra rekord ge-insert, na laaste rekord op DB. " + arrayFromFileFinal[fi].amount);	
							fi++;
	
						} else if (((fi + 3) <= arrayFromFileFinal.length) && (arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi+2].amount) //db stem ooreen met volgende volgende rekord op file, so insert, en skip volgende rekord op file
								&& (arrayFromDB[dbCounter].transactionDate == arrayFromFileFinal[fi+2].transactionDate)
								&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi+2].description1)){   
							const result04 = await saveBankTransactionInsert(arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
							const result05 = await saveBankTransactionInsert(arrayFromFileFinal[fi+1].transactionDate, arrayFromFileFinal[fi+1].amount, arrayFromFileFinal[fi+1].description1, fi+1, arrayFromFileFinal.length);
//console.log("dbCounter: --  fi:" + fi + "  .04.05.  2 ekstra rekords ge-insert, na laaste rekord op DB. " + arrayFromFileFinal[fi].amount + " en " +  + arrayFromFileFinal[fi+1].amount);				
							fi++;
							fi++;
							
						} else {   //iets anders verskil; oorskryf.  Dws bankStatementId, participantIdOnTransaction, description2 gaan verlore (as daar data was).
							const result06 = await saveBankTransactionUpdate(arrayFromDB[dbCounter].bankTransactionId, null, null, 
									null, arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + " .06.  iets verskil (nie net die datum nie); oorskryf.");	
	
						}
	
	
					} else if ((fi + 1) == arrayFromFileFinal.length) { //laaste rekord in file, terwyl daar meer in die DB is; ignoreer.
						// doen niks.
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + "  laaste rekord in file, terwyl daar meer in die DB is; ignoreer..");
	
					} else if ((arrayFromDB[dbCounter+1].amount == arrayFromFileFinal[fi+1].amount)  //volgende rekord lyk ok, so update datum, bedrag en desc.
							&& (arrayFromDB[dbCounter+1].description1 == arrayFromFileFinal[fi+1].description1)
							&& (arrayFromDB[dbCounter+1].transactionDate == arrayFromFileFinal[fi+1].transactionDate)) {
						const result07 = await saveBankTransactionUpdate(arrayFromDB[dbCounter].bankTransactionId, arrayFromDB[dbCounter].bankStatementId, arrayFromDB[dbCounter].participantIdOnTransaction, arrayFromDB[dbCounter].description2, arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + " .07.  volgende rekord lyk ok, so update datum, bedrag en desc.");	

					} else if ((arrayFromDB[dbCounter+1].amount == arrayFromFileFinal[fi].amount)  //'n rekord in die DB, wat missing is in die file
							&& (arrayFromDB[dbCounter+1].description1 == arrayFromFileFinal[fi].description1)
							&& (arrayFromDB[dbCounter+1].transactionDate == arrayFromFileFinal[fi].transactionDate)
							&& (arrayFromDB[dbCounter+2].amount == arrayFromFileFinal[fi+1].amount) 
							&& (arrayFromDB[dbCounter+2].description1 == arrayFromFileFinal[fi+1].description1)
							&& (arrayFromDB[dbCounter+2].transactionDate == arrayFromFileFinal[fi+1].transactionDate)) {
						const result15 = await saveBankTransactionDelete(arrayFromDB[dbCounter].bankTransactionId);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + " .15.  'n rekord in die DB, wat missing is in die file; delete");	
						dbCounter++;


					} else if ((arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi+1].amount) //db stem ooreen met volgende rekord op file, so insert, en skip volgende rekord op file
							&& (arrayFromDB[dbCounter].transactionDate == arrayFromFileFinal[fi+1].transactionDate)
							&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi+1].description1)){   
						const result08 = await saveBankTransactionInsert(arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: --  fi:" + fi + "  ekstra rekord op file ge-insert. " + arrayFromFileFinal[fi].amount);				
						fi++;
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromDB[dbCounter].amount + " .08.  klaar in DB.");
	
					} else if (((fi+3) <= arrayFromFileFinal.length) && (arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi+2].amount) //db stem ooreen met volgende volgende rekord op file, so insert, en skip volgende rekord op file
							&& (arrayFromDB[dbCounter].transactionDate == arrayFromFileFinal[fi+2].transactionDate)
							&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi+2].description1)){   
						const result09 = await saveBankTransactionInsert(arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
						const result10 = await saveBankTransactionInsert(arrayFromFileFinal[fi+1].transactionDate, arrayFromFileFinal[fi+1].amount, arrayFromFileFinal[fi+1].description1, fi+1, arrayFromFileFinal.length);
//console.log("dbCounter: --  fi:" + fi + "   ekstra 2rekords op file ge-insert. " + arrayFromFileFinal[fi].amount + " " + arrayFromFileFinal[fi+1].amount);
						fi++;
						fi++;
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromDB[dbCounter].amount + " .09.10.  klaar in DB.");
	
					} else if (((fi+4) <= arrayFromFileFinal.length) && (arrayFromDB[dbCounter].amount == arrayFromFileFinal[fi+3].amount) //db stem ooreen met volgende volgende rekord op file, so insert, en skip volgende rekord op file
							&& (arrayFromDB[dbCounter].transactionDate == arrayFromFileFinal[fi+3].transactionDate)
							&& (arrayFromDB[dbCounter].description1 == arrayFromFileFinal[fi+3].description1)){   
						const result11 = await saveBankTransactionInsert(arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
						const result12 = await saveBankTransactionInsert(arrayFromFileFinal[fi+1].transactionDate, arrayFromFileFinal[fi+1].amount, arrayFromFileFinal[fi+1].description1, fi+1, arrayFromFileFinal.length);
						const result13 = await saveBankTransactionInsert(arrayFromFileFinal[fi+2].transactionDate, arrayFromFileFinal[fi+2].amount, arrayFromFileFinal[fi+2].description1, fi+2, arrayFromFileFinal.length);
//console.log("dbCounter: --  fi:" + fi + "   ekstra 3rekords op file ge-insert. " + arrayFromFileFinal[fi].amount + " " + arrayFromFileFinal[fi+1].amount + " " + arrayFromFileFinal[fi+2].amount);
						fi++;
						fi++;
						fi++;
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromDB[dbCounter].amount + " .11.12.13.  klaar in DB.");
	
					} else { //Oorskryf, 
						const result14 = await saveBankTransactionUpdate(arrayFromDB[dbCounter].bankTransactionId, null, null, null, arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + "  " +  arrayFromFileFinal[fi].amount + " .14.  iets verskil; oorskryf alles.");	

					}
	
	
	//					
	//					
	//					
	//					}
	//				}
	//			
	//
	//, arrayFromDB[dbCounter].bankStatementId, arrayFromDB[dbCounter].participantIdOnTransaction, arrayFromDB[dbCounter].description2
	//
	
			} else if ((dbCounter == 0) || (dbCounter >= arrayFromDB.length)) {  //insert, hierdie is nog nie in die DB nie
				const result = await saveBankTransactionInsert(arrayFromFileFinal[fi].transactionDate, arrayFromFileFinal[fi].amount, arrayFromFileFinal[fi].description1, fi, arrayFromFileFinal.length);
//console.log("dbCounter: " + dbCounter + "  fi:" + fi + " insert " +  arrayFromFileFinal[fi].amount + "  " + arrayFromFileFinal[fi].transactionDate);	
	
	
	
			} else {
//console.log("Groot fout! - " + arrayFromFileFinal[fi].transactionDate + " amt: " + arrayFromFileFinal[fi].amount + " desc:  " + arrayFromFileFinal[fi].description1);
	
	
			}
				
			
			dbCounter++;
			
		}  //for(var fi = 0; fi < arrayFromFileFinal.length; fi++)
	} 
}

function saveBankTransactionInsert(dieDatum, dieBedrag, dieDesc, i, Total) {
	
	var elem = document.getElementById("myBar");
//	var ms = dieDatum.getTime();
//	console.dir("ms:" +  ms)		
	 return new Promise((resolve, reject) => {
		 
		    setTimeout(() => {	
	
				var postUrl = "/rest/ignite/v1/bank-transaction/new";
				
				var postData = {
						participantBankDetailsId : $("#csvParticipantBankDetailsId").val(),
						bankStatementId : null,
						participantIdOnTransaction : null,
						description1 : dieDesc,
						description2 : null,
						transactionDate : dieDatum,    //ms
						amount : dieBedrag
				};
//console.dir(postData)		
				// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
				saveEntry(postUrl, postData, null, null, null, function(request, response){
					var data = response;

			    	width = (i+1)/Total*100;                         ////
			    	elem.style.width = width + "%";  ////
//console.log(dieBedrag + "<<<bedrag   " + i + "  " + width)	
					if (i < 9) {
						setElementEnabled("uploadfile", false);	
						setElementEnabled("theCancelButton", false);
					}
					if ((i+1) == Total) {
						setElementEnabled("uploadfile", true);	
						setElementEnabled("theCancelButton", true);
						GetLastTransaction();
					}
				});
				resolve(dieBedrag);   //weet nie wat hierdie doen nie.
		    }, 50);  //dws 20 inserts per sekonde
	  });
		
}//saveBankTransactionInsert -- End


function saveBankTransactionUpdate(bankTransactionId, bankStatementId, participantIdOnTransaction, description2, dieDatum, dieBedrag, dieDesc, i, Total) {
	
	var elem = document.getElementById("myBar");
//	var ms = dieDatum.getTime();
//	console.dir("ms:" +  ms)		
	 return new Promise((resolve, reject) => {
		 
		    setTimeout(() => {	
	
				var postUrl = "/rest/ignite/v1/bank-transaction";
				
				var postData = {
						bankTransactionId : bankTransactionId,
						participantBankDetailsId : $("#csvParticipantBankDetailsId").val(),
						bankStatementId : bankStatementId,
						participantIdOnTransaction : participantIdOnTransaction,
						description1 : dieDesc,
						description2 : description2,
						transactionDate : dieDatum,    //ms
						amount : dieBedrag
				};
//console.dir(postData)		
				// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
				saveEntry(postUrl, postData, null, null, null, function(request, response){
					var data = response;

			    	width = (i+1)/Total*100;                         ////
			    	elem.style.width = width + "%";  ////
//console.log(dieBedrag + "<<<bedrag   " + i + "  " + width)	
					if (i < 9) {
						setElementEnabled("uploadfile", false);	
						setElementEnabled("theCancelButton", false);
					}
					if ((i+1) == Total) {
						setElementEnabled("uploadfile", true);	
						setElementEnabled("theCancelButton", true);
						GetLastTransaction();
					}
				});
				resolve(dieBedrag);   //weet nie wat hierdie doen nie.
		    }, 50);  //dws 20 inserts per sekonde
	  });
		
}//saveBankTransactionUpdate -- End



function saveBankTransactionDelete(bankTransactionId) {
	
	var queryUrl;
	
	queryUrl = "/rest/ignite/v1/bank-transaction/delete/" + bankTransactionId;
	
//	console.log("/////rest/ignite/v1/task-submission/delete/" + bankTransactionId);
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//GetLastTransaction



function importToArrayNL(arrayFromFile, accType){   //Nedbank
	var lengteVanArray = arrayFromFile.length;
	var dieDesc;
  for(var i = 0; i < arrayFromFile.length; i++){
  	let dieBedrag = arrayFromFile[i][2];
  	let dieNommer = arrayFromFile[i][0];
  	let dieDekade = dieNommer.substring(7, 8);
  	let lengte = dieNommer.length;
  	if (((dieDekade == 2) || (dieDekade == 1)) && (lengte == 9)){
	    	let dieJaar = dieNommer.substring(7, 9);
	    	let dieMaand = dieNommer.substring(3, 6);
	    	let dieDag = dieNommer.substring(0, 2);
	    	
			if(dieMaand == 'Jan') { dieMaand = '01'};
			if(dieMaand == 'Feb') { dieMaand = '02'};
			if(dieMaand == 'Mar') { dieMaand = '03'};
			if(dieMaand == 'Apr') { dieMaand = '04'};
			if(dieMaand == 'May') { dieMaand = '05'};
			if(dieMaand == 'Jun') { dieMaand = '06'};
			if(dieMaand == 'Jul') { dieMaand = '07'};
			if(dieMaand == 'Aug') { dieMaand = '08'};
			if(dieMaand == 'Sep') { dieMaand = '09'};	
			if(dieMaand == 'Oct') { dieMaand = '10'};
			if(dieMaand == 'Nov') { dieMaand = '11'};
			if(dieMaand == 'Dec') { dieMaand = '12'};	    	
	    	
	    	let dieDatum = new Date("20" + dieJaar + "/" + dieMaand + "/" + dieDag);
//	    	console.log("dieDatum: " + dieDatum);
	    			
	    	    	var valueToPush = { }; // or "var valueToPush = new Object();" which is the same
	    	    	valueToPush["counter"] = i;
	    	    	valueToPush["transactionDate"] = dieDatum.getTime();
	    	    	valueToPush["description1"] = arrayFromFile[i][1];
	    	    	valueToPush["amount"] = arrayFromFile[i][2];
	    	    	arrayFromFileFinal.push(valueToPush);	    			

//	    	const result = await saveBankTransactions(dieDatum, dieBedrag, dieDesc, i, lengteVanArray)
  	} //else {
//  		if (i+1 >= lengteVanArray) {
//  			const result = await finishProgressBar(i)
//  		}
//  	}
  }
	arrayFromFileFinal.sort((a, b) => a.counter - b.counter);
	$("#arrayFromFileFinalinput").val(arrayFromFileFinal.length);
};//importNL (Nedbank)



function importToArraySBSA(arrayFromFile, accType){    //Standard bank
	var lengteVanArray = arrayFromFile.length;
	var dieDesc;
  for(var i = 0; i < arrayFromFile.length; i++){
  	let dieNommer = arrayFromFile[i][1];
  	let dieEeu = dieNommer.substring(0, 2);
  	let lengte = dieNommer.length;
  	if ((dieEeu == 20) && (lengte == 8)){
	    	let dieJaar = dieNommer.substring(0, 4);
	    	let dieMaand = dieNommer.substring(4, 6);
	    	let dieDag = dieNommer.substring(6, 8);
	    	let dieDatum = new Date(dieJaar + "/" + dieMaand + "/" + dieDag);
//	    	console.log(dieDatum);
	    	if (accType == "CURRENT") {
	    		dieDesc = arrayFromFile[i][4] + "||" + arrayFromFile[i][5];
	    	} else {
	    		dieDesc = arrayFromFile[i][4];
	    	}
	    	
	    	var valueToPush = { }; // or "var valueToPush = new Object();" which is the same
	    	valueToPush["counter"] = i;
	    	valueToPush["transactionDate"] = dieDatum.getTime();
	    	valueToPush["description1"] = dieDesc;
	    	valueToPush["amount"] = arrayFromFile[i][3];
	    	arrayFromFileFinal.push(valueToPush);		    	
	    	
//	    	const result = await saveBankTransactions(dieDatum, dieBedrag, dieDesc, i, lengteVanArray)
  	} //else {
//  		if (i+1 == lengteVanArray) {
//  			const result = await finishProgressBar(i)
//  		}
//  	}
  }
	arrayFromFileFinal.sort((a, b) => a.counter - b.counter);
	$("#arrayFromFileFinalinput").val(arrayFromFileFinal.length);
};//importSBSA  (Standard bank)



function importToArrayABSA(arrayFromFile){     //ABSA
	var lengteVanArray = arrayFromFile.length;
	var dieDesc;
  for(var i = 0; i < arrayFromFile.length; i++){
  	let dieNommer = arrayFromFile[i][0];
  	let dieEeu = dieNommer.substring(0, 2);
  	let lengte = dieNommer.length;
  	if ((dieEeu == 20) && (lengte == 8)){
	    	let dieJaar = dieNommer.substring(0, 4);
	    	let dieMaand = dieNommer.substring(4, 6);
	    	let dieDag = dieNommer.substring(6, 8);
	    	let dieDatum = new Date(dieJaar + "/" + dieMaand + "/" + dieDag);
//	    	console.log(dieDatum);
	    	
	    	var valueToPush = { }; // or "var valueToPush = new Object();" which is the same
	    	valueToPush["counter"] = i;
	    	valueToPush["transactionDate"] = dieDatum.getTime();
	    	valueToPush["description1"] = arrayFromFile[i][1];
	    	valueToPush["amount"] = arrayFromFile[i][2];
	    	arrayFromFileFinal.push(valueToPush);	    		    	
	    	
//	    	const result = await (dieDatum, dieBedrag, dieDesc, i, lengteVanArray)
  	}
  }
	arrayFromFileFinal.sort((a, b) => a.counter - b.counter);
	$("#arrayFromFileFinalinput").val(arrayFromFileFinal.length);
};//importABSA



function importToArrayFNB(arrayFromFile){    //First National Bank

	var lengteVanArray = arrayFromFile.length;
	
  for(var i = 0; i < arrayFromFile.length; i++){

  	let dieNommer = arrayFromFile[i][0];
  	let dieEeu = dieNommer.substring(0, 2);
  	let lengte = dieNommer.length;
  	if ((dieEeu == 20) && (lengte == 10)){

	    	let dieDatum = new Date(dieNommer);

	    	var valueToPush = { }; // or "var valueToPush = new Object();" which is the same
	    	valueToPush["counter"] = i;
	    	valueToPush["transactionDate"] = dieDatum.getTime();
	    	valueToPush["description1"] = arrayFromFile[i][3];
	    	valueToPush["amount"] = arrayFromFile[i][1];
	    	arrayFromFileFinal.push(valueToPush);	    	
	    		
//	    	const result = await saveBankTransactions(dieDatum, dieBedrag, dieDesc, i, lengteVanArray)
  	}
  }
  arrayFromFileFinal.sort((a, b) => a.counter - b.counter);
  $("#arrayFromFileFinalinput").val(arrayFromFileFinal.length);
} //importFNB

function finishProgressBar(i) {
	
	var elem = document.getElementById("myBar");
	
	 return new Promise((resolve, reject) => {
		 
		    setTimeout(() => {	
//	    		console.log("i se waarde: " + i)
		    	elem.style.width = "100%";
				setElementEnabled("uploadfile", true);	
				setElementEnabled("theCancelButton", true);
				GetLastTransaction()
				
				resolve()   //weet nie wat hierdie doen nie.
		    }, 120) 
	  });
}//finishProgressBar -- End	

//importCsvDialog -- End

//bankStatementDialog -- Start
//editBankStatement-- Start
function editBankStatement(rowSelector) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = allBankStatementsTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		$("#bsBankStatementId").val(data.bankStatementId);
		let element1 = document.getElementById("bsLinkTransactionsButton");        	
		element1.removeAttribute("hidden");
		
		getTransactionsLinked();

	} else {  //dis 'n nuwe een

		let element1 = document.getElementById("bsLinkTransactionsButton");        	
		element1.setAttribute("hidden", "hidden");
	}
	allBankStatementsTable.rows().deselect();
	

//	$("#bsParticipantBankDetailsId").val(data.participantBankDetailsId);
	$("#bsDescription").val(data.description);
	$("#bsStatementNumber").val(data.statementNumber);
	
	$("#bsStatementDate").val(timestampToString(data.statementDate, false));
	$("#bsTransactionDateFrom").val(timestampToString(data.transactionDateFrom, false));
	$("#bsTransactionDateTo").val(timestampToString(data.transactionDateTo, false));

		

	// Set the Save Button to disabled
	setElementEnabled("saveBankStatementButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("bankStatementDialog");
}
//editBankStatement -- End


//closeBankStatementDialog -- Start
function closeBankStatementDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
	showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("bsDlgErrorDiv", "none");
				closeModalDialog("bankStatementDialog");
			}
	);
	}
}//closeBankStatementDialog -- End


//bankStatementDialogChanged -- Start
function bankStatementDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveBankStatementButton", true);
}//bankStatementDialogChanged -- End


//linkBankTransactions() -- Start
function linkBankTransactions() {

	var postUrl = "/rest/ignite/v1/bank-statement/do-linking";
	var postData = {
			
			bankStatementId : $("#bsBankStatementId").val(),
			participantBankDetailsId : $("#bsParticipantBankDetailsId").val()

	};
	
	var errors = "";

	if ((postData.bankStatementId == null) || (postData.bankStatementId == "")) {
		errors += "A Saved Bank Statement is required<br>";
	}

	if ((postData.participantBankDetailsId == null) || (postData.participantBankDetailsId == "")) {
		errors += "Fout: Waar is die Bank Details?!<br>";
	}


	saveEntry(postUrl, postData, null, "All Transactions found in the date range, have been linked.", null, function(request, response){
		var data = response;
		$("#bsTransactionsLinked").val(data);

		// Set the Save Button to disabled
		setElementEnabled("saveGeneralTabButton", false);
		somethingChangedGeneralTab = false;
		// askToSaveTab = false;
	});
}//linkBankTransactions() -- End

//saveBankStatement -- Begin
function saveBankStatement() {
	
	var fromDate = new Date($("#bsTransactionDateFrom").val())
	var toDate = new Date($("#bsTransactionDateTo").val())
	var sDate = new Date($("#bsStatementDate").val())
	
	var postData = {	
			bankStatementId: $("#bsBankStatementId").val(),
			participantBankDetailsId: $("#bsParticipantBankDetailsId").val(),
			description: $("#bsDescription").val(),
			statementNumber: $("#bsStatementNumber").val(),
			statementDate: getMsFromDatePicker("bsStatementDate"),
			transactionDateFrom: getMsFromDatePicker("bsTransactionDateFrom"),
			transactionDateTo: getMsFromDatePicker("bsTransactionDateTo"),
			statementPDF: null  //jobjectURL 
		};

					
	var errors = "";
	
	var postUrl = "/rest/ignite/v1/bank-statement/new"
	

	if ((postData.statementNumber == "") ||  (postData.statementNumber == null)) {
		errors += "Please enter a Statement Number<br>";
	}
	
	if ((postData.statementDate =="") ||  (postData.statementDate == null))  {
		errors += "Please select an Statement Date<br>";
	}
	if ((postData.transactionDateFrom =="") ||  (postData.transactionDateFrom == null))  {
		errors += "Please select a Date for the first Transaction on this Statement<br>";
	}	
	if ((postData.transactionDateTo =="") ||  (postData.transactionDateTo == null))  {
		errors += "Please select a Date for the last Transaction on this Statement<br>";
	}
	if (postData.transactionDateTo <= postData.transactionDateFrom )  {
		errors += "The From Date should be later than the To Date<br>";
	}
	
	
	if (showFormErrors("bsDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.bankStatementId != null) && (postData.bankStatementId != "")) {  
    // This is an update 
		postUrl = "/rest/ignite/v1/bank-statement";
	}
	
	saveEntry(postUrl, postData, "bankStatementDialog", "The Statement has been saved.", allBankStatementsTable);
//	$("#bsBankStatementId").val(bankStatementId);      //var bankStatementId = 
	
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
}
//saveBankStatement -- End



function getTransactionsLinked() {

	var queryUrl="/rest/ignite/v1/bank-transaction/list-statement/" + $("#bsBankStatementId").val();

//console.log(queryUrl)	
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			 $("#bsTransactionsLinked").val(data.length);

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});	
	
} //getTransactionsLinked -- End


//loadBankStatementsDialog -- Start
function loadBankStatementsDialog(tbl) {
	
	$("#bsEndDate").val(getTodaysDate());    
	$("#bsStartDate").val(getEarlierDate(365));    
	var row = tbl.rows('.selected').data()[0];
	$("#bsParticipantBankDetailsId").val(row.participantBankDetailsId);
		
	initializeBankStatementsList(row.participantBankDetailsId);
	showModalDialog("bankStatementsDialog");
	updateBankStatementToolbarButtons();
	
}//loadBankStatementsDialog -- End


//loadLinkTransactionsDialog -- Start
function loadLinkTransactionsDialog(tbl) {
	
	$("#bsEndDate").val(getTodaysDate());    
	$("#bsStartDate").val(getEarlierDate(365));    
	var row = tbl.rows('.selected').data()[0];
	$("#bsParticipantBankDetailsId").val(row.participantBankDetailsId);
		
	initializeBankStatementsList(row.participantBankDetailsId);
	showModalDialog("LinkTransactionsDialog");
	updateBankStatementToolbarButtons();
	
}//loadLinkTransactionsDialog -- End



//Bank Statements List 1 -- Start
function initializeBankStatementsList(participantBankDetailsId) {
	
	var fromDate = new Date($("#bsStartDate").val())
	var toDate = new Date($("#bsEndDate").val())
			
	var queryUrl="/rest/ignite/v1/bank-statement/list-all/" + participantBankDetailsId + "/" + fromDate + "/" + toDate;
	
	var columnsArray = [
		{ data: "bankStatementId" },          //0
		{ data: "participantBankDetailsId" }, //1
		{ data: "description" },		      //2
		{ data: "statementNumber" },          //3
		{ data: "statementDate" },            //4
		{ data: "transactionDateFrom" },      //5
		{ data: "transactionDateTo" }         //6
//		{ data: "statementPDF" }
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1,2]  //[0, 3, 4, 6]
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			width: "25%",  //"40px",    //maw so klein as moontlik
			targets: [4,5,6]
		},

	];

	var buttonsArray = [
	            		{
	            			titleAttr: "New",
	            			text: "<i class='fas fa-plus'></i>",
	            			className: "btn btn-sm",
	            			action: function( e, dt, node, config ) {
	            				editBankStatement(null);
	            			}
	            		},
	            		{
	            			attr: {
	            				id: "promptDeleteBankStatementBtn"
	            			},
	            			titleAttr: "Delete",
	            			text: "<i class='fas fa-minus'></i>",
	            			className: "btn btn-sm",
	            			action: function() {
	            				promptDeleteBankStatement();
	            			}
	            		}
	            	];
	
	allBankStatementsTable = initializeGenericTable("allBankStatementsTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											editBankStatement(rowSelector);  //Double click
										},
										null,
										null,
										[[0,"asc"]] //Order by column 0 ascending, normally defaults to column 1 ascending
	);
	
	allBankStatementsTable.off('deselect')
	allBankStatementsTable.on('deselect', function (e, dt, type, indexes) {
		updateBankStatementToolbarButtons();
	} );

	allBankStatementsTable.off('select')
	allBankStatementsTable.on('select', function (e, dt, type, indexes) {
		updateBankStatementToolbarButtons();
	} );
	
	updateBankStatementToolbarButtons();
}//initializeBankStatementsList


//updateBankStatementToolbarButtons -- Start
function updateBankStatementToolbarButtons() {
	var hasSelected = allBankStatementsTable.rows('.selected').data().length > 0;

	setTableButtonState(allBankStatementsTable, "promptDeleteBankStatementBtn", hasSelected);	
}
//updateBankStatementToolbarButtons -- End


//promptDeleteBankStatement -- Start
function promptDeleteBankStatement() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Bank Statement?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteBankStatement(allBankStatementsTable);
			   }
	);
}
//promptDeleteBankStatement -- End


//deleteBankStatement -- Start
function deleteBankStatement(tbl) {
	
	var row = tbl.rows('.selected').data()[0];
	var postUrl = "/rest/ignite/v1/bank-statement/delete/" + row.bankStatementId;
	

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The Bank Statement has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateBankStatementToolbarButtons();
			});
}
//deleteBankStatement -- End


