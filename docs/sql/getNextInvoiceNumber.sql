CREATE DEFINER=`administrator`@`localhost` PROCEDURE `getNextInvoiceNumber`(IN `pParticipantId` 	BIGINT, 
																		OUT `pNextInvoiceNumber` 	VARCHAR(18),  
																		OUT `vInvoiceNumberFormat` 	VARCHAR(18), 
																		OUT `vInvoicePrefix` 		varchar(5), 
																		OUT `vYearMonthPart` 		varchar(7), 
																		OUT `vLatestInvoiceNumber` 	BIGINT, 
																		OUT `vDigitLength` 			INTEGER)
    NO SQL
BEGIN

	-- Participant table:
--     declare vInvoiceNumberFormat varchar(12) default 'YY-MM-####';
--     declare vInvoicePrefix varchar(5) default '';
--     declare vLatestInvoiceNumber bigint default 0;
    
    declare vPrevInvoiceDate datetime;
    declare vPrevInvoiceNumber varchar(45);
    
	-- Work variables
	declare pNumberPart  varchar(16);
	declare pMonthPart 	 varchar(3);
	declare pYearPart 	 varchar(5);
    declare pPrefixPart  varchar(6);
	
	declare ExtractYear4 varchar(4);
	declare ExtractYear2 varchar(2);
    declare ExtractMonth varchar(2);
    
    declare previousMonth integer;
    declare previousYear  integer;
    declare currentMonth  integer;
    declare currentYear   integer;    
    declare setToZeroY boolean; 
    declare setToZeroM boolean;
       
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	set setToZeroM = false;   -- Set previous number to zero because we have a new year,  so we will start with 1
	set setToZeroY = false;   -- Set previous number to zero because we have a new month, so we will start with 1
    
	select 	i.InvoiceDate, i.InvoiceNumber
    into	vPrevInvoiceDate, vPrevInvoiceNumber
    from 	Invoice i
    where	i.ParticipantIdFrom = pParticipantId
    and		i.InvoiceNumber = (select j.InvoiceNumber 
								from Invoice j 
								where j.ParticipantIdFrom = pParticipantId 
                                ORDER BY InvoiceNumber DESC LIMIT 1);

    set currentYear   =  YEAR(CURDATE());
	set currentMonth  =  Month(CURDATE());
    
	IF ISNULL(vPrevInvoiceDate) THEN
		set previousYear  =  YEAR(CURDATE());
		set previousMonth =  Month(CURDATE());
	ELSE
		set previousYear  =  YEAR(vPrevInvoiceDate);
		set previousMonth =  Month(vPrevInvoiceDate);
	END IF;
	
	IF ISNULL(vPrevInvoiceNumber) THEN
	    set vPrevInvoiceNumber = 0;
	END IF;
	
	IF currentYear > previousYear THEN
		set setToZeroY = true;
		set setToZeroM = true;
	ELSEIF currentMonth > previousMonth THEN
		set setToZeroM = true;
	END IF;

-- SELECT previousMonth;

    select 	p.InvoiceNumberFormat, p.LatestInvoiceNumber, p.InvoicePrefix 		
    into 	 vInvoiceNumberFormat,  vLatestInvoiceNumber,  vInvoicePrefix
    from 	Participant  p
	where 	p.ParticipantId			= pParticipantId;
 
	If ISNULL(vInvoiceNumberFormat) THEN
