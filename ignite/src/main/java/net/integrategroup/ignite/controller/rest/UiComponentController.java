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

import net.integrategroup.ignite.persistence.model.UiComponent;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.UiComponentService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/ui-component")
public class UiComponentController {

	@Autowired
	UiComponentService uiComponentService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllUiComponents() {
		try {
			List<UiComponent> result = uiComponentService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveUiComponent(@RequestBody UiComponent uiComponent) {
		try {

			UiComponent test = uiComponentService.findByUiComponentId(uiComponent.getUiComponentId());
			if (test == null) {
				throw new Exception("UiComponent not found");
			}

			uiComponent = uiComponentService.save(uiComponent);
			return ResponseEntity.ok(uiComponent);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage()) ;
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveUiComponentNew(@RequestBody UiComponent uiComponent) {
		try {
			UiComponent test = uiComponentService.findByUiComponentId(uiComponent.getUiComponentId());
			if (test != null) {
				throw new Exception("The UiComponent already exists");
			}
			uiComponent = uiComponentService.save(uiComponent);
			return ResponseEntity.ok(uiComponent);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}





	@PostMapping("/delete")
	public ResponseEntity<?> deleteUiComponent(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long uiComponentId = mu.getAsLongOrNull(data, "uiComponentId");
		String sql = "CALL ig_db.deleteUiComponent(?);";

		System.out.println ("CALL ig_db.deleteUiComponent(" + uiComponentId +");");
		try {	//**//					
			Object[] params = {		
				uiComponentId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/unlink")
	public ResponseEntity<?> doRemoveUiComponentFromPermission(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long uiComponentId = mu.getAsLongOrNull(data, "uiComponentId");
		String sql = "CALL ig_db.doRemoveUiComponentFromPermission(?);";

		System.out.println ("CALL ig_db.doRemoveUiComponentFromPermission(" + uiComponentId +");");
		try {	//**//					
			Object[] params = {		
				uiComponentId	
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