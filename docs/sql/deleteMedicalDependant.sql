create procedure deleteMedicalDependant(pMedicalDependantId bigint) 
	delete from MedicalDependant where MedicalDependantId = pMedicalDependantId;
    
