package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;

public interface TripLoggerService {


	List<VTripLogger> viewTripLogger();

	List<VTripLogger> getTripLoggerList(Long tripLoggerId, Date firstDay, Date lastDay);

	TripLogger findByTripLoggerId(Long tripLoggerId);

	TripLogger save(TripLogger triplogger);

	List<VVehicle> viewVehicle();

	List<Vehicle> getVehicleByDriver(Long participantId, Long vehicleId);

	List<TripLoggerPointNameDto> getStartPointByProject(Long projectId);

	List<TripLoggerPointNameDto> getEndPointByProject(Long projectId);

	List<TripLoggerDto> getBehalfOfById(Long participantId, Long participantIdOnBehalfOf);


}
