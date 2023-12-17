package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.SoqTemplate;
import net.integrategroup.ignite.persistence.repository.SoqTemplateRepository;

@Service
public class SoqTemplateServiceImpl implements SoqTemplateService {

	@Autowired
	SoqTemplateRepository soqTemplateRepository;
	
	@Autowired
	DatabaseService databaseService;
	
	@Override
	public List<SoqTemplate> findAll() {
		return soqTemplateRepository.findAll();
	}
	
	public void delete(SoqTemplate soqTemplate) {
		soqTemplateRepository.delete(soqTemplate);
	}
	
	@Override
	public SoqTemplate save(SoqTemplate soqTemplate) {
		return soqTemplateRepository.save(soqTemplate);
	}
	
	@Override
	public SoqTemplate findByTemplateId(Long soqTemplateId) {
		return soqTemplateRepository.findByTemplateId(soqTemplateId);
	}

}
