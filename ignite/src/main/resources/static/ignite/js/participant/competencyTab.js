//Competency
function initializeIndividualCompetencyListTable(participantId, individualId){
	
	var queryUrl="/rest/ignite/v1/individual-sd-role/participant/" + participantId; 

	var columnsArray = [
               		{data:"individualSdRoleId"},          //  0 individualSdRoleId      
               		{data:"individualId"},                //  1 individualId            
               		{data:"sdRoleId"},                    //  2 sdRoleId                
               		{data:"competencyLevelId"},         //  3 competencyLevelId     
               		{data:"participantId"},               //  4 participantId        	
               		{data:"combinedName"},				  //  5 combinedName		   
               		{data:"competencyLevelName"},         //  6 competencyLevelName       
               		{data:"yearExperience"},            //  7      
               		
               		{data:"serviceDisciplineId"},         //  8 serviceDisciplineId  
               		{data:"serviceDisciplineCode"},       //  9 serviceDisciplineCode   
               		{data:"serviceDisciplineName"},       //  10 serviceDisciplineName   
               		{data:"roleOnAProjectName"}		      //  11 roleOnAProjectName	
            		,{data: "sdParentName" }             //12
            		,{data: "sdGrandParentName" }        //13
            		,{data: "sdIndustryName" }           //14
		];
		
	var columnDefsArray = [
		{
			visible:false, 
			targets:[0, 1, 2, 3, 4,  8, 9, 10, 11, 12, 13, 14]
		}
	];
	
	var buttonsArray = [
    		{
    			titleAttr: "New",
    			text: "<i class='fas fa-plus'></i>",
    			className: "btn btn-sm",
    			action: function( e, dt, node, config ) {
    				editIndividualCompetency(null, individualId);
    			}
    		},
    		{
    			attr: {
    				id: "promptDeleteIndividualCompetencyBtn"
    			},
    			titleAttr: "Delete",
    			text: "<i class='fas fa-minus'></i>",
    			className: "btn btn-sm",
    			action: function() {
    				promptDeleteIndividualCompetency();
    			}
    		}
    	];
	
	individualCompetencyTable = initializeGenericTable("individualCompetencyTable", 
											queryUrl,
								            columnsArray,
								            columnDefsArray,
								            buttonsArray,
								            function(rowSelector) {
												editIndividualCompetency(rowSelector, individualId);
											},
											null,
											10,
											[[9,"asc"]], //Order by column 0 ascending, normally defaults to column 1 ascending
											null,       //tableHeightPixels (ignored if you have pagelength)
										    false,    //showTableInfo   (Showing 0 to 5 etc....)
											true   //showFilter												
								);

	individualCompetencyTable.off('deselect');
	individualCompetencyTable.on('deselect', function (e, dt, type, indexes) {
		updateIndividualCompetencyTableToolbarButtons();
	} );
	
	individualCompetencyTable.off('select');
	individualCompetencyTable.on('select', function (e, dt, type, indexes) {
		updateIndividualCompetencyTableToolbarButtons();
	} );	

	updateIndividualCompetencyTableToolbarButtons();	

} // initializeIndividualCompetencyTable -- End



function updateIndividualCompetencyTableToolbarButtons() {
	var hasSelected = individualCompetencyTable.rows('.selected').data().length > 0;
	setTableButtonState(individualCompetencyTable, "promptDeleteIndividualCompetencyBtn", hasSelected);	
}



function promptDeleteIndividualCompetency() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected record?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteIndividualCompetency(individualCompetencyTable);
			   }
	);
}


function deleteIndividualCompetency(tbl) {
	var postUrl = "/rest/ignite/v1/individual-sd-role/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Record has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateIndividualCompetencyToolbarButtons();   // Disable delete button when record has been deleted.
			});
}



