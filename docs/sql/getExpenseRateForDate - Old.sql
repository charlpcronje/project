CREATE DEFINER = administrator@localhost 
FUNCTION getExpenseRateForDate
			(	pParticipantIdIndividual BIGINT, 
				pAgreementBetweenParticipantsId BIGINT, 
				pProjectParticipantIdForExpense BIGINT, 
				pRecoverableExpenseId BIGINT, 
				pDate DATETIME) 
			RETURNS decimal(12,2)
    NO SQL
BEGIN
	declare vExpenseRatePerUnit decimal(12,2);
	declare vNumberOfExpenseRates int;

    -- First check if there are any Rates for this Participant and ProjectParticipant
    select count(*) into vNumberOfExpenseRates
		from ExpenseRateUpline eru
		where eru.ParticipantIdIndividual = pParticipantIdIndividual
        and eru.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
        and eru.ProjectParticipantIdForExpense = pProjectParticipantIdForExpense
        and eru.RecoverableExpenseId = pRecoverableExpenseId;
		
    if vNumberOfExpenseRates = 0 then
		return -2;
	end if;	
	
    -- If the EndDate is null or after pDate, it is the current status
    -- But first make sure there are not more than one date range!
    select count(eru.ExpenseRateUplineId) into vNumberOfExpenseRates
    from 	ExpenseRateUpline eru
	where 	eru.ParticipantIdIndividual = pParticipantIdIndividual
        and eru.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
        and eru.ProjectParticipantIdForExpense = pProjectParticipantIdForExpense
            and eru.RecoverableExpenseId = pRecoverableExpenseId
			and ((eru.StartDate <= pDate) and ((eru.EndDate is null) or (eru.EndDate >= pDate)));

    if vNumberOfExpenseRates = 0 then
		return -2;
	end if;	
    if vNumberOfExpenseRates > 1 then
		-- return "Error: Rate Date ranges overlap";
		return -1;
	end if;	

    -- If the EndDate is null or after pDate, it is the current status
    select eru.ExpenseRatePerUnit into vExpenseRatePerUnit
    from 	ExpenseRateUpline eru
	where 	eru.ParticipantIdIndividual = pParticipantIdIndividual
        and eru.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
        and eru.ProjectParticipantIdForExpense = pProjectParticipantIdForExpense
            and eru.RecoverableExpenseId = pRecoverableExpenseId
			and ((eru.StartDate <= pDate) and ((eru.EndDate is null) or (eru.EndDate >= pDate)));

	if vExpenseRatePerUnit is not null then
		return vExpenseRatePerUnit;
	else
		return -3; -- Foutjie iewers!
	end if;

END