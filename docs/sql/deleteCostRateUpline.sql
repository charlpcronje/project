CREATE DEFINER=administrator@localhost PROCEDURE deleteCostRateUpline(pCostRateUplineId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the CostRateUplineId has been linked to a Timesheet
    select count(*) into vCount from ig_db.Timesheet
    where CostRateUplineId = pCostRateUplineId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Cost Rate Upline.  The Rate is linked to a Timesheet entry';
	end if;

	start transaction;
		delete from ig_db.CostRateUpline where CostRateUplineId = pCostRateUplineId;
	commit;

END