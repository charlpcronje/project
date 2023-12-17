var cityTable;

var somethingChangedInDialog = null;
var askToSaveDialog = null; //Only ask once for every tab change if the current tab's data changed

function initializeCityTable() {
	
	var columnsArray = [                      //Om 'n grid vol te maak.
     		 { data: "cityId" }                   //0 MySql-TableName: VCity
     		,{ data: "countryId" }              //1
     		,{ data: "countryNameAndCode" }         //2
     		,{ data: "provinceId" }             //3
     		,{ data: "provinceId_Name" }        //4
     		,{ data: "name" }                     //5
     		,{ data: "latitude" }                 //6
     		,{ data: "longitude" }                //7
     	];

     	var columnDefsArray = [
     		{
     			visible: false,
     			targets: [0, 1, 3]
     		}

     	];

	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				editCity(null);
			}
		},
		{
			attr: {
				id: "deleteCityBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function(e, dt, node, config) {
				promptDeleteCity();
			}
		}
	];
console.log("rest/ignite/v1/city/find-all-in-view")
	
//	function initializeGenericTable(tableElementName, 
//								queryUrl, 
//								columnsArray, 
//								columnDefsArray, 
//        						buttonsArray, 
//        						callbackMethod, 
//        						completeMethod, 
//        						pageLength, 
//        						orderArray,
//        						tableHeightPixels, 
//        						showTableInfo, 
//        						showFilter) {
	
	cityTable = initializeGenericTable(
		"cityTable", 			// tableElementName 
		"/rest/ignite/v1/city/find-all-in-view",	// queryUrl
		columnsArray,				// columnsArray
		columnDefsArray,			// columnDefsArray
		buttonsArray,				// buttonArray
		function(roleSelector) {	// callbackMethod  (double-click)
			editCity(roleSelector);
		},
		null,						// completeMethod
		35,							// pageLength
		[[5,"asc"]] 				// Order by column 0 ascending, normally defaults to column 1 ascending
	);


	cityTable.off('deselect');
	cityTable.on('deselect', function(e, dt, type, indexes) {
		updateCityToolbarButtons();
	});

	cityTable.off('select');
	cityTable.on('select', function(e, dt, type, indexes) {
		
		updateCityToolbarButtons();
	});
	
	

	// to initially set the buttons
	updateCityToolbarButtons();
}



function updateCityToolbarButtons() {
	var hasSelected = cityTable.rows('.selected').data().length > 0;

	setTableButtonState(cityTable, "deleteCityBtn", hasSelected);
}



