package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.MaintenanceType;
import net.integrategroup.ignite.persistence.repository.MaintenanceTypeRepository;

@Service
public class MaintenanceTypeServiceImpl implements MaintenanceTypeService {

	@Autowired
	MaintenanceTypeRepository maintenanceTypeRepository;

	@Override
	public MaintenanceType save(MaintenanceType maintenanceType) {
		return maintenanceTypeRepository.save(maintenanceType);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<MaintenanceType> getMaintenanceType() {
		return maintenanceTypeRepository.getMaintenanceType();
	}

	@Override
	public List<MaintenanceType> findMaintenanceTypeNotLinked(Long vehicleId) {
		return maintenanceTypeRepository.findMaintenanceTypeNotLinked(vehicleId);
	}

	@Override
	public MaintenanceType findByMaintenanceTypeId(Long maintenanceTypeId) {
		return maintenanceTypeRepository.findByMaintenanceTypeId(maintenanceTypeId);
	}
}
