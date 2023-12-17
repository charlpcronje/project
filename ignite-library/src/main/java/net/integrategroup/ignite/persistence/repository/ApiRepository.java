package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Api;

@Repository
public interface ApiRepository extends CrudRepository<Api, Long> {

	@Query("SELECT a FROM Api a WHERE apiId = ?1")
	Api findByApiId(Long apiId);

	@Override
	@Query("SELECT " +
			"    new net.integrategroup.ignite.persistence.model.Api(" +
			"        apiId, applicationName, apiKey, secret, activeFlag, lastUpdateTimestamp, lastUpdateUserName" +
			")" +
			"  FROM " +
			"    Api a ")
	List<Api> findAll();

	@Query("SELECT " +
			"    new net.integrategroup.ignite.persistence.model.Api(" +
			"        apiId, applicationName, apiKey, secret, activeFlag, lastUpdateTimestamp, lastUpdateUserName" +
			")" +
			"  FROM " +
			"    Api a " +
			"  WHERE " +
			"    a.apiKey = ?1")
	Api findByApiKey(String apiKey);
}
