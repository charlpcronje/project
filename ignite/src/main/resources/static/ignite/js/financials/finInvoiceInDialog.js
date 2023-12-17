// finInvoiceInDialog.js //
var somethingChangedInDialog = false;
var lastIncTyped = false;
var lastExcTyped = false;
//var iiInvoiceInFiles = null;

//*********************************************************

function editInvoiceIn(rowSelector) {

	var errors = "";
	var data = {}; // Give it an empty object (so, need to add a new record)
	var enabled = false;
	
	var participantIdTo = $("#finParticipantId").val();
	var participantNameTo = $("#finParticipantSystemName").val();
	var participantVatNumberTo = $("#finParticipantVat").val();
	
	console.log(participantIdTo);
	$("#iiParticipantIdTo").val(participantIdTo);
	$("#iiParticipantNameTo").val(participantNameTo);
	$("#iiParticipantVatNumberTo").val(participantVatNumberTo);
		
	if (rowSelector != null) {
		data = iiInvoicesInTable.row(rowSelector).data();
		console.dir(data)
		enabled = true;
		if ((data.invoiceTaxAmount == "") || (data.invoiceTaxAmount == null) || (data.invoiceTaxAmount == "0")) {
			document.getElementById("taxCheck").checked = false;
		} else {
			document.getElementById("taxCheck").checked = true;
		}		
		$("#iiInvoiceInId").val(data.invoiceId); 
		$("#iiInvoiceInDate").datepicker("setDate", data.invoiceDate == null? "" : new Date(data.invoiceDate));
		$("#iiDateReceived").datepicker("setDate", data.dateSentOrReceived == null? "" : new Date(data.dateSentOrReceived));
		
		$("#iiParticipantVatNumberFrom").val(data.fromVatNumber); 
		$("#iiParticipantIdFrom").val(data.participantIdFrom); 
		$("#iiParticipantFromSystemName").val(data.fromParticipantName); 
		$("#iiInvoiceNumber").val(data.invoiceNumber); 

		$("#iiClientReferenceNumber").val(data.clientReference); 
		$("#iiDescription").val(data.description);

		$("#iiInvoiceAmountExclTax").val((data.invoiceAmountExclTax != null) ? "R " + (data.invoiceAmountExclTax).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke		
		$("#iiInvoiceTaxAmount").val((data.invoiceTaxAmount != null) ? "R " + (data.invoiceTaxAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
		$("#iiInvoiceTotalAmountInclTax").val((data.invoiceTotalAmountInclTax != null) ? "R " + (data.invoiceTotalAmountInclTax).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke
		
		
		showDisplayerFrame(true);
		enabled = true; 
	} else {
		document.getElementById("taxCheck").checked = true;
		
		$("#iiInvoiceInId").val(""); 
		$("#iiInvoiceInDate").datepicker("setDate", timestampToString(new Date(), false));
		$("#iiDateReceived").datepicker("setDate", timestampToString(new Date(), false));
		
		$("#iiParticipantVatNumberFrom").val(""); 
		$("#iiParticipantIdFrom").val(""); 
		$("#iiParticipantFromSystemName").val(""); 
		$("#iiInvoiceNumber").val(""); 
		$("#iiInvoiceAmountExclTax").val(""); 
		$("#iiInvoiceTaxAmount").val(""); 
		$("#iiInvoiceTotalAmountInclTax").val(""); 
		$("#iiClientReferenceNumber").val(""); 
		$("#iiDescription").val(""); 

		showDisplayerFrame(false);
		enabled = false;
	}
	iiInvoicesInTable.rows().deselect();
	
	setElementEnabled("saveInvoiceInButton", false);
	somethingChangedInDialog = false;
	
	showModalDialog("finInvoiceInDialog");
}  //editInvoiceIn -- End



function showDisplayerFrame(show) {
	
}



function selectParticipantIdFrom() {
	selectParticipantFin("iiParticipantIdFrom", "iiParticipantFromSystemName");
}


function selectParticipantFin(targetId, targetName) {
	
	var queryUrl="/rest/ignite/v1/participant/all-in-view";
	
//	console.log("In finInvoiceInDialog.js  selectParticipantFin:  targetId:" + targetId + "  targetName:" + targetName + "   queryUrl:" + queryUrl);
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="participantId";
			var refColumnName="participantId";
			
			var columns = [
            		{ data: "participantId", name: "#" },				//0
            		{ data: "systemName", name: "System Name" },		//1 
            		{ data: "nickName", name: "Nickname (indiv)" },		//2 
            		{ data: "", name: "Registered / First Name" },		//3 Registered / First Name
            		{ data: "", name: "Trading / Last Name" },			//4 Trading / Last Name
            		{ data: "representativeIndividualId", name: "Representative Individual ID" },		//5
            		{ data: "representative", name: "Representative" },				//6
            		{ data: "participantTypeCode", name: "ParticipantTypeCode" },	//7
            		{ data: "participantTypeName", name: "Type" },					//8
            		{ data: "isIndividual", name: "Individual" },					//9
            		{ data: "tapSubscriptionCode", name: "TAP SubscriptionCode" },	//10
            		{ data: "tapSubscriptionName", name: "Subscription" },			//11
            		{ data: "vatNumber", name: "VatNumber" }			//12
            ];				

			var columnDefs = [
				{ 
					visible: false,
					targets: [5,6,7,8,10,11,12]
				}
				,{
					render: function(data, type, row) {
						id = leadingZeroPad(data,4);
						return id;
					},
					targets: [0]
				}
				,{
					render: function(data, type, row) {
						var name = "";
						if (row.hasOwnProperty("isIndividual")) {
							if (row.isIndividual == "Y") {
								name = row.firstName;
							} else {
								name = row.registeredName;
							}
						}
						return name;
					},
					targets: [3]
				}
				,{
					render: function(data, type, row) {
						var name = "";
						if (row.hasOwnProperty("isIndividual")) {
							if (row.isIndividual == "Y") {
								name = row.lastName;
							} else {
								name = row.tradingName;
							}
						}
						return name;
					},
					targets: [4]
				}
				,{
					render: function(data, type, row) {
						return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
					},
					className: "dt-center",
					targets: 9
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var repName = row.systemName;
				var vatNumber = row.vatNumber;

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				$("#iiParticipantVatNumberFrom").val(vatNumber);
				iiDialogChanged();                
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
}//selectParticipantFin







//saveInvoiceIn -- Begin
function saveInvoiceIn() {
	
	$("#iiInvoiceAmountExclTax").val($("#iiInvoiceAmountExclTax").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	$("#iiInvoiceTaxAmount").val($("#iiInvoiceTaxAmount").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	$("#iiInvoiceTotalAmountInclTax").val($("#iiInvoiceTotalAmountInclTax").val().replace('R','').replace(/ /g,'')); //remove spaces (thousand separator) and R-symbol
	
	var enabled = false;
	var postUrl = "/rest/ignite/v1/invoice/new";
	var postData = {

			 invoiceId				: $("#iiInvoiceInId").val(),
			 invoiceDate            : getMsFromDatePicker("iiInvoiceInDate"),
			 
			 dateSentOrReceived     : getMsFromDatePicker("iiDateReceived"),
			 
			 participantIdTo      	: $("#iiParticipantIdTo").val(),
			 toParticipantName      : $("#iiParticipantNameTo").val(),
			 participantIdFrom      : $("#iiParticipantIdFrom").val(),                      
			 fromParticipantName    : $("#iiParticipantFromSystemName").val(),
			 fromVatNumber          : $("#iiParticipantVatNumberFrom").val(),
			 toVatNumber            : $("#iiParticipantVatNumberTo").val(),
			 	 
			 invoiceNumber          : $("#iiInvoiceNumber").val(),
			 invoiceAmountExclTax      : $("#iiInvoiceAmountExclTax").val(),
			 invoiceTaxAmount          : $("#iiInvoiceTaxAmount").val(),
			 invoiceTotalAmountInclTax : $("#iiInvoiceTotalAmountInclTax").val(),
			 clientReference        : $("#iiClientReferenceNumber").val(),
			 description            : $("#iiDescription").val(),
			 flagDraft				: 'N'
	};

	var errors = "";

	
	// validate
	if ((postData.invoiceDate == null) || (postData.invoiceDate == "")) {
		errors += "An Invoice Date is required.<br>";
	}
	if ((postData.participantIdFrom == null) || (postData.participantIdFrom == "")) {
		errors += "Select a participant from whom the Invoice was received.<br>";
	}
	if ((postData.invoiceNumber == null) || (postData.invoiceNumber == "")) {
		errors += "An Invoice Number is required.<br>";
	}	
	
	
	if ((postData.invoiceTotalAmountInclTax == null) || (postData.invoiceTotalAmountInclTax == "")) {
		errors += "Please enter an Invoice Amount.<br>";
	} else {
		if (isNaN(postData.invoiceTotalAmountInclTax)) {
			errors += "Please enter a valid Invoice Amount.<br>";
		}
	}	
	
	if (showFormErrors("iiDlgErrorDiv", errors)) {
		return;
	};
	
	
	if ((postData.invoiceId != null) && (postData.invoiceId != "")) {  
		postUrl = "/rest/ignite/v1/invoice";
		$("#iiInvoiceInId").val(postData.invoiceId);
	}
	// Third 
	saveEntry(postUrl, postData, null, "The Invoice Received has been saved. Add detail and image(s) of the invoice if needed.", iiInvoicesInTable, function(request, response) {
		
		$("#iiInvoiceInId").val(response.invoiceId);
		setDivVisibility("iiDlgErrorDiv", "none");
		showDisplayerFrame(true);
	});
	
	enabled = true;
	askToSaveDialog = false;
	somethingChangedInDialog = false;
	setElementEnabled("saveInvoiceInButton", false);
	
}// saveInvoiceIn -- End




//iiDialogChanged -- Start
function iiDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveInvoiceInButton", true);
}
//iiDialogChanged -- End

//closeInvoiceInDialog -- Start
function closeInvoiceInDialog() {
	if (somethingChangedInDialog == true) {
		showDialog("Confirm?",
				"You have unsaved changes - are you sure you wish to cancel?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					setDivVisibility("iiDlgErrorDiv", "none");
					closeModalDialog("finInvoiceInDialog");
				}
		)
	} else {
		setDivVisibility("iiDlgErrorDiv", "none");
		closeModalDialog("finInvoiceInDialog");
	}
} //closeInvoiceInDialog -- End


function iiFileSelected() {
	
	const fileInput = document.getElementById('iiInvoiceImage');
	fileInput.onchange = () => {
	  const selectedFile = fileInput.files[0];
	  console.log(selectedFile);
	}
}


function iiPDFFileSelected() {

//	PDDocument document = new PDDocument();
//	PDPage page = new PDPage();
//	document.addPage(page);
//	
//	PDPageContentStream contentStream = new PDPageContentStream(document, page);
//	
//	contentStream.setFont(PDType1Font.COURIER, 12);
//	contentStream.beginText();
//	contentStream.showText("Hello World");
//	contentStream.endText();
//	contentStream.close();
//	
//	document.save("pdfBoxHelloWorld.pdf");
//	document.close();
}

// Attachment Upload -- Start

function showInvoiceUploadDialog() {
	// Set flags to indicate Purchase documents are uploaded
	$("#fileDlgUploadType").val("invoice");
	// This upload type determines which piece of code is executed in the UploadController when uploading
	$("#fileDlgPrimaryKey").val("invoiceId");
	$("#fileDlgPrimaryKeyValue").val($("#iiInvoiceInId").val());
	$("#sDlgUploadFileInput").val("")
	$("#fileDlgFileDescription").val("")
	showModalDialog("fileUploadDialog");
}


function initializeInvoiceInFiles() {
//	setElementEnabled("promptDeleteFileBtn", false);
	var invoiceId = $("#iiInvoiceInId").val();
	var queryUrl = "/rest/ignite/v1/invoice/files/" + invoiceId;
	console.log(queryUrl);
	console.log("Initializing table");
	
	
	var columnArray = [
		{ data: "invoiceFileId" },
		{ data: "invoiceId" },
		{ data: "fileName" },
		{ data: "originalFileName" },
		{ data: "fileType" },
		{ data: "fileSize" },
		{ data: "uploadDate" },
		{ data: "description" },
		{ data: "lastUpdateUserName" },
		{ data: "lastUpdateTimestamp" }
	];

	
	var columnDefsArray = [
		
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = timestampToString(data, false);
				}
				
				return data;
			},
			targets: [6, 9]
		},
		{
			visible: false,
			targets: [0, 1, 2, 3, 5, 8, 9]
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
				if (row.fileType == "Directory") {
				data = "<a href='#' onclick='changeDirectory(\"" + row.id + "\")'>" +
				"<i class='document-manager fas fa-folder' style='color: #f5c400'></i> " + row.name +
				"</a>";
				} else {
				// set as default file
				data = "<i class='document-manager far fa-file'></i> " + row.name;
				// depending on what we have select an icon
				if ((row.fileType != null) && (row.fileType != "")) {
				if (row.fileType.indexOf(".pdf") > -1) { data = "<i class='document-manager far fa-file-pdf' title='PDF File'></i> "; }
				if (row.fileType.indexOf("Powerpoint") > -1) { data = "<i class='document-manager far fa-file-powerpoint'></i> " + row.name; }
				if (row.fileType.indexOf("Word") > -1) { data = "<i class='document-manager far fa-file-word'></i> " + row.name; }
				if (row.fileType.indexOf(".xlsx") > -1) { data = "<i class='document-manager far fa-file-excel' title='xlsx'></i> "; }
				if (row.fileType.indexOf(".csv") > -1) { data = "<i class='document-manager fas fa-file-excel' title='.csv'></i>"; }
				if (row.fileType.indexOf(".jpg") > -1) { data = "<i class='document-manager far fa-file-image' title='.jpg'></i> "; }
				if (row.fileType.indexOf(".png") > -1) { data = "<i class='document-manager far fa-file-image' title='.png'></i> "; }
				if (row.fileType.indexOf(".txt") > -1) { data = "<i class='document-manager far fa-file-alt' title='.txt'></i> "; }
				if (row.fileType.indexOf("Compressed") > -1) { data = "<i class='document-manager far fa-file-archive'></i> " + row.name; }
				// there are a lot of other types, incl. contract, invoice, etc....
				// https://fontawesome.com/icons?d=gallery&q=file%20&s=solid&m=free
				// https://fontawesome.com/icons?d=gallery&q=file&s=regular&m=free
				}
				}
				}
				return data;
				},
			targets: 4
		}

		/*{
			render: function(data, type, row) {
				if (type == "display") {
					data = shortenText(data, 25);
				}
				return data;
			},
			targets: 2
		}*/
	];

	var buttonsArray = [
		{
			titleAttr: "Upload",
			text: "<i class='fas fa-folder-open'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				showInvoiceUploadDialog();
			}
		},
		{
			attr: {
				id: "promptDeleteFileBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteInvoiceFile();
			}
		}
	];

	iiInvoiceInFiles = initializeGenericTable("iiInvoiceInFiles",
		queryUrl,
		columnArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			// no dbl click at this point
		},
		null,
		30
	);
	
	iiInvoiceInFiles.off('deselect');
	iiInvoiceInFiles.on('deselect', function() {
		
		hideDocumentDisplayer();
		resetInvoicePreview();
		updateInvoiceFileToolbar();
	});

	iiInvoiceInFiles.off('select');
	iiInvoiceInFiles.on('select', function(e, dt, node, config) {
		
		showDocumentDisplayer();
		previewInvoiceFile(dt.data());
		updateInvoiceFileToolbar();
	});
	
	hideDocumentDisplayer();
	updateInvoiceFileToolbar();
}

