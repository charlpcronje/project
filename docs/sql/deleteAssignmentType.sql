CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAssignmentType`(`pAssignmentTypeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the AssignmentType has TaskTypes
    select count(*) into vCount from ig_db.TaskType
    where AssignmentTypeId = pAssignmentTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Assignment Type.  The Assignment Type has Task Types linked to it.';
	end if;


	start transaction;
		delete from ig_db.AssignmentType where AssignmentTypeId = pAssignmentTypeId;
	commit;

END