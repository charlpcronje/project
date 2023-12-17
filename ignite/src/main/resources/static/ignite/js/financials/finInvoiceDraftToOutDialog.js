var somethingChangedInDialog = false;
var invoiceId = null;
var enabled = false;

//*********************************************************
//editInvoiceOut -- Start
function editInvoiceOut(rowSelector, tbl) {

	var errors = "";
	var data = {}; // Give it an empty object (so, need to add a new record)
	
	var participantIdFrom = $("#finParticipantId").val();
	
//	console.log(participantIdFrom);
	$("#ioParticipantIdFrom").val(participantIdFrom);
	$("#ioParticipantIdFrom_Name").val($("#epSystemName").val());
//	console.log(data);

	if (rowSelector != null) {
		data = tbl.row(rowSelector).data();
//		console.log(data);
		enabled = true;
		invoiceId = data.invoiceId;
		
		$("#ioInvoiceId").val(data.invoiceId); 
		$("#ioParticipantIdTo").val(data.participantIdTo);
		$("#ioFlagDraft").prop("checked", data.flagDraft == "Y");  
		$("#ioUpUntilGenerateDate").datepicker("setDate", data.upUntilGenerateDate == null? timestampToString(new Date(), false) : new Date(data.upUntilGenerateDate));
		$("#ioInvoiceDate").datepicker("setDate", data.invoiceDate == null? timestampToString(new Date(), false) : new Date(data.invoiceDate));
		$("#ioDateSentOrReceived").datepicker("setDate", data.dateSentOrReceived == null? timestampToString(new Date(), false)  : new Date(data.dateSentOrReceived));
		$("#ioInvoiceNumber").val(data.invoiceNumber); 
		$("#ioParticipantIdTo_Name").val(data.toParticipantName); 
		$("#ioDescription").val(data.description); 
		$("#ioToAttention").val(data.toAttention); 
		$("#ioInvoiceAmount").val(valueToRand(data.invoiceTotalAmountInclTax).toString()); 
	
	} else {
		$("#ioInvoiceId").val(""); 
		$("#ioParticipantIdTo").val(""); 
		$("#ioFlagDraft").prop("checked", true);  
		$("#ioUpUntilGenerateDate").datepicker("");
		$("#ioInvoiceDate").datepicker("setDate", timestampToString(new Date(), false));
		$("#ioDateSentOrReceived").datepicker("");
		$("#ioInvoiceNumber").val(""); 
		$("#ioSystemNameTo").val(""); 
		$("#ioDescription").val(""); 
		$("#ioInvoiceAmount").val(""); 
		$("#ioToAttention").val(""); 
	}
	
	console.log(data);
	
	if (data.invoiceNumber == '0') {
		var newInvoiceNumber = "" ;
		newInvoiceNumber = getNewInvoiceNumber(participantIdFrom);
		$("#ioInvoiceNumber").val(newInvoiceNumber); 
	}
	
	tbl.rows().deselect();
	
	// setElementEnabled("saveInvoiceOutButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("finInvoiceDraftToOutDialog");
} // editInvoiceOut -- END



//getNewInvoiceNumber -- Begin
function getNewInvoiceNumber(participantId) {
	
	var url = "/rest/ignite/v1/participant/get-next-invoice-number/" + participantId;
	console.log(url);

	$.ajax({
		url: springUrl(url),
		type: "GET",
		success: function(data) {
			// load the data
			data = JSON.parse(data);
			console.dir(data);
			$("#ioInvoiceNumber").val(data.pNextInvoiceNumber);
			$("#ioTheFormat").val(data.vInvoiceNumberFormat);
			
			if ((data.vInvoicePrefix != null) && (data.vInvoicePrefix != "")) {
				$("#ioThePrefix").val(data.vInvoicePrefix + "-");
			}

			if ((data.vYearMonthPart != null) && (data.vYearMonthPart != "")) {
				$("#ioYearMonth").val(data.vYearMonthPart + "-");
			}
			
			$("#ioLastNumber").val(data.vLatestInvoiceNumber);
			$("#ioDigitLength").val(data.vDigitLength);
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});	
	
}  //getNewInvoiceNumber -- End


function overrideInvoiceNumber() {
	 
	  var x = document.getElementById("ioTheNumber");
	  if (x.style.display === 'none') {
		  x.style.display = "block";
	  } else {
		  x.style.display = "none";
		  $("#ioTheNumber").val("");
	  }	  
	  
}



function setLastInvoiceNumber() {
//	console.log("setLastInvoiceNumber");
	var participantIdFrom = $("#ioParticipantIdFrom").val();
	if (($("#ioTheNumber").val() == null) || ($("#ioTheNumber").val() == "")) {
		var newNumber =  parseInt($("#ioLastNumber").val()) + 1;
	} else {
		var newNumber =  $("#ioTheNumber").val();
	}

	var postUrl = "/rest/ignite/v1/participant/set-last-invoice-number/" + participantIdFrom + "/" + newNumber;
//	console.log("In finInvoiceDraftToOutDialog : setLastInvoiceNumber  postUrl" + postUrl);

	// Disable delete button when record has been deleted.
	saveEntry(postUrl, "", null, "", null,
			function(){
//				console.log("completed Nommer is geupdate");
			}
	);
}


//ioDialogChanged -- Start
function ioDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveInvoiceOutButton", true);
}
//ioDialogChanged -- End

