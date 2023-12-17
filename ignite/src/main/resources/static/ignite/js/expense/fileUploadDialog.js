var askToSaveDialog = false;
var somethingChangedInDialog = false;
//setElementEnabled("fileUploadFileImportButton", false);

var tableReload = false;

function resetFileDialog() {
	askToSaveDialog = false;
	somethingChangedInDialog = false;
	setElementEnabled("fileUploadFileImportButton", false);
}

function fileUploadDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("fileUploadFileImportButton", true);
}


function closeFileUploadDialog() {
	if ((askToSaveDialog == true) || (somethingChangedInDialog == true)) {
		showDialog("Confirm?",
				"The file has not been uploaded yet - are you sure you wish to cancel?",
				DialogConstants.TYPE_CONFIRM,
				DialogConstants.ALERTTYPE_INFO,
				function(e) {
					setDivVisibility("errorMsgDiv", "none");
					closeModalDialog("fileUploadDialog");
					resetFileDialog()
				}
		)
	} else {
		setDivVisibility("errorMsgDiv", "none");
		closeModalDialog("fileUploadDialog");
		resetFileDialog()
	}
}



function uploadFile(tbl) {
	var successMessage = "The file has been uploaded.";
	
	showToast("Success", successMessage, DialogConstants.ALERTTYPE_SUCCESS);
	
	setDivVisibility("errorMsgDiv", "none");
	resetFileDialog();
	
	window.setTimeout(function() {
		try {
			// TODO: This code needs to be made more generic
			if (!tableReload) {
				tableReload = true;
				
				tbl.rows( { selected: true } ).deselect();
				tbl.ajax.reload(function() {
					tableReload = false;
				});
			};
		} catch (e) {
			console.log(e);
		};
		
//		pdProjectExpenseFiles.ajax.reload();
	}, 500);
	
}



function selectFile() {
	$("#sDlgUploadFile").click();
}

function initializeFileUploadDialogSubmitHandler(successMethod, failureMethod) {
 	$("#fileUploadDialogForm").on('submit', function(e) {
		showPleaseWait();
		
		// e.preventDefault();
	    $.ajax(/*{
	        type: $(this).prop('method'),
	        url : $(this).prop('action'),
	        data: $(this).serialize()
	    }*/)
	    .done(function(data) {
			window.setTimeout(function() {
				var iframe = document.getElementById('fileUploadDialogIframe');
				var iframeUrl = iframe.contentWindow.location.href;
				
				console.log("iframeUrl:=   " + iframeUrl)				
				
				if (iframeUrl.indexOf("upload-success") > -1) {
					// success
					console.log("1")
					hidePleaseWait();
					$("#fileUploadDialog").modal("hide");
					
					successMethod();
				} else {
					// failure... get the message
					console.log("2")
					hidePleaseWait();

					var error = "An unknown error occurred";
					if (iframeUrl.indexOf("?e=") > -1) {
						error = decodeURI(iframeUrl.substring(iframeUrl.indexOf("?e=") + 3));
					}

					failureMethod(error);
				}
		    }, 2000);  // give it two seconds to upload and show the page
	    })
	    .fail(function(jqXHR, textStatus, errorThrown) {
			failureMethod(textStatus);
		});
	});
}
	
//**************************************************************** 

$(document).on('change', ':file', function() {
    var fileSelector = $(this);
    var filenameInput = $("#sDlgUploadFileInput");

    var label = fileSelector.val().replace(/\\/g, '/').replace(/.*\//, '');
    filenameInput.val(label);
    
    if ((filenameInput.val() != null) && (filenameInput.val() != "")) {
		document.getElementById("sDlgUploadFileInput").onchange();
	}
});

$(document).ready(function(){
});


