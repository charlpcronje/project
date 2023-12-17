package net.integrategroup.ignite.controller.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.BankTransaction;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.ParticipantBankDetails;
import net.integrategroup.ignite.persistence.model.VBankTransaction;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.service.BankTransactionService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.ParticipantBankDetailsService;
import net.integrategroup.ignite.utils.HttpUtils;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/transaction")
public class BankTransactionApiController {
	
	@Autowired
	BankTransactionService bankTransactionService;
	
	@Autowired
	IndividualService individualService;
	
	@Autowired
	ParticipantBankDetailsService participantBankDetailsService;
	
	@GetMapping
	public ResponseEntity<?> getTransactions(HttpServletRequest request) {
		try {
			/*
			 * Map<String, Object> data = ApiUtils.getParametersFromRequest(request);
			 * 
			 * // get the username from data MapUtils mu = new MapUtils(); String username =
			 * mu.getAsStringOrNull(data, "u");
			 */
			String username = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "u");
			String cardNumber = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "cn");
			
			// get transactions associated with this user
			List<VBankTransaction> transactionList = bankTransactionService.getBankTransactionsByUsernameAndCardNumber(username, cardNumber);
			
			return ResponseEntity.ok(transactionList);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
