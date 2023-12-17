SET collation_connection = 'utf8mb4_0900_ai_ci';
CREATE OR REPLACE
VIEW vInvoiceGenLineAmountsPerProjectTotals AS
select 
	v.ProjectId,    
	v.ProjectName,
	v.ParticipantIdContracting,
	v.ParticipantContracting, 
	v.ParticipantIdContracted,
	v.ParticipantContracted,
    
	SUM(v.SumNrOfUnits) as SumNrOfUnits, 
	SUM(v.LineAmount) as SumLineAmount, 
	SUM(v.RatesMissing) as SumRatesMissing,
	MAX(v.activityDate) activityDate
FROM vInvoiceGeneratorLineAmountsPerProjectAndExpense v 
GROUP BY 
	v.ProjectId,
	v.ProjectName,
	v.ParticipantIdContracting,
	v.ParticipantContracting, 
	v.ParticipantIdContracted,
	v.ParticipantContracted;

