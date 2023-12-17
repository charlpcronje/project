SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE 
VIEW vProjectParticipantSdRoles AS

SELECT 	
    ppsdr.ProjectParticipantSdRoleId AS ProjectParticipantSdRoleId,
    ppsdr.ProjectParticipantId AS ProjectParticipantId,
    ppsdr.ProjectSdId AS ProjectSdId,
    ppsdr.SdRoleId AS SdRoleId,

    vpp.ParticipantIdBeneficiary AS ParticipantIdBeneficiary,
    vpp.SystemNameBeneficiary AS SystemNameBeneficiary,

	psd.ServiceDisciplineId AS SdId, 
	sd.ServiceDisciplineCode AS SdCode, 

    sd.Name AS SdName,
    CONCAT(sd.Name, " - ", rop.Name) as SdAndRole,

    rop.RoleOnAProjectId AS RoleOnAProjectId,
    rop.Name AS RoleOnAProjectName,
    
    p.ProjectId, 
    CONCAT(p.ProjectNameText, " - ", p.SubProjNumber) as ProjectName,
    p.Title as ProjectTitle,
    p.ProjectParticipantIdLevel1, 
    p.ParticipantIdHost as ParticipantIdHost, 
    pt.SystemName as ParticipantNameHost, 
    p.ProjectNumberBigInt, 
    p.ProjectNumberText 
    
FROM 	
	ProjectParticipantSdRole ppsdr
    JOIN vProjectParticipantPayerBen vpp ON (vpp.ProjectParticipantId = ppsdr.ProjectParticipantId)
    JOIN ProjectSd psd ON (ppsdr.ProjectSdId = psd.ProjectSdId)
    JOIN ServiceDiscipline sd ON (psd.ServiceDisciplineId = sd.ServiceDisciplineId)
    JOIN SdRole sdr ON (ppsdr.SdRoleId = sdr.SdRoleId)
    JOIN RoleOnAProject rop ON (sdr.RoleOnAProjectId = rop.RoleOnAProjectId)
    JOIN Project p ON (psd.ProjectId = p.ProjectId)
    JOIN Participant pt ON (p.ParticipantIdHost = pt.ParticipantId);
    
