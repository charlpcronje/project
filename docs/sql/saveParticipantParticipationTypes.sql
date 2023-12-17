CREATE PROCEDURE ig_db.saveParticipantParticipationTypes
				(pQuery JSON, 
				pUserId VARCHAR(50))
BEGIN
    declare vParticipantId bigint;

    declare ptCount bigint;
    declare sIdx bigint default 0;

    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
    -- Fetch all the values from the JSON
    set vParticipantId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantId'));
 
    -- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that
    -- database constraints won't pick up
    -- ************************************************
    
    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    
    -- to stop anything from being saved... uncomment the line below
    -- leave saveParticipantParticipationTypesProc;
    
    -- ig_db.ParticipantParticipationType
    -- --------------------------
    
    -- Clear old ParticipantParticipationTypes because we will re-insert all the new services that they've chosen
    delete from ig_db.ParticipantParticipationType where ParticipantId = vParticipantId;
    
    -- now reinsert the ParticipantParticipationTypes
    set ptCount = JSON_EXTRACT(pQuery, '$.participationTypesCount');
    set sIdx = 0;
    while (sIdx < ptCount) do
        begin
            declare Id bigint;   -- eg., CLIENT
            declare isUsed varchar(1);  -- eg., Y

            set Id = JSON_UNQUOTE(JSON_EXTRACT(JSON_EXTRACT(pQuery, '$.participationTypesSelected'), concat('$[', sIdx, '].Id')));
            set isUsed = JSON_UNQUOTE(JSON_EXTRACT(JSON_EXTRACT(pQuery, '$.participationTypesSelected'), concat('$[', sIdx, '].selected')));
            
            if (isUsed = 'Y') then
                insert into ig_db.ParticipantParticipationType
                    (ParticipantId, ParticipationTypeId, LastUpdateTimestamp, LastUpdateUserName)
                    values
                    (vParticipantId, Id, current_timestamp, pUserId);
            end if;
            
            set sIdx = sIdx + 1;
        end;
    end while;

    commit;
END