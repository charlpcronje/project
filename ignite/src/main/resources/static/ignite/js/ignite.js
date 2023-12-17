var dynamicSelectGridTable = null;
var dynamicSelectOneDialog = "dynamicSelectOneDialog";
var dynamicSelectGridDialog = "dynamicSelectFromGridDialog";
var dynamicSelectGridCallbackMethod = null;

var seeFormName = false;

//var pathInvoices = "C:\ignite\invoices"

var _genericSelectType = "single";

var DialogConstants = {
    // dialog types
	TYPE_ALERT:   "alert",
    TYPE_CONFIRM: "confirm",
    TYPE_PROMPT:  "prompt",
    
    // alert types
    ALERTTYPE_DEFAULT: "default",
    ALERTTYPE_PRIMARY: "primary",
    ALERTTYPE_SUCCESS: "success",
    ALERTTYPE_INFO:    "info",
    ALERTTYPE_WARNING: "warning",
    ALERTTYPE_ERROR:   "danger",
	ALERTTYPE_DANGER:  "danger"
};
Object.freeze(DialogConstants);


if (!String.prototype.startsWith) {
	String.prototype.startsWith = function(search, pos) {
		return this.substr(!pos || pos < 0 ? 0 : +pos, search.length) === search;
	};
}


if (!String.prototype.endsWith) {
  String.prototype.endsWith = function(searchString, position) {
      var subjectString = this.toString();
      if (typeof position !== 'number' || !isFinite(position) 
          || Math.floor(position) !== position || position > subjectString.length) {
        position = subjectString.length;
      }
      position -= searchString.length;
      var lastIndex = subjectString.indexOf(searchString, position);
      return lastIndex !== -1 && lastIndex === position;
  };
}


// replace all functionality on strings
String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.split(search).join(replacement);
};


// Prototype for sorting arrays
// Source: https://stackoverflow.com/questions/1129216/sort-array-of-objects-by-string-property-value
Array.prototype.sortBy = function(p) {
  return this.slice(0).sort(function(a,b) {
    return (a[p] > b[p]) ? 1 : (a[p] < b[p]) ? -1 : 0;
  });
}

function isObject(val) {
    if (val === null) { return false;}
    return ( (typeof val === 'function') || (typeof val === 'object') );
}

function endsWith(value, text) {
	var result = false;
	
	if ((value != null) && (value != "")) {
		idx = value.toLowerCase().indexOf(text.toLowerCase());
		result = idx == (value.length - text.length);
	}
	
	return result;
}

// pad a number with leading zeros to the specified length
function leadingZeroPad(num, len) {
	while ((String(num)).length < len) {
		num = "0" + num;
	}
	return num;
}

function dateToString(timestamp) {
	if ((timestamp == null) || (timestamp == "")) {
		return null;
	}

	var date = new Date(timestamp);
	var result = dateTimeToString(date, false);
	
	if ((result == "1899-12-31") || (result == "1969-12-31")){      //J// if (result == "31/12/1899") {
		result = "";
	}
	
	return result;
}

function timestampToString(timestamp, includeTime, includeMilliseconds) {
	if (includeMilliseconds === undefined) {
		includeMilliseconds = false;
	}
	
	if (includeTime === undefined) {
		includeTime = true;
	}
	
	if (typeof timestamp == "date") {
		timestamp = date.getTime();
	}

	if ((timestamp == -2208996000000) && (includeTime)) {
		return null;
	}
	
	if ((timestamp == null) || (timestamp == "") || (timestamp == "null")) {
		return null;
	}
	
	if (typeof timestamp == "string") {
		if (timestamp.indexOf(":") > 0) {
			return timestamp;
		}
		
		timestamp = Number(timestamp);
	}
	
	var date = new Date(timestamp);
	return dateTimeToString(date, includeTime, includeMilliseconds);
}

function dateTimeToString(date, inclTime, inclMs) {
	var d = leadingZeroPad(date.getDate(), 2);
	var m = leadingZeroPad(date.getMonth() + 1, 2);
	var y = date.getFullYear();
	var h = leadingZeroPad(date.getHours(), 2);
	var mn = leadingZeroPad(date.getMinutes(), 2);
	var sec = leadingZeroPad(date.getSeconds(), 2);
	var ms = leadingZeroPad(date.getMilliseconds(), 3);
	
	var datStr = y + "-" + m + "-" + d;   //J// var datStr = d + "/" + m + "/" + y;
	
	if (inclMs === undefined) {
		inclMs = false;
	}
	
	if (inclTime) {
		datStr = datStr + " " + h + ":" + mn + ":" + sec;
		
		if (inclMs) {
			datStr = datStr + "." + ms;
		}
	}

	if ((datStr == "1900-01-01") || (datStr == "1900-01-01 00:00:00") || (datStr == "1970-01-01") || (datStr == "1970-01-01 00:00:00")) {     //if ((datStr == "01/01/1900") || (datStr == "01/01/1900 00:00:00")) {
		datStr = "";
	}
	
	return datStr;
}



function getMsFromDatePicker(elementId) {
	var result = null;
	
	var d = $("#" + elementId).datepicker("getDate");
//	var d = $(elementId).datepicker("getDate");
	if (d != null) {
		result = d.getTime();
	}
	
	return result;
} 

function springUrl(url) {
	if ((url != null) && (url.indexOf("/") == 0)) {
		url = url.substring(1);
	}

	return url;
}

function getMessageFromResponse(jqXHR, textStatus, errorThrown) {
	var msg = "Unknown error";
	
	if ((errorThrown == null) || (errorThrown == "")) {
		msg = jqXHR.responseText;
	} else {
		msg = jqXHR.status + " - " + errorThrown;
	}
	
	if ((msg != null) && (msg.startsWith("404 -"))) {
		msg = "404 - The page or service could not be found.";
	}
	
	if (msg != null) {
		// a 200 "error" occurs when the connection has timeed out and the API response sends us a login page
		if (msg.startsWith("0 -")) {
			window.location=springUrl("/login");
			return;
		} 
		
		// Should redirect to login if the server has gone away or if the session has expired (timeout)
		if (msg.startsWith("200 - SyntaxError: Unexpected token < in JSON at position 0")) {
			setCookie("timeout", "true");
			window.location=springUrl("/login");
			return;
		}
	}
	
	// this error is special - it may contain line breaks - change it to HTML
	if ((!(msg === undefined)) && (msg != null) && (msg.indexOf("\r\n") > -1)) {
		msg = msg.replaceAll("\r\n", "<br><br>");
	}
	
	return msg;
}

function ajaxError(jqXHR, textStatus, errorThrown) {
	var msg = getMessageFromResponse(jqXHR, textStatus, errorThrown);
	showDialog("Error", msg);
}

function ajaxSuccess(msg) {
	// nothing?
}

//Source: https://stackoverflow.com/questions/10420352/converting-file-size-in-bytes-to-human-readable-string
function humanFileSize(size) {
	if (size == null) {
		return "";
	}
    var i = (size == 0 ? 0 : Math.floor(Math.log(size) / Math.log(1024)));
    return ( size / Math.pow(1024, i) ).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
}

