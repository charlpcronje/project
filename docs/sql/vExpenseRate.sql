SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vExpenseRate AS
    SELECT 
		er.ExpenseRateId, 
		er.RecoverableExpenseId,
        ppayer.SystemName as SystemNamePayer,
        pben.SystemName as SystemNameBeneficiary,
		et.Name AS ExpenseTypeName,
		ut.Name AS ExpenseTypeUnitName,
        
        -- Payer details
        ppben.ProjectParticipantIdAboveMe as ProjectParticipantIdPayer,
        pppayer.ParticipantId as ParticipantIdPayer,

		-- Beneficiary details
        ppben.ProjectParticipantId as ProjectParticipantIdBeneficiary,
        ppben.ParticipantId as ParticipantIdBeneficiary,
        
		er.ExpenseRatePerUnit, 
		er.ExpenseHandlingPerc, 
		er.MaxExpenseAmtPerUnit, 
		er.Description, 
		er.StartDate, 
		er.EndDate,
		re.AgreementBetweenParticipantsId, 
		re.ExpenseTypeId, 
		et.UnitTypeCode AS ExpenseTypeUnitCode
FROM
		ExpenseRate er 
        JOIN RecoverableExpense re ON er.RecoverableExpenseId = re.RecoverableExpenseId
        join ExpenseType et on re.ExpenseTypeId = et.ExpenseTypeId
        join UnitType ut ON et.UnitTypeCode = ut.UnitTypeCode
        join AgreementBetweenParticipants abp on re.AgreementBetweenParticipantsId = abp.AgreementBetweenParticipantsId
        join ProjectParticipant ppben on abp.ProjectParticipantId = ppben.ProjectParticipantId
        join ProjectParticipant pppayer on ppben.ProjectParticipantIdAboveMe = pppayer.ProjectParticipantId
        join Participant ppayer on pppayer.ParticipantId = ppayer.ParticipantId
        join Participant pben on ppben.ParticipantId = pben.ParticipantId;
        
