SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    SQL SECURITY DEFINER
VIEW vParticipantBankDetails AS


	SELECT 
		pbd.ParticipantBankDetailsId, 
		pbd.ParticipantIdOwner, 
		pbd.BankId, 
        b.BankCode,
		b.Name AS BankName, 
		pbd.AccountTypeId, 
		ac.Name AS AccountName, 
		pbd.BranchId, 
		br.Name AS BranchName,
		pbd.Name AS BankDetailsName, 
		pbd.Description, 
		pbd.AccountHolderName, 
		pbd.AccountNumber, 
		(CASE WHEN pbd.ParticipantBankDetailsId = p.ParticipantBankDetailsIdDefault THEN 'Y' ELSE 'N' END) as FlagDefault,
        concat(pbd.AccountHolderName, " - ", pbd.AccountNumber, " (" , b.Name , ")" ) as AccountDetails
	FROM 
		ParticipantBankDetails pbd 
		LEFT JOIN Participant p 
		ON    	pbd.ParticipantIdOwner = p.ParticipantId 
		LEFT JOIN Bank b
		ON		pbd.BankId = b.BankId
		LEFT JOIN AccountType ac
		ON		pbd.AccountTypeId = ac.AccountTypeId
		LEFT JOIN Branch br
		ON		pbd.BranchId = br.BranchId
