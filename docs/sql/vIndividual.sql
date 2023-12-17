SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vIndividual AS

    SELECT 
		i.IndividualId, 
        i.ParticipantId, 
        i.FirstName,
        i.SecondName,
        i.ThirdName,
        i.NickName,
        i.LastName, 
        i.Initials, 
        i.IdNumber, 
        i.PassportNumber, 
        i.CountryId, 
        i.IsActiveMember, 
        i.AllowLoginFlag, 
        i.IncomeTaxNumber, 
        i.Pass, 
        i.UserName, 
        i.PasswordResetToken, 
        i.PasswordResetExpiryDate, 
        i.LastLoginTimestamp AS LastLoginTimestamp,
		
		i.MedicalName AS MedicalName,
		i.MedicalNumber AS MedicalNumber,
		
        i.LastUpdateTimestamp AS LastUpdateTimestamp,
        i.LastUpdateUserName AS LastUpdateUserName,
        
        sm.SystemMemberId AS SystemMemberId,
        sm.StartDate AS SystemMemberStartDate,
        sm.EndDate AS SystemMemberEndDate,

        r.RoleCode AS RoleCode,
        r.Name AS RoleName,
        
        c.Name,
        
        p.SystemName
        

    FROM
        Individual i
        JOIN Participant p ON (p.ParticipantId = i.ParticipantId)
        JOIN SystemMember sm ON (sm.IndividualId = i.IndividualId)
        JOIN Role r ON (sm.RoleCode = r.RoleCode)
        LEFT JOIN Country c ON (i.CountryId = c.CountryId);
