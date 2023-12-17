SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vContactPoint AS

    SELECT
		c.ContactPointId
		,c.Name
		,c.ParticipantOfficeId
			,po.Name as ParticipantOfficeId_Name
		,c.EmailAddress
		,c.PhoneNumber
		,c.AddressLine1
		,c.AddressLine2
		,c.AddressLine3

		,c.SuburbId
			,ss.Name as SuburbId_Name
		,ss.CityId
		,ss.CityId_Name as CityId_Name
		,ss.ProvinceId_Name as ProvinceId_Name
		,ss.CountryId_Name as CountryId_Name
		,ss.ProvinceId as ProvinceId
		,ss.CountryId  as CountryId

	FROM
		ContactPoint c
		LEFT JOIN ParticipantOffice po		ON (c.ParticipantOfficeId = po.ParticipantOfficeId)
		LEFT JOIN vSuburb ss		ON (c.SuburbId = ss.SuburbId)
        ;