//Source: https://www.bootply.com/PoVEEtvPZt
function ezBSAlert(options) {
	var deferredObject = $.Deferred();
	var defaults = {
		type : "alert", // alert, prompt, confirm
		modalSize : 'modal-sm', // modal-sm, modal-lg
		okButtonText : 'Ok',
		cancelButtonText : 'Cancel',
		yesButtonText : 'Yes',
		noButtonText : 'No',
		headerText : 'Attention',
		messageText : 'Message',
		alertType : 'default', // default, primary, success, info, warning, danger
		inputFieldType : 'text', // could ask for number, email, etc
	};
	
	$.extend(defaults, options);

	var _show = function() {
		var headClass = "navbar-default";
		switch (defaults.alertType) {
		case "primary":
			headClass = "alert-primary";
			break;
		case "success":
			headClass = "alert-success";
			break;
		case "info":
			headClass = "alert-info";
			break;
		case "warning":
			headClass = "alert-warning";
			break;
		case "danger":
			headClass = "alert-danger";
			break;
		}
		$('BODY')
				.append(
						'<div id="ezAlerts" class="modal fade">'
								+ '<div class="modal-dialog" class="'
								+ defaults.modalSize
								+ '">'
								+ '<div class="modal-content">'
								+ '<div id="ezAlerts-header" class="modal-header '
								+ headClass
								+ '">'
								+ '<h4 id="ezAlerts-title" class="modal-title">Modal title</h4>'
								
								+ '<button id="close-button" type="button" class="close" data-dismiss="modal">'
								+ '<span aria-hidden="true"><i class="fas fa-times"></i></span><span class="sr-only">Close</span>'
								+ '</button>'
								
								+ '</div>'
								+ '<div id="ezAlerts-body" class="modal-body">'
								+ '<div id="ezAlerts-message" ></div>'
								+ '</div>'
								+ '<div id="ezAlerts-footer" class="modal-footer">'
								+ '</div>' 
								+ '</div>' 
								+ '</div>' 
								+ '</div>');

		$('.modal-header').css({
			'padding' : '15px 15px',
			'-webkit-border-top-left-radius' : '5px',
			'-webkit-border-top-right-radius' : '5px',
			'-moz-border-radius-topleft' : '5px',
			'-moz-border-radius-topright' : '5px',
			'border-top-left-radius' : '5px',
			'border-top-right-radius' : '5px'
		});

		$('#ezAlerts-title').text(defaults.headerText);
		$('#ezAlerts-message').html(defaults.messageText);

		var keyb = false;
		var backd = "static";
		var calbackParam = "";
		switch (defaults.type) {
		case 'alert':
			keyb = true;
			backd = "true";
			$('#ezAlerts-footer').html(
					'<button class="btn btn-' + defaults.alertType + ' btn-sm">'
							+ defaults.okButtonText + '</button>').on('click',
					".btn", function() {
						calbackParam = true;
						$('#ezAlerts').modal('hide');
					});
			break;
		case 'confirm':
			var btnhtml = '<button id="ezok-btn" class="btn btn-primary">'
					+ defaults.yesButtonText + '</button>';
			if (defaults.noButtonText && defaults.noButtonText.length > 0) {
				btnhtml += '<button id="ezclose-btn" class="btn btn-sm btn-default">'
						+ defaults.noButtonText + '</button>';
			}
			$('#ezAlerts-footer').html(btnhtml).on('click', 'button',
					function(e) {
						if (e.target.id === 'ezok-btn') {
							calbackParam = true;
							$('#ezAlerts').modal('hide');
						} else if (e.target.id === 'ezclose-btn') {
							calbackParam = false;
							$('#ezAlerts').modal('hide');
						}
					});
			break;
		case 'prompt':
			$('#ezAlerts-message')
					.html(
							defaults.messageText
									+ '<br /><br /><div class="form-group"><input type="'
									+ defaults.inputFieldType
									+ '" class="form-control" id="prompt" value="' + 
									((defaults.inputFieldValue == null) ? "" : defaults.inputFieldValue) + 
									'"/></div>');
			$('#ezAlerts-footer').html(
					'<button class="btn btn-primary">' + defaults.okButtonText
							+ '</button>').on('click', ".btn", function() {
				calbackParam = $('#prompt').val();
				$('#ezAlerts').modal('hide');
			});
			break;
		}

		// center the dialog
		$('#ezAlerts').css({
	        top: 0,
    	    left: 0
      	});

		$('#ezAlerts').modal({
			show : false,
			backdrop : false, // backd,
			keyboard : keyb
		}).on('hidden.bs.modal', function(e) {
			$('#ezAlerts').remove();
			deferredObject.resolve(calbackParam);
		}).on('shown.bs.modal', function(e) {
			if ($('#prompt').length > 0) {
				$('#prompt').focus();
			}
		}).modal('show');
		
		// make it draggable
		$('#ezAlerts').draggable({
	    	handle: ".modal-header"
		});
	};

	_show();
	return deferredObject.promise();
}

function showDialog(title, message, dialogType, alertStyle, callback) {
	ezBSAlert({
		type : (dialogType == null ? "alert" : dialogType),
		headerText: title,
		messageText : message,
		alertType : (alertStyle == null ? "default" : alertStyle)
	}).done(function(e) {
		if (callback != null) {
			if (e) {
				callback(e);
			}
		}
	});
}

// type: alert, confirm, prompt
// alertType: default, primary, success, info, warning, danger
function inputDialog(title, message, alertStyle, inputType, inputValue, callback) {
	ezBSAlert({
		type : "prompt",
		headerText: title,
		messageText : message,
		inputFieldType : inputType,
		inputFieldValue : inputValue,
		alertType : (alertStyle == null ? "default" : alertStyle)
	}).done(function(e) {
		if ((callback != null) && (e)) {
			callback(e);
		}
	});
}

//Cookie source: https://stackoverflow.com/questions/14573223/set-cookie-and-get-cookie-with-javascript
function setCookie(name, value, days) {
	var expires = "";
	if (days) {
		var date = new Date();
		date.setTime(date.getTime() + (days*24*60*60*1000));
		expires = "; expires=" + date.toUTCString();
	}
	document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}


function dynamicSelectOneDialogSelect(columnName, referencedColumnName, colIndex) {
    var selectedCount = dynamicSelectGridTable.rows('.selected').data().length;
    if ((selectedCount != 1) || (colIndex == -1)) {
        return;
    }

    var row = dynamicSelectGridTable.rows('.selected').data()[0];
    
    if (dynamicSelectGridCallbackMethod != null) {
        dynamicSelectGridCallbackMethod(row);
    }
}


function getCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function deleteCookie(name) {   
	document.cookie = name+'=; Max-Age=-99999999;SameSite=None;';  
}

//Allow the hiding or showing of a div
function setDivVisibility(elName, displayStyle) {
	var el = document.getElementById(elName);
	
	if (el != null) {
		el.style.display = displayStyle;
	}
}

function setTabEnabled(tabId, enabled) {
	var el = $("#" + tabId);
	var ela = $("#" + tabId + " a");
	
	if ((el == null) || (ela == null)) {
		return;
	}
	
	if (enabled) {
		el.removeClass("disabled");
		ela.removeClass("disabled");
		ela.attr('data-toggle', 'tab');
	} else {
		el.removeClass("active");
		el.addClass("disabled");
		ela.addClass("disabled");
		ela.removeAttr('data-toggle');		
	}
}

function shortenText(text, len) {
	var result = text;

	if ((text != null) && (text.length > len)) {
		var shortText = text.substring(0, len - 3) + "...";

		text = text.replaceAll("'", "\"");
		
		// result = "<span title='" + text + "' onclick='copyToShortenTextToClipboard(\"" + text + "\")'>" + shortText + "</span>";
		result = "<span title='" + text + "'>" + shortText + "</span>";
	}

	return result;
}

function copyToShortenTextToClipboard(text) {
	copyToClipboard(text);
}

// Copies a string to the clipboard. Must be called from within an event handler such as click
// 
// May return false if it failed, but this is not always possible. Browser support for Chrome 43+,
// Firefox 42+, Safari 10+, Edge and Internet Explorer 10+
//
// Internet Explorer: The clipboard feature may be disabled by an administrator. By default a 
// prompt is shown the first time the clipboard is used (per session)
function copyToClipboard(text) {
	if (window.clipboardData && window.clipboardData.setData) {
		// Internet Explorer-specific code path to prevent textarea being shown while dialog is visible
		return clipboardData.setData("Text", text);
	} else {
		if (document.queryCommandSupported && document.queryCommandSupported("copy")) {
			var textarea = document.createElement("textarea");
			textarea.textContent = text;
			textarea.style.position = "fixed";  // Prevent scrolling to bottom of page in Microsoft Edge
			document.body.appendChild(textarea);
			textarea.select();

			try {
				return document.execCommand("copy");  // Security exception may be thrown by some browsers
			} catch (ex) {
				console.warn("Copy to clipboard failed.", ex);
				return false;
			} finally {
				document.body.removeChild(textarea);
			}
		}
	}
}

function initializeNotificationBars() {
	initializeTaskNotificationBar();
	initializeInboxNotificationBar();
	initializeAlertNotificationBar();
}

function initializeTaskNotificationBar() {
	// initialize the li contents for taskNotificationBar with the HTML being generated dynamically as below
	
	// taskNotificationBar
/*
          <li id="task_notification_bar" class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <i class="icon-task-l"></i>
                            <span class="badge bg-important">6</span>
                        </a>
            <ul class="dropdown-menu extended tasks-bar">
              <div class="notify-arrow notify-arrow-blue"></div>
              <li>
                <p class="blue">You have 6 pending letter</p>
              </li>
              <li>
                <a href="#">
                  <div class="task-info">
                    <div class="desc">Design PSD </div>
                    <div class="percent">90%</div>
                  </div>
                  <div class="progress progress-striped">
                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width: 90%">
                      <span class="sr-only">90% Complete (success)</span>
                    </div>
                  </div>
                </a>
              </li>
              <li>
                <a href="#">
                  <div class="task-info">
                    <div class="desc">
                      Project 1
                    </div>
                    <div class="percent">30%</div>
                  </div>
                  <div class="progress progress-striped">
                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 30%">
                      <span class="sr-only">30% Complete (warning)</span>
                    </div>
                  </div>
                </a>
              </li>
              <li>
                <a href="#">
                  <div class="task-info">
                    <div class="desc">Digital Marketing</div>
                    <div class="percent">80%</div>
                  </div>
                  <div class="progress progress-striped">
                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                      <span class="sr-only">80% Complete</span>
                    </div>
                  </div>
                </a>
              </li>
              <li>
                <a href="#">
                  <div class="task-info">
                    <div class="desc">Logo Designing</div>
                    <div class="percent">78%</div>
                  </div>
                  <div class="progress progress-striped">
                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="78" aria-valuemin="0" aria-valuemax="100" style="width: 78%">
                      <span class="sr-only">78% Complete (danger)</span>
                    </div>
                  </div>
                </a>
              </li>
              <li>
                <a href="#">
                  <div class="task-info">
                    <div class="desc">Mobile App</div>
                    <div class="percent">50%</div>
                  </div>
                  <div class="progress progress-striped active">
                    <div class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: 50%">
                      <span class="sr-only">50% Complete</span>
                    </div>
                  </div>

                </a>
              </li>
              <li class="external">
                <a href="#">See All Tasks</a>
              </li>
            </ul>
          </li>

 */
}

