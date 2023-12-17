CREATE DEFINER=administrator@localhost PROCEDURE deleteTimesheet(IN pTimesheetId BIGINT)
    NO SQL
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the Timesheet has been linked to a report line in an Invoice
    -- select count(*) into vCount from ig_db.ReportLine
    -- where TimesheetId = pTimesheetId;
    -- if (vCount > 0 ) then 
-- 		signal sqlstate '45000'
		-- set message_text = 'Can not delete Timesheet entry as it is referenced in an Invoice Report.';
	-- end if;
	
	start transaction;
		delete from ig_db.Timesheet where  TimesheetId = pTimesheetId;
	commit;

END