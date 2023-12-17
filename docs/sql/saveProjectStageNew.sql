CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveProjectStageNew`(
					pQuery JSON, 
					pUserId VARCHAR(50),
                    OUT pProjectStageId BIGINT)
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- ServiceDiscipline table:
    
    declare vProjectId BIGINT;
    declare vOrderNumber INT;
    declare vStageName varchar(255);
    declare vDescription varchar(255) default null;
    declare vStartDate DATETIME default null;
    declare vEndDate DATETIME default null;
    
    declare tmp VARCHAR(255);

    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    set vProjectId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectId'));
    set vOrderNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.orderNumber'));
    set vStageName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.stageName'));
    set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.startDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vStartDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
    end if;

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.endDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			set vEndDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
    end if;

    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;
    
		insert ig_db.ProjectStage
				(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, ProjectStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			values 
				(vProjectId, vOrderNumber, vStageName, vDescription, vStartDate, vEndDate, null, pUserId, current_timestamp);
		
        set pProjectStageId = last_insert_id();
        
		-- Link the new ProjectStage to all ProjectSds
		insert into ig_db.ProjectSdStage 
				(ProjectSdId, ProjectStageId, ProjectSdStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
        select 
				psd.ProjectSdId, pProjectStageId, null, pUserId, current_timestamp()
		from 	ig_db.ProjectSd psd
		where	psd.ProjectId = vProjectId;

		-- Link the newly added ProjectSds to all ProjectParticipantSdRoles
		insert into ig_db.PpSdRoleStage
				(ProjectParticipantSdRoleId, ProjectSdStageId, PpSdRoleStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
        select 	
				ppsdr.ProjectParticipantSdRoleId, 
                psds.ProjectSdStageId, 
                null, 
                pUserId, 
                current_timestamp()
		from 	ig_db.ProjectParticipantSdRole ppsdr
				join ProjectParticipant pp on pp.ProjectParticipantId = ppsdr.ProjectParticipantId,
                ProjectSdStage psds
		where	pp.ProjectId = vProjectId
				and psds.ProjectStageId = pProjectStageId
                and ppsdr.ProjectSdId = psds.ProjectSdId;
	commit;
END