function showDocumentDisplayer() {
	setDivVisibility("emptyDocumentDisplayer", "none");
	setDivVisibility("documentDisplayer", "block");
}

function hideDocumentDisplayer() {
	setDivVisibility("emptyDocumentDisplayer", "block");
	setDivVisibility("documentDisplayer", "none");
}

function updateInvoiceFileToolbar() {
	var hasSelected = iiInvoiceInFiles.rows('.selected').data().length > 0;
	
	setTableButtonState(iiInvoiceInFiles, "promptDeleteFileBtn", hasSelected);
}	

function promptDeleteInvoiceFile() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected file?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteFile(iiInvoiceInFiles);
			   }
	);
}

function deleteInvoiceFile(tbl) {
	var postUrl = "/rest/ignite/v1/invoice/file/delete";
	var row = tbl.rows('.selected').data()[0];
	
	// Disable delete button when record has been deleted.
	saveEntry(postUrl, row, null, "The file has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateInvoiceFileToolbar();
			});
}

function previewInvoiceFile(row) {
	if (row === undefined) {
		return;
	}
	var fileName = row.fileName;
	var fileType = row.fileType;
	var originalFileName = row.originalFileName;
	var invoiceFileId = row.invoiceFileId;
	
	var url = springUrl("/getfile?t=InvoiceFile&id=" + invoiceFileId);
	$("#displayerFrame").attr("src", url);
	$("#displayerFrame").attr("title", fileType);
	$("#displayerFrame").attr("style", "border-color: #dae2e7c4; border-style: solid"); 
}

