SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = root@localhost 
    SQL SECURITY DEFINER
VIEW vProcedureRelatedDocs AS
    SELECT 
	p.ProcedureRelatedDocsId  	AS ProcedureRelatedDocsId,
 	p.GenProcedureId  	AS GenProcedureId,
	p.RelatedDocsId  	AS RelatedDocsId,
	r.FileName			AS FileName,
	r.Description		AS Description,
	r.FolderPath		AS FolderPath

    FROM
        ProcedureRelatedDocs p
        JOIN RelatedDocs r ON (p.RelatedDocsId = r.RelatedDocsId)
        