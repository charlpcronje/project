CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteIndividualSdRole`(IN `pIndividualSdRoleId` VARCHAR(50))
    NO SQL
BEGIN

    DECLARE vCount integer default 0;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the IndividualSdRole has been used in IndividualCompetency
    select count(*) into vCount from IndividualCompetency
    where IndividualSdRoleId = pIndividualSdRoleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete IndividualSdRole.  The IndividualSdRole has been linked to IndividualCompetency.';
	end if;

	start transaction;
		delete from ig_db.IndividualSdRole where IndividualSdRoleId = pIndividualSdRoleId;
	commit;

END