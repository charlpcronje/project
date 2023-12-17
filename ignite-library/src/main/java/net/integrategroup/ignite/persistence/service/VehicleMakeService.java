package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VehicleMake;
import net.integrategroup.ignite.persistence.model.VehicleModel;

public interface VehicleMakeService {

	List<VehicleMake> findAll();

	VehicleMake save(VehicleMake vehicleMake);

	VehicleModel save(VehicleModel vehicleModel);

	VehicleMake findByVehicleMakeId(Long vehicleMakeId);

	//void delete(VehicleMake vehicleMake);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
