CREATE DEFINER=administrator@localhost PROCEDURE deleteRemunerationRateUpline(pRemunerationRateUplineId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
--  	-- Check if the RemunerationRateUpline has been linked to any Timesheet entries
--    select count(*) into vCount from ig_db.Timesheet
--    where RemunerationRateUplineId = pRemunerationRateUplineId;
--    if (vCount > 0 ) then 
-- 		signal sqlstate '45000'
-- 		set message_text = 'Can not delete Remuneration Rate.  It is linked to one or more Timesheet entries.';
-- 	end if;

	start transaction;
		delete from ig_db.RemunerationRateUpline where RemunerationRateUplineId = pRemunerationRateUplineId;
	commit;

END