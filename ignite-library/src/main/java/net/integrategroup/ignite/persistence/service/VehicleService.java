package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;

public interface VehicleService {

	List<Vehicle> findAll();

	List<Vehicle> findVehicleByParticipantId(Long participantId);

	Vehicle save(Vehicle vehicle);

	Vehicle findByVehicleId(Long vehicleId);

	VVehicle getVehicleDetail(Long vehicleId);

	List<Vehicle> findVehicleByUsername(String username);

	// void delete(Vehicle vehicle);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
