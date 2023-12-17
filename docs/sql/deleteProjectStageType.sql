CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectStageType`(IN `pProjectStageTypeId` BIGINT)
    NO SQL
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	-- Check if there are any ProjectStages linked to this ProjectStageType
--    select count(*) into vCount from ig_db.ProjectStage
--    where ProjectStageTypeId = pProjectStageTypeId;
--    if (vCount > 0 ) then 
--		signal sqlstate '45000'
--		set message_text = 'Can not delete Project Stage Type.  It has associated to Project Stages.';
--	  end if;

	start transaction;
	delete from ig_db.ProjectStageType where ProjectStageTypeId = pProjectStageTypeId;
	commit;
END