//individualCompetencyDialog -- Start
/////////////////////////////////////
function editIndividualCompetency(rowSelector, individualId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = individualCompetencyTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

		if ((individualId == undefined) || (individualId == null)){
			individualId = data.individualId;
		}
	}
	individualCompetencyTable.rows().deselect();

	$("#isdriIndividualSdRoleId").val(data.individualSdRoleId);
	$("#isdriIndividualId").val($("#eIndIndividualId").val());
	
	$("#isdriSdRoleId").val(data.sdRoleId);
	$("#isdriYearExperience").val(data.yearExperience);
	
	if (data.sdParentName == data.sdIndustryName) {
		$("#isdriSdParentName").val("");
	} else {
		$("#isdriSdParentName").val(data.sdParentName);
	}
	if (data.sdGrandParentName == data.sdIndustryName) {
		$("#isdriSdGrandParentName").val("");
	} else {
		$("#isdriSdGrandParentName").val(data.sdGrandParentName);
	}
	$("#isdriSdIndustryName").val(data.sdIndustryName);	
	
	
	$("#isdriSdRoleName").val(data.combinedName);
		
	$("#isdriCompetencyLevel").val(data.competencyLevelId);
	populateSelect("isdriCompetencyLevel", 
		       "/rest/ignite/v1/competency-level", 
		       "competencyLevelId", 
		       "name", 
		       data.competencyLevelId, 
		       true,
		       null 
	);
	
	// Set the Save Button to disabled
	setElementEnabled("saveIndividualCompetencyBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("individualCompetencyDialog");
} //editIndividualCompetency -- End



function saveIndividualCompetencyDialog() {
	
	var postUrl = "/rest/ignite/v1/individual-sd-role/new";
	
	var postData = {
			individualSdRoleId: $("#isdriIndividualSdRoleId").val(),
			individualId: $("#isdriIndividualId").val(),
			sdRoleId: $("#isdriSdRoleId").val(),
			competencyLevelId: $("#isdriCompetencyLevel").val(),
			yearExperience: $("#isdriYearExperience").val()
	};

	var errors = "";
	
	// validate
	if ((postData.sdRoleId == null) || (postData.sdRoleId == "")) {
		errors += "Please select a Service Discipline Role.<br>";
	}
	
	if ((postData.competencyLevelId == null) || (postData.competencyLevelId == "")) {
		errors += "A Competency Level is required.<br>";
	}
	
	if ((postData.yearExperience == null) || (postData.yearExperience == "")) {
		errors += "Please enter the number of years Experience.<br>";
	} else {
		if (isNaN(postData.yearExperience)) {
			errors += "Please enter a valid number for years Experience.<br>";
		} else {
			if ((postData.yearExperience > 60) || (postData.yearExperience < 0)) {
				errors += "Please enter a valid number for years Experience.<br>";
			};
			if (postData.yearExperience % 1 === 0){
				   // yes it's an integer.
			} else {
				errors += "Please use an Integer for years Experience.<br>";
			};
			
		}
	}		
	
	if (showFormErrors("icDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.individualSdRoleId != null) && (postData.individualSdRoleId != "")) {
		var postUrl = "/rest/ignite/v1/individual-sd-role";
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "individualCompetencyDialog", "The Individual Competency has been saved.", individualCompetencyTable);
} //saveIndividualCompetencyDialog -- End


function closeIndividualCompetencyDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("individualCompetencyDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("individualCompetencyDialog");
	};
	somethingChangedInDialog = false;

} //closeIndividualCompetencyDialog -- End


function editSelectSdRolei() {
	selectSdRole("isdriSdRoleId", "isdriSdRoleName", "isdriSdParentName", "isdriSdGrandParentName", "isdriSdIndustryName");   //  <-- this is on humanResourceTab.js
}



function individualCompetencyDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveIndividualCompetencyBtn", true);
} // individualCompetencyDialogChanged -- End
//// individualCompetencyDialog -- END



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//*************************************************************************************************************************************************//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//Qualification
function initializeIndividualQualificationListTable(participantId, individualId){
	
	var queryUrl="/rest/ignite/v1/individual-qualification/list-view-individual-qualification-for-participant/" + participantId; 

	
	var columnsArray = [
	           		 { data: "individualQualificationId" } //0 MySql-TableName: VIndividualQualification
	           		,{ data: "individualId" }             //1
	           		,{ data: "individualId_FirstName" }   //2
	           		,{ data: "individualId_LastName" }    //3
	           		,{ data: "participantId" }            //4
	           		,{ data: "instituteQualificationId" } //5
	           		,{ data: "instituteQualificationId_Name" } //6
	           		,{ data: "studyInstituteId_Name" }    //7
	           		,{ data: "yearCompleted" }            //8
	           		,{ data: "combinedQualification" }    //9
	           		,{ data: "description" }    //10 description
	           	];

	           	var columnDefsArray = [
	           		{
	           			visible: false,
	           			targets: [0, 1, 2, 3, 4, 5, 6, 7, 10]
	           		}
	           	];

	
	var buttonsArray = [
  		{
  			titleAttr: "New",
  			text: "<i class='fas fa-plus'></i>",
  			className: "btn btn-sm",
  			action: function( e, dt, node, config ) {
  				editIndividualQualification(null, individualId);
  			}
  		},
  		{
  			attr: {
  				id: "promptDeleteIndividualQualificationBtn"
  			},
  			titleAttr: "Delete",
  			text: "<i class='fas fa-minus'></i>",
  			className: "btn btn-sm",
  			action: function() {
  				promptDeleteIndividualQualification();
  			}
  		}
  	];
	
	individualQualificationTable = initializeGenericTable("individualQualificationTable", 
											queryUrl,
								            columnsArray,
								            columnDefsArray,
								            buttonsArray,
								            function(rowSelector) {
												editIndividualQualification(rowSelector, individualId);
											},
											null,
											10,
											[[8,"desc"]], //Order by column 0 ascending, normally defaults to column 1 ascending
											null,       //tableHeightPixels (ignored if you have pagelength)
										    false,    //showTableInfo   (Showing 0 to 5 etc....)
											true   //showFilter												
								);

	individualQualificationTable.off('deselect');
	individualQualificationTable.on('deselect', function (e, dt, type, indexes) {
		updateIndividualQualificationTableToolbarButtons();
	} );
	
	individualQualificationTable.off('select');
	individualQualificationTable.on('select', function (e, dt, type, indexes) {
		updateIndividualQualificationTableToolbarButtons();
	} );	

	updateIndividualQualificationTableToolbarButtons();	

} // initializeIndividualQualificationTable -- End


         
function updateIndividualQualificationTableToolbarButtons() {
	var hasSelected = individualQualificationTable.rows('.selected').data().length > 0;
	setTableButtonState(individualQualificationTable, "promptDeleteIndividualQualificationBtn", hasSelected);	
}  // updateIndividualQualificationTableToolbarButtons



function promptDeleteIndividualQualification() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected record?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteIndividualQualification(individualQualificationTable);
			   }
	);
}


function deleteIndividualQualification(tbl) {
	var postUrl = "/rest/ignite/v1/individual-qualification/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Record has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateIndividualQualificationTableToolbarButtons();   // Disable delete button when record has been deleted.
			});
}



//individualQualificationDialog -- Start
/////////////////////////////////////
function editIndividualQualification(rowSelector, individualId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = individualQualificationTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 

	}
	individualQualificationTable.rows().deselect();
	
	//  MySql-TableName: VIndividualQualification										   (js3Str)
	$("#iqIndividualQualificationId").val(data.individualQualificationId); //0
	$("#iqIndividualId").val(individualId);                     //1

	$("#iqInstituteQualificationId").val(data.instituteQualificationId); //5

	$("#iqYearCompleted").val(data.yearCompleted);                   //8
	$("#iqDescription").val(data.description);                       //9
	$("#iqInstituteQualificationName").val(data.combinedQualification);   //10
	
	// Set the Save Button to disabled
	setElementEnabled("saveIndividualQualificationBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("individualQualificationDialog");
} //editIndividualQualification -- End



function saveIndividualQualificationDialog() {

	let datum =  new Date().getFullYear();
	
	var postUrl = "/rest/ignite/v1/individual-qualification/new";
	var postData = {
		individualQualificationId : $("#iqIndividualQualificationId").val() //0 MySql-TableName: IndividualQualification
		,individualId : $("#iqIndividualId").val()                                  //1
		,instituteQualificationId : $("#iqInstituteQualificationId").val()          //2
		,yearCompleted : $("#iqYearCompleted").val()                                //3
		,description : $("#iqDescription").val()                                    //4
	};

	var errors = "";
	
	// validate
	if ((postData.instituteQualificationId == null) || (postData.instituteQualificationId == "")) {
		errors += "Please select a Qualification.<br>";
	}
		
	if ((postData.yearCompleted == null) || (postData.yearCompleted == "")) {
		errors += "Please enter the Year.<br>";
	} else {
		if (isNaN(postData.yearCompleted)) {
			errors += "Please enter a valid Year.<br>";
		} else {
			if ((postData.yearCompleted > datum) || (postData.yearCompleted < 1930)) {
				errors += "Please enter a valid Year.<br>";
			};
			if (postData.yearCompleted % 1 === 0){
				   // yes it's an integer.
			} else {
				errors += "Please enter a valid Year.<br>";
			};
			
		}
	}		
	
	if (showFormErrors("iqDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.individualQualificationId != null) && (postData.individualQualificationId != "")) {
		var postUrl = "/rest/ignite/v1/individual-qualification";
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "individualQualificationDialog", "The Qualification has been saved.", individualQualificationTable);
} //saveIndividualQualificationDialog -- End


function closeIndividualQualificationDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("icDlgErrorDiv", "none");
				closeModalDialog("individualQualificationDialog");
			});
	} else {
		setDivVisibility("icDlgErrorDiv", "none");
		closeModalDialog("individualQualificationDialog");
	};
	somethingChangedInDialog = false;

} //closeIndividualQualificationDialog -- End


