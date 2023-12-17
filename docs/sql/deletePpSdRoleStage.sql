CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deletePpSdRoleStage`(`pPpSdRoleStageId` bigint)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ParticipantType has been linked to Participants
    select count(*) into vCount from ig_db.Timesheet
    where PpSdRoleStageId = pPpSdRoleStageId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Service Discipline Stage Link.  It has one or more Timesheet entries linked to it';
	end if;
	
	start transaction;
		delete from ig_db.PpSdRoleStage where PpSdRoleStageId = pPpSdRoleStageId;
	commit;

END