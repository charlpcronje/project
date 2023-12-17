CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vGeneratableInvoiceTotalsNonNullValues AS
SELECT 
			RowNumber, 
            ActivityDate,
            ProjectId, 
            ProjectName, 
            ParticipantIdContracting, 
            ParticipantContracting, 
            ParticipantIdContracted, 
            ParticipantContracted, 
			LineAmount
			FROM vInvoiceGeneratorLineAmountsPerProjectAndExpense
            where LineAmount is not null;
    