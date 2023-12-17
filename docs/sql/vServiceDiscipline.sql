SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW `vServiceDiscipline` AS
WITH RECURSIVE vsd (
		ParentName,
		ServiceDisciplineId, 
		ServiceDisciplineCode, 
		ServiceDisciplineName, 
        Level, 
        Name, 
        Description, 
        ServiceDisciplineIdParent, 
		ParentSdCode,
        ParentSdName,
        ServiceDisciplineIdIndustry, 
        IndustrySdName,
        AnyChildren, 
        RowOrderNo, 
        OrderNumber, 
        AllowRoles)
AS ( SELECT 
        CONCAT(sd.ServiceDisciplineIdParent, ": ", sd.Name) as ParentName, -- Although it will be null, this sets the correct length for the data
		sd.ServiceDisciplineId as ServiceDisciplineId, 
		sd.ServiceDisciplineCode as ServiceDisciplineCode, 
        sd.Name as ServiceDisciplineName, 
        1 as Level, 
        CONCAT(sd.ServiceDisciplineCode, ": ", sd.Name) as Name, 
        sd.Description as Desciption, 
        sd.ServiceDisciplineIdParent as ServiceDisciplineIdParent, 
        CONCAT(sd.ServiceDisciplineIdParent, ": ", sd.ServiceDisciplineCode) as ParentSdCode, -- Although it will be null, this sets the correct length for the data
        CONCAT(sd.ServiceDisciplineIdParent, ": ", sd.Name) as ParentSdName, -- Although it will be null, this sets the correct length for the data
        sd.ServiceDisciplineIdIndustry as ServiceDisciplineIdIndustry, 
        sd.Name as IndustrySdName, -- Highest level is the industry
        (SELECT IF((COUNT(1) > 0), 'Y', 'N') FROM ServiceDiscipline sdd
            WHERE
                (sdd.ServiceDisciplineIdParent  = sd.ServiceDisciplineId )) AS AnyChildren ,
		((1 * 1000000) + (sd.OrderNumber * 1000000)) RowOrderNo,
        sd.OrderNumber,
        sd.AllowRoles
     FROM   
		ig_db.ServiceDiscipline sd 
     WHERE 
		sd.ServiceDisciplineIdParent is null
     UNION ALL
     
     SELECT 
		yd.Name as ParentName,
		sd2.ServiceDisciplineId as ServiceDisciplineId,
		sd2.ServiceDisciplineCode as ServiceDisciplineCode, 
        sd2.Name, (yd.Level + 1) as Level, 
        CONCAT(sd2.ServiceDisciplineCode, ": ", sd2.Name) as Name, 
        sd2.Description as Description, 
        sd2.ServiceDisciplineIdParent as ServiceDisciplineIdParent, 
        sd2parent.ServiceDisciplineCode as ParentSdCode,
        sd2parent.Name as ParentSdName,
        sd2.ServiceDisciplineIdIndustry,
        sd2industry.Name as IndustrySdName,
        (SELECT IF((COUNT(1) > 0), 'Y', 'N') FROM ServiceDiscipline ssd2
            WHERE
                (ssd2.ServiceDisciplineIdParent = sd2.ServiceDisciplineId)) AS AnyChildren,
		((yd.RowOrderNo + 1) + (sd2.OrderNumber * (case when yd.Level > 1 then 100 else 10000 end))) RowOrderNo,
        sd2.OrderNumber,
        sd2.AllowRoles
	 FROM
		vsd AS yd
			JOIN ig_db.ServiceDiscipline AS sd2 			ON sd2.ServiceDisciplineIdParent = yd.ServiceDisciplineId
            JOIN ig_db.ServiceDiscipline as sd2parent 		ON sd2.ServiceDisciplineIdParent = sd2parent.ServiceDisciplineId
            JOIN ig_db.ServiceDiscipline as sd2industry		ON sd2.ServiceDisciplineIdIndustry = sd2industry.ServiceDisciplineId
     
	)
SELECT 
	*
FROM vsd
GO




