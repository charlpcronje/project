SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vIndividualQualification AS

    SELECT
		i.IndividualQualificationId
		,i.IndividualId
			,ind.FirstName 		as IndividualId_FirstName
			,ind.LastName 		as IndividualId_LastName
			,ind.ParticipantId 	as ParticipantId
		,i.InstituteQualificationId
			,iq.Name 	as InstituteQualificationId_Name
				,s.Name as StudyInstituteId_Name
		,i.YearCompleted
		,i.Description
		,CONCAT(iq.Name, " - ", s.Name) AS CombinedQualification


	FROM
		IndividualQualification i
		LEFT JOIN Individual ind				ON (i.IndividualId = ind.IndividualId)
		LEFT JOIN InstituteQualification 	iq	ON (i.InstituteQualificationId = iq.InstituteQualificationId)
		LEFT JOIN StudyInstitute 			s	ON (iq.StudyInstituteId = s.StudyInstituteId)