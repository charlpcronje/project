CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteServiceDiscipline`(`pServiceDisciplineId` BIGINT)
BEGIN
    declare vCount integer;
    declare vCount2 integer;
    declare vErrorMessage varchar(100);
	declare vServiceDisciplineIdParent bigint;
	declare vServiceDisciplineIdIndustry bigint;
	declare vUpdateParent integer default 0;
	declare vTopLevelSd integer default 0;
	
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	set vUpdateParent = 0;
	
	SELECT ServiceDisciplineIdParent into vServiceDisciplineIdParent
	FROM ig_db.ServiceDiscipline
	WHERE ServiceDisciplineId = pServiceDisciplineId;
	
	select count(*) into vCount2 from ig_db.ServiceDiscipline
	where ServiceDisciplineIdParent = vServiceDisciplineIdParent;
	if (vCount2 = 1 ) then 
		set vUpdateParent = 1;
	end if;	
    
      if (pServiceDisciplineId = vServiceDisciplineIdIndustry) then -- Top level
			set vTopLevelSd = 1;
	end if;
	
  	-- Check if the ServiceDisciplineId has any Service children
    select count(*) into vCount from ig_db.ServiceDiscipline
    where ServiceDisciplineIdParent = pServiceDisciplineId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Service Discipline.  It has Child Service Disciplines linked to it.';
	end if;

  	-- Check if the ServiceDisciplineId has any Roles linked to it
    select count(*) into vCount from ig_db.SdRole
    where ServiceDisciplineId = pServiceDisciplineId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Service Discipline.  It has Roles linked to it.';
	end if;

	start transaction;
		if (vTopLevelSd = 0) then -- Top Level, so, set Industry fk to null
			update ig_db.ServiceDiscipline
			set ServiceDisciplineIdIndustry = null
			where ServiceDisciplineId = pServiceDisciplineId;
        else 
			if (vUpdateParent = 1) then -- Dit was die laaste een, so die Parent kan nou weer roles kry.
				update ig_db.ServiceDiscipline
				set AllowRoles = 'Y'
				where ServiceDisciplineId = vServiceDisciplineIdParent;
			end if;
		end if;
		delete from ig_db.ServiceDiscipline where ServiceDisciplineId = pServiceDisciplineId;
	commit;


END