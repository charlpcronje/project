CREATE DEFINER=administrator@localhost FUNCTION getCostRateUplineIdForDate(pParticipantIdIndividual BIGINT, pProjectParticipantId BIGINT, pDate DATETIME) RETURNS BIGINT
    NO SQL
BEGIN
	declare vCostRateUplineId bigint;
	declare vNumberOfRates int;

    -- First check if there are any Rates for this Participant and ProjectParticipant
    select count(*) into vNumberOfRates
		from CostRateUpline cru
		where cru.ParticipantIdIndividual = pParticipantIdIndividual
        and cru.ProjectParticipantId = pProjectParticipantId;
		
    if vNumberOfRates = 0 then
		return null;
	end if;	
	
    -- If the EndDate is null or after pDate, it is the current status
    -- But first make sure there are not more than one date range!
    select count(cru.CostRateUplineId) into vNumberOfRates
    from 	CostRateUpline cru
	where 	cru.ParticipantIdIndividual = pParticipantIdIndividual
			and cru.ProjectParticipantId = pProjectParticipantId
			and ((cru.StartDate <= pDate) and ((cru.EndDate is null) or (cru.EndDate >= pDate)));

    if vNumberOfRates = 0 then
		return null;
	end if;	
    if vNumberOfRates > 1 then
		-- return "Error: Rate Date ranges overlap";
		return null;
	end if;	

    -- If the EndDate is null or after pDate, it is the current status
    select cru.CostRateUplineId into vCostRateUplineId
    from 	CostRateUpline cru
	where 	cru.ParticipantIdIndividual = pParticipantIdIndividual
			and cru.ProjectParticipantId = pProjectParticipantId
			and ((cru.StartDate <= pDate) and ((cru.EndDate is null) or (cru.EndDate >= pDate)));

	if vCostRateUplineId is not null then
		return vCostRateUplineId;
	end if;

END