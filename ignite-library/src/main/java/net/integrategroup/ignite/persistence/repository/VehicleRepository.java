package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

	@Override
	List<Vehicle> findAll();

	@Query("SELECT b FROM Vehicle b WHERE b.vehicleId = ?1")
	Vehicle findByVehicleId(Long vehicleId);

	@Query("SELECT v" +
			"  FROM " +
			"    Vehicle v, Asset b" +
			"  WHERE" +
			"    b.participantIdCurrentOwner = ?1" +
			"  AND" +
			"    b.vehicleId = v.vehicleId")
	List<Vehicle> findVehicleByParticipantId(Long participantId);

	@Query("SELECT v FROM VVehicle v WHERE v.vehicleId = ?1")
	VVehicle getVehicleDetail(Long vehicleId);

	@Query("SELECT v" +
			"  FROM " +
			"    Vehicle v, " +
			"    Asset a, " +
			"    Individual i" +
			"  WHERE" +
			"    v.vehicleId = a.vehicleId" +
			"    AND a.participantIdCurrentOwner = i.participantId" +
			"    AND i.userName = ?1")
	List<Vehicle> findVehicleByUsername(String username);

}
