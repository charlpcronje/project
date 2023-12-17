var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;
var fecRelationshipTable = null;


//*********************************************************
//CostPerAgreement Tab -- Start
//*********************************************************

function getExpenseClaimForDateRange() {
	
	var participantId = $("#finParticipantId").val();
    var dateFromFin = getMsFromDatePicker("fecStartDate");
    var dateToFin= getMsFromDatePicker("fecEndDate");
    initializeFinExpenseClaimTab(participantId, dateFromFin, dateToFin);
    
}

//*********************************************************
//	Expense Claims - Table 1 - Relationships -- Start
//*********************************************************
function initializeFinExpenseClaimTab(participantId, dateFrom, dateTo) {

	showEmptyFecRecoverableExpensePanel();	
	
	
	var columnsArray = [
			{ data: "participantIdContracting" },			// 0 
			{ data: "participantInAgreementContracting" },	// 1
			{ data: "participantIdContracted" },			// 2
			{ data: "participantInAgreementContracted"},	// 3
			{ data: "sumNrOfUnits"},						// 4
			{ data: "lineAmount"},							// 5
			{ data: "ratesMissing"}							// 6
			
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
	               			width: "20%",
	               			targets: [4]
	               		},
	               		{
	               			width: "35%",
	               			targets: [5]
	               		},
	            		{
	            			render: function(data, type, row) {
	            				data = row.lineAmount;
	            				if (type == "display") {
	             					data = valueToRand(data);
	            				}
	            				return data;
	            			},
	            			className:"dt-right",
	            			targets: 5
	            		},
	            		{
	            			render: function(data, type, row) {
	            				data = "";
	            				if (type == "display") {
	            					rm = row.ratesMissing;
	            					if (rm > 0 ) { // There are one or more rates missing
	            						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
	            					}
	            				}
	            				return data;
	            			},
	            			className:"dt-right",
	            			targets: 6
	            		}
	               		
	];
	               		
	var buttonsArray = [
		
	];

	var queryUrl="/rest/ignite/v1/financials/relationships-unique-expense-claims/" + participantId + "/" + dateFrom + "/" + dateTo;
	console.log(queryUrl);
	
	fecRelationshipTable = initializeGenericTable("fecRelationshipTable",
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
	
		fecRelationshipTable.off('deselect')
		fecRelationshipTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyFecRecoverableExpensePanel();
	} );
	
		fecRelationshipTable.off('select')
		fecRelationshipTable.on('select', function (e, dt, type, indexes) {
		initializeFecRecoverableExpensePanel(dt.data(), dateFrom, dateTo);
	} );
	
}

function showEmptyFecRecoverableExpensePanel() {
	setDivVisibility("emptyFecRecoverableExpensePanel", "block");
	setDivVisibility("fecRecoverableExpensePanel", "none");
	showEmptyFecExpenseDetailPanel();
	$("#fecAllowanceFlag").val("")
}


//*********************************************************
// Expense Claims - Table 2 - Recoverable Expenses summary -- Start
//*********************************************************


