CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteCompetencyLevel`(IN `pCompetencyLevelId` BIGINT)
    NO SQL
BEGIN

    DECLARE vCount integer default 0;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	start transaction;
		delete from ig_db.CompetencyLevel where CompetencyLevelId = pCompetencyLevelId;
	commit;

END