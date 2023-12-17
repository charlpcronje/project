package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.InvoiceFile;
import net.integrategroup.ignite.persistence.repository.InvoiceFileRepository;

@Service
public class InvoiceFileServiceImpl implements InvoiceFileService {

	@Autowired
	InvoiceFileRepository invoiceFileRepository;

	@Override
	public List<InvoiceFile> findFilesByInvoiceId(Long invoiceId) {
		return invoiceFileRepository.findFilesByInvoiceId(invoiceId);
	}

	@Override
	public InvoiceFile save(InvoiceFile invoiceFile) {
		return invoiceFileRepository.save(invoiceFile);
	}

	@Override
	public InvoiceFile findByInvoiceFileId(Long invoiceFileId) {
		return invoiceFileRepository.findByInvoiceFileId(invoiceFileId);
	}

	@Override
	public void delete(Long invoiceFileId) {
		invoiceFileRepository.deleteById(invoiceFileId);
	}

}
