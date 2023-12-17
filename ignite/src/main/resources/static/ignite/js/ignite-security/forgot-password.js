function showSuccess() {
	showFormSuccess("errorDiv", "An email has been sent which will allow you to reset your password.");
}

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function submitReset() {
	var postUrl = springUrl("/rest/ignite/v1/profile/reset");
	console.log(postUrl);
	var postData = {
			email: $("#emailAddressInput").val()
	};
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var errors = "";
	
	if ((postData.email == null) || (postData.email == "")) {
		errors += "An email address must be supplied<br>";
	} else {
		if (!validateEmail(postData.email)) {
			errors += "A valid email address must be supplied<br>";
		}
	}

	if (showFormErrors("errorDiv", errors)) {
		return;
	}
	
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
			showSuccess();
 		},
		error: function(jqXHR, textStatus, errorThrown) {
			if ((errorThrown == null) || (errorThrown == "")) {
				errorThrown = jqXHR.responseText;
			}
			
			if ((jqXHR.status == 400) && (jqXHR.responseText.indexOf("ConstraintViolation") > 0)) {
				errorThrown = "Cannot save this record because of a database constraint.";
			}
			
			showFormErrors("errorDiv", errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}   
	});


	// TODO: validate the email address

	// TODO: call proc - on result update errors or submitted message
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
} );
