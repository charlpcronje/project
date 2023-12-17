CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveLoginPass`(
						pQuery JSON, 
						pUserId VARCHAR(50))
BEGIN

	declare vIndividualId bigint;
	declare vPass varchar(255);
   
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
	
	set vIndividualId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualId'));
	set vPass = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.pass'));
	
	start transaction;
	
    update ig_db.Individual 
		set 
			Pass = vPass, 
			LastUpdateTimestamp = current_timestamp(), 
			LastUpdateUserName = pUserId
		where IndividualId = vIndividualId;
        
	commit;

END