CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveExpenseRate`(
									IN pExpenseRateId BIGINT, 
                                    IN pRecoverableExpenseId BIGINT, 
                                    IN pExpenseRatePerUnit DECIMAL(12,2), 
                                    IN pExpenseHandlingPerc DECIMAL(5,2), 
                                    IN pMaxExpenseAmtPerUnit DECIMAL(12,2), 
                                    IN pDescription VARCHAR(255),
                                    IN pStartDate DATETIME, 
                                    IN pEndDate DATETIME, 
                                    IN pUserId VARCHAR(50))
BEGIN
	-- Work variables
	declare vDayBefore datetime;
   	declare vOverlapCount int;
    declare vOpenEndedStartDate datetime;
   	declare vEndDateOk int;

    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
	
    -- set vDayBefore = DATE_ADD(pStartDate, INTERVAL -1 DAY); 
    -- SELECT DATE_ADD(day, -1, pStartDate) into vDayBefore;
	
    -- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that 
    -- database constraints won't pick up
	-- ************************************************
    
    -- ************************************************
    -- Write to tables
    -- ************************************************
	-- ig_db.ExpenseRate
    -- --------------------------
		-- Is it an insert?
		if isnull(pExpenseRateId) then 

			-- If pEndDate == null : Openended
            -- Overlaps:
				-- If there is another openended record.  This will be a problem
				-- If StartDate between r.StartDate and r.EndDate
                -- If StartDate > 
			if isnull(pEndDate) then  
				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ExpenseRate r
						WHERE
							r.RecoverableExpenseId = pRecoverableExpenseId
                            AND isnull(r.EndDate);
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please close other open ended date ranges and re-check Expense Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount 
						FROM ExpenseRate r  -- Check if StartDate in another range ... Thus less than any End Date, since this is an open ended entry
						WHERE
							r.RecoverableExpenseId = pRecoverableExpenseId
							AND pStartDate <= r.EndDate;

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Start date within other date range.  Please re-check Expense Rate Dates';
				end if;
		
			else -- EndDate has a value - so, not openended. 

				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ExpenseRate r
						WHERE
							r.RecoverableExpenseId = pRecoverableExpenseId
                            AND isnull(r.EndDate)
							AND (	(r.StartDate <= pStartDate) 
									OR (r.StartDate <= pEndDate));
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date range in open ended date range.  Please close other open ended date ranges and re-check Expense Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount -- No open date ranges
					FROM ExpenseRate r
					WHERE
						r.RecoverableExpenseId = pRecoverableExpenseId
						AND ((pStartDate <= r.StartDate) and (pEndDate >= r.EndDate) -- Straddles the range
						OR (pStartDate between r.StartDate and r.EndDate)
						OR (pEndDate between r.StartDate and r.EndDate));

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please review and re-check Expense Rate Dates';
				end if;
			end if;
            
		ELSE  -- This record needs to be updated
        
			-- If pEndDate == null : Openended
            -- Overlaps:
				-- If there is another openended record.  This will be a problem
				-- If StartDate between r.StartDate and r.EndDate
                -- If StartDate > 
			if isnull(pEndDate) then  
				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ExpenseRate r
						WHERE
							r.ExpenseRateId <> pExpenseRateId
							AND r.RecoverableExpenseId = pRecoverableExpenseId
                            AND isnull(r.EndDate);
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please close other open ended date ranges and re-check Expense Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount 
						FROM ExpenseRate r  -- Check if StartDate in another range ... Thus less than any End Date, since this is an open ended entry
						WHERE
							r.ExpenseRateId <> pExpenseRateId
							AND r.RecoverableExpenseId = pRecoverableExpenseId
							AND pStartDate <= r.EndDate;

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Start date within other date range.  Please re-check Expense Rate Dates';
				end if;
		
			else -- EndDate has a value - so, not openended. 

				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ExpenseRate r
						WHERE
							r.ExpenseRateId <> pExpenseRateId
							AND r.RecoverableExpenseId = pRecoverableExpenseId
                            AND isnull(r.EndDate)
							AND (	(r.StartDate <= pStartDate) 
									OR (r.StartDate <= pEndDate));
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date range in open ended date range.  Please close other open ended date ranges and re-check Expnense Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount -- No open date ranges
					FROM ExpenseRate r
					WHERE
						r.ExpenseRateId <> pExpenseRateId
						AND r.RecoverableExpenseId = pRecoverableExpenseId
						AND ((pStartDate <= r.StartDate) and (pEndDate >= r.EndDate) -- Straddles the range
						OR (pStartDate between r.StartDate and r.EndDate)
						OR (pEndDate between r.StartDate and r.EndDate));

					if (vOverlapCount > 0) then
						-- Give error message
						signal sqlstate '45000'
						set message_text = 'Date ranges overlap.  Please review and re-check Expense Rate Dates';
					end if;
			end if;
        END IF;

    start transaction;
    
		IF isnull(pExpenseRateId) THEN 
        -- Insert New record
			INSERT INTO ExpenseRate
            SET
				RecoverableExpenseId = pRecoverableExpenseId, 
				ExpenseHandlingPerc = pExpenseHandlingPerc, 
                MaxExpenseAmtPerUnit = pMaxExpenseAmtPerUnit,
				ExpenseRatePerUnit = pExpenseRatePerUnit, 
				Description = pDescription,
				StartDate = pStartDate,
                EndDate = pEndDate,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId;

			set pExpenseRateId = last_insert_id();
	
		ELSE 
			UPDATE ExpenseRate
            SET
				RecoverableExpenseId = pRecoverableExpenseId, 
				ExpenseHandlingPerc = pExpenseHandlingPerc, 
                MaxExpenseAmtPerUnit = pMaxExpenseAmtPerUnit,
				ExpenseRatePerUnit = pExpenseRatePerUnit, 
				Description = pDescription,
				StartDate = pStartDate,
                EndDate = pEndDate,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId
			where
				ExpenseRateId = pExpenseRateId;
		end if;

	commit;
END