function initializeInboxNotificationBar() {
	// initialize the li contents for inboxNotificationBar with the HTML being generated dynamically as below

	// inboxNotificationBar
	/*
	          <li id="mail_notification_bar" class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <i class="icon-envelope-l"></i>
                            <span class="badge bg-important">5</span>
                        </a>
            <ul class="dropdown-menu extended inbox">
              <div class="notify-arrow notify-arrow-blue"></div>
              <li>
                <p class="blue">You have 5 new messages</p>
              </li>
              <li>
                <a href="#">
                                    <span class="photo"><img alt="avatar" src="./img/avatar-mini.jpg"></span>
                                    <span class="subject">
                                    <span class="from">Greg Martin</span>
                                    <span class="time">1 min</span>
                                    </span>
                                    <span class="message">
                                        I really like this admin panel.
                                    </span>
                                </a>
              </li>
              <li>
                <a href="#">
                                    <span class="photo"><img alt="avatar" src="./img/avatar-mini2.jpg"></span>
                                    <span class="subject">
                                    <span class="from">Bob Mckenzie</span>
                                    <span class="time">5 mins</span>
                                    </span>
                                    <span class="message">
                                     Hi, What is next project plan?
                                    </span>
                                </a>
              </li>
              <li>
                <a href="#">
                                    <span class="photo"><img alt="avatar" src="./img/avatar-mini3.jpg"></span>
                                    <span class="subject">
                                    <span class="from">Phillip   Park</span>
                                    <span class="time">2 hrs</span>
                                    </span>
                                    <span class="message">
                                        I am like to buy this Admin Template.
                                    </span>
                                </a>
              </li>
              <li>
                <a href="#">
                                    <span class="photo"><img alt="avatar" src="./img/avatar-mini4.jpg"></span>
                                    <span class="subject">
                                    <span class="from">Ray Munoz</span>
                                    <span class="time">1 day</span>
                                    </span>
                                    <span class="message">
                                        Icon fonts are great.
                                    </span>
                                </a>
              </li>
              <li>
                <a href="#">See all messages</a>
              </li>
            </ul>
          </li>

	 */

}

function initializeAlertNotificationBar() {
	// initialize the li contents for alertNotificationBar with the HTML being generated dynamically as below

	// alertNotificationBar

	/*

          <li id="alert_notification_bar" class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">

                            <i class="icon-bell-l"></i>
                            <span class="badge bg-important">7</span>
                        </a>
            <ul class="dropdown-menu extended notification">
              <div class="notify-arrow notify-arrow-blue"></div>
              <li>
                <p class="blue">You have 4 new notifications</p>
              </li>
              <li>
                <a href="#">
                    <span class="label label-primary"><i class="icon_profile"></i></span>
                    Friend Request
                    <span class="small italic pull-right">5 mins</span>
                </a>
              </li>
              <li>
                <a href="#">
                    <span class="label label-warning"><i class="icon_pin"></i></span>
                    John location.
                    <span class="small italic pull-right">50 mins</span>
                </a>
              </li>
              <li>
                <a href="#">
	            	<span class="label label-danger"><i class="icon_book_alt"></i></span>
	                Project 3 Completed.
	                <span class="small italic pull-right">1 hr</span>
	            </a>
              </li>
              <li>
                <a href="#">
                    <span class="label label-success"><i class="icon_like"></i></span>
                    Mick appreciated your work.
                    <span class="small italic pull-right"> Today</span>
                </a>
              </li>
              <li>
                <a href="#">See all notifications</a>
              </li>
            </ul>
          </li>

	 */
}

function initializeUserInfo() {
	// This info shouldn't ocme from a rest server it should be injected into the model map!!!!!!!!!!!!!
	var avatar = "";
	var foo;
	var username = "Unknown";

	if ((typeof _igModel === "undefined") || (_igModel == null)) {
		return;
	}
	
	if (_igModel.hasOwnProperty("avatar")) {
		foo = _igModel.avatar;

		if ((foo != null) && (foo != "")) {
			avatar = "<img alt='' src='img/" + foo + "_small.jpg'>";
		}
	}
	
	if (_igModel.hasOwnProperty("username")) {
		foo = _igModel.username;
		
		if ((foo != null) && (foo != "")) {
			username = foo;
		}
	}
	
	$("#igAvatar").html(avatar);
	$("#igUsername").html(username);
//	if  ((igniteUserName == "") || (igniteUserName == null)) {
//		igniteUserName = username;
//	}
	
}

function htmlDecode(input){
	var e = document.createElement('div');

	e.innerHTML = input;
	return e.childNodes[0].nodeValue;
}

function htmlEncode(value) {
    return value.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
}



//////////////////////////////////////////////////
function initializeGenericOrigTable(tableElementName, queryUrl, columnsArray, columnDefsArray, 
         buttonsArray, callbackMethod, completeMethod, pageLength, orderArray) {
	_genericSelectType = "single";

	return _initializeGenericOrigTable(tableElementName, queryUrl, columnsArray, columnDefsArray, 
	                               buttonsArray, callbackMethod, completeMethod, pageLength, orderArray);
}

