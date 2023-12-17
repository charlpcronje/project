-- drop function if exists ig_db.doDatesOverlapResourceRemuneration;

delimiter $$;

create function ig_db.doDatesOverlapResourceRemuneration(pStartDate date, pEndDate date) returns int 
reads sql data
begin
	declare vOverlapCount int;
    
    select count(1) into vOverlapCount from ResourceRemuneration where 
              ((StartDate <= pStartDate) and (EndDate >= pEndDate)) 
              or (pStartDate between StartDate and EndDate) 
              or (pEndDate between StartDate and EndDate);
	
    return vOverlapCount > 0;
end;
$$


-- select ig_db.doDatesOverlapResourceRemuneration('1990-12-21', '1990-12-24')
