
function initializeAbout() {
	var thisYear = (new Date()).getFullYear();

	// TODO: should this come out of the database?  Maybe a keyValuePair for about?
	var html = "<h4>Ignite: Version " + _igModel.version + "</h4>" +
	           htmlDecode(_igModel.about) +
	           "<p>" +
	           "Copyright &copy; 2020 - " + thisYear + " The Admin People" + 
	           "</p>";
	
	$("#aboutPanel").html(html);
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeAbout();
} );
