SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW ig_db.vProjectTree AS
-- Source: 
-- Recursive CTE: https://dba.stackexchange.com/questions/194131/parent-child-relation-in-same-table
-- Recursive CTE & Stored Proc alternative: https://stackoverflow.com/questions/4116416/display-parent-child-relationship-when-parent-and-child-are-stored-in-same-table
                    
WITH RECURSIVE prj (ProjectId, 
					ProjectIdParent, 
                    ProjectParticipantIdLevel1,
					ParticipantIdLevel1,
                    ParticipantNameLevel1,
                    ParticipantIdHost,
                    ParticipantNameHost,
                    ProjectIdSustenance,
					IsSustenanceProject,        
                    IndividualIdProjectAdmin, 
                    ProjectNumberBigInt,
                    ProjectNumberText,
                    ProjectNameText,
                    Title, 
					SubProjNumber,
                    Description, 
					IsActive,
                    StartDate, 
                    CompletionDate, 
					LastUpdateTimestamp, 
                    LastUpdateUserName, 
                    ProjectStage, 
                    NodeType, 
                    RowOrderNo, 
                    Level,
                    ProjectName)
                    
AS ( 
		SELECT 
         pr.ProjectId, 
         pr.ProjectIdParent, 
		 pr.ProjectParticipantIdLevel1,
		 parlevel1.ParticipantId as ParticipantIdLevel1,
		 parlevel1.SystemName as ParticipantNameLevel1,
		 pr.ParticipantIdHost,
         parhost.SystemName as ParticipantNameHost,
         parhost.ProjectIdSustenance,
		 pr.flagSustenanceProject as IsSustenanceProject,        
         pr.IndividualIdProjectAdmin, 
         pr.ProjectNumberBigInt, 
         pr.ProjectNumberText, 
         pr.ProjectNameText, 
         pr.Title, 
		 pr.SubProjNumber,
         pr.Description, 
         pr.IsActive, 
         pr.StartDate, 
         pr.CompletionDate, 
		 pr.LastUpdateTimestamp, 
         pr.LastUpdateUserName, 
         getProjectStageForDate(pr.ProjectId, now()) AS ProjectStage,
         CAST('NODETYPE_PROJECT' AS CHAR(50)) AS NodeType, 
         (UNIX_TIMESTAMP(pr.StartDate) * 1000) RowOrderNo, 
         1 Level,
		 pr.ProjectNameText
     FROM   
		ig_db.Project AS pr
        join Participant as parhost on pr.ParticipantIdHost = parhost.ParticipantId
        join ProjectParticipant pplevel1 on pr.ProjectParticipantIdLevel1 = pplevel1.ProjectParticipantId
        join Participant as parlevel1 on pplevel1.ParticipantId = parlevel1.ParticipantId
     WHERE 
		pr.ProjectIdParent is null
        
     UNION ALL
     
     SELECT 
         pr2.ProjectId, 
         pr2.ProjectIdParent, 
         pr2.ProjectParticipantIdLevel1,
		 parlevel1_2.ParticipantId as ParticipantIdLevel1,
		 parlevel1_2.SystemName as ParticipantNameLevel1,
         pr2.ParticipantIdHost AS ParticipantIdHost,
         parhost2.SystemName AS ParticipantNameHost,
         parhost2.ProjectIdSustenance,
		 pr2.flagSustenanceProject as IsSustenanceProject,        
         pr2.IndividualIdProjectAdmin, 
         pr2.ProjectNumberBigInt, 
         pr2.ProjectNumberText, 
         pr2.ProjectNameText, 
         pr2.Title, 
		 pr2.SubProjNumber as SubProjNumber,
         pr2.Description, 
         pr2.IsActive, 
         pr2.StartDate, 
         pr2.CompletionDate, 
		 pr2.LastUpdateTimestamp, 
         pr2.LastUpdateUserName,
         getProjectStageForDate(pr2.ProjectId, now()) AS ProjectStage,
         CAST('NODETYPE_SUBPROJECT' AS CHAR(50)) AS NodeType, 
         (UNIX_TIMESTAMP(pr2.StartDate) * 1000) RowOrderNo, 
         (prj2.Level + 1) Level,
		pr2.ProjectNameText
         
	 FROM
		prj AS prj2
     JOIN Project AS pr2 ON (pr2.ProjectIdParent = prj2.ProjectId)
     JOIN Participant as parhost2 ON (pr2.ParticipantIdHost = parhost2.ParticipantId)
	 join ProjectParticipant pplevel1_2 on pr2.ProjectParticipantIdLevel1 = pplevel1_2.ProjectParticipantId
     join Participant as parlevel1_2 on pplevel1_2.ParticipantId = parlevel1_2.ParticipantId

     )
SELECT 
	*,
    ProjectId as Pid, 
	(SELECT COUNT(1) from ig_db.Project x where x.ProjectIdParent = Pid) SubitemCount
    
FROM prj
GO

