CREATE DEFINER=administrator@localhost PROCEDURE saveNewParticipantBankDetails(
						pQuery JSON, pUserId VARCHAR(50), 
                        OUT pParticipantBankDetailsId BIGINT)

BEGIN

	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- ParticipantBankDetails table:
	-- declare vParticipantBankDetailsId   bigint;
	declare vParticipantIdOwner         bigint;
	declare vBankId                   varchar(50);
	declare vAccountTypeId            varchar(50);
	declare vBranchId                 varchar(50);
	declare vName                       varchar(45);
	declare vDescription                varchar(255);
	declare vAccountHolderName          varchar(255);
	declare vAccountNumber              varchar(255);
	declare vDefaultOne                 varchar(1);

	-- -- Local variables
    
	-- Work variables
	DECLARE vCount integer default 0;
    declare usernameCount int;
    declare systemNameCount int;
	declare tmp varchar(50);
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.ParticipantBankDetails
	-- set vParticipantBankDetailsId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.ParticipantBankDetailsId'));
	set vParticipantIdOwner       = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdOwner'));
	set vBankId                 = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.bankId'));
	set vAccountTypeId          = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountTypeId'));
	set vBranchId               = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.branchId'));
	set vName                     = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.name'));
	set vDescription              = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	set vAccountHolderName        = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountHolderName'));
	set vAccountNumber            = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.accountNumber'));
	set vDefaultOne               = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.defaultOne'));

	If vBranchId = 'null' then
		set vBranchId = null;
	End If;


    start transaction;
 
		-- ig_db.Participant
		-- --------------------------
		insert into ig_db.ParticipantBankDetails (
				ParticipantIdOwner, 
				BankId, 
				AccountTypeId, 
				BranchId, 
				Name, 
				Description, 
				AccountHolderName, 
				AccountNumber,  
				LastUpdateTimestamp, 
				LastUpdateUserName)
			values (
				vParticipantIdOwner, 
				vBankId, 
				vAccountTypeId, 
				vBranchId, 
				vName, 
				vDescription, 
				vAccountHolderName, 
				vAccountNumber, 
				current_timestamp(), -- vLastUpdateTimestamp
				pUserId -- vLastUpdateUserName
				);

		set pParticipantBankDetailsId = last_insert_id();

		select count(*) into vCount from ParticipantBankDetails
		where ParticipantIdOwner = vParticipantIdOwner;
		
		if (vCount = 1 ) then 
			update Participant set ParticipantBankDetailsIdDefault = pParticipantBankDetailsId where ParticipantId = vParticipantIdOwner;
		end if;
		
		if ((vCount > 1 ) and (vDefaultOne = 'Y'))  then 
			update Participant set ParticipantBankDetailsIdDefault = pParticipantBankDetailsId where ParticipantId = vParticipantIdOwner;
		end if;	

	commit;

END