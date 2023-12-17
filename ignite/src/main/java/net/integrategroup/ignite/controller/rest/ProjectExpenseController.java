package net.integrategroup.ignite.controller.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.ProjectExpense;
import net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto;
import net.integrategroup.ignite.persistence.model.VProjectExpense;
import net.integrategroup.ignite.persistence.model.VProjectExpenseMin;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.persistence.service.ProjectExpenseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project-expense")
public class ProjectExpenseController {

	@Autowired
	ProjectExpenseService projectExpenseService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	KeyValuePairService keyValuePairService;

	@GetMapping()
	public ResponseEntity<?> getAllProjectExpenses() {
		try {
			List<VProjectExpense> result = projectExpenseService.getProjectExpense();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProjectExpense(@RequestBody ProjectExpense projectExpense) {
		try {

			ProjectExpense test = projectExpenseService.findByProjectExpenseId(projectExpense.getProjectExpenseId());
			if (test == null) {
				throw new Exception("Project Expense not found");
			}

			projectExpense = projectExpenseService.save(projectExpense);
			return ResponseEntity.ok(projectExpense);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProjectExpenseNew(@RequestBody ProjectExpense projectExpense) {
		try {
			ProjectExpense test = projectExpenseService.findByProjectExpenseId(projectExpense.getProjectExpenseId());
			if (test != null) {
				throw new Exception("The ProjectExpense already exists");
			}
			projectExpense = projectExpenseService.save(projectExpense);
			return ResponseEntity.ok(projectExpense);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProjectExpense(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectExpenseId = mu.getAsLongOrNull(data, "projectExpenseId");
		String sql = "CALL ig_db.deleteProjectExpense(?);";

		System.out.println ("CALL ig_db.deleteProjectExpense(" + projectExpenseId +");");
		try {	//**//					
			Object[] params = {		
				projectExpenseId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/project-participant/{projectParticipantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByProjectParticipantIdPayer(@PathVariable("projectParticipantId") Long projectParticipantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByProjectParticipantIdPayer(projectParticipantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/project/{projectId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByProjectId(@PathVariable("projectId") Long projectId,
												@PathVariable(name = "firstDay") Long firstDay,
												@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByProjectId(projectId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-all/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByProjectIdAll(@PathVariable(name = "firstDay") Long firstDay,
												@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByProjectIdAll(fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@GetMapping("/participant-payer/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantIdPayer(@PathVariable("participantId") Long participantId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByParticipantIdPayer(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-expense-type/{projectId}/{firstDay}/{lastDay}/{expenseTypeId}")
	public ResponseEntity<?> findByProjectIdExpenseType(@PathVariable("projectId") Long projectId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay,
														@PathVariable(name = "expenseTypeId") Long expenseTypeId) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByProjectIdExpenseType(projectId, fd, ld, expenseTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/participant-project-expense-type/{participantId}/{firstDay}/{lastDay}/{expenseTypeId}")
	public ResponseEntity<?> findByParticipantIdPayerExpenseType(@PathVariable("participantId") Long participantId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay,
														@PathVariable(name = "expenseTypeId") Long expenseTypeId) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByParticipantIdPayerExpenseType(participantId, fd, ld, expenseTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-summary/{projectId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByProjectIdSummary(@PathVariable("projectId") Long projectId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<ProjectExpenseSummaryDto> result = projectExpenseService.findByProjectIdSummary(projectId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/participant-purchase/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantIdMadePurchase(@PathVariable("participantId") Long participantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByParticipantIdMadePurchase(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/individual-purchase/{participantId}/{projectId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByIndividualIdMadePurchasebyProject(@PathVariable("participantId") Long participantId,
															@PathVariable(name = "projectId") Long projectId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByIndividualIdMadePurchasebyProject(participantId, projectId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/participant-vendor/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantIdVendor(@PathVariable("participantId") Long participantId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<VProjectExpense> result = projectExpenseService.findByParticipantIdVendor(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//Expenses:
	//--------------------------------------------------------------------------------
	@GetMapping("/participant-expenses-all/{firstDay}/{lastDay}/{participantIdPayer}")
	public ResponseEntity<?> findByParticipantAllExpensesNonAllowance(
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay,
														@PathVariable(name = "participantIdPayer") Long participantIdPayer
														) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);

			List<VProjectExpense> result = projectExpenseService.findByParticipantAllExpensesNonAllowance(fd, ld, participantIdPayer);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/non-asset-expenses-all/{participantIdPayer}")
	public ResponseEntity<?> findAllNonAssetExpensesPerParticipant(@PathVariable(name = "participantIdPayer") Long participantIdPayer) {
		try {
			List<VProjectExpenseMin> result = projectExpenseService.findAllNonAssetExpensesPerParticipant(participantIdPayer);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
	
	
	
	
	@GetMapping("/participant-expense-summary/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantIdExpenseSummary(@PathVariable("participantId") Long participantId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay) {
		try {

			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<ProjectExpenseSummaryDto> result = projectExpenseService.findByParticipantIdExpenseSummary(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	//Allowances
	@GetMapping("/participant-allowance-summary/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantIdAllowanceSummary(@PathVariable("participantId") Long participantId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay) {
		try {

			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);
			List<ProjectExpenseSummaryDto> result = projectExpenseService.findByParticipantIdAllowanceSummary(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/participant-allowance-claims-all/{firstDay}/{lastDay}/{participantIdPayer}")
	public ResponseEntity<?> findByParticipantAllAllowanceClaims(
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay,
														@PathVariable(name = "participantIdPayer") Long participantIdPayer
														) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);

			List<VProjectExpense> result = projectExpenseService.findByParticipantAllAllowances(fd, ld, participantIdPayer);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Expense and Allowances use this
	@GetMapping("/participant-expenses/{projectId}/{firstDay}/{lastDay}/{expenseTypeId}/{participantIdPayer}")
	public ResponseEntity<?> findByProjectIdExpenseType(@PathVariable("projectId") Long projectId,
														@PathVariable(name = "firstDay") Long firstDay,
														@PathVariable(name = "lastDay")  Long lastDay,
														@PathVariable(name = "expenseTypeId") Long expenseTypeId,
														@PathVariable(name = "participantIdPayer") Long participantIdPayer
														) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);

			List<VProjectExpense> result = projectExpenseService.findByProjectIdParticipantIdExpenseType(projectId, fd, ld, expenseTypeId, participantIdPayer);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/asset-expenses-all/{participantIdPayer}")
	public ResponseEntity<?> findAllAssetExpenses(
														@PathVariable(name = "participantIdPayer") Long participantIdPayer
														) {
		try {

			List<VProjectExpense> result = projectExpenseService.findAllAssetExpenses(participantIdPayer);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	

	
	
	@GetMapping({"/view/{projectExpenseId}" })
	public ResponseEntity<?> findViewByProjectExpenseId(ModelMap modelMap,
			@PathVariable(name = "projectExpenseId") Long projectExpenseId) {
		try {
			VProjectExpense result = projectExpenseService.findViewByProjectExpenseId(projectExpenseId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}