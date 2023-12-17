SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vProjectSdStage AS

    SELECT 
		p.ProjectId, 
        p.ProjectNameText,
        psd.ProjectSdId,
        psd.IndustrySdName,
        psd.ServiceDisciplineId,
        psd.ServiceDisciplineCode,
        psd.ParentName,
        sd.Name AS SdName,
        psds.ProjectSdStageId,
        ps.StageName,
        ps.ProjectStageId,
		ps.OrderNumber
        
    FROM
        Project p
        JOIN vProjectSd psd ON (p.ProjectId = psd.ProjectId)
        JOIN ServiceDiscipline sd ON (sd.ServiceDisciplineId = psd.ServiceDisciplineId)
        JOIN ProjectSdStage psds ON (psd.ProjectSdId = psds.ProjectSdId)
        JOIN ProjectStage ps ON (ps.ProjectStageId = psds.ProjectStageId);