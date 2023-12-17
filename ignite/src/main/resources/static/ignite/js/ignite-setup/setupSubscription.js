var roleTable = null;
var permissionTable = null;

var availablePermissionsCache = null;
var assignedPermissionsCache = null;

function showCodeDescriptionDialog(title, code, name, description, saveFunction) {
	// Set the title
	$("#cdDlgTitle").text(title);
	
	// set our inputs
	$("#cdDlgCode").val(code);
	$("#cdDlgName").val(name);
	$("#cdDlgDescription").val(description);
	
	// disable code if this is an update
	$("#cdDlgCode").prop("readonly", (code != null) && (code != ""));

	// remove previous onclick events
	$('#cdDlgSaveButton').unbind('click');
	
	// set the onclick of the save
	$("#cdDlgSaveButton").on("click", function() {
		saveFunction();
	});
	
	// show the dialog
	showModalDialog("codeDescriptionDialog");
}





function initializeUiComponentTable() {
	var columnsArray = [

		  { data: "uiComponentId" },        //0
		  { data: "uiComponentTitle" },     //1
		  { data: "uiComponentLink" },      //2
		  { data: "orderNo" },              //3
		  { data: "parentUiComponentId" },  //4
		  { data: "" }                    //5
		  ,{ data: "activeInd" },            //6
		  { data: "iconClassName" },        //7
		  { data: "permissionCodeRequired" },  //8
		  { data: "rowOrderNo" },             //9
		  { data: "menuLevel" },              //10
		  { data: "subitemCount" }    	      //11

    ];
	
	var columnDefsArray = [
//		{
//			width: "140px",
//			targets: [0,1,2]
//		}
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("uiComponentParent")) {
					if (row.uiComponentParent != null) {
						data = row.uiComponentParent.uiComponentTitle; 
					}
				}
				return data;
			},
			targets: 5
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editUiComponent(null);
			}
		},
		{
			attr: {
				id: "DeleteUiComponentBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteUiComponent();
			}
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.activeInd == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 6
		}
	];

//	console.log("rest/ignite/v1/ui-component")
	
	uiComponentTable = initializeGenericTable("uiComponentTable"
			                            ,"/rest/ignite/v1/ui-component-view"
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,function(aThis) {
			                            	editUiComponent(aThis);
										}
										,null  //completeMethod
									    ,38    //pageLength
									    ,[3,"asc"]  //orderArray: column, asc or desc
									    ,null  //tableHeightPixels (ignored if you have pagelength)
									    ,false //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);	
	
	
	uiComponentTable.off('deselect');
	uiComponentTable.on('deselect', function (e, dt, type, indexes) {
		updateUiComponentToolbarButtons();
	} );

	uiComponentTable.off('select');
	uiComponentTable.on('select', function (e, dt, type, indexes) {
		updateUiComponentToolbarButtons();
	} );
	
	updateUiComponentToolbarButtons();	
	
	
} //initializeUiComponentTable -- END



