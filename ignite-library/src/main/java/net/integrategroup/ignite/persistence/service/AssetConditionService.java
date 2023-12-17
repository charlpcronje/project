package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.AssetCondition;

public interface AssetConditionService {

	AssetCondition findByAssetConditionId(Long assetConditionId);

	List<AssetCondition> findAll();

	AssetCondition save(AssetCondition assetCondition);

	//void delete(Long assetConditionId);

}
