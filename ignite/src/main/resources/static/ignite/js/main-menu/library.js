var folderTree = null;
var currentFolder = null;
var filesTable = null;

function initializeLibrary(completeMethod) {
	var url = "/rest/ignite/v1/library";
	
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			showCurrentLibraryFolder();
			data = JSON.parse(data);
			displayTree(data);

			if ((completeMethod !== undefined) && (completeMethod != null)) {
				completeMethod();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});

	// get the folder
	// update the tree
	// show files in the current folder
}

function showCurrentLibraryFolder() {
	var folderName = "\\";
	if (currentFolder != null) {
		folderName = currentFolder.pathName;
	}
	
	$("#currentFolderName").html(folderName);
}

function displayTree(data, selectedFolder) {
	if (selectedFolder === undefined) {
		selectedFolder = null;
	}
	
	// release the previous instance
	$("#lTree").tree("destroy");
	
	var style=" style='100%'";
	
	folderTree = $("#lTree").tree({
        data: data,
        //showEmptyFolder: true,
        selectable: true,
        closedIcon: $('<i class="jqtree-icon fas fa-folder"' + style + '></i>'),
        openedIcon: $('<i class="jqtree-icon fas fa-folder-open"' + style + '></i'),
        onCreateLi: function(node, $li, is_selected) {
			var add = true;
			
			console.dir(node);
			if (node.hasOwnProperty("children")) {
				if (node.children.length > 0) {
					add = false;
				}
			}
			
			if (add) {
				//jqtree-toggler jqtree_common jqtree-closed jqtree-toggler-left
				// this is an empty folder
				$li.find('.jqtree-title').before('<i class="jqtree-toggler jqtree_common jqtree-toggler-left jqtree-icon far fa-folder"></i>');
			}
			
			// demo of the badge functionality
			if (node.children.length > 0) {
				var badgeHtml = "<span class='badge-right'>";
				
				badgeHtml += "<span class='badge badge-empty badge-success' " +
                                                   "title='Valid'><img src='img/blank.gif' height='0px' width='0px'></span>";
                                                   
				if (node.name.length > 20) {
					badgeHtml += "<span class='badge badge-empty badge-warning' " +
                                                   "title='Some errors exist'><img src='img/blank.gif' height='0px' width='0px'></span>";
				}                                                   

				badgeHtml += "<span class='badge badge-success'>Valid</span>";
				badgeHtml += "<span class='badge badge-warning'>Warning(s)</span>";


				badgeHtml += "</span>";
				
				$li.find(".jqtree-title").after(badgeHtml);
			}
		}
    } );

	$("#lTreePanel").css( {
		overflow: "scroll"
	});

	$("#lTree").on(
		    'tree.dblclick',
		    function(event) {
		    	event.click_event.preventDefault();
		        $("#ppTree").tree(event.node.is_open ? "closeNode" : "openNode", event.node);
				var node = event.node;
			
				// Add the parent folder info
				node.currentFolderId = node.hasOwnProperty("parent") ? node.parent.id : null;
				node.currentFolderName = node.hasOwnProperty("parent") ? node.parent.name : null;
				
				showLibraryFolderDialog(node);
		    }
    );
	
	$("#lTree").on(
		    'tree.click',
		    function(event) {
		    	event.click_event.preventDefault();
				setCurrentFolder(event.node);
		    	showFolderContents(event.node);
		    }
    );
	
	if (selectedFolder == null) {
		selectFirstNode();
	} else {
		setCurrentFolder(selectedFolder);
	}
}

function selectNodeById(folderId) {
	var node = $("#lTree").tree('getNodeById', folderId);
	
	if (node != null) {
		$("#lTree").tree("openNode", node);
		$("#lTree").tree("selectNode", node);
	}
	
	setCurrentFolder(node);
}

function selectFirstNode() {
	var data = $("#lTree").tree("getTree");

	if (data.hasOwnProperty("children")) {
		if (data.children.length > 0) {
			var node = data.children[0];
			
			setCurrentFolder(node);
			showFolderContents(node);
		}
	}
}

function setCurrentFolder(node) {
	currentFolder = node;

	showCurrentLibraryFolder();
}

function promptCreateFolder() {
	var parentFolderName = "\\";
	var parentFolderId = null;
	
	if (currentFolder != null) {
		parentFolderId = currentFolder.id;
		parentFolderName = currentFolder.name;
	}
	
	showLibraryFolderDialog({
		id: null,
		name: "",
		description: "",
		currentFolderId: parentFolderId,
		currentFolderName: parentFolderName	
	});
}

