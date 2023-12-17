SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
	
VIEW vRoleOnAProject AS

    SELECT
		r.RoleOnAProjectId
		,r.ServiceDisciplineIdIndustry
			,sd.Name as ServiceDisciplineIdIndustry_Name
		,r.Name
		,r.Abbreviation
		,r.Description


	FROM
		RoleOnAProject r
		LEFT JOIN ServiceDiscipline 	sd	ON (r.ServiceDisciplineIdIndustry = sd.ServiceDisciplineId)
