package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VehicleItem;

public interface VehicleItemService {

	List<VehicleItem> findAll();

	List<VehicleItem> findByVehicleId(Long vehicleId);

	VehicleItem save(VehicleItem vehicleItem);

	VehicleItem findByVehicleItemId(Long vehicleItem);

	//void delete(VehicleItem vehicleItem);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
