package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.model.AssetUserDto;
import net.integrategroup.ignite.persistence.model.AssetUser;

@Repository
public interface AssetUserRepository extends CrudRepository<AssetUser, Long>{

	@Override
	List<AssetUser> findAll();

	@Query("SELECT au" +
		    " FROM " +
			"AssetUser au" +
		    " WHERE" +
			" assetUserId = ?1")
	AssetUser findByAssetUserId(Long assetUserId);

	@Query("SELECT " +
			"     new net.integrategroup.ignite.model.AssetUserDto(" +
			"           au.assetUserId, au.assetId, au.participantId, " +
			"           au.description, au.lastUpdateTimestamp, au.lastUpdateUserName, " +
			"           p.systemName, i.firstName, i.lastName, i.initials)" +
		    " FROM " +
			"     AssetUser au," +
		    "     Participant p, " +
			"     Individual i   " +
		    " WHERE" +
			"     au.assetId = ?1" +
		    " AND " +
			"     p.participantId = au.participantId" +
			" AND " +
			"     i.participantId = p.participantId"
			)
	List<AssetUserDto> findAssetUserByAssetId(Long assetId);

}
