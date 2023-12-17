package net.integrategroup.ignite.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.VBankCard;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.service.BankCardService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.ProjectParticipantService;
import net.integrategroup.ignite.utils.HttpUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/project")
public class ProjectApiController {

	@Autowired
	ProjectParticipantService projectParticipantService;
	
	@GetMapping
	public ResponseEntity<?> getProjectsForUsername(HttpServletRequest request) {
		try {
			String username = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "u");

			List<VProjectParticipant> cardList = projectParticipantService.findByUsername(username);
			
			return ResponseEntity.ok(cardList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}