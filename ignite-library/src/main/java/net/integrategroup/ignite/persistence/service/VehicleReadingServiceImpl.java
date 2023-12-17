package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VehicleReading;
import net.integrategroup.ignite.persistence.repository.VehicleReadingRepository;

@Service
public class VehicleReadingServiceImpl implements VehicleReadingService {

	@Autowired
	VehicleReadingRepository vehicleReadingRepository;

	@Override
	public List<VehicleReading> findAll() {
		return vehicleReadingRepository.findAll();
	}


	@Override
	public List<VehicleReading> findByVehicleId(Long vehicleId) {
		return vehicleReadingRepository.findByVehicleId(vehicleId);
	}

	@Override
	public VehicleReading save(VehicleReading vehicleReading) {
		return vehicleReadingRepository.save(vehicleReading);
	}

	@Override
	public VehicleReading findByVehicleReadingId(Long vehicleReadingId) {
		return vehicleReadingRepository.findByVehicleReadingId(vehicleReadingId);
	}

}
