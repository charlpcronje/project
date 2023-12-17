CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteActivity`(IN `pActivityId` BIGINT)
    NO SQL
BEGIN

    DECLARE vCount integer default 0;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the Activity has been used in Timesheet
    select count(*) into vCount from Timesheet
    where ActivityId = pActivityId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Activity.  The Activity has been linked to Timesheet.';
	end if;

	start transaction;
		delete from ig_db.Activity where ActivityId = pActivityId;
	commit;

END