//closeInvoiceOutDialog -- Start
function closeInvoiceOutDialog() {
	if (somethingChangedInDialog == true) {
		showDialog("Confirm?",
				"You have unsaved changes - are you sure you wish to cancel?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					setDivVisibility("ioDraftInvoiceDlgErrorDiv", "none");
					closeModalDialog("finInvoiceDraftToOutDialog");
					$("#ioTheNumber").val("");
					$("#ioDescription").val("");
					var x = document.getElementById("ioTheNumber");
				    x.style.display = "none";		
				}
		)
	} else {
		setDivVisibility("ioDraftInvoiceDlgErrorDiv", "none");
		closeModalDialog("finInvoiceDraftToOutDialog");
		$("#ioTheNumber").val("");
		$("#ioDescription").val("");
		var y = document.getElementById("ioTheNumber");
	    y.style.display = "none";		
	}
} //closeInvoiceOutDialog -- End


function ioFileSelected() {
	
	const fileInput = document.getElementById('ioInvoiceImage');
	fileInput.onchange = () => {
	  const selectedFile = fileInput.files[0];
	  console.log(selectedFile);
	}
}



//function createInvoiceOutReport(invoiceId) {
//
//	var url = "/rest/ignite/v1/invoice/create-invoice-report/" + invoiceId;
//	console.log(url);
//
//	window.open(springUrl(url));

	////	if (rowSelector != null) {
////		data = rowSelector;
////		invoiceId = data.invoiceId;
////	} else {
////		return;
////	}
//	
//	console.log("Show my Report!");
//	console.log("invoiceId = ", invoiceId);
//	
//	var url = "/rest/ignite/v1/invoice/report?id=" + invoiceId;
//	console.log(url);
//
//	window.open(springUrl(url));
////
////	
//////	var queryUrl="/rest/ignite/v1/invoice/create-invoice-report/" + invoiceId + "/" + invoiceNumber + "/" + invoiceDate;
////	var queryUrl="/rest/ignite/v1/invoice/create-invoice-report/" + invoiceId;
////	console.log (queryUrl);
////	
////	$.ajax({
////		url: springUrl(queryUrl),
////		type: "GET",
////		success: function(data) {
////			console.log(data);
////			previewInvoiceFile(invoiceId);
////		},
////		error: function(jqXHR, textStatus, errorThrown) {
////			ajaxError(jqXHR, textStatus, errorThrown);
////		},
////		complete: function(html) {
////			ajaxSuccess(html.status);
////		}  
////	});
//}


//saveGeneratedInvoiceOut -- Begin
function saveGeneratedInvoiceOut() {
	
	var postUrl = "/rest/ignite/v1/invoice/save-invoice-out";
	var postData = {
		invoiceId : $("#ioInvoiceId").val()                //0 MySql-TableName: Invoice
		,invoiceDate : getMsFromDatePicker("ioInvoiceDate")                         
		,invoiceNumber : $("#ioTheNumber").val()                                	
		,description : $("#ioDescription").val()                                    
		,toAttention : $("#ioToAttention").val()                                    
		,dateSentOrReceived : getMsFromDatePicker("ioDateSentOrReceived")  

	};
	
	var errors = "";
	
	// validate
	
	if ((postData.invoiceNumber == null) || (postData.invoiceNumber == "")) {
		postData.invoiceNumber = $("#ioInvoiceNumber").val()   //use the generated one
	} else {
		var trimmedNr = $("#ioTheNumber").val().trim();
		$("#ioTheNumber").val(trimmedNr);
		
		if ($("#ioTheNumber").val().length > $("#ioDigitLength").val()) {
			errors += "Your number is too long for the format chosen.<br>";
		}
		
		if ((postData.invoiceNumber.includes(".")) || (postData.invoiceNumber.includes(","))) {
			errors += "Your number needs to be an integer.<br>";
		}
		
		if (isNaN(postData.invoiceNumber)) {
			errors += "You can only use a numeric value.<br>";
		} else if (parseInt(postData.invoiceNumber) <= parseInt($("#ioLastNumber").val())) {
			errors += "Your number needs to be larger than " + $("#ioLastNumber").val() + "<br>";
		}
		postData.invoiceNumber = $("#ioThePrefix").val() + $("#ioYearMonth").val() + $("#ioTheNumber").val().toString().padStart($("#ioDigitLength").val(), '0')
	}
	
	if (showFormErrors("ioDraftInvoiceDlgErrorDiv", errors)) {
		return;
	};
	
	console.log(postUrl);
	console.log(postData);
	// Third 
	saveEntry(postUrl, postData, "finInvoiceDraftToOutDialog", "The Invoice Out has been saved.", draftTable1, function(request, response) {
		
		setDivVisibility("ioDraftInvoiceDlgErrorDiv", "none");
		
		setLastInvoiceNumber();
		
		$("#ioTheNumber").val("");
		$("#ioDescription").val("");
		var x = document.getElementById("ioTheNumber");
	    x.style.display = "none";		
	    
//	    createInvoiceOutReport(postData.invoiceId);

	    
		
	});
	
//	askToSaveDialog = false;
//	somethingChangedInDialog = false;
//	setElementEnabled("saveInvoiceOutButton", false);

}// saveGeneratedInvoiceOut -- End
