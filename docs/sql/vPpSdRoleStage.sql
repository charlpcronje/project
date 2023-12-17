SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vPpSdRoleStage AS
select 
ppsd.PpSdRoleStageId,
ppsd.ProjectParticipantSdRoleId, 
ppsd.ProjectSdStageId, 
ppsdr.ProjectParticipantId,
pp.ProjectId,
pp.ParticipantId,
psds.ProjectSdId,
psds.ProjectStageId,
ps.OrderNumber,
ps.StageName,
ps.Description,
ps.StartDate,
ps.EndDate
 

from PpSdRoleStage ppsd

		join ProjectParticipantSdRole ppsdr  on 	(ppsd.ProjectParticipantSdRoleId = ppsdr.ProjectParticipantSdRoleId)
		join ProjectParticipant  pp on 				(ppsdr.ProjectParticipantId = pp.ProjectParticipantId)
		join ProjectSdStage	 psds on 				(ppsd.ProjectSdStageId = psds.ProjectSdStageId)
		join ProjectStage	 ps   on 				(psds.ProjectStageId   = ps.ProjectStageId);
