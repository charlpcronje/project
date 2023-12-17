SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = administrator@localhost 
    SQL SECURITY DEFINER
	
VIEW vIndividualSdRole AS

SELECT 
	
	i.IndividualSdRoleId 		AS IndividualSdRoleId,
	i.IndividualId 				AS IndividualId,
	i.SdRoleId 					AS SdRoleId,
	i.CompetencyLevelId			AS CompetencyLevelId,
	i.YearExperience			AS YearExperience,
	ii.ParticipantId			AS ParticipantId,
	cl.Name						AS CompetencyLevelName,
	sdr.ServiceDisciplineId     AS ServiceDisciplineId,
	sd.ServiceDisciplineCode	AS ServiceDisciplineCode,
	sd.Name						AS ServiceDisciplineName,
	r.Name						AS RoleOnAProjectName,
	CONCAT(sd.ServiceDisciplineId, "  ", sd.Name, " - ", r.Name) AS CombinedName,

	sd2.name						AS sdParentName,
	sd3.name						AS sdGrandParentName,
	sdi.name						AS sdIndustryName
	
	
FROM	IndividualSdRole i
		
	LEFT JOIN Individual ii   		ON (i.IndividualId = ii.IndividualId)		
	LEFT JOIN CompetencyLevel cl 	ON (i.CompetencyLevelId = cl.CompetencyLevelId)
	LEFT JOIN SdRole sdr 			ON (i.SdRoleId = sdr.SdRoleId)
	LEFT JOIN ServiceDiscipline sd 	ON (sdr.ServiceDisciplineId = sd.ServiceDisciplineId)
	LEFT JOIN RoleOnAProject r 		ON (sdr.RoleOnAProjectId = r.RoleOnAProjectId)
	
	LEFT JOIN ServiceDiscipline sd2 ON (sd.serviceDisciplineIdParent = sd2.serviceDisciplineId)
	LEFT JOIN ServiceDiscipline sd3 ON (sd2.serviceDisciplineIdParent = sd3.serviceDisciplineId)
	LEFT JOIN ServiceDiscipline sdi ON (sd.serviceDisciplineIdIndustry = sdi.serviceDisciplineId)
		

		
		

