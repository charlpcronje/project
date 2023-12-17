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

import net.integrategroup.ignite.persistence.model.PaymentMethod;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.PaymentMethodService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/payment-method")
public class PaymentMethodController {

	@Autowired
	public PaymentMethodService paymentMethodService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<PaymentMethod> result = paymentMethodService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> savePaymentMethod(@RequestBody PaymentMethod paymentMethod) {
		try {

			PaymentMethod test = paymentMethodService.findByPaymentMethodCode(paymentMethod.getPaymentMethodCode());
			if (test == null) {
				throw new Exception("Payment Method not found");
			}

			paymentMethod = paymentMethodService.save(paymentMethod);
			return ResponseEntity.ok(paymentMethod);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> savePaymentMethodNew(@RequestBody PaymentMethod paymentMethod) {
		try {

			PaymentMethod test = paymentMethodService.findByPaymentMethodCode(paymentMethod.getPaymentMethodCode());
			if (test != null) {
				throw new Exception("The Payment Method already exists");
			}

			paymentMethod = paymentMethodService.save(paymentMethod);
			return ResponseEntity.ok(paymentMethod);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deletePaymentMethod(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		String paymentMethodCode = mu.getAsStringOrNull(data, "paymentMethodCode");
		String sql = "CALL ig_db.deletePaymentMethod(?);";

		System.out.println ("CALL ig_db.deletePaymentMethod(" + paymentMethodCode +");");
		try {	//**//					
			Object[] params = {		
				paymentMethodCode	
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
