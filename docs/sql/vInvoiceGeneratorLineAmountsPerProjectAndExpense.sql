CREATE OR REPLACE
VIEW vInvoiceGeneratorLineAmountsPerProjectAndExpense AS
select 
	'Time Related Cost' as ExpenseType,
    tc.ProjectId,
    tc.ProjectName,
    (tc.ProjectId * 2 * tc.RowNumber) as RowNumber,
	tc.AgreementParticipantIdPayer  as ParticipantIdContracting,
	tc.AgreementPayer as ParticipantContracting,
	tc.AgreementParticipantIdBeneficiary as ParticipantIdContracted,
	tc.AgreementBeneficiary as ParticipantContracted,
	tc.ActivityDate as ActivityDate, 
	tc.SumNrOfUnits as SumNrOfUnits,
	tc.LineAmount as LineAmount, 
	tc.RatesMissing as RatesMissing,
    tc.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId
from 
	ig_db.vParticipantTimeCostTotalsPerProject tc
UNION all
select 
	'Expense(s) Incurred' as ExpenseType,
    ex.ProjectId,
    ex.ProjectName,
    (ex.ProjectId * 3 * ex.RowNumber * (FLOOR(1 + (RAND() * 1000)))) as RowNumber,
	ex.ParticipantIdContracting  as ParticipantIdContracting,
	ex.ParticipantInAgreementContracting as ParticipantContracting,
	ex.ParticipantIdContracted as ParticipantIdContracted,
	ex.ParticipantInAgreementContracted as ParticipantContracted,
	ex.PurchaseDate as ActivityDate, 
	ex.SumNrOfUnits as SumNrOfUnits,
	ex.LineAmount as LineAmount, 
	ex.RatesMissing as RatesMissing,
    ex.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId
from 
	ig_db.vParticipantExpenseCostTotalsPerProject ex
UNION all
 select 
 	'Draft Invoice(s)' as ExpenseType,
    il.ProjectId,
    il.ProjectNameText as ProjectName,
    (inv.InvoiceId * il.ProjectId * inv.ParticipantIdTo ) as RowNumber,
 	inv.ParticipantIdTo  as ParticipantIdContracting,
 	pt.SystemName as ParticipantContracting,
 	inv.ParticipantIdFrom as ParticipantIdContracted,
 	pf.SystemName as ParticipantContracted,
 	-- inv.InvoiceDate as ActivityDate, 
 	inv.UpUntilGenerateDate as ActivityDate, 
 	1 as SumNrOfUnits,
 	(il.LineAmount * -1) as LineAmount, 
 	0 as RatesMissing,
    il.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId
 from 
 	ig_db.Invoice inv
    join ig_db.vInvoiceLine il on inv.InvoiceId = il.InvoiceId
	join Participant pt on inv.ParticipantIdTo = pt.ParticipantId
    join Participant pf on inv.ParticipantIdFrom = pf.ParticipantId
where inv.DateSentOrReceived is null
UNION all
 select 
 	'Previous Invoice(s) Sent' as ExpenseType,
    il.ProjectId,
    il.ProjectNameText as ProjectName,
    (inv.InvoiceId * il.ProjectId * inv.ParticipantIdTo ) as RowNumber,
 	inv.ParticipantIdTo  as ParticipantIdContracting,
   	pt.SystemName as ParticipantContracting,
 	inv.ParticipantIdFrom as ParticipantIdContracted,
 	pf.SystemName as ParticipantContracting,
 	-- inv.InvoiceDate as ActivityDate, 
 	inv.UpUntilGenerateDate as ActivityDate, 
 	1 as SumNrOfUnits,
 	(il.LineAmount * -1) as LineAmount, 
 	0 as RatesMissing,
    il.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId
 from 
 	ig_db.Invoice inv
   	join Participant pt on inv.ParticipantIdTo = pt.ParticipantId
    join Participant pf on inv.ParticipantIdFrom = pf.ParticipantId
    join ig_db.vInvoiceLine il on inv.InvoiceId = il.InvoiceId
where inv.DateSentOrReceived is not null
UNION all
 select 
 	'Invoice(s) Received' as ExpenseType,  -- From and To has been swapped to see invoices received as well
    il.ProjectId,
    il.ProjectNameText as ProjectName,
    (inv.InvoiceId * il.ProjectId * inv.ParticipantIdTo ) as RowNumber,
 	inv.ParticipantIdFrom as ParticipantIdContracting,
 	-- inv.FromParticipantName as ParticipantContracting,
    pf.SystemName as ParticipantContracting,
 	inv.ParticipantIdTo  as ParticipantIdContracted,
    pt.SystemName as ParticipantContracted,
 	-- inv.ToParticipantName as ParticipantContracted,
 	-- inv.InvoiceDate as ActivityDate, 
 	inv.UpUntilGenerateDate as ActivityDate, 
 	1 as SumNrOfUnits,
 	(il.LineAmount * -1) as LineAmount, 
 	0 as RatesMissing,
    il.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId
 from 
 	ig_db.Invoice inv
    join ig_db.vInvoiceLine il on inv.InvoiceId = il.InvoiceId
   	join Participant pt on inv.ParticipantIdTo = pt.ParticipantId
    join Participant pf on inv.ParticipantIdFrom = pf.ParticipantId

where inv.DateSentOrReceived is not null;

