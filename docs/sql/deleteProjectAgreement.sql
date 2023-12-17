CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectAgreement`(IN `pProjectAgreementId` BIGINT)
    NO SQL
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	-- Check if there are any ProjectExpenseAgreements linked to this ProjectAgreement
    select count(*) into vCount from ig_db.ProjectExpenseAgreement
    where ProjectAgreementId = pProjectAgreementId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Agreement.  It has associated Project Expense Agreements.';
	end if;

	-- Check if there are any PaymentSchedules linked to this ProjectAgreement
    select count(*) into vCount from ig_db.PaymentSchedule
    where ProjectAgreementId = pProjectAgreementId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Agreement.  It has associated Payment Schedules.';
	end if;


	-- Check if there are any PaymentSchedules linked to this ProjectAgreement
    select count(*) into vCount from ig_db.PaymentSchedule
    where ProjectAgreementId = pProjectAgreementId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Agreement.  It has associated Payment Schedules';
	end if;

	start transaction;
    
	delete from ig_db.ProjectAgreement where ProjectAgreementId = pProjectAgreementId;
	commit;
END