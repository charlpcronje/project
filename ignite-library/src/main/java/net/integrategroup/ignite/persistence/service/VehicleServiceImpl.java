package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;

	@Override
	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}

	@Override
	public List<Vehicle> findVehicleByParticipantId(Long participantId) {
		return vehicleRepository.findVehicleByParticipantId(participantId);
	}

	@Override
	public Vehicle save(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}

	@Override
	public Vehicle findByVehicleId(Long vehicleId) {
		return vehicleRepository.findByVehicleId(vehicleId);
	}

	@Override
	public VVehicle getVehicleDetail(Long vehicleId) {
		return vehicleRepository.getVehicleDetail(vehicleId);
	}

	@Override
	public List<Vehicle> findVehicleByUsername(String username) {
		return vehicleRepository.findVehicleByUsername(username);
	}

	//@Override
	//public void delete(Vehicle vehicle) {
	//	vehicleRepository.delete(vehicle);
	//}

}
