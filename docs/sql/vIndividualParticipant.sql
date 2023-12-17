SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = administrator@localhost 
    SQL SECURITY DEFINER
VIEW vindividualparticipant AS
    SELECT 
        i.IndividualId AS IndividualId,
        p.ParticipantId AS ParticipantId
    FROM
        (Individual i
        JOIN Participant p)
    WHERE
        ((i.ParticipantId = p.ParticipantId)
            AND (p.IsIndividual = 'Y'))