function resetInvoicePreview() {
	$("#displayerFrame").attr("src", "");
}



function taxChanged() {
    if (document.getElementById('taxCheck').checked) {
//        setDivVisibility("taxDiv", "block");
    } else {
//        setDivVisibility("taxDiv", "none");
    	$("#iiInvoiceTaxAmount").val("0.00");
    }
    
    if (lastExcTyped) {
    	amountExclChanged();
    } else if (lastIncTyped) {
    	amountInclChanged();
    }    
}



function amountExclChanged() {

	lastExcTyped = true;
	lastIncTyped = false;
	if (isNaN($("#iiInvoiceAmountExclTax").val())) {
		$("#iiInvoiceTaxAmount").val("")
		$("#iiInvoiceTotalAmountInclTax").val("")
	} else {
		var invAmt = $("#iiInvoiceAmountExclTax").val()
	    if (document.getElementById('taxCheck').checked) {
	    	$("#iiInvoiceTaxAmount").val( (invAmt * 0.15).toFixed(2) );
	    	$("#iiInvoiceTotalAmountInclTax").val( (invAmt * 1.15).toFixed(2) );
	    } else {
	    	$("#iiInvoiceTaxAmount").val("0.00");
	    	$("#iiInvoiceTotalAmountInclTax").val( invAmt );
	    }
	}
	
}

