SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = root@localhost 
    SQL SECURITY DEFINER
VIEW vPersonResponsible AS
    SELECT 
	p.PersonResponsibleId  	AS PersonResponsibleId,
 	p.GenProcedureId  	AS GenProcedureId,
	p.RoleOnAProjectId  	AS RoleOnAProjectId,
	r.Name			AS Name,
	r.Abbreviation		AS Abbreviation,
	r.Description		AS Description

    FROM
        PersonResponsible p
        JOIN RoleOnAProject r ON (p.RoleOnAProjectId = r.RoleOnAProjectId)
        