package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

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

public interface InvoiceService {

	List<Invoice> findInvoicesInForParticipant(Long participantId, Date firstDay, Date lastDay);

	Invoice save(Invoice invoice);

	Invoice findByInvoiceId(Long invoiceId);

	Invoice findViewByInvoiceId(Long invoiceId);

	List<Invoice> findInvoicesOutForParticipant(Long participantId, Date firstDay, Date lastDay);

//	List<ParticipantTimeCostTotalsDto> findInvoicesAvailableRelationshipsTimeCost(Long participantIdBeneficary);
//
//	List<ParticipantExpenseCostTotalsDto> findInvoicesAvailableRelationshipsExpense(Long participantIdContracted);

//	List<InvoiceOutLineTotalsPerProjectDto> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);


//	List<InvoiceOutLineTotalsPerExpenseDto> findInvoicesOutPerExpenseTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);



	// ParticipantTimeCostTotalsPerProjectDto probleempie
	List<VPpExpenseRateUplineRecursive> findContractingContractedExpenseCost(Long expenseTypeId, Long participantIdContracting, Long participantIdContracted, Long projectId);

	List<InvoiceLineDto> findInvoiceLines(Long invoiceId);


//	List<GeneratableInvoiceTotalsNonNullValuesDto> findInvoicesOutAvailableLineTotalsNotNull(Long participantIdContracted, Date lastDay);


	List<InvoiceLineDetail> findDraftInvoiceDetailsForInvoiceLine(Long invoiceId, Long projectId, String lineType);


	//--------------------------------------------------------------------------------------------//
	// Invoice Generator
	// Table 1
	// Available invoices to generate for dateRange Table 1 (Rates missing also shown)
	List<InvoiceOutLineTotalsDto> findInvoicesOutAvailableLineTotals(Long participantIdContracted, Date lastDay);

	// Table 2
	// Available invoices to generate for dateRange per Project
	List<InvoiceOutLineTotalsPerProjectDto> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);

	// Table 3
	// Available invoices lines to generate for dateRange per Project per Expense Type
	List<InvoiceOutLineTotalsPerExpenseDto> findInvoicesOutPerProjectPerExpenseTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay, Long projectId);

	// Time Related Costs
	// Line Totals Table Table 4a
	List<ParticipantTimeCostTotalsPerProjectDto> findLineTotalsTimeCost(Long participantIdContracting, Long participantIdContracted, Date lastDay, Long projectId);

	// Expense Claim Costs
	// Line Totals Table Table 4b
	List<ParticipantExpenseCostTotalsPerProjectDto> findLineTotalsExpenseClaim(Long participantIdContracting, Long participantIdContracted, Date lastDay, Long projectId);



	//--------------------------------------------------------------------------------------------//
	// Draft Invoice
	// Table 1
	// Available Draft invoices (Rates missing also shown)
	List<Invoice> findDraftInvoicesForParticipant(Long participantId);

	// Table 2
	// Invoice Line Totals per Project
	List<InvoiceOutLineTotalsPerProjectDto> getInvoiceLineTotalsPerProject(Long invoiceId);

	// Table 3
	// Available invoices lines to generate for invoice per Project per Expense Type
	List<InvoiceOutLineTotalsPerExpenseDto> getInvoiceLineTotalsPerProjectAndExpense(Long invoiceId, Long projectId);

	// Time Related Costs
	// Line Totals Table Table 4a
	List<InvoiceLineTimeCostTotalsPerProjectDto> findInvoiceLineTotalsTimeCostPerProject(Long invoiceId, Long projectId, String lineType);

	// Expense Claim Costs
	// Line Totals Table Table 4b

	// Draft Table 5b : Expense Claim Details
	List<InvoiceLineDetail> findDraftInvoiceDetailsForExpenseLine(Long invoiceId, Long projectId, String expenseType);




}
