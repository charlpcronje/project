SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vRecoverableExpense AS
    SELECT 
		re.RecoverableExpenseId,
		re.AgreementBetweenParticipantsId, 
		re.ExpenseTypeId, 
		re.ExpenseBudget AS ExpenseBudget,
		re.Description AS ExpenseAgreementDescription,

		va.SystemNamePayer,
        va.SystemNameBeneficiary,
		va.Description AS AgreementDescription,
        
        etp.ExpenseTypeParentId, 
        etp.Name as ExpenseTypeParentName, 
        
        et.Name AS ExpenseTypeName,
		et.UnitTypeCode AS ExpenseTypeUnitCode,
        
		ut.Name AS ExpenseTypeUnitName
FROM
        RecoverableExpense re 
        join vAgreementBetweenParticipants va on (re.AgreementBetweenParticipantsId = va.AgreementBetweenParticipantsId)
        join ExpenseType et on re.ExpenseTypeId = et.ExpenseTypeId
        join ExpenseTypeParent etp on et.ExpenseTypeParentId = etp.ExpenseTypeParentId
        join UnitType ut ON et.UnitTypeCode = ut.UnitTypeCode
