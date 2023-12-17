SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vProvince AS

    SELECT
		p.ProvinceId
		,p.CountryId
			,cou.Name as CountryId_Name
			,cou.CountryCode as CountryCode
            ,cou.CountryCodeAndName as CountryCodeAndName
            ,cou.CountryNameAndCode as CountryNameAndCode
		,p.ProvinceCode
		,p.Name
			,CONCAT(p.ProvinceCode, " - ", p.Name) as ProvinceCodeAndName
			,CONCAT(p.Name, " (", p.ProvinceCode, ")") as ProvinceNameAndCode
	FROM
		Province p
		LEFT JOIN vCountry cou		ON (p.CountryId = cou.CountryId)
