Name) values ('",         >>   Name) values ('
,'xxx',                   >>    ', '
, '"                      >>    , '
"', '                     >>    ', '
,""yyy)"                  >>    ');
"INSERT into              >>    INSERT into

SELECT "INSERT into  Permission ( PermissionId, Name, Description, LastUpdateTimestamp, LastUpdateUserName) values ('", PermissionId, "'xxx'", Name, "'xxx'", Description, "'xxx'", LastUpdateTimestamp, "'xxx'", LastUpdateUserName, """yyy)" FROM ig_db.permission;