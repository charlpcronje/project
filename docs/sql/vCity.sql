SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vCity AS

    SELECT
		c.CityId
        ,c.Name
		,c.Latitude
		,c.Longitude,

		pv.ProvinceId, 
        pv.ProvinceCode, 
        pv.Name as ProvinceId_Name, 
        pv.ProvinceCodeAndName, 
        pv.ProvinceNameAndCode,

        pv.CountryId, 
        pv.CountryId_Name, 
        pv.CountryCode, 
        pv.CountryCodeAndName, 
        pv.CountryNameAndCode
        

	FROM
		City c
		LEFT JOIN vProvince pv	ON (c.ProvinceId = pv.ProvinceId)
