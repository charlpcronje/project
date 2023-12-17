package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.KeyValuePair;

public interface KeyValuePairService {

	List<KeyValuePair> findAll();

	List<KeyValuePair> findGeneralParameters();

	KeyValuePair findByKeyName(String keyName);

	KeyValuePair save(KeyValuePair keyValuePair);

	void delete(KeyValuePair keyValuePair);

	String getKeyValue(String keyName);

}
