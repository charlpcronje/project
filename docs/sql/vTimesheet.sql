SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE

VIEW vTimesheet AS

    SELECT
		t.TimesheetId, 
        t.ProjectParticipantSdRoleId, 
		t.PpSdRoleStageId,
        t.ProjBasedRemunTypeId, 
        t.AssignmentId, 
        t.TaskId, 
        t.ActivityDate, 
        t.NumberOfUnits, 
        t.Description,

        pbrt.Name as RemunerationTypeName, -- e.g. Hourly Rate - Work
        
        ppsdr.ProjectParticipantId as ProjectParticipantIdThatBookedTime, 
        ppsdr.ProjectSdId, 
        ppsdr.SdRoleId, 
        ppben.ParticipantId as ParticipantIdThatBookedTime, 
        
       IF(pben.IsIndividual = 'Y', 
					concat(pbeni.FirstName, ' ', pbeni.LastName), 	-- It is an Individual
					pben.RegisteredName)								-- Not an Individual
			as SystemNameThatBookedTime, 
        
        sd.ServiceDisciplineId as SdId,
        sd.ServiceDisciplineCode as SdCode,
        sd.Name as SdName, 
        rop.Name as RoleName, --  SdAndRole,
        rop.RoleOnAProjectId, 

        ppben.ProjectId,         
        p.ProjectNumberText, 
        p.ProjectNameText,
        
        p.ParticipantIdHost as ParticipantIdHost,  
        p.ProjectParticipantIdLevel1, 
        
		IF(phost.IsIndividual = 'Y', 
			concat(phosti.FirstName, ' ', phosti.LastName), 	-- It is an Individual
			phost.RegisteredName)								-- Not an Individual
			as ParticipantNameHost,

        ut.UnitTypeCode as UnitTypeCode,
        ut.Name as UnitTypeName,
		
		psds.ProjectSdStageId,
		psds.ProjectStageId,
		ps.StageName

    FROM
		Timesheet t
        join ProjBasedRemunType pbrt on (t.ProjBasedRemunTypeId = pbrt.ProjBasedRemunTypeId)
        join UnitType ut on (pbrt.UnitTypeCode = ut.UnitTypeCode)
		join ProjectParticipantSdRole ppsdr on (t.ProjectParticipantSdRoleId = ppsdr.ProjectParticipantSdRoleId)
        join ProjectParticipant ppben on (ppsdr.ProjectParticipantId = ppben.ProjectParticipantId)
        join Participant pben on (ppben.ParticipantId = pben.ParticipantId)
        left join Individual pbeni on (pben.ParticipantId = pbeni.ParticipantId)
		
        join SdRole sdr on (ppsdr.SdRoleId = sdr.SdRoleId)
        join ServiceDiscipline sd on (sdr.ServiceDisciplineId = sd.ServiceDisciplineId)
        join RoleOnAProject rop on (sdr.RoleOnAProjectId = rop.RoleOnAProjectId)
        
        join Project p on (ppben.ProjectId = p.ProjectId)
        join Participant phost on (p.ParticipantIdHost = phost.ParticipantId)
        left join Individual phosti on (phost.ParticipantId = phosti.ParticipantId)
        

		left join PpSdRoleStage ppsdrs 	on 	(t.PpSdRoleStageId 		= ppsdrs.PpSdRoleStageId)
		left join ProjectSdStage psds  	on 	(ppsdrs.ProjectSdStageId 	= psds.ProjectSdStageId)
		left join ProjectStage ps 		on  (psds.ProjectStageId		= ps.ProjectStageId)
        
        