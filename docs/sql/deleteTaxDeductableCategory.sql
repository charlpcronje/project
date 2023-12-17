CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteTaxDeductableCategory`(`pTaxDeductableCategoryId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the TaxDeductableCategory is linked to a PaymentSchedule
    select count(*) into vCount from ig_db.ProjectExpense
    where TaxDeductableCategoryId = pTaxDeductableCategoryId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Payment Method.  It has one or more Project Expenses linked to it';
	end if;

	start transaction;
		delete from ig_db.TaxDeductableCategory where TaxDeductableCategoryId = pTaxDeductableCategoryId;
	commit;


END