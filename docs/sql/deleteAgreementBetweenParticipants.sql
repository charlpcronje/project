CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAgreementBetweenParticipants`(`pAgreementBetweenParticipantsId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the AgreementBetweenParticipants has Recoverable Expense Agreements linked to it
    select count(*) into vCount from ig_db.RecoverableExpense
    where AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Agreement.  There are Recoverable Expense Agreements linked to it.';
	end if;

  	-- Check if the AgreementBetweenParticipants has Rates Upline linked to it
    -- select count(*) into vCount from ig_db.RatesUpline
    -- where AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId;
    -- if (vCount > 0 ) then 
	-- 	signal sqlstate '45000'
	-- 	set message_text = 'Can not delete Agreement.  There are Rates Upline linked to it.';
	-- end if;
	
	start transaction;
		delete from ig_db.AgreementBetweenParticipants where AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId;
	commit;

END