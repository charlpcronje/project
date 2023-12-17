package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {


	@Query("SELECT iv FROM Invoice iv WHERE iv.participantIdTo  = ?1 AND (iv.invoiceDate BETWEEN ?2 AND ?3)")
	List<Invoice> findInvoicesInForParticipant(Long participantId, Date firstDay, Date lastDay);

	@Query("SELECT i FROM Invoice i WHERE i.invoiceId = ?1")
	Invoice findByInvoiceId(Long invoiceId);

	@Query("SELECT vi FROM Invoice vi WHERE vi.invoiceId = ?1")
	Invoice findViewByInvoiceId(Long invoiceId);

	@Query("   SELECT iv "
			+ " FROM Invoice iv "
			+ " WHERE iv.participantIdFrom  = ?1 "
			+ " AND (iv.invoiceDate BETWEEN ?2 AND ?3)"
			+ " AND flagDraft <> 'Y'")
	List<Invoice> findInvoicesOutForParticipant(Long participantId, Date firstDay, Date lastDay);


//	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsDto( "
//			+ "	v.agreementParticipantIdPayer, "
//			+ "	v.agreementPayer,"
//			+ "	v.agreementParticipantIdBeneficiary, "
//			+ "	v.agreementBeneficiary,"
//			+ "	SUM(v.sumNrOfUnits), "
//			+ "	SUM(v.lineAmount), "
//			+ "	SUM(v.ratesMissing)) "
//			+ " FROM VParticipantTimeCostTotals v "
//			+ " WHERE v.agreementParticipantIdBeneficiary = ?1"
//			+ " GROUP BY "
//			+ "	v.agreementParticipantIdPayer, "
//			+ "	v.agreementPayer,"
//			+ "	v.agreementParticipantIdBeneficiary, "
//			+ "	v.agreementBeneficiary ")
//
//	List<ParticipantTimeCostTotalsDto> findInvoicesAvailableRelationshipsTimeCost(Long participantIdBeneficiary);
//
//	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsDto( "
//			+ "	v.participantIdContracting, "
//			+ "	v.participantInAgreementContracting,"
//			+ "	v.participantIdContracted, "
//			+ "	v.participantInAgreementContracted,"
//			+ "	SUM(v.sumNrOfUnits), "
//			+ "	SUM(v.lineAmount), "
//			+ "	SUM(v.ratesMissing)) "
//			+ " FROM VParticipantExpenseCostTotals v "
//			+ " WHERE v.participantIdContracted = ?1"
//			+ " GROUP BY "
//			+ "	v.participantIdContracting, "
//			+ "	v.participantInAgreementContracting,"
//			+ "	v.participantIdContracted, "
//			+ "	v.participantInAgreementContracted")
//	List<ParticipantExpenseCostTotalsDto> findInvoicesAvailableRelationshipsExpense(Long participantIdContracted);

//	// Original:
//	// Available invoices Line amounts to generate for dateRange Table 2 Per Project
//	@Query("   SELECT ivp "
//			+ " FROM VInvoiceGenLineAmountsPerProjectTotals ivp "
//			+ " WHERE ivp.participantIdContracted = ?1 "
//			+ " AND ivp.participantIdContracting = ?2 "
//			+ " AND ivp.activityDate <= ?3"
//			+ " AND ivp.sumLineAmount > 0 ")
//
//	List<VInvoiceGenLineAmountsPerProjectTotals> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);


//	// Try to fix for activity dates later than lastDay sent in
//	// Available invoices Line amounts to generate for dateRange Table 2 Per Project
//
//	@Query(
//			" SELECT "
//			+ " ivp.projectId, "
//			+ " ivp.projectName, "
//			+ " ivp.participantIdContracting, "
//			+ " ivp.participantContracting, "
//			+ " ivp.participantIdContracted, "
//			+ " ivp.participantContracted, "
//			+ " ivp.sumNrOfUnits, "
//			+ " ivp.sumLineAmount, "
//			+ " ivp.sumRatesMissing,"
//			+ " ivp.activityDate "
//			+ "  FROM  "
//			+ " 	( "
//			+ " 		SELECT "
//			+ " 			v.projectId as projectId, "
//			+ " 			v.projectName as projectName, "
//			+ " 			v.participantIdContracting as participantIdContracting, "
//			+ " 			v.participantContracting as participantContracting, "
//			+ " 			v.participantIdContracted as participantIdContracted, "
//			+ " 			v.participantContracted as participantContracted, "
//			+ " 			SUM(v.sumNrOfUnits) as sumNrOfUnits, "
//			+ " 			SUM(v.lineAmount) as sumLineAmount, "
//			+ " 			SUM(v.ratesMissing) as sumRatesMissing,"
//			+ " 			MAX(v.activityDate) as activityDate "
//			+ " 		FROM VInvoiceGeneratorLineAmountsPerProjectAndExpense v "
//			+ " 		WHERE v.participantIdContracted = ?1 "
//			+ " 			AND v.participantIdContracting = ?2 "
//			+ " 			AND v.activityDate <= ?3"
//			+ " 		GROUP BY "
//			+ " 			v.projectId,"
//			+ " 			v.projectName,"
//			+ " 			v.participantIdContracting,"
//			+ " 			v.participantContracting, "
//			+ " 			v.participantIdContracted,"
//			+ " 			v.participantContracted "
//			+ " 	)  AS ivp "
//			+ " 			WHERE ivp.sumLineAmount > 0 ")
////			+ " 			WHERE ivp.participantIdContracted = ?1 "
////			+ " 			AND ivp.participantIdContracting = ?2 "
////			+ " 			AND ivp.activityDate <= ?3"
////			+ " 			AND ivp.sumLineAmount > 0 ")
//
//	List<VInvoiceGenLineAmountsPerProjectTotals> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);

//	SELECT COUNT(cc.customer) AS tot
//	  FROM
//	    SELECT DISTINCT new RatingsAndReviews(c.customer,c.customerRating)
//	    FROM RatingsAndReviews AS c
//	    WHERE c.vehicle='some test value here' AS cc
//	  WHERE cc.customerRating=<some test value here>


	// Try to fix for activity dates later than lastDay sent in
	// Available invoices Line amounts to generate for dateRange Table 2 Per Project




//	select pc
//	from PushCampaign pc
//	where pc.creationDate =
//	(select max(creationDate) from PushCampaign inner where inner.bde = pc.bde)



//

//and this will give the following error
//
//
//		@Query(
//				"  WITH ivp AS  "
//				+ " 	( "
//				+ " 		SELECT "
//				+ " 			v.projectId as projectId, "
//				+ " 			v.projectName as projectName, "
//				+ " 			v.participantIdContracting as participantIdContracting, "
//				+ " 			v.participantContracting as participantContracting, "
//				+ " 			v.participantIdContracted as participantIdContracted, "
//				+ " 			v.participantContracted as participantContracted, "
//				+ " 			SUM(v.sumNrOfUnits) as sumNrOfUnits, "
//				+ " 			SUM(v.lineAmount) as sumLineAmount, "
//				+ " 			SUM(v.ratesMissing) as sumRatesMissing,"
//				+ " 			MAX(v.activityDate) as activityDate "
//				+ " 		FROM VInvoiceGeneratorLineAmountsPerProjectAndExpense v "
//				+ " 		WHERE v.participantIdContracted = ?1 "
//				+ " 			AND v.participantIdContracting = ?2 "
//				+ " 			AND v.activityDate <= ?3"
//				+ " 		GROUP BY "
//				+ " 			v.projectId,"
//				+ " 			v.projectName,"
//				+ " 			v.participantIdContracting,"
//				+ " 			v.participantContracting, "
//				+ " 			v.participantIdContracted,"
//				+ " 			v.participantContracted "
//				+ " 	)  "
//				+ " SELECT "
//				+ " 	ivp.projectId, "
//				+ " 	ivp.projectName, "
//				+ " 	ivp.participantIdContracting, "
//				+ " 	ivp.participantContracting, "
//				+ " 	ivp.participantIdContracted, "
//				+ " 	ivp.participantContracted, "
//				+ " 	ivp.sumNrOfUnits, "
//				+ " 	ivp.sumLineAmount, "
//				+ " 	ivp.sumRatesMissing,"
//				+ " 	ivp.activityDate "
//				+ " FROM ivp "
//				+ " WHERE ivp.sumLineAmount > 0 ")
//
//		List<VInvoiceGenLineAmountsPerProjectTotals> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);








	// Available invoices Line amounts to generate for dateRange Table 3 Per Project
	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerProjectDto( "
			+ "	v.projectId,"
			+ "	v.projectName,"
			+ "	v.participantIdContracting,"
			+ "	v.participantContracting, "
			+ "	v.participantIdContracted,"
			+ "	v.participantContracted,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount), "
			+ "	SUM(v.ratesMissing)) "
			+ " FROM VInvoiceGeneratorLineAmountsPerProjectAndExpense v "
			+ " WHERE v.participantIdContracted = ?1 "
			+ " AND v.participantIdContracting = ?2 "
			+ " AND v.activityDate <= ?3"
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.projectName,"
			// + "	v.expenseType,"
			+ "	v.participantIdContracting,"
			+ "	v.participantContracting, "
			+ "	v.participantIdContracted,"
			+ "	v.participantContracted")

	List<InvoiceOutLineTotalsPerProjectDto> findInvoicesOutPerProjectExpenseTypeTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);

	// Table 5: Expense Claims
	@Query("SELECT v "
			+ " FROM VPpExpenseRateUplineRecursive v"
			+ " WHERE  	v.expenseTypeId = ?1 "
			+ " AND 	v.participantIdContracting = ?2 "
			+ " AND 	v.participantIdContracted = ?3 "
			+ " AND 	v.projectId = ?4 "
			+ " ORDER BY 	v.purchaseDate")

	List<VPpExpenseRateUplineRecursive> findContractingContractedExpenseCost(Long expenseTypeId, Long participantIdContracting, Long participantIdContracted, Long projectId);

	@Query("SELECT iv FROM Invoice iv "
			+ "WHERE iv.participantIdFrom  = ?1 "
			+ "OR  iv.participantIdTo  = ?1 "
			+ "AND (iv.invoiceDate BETWEEN ?2 AND ?3)")
	List<Invoice> findInvoicesInAndOutForParticipant(Long participantId, Date firstDay, Date lastDay);

