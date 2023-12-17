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

import net.integrategroup.ignite.persistence.model.CompetencyLevel;
import net.integrategroup.ignite.persistence.service.CompetencyLevelService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/competency-level")
public class CompetencyLevelController {


	@Autowired
	CompetencyLevelService competencyLevelService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getCompetencyLevel() {
		try {
			List<CompetencyLevel> result = competencyLevelService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveCompetencyLevel(@RequestBody CompetencyLevel competencyLevel) {
		try {
			CompetencyLevel test = competencyLevelService.findByCompetencyLevelId(competencyLevel.getCompetencyLevelId());
			if (test == null) {
				throw new Exception("Competency Level not found");
			}

			competencyLevel = competencyLevelService.save(competencyLevel);

			return ResponseEntity.ok(competencyLevel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveCompetencyLevelNew(@RequestBody CompetencyLevel competencyLevel) {
		try {
			CompetencyLevel test = competencyLevelService.findByCompetencyLevelId(competencyLevel.getCompetencyLevelId());
			if (test != null) {
				throw new Exception("Competency Level already exists");
			}

			competencyLevel = competencyLevelService.save(competencyLevel);

			return ResponseEntity.ok(competencyLevel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteCompetencyLevel(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long competencyLevelId = mu.getAsLongOrNull(data, "competencyLevelId");
		String sql = "CALL ig_db.deleteCompetencyLevel(?);";

		System.out.println ("CALL ig_db.deleteCompetencyLevel(" + competencyLevelId+");");
		try {
			Object[] params = {
					competencyLevelId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}