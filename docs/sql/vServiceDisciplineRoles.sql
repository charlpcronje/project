SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vServiceDisciplineRoles AS
select 
    vsd.ServiceDisciplineName as SdName, 
	r.SdRoleId, 
    r.ServiceDisciplineId, 
    r.RoleOnAProjectId,
    
    rop.Name as RoleOnAProjectName,

    vsd.AnyChildren, 
    vsd.RowOrderNo, 
    vsd.OrderNumber, 
    vsd.AllowRoles
from
	SdRole r
    JOIN vServiceDiscipline vsd ON (r.ServiceDisciplineId = vsd.ServiceDisciplineId)
    JOIN RoleOnAProject rop ON (r.RoleOnAProjectId = rop.RoleOnAProjectId);




