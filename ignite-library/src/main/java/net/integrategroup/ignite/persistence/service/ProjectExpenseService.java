package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectExpense;
import net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto;
import net.integrategroup.ignite.persistence.model.VProjectBankExpenses;
import net.integrategroup.ignite.persistence.model.VProjectExpense;
import net.integrategroup.ignite.persistence.model.VProjectExpenseMin;

public interface ProjectExpenseService {

	List<VProjectExpense> getProjectExpense();

	ProjectExpense save(ProjectExpense projectExpense);

	ProjectExpense findByProjectExpenseId(Long projectExpenseId);


	List<ProjectExpense> findProjectExpenseByProject(Long projectId);


	List<ProjectExpense> findProjectExpenseByProjectParticipant(Long projectParticipantId);

	List<ProjectExpense> findProjectExpenseByParticipantMadePurchase(Long participantId);

	List<ProjectExpense> findProjectExpenseByParticipantVendor(Long participantId);

	List<ProjectExpense> findProjectExpenseByExpenseType(Long expenceTypeId);

	List<ProjectExpense> findProjectExpenseByAsset(Long assetId);

	List<ProjectExpense> findProjectExpenseByPaymentMethod(String paymentMethodCode);

	List<ProjectExpense> findProjectExpenseByBankCard(Long bankCardId);

	List<ProjectExpense> findProjectExpenseByTaxDeductableCategory(Long taxDeductableCategoryId);
	
	List<VProjectExpense> findAllAssetExpenses(Long participantIdPayer);;

	List<VProjectExpenseMin> findAllNonAssetExpensesPerParticipant(Long participantIdPayer);

	
	
	
	VProjectExpense findViewByProjectExpenseId(Long projectExpenseId);

	List<VProjectExpense> findByProjectParticipantIdPayer(Long projectParticipantId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByParticipantIdPayer(Long participantId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByProjectIdExpenseType(Long projectId, Date firstDay, Date lastDay, Long expenseTypeId);

	List<VProjectExpense> findByParticipantIdPayerExpenseType(Long participantId, Date firstDay, Date lastDay, Long expenseTypeId);

	List<ProjectExpenseSummaryDto> findByProjectIdSummary(Long projectId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByProjectId(Long projectId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByProjectIdAll(Date firstDay, Date lastDay);

	List<VProjectExpense> findByParticipantIdMadePurchase(Long participantId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByIndividualIdMadePurchasebyProject(Long participantId, Long projectId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByParticipantIdVendor(Long participantId, Date firstDay, Date lastDay);

	// Expenses
	List<ProjectExpenseSummaryDto> findByParticipantIdExpenseSummary(Long participantId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByParticipantAllExpensesNonAllowance(Date firstDay, Date lastDay, Long participantIdPayer);

	//Allowances:

	List<ProjectExpenseSummaryDto> findByParticipantIdAllowanceSummary(Long participantId, Date firstDay, Date lastDay);

	List<VProjectExpense> findByParticipantAllAllowances(Date firstDay, Date lastDay, Long participantIdPayer);

	// Expense and Allowances use this
	List<VProjectExpense> findByProjectIdParticipantIdExpenseType(Long projectId, Date firstDay, Date lastDay, Long expenseTypeId, Long participantIdPayer);

	List<VProjectBankExpenses> getProjectExpensesByUsernameAndCardNumber(String username, String cardNumber);

	Long createProjectExpense(String cardNumber, String projectName, Date purchaseDate, String description,
			Double amountPerUnit, Double numberOfUnits, String noteToAccountant, String expenseType, 
			String paymentMethodCode, String username);

	
	
	
}