function _initializeGenericOrigTable(tableElementName, queryUrl, columnsArray, columnDefsArray, 
		                                                         buttonsArray, callbackMethod, completeMethod, pageLength, orderArray) {
	var orderData = [[1, "asc"]];
	var colName = columnsArray.length < 2 ? columnsArray[0].data : columnsArray[1].data;

	if ((pageLength === undefined) || (pageLength == null)) {
		pageLength = 25;
	}
	
	// For tables with three columns order by the first (0) column
	if ((columnsArray != null) && (columnsArray.length <= 3)) {
		orderData = [[0, "asc"]];
	}
	
	if (endsWith(colName, "Date") || 
		endsWith(colName, "DateTime") ||
		endsWith(colName, "Timestamp")) {
		orderData = [[1, "desc"]];
	}

	if ((!(orderArray === undefined)) && (orderArray != null)) {
		orderData = orderArray;
	}

	var tbl = $("#" + tableElementName).DataTable( {
		ajax: {
			url: springUrl(queryUrl),
			cache: false,
			dataSrc: null,
			error: function(jqXHR, textStatus, errorThrown) {
				ajaxError(jqXHR, textStatus, errorThrown);
			},
			complete: function(html) {
				ajaxSuccess(html.status);
				if ((completeMethod) && (completeMethod != null)) {
					completeMethod(this);
				}
			}
		}, 
		select: {
			style: _genericSelectType
		},
		pageLength: pageLength,
		destroy: true,
		autoWidth: true,
		rowId: columnsArray[0].data,
		scrollX: "100%",
		// scrollY: "500px", // hier
		scroller: true,
		fixedHeader: true,
		scrollCollapse: true,
		processing : true,
		lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
		order: orderData,
		dom: "<RlBfr<t>ip>",
		columns: columnsArray,		
		columnDefs: columnDefsArray, 
		buttons: buttonsArray,
		deferRender: true,
		oLanguage: { 
			sSearch: "",
			sLengthMenu: "Show _MENU_&nbsp;"
		},
		colReorder: {
			allowReorder: true
		},
		preDrawCallback: function (settings) {
			pageScrollPos = $('div.dataTables_scrollBody').scrollTop();
		},
		drawCallback: function (settings) {
			if (pageScrollPos != null) {
				$('div.dataTables_scrollBody').scrollTop(pageScrollPos);
			}
		
			$('.dataTables_length button').removeClass("btn-default");			
			$('.dataTables_length label').addClass('length-label');
			$('.dataTables_length select').addClass('form-control input-md length-select');
			$('.dataTables_length select').addClass('btn dt-button btn-secondary');
			$('.dataTables_filter input').addClass('form-control input-sm search-input');
			$('.dataTables_filter input').attr('placeholder', 'filter');
		}
	} );

	$("#" + tableElementName + " tbody").unbind("click");
    $("#" + tableElementName + " tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    } );

	$("#" + tableElementName + " tbody").unbind("dblclick");
	$("#" + tableElementName + " tbody").on('dblclick', 'tr', function (event) {
		if ($(event.target).hasClass("dataTables_empty")) {
			return;
		}

		if (callbackMethod != null) {
			callbackMethod(this);
		}
	} );	
	
	// fix for table header width which are broken on dialog shows 
	window.setTimeout(function() {
		if ((tbl != null) && (tbl.columns != null)) {
			try {
				tbl.columns.adjust();
			} catch (e) {
				// nothing
			}
		}
	}, 200);

	return tbl;
}

// This is a special table which is initialized with JSON data, is NOT pageable 
// and only shows the items as a list
function initializeJsonTable(tableElementName, jsonData, columnsArray, columnDefsArray, 
		                                 buttonsArray, callbackMethod, orderArray) {
	var orderData = [[1, "asc"]];
	var colName = columnsArray.length < 2 ? columnsArray[0].data : columnsArray[1].data;
	
	// For tables with three columns order by the first (0) column
	if ((columnsArray != null) && (columnsArray.length <= 3)) {
		orderData = [[0, "asc"]];
	}
	
	if (endsWith(colName, "Date") || 
		endsWith(colName, "DateTime") ||
		endsWith(colName, "Timestamp")) {
		orderData = [[1, "desc"]];
	}

	if ((!(orderArray === undefined)) && (orderArray != null)) {
		orderData = orderArray;
	}

	var tbl = $("#" + tableElementName).DataTable( {
		data: jsonData,
		select: {
			style: _genericSelectType
		},
		pageLength: -1,
		destroy: true,
		autoWidth: true,
		rowId: columnsArray[0].data,
		scrollX: "100%",
		scrollY: "60dvh",
		scroller: true,
		fixedHeader: true,
		scrollCollapse: true,
		processing : true,
		order: orderData,
		dom: "<RBrf<t>>",
		columns: columnsArray,		
		columnDefs: columnDefsArray,
		buttons: buttonsArray,
		deferRender: true,
		colReorder: {
			allowReorder: false
		},
		oLanguage: { 
			sSearch: "",
			sLengthMenu: "Show _MENU_&nbsp;"
		},
		
		preDrawCallback: function (settings) {
			pageScrollPos = $('div.dataTables_scrollBody').scrollTop();
		},
		drawCallback: function (settings) {
			if (pageScrollPos != null) {
				$('div.dataTables_scrollBody').scrollTop(pageScrollPos);
			}
		
			$('.dataTables_length button').removeClass("btn-default");			
			$('.dataTables_length label').addClass('length-label');
			$('.dataTables_length select').addClass('form-control input-md length-select');
			$('.dataTables_length select').addClass('btn dt-button btn-secondary');
			$('.dataTables_filter input').addClass('form-control input-sm search-input');
			$('.dataTables_filter input').attr('placeholder', 'filter');
		}
	} );

	$("#" + tableElementName + " tbody").unbind("click");
    $("#" + tableElementName + " tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    } );

	$("#" + tableElementName + " tbody").unbind("dblclick");
	$("#" + tableElementName + " tbody").on('dblclick', 'tr', function (event) {
		if ($(event.target).hasClass("dataTables_empty")) {
			return;
		}

		if (callbackMethod != null) {
			callbackMethod(this);
		}
	} );	
	
	// fix for table header width which are broken on dialog shows 
	window.setTimeout(function() {
		if ((tbl != null) && (tbl.columns != null)) {
			try {
				tbl.columns.adjust();
			} catch (e) {
				// nothing
			}
		}
	}, 200);

	return tbl;
}

//////////////////////////////////////////////////

function initializeGenericTable(tableElementName, 
        queryUrl, 
        columnsArray, 
        columnDefsArray, 
        buttonsArray, 
        callbackMethod, 
        completeMethod, 
        pageLength, 
        orderArray,
        tableHeightPixels, 
        showTableInfo, 
        showFilter,
		toolbar ) {
	
_genericSelectType = "single";

return _initializeGenericTable(tableElementName, queryUrl, columnsArray, columnDefsArray, 
           buttonsArray, callbackMethod, completeMethod, pageLength, 
           orderArray, 
           tableHeightPixels,
           showTableInfo,   // true by default
           showFilter,      // true by default
           toolbar      //toolbar is used to give a heading for the grid.  (normaly use tablename for the Toolbar)
          );
}

function _initializeGenericTable(tableElementName, queryUrl, columnsArray, columnDefsArray, 
         buttonsArray, callbackMethod, completeMethod, 
         pageLength, // used to set the table height (takes precedence over tableHeightPixels
         orderArray,  
         tableHeightPixels, //620px by default 
         showTableInfo, // true by default
         showFilter,  // true by default
         toolbar
         ) {
	var orderData = [[1, "asc"]];
	var colName = columnsArray.length < 2 ? columnsArray[0].data : columnsArray[1].data;

	//	tableRender = "<RlBifr<t>>" in orig version  
	
	//		The built-in table control elements in DataTables are:
	//
	//	    l - length changing input control
	//	    f - filtering input
	//	    t - The table!
	//	    i - Table information summary
	//	    p - pagination control
	//	    r - processing display element

	
	if ((showTableInfo === undefined) || (showTableInfo == null)) {
		showTableInfo = true;
	} 
	
	if ((showFilter === undefined) || (showFilter == null)) {
		showFilter = true;
	}

	if ((tableHeightPixels === undefined) || (tableHeightPixels == null)) {
		tableHeightPixels = 1400;  // So we make the table height 1400 pixels
	}

	if (! ((pageLength === undefined) || (pageLength == null))) {
		tableHeightPixels = pageLength * 35;  	// So we make the table height to show the page Length number of columns
												// But all data is loaded, not just page by page
	}
	
	pageLength = 0;
	
	
	
	
	
	if ((toolbar !== undefined) && (toolbar !== null) && ((showTableInfo) || (showFilter))) {	
		if (showTableInfo) {
			tableRender = "<'row'<'col-sm-2'RBi><'col-sm-8'<'"+toolbar+"'>>";
		} else {
			tableRender = "<'row'<'col-sm-2'RB><'col-sm-8'<'"+toolbar+"'>>";
		}
		if (showFilter) {
			tableRender += "'col-sm-1'f<'search-box'i>r><t>>";
		} else {
			tableRender += "><t>>";
		}
	} else if ((toolbar !== undefined) && (toolbar !== null) && ((!showTableInfo) && (!showFilter))) {	
		tableRender = '<RB<"'+toolbar+'">r<t>>';
	} else {
		if (showTableInfo) {
		    tableRender = '<RBi';
		} else {
		    tableRender = '<RB';
		}		
		if (showFilter) {
		    tableRender += 'f<"search-box"i>r<t>>';
		} else {
		    tableRender += 'r<t>>';
		}		
	}
	
//console.log("tableRender:=" + tableRender)

//  Gebruik hierdie as die hierbo probleme gee.
	
//	var tableRender = "<R";
//	if (buttonsArray.length > 0) {
//		tableRender += "B";	
//	}
//	
//	if (showTableInfo){
//		tableRender += "i";
//	}
//
//	if (showFilter){
//		tableRender += "f<"search-box"i>";
//	}
//	
//	// Close our tag
//	tableRender += "r<t>>";	
	
	
	
	
	// For tables with three columns order by the first (0) column
	if ((columnsArray != null) && (columnsArray.length <= 3)) {
		orderData = [[0, "asc"]];
	}
	
	if (endsWith(colName, "Date") || 
		endsWith(colName, "DateTime") ||
		endsWith(colName, "Timestamp")) {
		orderData = [[1, "desc"]];
	}

	if ((!(orderArray === undefined)) && (orderArray != null)) {
		orderData = orderArray;
	}

	var tbl = $("#" + tableElementName).DataTable( {
		ajax: {
			url: springUrl(queryUrl),
			cache: false,
			dataSrc: null,
			error: function(jqXHR, textStatus, errorThrown) {
				ajaxError(jqXHR, textStatus, errorThrown);
			},
			complete: function(html) {
				ajaxSuccess(html.status);
				if ((completeMethod) && (completeMethod != null)) {
					completeMethod(this);
				}
			}
		}, 
		select: {
			style: 'single'  //_genericSelectType //
		},
		paging: pageLength != 0,
		pageLength: pageLength,
		destroy: true,
		autoWidth: true,
		rowId: columnsArray[0].data,
		scrollX: "100%",
		scrollY: (tableHeightPixels / 2.5) + "px",
		scroller: true,
		fixedHeader: true,
		scrollCollapse: true,
		processing : true,
		//lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
		order: orderData,
		dom: tableRender,//"<RlBfr<t>ip>",
		info: false,
		columns: columnsArray,		
		columnDefs: columnDefsArray, 
		buttons: buttonsArray,
		deferRender: true,
		oLanguage: { 
			sSearch: "",
			sLengthMenu: "Show _MENU_&nbsp;"
		},
		colReorder: {
			allowReorder: true
		},
		preDrawCallback: function (settings) {
			pageScrollPos = $('div.dataTables_scrollBody').scrollTop();
		},
		drawCallback: function (settings) {
			if (pageScrollPos != null) {
				$('div.dataTables_scrollBody').scrollTop(pageScrollPos);
			}
		
			$('.dataTables_length button').removeClass("btn-default");			
			$('.dataTables_length label').addClass('length-label');
			$('.dataTables_length select').addClass('form-control input-md length-select');
			$('.dataTables_length select').addClass('btn dt-button btn-secondary');
			$('.dataTables_filter input').addClass('form-control input-sm search-input');
			$('.dataTables_filter input').attr('placeholder', 'filter');
		},
	} );

	$("#" + tableElementName + " tbody").unbind("click");
    $("#" + tableElementName + " tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    } );

	$("#" + tableElementName + " tbody").unbind("dblclick");
	$("#" + tableElementName + " tbody").on('dblclick', 'tr', function (event) {
		if ($(event.target).hasClass("dataTables_empty")) {
			return;
		}

		if (callbackMethod != null) {
			callbackMethod(this);
		}
	} );	

	// fix for table header width which are broken on dialog shows 
	window.setTimeout(function() {
		if ((tbl != null) && (tbl.columns != null)) {
			try {
				tbl.columns.adjust();
			} catch (e) {
				// nothing
			}
		}
	}, 200);

	$('#' + tableElementName + '_filter input').focus();

	return tbl;
} // _initializeGenericTable -- END




function saveEntry(postUrl, postData, dialogId, successMessage, table, callbackMethod) {
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
			hidePleaseWait();
			if ((table !== undefined) && (table != null)) {
				// I want to deselect any records when table is reloaded
				try {
					table.ajax.reload();
				}  catch(error) {
					console.log("Warning: " + error);
				}
				// console.log(table + "refreshed");
			}

			if (dialogId != null) {
				$("#" + dialogId).modal("hide");
				if ($("#" + dialogId).data("saveCallbackMethod")) {
					// we have a saveCallbackMethod
					var saveCallbackMethod = $("#" + dialogId).data("saveCallbackMethod");
					// Execute the callback method, passing in the object of data;
					saveCallbackMethod(response);
				}
			}
			
			if (callbackMethod != null) {
				callbackMethod(this, response);
			}
			
			if (successMessage != null) {
				showToast("Success", successMessage, DialogConstants.ALERTTYPE_SUCCESS);
			}
 		},
		error: function(jqXHR, textStatus, errorThrown) {
			hidePleaseWait();
			
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
			hidePleaseWait();
			ajaxSuccess(html.status);
		}
	});
}


