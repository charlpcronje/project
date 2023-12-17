package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Api;
import net.integrategroup.ignite.persistence.repository.ApiRepository;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	ApiRepository apiRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<Api> findAll() {
		return apiRepository.findAll();
	}

	@Override
	public Api findByApiKey(String apiKey) {
		return apiRepository.findByApiKey(apiKey);
	}

	@Override
	public Api save(Api api) {
		return apiRepository.save(api);
	}

	@Override
	public void delete(Long apiId) {
		Api api = apiRepository.findByApiId(apiId);

		apiRepository.delete(api);
	}

	@Override
	public boolean isValidApiKey(String apiKey) {
		boolean result = false;
		
		Api api = apiRepository.findByApiKey(apiKey);
		if (api != null) {
			result = IgniteConstants.FLAG_YES.equalsIgnoreCase(api.getActiveFlag());
		}
		
		return result;
	}

}
