CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vParticipantMin AS
    SELECT 
        p.ParticipantId AS ParticipantId,
        p.SystemName AS SystemName,
        p.RegisteredName AS RegisteredName,
        p.TradingName AS TradingName,
        p.LatestProjectNumber,
        p.ProjectPrefix,
        p.ProjectPostfix,
        iv.IndividualId AS IndividualId,
        iv.FirstName AS FirstName,
        iv.NickName AS NickName,
        iv.LastName AS LastName,
        iv.Initials AS Initials,
        iv.IdNumber AS IdNumber,
        CONCAT(iv.NickName, ' ', iv.LastName) AS IndividualName,
        CONCAT(p.RegisteredName, ' trading as ', p.TradingName) AS NonIndivName,
        
        p.RepresentativeIndividualId AS RepresentativeIndividualId,
        CONCAT(ri.NickName, ' ', ri.LastName) AS Representative,
        p.MarketingIndividualId AS MarketingIndividualId,
        CONCAT(mi.FirstName, ' ', mi.LastName) AS Marketer,
        p.ParticipantTypeCode AS ParticipantTypeCode,
        pt.TypeName AS ParticipantTypeName,
        p.IsIndividual AS IsIndividual,
        p.TapAdministered AS TapAdministered,
        p.TapSubscriptionCode AS TapSubscriptionCode,
        ps.Name AS TapSubscriptionName,
		p.EmailAddressAccounts 	AS EmailAddressAccounts,
        iv.IsActiveMember AS IsActiveMember,
        vs.CityId_Name 	as ContactPointCityName,
        vs.CountryId_Name 	as ContactPointCountryName,
        cp.EmailAddress,
        cp.PhoneNumber,
		p.VatNumber
        
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
        