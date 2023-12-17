package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VehicleModel;
import net.integrategroup.ignite.persistence.repository.VehicleModelRepository;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {

	@Autowired
	VehicleModelRepository vehicleModelRepository;

	@Override
	public List<VehicleModel> findAll() {
		return vehicleModelRepository.findAll();
	}

	@Override
	public List<VehicleModel> findByVehicleMakeId(Long vehicleMakeId) {
		return vehicleModelRepository.findByVehicleMakeId(vehicleMakeId);
	}

	@Override
	public VehicleModel save(VehicleModel vehicleModel) {
		return vehicleModelRepository.save(vehicleModel);
	}

	@Override
	public VehicleModel findByVehicleModelId(Long vehicleModelId) {
		return vehicleModelRepository.findByVehicleModelId(vehicleModelId);
	}

	//@Override
	//public void delete(VehicleModel vehicleModel) {
	//	vehicleModelRepository.delete(vehicleModel);
	//}

}
