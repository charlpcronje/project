CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteInvoice`(IN pInvoiceId BIGINT)
    NO SQL
BEGIN

    DECLARE vCount integer default 0;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if there hase been pdfs generated or linked to this invoice
    select count(*) into vCount from InvoiceFile
    where InvoiceId = pInvoiceId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Invoice.  There are pdf files linked to it.';
	end if;

  	-- Check if there are any BankTransactions linked to this invoice
    select count(*) into vCount from BankTransactionLink
    where InvoiceId = pInvoiceId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Invoice.  There are Bank Transactions linked to it.';
	end if;

	start transaction;
		delete 	ild.*
        from  	ig_db.InvoiceLineDetail ild 
		join	ig_db.InvoiceLine il on (il.InvoiceLineId = ild.InvoiceLineId)
		where 	il.InvoiceId = pInvoiceId;
            
		delete from ig_db.InvoiceLine where InvoiceId = pInvoiceId;
		delete from ig_db.Invoice where InvoiceId = pInvoiceId;
	commit;

END