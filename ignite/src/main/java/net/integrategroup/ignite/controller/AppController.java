package net.integrategroup.ignite.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.InvoiceFile;
import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.model.ProjectExpenseFile;
import net.integrategroup.ignite.persistence.model.UiComponentView;
import net.integrategroup.ignite.persistence.model.VParticipant;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.InvoiceFileService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.persistence.service.ParticipantService;
import net.integrategroup.ignite.persistence.service.ProjectExpenseFileService;
import net.integrategroup.ignite.persistence.service.UiComponentViewService;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.RuntimeUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@Controller
public class AppController {
//	private Logger logger = Logger.getLogger(AppController.class.getName());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private RuntimeUtils runtimeUtils;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	IndividualService individualService;

	@Autowired
	UiComponentViewService uiComponentViewService;

	@Autowired
	ProjectExpenseFileService projectExpenseFileService;

	@Autowired
	InvoiceFileService invoiceFileService;

	@Autowired
	ParticipantService participantService;

	private String menuToView(HttpServletResponse response, ModelMap model, String requiredPermission, String view) {
		userInfoToModel(model);

		if ((requiredPermission != null) && (!securityUtils.hasPermission(requiredPermission))) {
			try {
				response.sendError(HttpStatus.FORBIDDEN.value());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "error";
		}

		return view;
	}

	private String getMenu(String contextPath) {
		String username = securityUtils.getUsername();
		String result = "[]";

		try {
			List<UiComponentView> menuData = uiComponentViewService.getMenu(username);
			ObjectMapper mapper = new ObjectMapper();

			// We have to build and add on the context path for when the app is deployed to
			// a server
			if ((contextPath != null) && (!contextPath.isEmpty())) {
				// make sure our contextPath does not end with "/"
				if (contextPath.endsWith("/")) {
					contextPath = contextPath.substring(0, contextPath.length() - 1);
				}

				for (UiComponentView uiComponent : menuData) {
					String link = uiComponent.getUiComponentLink();

					if ((link != null) && (!link.isEmpty())) {
						if (!link.startsWith("/")) {
							link = "/" + link;
						}

						uiComponent.setUiComponentLink(contextPath + link);
					}
				}
			}

			result = mapper.writeValueAsString(menuData);

			// don't know why null strings are being written out as "null"
			// We'll fix it by replacing all "null" with null rather...
			result = result.replaceAll("\"null\"", "null");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return result;
	}

	private void userInfoToModel(ModelMap model) {
		Individual user = securityUtils.getIndividual();

		if (user != null) {
			// TODO: figure out how we will store and use the avatar
			model.put("igUserAvatar", "avatar_unknown");
			model.put("igUsername", user.getDisplayName());
			model.put("igUserId", user.getIndividualId());
			model.put("igParticipantId", user.getParticipantId());
		}

		String contextPath = request.getContextPath();
		model.put("igMenu", getMenu(contextPath));
	}

	@GetMapping("/login") //
	public String login() {
		return "login";
	}

	@GetMapping("/logout") //
	public String logout(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		redirectAttributes.addFlashAttribute("message", "You have been logged out.");
		return "redirect:/login";
	}

	@GetMapping({ "/", "/index" })
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String message = "";

		KeyValuePair kvPair = keyValuePairService.findByKeyName(IgniteConstants.KEY_WELCOME_MESSAGE);
		if (kvPair != null) {
			message = kvPair.getContiguousValue();
		}

		model.put("welcomeMessage", message);

		// the timesheet-edit requires us to pass in an id, so we put it onto the model
		model.put("participantId", securityUtils.getIndividual().getParticipantId());

		//return menuToView(response, model, null, "index");
		return menuToView(response, model, "menu-ignite", "timesheet/timesheet-edit");
	}

	@GetMapping("/test") //
	public String testPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, null, "test");
	}

	@GetMapping("/profile") //
	public String profile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Individual individual = individualService.findByUsername(securityUtils.getUsername());
		model.put("individual", individual);

