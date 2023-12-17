package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Invoice;
import net.integrategroup.ignite.persistence.model.InvoiceLineDetail;
import net.integrategroup.ignite.persistence.model.InvoiceLineDto;
import net.integrategroup.ignite.persistence.model.InvoiceLineTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsDto;
import net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerExpenseDto;
import net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.VPpExpenseRateUplineRecursive;
import net.integrategroup.ignite.persistence.repository.InvoiceRepository;

// @author Ingrid Marais

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	InvoiceRepository invoiceRepository;

	@Override
	public List<Invoice> findInvoicesInForParticipant(Long participantId, Date firstDay, Date lastDay) {
		return invoiceRepository.findInvoicesInForParticipant(participantId, firstDay, lastDay);
	}

	@Override
	public Invoice save(Invoice invoice) {
		return invoiceRepository.save(invoice);
	}
	@Override
	public Invoice findByInvoiceId(Long invoiceId) {
		return invoiceRepository.findByInvoiceId(invoiceId);
	}
	@Override
	public Invoice findViewByInvoiceId(Long invoiceId) {
		return invoiceRepository.findViewByInvoiceId(invoiceId);
	}

	@Override
	public List<Invoice> findInvoicesOutForParticipant(Long participantId, Date firstDay, Date lastDay) {
		return invoiceRepository.findInvoicesOutForParticipant(participantId, firstDay, lastDay);
	}

//	@Override
//	public List<ParticipantTimeCostTotalsDto> findInvoicesAvailableRelationshipsTimeCost(Long participantIdBeneficiary) {
//		return invoiceRepository.findInvoicesAvailableRelationshipsTimeCost(participantIdBeneficiary);
//	}
//
//	@Override
//	public List<ParticipantExpenseCostTotalsDto> findInvoicesAvailableRelationshipsExpense(Long participantIdContracted) {
//		return invoiceRepository.findInvoicesAvailableRelationshipsExpense(participantIdContracted);
//	}

//	@Override
//	public List<InvoiceOutLineTotalsDto> findInvoicesOutAvailableLineTotals(Long participantIdContracted, Date firstDay, Date lastDay) {
//		return invoiceRepository.findInvoicesOutAvailableLineTotals(participantIdContracted, firstDay, lastDay);
//	}

//	@Override
//	public List<InvoiceOutLineTotalsPerProjectDto> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay) {
//		return invoiceRepository.findInvoicesOutPerProjectTotals(participantIdContracted, participantIdContracting, lastDay);
//	}

//	@Override
//	public List<InvoiceOutLineTotalsPerExpenseDto> findInvoicesOutPerExpenseTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay) {
//		return invoiceRepository.findInvoicesOutPerExpenseTotals(participantIdContracted, participantIdContracting, lastDay);
//	}



	// ParticipantTimeCostTotalsPerProjectDto probleempie

	@Override
	public List<VPpExpenseRateUplineRecursive> findContractingContractedExpenseCost(Long expenseTypeId, Long participantIdContracting, Long participantIdContracted, Long projectId) {
		return invoiceRepository.findContractingContractedExpenseCost(expenseTypeId, participantIdContracting, participantIdContracted, projectId);
	}

	@Override
	public List<InvoiceLineDto> findInvoiceLines(Long invoiceId) {
		return invoiceRepository.findInvoiceLines(invoiceId);
	}


//	@Override
//	public List<GeneratableInvoiceTotalsNonNullValuesDto> findInvoicesOutAvailableLineTotalsNotNull(Long participantIdContracted, Date lastDay) {
//		return invoiceRepository.findInvoicesOutAvailableLineTotalsNotNull(participantIdContracted, lastDay);
//	}

	@Override
	public List<InvoiceLineDetail> findDraftInvoiceDetailsForInvoiceLine(Long invoiceId, Long projectId, String lineType) {
		return invoiceRepository.findDraftInvoiceDetailsForInvoiceLine(invoiceId, projectId, lineType);
	}


	//--------------------------------------------------------------------------------------------//
	// Invoice Generator
	// Table 1
	// Available invoices to generate for dateRange Table 1 (Rates missing also shown)
	@Override
	public List<InvoiceOutLineTotalsDto> findInvoicesOutAvailableLineTotals(Long participantIdContracted, Date lastDay) {
		return invoiceRepository.findInvoicesOutAvailableLineTotals(participantIdContracted,lastDay);
	}

	// Table 2
	// Available invoices to generate for dateRange per Project
	@Override
	public List<InvoiceOutLineTotalsPerProjectDto> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay) {
		return invoiceRepository.findInvoicesOutPerProjectTotals(participantIdContracted, participantIdContracting, lastDay);
	}

	// Table 3
	// Available invoices lines to generate for dateRange per Project per Expense Type
	@Override
	public List<InvoiceOutLineTotalsPerExpenseDto> findInvoicesOutPerProjectPerExpenseTotals(Long participantIdContracted, Long participantIdContracting,
							Date lastDay, Long projectId) {
		return invoiceRepository.findInvoicesOutPerProjectPerExpenseTotals(participantIdContracted, participantIdContracting, lastDay, projectId);
	}

	// Time Related Costs
	// Line Totals Table Table 4a
	@Override
	public List<ParticipantTimeCostTotalsPerProjectDto> findLineTotalsTimeCost(Long participantIdContracting, Long participantIdContracted, Date lastDay, Long projectId) {
		return invoiceRepository.findLineTotalsTimeCost(participantIdContracting, participantIdContracted, lastDay, projectId);
	}

	// Expense Claim Costs
	// Line Totals Table Table 4b
	@Override
	public List<ParticipantExpenseCostTotalsPerProjectDto> findLineTotalsExpenseClaim(Long participantIdContracting, Long participantIdContracted, Date lastDay, Long projectId) {
		return invoiceRepository.findLineTotalsExpenseClaim(participantIdContracting, participantIdContracted, lastDay, projectId);
	}



	//--------------------------------------------------------------------------------------------//
	// Draft Invoice
	// Table 1
	// Available Draft invoices (Rates missing also shown)
	@Override
	public List<Invoice> findDraftInvoicesForParticipant(Long participantId) {
		return invoiceRepository.findDraftInvoicesForParticipant(participantId);
	}


	// Table 2
	// Invoice Line Totals per Project
	@Override
	public List<InvoiceOutLineTotalsPerProjectDto> getInvoiceLineTotalsPerProject(Long invoiceId) {
		return invoiceRepository.getInvoiceLineTotalsPerProject(invoiceId);
	}

	// Table 3
	// Available invoices lines to generate for dateRange per Project per Expense Type
	@Override
	public List<InvoiceOutLineTotalsPerExpenseDto> getInvoiceLineTotalsPerProjectAndExpense(Long invoiceId, Long projectId) {
		return invoiceRepository.getInvoiceLineTotalsPerProjectAndExpense(invoiceId, projectId);
	}

	// Time Related Costs
	// Line Totals Table Table 4a
	@Override
	public List<InvoiceLineTimeCostTotalsPerProjectDto> findInvoiceLineTotalsTimeCostPerProject(Long invoiceId, Long projectId, String lineType) {
		return invoiceRepository.findInvoiceLineTotalsTimeCostPerProject(invoiceId, projectId, lineType);
	}

	// Expense Claim Costs
	// Line Totals Table Table 4b

	// Draft Table 5b : Expense Claim Details
	@Override
	public List<InvoiceLineDetail> findDraftInvoiceDetailsForExpenseLine(Long invoiceId, Long projectId, String expenseType) {
		return invoiceRepository.findDraftInvoiceDetailsForExpenseLine(invoiceId, projectId, expenseType);
	}



}

