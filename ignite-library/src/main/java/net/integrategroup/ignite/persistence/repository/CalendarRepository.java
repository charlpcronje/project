package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Calendar;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Date> {

	@Override
	List<Calendar> findAll();

	@Query("SELECT c FROM Calendar c WHERE c.calendarDate = ?1")
	Calendar findByCalendarDate(Date calendarDate);

	@Query("SELECT c FROM Calendar c WHERE c.calendarDate > NOW()")
	List<Calendar> findFutureEvents();
}
