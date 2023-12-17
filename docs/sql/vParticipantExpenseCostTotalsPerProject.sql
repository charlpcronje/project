SET collation_connection = 'utf8mb4_0900_ai_ci';
CREATE OR REPLACE
VIEW vParticipantExpenseCostTotalsPerProject AS
select 
	ANY_VALUE(eru.RowNumber) as RowNumber,
	ANY_VALUE(eru.ProjectId) as ProjectId,
    ANY_VALUE(eru.ProjectName) as ProjectName,

	ANY_VALUE(eru.RecoverableExpenseId) as RecoverableExpenseId,
	ANY_VALUE(eru.ExpenseTypeId) as ExpenseTypeId,
	ANY_VALUE(eru.ExpenseTypeName) as ExpenseTypeName,
	ANY_VALUE(eru.UnitTypeCode) as UnitTypeCode,
	ANY_VALUE(eru.UnitTypeName) as UnitTypeName,

	ANY_VALUE(eru.ParticipantIdContracting) as ParticipantIdContracting,
	ANY_VALUE(eru.ParticipantInAgreementContracting) as ParticipantInAgreementContracting,
    
    ANY_VALUE(eru.ParticipantIdContracted) as ParticipantIdContracted,
	ANY_VALUE(eru.ParticipantInAgreementContracted) as ParticipantInAgreementContracted,

    ANY_VALUE(eru.PurchaseDate) as PurchaseDate,
	ANY_VALUE(eru.ExpenseRateForDate) as ExpenseRateForDate,
	SUM(eru.NumberOfUnits) as SumNrOfUnits,
	sum(eru.NumberOfUnits * eru.ExpenseRateForDate) as LineAmount,
	0 AS RatesMissing,
    ANY_VALUE(eru.AgreementBetweenParticipantsId) as AgreementBetweenParticipantsId
from 
	ig_db.vPpExpenseRateUplineRecursive eru
where 
    eru.ExpenseRateForDate >= 0
group by 
	eru.RowNumber,
	eru.ProjectId,
    eru.ProjectName,

	eru.RecoverableExpenseId,
	eru.ExpenseTypeId,
	eru.ExpenseTypeName,
	eru.UnitTypeCode,
	eru.UnitTypeName,

	eru.ProjectParticipantIdContracting,
	eru.ParticipantInAgreementContracting,
    
    eru.ProjectParticipantIdContracted,
	eru.ParticipantInAgreementContracted,

    eru.PurchaseDate,
    eru.ExpenseRateForDate,
	eru.AgreementBetweenParticipantsId

UNION all
select 
	ANY_VALUE(eru2.RowNumber) as RowNumber,
	ANY_VALUE(eru2.ProjectId) as ProjectId,
    ANY_VALUE(eru2.ProjectName) as ProjectName,

	ANY_VALUE(eru2.RecoverableExpenseId) as RecoverableExpenseId,
	ANY_VALUE(eru2.ExpenseTypeId) as ExpenseTypeId,
	ANY_VALUE(eru2.ExpenseTypeName) as ExpenseTypeName,
	ANY_VALUE(eru2.UnitTypeCode) as UnitTypeCode,
	ANY_VALUE(eru2.UnitTypeName) as UnitTypeName,

	ANY_VALUE(eru2.ParticipantIdContracting) as ParticipantIdContracting,
	ANY_VALUE(eru2.ParticipantInAgreementContracting) as ParticipantInAgreementContracting,
    
    ANY_VALUE(eru2.ParticipantIdContracted) as ParticipantIdContracted,
	ANY_VALUE(eru2.ParticipantInAgreementContracted) as ParticipantInAgreementContracted,

    ANY_VALUE(eru2.PurchaseDate) as PurchaseDate,
	ANY_VALUE(eru2.ExpenseRateForDate) as ExpenseRateForDate,
	SUM(eru2.NumberOfUnits) as SumNrOfUnits,
	null as LineAmount, -- "Remuneration rate(s) missing" as LineAmount
    1 as RatesMissing,
    ANY_VALUE(eru2.AgreementBetweenParticipantsId) as AgreementBetweenParticipantsId

from 
	ig_db.vPpExpenseRateUplineRecursive eru2
where 
    eru2.ExpenseRateForDate < 0
group by 
	eru2.RowNumber,
	eru2.ProjectId,
    eru2.ProjectName,

	eru2.RecoverableExpenseId,
    eru2.ExpenseTypeId,
	eru2.ExpenseTypeName,
	eru2.UnitTypeCode,
	eru2.UnitTypeName,

	eru2.ProjectParticipantIdContracting,
	eru2.ParticipantInAgreementContracting,
    
    eru2.ProjectParticipantIdContracted,
	eru2.ParticipantInAgreementContracted,

    eru2.PurchaseDate,
    eru2.ExpenseRateForDate,
   	eru2.AgreementBetweenParticipantsId
;