//		return menuToView(response, model, "menu-participants-view", "profile");
		return menuToView(response, model, "menu-ignite", "main-menu/profile");
	}

	@GetMapping("/about") // 5
	public String about(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String about = "";
		KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_ABOUT_MESSAGE);
		if (kvp != null) {
			about = kvp.getContiguousValue();
		}

		model.put("igVersion", runtimeUtils.getVersion());
		model.put("igAbout", about);

		return menuToView(response, model, "menu-ignite", "main-menu/about");
	}

	@GetMapping("/participant") // 20
	public String participant(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-about-view", "participant");
		return menuToView(response, model, "menu-ignite", "main-menu/participant");
	}

	@GetMapping("/participant-edit")
	public String editParticipant(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("id");
		model.put("participantId", id);
		return menuToView(response, model, "menu-ignite", "participant/participant-edit");
	}

	@GetMapping("/project") // 30
	public String project(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-projects-view", "project");
		return menuToView(response, model, "menu-ignite", "main-menu/project");
	}

	@GetMapping("/project-edit")
	public String editProject(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("id");
		model.put("projectId", id);
		return menuToView(response, model, "menu-ignite", "project/project-edit");
	}

	@GetMapping("/project-tree") // 40
	public String projectTree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-about-view", "project-tree");
		return menuToView(response, model, "menu-ignite", "main-menu/project-tree");
	}

	@GetMapping("/expense") // 45
	public String expense(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-about-view", "expense");
		return menuToView(response, model, "menu-ignite", "main-menu/expense");
	}

	@GetMapping("/upload-success")
	public String uploadSuccess() {
		return "upload-success";
	}

	@GetMapping("/upload-failure")
	public String uploadFailure() {
		return "upload-failure";
	}

	@GetMapping("/expense-edit")
	public String editExpense(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("id");
		model.put("participantId", id);
		return menuToView(response, model, "menu-ignite", "expense/expense-edit");
	}

	@GetMapping("/timesheet") // 50
	public String timesheet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-timesheet-view", "timesheet");
		return menuToView(response, model, "menu-ignite", "main-menu/timesheet");
	}

	@GetMapping("/timesheet-edit")
	public String editTimesheet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("id");


		if ((id == null) || (id.isEmpty())) {
			// We were not given a participant id
			// assume that this is for the logged in user
			Individual i = securityUtils.getIndividual();

			// Now we simply set the ID and let the process continue
			id = String.valueOf(i.getParticipantId());
		}

		model.put("participantId", id);

		return menuToView(response, model, "menu-ignite", "timesheet/timesheet-edit");
	}

	@GetMapping("/financials") // 20
	public String financials(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-about-view", "participant");
		return menuToView(response, model, "menu-ignite", "main-menu/financials");
	}

	@GetMapping("/financials-participant")
	public String editParticipantFinancials(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("id");
		model.put("participantId", id);
		return menuToView(response, model, "menu-ignite", "financials/financials-participant");
	}

	@GetMapping("/trip-logger") // 55
	public String tripLogger(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "main-menu/trip-logger");
	}

	@GetMapping("/report") // 90
	public String report(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-reports-view", "report");
		return menuToView(response, model, "menu-ignite", "main-menu/report");
	}

	@GetMapping("/library") // 92
	public String library(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "main-menu/library");
	}
	
	@GetMapping("/soq-capture") // 10
	public String soqActiveSchedules(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "main-menu/soq-active-schedules");
	}
	
	@GetMapping("/soq-template")
	public String soqTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "main-menu/soq-template");
	}
	
	@GetMapping("/soq-setup")
	public String soqSetup(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "main-menu/soq-setup");
	}
	
	@GetMapping("/soq-utility")
	public String soqUtility(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "main-menu/soq-utility");
	}

	@GetMapping("/setup-general") // 95
	public String setupGeneral(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// Get our e-mail settings
		String mailEnabled = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_ENABLED);

		String mailServerName = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SERVER_NAME);
		String mailServerPort = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SERVER_PORT);

		String mailSmtpUsername = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_USERNAME);
		String mailSmtpPassword = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_PASSWORD);

		String mailProxyEnabled = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_ENABLED);
		String mailProxyServer = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_SERVER_NAME);
		String mailProxyPort = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_SERVER_PORT);

		model.addAttribute("mailEnabled", mailEnabled);
		model.addAttribute("mailServerName", mailServerName);
		model.addAttribute("mailServerPort", mailServerPort);

		model.addAttribute("mailSmtpUsername", mailSmtpUsername);
		model.addAttribute("mailSmtpPassword", IgniteUtils.obfuscatePassword(mailSmtpPassword));

		model.addAttribute("mailProxyEnabled", mailProxyEnabled);
		model.addAttribute("mailProxyServerName", mailProxyServer);
		model.addAttribute("mailProxyServerPort", mailProxyPort);

