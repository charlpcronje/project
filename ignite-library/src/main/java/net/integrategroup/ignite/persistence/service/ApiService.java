package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Api;

public interface ApiService {

	List<Api> findAll();

	Api findByApiKey(String apiKey);

	Api save(Api api);

	void delete(Long apiId);

	boolean isValidApiKey(String apiKey);

}
