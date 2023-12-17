var existingData = [];
var timesheetSummaryTable = null;
var timesheetTable = null;
var tRatesTable = null;
var participantIdSelected = null;
var resourceId = null;

var somethingChangedInDialog = null;
var askToSaveTab = null; //Only ask once for every tab change if the current tab's data changed
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed
var currentTab = null;

var newDateRangeSelected = false;
const now = new Date();
const firstDay = new Date(now.getFullYear(), now.getMonth(), 1); //Current month's first day
const lastDay = new Date(now.getFullYear(), now.getMonth() + 1, 0); //Current month's last day
var isUpdatingTime = false;


//*********** initializeVehicleTripsTable*********** //
function initializeVehicleTripsTable() {
	var columnsArray = [
		{ data: "tripLoggerId" }, 	 				//0
		{ data: "projectId" },						//1
		{ data: "tripDate" },						//2
		{ data: "tripStartTime" },					//3
		{ data: "vehicleName" },					//4
		{ data: "licenceNumber" },					//5
		{ data: "projectNameText" },				//6
		{ data: "behalfOf" },						//7
		{ data: "driver" },							//8
		{ data: "tripEndPointName" },				//9
		{ data: "tripDistance" },					//10
		{ data: "travelTimeMinutes" },				//11
		{ data: "instructedBy" },					//12
		{ data: "tripStartingPointName" },			//13
		{ data: "vehicleId" },						//14
		{ data: "description" },					//15
		{ data: "modelName" },						//16
		{ data: "makeName" },						//17
		{ data: "tripOrderDate" },					//18
		{ data: "tripEndTime" },					//19
		{ data: "tripStartCoordinates" },			//20
		{ data: "tripEndCoordinates" },				//21
		{ data: "ownerId" },						//22
		{ data: "ownerName" },						//23 
		{ data: "participantIdDriver" },			//24
		{ data: "participantIdGaveInstruction" },	//25
		{ data: "participantIdOnBehalfOf" },		//26
		{ data: "odometerReading" },				//27
		{ data: "tripStartKm" },					//28
		{ data: "tripEndKm" }						//29
	]

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
		},
		{
			render: function(data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [2, 16]
		},
		{
			render: function(data, type, row) {
				var formattedTripStartTime = "";
				if (type === "display" && data) {
					var tripStartTime = new Date(data);
					var hours = ("0" + tripStartTime.getHours()).slice(-2);
					var minutes = ("0" + tripStartTime.getMinutes()).slice(-2);
					var amOrPm = hours < 12 ? "am" : "pm";
					formattedTripStartTime = hours + ":" + minutes + " " + amOrPm;
				}
				return formattedTripStartTime;
			},
			targets: [3],
			type: 'date'
		},
		{
			render: function(data, type, row) {
				if (type === "display") {
					if (data === null || data === "") {
						return ""; // Display an empty string if the data is null or empty
					} else {
						var formattedData = parseFloat(data).toLocaleString("en-US", {
							useGrouping: true,
							minimumFractionDigits: 0,
							maximumFractionDigits: 0,
						});
						var formattedValue = `${formattedData.replace(/,/g, ' ')} km`;
						return `<div style="text-align: right;">${formattedValue}</div>`;
					}
				}
				return data;
			},
			targets: [10],
		}

	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editTripLogger(null);
			}
		},
		{
			attr: {
				id: "promptDeleteTripLoggerBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteTripLogger();
			}
		}

	];

	vehicleTripsTable = initializeGenericTable("vehicleTripsTable",
		"/rest/ignite/v1/trip-logger/all-in-view",
		columnsArray,
		columnDefsArray,
		buttonsArray,
		function(rowSelector) {
			editTripLogger(rowSelector);
		},
		null,
		40,
		[[2, "asc"]]
	);
}
// initializeVehicleTripsTable -- End

