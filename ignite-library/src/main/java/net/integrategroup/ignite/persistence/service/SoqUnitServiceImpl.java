package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.SoqUnit;
import net.integrategroup.ignite.persistence.repository.SoqUnitRepository;

@Service
public class SoqUnitServiceImpl implements SoqUnitService {

	@Autowired
	SoqUnitRepository soqUnitRepository;
	
	@Autowired
	DatabaseService databaseService;
	
	@Override
	public List<SoqUnit> findAll() {
		return soqUnitRepository.findAll();
	}
		
}
