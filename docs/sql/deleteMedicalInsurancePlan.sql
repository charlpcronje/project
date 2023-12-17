CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteMedicalInsurancePlan`(`pMedicalInsurancePlanId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	select vCount = 0;
  	-- Check if the Country has been used in Individual
    -- select count(*) into vCount from ig_db.Individual where medicalInsurancePlan = pMedicalInsurancePlanId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Cannot delete the Medical Insurance Plan.  The Medical Insurance Plan has been linked to a ??.';
	end if;

	start transaction;
		delete from ig_db.MedicalInsurancePlan where MedicalInsurancePlanId = pMedicalInsurancePlanId;
	commit;

END
