package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.AssetStatus;

public interface AssetStatusService {

	AssetStatus findByAssetStatusId(Long assetStatusId);

	List<AssetStatus> findAll();

	AssetStatus save(AssetStatus assetStatus);

	//void delete(Long assetStatusId);

}
