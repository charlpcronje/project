SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = root@localhost 
    SQL SECURITY DEFINER
VIEW vassignmentforindividuallist AS
    SELECT 
        a.AssignmentId AS AssignmentId,
--        a.ProjectParticipantIdInstructor AS ProjectParticipantIdInstructor,
--        pa.SystemName AS PpInstructorName,
--        a.IndividualIdInstructor AS IndividualIdInstructor,
--        CONCAT(i.FirstName, ' ', i.LastName) AS IndividualInstructorName,
        a.ProjectParticipantIdIndivResp AS ProjectParticipantIdIndivResp,
        CONCAT(ii.FirstName, ' ', ii.LastName) AS IndividualRespName,
--        a.IndividualIdLogger AS IndividualIdLogger,
--        CONCAT(iii.FirstName, ' ', iii.LastName) AS IndividualLoggerName,
        a.AssignmentGroupId AS AssignmentGroupId,
        CONCAT(ag.AssignmentGroupNumber, ' ', ag.Name) AS AssignmentGroupName,
        a.AssignmentNumber AS AssignmentNumber,
        a.Name AS Name,
        a.Description AS Description,
        a.StartDate AS StartDate,
        a.TimeOfDay AS TimeOfDay,
        a.RepeatCode AS RepeatCode,
        a.LastUpdateTimestamp AS LastUpdateTimestamp,
        a.LastUpdateUserName AS LastUpdateUserName,
        ii.IndividualId,
        a.Completed AS Completed
--        p.ProjectId AS ProjectId,
--        CONCAT(p.Id, '-', a.AssignmentNumber) AS ProjectAssignmentNumber,
--        p.Title AS Title,
--        CONCAT(p.Id, ' ', p.Title) AS ProjectName
    FROM
        ((((Assignment a
--        LEFT JOIN projectparticipant pins ON ((a.ProjectParticipantIdInstructor = pins.ProjectParticipantId)))
--        LEFT JOIN project p ON ((pins.ProjectId = p.ProjectId)))
--        LEFT JOIN participant pa ON ((pins.ParticipantId = pa.ParticipantId)))
--        LEFT JOIN individual i ON ((a.IndividualIdInstructor = i.IndividualId)))
        LEFT JOIN ProjectParticipant ppresp ON ((a.ProjectParticipantIdIndivResp = ppresp.ProjectParticipantId)))
        LEFT JOIN Participant pa2 ON ((ppresp.ParticipantId = pa2.ParticipantId)))
        LEFT JOIN Individual ii ON ((pa2.ParticipantId = ii.ParticipantId)))
--        LEFT JOIN individual iii ON ((a.IndividualIdLogger = iii.IndividualId)))
        LEFT JOIN AssignmentGroup ag ON ((a.AssignmentGroupId = ag.AssignmentGroupId)))