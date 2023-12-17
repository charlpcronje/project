SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE  
ALGORITHM=UNDEFINED 
SQL SECURITY DEFINER VIEW vProjectParticipantPayerBen AS 
SELECT 
	pp.ProjectId AS ProjectId, 
    p.ParticipantIdHost AS ParticipantIdHost,
	p.ProjectNumberText AS ProjectNumberText, 
	p.ProjectNameText AS ProjectTitle, 
	pp.ProjectParticipantId AS ProjectParticipantId, 
	pp.ProjectParticipantIdAboveMe AS ProjectParticipantIdAboveMe, 
	payer.ParticipantId AS ParticipantIdPayer, 
	payer.SystemName AS SystemNamePayer, 
	ben.ParticipantId AS ParticipantIdBeneficiary, 
	ben.SystemName AS SystemNameBeneficiary
FROM 
	ProjectParticipant pp 
    JOIN ProjectParticipant ppPayer ON (pp.ProjectParticipantIdAboveMe = ppPayer.ProjectParticipantId)
	JOIN Participant payer ON (ppPayer.ParticipantId = payer.ParticipantId)
    JOIN Participant ben ON (pp.ParticipantId = ben.ParticipantId) 
    JOIN Project p ON (pp.ProjectId = p.ProjectId)
;