// Decide whether or not to show errors, and show them if there are errors
function showFormErrors(elementId, errors) {
	if (errors === undefined) {
		errors = null;
	}
	var hasErrors = ((errors != null) && (errors != ""));

	var html = "<div class='alert alert-danger' role='alert'>" +
	           "	<table width='100%'>" +
               "		<tr>" +
               "            <td width='50px' valign='top'><i class='fas fa-exclamation-triangle' style='font-size:28pt !important'></i></td>" +
               "            <td id='" + elementId + "_formErrors' valign='top' width='*'>" +
               "                " + (errors == null ? "" : errors) +
               "            </td>" +
               "        </tr>" +
               "    </table>" +
               "</div>";
	
	$("#" + elementId).html(errors == null ? "" : html);
	setDivVisibility(elementId, hasErrors ? "block" : "none");

	return hasErrors;
}

function showFormSuccess(elementId, message) {
	if ((message == null) || (message == "")) {
		setDivVisibility(elementId, "none");
		return false;
	}

	var html = "<div class='alert alert-success' role='alert'>" +
	           "	<table width='100%'>" +
               "		<tr>" +
               "            <td width='50px' valign='top'><i class='fas fa-check-circle' style='font-size:28pt !important'></i></td>" +
               "            <td valign='top' width='*'>" +
               "                " + message +
               "            </td>" +
               "        </tr>" +
               "    </table>" +
               "</div>";
	
	$("#" + elementId).html(html);
	setDivVisibility(elementId, "block");

	return true;
}

function setActiveTab(tabId) {
	if ((tabId != null) && (tabId != "")) {
		$("#" + tabId).trigger("click");
	}
}

function showSubmenuOption() {
	var currentUrl = window.location.href;
	var elements = currentUrl.split("/");
	var lastElement = elements[elements.length - 1];
	
	if ((lastElement != null) && (lastElement != "")) {
		var findSpec = "a[href=\"/" + springUrl(lastElement) + "\"]";
		var thisEl = $("#sidebarMenu").find(findSpec).parent(); // we want the links container
		var closest = $("#sidebarMenu").find(findSpec).closest("ul");
		
		if (closest.parent().closest("ul").selector.indexOf("sidebarMenu") > 0) {
			// show parent submenu
			closest.parent().closest("ul").addClass("show");
		}
		
		// show our submenu
		closest.addClass("show");
		
		// highlight our current optoin
		thisEl.css("background-color", "#1a2732");
	}
}



function showModalDialog(dialogId) {
	
	var isAlreadyVisible = $('#' + dialogId).is(':visible');
	if (isAlreadyVisible) {
		console.log("returning because its already visible");
		return;
	}
	
	// reset dialogs position to screen center
	$("#" + dialogId).css({
        top: 0,
        left: 0
      });
      
//    // save the callback method
//    if ((saveCallbackMethod !== undefined) && (saveCallbackMethod != null)) {
//		console.log("storing callback method");
//    	$("#" + dialogId).data("saveCallbackMethod", saveCallbackMethod);
//	}

    // show the dialog
	$("#" + dialogId).modal({backdrop: false, show: true});

	// make it draggable
	$("#" + dialogId).draggable({
    	handle: ".modal-header"
	});
	
}

function clearDialog(dialogId) {
    $("#" + dialogId).find(":input").each(function() {
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		
		if (type == "text") {
			this.value = "";
		}
		
		if (this.tag == "select") {
			this.value = "";
		}
    });
};

function emptySelect(elementId) {
	var selectElement = $("#" + elementId);

	selectElement.empty();
}

