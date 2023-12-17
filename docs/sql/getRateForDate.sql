CREATE DEFINER=`root`@`localhost` FUNCTION `getRateForDate`(
				pProjectParticipantSdRoleIdForRate BIGINT, 
				pParticipantIdIndividual BIGINT, 
                pAgreementBetweenParticipantsId BIGINT, 
                pProjBasedRemunTypeId bigint, 
                pDate DATE) RETURNS decimal(10,2)
    NO SQL
BEGIN
	declare vRatePerUnit decimal;
	declare vNumberOfRates int;

    -- First check if there are any Rates for this Participant and ProjectParticipant
    select count(*) into vNumberOfRates
		from RemunerationRateUpline cru
		where cru.ParticipantIdIndividual = pParticipantIdIndividual
        and cru.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
        and cru.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
        and cru.ProjBasedRemunTypeId = pProjBasedRemunTypeId;
		
    if vNumberOfRates = 0 then
		return -2;
	end if;	
	
    -- If the EndDate is null or after pDate, it is the current status
    -- But first make sure there are not more than one date range!
    select count(cru.RemunerationRateUplineId) into vNumberOfRates
    from 	RemunerationRateUpline cru
	where 	cru.ParticipantIdIndividual = pParticipantIdIndividual
        and cru.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
        and cru.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
            and cru.ProjBasedRemunTypeId = pProjBasedRemunTypeId
			and ((cru.StartDate <= pDate) and ((cru.EndDate is null) or (cru.EndDate >= pDate)));

    if vNumberOfRates = 0 then
		return -2;
	end if;	
    if vNumberOfRates > 1 then
		-- return "Error: Rate Date ranges overlap";
		return -1;
	end if;	

    -- If the EndDate is null or after pDate, it is the current status
    select cru.RatePerUnit into vRatePerUnit
    from 	RemunerationRateUpline cru
	where 	cru.ParticipantIdIndividual = pParticipantIdIndividual
        and cru.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
        and cru.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
            and cru.ProjBasedRemunTypeId = pProjBasedRemunTypeId
			and ((cru.StartDate <= pDate) and ((cru.EndDate is null) or (cru.EndDate >= pDate)));

	if vRatePerUnit is not null then
		return vRatePerUnit;
	end if;

END