CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjBasedRemunType`(`pProjBasedRemunTypeId` VARCHAR(50))
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if there are any associated AgreementBetweenParticipants
    select count(*) into vCount from ig_db.AgreementBetweenParticipants
    where ProjBasedRemunTypeId = pProjBasedRemunType;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Based Remuneration Type.  It has associated Agreement between Participants.';
	end if;
	start transaction;
		delete from ig_db.ProjBasedRemunType where ProjBasedRemunTypeId = pProjBasedRemunType;
	commit;

END