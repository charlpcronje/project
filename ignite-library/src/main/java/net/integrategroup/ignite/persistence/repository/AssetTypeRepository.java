package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AssetType;

@Repository
public interface AssetTypeRepository extends CrudRepository<AssetType, Long> {

	@Override
	List<AssetType> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    AssetType b" +
			"  WHERE" +
			"    b.assetTypeId not in ('VEHICLE', 'Toets' )")
	List<AssetType> findAllOther();





	@Query("SELECT cl FROM AssetType cl WHERE cl.assetTypeId = ?1")
	AssetType findByAssetTypeId(Long assetTypeId);

}
