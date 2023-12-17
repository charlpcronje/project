CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteIndividualCompetency`(IN `pIndividualCompetencyId` BIGINT)
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

	start transaction;
	delete from ig_db.IndividualCompetency where IndividualCompetencyId = pIndividualCompetencyId;
	commit;
END