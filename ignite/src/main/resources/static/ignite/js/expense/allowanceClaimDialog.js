// allowanceClaimDialog.js //

function editAllowanceClaim(rowSelector, participantIdPayer, participantPayerSystemName) {
	
	var disNuweEen;
	
	var data = {}; // Give it an empty object (so, need to add a new record)

	$("#alcParticipantIdPayer").val(participantIdPayer);
	$("#alcParticipantPayerSystemName").val(participantPayerSystemName);
	
	if (rowSelector != null) {
		disNuweEen = false;
//		data = rowSelector; 
		data = alctAllowanceClaimTable.row(rowSelector).data();
		$("#alcProjectId").val(data.projectId);
		$("#alcProjectName").val(data.projectName);
	} else {
		disNuweEen = true;
		$("#alcProjectId").val("");
		$("#alcProjectName").val("");
		$("#alcExpenseTypeName").val("");
		data.expenseTypeId = null; 
		data.expenseTypeParentId = null;
	}
	
	alctAllowanceClaimTable.rows().deselect();
	
	var selectedProjectId = data.projectId//Tells JQuery to fetch the value of the element id called npwBankId from the html page
//	if ((selectedProjectId != null) && (selectedProjectId != null)) {  
//		// var queryUrl = "/rest/ignite/v1/agreement-between-participants/distinct-recoverable-expense/" + selectedProjectId
//		var queryUrl="/rest/ignite/v1/expense-type/allowance-only"
//	
//		populateSelect("alcExpenseTypeName", //elementId, html select element that will be populated
//			       queryUrl, //url, url where it must get the data (you can paste in browser window to see the data)
//			       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
//			       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
//			       data.expenseTypeId, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
//			       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
//	  	           null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
//		);
//	
//	}
	$("#alcProjectExpenseId").val(data.projectExpenseId);
	$("#alcProjectParticipantIdPayer").val(data.projectParticipantIdPayer);


	$("#alcProjectName").val(data.projectName);

	$("#alcExpenseTypeId").val(data.expenseTypeId);
	$("#alcUnitTypeCode").val(data.unitTypeCode);
	$("#alcExpenseTypeName").val(data.expenseTypeName);
	
	$("#alcParticipantIdMadePurchase").val(data.participantIdMadePurchase);
	$("#alcParticipantMadePurchaseSystemName").val(data.participantMadePurchaseSystemName);
	
	$("#alcPaymentDescription").val(data.paymentDescription);
	
	$("#alcAllowanceDate").datepicker("setDate", data.purchaseDate == null? timestampToString(new Date(), false) : new Date(data.purchaseDate));

	if (data.unitTypeCode == null) {
		$("#alcLabelNumberOfUnits").show();
		$("#alcLabelNumberOfUnits").html("Number of Units *");
		$("#alcNumberOfUnits").show();
	} else if (data.unitTypeCode == "AMOUNT") {
		$("#alcLabelNumberOfUnits").hide();
		$("#alcNumberOfUnits").hide();
	} else if (data.unitTypeCode == "PER_DAY") {
		$("#alcLabelNumberOfUnits").show();
		$("#alcLabelNumberOfUnits").html("Number of Days *");
		$("#alcNumberOfUnits").show();
	} else if (data.unitTypeCode == "PER_HOUR") {
		$("#alcLabelNumberOfUnits").show();
		$("#alcLabelNumberOfUnits").html("Number of Hours *");
		$("#alcNumberOfUnits").show();
	} else if (data.unitTypeCode == "PER_KM") {
		$("#alcLabelNumberOfUnits").show();
		$("#alcLabelNumberOfUnits").html("Number of Kms *");
		$("#alcNumberOfUnits").show();
	} else {
		$("#alcLabelNumberOfUnits").show();
		$("#alcLabelNumberOfUnits").html("Number of Units *");
		$("#alcNumberOfUnits").show();
	}
	
	
	populateSelect("alcTaxDeductableCategoryId",
		       "/rest/ignite/v1/tax-deductable-category",
		       "taxDeductableCategoryId",
		       "name",
		       data.taxDeductableCategoryId,
		       true,
		       null
	);		
	
	
	$("#alcNumberOfUnits").val(data.numberOfUnits);

	$("#alcNoteToAccountant").val(data.noteToAccountant);
	
	// Set the Save Button to disabled
	setElementEnabled("saveAllowanceClaimButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	showModalDialog("allowanceClaimDialog");
	
	setElementEnabled("saveAllowanceClaimButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
	
}// editAllowanceClaim -- End




function selectProjectForAllowance() {
	var participantId = $("#alcParticipantIdPayer").val();

	var queryUrl="/rest/ignite/v1/project-participant/for-participant/" + participantId ;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="projectParticipantId";
			var refColumnName="projectParticipantId";
			var columns = [
				{ data: "projectParticipantId", name: "ProjectParticipantId" },				// 0
				{ data: "participantIdHost", name: "Top Level Participant Number" },		// 1
				{ data: "participantNameHost", name: "Host Level Participant" },			// 2
				{ data: "participantNameLevel1", name: "Top Level Participant" },			// 3
				{ data: "projectNumberText", name: "Project Number" },						// 4
				{ data: "projectName", name: "Project" },									// 5
				{ data: "projectId", name: "ProjectId" },									// 6
				{ data: "subProjNumber", name: "Sub Project" }								// 7 
				
			];
			var columnDefs = [
  				{ 
					visible: seeFormName,
					targets: [0,6]
				},	
//  				{ 
//					
//					render: function(data, type, row) {
//						row.addClass('light-yellow');
//		   			},
//					targets: [0,5]
//
//				},				
  				{ 
					visible: false,
					targets: [1,2,3]
				},		
  				
		   		{
		   			render: function(data, type, row) {
		   				a = leadingZeroPad(row.participantIdHost,4);
		   				return a;
		   			},
		   			targets: [1]
		   		},
		   		{
		   			render: function(data, type, row) {
		   				a = leadingZeroPad(row.projectNumberBigInt,4);
		   				return a;
		   			},
		   			targets: [3]
		   		}
		   	];

				selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				
				$("#alcProjectParticipantId").val(row.projectParticipantId);

				var projectName = row.projectName;
				$("#alcProjectName").val(projectName);
				$("#alcProjectId").val(row.projectId);
				$("#alcProjectParticipantIdPayer").val(row.projectParticipantId);
				
				// selectRecoverableExpensesForThisProject();
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


function editSelectExpenseTypeForAllowance() {
	selectExpenseTypeForAllowance("alcExpenseTypeId", "alcExpenseTypeName", "alcUnitTypeCode", "alcAllowAsset");
	alcDialogChanged();
}


function selectExpenseTypeForAllowance(targetId, targetName, targetUtId, targetAllowAsset) {
	
	// var queryUrl="/rest/ignite/v1/expense-type";
	var queryUrl="/rest/ignite/v1/expense-type/allowance-only"
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="expenseTypeId";
			var refColumnName="expenseTypeId";
			var columns = [
				{ data: "expenseTypeId", name: "expenseTypeId" },				// 0
				{ data: "expenseTypeParentId", name: "ParentCode" },			// 1
				{ data: "unitTypeCode", name: "Unit Type Code" },				// 2
				{ data: "expenseTypeId", name: "Expense Type Id" },			// 3
				{ data: "name", name: "Expense Type" },							// 4
				{ data: "description", name: "Description" },					// 5
				{ data: "allowanceFlag", name: "Allowance" },					// 6
				{ data: "allowHandlingPerc", name: "Allow Handling Perc" },		// 7
				{ data: "allowMaxAmtPerUnit", name: "Allow MaxAmt PerUnit" },	// 8
				{ data: "allowAsset", name: "Allow Asset" }						// 9
			];
			
			var columnDefs = [
				{ 
					visible: false,
					targets: [0,1,2,3,5,6,7,8,9]
					},

         		    {
             			render: function (data, type, row) {
             				if (row.allowanceFlag == "Y") {
             					return "Allowance";
             				} else {
             					return "";
             				}
             			},
             			className: "dt-center",
             			targets: 6
             		},             		{
             			render: function (data, type, row) {
             				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowHandlingPerc == "Y" ? " checked " : "") + ">";
             			},
             			className: "dt-center",
             			targets: 7
             		},		
             		{
             			render: function (data, type, row) {
             				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowMaxAmtPerUnit == "Y" ? " checked " : "") + ">";
             			},
             			className: "dt-center",
             			targets: 8
             		},		
             		{
             			render: function (data, type, row) {
             				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowAsset == "Y" ? " checked " : "") + ">";
             			},
             			className: "dt-center",
             			targets: 9
             		}
			];

			
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.expenseTypeId;
				var repName = row.name + ", " + row.description;
				var utCode = row.unitTypeCode;
				var allowAsset = row.allowAsset;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#" + targetUtId).val(utId);
				$("#" + targetAllowAsset).val(allowAsset);
				

				if (utCode == "AMOUNT") {
					$("#alcLabelNumberOfUnits").hide();
					$("#alcNumberOfUnits").hide();
					$("#alcNumberOfUnits").val("1");
				} else if (utCode == "PER_DAY") {
					$("#alcLabelNumberOfUnits").show();
					$("#alcLabelNumberOfUnits").html("Number of Days *");
					$("#alcNumberOfUnits").show();
				} else if (utCode == "PER_HOUR") {
					$("#alcLabelNumberOfUnits").show();
					$("#alcLabelNumberOfUnits").html("Number of Hours *");
					$("#alcNumberOfUnits").show();
				} else if (utCode == "PER_KM") {
					$("#alcLabelNumberOfUnits").show();
					$("#alcLabelNumberOfUnits").html("Number of Kms *");
					$("#alcNumberOfUnits").show();
				} else {
					$("#alcLabelNumberOfUnits").show();
					$("#alcLabelNumberOfUnits").html("Number of Units *R");
					$("#alcNumberOfUnits").show();
				}				

			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectExpenseType































function selectRecoverableExpensesForThisProject() {
	
	var selectedProjectId = $("#ipeProjectId").val(); //Tells JQuery to fetch the value of the element id called npwBankId from the html page
	var queryUrl = "/rest/ignite/v1/agreement-between-participants/distinct-recoverable-expense/" + selectedProjectId
	$("#ipeExpenseTypeId").val("");
	$("#ipeUnitTypeName").val("");
	$("#ipeUnitTypeCode").val("");

	populateSelect("ipeRecoverableExpenseAgrProject", //elementId, html select element that will be populated
		       queryUrl, //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "expenseTypeName", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
  	           null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	
}

function populateExpenseTypeChildren() {
	var selectedExpenseTypeParent = $("#ipeExpenseTypeParent").val(); //Tells JQuery to fetch the value of the element id called npwBankId from the html page
	$("#ipeExpenseTypeId").val("");
	$("#ipeUnitTypeName").val("");
	$("#ipeUnitTypeCode").val("");
	
	populateSelect("ipeExpenseType", //elementId, html select element that will be populated
		       "/rest/ignite/v1/expense-type/by-parent/" + selectedExpenseTypeParent, //url, url where it must get the data (you can paste in browser window to see the data)
		       "expenseTypeId", //idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
		       "name", //displayField,  name of the field in the result from url - java object field - variable, that	must be displayed to the user
		       null, //selectedId, variable of the value that must be selected (null or default when new record)  or current value
		       true, //addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
  	           null//completeMethod javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
	);
	
}

function populateExpenseUnitTypeCode() {
	// Haal een rekord van die db

	// Find Unit Type for the selectedExpenseTypeId
	var queryUrl;
	// var selectedExpenseTypeId = $("#ipeExpenseType").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  
	var selectedExpenseTypeId = $("#ipeRecoverableExpenseAgrProject").val(); //Tells JQuery to fetch the value of the element id called niwBankId from the html page  
	queryUrl = "/rest/ignite/v1/expense-type/unit/" + selectedExpenseTypeId;
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#ipeUnitTypeCode").val(data.unitTypeCode);
			$("#ipeUnitTypeName").val(data.name);
			if (data.unitTypeCode == "AMOUNT") {
				setDivVisibility("ipeAmountPerUnitPanel", "block" );
			} else {  	
				setDivVisibility("ipeAmountPerUnitPanel", "none" );
				$("#ipeAmountPerUnit").val("");
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










//saveAllowanceClaim -- Begin
function saveAllowanceClaim() {
	
	var postUrl = "/rest/ignite/v1/project-expense/new";
	var postData = {
			 projectExpenseId				: $("#alcProjectExpenseId").val(),
			 projectParticipantIdPayer      : $("#alcProjectParticipantIdPayer").val(),
			 participantIdMadePurchase      : $("#alcParticipantIdMadePurchase").val(),
			 participantIdVendor            : null,
			 expenseTypeId                  : $("#alcExpenseTypeId").val(),
			 assetId                        : null,
			 paymentMethodCode              : null,
			 bankCardIdUsed                 : null,
			 participantBankDetailsIdUsed   : null,
			 taxDeductableCategoryId      : $("#alcTaxDeductableCategoryId").val(),
			 paymentDescription             : $("#alcPaymentDescription").val(),
			 purchaseDate                   : getMsFromDatePicker("alcAllowanceDate"),
			 numberOfUnits                  : $("#alcNumberOfUnits").val(),
			 amountPerUnit                  : null,

			 noteToAccountant               : $("#alcNoteToAccountant").val(),
			 fullyLinked                    : null,
			 bankReference                  : null
	};

	var errors = "";


	// validate
	if ((postData.projectParticipantIdPayer == null) || (postData.projectParticipantIdPayer == "")) {
		errors += "Select a payer for the purchase.<br>";
	}
	if ((postData.participantIdMadePurchase == null) || (postData.participantIdMadePurchase == "")) {
		errors += "Select a person that made the purchase.<br>";
	}	
	if ((postData.expenseTypeId == null) || (postData.expenseTypeId == "")) {
		errors += "An Allowance Type is required.<br>";
	}
	if ((postData.purchaseDate == null) || (postData.purchaseDate == "")) {
		errors += "A Purchase Date is required.<br>";
	}
	if ((postData.numberOfUnits == null) || (postData.numberOfUnits == "")) {
		errors += "The Number of Units is required. saveAllowanceClaim <br>";
	}	
	if ((postData.paymentDescription == null) || (postData.paymentDescription == "")) {
		errors += "A Payment Description is required.<br>";
	}	
	
	if (showFormErrors("alcDlgErrorDiv", errors)) {
		return;
	};

	if (postData.taxDeductableCategoryId == "") { 
		postData.taxDeductableCategoryId = null;
	}
	
	if ((postData.projectExpenseId != null) && (postData.projectExpenseId != "")) {   //update
		var postUrl = "/rest/ignite/v1/project-expense";
		
	}
	
	saveEntry(postUrl, postData, "allowanceClaimDialog", "The Project Allowance Claim has been saved.", alctAllowanceClaimTable, function(request, response) {
		
		alctAllowanceClaimSummaryTable.ajax.reload();
//		alctAllowanceClaimTable.ajax.reload();
		setDivVisibility("alcDlgErrorDiv", "none");

	});

//	
//	askToSaveDialog = false;
//	somethingChangedInDialog = false;
//	setElementEnabled("saveAllowanceClaimButton", false);

}// saveAllowanceClaim-- End



//closeAllowanceClaimDialog -- Start
function closeAllowanceClaimDialog() {
	if ((askToSaveDialog == true) || (somethingChangedInDialog == true)) {
		showDialog("Confirm?",
				"You have unsaved changes - are you sure you wish to cancel?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					setDivVisibility("alcDlgErrorDiv", "none");
					closeModalDialog("allowanceClaimDialog");
				}
		)
	} else {
		setDivVisibility("alcDlgErrorDiv", "none");
		closeModalDialog("allowanceClaimDialog");
	}
} //closeAllowanceClaimDialog -- End


function editSelectParticipantMadePurchaseForAllowance() {
	selectParticipantIndividual("alcParticipantIdMadePurchase", "alcParticipantMadePurchaseSystemName");
	alcDialogChanged();
}


//alcDialogChanged -- Start
function alcDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveAllowanceClaimButton", true);
}
//alcDialogChanged -- End