function editSelectInstituteQualification() {
	selectInstituteQualification("iqInstituteQualificationId", "iqInstituteQualificationName");
}



function selectInstituteQualification(targetId, targetName) {    
	
	var queryUrl="/rest/ignite/v1/institute-qualification/find-all-in-view";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="instituteQualificationId";
			var refColumnName="combinedName";
			var columns = [
				{ data: "instituteQualificationId", name: "instituteQualificationId" },
				{ data: "name", name: "Qualification" },
				{ data: "studyInstituteId_Name", name: "Institute" },
				{ data: "combinedName", name: "CombinedName" },
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0, 3]
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.instituteQualificationId;
				var repName = row.combinedName;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
} //selectInstituteQualification  --  End



function individualQualificationDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveIndividualQualificationBtn", true);
} // individualQualificationDialogChanged -- End
//// individualQualificationDialog  --  END

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//*************************************************************************************************************************************************//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




//Prof Registration
function initializeIndividualProfRegistrationListTable(participantId, individualId){
	
	var queryUrl="/rest/ignite/v1/individual-prof-registration/list-view-individual-prof-registration-for-participant/" + participantId; 

	var columnsArray = [
	           		 { data: "individualProfRegistrationId" } //0 MySql-TableName: VIndividualProfRegistration
	           		,{ data: "individualId" }             //1
	           		,{ data: "individualId_FirstName" }   //2
	           		,{ data: "individualId_LastName" }    //3
	           		,{ data: "participantId" }            //4
	           		,{ data: "professionalInstituteId" }  //5
	           		,{ data: "professionalInstituteId_Name" } //6
	           		,{ data: "yearAccepted" }             //7
	           		,{ data: "profNumber" }               //8
	           		,{ data: "description" }              //9
	           	];

	           	var columnDefsArray = [
	           		{
	           			visible: false,
	           			targets: [0, 1, 2, 3, 4, 5, 9]
	           		}
	           	];

	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editIndividualProfRegistration(null, individualId);
			}
		},
		{
			attr: {
				id: "promptDeleteIndividualProfRegistrationBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteIndividualProfRegistration();
			}
		}
	];
	
	individualProfRegistrationTable = initializeGenericTable("individualProfRegistrationTable", 
											queryUrl,
								            columnsArray,
								            columnDefsArray,
								            buttonsArray,
								            function(rowSelector) {
												editIndividualProfRegistration(rowSelector, individualId);
											},
											null,
											10,
											[[7,"desc"]], //Order by column 0 ascending, normally defaults to column 1 ascending
											null,       //tableHeightPixels (ignored if you have pagelength)
										    false,    //showTableInfo   (Showing 0 to 5 etc....)
											true   //showFilter												
								);

	individualProfRegistrationTable.off('deselect');
	individualProfRegistrationTable.on('deselect', function (e, dt, type, indexes) {
		updateIndividualProfRegistrationTableToolbarButtons();
	} );
	
	individualProfRegistrationTable.off('select');
	individualProfRegistrationTable.on('select', function (e, dt, type, indexes) {
		updateIndividualProfRegistrationTableToolbarButtons();
	} );	

	updateIndividualProfRegistrationTableToolbarButtons();	

} // initializeIndividualProfRegistrationTable -- End


       
function updateIndividualProfRegistrationTableToolbarButtons() {
	var hasSelected = individualProfRegistrationTable.rows('.selected').data().length > 0;
	setTableButtonState(individualProfRegistrationTable, "promptDeleteIndividualProfRegistrationBtn", hasSelected);	
}  // updateIndividualProfRegistrationTableToolbarButtons



function promptDeleteIndividualProfRegistration() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected record?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteIndividualProfRegistration(individualProfRegistrationTable);
			   }
	);
}


function deleteIndividualProfRegistration(tbl) {
	var postUrl = "/rest/ignite/v1/individual-prof-registration/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Record has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateIndividualProfRegistrationTableToolbarButtons();   // Disable delete button when record has been deleted.
			});
}



