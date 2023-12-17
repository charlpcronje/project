UserName) values (",     >>    UserName) values (
," xxx'",                 >>    , '
,'xxx',                   >>    ', '
,"'xxx ",                 >>    ', 
," xxx ",                 >>    , 
,""yyy)"                  >>    ');
, '"                      >>    , '
"', '                     >>    ', '
"INSERT into              >>    INSERT into

SELECT "INSERT into  UiComponent (UiComponentId, UiComponentTitle, UiComponentLink, OrderNo, ParentUiComponentId, ActiveInd, PermissionIdRequired, IconClassName, LastUpdateTimestamp, LastUpdateUserName) values (", UiComponentId, " xxx'", UiComponentTitle, "'xxx'", UiComponentLink, "'xxx ", OrderNo, " xxx ", ParentUiComponentId, " xxx'", ActiveInd, "'xxx'", PermissionIdRequired, "'xxx'", IconClassName, "'xxx'", LastUpdateTimestamp, "'xxx'", LastUpdateUserName, """yyy)" FROM ig_db.uicomponent;