package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.CardType;

@Repository
public interface CardTypeRepository extends CrudRepository<CardType, Long> {

	@Override
	List<CardType> findAll();

	@Query("SELECT c FROM CardType c WHERE cardTypeId = ?1")
	CardType findByCardTypeId(Long cardTypeId);

}
