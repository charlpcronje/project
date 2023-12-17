package net.integrategroup.ignite.controller.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.Invoice;
import net.integrategroup.ignite.persistence.model.InvoiceFile;
import net.integrategroup.ignite.persistence.model.InvoiceLineDetail;
import net.integrategroup.ignite.persistence.model.InvoiceLineTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsDto;
import net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerExpenseDto;
import net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.Report;
import net.integrategroup.ignite.persistence.model.VPpExpenseRateUplineRecursive;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.InvoiceFileService;
import net.integrategroup.ignite.persistence.service.InvoiceService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.persistence.service.ReportService;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/invoice")

public class InvoiceController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	InvoiceFileService invoiceFileService;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	ReportService reportService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/invoices-in/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findInvoicesInForParticipant(ModelMap modelMap,
			@PathVariable("participantId") Long participantId, @PathVariable(name = "firstDay") Long firstDay,
			@PathVariable(name = "lastDay") Long lastDay) {
		try {
			Date fd = new Date(firstDay); // Dates to and db

			// Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
			Date ld = new Date(IgniteUtils.endOfDay(lastDay)); // 1 ms before midnight

			List<Invoice> result = invoiceService.findInvoicesInForParticipant(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveInvoice(@RequestBody Invoice invoice) {
		try {

			Invoice test = invoiceService.findByInvoiceId(invoice.getInvoiceId());
			if (test == null) {
				throw new Exception("Invoice not found");
			}

			invoice = invoiceService.save(invoice);
			return ResponseEntity.ok(invoice);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/new")
	public ResponseEntity<?> saveInvoiceNew(@RequestBody Invoice invoice) {
		try {
			Invoice test = invoiceService.findByInvoiceId(invoice.getInvoiceId());
			if (test != null) {
				throw new Exception("The Invoice already exists");
			}

			invoice = invoiceService.save(invoice);
			return ResponseEntity.ok(invoice);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteInvoice(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long invoiceId = mu.getAsLongOrNull(data, "invoiceId");
		String sql = "CALL ig_db.deleteInvoice(?);";

		System.out.println("CALL ig_db.deleteInvoice(" + invoiceId + ");");
		try {	//**//					
			Object[] params = {		
				invoiceId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/file/delete")
	public ResponseEntity<?> deleteInvoiceFile(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long invoiceFileId = mu.getAsLongOrNull(data, "invoiceFileId");

		try {
			// Delete the database entry
			InvoiceFile iif = invoiceFileService.findByInvoiceFileId(invoiceFileId);
			String filename = iif.getFileName();
			invoiceFileService.delete(invoiceFileId);

			// Delete the physical file
			KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_UPLOAD_PATH_INVOICE);
			String fullFilename = IgniteUtils.getTerminatedPath(kvp.getValue()) + filename;

			File f = new File(fullFilename);
			f.delete();

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/invoices-out/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findInvoicesOutForParticipant(ModelMap modelMap,
			@PathVariable("participantId") Long participantId, @PathVariable(name = "firstDay") Long firstDay,
			@PathVariable(name = "lastDay") Long lastDay) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			//			System.out.println("\n" + "firstdayDay: " + firstDay + "\n");
			//			System.out.println("\n" + "lastDay: " + lastDay + "\n");
			//			lastDay = lastDay + (24*60*60*1000) - 1; // 1 ms before midnight
			//			Date ld = new Date(lastDay);
			Date ld = new Date(lastDay + (24*60*60*1000) - 1000); // 1 s before midnight
			//			System.out.println("\n\n\n" + "lastDay + 86 400 000: " + lastDay + "\n\n\n");
			//			System.out.println("\n\n\n" + "ld + 86 400 000: " + ld + "\n\n\n");

			List<Invoice> result = invoiceService.findInvoicesOutForParticipant(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/avail-tc-rel-to-gen/{participantIdBeneficiary}")
//	public ResponseEntity<?> findInvoicesAvailableRelationshipsTimeCost(ModelMap modelMap,
//															@PathVariable("participantIdBeneficiary") Long participantIdBeneficiary
//															) {
//		try {
//			List<ParticipantTimeCostTotalsDto> result = invoiceService.findInvoicesAvailableRelationshipsTimeCost(participantIdBeneficiary);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//	@GetMapping("/avail-expense-rel-to-gen/{participantIdContracted}")
//	public ResponseEntity<?> findInvoicesAvailableRelationshipsExpense(ModelMap modelMap,
//															@PathVariable("participantIdContracted") Long participantIdContracted
//															) {
//		try {
//			List<ParticipantExpenseCostTotalsDto> result = invoiceService.findInvoicesAvailableRelationshipsExpense(participantIdContracted);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


//	// Available invoices to generate (end date only) Table 1
//	@GetMapping("/avail-inv-rel-to-gen-not-null/{participantIdContracted}/{lastDay}")
//	public ResponseEntity<?> findInvoicesOutAvailableLineTotalsNotNull(ModelMap modelMap,
//			@PathVariable("participantIdContracted") Long participantIdContracted,
//			@PathVariable(name = "lastDay") Long lastDay) {
//	try {
//			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
//
//			List<GeneratableInvoiceTotalsNonNullValuesDto> result = invoiceService
//					.findInvoicesOutAvailableLineTotalsNotNull(participantIdContracted, ld);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

//	// Available invoices to generate for dateRange Table 2 per Project Type
//	@GetMapping("/avail-project-rel-to-gen/{participantIdContracted}/{participantIdContracting}/{lastDay}")
//	public ResponseEntity<?> findInvoicesOutPerProjectTotals(ModelMap modelMap,
//			@PathVariable("participantIdContracted") Long participantIdContracted,
//			@PathVariable("participantIdContracting") Long participantIdContracting,
//			@PathVariable(name = "lastDay") Long lastDay) {
//
//		try {
//			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
//			List<VInvoiceGenLineAmountsPerProjectTotals> result = invoiceService
//					.findInvoicesOutPerProjectTotals(participantIdContracted, participantIdContracting, ld);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}




//	// Available invoices to generate for dateRange Table 2 per Project Type
//	@GetMapping("/avail-project-rel-to-gen/{participantIdContracted}/{participantIdContracting}/{lastDay}")
//	public ResponseEntity<?> findInvoicesOutPerProjectTotals(ModelMap modelMap,
//			@PathVariable("participantIdContracted") Long participantIdContracted,
//			@PathVariable("participantIdContracting") Long participantIdContracting,
//			@PathVariable(name = "lastDay") Long lastDay) {
//
//		try {
//			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
////			List<InvoiceOutLineTotalsPerProjectDto> result = invoiceService
////					.findInvoicesOutPerProjectTotals(participantIdContracted, participantIdContracting, ld);
//			return ResponseEntity.ok(null);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	// Available invoices to generate for dateRange Table 2 per Expense Type
	@GetMapping("/avail-expense-rel-to-gen/{participantIdContracted}/{participantIdContracting}/{lastDay}")
	public ResponseEntity<?> findInvoicesOutPerExpenseTotals(ModelMap modelMap,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable(name = "lastDay") Long lastDay) {

		try {
			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
//			List<InvoiceOutLineTotalsPerExpenseDto> result = invoiceService
//					.findInvoicesOutPerExpenseTotals(participantIdContracted, participantIdContracting, ld);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Time Related Costs
	// Line Totals Table Table 3
	@GetMapping("/payer-ben-time-cost/{participantIdContracting}/{participantIdContracted}/{lastDay}")
	public ResponseEntity<?> findLineTotalsTimeCost(ModelMap modelMap,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable(name = "lastDay") Long lastDay) {

		try {
			Date ld = new Date(lastDay); 
//???			List<ParticipantTimeCostTotalsPerProjectDto> result = invoiceService
//					.findLineTotalsTimeCost(participantIdContracting, participantIdContracted, ld);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Expense Claim Costs
	// Line Totals Table Table 3
	@GetMapping("/payer-ben-expense-claim/{participantIdContracting}/{participantIdContracted}/{lastDay}")
	public ResponseEntity<?> findLineTotalsExpenseClaim(ModelMap modelMap,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable(name = "lastDay") Long lastDay) {

		try {
			Date ld = new Date(lastDay); 

//???			List<ParticipantExpenseCostTotalsPerProjectDto> result = invoiceService
//					.findLineTotalsExpenseClaim(participantIdContracting, participantIdContracted, ld);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// Table 4 Bottom
	@GetMapping("/contracting-contracted-expenses/{expenseTypeId}/{participantIdContracting}/{participantIdContracted}/{projectId}")
	public ResponseEntity<?> findContractingContractedExpenseCost(ModelMap modelMap,
			@PathVariable(name = "expenseTypeId") Long expenseTypeId,
			@PathVariable(name = "participantIdContracting") Long participantIdContracting,
			@PathVariable(name = "participantIdContracted") Long participantIdContracted,
			@PathVariable(name = "projectId") Long projectId) {
		try {
			List<VPpExpenseRateUplineRecursive> result = invoiceService.findContractingContractedExpenseCost(
					expenseTypeId, participantIdContracting, participantIdContracted, projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/files/{invoiceId}")
	public ResponseEntity<?> findFilesByInvoiceId(@PathVariable("invoiceId") Long invoiceId) {
		try {
			List<InvoiceFile> result = invoiceFileService.findFilesByInvoiceId(invoiceId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/lines/{invoiceId}")
	public ResponseEntity<?> findInvoiceLines(ModelMap modelMap, @PathVariable("invoiceId") Long invoiceId) {
		try {
//			List<VInvoiceLine> result = invoiceService.findInvoiceLines(invoiceId);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/generate-draft-invoice")
	public ResponseEntity<?> generateInvoiceOut(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long invoiceId = null;
		Invoice invoice = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.generateDraftInvoice('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', invoiceId" + ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.generateDraftInvoice(?, ?, ?);";

			try {											//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();
				invoiceId = cstm.getLong(3);
		
			if (invoiceId != null) {
				invoice = invoiceService.findByInvoiceId(invoiceId);
			}
			return ResponseEntity.ok(invoice);
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	} finally {							//**//
		try {							//**//
			if (cstm != null) {			//**//
				cstm.close();			//**//
			}							//**//
										//**//
			if (conn != null) {			//**//
				conn.close();			//**//
			}							//**//
		} catch (SQLException e) {		//**//
			e.printStackTrace();		//**//
		}								//**//
	}									//**//
}										//**//	
//**//


	//--------------------------------------------------------------------------------------------//
	// Invoice Generator
	// Table 1
	// Available invoices to generate for dateRange Table 1 (Rates missing also shown)
	@GetMapping("/avail-inv-rel-to-gen/{participantIdContracted}/{lastDay}")
	public ResponseEntity<?> findInvoicesOutAvailableLineTotals(ModelMap modelMap,
								@PathVariable("participantIdContracted") Long participantIdContracted,
								@PathVariable(name = "lastDay") Long lastDay) {

	try {
			// Date ld = new Date(lastDay + (24*60*60*1000) - 1000); // 1sec before midnight
			Date ld = new Date(lastDay); // 1sec before midnight
			System.out.println("\n\n\n" + ld + "\n\n\n");


			List<InvoiceOutLineTotalsDto> result = invoiceService.findInvoicesOutAvailableLineTotals(participantIdContracted, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Table 2
	// Available invoices to generate for dateRange per Project
	@GetMapping("/avail-project-rel-to-gen/{participantIdContracted}/{participantIdContracting}/{lastDay}")
	public ResponseEntity<?> findInvoicesOutPerProjectTotals(ModelMap modelMap,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable(name = "lastDay") Long lastDay) {

		try {
			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
			List<InvoiceOutLineTotalsPerProjectDto> result = invoiceService.findInvoicesOutPerProjectTotals(participantIdContracted, participantIdContracting, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// Table 3
	// Available invoices lines to generate for dateRange per Project per Expense Type
	@GetMapping("/avail-proj-expense-rel-to-gen/{participantIdContracted}/{participantIdContracting}/{lastDay}/{projectId}")
	public ResponseEntity<?> findInvoicesOutPerProjectPerExpenseTotals(ModelMap modelMap,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable(name = "lastDay") Long lastDay,
			@PathVariable(name = "projectId") Long projectId) {

		try {
			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
			List<InvoiceOutLineTotalsPerExpenseDto> result = invoiceService
					.findInvoicesOutPerProjectPerExpenseTotals(participantIdContracted, participantIdContracting, ld, projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Time Related Costs
	// Line Totals Table Table 4a
	@GetMapping("/inv-payer-ben-time-cost/{participantIdContracting}/{participantIdContracted}/{lastDay}/{projectId}")
	public ResponseEntity<?> findLineTotalsTimeCost(ModelMap modelMap,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable(name = "lastDay") Long lastDay,
			@PathVariable("projectId") Long projectId) {

		try {
			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
			List<ParticipantTimeCostTotalsPerProjectDto> result = invoiceService
					.findLineTotalsTimeCost(participantIdContracting, participantIdContracted, ld, projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Expense Claim Costs
	// Line Totals Table Table 4b
	@GetMapping("/inv-payer-ben-expense-claim/{participantIdContracting}/{participantIdContracted}/{lastDay}/{projectId}")
	public ResponseEntity<?> findLineTotalsExpenseClaim(ModelMap modelMap,
			@PathVariable("participantIdContracting") Long participantIdContracting,
			@PathVariable("participantIdContracted") Long participantIdContracted,
			@PathVariable(name = "lastDay") Long lastDay,
			@PathVariable("projectId") Long projectId) {

		try {
			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight

			List<ParticipantExpenseCostTotalsPerProjectDto> result = invoiceService
					.findLineTotalsExpenseClaim(participantIdContracting, participantIdContracted, ld, projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	//--------------------------------------------------------------------------------------------//
	// Draft Invoices
	//*********************************************************
	// Table 1
	//*********************************************************
	// Available Draft Invoices
	@GetMapping("/draft-invoices/{participantId}")
	public ResponseEntity<?> findDraftInvoicesForParticipant(ModelMap modelMap,
			@PathVariable("participantId") Long participantId) {
		try {
			List<Invoice> result = invoiceService.findDraftInvoicesForParticipant(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//*********************************************************
	// Table 2
	//*********************************************************
	// Available invoice line totals per Project
	@GetMapping("/line-totals-per-project/{invoiceId}")
	public ResponseEntity<?> getInvoiceLineTotalsPerProject(ModelMap modelMap, @PathVariable("invoiceId") Long invoiceId) {
		try {
			List<InvoiceOutLineTotalsPerProjectDto> result = invoiceService.getInvoiceLineTotalsPerProject(invoiceId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//*********************************************************
	// Table 3
	//*********************************************************
	// Available invoices lines to generate for dateRange per Project per Expense Type
	@GetMapping("/line-totals-per-project-per-expense/{invoiceId}/{projectId}")
	public ResponseEntity<?> getInvoiceLineTotalsPerProjectAndExpense(ModelMap modelMap
			, @PathVariable("invoiceId") Long invoiceId
			, @PathVariable("projectId") Long projectId) {
		try {
			List<InvoiceOutLineTotalsPerExpenseDto> result = invoiceService.getInvoiceLineTotalsPerProjectAndExpense(invoiceId, projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Time Related Costs
	// Line Totals Table Table 4a
	@GetMapping("/line-totals-per-project-line-type/{invoiceId}/{projectId}/{lineType}")
	public ResponseEntity<?> findInvoiceLineTotalsTimeCostPerProject(ModelMap modelMap
			, @PathVariable("invoiceId") Long invoiceId
			, @PathVariable("projectId") Long projectId
			, @PathVariable("lineType") String lineType) {
		try {
			List<InvoiceLineTimeCostTotalsPerProjectDto> result = invoiceService.findInvoiceLineTotalsTimeCostPerProject(invoiceId, projectId, lineType);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// Time Related Cost Details
	// Draft Table 5a
	@GetMapping("/invoice-line-detail/{invoiceId}/{projectId}/{lineType}")
	public ResponseEntity<?> findDraftInvoiceDetailsForInvoiceLine(ModelMap modelMap,
			@PathVariable("invoiceId") Long invoiceId,
			@PathVariable("projectId") Long projectId,
			@PathVariable("lineType") String lineType) {
		try {
			List<InvoiceLineDetail> result = invoiceService.findDraftInvoiceDetailsForInvoiceLine(invoiceId, projectId, lineType);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Draft Table 5b : Expense Claim Details
	@GetMapping("/invoice-line-expense-detail/{invoiceId}/{projectId}/{expenseType}")
	public ResponseEntity<?> findDraftInvoiceDetailsForExpenseLine(ModelMap modelMap,
			@PathVariable("invoiceId") Long invoiceId,
			@PathVariable("projectId") Long projectId,
			@PathVariable("expenseType") String expenseType) {
		try {
			List<InvoiceLineDetail> result = invoiceService.findDraftInvoiceDetailsForExpenseLine(invoiceId, projectId, expenseType);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	// Expense Claim Costs
	// Line Totals Table Table 4b


//	// Joshua's code
//	@GetMapping("/report")
//	public ResponseEntity<?> invoiceReport(ModelMap modelMap,
//			@RequestParam("id") Long invoiceId) {
//		try {
//			// Look up the Report Id for invoice_generic_a4
//			Report report = reportService.findReportByName("A4 Invoice");
//
//			if (report == null) {
//				throw new Exception("The invoice_generic_a4 report was not found/configured.");
//			}
//
//			Long reportId = report.getReportId();
//
//
//			Map<String, Object> parameters = new HashMap<>();
//			parameters.put(IgniteConstants.GENERATION_TYPE, "inline");
//			// These are the parameter names that are in the actual Jasper Report, so the name-case and punctuation must match.
//			parameters.put("INVOICE_ID", invoiceId);
//
//			// Call the reportRun with the Report Id, passing in InvoiceId
//			return reportService.runReport(reportId, parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//





	@GetMapping("/get-file-on-drive/{fullFileName}")
	public ResponseEntity<?> getFileOnDisk	(ModelMap modelMap,
			@PathVariable("fullFileName") String fullFileName) {

		try {
			// Look up the Report Id for invoice_generic_a4
			return getTheResource(fullFileName, "origFileName.pdf");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
}

private ResponseEntity<InputStreamResource> getTheResource(String fullFileName, String originalFileName) throws Exception {

		ResponseEntity<InputStreamResource> result = null;

		// Check that the file exists using the file object.
		// The test is for f.exists(), if it doesn't exist, throw an exception saying that the file does not exist


		// Using IgniteUtils.getFileExt(), get the file extension
		String fileExt = IgniteUtils.getFileExtension(fullFileName);


		boolean handled = false;
		// if ".txt".equalsIgnoreCase(fileExt), call a reader to get the .txt file as an InputStreamResource. Set handled = true
		// if ".jpg".equalsIgnoreCase(fileExt), call a reader to get the .jpg file as an InputStreamResource. Set handled = true
		// if ".csv".equalsIgnoreCase(fileExt), call a reader to get the .csv file as an InputStreamResource. Set handled = true

//		if (".jpg".equalsIgnoreCase(fileExt)) {
//			result = getJpgFile(fullFileName, originalFileName);
//			handled = true;
//		}
//
//		if (".png".equalsIgnoreCase(fileExt)) {
//			result = getPngFile(fullFileName, originalFileName);
//			handled = true;
//		}
//
//		if (".xlsx".equalsIgnoreCase(fileExt)) {
//			result = getXlsFile(fullFileName, originalFileName);
//			handled = true;
//		}
//
//
//		if (".csv".equalsIgnoreCase(fileExt)) {
//			result = getCsvFile(fullFileName, originalFileName);
//			handled = true;
//		}
//
		if (".pdf".equalsIgnoreCase(fileExt)) {
			result = getPdfFile(fullFileName, originalFileName);
			handled = true;
		}

		if (!handled) {
			throw new Exception("Unknown file type passed to getResource");
		}

		return result;
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



	@PostMapping("/save-invoice-out")
	public ResponseEntity<?> saveInvoiceOut(@RequestBody Map<String, Object> data) {
		MapUtils mu = new MapUtils();
		Long invoiceId = mu.getAsLongOrNull(data, "invoiceId");
		String invoiceNumber = mu.getAsStringOrNull(data, "invoiceNumber");
		Long invoiceDate = mu.getAsLongOrNull(data, "invoiceDate");
		Long dateSentOrReceived = mu.getAsLongOrNull(data, "dateSentOrReceived");
		String description = mu.getAsStringOrNull(data, "description");
		String toAttenion = mu.getAsStringOrNull(data, "toAttention");

		Date invoiceDateAsDate = new Date(invoiceDate);
		Date dateSentOrReceivedAsDate = new Date(dateSentOrReceived);

		try {

			Invoice theInvoice = invoiceService.findByInvoiceId(invoiceId);
			if (theInvoice == null) {
				throw new Exception("Invoice not found");
			}

			theInvoice.setInvoiceNumber(invoiceNumber);
			theInvoice.setFlagDraft("N");
			theInvoice.setInvoiceDate(invoiceDateAsDate);
			theInvoice.setDateSentOrReceived(dateSentOrReceivedAsDate);
			theInvoice.setDescription(description);
			theInvoice.setToAttention(toAttenion);

			theInvoice = invoiceService.save(theInvoice);
			return ResponseEntity.ok(theInvoice);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	// Report creation Ingrid
//	@GetMapping("/create-invoice-report/{invoiceId}")
//	public ResponseEntity<?> createInvoiceReport(ModelMap modelMap,
//			@PathVariable("invoiceId") Long invoiceId) {
//		try {
//
//	//		// Invoice main details:
//	//		Invoice invoice = invoiceService.findViewByInvoiceId(invoiceId);
//
//			Map<String, Object> parameters = new HashMap<String, Object>();
//
//			String invoiceIdString = invoiceId.toString();
//			parameters.put("INVOICE_ID",invoiceIdString);
//
//			String filePath = "C:\\workspace\\ignite-project\\jasper-reports\\ignite\\gen-invoice-main.jrxml";
//
//
//	//		JRBeanCollectionDataSource invoiceLineSummary = new JRBeanCollectionDataSource(result);
//	//		parameters.put("INVOICE_LINE_SUMMARY_DATA_SET",invoiceLineSummary);
//
//			JasperReport report = JasperCompileManager.compileReport(filePath);
//
//			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//
//			String invoiceIdToString = new String(IgniteUtils.leadingZeroPad(invoiceId,4));
//			String pdfReportFile = "C:\\ignite\\invoice-files\\Invoice" + invoiceIdToString + ".pdf";
//			System.out.println(pdfReportFile);
//
//			JasperExportManager.exportReportToPdfFile(print, pdfReportFile);
//
//			// Save invoice file info in InvoiceFileTable
//
//			System.out.println("Report created");
//
//			return ResponseEntity.ok(pdfReportFile);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


	// Joshua's code modified by Ingrid

	@GetMapping("/create-invoice-report/{invoiceId}")
	public ResponseEntity<?> createInvoiceReport(ModelMap modelMap,
			@PathVariable("invoiceId") Long invoiceId) {
		try {

			Map<String, Object> parameters = new HashMap<>();

			String invoiceIdString = invoiceId.toString();
			parameters.put("INVOICE_ID",invoiceIdString);

//			String filePath = "C:\\workspace\\ignite-project\\jasper-reports\\ignite\\gen-invoice-main.jrxml";

			// Look up the Report Id for invoice_generic_a4
			Report report = reportService.findReportByName("gen-invoice-main");

			if (report == null) {
				throw new Exception("The Gen invoice main report was not found/configured.");
			}

			Long reportId = report.getReportId();

			parameters.put(IgniteConstants.GENERATION_TYPE, "inline");
			// These are the parameter names that are in the actual Jasper Report, so the name-case and punctuation must match.
			parameters.put("INVOICE_ID", invoiceIdString);

			// Call the reportRun with the Report Id, passing in InvoiceId
			return reportService.runReport(reportId, parameters);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
