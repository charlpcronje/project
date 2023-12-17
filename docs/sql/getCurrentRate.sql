CREATE DEFINER=`administrator`@`localhost` FUNCTION `getRateCurrent`(pParticipantIdIndividual BIGINT, pProjectParticipantId BIGINT) RETURNS decimal(10,0)
    NO SQL
BEGIN
	declare vRatePerUnit decimal;
	declare vNumberOfRates int;

    -- First check if there are any Rates for this Participant and ProjectParticipant
    select count(*) into vNumberOfRates
		from CostRateUpline cru
		where cru.ParticipantIdIndividual = pParticipantIdIndividual
        and cru.ProjectParticipantId = pProjectParticipantId;
		
    if vNumberOfRates = 0 then
		return 0;
	end if;	
	
    -- If the EndDate is null or after now(), it is the current status
    -- But first make sure there are not more than one date range!
    select count(cru.CostRateUplineId) into vNumberOfRates
    from 	CostRateUpline cru
	where 	cru.ParticipantIdIndividual = pParticipantIdIndividual
			and cru.ProjectParticipantId = pProjectParticipantId
			and ((cru.StartDate <= now()) and ((cru.EndDate is null) or (cru.EndDate >= now())));

    if vNumberOfRates = 0 then
		return 0;
	end if;	
    if vNumberOfRates > 1 then
		-- return "Error: Rate Date ranges overlap";
		return -1;
	end if;	

    -- If the EndDate is null or after now(), it is the current status
    select cru.RatePerUnit into vRatePerUnit
    from 	CostRateUpline cru
	where 	cru.ParticipantIdIndividual = pParticipantIdIndividual
			and cru.ProjectParticipantId = pProjectParticipantId
			and ((cru.StartDate <= now()) and ((cru.EndDate is null) or (cru.EndDate >= now())));

	if vRatePerUnit is not null then
		return vRatePerUnit;
	end if;

END