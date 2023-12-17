SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vRemunerationRateUpline AS
    SELECT 
		rru.RemunerationRateUplineId, 
		rru.AgreementBetweenParticipantsId, 
		rru.ProjectParticipantSdRoleIdForRate AS ProjectParticipantSdRoleIdIndividual, 
		rru.ParticipantIdIndividual, 
		rru.ProjBasedRemunTypeId, 
        vppsdr.ProjectParticipantSdRoleId, 
        vppsdr.SystemNameBeneficiary, 
        vppsdr.SdId, 
        vppsdr.SdCode, 
        vppsdr.SdName, 
        vppsdr.RoleOnAProjectId, 
        vppsdr.RoleOnAProjectName, 
        vppsdr.ProjectSdId, 
        vppsdr.ProjectId, 
        vppsdr.ProjectNumberBigInt, 
        vppsdr.ProjectTitle,
		pbrt.Name AS ProjBasedRemunTypeName,
		pbrt.UnitTypeCode AS ProjBasedRemunTypeUnitCode,
		pbrut.Name AS ProjBasedRemunTypeUnitName,
        ri.Name AS RemunerationInterval,
		rru.RatePerUnit, 
		rru.Description, 
		rru.StartDate, 
		rru.EndDate
FROM
		RemunerationRateUpline rru 
        JOIN vProjectParticipantSdRoles vppsdr ON rru.ProjectParticipantSdRoleIdForRate = vppsdr.ProjectParticipantSdRoleId
        JOIN ProjBasedRemunType pbrt ON rru.ProjBasedRemunTypeId = pbrt.ProjBasedRemunTypeId
        JOIN UnitType pbrut ON pbrt.UnitTypeCode = pbrut.UnitTypeCode
        JOIN RemunerationInterval ri ON pbrt.RemunerationIntervalCode = ri.RemunerationIntervalCode
