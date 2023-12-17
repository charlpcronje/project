CREATE DEFINER=`administrator`@`localhost` PROCEDURE saveRemunerationRateUpline
							(IN pRemunerationRateUplineId BIGINT, 
                             IN pAgreementBetweenParticipantsId BIGINT, 
                             IN pProjectParticipantSdRoleIdForRate BIGINT, 
                             IN pParticipantIdIndividual BIGINT, 
                             IN pProjBasedRemunTypeId bigint,
                             IN pRatePerUnit DECIMAL(12,2), 
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
	-- ig_db.RemunerationRateUpline
    -- --------------------------
		-- Is it an insert?
		if isnull(pRemunerationRateUplineId) then 

			-- If pEndDate == null : Openended
            -- Overlaps:
				-- If there is another openended record.  This will be a problem
				-- If StartDate between r.StartDate and r.EndDate
                -- If StartDate > 
			if isnull(pEndDate) then  
				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM RemunerationRateUpline r
						WHERE
							r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
							AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
							-- AND r.ProjectParticipantIdForExpense = pProjectParticipantIdForExpense
							AND r.ParticipantIdIndividual = pParticipantIdIndividual
							AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
                            AND isnull(r.EndDate);
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount 
						FROM RemunerationRateUpline r  -- Check if StartDate in another range ... Thus less than any End Date, since this is an open ended entry
						WHERE
							r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
							AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
							AND r.ParticipantIdIndividual = pParticipantIdIndividual
							AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
							AND pStartDate <= r.EndDate;

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Start date within other date range.  Please re-check Remuneration Rate Dates';
				end if;
		
			else -- EndDate has a value - so, not openended. 

				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM RemunerationRateUpline r
						WHERE
							r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
							AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
							AND r.ParticipantIdIndividual = pParticipantIdIndividual
							AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
                            AND isnull(r.EndDate)
							AND (	(r.StartDate <= pStartDate) 
									OR (r.StartDate <= pEndDate));
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date range in open ended date range.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount -- No open date ranges
					FROM RemunerationRateUpline r
					WHERE
						r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
						AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
						AND r.ParticipantIdIndividual = pParticipantIdIndividual
						AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
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
						FROM RemunerationRateUpline r
						WHERE
							r.RemunerationRateUplineId <> pRemunerationRateUplineId
							AND r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
							AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
							AND r.ParticipantIdIndividual = pParticipantIdIndividual
							AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
                            AND isnull(r.EndDate);
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount 
						FROM RemunerationRateUpline r  -- Check if StartDate in another range ... Thus less than any End Date, since this is an open ended entry
						WHERE
							r.RemunerationRateUplineId <> pRemunerationRateUplineId
							AND r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
							AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
							AND r.ParticipantIdIndividual = pParticipantIdIndividual
							AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
							AND pStartDate <= r.EndDate;

				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date ranges overlap.  Start date within other date range.  Please re-check Remuneration Rate Dates';
				end if;
		
			else -- EndDate has a value - so, not openended. 

				SELECT COUNT(1) INTO vOverlapCount -- Check for other openended date ranges
						FROM RemunerationRateUpline r
						WHERE
							r.RemunerationRateUplineId <> pRemunerationRateUplineId
							AND r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
							AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
							AND r.ParticipantIdIndividual = pParticipantIdIndividual
							AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
                            AND isnull(r.EndDate)
							AND (	(r.StartDate <= pStartDate) 
									OR (r.StartDate <= pEndDate));
                            
				if (vOverlapCount > 0) then
					-- Give error message
					signal sqlstate '45000'
					set message_text = 'Date range in open ended date range.  Please close other open ended date ranges and re-check Remuneration Rate Dates';
				end if;

				SELECT COUNT(1) INTO vOverlapCount -- No open date ranges
					FROM RemunerationRateUpline r
					WHERE
						r.RemunerationRateUplineId <> pRemunerationRateUplineId
						AND r.AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId
						AND r.ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate
						AND r.ParticipantIdIndividual = pParticipantIdIndividual
						AND r.ProjBasedRemunTypeId= pProjBasedRemunTypeId
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
    
		IF isnull(pRemunerationRateUplineId) THEN 
        -- Insert New record
			INSERT INTO RemunerationRateUpline 
            SET
				AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId,
				ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate,
				ParticipantIdIndividual = pParticipantIdIndividual,
				ProjBasedRemunTypeId = pProjBasedRemunTypeId,
				RatePerUnit = pRatePerUnit, 
				Description = pDescription,
				StartDate = pStartDate,
                EndDate = pEndDate,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId;

			set pRemunerationRateUplineId = last_insert_id();
	
		ELSE 
			UPDATE RemunerationRateUpline
            SET
				AgreementBetweenParticipantsId = pAgreementBetweenParticipantsId,
				ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleIdForRate,
				ParticipantIdIndividual = pParticipantIdIndividual,
				ProjBasedRemunTypeId = pProjBasedRemunTypeId,
				RatePerUnit = pRatePerUnit, 
				Description = pDescription,
				StartDate = pStartDate,
                EndDate = pEndDate,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId
			where
				RemunerationRateUplineId = pRemunerationRateUplineId;
		end if;

	commit;
END