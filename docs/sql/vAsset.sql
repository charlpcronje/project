SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = administrator@localhost 
    SQL SECURITY DEFINER
VIEW vAsset AS
	SELECT 
    	a.AssetId, 
        a.AssetTypeId, 
        ast.Name as AssetTypeName,
        
        a.AssetConditionId, 
        ac.Name as AssetConditionName,
        
        a.AssetStatusId, 
        astat.Name as AssetStatusName,

        a.ParticipantIdOriginalOwner, 
        po.SystemName as OriginalOwner,

        a.ParticipantIdCurrentOwner, 
        pco.SystemName as CurrentOwner,

        a.ParticipantIdSponsor, 
        psp.SystemName as Sponsor,

        a.ParticipantIdSoldTo, 
        pst.SystemName as SoldTo,

        a.VehicleId, 
        v.Name as VehicleName,
        v.LicenceNumber as LicenceNumber,
        
        a.ParticipantOfficeIdLocation, 
		pof.Name as ParticipantOfficeName,
        
        a.AssetNumber, 
        a.Description, 
        a.BrandOrMake, 
        a.SerialNumber, 
        a.ParticipantRightOfUse, 

        a.PurchaseAmount, 
        a.GuaranteePeriodEnd, 

        a.AssetAquiredDate, 
        a.OwnershipToSponsorDate,
        a.AssetRemovedDate, 
        a.AssetSoldAmount, 
        a.LastUpdateTimestamp, 
        a.LastUpdateUserName
	FROM 
		Asset a 
		JOIN AssetType ast ON a.AssetTypeId = ast.AssetTypeId
		JOIN AssetCondition ac ON a.AssetConditionId = ac.AssetConditionId
		JOIN AssetStatus astat ON a.AssetStatusId = astat.AssetStatusId
		LEFT JOIN Participant po ON a.ParticipantIdOriginalOwner = po.ParticipantId
		LEFT JOIN Participant pco ON a.ParticipantIdCurrentOwner = pco.ParticipantId
		LEFT JOIN Participant psp ON a.ParticipantIdSponsor = psp.ParticipantId
		LEFT JOIN Participant pst ON a.ParticipantIdSoldTo = pst.ParticipantId
		LEFT JOIN Vehicle v ON a.VehicleId = v.VehicleId
		LEFT JOIN ParticipantOffice pof ON a.ParticipantOfficeIdLocation = pof.ParticipantOfficeId
;

