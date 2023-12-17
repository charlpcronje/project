SET collation_connection = 'utf8mb4_0900_ai_ci';
CREATE OR REPLACE 
VIEW vProjectParticipantList AS
    SELECT 
		pp.ProjectParticipantId,
		pp.ParticipantId,
		
        pr.ProjectId,
        pr.ProjectIdParent,
        pr.ParticipantIdHost, 
		parhost.SystemName as ParticipantNameHost,

        pr.ProjectParticipantIdLevel1, 
		parlevel1.ParticipantId as ParticipantIdLevel1,
		parlevel1.SystemName as ParticipantNameLevel1,

        pr.IndividualIdProjectAdmin, 
        pr.FlagSustenanceProject, 
        pr.ProjectNumberBigInt, 
        parentproj.ProjectNumberBigInt AS ParentProjectNumberBigInt,

        pr.ProjectNumberText, 
        pr.ProjectNameText, 
        pr.SubProjNumber, 
        pr.Title, 
        pr.Description, 
        pr.IsActive, 
        pr.StartDate, 
        pr.CompletionDate,

        parentproj.ProjectNameText as ProjectParentNameText,

        CONCAT(i.NickName, ' ', i.LastName) AS IndividualNameProjectAdmin,
        
        getProjectStageForDate(pr.ProjectId, now()) AS ProjectStageCurrent
		
    FROM
		ProjectParticipant pp
		LEFT JOIN Project pr ON (pp.ProjectId = pr.ProjectId)
        LEFT JOIN Project parentproj ON (parentproj.ProjectId = pr.ProjectIdParent)
        LEFT JOIN Participant parentpart ON (parentproj.ParticipantIdHost = parentpart.ParticipantId)

        LEFT JOIN Individual i ON (i.IndividualId = pr.IndividualIdProjectAdmin)
        JOIN Participant parhost ON (pr.ParticipantIdHost = parhost.ParticipantId)

        join ProjectParticipant pplevel1 on pr.ProjectParticipantIdLevel1 = pplevel1.ProjectParticipantId
        join Participant as parlevel1 on pplevel1.ParticipantId = parlevel1.ParticipantId
        