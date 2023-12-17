package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.Asset;
import net.integrategroup.ignite.persistence.model.VAsset;

public interface AssetService {

	List<Asset> findAll();

	List<VAsset> findAllVasset();

	List<VAsset> findByParticipantId(Long participantId, Date firstDay, Date lastDay);

	List<VAsset> findByParticipantIdVehicleInc(Long participantId, Date firstDay, Date lastDay);

	Asset save(Asset asset);

	Asset findByAssetId(Long assetId);

	VAsset findForVehicle(Long participantId, Long vehicleId);

	Integer getNextAssetNumber(Long participantId);




	// void delete(Asset asset);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
