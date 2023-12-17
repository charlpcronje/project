SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
view vRecoverableExpenseAgreement as
    select 
        re.RecoverableExpenseId,
		va.AgreementBetweenParticipantsId,
        va.ProjectId,
        va.ProjectName,
		va.ProjectParticipantId,
        va.ParticipantIdPayer,
		va.SystemNamePayer,  -- Bygesit
		va.ParticipantIdBeneficiary,  -- Bygesit
		va.SystemNameBeneficiary,  -- Bygesit
        va.AgreementBudget, 
        CONCAT(va.SystemNamePayer, ' - ', va.SystemNameBeneficiary) as Agreement,
        re.ExpenseTypeId,
        re.Description as ExpenseAgreementDescription,
        re.ExpenseBudget,
        CONCAT(etp.Name, ' - ', et.Name) as ExpenseTypeName,
        et.AllowHandlingPerc,
        et.AllowMaxAmtPerUnit,
        ut.UnitTypeCode,
        ut.Name as UnitName,
        va.Level,
        et.AllowForAllParticipantTypes

    from
        vAgreementBetweenParticipants va
        join RecoverableExpense re on (re.AgreementBetweenParticipantsId = va.AgreementBetweenParticipantsId)
        join ExpenseType et on (re.ExpenseTypeId = et.ExpenseTypeId)
        join ExpenseTypeParent etp on (et.ExpenseTypeParentId = etp.ExpenseTypeParentId)
        join UnitType ut on (et.UnitTypeCode = ut.UnitTypeCode);
