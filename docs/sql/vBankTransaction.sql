SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vBankTransaction AS
	SELECT 
    
		bt.BankTransactionId, 
        bt.ParticipantBankDetailsId, 
        bt.BankStatementId, 
        bt.Description1, 
        bt.Description2, 
        bt.TransactionDate, 
        bt.Amount, 
    
		pbt.ParticipantId as ParticipantIdOwner,
		pbt.SystemName as OwnerSystemName,
        bt.ParticipantIdOnTransaction, 
		pot.SystemName as LinkedParticipant,
        bs.StatementNumber,
		
		bt.FullyLinked
	FROM 
		BankTransaction bt 
        JOIN ParticipantBankDetails pbd 	on 		bt.ParticipantBankDetailsId = pbd.ParticipantBankDetailsId
        JOIN Participant pbt 				on 		pbd.ParticipantIdOwner = pbt.ParticipantId
		LEFT JOIN Participant pot 			ON 		bt.ParticipantIdOnTransaction = pot.participantId 
		LEFT JOIN BankStatement  bs			ON		bt.BankStatementId = bs.BankStatementId;
