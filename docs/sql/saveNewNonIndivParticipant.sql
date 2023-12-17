CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveNewNonIndivParticipant`(
						pQuery JSON, 
						pUserId VARCHAR(50), 
						OUT pProjectParticipantId BIGINT, 
						OUT pParticipantId BIGINT, 
						OUT pContactPointId BIGINT, 
						OUT pParticipantOfficeId BIGINT)
    NO SQL
BEGIN
 
	declare pProjectIdSustenanceParent bigint;
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input

	-- Participant table:
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
    declare vVatNumber varchar(45) default null;
    declare vDefaultInvoiceDay int default 1;
    declare vProjectPrefix varchar(10) default null;
    declare vLatestProjectNumber int default 1;
    declare vProjectPostfix varchar(10) default null;
	declare vStartDate date;
    declare vEndDate date;

	-- Project table
    -- declare vProjectSustenanceTitle varchar
		
	-- -- ContactPoint table:    
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

    declare vInvoicePrefix varchar(10) default null;
    declare vLatestInvoiceNumber bigint default null;
    declare vInvoiceNumberFormat varchar(12) default null;
	
	-- -- Local variables
    
	-- Work variables
	declare pSubProjectId bigint;
	declare tmp varchar(50);
    declare systemNameCount int;
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    
    -- ig_db.Participant
    set vParticipantTypeCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantTypeCode'));
    set vTapSubscriptionCode = if(JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tapSubscriptionCode')) = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tapSubscriptionCode')));

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.representativeIndividualId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vRepresentativeIndividualId = JSON_EXTRACT(pQuery, '$.representativeIndividualId');
    end if;
    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.marketingIndividualId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vMarketingIndividualId = JSON_EXTRACT(pQuery, '$.marketingIndividualId');
    end if;

    set vRegisteredName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.registeredName'));
    set vTradingName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tradingName'));
    set vSystemName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.systemName'));
    set vTapAdministered = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.tapAdministered'));
    set vIsIndividual = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.isIndividual'));
    set vRegistrationNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.registrationNumber'));
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

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.startDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			set vStartDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.endDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			set vEndDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;

	-- ig_db.Project
	-- set vProjectSustenanceTitle = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectSustenanceTitle'));

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
	
	-- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that 
    -- database constraints won't pick up
	-- ************************************************
    
    select count(1) into systemNameCount from ig_db.Participant where SystemName = vSystemName;
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
    
    -- ig_db.Participant
    -- --------------------------
    insert into ig_db.Participant
		(	ParticipantIdBUParent, 
			ParticipantTypeCode, 
            TapSubscriptionCode, 
            RepresentativeIndividualId, 
            MarketingIndividualId, 
            ParticipantOfficeIdDefault, 
            ParticipantBankDetailsIdDefault, 
            SystemName, 
            RegisteredName, 
            TradingName, 
            ProjectPrefix, 
            LatestProjectNumber, 
            ProjectPostfix, 
            StartDate, 
            EndDate, 
			TapAdministered,
            IsIndividual, 
            RegistrationNumber, 
            VATNumber, 
            DefaultInvoiceDay, 
			InvoicePrefix,
			LatestInvoiceNumber,
			InvoiceNumberFormat,			
			EmailAddressAccounts,            
            LastUpdateTimestamp, 
            LastUpdateUserName)
		values
		(	null, -- ParticipantIdBUParent, 
			vParticipantTypeCode, 
            vTapSubscriptionCode, 
            vRepresentativeIndividualId, 
            vMarketingIndividualId, 
            null, -- ParticipantOfficeIdDefault, 
            null, -- ParticipantBankDetailsIdDefault, 
            vSystemName, 
            vRegisteredName, 
            vTradingName, 
            vProjectPrefix, 
            vLatestProjectNumber, 
            vProjectPostfix, 
            current_timestamp(), -- vStartDate, 
            vEndDate, 
			vTapAdministered,
            "N", -- vIsIndividual, 
            vRegistrationNumber, 
            vVATNumber, 
            vDefaultInvoiceDay, 
			vInvoicePrefix,
			vLatestInvoiceNumber,
			vInvoiceNumberFormat,		
            vEmailAddress,
            current_timestamp(), -- vLastUpdateTimestamp
            pUserId -- vLastUpdateUserName
            );

		set pParticipantId = last_insert_id();

		insert into ig_db.Project
			(	ProjectIdParent, 
				ParticipantIdHost, 
                ProjectParticipantIdLevel1, 
				IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, ProjectNumberText, ProjectNameText, Title, 
				SubProjNumber,
                Description, IsActive, StartDate, CompletionDate, LastUpdateTimestamp, LastUpdateUserName)
	 		values 
			(	null, -- ProjectIdParent, 
				pParticipantId, -- ParticipantIdHost, 
				null, 	-- pProjectParticipantId, -- ProjectParticipantIdLevel1, 
				vRepresentativeIndividualId, -- IndividualIdProjectAdmin, 
				"Y", -- FlagSustenanceProject
				1, --  ProjectNumberBigInt, 
				"0001", --  ProjectNumberText, 
                CONCAT(
					CONCAT(left('0000', 4 - length(pParticipantId)), pParticipantId),
					' - 0001 - Sustenance - ', vRegisteredName),
				CONCAT('Sustenance - ', vRegisteredName), -- Title, 
				null, -- SubProjNumber,
				"Sustenance Admin", -- Description, 
				"Y", -- IsActive, 
				current_timestamp(), -- StartDate, 
				null, -- CompletionDate, 
				current_timestamp(), -- LastUpdateTimestamp, 
				pUserId); -- LastUpdateUserName

		set pProjectIdSustenanceParent = last_insert_id();
       
  		update ig_db.Participant
			set ProjectIdSustenance = pProjectIdSustenanceParent
			where ParticipantId = pParticipantId;
 
		insert into ig_db.ProjectParticipant
				(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate,
				 LastUpdateTimestamp, LastUpdateUserName)
			values 
				(pProjectIdSustenanceParent, 
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
				set ProjectParticipantIdLevel1 = pProjectParticipantId
				where ProjectId = pProjectIdSustenanceParent;

 
	-- ---------------------------------------------------------------------------------------------------------------------
    -- Substenance Sub Project
	 	insert into ig_db.Project
  			(	ProjectIdParent, 
   				ParticipantIdHost, 
                ProjectParticipantIdLevel1, 
				IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, ProjectNumberText, ProjectNameText, Title, 
				SubProjNumber,
                Description, IsActive, StartDate, CompletionDate, LastUpdateTimestamp, LastUpdateUserName)
	 		values 
			(	pProjectIdSustenanceParent, 
				pParticipantId, 
                null, -- pProjectParticipantId, 
                null, 
				"Y", -- FlagSustenanceProject
				null, "0001", 
                CONCAT(CONCAT(left('0000', 4 - length(pParticipantId)), pParticipantId),' - 0001 - Sustenance - 04 - Financial') , "Sustenance",   
   				"04 - Financial", -- SubProjNumber
				"", "Y", current_timestamp(), null, current_timestamp(), pUserId); 

			set pSubProjectId = last_insert_id();

			insert into ig_db.ProjectStage
			(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, LastUpdateUserName, LastUpdateTimestamp)
			values 
			(pSubProjectId, 1,  "Stage 1 - Single Stage" , "Default Single Stage",  current_timestamp(), null, pUserId, current_timestamp());

			insert into ig_db.ProjectParticipant
				(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate,
				 LastUpdateTimestamp, LastUpdateUserName)
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
				set ProjectParticipantIdLevel1 = pProjectParticipantId
				where ProjectId = pSubProjectId;
    -- ---------------------------------------------------------------------------------------------------------------------
    -- Insert ProjectSustenance Child projects End







	-- 	-- ig_db.ParticipantOffice
	-- 	-- --------------------------
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
	end if;
    set pParticipantOfficeId = last_insert_id();

	-- 	-- ig_db.ContactPoint
	-- 	-- --------------------------
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
	end if;

    -- ig_db.ParticipantOffice and ig_db.ContactPoint
    -- --------------------------
	 	-- update participant with default financial admin project
		if (pProjectIdSustenanceParent is not null) and (pProjectIdSustenanceParent <> '') then
			update ig_db.Participant
				set ProjectIdSustenance = pProjectIdSustenanceParent
				where ParticipantId = pParticipantId;
		end if;
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

	commit;

END