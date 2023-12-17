package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.RelatedDocs;

public interface RelatedDocsService {

	List<RelatedDocs> getRelatedDocs();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	RelatedDocs findByRelatedDocsId(Long relatedDocsId);

	List<RelatedDocs> findRelatedDocsNotLinked(Long genProcedureId);

	RelatedDocs save(RelatedDocs relatedDocs);

}