/* function populateSelect(
					elementId, html select element that will be populated 
					url, url where it must get the data (you can paste in browser window to see the data)
					idField,  name of the field in the result from url - java object field - variable, that must be used as the value of the select
					displayField,  name of the field in the result from url - java object field - variable, that must be displayed to the user
					selectedId, variable of the value that must be selected (null or default when new record)  or current value  
					addEmpty, boolean: should we add empty object at the top - normally set to true, but validation will check if we do need a selection here
					completeMethod, javascript method that is called when the person selects something e.g. When selecting a bank, populate it's branches
					existingData, the existing data to be inspected
					compareField, the field to test which will result in values not being added if they already exist
					compareMethod, a method to be called which returns true or false to perform the comparison on more complex matches
					
			)
*/
function populateSelect(elementId, url, idField, displayField, selectedId, 
						addEmpty, completeMethod, existingData, compareField, compareMethod) {
	var emptyLabel = "";
	
	if ((addEmpty === undefined) || (addEmpty == null)) {
		addEmpty = true;
	}
	
	if ((typeof addEmpty === "string") || (addEmpty instanceof String)) {
		emptyLabel = addEmpty;
		addEmpty = true;
	}
	
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			var selectElement = $("#" + elementId);
			var selected = "";
			var optionData = [];

			selectElement.empty();

			if (selectedId == null) {
				selected = " selected";
			}
			
			if (addEmpty) {
				selectElement.append("<option value=''" + selected + ">" + emptyLabel + "</option>");
			}

//			data = data.sortBy(sortField); //Lyk nie of dit werk nie.

			for (var foo = 0; foo < data.length; foo++) {
				var id = data[foo][idField];
				var title = data[foo][displayField];
				var selected = "";
				var add = true;
				
				//inspect our existing data and if we already have this value, do not add it
				if ((existingData != undefined) && (existingData != null)) {
					//loop through our data 
					for (var bar = 0; bar < existingData.length; bar++) {
						var efValue = existingData[bar][compareField];
						
						if (efValue == title) {
							add = false;
							break;
						}
					}
				}
				
				if ((compareMethod != undefined) && (compareMethod != null)) {
					add = compareMethod(data[foo]);
				}
				
				if (!add) {
					continue;
				}

				if ((selectedId != null) && (id == selectedId)) {
					selected = " selected ";
				}

				optionData.push({
					id: id,
					title: title
				});
				
				var option = "<option " +
				             "value='" + id + "'" + 
				             "title='" + title+ "'" + 
				             selected +  
				             ">" +
						     title + "</option>";
				selectElement.append(option);
			}

			if ((!(completeMethod === undefined)) && (completeMethod != null)) {
				completeMethod(optionData);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function dynamicSelectOneDialogExecuteCallback() {
	var selected = $("#" + dynamicSelectOneDialog + "Select").val();
	
	dynamicSelectOneDialogCallbackMethod(selected);
}



function showSelectOneDialog(elementId, options, callbackMethod) {
	var el = document.getElementById(dynamicSelectOneDialog);
	var currentValue = null;
	
	if (elementId != null) {
		currentValue = $("#" + elementId).val();
	}
	
	// save our callback method
	dynamicSelectOneDialogCallbackMethod = callbackMethod;
	
	// If the modal dialog already exists remove it so that we can rebuild it for this array
	if ((el != null) && (el.parentNode != null)) {
		el.parentNode.removeChild(el);
	}
	
	// dialog header
    var html = '<div class="modal" id="' + dynamicSelectOneDialog + '" data-backdrop="static" data-keyboard="false" role="dialog">\
        <div class="modal-dialog" style="margin-top: 25% !important">\
            <div class="modal-content">\
                <div class="modal-header alert-info">\
                    <h4 class="modal-title">Select...</h4>\
                </div>\
                <div class="modal-body">';

    // build and add our select
	html += "<div class='row'>" +
	        "  <div class='col-md-12'>" +
	        "    <select id='" + dynamicSelectOneDialog + "Select' class='form-control'>";

	for (var foo = 0; foo < options.length; foo++) {
		var sel = "";
		var value = options[foo];
		var text = options[foo];
		
		if ((value != null) && (typeof value === "object")) {
			value = options[foo].value;
			text = options[foo].text;
		}
		
		if (value == currentValue) {
			sel = " selected ";
		}
		
		html += "    <option value='" + value + "'" + sel + ">" + text + "</option>";
	}
	html += "    </select>" +
	        "  </div>" + 
	        "</div>";
	
	// dialog footer
	html += '                </div>\
		                     <div class="modal-footer">\
                                 <button type="button" class="btn btn-sm btn-primary" onclick="dynamicSelectOneDialogExecuteCallback()" data-dismiss="modal">Ok</button>\
                                 <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Cancel</button>\
	                         </div>\
                         </div>\
                     </div>\
                 </div>';
	
	// show dialog
	$(document.body).append(html);
    showModalDialog(dynamicSelectOneDialog);
} // showSelectOneDialog -- END






//function quickAddDialog(qaDialog, qaReturnId, qaReturnName, qaExtraParam) {
//	showModalDialog(qaDialog, function(){
//		console.log("refreshed");
//		dynamicSelectGridTable.ajax.reload();
//	 }, 
//	 qaReturnId, qaReturnName, qaExtraParam);
//}



//qaDialog (quick-add-dialog, name of dialog to load, if user is allowed to add a record)
//qaReturnId (the Id field, to which a value should be returned)
//qaReturnName (the Name field, to which a value should be returned))
//
//The quick-add-dialog must have the following three hidden fields:
// <span style="display: inline-block;font-size: 90%; background-color: #ffffff">&nbsp;qaReturnId</span><input readonly id="qaReturnId" style="background-color: #ffff99 !important; color:Black; width: 250px; height: 20px;">
// <span style="display: inline-block;font-size: 90%; background-color: #ffffff">&nbsp;qaReturnName</span><input readonly id="qaReturnName" style="background-color: #ffff99 !important; color:Black; width: 250px; height: 20px;">
// <span style="display: inline-block;font-size: 90%; background-color: #ffffff">&nbsp;qaExtraParam</span><input readonly id="qaExtraParam" style="background-color: #ffff99 !important; color:Black; width: 20px; height: 20px;">


function selectFromGridDialog(columnName, referencedColumnName, columns, columnDefs, data,
							 callbackMethod) {  // , qaDialog, qaReturnId, qaReturnName, qaExtraParam, qaBeforeMethod) {
	var el = document.getElementById(dynamicSelectOneDialog);
	var elementId = columnName;
	var currentValue = $("#" + elementId).val();
	var buttonsArray = [];

//	// If they have passed in a quick add dialog...
//	if (qaDialog != null) {
//		// create a plus button to add
//		buttonsArray = [
//			{
//				titleAttr: "Add",
//				text: "<i class='fas fa-plus'></i>",
//				className: "btn btn-sm",
//				action: function( e, dt, node, config ) {
//					// Here we hide the SelectFromGridDialog and show the quick add dialog that they supplied
//					if ((qaBeforeMethod !== undefined) && (qaBeforeMethod !== null)) {
//						qaBeforeMethod();
//					}
//					console.log("hiding " + dynamicSelectGridDialog);
//					quickAddDialog(qaDialog, qaReturnId, qaReturnName, qaExtraParam);
//					$("#" + dynamicSelectGridDialog).modal("hide");
//					$("#" + qaDialog).on("hidden.bs.modal", function() {
//						$("#" + dynamicSelectGridDialog).modal("show");
//					});
//				}
//			}
//		];
//		
//	}
	
	// save our callback method
    dynamicSelectGridCallbackMethod = callbackMethod; 
      
	// If the modal dialog already exists remove it so that we can rebuild it for this array
	if ((el != null) && (el.parentNode != null)) {
		el.parentNode.removeChild(el);
	}
	
	if (dynamicSelectGridTable != null) {
		// if we dont destroy the table we land up with two selection rows
		dynamicSelectGridTable
				    .rows(".selected")
				    .nodes()
				    .to$() 
				    .removeClass("selected");
	}
	
	var dynamicSelectGridTableId = dynamicSelectGridDialog + "_table";
	
	// delete previous version
	if ($("#" + dynamicSelectGridDialog).length) {
		$("#" + dynamicSelectGridDialog).remove();
	}
	
	// dialog header
    var html = '<div class="modal" id="' + dynamicSelectGridDialog + '" data-backdrop="static" data-keyboard="false" role="dialog">\
        <div class="modal-dialog modal-lg">\
            <div class="modal-content">\
                <div class="modal-header alert-info">\
                    <h4 class="modal-title">Select...</h4>\
                </div>\
                <div class="modal-body fixed-dialog-body">\
    				<div class="row">\
    					<div class="col-md-12">\
    						<table width="100%" id="' + dynamicSelectGridTableId + '" class="table table-striped table-advance table-hover">\
    							<thead>';

	var colIndex = -1;

	// Header row
	html += "<tr>";
	
    for (var foo = 0; foo < columns.length; foo++) {
    	var colData = columns[foo].data;
    	var colTitle = columns[foo].name;
  
    	
    	if (colData == referencedColumnName) {
    		colIndex = foo;
    	}
    	
    	html += "<th>" + colTitle +"</th>"; 
    }
    html += "</tr>" +
            "</thead>";

	// dialog footer
	html += '<tbody></tbody>\
		                                </table>\
		                            </div>\
                                </div>\
		                    </div>\
		                     <div class="modal-footer fixed-dialog-footer">\
                                 <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Cancel</button>';
    
    html += '                    <button id="dynamicSelectOneDialogSelectBtn" \
	                                     type="button" class="btn btn-sm btn-primary" \
	                                     onclick="dynamicSelectOneDialogSelect(\'' + columnName + '\', \'' + referencedColumnName + '\', ' + colIndex + ')" \
	                                     data-dismiss="modal">Select</button>\
	                         </div>\
                         </div>\
                     </div>\
                 </div>';
	
	// show dialog
	$(document.body).append(html);
    showModalDialog(dynamicSelectGridDialog);
    
	dynamicSelectGridTable = $("#" + dynamicSelectGridTableId).DataTable({
		select: {
			style: "single"
		},
		data: data,
		columns: columns,
		columnDefs : columnDefs,
		pageLength: 385,
		destroy: true,
		autoWidth: true,
		rowId: "name",
		scrollY: "60em", // Ingrid made the change to have a bigger selection table
		scrollX: "100%",
		scroller: false,
		fixedHeader: true,
		scrollCollapse: true,
		processing : true,
		//lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
		order: [[1, "asc"]],
		dom: "<RBf<t>i>",
		buttons: buttonsArray,
		deferRender: true,
		oLanguage: { 
			sSearch: "",
			sLengthMenu: "Show _MENU_&nbsp;"
		},
		colReorder: {
			allowReorder: true
		},
		preDrawCallback: function (settings) {
			pageScrollPos = $('div.dataTables_scrollBody').scrollTop();
		},
		drawCallback: function (settings) {
			if (pageScrollPos != null) {
				$('div.dataTables_scrollBody').scrollTop(pageScrollPos);
			}
		
			$('.dataTables_length button').removeClass("btn-default");			
			$('.dataTables_length label').addClass('length-label');
			$('.dataTables_length select').addClass('form-control input-md length-select');
			$('.dataTables_length select').addClass('btn dt-button btn-secondary');
			
			$('.dataTables_filter input').addClass('form-control input-sm search-input');
			$('.dataTables_filter input').attr('placeholder', 'filter');
			$('.dataTables_filter input').focus();
		}
	});

	//dynamicSelectGridTable.unbind("dblclick");
	dynamicSelectGridTable.on('dblclick', 'tr', function (event) {
		event.stopPropagation();
	    var row = dynamicSelectGridTable.rows(this).data()[0];

	    $("#" + dynamicSelectGridDialog).modal("hide");
		
	    // call select button...
	    if (dynamicSelectGridCallbackMethod != null) {
	    	dynamicSelectGridCallbackMethod(row);
	    }
	} );	

	dynamicSelectGridTable.on( 'select', function ( e, dt, type, indexes ) {
		enableDisableDynamicSelectButton();
	} );
	
	dynamicSelectGridTable.on( 'deselect', function ( e, dt, type, indexes ) {
		enableDisableDynamicSelectButton();
	} );

	enableDisableDynamicSelectButton();
	
	
//	console.log(html)
}  // selectFromGridDialog  --  END




function enableDisableDynamicSelectButton() {
	var selectedCount = dynamicSelectGridTable.rows('.selected').data().length;
	
	setElementEnabled("dynamicSelectOneDialogSelectBtn", selectedCount == 1);
}

function setElementEnabled(elementId, enabled) {
	var el=document.getElementById(elementId);
	
	if (enabled) {
		$("#"+elementId).removeClass("disabled");
		$("#"+elementId).prop("disabled", false);
		$("#"+elementId).attr("disabled", "disabled");
	} else {
		$("#"+elementId).addClass("disabled");
		$("#"+elementId).prop("disabled", true);
		$("#"+elementId).removeAttr("disabled");
	} 
	if (el != null) {
		el.disabled = !enabled;
	}
}

function columnNameToTitle(name) {
	// Make camelcase words
	name = name.replace( /([A-Z])/g, " $1" );
	
	// Uppercase the first letter
	name = name.charAt(0).toUpperCase() + name.slice(1);
	
	// Replace double underscores with a spaced hyphen
	name = name.replace(/__/g, " - ");
	
	// Replace underscores with spaces 
	name = name.replace(/_/g, " ");
	
	var newName = "";
	var twoAgo = null;
	for (var foo = 0; foo < name.length; foo++) {
		if (foo > 2) {
			twoAgo = name.charAt(foo - 2);
		}
		
		var ch = name.charAt(foo);
		if (ch != " ") {
			newName = newName + ch;
		} else {
			if ((twoAgo != null) && (twoAgo != " ")) {
				var lastCh = newName.charAt(newName.length - 1);
				if (lastCh != " ") {
					newName = newName + ch;
				}
			}
		}
	}
	name = newName;
	
	return name;
}

function dateToYmdString(str) {             //J// this is not used anywhere.
	var result = "";
	
	if (str == null) {
		return result;
	}
	
	if (typeof str == "object") {
		// This is a date... covert it to a dmy string
		var d = str.getDate();
		var m = str.getMonth() + 1;
		var y = str.getFullYear();
		
		str = d + "/" + m + "/" + y;
	}
	
	if ((str != null) && (str != "")) {
		if (str.indexOf("/") > -1) {
			var strParts = str.split("/");
			if (strParts.length == 3) {
				var d = strParts[0];
				var m = strParts[1];
				var y = strParts[2];
				
				result += y;

				if (m.length < 2) { result += "0"; }
				result += m;

				if (d.length < 2) { result += "0"; }
				result += d;

			} 
		}
	}
	
	return result;
}

function toggleSidebar() {
	//  I put this on the "Toggle Navigation" div:   onclick="toggleSidebar()"> in the about page
	$("#sidebar").toggle();
	var visible = $("#sidebar").is(":visible");
	
	$("#main-content").removeAttr("style");
	window.setTimeout(function() {
		$("#main-content").css({ "margin-left" :  visible ? "180px" : "10px" });
	}, 40);
}

function initializeMenuStructure() {
	var data = null;
	var parentMenuId = null;
	
	// if we didn't get a menu from the backend...
	if (typeof _menuData == "undefined") {
		// ... return
		return;
	}

	data = JSON.parse(htmlDecode(_menuData));
	
	var menuHtml = "<ul class='sidebar-menu' style='display:block'><li>";
	var previousMenuParentId = null;
	var subMenuLevel = 0;
	for (var foo = 0; foo < data.length; foo++) {
		var obj = data[foo];
		var iconHtml = "";

		// if our parentId is different close this menu...
		if (previousMenuParentId != obj.parentUiComponentId) {
			menuHtml += "</ul></li></li>";
			previousMenuParentId = obj.parentUiComponentId;  // indicate that we've closed
			subMenuLevel--;
		}
		
		if ((obj.iconClassName != null) && (obj.iconClassName != "")) {
			iconHtml = "<span class='menuSpan'><i class='menuIcon " + obj.iconClassName + "'></i></span>";
		}
		
		if ((obj.uiComponentLink == null) || (obj.uiComponentLink == "")) {
			// Add a submenu			
			menuHtml += "<li class='sub-menu'><a href='javascript:;' class='menuLink'>" +
			            iconHtml + "<span>" + obj.uiComponentTitle + "</span>" +
			            "<span class='menu-arrow arrow_carrot-right'></span>" +
			            "</a>" +
			            "<ul class='sub' style='display: none'>";
						
			previousMenuParentId = obj.uiComponentId;
			subMenuLevel++;
		} else {
			menuHtml += "<li><a href='" + obj.uiComponentLink + "'>" + iconHtml + obj.uiComponentTitle + "</a></li>";
		}
	}
	menuHtml += "</ul>";
	
	$("#sidebarMenu").html(menuHtml);
	
	// Copied out of scripts.js because we need to reattach the functionality of opening and closing menus
	jQuery('#sidebar .sub-menu > a').click(function () {
        var last = jQuery('.sub-menu.open', jQuery('#sidebar'));        
        jQuery('.sub', last).slideUp(200);
        
        var link = jQuery(this);
        var sub = link.next();
        
        if (sub.is(":visible")) {
        	link.find(".menu-arrow").removeClass('arrow_carrot-down');
        	link.find(".menu-arrow").addClass('arrow_carrot-right');
        	sub.slideUp(200);
        } else {
        	link.find(".menu-arrow").removeClass('arrow_carrot-right');
        	link.find(".menu-arrow").addClass('arrow_carrot-down');
            
        	sub.slideDown(200);
        }
        var o = (jQuery(this).offset());
        diff = 200 - o.top;
        if(diff>0)
            jQuery("#sidebar").scrollTo("-="+Math.abs(diff),500);
        else
            jQuery("#sidebar").scrollTo("+="+Math.abs(diff),500);
    });	

	window.setTimeout(function() {
		showSubmenuOption();
	}, 200);
}

function updateApplicationVersion() {
	var url = "/rest/ignite/v1/version";
	
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			var html = "";
			
			html = "<a th:href='" + springUrl("/") + "' class='logo'>Ignite <span class='lite'>Admin</span>";
			
			if ((data != null) && (data.hasOwnProperty("version")) && (data.version != "")) {
				html += "<span class='lite-fade'> - " + data.version + "</span>";
			}
			html += "</a>";

			$("#headerLogo").html(html);
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});
}

