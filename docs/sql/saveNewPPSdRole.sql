CREATE DEFINER=administrator@localhost PROCEDURE saveNewPPSdRole(
						pQuery JSON, pUserId VARCHAR(50), 
                        OUT pProjectParticipantSdRoleId BIGINT)
BEGIN
 
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- ProjectParticipantSdRole:
	declare vProjectParticipantId bigint;
	declare vProjectSdId bigint;
    declare vSdRoleId bigint default null;
    
    -- SdRole Table
    declare vRoleOnAProjectId bigint;

    -- ServiceDiscipline Table
    declare vServiceDisciplineId bigint;

	-- -- Local variables

	-- Work variables
	declare tmp varchar(50);
   declare sdCount int;

   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.Individual
    set vProjectParticipantId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectParticipantId'));
    set vProjectSdId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectSdId'));
    set vRoleOnAProjectId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.roleOnAProjectId'));

	-- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that 
    -- database constraints won't pick up
	-- ************************************************
	
	-- Ensure that user can only select a role once per Service Discipline
    select ServiceDisciplineId into vServiceDisciplineId 
		from ig_db.ProjectSd
        where ProjectSdId = vProjectSdId;

    select 		SdRoleId into vSdRoleId 
		from 	ig_db.SdRole
        where 	RoleOnAProjectId = vRoleOnAProjectId
        and 	ServiceDisciplineId = vServiceDisciplineId;

    select 	count(1) into sdCount 
	from 	ig_db.ProjectParticipantSdRole ppsdr,
			ig_db.SdRole sdr
	where	   
			ppsdr.ProjectParticipantId = vProjectParticipantId 
            and ppsdr.SdRoleId = sdr.SdRoleId 
			and ppsdr.ProjectSdId = vProjectSdId
            and sdr.RoleOnAProjectId = vRoleOnAProjectId;

	if (sdCount > 0) then
		signal sqlstate '45000'
			set message_text = 'This role has already been selected for this Participant';
	end if;

    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;

    -- ig_db.Participant
    -- --------------------------
		insert into ig_db.ProjectParticipantSdRole
			(
				ProjectParticipantId, 
				ProjectSdId, 
				SdRoleId, 
				LastUpdateTimestamp,
				LastUpdateUserName
			)
			values 
			(	vProjectParticipantId, 
				vProjectSdId, 
				vSdRoleId, 
				current_timestamp(), 
				pUserId);

		set pProjectParticipantSdRoleId = last_insert_id();
		
			-- Link the newly added ProjectParticipantSdRole to all ProjectStages
			insert into ig_db.PpSdRoleStage
					(ProjectParticipantSdRoleId, ProjectSdStageId, PpSdRoleStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			select 	
					pProjectParticipantSdRoleId, 
					psds.ProjectSdStageId, 
					null, 
					pUserId, 
					current_timestamp()
			from 	ProjectParticipantSdRole ppsdr,
					ProjectSdStage psds
			where	ppsdr.ProjectParticipantSdRoleId = pProjectParticipantSdRoleId
                    and psds.ProjectSdId = ppsdr.ProjectSdId;
	commit;

END