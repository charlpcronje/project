package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.repository.TripLoggerRepository;


@Service
public class TripLoggerServiceImpl implements TripLoggerService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TripLoggerRepository tripLoggerRepository;

//	@Override
//	public List<TripLogger> findAll() {
//		return tripLoggerRepository.findAll();
//	}

	@Override
	public TripLogger save(TripLogger triplogger) {
		return tripLoggerRepository.save(triplogger);
	}

	@Override
	public List<VTripLogger> viewTripLogger() {
		return tripLoggerRepository.viewTripLogger();
	}
	@Override
	public List<VVehicle> viewVehicle() {
		return tripLoggerRepository.viewVehicle();
	}

	@Override
	public TripLogger findByTripLoggerId(Long tripLoggerId) {
		return tripLoggerRepository.findByTripLoggerId(tripLoggerId);
	}

	@Override
	public List<VTripLogger> getTripLoggerList(Long tripLoggerId, Date firstDay, Date lastDay) {
		return tripLoggerRepository.getTripLoggerList(tripLoggerId, firstDay, lastDay);
	}

	@Override
	public List<Vehicle> getVehicleByDriver(Long participantId, Long vehicleId) {
		return tripLoggerRepository.getVehicleByDriver(participantId, vehicleId);
	}

	@Override
	public List<TripLoggerPointNameDto> getStartPointByProject(Long projectId) {
		return tripLoggerRepository.getStartPointByProject(projectId);
	}

	@Override
	public List<TripLoggerPointNameDto> getEndPointByProject(Long projectId) {
		return tripLoggerRepository.getEndPointByProject(projectId);
	}

	@Override
	public List<TripLoggerDto> getBehalfOfById(Long participantId, Long participantIdOnBehalfOf) {
	    return tripLoggerRepository.getBehalfOfById(participantId, participantIdOnBehalfOf);
	}



}

