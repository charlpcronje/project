package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VehicleReading;

public interface VehicleReadingService {

	List<VehicleReading> findAll();

	List<VehicleReading> findByVehicleId(Long vehicleId);

	VehicleReading save(VehicleReading vehicleReading);

	VehicleReading findByVehicleReadingId(Long vehicleReading);

	//void delete(VehicleReading vehicleReading);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
