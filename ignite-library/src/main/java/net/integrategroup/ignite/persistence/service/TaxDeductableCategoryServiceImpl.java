package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TaxDeductableCategory;
import net.integrategroup.ignite.persistence.repository.TaxDeductableCategoryRepository;

@Service
public class TaxDeductableCategoryServiceImpl implements TaxDeductableCategoryService {

	@Autowired
	TaxDeductableCategoryRepository taxDeductableCategoryRepository;

	@Override
	public List<TaxDeductableCategory> findAll() {
		return taxDeductableCategoryRepository.findAll();
	}

	@Override
	public TaxDeductableCategory findByTaxDeductableCategoryId(Long taxDeductableCategoryId) {
		return taxDeductableCategoryRepository.findByTaxDeductableCategoryId(taxDeductableCategoryId);
	}

	@Override
	public TaxDeductableCategory save(TaxDeductableCategory taxDeductableCategory) {
		return taxDeductableCategoryRepository.save(taxDeductableCategory);
	}

}
