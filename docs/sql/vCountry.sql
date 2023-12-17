SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW vCountry AS

SELECT 	
	c.CountryId,
    c.CountryCode,
    c.Name,
    CONCAT(c.CountryCode, " - ", c.Name) as CountryCodeAndName,
    CONCAT(c.Name, " (", c.CountryCode, ")") as CountryNameAndCode
FROM 	Country c;
