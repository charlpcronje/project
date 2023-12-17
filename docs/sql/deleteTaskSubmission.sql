CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteTaskSubmission`(IN `pTaskSubmissionId` BIGINT)
    NO SQL
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the TaskSubmission has ...
--    select count(*) into vCount from ig_db.TaskSubmission
--    where TaskSubmissionId = pTaskSubmissionId;
--    if (vCount > 0 ) then 
--		signal sqlstate '45000'
--		set message_text = 'Can not delete Task Submission as it has Submissions linked to it.';
--	end if;

	
	start transaction;
		delete from ig_db.TaskSubmission where  TaskSubmissionId = pTaskSubmissionId;
	commit;

END