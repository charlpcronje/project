package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.CardType;
import net.integrategroup.ignite.persistence.repository.CardTypeRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class CardTypeServiceImpl implements CardTypeService {

	@Autowired
	CardTypeRepository cardTypeRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<CardType> findAll() {
		return cardTypeRepository.findAll();
	}

	@Override
	public CardType findByCardTypeId(Long cardTypeId) {
		return cardTypeRepository.findByCardTypeId(cardTypeId);
	}

	@Override
	public CardType save(CardType cardType) {
		return cardTypeRepository.save(cardType);
	}

	//@Override
	//public void delete(CardType cardType) {
	//	cardTypeRepository.delete(cardType);
	//}

}
