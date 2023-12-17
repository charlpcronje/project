package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.CardType;

public interface CardTypeService {

	List<CardType> findAll();

	CardType findByCardTypeId(Long cardTypeId);

	CardType save(CardType cardType);

	//void delete(CardType cardType);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
