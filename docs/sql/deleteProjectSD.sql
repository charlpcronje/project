CREATE DEFINER=root@localhost PROCEDURE deleteProjectSd(pProjectSdId bigint)
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	-- Check if there are any ProjectParticipants linked to this Project
    select count(*) into vCount from ig_db.ProjectParticipantSdRole
    where ProjectSdId = pProjectSdId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Service Discipline.  It has linked Project Participant(s).';
	end if;
    
	-- Check if there are any ProjectSdStage' linked to this ProjectStage
    /*
    select count(*) into vCount from ig_db.ProjectSdStage
    where ProjectSdId = pProjectSdId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete this record.  It is linked to a Stage.';
	end if;	
	*/
	start transaction;
		delete from ProjectSdStage where ProjectSdId = pProjectSdId;
		delete from ig_db.ProjectSd where projectSdId = pProjectSdId;
	commit;
END