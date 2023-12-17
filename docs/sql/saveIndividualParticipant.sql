CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveIndividualParticipant`(
						pQuery JSON, 
						pUserId VARCHAR(50), 
						OUT pIndividualId BIGINT, 
                        OUT pProjectParticipantId BIGINT, 
                        OUT pParticipantId BIGINT, 
                        OUT pSystemMemberId BIGINT, 
                        OUT pContactPointId BIGINT,
                        OUT pParticipantOfficeId BIGINT)
BEGIN

	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- Individual table:
	declare vIndividualId bigint;
	declare vFirstName varchar(50);
	declare vSecondName varchar(50);
	declare vThirdName varchar(50);
	declare vNickName varchar(50);
    
    declare vLastName varchar(250);
    declare vInitials varchar(250);
    declare vIdNumber varchar(13);
    declare vPassportNumber varchar(45);
    declare vCountryId varchar(45);
    declare vIncomeTaxNumber varchar(255);
	declare vAllowLoginFlag varchar(1);
	declare vIsActiveMember varchar(1);
	declare vUserName varchar(100);
	declare vPass varchar(255);

	-- passwordResetToken
	-- passwordResetExpiryDate : $("#niwPasswordResetExpiryDate").val(),

	-- Project Participant table:

	-- Participant table:
    declare vProjectIdSustenanceParent bigint;
    declare vParticipantTypeCode varchar(50);
    declare vTapSubscriptionCode varchar(50);
    declare vRepresentativeIndividualId bigint default null;
	declare vMarketingIndividualId bigint default null;
    declare vRegisteredName varchar(255) default null;
    declare vTradingName varchar(45) default null;
    declare vRegistrationNumber varchar(45) default null;
    declare vSystemName varchar(255);
    declare vTapAdministered varchar(1);
    declare vIsIndividual varchar(1);
    declare vCompanyRegistrationNumber varchar(45) default null;
    declare vVatNumber varchar(45) default null;
    declare vDefaultInvoiceDay int default 1;
    declare vProjectPrefix varchar(10) default null;
    declare vLatestProjectNumber int default 0;
    declare vProjectPostfix varchar(10) default null;
    declare vInvoicePrefix varchar(10) default null;
    declare vLatestInvoiceNumber bigint default null;
    declare vInvoiceNumberFormat varchar(12) default null;
	
	-- SystemMember table:
	declare vSystemMemberId BIGINT;
	declare vRoleCode varchar(50);
	declare vStartDate date;
	declare vEndDate date;
			
	-- -- ContactPoint table:    
    declare vContactPointId BIGINT;
    declare vParticipantOfficeId BIGINT;
    declare vParticipantOfficeName varchar(255);
    declare vParticipantOfficeDescription varchar(255);
	declare vOfficeCostPerSeat decimal(12,2);
	
	declare vSuburbId  bigint;    
    declare vAddressLine1 varchar(50);
    declare vAddressLine2 varchar(50);
    declare vAddressLine3 varchar(50);
	declare vContactPointName varchar(255);
    declare vEmailAddress varchar(255);
    declare vPhoneNumber varchar(40);

	-- -- Local variables
    
	-- Work variables
	declare pSubProjectId bigint;
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
	-- Ids
	set vIndividualId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualId'));
	set pIndividualId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualId'));
	 set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectParticipantId'));
	 if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set pProjectParticipantId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectParticipantId'));
     end if;
    
	set pParticipantId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantId'));

    -- ig_db.Individual
    set vFirstName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.firstName'));
    set vSecondName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.secondName'));
    set vThirdName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.thirdName'));
    set vNickName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.nickName'));
	set vInitials = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.initials'));
	set vLastName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.lastName'));
	set vIDNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.idNumber'));
	set vPassportNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.passportNumber'));
	set vCountryId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.countryId'));
	set vIncomeTaxNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.incomeTaxNumber'));
	set vAllowLoginFlag = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.allowLoginFlag'));
	set vIsActiveMember = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.isActiveMember'));
	set vUserName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.userName'));
    set vPass = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.pass'));

    -- ig_db.Participant
    set vParticipantTypeCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantTypeCode'));
    set vTapSubscriptionCode = if(JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tapSubscriptionCode')) = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tapSubscriptionCode')));

    -- set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.representativeIndividualId'));
	-- if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
	-- 	set vRepresentativeIndividualId = JSON_EXTRACT(pQuery, '$.representativeIndividualId');
    -- end if;
       
    set vRegisteredName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.registeredName'));
    set vTradingName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tradingName'));
    set vSystemName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.systemName'));
    set vTapAdministered = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tapAdministered'));
    set vIsIndividual = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.isIndividual'));
    set vCompanyRegistrationNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.companyRegistrationNumber'));
    set vVatNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.vatNumber'));

    set vInvoicePrefix = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.invoicePrefix'));
    set vInvoiceNumberFormat = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.invoiceNumberFormat'));	

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.latestInvoiceNumber'));
	IF ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
	 	set vLatestInvoiceNumber = JSON_EXTRACT(pQuery, '$.latestInvoiceNumber');
	ELSE
		set vLatestInvoiceNumber = 0;
    END IF;
	
    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.defaultInvoiceDay'));
	 if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
	 	set vDefaultInvoiceDay = JSON_EXTRACT(pQuery, '$.defaultInvoiceDay');
     end if;

    set vProjectPrefix = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectPrefix'));
    set vLatestProjectNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.latestProjectNumber'));
    set vProjectPostfix = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectPostfix'));

    -- ig_db.SystemMember
    set vSystemMemberId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.systemMemberId'));
	set pSystemMemberId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.systemMemberId'));

	set vRoleCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.roleCode'));

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.startDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			set vStartDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.endDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			set vEndDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;

    -- ig_db.ParticipantOffice
	set vParticipantOfficeName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantOfficeName'));
    set vParticipantOfficeDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantOfficeDescription'));
        
    -- set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.officeCostPerSeat'));
    -- if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
	-- 	set vOfficeCostPerSeat = tmp;
	-- end if;
	set vOfficeCostPerSeat = null;
    
    -- -- ig_db.ContactPoint

    set vSuburbId = if(JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.suburbId')) = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.suburbId')));
	set vAddressLine1 = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.addressLine1'));
	set vAddressLine2 = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.addressLine2'));
	set vAddressLine3 = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.addressLine3'));
	set vContactPointName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.contactPointName'));
	set vEmailAddress = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.emailAddress'));
	set vPhoneNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery,  '$.phoneNumber'));

	IF vSuburbId = "" THEN
		set vSuburbId = NULL;
	END IF;

	-- Get ids if not null
	-- set vContactPointId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.contactPointId'));
	-- set pContactPointId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.contactPointId'));
	-- set vParticipantOfficeId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantOfficeId'));
	-- set pParticipantOfficeId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantOfficeId'));


	select p.ProjectIdSustenance into vProjectIdSustenanceParent from Participant p where ParticipantId = pParticipantId;

	select p.ParticipantOfficeIdDefault into vParticipantOfficeId from Participant p where ParticipantId = pParticipantId;
	select p.ParticipantOfficeIdDefault into pParticipantOfficeId from Participant p where ParticipantId = pParticipantId;
	
	if (vParticipantOfficeId is not null) and (vParticipantOfficeId <> '') then  -- Existing Office
	   	select po.ContactPointIdDefault into vContactPointId from ParticipantOffice po where ParticipantOfficeId = vParticipantOfficeId;
	   	select po.ContactPointIdDefault into pContactPointId from ParticipantOffice po where ParticipantOfficeId = vParticipantOfficeId;
	else 
	   	set vContactPointId = null;
	   	set pContactPointId = null;
	end if;

	-- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that 
    -- database constraints won't pick up
	-- ************************************************


	
	-- Ensure that username is unique
    select count(1) into usernameCount from ig_db.Individual where UserName = vUserName and IndividualId <> vIndividualId;
	if (vAllowLoginFlag = 'Y') then
		if (usernameCount > 0) then
			signal sqlstate '45000'
				set message_text = 'The username must be unique';
		end if;
	end if;

    select count(1) into systemNameCount from ig_db.Participant where SystemName = vSystemName and ParticipantId <> pParticipantId;
	if (systemNameCount > 0) then
		signal sqlstate '45000'
			set message_text = 'The System Name must be unique';
	end if;
    
    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;
    
	-- ig_db.Individual
    -- --------------------------
	if (vCountryId = '') then
		set vCountryId = null;
    end if;

    -- ig_db.Participant
    -- --------------------------
    update ig_db.Participant
		set ParticipantIdBUParent = null, 
			ParticipantTypeCode = vParticipantTypeCode, 
            TapSubscriptionCode= vTapSubscriptionCode, 
            RepresentativeIndividualId = vRepresentativeIndividualId, 
            MarketingIndividualId = vMarketingIndividualId, 
            ParticipantOfficeIdDefault = vParticipantOfficeId, 
            -- ParticipantBankDetailsIdDefault = vParticipantBankDetailsId, do not update it here
            SystemName = vSystemName, 
            RegisteredName = null, 
            TradingName = vTradingName, 
            ProjectPrefix = vProjectPrefix, 
            LatestProjectNumber = vLatestProjectNumber, 
            ProjectPostfix = vProjectPostfix, 
            StartDate = vStartDate, 
            EndDate = vEndDate, 
			TapAdministered = vTapAdministered,
            IsIndividual = vIsIndividual, 
            RegistrationNumber = null, 
            VATNumber = null, 
            DefaultInvoiceDay = vDefaultInvoiceDay, 
			InvoicePrefix = vInvoicePrefix,
			LatestInvoiceNumber = vLatestInvoiceNumber,
			InvoiceNumberFormat = vInvoiceNumberFormat,				
			EmailAddressAccounts = vEmailAddress,
            LastUpdateTimestamp = current_timestamp(),
            LastUpdateUserName = pUserId

		where ParticipantId = pParticipantId;

    update ig_db.Individual 
		set ParticipantId = pParticipantId, 
			FirstName = vFirstName, 
			SecondName = vSecondName, 
			ThirdName = vThirdName, 
			NickName = vNickName, 
			LastName = vLastName, 
			Initials = vInitials, 
			IDNumber = vIDNumber, 
			PassportNumber = vPassportNumber, 
			CountryId = vCountryId, 
			IsActiveMember = vIsActiveMember, 
			AllowLoginFlag = vAllowLoginFlag, 
			IncomeTaxNumber = vIncomeTaxNumber, 
--			Pass = vPass, 
			UserName = vUserName, 
			-- PasswordResetToken = vPasswordResetToken, 
			-- PasswordResetExpiryDate = vPasswordResetExpiryDate, 
			LastUpdateTimestamp = current_timestamp(), 
			LastLoginTimestamp = current_timestamp(), 
			LastUpdateUserName = pUserId
		where IndividualId = vIndividualId;
    

	if (vProjectIdSustenanceParent is null) or (vProjectIdSustenanceParent = '') then  -- Financial Admin project must be added

			insert into ig_db.Project
				(	ProjectIdParent, 
					ParticipantIdHost, 
					ProjectParticipantIdLevel1, 
					IndividualIdProjectAdmin, 
					FlagSustenanceProject,
					ProjectNumberBigInt, 
					ProjectNumberText, 
					ProjectNameText,
					Title, 
					SubProjNumber,
					Description, 
					IsActive, 
					StartDate, 
					CompletionDate, 
					LastUpdateTimestamp, 
					LastUpdateUserName)
				values 
				(	null, -- ProjectIdParent, 
					pParticipantId,
					null, -- pProjectParticipantId, -- ProjectParticipantIdLevel1, 
					pIndividualId, -- IndividualIdProjectAdmin, 
					"Y", -- FlagSustenanceProject
					1, --  ProjectNumberBigInt, 
					"0001", --  ProjectNumberText, 
					CONCAT(
						CONCAT(left('0000', 4 - length(pParticipantId)), pParticipantId),
						' - 0001 - Sustenance - ', vNickName, ' ', vLastName), -- ProjectNameText
					CONCAT('Sustenance - ', vNickName, ' ', vLastName), -- Title, 
					null, -- SubProjNumber,
					"Sustenance Administration", -- Description, 
					"Y", -- IsActive, 
					current_timestamp(), -- StartDate, 
					null, -- CompletionDate, 
					current_timestamp(), -- LastUpdateTimestamp, 
					pUserId); -- LastUpdateUserName
				
			set vProjectIdSustenanceParent = last_insert_id();

			update ig_db.Participant
				set ProjectIdSustenance = vProjectIdSustenanceParent
				where ParticipantId = pParticipantId;

				insert into ig_db.ProjectStage
				(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, LastUpdateUserName, LastUpdateTimestamp)
				values 
				(vProjectIdSustenanceParent, 1,  "Single Stage" , "Default Single Stage",  current_timestamp(), null, pUserId, current_timestamp());

				insert into ig_db.ProjectParticipant
					(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate, 
					LastUpdateTimestamp, LastUpdateUserName )
				values 
					(vProjectIdSustenanceParent, 
					pParticipantId, 
					null, -- ProjectParticipantIdAboveMe, 
					"Top level Participant for Sustenance Project", -- Description, 
					current_timestamp(), -- StartDate, 
					null, -- CompletionDate, 
					current_timestamp(), -- LastUpdateTimestamp, 
					pUserId -- LastUpdateUserName
					);

				set pProjectParticipantId = last_insert_id();

				update ig_db.Project
				set ProjectParticipantIdLevel1 = pProjectParticipantId,
					ParticipantIdHost = pParticipantId
				where ProjectId = vProjectIdSustenanceParent;

            
			-- Insert ProjectSustenance Child projects Start
			-- Later: Do it with a cursor from the TypicalFolderStructure Reference Table
			-- ---------------------------------------------------------------------------------------------------------------------
			insert into ig_db.Project
				(
					ProjectIdParent, 
					ParticipantIdHost, 
					ProjectParticipantIdLevel1, 
					IndividualIdProjectAdmin, 
					FlagSustenanceProject,
					ProjectNumberBigInt, 
					ProjectNumberText, 
					ProjectNameText,
					Title, 
					SubProjNumber,
					Description, 
					IsActive, 
					StartDate, 
					CompletionDate, 
					LastUpdateTimestamp, 
					LastUpdateUserName)
				values 
				(	vProjectIdSustenanceParent,
					pParticipantId, -- ParticipantIdHost, 
					null, -- ProjectParticipantIdLevel1, 
					pIndividualId, -- IndividualIdProjectAdmin, 
					"Y", -- FlagSustenanceProject
					null, --  ProjectNumberBigInt is only not-null in the parent project 
					"0001", --  ProjectNumberText, 
					CONCAT(
						CONCAT(left('0000', 4 - length(pParticipantId)), pParticipantId),
						' - 0001 - Sustenance - 01 - Non Private - Financial ') , -- ProjectNameText
					"Sustenance",    -- Title, 
					"01 - Non Private - Financial", -- SubProjNumber
					null, -- Description, 
					"Y", -- IsActive, 
					current_timestamp(), -- StartDate, 
					null, -- CompletionDate, 
					current_timestamp(), -- LastUpdateTimestamp, 
					pUserId); -- LastUpdateUserName
			
				set pSubProjectId = last_insert_id();
                
				insert into ig_db.ProjectStage
				(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, LastUpdateUserName, LastUpdateTimestamp)
				values 
				(pSubProjectId, 1,  "Stage 1 - Single Stage" , "Default Single Stage",  current_timestamp(), null, pUserId, current_timestamp());

				insert into ig_db.ProjectParticipant
					(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate, 
					LastUpdateTimestamp, LastUpdateUserName )
				values 
					(pSubProjectId, 
					pParticipantId, 
					null, -- ProjectParticipantIdAboveMe, 
					"Top level Participant for Sustenance Project", -- Description, 
					current_timestamp(), -- StartDate, 
					null, -- CompletionDate, 
					current_timestamp(), -- LastUpdateTimestamp, 
					pUserId -- LastUpdateUserName
					);

				set pProjectParticipantId = last_insert_id();

				update ig_db.Project
				set ProjectParticipantIdLevel1 = pProjectParticipantId,
					ParticipantIdHost = pParticipantId
				where ProjectId = pSubProjectId;

		-- ---------------------------------------------------------------------------------------------------------------------
        /*  NB Ingrid took out all other sustenance projects except financial */
		-- ---------------------------------------------------------------------------------------------------------------------
			-- Insert ProjectSustenance Child projects End
		-- ---------------------------------------------------------------------------------------------------------------------

	end if;

	-- 	-- ig_db.ParticipantOffice
	-- 	-- --------------------------
	if (vParticipantOfficeId is not null) and (vParticipantOfficeId <> '') then  -- Existing Office
		if (vParticipantOfficeName is not null) and (vParticipantOfficeName <> '') then
			update ig_db.ParticipantOffice 
				set ParticipantId = pParticipantId,  
                ContactPointIdDefault = vContactPointId, 
                Name = vParticipantOfficeName, 
                Description = vParticipantOfficeDescription, 
                -- DateFrom = vOfficeDateFrom, Stays the same
                -- CostPerSeat = vOfficeCostPerSeat, Does not change here
                LastUpdateTimestamp = current_timestamp(), 
                LastUpdateUserName = pUserId
			where ParticipantOfficeId = vParticipantOfficeId;
		end if;
	else -- New office
		if (vParticipantOfficeName is not null) and (vParticipantOfficeName <> '') then
			insert into ig_db.ParticipantOffice 
				(ParticipantId, ContactPointIdDefault, Name, Description, DateFrom, CostPerSeat, LastUpdateTimestamp, LastUpdateUserName)
				values 
				(pParticipantId, 
				null, 
				vParticipantOfficeName, 
				vParticipantOfficeDescription, 
				current_timestamp(), -- vOfficeDateFrom, 
				null, -- vOfficeCostPerSeat, 
				current_timestamp(), 
				pUserId);
                
			set pParticipantOfficeId = last_insert_id();
			set vParticipantOfficeId = pParticipantOfficeId;
		end if;
	end if;

	-- 	-- ig_db.ContactPoint
	-- 	-- --------------------------
	if (vContactPointId is not null) and (vContactPointId <> '') then  -- Existing ContactPoint
		if (vContactPointName is not null) and (vContactPointName <> '') then
			update ig_db.ContactPoint
				set 
                ParticipantOfficeId = vParticipantOfficeId, 
				SuburbId = vSuburbId,
				AddressLine1 = vAddressLine1,
				AddressLine2 = vAddressLine2,
				AddressLine3 = vAddressLine3,
                Name = vContactPointName, 
                EmailAddress = vEmailAddress, 
                PhoneNumber = vPhoneNumber, 
                LastUpdateTimestamp = current_timestamp(), 
                LastUpdateUserName  = pUserId
			where ContactPointId = vContactPointId;
		end if;
	else -- New ContactPoint
		if (vContactPointName is not null) and (vContactPointName <> '') then
			insert into ig_db.ContactPoint
				(ParticipantOfficeId, SuburbId, Name, EmailAddress, AddressLine1, AddressLine2, AddressLine3, PhoneNumber, LastUpdateTimestamp, LastUpdateUserName)        
				values
				(pParticipantOfficeId, 
				vSuburbId,
				vContactPointName, 
				vEmailAddress, 
				vAddressLine1, 
				vAddressLine2, 
				vAddressLine3, 
				vPhoneNumber,  
				current_timestamp(), 
				pUserId);
			set pContactPointId = last_insert_id();
			set vContactPointId = pContactPointId;
		end if;
	end if;

    -- ig_db.ParticipantOffice and ig_db.ContactPoint
    -- --------------------------
	 	-- update participant with default participant office
		if (pParticipantOfficeId is not null) and (pParticipantOfficeId <> '') then
			update ig_db.Participant
				set ParticipantOfficeIdDefault = pParticipantOfficeId
				where ParticipantId = pParticipantId;
		end if;
			-- update participantOffice contactPointIdDefault
		if (pContactPointId is not null) and (pContactPointId <> '') then
			update ig_db.ParticipantOffice
	 			set ContactPointIdDefault = pContactPointId
	 			where ParticipantOfficeId = pParticipantOfficeId;
	 	end if;

    -- -- ig_db.ParticipantBankDetails
    -- --------------------------
    --  Add this in Bank Details tab in front end

    -- ig_db.SystemMember
    -- --------------------------

	if (vSystemMemberId is not null) and (vSystemMemberId <> '') then
		update ig_db.SystemMember
			set 				
                RoleCode = vRoleCode, 
                StartDate = vStartDate, 
                EndDate = vStartDate, 
                LastUpdateTimestamp = current_timestamp(), 
				LastUpdateUserName = pUserId
			where 
				SystemMemberId = vSystemMemberId;
	end if;
    
	commit;

END