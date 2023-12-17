SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vCityMin AS

    SELECT
		c.CityId
        ,c.Name
		,c.ProvinceId
        
	FROM
		City c
