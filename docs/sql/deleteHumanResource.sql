CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteHumanResource`(`pHumanResourceId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if there are any ResourceRemunerations linked to the Human Resource
     select count(*) into vCount from ig_db.ResourceRemuneration
	 where HumanResourceId = pHumanResourceId;
     if (vCount > 0 ) then 
	 	signal sqlstate '45000'
	 	set message_text = 'Can not delete Human Resource.  There are Remunerations linked to it';
	 end if;
	
	start transaction;
		delete from ig_db.HumanResource where HumanResourceId = pHumanResourceId;
	commit;

END