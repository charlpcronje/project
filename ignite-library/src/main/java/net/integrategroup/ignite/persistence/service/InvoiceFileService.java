package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.InvoiceFile;

public interface InvoiceFileService {

	List<InvoiceFile> findFilesByInvoiceId(Long invoiceId);

	InvoiceFile save(InvoiceFile invoiceFile);

	InvoiceFile findByInvoiceFileId(Long invoiceFileId);

	void delete(Long invoiceFileId);
}
