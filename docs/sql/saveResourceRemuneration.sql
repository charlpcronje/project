CREATE DEFINER=`administrator`@`localhost` PROCEDURE saveResourceRemuneration
							(IN pResourceRemunerationId BIGINT, 
                             IN pNonProjectRelatedAgreementId BIGINT, 
                             IN pResourceRemunTypeId bigint,
                             IN pDescription VARCHAR(255),
                             IN pStartDate DATETIME, 
                             IN pEndDate DATETIME, 
                             IN pAmount DECIMAL(12,2), 
                             IN pAllowExpenseDeductions VARCHAR(1), 
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
	-- ig_db.ResourceRemuneration
    -- --------------------------
		-- Is it an insert?
		if isnull(pResourceRemunerationId) then 

			-- If pEndDate == null : Openended
            -- Overlaps:
				-- If there is another openended record.  This will be a problem
				-- If StartDate between r.StartDate and r.EndDate
                -- If StartDate > 
			if isnull(pEndDate) then  
				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ResourceRemuneration r
						WHERE
							r.ResourceRemunTypeId = pResourceRemunTypeId
							AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
                            AND isnull(r.EndDate);
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount 
						FROM ResourceRemuneration r  -- Check if StartDate in another range ... Thus less than any End Date, since this is an open ended entry
						WHERE
							r.ResourceRemunTypeId = pResourceRemunTypeId
							AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
							AND pStartDate <= r.EndDate;

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Start date within other date range.  Please re-check Remuneration Rate Dates';
				end if;
		
			else -- EndDate has a value - so, not openended. 

				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ResourceRemuneration r
						WHERE
							r.ResourceRemunTypeId = pResourceRemunTypeId
							AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
                            AND isnull(r.EndDate)
							AND (	(r.StartDate <= pStartDate) 
									OR (r.StartDate <= pEndDate));
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date range in open ended date range.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount -- No open date ranges
					FROM ResourceRemuneration r
					WHERE
						r.ResourceRemunTypeId = pResourceRemunTypeId
						AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
						AND ((pStartDate <= r.StartDate) and (pEndDate >= r.EndDate) -- Straddles the range
						OR (pStartDate between r.StartDate and r.EndDate)
						OR (pEndDate between r.StartDate and r.EndDate));

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please review and re-check Remuneration Rate Dates';
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
						FROM ResourceRemuneration r
						WHERE
							r.ResourceRemunerationId <> pResourceRemunerationId
							AND r.ResourceRemunTypeId = pResourceRemunTypeId
							AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
                            AND isnull(r.EndDate);
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount 
						FROM ResourceRemuneration r  -- Check if StartDate in another range ... Thus less than any End Date, since this is an open ended entry
						WHERE
							r.ResourceRemunerationId <> pResourceRemunerationId
							AND r.ResourceRemunTypeId = pResourceRemunTypeId
							AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
							AND pStartDate <= r.EndDate;

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Start date within other date range.  Please re-check Remuneration Rate Dates';
				end if;
		
			else -- EndDate has a value - so, not openended. 

				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM ResourceRemuneration r
						WHERE
							r.ResourceRemunerationId <> pResourceRemunerationId
							AND r.ResourceRemunTypeId = pResourceRemunTypeId
							AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
                            AND isnull(r.EndDate)
							AND (	(r.StartDate <= pStartDate) 
									OR (r.StartDate <= pEndDate));
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date range in open ended date range.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount -- No open date ranges
					FROM ResourceRemuneration r
					WHERE
						r.ResourceRemunerationId <> pResourceRemunerationId
						AND r.ResourceRemunTypeId = pResourceRemunTypeId
						AND r.NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId
						AND ((pStartDate <= r.StartDate) and (pEndDate >= r.EndDate) -- Straddles the range
						OR (pStartDate between r.StartDate and r.EndDate)
						OR (pEndDate between r.StartDate and r.EndDate));

					if (vOverlapCount > 0) then
						-- Give error message
						signal sqlstate '45000'
						set message_text = 'Date ranges overlap.  Please review and re-check Remuneration Rate Dates';
					end if;
			end if;
        END IF;

    start transaction;
    
		IF isnull(pResourceRemunerationId) THEN 
        -- Insert New record
			INSERT INTO ResourceRemuneration 
            SET
				ResourceRemunTypeId = pResourceRemunTypeId,
				NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId,

				Description = pDescription,
				StartDate = pStartDate,
                EndDate = pEndDate,
				Amount = pAmount,
				AllowExpenseDeductions = pAllowExpenseDeductions,
				
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId;

			set pResourceRemunerationId = last_insert_id();
	
		ELSE 
			UPDATE ResourceRemuneration
            SET
				ResourceRemunTypeId = pResourceRemunTypeId,
				NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId,

				Description = pDescription,
				StartDate = pStartDate,
                EndDate = pEndDate,
				Amount = pAmount,
				AllowExpenseDeductions = pAllowExpenseDeductions,
				
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId
			where
				ResourceRemunerationId = pResourceRemunerationId;
		end if;

	commit;
END