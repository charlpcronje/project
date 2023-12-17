/*
 * Javascript File Manager
 * Developed by Tony De Buys
 *
 * Requires HTML Elements for the title and body of the file manager, eg
 *
 * <div id="fileManagerPanel"></div>
 *
 */
var fileManagerUrl = null;
var fileManagerElementId = null;

var fileManagerTable = null;

function getDefaultToolbarButtons() {
	var buttonsArray = [
		{
			titleAttr : "Upload",
			text : "<i class='fas fa-upload'></i>",
			className : "btn btn-sm",
			action : function() {
				fileManagerUpload();
			}
		},
		{
			attr: {
				id: "fileManagerDownloadBtn"
			},
			requiresSelection: true,
			titleAttr : "Download",
			text : "<i class='fas fa-download'></i>",
			className : "btn btn-sm",
			action : function() {
				fileManagerDownload();
			}
		},
		{
			titleAttr : "Create folder",
			text : "<i class='fas fa-plus'></i>",
			className : "btn btn-sm",
			action : function() {
				fileManagerPromptNewDirectory();
			}
		},
		{
			attr: {
				id: "fileManagerDeleteBtn"
			},
			requiresSelection: true,
			titleAttr : "Delete",
			text : "<i class='fas fa-minus'></i>",
			className : "btn btn-sm",
			action : function() {
				fileManagerPromptDelete();
			}
		},
		{
			attr: {
				id: "fileManagerRenameBtn"
			},
			requiresSelection: true,
			titleAttr : "Rename",
			text : "<i class='fas fa-i-cursor'></i>",
			className : "btn btn-sm",
			action : function() {
				fileManagerPromptRename();
			}
		}
	];
	
	return buttonsArray;
}

function getTitleHtml(directory) {
	var title = directory;
	
	if (
		(directory === undefined) ||
		(directory == null) ||
		(directory == "")) {
		title = "/";
	}
	title = "Path: <b>" + title.replaceAll("/", "\\") + "</b>";
	
	return title;
}

function changeDirectory(directory) {
	if (directory === undefined) {
		directory = null;
	}

	fileManagerTable = initializeFileManager(fileManagerElementId, directory, fileManagerUrl);  // buttonsArray, doubleClickMethod
}

function fileManagerSelectUploadFile(fileInput) {
    $('#' + fileInput)[0].click();
}

function getUploadDialogHtml() {
	var html = '<!-- Start: fileManagerUploadDialog --> \
	<div id="fileManagerUploadDialog" class="modal fade" role="dialog" data-backdrop="static"> \
	 \
		<div class="modal-dialog"> \
			<div class="modal-content"> \
				 \
				<div class="modal-header"> \
					<h4 class="modal-title">Upload</h4> \
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> \
				</div> \
				 \
				<form method="POST"';
				
	html += ' action="' + springUrl("/file-manager/report/upload") + '"';
	
	html += ' enctype="multipart/form-data" \
					  > \
					<fieldset> \
					 \
						<div class="modal-body"> \
							<div class="row"> \
								<label class="col-md-3 col-form-label" for="fmDlgUploadFileInput">File</label> \
								<div class="col-md-8" style="padding-left: 0px !important"> \
									<div class="input-group"> \
										<input id="fmDlgUploadFileInput" type="text" class="form-control input-sm" readonly> \
										<span class="input-group-addon pointer" onclick="fileManagerSelectUploadFile()"> \
											<i class="fas fa-folder-open"></i> \
											<input id="fmDlgUploadFile" name="ruDlgUploadFile" type="file" style="display:none;"> \
										</span> \
									</div> \
								</div> \
							</div> \
						</div> \
                         \
						<div class="modal-footer"> \
							<button data-dismiss="modal" class="btn btn-sm btn-default" type="button">Cancel</button> \
							<button type="submit" class="btn btn-sm btn-primary">Upload</button> \
						</div> \
                         \
					</fieldset> \
				</form> \
				\
			</div><!-- /.modal-content --> \
		</div><!-- /.modal-dialog --> \
	</div><!-- fileManagerUploadDialog -->'
	
	return html;
}

