SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vIndividualProfRegistration AS

    SELECT
		i.IndividualProfRegistrationId
		,i.IndividualId
			,ind.FirstName 	as IndividualId_FirstName
			,ind.LastName 	as IndividualId_LastName
			,ind.ParticipantId 	as ParticipantId
		,i.ProfessionalInstituteId
			,pi.Name as ProfessionalInstituteId_Name
		,i.YearAccepted
		,i.ProfNumber
		,i.Description


	FROM
		IndividualProfRegistration i
		LEFT JOIN Individual 			ind	ON (i.IndividualId = ind.IndividualId)
		LEFT JOIN ProfessionalInstitute 	pi	ON (i.ProfessionalInstituteId = pi.ProfessionalInstituteId)
