CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteVehicleModel`(`pVehicleModelId` INT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the VehicleModelId has Vehicles
    select count(*) into vCount from ig_db.Vehicle
    where VehicleModelId = pVehicleModelId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Vehicle Model.  The Vehicle Model has been used.';
	end if;

	start transaction;
		delete from ig_db.VehicleModel where VehicleModelId = pVehicleModelId;
	commit;

END