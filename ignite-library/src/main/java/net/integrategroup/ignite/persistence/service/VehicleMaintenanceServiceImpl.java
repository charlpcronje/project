package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VehicleMaintenance;
import net.integrategroup.ignite.persistence.repository.VehicleMaintenanceRepository;

@Service
public class VehicleMaintenanceServiceImpl implements VehicleMaintenanceService {

	@Autowired
	VehicleMaintenanceRepository vehicleMaintenanceRepository;

	@Override
	public List<VehicleMaintenance> findAll() {
		return vehicleMaintenanceRepository.findAll();
	}


	@Override
	public List<VehicleMaintenance> findByVehicleId(Long vehicleId) {
		return vehicleMaintenanceRepository.findByVehicleId(vehicleId);
	}

	@Override
	public VehicleMaintenance save(VehicleMaintenance vehicleMaintenance) {
		return vehicleMaintenanceRepository.save(vehicleMaintenance);
	}

	@Override
	public VehicleMaintenance findByVehicleMaintenanceId(Long vehicleMaintenanceId) {
		return vehicleMaintenanceRepository.findByVehicleMaintenanceId(vehicleMaintenanceId);
	}

}
