CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAssignment`(IN `pAssignmentId` BIGINT)
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
    select count(*) into vCount from ig_db.Task
    where AssignmentId = pAssignmentId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Assignment as it has Tasks linked to it.';
	end if;

	
	start transaction;
		delete from ig_db.Assignment where  AssignmentId = pAssignmentId;
	commit;

END