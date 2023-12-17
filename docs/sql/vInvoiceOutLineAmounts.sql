SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vInvoiceOutLineAmounts AS
    SELECT 
        'Time Related' AS ExpenseType,
        ((tc.AgreementParticipantIdPayer * 2) * tc.RowNumber) AS RowNumber,
        tc.AgreementParticipantIdPayer AS ParticipantIdContracting,
        tc.AgreementPayer AS ParticipantContracting,
        tc.AgreementParticipantIdBeneficiary AS ParticipantIdContracted,
        tc.AgreementBeneficiary AS ParticipantContracted,
        tc.ActivityDate AS ActivityDate,
        tc.SumNrOfUnits AS SumNrOfUnits,
        tc.LineAmount AS LineAmount,
        tc.RatesMissing AS RatesMissing
    FROM
        vParticipantTimeCostTotals tc