SET collation_connection = 'utf8mb4_0900_ai_ci';
CREATE OR REPLACE
VIEW ig_db.vProject AS
		SELECT 
			pr.ProjectId, 
            pr.ProjectIdParent, 
            pr.ParticipantIdHost, 
            ph.SystemName as ParticipantNameHost,
            pr.ProjectParticipantIdLevel1, 
            pl1.ParticipantId as ParticipantIdLevel1, 
            pl1.SystemName as ParticipantNameLevel1,
            pr.IndividualIdProjectAdmin, 
            pi.SystemName as IndividualNameProjectAdmin, 
            pr.ProjectNumberBigInt, 
            pr.ProjectNumberText, 
            pr.ProjectNameText, 
            pr.ProjectNameText as ProjectName, 
            pr.Title, 
            pr.SubProjNumber, 
            pr.Description, 
            pr.IsActive, 
			pr.FlagSustenanceProject, 
            pr.StartDate, 
            pr.CompletionDate
            
		FROM
			Project pr
            join		Participant ph on (pr.ParticipantIdHost = ph.ParticipantId)
            join		ProjectParticipant ppl1 on (pr.ProjectParticipantIdLevel1 = ppl1.ProjectParticipantId)
            join		Participant pl1 on (ppl1.ParticipantId = pl1.ParticipantId)
            left join	Individual i on (pr.IndividualIdProjectAdmin = i.IndividualId)
            left join	Participant pi on (i.ParticipantId = pi.ParticipantId);
            
            
            