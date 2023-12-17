SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW vPPTree AS
WITH RECURSIVE vpp 
	(ProjectId, 
	ProjectName,
	Level, 
    
	ProjectParticipantIdContracting, 
	ParticipantIdContracting, 
	ParticipantNameContracting,
	IndividualIdContracting,
	IndividualNameContracting,
    
	ProjectParticipantIdContracted, 
	ParticipantIdContracted, 
	ParticipantNameContracted, 
	IndividualIdContracted,
	IndividualNameContracted,
    ContractedStartDate,
    ContractedEndDate,
    
	AnyChildren, 
	IsIndividual
	)

AS ( SELECT 
		pp.ProjectId, 
        pr.ProjectNameText as ProjectName,
        1 Level, 

        pp.ProjectParticipantIdAboveMe as ProjectParticipantIdContracting, 
        0 AS ParticipantIdContracting,
        i1.FirstName AS ParticipantContracting,
        i1.IndividualId as IndividualIdContracting,
        concat(i1.NickName, ' ' , i1.LastName) as IndividualNameContracting,

        pp.ProjectParticipantId as ProjectParticipantIdContracted, 
        p.ParticipantId as ParticipantIdContracted, 
        p.SystemName as ParticipantNameContracted, 
        i2.IndividualId as IndividualIdContracted,
        concat(i2.NickName, ' ' , i2.LastName) as IndividualNameContracted,

		pp.StartDate as ContractedStartDate,
		pp.EndDate as ContractedEndDate,

        (SELECT IF((COUNT(1) > 0), 'Y', 'N') FROM ProjectParticipant ppp
            WHERE
                (ppp.ProjectParticipantIdAboveMe = pp.ProjectParticipantId)) AS AnyChildren,
        p.IsIndividual AS IsIndividual

     FROM   
		ProjectParticipant pp
        JOIN Participant  p ON (pp.ParticipantId = p.ParticipantId)
        JOIN Project pr on (pp.ProjectId = pr.ProjectId)
        LEFT JOIN Individual i1 ON (i1.ParticipantId = null) -- p.ParticipantIdAboveMe)       
        LEFT JOIN Individual i2 ON (i2.ParticipantId = pp.ParticipantId)       
     WHERE 
		pp.ProjectParticipantIdAboveMe is null
        
     UNION ALL
     SELECT 
		pp2.ProjectId, 
		yd.ProjectName, 
        (yd.Level + 1) AS Level, 

        pp2.ProjectParticipantIdAboveMe as ProjectParticipantIdContracting, 
        yd.ParticipantIdContracted  AS ParticipantIdContracting,
        yd.ParticipantNameContracted AS ParticipantNameContracting,
        i11.IndividualId as IndividualIdContracting,
        concat(i11.FirstName, ' ' , i11.LastName) as IndividualNameContracting,
        
        pp2.ProjectParticipantId as ProjectParticipantIdContracted, 
        p2.ParticipantId as ParticipantIdContracted, 
        p2.SystemName as ParticipantNameContracted, 
        i22.IndividualId as IndividualIdContracted,
        concat(i22.FirstName, ' ' , i22.LastName) as IndividualNameContracted,

		pp2.StartDate as ContractedStartDate,
		pp2.EndDate as ContractedEndDate,

		(SELECT IF((COUNT(1) > 0), 'Y', 'N') 
           FROM ProjectParticipant ppp2
            WHERE (ppp2.ProjectParticipantIdAboveMe = pp2.ProjectParticipantId)) AS AnyChildren,
       p2.IsIndividual AS IsIndividual

	 FROM
		vpp yd
        JOIN ProjectParticipant pp2 ON (pp2.ProjectParticipantIdAboveMe = yd.ProjectParticipantIdContracted)
		JOIN Participant p2 ON (pp2.ParticipantId = p2.ParticipantId)
        LEFT JOIN Individual i11 ON (i11.ParticipantId = yd.ParticipantIdContracting)       
        LEFT JOIN Individual i22 ON (i22.ParticipantId = pp2.ParticipantId)       

	)

SELECT 
	*
FROM vpp
GO






