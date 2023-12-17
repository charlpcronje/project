package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VehicleModel;

@Repository
public interface VehicleModelRepository extends CrudRepository<VehicleModel, Long> {

	@Override
	List<VehicleModel> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    VehicleModel b" +
			"  WHERE" +
			"    b.vehicleMakeId = ?1")
	List<VehicleModel> findByVehicleMakeId(Long vehicleMakeId);

	@Query("SELECT b" +
			"  FROM " +
			"    VehicleModel b" +
			"  WHERE" +
			"    b.vehicleModelId = ?1")
	VehicleModel findByVehicleModelId(Long vehicleModelId);

}
