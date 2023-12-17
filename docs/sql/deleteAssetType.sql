CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAssetType`(pAssetTypeId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the      has been used in Asset
    select count(*) into vCount from ig_db.Asset
    where AssetTypeId = pAssetTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Asset Type.  The Asset Type has been linked to one or more Assets.';
	end if;

	start transaction;
		delete from ig_db.AssetType where AssetTypeId = pAssetTypeId;
	commit;

END