package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.SoqTemplate;

@Service
public interface SoqTemplateService {
	
	List<SoqTemplate> findAll();
	
	void delete(SoqTemplate soqTemplate);

	SoqTemplate save(SoqTemplate soqTemplate);

	SoqTemplate findByTemplateId(Long soqTemplateId);

}
