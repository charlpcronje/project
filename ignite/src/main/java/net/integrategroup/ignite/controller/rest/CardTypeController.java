package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.CardType;
import net.integrategroup.ignite.persistence.service.CardTypeService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/card-type")
public class CardTypeController {

	@Autowired
	public CardTypeService cardTypeService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getCardTypes() {
		try {
			List<CardType> result = cardTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("{cardTypeId}")
	public ResponseEntity<?> getCardType(@PathVariable(name = "cardTypeId") Long cardTypeId) {
		try {
			CardType result = cardTypeService.findByCardTypeId(cardTypeId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveCardType(@RequestBody CardType cardType) {
		try {

			CardType test = cardTypeService.findByCardTypeId(cardType.getCardTypeId());
			if (test == null) {
				throw new Exception("Card Type not found");
			}

			cardType = cardTypeService.save(cardType);
			return ResponseEntity.ok(cardType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveCardTypeNew(@RequestBody CardType cardType) {
		try {

			CardType test = cardTypeService.findByCardTypeId(cardType.getCardTypeId());
			if (test != null) {
				throw new Exception("The Card Type already exists");
			}

			cardType = cardTypeService.save(cardType);
			return ResponseEntity.ok(cardType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteCardType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long cardTypeId = mu.getAsLongOrNull(data, "cardTypeId");
		String sql = "CALL ig_db.deleteCardType(?);";

		System.out.println ("CALL ig_db.deleteCardType(" + cardTypeId+");");
		try {
			Object[] params = {
					cardTypeId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}