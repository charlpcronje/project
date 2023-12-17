CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deletePersonResponsible`(IN `pPersonResponsibleId` BIGINT)
    NO SQL
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
     
  	-- Check if the BankId has Branches
    select count(*) into vCount from ig_db.PLine
    where PersonResponsibleId = pPersonResponsibleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete this Role as it has already been linked to Procedure Lines.';
	end if;

	start transaction;
		delete from ig_db.PersonResponsible where  PersonResponsibleId = pPersonResponsibleId;
	commit;

END