/**
 * Hides "Please wait" overlay. See function showPleaseWait().
 *
 * Source: https://gist.github.com/Ravaelles/0507c53597c8a82168eb
 */
function hidePleaseWait() {
	var pleaseWaitDialog = $("#___pleaseWaitDialog");
	
	if (pleaseWaitDialog != null) {
		pleaseWaitDialog.modal("hide");
	}
}

/**
 * Displays overlay with "Please wait" text. Based on bootstrap modal. Contains animated progress bar.
 *
 * Source: https://gist.github.com/Ravaelles/0507c53597c8a82168eb
 */
function showPleaseWait() {
    var html = '<div class="modal" id="___pleaseWaitDialog" data-backdrop="static" data-keyboard="false" role="dialog">\
        <div class="modal-dialog" style="margin-top: 25% !important">\
            <div class="modal-content">\
                <div class="modal-header">\
                    <h4 class="modal-title">Please wait...</h4>\
                </div>\
                <div class="modal-body">\
                    <div class="progress">\
                      <div class="progress-bar progress-bar-info progress-bar-striped progress-bar-animated " role="progressbar"\
                      aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width:100%; height: 40px">\
                      </div>\
                    </div>\
                </div>\
            </div>\
        </div>\
    </div>';
    
	$(document.body).append(html);
    showModalDialog("___pleaseWaitDialog");
}

var randFormatter = new Intl.NumberFormat([], {
style:"currency",
currency: "ZAR",
currencyDisplay: "narrowSymbol"
});



function valueToRand(value) {
var result = "";
 
	if ((value != null) && (value !="")) {
	// 2 decimal places...
	value = value.toFixed(2);
     
	// to a ran string, with 1 000's spaced, and 2 decimal places
	result =  value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
     
	// add the symbol
	result = "R " + result;
	}
 
	return result;
}



function valueToPercentage(value) {
var result = "";
 
	if ((value != null) && (value !="")) {

	// add the symbol
	result = value + "%";
	}
 
	return result;
}

 function isNumber(n) {
	var result = false;
	
	try {
	var tmp = Number(n);
	if (!isNaN(tmp)) {
		result = true;
	}
	} catch (err) {
		result = false;
	}
	return result;
}

function setInputDatePicker(inputId, object, field){
	var date = null;
	if ((object.hasOwnProperty(field)) && (object[field] != null)) {
		date = new Date(object[field]);
		$("#" + inputId).datepicker("setDate", date);
	} else {
		$("#" + inputId).datepicker.val("");
	}
	//$("#" + inputId).datepicker("setDate", date);
}

