CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteBranch`(`pBranchId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the BranchId has been used in ParticipantBankDetails
    select count(*) into vCount from ig_db.ParticipantBankDetails
    where BranchId = pBranchId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Branch.  The Branch has been linked to Participants Bank Details.';
	end if;

	start transaction;
		delete from ig_db.Branch where BranchId = pBranchId;
	commit;

END