//individualProfRegistrationDialog -- Start
/////////////////////////////////////
function editIndividualProfRegistration(rowSelector, individualId) {
	var data = {}; // Give it an empty object (so, need to add a new record)

	if (rowSelector != null) {
		data = individualProfRegistrationTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
	}
	individualProfRegistrationTable.rows().deselect();
	
	//  MySql-TableName: VIndividualProfRegistration										   (js3Str)
	$("#iprIndividualProfRegistrationId").val(data.individualProfRegistrationId); //0
	$("#iprIndividualId").val(individualId);                     //1

	$("#iprProfessionalInstituteId").val(data.professionalInstituteId); //5

	$("#iprProfessionalInstituteName").val(data.professionalInstituteId_Name); //6
	$("#iprYearAccepted").val(data.yearAccepted);                     //7
	$("#iprProfNumber").val(data.profNumber);                         //8
	$("#iprDescription").val(data.description);                       //9
	
	// Set the Save Button to disabled
	setElementEnabled("saveIndividualProfRegistrationBtn", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;
	
	showModalDialog("individualProfRegistrationDialog");
} //editIndividualProfRegistration -- End



function saveIndividualProfRegistrationDialog() {

	let datum =  new Date().getFullYear();
	
	var postUrl = "/rest/ignite/v1/individual-prof-registration/new";
	var postData = {
		individualProfRegistrationId : $("#iprIndividualProfRegistrationId").val() //0 MySql-TableName: IndividualProfRegistration
		,individualId : $("#iprIndividualId").val()                                  //1
		,professionalInstituteId : $("#iprProfessionalInstituteId").val()            //2
		,yearAccepted : $("#iprYearAccepted").val()                                  //3
		,profNumber : $("#iprProfNumber").val()                                      //4
		,description : $("#iprDescription").val()                                    //5
	};

	var errors = "";
	
	// validate
	if ((postData.professionalInstituteId == null) || (postData.professionalInstituteId == "")) {
		errors += "Please select an Institute.<br>";
	}
		
	if ((postData.yearAccepted == null) || (postData.yearAccepted == "")) {
		errors += "Please enter the Year.<br>";
	} else {
		if (isNaN(postData.yearAccepted)) {
			errors += "Please enter a valid Year.<br>";
		} else {
			if ((postData.yearAccepted > datum) || (postData.yearAccepted < 1930)) {
				errors += "Please enter a valid Year.<br>";
			};
			if (postData.yearAccepted % 1 === 0){
				   // yes it's an integer.
			} else {
				errors += "Please enter a valid Year.<br>";
			};
			
		}
	}		
	
	if (showFormErrors("iprDlgErrorDiv", errors)) {
		return;
	}

	if ((postData.individualProfRegistrationId != null) && (postData.individualProfRegistrationId != "")) {
		var postUrl = "/rest/ignite/v1/individual-prof-registration";
	}

	// saveEntry (Save data in , Data to save, Name of dialog box to close, Message displayed, Name of table to be refreshed)
	saveEntry(postUrl, postData, "individualProfRegistrationDialog", "The Record has been saved.", individualProfRegistrationTable);
} //saveIndividualProfRegistrationDialog -- End


function closeIndividualProfRegistrationDialog() {
	if ((somethingChangedInDialog)) {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				setDivVisibility("iprDlgErrorDiv", "none");
				closeModalDialog("individualProfRegistrationDialog");
			});
	} else {
		setDivVisibility("iprDlgErrorDiv", "none");
		closeModalDialog("individualProfRegistrationDialog");
	};
	somethingChangedInDialog = false;

} //closeIndividualProfRegistrationDialog -- End


function editSelectProfessionalInstitute() {
	selectProfessionalInstitute("iprProfessionalInstituteId", "iprProfessionalInstituteName");
}



function selectProfessionalInstitute(targetId, targetName) {    
	
	var queryUrl="/rest/ignite/v1/professional-institute/find-all";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="professionalInstituteId";
			var refColumnName="combinedName";
			var columns = [
				{ data: "professionalInstituteId", name: "professionalInstituteId" },
				{ data: "name", name: "Institute" },
				{ data: "description", name: "Description" },
			];
			var columnDefs = [
				{ 
					visible: false,
					targets: [0]
				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.professionalInstituteId;
				var repName = row.name;
				$("#" + targetId).val(id);
				$("#" + targetName).val(repName);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
} //selectProfessionalInstitute  --  End



function individualProfRegistrationDialogChanged() {
	askToSaveDialog = true;
	somethingChangedInDialog = true;
	setElementEnabled("saveIndividualProfRegistrationBtn", true);
} // individualProfRegistrationDialogChanged -- End
////individualProfRegistrationDialog  --  END

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//*************************************************************************************************************************************************//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
