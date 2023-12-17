package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.repository.KeyValuePairRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class KeyValuePairServiceImpl implements KeyValuePairService {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	KeyValuePairRepository keyValuePairRepository;

	@Override
	public List<KeyValuePair> findAll() {
		return keyValuePairRepository.findAll();
	}

	@Override
	public List<KeyValuePair> findGeneralParameters() {
		return keyValuePairRepository.findGeneralParameters();
	}

	@Override
	public KeyValuePair findByKeyName(String keyName) {
		return keyValuePairRepository.findByKeyName(keyName);
	}

	@Override
	public KeyValuePair save(KeyValuePair keyValuePair) {
		return keyValuePairRepository.save(keyValuePair);
	}

	@Override
	public void delete(KeyValuePair keyValuePair) {
		keyValuePairRepository.delete(keyValuePair);
	}

	@Override
	public String getKeyValue(String keyName) {
		String result = null;
		KeyValuePair kvp = findByKeyName(keyName);

		if (kvp != null) {
			result = kvp.getValue();
		}

		return result;
	}

}
