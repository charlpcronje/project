CREATE
DEFINER=`administrator`@`localhost` 
PROCEDURE saveTopDescriptionsUsed(pProjectParticipantSdRoleId BIGINT)
BEGIN
	declare vProjectParticipantId 	bigint;
	set vProjectParticipantId = (select ProjectParticipantId 
								from ProjectParticipantSdRole
                                where projectParticipantSdRoleId = pProjectParticipantSdRoleId);
                                
delete from TopDescriptionsUsed where ProjectParticipantId = vProjectParticipantId;

insert into TopDescriptionsUsed (ProjectParticipantId, Description)                                
	(SELECT DISTINCT
		pp.ProjectParticipantId AS projectParticipantId,
		t.Description AS description
	FROM
		(timesheet t
		JOIN projectparticipantsdrole pp ON ((t.ProjectParticipantSdRoleId = pp.ProjectParticipantSdRoleId)))
	WHERE t.ProjectParticipantSdRoleId = pProjectParticipantSdRoleId
	ORDER BY t.TimesheetId DESC      
	LIMIT 10);
                                
END