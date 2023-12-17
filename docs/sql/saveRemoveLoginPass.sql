CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveRemoveLoginPass`(
						pQuery JSON, 
						pUserId VARCHAR(50))
BEGIN

	declare vIndividualId bigint;
   
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
	
	set vIndividualId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualId'));
	
	start transaction;
	
    update ig_db.Individual 
		set 
			Pass = null, 
			LastUpdateTimestamp = current_timestamp(), 
			LastUpdateUserName = pUserId
		where IndividualId = vIndividualId;
        
	commit;

END