function initializeFileManager(elementId, directory, url, buttonsArray, doubleClickMethod) {
	var html = "<h4 id='fileManagerPath'>" + getTitleHtml(directory) + "</h4>" +
	           "<input id='fileManagerCurrentDir' type='hidden' value='" + directory + "'>" +
	           "<table id='" + elementId + "_table' class='table table-striped table-advance table-hover' style='width:100%; padding-left: 10px; padding-right: 10px'>" +
	           "  <thead>" +
			   "    <tr>" +
			   "      <th>Id</th>" +
			   "      <th>Name</th>" +
			   "      <th>Modified Date</th>" +
			   "      <th>Type</th>" +
			   "      <th>Size</th>" +
			   "    </tr>" +
			   "  </thead>" +
			   "<tbody></tbody>" +
			   "</table>";
	//$("#" + elementId).html(html + getUploadDialogHtml());
	$("#" + elementId).html(html);
	
	// set our global variables - only if we havent set it before
	if (fileManagerUrl == null) {
		fileManagerElementId = elementId;
		fileManagerUrl = url;
	}
	
	var columns = [
      { data: "id" },
      { data: "name" },
      { data: "longCreationDate" },
      { data: "fileType" },
      { data: "size" }
    ];
	
	var columnDefs = [
		{
			visible: false,
			targets: 0
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					if (row.fileType == "Directory") {
						data = "<a href='#' onclick='changeDirectory(\"" + row.id + "\")'>" +
							   "<i class='document-manager fas fa-folder' style='color: #f5c400'></i> " + row.name +
							   "</a>";
					} else {
						// set as default file
						data = "<i class='document-manager far fa-file'></i> " + row.name;

						// depending on what we have select an icon
						if ((row.fileType != null) && (row.fileType != "")) {
							if (row.fileType.indexOf("PDF") > -1) { data = "<i class='document-manager far fa-file-pdf'></i> " + row.name; }
							if (row.fileType.indexOf("Powerpoint") > -1) { data = "<i class='document-manager far fa-file-powerpoint'></i> " + row.name; }
							if (row.fileType.indexOf("Word") > -1) { data = "<i class='document-manager far fa-file-word'></i> " + row.name; }
							if (row.fileType.indexOf("Excel") > -1) { data = "<i class='document-manager far fa-file-excel'></i> " + row.name; }
							if (row.fileType.indexOf("CSV") > -1) { data = "<i class='document-manager fas fa-file-excel'></i> " + row.name; }
							if (row.fileType.indexOf("Image") > -1) { data = "<i class='document-manager far fa-file-image'></i> " + row.name; }
							if (row.fileType.indexOf("Text") > -1) { data = "<i class='document-manager far fa-file-alt'></i> " + row.name; }
							if (row.fileType.indexOf("Compressed") > -1) { data = "<i class='document-manager far fa-file-archive'></i> " + row.name; }

							// there are a lot of other types, incl. contract, invoice, etc....
							// https://fontawesome.com/icons?d=gallery&q=file%20&s=solid&m=free
							// https://fontawesome.com/icons?d=gallery&q=file&s=regular&m=free
						}
					}
				}

				return data;
			},
			targets: 1
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = timestampToString(data);
				}

				return data;
			},
			targets: 2
		},
		{
			render: function(data, type, row) {
				if (type == "display") {
					data = humanFileSize(data);
				}
				
				return data;
			},
			targets: 4
		}
	];

	if (
		(buttonsArray === undefined) ||
		(buttonsArray == null)) {
		buttonsArray = getDefaultToolbarButtons();		
	}

	var urlWithId = url;
	
	if (directory != null) {
		if (urlWithId.indexOf("?") == -1) {
			urlWithId += "?";
		} else {
			urlWithId += "&";
		}
		
		urlWithId += "id=" + directory;
	}

	fileManagerTable = $("#" + elementId + "_table").DataTable( {
		ajax: {
			url: springUrl(urlWithId),
			cache: false,
			dataSrc: null,
			error: function(jqXHR, textStatus, errorThrown) {
				ajaxError(jqXHR, textStatus, errorThrown);
			},
			complete: function(html) {
				ajaxSuccess(html.status);
			}
		}, 
		select: {
			style: "single"
		},
		paging: false,
		destroy: true,
		autoWidth: true,
		rowId: columns[0].data,
		scrollY: "42dvh",
		scrollX: "100%",
		scroller: false,
		fixedHeader: true,
		scrollCollapse: true,
		processing: true,
		order: [[1, "asc"]],
		dom: "<Bfr<t>i>",
		columns: columns,		
		columnDefs: columnDefs, 
		buttons: buttonsArray,
		deferRender: true,
		preDrawCallback: function (settings) {
			pageScrollPos = $('div.dataTables_scrollBody').scrollTop();
		},
		drawCallback: function (settings) {
			if (pageScrollPos != null) {
				$('div.dataTables_scrollBody').scrollTop(pageScrollPos);
			}
	
			$('.dataTables_filter input').addClass('form-control');
			$('.dataTables_filter input').attr('placeholder', 'filter');
		}
	} );

	$("#" + elementId + "_table tbody").unbind("dblclick");
	$("#" + elementId + "_table tbody").on('dblclick', 'tr', function (event) {
		if ((doubleClickMethod !== undefined) && (doubleClickMethod != null)) {
			doubleClickMethod(this);
		}
	} );

	fileManagerTable.off('deselect');
	fileManagerTable.on('deselect', function (e, dt, type, indexes) {
		updateFileManagerToolbar(fileManagerTable, buttonsArray, null);
	} );

	fileManagerTable.off('select');
	fileManagerTable.on('select', function (e, dt, type, indexes) {
		var dat = null;
		if (dt != null) {
			dat = dt.data();
		}
		updateFileManagerToolbar(fileManagerTable, buttonsArray, dat);
	} );
	
	// to initially set the buttons
	updateFileManagerToolbar(fileManagerTable, buttonsArray, null);
	
	return fileManagerTable;
}

