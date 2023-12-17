DELIMITER $$$ 
CREATE PROCEDURE ig_db.clearParticipantLogo(pParticipantId bigint) 
	BEGIN 
		UPDATE Participant SET ParticipantLogo = NULL WHERE participantId = pParticipantId;
    END 
    $$$