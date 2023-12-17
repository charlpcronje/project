package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.model.AssetUserDto;
import net.integrategroup.ignite.persistence.model.AssetUser;
import net.integrategroup.ignite.persistence.repository.AssetUserRepository;

@Service
public class AssetUserServiceImpl implements AssetUserService {

	@Autowired
	AssetUserRepository assetUserRepository;

	@Override
	public List<AssetUser> findAll() {
		return assetUserRepository.findAll();
	}

	@Override
	public AssetUser findByAssetUserId(Long assetUserId) {
		return assetUserRepository.findByAssetUserId(assetUserId);
	}

	@Override
	public List<AssetUserDto> findAssetUserByAssetId(Long assetId) {
		return assetUserRepository.findAssetUserByAssetId(assetId);
	}


	@Override
	public AssetUser save(AssetUser assetUser) {
		return assetUserRepository.save(assetUser);
	}

}
