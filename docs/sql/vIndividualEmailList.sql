SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vIndividualEmailList AS

SELECT 
    i.IndividualId,
    i.Username,
    cp.EmailAddress

FROM
    Individual i
    LEFT JOIN Participant pt ON (pt.ParticipantId = i.ParticipantId)
    LEFT JOIN ParticipantOffice po ON (pt.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
    LEFT JOIN ContactPoint cp ON (po.ContactPointIdDefault = cp.ContactPointId)

