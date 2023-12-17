CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteResourceRelationship`(`pResourceRelationshipId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

    -- Check if there are any Remunerations linked to this Resource Relationship
    select count(*) into vCount from ig_db.ResourceRemuneration
    where ResourceRelationshipId = pResourceRelationshipId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Resource.  There are Remunerations linked to it';
	end if;
	
	start transaction;
		delete from ig_db.ResourceRelationship where ResourceRelationshipId = pResourceRelationshipId;
	commit;

END