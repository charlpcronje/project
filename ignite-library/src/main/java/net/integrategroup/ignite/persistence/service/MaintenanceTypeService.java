package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.MaintenanceType;

public interface MaintenanceTypeService {

	List<MaintenanceType> getMaintenanceType();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	MaintenanceType findByMaintenanceTypeId(Long maintenanceTypeId);

	List<MaintenanceType> findMaintenanceTypeNotLinked(Long vehicleId);

	MaintenanceType save(MaintenanceType maintenanceType);

}