function selectParentUiComponentId() {
	var queryUrl="/rest/ignite/v1/ui-component";
	
	$.ajax({
		url: springUrl(queryUrl),
		type: "GET",
		success: function(data) {
		
			var columnName="uiParentUiComponentId";
			var refColumnName="uiParentUiComponentIdName";
			var columns = [
				{ data: "uiComponentId", name: "uiComponentId" },
				{ data: "uiComponentTitle", name: "uiComponentTitle" }
//				{ data: "description", name: "Description" }
			];
			var columnDefs = [
//				{ 
//					visible: false,
//					targets: 0
//				}
			];
			selectFromGridDialog(columnName, refColumnName, columns, columnDefs, data, function(row) {
				var id = row.uiComponentId;
				var repName = row.uiComponentTitle;
				
//console.log("id:"+id)
				$("#uiParentUiComponentId").val(id);
				$("#uiParentUiComponentIdName").val(repName);
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete: function(html) {
			ajaxSuccess(html.status);
		}  
	});
} // SelectParentUiComponentId -- END









//in die saveUiComponent      activeInd : $("#uiActiveInd").is(":checked") ? "Y" : "N",
//saveUiComponent -- Begin
function saveUiComponent() {


	var postUrl = "/rest/ignite/v1/ui-component/new";
	var postData = {
			 uiComponentId: $("#uiUiComponentId").val(),
			 parentUiComponentId: $("#uiParentUiComponentId").val(),
			 permissionCodeRequired: $("#uiPermissionCodeRequired").val() == "" ? null : $("#uiPermissionCodeRequired").val(),
			 uiComponentTitle: $("#uiUiComponentTitle").val(),
			 uiComponentLink: $("#uiUiComponentLink").val(),
			 orderNo: $("#uiOrderNo").val(),
			 activeInd : $("#uiActiveInd").is(":checked") ? "Y" : "N",
			 iconClassName: $("#uiIconClassName").val(),

		 
			 
	};

	
	var errors = "";

	// validate
	
	
	
	if ((postData.uiComponentTitle == null) || (postData.uiComponentTitle == "")) {
		errors += "A uiComponentTitle is required.<br>";
	}		
	
	if ((postData.orderNo == null) || (postData.orderNo == "")) {
		errors += "An Order No is required.<br>";
	}	
	
	if (errors != "") {
		errors += "Keep in mind that duplicate values for Order No is not allowed."
	}
	
	if (showFormErrors("uicDlgErrorDiv", errors)) {
		return;
	}
	
	var theDialog = "uiComponentDialog";
	var theSentence = "The UiComponent has been saved.";
	
	if ((postData.uiComponentId != null) && (postData.uiComponentId != "")) {
		var postUrl = "/rest/ignite/v1/ui-component";	
	}  else {
		postData.uiComponentId = null;  //empty string werk nie
	}
	
	saveEntry(postUrl, postData, theDialog, theSentence, uiComponentTable);
	
}// saveUiComponent -- End





//--editUiComponent-- Start
function editUiComponent(rowSelector) {
	
	var data = {}; // Give it an empty object (so, need to add a new record)
	var disNuweEen;

	if (rowSelector != null) { //hy bestaan reeds, ons gaan hom edit.
		data = uiComponentTable.row(rowSelector).data(); //Will give us the data of the double-clicked row 
		disNuweEen = false;
	} else {  //dis 'n nuwe een.
		disNuweEen = true
	}
	
//console.dir(data)	

	uiComponentTable.rows().deselect();
	
	$("#uiUiComponentId").val(data.uiComponentId);        	// 	0
	$("#uiParentUiComponentId").val(data.parentUiComponentId);     
	$("#uiParentUiComponentIdName").val((data.uiComponentParent != null) ? data.uiComponentParent.uiComponentTitle : "");   
	
	populateSelect("uiPermissionCodeRequired", 
		       "/rest/ignite/v1/permission", 
		       "permissionCode", 
		       "name", 
		       data.permissionCodeRequired, 
		       true,
		       null 
	);	
	
	
	$("#uiUiComponentTitle").val(data.uiComponentTitle);    // 	1
	$("#uiUiComponentLink").val(data.uiComponentLink);      // 	2
	$("#uiOrderNo").val(data.orderNo);        				// 	3
	
	$("#uiActiveInd").prop("checked", data.activeInd == "Y");

	$("#uiIconClassName").val(data.iconClassName);    // 	1	
	
	
	
	showModalDialog("uiComponentDialog");

	// Set the Save Button to disabled
	setElementEnabled("saveUiComponentButton", false);
	somethingChangedInDialog = false;
	askToSaveDialog = false;	
	
}
//editUiComponent -- End





function initializeRoleTable() {
	var columnsArray = [
    	{ data: "roleCode" },
    	{ data: "name" },
    	{ data: "description" },
    ];
	
	var columnDefsArray = [
		{
			width: "140px",
			targets: [0,1,2]
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editRole(null);
			}
		},
		{
			attr: {
				id: "DeleteRoleBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteRole();
			}
		}
	];

	roleTable = initializeGenericTable("roleTable"
			                            ,"/rest/ignite/v1/role"
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,function(aThis) {
		                                    editRole(aThis);
										}
										,null  //completeMethod
									    ,7     //pageLength
									    ,[1,"asc"]  //orderArray: column, asc or desc
									    ,null  //tableHeightPixels (ignored if you have pagelength)
									    ,false //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);	
	
	
	roleTable.off('deselect');
	roleTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyChildPermissionPanel();
		showEmptyUiComponent1Panel();
		updateRoleToolbarButtons();
	} );

	roleTable.off('select');
	roleTable.on('select', function (e, dt, type, indexes) {
		showChildPermissionTable(dt.data());
		showEmptyUiComponent1Panel();
		updateRoleToolbarButtons();
	} );
	
	updateRoleToolbarButtons();	
	
	
} //initializeRoleTable -- END








function initializePermissionTable() {
	
	var columnsArray = [
    	{ data: "permissionCode" },
    	{ data: "name" },
    	{ data: "description" },
    ];
	
	var columnDefsArray = [
		{
			width: "140px",
			targets: [0,1,2]
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editPermission(null);
			}
		},
		{
			attr: {
				id: "DeletePermissionBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeletePermission();
			}
		}
	];

	permissionTable = initializeGenericTable("permissionTable"
			                            ,"/rest/ignite/v1/permission"
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,function(aThis) {
			                            	editPermission(aThis);
										}
										,null  //completeMethod
									    ,7     //pageLength
									    ,[1,"asc"]  //orderArray: column, asc or desc
									    ,null  //tableHeightPixels (ignored if you have pagelength)
									    ,false //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);	
	permissionTable.off('deselect');
	permissionTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyChildRolePanel();
		showEmptyUiComponent2Panel();
		updatePermissionToolbarButtons();
	} );

	permissionTable.off('select');
	permissionTable.on('select', function (e, dt, type, indexes) {
		showChildRoleTable(dt.data());
		showUiComponent2Table(dt.data());
		showEmptyUiComponent1Panel();   //want hy vat te veel plek
		updatePermissionToolbarButtons();

	} );
	
	updatePermissionToolbarButtons();	
	
	
} // initializePermissionTable -- END



