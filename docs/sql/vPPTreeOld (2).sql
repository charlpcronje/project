CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vPPTree` AS
WITH RECURSIVE vpp 
	(ProjectId, 
	ProjectParticipantId, 
	Level, 
	ProjectParticipantIdAboveMe, 
	ParticipantIdAboveMe, 
	ParticipantNameAboveMe,
	ParticipantId, 
	ParticipantName, 
	IndividualId,
	-- IndividualName,
	Description, 
	AnyChildren, 
	IsIndividual
	)

AS ( SELECT 
		pp.ProjectId, 
        pp.ProjectParticipantId, 
        1 Level, 
        pp.ProjectParticipantIdAboveMe as ProjectParticipantIdAboveMe, 
        0 AS ParticipantIdAboveMe,
        concat("level 0: ", p.SystemName) AS ParticipantNameAboveMe,
        p.ParticipantId, 
        p.SystemName as ParticipantName, 
        i.IndividualId,
        -- p.SystemName as IndividualName,
        pp.Description, 
        (SELECT IF((COUNT(1) > 0), 'Y', 'N') FROM ProjectParticipant ppp
            WHERE
                (ppp.ProjectParticipantIdAboveMe = pp.ProjectParticipantId)) AS AnyChildren,
        p.IsIndividual AS IsIndividual
     FROM   
		ProjectParticipant pp
        JOIN Participant  p ON (pp.ParticipantId = p.ParticipantId)
        LEFT JOIN Individual i ON (i.ParticipantId = p.ParticipantId)       
     WHERE 
		pp.ProjectParticipantIdAboveMe is null
        
     UNION ALL
     SELECT 
		pp2.ProjectId, 
        pp2.ProjectParticipantId, 
        (yd.Level + 1) AS Level, 
        pp2.ProjectParticipantIdAboveMe as ProjectParticipantIdAboveMe, 
        yd.ParticipantId  AS ParticipantIdAboveMe,
        yd.ParticipantName AS ParticipantNameAboveMe,
        
        p2.ParticipantId, 
        p2.SystemName as ParticipantName, 
        i2.IndividualId,
        pp2.Description, 
		(SELECT IF((COUNT(1) > 0), 'Y', 'N') 
           FROM ProjectParticipant ppp2
            WHERE (ppp2.ProjectParticipantIdAboveMe = pp2.ProjectParticipantId)) AS AnyChildren,
       p2.IsIndividual AS IsIndividual
       -- p2.RegisteredName AS ParticipantNameUpline
	 FROM
		vpp yd
        JOIN ProjectParticipant pp2 ON (pp2.ProjectParticipantIdAboveMe = yd.ProjectParticipantId)
		JOIN Participant p2 ON (pp2.ParticipantId = p2.ParticipantId)
        LEFT JOIN Individual i2 ON (i2.ParticipantId = p2.ParticipantId)       
	)

SELECT 
	*
FROM vpp
GO






