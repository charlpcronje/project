package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.PersonResponsible;
import net.integrategroup.ignite.persistence.repository.PersonResponsibleRepository;

@Service
public class PersonResponsibleServiceImpl implements PersonResponsibleService {

	@Autowired
	PersonResponsibleRepository personResponsibleRepository;


	@Override
	public List<PersonResponsible> findAll() {
		return personResponsibleRepository.findAll();
	}

	@Override
	public PersonResponsible save(PersonResponsible personResponsible) {
		return personResponsibleRepository.save(personResponsible);
	}

	@Override
	public PersonResponsible findByPersonResponsibleId(Long personResponsibleId) {
		return personResponsibleRepository.findByPersonResponsibleId(personResponsibleId);
	}


}
