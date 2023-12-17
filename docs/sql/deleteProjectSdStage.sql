CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectSdStage`(`pProjectSdStageId` Bigint)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ProjectSdStage has been linked to a PpSdRoleStage
    select count(*) into vCount from ig_db.PpSdRoleStage
    where ProjectSdStageId = pProjectSdStageId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Service Discipline Stage.  It has been linked to one or more Participant Roles.';
	end if;

	start transaction;
		delete from ig_db.ProjectSdStage where ProjectSdStageId = pProjectSdStageId;
	commit;

END