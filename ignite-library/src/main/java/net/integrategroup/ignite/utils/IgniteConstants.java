package net.integrategroup.ignite.utils;

public class IgniteConstants {
	// File upload size limit in Mb
	public static final int UPLOAD_FILE_SIZE_LIMIT_MB                       = 100;

	// Can be used as a secret key/salt key, etc
	public static final String IGNITE_UNIQUE_KEY                            = "IntegrateSystems";

	// Flags for Yes/No fields
	public static final String FLAG_YES                                     = "Y";
	public static final String FLAG_NO                                      = "N";

	// Roles
	public static final String DEFAULT_ROLE                                 = "ROLE_USER";

	// Constants for Key Value Pair entries
	public static final String KEY_WELCOME_MESSAGE                          = "ui.ignite.welcome";
	public static final String KEY_ABOUT_MESSAGE                            = "ui.ignite.about";

	public static final String KEY_SITE_BASE_URL                            = "site.base.url";

	public static final String KEY_MAIL_ENABLED 							= "email.enabled";
	public static final String KEY_MAIL_SERVER_NAME 						= "email.server.name";
	public static final String KEY_MAIL_SERVER_PORT 						= "email.server.port";
	public static final String KEY_MAIL_SMTP_USERNAME 						= "email.smtp.username";
	public static final String KEY_MAIL_SMTP_PASSWORD 						= "email.smtp.password";

	public static final String KEY_MAIL_PROXY_ENABLED                       = "email.proxy.enabled";
	public static final String KEY_MAIL_PROXY_SERVER_NAME                   = "email.proxy.server.name";
	public static final String KEY_MAIL_PROXY_SERVER_PORT                   = "email.proxy.server.port";

	public static final String KEY_SCHEDULE_HEALTH_ENABLED				    = "schedule.health.enabled";
	public static final String KEY_SCHEDULE_HEALTH                          = "schedule.health.crontab";

	// Jaspersoft Report constants
	public static final String JASPER_SRC_EXTENSION                         = ".jrxml";
	public static final String JASPER_BIN_EXTENSION                         = ".jasper";

	// Report parameters
	public static final String RP_JASPER_REPORTS_PATH                       = "REPORTS_PATH";

	public static final String PROPERTIES_REPORTS_PATH						= "reports.path";
	public static final String PROPERTIES_PROJECTS_BASE_PATH                = "projects.base.path";
	public static final String GENERATE_REPORT_AS_KEY                       = "generateReportAs";
	public static final String GENERATION_TYPE                              = "generationType";  // Default to download, other option is inline
	public static final String REPORT_GENERATION_INLINE                     = "inline";

	// Report types
	public static final String REPORT_TYPE_PDF                              = ".pdf";
	public static final String REPORT_TYPE_EXCEL                            = ".xls";

	// Used to get the locations to save uploaded files
	public static final String KEY_UPLOAD_PATH_PROJECT_EXPENSE              = "upload.project.expense";
	public static final String KEY_UPLOAD_PATH_INVOICE                      = "upload.invoice";
	public static final String KEY_UPLOAD_PATH_LOGO                      	= "upload.logo";


}
