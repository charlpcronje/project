package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.model.VehicleUserDto;
import net.integrategroup.ignite.persistence.model.VehicleUser;

public interface VehicleUserService {

	List<VehicleUser> findAll();

	VehicleUser findByVehicleUserId(Long vehicleUserId);

	List<VehicleUserDto> findVehicleUserByVehicleId(Long vehicleId);

	VehicleUser save(VehicleUser vehicleUser);
}
