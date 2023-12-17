CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteSdRole`(`pSdRoleId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the SdRole has been used in 
    select count(*) into vCount from ig_db.IndividualSdRole
    where SdRoleId = pSdRoleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Role.  The Role has been used.';
	end if;
	
  	-- Check if the SdRole has been used in 
    select count(*) into vCount from ig_db.ProjectParticipantSdRole
    where SdRoleId = pSdRoleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Role.  The Role has been used.';
	end if;	

	start transaction;
		delete from ig_db.SdRole where SdRoleId = pSdRoleId;
	commit;

END