package net.integrategroup.ignite.controller.rest;

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

import net.integrategroup.ignite.persistence.model.GenProcedure;
import net.integrategroup.ignite.persistence.model.VPersonResponsible;
import net.integrategroup.ignite.persistence.model.VProcedureDefinition;
import net.integrategroup.ignite.persistence.model.VProcedureRelatedDocs;
import net.integrategroup.ignite.persistence.service.BranchService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.GenProcedureService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/gen-procedure")


public class GenProcedureController {

	@Autowired
	GenProcedureService genProcedureService;

	@Autowired
	BranchService branchService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllGenProcedures() {
		try {
			List<GenProcedure> result = genProcedureService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProcedure(@RequestBody GenProcedure genProcedure) {
		try {

			GenProcedure test = genProcedureService.findByGenProcedureId(genProcedure.getGenProcedureId());
			if (test == null) {
				throw new Exception("Procedure not found");
			}

			genProcedure = genProcedureService.save(genProcedure);
			return ResponseEntity.ok(genProcedure);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProcedureNew(@RequestBody GenProcedure genProcedure) {
		try {
			GenProcedure test = genProcedureService.findByGenProcedureId(genProcedure.getGenProcedureId());
			if (test != null) {
				throw new Exception("The Procedure already exists");
			}

			genProcedure = genProcedureService.save(genProcedure);
			return ResponseEntity.ok(genProcedure);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteGenProcedure(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long genProcedureId = mu.getAsLongOrNull(data, "genProcedureId");
		String sql = "CALL ig_db.deleteGenProcedure(?);";

		System.out.println ("CALL ig_db.deleteGenProcedure(" + genProcedureId +");");
		try {
			Object[] params = {
					genProcedureId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list/{serviceDisciplineId}")
	public ResponseEntity<?> findAllProceduresForServiceDisciplineId(@PathVariable("serviceDisciplineId") Long serviceDisciplineId, ModelMap modelMap) {
		try {
			List<GenProcedure> result = genProcedureService.findAllGenProceduresForServiceDisciplineId(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/next-gen-procedure-number/{serviceDisciplineId}")
	public ResponseEntity<?> getNextGenProcedureNumber(@PathVariable("serviceDisciplineId") Long serviceDisciplineId, ModelMap modelMap) {
		try {

			Integer result = genProcedureService.getNextGenProcedureNumber(serviceDisciplineId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/pd-list")
	public ResponseEntity<?> findAllProcedureDefinition() {
		try {
			List<VProcedureDefinition> result = genProcedureService.findAllProcedureDefinition();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/pd-list-procedure/{genProcedureId}")
	public ResponseEntity<?> findByVPDbyProcedureId(@PathVariable("genProcedureId") Long genProcedureId, ModelMap modelMap) {
		try {
			List<VProcedureDefinition> result = genProcedureService.findByVPDbyProcedureId(genProcedureId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/pd-list-definition/{definitionId}")
	public ResponseEntity<?> findByVPDbyDefinitionId(@PathVariable("definitionId") Long definitionId, ModelMap modelMap) {
		try {
			List<VProcedureDefinition> result = genProcedureService.findByVPDbyDefinitionId(definitionId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/pr-list")
	public ResponseEntity<?> findAllPersonResponsible() {
		try {
			List<VPersonResponsible> result = genProcedureService.findAllPersonResponsible();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/pr-list-procedure/{genProcedureId}")
	public ResponseEntity<?> findPRbyProcedureId(@PathVariable("genProcedureId") Long genProcedureId, ModelMap modelMap) {
		try {
			List<VPersonResponsible> result = genProcedureService.findPRbyProcedureId(genProcedureId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}





	@GetMapping("/prd-list")
	public ResponseEntity<?> findAllProcedureRelatedDocs() {
		try {
			List<VProcedureRelatedDocs> result = genProcedureService.findAllProcedureRelatedDocs();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/prd-list-procedure/{genProcedureId}")
	public ResponseEntity<?> findPRDbyProcedureId(@PathVariable("genProcedureId") Long genProcedureId, ModelMap modelMap) {
		try {
			List<VProcedureRelatedDocs> result = genProcedureService.findPRDbyProcedureId(genProcedureId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



}
