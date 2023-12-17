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

import net.integrategroup.ignite.persistence.model.AssetCondition;
import net.integrategroup.ignite.persistence.service.AssetConditionService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/asset-condition")
public class AssetConditionController {


	@Autowired
	AssetConditionService assetConditionService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getAssetCondition() {
		try {
			List<AssetCondition> result = assetConditionService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveAssetCondition(@RequestBody AssetCondition assetCondition) {
		try {
			AssetCondition test = assetConditionService.findByAssetConditionId(assetCondition.getAssetConditionId());
			if (test == null) {
				throw new Exception("Asset Condition not found");
			}

			assetCondition = assetConditionService.save(assetCondition);

			return ResponseEntity.ok(assetCondition);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssetConditionNew(@RequestBody AssetCondition assetCondition) {
		try {
			AssetCondition test = assetConditionService.findByAssetConditionId(assetCondition.getAssetConditionId());
			if (test != null) {
				throw new Exception("Asset Condition already exists");
			}

			assetCondition = assetConditionService.save(assetCondition);

			return ResponseEntity.ok(assetCondition);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAssetCondition(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long assetConditionId = mu.getAsLongOrNull(data, "assetConditionId");
		String sql = "CALL ig_db.deleteAssetCondition(?);";

		System.out.println ("CALL ig_db.deleteAssetCondition(" + assetConditionId+");");
		try {
			Object[] params = {
					assetConditionId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}