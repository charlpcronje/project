CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAsset`(`pAssetId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the Asset has AssetUser
    select count(*) into vCount from ig_db.AssetUser
    where AssetId = pAssetId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Asset.  The Asset has Users linked to it.';
	end if;

    -- Check if there are any ProjectExpense linked to this Asset
    select count(*) into vCount from ig_db.ProjectExpense
    where AssetId = pAssetId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Asset.  There are Project Expenses linked to it.';
	end if;
	
	start transaction;
		delete from ig_db.Asset where AssetId = pAssetId;
	commit;

END