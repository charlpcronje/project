SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW vProjectSd AS

SELECT 	
	vsd.RowOrderNo as RowNumber, -- CONCAT(vsd.ServiceDisciplineId,"000000") AS RowNumber,
	vsd.ParentName as ParentName,
	vsd.ServiceDisciplineIdParent AS ServiceDisciplineIdParent,
    vsd.Level as Level,
    vsd.ServiceDisciplineID   AS ServiceDisciplineId, 
    vsd.ServiceDisciplineCode AS ServiceDisciplineCode, 
    vsd.ServiceDisciplineName AS ServiceDisciplineName,
    vsd.Name as sdCodeAndName,
	psd.ProjectSdId AS ProjectSdId, 
	psd.ProjectId AS ProjectId,
	vsd.ServiceDisciplineIdIndustry as ServiceDisciplineIdIndustry,
    vsd.IndustrySdName as IndustrySdName
    
FROM 	ProjectSd psd 
		JOIN vServiceDiscipline vsd ON  psd.ServiceDisciplineId = vsd.ServiceDisciplineId;
