package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;

@Repository
public interface TripLoggerRepository extends CrudRepository<TripLogger, Long> {

	//@Override
	//List<TripLogger> findAll();

	@Query("Select v FROM VVehicle v")
	List<VVehicle> viewVehicle();

	@Query("SELECT tl FROM VTripLogger tl")
	List<VTripLogger> viewTripLogger();

	@Query("SELECT tl FROM TripLogger tl WHERE tl.tripLoggerId = ?1")
	TripLogger findByTripLoggerId(Long tripLoggerId);

	@Query("SELECT tl FROM VTripLogger tl"
			+ " WHERE tl.tripLoggerId = ?1"
			+ " AND tl.tripOrderDate BETWEEN ?2 AND ?3")
	List<VTripLogger> getTripLoggerList(Long tripLoggerId, Date firstDay, Date lastDay);

	@Query("select v"
			+ " from Vehicle v"
			+ " LEFT JOIN Asset a ON (a.vehicleId = v.vehicleId)"
			+ " LEFT JOIN Participant pt ON (pt.participantId = a.participantIdCurrentOwner)"
			+ " where pt.participantId = ?1 or v.vehicleId = ifnull(?2,-999)")
	List<Vehicle> getVehicleByDriver(Long participantId, Long vehicleId);

	@Query("SELECT DISTINCT NEW net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto(tl.tripStartingPointName, '') FROM TripLogger tl WHERE tl.projectId = ?1")
    List<TripLoggerPointNameDto> getStartPointByProject(Long projectId);

    @Query("SELECT DISTINCT NEW net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto('', tl.tripEndPointName) FROM TripLogger tl WHERE tl.projectId = ?1")
    List<TripLoggerPointNameDto> getEndPointByProject(Long projectId);

	@Query("SELECT DISTINCT new "
			+ " net.integrategroup.ignite.persistence.model.TripLoggerDto(tl.participantIdOnBehalfOf, tl.behalfOf)"
			+ " FROM VTripLogger tl"
			+ " WHERE tl.participantIdGaveInstruction = ?1 OR tl.participantIdOnBehalfOf = ?2")
	List<TripLoggerDto> getBehalfOfById(Long participantId, Long participantIdOnBehalfOf);





}
