CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteTaskRevision`(IN `pTaskRevisionId` BIGINT)
    NO SQL
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the TaskRevision has Submissions
    select count(*) into vCount from ig_db.TaskSubmission
    where TaskRevisionId = pTaskRevisionId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Task Revision as it has Submissions linked to it.';
	end if;

	
	start transaction;
		delete from ig_db.TaskRevision where  TaskRevisionId = pTaskRevisionId;
	commit;

END