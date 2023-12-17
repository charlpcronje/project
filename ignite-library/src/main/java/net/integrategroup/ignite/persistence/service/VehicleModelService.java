package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VehicleModel;

public interface VehicleModelService {

	List<VehicleModel> findAll();

	List<VehicleModel> findByVehicleMakeId(Long vehicleMakeId);

	VehicleModel save(VehicleModel vehicleModel);

	VehicleModel findByVehicleModelId(Long vehicleModelId);

	// void delete(VehicleModel vehicleModel);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
