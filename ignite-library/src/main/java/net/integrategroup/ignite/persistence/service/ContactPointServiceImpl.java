package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.VContactPoint;
import net.integrategroup.ignite.persistence.repository.ContactPointRepository;

@Service
public class ContactPointServiceImpl implements ContactPointService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	ContactPointRepository contactPointRepository;

	@Override
	public List<ContactPoint> findAll() {
		return contactPointRepository.findAll();
	}

	@Override
	public ContactPoint save(ContactPoint contactPoint) {
		return contactPointRepository.save(contactPoint);
	}

	@Override
	public ContactPoint findByContactPointId(Long contactPointId) {
		return contactPointRepository.findByContactPointId(contactPointId);
	}

	//@Override
	//public void delete(ContactPoint contactPoint) {
	//	contactPointRepository.delete(contactPoint);
	//}

	@Override
	public List<ContactPoint> getContactPointsForOffice(Long participantOfficeId) {
		return contactPointRepository.getContactPointsForOffice(participantOfficeId);
	}


	@Override								//List from View that needs parameter
	public List<VContactPoint> findListVContactPointsForOffice(Long participantOfficeId) {
		return contactPointRepository.findListVContactPointsForOffice(participantOfficeId);
	}

}
