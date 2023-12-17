SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vTripLogger AS

SELECT 
    tl.TripLoggerId,
    tl.ParticipantIdOnBehalfOf,
    p.ProjectId,
    p.ProjectNameText,
    tl.TripDistance,
    tl.TravelTimeMinutes,
    pt.SystemName As InstructedBy,
    tl.TripStartingPointName,
    tl.TripEndPointName,
    v.Name AS VehicleName,
    v.VehicleId,
    v.LicenceNumber,
    vm.Name AS ModelName,
    vmk.Name AS MakeName,
    tl.TripStartCoordinates,
    tl.TripEndCoordinates,
    tl.TripStartKm,
    tl.TripEndKm,
    tl.TripOrderDate,
    tl.TripDate,
    tl.TripStartTime,
    tl.TripEndTime,
	pc.ParticipantId AS OwnerId,
	pc.SystemName AS OwnerName,
    tl.ParticipantIdDriver,
    tl.ParticipantIdGaveInstruction,
    pat.SystemName As Driver,
    pb.SystemName As BehalfOf,
    tl.Description,
	(
		SELECT vr.OdometerReading
		FROM VehicleReading vr
		WHERE vr.VehicleId = v.VehicleId
	) AS OdometerReading

FROM
    Project p
    JOIN TripLogger tl ON (p.ProjectId = tl.ProjectId)
    JOIN Participant pt ON (tl.ParticipantIdGaveInstruction = pt.ParticipantId)
    JOIN Vehicle v ON (tl.VehicleId = v.VehicleId)
    JOIN VehicleModel vm ON (vm.VehicleModelId = v.VehicleModelId)
    JOIN VehicleMake vmk ON (vmk.VehicleMakeId = vm.VehicleMakeId)
    JOIN Asset a ON (a.VehicleId = v.VehicleId)
    JOIN Participant pc ON (a.ParticipantIdCurrentOwner = pc.ParticipantId)
    JOIN Participant pat ON (tl.ParticipantIdDriver = pat.ParticipantId)
    JOIN Participant pb ON (tl.ParticipantIdOnBehalfOf = pb.ParticipantId)
