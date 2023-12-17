CREATE DEFINER=administrator@localhost PROCEDURE deleteTaskImportanceType(pTaskImportanceTypeId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the TaskImportanceType has been used in Tasks
    select count(*) into vCount from ig_db.Task
    where TaskImportanceTypeId = pTaskImportanceTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Task Importance Type.  It has been linked to one or more Tasks';
	end if;

	start transaction;
		delete from ig_db.TaskImportanceType where TaskImportanceTypeId = pTaskImportanceTypeId;
	commit;


END