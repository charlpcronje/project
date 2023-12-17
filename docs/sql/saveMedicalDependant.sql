delimiter $$$

create procedure ig_db.saveMedicalDependant(
                                in pMedicalDependantId bigint,
                                in pMainMemberMedicalInsuranceId bigint,
                                in pIndividualIdDependant bigint,
                                in pDescription varchar(500),
                                in pUsername varchar(50)
              )
begin
	if ((pMedicalDependantId is null) or 
			(pMedicalDependantId = '') or
			(pMedicalDependantId = -1)) then 
		-- perform insert
        insert 
			into 
			  MedicalDependant
              (MainMemberMedicalInsuranceId, IndividualIdDependant, Description, LastUpdateTimestamp, LastUpdateUsername)
			values 
              (pMainMemberMedicalInsuranceId, pIndividualIdDependant, pDescription, current_timestamp, pUsername);
	else
		update 
			  MedicalDepdentant 
            set
			  IndividualIdDependant = pIndividualIdDependant,
              Description = pDescription,
              LastUpdateTimestamp = current_timestamp,
              LastUpdateUsername = pUsername
			where
			  medicalDependantId = pMedicalDependantId;
    end if;
    
end
$$$
