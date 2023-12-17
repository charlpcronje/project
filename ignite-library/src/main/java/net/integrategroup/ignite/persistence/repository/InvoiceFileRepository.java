package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.InvoiceFile;

@Repository
public interface InvoiceFileRepository extends CrudRepository<InvoiceFile, Long>{

	@Query("select i from InvoiceFile i where i.invoiceId = ?1")
	List<InvoiceFile> findFilesByInvoiceId(Long invoiceId);

	@Query("select i from InvoiceFile i where i.invoiceFileId = ?1")
	InvoiceFile findByInvoiceFileId(Long invoiceFileId);
}
