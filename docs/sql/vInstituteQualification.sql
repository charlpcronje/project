SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vInstituteQualification AS

    SELECT
		i.InstituteQualificationId
		,i.StudyInstituteId
			,s.Name as StudyInstituteId_Name
		,i.Name
		,i.Description
		,CONCAT(i.Name, " - ", s.Name) AS CombinedName


	FROM
		InstituteQualification i
		LEFT JOIN StudyInstitute 	s	ON (i.StudyInstituteId = s.StudyInstituteId)