//***********************************************************************************************************
//** Toasts
//***********************************************************************************************************

function hideToast() {
	$("#gdiToastPanel").fadeOut(500);

	// after its faded out, get rid of it
	window.setTimeout(function() {
		$("#gdiToastPanel").className = "";
		$("#gdiToastPanel").remove();
	}, 500);
}

function showSuccessToast(message) {
	showToast("Success", message, DialogConstants.ALERTTYPE_SUCCESS);
}

// Use the DialogConstants.ALERTTYPE_* constants for the toast type
function showToast(title, message, toastType) {
	if (toastType === undefined) {
		toastType = "";
	} else {
		toastType = "alert-" + toastType;
	}

	var toastHtml = "<div id='gdiToastPanel' class='toast fade show'>\
					   <div class='toast-header " + toastType + "'>\
					   	 <span class='toast-title'>" + title + "</span>\
					     <button type='button' class='close' onclick='hideToast()'>\
						   <span aria-hidden='true'><i class='fas fa-times'></i></span><span class='sr-only'>Close</span>\
						 </button>\
					  </div>\
					  <div class='toast-body'>" + message + "</div>\
					</div>";
	
	// get rid of any previous toasts...
	$("#gdiToastPanel").remove();
	
	// Add our toast
	$(document.body).append(toastHtml);
	
	// Show our toast
	window.setTimeout(function() {
		$("#gdiToastPanel").className = "show";
		
		// Hide after a bit...
		window.setTimeout(function() {
			hideToast();
		}, 2500);  // * 1000 = seconds
		
	}, 200);
}

function setTableButtonState(table, buttonId, enable) {
	if (table == null) {
		console.log("table is null... returning");
		return;
	}
	
	try {
		var button = null;
		var btns = table.buttons();
		
		for (var foo = 0; foo < btns.length; foo++) {
			var btn = btns[foo];

			if ((btn.node.id !== undefined) && (btn.node.id == buttonId)) {
				button = table.button(foo);
				break;
			}
		}

		if (enable) {
			if (button != null) {
				button.enable();
			}
			
			$("#" + buttonId).removeClass("disabled");
		} else {
			if (button != null) {
				button.disable();
			}
			$("#" + buttonId).addClass("disabled");
		}
	} catch (e) {
		// nothing
	}
}

function clearTextSelection() {
	if (window.getSelection) {
		if (window.getSelection().empty) {  // Chrome
			window.getSelection().empty();
		} else if (window.getSelection().removeAllRanges) {  // Firefox
			window.getSelection().removeAllRanges();
		}
	} else if (document.selection) {  // IE?
		document.selection.empty();
	}
}

function addUrlParameter(url, parameterName, value) {
	url += url.indexOf("?") == -1 ? "?" : "&";
	url += parameterName + "=" + value;	
	
	return url;
}

function closeModalDialog(dialogId) {
	$("#"+ dialogId).modal("hide");
}


function selectRowThatWasEdited(table, id){
    // deselect everything.
    table.rows().deselect();
   
    // select columns that have the ID in the first column
    var indexes = table.rows().eq( 0 ).filter( function (rowIdx) {
         // check the data in column 0 (note 0 offset column count)
    	var selected = table.cell( rowIdx, 0 ).data() == id ? true : false;
    	
    	if (selected) {
    		table.rows(rowIdx).select();
    	}
        return selected;
         //console.log (table.cell(rowIdx,0));
    } );
    //console.dir(indexes);
    // Add a class to those rows using an index selector
    /*
    table.rows( indexes )
       .nodes()
       .to$()
       .addClass( 'selected' );
      */
}


function initializeDynamicInsertUpdate(elementId, dialogId, fetchUrl, keyField, displayField, insertMethodName, editMethodName) {
	var template = "<span class='input-group-addon pointer'" +
					" onclick='##method##'><i class='fas fa-##icon##'></i></span>";

	var methodParameters = "\"" + dialogId + "\", " +
	                       "\"" + elementId + "\", " +
	                       "\"" + fetchUrl + "\", " +
	                       "\"" + keyField + "\", " +
	                       "\"" + displayField + "\"";

	if (editMethodName != null) {
		var btnHtml = template;
		
		btnHtml = btnHtml.replace("##method##", editMethodName + "(" + methodParameters + ")");		
		btnHtml = btnHtml.replace("##icon##", "edit");
		$("#" + elementId).after(btnHtml);
	}

	if (insertMethodName != null) {
		var btnHtml = template;
		
		btnHtml = btnHtml.replace("##method##", insertMethodName + "(" + methodParameters + ")");
		btnHtml = btnHtml.replace("##icon##", "plus");
		$("#" + elementId).after(btnHtml);
	}
}




function onlyNumberAndDotKey(evt) {  // 0.00
    
    // Only ASCII character in that range allowed	   add to the input:   onkeypress="return onlyNumberAndDotKey(event)"	net integers en 'n punt
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode

    if ((ASCIICode > 31) && (ASCIICode != 46) && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
};


function onlyNumberAndDotKeyAndMinus(evt) {  // 0.00
    
    // Only ASCII character in that range allowed	   add to the input:   onkeypress="return onlyNumberAndDotKeyAndMinus(event)"	net integers en 'n punt en minus
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode

    if ((ASCIICode > 31) && (ASCIICode != 45) && (ASCIICode != 46) && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
};





function formatMoney(number, decPlaces, decSep, thouSep) {
    decPlaces = isNaN(decPlaces = Math.abs(decPlaces)) ? 2 : decPlaces,
    decSep = typeof decSep === "undefined" ? "." : decSep;
    thouSep = typeof thouSep === "undefined" ? " " : thouSep; 
    var sign = number < 0 ? "-R " : "R ";
    var i = String(parseInt(number = Math.abs(Number(number) || 0).toFixed(decPlaces)));
    var j = (j = i.length) > 3 ? j % 3 : 0;

    return sign + 
        (j ? i.substr(0, j) + thouSep : "") +
        i.substr(j).replace(/(\decSep{3})(?=\decSep)/g, "$1" + thouSep) +
        (decPlaces ? decSep + Math.abs(number - i).toFixed(decPlaces).slice(2) : "");
}



//hierdie een werk beter: R2 000 000.00   (vs R2 000000.00)
function formatDollar(num) {                     
    var p = num.toFixed(2).split(".");
    return "R" + p[0].split("").reverse().reduce(function(acc, num, i, orig) {
        return num + (num != "-" && i && !(i % 3) ? " " : "") + acc;
    }, "") + "." + p[1];
}

function onlyNumberKey(evt) { //  0123456789
    
    // Only ASCII character in that range allowed       add to the input:   onkeypress="return onlyNumberKey(event)"        net integers
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
}

function getTodaysDate() {
    var d = new Date();

    var month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();
    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;
    return [year, month, day].join('-');				
}

function getEarlierDate(NrDays) { // Get NrDays before today
    var d = new Date();
    d.setDate(d.getDate() - NrDays);	
    var month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();
    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;
    return [year, month, day].join('-');				
}

function getPreviousSunday(fromDate) {
	var date = new Date(fromDate);
	var day = date.getDay();
	var prevSunday = new Date(date.getTime());
	if(date.getDay() == 0) { //Sunday
		prevSunday.setDate(date.getDate());
	} else {
		prevSunday.setDate(date.getDate() - (day));
	}
	return prevSunday;
}

function nrToCurrency(dieNommer) {
	
	if ((dieNommer == null) || (dieNommer == "")) {
		dieNommer = 0;
	}
	result = dieNommer.toLocaleString('en-ZA', { style: 'currency', currency: 'ZAR' })

	result2 =  result.padStart(16, '.')			
			
//console.log(result)	
//console.log(result2)	

	return result2;
}

function setElementVisibility(id, visible) {
	var e = document.getElementById(id);

	if (e != null) {
		if (visible) {
			e.removeAttribute("hidden");
		} else {
			e.setAttribute("hidden","hidden");
		}
	}
}

function processElements(elementType) {
	var igDevKey = "__igdev_";
	$(elementType).each(function() {
		var id = this.id;
//		console.log("   -> " + id);
		if (id.startsWith(igDevKey)) {
//			console.log(" (igniteUserName == \"Johannes Marais\")  -> " + (igniteUserName == "Johannes Marais"));
			setElementVisibility(id, seeFormName) ;    // (seeFormName && (igniteUserName == "Johannes Marais")));
		}
	});
}


function showIgDeveloperOption() {
//	console.log(":::::" + seeFormName);
	
	processElements("input");
	processElements("span");
	processElements("h5"); //deprecated, rather put elements inside of a span
}



//***********************************************************************

$(document).ready(function() {
	$(".modal").on('shown.bs.modal', function () {
	    $(this).find("input:visible:first").focus();
	});
	
	updateApplicationVersion();
	initializeUserInfo();
	initializeMenuStructure();
	initializeNotificationBars();
} );