-- 		signal sqlstate '45000'
--         set message_text = 'No Invoice Number Format was found for this Participant';
		set vInvoiceNumberFormat = 'YY-MM-####';
	END IF;
 
	IF ISNULL(vInvoicePrefix) or (vInvoicePrefix = '') THEN
		set pPrefixPart = '';
    ELSE
		set pPrefixPart = concat(vInvoicePrefix, '-');
    END IF;
 
 	IF ISNULL(vLatestInvoiceNumber) THEN
	    set vLatestInvoiceNumber = 0;
	END IF;
 
	SET ExtractYear4 = LEFT(vInvoiceNumberFormat, 4);
	SET ExtractYear2 = LEFT(vInvoiceNumberFormat, 2);
	
	IF ExtractYear4 = 'YYYY' THEN
		set vYearMonthPart = convert(YEAR(CURDATE()), char(4));
		set pYearPart =  concat(convert(YEAR(CURDATE()), char(4)) , '-');
        set ExtractMonth = Substring(vInvoiceNumberFormat, 6, 2);
        IF ExtractMonth = 'MM' THEN
			set pMonthPart = concat('0', convert(MONTH(CURDATE()), char(2)));
			set vYearMonthPart = concat(pYearPart, right(pMonthPart, 2));
            set pMonthPart = concat(right(pMonthPart, 2), '-');
        ELSE
			set pMonthPart = '';
        END IF;
	ELSEIF ExtractYear2 = 'YY' THEN
		set vYearMonthPart = right(convert(YEAR(CURDATE()), char(4)), 2);
		set pYearPart =  concat(right(convert(YEAR(CURDATE()), char(4)), 2) , '-');
        set ExtractMonth = Substring(vInvoiceNumberFormat, 4, 2);
		IF ExtractMonth = 'MM' THEN
			set pMonthPart = concat('0', convert(MONTH(CURDATE()), char(2)));
			set vYearMonthPart = concat(pYearPart, right(pMonthPart, 2));
            set pMonthPart = concat(right(pMonthPart, 2), '-');
        ELSE
			set pMonthPart = '';
        END IF;
	ELSE
		set vYearMonthPart = '';
		set pYearPart = '';
        set pMonthPart = '';
	END IF;
	
	IF pYearPart <> '' AND pMonthPart = '' THEN
		IF setToZeroY = true THEN 
			SET vLatestInvoiceNumber = 0;
		END IF;
    ELSEIF  pYearPart <> '' AND pMonthPart <> '' THEN  
		IF setToZeroM = true THEN 
			SET vLatestInvoiceNumber = 0;
		END IF;
	END IF;
    
	set pNumberPart = '00000000';
    IF right(vInvoiceNumberFormat, 8) = '########' THEN
		set pNumberPart = concat(pNumberPart, convert(vLatestInvoiceNumber+1, char));
        set pNumberPart = right(pNumberPart, 8);
		set vDigitLength = 8;
	ELSEIF right(vInvoiceNumberFormat, 7) = '#######' THEN
		set pNumberPart = concat(pNumberPart, convert(vLatestInvoiceNumber+1, char));
        set pNumberPart = right(pNumberPart, 7);
		set vDigitLength = 7;
	ELSEIF right(vInvoiceNumberFormat, 6) = '######' THEN
		set pNumberPart = concat(pNumberPart, convert(vLatestInvoiceNumber+1, char));
        set pNumberPart = right(pNumberPart, 6);
		set vDigitLength = 6;
	ELSEIF right(vInvoiceNumberFormat, 5) = '#####' THEN
		set pNumberPart = concat(pNumberPart, convert(vLatestInvoiceNumber+1, char));
        set pNumberPart = right(pNumberPart, 5);
		set vDigitLength = 5;
	ELSEIF right(vInvoiceNumberFormat, 4) = '####' THEN
		set pNumberPart = concat(pNumberPart, convert(vLatestInvoiceNumber+1, char));
        set pNumberPart = right(pNumberPart, 4);
		set vDigitLength = 4;
	ELSEIF right(vInvoiceNumberFormat, 3) = '###' THEN
		set pNumberPart = concat(pNumberPart, convert(vLatestInvoiceNumber+1, char));
        set pNumberPart = right(pNumberPart, 3);
		set vDigitLength = 3;
	ELSE
		signal sqlstate '45000'
        set message_text = 'There was an error in generating the next invoice number';
    END IF;

    start transaction;


	set pNextInvoiceNumber =  concat(pPrefixPart, pYearPart, pMonthPart, pNumberpart);
   

	commit;

END