function editRole(aThis) {
	var code = "";
	var name = "";
	var description = "";

	if (aThis != null) {
		// get values from table
		var data = roleTable.row(aThis).data();
		code = data.roleCode;
		name = data.name;
		description = data.description;
	}
	roleTable.rows().deselect();
	
	showCodeDescriptionDialog("Role", code, name, description, function() {
		saveRole();
	});
}

function editPermission(aThis) {
	var code = "";
	var name = "";
	var description = "";

	if (aThis != null) {
		// get values from table
		var data = permissionTable.row(aThis).data();
		code = data.permissionCode;
		name = data.name;
		description = data.description;
	}
	permissionTable.rows().deselect();
	
	showCodeDescriptionDialog("Permission", code, name, description, function() {
		savePermission();
	});
}



function showEmptyChildPermissionPanel() {
	setDivVisibility("rpChildPermissionPanelEmpty", "block");
	setDivVisibility("rpChildPermissionPanel", "none");
	$("h4.modal-title2").text("Permissions linked to Role");
}
function showEmptyChildRolePanel() {
	setDivVisibility("rpChildRolePanelEmpty", "block");
	setDivVisibility("rpChildRolePanel", "none");
	$("h4.modal-title5").text("Roles linked to Permission");
}


function showEmptyUiComponent1Panel() {
	setDivVisibility("rpUiComponentForPermission1PanelEmpty", "block");
	setDivVisibility("rpUiComponentForPermission1Panel", "none");
	$("h4.modal-title3").text("UiComponents linked to Permission");
}
function showEmptyUiComponent2Panel() {
	setDivVisibility("rpUiComponentForPermission2PanelEmpty", "block");
	setDivVisibility("rpUiComponentForPermission2Panel", "none");
	$("h4.modal-title6").text("UiComponents linked to Permission");
}



function showChildPermissionTable(parentRow) {

	setDivVisibility("rpChildPermissionPanelEmpty", "none");
	setDivVisibility("rpChildPermissionPanel", "block");

	$("h4.modal-title2").text("Permissions linked to " + parentRow.name);
	
	var columnsArray = [
    	{ data: "rolePermissionId" },
    	{ data: "roleCode" },
    	{ data: "permissionCode" },
    	{ data: "" },
    ];
	
	var columnDefsArray = [
		{
			width: "140px",
			targets: [0,1,2]
		}
		,{
			visible: false,
			targets: [0,1]
		}
		,{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("permission")) {
					if (row.permission != null) {
						data = row.permission.name; 
					}
				}
				return data;
			},
			targets: 3
		}
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editChildPermission(parentRow.roleCode, parentRow.name);
			}
		},
		{
			attr: {
				id: "DeleteChildPermissionBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteChildPermission();
			}
		}
	];

console.log("rest/ignite/v1/role-permission/by-role/" + parentRow.roleCode)	
	
	childPermissionTable = initializeGenericTable("childPermissionTable"
			                            ,"/rest/ignite/v1/role-permission/by-role/" + parentRow.roleCode
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,null
//			                            ,function(aThis) {
//			                            	editChildPermission(aThis);
//										}
										,null  //completeMethod
									    ,7     //pageLength
									    ,[1,"asc"]  //orderArray: column, asc or desc
									    ,null  //tableHeightPixels (ignored if you have pagelength)
									    ,false //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);	
	childPermissionTable.off('deselect');
	childPermissionTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyUiComponent1Panel();
		updateChildPermissionToolbarButtons();
	} );

	childPermissionTable.off('select');
	childPermissionTable.on('select', function (e, dt, type, indexes) {
		showUiComponent1Table(dt.data());
		updateChildPermissionToolbarButtons();
//console.dir(dt.data);		

	} );
	
	updateChildPermissionToolbarButtons();	
	
} // showChildPermissionTable -- END



