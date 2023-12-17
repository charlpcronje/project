CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveParticipantBankDetails`(pQuery JSON, pUserId VARCHAR(50))
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    
    declare vParticipantBankDetailsId bigint default null;
    declare vParticipantIdOwner bigint;
    declare vBankId bigint;
    declare vAccountTypeId bigint;
    declare vBranchId bigint;
    declare vFlagDefault varchar(1);
    declare vName varchar(50);
    declare vDescription varchar(255) default null;
    declare vAccountHolderName varchar(255);
    declare vAccountNumber varchar(255);
    
	-- -- Local variables
    -- Work variables
	declare tmp varchar(50);
    declare vCount integer ;
    declare vCountDefault integer ;
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantBankDetailsId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vParticipantBankDetailsId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantBankDetailsId'));
    end if;
    
    set  vParticipantIdOwner= JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdOwner'));
    set vBankId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.bankId'));
    
    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountTypeId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vAccountTypeId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountTypeId'));
    end if;
    
    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.branchId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vBranchId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.branchId'));
    end if;

    set vFlagDefault = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.flagDefault'));

    set vName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.name'));
    
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
    end if;
    
    set vAccountHolderName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountHolderName'));
    set vAccountNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountNumber'));
	
	-- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that 
    -- database constraints won't pick up
	-- ************************************************
    
    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;
    
	-- ig_db.ParticipantBankDetails
    -- --------------------------
	-- Insert or update?
    	if (vParticipantBankDetailsId is null) or (vParticipantBankDetailsId = '') then  -- Financial Admin project must be added

			insert into ig_db.ParticipantBankDetails
					(ParticipantIdOwner, 
                    BankId, 
                    AccountTypeId, 
                    BranchId, 
                    FlagDefault,
                    Name, 
                    Description, 
                    AccountHolderName, 
                    AccountNumber, 
                    LastUpdateTimestamp, 
                    LastUpdateUserName)
			values 
					(vParticipantIdOwner, 
                    vBankId, 
                    vAccountTypeId, 
                    vBranchId, 
                    vFlagDefault,
                    vName, 
                    vDescription, 
                    vAccountHolderName, 
                    vAccountNumber, 
   					current_timestamp(), -- LastUpdateTimestamp, 
					pUserId); -- LastUpdateUserName
			
            set vParticipantBankDetailsId = last_insert_id();
            
		else -- update
        
        		update ig_db.ParticipantBankDetails
				set 
    				ParticipantIdOwner = vParticipantIdOwner, 
                    BankId = vBankId, 
                    AccountTypeId = vAccountTypeId, 
                    BranchId = vBranchId, 
                    Name = vName, 
                    Description = vDescription, 
                    AccountHolderName = vAccountHolderName, 
                    AccountNumber = vAccountNumber, 
                    LastUpdateTimestamp = current_timestamp(),
                    LastUpdateUserName = pUserId
                    
				where ParticipantBankDetailsId = vParticipantBankDetailsId;
		end if;
        
      	if (vFlagDefault = 'Y' ) then  -- This is the Participant's default banking details.
			update  Participant
            set 	ParticipantBankDetailsIdDefault = vParticipantBankDetailsId
            where	ParticipantId = vParticipantIdOwner;
            
            update 	ParticipantBankDetails
            set		FlagDefault = 'N'
            where 	ParticipantIdOwner = vParticipantIdOwner
            and		ParticipantBankDetailsId <> vParticipantBankDetailsId;
            
        else -- Although Flag = 'N' : If there are no other Bank Details, this must be the default banking details
        
			select 	count(1) into vCount 
            from 	ig_db.ParticipantBankDetails
            where 	ParticipantIdOwner = vParticipantIdOwner;
            
			if (vCount > 1) then  -- Make sure one of them is the default banking details
				select 	count(1) into vCountDefault 
				from 	ig_db.ParticipantBankDetails
				where 	ParticipantIdOwner = vParticipantIdOwner
				and		FlagDefault = 'Y';
                
                if (vCountDefault <> 1) then -- set only current record as the default
					update 	ParticipantBankDetails 
                    set		FlagDefault = 'N'
                    where	ParticipantIdOwner = vParticipantIdOwner;
                    
					update 	ParticipantBankDetails 
                    set		FlagDefault = 'Y'
                    where	ParticipantBankDetailsId = vParticipantBankDetailsId;
                    
                    update 	Participant
                    set		ParticipantBankDetailsIdDefault = vParticipantBankDetailsId
                    where	ParticipantId	= vParticipantIdOwner;
                end if;

			else  -- There is only 1 Banking Details record, and it has to be the default (even if the flag was set to 'N')
				update 	ParticipantBankDetails 
				set		FlagDefault = 'Y'
				where	ParticipantBankDetailsId = vParticipantBankDetailsId;
				
				update 	Participant
				set		ParticipantBankDetailsIdDefault = vParticipantBankDetailsId
				where	ParticipantId	= vParticipantIdOwner;
					
			end if;
		end if;
            
	commit;

END