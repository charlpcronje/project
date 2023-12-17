DELIMITER $$$
CREATE PROCEDURE `deleteSoqTemplate`(IN `sTemplateId` BIGINT)
    NO SQL
BEGIN

    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
   	-- Check if the Participant is part of a Project
    select 	count(*) into vCount 
    from 	ig_db.SoqTemplate
    where 	soqTemplateId = sTemplateId;
 
	if (vCount > 0 ) then
		signal sqlstate '45000'
		set message_text = 'Can not delete Template.  It is linked to a Sub Schedule.';
	end if;

	start transaction;
		SET FOREIGN_KEY_CHECKS=0;
			
			delete from ig_db.SoqTemplate 
            where 	SoqTemplateId = sTemplateId;
			
		SET FOREIGN_KEY_CHECKS=1;    
	commit;

END
$$$