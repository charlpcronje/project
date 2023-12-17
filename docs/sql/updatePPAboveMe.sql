CREATE DEFINER=`root`@`localhost` PROCEDURE `updatePPAboveMe`()
BEGIN
	DECLARE finished INTEGER DEFAULT 0;
	declare vClonedPPId bigint; 
	declare vClonedPPIdAboveMe bigint; 
	-- declare cursor for pp
	DEClARE curPPTemp 
		CURSOR FOR 
			SELECT 
				ClonedPPId,
				ClonedPPIdAboveMe 
			FROM tempPPAbove;

	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

	OPEN curPPTemp;

	getPPAboveMe: LOOP
		FETCH curPPTemp 
		INTO 
				vClonedPPId, 
				vClonedPPIdAboveMe;

		IF finished = 1 THEN 
			LEAVE getPPAboveMe;
		END IF;
		-- update ProjectParticipant
		update 	ProjectParticipant pp
        set 	pp.ProjectParticipantIdAboveMe =  vClonedPPIdAboveMe
        where 	pp.ProjectParticipantId = vClonedPPId;
        
        
	END LOOP getPPAboveMe;
	CLOSE curPPTemp;
End