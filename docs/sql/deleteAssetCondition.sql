CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAssetCondition`(pAssetConditionId BIGINT)
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
    where AssetConditionId = pAssetConditionId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Asset Condition.  The Asset Condition has been linked to one or more Assets.';
	end if;

	start transaction;
		delete from ig_db.AssetCondition where AssetConditionId = pAssetConditionId;
	commit;

END