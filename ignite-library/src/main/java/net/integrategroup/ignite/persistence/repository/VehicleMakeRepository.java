package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VehicleMake;

@Repository
public interface VehicleMakeRepository extends CrudRepository<VehicleMake, Long> {

	@Override
	List<VehicleMake> findAll();

	@Query("SELECT b FROM VehicleMake b WHERE b.vehicleMakeId = ?1")
	VehicleMake findByVehicleMakeId(Long vehicleMakeId);

}
