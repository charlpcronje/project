CREATE DEFINER=`administrator`@`localhost` FUNCTION `getProjectStageForDate`(pProjectId BIGINT, pDate DATETIME) RETURNS varchar(50) CHARSET utf8 COLLATE utf8_bin
    NO SQL
BEGIN
	declare vProjectStageName varchar(50);
	declare vNumberOfStages int;
    
    -- First check if there are any Project Stages for this project
    select count(*) into vNumberOfStages
		from ProjectStage ps
		where ps.ProjectId = pProjectId;
		
    if vNumberOfStages = 0 then
		return null;
	end if;	
	
    -- If the EndDate is null or after pDate, it is the current status
    -- But first make sure there are not more than one date range!
    select count(ps.StageName) into vNumberOfStages
    from 	ProjectStage ps
    where 	ps.ProjectId = pProjectId
    and    ((ps.StartDate <= pDate) and ((ps.EndDate is null) or (ps.EndDate >= pDate)));

    if vNumberOfStages = 0 then
		return null;
	end if;	
    if vNumberOfStages > 1 then
		return "Multiple Project Stages for date";
	end if;	

    -- If the EndDate is null or after pDate, it is the current status
    -- But first make sure there are not more than one date range!
    select 	ps.StageName into vProjectStageName
    from 	ProjectStage ps
    where 	ps.ProjectId = pProjectId
	and    ((ps.StartDate <= pDate) and ((ps.EndDate is null) or (ps.EndDate >= pDate)));

	if vProjectStageName is not null then
		return vProjectStageName;
	end if;

    -- All the date ranges for the Project's Stages are closed
	select count(pst.StageName) into vNumberOfStages
    from ProjectStage ps, ProjectStageType pst
    where ps.ProjectId = pProjectId
    and ps.EndDate = (select max(px.EndDate) from ProjectStage px where px.ProjectId = pProjectId);

    if vNumberOfStages = 0 then
		return null;
	end if;	
    
    if vNumberOfStages > 1 then
		return "Multiple Project Stages for date";
	end if;	

    -- All the date ranges for the Project's Stages are closed
	select pst.StageName into vProjectStageName
    from 	ProjectStage ps
    where 	ps.ProjectId = pProjectId
    and ps.EndDate = (select max(px.EndDate) from ProjectStage px where px.ProjectId = pProjectId);
    
	if vProjectStageName is not null then
		return vProjectStageName;
	end if;

END