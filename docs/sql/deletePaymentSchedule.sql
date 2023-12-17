CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deletePaymentSchedule`(`pPaymentScheduleId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
    -- Currently no checks...
  	-- Check if the PaymentScheduleId is linked to a Project Agreement
    -- select count(*) into vCount from ig_db.ProjectAgreement
    -- where PaymentScheduleIdDefault = pParticipantOfficeId;
    -- if (vCount > 0 ) then 
	--	signal sqlstate '45000'
	--	set message_text = 'Can not delete Participant Office.  The Participant Office is set as the Default Office in the Additional Tab';
	-- end if;
	
	start transaction;
		delete from ig_db.PaymentSchedule where PaymentScheduleId = pPaymentScheduleId;
	commit;

END