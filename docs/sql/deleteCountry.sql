CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteCountry`(`pCountryId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the Country has been used in Individual
    select count(*) into vCount from ig_db.Individual
    where CountryId = pCountryId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Country.  The Country has been linked to an Individual.';
	end if;

	start transaction;
		delete from ig_db.Country where CountryId = pCountryId;
	commit;

END