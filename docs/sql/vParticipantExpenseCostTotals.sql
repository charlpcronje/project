SET collation_connection = 'utf8mb4_0900_ai_ci';
CREATE OR REPLACE
VIEW vParticipantExpenseCostTotals AS
select 
	ANY_VALUE(eru.RowNumber) as RowNumber,
    ANY_VALUE(eru.ParticipantIdContracting) as ParticipantIdContracting,
	ANY_VALUE(eru.ParticipantInAgreementContracting) as ParticipantInAgreementContracting,
    ANY_VALUE(eru.ParticipantIdContracted) as ParticipantIdContracted,
	ANY_VALUE(eru.ParticipantInAgreementContracted) as ParticipantInAgreementContracted,
    ANY_VALUE(eru.PurchaseDate) as PurchaseDate,
    ANY_VALUE(eru.ExpenseRateForDate) as ExpenseRateForDate,
	SUM(eru.NumberOfUnits) as SumNrOfUnits,
	sum(eru.NumberOfUnits * eru.ExpenseRateForDate) as LineAmount,
	0 AS RatesMissing
     
from 
	ig_db.vPpExpenseRateUplineRecursive eru
where 
    eru.ExpenseRateForDate >= 0
group by 
	 	eru.ParticipantIdContracting,
		eru.ParticipantIdContracted,
        eru.PurchaseDate
UNION all
select 
	ANY_VALUE(eru2.RowNumber) as RowNumber,
    ANY_VALUE(eru2.ParticipantIdContracting) as ParticipantIdContracting,
	ANY_VALUE(eru2.ParticipantInAgreementContracting) as ParticipantInAgreementContracting,
    ANY_VALUE(eru2.ParticipantIdContracted) as ParticipantIdContracted,
	ANY_VALUE(eru2.ParticipantInAgreementContracted) as ParticipantInAgreementContracted,
    ANY_VALUE(eru2.PurchaseDate) as PurchaseDate,

    ANY_VALUE(eru2.ExpenseRateForDate) as ExpenseRateForDate,
	SUM(eru2.NumberOfUnits) as SumNrOfUnits,
	null as LineAmount, -- "Remuneration rate(s) missing" as LineAmount
    1 as RatesMissing
     
from 
	ig_db.vPpExpenseRateUplineRecursive eru2
where 
    eru2.ExpenseRateForDate < 0
group by 
	 	eru2.ParticipantIdContracting,
		eru2.ParticipantIdContracted,
        eru2.PurchaseDate;


