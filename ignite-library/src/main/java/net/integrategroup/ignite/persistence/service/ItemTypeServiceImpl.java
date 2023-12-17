package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ItemType;
import net.integrategroup.ignite.persistence.repository.ItemTypeRepository;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

	@Autowired
	ItemTypeRepository itemTypeRepository;

	@Override
	public ItemType save(ItemType itemType) {
		return itemTypeRepository.save(itemType);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<ItemType> getItemType() {
		return itemTypeRepository.getItemType();
	}

	@Override
	public List<ItemType> findItemTypeNotLinked(Long vehicleId) {
		return itemTypeRepository.findItemTypeNotLinked(vehicleId);
	}

	@Override
	public ItemType findByItemTypeId(Long itemTypeId) {
		return itemTypeRepository.findByItemTypeId(itemTypeId);
	}
}
