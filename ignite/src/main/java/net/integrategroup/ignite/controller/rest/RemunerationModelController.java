package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.RemunerationModel;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RemunerationModelService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/remuneration-model")
public class RemunerationModelController {

	@Autowired
	public RemunerationModelService remunerationModelService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getRemunerationModels() {
		try {
			List<RemunerationModel> result = remunerationModelService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveRemunerationModel(@RequestBody RemunerationModel remunerationModel) {
		try {
			RemunerationModel test = remunerationModelService
					.findByRemunerationModelCode(remunerationModel.getRemunerationModelCode());
			if (test == null) {
				throw new Exception("Remuneration Model not found");
			}

			remunerationModel = remunerationModelService.save(remunerationModel);
			return ResponseEntity.ok(remunerationModel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveRemunerationModelNew(@RequestBody RemunerationModel remunerationModel) {
		try {
			RemunerationModel test = remunerationModelService
					.findByRemunerationModelCode(remunerationModel.getRemunerationModelCode());
			if (test != null) {
				throw new Exception("The Remuneration Model already exists");
			}

			remunerationModel = remunerationModelService.save(remunerationModel);
			return ResponseEntity.ok(remunerationModel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteRemunerationModel(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		String remunerationModelCode = mu.getAsStringOrNull(data, "remunerationModelCode");
		String sql = "CALL ig_db.deleteRemunerationModel(?);";

		System.out.println ("CALL ig_db.deleteRemunerationModel(" + remunerationModelCode + ");");
		try {	//**//					
			Object[] params = {		
				remunerationModelCode	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
