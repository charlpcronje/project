
function showErrorMessage() {
	var error = "No error message could be retrieved";
	
	if (_igModel.hasOwnProperty("error")) {
		if ((error != null) || (_igModel.error == "")) {
			error = _igModel.error;
		}
	}
	
	if ((error.toLowerCase() == "access denied") || 
			(error.toLowerCase() == "access denied.")) {
		error = "You do not have permission to access the requested resource." +
		        "<br><br>" +
		        "Please contact the Administrator.";
	}
	
	$("#errorMessage").html(error);
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	showErrorMessage();
} );