//	@Query("SELECT vil FROM InvoiceLine vil WHERE vil.invoiceId  = ?1")
//	List<InvoiceLine> findInvoiceLines(Long invoiceId);

	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceLineDto( "
			+ "	vil.invoiceId,"
			+ "	vil.participantIdFrom,"
			+ "	vil.participantIdTo,"
			+ "	vil.invoiceNumber,"
			+ "	vil.invoiceAmount,"
			+ "	vil.invoiceDate,"
			+ "	vil.projectId,"
			+ "	vil.projectNameText,"
			+ "	vil.lineType,"
			+ "	SUM(vil.totalUnits),"
			+ "	SUM(vil.lineAmount))"
			+ " FROM VInvoiceLine vil"
			+ " WHERE vil.invoiceId = ?1"
			+ " GROUP BY "
			+ "	vil.projectNameText,"
			+ "	vil.lineType,"
			+ "	vil.invoiceId,"
			+ "	vil.projectId,"
			+ "	vil.participantIdFrom,"
			+ "	vil.participantIdTo,"
			+ "	vil.invoiceNumber,"
			+ "	vil.invoiceAmount,"
			+ "	vil.invoiceDate")
	List<InvoiceLineDto> findInvoiceLines(Long invoiceId);


//	// Invoice generator
//	@Query("SELECT new net.integrategroup.ignite.persistence.model.GeneratableInvoiceTotalsNonNullValuesDto( "
//			+ "	v.participantIdContracting,"
//			+ "	v.participantContracting,"
//			+ "	v.participantIdContracted,"
//			+ "	v.participantContracted,"
//			+ "	SUM(v.lineAmount))"
//			+ " FROM VGeneratableInvoiceTotalsNonNullValues v "
//			+ " WHERE  v.participantIdContracted = ?1 "
//			+ " AND v.activityDate <= ?2"
//			// + " AND SUM(v.lineAmount) > 0"
//			+ " GROUP BY "
//			+ "	v.participantIdContracting,"
//			+ "	v.participantContracting,"
//			+ "	v.participantIdContracted,"
//			+ "	v.participantContracted")
//	List<GeneratableInvoiceTotalsNonNullValuesDto> findInvoicesOutAvailableLineTotalsNotNull(Long participantIdContracting, Date lastDay);

