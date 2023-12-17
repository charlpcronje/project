package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
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

import net.integrategroup.ignite.model.AssetUserDto;
import net.integrategroup.ignite.persistence.model.Asset;
import net.integrategroup.ignite.persistence.model.AssetUser;
import net.integrategroup.ignite.persistence.model.VAsset;
import net.integrategroup.ignite.persistence.service.AssetService;
import net.integrategroup.ignite.persistence.service.AssetUserService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/asset")
public class AssetController {

	@Autowired
	AssetService assetService;

	@Autowired
	AssetUserService assetUserService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllAssets() {
		try {
			List<Asset> result = assetService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/all-vasset")
	public ResponseEntity<?> findAllVasset() {
		try {
			List<VAsset> result = assetService.findAllVasset();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping()
	public ResponseEntity<?> saveAsset(@RequestBody Asset asset) {
		try {

			Asset test = assetService.findByAssetId(asset.getAssetId());
			if (test == null) {
				throw new Exception("Asset not found");
			}

			asset = assetService.save(asset);
			return ResponseEntity.ok(asset);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssetNew(@RequestBody Asset asset) {
		try {
			Asset test = assetService.findByAssetId(asset.getAssetId());
			if (test != null) {
				throw new Exception("The Asset already exists");
			}

			asset = assetService.save(asset);
			return ResponseEntity.ok(asset);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAsset(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long assetId = mu.getAsLongOrNull(data, "assetId");
		String sql = "CALL ig_db.deleteAsset(?);";

		System.out.println ("CALL ig_db.deleteAsset(" + assetId +");");
		try {
			Object[] params = {
					assetId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// /rest/ignite/v1/asset/participant/
	@GetMapping("/participant/{id}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantId(@PathVariable("id") Long participantId,
												 @PathVariable("firstDay") Date firstDay,
		 										 @PathVariable("lastDay") Date lastDay) {
		try {
			List<VAsset> result = assetService.findByParticipantId(participantId, firstDay, lastDay);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// /rest/ignite/v1/asset/participant-vehicles-included/
	@GetMapping("/participant-vehicles-included/{id}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findByParticipantIdVehicleInc(@PathVariable("id") Long participantId,
												 @PathVariable("firstDay") Date firstDay,
		 										 @PathVariable("lastDay") Date lastDay) {
		try {
			List<VAsset> result = assetService.findByParticipantIdVehicleInc(participantId, firstDay, lastDay);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// /rest/ignite/v1/asset/asset-info-for-vehicle/
	@GetMapping("/asset-info-for-vehicle/{participantId}/{vehicleId}")
	public ResponseEntity<?> findForVehicle(@PathVariable("participantId") Long participantId,
												@PathVariable("vehicleId") Long vehicleId) {

		try {
			VAsset result = assetService.findForVehicle(participantId, vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}


	@GetMapping("/next-asset-number/{participantId}")
	public ResponseEntity<?> getNextAssetNumber(@PathVariable("participantId") Long participantId, ModelMap modelMap) {
		try {

			Integer result = assetService.getNextAssetNumber(participantId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/by-assetId/{assetId}")
	public ResponseEntity<?> findByAssetId(@PathVariable("assetId") Long assetId) {
		try {
			Asset result = assetService.findByAssetId(assetId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/user/{assetId}")
	public ResponseEntity<?> findAssetUserByAssetId(@PathVariable("assetId") Long assetId) {
		try {
			List<AssetUserDto> result = assetUserService.findAssetUserByAssetId(assetId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/user/new")
	public ResponseEntity<?> saveAssetUserNew(@RequestBody AssetUser assetUser) {
		try {
			AssetUser test = assetUserService.findByAssetUserId(assetUser.getAssetUserId());
			if (test != null) {
				throw new Exception("The Asset User already exists");
			}

			assetUser = assetUserService.save(assetUser);
			return ResponseEntity.ok(assetUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}


	@PostMapping("/user")
	public ResponseEntity<?> saveAssetUser(@RequestBody AssetUser assetUser) {
		try {
			AssetUser test = assetUserService.findByAssetUserId(assetUser.getAssetUserId());
			if (test == null) {
				throw new Exception("The Asset User does not exist");
			}

			assetUser = assetUserService.save(assetUser);
			return ResponseEntity.ok(assetUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PostMapping("/user/delete")
	public ResponseEntity<?> deleteAssetUser(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long assetUserId = mu.getAsLongOrNull(data, "assetUserId");
		String sql = "CALL ig_db.deleteAssetUser(?);";

		System.out.println ("CALL ig_db.deleteAssetUser(" + assetUserId +");");
		try {
			Object[] params = {
					assetUserId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}