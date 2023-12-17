package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Soq;
import net.integrategroup.ignite.persistence.model.SoqSubSchedule;
import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;

@Repository
public interface SoqRepository extends CrudRepository<Soq, Long> {

	@Query("SELECT s FROM Soq s")
	List<Soq> viewSoq();

	@Query("SELECT s FROM Soq s WHERE s.soqModeCode = ?1")
	Soq findByItemNo(Long itemNo);

	@Modifying
	@Query("UPDATE Soq s SET s.description = ?1 WHERE s.soqModeCode = ?2")
	void updateSoq(String value, Long id);
	
	
	@Query("SELECT sss FROM SoqSubSchedule sss")
	List<SoqSubSchedule> viewSoqSubSchedule();




}
