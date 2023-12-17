CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteContactPoint`(`pContactPointId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ContactPointId is set as the default Contact Point for the Particpant Office
    select count(*) into vCount from ig_db.ParticipantOffice
    where ContactPointIdDefault = pContactPointId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Contact Point.  The Contact Point is set as the Default for an Office';
	end if;

	start transaction;
		delete from ig_db.ContactPoint where ContactPointId = pContactPointId;
	commit;

END