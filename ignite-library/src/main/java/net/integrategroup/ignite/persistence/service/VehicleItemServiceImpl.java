package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VehicleItem;
import net.integrategroup.ignite.persistence.repository.VehicleItemRepository;

@Service
public class VehicleItemServiceImpl implements VehicleItemService {

	@Autowired
	VehicleItemRepository vehicleItemRepository;

	@Override
	public List<VehicleItem> findAll() {
		return vehicleItemRepository.findAll();
	}


	@Override
	public List<VehicleItem> findByVehicleId(Long vehicleId) {
		return vehicleItemRepository.findByVehicleId(vehicleId);
	}

	@Override
	public VehicleItem save(VehicleItem vehicleItem) {
		return vehicleItemRepository.save(vehicleItem);
	}

	@Override
	public VehicleItem findByVehicleItemId(Long vehicleItemId) {
		return vehicleItemRepository.findByVehicleItemId(vehicleItemId);
	}

}
