
function initializeReset() {
	if (typeof _igModel === "undefined") {
		return;
	}
	
	if (showFormErrors("errorDiv", _igModel.message)) {
		setDivVisibility("inputForm", "none");
		return;
	}
	
	setDivVisibility("errorDiv", "none");
	setDivVisibility("inputForm", "block");
	
	$("#userIdInput").val(_igModel.userId);
	$("#usernameInput").val(_igModel.username);
	$("#tokenInput").val(_igModel.token);
}

function submitResetProfile() {
	var userId = $("#userIdInput").val();
	var password1 = $("#password1Input").val();
	var password2 = $("#password2Input").val();
	var token = $("#tokenInput").val();
	var errors = "";
	
	if (password1 != password2) {
		errors += "The password and verify password do not match<br>";
	}
	
	if (password1.length < 5) {
		errors += "The password must be longer than 5 characters<br>";
	}
	

	if (showFormErrors("errorDiv", errors)) {
		return;
	}
	
	var postUrl = "/rest/ignite/v1/profile/set";
	var postData = {
			userId: userId,
			password: password1,
			token: token
	};

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		url: springUrl(postUrl),
		type: 'POST',
		data: JSON.stringify(postData),
		contentType: "application/json",		
		beforeSend: function(xhr) {
			try {
				if ((!(header === undefined)) && (header != null)) {
					xhr.setRequestHeader(header, token);
				}
			} catch(error) {
				console.log("Warning: " + error);
			}
		},
		success: function(response, statusText, xhr) {
			showDialog("Success",
					   "Your password has been changed", 
					   DialogConstants.TYPE_ALERT, 
					   DialogConstants.ALERTTYPE_SUCCESS,
					   function() {
				           window.location.href = springUrl("/logout");
					   } );
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if ((errorThrown == null) || (errorThrown == "")) {
				errorThrown = jqXHR.responseText;
			}
			
			if ((jqXHR.status == 400) && (jqXHR.responseText.indexOf("ConstraintViolation") > 0)) {
				jqXHR = {
						status: "Error"
				};
				errorThrown = "Cannot save this record because of a database constraint.";
			}
			
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeReset();
} );
