var calendarTable = null;

function initializeCalendar() {
	var columnsArray = [
		{ data: "calendarDate" },
		{ data: "calendarDate" },
		{ data: "eventName" },
		{ data: "eventDescription" },
		{ data: "isPublicHoliday"}
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 3]
		},
		{
			width: "120px",
			targets: 1,
		},
		{
			render: function(data, type, row) {
				if ((data != null) && (data != "")) {
					data = timestampToString(data, false);
				}
				
				return data;
			},
			targets: 1
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					var checkedStatus = data == "Y" ? " checked " : "";
					data = "<input type='checkbox' " + checkedStatus + " readonly onclick='return false'>";
				}
				return data;
			},
			width: "100px",
			className: "dt-center",
			targets: 4
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editCalendar(null);
			}
		},
		{
			attr: {
				id : "deleteCalendarEntryBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDeleteCalendarEntry();
			}
		}
	];

	calendarTable = initializeGenericTable("calendarTable", 
			                            "/rest/ignite/v1/calendar",
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
                                            editCalendar(aThis);
										},
										null, // completeMethod
										null, // pageLength
										[[0, "asc"]]
	);
	
	calendarTable.on( 'select', function ( e, dt, type, indexes ) {
		updateCalendarToolbarButtons();
	} );
	
	calendarTable.on( 'deselect', function ( e, dt, type, indexes ) {
		updateCalendarToolbarButtons();
	} );

	updateCalendarToolbarButtons();
	
}

function editCalendar(aThis) {
	var data = {};
	
	if (aThis != null) {
		// get values from table
		data = calendarTable.row(aThis).data();
	}
	calendarTable.rows().deselect();
	
	$("#cDlgOriginalCalendarDate").val(data.calendarDate);
	//$("#cDlgCalendarDate").val(dateToString(data.calendarDate));
	$("#cDlgCalendarDate").datepicker("setDate", new Date(data.calendarDate));
	
	$("#cDlgEventName").val(data.eventName);
	$("#cDlgEventDescription").val(data.eventDescription);

	// disable the key if update, enable if insert
	$("#cDlgIsPublicHoliday").prop("checked", data.isPublicHoliday == "Y")

	showModalDialog("calendarDialog");
}

function saveCalendar() {
	var postUrl = "/rest/ignite/v1/calendar";
	var cd = $("#cDlgCalendarDate").datepicker("getDate");
	
	var postData = {
			mode: $("#cDlgOriginalCalendarDate").val() == "" ? "i" : "u",
			calendarDate:  (cd == null) || (cd == "") ? null : cd.getTime(),
			eventName: $("#cDlgEventName").val(),
			eventDescription: $("#cDlgEventDescription").val(),
			isPublicHoliday : $("#cDlgIsPublicHoliday").is(":checked") ? "Y" : "N"	
	};
	
	var errors = "";
	
	if (postData.calendarDate == "") {
		errors += "A Date is required<br>";
	}
	
	if (postData.eventName == "") {
		errors += "A Name is required<br>";
	}

	// validation...
	if (showFormErrors("cDlgErrorDiv", errors)) {
		return;
	}
	
	saveEntry(postUrl, postData, "calendarDialog", "The Calendar entry has been saved.", calendarTable);
}

function updateCalendarToolbarButtons() {
	var hasSelected = calendarTable.rows('.selected').data().length > 0;

	setTableButtonState(calendarTable, "deleteCalendarEntryBtn", hasSelected);
}
function promptDeleteCalendarEntry() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Calendar entry?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteCalendarEntry();
			   }
	);
}

function deleteCalendarEntry() {
	var row = calendarTable.rows('.selected').data()[0];

	var postUrl = "/rest/ignite/v1/calendar/delete";
	var postData = {
			calendarDate: row.calendarDate
	};
	
	// Disable delete button after record has been deleted.
	saveEntry(postUrl, postData, null, "The Calendar entry has been deleted.", calendarTable,
			function(){	
				calendarTable.rows(".selected").nodes().to$().removeClass("selected");
				updateCalendarToolbarButtons();
			}
	);
	
}
// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeCalendar();
} );
