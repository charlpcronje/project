CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteNonProjectRelatedAgreement`(`pNonProjectRelatedAgreementId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the NonProjectRelatedAgreementId has ResourceRemuneration
    select count(*) into vCount from ig_db.ResourceRemuneration
    where NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete NonProjectRelatedAgreement.  The NonProjectRelatedAgreement has ResourceRemuneration linked to it.';
	end if;


	
	start transaction;
		delete from ig_db.NonProjectRelatedAgreement where NonProjectRelatedAgreementId = pNonProjectRelatedAgreementId;
	commit;

END