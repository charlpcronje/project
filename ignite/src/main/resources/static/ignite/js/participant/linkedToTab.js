var humanResourceOfTable = null;

//********************** Participant is linked To the following *********************** //
//initializeLinkedToTab -- Start

function initializeLinkedToTab(participantId, individualId){
	if (individualId > 0) {
		initializeIndividualResourceOf(participantId);
		initializeRepresentativeOfTable(individualId);
		initializeMarketingRepOfTable(individualId);
		setDivVisibility("humanResourceOfPanel", "block");
	} else {
		setDivVisibility("humanResourceOfPanel", "none");
	}
	
	initializeParticipantOfProjectTable(participantId);
}

function initializeIndividualResourceOf(participantId){

	showEmptyRemunerationPanel();
	showEmptyCompetencyPanel();
	$("#selectedIndividualId").val("");
	
	var queryUrl="/rest/ignite/v1/non-project-related-agreement/resource-of/" + participantId; 
	
	var columnsArray = [
		{ data: "nonProjectRelatedAgreementId" },	//0
		{ data: "participantIdPayer" },				//1
		{ data: "systemNamePayer" },			//2
		{ data: "participantIdBeneficiary" },	//3
		{ data: "systemNameBeneficiary" },		//4
		{ data: "resourceTypeId" },			//5
		{ data: "resourceTypeName" },			//6
		{ data: "description" },				//7
		{ data: "startDate" },					//8
		{ data: "endDate" },					//9
		{ data: "individualId" }				//10
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1,  3, 4, 5, 7, 8, 9, 10]
		}
	];
	
	var buttonsArray = [];
	
	humanResourceOfTable = initializeGenericTable("humanResourceOfTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            null			// Double Click function
										,null     //completeMethod
									    ,14      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[2,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);

}


//initializeRepresentativeOfTable -- Start
function initializeRepresentativeOfTable(individualId){

	var queryUrl="/rest/ignite/v1/participant/representative-of/" + individualId; 
	
	var columnsArray = [
			{ data: "participantId" },					//0
			{ data: "systemName" },						//1 
			{ data: "registeredName" },					//2 Registered Name
			{ data: "tradingName" }						//3
			
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1]
		}
	];
	
	var buttonsArray = [];
	
	representativeOfTable = initializeGenericTable("representativeOfTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            null			// Double Click function
										,null     //completeMethod
									    ,14      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[1,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);

}
//initializeRepresentativeOfTable -- End

//initializeMarketingRepOfTable -- Start
function initializeMarketingRepOfTable(individualId){

	var queryUrl="/rest/ignite/v1/participant/marketing-rep-of/" + individualId; 
	
	var columnsArray = [
			{ data: "participantId" },					//0
			{ data: "systemName" },						//1 
			{ data: "registeredName" },					//2 Registered Name
			{ data: "tradingName" }						//3
			
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0,1]
		}
	];
	
	var buttonsArray = [];
	
	marketingRepOfTable = initializeGenericTable("marketingRepOfTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            null			// Double Click function
										,null     //completeMethod
									    ,14      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[1,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);

}
//initializeMarketingRepOfTable -- End




//initializeParticipantOfProjectTable -- Start

function initializeParticipantOfProjectTable(participantId){

	showEmptyRolesOnProjectPanel();
	
	var queryUrl="/rest/ignite/v1/project-participant/for-participant/" + participantId ;
	
	var columnsArray = [
		{ data: "projectParticipantId" },		//0
		{ data: "participantIdHost" },			//1
		{ data: "participantNameHost" },		//2
		{ data: "participantNameLevel1" },		//3
		{ data: "projectNumberText" },			//4
		{ data: "projectName" },				//5
		{ data: "projectId" },					//6
		{ data: "subProjNumber" }				//7
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 3, 4, 6, 7]
		}
		];
	
	var buttonsArray = [];
	
	participantOfProjectTable = initializeGenericTable("participantOfProjectTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editHumanResource(participantId, aThis);
										}
										,null     //completeMethod
									    ,14      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[2,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,true   //showFilter
	);

	participantOfProjectTable.off('deselect');
	participantOfProjectTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyRolesOnProjectPanel();
	} );

	participantOfProjectTable.off('select');
	participantOfProjectTable.on('select', function (e, dt, type, indexes) {
		showRolesOnProjectTable(dt.data());
	} );
	
}

//initializeParticipantOfProjectTable -- End


function showEmptyRolesOnProjectPanel() {
	setDivVisibility("rolesOnProjectTableEmptyPanel", "block");
	setDivVisibility("rolesOnProjectTablePanel", "none");
}

//showRolesOnProjectTable -- Start

function showRolesOnProjectTable(row){
	
	if (row == null) {
		return;
	}
		
	var projectParticipantId = row.projectParticipantId

	setDivVisibility("rolesOnProjectTableEmptyPanel", "none");
	setDivVisibility("rolesOnProjectTablePanel", "block");
	
	var queryUrl="/rest/ignite/v1/project/list-only-my-sds-and-roles/" + projectParticipantId ;
	
	var columnsArray = [
		{ data: "projectParticipantId" },		//0
		{ data: "sdCode" },						//1
		{ data: "sdName" },						//2
		{ data: "roleOnAProjectName" }			//3
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0]
		}
		];
	
	var buttonsArray = [];
	
	rolesOnProjectTable = initializeGenericTable("rolesOnProjectTable", 
			                            queryUrl,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											editHumanResource(participantId, aThis);
										}
										,null     //completeMethod
									    ,14      //pageLength   used to set the table height (takes precedence over tableHeightPixels)
									    ,[1,"asc"]  //orderArray: column, asc or desc   default: [1, asc]
									    ,null       //tableHeightPixels (ignored if you have pagelength)
									    ,false    //showTableInfo   (Showing 0 to 5 etc....)
									    ,true   //showFilter
	);

}

//showRolesOnProjectTable -- End


