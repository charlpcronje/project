var refDataItems = [
	{name: "Asset Condition", link: "/setup-asset-condition", permission: "menu-ignite"},
	{name: "Asset Type", link: "/setup-asset-type", permission: "menu-ignite"},
	{name: "Asset Status", link: "/setup-asset-status", permission: "menu-ignite"},
	{name: "Bank Account Type", link: "/setup-bank-account-type", permission: "menu-ignite"},
	{name: "Bank & Branch", link: "/setup-bank", permission: "menu-ignite"},
	{name: "Card Type", link: "/setup-card-type", permission: "menu-ignite"},
	{name: "City", link: "/setup-city", permission: "menu-ignite"},
	{name: "Competency Level", link: "/setup-competency-level", permission: "menu-ignite"},
	{name: "Country", link: "/setup-country", permission: "menu-ignite"},
	{name: "Expense Type", link: "/setup-expense-type", permission: "menu-ignite"},
	{name: "Medical Insurance Company", link: "/setup-medical-insurance-company", permission: "menu-ignite"},
//	{name: "Participant Status", link: "/setup-participant-status", permission: "menu-ignite"},
	{name: "Payment Method", link: "/setup-payment-method", permission: "menu-ignite"},
	{name: "Payment Type", link: "/setup-payment-type", permission: "menu-ignite"},
	{name: "Professional Institute", link: "/setup-professional-institute", permission: "menu-ignite"},  
	{name: "Project Based Remuneration Type", link: "/setup-proj-based-remun-type", permission: "menu-ignite"},
	{name: "Project Stage Type", link: "/setup-project-stage-type", permission: "menu-ignite"},
	{name: "Province", link: "/setup-province", permission: "menu-ignite"},
	{name: "Remuneration Model", link: "/setup-remuneration-model", permission: "menu-ignite"},
	{name: "Resource Remuneration Type", link: "/setup-resource-remun-type", permission: "menu-ignite"},
	{name: "Role on a Project", link: "/setup-role-on-a-project", permission: "menu-ignite"},
//	{name: "Service", link: "/setup-service", permission: "menu-ignite"},  
	{name: "Study Institute", link: "/setup-study-institute", permission: "menu-ignite"},  
	{name: "Tap Subscription", link: "/setup-tap-subscription", permission: "menu-ignite"},
	{name: "Tax Deductable Category", link: "/setup-tax-deductable-category", permission: "menu-ignite"},
	{name: "Unit Type", link: "/setup-unit-type", permission: "menu-ignite"},
	{name: "Deliverable", link: "/setup-deliverable-type", permission: "menu-ignite"},
	{name: "Vehicle Make", link: "/setup-vehicle-make", permission: "menu-ignite"},
	{name: "Vehicle Item", link: "/setup-vehicle-item-type", permission: "menu-ignite"},
	{name: "Vehicle Maintenance", link: "/setup-vehicle-maintenance-type", permission: "menu-ignite"}
];

function initializeReferenceData() {
	var columnsArray = [
		{ "data" : "name" },
        { "data" : "link" },
        { "data" : "permission" }
	];
	
	var columnDefsArray = [
		{
			visible: false,
			targets: [1, 2]
		}
		/*
		// Example of rendering a column with a badge
		,
		{
			render: function(data, type, row) {
				var html = data;
				
				if (type == "display") {
					html = "<span class='badge badge-success'>" + data + "</span>";
				}
				
				return html;
			},
			targets: 0
		}
		*/
	];
	
	var buttonsArray = [];
	
	var tbl = initializeJsonTable("referenceDataTable", refDataItems, columnsArray, columnDefsArray, 
		                                 buttonsArray, null);
		              
	$("#referenceDataTable tbody").on( 'click', 'tr', function () {
        var data = tbl.row( this ).data();
        
        //console.dir(data);
        var link = springUrl(data.link);
        
        // set the source of the frame to the link in the object
        $("#referenceDataFrame").attr("src", link);  
    } );
              
}

// ***********************************************************************

$(document).ready(function() {
	// Any initialization
	initializeReferenceData();
} );
