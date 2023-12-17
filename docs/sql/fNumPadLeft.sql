create function ig_db.fNumPadLeft (theNumber bigint, padWith int)
returns varchar(250)
reads sql data
begin
    declare numStr varchar(250);

    set numStr = LTRIM(theNumber);

    if (padWith > length(numStr)) then 
        set numStr = REPLICATE('0', padWith - LEN(numStr)) + numStr;
	end if;

    return numStr;
end;
