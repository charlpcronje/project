CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteIndividual`(`pIndividualId` BIGINT)
BEGIN
	declare vParticipantId bigint;

    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
    select	ParticipantId into vParticipantId 
    from 	ig_db.Individual where IndividualId = pIndividualId;
    
  	-- Check if the Individual was used as a MarketingIndividual or RepresentativeIndividual
    select count(*) into vCount from ig_db.Participant
    where RepresentativeIndividualId = pIndividualId
    or 	MarketingIndividualId = pIndividualId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is setup as a Marketing Contact or a Representative';
	end if;
    
  	-- Check if the Individual was used as a Project Admin on a project
    select count(*) into vCount from ig_db.Project
    where IndividualIdProjectAdmin = pIndividualId;
    if (vCount > 1 ) then -- We ignore the Sustenance default project
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is setup as a Project Administrator on one or more Projects';
	end if;
    
   	-- Check if there are any Bank cards linked
    select count(*) into vCount from ig_db.BankCard
    where IndividualIdMainCardUser = pIndividualId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to Bank Card(s).';
	end if;

   	-- Check if there are any Assignments linked
    select count(*) into vCount from ig_db.Assignment
    where 	IndividualIdInstructor = pIndividualId or
			IndividualIdLogger = pIndividualId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to Assignment(s) as an Instructor or a Logger.';
	end if;

   	-- Check if there are any SdRoles linked
    select count(*) into vCount from ig_db.IndividualSdRole
    where 	IndividualId = pIndividualId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She has linked Service Disciplines.';
	end if;

   	-- Check if the Participant is part of a Project
    select count(*) into vCount from ig_db.ProjectParticipant
    where ParticipantId = vParticipantId;
    if (vCount > 1 ) then -- We ignore the Sustenance default project
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to a Project.';
	end if;

   	-- Check if there are any Remuneration Rates linked to this Individual
    select count(*) into vCount from ig_db.RemunerationRateUpline
    where ParticipantIdIndividual = vParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to a Project.';
	end if;

   	-- Check if there are any Project Expenses linked to this Individual
    select count(*) into vCount from ig_db.ProjectExpense
    where ParticipantIdVendor = vParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to a Project expense as a Vendor.';
	end if;

   	-- Check if there are any Project Expenses linked to this Individual
    select count(*) into vCount from ig_db.ProjectExpense
    where ParticipantIdMadePurchase = vParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to a Project expense as a Purchaser.';
	end if;


	-- Add later when implementing invoices
--   	-- Check if there are any Invoices linked to this Individual
--      select count(*) into vCount from ig_db.Invoice
--      where 	ParticipantIdFrom = vParticipantId 
--      or		ParticipantIdTo = vParticipantId;
--      if (vCount > 0 ) then 
--  		signal sqlstate '45000'
--  		set message_text = 'Can not delete Individual.  He/She is linked to Invoices.';
--  	end if;

   	-- Check if there are any NonProjectRelatedAgreements linked to this Individual
  	    select count(*) into vCount from ig_db.NonProjectRelatedAgreement
  	    where ParticipantIdPayer = vParticipantId
  	    or ParticipantIdBeneficiary = vParticipantId;
 	    if (vCount > 0 ) then 
  			signal sqlstate '45000'
  			set message_text = 'Can not delete Individual.  He/She is linked to Non Project Related Agreements.';
  		end if;

   	-- Check if there are any BankTransactions linked to this Individual
    select count(*) into vCount from ig_db.BankTransaction
    where ParticipantIdOnTransaction = vParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to Non Project Related Agreements.';
	end if;

   	-- Check if there are any Assets linked to this Individual
    select count(*) into vCount from ig_db.Asset
    where ParticipantIdOriginalOwner = vParticipantId
		or	ParticipantIdCurrentOwner = vParticipantId
		or	ParticipantIdSponsor = vParticipantId
		or	ParticipantIdSoldTo = vParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to one or more Assets.';
	end if;

   	-- Check if there are any Projects linked to this Individual other than his default Sustenance Project
    select count(*) into vCount from ig_db.Project
    where 	ParticipantIdHost = vParticipantId
		and	IndividualIdProjectAdmin = pIndividualId;
    if (vCount > 1 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Individual.  He/She is linked to Project(s).';
	end if;

	start transaction;
		SET FOREIGN_KEY_CHECKS=0;
			delete cp.* 
			from 	ig_db.ContactPoint cp,
					ig_db.ParticipantOffice po
			where 	po.ParticipantId = vParticipantId
            and		po.ParticipantOfficeId = cp.ParticipantOfficeId;

			delete 	t.*
            from 	TopDescriptionsUsed t
            join	ProjectParticipant pp on t.ProjectParticipantId = p.ProjectParticipantId
            where 	p.ParticipantId = vParticipantId;
			
            delete from ig_db.ProjectParticipant where ParticipantId = vParticipantId;

			delete p.* 
            from 	ig_db.Project p
			where 	ParticipantIdHost = vParticipantId
			and		IndividualIdProjectAdmin = pIndividualId;
            
			delete from ig_db.ParticipantBankDetails where ParticipantIdOwner = vParticipantId;

			delete from ig_db.SystemMember where IndividualId = pIndividualId;
			delete from ig_db.Individual where IndividualId = pIndividualId;
			delete from ig_db.Participant where ParticipantId = vParticipantId;
		SET FOREIGN_KEY_CHECKS=1;    
	commit;

END