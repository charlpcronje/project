package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.Calendar;

public interface CalendarService {

	List<Calendar> findAll();

	List<Calendar> findFutureEvents();

	Calendar findByDate(Date calendarDate);

	Calendar save(Calendar calendar);

	void delete(Calendar calendar);

}
