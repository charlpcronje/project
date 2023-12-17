package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.model.VehicleUserDto;
import net.integrategroup.ignite.persistence.model.VehicleUser;
import net.integrategroup.ignite.persistence.repository.VehicleUserRepository;

@Service
public class VehicleUserServiceImpl implements VehicleUserService {

	@Autowired
	VehicleUserRepository vehicleUserRepository;

	@Override
	public List<VehicleUser> findAll() {
		return vehicleUserRepository.findAll();
	}

	@Override
	public VehicleUser findByVehicleUserId(Long vehicleUserId) {
		return vehicleUserRepository.findbyVehicleUserId(vehicleUserId);
	}

	@Override
	public List<VehicleUserDto> findVehicleUserByVehicleId(Long vehicleId) {
		return vehicleUserRepository.findVehicleUserByVehicleId(vehicleId);
	}

	@Override
	public VehicleUser save(VehicleUser vehicleUser) {
		return vehicleUserRepository.save(vehicleUser);
	}

}
