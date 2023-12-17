package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ItemType;

@Repository
public interface ItemTypeRepository extends CrudRepository<ItemType, Long> {

	@Query("SELECT a FROM ItemType a")
	List<ItemType> getItemType();

	@Query("SELECT a FROM ItemType a WHERE itemTypeId = ?1")
	ItemType findByItemTypeId(Long itemTypeId);

	@Query("SELECT a FROM ItemType a WHERE itemTypeId not in (Select itemTypeId from VehicleItem where vehicleId = ?1)")
	List<ItemType> findItemTypeNotLinked(Long vehicleId);

}
