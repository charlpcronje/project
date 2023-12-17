package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VehicleMake;
import net.integrategroup.ignite.persistence.model.VehicleModel;
import net.integrategroup.ignite.persistence.repository.VehicleMakeRepository;
import net.integrategroup.ignite.persistence.repository.VehicleModelRepository;

@Service
public class VehicleMakeServiceImpl implements VehicleMakeService {

	@Autowired
	VehicleMakeRepository vehicleMakeRepository;

	@Autowired
	VehicleModelRepository vehicleModelRepository;

	@Override
	public List<VehicleMake> findAll() {
		return vehicleMakeRepository.findAll();
	}

	@Override
	public VehicleMake save(VehicleMake vehicleMake) {
		return vehicleMakeRepository.save(vehicleMake);
	}

	@Override
	public VehicleModel save(VehicleModel vehicleModel) {
		return vehicleModelRepository.save(vehicleModel);
	}

	@Override
	public VehicleMake findByVehicleMakeId(Long vehicleMakeId) {
		return vehicleMakeRepository.findByVehicleMakeId(vehicleMakeId);
	}

}
