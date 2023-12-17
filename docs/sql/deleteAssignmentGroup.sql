CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAssignmentGroup`(`pAssignmentGroupId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the AssignmentGroupId has been used in Assignment
    select count(*) into vCount from ig_db.Assignment
    where AssignmentGroupId = pAssignmentGroupId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Assignment Group.  The Assignment Group has been linked to Assignments.';
	end if;

	start transaction;
		delete from ig_db.AssignmentGroup where AssignmentGroupId = pAssignmentGroupId;
	commit;

END