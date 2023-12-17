SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vBankCard AS
	SELECT 
    bc.BankCardId, 
    bc.ParticipantBankDetailsId, 
    bc.IndividualIdMainCardUser, 
    bc.CardTypeId, 
    bc.CardNumber, 
    bc.NameOnCard, 
    bc.PersonalCard, 
    bc.Description,
    
    Concat(i.NickName, " " , i.LastName) as MainCardUser,
    ct.Name as CardTypeId_Name
    
	FROM 
		BankCard bc 
        JOIN CardType ct 	on 		bc.CardTypeId = ct.CardTypeId
        LEFT JOIN Individual i on 	bc.IndividualIdMainCardUser = i.IndividualId;
