SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vProjectParticipant AS
    SELECT 
		pp.ProjectParticipantId AS ProjectParticipantId,
		
        pp.ProjectId AS ProjectId,
        pr.ProjectParticipantIdLevel1 AS ProjectParticipantIdLevel1,
        plevel1.SystemName AS ParticipantNameLevel1,
        pr.ParticipantIdHost AS ParticipantIdHost,
        parthost.SystemName AS ParticipantNameHost,
        pr.ProjectNumberBigInt AS ProjectNumberBigInt,
        pr.ProjectNumberText AS ProjectNumberText,
        pr.Title AS ProjectTitle,
        pr.ProjectName AS ProjectName,
        pr.SubProjNumber AS SubProjNumber,
        pp.ProjectParticipantIdAboveMe AS ProjectParticipantIdAboveMe,
        pam.SystemName AS ProjPartAboveMeSystemName,
        payer.ParticipantId AS ParticipantIdPayer,
        payer.SystemName AS SystemNamePayer,
        payer.IsIndividual AS IsIndividual,
        payer.RepresentativeIndividualId AS RepresentativeIndividualId,
        i.FirstName AS FirstName,
        i.LastName AS LastName,
        i2.IndividualId AS IndividualId,
        i2.FirstName AS FirstName2,
        i2.LastName AS LastName2
    FROM
        ProjectParticipant pp
        JOIN Participant payer ON (pp.ParticipantId = payer.ParticipantId)
        JOIN vProject pr ON (pp.ProjectId = pr.ProjectId)
        LEFT JOIN Individual i ON (payer.RepresentativeIndividualId = i.IndividualId)
        LEFT JOIN Individual i2 ON (payer.ParticipantId = i2.ParticipantId)

        JOIN Participant parthost ON (parthost.ParticipantId = pr.ParticipantIdHost)

        LEFT JOIN ProjectParticipant ppam ON (pp.ProjectParticipantIdAboveMe = ppam.ProjectParticipantId)
        LEFT JOIN Participant pam ON (ppam.ParticipantId = pam.ParticipantId)
        
        LEFT JOIN ProjectParticipant pplevel1 ON (pplevel1.ProjectParticipantId = pr.ProjectParticipantIdLevel1)
        LEFT JOIN Participant plevel1 ON (pplevel1.ParticipantId = plevel1.ParticipantId)