function amountInclChanged() {

	lastExcTyped = false;
	lastIncTyped = true;
	if (isNaN($("#iiInvoiceTotalAmountInclTax").val())) {
		$("#iiInvoiceTaxAmount").val("")
		$("#iiInvoiceTotalAmountExclTax").val("")
	} else {
		var invAmt = $("#iiInvoiceTotalAmountInclTax").val()
	    if (document.getElementById('taxCheck').checked) {
	    	$("#iiInvoiceTaxAmount").val( (invAmt / 115 * 15).toFixed(2) );
	    	$("#iiInvoiceAmountExclTax").val( (invAmt / 115 * 100).toFixed(2) );
	    } else {
	    	$("#iiInvoiceTaxAmount").val("0.00");
	    	$("#iiInvoiceAmountExclTax").val( invAmt );
	    }
	}
	
}



function initializeInvoice() {
	$("#projectExpenseDialog").on('show.bs.modal', function () {
    	//nothing
  	});
  	
  	initializeFileUploadDialogSubmitHandler(
		function() {
			// success
	        uploadFile(iiInvoiceInFiles);
		},
		function(errorMessage) {
			// failure
			showDialog("Error", errorMessage);
		}
	);
  	

}



//***********************************************************************

document.addEventListener("DOMContentLoaded", function(event) { 
	initializeInvoice();
});

//$(document).ready(function() {
	// Any initialization
//	initializeInvoice();
//});
