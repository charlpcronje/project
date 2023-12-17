
function loadTemplate() {
	var data = [
		{
			anyChildren: "Y",
			description: "",
			id: 1,
			level: 1,
			name: "CONTRACT ABC",
			orderNumber: 1,
			parentName: null,
			children: [
				{
					anyChildren: "Y",
					description: "",
					id: 1,
					level: 1,
					name: "SCHEDULE A",
					orderNumber: 1,
					parentName: null,
					children : [
						{
							anyChildren: "Y",
							description: "",
							id: 1,
							level: 1,
							name: "GENERAL",
							orderNumber: 1,
							parentName: null,
						},
						{
							anyChildren: "Y",
							description: "",
							id: 1,
							level: 1,
							name: "SITE CLEARANCE",
							orderNumber: 1,
							parentName: null,
						},
						{
							anyChildren: "Y",
							description: "",
							id: 1,
							level: 1,
							name: "EARTHWORKS",
							orderNumber: 1,
							parentName: null,
						}
					]
				}
			]
		}
	];
	
	var tree = $("#tfsTree").tree({
		data: data
	});
}

//function showSoqScheduleDialog() {
//	showModalDialog("soqScheduleDialog");
//}
//
//function showSoqSubScheduleDialog() {
//	showModalDialog("soqSubScheduleDialog");
//}
//
//function showSoqTemplateItemDialog() {
//	showModalDialog("soqTemplateItemDialog");
//}

$(document).ready(function() {
	// Any initialization

	var tree = $("#tfsTree").tree();

} );