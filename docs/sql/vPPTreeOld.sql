CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vPPTree` AS

WITH RECURSIVE vpp (ProjectId, ProjectParticipantId, Level, ParticipantId, Name, Description, ProjectParticipantIdAboveMe, AnyChildren, IsIndividual, ParticipantIdUpline, ParticipantNameUpline)

AS ( SELECT 
		pp.ProjectId, pp.ProjectParticipantId, 1 Level, p.ParticipantId, p.SystemName as Name, pp.Description, pp.ProjectParticipantIdAboveMe, 
           (SELECT IF((COUNT(1) > 0), 'Y', 'N') FROM ProjectParticipant ppp
            WHERE
                (ppp.ProjectParticipantIdAboveMe = pp.ProjectParticipantId)) AS AnyChildren,
                p.IsIndividual AS IsIndividual,
                0 AS ParticipantIdUpline,
                "NONE                         " AS ParticipantNameUpline
     FROM   
		ig_db.ProjectParticipant AS pp,
        ig_db.Participant AS p
     WHERE 
		pp.ProjectParticipantIdAboveMe is null
        AND pp.ParticipantId = p.ParticipantId
     UNION ALL
     SELECT 
		pp2.ProjectId, pp2.ProjectParticipantId, (yd.Level + 1) Level, p2.ParticipantId, p2.SystemName as Name, pp2.Description, pp2.ProjectParticipantIdAboveMe, 
           (SELECT IF((COUNT(1) > 0), 'Y', 'N') 
           FROM ProjectParticipant ppp2
            WHERE
                (ppp2.ProjectParticipantIdAboveMe = pp2.ProjectParticipantId)) AS AnyChildren,
       p2.IsIndividual AS IsIndividual,
       yd.ProjectParticipantId  AS ParticipantIdUpline,
       yd.Name AS ParticipantNameUpline
	 FROM
		vpp AS yd,
        ig_db.Participant AS p2,
		ig_db.ProjectParticipant AS pp2
     WHERE
		pp2.ProjectParticipantIdAboveMe = yd.ProjectParticipantId
        AND pp2.ParticipantId = p2.ParticipantId
	)

SELECT 
	*
FROM vpp
GO






