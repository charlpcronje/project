CREATE OR REPLACE VIEW ig_db.vLibraryFolders AS
	WITH RECURSIVE tree_view AS (
		SELECT
			lf.libraryFolderId,
			lf.parentFolderId,
			lf.folderName,
			lf.description,
			lf.LastUpdateTimestamp,
			lf.lastUpdateUserName,
            (cast(lf.folderName as char(4000))) as pathName,
			0 AS level,
			(cast(lpad(lf.libraryFolderId, 10, 0) as char(100))) AS rowOrder,
            (select count(1) from ig_db.LibraryFolder x where x.parentFolderId = lf.libraryFolderId) as childCount
		FROM ig_db.LibraryFolder lf
		WHERE lf.parentFolderId IS NULL
		 
	UNION ALL
	 
		SELECT 
			 p.libraryFolderId,
			 p.parentFolderId,
			 p.folderName,
			 p.description,
			 p.LastUpdateTimestamp,
			 p.lastUpdateUserName,
             concat(pathName, '\\', p.folderName) as pathName,
			 level + 1 AS level,
			 concat(cast(lpad(rowOrder, 10, 0) as char(100)), '_' , cast(lpad(p.libraryFolderId, 10, 0) as char(100))) AS rowOrder,
             (select count(1) from ig_db.LibraryFolder x where x.parentFolderId = p.libraryFolderId) as childCount
		FROM ig_db.LibraryFolder p
		JOIN tree_view tv
		  ON p.parentFolderId = tv.libraryFolderId
	)
	  
	SELECT
		libraryFolderId,
		parentFolderId,
		folderName,
		description,
        lastUpdateTimestamp,
        lastUpdateUserName,
		(level + 1) as level,
        pathName,
		rowOrder,
        childCount
	FROM tree_view
	ORDER BY rowOrder;

-- select * from vLibraryFolders order by rowOrder;
