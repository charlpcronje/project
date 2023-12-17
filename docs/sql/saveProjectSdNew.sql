CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveProjectSdNew`
					(pQuery JSON, 
					pUserId VARCHAR(50),
                    OUT pProjectSdId BIGINT)
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- ServiceDiscipline table:
    
    declare vProjectId BIGINT;
    declare vServiceDisciplineId bigint;
    
    declare tmp VARCHAR(255);

    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    set vProjectId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectId'));
    set vServiceDisciplineId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineId'));

    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;

		insert into ig_db.ProjectSd
				(ProjectId, ServiceDisciplineId, ProjectSdIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			values 
				(vProjectId, vServiceDisciplineId, null, pUserId, current_timestamp);
		
        set pProjectSdId = last_insert_id();
        
		-- Link the new ProjectStage to all ProjectSds
		insert into ig_db.ProjectSdStage 
				(ProjectSdId, ProjectStageId, ProjectSdStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
        select 
				psd.ProjectSdId, ps.ProjectStageId, null, pUserId, current_timestamp()
		from 	ig_db.ProjectSd psd,
				ig_db.ProjectStage ps
		where	psd.ProjectSdId = pProjectSdId
                and ps.ProjectId = vProjectId;

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
				and psds.ProjectSdId = pProjectSdId
                and ppsdr.ProjectSdId = psds.ProjectSdId;
	commit;
END