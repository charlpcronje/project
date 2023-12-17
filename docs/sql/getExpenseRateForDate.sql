CREATE DEFINER=`administrator`@`localhost` FUNCTION `getExpenseRateForDate`(
			pRecoverableExpenseId BIGINT, 
            pDate DATETIME, 
            pAmountPerUnit DECIMAL(12,2)) RETURNS decimal(12,2)
    NO SQL
BEGIN
	declare vExpenseRatePerUnit decimal(12,2);
	declare vExpenseHandlingPerc decimal(12,2);
	declare vMaxExpenseAmtPerUnit decimal(12,2);

	declare vNumberOfExpenseRates int;

	-- Are there even an agreement setup?
	if pRecoverableExpenseId is null then
			return -4;		-- No expense agreement setup
	end if;

    -- First check if there are any Rates 
    select count(*) into vNumberOfExpenseRates
		from ExpenseRate er
		where 
				er.RecoverableExpenseId = pRecoverableExpenseId;
		
    if vNumberOfExpenseRates = 0 then
		return -2;
	end if;	
	
    -- If the EndDate is null or after pDate, it is the current status
    -- But first make sure there are not more than one date range!
    select count(*) into vNumberOfExpenseRates
		from ExpenseRate er
		where 		er.RecoverableExpenseId = pRecoverableExpenseId
				and ((er.StartDate <= pDate) and ((er.EndDate is null) or (er.EndDate >= pDate)));

    if vNumberOfExpenseRates = 0 then
		return -2;
	end if;	
    if vNumberOfExpenseRates > 1 then
		-- return "Error: Rate Date ranges overlap";
		return -1;
	end if;	

    -- If the EndDate is null or after pDate, it is the current status
    select  er.ExpenseRatePerUnit into vExpenseRatePerUnit
    from 	ExpenseRate er
	where 	er.RecoverableExpenseId = pRecoverableExpenseId
			and ((er.StartDate <= pDate) and ((er.EndDate is null) or (er.EndDate >= pDate)));

    select  er.ExpenseHandlingPerc into vExpenseHandlingPerc
    from 	ExpenseRate er
	where 	er.RecoverableExpenseId = pRecoverableExpenseId
			and ((er.StartDate <= pDate) and ((er.EndDate is null) or (er.EndDate >= pDate)));

    select  er.MaxExpenseAmtPerUnit into vMaxExpenseAmtPerUnit
    from 	ExpenseRate er
	where 	er.RecoverableExpenseId = pRecoverableExpenseId
			and ((er.StartDate <= pDate) and ((er.EndDate is null) or (er.EndDate >= pDate)));

 	if pAmountPerUnit is not null then
 		if pAmountPerUnit > vMaxExpenseAmtPerUnit then  -- The recoverable amount is the max amount specified
 			select vMaxExpenseAmtPerUnit into vExpenseRatePerUnit;
 		else 
 			select pAmountPerUnit into vExpenseRatePerUnit;
 		end if;

		if vExpenseHandlingPerc is not null then -- Add handling percentage to the amount paid
			select (vExpenseRatePerUnit +  (vExpenseRatePerUnit * (vExpenseHandlingPerc/100))) into vExpenseRatePerUnit;
 		end if;
 	end if;
    
	return vExpenseRatePerUnit;

END