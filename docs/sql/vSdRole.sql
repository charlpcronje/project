SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
	
VIEW vSdRole AS

SELECT 
	
	sdr.sdRoleId 					AS sdRoleId,
	sdr.serviceDisciplineId   	    AS serviceDisciplineId,
	r.roleOnAProjectId        	AS roleOnAProjectId,
	sd.serviceDisciplineCode		AS serviceDisciplineCode,
	sd.name							AS serviceDisciplineName,
	r.name							AS roleOnAProjectName,
	CONCAT(sd.serviceDisciplineId, "  ", sd.name, " - ", r.name) AS combinedName,
	sd.serviceDisciplineIdIndustry  AS serviceDisciplineIdIndustry,
	sd.orderNumber					AS orderNumber,
	sd2.name						AS sdParentName,
	sd3.name						AS sdGrandParentName,
	sdi.name						AS sdIndustryName
	
	
FROM	SdRole sdr

	LEFT JOIN ServiceDiscipline sd 	ON (sdr.serviceDisciplineId = sd.serviceDisciplineId)
	LEFT JOIN RoleOnAProject r 		ON (sdr.roleOnAProjectId = r.roleOnAProjectId)
	LEFT JOIN ServiceDiscipline sd2 ON (sd.serviceDisciplineIdParent = sd2.serviceDisciplineId)
	LEFT JOIN ServiceDiscipline sd3 ON (sd2.serviceDisciplineIdParent = sd3.serviceDisciplineId)
	
	LEFT JOIN ServiceDiscipline sdi ON (sd.serviceDisciplineIdIndustry = sdi.serviceDisciplineId)
		

		
		

