package net.integrategroup.ignite.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.UiComponentView;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.UiComponentViewService;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/ui-component-view")
public class UiComponentViewController {

	@Autowired
	UiComponentViewService uiComponentViewService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllUiComponents() {
		try {
			List<UiComponentView> result = uiComponentViewService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@PostMapping()
//	public ResponseEntity<?> saveUiComponent(@RequestBody UiComponentView uiComponentView) {
//		try {
//
//			UiComponentView test = uiComponentViewService.findByUiComponentId(uiComponentView.getUiComponentId());
//			if (test == null) {
//				throw new Exception("UiComponent not found");
//			}
//
//			uiComponentView = uiComponentViewService.save(uiComponentView);
//			return ResponseEntity.ok(uiComponentView);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//	@PostMapping("/new")
//	public ResponseEntity<?> saveUiComponentNew(@RequestBody UiComponentView uiComponentView) {
//		try {
//			UiComponentView test = uiComponentViewService.findByUiComponentId(uiComponentView.getUiComponentId());
//			if (test != null) {
//				throw new Exception("The UiComponent already exists");
//			}
//			uiComponentView = uiComponentViewService.save(uiComponentView);
//			return ResponseEntity.ok(uiComponentView);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}











	@GetMapping("/by-permission/{permissionCode}")
	public ResponseEntity<?> findByPermissionCode(@PathVariable("permissionCode") String permissionCode) {
		try {
			List<UiComponentView> result = uiComponentViewService.findByPermissionCode(permissionCode);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}