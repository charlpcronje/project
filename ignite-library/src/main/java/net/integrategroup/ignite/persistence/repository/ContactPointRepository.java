package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.VContactPoint;

@Repository
public interface ContactPointRepository extends CrudRepository<ContactPoint, Long> {

	@Override
	List<ContactPoint> findAll();

	// ContactPoint save(ContactPoint contactPoint);

	@Query("SELECT cp FROM ContactPoint cp WHERE cp.contactPointId = ?1")
	ContactPoint findByContactPointId(Long contactPointId);

	@Query("SELECT cp FROM ContactPoint cp WHERE cp.participantOfficeId = ?1")
	List<ContactPoint> getContactPointsForOffice(Long participantOfficeId);

	@Query("SELECT v FROM VContactPoint v WHERE v.participantOfficeId = ?1")				//List from View that needs parameter
	List<VContactPoint> findListVContactPointsForOffice(Long participantOfficeId);
}