function showChildRoleTable(parentRow) {
	
	setDivVisibility("rpChildRolePanelEmpty", "none");
	setDivVisibility("rpChildRolePanel", "block");
	
	$("h4.modal-title5").text("Roles linked to " + parentRow.permissionCode);
	
	var columnsArray = [
    	{ data: "rolePermissionId" },
    	{ data: "permissionCode" },
    	{ data: "roleCode" },
    	{ data: "" },
    ];
	
	var columnDefsArray = [
		{
			width: "140px",
			targets: [0,1,2]
		}
		,{
			visible: false,
			targets: [0,1]
		}
		,{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("role")) {
					if (row.role != null) {
						data = row.role.name; 
					}
				}
				return data;
			},
			targets: 3
		}
		
		
	];
	
	var buttonsArray = [
		{
			titleAttr: "New",
			text: "<i class='fas fa-plus'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				editChildRole(parentRow.permissionCode, parentRow.name);
			}
		},
		{
			attr: {
				id: "DeleteChildRoleBtn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteChildRole();
			}
		}
	];

//console.log("rest/ignite/v1/role-permission/by-permission/" + parentRow.permissionCode)	
	
	childRoleTable = initializeGenericTable("childRoleTable"
			                            ,"/rest/ignite/v1/role-permission/by-permission/" + parentRow.permissionCode
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,null
//			                            ,function(aThis) {
//			                            	editChildPermission(aThis);
//										}
										,null  //completeMethod
									    ,7     //pageLength
									    ,[1,"asc"]  //orderArray: column, asc or desc
									    ,null  //tableHeightPixels (ignored if you have pagelength)
									    ,false //showTableInfo   (Showing 0 to 5 etc....)
									    ,false   //showFilter
	);	
	childRoleTable.off('deselect');
	childRoleTable.on('deselect', function (e, dt, type, indexes) {
		showEmptyUiComponent2Panel();
		updateChildRoleToolbarButtons();
	} );

	childRoleTable.off('select');
	childRoleTable.on('select', function (e, dt, type, indexes) {
		
		showUiComponent2Table(dt.data());
		updateChildRoleToolbarButtons();
	} );
	
	updateChildRoleToolbarButtons();	
	
} // showChildRoleTable -- END




function showUiComponent1Table(parentRow) {

	setDivVisibility("rpUiComponentForPermission1PanelEmpty", "none");
	setDivVisibility("rpUiComponentForPermission1Panel", "block");

	
console.dir(parentRow)	
	
	 $("h4.modal-title3").text("UiComponents linked to " + parentRow.permissionCode);
	
	var columnsArray = [
        { data: "uiComponentId" },
        { data: "uiComponentTitle" },
        { data: "uiComponentLink" },
        { data: "orderNo" },
        { data: "parentUiComponentId" },
        { data: "" },
        { data: "activeInd" },
        { data: "iconClassName" },
        { data: "permissionCodeRequired" },
        { data: "rowOrderNo" },
        { data: "menuLevel" },
        { data: "subitemCount" }
    ];
	
	var columnDefsArray = [
//		{
//			width: "140px",
//			targets: [0,1,2]
//		}
		{
			visible: false,
			targets: [0,1]
		},
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("uiComponentParent")) {
					if (row.uiComponentParent != null) {
						data = row.uiComponentParent.uiComponentTitle; 
					}
				}
				return data;
			},
			targets: 5
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.activeInd == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 6
		}
		/*{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.is(":checked") == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 6
		}	*/
		
	];
	
	var buttonsArray = [

//		{
//			titleAttr: "New",
//			text: "<i class='fas fa-plus'></i>",
//			className: "btn btn-sm"
////			action: function( e, dt, node, config ) {
////				editChildRole(parentRow.permissionCode, parentRow.name);
////			}
//		},
		{
			attr: {
				id: "DeleteUiComponent1Btn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteUiComponent1();
			}
		}
	];

//console.log("rest/ignite/v1/ui-component/by-permission/" + parentRow.permissionCode)	
	
	uiComponent1Table = initializeGenericTable("uiComponent1Table"
			                            ,"/rest/ignite/v1/ui-component-view/by-permission/" + parentRow.permissionCode
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,null
//			                            ,function(aThis) {
//			                            	editChildPermission(aThis);
//										}
										,null  		//completeMethod
									    ,15   		//pageLength
									    ,[3,"asc"]  //orderArray: column, asc or desc
									    ,null  		//tableHeightPixels (ignored if you have pagelength)
									    ,false 		//showTableInfo   (Showing 0 to 5 etc....)
									    ,false   	//showFilter
	);	
	uiComponent1Table.off('deselect');
	uiComponent1Table.on('deselect', function (e, dt, type, indexes) {
		updateUiComponent1ToolbarButtons();
	} );

	uiComponent1Table.off('select');
	uiComponent1Table.on('select', function (e, dt, type, indexes) {
		updateUiComponent1ToolbarButtons();
	} );
	
	updateUiComponent1ToolbarButtons();	
	
} // showUiComponent1Table -- END




