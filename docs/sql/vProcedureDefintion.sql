SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = root@localhost 
    SQL SECURITY DEFINER
VIEW vProcedureDefinition AS
    SELECT 
	p.ProcedureDefinitionId  AS ProcedureDefinitionId,
 	p.GenProcedureId  AS GenProcedureId,
	p.DefinitionId  AS DefinitionId,
	d.Name		AS Name,
	d.Description	AS Description

    FROM
        ProcedureDefinition p
        JOIN Definition d ON (p.DefinitionId = d.DefinitionId)
        