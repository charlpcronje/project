package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VehicleItem;

@Repository
public interface VehicleItemRepository extends CrudRepository<VehicleItem, Long> {

	@Override
	List<VehicleItem> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    VehicleItem b" +
			"  WHERE" +
			"    b.vehicleId = ?1")
	List<VehicleItem> findByVehicleId(Long vehicleId);

	@Query("SELECT b FROM VehicleItem b WHERE b.vehicleItemId = ?1")
	VehicleItem findByVehicleItemId(Long vehicleItemId);
}
