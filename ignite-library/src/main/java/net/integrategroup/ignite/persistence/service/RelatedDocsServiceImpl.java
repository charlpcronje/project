package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RelatedDocs;
import net.integrategroup.ignite.persistence.repository.RelatedDocsRepository;

@Service
public class RelatedDocsServiceImpl implements RelatedDocsService {

	@Autowired
	RelatedDocsRepository relatedDocsRepository;

	@Override
	public RelatedDocs save(RelatedDocs relatedDocs) {
		return relatedDocsRepository.save(relatedDocs);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<RelatedDocs> getRelatedDocs() {
		return relatedDocsRepository.getRelatedDocs();
	}

	@Override
	public List<RelatedDocs> findRelatedDocsNotLinked(Long genProcedureId) {
		return relatedDocsRepository.findRelatedDocsNotLinked(genProcedureId);
	}

	@Override
	public RelatedDocs findByRelatedDocsId(Long relatedDocsId) {
		return relatedDocsRepository.findByRelatedDocsId(relatedDocsId);
	}
}
