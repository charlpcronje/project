CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteParticipant`(pParticipantId BIGINT)
BEGIN

    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
   	-- Check if the Participant is part of a Project
    select 	count(*) into vCount 
    from 	ig_db.ProjectParticipant
    where 	ParticipantId = pParticipantId;
 
	if (vCount > 1 ) then -- We ignore the Sustenance default project
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant.  It is linked to a Project.';
	end if;

   	-- Check if there are any Remuneration Rates linked to this Participant
    select count(*) into vCount from ig_db.RemunerationRateUpline 
    where ParticipantIdIndividual = pParticipantId;
    
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant.  It is linked to a Project.';
	end if;

   	-- Check if there are any Project Expenses linked to this Participant
    select count(*) into vCount from ig_db.ProjectExpense
    where ParticipantIdVendor = pParticipantId;
    
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant.  It is linked to a Project expense as a Vendor.';
	end if;

   	-- Check if there are any Project Expenses linked to this Participant
    select count(*) into vCount from ig_db.ProjectExpense
    where ParticipantIdMadePurchase = pParticipantId;
    
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant.  It is linked to a Project expense as a Purchaser.';
	end if;

	-- Add later when implementing invoices
--   	-- Check if there are any Invoices linked to this Participant
--      select count(*) into vCount from ig_db.Invoice
--      where 	ParticipantIdFrom = pParticipantId 
--      or		ParticipantIdTo = pParticipantId;
--      if (vCount > 0 ) then 
--  		signal sqlstate '45000'
--  		set message_text = 'Can not delete Participant.  It is linked to Invoices.';
--  	end if;

   	-- Check if there are any NonProjectRelatedAgreements linked to this Participant
  	    select count(*) into vCount from ig_db.NonProjectRelatedAgreement
  	    where ParticipantIdPayer = pParticipantId
  	    or ParticipantIdBeneficiary = pParticipantId;
 	    if (vCount > 0 ) then 
  			signal sqlstate '45000'
  			set message_text = 'Can not delete Participant.  It is linked to Non Project Related Agreements.';
  		end if;

   	-- Check if there are any BankTransactions linked to this Participant
    select count(*) into vCount from ig_db.BankTransaction
    where ParticipantIdOnTransaction = pParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant.  It is linked to Non Project Related Agreements.';
	end if;

   	-- Check if there are any Assets linked to this Participant
    select count(*) into vCount from ig_db.Asset
    where ParticipantIdOriginalOwner = pParticipantId
		or	ParticipantIdCurrentOwner = pParticipantId
		or	ParticipantIdSponsor = pParticipantId
		or	ParticipantIdSoldTo = pParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant.  It is linked to one or more Assets.';
	end if;

	start transaction;
		SET FOREIGN_KEY_CHECKS=0;
			delete cp.* 
			from 	ig_db.ContactPoint cp,
					ig_db.ParticipantOffice po
			where 	po.ParticipantId = pParticipantId
            and		po.ParticipantOfficeId = cp.ParticipantOfficeId;
			
			delete from ig_db.ProjectParticipant 
            where 	ParticipantId = pParticipantId;
			
			delete 	t.*
            from 	TopDescriptionsUsed t
            join	ProjectParticipant pp on t.ProjectParticipantId = p.ProjectParticipantId
            where 	p.ParticipantId = pParticipantId;

			delete p.* 
            from 	ig_db.Project p,
					ig_db.ProjectParticipant pp
			where 	p.ProjectParticipantIdLevel1 = pp.ProjectParticipantId
			and		pp.ParticipantId = pParticipantId;
            
			delete p.* 
            from 	ig_db.Project p
			where 	p.ParticipantIdHost = pParticipantId;
            
			delete from ig_db.ParticipantBankDetails where ParticipantIdOwner = pParticipantId;

			delete from ig_db.Individual where ParticipantId = pParticipantId;

			delete from ig_db.Participant where ParticipantId = pParticipantId;
			
		SET FOREIGN_KEY_CHECKS=1;    
	commit;

END