package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.AssetType;

public interface AssetTypeService {

	AssetType findByAssetTypeId(Long assetTypeId);

	List<AssetType> findAll();

	List<AssetType> findAllOther();

	AssetType save(AssetType assetType);

	//void delete(Long assetTypeId);

}