//	// Invoice generator
//	@Query("SELECT new net.integrategroup.ignite.persistence.model.GeneratableInvoiceTotalsNonNullValuesDto( "
//			+ "	a.participantIdContracting,"
//			+ "	a.participantContracting,"
//			+ "	a.participantIdContracted,"
//			+ "	a.participantContracted,"
//			+ "	a.SUM_lineAmount)"
//			+ " FROM "
//			+ "      (SELECT "
//			+ "	       v.participantIdContracting,"
//			+ "	       v.participantContracting,"
//			+ "	       v.participantIdContracted,"
//			+ "	       v.participantContracted,"
//			+ "	       SUM(v.lineAmount) as SUM_lineAmount"
//			+ "      FROM VGeneratableInvoiceTotalsNonNullValues v "
//			+ "      WHERE  v.participantIdContracted = ?1 "
//			+ "        AND v.activityDate <= ?2"
//			+ "      GROUP BY "
//			+ "	       v.participantIdContracting,"
//			+ "	       v.participantContracting,"
//			+ "	       v.participantIdContracted,"
//			+ "	       v.participantContracted) a"
//			+ " WHERE a.SUM_lineAmount > 0")
//	List<GeneratableInvoiceTotalsNonNullValuesDto> findInvoicesOutAvailableLineTotalsNotNull(Long participantIdContracting, Date lastDay);



	@Query	("SELECT ild "
			+ " FROM InvoiceLineDetail ild "
			+ " JOIN InvoiceLine il ON (il.invoiceLineId = ild.invoiceLineId) "
			+ " WHERE il.invoiceId  = ?1"
			+ " AND il.projectId  = ?2"
			+ " AND ild.lineType  = ?3")
	List<InvoiceLineDetail> findDraftInvoiceDetailsForInvoiceLine(Long invoiceId, Long projectId, String lineType);






	//--------------------------------------------------------------------------------------------//
	// Invoice Generator
	// Table 1
	// Available invoices to generate for dateRange Table 1 (Rates missing also shown)
	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsDto( "
			+ "	v.participantIdContracting,"
			+ "	v.participantContracting, "
			+ "	v.participantIdContracted,"
			+ "	v.participantContracted,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount), "
			+ "	SUM(v.ratesMissing)) "
			+ " FROM VInvoiceGeneratorLineAmountsPerProjectAndExpense v "
			+ " WHERE v.participantIdContracted = ?1"
			+ " AND v.activityDate <= ?2"
			+ " GROUP BY "
			+ "	v.participantIdContracting,"
			 + "	v.participantContracting, "
			+ "	v.participantIdContracted,"
			 + "	v.participantContracted")

	List<InvoiceOutLineTotalsDto> findInvoicesOutAvailableLineTotals(Long participantIdContracted, Date lastDay);

	// Table 2
	// Available invoices to generate for dateRange per Project
	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerProjectDto( "
			+ " 			v.projectId, "
			+ " 			v.projectName, "
			+ " 			v.participantIdContracting, "
			+ " 			v.participantContracting, "
			+ " 			v.participantIdContracted, "
			+ " 			v.participantContracted, "
			+ " 			SUM(v.sumNrOfUnits), "
			+ " 			SUM(v.lineAmount), "
			+ " 			SUM(v.ratesMissing))"
			+ " 		FROM VInvoiceGeneratorLineAmountsPerProjectAndExpense v "
			+ " 		WHERE v.participantIdContracted = ?1 "
			+ " 			AND v.participantIdContracting = ?2 "
			+ " 			AND v.activityDate <= ?3"
			+ " 			AND (("
			+ "						(	SELECT 	SUM(vv.lineAmount) "
			+ "							FROM 	VInvoiceGeneratorLineAmountsPerProjectAndExpense vv "
			+ " 						WHERE 	vv.projectId = v.projectId "
			+ "							AND vv.participantIdContracting = v.participantIdContracting"
			+ " 						AND vv.participantIdContracted = v.participantIdContracted "
			+ " 						AND vv.activityDate <= ?3"
			+ "						) > 0) "
			+ " 				OR ("
			+ "						(	SELECT 	SUM(vv1.ratesMissing) "
			+ "							FROM 	VInvoiceGeneratorLineAmountsPerProjectAndExpense vv1 "
			+ " 						WHERE 	vv1.projectId = v.projectId "
			+ "							AND vv1.participantIdContracting = v.participantIdContracting"
			+ " 						AND vv1.participantIdContracted = v.participantIdContracted "
			+ " 						AND vv1.activityDate <= ?3"
			+ "						) > 0)"
			+ "				) "
			+ " 		GROUP BY "
			+ " 			v.projectId,"
			+ " 			v.projectName,"
			+ " 			v.participantIdContracting,"
			+ " 			v.participantContracting, "
			+ " 			v.participantIdContracted,"
			+ " 			v.participantContracted ")

	List<InvoiceOutLineTotalsPerProjectDto> findInvoicesOutPerProjectTotals(Long participantIdContracted, Long participantIdContracting, Date lastDay);

	// Table 3
	// Available invoices lines to generate for dateRange per Project per Expense Type
	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerExpenseDto( "
			+ "	v.projectId,"
			+ "	v.expenseType,"
			+ "	v.participantIdContracting,"
			+ "	v.participantContracting, "
			+ "	v.participantIdContracted,"
			+ "	v.participantContracted,"
			+ "	SUM(v.sumNrOfUnits) as sumNrOfUnits, "
			+ "	SUM(v.lineAmount) as lineAmount, "
			+ "	SUM(v.ratesMissing) as ratesMissing) "
			// + " FROM VInvoiceOutLineAmounts v "
			+ " FROM VInvoiceGeneratorLineAmountsPerProjectAndExpense v "
			+ " WHERE v.participantIdContracted = ?1 "
			+ " AND v.participantIdContracting = ?2 "
			+ " AND v.activityDate <= ?3"
			+ " AND v.projectId = ?4"
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.expenseType,"
			+ "	v.participantIdContracting,"
			+ "	v.participantContracting, "
			+ "	v.participantIdContracted,"
			+ "	v.participantContracted")

	List<InvoiceOutLineTotalsPerExpenseDto> findInvoicesOutPerProjectPerExpenseTotals(Long participantIdContracted, Long participantIdContracting,
																						Date lastDay, Long projectId);

	// Time Related Costs
	// Line Totals Table Table 4a
	// Time Related -  Table 4a: Summary per Project - Time Cost
	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto( "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.sdName,"
			+ "	v.unitTypeName,"
			+ "	v.agreementBetweenParticipantsId,"
			+ "	v.agreementBetween,"
			+ "	v.agreementParticipantIdPayer,"
			+ "	v.agreementPayer,"
			+ "	v.agreementParticipantIdBeneficiary,"
			+ "	v.agreementBeneficiary,"
			+ "	v.projectSdId,"
			+ "	v.remunerationTypeName,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount),"
			+ "	SUM(v.ratesMissing))"
			+ " FROM VParticipantTimeCostTotalsPerProject v "
			+ " WHERE  v.agreementParticipantIdPayer = ?1 "
			+ " AND v.agreementParticipantIdBeneficiary = ?2"
			+ " AND v.activityDate <= ?3"
			+ " AND v.projectId = ?4"
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.sdName,"
			+ "	v.unitTypeName,"
			+ "	v.agreementBetweenParticipantsId,"
			+ "	v.agreementBetween,"
			+ "	v.agreementParticipantIdPayer,"
			+ "	v.agreementPayer,"
			+ "	v.agreementParticipantIdBeneficiary,"
			+ "	v.agreementBeneficiary,"
			+ "	v.projectSdId,"
			+ "	v.remunerationTypeName")

	List<ParticipantTimeCostTotalsPerProjectDto> findLineTotalsTimeCost(Long participantIdContracting, Long participantIdContracted, Date lastDay, Long projectId);

	// Expense Claim Costs
	// Line Totals Table Table 4b
	// Expense Claims -  Table 4b : Summary per Project - Expense Claims
	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto( "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.recoverableExpenseId,"
			+ "	v.expenseTypeId,"
			+ "	v.expenseTypeName,"
			+ "	v.unitTypeCode,"
			+ "	v.unitTypeName,"
			+ "	v.participantIdContracting,"
			+ "	v.participantInAgreementContracting,"
			+ "	v.participantIdContracted,"
			+ "	v.participantInAgreementContracted,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount),"
			+ "	SUM(v.ratesMissing))"
			+ " FROM VParticipantExpenseCostTotalsPerProject v "
			+ " WHERE  v.participantIdContracting = ?1 "
			+ " AND v.participantIdContracted = ?2"
			+ " AND v.purchaseDate <= ?3"
			+ " AND v.projectId = ?4"
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.recoverableExpenseId,"
			+ "	v.expenseTypeId,"
			+ "	v.expenseTypeName,"
			+ "	v.unitTypeCode,"
			+ "	v.unitTypeName,"
			+ "	v.participantIdContracting,"
			+ "	v.participantInAgreementContracting,"
			+ "	v.participantIdContracted,"
			+ "	v.participantInAgreementContracted")
		List<ParticipantExpenseCostTotalsPerProjectDto> findLineTotalsExpenseClaim(Long participantIdContracting, Long participantIdContracted,
				Date lastDay, Long projectId);

	//--------------------------------------------------------------------------------------------//
	// Draft Invoice
	// Table 1
	// Available draft Invoices Table 1 (Rates missing also shown)
	@Query("   SELECT iv "
			+ " FROM Invoice iv "
			+ " WHERE iv.participantIdFrom  = ?1 "
			+ " AND flagDraft = 'Y'")
	List<Invoice> findDraftInvoicesForParticipant(Long participantId);

	// Table 2
	// Available invoices to generate for dateRange per Project
	// Trying to re-use InvoiceOutLineTotalsPerProjectDto

	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerProjectDto( "
			+ " 	vil.projectId, "
			+ "		vil.projectNameText as projectName, "
			+ " 	vil.participantIdTo as participantIdContracting, "
			+ " 	vil.participantTo as participantContracting, "
			+ " 	vil.participantIdFrom as participantIdContracted, "
			+ " 	vil.participantFrom as participantContracted, "
			+ " 	SUM(ild.numberOfUnits) as sumNrOfUnits, "
			+ " 	SUM(ild.lineTotal) as lineAmount, "
			+ " 	SUM(ild.ratesMissing) as ratesMissing)"
			+ " 	FROM VInvoiceLine vil "
			+ " 	JOIN InvoiceLineDetail ild ON (ild.invoiceLineId = vil.invoiceLineId) "
			+ " 	WHERE vil.invoiceId = ?1 "
			+ " 	GROUP BY "
			+ " 	vil.projectId, "
			+ "		vil.projectNameText, "
			+ " 	vil.participantIdTo, "
			+ " 	vil.participantIdFrom")

	List<InvoiceOutLineTotalsPerProjectDto> getInvoiceLineTotalsPerProject(Long invoiceId);


	// Table 3
	// Available draft invoices lines to generate for dateRange per Project per Expense Type

	@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceOutLineTotalsPerExpenseDto( "
			+ "	v.projectId,"
			+ "	v.lineType as expenseType,"
			+ "	v.participantIdTo as participantIdContracting,"
			+ "	v.participantTo as participantContracting, "
			+ "	v.participantIdFrom as participantIdContracted,"
			+ "	v.participantFrom as participantContracted,"
			+ "	SUM(v.totalUnits) as sumNrOfUnits, "
			+ "	SUM(v.lineAmount) as lineAmount, "
			+ "	SUM(v.ratesMissing) as ratesMissing) "
			+ " FROM VInvoiceLine v "
			+ " WHERE v.invoiceId = ?1 "
			+ " AND v.projectId = ?2"
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.lineType,"
			+ "	v.participantIdTo,"
			+ "	v.participantTo, "
			+ "	v.participantIdFrom,"
			+ "	v.participantFrom")

		List<InvoiceOutLineTotalsPerExpenseDto> getInvoiceLineTotalsPerProjectAndExpense(Long invoiceId, Long projectId);

		// Time Related Costs
		// Line Totals Table Table 4a
		// Time Related -  Draft Table 4a: Summary per Project - Time Cost

		@Query("SELECT new net.integrategroup.ignite.persistence.model.InvoiceLineTimeCostTotalsPerProjectDto( "
				+ "	il.projectId,"
				+ "	ild.sdName,"
				+ "	ild.theType,"
				+ "	SUM(ild.numberOfUnits) as sumNumberOfUnits,"
				+ "	SUM(ild.lineTotal), "
				+ "	SUM(ild.ratesMissing))"
				+ " FROM InvoiceLine il"
				+ " JOIN InvoiceLineDetail ild ON (il.invoiceLineId = ild.invoiceLineId) "
				+ " WHERE  il.invoiceId = ?1 "
				+ " AND il.projectId = ?2"
				+ " AND ild.lineType = ?3"
				+ " GROUP BY "
				+ "	il.projectId,"
				+ "	ild.sdName,"
				+ "	ild.theType")

		List<InvoiceLineTimeCostTotalsPerProjectDto> findInvoiceLineTotalsTimeCostPerProject(Long invoiceId, Long projectId, String lineType);

		// Draft Table 5b : Expense Claim Details
		@Query	("SELECT ild "
				+ " FROM InvoiceLineDetail ild "
				+ " JOIN InvoiceLine il ON (il.invoiceLineId = ild.invoiceLineId) "
				+ " WHERE il.invoiceId  = ?1"
				+ " AND il.projectId  = ?2"
				+ " AND ild.theType  = ?3")
		List<InvoiceLineDetail> findDraftInvoiceDetailsForExpenseLine(Long invoiceId, Long projectId, String expenseType);


}