function initializeFecRecoverableExpensePanel(relationshipRow, dateFrom, dateTo) {

	if (relationshipRow == null) {
		return;
	}
	
	var participantIdContracting = relationshipRow.participantIdContracting;
	var participantIdContracted = relationshipRow.participantIdContracted;
	
	setDivVisibility("emptyFecRecoverableExpensePanel", "none");
	setDivVisibility("fecRecoverableExpensePanel", "block");
	showEmptyFecExpenseDetailPanel();

	var columnsArray = [
	                    
		{ data: "projectId" },							// 0
		{ data: "projectName" },						// 1 --
		{ data: "expenseTypeName"},						// 2 
		{ data: "unitTypeName" },						// 3 --
		{ data: "participantIdContracting" },			// 4
		{ data: "participantInAgreementContracting"},	// 5 
		{ data: "participantIdContracted" },			// 6
		{ data: "participantInAgreementContracted" },	// 7 
		{ data: "sumNrOfUnits"},						// 8 --
		{ data: "lineAmount"},							// 9 --
		{ data: "expenseTypeId"},						// 10 
		{ data: "recoverableExpenseId"},				// 11 
		{ data: "ratesMissing"}							// 12 
		]
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0,3,4,5,6,7,10,11]
		},
		{
			width: "50%",
			targets: [1]
		},
		{
			width: "30%",
			targets: [2]
		},
		{
			width: "10%",
			targets: [8,9]
		},
		{
			render: function(data, type, row) {
				data = row.lineAmount;
				if (type == "display") {
 					data = valueToRand(data);
				}
				return data;
			},
			className:"dt-right",
			targets: 9
		},
		{
			render: function(data, type, row) {
				data = "";
				if (type == "display") {
					rm = row.ratesMissing;
					if (rm > 0 ) { // There are one or more rates missing
						data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>'; 
					}
				}
				return data;
			},
			className:"dt-right",
			targets: 12
		}
		];

	var buttonsArray = [		
//	 {	
//		attr: {
//			id: "newPurchaseBtn"
//		},
//		titleAttr: "New",
//		text: "<i class='fas fa-plus'></i>",
//		className: "btn btn-sm",
//		action: function(e, dt, node, config) {
//			editPurchase(null);
//		}
//	},
//	{
//		attr: {
//			id: "deletePurchaseBtn"
//		},
//		titleAttr: "Delete",
//		text: "<i class='fas fa-minus'></i>",
//		className: "btn btn-sm",
//		action: function(e, dt, node, config) {
//			promptDeletePurchase();
//		}
//	}
	];

	var queryUrl="/rest/ignite/v1/financials/payer-ben-expense-cost/" + participantIdContracting + "/" + participantIdContracted + "/" + dateFrom + "/" + dateTo;
	console.log("table3");
	console.log(queryUrl);
	
	fecRecoverableExpenseTable = initializeGenericTable("fecRecoverableExpenseTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function() {
										}
	);

	fecRecoverableExpenseTable.off('deselect')
	fecRecoverableExpenseTable.on('deselect', function (e, dt, type, indexes) {
	showEmptyFecExpenseDetailPanel();
} );

	fecRecoverableExpenseTable.off('select')
	fecRecoverableExpenseTable.on('select', function (e, dt, type, indexes) {
		initializeFecExpenseDetailTablePanel(dt.data());
} );
	
}

function showEmptyFecExpenseDetailPanel() {
	setDivVisibility("emptyFecExpenseDetailPanel", "block");
	setDivVisibility("fecExpenseDetailPanel", "none");
}


//*********************************************************
// Expense Claims - Table 3 - Recoverable Expenses details -- Start
//*********************************************************

function initializeFecExpenseDetailTablePanel(agreementExpenseCostRow) {
	if (agreementExpenseCostRow == null) {
		return;
	}
    var dagVan = getMsFromDatePicker("fecStartDate");
    var dagTot = getMsFromDatePicker("fecEndDate");
	var expenseTypeId = agreementExpenseCostRow.expenseTypeId;
	
	getAllowanceFlag(expenseTypeId);	
	
	var participantIdContracting = agreementExpenseCostRow.participantIdContracting;
	var participantIdContracted = agreementExpenseCostRow.participantIdContracted;
	console.log(expenseTypeId, participantIdContracting, participantIdContracted)
	
//	{ data: "projectId" },							// 0
//	{ data: "projectName" },						// 1 --
//	{ data: "expenseTypeName"},						// 2 
//	{ data: "unitTypeName" },						// 3 --
//	{ data: "participantIdContracting" },			// 4
//	{ data: "participantInAgreementContracting"},	// 5 
//	{ data: "participantIdContracted" },			// 6
//	{ data: "participantInAgreementContracted" },	// 7 
//	{ data: "sumNrOfUnits"},						// 8 --
//	{ data: "lineAmount"},							// 9 --
//	{ data: "expenseTypeId"},						// 10 
//	{ data: "recoverableExpenseId"}					// 11 

	setDivVisibility("emptyFecExpenseDetailPanel", "none");
	setDivVisibility("fecExpenseDetailPanel", "block");

	var columnsArray = [
		    { data: "rowNumber" },							// 0
			{ data: "expenseHandlingPerc" },				// 1
			{ data: "maxExpenseAmtPerUnit" },				// 2
			{ data: "paymentDescription"},					// 3
			{ data: "purchaseDate" },						// 4 --
			{ data: "participantMadePurchaseSystemName" },	// 5 --
			{ data: "numberOfUnits" },						// 6 --
			{ data: "amountPerUnit"},						// 7
			{ data: "expenseRateForDate"},					// 8 --
			{ data: "lineTotal"},							// 9 --
			{ data: "lineTotal"}							// 10
			]
	
	var columnDefsArray = [
	{
		visible: false,
		targets: [0,1,2,3,7]
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
		targets: [4]
	},
	{
		render: function(data, type, row) {
			r = row.expenseRateForDate;
			if (r > 0) {
				r = valueToRand(r);
			} else {
				r = '&nbsp;';
			}
			return r;
		},
		className:"dt-right",
		targets: [8]
	},
	{
		render: function(data, type, row) {
			data = row.lineAmount;
			if (type == "display") {
					data = valueToRand(data);
			}
			return data;
		},
		className:"dt-right",
		targets: 9
	},
	{
		render: function(data, type, row) {
			data = "";
			l = row.lineTotal;
			r = row.expenseRateForDate;
			console.log(l,r);
			if (l < 0) {
				if (r == -4) {
					data = '<span class="badge badge-pill badge-info" title="No Agreement Setup" >A</span>'; 
				} else {  // This should only be -2 for rates missing in agreement
					data = '<span class="badge badge-pill badge-warning" title="Rates missing" >!</span>';	
				}
			} 
			return data;
		},
		className:"dt-right",
		targets: [10]
	}
	];

	var buttonsArray = [];

	queryUrl = "/rest/ignite/v1/financials/contracting-contracted-expenses/" + expenseTypeId + "/" + dagVan + "/" + dagTot + "/" + participantIdContracting + "/" + participantIdContracted;
	
	console.log(queryUrl);
	
	fecExpenseDetailTable = initializeGenericTable("fecExpenseDetailTable",
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(rowSelector) {
											showPurchaseDetails(rowSelector);
										}
	);
	
}



