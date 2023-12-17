package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.RemunerationInterval;

public interface RemunerationIntervalService {

	List<RemunerationInterval> findAll();

	RemunerationInterval save(RemunerationInterval RemunerationInterval);

	RemunerationInterval findByRemunerationIntervalCode(String remunerationIntervalCode);

	void delete(RemunerationInterval remunerationInterval);

}
