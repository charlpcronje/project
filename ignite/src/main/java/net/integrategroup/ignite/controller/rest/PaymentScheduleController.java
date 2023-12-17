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

import net.integrategroup.ignite.persistence.model.PaymentSchedule;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.PaymentScheduleService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/payment-schedule")
public class PaymentScheduleController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	PaymentScheduleService paymentScheduleService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/{agreementBetweenParticipantsId}")
	public ResponseEntity<?> findAllForAgreement(@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId,
			ModelMap modelMap) {
		try {
			List<PaymentSchedule> result = paymentScheduleService.findAllForAgreement(agreementBetweenParticipantsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> savePaymentSchedule(@RequestBody PaymentSchedule paymentSchedule) {
		try {

			PaymentSchedule test = paymentScheduleService.findByPaymentScheduleId(paymentSchedule.getPaymentScheduleId());
			if (test == null) {
				throw new Exception("Payment Schedule not found");
			}

			paymentSchedule = paymentScheduleService.save(paymentSchedule);
			return ResponseEntity.ok(paymentSchedule);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> savePaymentScheduleNew(@RequestBody PaymentSchedule paymentSchedule) {
		try {

			PaymentSchedule test = paymentScheduleService.findByPaymentScheduleId(paymentSchedule.getPaymentScheduleId());
			if (test != null) {
				throw new Exception("Payment Schedule already exists");
			}

			paymentSchedule = paymentScheduleService.save(paymentSchedule);
			return ResponseEntity.ok(paymentSchedule);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deletePaymentSchedule(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long paymentScheduleId = mu.getAsLongOrNull(data, "paymentScheduleId");

		String sql = "CALL ig_db.deletePaymentSchedule(?);";

		System.out.println ("CALL ig_db.deletePaymentSchedule(" + paymentScheduleId+");");
		try {	//**//					
			Object[] params = {		
				paymentScheduleId	
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
