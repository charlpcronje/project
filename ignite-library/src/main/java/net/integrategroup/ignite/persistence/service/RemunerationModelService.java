package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.RemunerationModel;

public interface RemunerationModelService {

	List<RemunerationModel> findAll();

	RemunerationModel findByRemunerationModelCode(String remunerationModelCode);

	RemunerationModel save(RemunerationModel remunerationModel);


}
