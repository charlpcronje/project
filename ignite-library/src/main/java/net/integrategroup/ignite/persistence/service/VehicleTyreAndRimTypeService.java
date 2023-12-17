package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VehicleTyreAndRimType;

public interface VehicleTyreAndRimTypeService {

	List<VehicleTyreAndRimType> getVehicleTyreAndRimType();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	VehicleTyreAndRimType findByVehicleTyreAndRimTypeId(Long vehicleTyreAndRimTypeId);

	VehicleTyreAndRimType save(VehicleTyreAndRimType vehicleTyreAndRimType);

}
