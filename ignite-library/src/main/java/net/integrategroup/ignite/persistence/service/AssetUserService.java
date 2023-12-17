package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.model.AssetUserDto;
import net.integrategroup.ignite.persistence.model.AssetUser;

public interface AssetUserService {

	List<AssetUser> findAll();

	AssetUser findByAssetUserId(Long assetUserId);

	List<AssetUserDto> findAssetUserByAssetId(Long assetId);

	AssetUser save(AssetUser assetUser);

}