function updateFileManagerToolbar(table, buttonsArray, data) {
	if (buttonsArray == null) {
		return;
	}

	for (var foo = 0; foo < buttonsArray.length; foo++) {
		var btn = buttonsArray[foo];
		if ((btn.requiresSelection !== undefined) && (btn.requiresSelection)) {
			var id = btn.attr.id;

			if ((id !== undefined) && (id != null) && (id != "")) {
				var hasSelected = table.rows('.selected').data().length > 0;
				var enabled = hasSelected;
				
				if (hasSelected) {
					var row = table.rows('.selected').data()[0];
					
					if (row.directory) {
						// Don't allow the download if we're on a directory
						if (id == "fileManagerDownloadBtn") {
							enabled = false;
						}
					}
					
					if (row.name == "..") {
						// Cannot download or delete (or anything...)
						enabled = false;
					}
				}
				
				setTableButtonState(table, id, enabled);
			}
		}
	}
}

function fileManagerPromptNewDirectory() {
	inputDialog("New Folder...", 
		    "Enter a name for the new folder", 
		    DialogConstants.TYPE_PROMPT,
		    "text", 
		    "",
		    function(newDirectoryName) {
				fileManagerCreateDirectory(newDirectoryName);
			});
}

function fileManagerCreateDirectory(directoryName) {
	var row = fileManagerTable.rows('.selected').data()[0];
	var url = fileManagerUrl;
	
	var currentDir = $("#fileManagerCurrentDir").val();
	
	url = addUrlParameter(url, "target", currentDir);
	url = addUrlParameter(url, "id", directoryName);
	url = addUrlParameter(url, "action", "mkdir");

	executeUrlAndRefreshFileManager(url);
}

function executeUrlAndRefreshFileManager(url) {
	$.ajax({
		url : springUrl(url),
		type : "GET",
		success : function(data) {
			var currentDir = $("#fileManagerCurrentDir").val();

			showToast("Success", data.result, DialogConstants.ALERTTYPE_SUCCESS);
			changeDirectory(currentDir);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			ajaxError(jqXHR, textStatus, errorThrown);
		},
		complete : function(html) {
			ajaxSuccess(html.status);
		}
	});
}

function fileManagerDownload() {
	var row = fileManagerTable.rows('.selected').data()[0];
	var url = fileManagerUrl;
	
	url = addUrlParameter(url, "id", row.id);
	url = addUrlParameter(url, "action", "download");

	window.location = springUrl(url);
}

function fileManagerPromptDelete() {
	showDialog("Confirm?",
		       "Are you sure that you wish to delete the selected file/folder?",
		       DialogConstants.TYPE_CONFIRM, 
		       DialogConstants.ALERTTYPE_INFO, 
		       function (e) {
		           fileManagerDelete();
			   }
	);
}

function fileManagerDelete() {
	var row = fileManagerTable.rows('.selected').data()[0];
	var url = fileManagerUrl;
	url = addUrlParameter(url, "id", row.id);
	url = addUrlParameter(url, "action", "delete");

	executeUrlAndRefreshFileManager(url);
}

function fileManagerPromptRename() {
	var row = fileManagerTable.rows('.selected').data()[0];
	var url = fileManagerUrl;

	inputDialog("Rename", "Rename " + row.id, DialogConstants.TYPE_PROMPT, "text", row.name, function(newValue) {
		url = addUrlParameter(url, "id", row.id);
		url = addUrlParameter(url, "newName", newValue);
		url = addUrlParameter(url, "action", "rename");
		
		executeUrlAndRefreshFileManager(url);
	});
}

function fileManagerUpload() {
	var url = fileManagerUrl;
	
	$(document).on('change', ':file', function() {
	    var fileSelector = $(this);
		var filenameInput = $("#fmDlgUploadFileInput");

		var label = fileSelector.val().replace(/\\/g, '/').replace(/.*\//, '');
		filenameInput.val(label);
	});

	showModalDialog("fileManagerUploadDialog");
}
