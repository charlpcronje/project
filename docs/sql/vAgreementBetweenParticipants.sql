SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    SQL SECURITY DEFINER
VIEW vAgreementBetweenParticipants AS
    SELECT 
    	abp.AgreementBetweenParticipantsId AS AgreementBetweenParticipantsId,
        concat("Level ", pp.Level- 1,": ",payer.SystemName, " - ", ben.SystemName) as AgreementName,
    	abp.AgreementBudget,
        pp.ProjectId AS ProjectId,
        p.ProjectNameText AS ProjectName, -- Added later
        p.ProjectNumberText as ProjectNumberText, -- Added later
        p.Title as ProjectTitle, -- Added later
        pp.Level,
		abp.ProjectParticipantId AS ProjectParticipantId,
        payer.ParticipantId AS ParticipantIdPayer,
        
        payer.SystemName AS SystemNamePayer,
        
        ben.ParticipantId AS ParticipantIdBeneficiary,
        ben.SystemName AS SystemNameBeneficiary,

		abp.RemunerationModelCode AS RemunerationModelCode,
        rm.Name AS RemunerationModelName,
		-- abp.ResourceRemunerationIdOverride AS ResourceRemunerationIdOverride,
		abp.Description AS Description,
        abp.FSPreSplitContractingExpDeduct, 
        abp.FSPreSplitContractingThirdPDeduct, 
        abp.FSContractedExpensesAdded, 
        abp.FSPreSplitOtherPartInvoices
	FROM
        AgreementBetweenParticipants abp
        JOIN vPPTree pp ON (abp.ProjectParticipantId = pp.ProjectParticipantIdContracted)
        JOIN ProjectParticipant ppPayer ON (pp.ProjectParticipantIdContracting = ppPayer.ProjectParticipantId)
        JOIN Participant payer ON (ppPayer.ParticipantId = payer.ParticipantId)
        left join Individual payeri on (payer.ParticipantId = payeri.ParticipantId)
        
        JOIN Participant ben ON (pp.ParticipantIdContracted = ben.ParticipantId)
        left join Individual beni on (ben.ParticipantId = beni.ParticipantId)

        JOIN RemunerationModel rm ON (abp.RemunerationModelCode = rm.RemunerationModelCode)
        JOIN Project p on (pp.ProjectId = p.ProjectId)
	ORDER By
		ProjectId,
        Level