function promptDeleteFolder() {
	var folderName = currentFolder.name;
	
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the folder \"" + folderName + "\"?<br><br>" +
		       "<b>Note:</b> The folder must not contain any subfolders or files!",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
					deleteFolder();
			   }
	);
}

function deleteFolder() {
	var postUrl = "/rest/ignite/v1/library/delete";
	var postData = {
		libraryFolderId: currentFolder.id
	};
	
	saveEntry(postUrl, postData, null, "The folder has been deleted.", null, function(request, response) {
		var folderId = response;  // the delete Api call returns the parent folder Id
		// refresh and select
		initializeLibrary(function() {
			selectNodeById(folderId);
		});
	});
}

function showFolderContents(node) {
	var url = "/rest/ignite/v1/library/files";
	
	if (currentFolder != null) {
		url = url + "/" + currentFolder.id;
	}

	setDivVisibility("lEmptyPanel", node == null ? "block" : "none");
	setDivVisibility("lFilesPanel", node != null ? "block" : "none");
	
	var columnsArray = [
		{ data: "fileName" },
		{ data: "description" },
		{ data: "filesize" },
		{ data: "lastUpdateTimestamp" }
	];

	var columnDefsArray = [
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = humanFileSize(data);
				}
				return html;
			},
			targets: 2
		},
		{
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, true);
				}
				return html;
			},
			//width: "100px",
			targets: 3
		}	
	];

	var buttonsArray = [
		{
			titleAttr: "Upload",
			text: "<i class='fas fa-file-upload'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				uploadFile();
			}
		},
		{
			attr: {
				id : "downloadBtn"
			},
			titleAttr: "Download",
			text: "<i class='fas fa-file-download'></i>",
			className: "btn btn-sm",
			action: function( e, dt, node, config ) {
				promptDownload();
			}
		}
	];

	filesTable = initializeGenericTable("lFilesTable",
			                            url,
			                            columnsArray,
			                            columnDefsArray,
			                            buttonsArray,
			                            function(aThis) {
											handleFile(aThis);
										},
										null, // complete method
										null, // pagelength
										[0, "desc"] 
	);

	filesTable.on( 'select', function ( e, dt, type, indexes ) {
		updateFileTableButtons();
	} );

	filesTable.on( 'deselect', function ( e, dt, type, indexes ) {
		updateFileTableButtons();
	} );

	updateFileTableButtons();
}

function showLibraryFolderDialog(data) {
	$("#lfDlgLibraryFolderId").val(data.id);
	$("#lfdlgLibraryFolderName").val(data.name);
	$("#lfDlgDescription").val(data.description);
	$("#lfDlgParentFolderId").val(data.currentFolderId);
	$("#lfDlgParentFolderName").val(data.currentFolderName);
	
	var message = "";  // edit doesn't need a message
	
	if (data.id == null) {
		message = "Create new folder in \"" + data.currentFolderName + "\"";
	}
	
	$("#lfDlgMessage").html(message);
	
	showModalDialog("libraryFolderDialog");
}

function saveLibraryFolder() {
	var postUrl = "/rest/ignite/v1/library/save";

	var postData = {
		libraryFolderId : $("#lfDlgLibraryFolderId").val(),
		folderName : $("#lfdlgLibraryFolderName").val(),
		description : $("#lfDlgDescription").val(),
		parentFolderId : $("#lfDlgParentFolderId").val()
	};
	
	// TODO: validate, check that they have captured a name, etc
	
	// if description = null make it the same as the name
	if ((postData.description == null) || (postData.description == "")) {
		postData.description = postData.folderName;
	}

	saveEntry(postUrl, postData, "libraryFolderDialog", "The folder has been deleted.", null, function(request, response) {
		// refresh and select
		initializeLibrary(function() {
			selectNodeById(response.libraryFolderId);
		});
	});
}

function updateFileTableButtons() {
	var hasSelected = filesTable.rows('.selected').data().length > 0;

	setTableButtonState(filesTable, "downloadBtn", hasSelected);
}

function handleFile(aThis) {	
	// Click method nothing
	// maybe offer to view the file or download the file?
	console.dir(aThis);
}

function uploadFile() {
	// TODO: upload file to the currentFolder
}

function downloadFolder() {
	// TODO: complete
	// We can compress the folder and then download the compressed folder,
	// or we can call the downloads one by one... I don't know if this will be a good feature to have
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeLibrary();
} );
