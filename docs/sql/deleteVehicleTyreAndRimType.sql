CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteVehicleTyreAndRimType`(pVehicleTyreAndRimTypeId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the      has been used in VehicleTyreAndRim
    select count(*) into vCount from ig_db.VehicleTyreAndRim
    where VehicleTyreAndRimTypeId = pVehicleTyreAndRimTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete this Type.  It has been linked to one or more Vehicles.';
	end if;

	start transaction;
		delete from ig_db.VehicleTyreAndRimType where VehicleTyreAndRimTypeId = pVehicleTyreAndRimTypeId;
	commit;


END