package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VehicleMaintenance;

public interface VehicleMaintenanceService {

	List<VehicleMaintenance> findAll();

	List<VehicleMaintenance> findByVehicleId(Long vehicleId);

	VehicleMaintenance save(VehicleMaintenance vehicleMaintenance);

	VehicleMaintenance findByVehicleMaintenanceId(Long vehicleMaintenance);

	//void delete(VehicleMaintenance vehicleMaintenance);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
