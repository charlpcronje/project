SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vVehicle AS

    SELECT 
		v.VehicleId,
        v.Name AS VehicleName,
        vm.Name AS ModelName,
        vm.VehicleModelId,
        vmk.Name AS MakeName,
        vmk.VehicleMakeId,
		pc.ParticipantId AS OwnerId,
		pc.SystemName AS OwnerName,
        v.LicenceNumber,
        vr.OdometerReading
        
    FROM
        Vehicle v
        JOIN VehicleModel vm ON (v.VehicleModelId = vm.VehicleModelId)
        JOIN VehicleMake vmk ON (vm.VehicleMakeId = vmk.VehicleMakeId)
        JOIN Asset a ON (a.VehicleId = v.VehicleId)
		JOIN Participant pc ON (a.ParticipantIdCurrentOwner = pc.ParticipantId)
        LEFT Join VehicleReading vr ON (vr.VehicleReadingId = v.VehicleId);