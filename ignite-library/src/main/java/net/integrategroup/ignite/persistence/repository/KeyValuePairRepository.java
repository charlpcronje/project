package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.KeyValuePair;

@Repository
public interface KeyValuePairRepository extends CrudRepository<KeyValuePair, String> {

	@Override
	@Query("SELECT kv FROM KeyValuePair kv")
	List<KeyValuePair> findAll();

	@Query("SELECT kv FROM KeyValuePair kv WHERE kv.keyName NOT LIKE 'email%'")
	List<KeyValuePair> findGeneralParameters();

	@Query("SELECT kv FROM KeyValuePair kv WHERE kv.keyName = ?1")
	KeyValuePair findByKeyName(String keyName);

}
