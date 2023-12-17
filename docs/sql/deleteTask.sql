CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteTask`(IN `pTaskId` BIGINT)
    NO SQL
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the Task has Revisions
    select count(*) into vCount from ig_db.TaskRevision
    where TaskId = pTaskId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Task as it has Revisions linked to it.';
	end if;

	
	start transaction;
		delete from ig_db.Task where  TaskId = pTaskId;
	commit;

END