package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ItemType;

public interface ItemTypeService {

	List<ItemType> getItemType();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	ItemType findByItemTypeId(Long itemTypeId);

	List<ItemType> findItemTypeNotLinked(Long vehicleId);

	ItemType save(ItemType itemType);

}
