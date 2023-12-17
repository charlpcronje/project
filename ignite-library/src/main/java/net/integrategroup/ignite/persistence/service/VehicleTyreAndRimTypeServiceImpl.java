package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VehicleTyreAndRimType;
import net.integrategroup.ignite.persistence.repository.VehicleTyreAndRimTypeRepository;

@Service
public class VehicleTyreAndRimTypeServiceImpl implements VehicleTyreAndRimTypeService {

	@Autowired
	VehicleTyreAndRimTypeRepository vehicleTyreAndRimTypeRepository;

	@Override
	public VehicleTyreAndRimType save(VehicleTyreAndRimType vehicleTyreAndRimType) {
		return vehicleTyreAndRimTypeRepository.save(vehicleTyreAndRimType);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<VehicleTyreAndRimType> getVehicleTyreAndRimType() {
		return vehicleTyreAndRimTypeRepository.getVehicleTyreAndRimType();
	}



	@Override
	public VehicleTyreAndRimType findByVehicleTyreAndRimTypeId(Long vehicleTyreAndRimTypeId) {
		return vehicleTyreAndRimTypeRepository.findByVehicleTyreAndRimTypeId(vehicleTyreAndRimTypeId);
	}
}
