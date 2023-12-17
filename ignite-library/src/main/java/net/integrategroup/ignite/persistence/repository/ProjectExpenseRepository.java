package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectExpense;
import net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto;
import net.integrategroup.ignite.persistence.model.VProjectBankExpenses;
import net.integrategroup.ignite.persistence.model.VProjectExpense;
import net.integrategroup.ignite.persistence.model.VProjectExpenseMin;

@Repository
public interface ProjectExpenseRepository extends CrudRepository<ProjectExpense, Long> {

	@Query("SELECT a FROM VProjectExpense a")
	List<VProjectExpense> getProjectExpense();

	@Query("SELECT a FROM ProjectExpense a WHERE projectExpenseId = ?1")
	ProjectExpense findByProjectExpenseId(Long projectExpenseId);

	@Query("SELECT a FROM ProjectExpense a "
			+ " LEFT JOIN ProjectParticipant pp "
			+ " ON a.projectParticipantIdPayer = pp.projectParticipantId"
			+ " WHERE pp.projectId = ?1")
	List<ProjectExpense> findProjectExpenseByProject(Long projectId);

	@Query("SELECT a FROM ProjectExpense a WHERE projectParticipantIdPayer = ?1")
	List<ProjectExpense> findProjectExpenseByProjectParticipant(Long projectParticipantId);

	@Query("SELECT a FROM ProjectExpense a WHERE participantIdMadePurchase = ?1")
	List<ProjectExpense> findProjectExpenseByParticipantMadePurchase(Long participantId);

	@Query("SELECT a FROM ProjectExpense a WHERE participantIdVendor = ?1")
	List<ProjectExpense> findProjectExpenseByParticipantVendor(Long participantId);

	@Query("SELECT a FROM ProjectExpense a WHERE expenceTypeId = ?1")
	List<ProjectExpense> findProjectExpenseByExpenseType(Long expenceTypeId);

	@Query("SELECT a FROM ProjectExpense a WHERE assetId = ?1")
	List<ProjectExpense> findProjectExpenseByAsset(Long assetId);

	@Query("SELECT a FROM ProjectExpense a WHERE paymentMethodCode = ?1")
	List<ProjectExpense> findProjectExpenseByPaymentMethod(String paymentMethodCode);

	@Query("SELECT a FROM ProjectExpense a WHERE bankCardIdUsed = ?1")
	List<ProjectExpense> findProjectExpenseByBankCard(Long bankCardId);

	@Query("SELECT a FROM ProjectExpense a WHERE taxDeductableCategoryId = ?1")
	List<ProjectExpense> findProjectExpenseByTaxDeductableCategory(Long taxDeductableCategoryId);

	@Query("SELECT a FROM VProjectExpense a WHERE a.assetId is not null and a.participantIdPayer = ?1")
	List<VProjectExpense> findAllAssetExpenses(Long participantIdPayer);

	@Query("SELECT a FROM VProjectExpenseMin a "
			+ " WHERE a.assetId is null  "
			+ " and a.vehicleId is null "
			+ " and a.allowanceFlag != 'Y' " 
			+ " and a.participantIdPayer = ?1")
	List<VProjectExpenseMin> findAllNonAssetExpensesPerParticipant(Long participantIdPayer);

	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE" +
			"    b.projectExpenseId = ?1")
	VProjectExpense findViewByProjectExpenseId(Long projectExpenseId);



	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.projectParticipantIdPayer = ?1" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByProjectParticipantIdPayer(Long projectParticipantId, Date firstDay, Date lastDay);

	// NOTE: change the package path to the package where you saved ProjectExpenseSummaryDto
	@Query("SELECT new net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto( "
			+ " b.projectId, "
			+ " b.projectName, "
			+ " b.subProjNumber, "
			+ " b.expenseTypeId, "
			+ " b.expenseTypeParentId, "
			+ " b.expenseTypeParentName, "
			+ " b.expenseTypeName, "
			+ " SUM(b.numberOfUnits * b.amountPerUnit) as theSum) "
			+ "  FROM "
			+ "    VProjectExpense b"
			+ "  WHERE "
			+ "    b.projectId = ?1"
			+ "  AND "
			+ "    b.purchaseDate BETWEEN ?2 AND ?3 "
			+ "  GROUP BY "
			+ "    b.projectId, "
			+ "    b.projectName, "
			+ "    b.subProjNumber, "
			+ "    b.expenseTypeId, "
			+ "    b.expenseTypeParentId, "
			+ "    b.expenseTypeParentName, "
			+ "    b.expenseTypeName " )
	List<ProjectExpenseSummaryDto> findByProjectIdSummary(Long projectId, Date firstDay, Date lastDay);



	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.participantIdPayer = ?1" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByParticipantIdPayer(Long participantId, Date firstDay, Date lastDay);

	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.projectId = ?1" +
			"  AND " +
			"    b.expenseTypeId = ?4" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByProjectIdExpenseType(Long projectId, Date firstDay, Date lastDay, Long expenseTypeId);

	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.participantIdPayer = ?1" +
			"  AND " +
			"    b.expenseTypeId = ?4" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByParticipantIdPayerExpenseType(Long participantId, Date firstDay, Date lastDay, Long expenseTypeId);



	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.projectId = ?1" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByProjectId(Long projectId, Date firstDay, Date lastDay);

	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.purchaseDate BETWEEN ?1 AND ?2")
	List<VProjectExpense> findByProjectIdAll(Date firstDay, Date lastDay);





	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.participantIdMadePurchase = ?1" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByParticipantIdMadePurchase(Long participantId, Date firstDay, Date lastDay);

