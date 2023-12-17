SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE

VIEW vBankTransactionLink AS
    SELECT
	
		btl.bankTransactionLinkId,		
		btl.bankTransactionId,			
		btl.invoiceId,					
		btl.projectExpenseId,			
		btl.participantIdApprovedLink,	
		btl.linkAmount,					
		btl.automaticCursorLinkCreated,	
		btl.linkApproved,				
		btl.description,				
	
		bt.participantBankDetailsId,	
		bt.bankStatementId,				
		bt.participantIdOnTransaction,
		bt.description1,             
		bt.description2,             
		bt.transactionDate,          
		bt.amount,                   
		bt.fullyLinked as fullyLinkedBt,              

		pe.participantIdMadePurchase, 		
		pe.participantIdVendor,      		
		pe.expenseTypeId,            		
		pe.assetId,                  		
		pe.paymentMethodCode,        		
		pe.bankCardIdUsed,           		
		pe.participantBankDetailsIdUsed, 	
		pe.taxDeductableCategoryId, 		
		pe.paymentDescription,       
		pe.purchaseDate,             
		pe.numberOfUnits,            
		pe.amountPerUnit,            
		           
		pe.noteToAccountant,         
		pe.fullyLinked as fullyLinkedPe,               
		pe.bankReference,

		p.systemName as approvedBy

    FROM
		BankTransactionLink	btl
        join BankTransaction bt on (btl.bankTransactionId 	= 	bt.bankTransactionId)
        left  join ProjectExpense	 pe on (btl.projectExpenseId	=	pe.projectExpenseId)
        left join Participant 	 p  on (btl.ParticipantIdApprovedLink = p.ParticipantId);
        
        