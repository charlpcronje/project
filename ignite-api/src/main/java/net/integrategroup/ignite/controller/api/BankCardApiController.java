package net.integrategroup.ignite.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.BankCard;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.VBankCard;
import net.integrategroup.ignite.persistence.service.BankCardService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.utils.HttpUtils;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/bank-card")
public class BankCardApiController {

	@Autowired
	IndividualService individualService;
	
	@Autowired
	BankCardService bankcardService;
	
	@GetMapping
	public ResponseEntity<?> getBankCards(HttpServletRequest request) {
		try {
			String username = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "u");
			
			List<VBankCard> cardList = bankcardService.findByUsername(username);
			
			return ResponseEntity.ok(cardList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
