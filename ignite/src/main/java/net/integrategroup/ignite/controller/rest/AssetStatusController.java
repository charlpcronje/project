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

import net.integrategroup.ignite.persistence.model.AssetStatus;
import net.integrategroup.ignite.persistence.service.AssetStatusService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/asset-status")
public class AssetStatusController {


	@Autowired
	AssetStatusService assetStatusService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getAssetStatus() {
		try {
			List<AssetStatus> result = assetStatusService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveAssetStatus(@RequestBody AssetStatus assetStatus) {
		try {
			AssetStatus test = assetStatusService.findByAssetStatusId(assetStatus.getAssetStatusId());
			if (test == null) {
				throw new Exception("Asset Status not found");
			}

			assetStatus = assetStatusService.save(assetStatus);

			return ResponseEntity.ok(assetStatus);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssetStatusNew(@RequestBody AssetStatus assetStatus) {
		try {
			AssetStatus test = assetStatusService.findByAssetStatusId(assetStatus.getAssetStatusId());
			if (test != null) {
				throw new Exception("Asset Status already exists");
			}

			assetStatus = assetStatusService.save(assetStatus);

			return ResponseEntity.ok(assetStatus);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAssetStatus(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long assetStatusId = mu.getAsLongOrNull(data, "assetStatusId");
		String sql = "CALL ig_db.deleteAssetStatus(?);";

		System.out.println ("CALL ig_db.deleteAssetStatus(" + assetStatusId+");");
		try {
			Object[] params = {
					assetStatusId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}