function showUiComponent2Table(parentRow) {

	setDivVisibility("rpUiComponentForPermission2PanelEmpty", "none");
	setDivVisibility("rpUiComponentForPermission2Panel", "block");

	 $("h4.modal-title6").text("UiComponents linked to " + parentRow.permissionCode);
	
	var columnsArray = [
        { data: "uiComponentId" },
        { data: "uiComponentTitle" },
        { data: "uiComponentLink" },
        { data: "orderNo" },
        { data: "parentUiComponentId" },
        { data: "" },
        { data: "activeInd" },
        { data: "iconClassName" },
        { data: "permissionCodeRequired" },
        { data: "rowOrderNo" },
        { data: "menuLevel" },
        { data: "subitemCount" }
    ];
	
	var columnDefsArray = [
//		{
//			width: "140px",
//			targets: [0,1,2]
//		}
		{
			visible: false,
			targets: [0,1]
		},
		{
			render: function(data, type, row) {
				data = "";
				if (row.hasOwnProperty("uiComponentParent")) {
					if (row.uiComponentParent != null) {
						data = row.uiComponentParent.uiComponentTitle; 
					}
				}
				return data;
			},
			targets: 5
		},
		{
			render: function(data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.activeInd == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets: 6
		}		
	
		
	];
	
	var buttonsArray = [
//		{
//			titleAttr: "New",
//			text: "<i class='fas fa-plus'></i>",
//			className: "btn btn-sm"
////			action: function( e, dt, node, config ) {
////				editChildRole(parentRow.permissionCode, parentRow.name);
////			}
//		},
		{
			attr: {
				id: "DeleteUiComponent2Btn"
			},
			titleAttr: "Delete",
			text: "<i class='fas fa-minus'></i>",
			className: "btn btn-sm",
			action: function() {
				promptDeleteUiComponent2();
			}
		}
	];

console.log("rest/ignite/v1/ui-component-view/by-permission/" + parentRow.permissionCode)	
	
	uiComponent2Table = initializeGenericTable("uiComponent2Table"
			                            ,"/rest/ignite/v1/ui-component-view/by-permission/" + parentRow.permissionCode
			                            ,columnsArray
			                            ,columnDefsArray
			                            ,buttonsArray
			                            ,null
//			                            ,function(aThis) {
//			                            	editChildPermission(aThis);
//										}
										,null  		//completeMethod
									    ,7     		//pageLength
									    ,[3,"asc"]  //orderArray: column, asc or desc
									    ,null  		//tableHeightPixels (ignored if you have pagelength)
									    ,false 		//showTableInfo   (Showing 0 to 5 etc....)
									    ,false   	//showFilter
	);	
	uiComponent2Table.off('deselect');
	uiComponent2Table.on('deselect', function (e, dt, type, indexes) {
		updateUiComponent2ToolbarButtons();
	} );

	uiComponent2Table.off('select');
	uiComponent2Table.on('select', function (e, dt, type, indexes) {
		updateUiComponent2ToolbarButtons();
	} );
	
	updateUiComponent2ToolbarButtons();	
	
} // showUiComponent2Table -- END







function saveRole() {
	var postUrl = "/rest/ignite/v1/role";
	var postData = {
		roleCode: $("#cdDlgCode").val(),
		name: $("#cdDlgName").val(),
		description: $("#cdDlgDescription").val()
	};
	var errors = "";
	
	// validation
	if (postData.roleCode == "") {
		errors += "A Role Code is required<br>";
	}
	
	if (showFormErrors("cdDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "codeDescriptionDialog", "The Role has been saved.", roleTable);
}



function saveRolePermissionForRoleDialog() {
	var postUrl = "/rest/ignite/v1/role-permission/new";
	var postData = {
		roleCode: $("#rpForRoleRoleCode").val(),
		permissionCode: $("#rpPermission").val()
	};
	var errors = "";
	
	// validation
	if (postData.permissionCode == "") {
		errors += "A Permission Code is required<br>";
	}
	
	if (showFormErrors("rpfrDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "rolePermissionForRoleDialog", "The RolePermission has been saved.", childPermissionTable);

}

function saveRolePermissionForPermissionDialog() {
	var postUrl = "/rest/ignite/v1/role-permission/new";
	var postData = {
		roleCode: $("#rpRole").val(),
		permissionCode: $("#rpForPermissionPermissionCode").val()
	};
	var errors = "";
	
	// validation
	if (postData.roleCode == "") {
		errors += "A Role Code is required<br>";
	}
	
	if (showFormErrors("rpfpDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "rolePermissionForPermissionDialog", "The RolePermission has been saved.", childRoleTable);

}


//function initializePermissionTable() {
//	var columnsArray = [
//    	{ data: "permissionCode" },
//    	{ data: "name" },
//    	{ data: "description" }
//    ];
//	
//	var columnDefsArray = [
//		{
//			width: "200px",
//			targets: 0
//		},
//		{
//			render: function(data, type, row) {
//				if (type == "display") {
//					data = shortenText(data, 100);
//				}
//				
//				return data;
//			},
//			targets: 1
//		}
//	];
//	
//	var buttonsArray = [
//		{
//			titleAttr: "New",
//			text: "<i class='fas fa-plus'></i>",
//			className: "btn btn-sm",
//			action: function( e, dt, node, config ) {
//				editPermission(null);
//			}
//		}
//	];
//
//	permissionTable = initializeGenericTable("permissionTable", 
//			                            "/rest/ignite/v1/permission",
//			                            columnsArray,
//			                            columnDefsArray,
//			                            buttonsArray,
//			                            function(aThis) {
//											editPermission(aThis);
//										}
//	);
//}





function editChildPermission(roleCode, roleName) {
	
	 $("#rpForRoleRoleCode").val(roleCode);
	 $("#rpForRoleRoleName").val(roleName);
	 
		populateSelect("rpPermission", 
			       "/rest/ignite/v1/role-permission/permission-not-linked/" + roleCode,
			       "permissionCode", 
			       "name", 
			       null, 
			       true,
			       null
		)
			       
	showModalDialog("rolePermissionForRoleDialog");
		
}

function editChildRole(permissionCode, permissionName) {
	
	 $("#rpForPermissionPermissionCode").val(permissionCode);
	 $("#rpForPermissionPermissionName").val(permissionName);
	 
		populateSelect("rpRole", 
			       "/rest/ignite/v1/role-permission/role-not-linked/" + permissionCode,
			       "roleCode", 
			       "name", 
			       null, 
			       true,
			       null
		)
			       
	showModalDialog("rolePermissionForPermissionDialog");
		
}


function savePermission() {
	var postUrl = "/rest/ignite/v1/permission";
	var postData = {
		permissionCode: $("#cdDlgCode").val(),
		name: $("#cdDlgName").val(),
		description: $("#cdDlgDescription").val()
	};
	var errors = "";
	
	// validation
	if (postData.permissionCode == "") {
		errors += "A Permission Code is required<br>";
	}
	
	if (postData.name == "") {
		errors += "A Name is required<br>";
	}
	
	if (showFormErrors("cdDlgErrorDiv", errors)) {
		return;
	}

	saveEntry(postUrl, postData, "codeDescriptionDialog", "The Permission has been saved.", permissionTable);
}


function initializeRolePermissions() {
	populateSelect("rpRoleSelect", 
			       "/rest/ignite/v1/role", 
			       "roleCode", 
			       "name", 
			       null, 
			       false, 
			       function() {
		             // show first option details
		             selectRole();
		             updateSelectButtons();
                   });
}


function updateSelectButtons() {
	var selectedRole = $("#rpRoleSelect").val();
	var availablePermission = $("#availablePermissions").val();
	var assignedPermission = $("#assignedPermissions").val();

	setElementEnabled("assignPermissionBtn", (availablePermission != null) && (availablePermission != ""));
	setElementEnabled("revokePermissionBtn", (assignedPermission != null) && (assignedPermission != ""));

}

function selectRole(selectedAvailable, selectedAssigned) {
	var selectedRole = $("#rpRoleSelect").val();

	if (selectedAvailable === undefined) {
		selectedAvailable = null;
	}
	if (selectedAssigned === undefined) {
		selectedAssigned = null;
	}
	
	if (selectedRole == null) {
		emptySelect("availablePermissions");
		emptySelect("assignedPermissions");
	} else {
		console.log("/rest/ignite/v1/role/" + selectedRole + "/available");
		populateSelect("availablePermissions", "/rest/ignite/v1/role/" + selectedRole + "/available", "permissionCode", "name", selectedAvailable, false, function(optionData) {
			availablePermissionsCache = optionData;	
		});
		console.log("/rest/ignite/v1/role/" + selectedRole + "/assigned");
		populateSelect("assignedPermissions", "/rest/ignite/v1/role/" + selectedRole + "/assigned", "permissionCode", "name", selectedAssigned, false, function(optionData) {
			assignedPermissionsCache = optionData;
		});
	}

	window.setTimeout(function() {
		updatePermissionFilter();
		updateSelectButtons();
	}, 200);
}


function assignSelectedPermission() {
	var selectedRole = $("#rpRoleSelect").val();
	var selectedPermission = $("#availablePermissions").val();

	console.log(selectedRole);
	console.log(selectedPermission);
	if ((selectedRole == null) || (selectedPermission == null)) {
		return;
	}

	var postUrl = "/rest/ignite/v1/role/assign";
	var postData = {
		roleCode: selectedRole,
		permissionCode: selectedPermission
	};
	console.log(postData);
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
    	type: "POST",
    	url: springUrl(postUrl),
        data: JSON.stringify(postData),
		contentType: "application/json",
		beforeSend: function(xhr) {
			if ((!(header === undefined)) && (header != null)) {
				xhr.setRequestHeader(header, token);
			}
		},
		success: function(response, statusText, xhr) {
			selectRole(null, selectedPermission); // to update available options
        },
        error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
        }
    });	
}

function revokeSelectedPermission() {
	var selectedRole = $("#rpRoleSelect").val();
	var selectedPermission = $("#assignedPermissions").val();
	
	if ((selectedRole == null) || (selectedPermission == null)) {
		return;
	}

	var postUrl = "/rest/ignite/v1/role/revoke";
	var postData = {
		roleCode: selectedRole,
		permissionCode: selectedPermission
	};
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
    	type: "POST",
    	url: springUrl(postUrl),
        data: JSON.stringify(postData),
		contentType: "application/json",
		beforeSend: function(xhr) {
			if ((!(header === undefined)) && (header != null)) {
				xhr.setRequestHeader(header, token);
			}
		},
		success: function(response, statusText, xhr) {
			selectRole(selectedPermission, null); // to update available options
        },
        error: function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
        }
    });
}

//Source:
//https://stackoverflow.com/questions/39782753/filter-options-of-a-select-with-javascript
function updatePermissionFilter() {
	var val = $("#filterInput").val();
	
	updatePermissionSelect("availablePermissions", val, availablePermissionsCache);
	updatePermissionSelect("assignedPermissions", val, assignedPermissionsCache);
}

function updatePermissionSelect(selectId, filterValue, optionsArray) {
    var select = $("#" + selectId); //document.getElementById(selectId);
    filterValue = filterValue.toLowerCase();
    
    // clear the select
    select.empty();
    
    if (optionsArray == null) {
    	return;
    }
    
    for (var foo = 0; foo < optionsArray.length; foo++) {
        var option = optionsArray[foo];

        if ((filterValue == "") || (option.title.toLowerCase().match(filterValue))) {
        	// add this item
        	var optionHtml = "<option value='" + option.id + "'" + "title='" + option.title + "'>" + option.title + "</option>";
        	select.append(optionHtml);
        }
    }
}


function updateUiComponentToolbarButtons() {
	var hasSelected = uiComponentTable.rows('.selected').data().length > 0;
	setTableButtonState(uiComponentTable, "DeleteUiComponentBtn", hasSelected);	
}

function updatePermissionToolbarButtons() {
	var hasSelected = permissionTable.rows('.selected').data().length > 0;
	setTableButtonState(permissionTable, "DeletePermissionBtn", hasSelected);	
}
function updateRoleToolbarButtons() {
	var hasSelected = roleTable.rows('.selected').data().length > 0;
	setTableButtonState(roleTable, "DeleteRoleBtn", hasSelected);	
}
 
function updateChildPermissionToolbarButtons() {
	var hasSelected = childPermissionTable.rows('.selected').data().length > 0;
	setTableButtonState(childPermissionTable, "DeleteChildPermissionBtn", hasSelected);	
}
function updateChildRoleToolbarButtons() {
	var hasSelected = childRoleTable.rows('.selected').data().length > 0;
	setTableButtonState(childRoleTable, "DeleteChildRoleBtn", hasSelected);	
}

function updateUiComponent1ToolbarButtons() {
	var hasSelected = uiComponent1Table.rows('.selected').data().length > 0;
	setTableButtonState(uiComponent1Table, "DeleteUiComponent1Btn", hasSelected);	
}
function updateUiComponent2ToolbarButtons() {
	var hasSelected = uiComponent2Table.rows('.selected').data().length > 0;
	setTableButtonState(uiComponent2Table, "DeleteUiComponent2Btn", hasSelected);	
}


function promptDeletePermission() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Permission?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deletePermission(permissionTable);
			   }
	);
}


