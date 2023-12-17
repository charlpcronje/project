CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vParticipant AS
    SELECT 
        p.ParticipantId AS ParticipantId,
        p.ProjectIdSustenance AS ProjectIdSustenance,
        p.ParticipantIdBUParent AS ParticipantIdBUParent,
        p.ParticipantTypeCode AS ParticipantTypeCode,
        p.TapSubscriptionCode AS TapSubscriptionCode,
        p.RepresentativeIndividualId AS RepresentativeIndividualId,
        CONCAT(ri.FirstName, ' ', ri.LastName) AS Representative,
        p.MarketingIndividualId AS MarketingIndividualId,
        CONCAT(mi.FirstName, ' ', mi.LastName) AS Marketer,
        p.ParticipantOfficeIdDefault AS ParticipantOfficeIdDefault,
        p.ParticipantBankDetailsIdDefault AS ParticipantBankDetailsIdDefault,
        p.SystemName AS SystemName,
        p.RegisteredName AS RegisteredName,
        p.TradingName AS TradingName,
        p.ProjectPrefix AS ProjectPrefix,
        p.LatestProjectNumber AS LatestProjectNumber,
        p.ProjectPostfix AS ProjectPostfix,
        p.StartDate AS StartDate,
        p.EndDate AS EndDate,
        p.IsIndividual AS IsIndividual,
        p.TapAdministered AS TapAdministered,
        p.RegistrationNumber AS RegistrationNumber,
        p.VatNumber AS VatNumber,
        p.DefaultInvoiceDay AS DefaultInvoiceDay,
        p.ParticipantLogo AS ParticipantLogo,
        iv.IndividualId AS IndividualId,
        iv.FirstName AS FirstName,
        iv.SecondName AS SecondName,
        iv.ThirdName AS ThirdName,
        iv.NickName AS NickName,
        iv.LastName AS LastName,
        iv.Initials AS Initials,
        iv.IdNumber AS IdNumber,
        iv.PassportNumber AS PassportNumber,
        iv.CountryId AS CountryId,
        iv.Name AS CountryName,
        iv.IsActiveMember AS IsActiveMember,
        iv.AllowLoginFlag AS AllowLoginFlag,
        iv.IncomeTaxNumber AS IncomeTaxNumber,
        iv.Pass AS Pass,
        iv.UserName AS UserName,
        iv.PasswordResetToken AS PasswordResetToken,
        iv.PasswordResetExpiryDate AS PasswordResetExpiryDate,
        iv.LastLoginTimestamp AS LastLoginTimestamp,
        iv.SystemMemberId AS SystemMemberId,
        iv.SystemMemberStartDate AS SystemMemberStartDate,
        iv.SystemMemberEndDate AS SystemMemberEndDate,
        iv.RoleCode AS RoleCode,
        iv.RoleName AS RoleName,
		iv.MedicalName,
		iv.MedicalNumber,
        ps.Name AS TapSubscriptionName,
        pt.TypeName AS ParticipantTypeName,
        po.ParticipantOfficeId AS ParticipantOfficeId,
        po.ContactPointIdDefault AS ContactPointIdDefault,
        po.Name AS ParticipantOfficeName,
        po.Description AS ParticipantOfficeDescription,
        cp.Name AS ContactPointName,

		cp.SuburbId  	as ContactPointSuburbId,
		vs.Name  		as ContactPointSuburbName,
        vs.CityId	 	as ContactPointCityId,
        vs.CityId_Name 	as ContactPointCityName,
        vs.ProvinceId 	as ContactPointProvinceId,
        vs.ProvinceId_Name 	as ContactPointProvinceName,
        vs.CountryId 		as ContactPointCountryId,
        vs.CountryId_Name 	as ContactPointCountryName,

        cp.EmailAddress AS EmailAddress,
        cp.AddressLine1 AS AddressLine1,
        cp.AddressLine2 AS AddressLine2,
        cp.AddressLine3 AS AddressLine3,
        cp.PhoneNumber AS PhoneNumber,
        CONCAT(pbd.AccountHolderName,
                ', ',
				pbd.BankName,		-- Bank Name
                ', Branch code: ',
                pbd.BranchName, 
                ', ',
				pbd.AccountName, -- Account Type
                ', Account no: ',
				pbd.AccountNumber) AS BankDetails,
        pr.ProjectNameText AS ProjectSustenanceName,
		
		p.InvoicePrefix 		AS InvoicePrefix,
		p.LatestInvoiceNumber 	AS LatestInvoiceNumber,
		p.InvoiceNumberFormat 	AS InvoiceNumberFormat,
		
		p.EmailAddressAccounts 	AS EmailAddressAccounts
		
    FROM
        Participant p
        LEFT JOIN Project pr ON (p.ProjectIdSustenance = pr.ProjectId)
        LEFT JOIN vIndividual iv ON (p.ParticipantId = iv.ParticipantId)
        LEFT JOIN ParticipantOffice po ON (p.ParticipantOfficeIdDefault = po.ParticipantOfficeId)
        LEFT JOIN Individual ri ON (p.RepresentativeIndividualId = ri.IndividualId)
        LEFT JOIN Individual mi ON (p.MarketingIndividualId = mi.IndividualId)
        LEFT JOIN ContactPoint cp ON (po.ContactPointIdDefault = cp.ContactPointId)
		LEFT JOIN vSuburb	vs ON (cp.SuburbId = vs.SuburbId)
        LEFT JOIN TapSubscription ps ON (p.TapSubscriptionCode = ps.TapSubscriptionCode)
        LEFT JOIN ParticipantType pt ON (p.ParticipantTypeCode = pt.ParticipantTypeCode)
        LEFT JOIN vParticipantBankDetails pbd ON (p.ParticipantBankDetailsIdDefault = pbd.ParticipantBankDetailsId)
        