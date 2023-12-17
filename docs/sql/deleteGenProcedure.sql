CREATE DEFINER=administrator@localhost PROCEDURE deleteGenProcedure(IN pGenProcedureId BIGINT)
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
    
	update ig_db.PLine	set PLineIdParent = NULL where GenProcedureId = pGenProcedureId;
	delete from ig_db.PLine     		where GenProcedureId = pGenProcedureId;
	delete from ig_db.ProcedureRelatedDocs   where GenProcedureId = pGenProcedureId;
	delete from ig_db.PersonResponsible     where GenProcedureId = pGenProcedureId;
	delete from ig_db.ProcedureDefinition   where GenProcedureId = pGenProcedureId;
	delete from ig_db.GenProcedure          where GenProcedureId = pGenProcedureId;
	commit;
END