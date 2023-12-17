package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Health;
import net.integrategroup.ignite.persistence.repository.HealthRepository;

/**
 * @author Tony De Buys
 *
 */
@Service
public class HealthServiceImpl implements HealthService {

	@Autowired
	HealthRepository healthRepository;

	@Override
	public List<Health> findAll() {
		return healthRepository.findAll();
	}

	@Override
	public Health findByComponentName(String componentName) {
		return healthRepository.findByComponentName(componentName);
	}

	@Override
	public Health save(Health health) {
		return healthRepository.save(health);
	}

}
