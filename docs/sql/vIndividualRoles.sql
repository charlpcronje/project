SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `administrator`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vIndividualRoles` AS
    SELECT 
        `i`.`UserName` AS `UserName`,
        `sm`.`RoleCode` AS `RoleCode`,
        `i`.`AllowLoginFlag` AS `AllowLoginFlag`,
        `i`.`IsActiveMember` AS `IsActiveMember`
    FROM
        ((`Individual` `i`
        JOIN `SystemMember` `sm`)
        JOIN `Participant` `p`)
    WHERE
        ((`sm`.`IndividualId` = `i`.`IndividualId`)
            AND (`p`.`ParticipantId` = `i`.`ParticipantId`)
            AND (`p`.`IsIndividual` = 'Y'))