package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Asset;
import net.integrategroup.ignite.persistence.model.VAsset;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {

	@Override
	List<Asset> findAll();


	@Query("SELECT b" +
			"  FROM " +
			"    VAsset b")
	List<VAsset> findAllVasset();


	@Query("SELECT b" +
			"  FROM " +
			"    VAsset b" +
			"  WHERE" +
			"    b.assetTypeId != 'VEHICLE'" +
			"  AND (" +
			"    (b.participantIdCurrentOwner = ?1 AND b.assetRemovedDate = null AND b.assetAquiredDate <= ?3)" +
			"  OR " +
			"    (b.participantIdOriginalOwner = ?1 AND b.assetRemovedDate != null AND ((b.assetAquiredDate BETWEEN ?2 AND ?3) OR (b.assetRemovedDate BETWEEN ?2 AND ?3)) ))")
	List<VAsset> findByParticipantId(Long participantId, Date firstDay, Date lastDay);


	@Query("SELECT b" +
			"  FROM " +
			"    VAsset b" +
			"  WHERE (" +
			"    (b.participantIdCurrentOwner = ?1 AND b.assetRemovedDate = null AND b.assetAquiredDate <= ?3)" +
			"  OR " +
			"    (b.participantIdOriginalOwner = ?1 AND b.assetRemovedDate != null AND ((b.assetAquiredDate BETWEEN ?2 AND ?3) OR (b.assetRemovedDate BETWEEN ?2 AND ?3)) ))")
	List<VAsset> findByParticipantIdVehicleInc(Long participantId, Date firstDay, Date lastDay);




	@Query("SELECT b" +
			"  FROM " +
			"    Asset b" +
			"  WHERE" +
			"    b.assetId = ?1")
	Asset findByAssetId(Long assetId);


	@Query("SELECT b" +
			"  FROM " +
			"    VAsset b" +
			"  WHERE" +
			"    b.participantIdCurrentOwner = ?1" +
			"  AND" +
			"    b.vehicleId = ?2")
	VAsset findForVehicle(Long participantId, Long vehicleId);




	@Query("SELECT max(a.assetNumber) + 1 FROM VAsset a WHERE a.participantIdCurrentOwner = ?1")
	Integer getNextAssetNumber(Long participantId);

}
