package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AssetStatus;

@Repository
public interface AssetStatusRepository extends CrudRepository<AssetStatus, Long> {

	@Override
	List<AssetStatus> findAll();

	@Query("SELECT cl FROM AssetStatus cl WHERE cl.assetStatusId = ?1")
	AssetStatus findByAssetStatusId(Long assetStatusId);

}
