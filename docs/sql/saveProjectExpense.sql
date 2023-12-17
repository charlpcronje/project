-- drop procedure ig_db.saveProjectExpense;
 
delimiter $$$

create procedure ig_db.saveProjectExpense(in pCardNumber varchar(200), 
                                          in pProjectName varchar(200), 
                                          in pPurchaseDate date, 
                                          in pDescription varchar(4000), 
                                          in pAmountPerUnit double, 
                                          in pNumberOfUnits double, 
                                          in pNoteToAccountant varchar(4000), 
                                          in pExpenseType varchar(100),
										  in pPaymentMethodCode varchar(50),
										  in pUsername varchar(50), 
                                          out pProjectExpenseId bigint)
begin
	declare vBankCardId bigint default null;
	declare vParticipantId bigint;
	declare vProjectParticipantId bigint;
	declare vExpenseTypeId bigint;
	
	select 
	    e.ExpenseTypeId into vExpenseTypeId 
	  from 
	    ExpenseType e
	  where 
	    e.Name = pExpenseType; 
	
	select 
		bc.BankCardId into vBankCardId
	  from
	    BankCard bc
	  where 
	    bc.CardNumber = pCardNumber;
	
	
	select 
		pp.ProjectParticipantId into vProjectParticipantId
	  from 
		ProjectParticipant pp,
		Project p,
		Individual i
	  where 
		pp.ProjectId = p.ProjectId
		and pp.ParticipantId = i.ParticipantId
		and p.ProjectNameText = pProjectName
		and i.UserName = pUsername;
		
	select 
		i.ParticipantId into vParticipantId
	  from
	    Individual i
	  where
	    i.UserName = pUserName;
		
	-- insert
	insert into ig_db.ProjectExpense (
	  BankCardIdUsed, PaymentDescription, PurchaseDate, NumberOfUnits, AmountPerUnit, 
      NoteToAccountant, ExpenseTypeId, PaymentMethodCode,
	  ProjectParticipantIdPayer, ParticipantIdMadePurchase, 
      LastUpdateUserName, LastUpdateTimestamp
    ) values (
	  vBankCardId, pDescription, pPurchaseDate, pNumberOfUnits,pAmountPerUnit, 
      pNoteToAccountant, vExpenseTypeId, pPaymentMethodCode,
	  vProjectParticipantId, vParticipantId, 
      pUsername, current_timestamp()
	);
	
	-- set id into out variable
	set pProjectExpenseId = last_insert_id();
	
end;
$$$
