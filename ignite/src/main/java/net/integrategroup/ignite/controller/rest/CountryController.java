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

import net.integrategroup.ignite.persistence.model.Country;
import net.integrategroup.ignite.persistence.model.VCountry;
import net.integrategroup.ignite.persistence.service.CountryService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/country")
public class CountryController {

	@Autowired
	public CountryService countryService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getCountries() {
		try {
			List<Country> reports = countryService.findAll();

			return ResponseEntity.ok(reports);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveCountry(@RequestBody Country country) {
		try {

			Country test = countryService.findByCountryId(country.getCountryId());
			if (test == null) {
				throw new Exception("Country not found");
			}

			country = countryService.save(country);
			return ResponseEntity.ok(country);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveCountryNew(@RequestBody Country country) {
		try {
			Country test = countryService.findByCountryId(country.getCountryId());
			if (test != null) {
				throw new Exception("The Country already exists");
			}

			country = countryService.save(country);
			return ResponseEntity.ok(country);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteCountry(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long countryId = mu.getAsLongOrNull(data, "countryId");
		String sql = "CALL ig_db.deleteCountry(?);";

		System.out.println ("CALL ig_db.deleteCountry(" + countryId+");");
		try {
			Object[] params = {
					countryId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-country-order-by-name")                                       //Find List from VCountry without parameter
	public ResponseEntity<?> findListVCountryOrderByName()  {
		try {
			List<VCountry> result = countryService.findListVCountryOrderByName();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	

}
