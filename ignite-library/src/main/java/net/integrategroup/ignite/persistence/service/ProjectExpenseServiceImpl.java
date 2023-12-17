package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.ProjectExpense;
import net.integrategroup.ignite.persistence.model.ProjectExpenseSummaryDto;
import net.integrategroup.ignite.persistence.model.VProjectBankExpenses;
import net.integrategroup.ignite.persistence.model.VProjectExpense;
import net.integrategroup.ignite.persistence.model.VProjectExpenseMin;
import net.integrategroup.ignite.persistence.repository.ProjectExpenseRepository;

@Service
public class ProjectExpenseServiceImpl implements ProjectExpenseService {

	@Autowired
	DatabaseService databaseService;
	
	@Autowired
	ProjectExpenseRepository projectExpenseRepository;

	@Override
	public List<VProjectExpense> getProjectExpense() {
		return projectExpenseRepository.getProjectExpense();
	}

	@Override
	public ProjectExpense findByProjectExpenseId(Long projectExpenseId) {
		return projectExpenseRepository.findByProjectExpenseId(projectExpenseId);
	}


	@Override
	public ProjectExpense save(ProjectExpense projectExpense) {
		return projectExpenseRepository.save(projectExpense);
	}


	@Override
	public List<ProjectExpense> findProjectExpenseByProject(Long projectId) {
		return projectExpenseRepository.findProjectExpenseByProject(projectId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByProjectParticipant(Long projectParticipantId) {
		return projectExpenseRepository.findProjectExpenseByProjectParticipant(projectParticipantId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByParticipantMadePurchase(Long participantId) {
		return projectExpenseRepository.findProjectExpenseByParticipantMadePurchase(participantId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByParticipantVendor(Long participantId) {
		return projectExpenseRepository.findProjectExpenseByParticipantVendor(participantId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByExpenseType(Long expenceTypeId) {
		return projectExpenseRepository.findProjectExpenseByExpenseType(expenceTypeId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByAsset(Long assetId) {
		return projectExpenseRepository.findProjectExpenseByAsset(assetId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByPaymentMethod(String paymentMethodCode) {
		return projectExpenseRepository.findProjectExpenseByPaymentMethod(paymentMethodCode);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByBankCard(Long bankCardId) {
		return projectExpenseRepository.findProjectExpenseByBankCard(bankCardId);
	}

	@Override
	public List<ProjectExpense> findProjectExpenseByTaxDeductableCategory(Long taxDeductableCategoryId) {
		return projectExpenseRepository.findProjectExpenseByTaxDeductableCategory(taxDeductableCategoryId);
	}

	@Override
	public List<VProjectExpense> findAllAssetExpenses(Long participantIdPayer) {
		return projectExpenseRepository.findAllAssetExpenses(participantIdPayer);
	}

	
	@Override
	public List<VProjectExpenseMin> findAllNonAssetExpensesPerParticipant(Long participantIdPayer) {
		return projectExpenseRepository.findAllNonAssetExpensesPerParticipant(participantIdPayer);
	}
	
		
	@Override
	public VProjectExpense findViewByProjectExpenseId(Long projectExpenseId) {
		return projectExpenseRepository.findViewByProjectExpenseId(projectExpenseId);
	}

	@Override
	public List<VProjectExpense> findByProjectParticipantIdPayer(Long projectParticipantId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByProjectParticipantIdPayer(projectParticipantId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByProjectId(Long projectId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByProjectId(projectId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByProjectIdAll(Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByProjectIdAll(firstDay, lastDay);
	}


	@Override
	public List<VProjectExpense> findByProjectIdExpenseType(Long projectId, Date firstDay, Date lastDay, Long expenseTypeId) {
		return projectExpenseRepository.findByProjectIdExpenseType(projectId, firstDay, lastDay, expenseTypeId);
	}


	@Override
	public List<VProjectExpense> findByParticipantIdPayer(Long participantId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByParticipantIdPayer(participantId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByParticipantIdPayerExpenseType(Long participantId, Date firstDay, Date lastDay, Long expenseTypeId) {
		return projectExpenseRepository.findByParticipantIdPayerExpenseType(participantId, firstDay, lastDay, expenseTypeId);
	}

	@Override
	public List<ProjectExpenseSummaryDto> findByProjectIdSummary(Long projectId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByProjectIdSummary(projectId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByParticipantIdMadePurchase(Long participantId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByParticipantIdMadePurchase(participantId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByIndividualIdMadePurchasebyProject(Long participantId, Long projectId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByIndividualIdMadePurchasebyProject(participantId, projectId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByParticipantIdVendor(Long participantId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByParticipantIdVendor(participantId, firstDay, lastDay);
	}

	// Expenses
	@Override
	public List<ProjectExpenseSummaryDto> findByParticipantIdExpenseSummary(Long participantId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByParticipantIdExpenseSummary(participantId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByParticipantAllExpensesNonAllowance(Date firstDay, Date lastDay, Long participantIdPayer) {
		return projectExpenseRepository.findByParticipantAllExpensesNonAllowance(firstDay, lastDay, participantIdPayer);
	}

	//Allowances

	@Override
	public List<ProjectExpenseSummaryDto> findByParticipantIdAllowanceSummary(Long participantId, Date firstDay, Date lastDay) {
		return projectExpenseRepository.findByParticipantIdAllowanceSummary(participantId, firstDay, lastDay);
	}

	@Override
	public List<VProjectExpense> findByParticipantAllAllowances(Date firstDay, Date lastDay, Long participantIdPayer) {
		return projectExpenseRepository.findByParticipantAllAllowances(firstDay, lastDay, participantIdPayer);
	}

	// Expense and Allowances use this
	@Override
	public List<VProjectExpense> findByProjectIdParticipantIdExpenseType(Long projectId, Date firstDay, Date lastDay, Long expenseTypeId, Long participantIdPayer) {
		return projectExpenseRepository.findByProjectIdParticipantIdExpenseType(projectId, firstDay, lastDay, expenseTypeId, participantIdPayer);
	}

	@Override
	public List<VProjectBankExpenses> getProjectExpensesByUsernameAndCardNumber(String username, String cardNumber) {
		return projectExpenseRepository.getProjectExpensesByUsernameAndCardNumber(username, cardNumber);
	}

	@Override
	public Long createProjectExpense(String cardNumber, String projectName, Date purchaseDate, String description,
			                            Double amountPerUnit, Double numberOfUnits, String noteToAccountant, 
			                            String expenseType, String paymentMethodCode, String username) { 
		Connection conn = null;
		CallableStatement cstm = null;
		Long projectExpenseId = null;

		try {
			java.sql.Date sqlDate = null;
			
			if (purchaseDate != null) {
				sqlDate = new java.sql.Date(purchaseDate.getTime());
			}

			String sql = "CALL ig_db.saveProjectExpense(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			try {
				conn = databaseService.getConnection();
				cstm = conn.prepareCall(sql);

				cstm.setString(1, cardNumber);
				cstm.setString(2, projectName);
				cstm.setDate(3, sqlDate);
				cstm.setString(4, description);
				cstm.setDouble(5, amountPerUnit);
				cstm.setDouble(6, numberOfUnits);
				cstm.setString(7, noteToAccountant);
				cstm.setString(8, expenseType);
				cstm.setString(9, paymentMethodCode);
				cstm.setString(10, username);
				cstm.registerOutParameter(11, java.sql.Types.BIGINT);  // The new projectExpenseId
				cstm.execute();
				
				projectExpenseId = cstm.getLong(11);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (cstm != null) {
					cstm.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return projectExpenseId;
	}

}
