CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteCardType`(`pCardTypeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the CardType has been linked to a BankCard
    select count(*) into vCount from ig_db.BankCard
    where CardTypeId = pCardTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Card Type.  The Card Type has been linked to one or more Bank Cards.';
	end if;

	start transaction;
		delete from ig_db.CardType where CardTypeId = pCardTypeId;
	commit;

END