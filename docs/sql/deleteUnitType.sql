CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteUnitType`(`pUnitTypeCode` VARCHAR(50))
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

  	-- Check if the UnitType is linked to ResourceRemunType
    select count(*) into vCount from ig_db.ResourceRemunType
    where UnitTypeCode = pUnitTypeCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Unit Type.  It has associated Resource Remun Type.';
	end if;

  	-- Check if the UnitType is linked to ExpenseType
    select count(*) into vCount from ig_db.ExpenseType
    where UnitTypeCode = pUnitTypeCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Unit Type.  It has associated Expense Types.';
	end if;


 	-- Check if the UnitType is linked to ProjBasedRemunType
     select count(*) into vCount from ig_db.ProjBasedRemunType
    where UnitTypeCode = pUnitTypeCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Unit Type.  It has associated Project Based Remuneration Types.';
	end if;

	start transaction;
		delete from ig_db.UnitType where UnitTypeCode = pUnitTypeCode;
	commit;

END