CREATE DEFINER=administrator@localhost PROCEDURE doMarkTaskAsComplete(pTaskId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
    declare vAssignmentId BIGINT;
	
    DECLARE done INT DEFAULT FALSE;

	declare exit handler for sqlexception    
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	start transaction;
		
        UPDATE Task t set Completed = 'Y' where t.TaskId = pTaskId;
        
        Select AssignmentId into vAssignmentId from Task tt where tt.TaskId = pTaskId;
        
        select count(*) into vCount from Task ttt where ttt.AssignmentId = vAssignmentId and ttt.Completed = 'N';
    
		IF vCount < 1 THEN 
			UPDATE Assignment a set Completed = 'Y' where a.AssignmentId = vAssignmentId;
		END IF;
   
	commit;

END