//		return menuToView(response, model, "menu-setup-general-view", "setup-general");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-general");
	}

	@GetMapping("/setup-api") // 110
	public String setupApis(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-apis-view", "setup-api");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-api");
	}

	@GetMapping("/setup-workflow")
	public String setupWorkflow(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-workflow-view", "setup-workflow");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-workflow");
	}

	@GetMapping("/setup-calendar") // 112
	public String setupCalendar(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-calendar-view", "setup-calendar");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-calendar");
	}

	@GetMapping("//setup-tfs-tree") // 115
	public String setupTypicalFolderStructure(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-sd-view", "setup-tfs-tree");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-tfs-tree");
	}		
	
	@GetMapping("/select-reference-data-table")
	public String setupReferenceDataBlankPage() {
		// we don't need to check for a permission so we simply return the view
		return "/reference-data/select-reference-data-table";
	}
	
	@GetMapping("/setup-medical-insurance-company")
	public String setupMedicalInsuranceCompany() {
		// we don't need to check for a permission so we simply return the view
		return "/reference-data/setup-medical-insurance-company";
	}
	
	
	
	@GetMapping("/setup-report") // 116
	public String setupReport(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-reports-view", "setup-report");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-report");
	}
	
	@GetMapping("/setup-role") // 117
	public String setupRole(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-roles-&-permissions-view", "setup-role");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-role");
	}
	
	@GetMapping("/sd-tree") // 120 MAAK REG! Skuif na ignite-setup as Johannes gecommit het....
	public String sdTree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-about-view", "sd-tree");
		return menuToView(response, model, "menu-ignite", "ignite-setup/sd-tree");
	}

	@GetMapping("/subscription") 
	public String setupSubscription(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "ignite-setup/setupSubscription");
	}

	
	
	@GetMapping("/setup-reference-data")  // 130
	public String setupReferenceData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, "menu-ignite", "reference-data/setup-reference-data");
	}	
	
	
	@GetMapping("/setup-service") // 125
	public String setupService(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-sd-view", "setup-service");
		return menuToView(response, model, "menu-ignite", "ignite-setup/setup-service");
	}

	
	
	
	@GetMapping("/setup-asset-condition") // 190
	public String setupAssetCondition(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-asset-condition-view", "setup-asset-condition");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-asset-condition");
	}

	@GetMapping("/setup-asset-type") // 195
	public String setupAssetType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-asset-type-view", "setup-asset-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-asset-type");
	}

	@GetMapping("/setup-asset-status") // ??? Not in UiComponent table yet?
	public String setupAssetStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-asset-type-view", "setup-asset-status");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-asset-status");
	}

	@GetMapping("/setup-bank-account-type") // 197
	public String setupBankAccountType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-bank-account-type-view", "setup-bank-account-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-bank-account-type");
	}

	@GetMapping("/setup-bank") // 200
	public String setupBank(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-banks-&-branches-view", "setup-bank");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-bank");
	}

	@GetMapping("/setup-card-type") // 205
	public String setupCardType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-card-type-view", "setup-card-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-card-type");
	}

	@GetMapping("/setup-city") //
	public String setupCity(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-city-view", "setup-city");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-city");
	}

	@GetMapping("/setup-competency-level") // 207
	public String setupCompetencyLevel(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-competency-level-view", "setup-competency-level");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-competency-level");
	}

	@GetMapping("/setup-country") // 210
	public String setupCountry(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-countries-view", "setup-country");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-country");
	}

	@GetMapping("/setup-expense-type") // 220
	public String setupExpenseType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-expense-type-view", "setup-expense-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-expense-type");
	}

