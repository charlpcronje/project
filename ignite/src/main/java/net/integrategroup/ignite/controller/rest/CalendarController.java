package net.integrategroup.ignite.controller.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Calendar;
import net.integrategroup.ignite.persistence.service.CalendarService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/calendar")
public class CalendarController {

	@Autowired
	CalendarService calendarService;

	@GetMapping()
	public ResponseEntity<?> getCalendar() {
		try {
			List<Calendar> result = calendarService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveCalendar(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			String mode = mu.getAsStringOrNull(data, "mode");
			Date calendarDate = mu.getAsDateOrNull(data, "calendarDate");
			String eventName = mu.getAsStringOrNull(data, "eventName");
			String eventDescription = mu.getAsStringOrNull(data, "eventDescription");
			String isPublicHoliday = mu.getAsStringOrNull(data, "isPublicHoliday");

			Calendar calendar;
			if ("i".equalsIgnoreCase(mode)) {
				calendar = new Calendar();
				calendar.setCalendarDate(calendarDate);
			} else {
				calendar = calendarService.findByDate(calendarDate);

				if (calendar == null) {
					throw new Exception("Calendar entry not found");
				}
			}

			calendar.setEventName(eventName);
			calendar.setEventDescription(eventDescription);
			calendar.setIsPublicHoliday(isPublicHoliday);

			calendar = calendarService.save(calendar);

			return ResponseEntity.ok(calendar);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteCalendar(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Date calendarDate = mu.getAsDateOrNull(data, "calendarDate");
			Calendar calendar = calendarService.findByDate(calendarDate);

			if (calendar == null) {
				throw new Exception("Calendar entry not found");
			}

			calendarService.delete(calendar);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
