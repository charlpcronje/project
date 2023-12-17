CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteIndividualSD`(IN `pIndividualSDId` BIGINT)
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
    
	-- Check if the IndividualSDId has been used in IndividualCompetenscy
    select count(*) into vCount from ig_db.IndividualCompetency
    where IndividualSDId = pIndividualSDId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual Service Discipline.  There are one or more Individual Competencies linked to it.';
	end if;
	start transaction;
	delete from ig_db.IndividualSD where IndividualSDId = pIndividualSDId;
	commit;
END