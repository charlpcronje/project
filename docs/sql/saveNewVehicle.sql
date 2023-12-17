CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveNewVehicle`(
						`pQuery` JSON, `pUserId` VARCHAR(50), 
                        OUT `pVehicleId` BIGINT)
BEGIN

	declare vVehicleModelId bigint;
	declare vVehicleTyreAndRimTypeId bigint;
	declare vName varchar(255);
	declare vLicenceNumber varchar(255);
	declare vRegistrationYear int;
	declare vLicenceExpiryDate datetime;
	declare vVehicleRegisterNumber varchar(255);
	declare vVINNumber varchar(255);
	declare vEnginNumber varchar(255);
	declare vLicenceDiskScan varchar(255);
	declare vRegistrationDocument varchar(255);
	declare vNextServiceDate datetime;
	declare vNextServiceOdoReading int;
	

	declare vAssetConditionId bigint;
	declare vAssetStatusId bigint;
	declare vParticipantIdOriginalOwner bigint;
	declare vParticipantIdCurrentOwner bigint;
	declare vParticipantIdSponsor bigint;
	declare vParticipantIdSoldTo bigint;

	declare vParticipantOfficeIdLocation bigint;

	declare vParticipantRightOfUse varchar(10);
	declare vAssetImage varchar(255);
	declare vInvoiceImage varchar(255);
	declare vPurchaseAmount decimal(12,2);
	declare vGuaranteePeriodEnd datetime;
	declare vGuaranteeCertificate varchar(255);
	declare vAssetStatus varchar(255);
	declare vAssetAquiredDate datetime;
	declare vOwnershipToSponsorDate datetime;
	declare vAssetRemovedDate datetime;
	declare vAssetSoldAmount decimal(12,2);	
    
	
	
	-- Work variables
    declare usernameCount int;
    declare systemNameCount int;
	declare tmp varchar(50);
   declare vLicenceExpiryDateString varchar(20);
   declare vNextServiceDateString varchar(20);
   declare vGuaranteePeriodEndString varchar(20);
   declare vAssetAquiredDateString varchar(20);
   declare vOwnershipToSponsorDateString varchar(20);
   declare vAssetRemovedDateString varchar(20);
   
   declare vNextServiceOdoReadingString varchar(20);
   
   declare vParticipantIdSponsorString varchar(20);
   declare vParticipantIdSoldToString varchar(20);
   declare vParticipantOfficeIdLocationString varchar(20);
   
   declare vPurchaseAmountString varchar(20);
   declare vAssetSoldAmountString	varchar(20);
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	set vPurchaseAmountString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.purchaseAmount'));
    set vPurchaseAmount	= if(vPurchaseAmountString = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.purchaseAmount')));
	set vAssetSoldAmountString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.assetSoldAmount'));
    set vAssetSoldAmount	= if(vAssetSoldAmountString = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.assetSoldAmount')));    

	set vParticipantIdSponsorString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.participantIdSponsor'));
    set vParticipantIdSponsor	= if(vParticipantIdSponsorString = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.participantIdSponsor')));
	set vParticipantIdSoldToString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.participantIdSoldTo'));
    set vParticipantIdSoldTo	= if(vParticipantIdSoldToString = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.participantIdSoldTo')));
	set vParticipantOfficeIdLocationString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.participantOfficeIdLocation'));
    set vParticipantOfficeIdLocation	= if(vParticipantOfficeIdLocationString = '', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.participantOfficeIdLocation')));    
    
	set vLicenceExpiryDateString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.licenceExpiryDate'));
    set vLicenceExpiryDate	= if(vLicenceExpiryDateString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.licenceExpiryDate')));
    
	set vNextServiceDateString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.nextServiceDate'));
    set vNextServiceDate	= if(vNextServiceDateString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.nextServiceDate')));
    
	set vGuaranteePeriodEndString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.guaranteePeriodEnd'));
    set vGuaranteePeriodEnd	= if(vGuaranteePeriodEndString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.guaranteePeriodEnd')));
    
	set vAssetAquiredDateString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.assetAquiredDate'));
    set vAssetAquiredDate	= if(vAssetAquiredDateString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.assetAquiredDate')));
    
	set vOwnershipToSponsorDateString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.ownershipToSponsorDate'));
    set vOwnershipToSponsorDate	= if(vOwnershipToSponsorDateString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.ownershipToSponsorDate')));
    
	set vAssetRemovedDateString = JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.assetRemovedDate'));
    set vAssetRemovedDate	= if(vAssetRemovedDateString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.assetRemovedDate')));

	set vNextServiceOdoReadingString = convert(JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.nextServiceOdoReading')), char);
    set vNextServiceOdoReading	= if(vNextServiceOdoReadingString = 'null', null, JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.nextServiceOdoReading')));
    
	set vVehicleModelId				= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.vehicleModelId'));
	set vVehicleTyreAndRimTypeId	= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.vehicleTyreAndRimTypeId'));
	set vName						= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.name'));
	set vLicenceNumber				= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.licenceNumber'));
	set vRegistrationYear			= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.registrationYear'));
-- 	set vLicenceExpiryDate			= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.licenceExpiryDate'));
	set vVehicleRegisterNumber		= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.vehicleRegisterNumber'));
	set vVINNumber					= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.vINNumber'));
	set vEnginNumber				= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.enginNumber'));
	set vLicenceDiskScan			= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.licenceDiskScan'));
	set vRegistrationDocument		= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.registrationDocument'));
-- 	set vNextServiceDate			= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.nextServiceDate'));
-- 	set vNextServiceOdoReading		= JSON_UNQUOTE(JSON_EXTRACT(pQuery,'$.nextServiceOdoReading'));
    
	set vAssetConditionId           = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assetConditionId'));
	set vAssetStatusId              = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assetStatusId'));
	set vParticipantIdOriginalOwner   = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdOriginalOwner'));
	set vParticipantIdCurrentOwner    = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdCurrentOwner'));
-- 	set vParticipantIdSponsor         = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdSponsor'));
-- 	set vParticipantIdSoldTo          = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantIdSoldTo'));
-- 	set vParticipantOfficeIdLocation  = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantOfficeIdLocation'));

	set vParticipantRightOfUse        = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantRightOfUse'));
	set vAssetImage                   = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.apssetImage'));
	set vInvoiceImage                 = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.invoiceImage'));
-- 	set vPurchaseAmount               = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.purchaseAmount'));
-- 	set vGuaranteePeriodEnd           = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.guaranteePeriodEnd')); 
	set vGuaranteeCertificate         = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.guaranteeCertificate'));
	set vAssetStatus                  = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assetStatus'));
-- 	set vAssetAquiredDate             = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assetAquiredDate')); 
-- 	set vOwnershipToSponsorDate       = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.ownershipToSponsorDate')); 
-- 	set vAssetRemovedDate             = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assetRemovedDate'));
-- 	set vAssetSoldAmount              = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assetSoldAmount'));

    
    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;
    


	-- 	-- ig_db.Vehicle
	-- 	-- --------------------------
	
	insert into ig_db.Vehicle
		(VehicleModelId, VehicleTyreAndRimTypeId, Name, LicenceNumber, RegistrationYear, LicenceExpiryDate, VehicleRegisterNumber, VINNumber, EnginNumber, LicenceDiskScan, RegistrationDocument, NextServiceDate, NextServiceOdoReading, LastUpdateTimestamp, LastUpdateUserName)
		values
		(vVehicleModelId, vVehicleTyreAndRimTypeId, vName, vLicenceNumber, vRegistrationYear, vLicenceExpiryDate, vVehicleRegisterNumber, vVINNumber, vEnginNumber, vLicenceDiskScan, vRegistrationDocument, vNextServiceDate, vNextServiceOdoReading, current_timestamp(), pUserId);
	set pVehicleId = last_insert_id();



    -- ig_db.Asset
    -- --------------------------
 

    insert into ig_db.Asset 
        (AssetTypeId, AssetConditionId, AssetStatusId, ParticipantIdOriginalOwner, ParticipantIdCurrentOwner, ParticipantIdSponsor, ParticipantIdSoldTo, VehicleId, ParticipantOfficeIdLocation, AssetNumber, Description, BrandOrMake, SerialNumber, ParticipantRightOfUse, AssetImage, InvoiceImage, PurchaseAmount, GuaranteePeriodEnd, GuaranteeCertificate, AssetStatus, AssetAquiredDate, OwnershipToSponsorDate, AssetRemovedDate, AssetSoldAmount, LastUpdateTimestamp, LastUpdateUserName) 
        values 
        ('VEHICLE', vAssetConditionId, vAssetStatusId, vParticipantIdOriginalOwner, vParticipantIdCurrentOwner, vParticipantIdSponsor, vParticipantIdSoldTo, pVehicleId, vParticipantOfficeIdLocation, null, null, null, null, vParticipantRightOfUse, vAssetImage, vInvoiceImage, vPurchaseAmount, vGuaranteePeriodEnd, vGuaranteeCertificate, vAssetStatus, vAssetAquiredDate, vOwnershipToSponsorDate, vAssetRemovedDate, vAssetSoldAmount, current_timestamp(), pUserId);
 
    

    
	commit;

END