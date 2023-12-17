package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VehicleReading;

@Repository
public interface VehicleReadingRepository extends CrudRepository<VehicleReading, Long> {

	@Override
	List<VehicleReading> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    VehicleReading b" +
			"  WHERE" +
			"    b.vehicleId = ?1")
	List<VehicleReading> findByVehicleId(Long vehicleId);

	@Query("SELECT b FROM VehicleReading b WHERE b.vehicleReadingId = ?1")
	VehicleReading findByVehicleReadingId(Long vehicleReadingId);
}
