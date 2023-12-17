CREATE DEFINER=`administrator`@`localhost` PROCEDURE `generateDraftInvoice`(
						pQuery JSON, 
                        pUserId varchar(50), 
                        OUT pInvoiceId bigint)
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- Timesheet table:
    declare vParticipantIdFrom bigint; 
    declare vParticipantIdTo bigint; 
    declare vInvoiceNumber varchar(45);
    declare vInvoiceAmountExclTax decimal(12,2) default 0;
    declare vInvoiceTaxAmount decimal(12,2) default 0;
    declare vInvoiceTotalAmountInclTax decimal(12,2) default 0;
    declare vInvoiceDate datetime;
    declare vDescription varchar(255);
    declare vDateTo date;

	-- Local variables
	-- Work variables
	declare tmp varchar(50);
	declare vAddressLine1  varchar(50);
    declare vAddressLine2 varchar(50);
    declare vAddressLine3 varchar(50);
    declare vSuburbId_Name varchar(50);
	declare vCityId_Name varchar(50);
	declare vCountryId_Name varchar(50);
    
	declare vInvoiceHeading varchar(255);
    declare vFromIsIndividual varchar(1);
    declare vFromParticipantName varchar(255);
    declare vFromCompRegistrationNumber varchar(50);
    declare vFromAddress varchar(255);
    declare vFromVatNumber varchar(255);
    declare vFromEmailAddress varchar(255);
    declare vFromPhoneNumber varchar(255);
    declare vFromBankDetails varchar(255);
    declare vFromThankYouMsg varchar(255) DEFAULT 'Thank you for your business';

    declare vToIsIndividual varchar(1);
    declare vToParticipantName varchar(255);
    declare vToCompRegistrationNumber varchar(50);
    declare vToAddress varchar(255);
    declare vToVatNumber varchar(255);
    declare vToEmailAddress varchar(255);
    declare vToPhoneNumber varchar(255);
    declare vToAttention varchar(255);
    
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    
    set vParticipantIdFrom = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdFrom')); 
    set vParticipantIdTo = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdTo'));
    -- set vInvoiceNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.invoiceNumber'));

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.invoiceDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			-- set tmp = (tmp + (24*60*60*1000) - 1) ;    -- make time 1 sec before midnight
	 		set vInvoiceDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	 end if;

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.invoiceUpUntilDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			-- set tmp = (tmp + (24*60*60*1000) - 1000) ;    -- make time 1 sec before midnight
			set vDateTo = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;

    set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	
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
    
	-- Get FROM participant's Invoice information
	set 	vFromVatNumber =  (select VatNumber
								from Participant
								where ParticipantId = vParticipantIdFrom);
	set 	vFromIsIndividual =  (select IsIndividual
								from Participant
								where ParticipantId = vParticipantIdFrom);
	set 	vFromEmailAddress =  (select EmailAddressAccounts
								from Participant
								where ParticipantId = vParticipantIdFrom);

	-- select 	vParticipantIdFrom,vFromVatNumber,vFromIsIndividual,vFromEmailAddress;
    
    if (vFromIsIndividual = 'Y') then
		set 	vFromParticipantName = (select CONCAT(FirstName, ' ', Initials, ' ', LastName)
        from	Individual 
        where	ParticipantId = vParticipantIdFrom);

   		set 	vFromCompRegistrationNumber = 'N/A';
    else
   		set 	vFromParticipantName = (select RegisteredName
        from	Participant 
        where	ParticipantId = vParticipantIdFrom);

   		set 	vFromCompRegistrationNumber = (select RegistrationNumber
        from	Participant 
        where	ParticipantId = vParticipantIdFrom);
    end if;
	
	if ((vFromVatNumber is not null) and (vFromVatNumber <> '') and (vFromVatNumber <> 'null')) then
			set vInvoiceHeading = 'TAX INVOICE';
	else 
			set vInvoiceHeading = 'INVOICE';
            set vFromVatNumber = 'N/A';
	end if;

	set  vAddressLine1 = (select trim(cp.AddressLine1)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdFrom);
	set  vAddressLine2 = (select trim(cp.AddressLine2)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdFrom);
	set  vAddressLine3 = (select trim(cp.AddressLine3)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdFrom);
	set  vSuburbId_Name = (select trim(cp.SuburbId_Name)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdFrom);
	set  vCityId_Name = (select trim(cp.CityId_Name)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdFrom);
	set  vCountryId_Name = (select trim(cp.CountryId_Name)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdFrom);
                                

	set		vFromAddress = null; 
			if	((vAddressLine1 IS NOT NULL) and (vAddressLine1 <> '')) then
				set	vFromAddress = concat(vAddressLine1, char(13)); 
			end if;
			if	((vAddressLine2 IS NOT NULL) and (vAddressLine2 <> '')) then
				set	vFromAddress = concat(vFromAddress, vAddressLine2, char(13)); 
			end if;
			if	((vAddressLine3 IS NOT NULL) and (vAddressLine3 <> '')) then
							set	vFromAddress = concat(vFromAddress, vAddressLine3, char(13)); 
			end if;
			if	((vSuburbId_Name IS NOT NULL) and (vSuburbId_Name <> '')) then
				set	vFromAddress = concat(vFromAddress, vSuburbId_Name, char(13)); 
			end if;
   			if	((vCityId_Name IS NOT NULL) and (vCityId_Name <> '')) then
				set	vFromAddress = concat(vFromAddress, vCityId_Name, char(13)); 
			end if;
   			if	((vCountryId_Name IS NOT NULL) and (vCountryId_Name <> '')) then
				set	vFromAddress = concat(vFromAddress, vCountryId_Name); 
			end if;
    
	set		vFromPhoneNumber = (select cp.PhoneNumber
    from	Participant p
			left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
            left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
    where	p.ParticipantId = vParticipantIdFrom);

    set vFromBankDetails = (select BankDetails
    from	vParticipant
    where	ParticipantId = vParticipantIdFrom);

