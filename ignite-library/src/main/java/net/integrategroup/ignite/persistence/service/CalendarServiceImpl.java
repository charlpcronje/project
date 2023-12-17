package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Calendar;
import net.integrategroup.ignite.persistence.repository.CalendarRepository;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	CalendarRepository calendarRepository;

	@Override
	public List<Calendar> findAll() {
		return calendarRepository.findAll();
	}

	@Override
	public List<Calendar> findFutureEvents() {
		return calendarRepository.findFutureEvents();
	}

	@Override
	public Calendar findByDate(Date calendarDate) {
		return calendarRepository.findByCalendarDate(calendarDate);
	}

	@Override
	public Calendar save(Calendar calendar) {
		return calendarRepository.save(calendar);
	}

	@Override
	public void delete(Calendar calendar) {
		calendarRepository.delete(calendar);
	}

}