//	@GetMapping("/setup-participant-status") // 230
//	public String setupParticipantStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
////		return menuToView(response, model, "menu-setup-participant-status-view", "setup-participant-status");
//		return menuToView(response, model, "menu-ignite", "reference-data/setup-participant-status");
//	}

	@GetMapping("/setup-payment-method") // 233
	public String setupPaymentMethod(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-payment-type-view", "setup-payment-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-payment-method");
	}

	@GetMapping("/setup-payment-type") // 235
	public String setupPaymentType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-payment-type-view", "setup-payment-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-payment-type");
	}

	@GetMapping("/setup-professional-institute") // 237
	public String setupProfessionalInstitute(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-professional-institute-view", "setup-professional-institute");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-professional-institute");
	}

	@GetMapping("/setup-proj-based-remun-type") // 240
	public String setupProjBasedRemunType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-remuneration-type-view", "setup-proj-based-remun-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-proj-based-remun-type");
	}

	@GetMapping("/setup-project-stage-type") // 245
	public String setupProjectStageType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-project-stage-type-view", "setup-project-stage-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-project-stage-type");
	}

	@GetMapping("/setup-province")           //
	public String setupProvince(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-province-view", "setup-province");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-province");
	}


	@GetMapping("/setup-remuneration-model") // 247
	public String setupRemunerationModel(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-remuneration-model-view", "setup-remuneration-model");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-remuneration-model");
	}

	@GetMapping("/setup-resource-remun-type") // 250
	public String setupResourceRemunType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-remuneration-type-view", "setup-resource-remun-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-resource-remun-type");
	}

	@GetMapping("/setup-role-on-a-project") // 260
	public String setupRoleOnAProject(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-role-on-a-project-view", "setup-role-on-a-project");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-role-on-a-project");
	}

	@GetMapping("/setup-study-institute") // 261
	public String setupStudyInstitute(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-study-institute", "setup-study-institute");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-study-institute");
	}

	@GetMapping("/setup-tap-subscription") // 262
	public String setupTapSubscription(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-asset-condition-view", "setup-asset-condition");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-tap-subscription");
	}


	@GetMapping("/setup-tax-deductable-category") // 263
	public String setupTaxDeductableCategory(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-role-on-a-project-view", "setup-tax-deductable-category");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-tax-deductable-category");
	}

	@GetMapping("/setup-unit-type") // 265
	public String setupUnitType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-unit-type-view", "setup-unit-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-unit-type");
	}
	
	@GetMapping("/setup-deliverable-type") //
	public String setupDeliverableType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-deliverable-type-view", "setup-deliverable-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-deliverable-type");
	}

	@GetMapping("/setup-vehicle-make") // 270
	public String setupVehicleMake(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-vehicle-make-view", "setup-vehicle-make");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-vehicle-make");
	}

	@GetMapping("/setup-vehicle-item-type") // 270 To-do
	public String setupVehicleItemType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-vehicle-item-type-view", "setup-vehicle-item-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-vehicle-item-type");
	}

	@GetMapping("/setup-vehicle-maintenance-type") // 270 To-do
	public String setupVehicleMaintenanceType(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
//		return menuToView(response, model, "menu-setup-vehicle-maintenance-type-view", "setup-vehicle-maintenance-type");
		return menuToView(response, model, "menu-ignite", "reference-data/setup-vehicle-maintenance-type");
	}

	@GetMapping("/assignment") // 355
	public String assignment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-assignment-view", "assignment");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/assignment");
	}

	@GetMapping("/my-assignment") // 357
	public String myAssignment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-my-assignment-view", "my-assignment");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/my-assignment");
	}

	@GetMapping("/procedure-generator") // 365
	public String procedureGenerator(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-procedure-generator", "procedure-generator");
		return menuToView(response, model, "menu-task-manager", "procedure-manager/gen-procedure");
	}

	@GetMapping("/setup-assignment-group") // 650
	public String assignmentGroup(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-assignment-group-view", "setup-assignment-group");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/setup-assignment-group");
	}

	@GetMapping("/setup-assignment-type") // 700
	public String assignmentType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-assignment-type-view", "setup-assignment-type");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/setup-assignment-type");
	}

	@GetMapping("/setup-definition-view") // 750
	public String definition(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-definition-view", "setup-definition-view");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/setup-definition");
	}

	@GetMapping("/setup-procedure-status") // 800
	public String procedureStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-procedure-status-view", "setup-procedure-status");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/setup-procedure-status");
	}

	@GetMapping("/setup-task-importance-type") // 850
	public String setupTaskImportanceType(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//		return menuToView(response, model, "menu-setup-task-importance-type-view", "setup-task-importance-type");
		return menuToView(response, model, "menu-task-manager", "task-manager/dialogs/setup-task-importance-type");
	}

	@GetMapping("/profile-reset") //
	public String profileReset(@RequestParam(name = "token", required = false) String token, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if ((token == null) || (token.isEmpty())) {
			model.put("message", "A valid token was not supplied");
		} else {
			Individual individual = individualService.findByPasswordResetToken(token);

			if (individual == null) {
				model.put("message", "No user was found for the given token.");
			} else {
				Date now = new Date();
				Date pred = individual.getPasswordResetExpiryDate();

				if (!pred.after(now)) {
					// This token Expiry date is old - clear it
					individual.clearPasswordReset();
					individualService.save(individual);

					model.put("message", "The token has expired.");
				} else {
					// Allow the user to proceed with the password reset
					model.put("igUserId", individual.getIndividualId());
					model.put("igUsername", individual.getDisplayName());
					model.put("token", token);
				}
			}
		}

		return menuToView(response, model, null, "ignite-security/profile-reset");
	}

	@GetMapping("/forgot-password") //
	public String forgotPassword(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return menuToView(response, model, null, "ignite-security/forgot-password");
	}

	/*
	 * @GetMapping("/resource") public ResponseEntity<byte[]>
	 * getResource(@RequestParam("f") String filename) throws IOException {
	 *
	 * String filepath =
	 * "C:\\SpringToolSuite\\WorkSpace\\ignite-project\\ignite\\src\\main\\resources\\static\\img\\"
	 * + filename; File f = new File(filepath); if (!f.exists()) { // don't send
	 * them the full path and filename, just send the name... throw new
	 * IOException("The source file for " + filename +
	 * " could not be found and therefore cannot be downloaded"); }
	 *
	 * byte[] documentBody = Files.readAllBytes(Paths.get(filepath));
	 *
	 * HttpHeaders responseHeaders = new HttpHeaders();
	 * responseHeaders.set("charset", "utf-8");
	 * responseHeaders.setContentType(MediaType.APPLICATION_PDF);
	 * responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	 * responseHeaders.setContentLength(documentBody.length);
	 * responseHeaders.set("Content-disposition", "attachment; filename=" +
	 * filename);
	 *
	 * return new ResponseEntity<>(documentBody, responseHeaders, HttpStatus.OK);
	 *
	 *
	 *
	 * }
	 */

	@GetMapping("/getfile")
	public ResponseEntity<InputStreamResource> getFile(@RequestParam("t") String fileType, @RequestParam("id") Long id)
			throws Exception {
		ResponseEntity<InputStreamResource> result = null;
		boolean handled = false;

		if (fileType.equalsIgnoreCase("InvoiceReportGenerated")) {


//			// Fix this Ingrid!
//			// Get the InvoiceReportGenerated record using the service
//			ProjectExpenseFile projectExpenseFile = projectExpenseFileService.getByProjectExpenseFileId(id);
//
//			// Get the location of projectExpenseFiles using the keyValuePair service
//			KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_UPLOAD_PATH_PROJECT_EXPENSE);
//			String fullFileName = IgniteUtils.getTerminatedPath(kvp.getValue()) + projectExpenseFile.getFileName();
//
//			// Create a variable for the file name which is projectExpensePath +
//			// projectExpenseFileName
//			result = getResource(fullFileName, projectExpenseFile.getOriginalFileName());
//
//
			String invoiceIdToString = new String(IgniteUtils.leadingZeroPad(id,4));

//			String fullFileName = "C:\\ignite\\invoice-files\\Invoice" + invoiceIdToString + ".pdf";
			String fullFileName = "C:\\ignite\\invoice-files\\Invoice0027.pdf";

			result = getResource(fullFileName, "Generated Invoice");

			handled = true;
		}

		if (fileType.equalsIgnoreCase("ProjectExpenseFile")) {
			// Get the projectExpenseFile record using the service
			ProjectExpenseFile projectExpenseFile = projectExpenseFileService.getByProjectExpenseFileId(id);

			// Get the location of projectExpenseFiles using the keyValuePair service
			KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_UPLOAD_PATH_PROJECT_EXPENSE);
			String fullFileName = IgniteUtils.getTerminatedPath(kvp.getValue()) + projectExpenseFile.getFileName();

			// Create a variable for the file name which is projectExpensePath +
			// projectExpenseFileName
			result = getResource(fullFileName, projectExpenseFile.getOriginalFileName());

			handled = true;
		}

		// There is no such thing as an invoice expense, but this code is here for demonstration purposes
		if (fileType.equalsIgnoreCase("InvoiceFile")) {
			// Get the projectExpenseFile record using the service
			InvoiceFile invoiceFile = invoiceFileService.findByInvoiceFileId(id);

			// Get the location of projectExpenseFiles using the keyValuePair service
			KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_UPLOAD_PATH_INVOICE);
			String fullFileName = IgniteUtils.getTerminatedPath(kvp.getValue()) + invoiceFile.getFileName();

			// Create a variable for the file name which is projectExpensePath +
			// projectExpenseFileName
			result = getResource(fullFileName, invoiceFile.getOriginalFileName());

			handled = true;
		}

		if (!handled) {
			throw new Exception("Unknown file type passed to getFile");
		}
		return result;
	}

	private ResponseEntity<InputStreamResource> getResource(String fullFileName, String originalFileName) throws Exception {

		ResponseEntity<InputStreamResource> result = null;

		// Check that the file exists using the file object.
		// The test is for f.exists(), if it doesn't exist, throw an exception saying that the file does not exist


		// Using IgniteUtils.getFileExt(), get the file extension
		String fileExt = IgniteUtils.getFileExtension(fullFileName);


		boolean handled = false;

		if (".jpg".equalsIgnoreCase(fileExt)) {
			result = getJpgFile(fullFileName, originalFileName);
			handled = true;
		}

		if (".png".equalsIgnoreCase(fileExt)) {
			result = getPngFile(fullFileName, originalFileName);
			handled = true;
		}

		if (".xlsx".equalsIgnoreCase(fileExt)) {
			result = getXlsFile(fullFileName, originalFileName);
			handled = true;
		}


		if (".csv".equalsIgnoreCase(fileExt)) {
			result = getCsvFile(fullFileName, originalFileName);
			handled = true;
		}

		if (".pdf".equalsIgnoreCase(fileExt)) {
			result = getPdfFile(fullFileName, originalFileName);
			handled = true;
		}

		if (".txt".equalsIgnoreCase(fileExt)) {
			result = getTxtFile(fullFileName, originalFileName);
			handled = true;
		}

		if (!handled) {
			throw new Exception("Unknown file type passed to getResource");
		}

		return result;
	}


	private ResponseEntity<InputStreamResource> getCsvFile(String fullFileName,
															String originalFileName) throws FileNotFoundException {
		File file = new File(fullFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline; filename=" + originalFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("text/plain")).body(resource);
	}


	private ResponseEntity<InputStreamResource> getXlsFile(String fullFileName,
															String originalFileName) throws FileNotFoundException {
		File file = new File(fullFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + originalFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
		.contentType(MediaType.parseMediaType("application/x-excel")).body(resource);
	}


	private ResponseEntity<InputStreamResource> getJpgFile(String fullFileName,
															String originalFileName) throws FileNotFoundException {
		File file = new File(fullFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + originalFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("image/jpeg")).body(resource);
	}

	private ResponseEntity<InputStreamResource> getPngFile(String fullFileName,
															String originalFileName) throws FileNotFoundException {
		File file = new File(fullFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + originalFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
		.contentType(MediaType.parseMediaType("image/png")).body(resource);
	}

	private ResponseEntity<InputStreamResource> getTxtFile(String fullFileName, String originalFileName) throws FileNotFoundException {
		File file = new File(fullFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + originalFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("text/plain")).body(resource);
	}

	private ResponseEntity<InputStreamResource> getPdfFile(String fullFileName,
															String originalFileName) throws FileNotFoundException {

		File file = new File(fullFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + originalFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/pdf")).body(resource);
	}

	@GetMapping("/display-blob")
	public ResponseEntity<?> displayBlob(@RequestParam("id") Long participantId,
											HttpServletResponse response) {
		VParticipant participant = participantService.findInViewByParticipantId(participantId);
		String logo = participant.getLogo();

		if (logo == null) {
			// get that default, not found image
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.ok().body(logo);
		}
	}
}