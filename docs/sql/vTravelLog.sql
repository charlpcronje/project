SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = root@localhost 
    SQL SECURITY DEFINER
VIEW vTravelLog AS
    SELECT 
        tl.TravelLogId AS TravelLogId,
        tl.ParticipantIdOnBehalfOf AS ParticipantIdOnBehalfOf,
        tl.ProjectId AS ProjectId,
        p.ProjectNameText AS ProjectNameText,
        tl.TripDistance AS TripDistance,
        tl.TravelTimeMinutes AS TravelTimeMinutes,
        CONCAT(i1.FirstName, ' ', i1.LastName) AS InstructedBy,
        CONCAT(i3.FirstName, ' ', i3.LastName) AS Driver,
        CONCAT(i2.FirstName, ' ', i2.LastName) AS BehalfOf,
        tl.TripStartingPointName AS TripStartingPointName,
        tl.TripEndPointName AS TripEndPointName,
        v.Name AS VehicleName,
        tl.VehicleId AS VehicleId,
        v.LicenceNumber AS LicenceNumber,
        vm.Name AS ModelName,
        vmm.Name AS MakeName,
        tl.TripOrderDate AS TripOrderDate,
        tl.TripDate AS TripDate,
        tl.TripStartTime AS TripStartTime,
        tl.TripEndTime AS TripEndTime,
        tl.TripStartCoordinates AS TripStartCoordinates,
        tl.TripEndCoordinates AS TripEndCoordinates,
        0 AS OwnerId,
        '' AS OwnerName,
        tl.ParticipantIdDriver AS ParticipantIdDriver,
        tl.ParticipantIdGaveInstruction AS ParticipantIdGaveInstruction,
        vr.OdometerReading AS OdometerReading,
        tl.Description AS Description,
        tl.TripStartKm AS TripStartKm,
        tl.TripEndKm AS TripEndKm
    FROM
        TravelLog tl
        JOIN Participant p1 ON tl.ParticipantIdGaveInstruction = p1.ParticipantId
        JOIN Individual i1 ON p1.ParticipantId = i1.ParticipantId
        JOIN Participant p2 ON tl.ParticipantIdOnBehalfOf = p2.ParticipantId
        JOIN Individual i2 ON p2.ParticipantId = i2.ParticipantId
        JOIN Participant p3 ON tl.ParticipantIdDriver = p3.ParticipantId
        JOIN Individual i3 ON p3.ParticipantId = i3.ParticipantId
        JOIN Project p ON tl.ProjectId = p.ProjectId
        JOIN Vehicle v ON tl.VehicleId = v.VehicleId
        JOIN VehicleModel vm ON v.VehicleModelId = vm.VehicleModelId
        JOIN VehicleMake vmm ON vm.VehicleMakeId = vmm.VehicleMakeId
        LEFT JOIN VehicleReading vr ON v.VehicleId = vr.VehicleId