function promptDeleteUiComponent1() {
	showDialog("Confirm?",
		       "Are you sure that you wish to unlink the selected UiComponent?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					unlinkUiComponent(uiComponent1Table);
			   }
	);
}

function promptDeleteUiComponent2() {
	showDialog("Confirm?",
		       "Are you sure that you wish to unlink the selected UiComponent?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					unlinkUiComponent(uiComponent2Table);
			   }
	);
}

function promptDeleteUiComponent() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected UiComponent?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteUiComponent(uiComponentTable);
			   }
	);
}



function promptDeleteChildPermission() {
	showDialog("Confirm?",
		       "Are you sure that you wish to remove the selected Permission?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteChildPermission(childPermissionTable);
			   }
	);
}



function promptDeleteRole() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected Role?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteRole(roleTable);
			   }
	);
		
}

function promptDeleteChildRole() {
	showDialog("Confirm?",
		       "Are you sure that you wish to remove the selected Role?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function () {
					deleteChildRole(childRoleTable);
			   }
	);
}



function unlinkUiComponent(tbl) {
	var postUrl = "/rest/ignite/v1/ui-component/unlink";  //UPDATE UiComponent SET PermissionCodeRequired = NULL
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The UiComponent was unlinked.", tbl,
			function(){	
				tbl.rows().deselect();
				updateUiComponent1ToolbarButtons();
				updateUiComponent2ToolbarButtons();
			});
}