//editTripLogger -- Start
function editTripLogger(rowSelector) {

	var data = {};
	var username = $("#igUsername").text();
	var userParticipantId = _igModel.participantId;
	
	resetErrors();

	if (rowSelector != null) {
		data = vehicleTripsTable.row(rowSelector).data();
		setTimeout(function() {
			updateTimeCalc();
			formatAndAddKm("tlDistanceFormat", "tlDistance");
			formatAndAddKm("tlKmReadingEndFormatted", "tlKmReadingEnd");
			formatAndAddKm("tlKmReadingStartFormatted", "tlKmReadingStart");
		}, 100);

	} else {
		setTimeout(function() {
			$('#tlDistance').val("");
			$('#tlDistanceFormat').val("");
			$("#tlTripTime").val("");

		}, 100);
		setTimeout(function() {
			nullChecker("tlDistance", "tlDistanceFormat");
		}, 150);
	}

	$('#tlStartName').empty();
	$('#tlEndName').empty();





	vehicleTripsTable.rows().deselect();
	$("#tlOrderBy").val(data.instructedBy);
	$("#tlDriver").val(data.driver);
	$("#tlParticipantDriverId").val(data.participantIdDriver);
	$("#tlProject").val(data.projectNameText);
	$("#tlVehicle").val(data.vehicleName);
	$("#tlOwner").val(data.ownerName);
	$("#tlLicensePlate").val(data.licenceNumber);
	$("#tlLastkmReadingFormatted").val(data.odometerReading);
	$("#tlTripLoggerId").val(data.tripLoggerId);
	$("#tlVehicleId").val(data.vehicleId);
	$("#tlProjectId").val(data.projectId);
	$("#tlOwnerId").val(data.ownerId);
	$("#tlParticipantIdGaveInstruction").val(data.participantIdGaveInstruction);
	$("#tlParticipantIdOnBehalfOf").val(data.participantIdOnBehalfOf);
	$("#tlDescription").val(data.description);

	$("#tlCoordinateEnd").val(data.tripEndCoordinates);
	$("#tlCoordinateStart").val(data.tripStartCoordinates);
	$("#tlKmReadingStartFormatted").val(data.tripStartKm);
	$("#tlKmReadingEndFormatted").val(data.tripEndKm);
	$("#tlDistanceFormat").val(data.tripDistance);
	$("#tlTripTimeMinutes").val(data.travelTimeMinutes);
	$("#tlTripOrderDate").datepicker("setDate", data.tripOrderDate == null ? timestampToString(new Date(), false) : new Date(data.tripOrderDate));
	$("#tlTripDate").datepicker("setDate", data.tripDate == null ? null : new Date(data.tripDate));
	$("#tlStartTime").val(data.tripStartTime == null ? timestampToString(new Date(), true) : data.tripStartTime);
	$("#tlEndTime").val(data.tripEndTime == null ? timestampToString(new Date(), true) : data.tripEndTime);

	setTimeout(function() {
		if (data.tripDistance === null || data.tripDistance === "NaN") {
			$("#tlDistance").val("");
			$("#tlDistanceFormat").val("");
		
		} else {
			$("#tlDistance").val(data.tripDistance);
			var formattedDistance = parseFloat(data.tripDistance).toLocaleString("en-US", {
				useGrouping: true,
				minimumFractionDigits: 0,
				maximumFractionDigits: 0,
			});
			formattedDistance = formattedDistance.replace(/,/g, ' ');
			formattedDistance += " km";
			$("#tlDistanceFormat").val(formattedDistance);
		}



		if (data.odometerReading === null || data.odometerReading === "NaN") {
			$("#tlLastKmReading").val("");
		} else {
			$("#tlLastKmReading").val(data.odometerReading);
		}

		if (data.tripStartKm === null || data.tripStartKm === "NaN") {
			$("#tlKmReadingStart").val("");
		} else {
			$("#tlKmReadingStart").val(data.tripStartKm);
		}

		if (data.tripEndKm === null || data.tripEndKm === "NaN") {
			$("#tlKmReadingEnd").val("");
		} else {
			$("#tlKmReadingEnd").val(data.tripEndKm);
		}
	}, 150);

	setTimeout(function() {
		var startNameValue = data.tripStartingPointName;
		var endNameValue = data.tripEndPointName;

		$("#tlStartName").val(startNameValue);
		$("#tlEndName").val(endNameValue);

		$("#tlStartName option:selected").text(startNameValue);
		$("#tlEndName option:selected").text(endNameValue);

		// Check if the blank space option is selected and then remove it
		if ($("#tlStartName option[value='']:selected").length > 0) {
			$("#tlStartName option[value='']").remove();
		}

		if ($("#tlEndName option[value='']:selected").length > 0) {
			$("#tlEndName option[value='']").remove();
		}
		$("#tlStartName").prepend('<option value=""></option>');
		$("#tlEndName").prepend('<option value=""></option>');
	}, 200);



	if (data.tripStartTime == null) {
		$("#tlStartTime").val("");

	} else {
		var tripStartTime = new Date(data.tripStartTime);
		var formattedTripStartTime = tripStartTime.getHours() + ":" + ("0" + tripStartTime.getMinutes()).slice(-2);
		$("#tlStartTime").val(formattedTripStartTime);
	}



	if (data.tripEndTime == null) {
		$("#tlEndTime").val("");
	} else {
		var tripEndTime = new Date(data.tripEndTime);
		var formattedTripEndTime = tripEndTime.getHours() + ":" + ("0" + tripEndTime.getMinutes()).slice(-2);
		$("#tlEndTime").val(formattedTripEndTime);
	}



	if ($("#tlOrderBy").val() === "") {
		$("#tlOrderBy").val(username);
		$("#tlParticipantIdGaveInstruction").val(userParticipantId);
	}
	if ($("#tlDriver").val() === "") {
		$("#tlDriver").val(username);
		$("#tlParticipantDriverId").val(userParticipantId);
	}


	if (data.tripstartkm !== null && data.tripstartkm !== '' && !isNaN(data.tripstartkm) &&
		data.tripendkm !== null && data.tripendkm !== '' && !isNaN(data.tripendkm)) {
		updateDistanceCalc();
	}



	populateBehalfOfSelect();

	populateVehicleSelect();

	populateProjectSelect();
	
	populateStartingPointSelect();
	populateEndPointSelect();
	
	// Set the Save Button to disabled
	setElementEnabled("saveTripLoggerDialogButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	showModalDialog("tripLoggerDialog");
}
//editTripLogger -- End

function updateHiddenInputFromSelect(selectElement, hiddenInputId) {
	var selectedId = $(selectElement).val();  // get the selected option's value ID
	$("#" + hiddenInputId).val(selectedId);
}




function updateDistanceCalc() {
	
	// Get the current values of startKm, endKm, and distance
	var currentStartKm = parseFloat($("#tlKmReadingStart").val());
	var currentEndKm = parseFloat($("#tlKmReadingEnd").val());
	var currentDistance = parseFloat($("#tlDistance").val());

	// Check if startKm and distance are entered, calculate endKm if missing
	if (!isNaN(currentStartKm) && isNaN(currentEndKm) && !isNaN(currentDistance)) {
		var calculatedEndKm = currentStartKm + currentDistance;
		$("#tlKmReadingEnd").val(calculatedEndKm);
		// Format and display the calculated endKm with " km" in the visible input
		var formattedEndKm = calculatedEndKm.toLocaleString("en-US", {
			useGrouping: true,
			minimumFractionDigits: 0,
			maximumFractionDigits: 0,
		});
		formattedEndKm = formattedEndKm.replace(/,/g, ' ');
		formattedEndKm += " km";
		$("#tlKmReadingEndFormatted").val(formattedEndKm);
	}

	// Check if endKm and distance are entered, calculate startKm if missing
	if (!isNaN(currentEndKm) && isNaN(currentStartKm) && !isNaN(currentDistance)) {
		var calculatedStartKm = currentEndKm - currentDistance;
		$("#tlKmReadingStart").val(calculatedStartKm);
		var formattedStartKm = calculatedStartKm.toLocaleString("en-US", {
			useGrouping: true,
			minimumFractionDigits: 0,
			maximumFractionDigits: 0,
		});
		formattedStartKm = formattedStartKm.replace(/,/g, ' ');
		formattedStartKm += " km";
		$("#tlKmReadingStartFormatted").val(formattedStartKm);
	}

	// Check if startKm and endKm are entered, calculate distance if missing
	if (!isNaN(currentStartKm) && !isNaN(currentEndKm) && isNaN(currentDistance)) {
		var calculatedDistance = currentEndKm - currentStartKm;
		$("#tlDistance").val(calculatedDistance);
		var formattedDistance = calculatedDistance.toLocaleString("en-US", {
			useGrouping: true,
			minimumFractionDigits: 0,
			maximumFractionDigits: 0,
		});
		formattedDistance = formattedDistance.replace(/,/g, ' ');
		formattedDistance += " km";
		$("#tlDistanceFormat").val(formattedDistance);
	}
	if (!isNaN(currentStartKm) && !isNaN(currentEndKm)) {
		var calculatedDistance = currentEndKm - currentStartKm;

		// Update the hidden input for distance
		$("#tlDistance").val(calculatedDistance);

		// Format and display the calculated distance with " km" in the visible input
		var formattedDistance = calculatedDistance.toLocaleString("en-US", {
			useGrouping: true,
			minimumFractionDigits: 0,
			maximumFractionDigits: 0,
		});
		formattedDistance = formattedDistance.replace(/,/g, ' ');
		formattedDistance += " km";
		$("#tlDistanceFormat").val(formattedDistance);
	}
}

function calculateEndKm(startKmId, endKmId, distanceId, endKmFormatId) {
	setTimeout(function() {
		var currentStartKm = parseFloat($("#" + startKmId).val());
		var currentDistance = parseFloat($("#" + distanceId).val());

		// Check if startKm and distance are entered, calculate endKm if missing
		if (!isNaN(currentStartKm) && !isNaN(currentDistance)) {
			var calculatedEndKm = currentStartKm + currentDistance;
			$("#" + endKmId).val(calculatedEndKm);
			//formatAndAddKm("tlKmReadingEndFormatted", "tlKmReadingEnd");

			// Format and display the calculated endKm with " km" in the visible input
			var formattedEndKm = calculatedEndKm.toLocaleString("en-US", {
				useGrouping: true,
				minimumFractionDigits: 0,
				maximumFractionDigits: 0,
			});
			formattedEndKm = formattedEndKm.replace(/,/g, ' ');
			formattedEndKm += " km";

			$("#" + endKmFormatId).val(formattedEndKm);
			
		}
	}, 50)
}






function updateTimeCalc() {
	if (isUpdatingTime) {
		return;
	}


	isUpdatingTime = true;

	var startTime = $("#tlStartTime").val(); // Fetch start and end time value (format: "hh:mm")
	var endTime = $("#tlEndTime").val();
	if (startTime && endTime) {

		var startTimeParts = startTime.split(":");
		var endTimeParts = endTime.split(":");

		var startHour = parseInt(startTimeParts[0], 10);
		var startMinute = parseInt(startTimeParts[1], 10);
		var endHour = parseInt(endTimeParts[0], 10);
		var endMinute = parseInt(endTimeParts[1], 10);

		var totalMinutes;

		if (endHour < startHour || (endHour === startHour && endMinute < startMinute)) {
			// End time is before the start time, indicating a crossing midnight scenario
			totalMinutes = (24 - startHour + endHour) * 60 + (endMinute - startMinute);
		} else {
			totalMinutes = (endHour - startHour) * 60 + (endMinute - startMinute);
		}

		// Convert totalMinutes to hh:mm format
		var hours = Math.floor(totalMinutes / 60);
		var minutes = totalMinutes % 60;
		var formattedTime = (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes;

		$("#tlTripTime").val(formattedTime);
		$("#tlTripTimeMinutes").val(totalMinutes);
	}

	isUpdatingTime = false;
}





//tripLoggerDialogChanged -- Start
function tripLoggerDialogChanged() {
	setElementEnabled("saveTripLoggerDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;

}
//tripLoggerDialogChanged -- End

//tripLoggerDialogChangedStartingPoint - Start
function tripLoggerDialogChangedStartingPoint() {
	const selectElement = document.getElementById('tlStartName');
	const selectedValue = selectElement.value;
	const customInput = document.getElementById('customInput');
	const customButton = document.getElementById('tlStartingNameButton');

	if (selectedValue === 'custom') {
		// Replace the select with the custom input
		customInput.style.display = 'block';
		customButton.style.display = 'block';
	} else {
		// Hide the custom input and show the select
		customInput.style.display = 'none';
		customButton.style.display = 'none';
	}
	setElementEnabled("saveTripLoggerDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}
//tripLoggerDialogChangedStartingPoint - End

//tripLoggerDialogChangedEndPoint - Start
function tripLoggerDialogChangedEndPoint() {
	const selectElement = document.getElementById('tlEndName');
	const selectedValue = selectElement.value;
	const customInput = document.getElementById('tlEndCustomInput');
	const customButton = document.getElementById('tlEndNameButton');

	if (selectedValue === 'custom') {
		customInput.style.display = 'block';
		customButton.style.display = 'block';
	} else {
		customInput.style.display = 'none';
		customButton.style.display = 'none';
	}
	setElementEnabled("saveTripLoggerDialogButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}
//tripLoggerDialogChangedEndPoint - End

//saveTripLoggerDialog -- Start
function saveTripLoggerDialog() {


	var postUrl = "/rest/ignite/v1/trip-logger/new";

	var postData = {
		tripLoggerId: $("#tlTripLoggerId").val(),
		participantIdGaveInstruction: $("#tlParticipantIdGaveInstruction").val(),
		participantIdDriver: $("#tlParticipantDriverId").val(),
		participantIdOnBehalfOf: $("#tlParticipantIdOnBehalfOf").val(),
		tripDistance: $("#tlDistance").val(),
		travelTimeMinutes: $("#tlTripTimeMinutes").val(),
		tripStartingPointName: $("#tlStartName option:selected").text(),
		tripEndPointName: $("#tlEndName option:selected").text(),
		tripStartCoordinates: $("#tlCoordinateStart").val(),
		tripEndCoordinates: $("#tlCoordinateEnd").val(),
		tripStartKm: $("#tlKmReadingStart").val(),
		tripEndKm: $("#tlKmReadingEnd").val(),
		vehicleId: $("#tlVehicleId").val(),
		projectId: $("#tlProjectId").val(),
		tripOrderDate: getMsFromDatePicker("tlTripOrderDate"),
		tripDate: getMsFromDatePicker("tlTripDate"),
		tripStartTime: getMsFromTimePicker("tlStartTime"),
		tripEndTime: getMsFromTimePicker("tlEndTime"),
		description: $("#tlDescription").val(),
	};
	console.dir(postData)

	var errors = "";

	if ((postData.participantIdDriver == null) || (postData.participantIdDriver == "")) {
		errors += "A driver is required.<br>";
	}


	if ((postData.projectId == null) || (postData.projectId == "")) {
		errors += "A project is required.<br>";
	}


	if ((postData.tripDistance < 0)) {
		errors += "Trip distance cannot be negative.<br>";
	}


	var tripEndKm = parseFloat(postData.tripEndKm);
	var tripStartKm = parseFloat(postData.tripStartKm);
	var lastKmReading = parseFloat($("#tlLastKmReading").val());

	if (!isNaN(lastKmReading) && tripStartKm < lastKmReading) {
		errors += "Trip start km cannot be smaller than the last km reading.<br>";
	}

	if (tripEndKm !== '' && tripStartKm !== '') {
		tripEndKm = parseFloat(tripEndKm);
		tripStartKm = parseFloat(tripStartKm);

		if (!isNaN(tripEndKm) && !isNaN(tripStartKm)) {
			if (tripEndKm < tripStartKm) {
				errors += "Trip End kilometer cannot be smaller than trip start kilometer.<br>";
			}
		} else {

			tripEndKm = "";
			tripStartKm = "";
		}
	}

	if (showFormErrors("tlDlgErrorDiv", errors)) {
		return;
	}





	if ((postData.tripLoggerId == null) || (postData.tripLoggerId != "")) {
		var postUrl = "/rest/ignite/v1/trip-logger";

	}
	console.dir(postData);
	console.dir(postUrl);
	saveEntry(postUrl, postData, "tripLoggerDialog", "The Trip has been saved.", vehicleTripsTable);
}
//saveTripLoggerDialog -- End


function updateTripLoggerToolbarButtons() {
	var hasSelected = vehicleTripsTable.rows('.selected').data().length > 0;
	
	setTableButtonState(vehicleTripsTable, "promptDeleteTripLoggerBtn", hasSelected);

}


//promptDeleteTripLogger -- Start
function promptDeleteTripLogger() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the Selected Logged Trip?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteTripLogger(vehicleTripsTable);
		}
	);
}
//promptDeleteTripLogger -- End

//deleteTripLogger -- Start
function deleteTripLogger(tbl) {
	var postUrl = "/rest/ignite/v1/trip-logger/delete";
	var row = tbl.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The Logged Trip has been deleted.", tbl,
		function() {
			tbl.rows().deselect();
			updateTripLoggerToolbarButtons();

		}
	);
}
//deleteTripLogger -- End



//closeTripLoggerDialog -- Start
function closeTripLoggerDialog() {
	if ((somethingChangedInDialog == true) || (askToSaveDialog == true)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("tripLoggerDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("tripLoggerDialog");
	}
	somethingChangedInDialog = false;
}
//closeTripLoggerDialog -- End

//editSelectTlVehicle -- Start
function editSelectTlVehicle() {


	var queryUrl = "/rest/ignite/v1/trip-logger/vehicle";


	var targetId = "tlVehicleId";
	var targetName = "tlVehicle";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "vehicleId";
			var refColumnName = "vehicleName";
			var columns = [
				{ data: "vehicleId", name: "id" },
				{ data: "vehicleName", name: "Name" },
				{ data: "vehicleModelId", name: "Model Id" },
				{ data: "modelName", name: "Model Name" },
				{ data: "vehicleMakeId", name: "VehicleMakeId" },
				{ data: "makeName", name: "MakeName" },
			];
			var columnDefs = [
				{
					visible: false,
					targets: [0, 2, 4]
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.vehicleId;
				var vehicleName = row.vehicleName;

				updateFieldsFromSelectedVehicle(row);

				$("#" + targetId).val(id);
				//$("#" + targetName).val(vehicleName);
				$("#" + targetName + " option:selected").text(vehicleName);
				$("#" + targetName + " option:selected").val(id);
				$("#" + targetName).trigger("change");

				populateVehicleSelect()

			}, "tripLoggerDialog", targetId, targetName, "I");


		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectTlVehicle -- End

function updateFieldsFromSelectedVehicle(row) {
	$("#tlOwner").val(row.ownerName);
	$("#tlLicensePlate").val(row.licenceNumber);
	$("#tlLastKmReading").val(row.odometerReading);
}

//editSelectTlProject -- Start
function editSelectTlProject() {


	var queryUrl = "/rest/ignite/v1/project/list";


	var targetId = "tlProjectId";
	var targetName = "tlProject";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "projectId";
			var refColumnName = "projectId";
			var columns = [
				{ data: "projectId", name: "project id" },
				{ data: "participantIdHost", name: "Host Id" },
				{ data: "participantNameHost", name: "Host Name" },
				{ data: "projectNameText", name: "Title" },
				{ data: "subProjNumber", name: "Sub Project" },
			];
			var columnDefs = [
				{
					visible: false,
					targets: [0, 1]
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.projectId;
				var title = row.projectNameText;


				$("#" + targetId).val(id);
				//$("#" + targetName).val(title);
				$("#" + targetName + " option:selected").text(title);
				$("#" + targetName + " option:selected").val(id);
				$("#" + targetName).trigger("change");

				populateProjectSelect();

				populateStartingPointSelect();

				populateEndPointSelect();

			}, "tripLoggerDialog", targetId, targetName, "I");

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectTlProject -- End

//editSelectTlDriver -- Start
function editSelectTlDriver() {


	var queryUrl = "/rest/ignite/v1/individual/list";


	var targetId = "tlParticipantDriverId";
	var targetName = "tlDriver";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "firstName";
			var columns = [
				{ data: "firstName", name: "Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "initials", name: "Initials" },
				{ data: "nickName", name: "Nick Name" },
				{ data: "systemName", name: "System Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: []
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var driverName = row.firstName + " " + row.lastName;


				$("#" + targetId).val(id);
				$("#" + targetName).val(driverName);
				$("#" + targetName).trigger("change");

				populateVehicleSelect();

			}, "tripLoggerDialog", targetId, targetName, "I");

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectTlDriver -- End

//editSelectTlInstructedBy -- Start
function editSelectTlInstructedBy() {


	var queryUrl = "/rest/ignite/v1/individual/list";

	var targetId = "tlParticipantIdGaveInstruction";
	var targetName = "tlOrderBy";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "fistName";
			var columns = [
				{ data: "firstName", name: "Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "initials", name: "Initials" },
				{ data: "nickName", name: "Nick Name" },
				{ data: "systemName", name: "System Name" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: []
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var instructedByName = row.firstName + " " + row.lastName;


				$("#" + targetId).val(id);
				//$("#" + targetName).text(instructedByName);
				$("#" + targetName).val(instructedByName);
				$("#" + targetName).trigger("change");

				populateBehalfOfSelect()

			}, "tripLoggerDialog", targetId, targetName, "I");

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectTlInstructedBy -- End

//editSelectTlOnBehalfOf -- Start
function editSelectTlOnBehalfOf() {


	var queryUrl = "/rest/ignite/v1/participant/all-in-view";


	var targetId = "tlParticipantIdOnBehalfOf";
	var targetName = "tlBehalfOf";

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {

			var columnName = "participantId";
			var refColumnName = "systemName";
			var columns = [
				{ data: "systemName", name: "On Behalf Of" },
				{ data: "systemName", name: "On Behalf Of" },
				{ data: "registeredName", name: "Registered Name" },
				{ data: "firstName", name: "Name" },
				{ data: "tradingName", name: "Trading Name" },
				{ data: "lastName", name: "Last Name" },
				{ data: "isIndividual", name: "Individual" }
			];
			var columnDefs = [
				{
					visible: false,
					targets: [0]
				}
			];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.participantId;
				var systemName = row.systemName;


				$("#" + targetId).val(id);
				$("#" + targetName + " option:selected").text(systemName);
				$("#" + targetName + " option:selected").val(id);
				$("#" + targetName).trigger("change");

				populateProjectSelect();
				$("#tlProject").val(0).change();


			}, "tripLoggerDialog", targetId, targetName, "I");

		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});
}
//editSelectTlOnBehalfOf -- End

function populateProjectSelect() {

	var projectId = $("#tlProjectId").val();
	var projectNameText = $("#tlProject").text();
	var participantIdOnBehalfOf = $("#tlParticipantIdOnBehalfOf").val();
	var currentUser = _igModel.username;

	if ((projectId == null) || (projectId == '')) {

		projectId = -999;

	}
	if ((participantIdOnBehalfOf == null) || (participantIdOnBehalfOf == '')) {
		$("#tlProject").val("");
		$("#tlProject").empty();
		return;
	}

	if (participantIdOnBehalfOf === currentUser) {
		participantIdOnBehalfOf = _igModel.participantId;
	}

	if (participantIdOnBehalfOf === "") {
		// If tlBehalfOf is blank, clear tlProject and its select options
		$("#tlProject").val("");
		$("#tlProject").empty();
		return;
	}

	populateSelect("tlProject",
		"/rest/ignite/v1/project/behalfOf/" + participantIdOnBehalfOf + "/" + projectId,
		"projectId",
		"projectNameText",
		projectId,
		true,
		function() {
			populateStartingPointSelect();
			populateEndPointSelect();
		}
	);




}

function populateVehicleSelect() {
	var vehicleId = $("#tlVehicleId").val();
	var participantDriverId = $("#tlParticipantDriverId").val();

	if ((vehicleId == null) || (vehicleId == '')) {
		vehicleId = -999;
	}

	if ((participantDriverId == null) || (participantDriverId == '')) {
		return;
	}

	populateSelect("tlVehicle",
		"/rest/ignite/v1/trip-logger/driver/" + participantDriverId + "/" + vehicleId,
		"vehicleId",
		"name",
		vehicleId,
		true,
		function(data) {
			if (data.length == 0) {
				clearSelectedVehicle();
				return;
			}

			var id = data[0].id;

			updateVehicleInformation(id);
		}
	);
}


function populateBehalfOfSelect() {
    var participantId = $("#tlParticipantIdGaveInstruction").val();
    var participantIdOnBehalfOf = $("#tlParticipantIdOnBehalfOf").val();
    var selectElement = $("#tlBehalfOf");
    var queryUrl = "/rest/ignite/v1/trip-logger/instructedBy/" + participantId + "/" + participantIdOnBehalfOf;

    // Clear existing options in the select element
    selectElement.empty();

    // Add a blank option at the top
    selectElement.append($("<option>"));

    // Get the current user's username
    var currentUserUsername = _igModel.username;

    // Always add the current user as an option
    var currentUserOption = $("<option>").val(currentUserUsername).text(currentUserUsername);
    selectElement.append(currentUserOption);

    // If participantIdOnBehalfOf is blank, set the current user as the selected option
    if (participantIdOnBehalfOf === "") {
        currentUserOption.prop("selected", true);
        $("#tlParticipantIdOnBehalfOf").val(_igModel.participantId);
    }

    // Add a condition to check if projectId is empty
    if (!participantIdOnBehalfOf) {
        return;
    }

    // Perform an AJAX request to retrieve new data, treating the form as an already made one
    $.ajax({
        url: queryUrl,
        type: "GET",
        success: function (newData) {
            // Merge existingData with the new data
            var mergedData = existingData.concat(newData);

            // Map mergedData to an array of display values
            var displayValues = mergedData.map(function (item) {
                return item.behalfOf;
            });

            // Filter out duplicates and sort the display values
            displayValues = displayValues.filter(function (value, index, self) {
                return self.indexOf(value) === index;
            }).sort();

            // Add options to the select element
            displayValues.forEach(function (value) {
                var option = $("<option>").val(value).text(value);
                selectElement.append(option);
            });

            // If the current user is in the data, set it as selected
            if (displayValues.indexOf(currentUserUsername) !== -1) {
                currentUserOption.prop("selected", true);
            }

            // Call populateSelect with the displayValues
            populateSelect("tlBehalfOf",
                "/rest/ignite/v1/trip-logger/instructedBy/" + participantId + "/" + participantIdOnBehalfOf,
                "participantIdOnBehalfOf",
                "behalfOf",
                participantIdOnBehalfOf,
                true,
                null,
                displayValues
            );
        },
        error: function (jqXHR, textStatus, errorThrown) {
            ajaxError(jqXHR, textStatus, errorThrown);
        },
        complete: function (html) {
            ajaxSuccess(html.status);
        }
    });
}



function resetErrors() {
    $("#tlDlgErrorDiv").html("");
    $("#tlDlgErrorDiv").hide();
    }



function populateStartingPointSelect() {

	var projectId = $("#tlProjectId").val();
	var tripLoggerId = $("#tlTripLoggerId").val();

	if ((projectId == null) || (projectId == '')) {
		return;
	}




	populateSelect(
		"tlStartName",
		"/rest/ignite/v1/trip-logger/startPoint/" + projectId,
		"tripLoggerId",
		"tripStartingPointName",
		tripLoggerId,
		true,
		function() {
			// Add the "Custom" option to the select element after population
			const customOption = document.createElement('option');
			customOption.value = 'custom';
			customOption.textContent = 'New';
			document.getElementById('tlStartName').appendChild(customOption);

			// Keep track of the number of options added.
			let optionsAdded = 0;

			// Loop through the select's options and remove any excess options
			const selectElement = document.getElementById('tlStartName');
			for (let i = selectElement.options.length - 1; i >= 1; i--) {
				if (optionsAdded >= 10) {
					selectElement.remove(i);
				} else {
					optionsAdded++;
				}
			}


		}
	);

}

function populateEndPointSelect() {
	var projectId = $("#tlProjectId").val();
	var tripLoggerId = $("#tlTripLoggerId").val();

	if ((projectId == null) || (projectId == '')) {
		return;
	}

	populateSelect(
		"tlEndName",
		"/rest/ignite/v1/trip-logger/endPoint/" + projectId,
		"tripLoggerId",
		"tripEndPointName",
		tripLoggerId,
		true,
		function() {
			// Keep track of the number of options added.
			let optionsAdded = 0;

			// Loop through the select's options and remove any excess options
			const selectElement = document.getElementById('tlEndName');
			for (let i = selectElement.options.length - 1; i >= 1; i--) {
				if (optionsAdded >= 10) {
					selectElement.remove(i);
				} else {
					optionsAdded++;
				}
			}

			// Add the "Custom" option to the select element at the end
			const customOption = document.createElement('option');
			customOption.value = 'custom';
			customOption.textContent = 'New';
			selectElement.appendChild(customOption);
		}
	);

	// Add the blank space option at the top
	const selectElement = document.getElementById('tlEndName');
	const blankOption = document.createElement('option');
	blankOption.value = '';
	blankOption.textContent = 'Select an option'; // Replace with your desired text
	selectElement.insertBefore(blankOption, selectElement.firstChild);
}



function clearSelectedVehicle() {
	$("#tlOwner").val("");
	$("#tlOwnerId").val("");
	$("#tlLicensePlate").val("");
	$("#tlLastKmReading").val("");
	$("#tlLastkmReadingFormatted").val("");
}

function updateVehicleInformation(vehicleId) {

	if ($("#tlVehicleId").val() == '') {
		clearSelectedVehicle();
		return;
	}

	var queryUrl = "/rest/ignite/v1/vehicle/" + vehicleId;

	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			$("#tlOwner").val(data.ownerName);
			$("#tlOwnerId").val(data.ownerId);
			$("#tlLicensePlate").val(data.licenceNumber);
			$("#tlLastKmReading").val(data.odometerReading);
			$("#tlLastkmReadingFormatted").val(data.odometerReading);
			formatAndAddKm("tlLastkmReadingFormatted", "tlLastKmReading");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}
	});

}

function nullChecker(hiddenInputId, formattedInputId) {
	var hiddenInput = document.getElementById(hiddenInputId);
	var formattedInput = document.getElementById(formattedInputId);

	if (isNaN(hiddenInput.value)) {
		hiddenInput.value = "";
	}

	if (formattedInput === null) {
		return; // Exit the function if formattedInput is null
	}

	setTimeout(function() {
		if (hiddenInput.value === "") {
			formattedInput.value = "";
		}
	}, 100);
}

function formatAndAddKm(formattedInputId, hiddenInputId) {

	var formattedInput = document.getElementById(formattedInputId);

	var hiddenInput = document.getElementById(hiddenInputId);

	//var value1 = $("#tlLastKmReading").val();
	//var value2 = $("#tlLastkmReadingFormatted").val(); 

	var formattedValue = formattedInput.value;

	var rawValue = parseFloat(formattedValue.replace(/[^0-9]/g, ''));

	hiddenInput.value = rawValue;

	// Format the visible input field
	if (!isNaN(rawValue)) {
		var formattedNumericValue = rawValue.toLocaleString("en-US", {
			useGrouping: true,
			minimumFractionDigits: 0,
			maximumFractionDigits: 0,
		});

		var tlDistanceInput = document.getElementById("tlDistanceFormat");
		tlDistanceInput.addEventListener("blur", function() {
			formatAndAddKm("tlDistanceFormat", "tlDistance");
		});


		formattedNumericValue = formattedNumericValue.replace(/,/g, ' ');

		var finalFormattedValue = formattedNumericValue + " km";

		formattedInput.value = finalFormattedValue;
	}
}





function openTimePicker(elementId) {
	var timepickerInput = document.getElementById(elementId);
	var inputTime = timepickerInput.value.trim();

	if (!inputTime) {
		// If the input is empty, set it to 12:00 PM
		timepickerInput._flatpickr.setDate(new Date().setHours(12, 0));
	} else {
		// If there's a time in the input, parse and set it
		var timeParts = inputTime.split(':');
		if (timeParts.length === 2) {
			var hours = parseInt(timeParts[0], 10);
			var minutes = parseInt(timeParts[1], 10);

			if (!isNaN(hours) && !isNaN(minutes)) {
				var selectedTime = new Date();
				selectedTime.setHours(hours, minutes);
				timepickerInput._flatpickr.setDate(selectedTime);
			}
		}
	}

	timepickerInput._flatpickr.open();
}







function getMsFromTimePicker(elementId) {
	var result = null;

	var timeString = $("#" + elementId).val();
	if (timeString) {
		var timeParts = timeString.split(":");
		if (timeParts.length === 2) {
			var hours = parseInt(timeParts[0], 10);
			var minutes = parseInt(timeParts[1], 10);
			if (!isNaN(hours) && !isNaN(minutes)) {
				var timeDate = new Date();
				timeDate.setHours(hours, minutes, 0, 0);
				result = timeDate.getTime();
			}
		}
	}

	return result;
}
//**********************************************************

// ***********************************************************************

document.addEventListener('DOMContentLoaded', function() {
	flatpickr('#tlStartTime', {
		enableTime: true,
		noCalendar: true,
		dateFormat: 'H:iK',
		time_24hr: true,
		appendTo: document.getElementById('tlClockContainer'), // Use the container for Start Time
	});

	flatpickr('#tlEndTime', {
		enableTime: true,
		noCalendar: true,
		dateFormat: 'H:iK',
		time_24hr: true,
		appendTo: document.getElementById('tlBinosContainer'), // Use the container for End Time
	});
});



document.addEventListener('click', function(event) {
	if (event.target && event.target.id === 'tlClock1') {
		openTimePicker('tlStartTime');
	}
	if (event.target && event.target.id === 'tlClock2') {
		openTimePicker('tlEndTime');
	}
});


$(document).ready(function() {
	initializeVehicleTripsTable();
	initializeUserInfo();


	$('#tlStartName').change(function() {
		if ($(this).val() === 'custom') {
			$('#customInput').show();
			$('#startingNameButton').show();
		} else {
			$('#customInput').hide();
			$('#tlStartingNameButton').hide();
		}
	});

	$('#tlEndName').change(function() {
		if ($(this).val() === 'custom') {
			$('#tlEndCustomInput').show();
			$('#tlEndNameButton').show();
		} else {
			$('#tlEndCustomInput').hide();
			$('#tlEndNameButton').hide();
		}
	});

	$('#customInput').blur(function() {
		const customValue = $(this).val();
		const selectElement = $('#tlStartName');

		// Find the "New" option
		const newOption = selectElement.find('option[value="custom"]');
		if (newOption.length > 0) {
			// Insert the new option before the "New" option
			newOption.before($('<option>', {
				value: customValue,
				text: customValue
			}));
		} else {
			// "New" option not found, append it at the end
			selectElement.append($('<option>', {
				value: customValue,
				text: customValue
			}));
		}

		// Set the selected value to the custom value
		selectElement.val(customValue);
		$(this).hide().val('');

		$('#tlStartingNameButton').hide();
	});

	$('#tlEndCustomInput').blur(function() {
		const customValue = $(this).val();
		const selectElement = $('#tlEndName');

		// Find the "New" option
		const newOption = selectElement.find('option[value="custom"]');
		if (newOption.length > 0) {

			newOption.before($('<option>', {
				value: customValue,
				text: customValue
			}));
		} else {
			selectElement.append($('<option>', {
				value: customValue,
				text: customValue
			}));
		}

		selectElement.val(customValue);
		$(this).hide().val('');
		$('#tlEndNameButton').hide();
	});





	$("#tlVehicle").on("change", function() {
		clearSelectedVehicle();
		populateVehicleSelect();
	});

	$("#tlProject").on("change", function() {
		populateProjectSelect();

	});

	$("#tlBehalfOf").on("change", function() {
	setTimeout(function(){
		$("#tlProject").val(0).change();
		$("#tlStartName").val(0).change();
		$("#tlEndName").val(0).change();
	}, 200);
	
		populateProjectSelect();

	});



});


