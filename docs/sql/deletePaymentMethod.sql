CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deletePaymentMethod`(`pPaymentMethodCode` VARCHAR(50))
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the PaymentMethod is linked to a PaymentSchedule
    select count(*) into vCount from ig_db.ProjectExpense
    where PaymentMethodCode = pPaymentMethodCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Payment Method.  It has one or more Project Expenses linked to it';
	end if;

	start transaction;
		delete from ig_db.PaymentMethod where PaymentMethodCode = pPaymentMethodCode;
	commit;

END