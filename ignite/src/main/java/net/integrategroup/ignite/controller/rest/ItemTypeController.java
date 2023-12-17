package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
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

import net.integrategroup.ignite.persistence.model.ItemType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ItemTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/item-type")
public class ItemTypeController {

	@Autowired
	ItemTypeService itemTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getItemType() {
		try {
			List<ItemType> result = itemTypeService.getItemType();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveItemType(@RequestBody ItemType itemType) {
		try {

			ItemType test = itemTypeService
					.findByItemTypeId(itemType.getItemTypeId());
			if (test == null) {
				throw new Exception(" Type not found");
			}

			test.setName(itemType.getName());
			test.setDescription(itemType.getDescription());

			itemType = itemTypeService.save(test);
			return ResponseEntity.ok(itemType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveItemTypeNew(@RequestBody ItemType itemType) {
		try {

			ItemType test = itemTypeService
					.findByItemTypeId(itemType.getItemTypeId());
			if (test != null) {
				throw new Exception("ItemType already exists");
			}

			itemType = itemTypeService.save(itemType);
			return ResponseEntity.ok(itemType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteItemType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long itemTypeId = mu.getAsLongOrNull(data, "itemTypeId");
		String sql = "CALL ig_db.deleteItemType(?);";

		System.out.println ("CALL ig_db.deleteItemType(" + itemTypeId +");");
		try {	//**//					
			Object[] params = {		
				itemTypeId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/not-linked/{vehicleId}")
	public ResponseEntity<?> findItemTypeNotLinked(@PathVariable("vehicleId") Long vehicleId, ModelMap modelMap) {
		try {
			List<ItemType> result = itemTypeService.findItemTypeNotLinked(vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




}
