SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = root@localhost 
    SQL SECURITY DEFINER
VIEW vHumanResourceUnionList AS
    SELECT  n.participantIdPayer 		as participantIdPayer,
			n.participantIdBeneficiary 	as participantIdBeneficiary, 
			 p.systemName 				as name
	FROM   NonProjectRelatedAgreement n
	JOIN   Participant p
	ON 		n.participantIdBeneficiary = p.participantId
	UNION
    SELECT	DISTINCT
			 nn.participantIdPayer 		as participantIdPayer, 
			 nn.participantIdPayer 		as participantIdBeneficiary, 
			 pp.systemName 				as name
	FROM   NonProjectRelatedAgreement nn
	JOIN   Participant pp
	ON 		nn.participantIdPayer = pp.participantId  
	WHERE  pp.isIndividual = 'Y'
        