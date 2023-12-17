package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TaxDeductableCategory;

@Repository
public interface TaxDeductableCategoryRepository extends CrudRepository<TaxDeductableCategory, Long> {

	@Override
	List<TaxDeductableCategory> findAll();

	@Query("SELECT tdc FROM TaxDeductableCategory tdc WHERE taxDeductableCategoryId = ?1")
	TaxDeductableCategory findByTaxDeductableCategoryId(Long taxDeductableCategoryId);

}
