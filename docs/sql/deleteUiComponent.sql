CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteUiComponent`(`pUiComponentId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the UiComponent is linked to RemunTypeNonProj
--    select count(*) into vCount from ig_db.RemunTypeNonProj
--    where UiComponentId = pUiComponentId;
--    if (vCount > 0 ) then 
--		signal sqlstate '45000'
--		set message_text = 'Can not delete Unit Type.  It has associated Non Project Rumuneration Types.';
--	end if;


	start transaction;
		delete from ig_db.UiComponent where UiComponentId = pUiComponentId;
	commit;

END