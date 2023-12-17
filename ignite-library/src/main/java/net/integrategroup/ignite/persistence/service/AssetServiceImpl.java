package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Asset;
import net.integrategroup.ignite.persistence.model.VAsset;
import net.integrategroup.ignite.persistence.repository.AssetRepository;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	AssetRepository assetRepository;

	@Override
	public List<Asset> findAll() {
		return assetRepository.findAll();
	}

	@Override
	public List<VAsset> findAllVasset() {
		return assetRepository.findAllVasset();
	}

	@Override
	public List<VAsset> findByParticipantId(Long participantId, Date firstDay, Date lastDay) {
		return assetRepository.findByParticipantId(participantId, firstDay, lastDay);
	}

	@Override
	public List<VAsset> findByParticipantIdVehicleInc(Long participantId, Date firstDay, Date lastDay) {
		return assetRepository.findByParticipantIdVehicleInc(participantId, firstDay, lastDay);
	}

	@Override
	public Asset save(Asset asset) {
		return assetRepository.save(asset);
	}

	@Override
	public Asset findByAssetId(Long assetId) {
		return assetRepository.findByAssetId(assetId);
	}

	@Override
	public VAsset findForVehicle(Long participantId, Long vehicleId) {
		return assetRepository.findForVehicle(participantId, vehicleId);
	}

	@Override
	public Integer getNextAssetNumber(Long participantId) {
		return assetRepository.getNextAssetNumber(participantId);
	}
	//@Override
	//public void delete(Asset asset) {
	//	assetRepository.delete(asset);
	//}

}
