CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDeliverableType`(IN `pDeliverableTypeId` long)
BEGIN
	start transaction;
		delete from ig_db.DeliverableType where DeliverableTypeId = pDeliverableTypeId;
	commit;
END