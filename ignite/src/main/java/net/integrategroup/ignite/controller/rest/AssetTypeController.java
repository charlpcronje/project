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

import net.integrategroup.ignite.persistence.model.AssetType;
import net.integrategroup.ignite.persistence.service.AssetTypeService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/asset-type")
public class AssetTypeController {


	@Autowired
	AssetTypeService assetTypeService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getAssetType() {
		try {
			List<AssetType> result = assetTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/other")
	public ResponseEntity<?> findAllOther() {

		try {
			List<AssetType> assetType = assetTypeService.findAllOther();
			return ResponseEntity.ok(assetType);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveAssetType(@RequestBody AssetType assetType) {
		try {
			AssetType test = assetTypeService.findByAssetTypeId(assetType.getAssetTypeId());
			if (test == null) {
				throw new Exception("Asset Type not found");
			}

			assetType = assetTypeService.save(assetType);

			return ResponseEntity.ok(assetType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssetTypeNew(@RequestBody AssetType assetType) {
		try {
			AssetType test = assetTypeService.findByAssetTypeId(assetType.getAssetTypeId());
			if (test != null) {
				throw new Exception("Asset Type already exists");
			}

			assetType = assetTypeService.save(assetType);

			return ResponseEntity.ok(assetType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAssetType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long assetTypeId = mu.getAsLongOrNull(data, "assetTypeId");
		String sql = "CALL ig_db.deleteAssetType(?);";

		System.out.println ("CALL ig_db.deleteAssetType(" + assetTypeId+");");
		try {
			Object[] params = {
					assetTypeId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}