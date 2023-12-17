use ig_db;

drop procedure if exists saveMainMemberMedicalInsurance;

delimiter $$$
create procedure saveMainMemberMedicalInsurance(IN pIndividualId BIGINT, IN pMedicalInsuranceCompanyId BIGINT, 
									IN pMedicalInsurancePlanId BIGINT, IN pInsuranceNumber VARCHAR(50), 
                                    IN pDescription varchar(255), IN pUserId VARCHAR(50))
BEGIN
	-- Work variables
	declare vMainMemberMedicalInsuranceId bigint;
   	declare vOpenEndedStartDate datetime;
   	
    -- determine if this is an insert or update
	select MainMemberMedicalInsuranceId into vMainMemberMedicalInsuranceId from MainMemberMedicalInsurance where 
               		IndividualId = pIndividualId;
    
	if isnull(vMainMemberMedicalInsuranceId) then 
			insert into ig_db.MainMemberMedicalInsurance
					(IndividualId, MedicalInsuranceCompanyId, MedicalInsurancePlanId, InsuranceNumber, Description, LastUpdateTimestamp, LastUpdateUserName) 
                    values 
                    (pIndividualId, pMedicalInsuranceCompanyId, pMedicalInsurancePlanId, pInsuranceNumber, pDescription, current_timestamp, pUserId);
		else
			update ig_db.MainMemberMedicalInsurance
				set 
					MedicalInsuranceCompanyId = pMedicalInsuranceCompanyId,
					MedicalInsurancePlanId = pMedicalInsurancePlanId,
					InsuranceNumber = pInsuranceNumber,
					Description = pDescription,
					LastUpdateTimestamp = current_timestamp,
                    LastUpdateUserName = pUserId
                where 
					MainMemberMedicalInsuranceId = vMainMemberMedicalInsuranceId;                
	end if;
    
    commit;
END
$$$
