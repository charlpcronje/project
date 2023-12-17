package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RemunerationModel;
import net.integrategroup.ignite.persistence.repository.RemunerationModelRepository;

@Service
public class RemunerationModelServiceImpl implements RemunerationModelService {

	@Autowired
	RemunerationModelRepository remunerationModelRepository;

	@Override
	public List<RemunerationModel> findAll() {
		return remunerationModelRepository.findAll();
	}

	@Override
	public RemunerationModel findByRemunerationModelCode(String remunerationModelCode) {
		return remunerationModelRepository.findByRemunerationModelCode(remunerationModelCode);
	}

	@Override
	public RemunerationModel save(RemunerationModel remunerationModel) {
		return remunerationModelRepository.save(remunerationModel);
	}

}