function editCity(rowSelector) {
	
	var cityId = "";
	var name = "";
	var data = {}; // Give it an empty object
	
	if (rowSelector != null) {
		data = cityTable.row(rowSelector).data();
		cityId = data.cityId;
		name = data.name;
	}
	cityTable.rows().deselect();
	console.log("cityId TEst = "+ cityId)

	//  MySql-TableName: VCity										   (js3Str)
	$("#scCityId").val(data.cityId);                                 //0
	$("#scCountryId").val(data.countryId);                       //1

	$("#scCountryId_Name").val(data.countryNameAndCode);             //2
	
	populateSelect("scProvinceId",                                  //name of html select element that will be populated
			"/rest/ignite/v1/province/list-view-province-for-country/" + data.countryId,                      //url
			"provinceId",                                           //the value that must be saved (ReferencedColumnName)
			"name",                                                   //shown to user (usually a Name column)
			data.provinceId,                                        //The selected one, if there is one
			true,                                                     //addEmpty, boolean: should we add empty object at the top
			null                                                      //completeMethod
	),


	$("#scName").val(data.name);                                      //5
	$("#scLatitude").val(data.latitude);                              //6
	$("#scLongitude").val(data.longitude);                            //7

	if ( (isNaN($("#scLatitude").val())) || (isNaN($("#scLongitude").val())) || ($("#scLatitude").val() == "") || ($("#scLongitude").val() == "")) {
		document.getElementById("gmBtn").style.visibility="hidden";
	} else {
		document.getElementById("gmBtn").style.visibility="visible";
	}

	// disable the key if update, enable if insert
	//$("#scCityId").prop("readonly", (cityId != null) && (cityId != ""));

	// Set the Save Button to disabled
	setElementEnabled("saveCityButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;

	
	showModalDialog("cityDialog");
}

function cityDialogChanged() {
	setElementEnabled("saveCityButton", true);
	somethingChangedInDialog = true;
	askToSaveDialog = true;
}


function saveCity() {
	
	var postUrl = "/rest/ignite/v1/city/new";
	var postData = {
		cityId : $("#scCityId").val()                   //0 MySql-TableName: City
		,countryId : $("#scCountryId").val()                                    //1
		,provinceId : $("#scProvinceId").val()                                  //2
		,name : $("#scName").val()                                                  //3
		,latitude : $("#scLatitude").val()                                          //4
		,longitude : $("#scLongitude").val()                                        //5
	};
	
	var errors = "";
	
	
	if ((postData.countryId == null) || (postData.countrId == "")) {
		errors += "A Country is required<br>";
	}
	
	if ((postData.provinceId == null) || (postData.provinceId == "")) {
		errors += "A Province is required<br>";
	}
	
	if ((postData.name == null) || (postData.name == "")) {
		errors += "A City Name is required<br>";
	}
	
	
//	if ((postData.latitude == null) || (postData.latitude == "")) {
//		errors += "Please enter the Latitude<br>";
//	}
	
	if ((postData.latitude != null) && (postData.latitude != "")) {
		if (isNaN(postData.latitude)) {
			errors += "Latitude is not valid<br>";
		} else {
			if ((postData.latitude > 90) || (postData.latitude < -90)){
				errors += "Latitude is not valid<br>";
			}
		}
	}	
	if ((postData.longitude != null) && (postData.longitude != "")) {
		if (isNaN(postData.longitude)) {
			errors += "Longitude is not valid<br>";
		} else {
			if ((postData.longitude > 180) || (postData.longitude < -180)){
				errors += "Longitude is not valid<br>";
			}
		}
	}
	
	if (showFormErrors("scCityDlgErrorDiv", errors)) {
		return;
	}
	
	//Is the key readonly?  If it is, then the record already exists. 
	if ((postData.cityId != null) && (postData.cityId != "")) {
		// This is an update 
		postUrl = "/rest/ignite/v1/city";
	} else {
		postData.cityId = null;  //empty string werk nie
	}

	saveEntry(postUrl, postData, "cityDialog", "The City has been saved.", cityTable);
}



function promptDeleteCity() {
	showDialog("Confirm?",
		"Are you sure that you wish to delete the selected City?",
		DialogConstants.TYPE_CONFIRM,
		DialogConstants.ALERTTYPE_INFO,
		function(e) {
			deleteCity();
		}
	);
}

function deleteCity() {
	var postUrl = "/rest/ignite/v1/city/delete";
	var row = cityTable.rows('.selected').data()[0];

	// Disable delete button after record has been deleted.
	saveEntry(postUrl, row, null, "The City has been deleted.", cityTable,
		function() {
			cityTable.rows(".selected").nodes().to$().removeClass("selected");
			updateCityToolbarButtons();
		}
	);
}

function closeCityDialog() {
	if ((somethingChangedInDialog) || (askToSaveDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("scCityDlgErrorDiv", "none");
				closeModalDialog("cityDialog");
			});
	} else {
		setDivVisibility("scCityDlgErrorDiv", "none");
		closeModalDialog("cityDialog");
	}
}

// ***********************************************************************


function editSelectCountryId() {
	selectCountry("scCountryId", "scCountryId_Name");
	cityDialogChanged();
}

function selectCountry(targetId, targetName) {
	
	// var queryUrl="/rest/ignite/v1/expense-type";
	var queryUrl="/rest/ignite/v1/country/list-country-order-by-name"
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
			
			var columnName="countryId";
			var refColumnName="name";
			var columns = [
			       		{ data: "countryId", name: "CountryId" }                       //0 MySql-TableName: Country
			       		,{ data: "countryNameAndCode", name: "Name" }                                     //1
			       		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //2
			       		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //3
			       	];

			       	var columnDefs = [
			       		{
			       			visible: false,
			       			targets: [0, 2, 3]
			       		}
			       	];

			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.countryId;
				var repName = row.name; 

				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
				populateProvince();
				
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
			//populateProvince();
		}  
	});
}//selectCountry


function populateProvince() {

	var countryId = $("#scCountryId").val();
	
	if ((countryId == null) || (countryId == "")) {
		console.log("geReturn")
		return
	}
	
	populateSelect("scProvinceId",                                  //name of html select element that will be populated
			"/rest/ignite/v1/province/list-view-province-for-country/" + countryId,                      //url
			"provinceId",                                           //the value that must be saved (ReferencedColumnName)
			"name",                                                   //shown to user (usually a Name column)
			null,                                        			  //The selected one, if there is one
			true,                                                     //addEmpty, boolean: should we add empty object at the top
			null                                                      //completeMethod
	)
}




function checkLatLong() {
	
	if ( (isNaN($("#scLatitude").val())) || (isNaN($("#scLongitude").val())) || ($("#scLatitude").val() == "") || ($("#scLongitude").val() == "")) {
		document.getElementById("gmBtn").style.visibility="hidden";
	} else {
		document.getElementById("gmBtn").style.visibility="visible";
	}
	
}

function openGM() {

	window.open("https://www.google.com/maps/place/" + $("#scLatitude").val() + ", " + $("#scLongitude").val());
	
}


function GetCurPos() {
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(function(position) {
	    const latitude = position.coords.latitude;
	    const longitude = position.coords.longitude;
	    console.log("Current Position:");
	    console.log(`${latitude}, ${longitude}`);
	  });
	} else {
	  console.log("Geolocation is not supported by this browser.");
	}
}


$(document).ready(function() {
	// Any initialization
	initializeCityTable();
	showIgDeveloperOption();
});






