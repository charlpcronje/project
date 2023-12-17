SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = administrator@localhost 
    SQL SECURITY DEFINER
VIEW vNonProjectRelatedAgreement AS
    SELECT 
		n.NonProjectRelatedAgreementId AS NonProjectRelatedAgreementId,
		n.ParticipantIdPayer		   AS ParticipantIdPayer,
		p.SystemName					AS SystemNamePayer,
		n.ParticipantIdBeneficiary		AS ParticipantIdBeneficiary,
		p2.SystemName					AS SystemNameBeneficiary,
		n.ResourceTypeId				AS ResourceTypeId,
		rt.Name							AS ResourceTypeName,
		n.Description					AS Description,
		n.StartDate						AS StartDate,
		n.EndDate						AS EndDate,
		i.IndividualId					AS IndividualId

    FROM
        NonProjectRelatedAgreement n
		LEFT JOIN Participant p   ON (n.ParticipantIdPayer = p.ParticipantId)		
		LEFT JOIN Participant p2  ON (n.ParticipantIdBeneficiary = p2.ParticipantId)
		LEFT JOIN ResourceType rt ON (n.ResourceTypeId = rt.ResourceTypeId)
		LEFT JOIN Individual i    ON (n.ParticipantIdBeneficiary = i.ParticipantId)
		
		

