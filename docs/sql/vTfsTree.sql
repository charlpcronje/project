SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW vTfsTree AS
WITH RECURSIVE vtfs 
	(TypicalFolderStructureId, 
	TfsName,
	Level, 
	OrderNumber,
    PerSdFlag,
	Description,
    
	ServiceDisciplineIdIndustry,
 	TypicalFolderStructureIdParent, 
 	ParentName, 
 	ParentPerSdFlag,
    
	AnyChildren
	)

AS ( SELECT 

		tfs.TypicalFolderStructureId, 
        tfs.Name AS TfsName,
        1 Level, 
		tfs.OrderNumber	as OrderNumber,
        tfs.PerSdFlag AS PerSdFlag,
		tfs.Description	as Description,

        tfs.ServiceDisciplineIdIndustry 	as ServiceDisciplineIdIndustry, 
        tfs.TypicalFolderStructureIdParent 	as TypicalFolderStructureIdParent, 
         concat(tfs.TypicalFolderStructureIdParent, tfs.Name ) AS ParentName, -- Parent Id is null, but, we have to do this to create "space" for children records
        'N' AS ParentPerSdFlag,

        (SELECT IF((COUNT(1) > 0), 'Y', 'N') FROM TypicalFolderStructure tfsnested
            WHERE
                (tfsnested.TypicalFolderStructureIdParent = tfs.TypicalFolderStructureId)) AS AnyChildren
     FROM   
		TypicalFolderStructure tfs
     WHERE 
		tfs.TypicalFolderStructureIdParent is null
        
     UNION ALL
    SELECT 
		tfs2.TypicalFolderStructureId, 
		tfs2.Name as TfsName, 
        (yd.Level + 1) AS Level, 
		tfs2.OrderNumber	as OrderNumber,
        tfs2.PerSdFlag   AS PerSdFlag,
		tfs2.Description	as Description,

        tfs2.ServiceDisciplineIdIndustry 	as ServiceDisciplineIdIndustry, 
        tfs2.TypicalFolderStructureIdParent as TypicalFolderStructureIdParent,
        yd.TfsName  AS ParentName,
        yd.PerSdFlag AS ParentPerSdFlag,

		(SELECT IF((COUNT(1) > 0), 'Y', 'N') 
           FROM TypicalFolderStructure tfsnested2
            WHERE (tfsnested2.TypicalFolderStructureIdParent = tfs2.TypicalFolderStructureId)) AS AnyChildren
	 FROM
		vtfs yd
	 JOIN TypicalFolderStructure tfs2 ON (tfs2.TypicalFolderStructureIdParent = yd.TypicalFolderStructureId)
	)

SELECT 
	*
FROM vtfs
GO