--    declare vToAddress varchar(255);
--    declare vToPhoneNumber varchar(255);
--    declare vToAttention varchar(255);

	-- Get To participant's Invoice information
	set 	vToVatNumber =  (select VatNumber
    from Participant
    where ParticipantId = vParticipantIdTo);

	set 	vToIsIndividual = (select IsIndividual
    from Participant
    where ParticipantId = vParticipantIdTo);

	set 	vToEmailAddress = (select EmailAddressAccounts
    from Participant
    where ParticipantId = vParticipantIdTo);

    if (vToIsIndividual = 'Y') then
		set 	vToParticipantName = (select CONCAT(FirstName, ' ', Initials, ' ', LastName)
        from	Individual 
        where	ParticipantId = vParticipantIdTo);

   		set 	vToCompRegistrationNumber = 'N/A';
    else
   		set  	vToParticipantName = (select RegisteredName 
        from	Participant 
        where	ParticipantId = vParticipantIdTo);

   		set 	vToCompRegistrationNumber = (select RegistrationNumber
        from	Participant 
        where	ParticipantId = vParticipantIdTo);

    end if;

	set  vAddressLine1 = (select trim(cp.AddressLine1)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdTo);
	set  vAddressLine2 = (select trim(cp.AddressLine2)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdTo);
	set  vAddressLine3 = (select trim(cp.AddressLine3)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdTo);
	set  vSuburbId_Name = (select trim(cp.SuburbId_Name)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdTo);
	set  vCityId_Name = (select trim(cp.CityId_Name)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdTo);
	set  vCountryId_Name = (select trim(cp.CountryId_Name)
						from	Participant p
								left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
								left join vContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
								where	p.ParticipantId = vParticipantIdTo);
                                

	set		vToAddress = null; 
			if	((vAddressLine1 IS NOT NULL) and (vAddressLine1 <> '')) then
				set	vToAddress = concat(vAddressLine1, char(13)); 
			end if;
			if	((vAddressLine2 IS NOT NULL) and (vAddressLine2 <> '')) then
				set	vToAddress = concat(vToAddress, vAddressLine2, char(13)); 
			end if;
			if	((vAddressLine3 IS NOT NULL) and (vAddressLine3 <> '')) then
							set	vToAddress = concat(vToAddress, vAddressLine3, char(13)); 
			end if;
			if	((vSuburbId_Name IS NOT NULL) and (vSuburbId_Name <> '')) then
				set	vToAddress = concat(vToAddress, vSuburbId_Name, char(13)); 
			end if;
   			if	((vCityId_Name IS NOT NULL) and (vCityId_Name <> '')) then
				set	vToAddress = concat(vToAddress, vCityId_Name, char(13)); 
			end if;
   			if	((vCountryId_Name IS NOT NULL) and (vCountryId_Name <> '')) then
				set	vToAddress = concat(vToAddress, vCountryId_Name); 
			end if;

	set 	vToPhoneNumber = (select cp.PhoneNumber
    from	Participant p
			left join ParticipantOffice po on (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
            left join ContactPoint cp on (po.ContactPointIdDefault = cp.ContactPointId)
    where	p.ParticipantId = vParticipantIdTo);



	-- Insert into Invoice
	INSERT INTO Invoice
		SET
			ParticipantIdFrom = vParticipantIdFrom,
			ParticipantIdTo = vParticipantIdTo,
			InvoiceNumber = 0,
            InvoiceHeading = vInvoiceHeading,
			InvoiceDate = vInvoiceDate,
			InvoiceAmountExclTax = 0,
			InvoiceTaxAmount = 0,
			InvoiceTotalAmountInclTax = 0,
            UpUntilGenerateDate = vDateTo,
            FlagDraft = 'Y',
			Description = vDescription,

			FromParticipantName = vFromParticipantName,
			FromAddress = vFromAddress,
			FromCompRegistrationNumber = vFromCompRegistrationNumber,
			FromVatNumber = vFromVatNumber,
			FromEmailAddress = vFromEmailAddress,
			FromPhoneNumber = vFromPhoneNumber,
			FromBankDetails = vFromBankDetails,
            FromThankYouMsg = "Thank you for your business",
            

			ToParticipantName = vToParticipantName,
			ToAddress = vToAddress,
			ToCompRegistrationNumber = vToCompRegistrationNumber,
			ToVatNumber = vToVatNumber,
			ToEmailAddress = vToEmailAddress,
			ToPhoneNumber = vToPhoneNumber,
			ToAttention = vToAttention,

			LastUpdateTimestamp = current_timestamp,
			LastUpdateUserName = pUserId;

	set pInvoiceId = last_insert_id();
    
	insert into InvoiceLine
		(InvoiceId,
		ProjectId,
        LineType,
        RatesMissing,
        TotalUnits, 
        LineAmount,
        AgreementBetweenParticipantsId)
	select
		pInvoiceId,
		v.ProjectId,
        v.ExpenseType,
        sum(v.RatesMissing), 
        sum(v.SumNrOfUnits), 
        sum(v.LineAmount),
        v.AgreementBetweenParticipantsId 
        from 	vInvoiceGeneratorLineAmountsPerProjectAndExpense v
        where  		v.ParticipantIdContracted = vParticipantIdFrom
			and 	v.ParticipantIdContracting = vParticipantIdTo
			and     v.ActivityDate <= vDateTo
            and 
				(
					SELECT 	SUM(vv.LineAmount) 
					FROM 	vInvoiceGeneratorLineAmountsPerProjectAndExpense vv 
					WHERE 	vv.projectId = v.projectId
							and vv.ParticipantIdContracted = vParticipantIdFrom
							and vv.ParticipantIdContracting = vParticipantIdTo

					AND 	vv.ActivityDate <= vDateTo	
				) > 0
        group by 
			v.ProjectId,
			v.ExpenseType,
			v.AgreementBetweenParticipantsId;        

		set 	vInvoiceAmountExclTax = 
      				(select sum(il.LineAmount) from InvoiceLine il 
					where il.InvoiceId = pInvoiceId);

		if (vFromVatNumber = 'N/A') then
			set vInvoiceTaxAmount = 0;
            set vInvoiceTotalAmountInclTax = vInvoiceAmountExclTax;
        else
			set vInvoiceTaxAmount = (vInvoiceAmountExclTax * 0.15);
            set vInvoiceTotalAmountInclTax = vInvoiceAmountExclTax + vInvoiceTaxAmount;
        end if;

		UPDATE Invoice i 
SET 
    i.RatesMissing = (SELECT 
            SUM(il.RatesMissing)
        FROM
            InvoiceLine il
        WHERE
            il.InvoiceId = pInvoiceId)
WHERE
    i.InvoiceId = pInvoiceId;

		UPDATE Invoice i 
SET 
    i.InvoiceAmountExclTax = vInvoiceAmountExclTax,
    i.InvoiceTaxAmount = vInvoiceTaxAmount,
    i.InvoiceTotalAmountInclTax = vInvoiceTotalAmountInclTax
WHERE
    i.InvoiceId = pInvoiceId;

        -- The invoice detail lines for the draft invoice
        -- TIME COST DETAILS: - Rates Available
		insert into InvoiceLineDetail
			(InvoiceLineId, LineType, ActivityDate, ProjectName, StageName, PartBookedTimeOrMadePurchase, SdName, SdAndRole, 
            TheType, Description, NumberOfUnits, RateForDate, LineTotal, RatesMissing)
		SELECT 
			vil.InvoiceLineId AS InvoiceLineId,
			"Time Related Cost" AS LineType,
			vppru.ActivityDate AS ActivityDate, 
			vppru.ProjectName AS ProjectName, 
			vppru.StageName AS StageName,
			vppru.SystemNameThatBookedTime AS PartBookedTimeOrMadePurchase,
			vppru.SdName AS SdName,
			CONCAT(vppru.SdId ," - " , vppru.SdAndRole) AS SdAndRole,
			vppru.RemunerationTypeName AS TheType, 
			vppru.Description AS Description, 
			vppru.NumberOfUnits as NumberOfUnits, 
			vppru.RateForDate as RateForDate,
			(vppru.NumberOfUnits * vppru.RateForDate) AS LineTotal,
            0 as RatesMissing

		FROM 	
			vPPIndividualRatesUpline vppru
			JOIN vInvoiceLine vil ON (vppru.agreementBetweenParticipantsId = vil.agreementBetweenParticipantsId)
		WHERE  	
			vil.InvoiceId = pInvoiceId
            and vil.LineType = "Time Related Cost" 
			and vppru.ActivityDate <= vDateTo			
            and vil.RatesMissing = 0
            and vppru.RateForDate >= 0
		ORDER by activityDate;
        
		-- ----------------------------------------------
        -- TIME COST DETAILS: - Rates Not Available
		insert into InvoiceLineDetail
			(InvoiceLineId, LineType, ActivityDate, ProjectName, StageName, PartBookedTimeOrMadePurchase, SdName, SdAndRole, 
            TheType, Description, NumberOfUnits, RateForDate, LineTotal, RatesMissing)
		SELECT 
			vil.InvoiceLineId AS InvoiceLineId,
			"Time Related Cost" AS LineType,
			vppru.ActivityDate AS ActivityDate, 
			vppru.ProjectName AS ProjectName, 
			vppru.StageName AS StageName,
			vppru.SystemNameThatBookedTime AS PartBookedTimeOrMadePurchase,
			vppru.SdName AS SdName,
			CONCAT(vppru.SdId ," - " , vppru.SdAndRole) AS SdAndRole,
			vppru.RemunerationTypeName AS TheType, 
			vppru.Description AS Description, 
			vppru.NumberOfUnits as NumberOfUnits, 
			vppru.RateForDate as RateForDate,
			(vppru.NumberOfUnits * vppru.RateForDate) AS LineTotal,
			1 as RatesMissing
		FROM 	
			vPPIndividualRatesUpline vppru
			JOIN vInvoiceLine vil ON (vppru.agreementBetweenParticipantsId = vil.agreementBetweenParticipantsId)
		WHERE  	
			vil.InvoiceId = pInvoiceId
            and vil.LineType = "Time Related Cost" 
			and vppru.ActivityDate <= vDateTo			
            and vil.RatesMissing > 0
            and vppru.RateForDate < 0
		ORDER by activityDate;

        
		-- ----------------------------------------------
        -- EXPENSE DETAILS: Rates Available
		insert into InvoiceLineDetail
			(InvoiceLineId, LineType, ActivityDate, ProjectName, StageName, PartBookedTimeOrMadePurchase, SdName, SdAndRole, 
            TheType, Description, NumberOfUnits, RateForDate, LineTotal, RatesMissing)
		SELECT 
			vil.InvoiceLineId AS InvoiceLineId,
			"Expense(s) Incurred" AS LineType,
			vpperu.PurchaseDate AS ActivityDate, 
			vpperu.ProjectName AS ProjectName, 
			null  AS StageName, -- StageName
			vpperu.ParticipantMadeOrigPayment AS PartBookedTimeOrMadePurchase, 
			null AS SdName,
			null AS SdAndRole,
			vpperu.ExpenseTypeName AS TheType,
			vpperu.PaymentDescription AS Description, 
			vpperu.NumberOfUnits AS NumberOfUnits, 
			vpperu.ExpenseRateForDate AS RateForDate,
			(vpperu.NumberOfUnits * vpperu.ExpenseRateForDate) AS LineTotal,
            0 as RatesMissing

		FROM 	vPpExpenseRateUplineRecursive vpperu
			JOIN vInvoiceLine vil ON (vpperu.agreementBetweenParticipantsId = vil.agreementBetweenParticipantsId)
		WHERE  	
			vil.InvoiceId = pInvoiceId
            and vil.LineType = "Expense(s) Incurred"
			and vil.LineAmount is not null
            and vpperu.ExpenseRateForDate >= 0
			and vpperu.PurchaseDate <= vDateTo			
		ORDER by vpperu.PurchaseDate;

		-- ----------------------------------------------
        -- EXPENSE DETAILS: Rates Not Available
		insert into InvoiceLineDetail
			(InvoiceLineId, LineType, ActivityDate, ProjectName, StageName, PartBookedTimeOrMadePurchase, SdName, SdAndRole, 
            TheType, Description, NumberOfUnits, RateForDate, LineTotal, RatesMissing)
		SELECT 
			vil.InvoiceLineId AS InvoiceLineId,
			"Expense(s) Incurred" AS LineType,
			vpperu.PurchaseDate AS ActivityDate, 
			vpperu.ProjectName AS ProjectName, 
			null  AS StageName, -- StageName
			vpperu.ParticipantMadeOrigPayment AS PartBookedTimeOrMadePurchase, 
			null AS SdName,
			null AS SdAndRole,
			vpperu.ExpenseTypeName AS TheType,
			vpperu.PaymentDescription AS Description, 
			vpperu.NumberOfUnits AS NumberOfUnits, 
			vpperu.ExpenseRateForDate AS RateForDate,
			(vpperu.NumberOfUnits * vpperu.ExpenseRateForDate) AS LineTotal,
            1 as RatesMissing

		FROM 	vPpExpenseRateUplineRecursive vpperu
			JOIN vInvoiceLine vil ON (vpperu.agreementBetweenParticipantsId = vil.agreementBetweenParticipantsId)
		WHERE  	
			vil.InvoiceId = pInvoiceId
            and vil.LineType = "Expense(s) Incurred"
			and vil.LineAmount is null
            and vpperu.ExpenseRateForDate < 0
			and vpperu.PurchaseDate <= vDateTo			
		ORDER by vpperu.PurchaseDate;
	commit;

END