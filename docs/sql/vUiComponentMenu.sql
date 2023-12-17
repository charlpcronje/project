SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
view ig_db.vUiComponentMenu as
-- Source: 
-- Recursive CTE: https://dba.stackexchange.com/questions/194131/parent-child-relation-in-same-table
-- Recursive CTE & Stored Proc alternative: https://stackoverflow.com/questions/4116416/display-parent-child-relationship-when-parent-and-child-are-stored-in-same-table
WITH RECURSIVE uic (UiComponentId, UiComponentTitle, UiComponentLink, OrderNo, ParentUiComponentId, 
         PermissionCodeRequired, ActiveInd, IconClassName, RowOrderNo, MenuLevel)
AS ( SELECT 
         u.UiComponentId, u.UiComponentTitle, u.UiComponentLink, u.OrderNo, u.ParentUiComponentId, 
         u.PermissionCodeRequired, u.ActiveInd, u.IconClassName,
		 ((1 * 1000000) + (u.OrderNo * 1000000)) RowOrderNo, 1 MenuLevel
     FROM   
		ig_db.UiComponent AS u
     WHERE 
		u.ParentUiComponentId is null
	    AND u.ActiveInd = 'Y'
     UNION ALL
     SELECT 
         u2.UiComponentId, u2.UiComponentTitle, u2.UiComponentLink, u2.OrderNo, u2.ParentUiComponentId, 
		 u2.PermissionCodeRequired, u2.ActiveInd, u2.IconClassName, 
		 ((yd.RowOrderNo + 1) + (u2.OrderNo * (case when yd.MenuLevel > 1 then 100 else 10000 end))) RowOrderNo, (yd.MenuLevel + 1) MenuLevel
	 FROM
		uic AS yd
     JOIN
		ig_db.UiComponent AS u2
     ON 
		u2.ParentUiComponentId = yd.UiComponentId
		AND u2.ActiveInd = 'Y' )
SELECT 
	*,
	(select count(1) from ig_db.UiComponent x where x.ParentUiComponentId = UiComponentId) SubitemCount
FROM uic
GO