function deleteUiComponent(tbl) {
	var postUrl = "/rest/ignite/v1/ui-component/delete";  
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The UiComponent was deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateUiComponentToolbarButtons();
			});
}


function deletePermission(tbl) {
	var postUrl = "/rest/ignite/v1/permission/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Permission has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updatePermissionToolbarButtons();
			});
}
function deleteRole(tbl) {
	var postUrl = "/rest/ignite/v1/role/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Role has been deleted.", tbl,
			function(){	
				tbl.rows().deselect();
				updateRoleToolbarButtons();
			});
}





function deleteChildPermission(tbl) {
	var postUrl = "/rest/ignite/v1/role-permission/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Permission has been removed.", tbl,
			function(){	
				tbl.rows().deselect();
				updateChildPermissionToolbarButtons();
			});
}
function deleteChildRole(tbl) {
	var postUrl = "/rest/ignite/v1/role-permission/delete";
	var row = tbl.rows('.selected').data()[0];
	saveEntry(postUrl, row, null, "The Role has been removed.", tbl,
			function(){	
				tbl.rows().deselect();
				updateChildRoleToolbarButtons();
			});
}



//closeRolePermissionForRoleDialog -- Start
function closeRolePermissionForRoleDialog() {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				closeModalDialog("rolePermissionForRoleDialog");
			}
		);
}