//--getAllowanceFlag-- Start
function getAllowanceFlag(expenseTypeId) {

	queryUrl = "/rest/ignite/v1/expense-type/" + expenseTypeId;		

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {	
	
			$("#fecAllowanceFlag").val(data.allowanceFlag);        						// 	0

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});			

}
//getAllowanceFlag -- End





function showPurchaseDetails(rowSelector) {

	var data = {}; // Give it an empty object (so, need to add a new record)
	var isPayment

	data = fecExpenseDetailTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

	fecExpenseDetailTable.rows().deselect();
	
	if ($("#fecAllowanceFlag").val() == "N") {
		isPayment = true;
		document.getElementById("lblPayment").style.display = 'block';
		document.getElementById("lblPdate").style.display = 'block';
		document.getElementById("lblAllow").style.display = 'none';
		document.getElementById("lblAdate").style.display = 'none';
		document.getElementById("lblAmountPerUnit").style.display = 'block';
		document.getElementById("lblCheckboxie").style.display = 'block';
		document.getElementById("finDetailBankReferenceLabel").style.display = 'block';
		$("#finDetailAmountPerUnit").attr("hidden", false);
		$("#finDetailFullyLinked").attr("hidden", false);
//		$("#finDetailBankReferenceLabel").attr("hidden", false);
		$("#finDetailBankReference").attr("hidden", false);
	} else if ($("#fecAllowanceFlag").val() == "Y") {
		isPayment = false;
		document.getElementById("lblPayment").style.display = 'none';
		document.getElementById("lblPdate").style.display = 'none';
		document.getElementById("lblAllow").style.display = 'block';
		document.getElementById("lblAdate").style.display = 'block';
		document.getElementById("lblAmountPerUnit").style.display = 'none';
		document.getElementById("lblCheckboxie").style.display = 'none';
		document.getElementById("finDetailBankReferenceLabel").style.display = 'none';
		$("#finDetailAmountPerUnit").attr("hidden", true);
		$("#finDetailFullyLinked").attr("hidden", true);
//		$("#finDetailBankReferenceLabel").attr("hidden", true);
		$("#finDetailBankReference").attr("hidden", true);
	} else {
		console.log("Nie een van die twee nie.....");
		document.getElementById("lblAllow").style.display = 'none';
		document.getElementById("lblAdate").style.display = 'none';
	}
		

	$("#finDetailRowNumber").val(data.rowNumber);  
	$("#finDetailProjectExpenseId").val(data.projectExpenseId);             //1
	$("#finDetailParticipantMadeOrigPayment").val(data.participantMadeOrigPayment); //4
	$("#finDetailProjectParticipantIdMadeOrigPayment").val(data.projectParticipantIdMadeOrigPayment); //2
	$("#finDetailProjectName").val(data.projectName);                       //23
	$("#finDetailParticipantIdMadeOrigPayment").val(data.participantIdMadeOrigPayment); //3
	$("#finDetailExpenseTypeName").val(data.expenseTypeName);               //7
	$("#finDetailExpenseTypeId").val(data.expenseTypeId);                   //12
	$("#finDetailUnitTypeCode").val(data.unitTypeCode);                     //24
	$("#finDetailParticipantMadePurchaseSystemName").val(data.participantMadePurchaseSystemName); //18
	$("#finDetailParticipantIdMadePurchase").val(data.participantIdMadePurchase); //17
	$("#finDetailParticipantIdVendor").val(data.participantIdVendor);       //33
	if ((data.participantIdVendor == "") || (data.participantIdVendor == null)) {
		$("#vendorRow").attr("hidden", true)
	} else {
		$("#vendorRow").attr("hidden", false)
	}
//    $("#vendorRow").attr("hidden", (data.participantIdVendor == "" : false ? true));
	
	
	$("#finDetailParticipantVendorSystemName").val(data.participantVendorSystemName); //34

	$("#finDetailAssetId").val(data.assetId);                               //35
	if ((data.assetId == "") || (data.assetId == null)){
		$("#assetRow").attr("hidden", true)
	} else {
		$("#assetRow").attr("hidden", false)
	}	
	
	$("#finDetailVehicleId").val(data.vehicleId);                           //36
	if ((data.vehicleId == "") || (data.vehicleId == null)) {
		$("#vehicleRow").attr("hidden", true)
	} else {
		$("#vehicleRow").attr("hidden", false)
	}	
	
	$("#finDetailVehicleName").val(data.vehicleName);                       //37
	$("#finDetailAssetDescription").val(data.assetDescription);             //38
	
	$("#finDetailPaymentMethodName").val(data.paymentMethodName);           //40
	if ((data.paymentMethodName == "") || (data.paymentMethodName == null)) {
		$("#paymentMethodRow").attr("hidden", true)
	} else {
		$("#paymentMethodRow").attr("hidden", false)
	}		
	
	
//		$("#finDetailPaymentMethodCode").val(data.paymentMethodCode);           //39

	$("#finDetailBankCard").val(data.bankCardNumber + " " + data.bankCardNameOnCard + " " + data.bankCardDescription);   
	$("#finDetailBankAccount").val(data.accountNumber + " " + data.participantBankDetailsName + " " + data.participantBankDetailsDescription); 
	
	$("#finDetailBankCardIdUsed").val(data.bankCardIdUsed);                 //41
	if ((data.bankCardIdUsed == "") || (data.bankCardIdUsed == null)) {
		$("#bankCardGroup").attr("hidden", true)
	} else {
		$("#bankCardGroup").attr("hidden", false)
	}
	
	$("#finDetailParticipantBankDetailsIdUsed").val(data.participantBankDetailsIdUsed); //45	
	if ((data.participantBankDetailsIdUsed == "") || (data.participantBankDetailsIdUsed == null)) {
		$("#bankAccountGroup").attr("hidden", true)
	} else {
		$("#bankAccountGroup").attr("hidden", false)
	}	
	
	
	$("#finDetailTaxDeductableCategoryId").val(data.taxDeductableCategoryId); //49
	$("#finDetailTaxDeductableCategoryName").val(data.taxDeductableCategoryName); //50
	$("#finDetailPaymentDescription").val(data.paymentDescription);         //13
	$("#finDetailPurchaseDate").datepicker("setDate", data.purchaseDate == null? null : new Date(data.purchaseDate));                     //14
	$("#finDetailNumberOfUnits").val(data.numberOfUnits);                   //15
	
	
	$("#finDetailAmountPerUnit").val((data.amountPerUnit != null) ? "R " + (data.amountPerUnit).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke                       //9	
		
		

	if (data.unitTypeCode == null) {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Units");
		$("#finDetailNumberOfUnits").show();
		$("#lblAmountPerUnit").html("Amount per Unit");
	} else if (data.unitTypeCode == "AMOUNT") {
		$("#labelNumberOfUnits").hide();
		$("#finDetailNumberOfUnits").hide();
		$("#lblAmountPerUnit").html("Amount");
	} else if (data.unitTypeCode == "PER_DAY") {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Days");
		$("#finDetailNumberOfUnits").show();
		$("#lblAmountPerUnit").html("Amount per Day");
	} else if (data.unitTypeCode == "PER_HOUR") {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Hours");
		$("#finDetailNumberOfUnits").show();
		$("#lblAmountPerUnit").html("Amount per Hour");
	} else if (data.unitTypeCode == "PER_KM") {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Kms");
		$("#finDetailNumberOfUnits").show();
		$("#lblAmountPerUnit").html("Amount per Km");
	} else {
		$("#labelNumberOfUnits").show();
		$("#labelNumberOfUnits").html("Number of Units");
		$("#finDetailNumberOfUnits").show();
		$("#lblAmountPerUnit").html("Amount per Unit")
	}	
	
	
	
	
	
//	hier.....
	

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
	
	showModalDialog("showFinDetailDialog");

	

	somethingChangedInDialog = false;
	askToSaveDialog = false;

} //showPurchaseDetails -- End



function closeShowFinDetailDialog() {
	closeModalDialog("showFinDetailDialog");
} //closeShowFinDetailDialog -- End

