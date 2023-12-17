package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.VContactPoint;

public interface ContactPointService {

	List<ContactPoint> findAll();

	ContactPoint save(ContactPoint contactPoint);

	ContactPoint findByContactPointId(Long contactPointId);

	//void delete(ContactPoint contactPoint);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	List<ContactPoint> getContactPointsForOffice(Long participantOfficeId);

	List<VContactPoint> findListVContactPointsForOffice(Long participantOfficeId);						//List from View that needs parameter

}
