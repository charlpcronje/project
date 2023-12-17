SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vSuburb AS

    SELECT
		s.SuburbId,
        s.Name, 
        s.BoxCode, 
        s.StrCode,
        
   		cv.CityId,
        cv.Name as CityId_Name,
		cv.Latitude,
		cv.Longitude,

		cv.ProvinceId, 
        cv.ProvinceCode, 
        cv.ProvinceId_Name, 
        cv.ProvinceCodeAndName, 
        cv.ProvinceNameAndCode,
        
        cv.CountryId, 
        cv.CountryId_Name, 
        cv.CountryCode, 
        cv.CountryCodeAndName, 
        cv.CountryNameAndCode

	FROM
		Suburb s
		JOIN vCity 	cv	ON (s.CityId = cv.CityId)
