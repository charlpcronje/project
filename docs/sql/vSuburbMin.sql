SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vSuburbMin AS

    SELECT
		s.SuburbId,
        s.Name, 
   		s.CityId

	FROM
		Suburb s
