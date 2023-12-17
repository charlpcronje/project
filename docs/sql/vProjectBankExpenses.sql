create or replace view ig_db.vProjectBankExpenses as
select 
	pe.ProjectExpenseId,
    pe.BankCardIdUsed,
    pe.PaymentDescription,
    pe.PurchaseDate,
    pe.NumberOfUnits,
    pe.AmountPerUnit,
    pe.NoteToAccountant,
    bc.BankCardId,
    bc.CardNumber,
    et.ExpenseTypeId,
    et.Name ExpenseTypeName,
    i.IndividualId,
    i.ParticipantId,
    p.Title ProjectTitle,
    i.UserName
  from 
    ProjectExpense pe,
    ExpenseType et,
    Individual i,
    BankCard bc,
    ProjectParticipant pp,
    Project p
  where
    et.expenseTypeId = pe.expenseTypeId
    and pe.ParticipantIdMadePurchase = i.ParticipantId
    and pp.ProjectParticipantId = pe.ProjectParticipantIdPayer
    and bc.BankCardId = pe.BankCardIdUsed
    and p.ProjectId = pp.ProjectId;
    
    

