CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vStatement AS
    SELECT 
   		(ii.ParticipantIdTo * ii.InvoiceId) as RowNumber,
		"Invoice Received"  as TransactionType,
    	ii.InvoiceId as InvoiceId, 
        null as BankTransactionId,
        ii.ParticipantIdFrom as TheOtherParticipantId, 
        ii.ParticipantIdTo as TheParticipantId, 
        ii.InvoiceNumber as TheNumber, 
        (ii.InvoiceTotalAmountInclTax * -1) as TheAmount, 
        ii.InvoiceDate as TheDate, 
        ii.Description as TheDescription, 
        ii.FromParticipantName as TheOtherParticipant,
        ii.ToParticipantName as TheParticipant
    FROM
		Invoice ii 
UNION ALL
    SELECT 
		(ino.ParticipantIdFrom * ino.InvoiceId) as RowNumber,
		"Invoice Sent" as TransactionType,
    	ino.InvoiceId as InvoiceId, 
        null as BankTransactionId,
        ino.ParticipantIdTo as TheOtherParticipantId, 
        ino.ParticipantIdFrom as TheParticipantId, 
        ino.InvoiceNumber as TheNumber, 
        ino.InvoiceTotalAmountInclTax as TheAmount, 
        ino.InvoiceDate as TheDate, 
        ino.Description as TheDescription, 
        ino.ToParticipantName as TheOtherParticipant,
        ino.FromParticipantName as TheParticipant
    FROM
		Invoice ino 
UNION ALL
    SELECT 
		(pr.ParticipantIdOwner * pr.BankTransactionId) as RowNumber,
		"Bank Transaction" as TransactionType,
        null as InvoiceId,
        pr.BankTransactionId as BankTransactionId, 
        pr.ParticipantIdOnTransaction as TheOtherParticipantId, 
        pr.ParticipantIdOwner as TheParticipantId,
        pr.StatementNumber as TheNumber, 
        (pr.Amount  * -1) as TheAmount,
        pr.TransactionDate as TheDate,
        concat(pr.Description1, ' - ' , pr.Description2) as TheDescription,
        pr.LinkedParticipant as TheOtherParticipant,
        pr.OwnerSystemName as TheParticipant
    FROM
		vBankTransaction pr 
		WHERE pr.ParticipantIdOnTransaction is not null;
