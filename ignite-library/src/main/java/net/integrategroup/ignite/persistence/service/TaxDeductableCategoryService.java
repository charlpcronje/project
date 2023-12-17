package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TaxDeductableCategory;

public interface TaxDeductableCategoryService {

	public List<TaxDeductableCategory> findAll();

	public TaxDeductableCategory findByTaxDeductableCategoryId(Long taxDeductableCategoryId);

	public TaxDeductableCategory save(TaxDeductableCategory taxDeductableCategory);

}
