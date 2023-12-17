
function loadProfile() {
	if (typeof individual === undefined) {
		return;
	}
	
	$("#usernameInput").val(individual.userName);
	$("#firstNameInput").val(individual.firstName);
	$("#initialsInput").val(individual.initials);
	$("#lastNameInput").val(individual.lastName);
	$("#idNumberInput").val(individual.idNumber);
}

function saveProfile() {
	var postUrl = "/rest/ignite/v1/profile";
	var postData = {
			userName: $("#usernameInput").val(),
			firstName: $("#firstNameInput").val(),
			initials: $("#initialsInput").val(),
			lastName: $("#lastNameInput").val(),
			idNumber: $("#idNumberInput").val()
	};
	
	var errors = "";

	if (postData.userName == "") { errors += "A Username is required<br>"; }
	if (postData.firstName == "") { errors += ">A First Name is required<br>"; }
	if (postData.lastName == "") { errors += "A Last Name is required<br>"; }
	if (postData.idNumber == "") { 
		errors += "An ID Number is required<br>"; 
	} else {
		if (postData.idNumber.length != 13) {
			errors += "An ID Number must be 13 characters long<br>";
		}
	}
	
	if (showFormErrors("profileErrorDiv", errors)) {
		return;
	}
	
	saveEntry(postUrl, postData, null, "Your profile has been updated.", null, function() {
		// refresh the page because the users display name could have changed
		location.reload();
	});
}

function changePassword() {
	showModalDialog("changePasswordDialog");
}

function saveNewPassword() {
	var errors = "";
	var currentPwd = $("#cpDlgExisting").val();
	var newPwd = $("#cpDlgNew").val();
	var confirmPwd = $("#cpDlgConfirm").val();
	
	if ((currentPwd == null) || (currentPwd == "")) {
		errors += "The Current Password must be captured<br>";
	}
		
	if ((newPwd == null) || (newPwd == "")) {
		errors += "The New Password must be captured<br>";
	}
		
	if ((confirmPwd == null) || (confirmPwd == "")) {
		errors += "The confirmation of the New Password must be captured<br>";
	}
		
	if ((newPwd != null) && (newPwd != "")) {
		if ((confirmPwd != null) && (confirmPwd != "")) {
			if (newPwd != confirmPwd) {
				errors += "The New Password and the Confirm Password do not match<br>";
			}
		}	
	}

	if (scorePassword(newPwd) <= 50) {
		errors += "Your password is too weak";
	}
			
	if (showFormErrors("cpDlgErrorDiv", errors)) {
		return;
	}

	var postUrl = "/rest/ignite/v1/profile/change";
	var postData = {
		currentPwd: currentPwd,
		newPwd: newPwd
	}
	
	saveEntry(postUrl, postData, "changePasswordDialog", "Your Password has been updated.", null, null);
}

function scorePassword(pass) {
    var score = 0;
    if (!pass)
        return score;

    // award every unique letter until 5 repetitions
    var letters = new Object();
    for (var i=0; i<pass.length; i++) {
        letters[pass[i]] = (letters[pass[i]] || 0) + 1;
        score += 5.0 / letters[pass[i]];
    }

    // bonus points for mixing it up
    var variations = {
        digits: /\d/.test(pass),
        lower: /[a-z]/.test(pass),
        upper: /[A-Z]/.test(pass),
        nonWords: /\W/.test(pass),
    }

    var variationCount = 0;
    for (var check in variations) {
        variationCount += (variations[check] == true) ? 1 : 0;
    }
    score += (variationCount - 1) * 10;

    return parseInt(score);
}

function passwordScoreToText(pass) {
    var score = scorePassword(pass);

	var fontGreen = "<font color='#0bd400'>";
	var fontRed = "<font color='#d40000'>"

    if (score >= 90) return "Your password is " + fontGreen + "<b>very strong</b></font>";
    if (score >= 80) return "Your password is " + fontGreen + "<b>strong</b></font>";
    if (score >= 60) return "Your password is " + fontGreen + "<b>good</b></font>";
    if (score >= 50) return "Your password is " + fontGreen + "<b>adequate</b></font>";
    if (score >= 30) return "Your password is " + fontRed + "<b>weak</b></font>";
    if (score < 30) return "Your password is " + fontRed + "<b>very weak</b></font>";
}

function updatePasswordStrength() {
	var pwd = $("#cpDlgNew").val();
	var txt = passwordScoreToText(pwd);
	
	$("#passwordStrengthLabel").html(txt);
}

// ***********************************************************************

$(document).ready(function() {
	loadProfile();
	
	$("#cpDlgNew").on("input", function() {
		updatePasswordStrength();
	});
} );
