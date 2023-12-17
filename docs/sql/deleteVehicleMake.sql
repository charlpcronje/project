CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteVehicleMake`(`pVehicleMakeId` INT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the VehicleMakeId has VehicleModeles
    select count(*) into vCount from ig_db.VehicleModel
    where VehicleMakeId = pVehicleMakeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Vehicle Make.  The Vehicle Make has Vehicle Models linked to it.';
	end if;

 
	
	start transaction;
		delete from ig_db.VehicleMake where VehicleMakeId = pVehicleMakeId;
	commit;

END