//	if ((somethingChangedInDialog) || (askToSaveDialog)) {
//		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
//		if (stayAndSaveFirst) {
//			//Moenie serviceLevel1Dialog toemaak nie... save eers
//		} else {
//			closeModalDialog("rolePermissionForRoleDialog");
//		}
//	} else  {
//		closeModalDialog("rolePermissionForRoleDialog");
//	}

//closeRolePermissionForRoleDialog -- End

//closeRolePermissionForPermissionDialog -- Start
function closeRolePermissionForPermissionDialog() {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				closeModalDialog("rolePermissionForPermissionDialog");
			}
	);
}
//	if ((somethingChangedInDialog) || (askToSaveDialog)) {
//		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
//		if (stayAndSaveFirst) {
//			//Moenie serviceLevel1Dialog toemaak nie... save eers
//		} else {
//			closeModalDialog("rolePermissionForPermissionDialog");
//		}
//	} else  {
//	}

//closeRolePermissionForPermissionDialog -- End

//closeUiComponentDialog -- Start
function closeUiComponentDialog() {
		showDialog("Confirm?",
			"You have unsaved changes - are you sure you wish to cancel?",
			DialogConstants.TYPE_CONFIRM,
			DialogConstants.ALERTTYPE_INFO,
			function(e) {
				closeModalDialog("uiComponentDialog");
			}
		);
}
//	if ((somethingChangedInDialog) || (askToSaveDialog)) {
//		var stayAndSaveFirst = confirm("You did not save your changes. Do you want to check your changes and save?");
//		if (stayAndSaveFirst) {
//			//Moenie uiComponentDialog toemaak nie... save eers
//		} else {
//			closeModalDialog("uiComponentDialog");
//		}
//	} else  {
//		closeModalDialog("uiComponentDialog");
//	}

//closeUiComponentDialog -- End

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	setActiveTab("setupSubscriptionTab");
	initializeRoleTable();
	initializePermissionTable();

	// Assign double click for moving permissions between available and assigned
	$("#availablePermissions").dblclick(function() {
		assignSelectedPermission();
	});

	$("#assignedPermissions").dblclick(function() {
		revokeSelectedPermission();
	});

	// react to tab change and load appropriate data
	$('.nav-tabs a').on('shown.bs.tab', function(event) {
		var target = $(event.target);
		var activeTab = target.text();
		var activeTabId = target.attr('id');
		
//console.log("activeTab: " + activeTab)	;	

		if (activeTab == "Subscription Setup") {
			initializeRoleTable();
			initializePermissionTable();
		}

		if (activeTab == "Subscribers") {
			initializeUiComponentTable();
		}
	} );
} );
