package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RemunerationInterval;
import net.integrategroup.ignite.persistence.repository.RemunerationIntervalRepository;

@Service
public class RemunerationIntervalServiceImpl implements RemunerationIntervalService {

	@Autowired
	RemunerationIntervalRepository remunerationIntervalRepository;

	@Override
	public List<RemunerationInterval> findAll() {
		return remunerationIntervalRepository.findAll();
	}

	@Override
	public RemunerationInterval save(RemunerationInterval remunerationInterval) {

		return remunerationIntervalRepository.save(remunerationInterval);
	}

	@Override
	public RemunerationInterval findByRemunerationIntervalCode(String remunerationIntervalCode) {
		return remunerationIntervalRepository.findByRemunerationIntervalCode(remunerationIntervalCode);
	}

	@Override
	public void delete(RemunerationInterval remunerationInterval) {
		remunerationIntervalRepository.delete(remunerationInterval);
	}

}
