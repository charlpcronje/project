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

import net.integrategroup.ignite.persistence.model.PaymentType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.PaymentTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/payment-type")
public class PaymentTypeController {

	@Autowired
	public PaymentTypeService paymentTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<PaymentType> result = paymentTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> savePaymentType(@RequestBody PaymentType paymentType) {
		try {

			PaymentType test = paymentTypeService.findByPaymentTypeId(paymentType.getPaymentTypeId());
			if (test == null) {
				throw new Exception("Payment Type not found");
			}

			paymentType = paymentTypeService.save(paymentType);
			return ResponseEntity.ok(paymentType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> savePaymentTypeNew(@RequestBody PaymentType paymentType) {
		try {

			PaymentType test = paymentTypeService.findByPaymentTypeId(paymentType.getPaymentTypeId());
			if (test != null) {
				throw new Exception("The Payment Type already exists");
			}

			paymentType = paymentTypeService.save(paymentType);
			return ResponseEntity.ok(paymentType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deletePaymentType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long paymentTypeId = mu.getAsLongOrNull(data, "paymentTypeId");
		String sql = "CALL ig_db.deletePaymentType(?);";

		System.out.println ("CALL ig_db.deletePaymentType(" + paymentTypeId +");");
		try {	//**//					
			Object[] params = {		
				paymentTypeId	
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