//	@Query("SELECT b" +
//			"  FROM " +
//			"    VProjectExpense b " +
//			"  WHERE " +
//			"    b.participantIdMadePurchase = (SELECT ParticipantId FROM Individual i WHERE i.individualId = ?1)" +
//			"  AND " +
//			"    b.purchaseDate BETWEEN ?2 AND ?3")
//	List<VProjectExpense> findByIndividualIdMadePurchase(Long individualId, Date firstDay, Date lastDay);


	@Query("SELECT b " +
			"  FROM " +
			"    VProjectExpense b " +
			"  WHERE " +
			"    b.participantIdMadePurchase = (SELECT i.participantId FROM Individual i WHERE i.individualId = ?1) " +
			"  AND " +
			"    b.projectParticipantIdPayer in (SELECT pp.projectParticipantId FROM ProjectParticipant pp WHERE pp.projectId = ?2) " +
			"  AND " +
			"    b.purchaseDate BETWEEN ?3 AND ?4")
	List<VProjectExpense> findByIndividualIdMadePurchasebyProject(Long individualId, Long projectId, Date firstDay, Date lastDay);


	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.participantIdVendor = ?1" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?2 AND ?3")
	List<VProjectExpense> findByParticipantIdVendor(Long participantId, Date firstDay, Date lastDay);

	// Expenses
	@Query("SELECT b" +
			"  FROM " +
			"    VProjectExpense b" +
			"  WHERE " +
			"    b.participantIdPayer = ?3" +
			"  AND " +
			"    b.allowanceFlag != 'Y'" +
			"  AND " +
			"    b.purchaseDate BETWEEN ?1 AND ?2")
	List<VProjectExpense> findByParticipantAllExpensesNonAllowance(Date firstDay, Date lastDay, Long participantIdPayer);





		// Allowances

		@Query("SELECT b" +
				"  FROM " +
				"    VProjectExpense b" +
				"  WHERE " +
				"    b.participantIdPayer = ?3" +
				"  AND " +
				"    b.allowanceFlag = 'Y'" +
				"  AND " +
				"    b.purchaseDate BETWEEN ?1 AND ?2")
		List<VProjectExpense> findByParticipantAllAllowances(Date firstDay, Date lastDay, Long participantIdPayer);





		// Expense and Allowances use this
		@Query("SELECT b" +
				"  FROM " +
				"    VProjectExpense b" +
				"  WHERE " +
				"    b.participantIdPayer = ?5" +
				"  AND " +
				"    b.projectId = ?1" +
				"  AND " +
				"    b.expenseTypeId = ?4" +
				"  AND " +
				"    b.purchaseDate BETWEEN ?2 AND ?3")
		List<VProjectExpense> findByProjectIdParticipantIdExpenseType(Long projectId, Date firstDay, Date lastDay, Long expenseTypeId, Long participantIdPayer);


		// NOTE: change the package path to the package where you saved ProjectExpenseSummaryDto
		@Query("SELECT new net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto( "
				+ " b.projectId, "
				+ " b.projectName, "
				+ " b.subProjNumber, "
				+ " b.expenseTypeId, "
				+ " b.expenseTypeParentId, "
				+ " b.expenseTypeParentName, "
				+ " b.expenseTypeName, "
				+ " SUM(b.numberOfUnits) as theSum) "  // * b.amountPerUnit//
				+ "  FROM "
				+ "    VProjectExpense b"
				+ "  WHERE "
				+ "    b.participantIdPayer = ?1"
				+ "  AND "
				+ "    b.allowanceFlag = 'Y' "
				+ "  AND "
				+ "    b.purchaseDate BETWEEN ?2 AND ?3 "
				+ "  GROUP BY "
				+ "    b.projectId, "
				+ "    b.projectName, "
				+ "    b.subProjNumber, "
				+ "    b.expenseTypeId, "
				+ "    b.expenseTypeParentId, "
				+ "    b.expenseTypeParentName, "
				+ "    b.expenseTypeName " )
		List<ProjectExpenseSummaryDto> findByParticipantIdAllowanceSummary(Long participantId, Date firstDay, Date lastDay);

		// NOTE: change the package path to the package where you saved ProjectExpenseSummaryDto
		@Query("SELECT new net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto( "
				+ " b.projectId, "
				+ " b.projectName, "
				+ " b.subProjNumber, "
				+ " b.expenseTypeId, "
				+ " b.expenseTypeParentId, "
				+ " b.expenseTypeParentName, "
				+ " b.expenseTypeName, "
				+ " SUM(b.numberOfUnits * b.amountPerUnit) as theSum) "
				+ "  FROM "
				+ "    VProjectExpense b"
				+ "  WHERE "
				+ "    b.participantIdPayer = ?1"
				+ "  AND "
				+ "    b.allowanceFlag != 'Y' "
				+ "  AND "
				+ "    b.purchaseDate BETWEEN ?2 AND ?3 "
				+ "  GROUP BY "
				+ "    b.projectId, "
				+ "    b.projectName, "
				+ "    b.subProjNumber, "
				+ "    b.expenseTypeId, "
				+ "    b.expenseTypeParentId, "
				+ "    b.expenseTypeParentName, "
				+ "    b.expenseTypeName " )
		List<ProjectExpenseSummaryDto> findByParticipantIdExpenseSummary(Long participantId, Date firstDay, Date lastDay);

		@Query("SELECT " +
		       "    v" +
			   "  FROM" +
		       "    VProjectBankExpenses v" +
			   "  WHERE" +
		       "    v.userName = ?1" +
		       "    AND v.cardNumber = ?2" +
		       "  ORDER BY" +
		       "     purchaseDate DESC")
		List<VProjectBankExpenses> getProjectExpensesByUsernameAndCardNumber(String username, String cardNumber);
	
}