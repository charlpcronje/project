CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteDefinition`(pDefinitionId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the      has been used in Procedure
    select count(*) into vCount from ig_db.ProcedureDefinition
    where DefinitionId = pDefinitionId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Definition.  The Definition has been linked to one or more Procedures.';
	end if;

	start transaction;
		delete from ig_db.Definition where DefinitionId = pDefinitionId;
	commit;

END