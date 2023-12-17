
-- shows permissions in menus that do not exist in permissions table
select u.* from ig_db.UiComponent u where u.RequiredPermissionId not in (select p.PermissionId from ig_db.Permission p);

