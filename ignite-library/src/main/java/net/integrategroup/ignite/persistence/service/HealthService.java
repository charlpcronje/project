package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Health;

/**
 * @author Tony De Buys
 *
 */
public interface HealthService {

	List<Health> findAll();

	Health findByComponentName(String componentName);

	Health save(Health health);

}
