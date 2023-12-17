CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vPPTree` AS
WITH RECURSIVE vpp 
	(ProjectId, 
	ProjectParticipantIdContracted, 
	Level, 
	ProjectParticipantIdContracting, 
	ParticipantIdContracting, 
	ParticipantNameContracting,
	ParticipantIdContracted, 
	ParticipantNameContracted, 
	IndividualIdContracted,
	IndividualNameContracted,
	Description, 
	AnyChildren, 
	IsIndividual
	)

AS ( SELECT 
		pp.ProjectId, 
        pp.ProjectParticipantId, 
        1 Level, 
        pp.ProjectParticipantIdAboveMe as ProjectParticipantIdContracting, 
        0 AS ParticipantIdContracting,
        concat("level 0: ", p.SystemName) AS ParticipantContracting,
        p.ParticipantId as ParticipantIdContracted, 
        p.SystemName as ParticipantNameContracted, 
        i.IndividualId as IndividualIdContracted,
        p.SystemName as IndividualNameContracted,
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
        pp2.ProjectParticipantId as ProjectParticipantIdContracted, 
        (yd.Level + 1) AS Level, 
        pp2.ProjectParticipantIdAboveMe as ProjectParticipantIdContracting, 
        yd.ParticipantIdContracted  AS ParticipantIdContracting,
        yd.ParticipantNameContracted AS ParticipantNameContracting,
        
        p2.ParticipantId as ParticipantIdContracted, 
        p2.SystemName as ParticipantNameContracted, 
        i2.IndividualId as IndividualIdContracted,
        p2.SystemName as IndividualNameContracted,
        pp2.Description, 
		(SELECT IF((COUNT(1) > 0), 'Y', 'N') 
           FROM ProjectParticipant ppp2
            WHERE (ppp2.ProjectParticipantIdAboveMe = pp2.ProjectParticipantId)) AS AnyChildren,
       p2.IsIndividual AS IsIndividual
       -- p2.RegisteredName AS ParticipantNameUpline
	 FROM
		vpp yd
        JOIN ProjectParticipant pp2 ON (pp2.ProjectParticipantIdAboveMe = yd.ProjectParticipantIdContracted)
		JOIN Participant p2 ON (pp2.ParticipantId = p2.ParticipantId)
        LEFT JOIN Individual i2 ON (i2.ParticipantId = p2.ParticipantId)       
	)

SELECT 
	*
FROM vpp
GO






