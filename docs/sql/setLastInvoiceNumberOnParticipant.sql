<<<<<<< HEAD
CREATE DEFINER=`administrator`@`localhost` PROCEDURE `setLastInvoiceNumberOnParticipant`(IN `pParticipantId` 	BIGINT, 
																		IN `pNewNumber` 	INTEGER)
=======
CREATE DEFINER=`administrator`@`localhost` PROCEDURE `setLastInvoiceNumberOnParticipant`(IN `pParticipantId` BIGINT, IN `pNewNumber` INT)
>>>>>>> b60adec9262d726a1018d93a4926168abe11bf4a
    NO SQL
BEGIN


    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;


    start transaction;

		update Participant set LatestInvoiceNumber = pNewNumber where participantId = pParticipantId;
   
	commit;

END