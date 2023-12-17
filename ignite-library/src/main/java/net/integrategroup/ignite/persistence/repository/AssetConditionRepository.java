package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AssetCondition;

@Repository
public interface AssetConditionRepository extends CrudRepository<AssetCondition, Long> {

	@Override
	List<AssetCondition> findAll();

	@Query("SELECT cl FROM AssetCondition cl WHERE cl.assetConditionId = ?1")
	AssetCondition findByAssetConditionId(Long assetConditionId);

}
