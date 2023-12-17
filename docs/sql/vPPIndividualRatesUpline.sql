CREATE OR REPLACE
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW vPPIndividualRatesUpline AS
select 
(vt.TimesheetId * vap.AgreementBetweenParticipantsId * vt.ProjectParticipantIdThatBookedTime) AS RowNumber,
va.Level,
vt.ProjectNameText as ProjectName, 
vt.ActivityDate, 
vt.Description,
vt.UnitTypeName,
vt.NumberOfUnits, 

getRateForDate(vt.ProjectParticipantSdRoleId, vt.ParticipantIdThatBookedTime, vap.AgreementBetweenParticipantsId, vt.ProjBasedRemunTypeId, vt.ActivityDate) AS RateForDate, -- -2: no rates, -1: Rates overlap, otherwise the CurrentRate
vt.SystemNameThatBookedTime, 
CONCAT(va.SystemNamePayer, ' - ', va.SystemNameBeneficiary) AS AgreementBetween,
CONCAT(vt.SdName, ' - ', vt.RoleName) AS SdAndRole,

vt.TimesheetId, 
vt.ProjectParticipantSdRoleId, 
vt.RemunerationTypeName, 
vt.ProjectParticipantIdThatBookedTime, 
vt.ProjectId, 
vt.ParticipantIdThatBookedTime, 

vt.UnitTypeCode,

vap.AgreementBetweenParticipantsId, 
va.ParticipantIdPayer as AgreementParticipantIdPayer,
va.SystemNamePayer as AgreementPayer,
va.ParticipantIdBeneficiary as AgreementParticipantIdBeneficiary,
va.SystemNameBeneficiary as AgreementBeneficiary,

vap.ParticipantIdContracting as ParticipantIdPayer, 
vap.ParticipantNameContracting, 
vap.RemunerationModelCode,
vt.SdId, 
vt.SdCode, 
vt.SdName, 
vt.RoleOnAProjectId,
vt.ProjectSdId,
vt.StageName

from vTimesheet vt
		join vAgreementParticipants vap on ((vt.ParticipantIdThatBookedTime = vap.ParticipantIdContracted)
													and vap.RemunerationModelCode = "TIME_COST"
													and vap.IsIndividual = "Y"
                                                    and vt.ProjectId = vap.ProjectId)
		join vAgreementBetweenParticipants va on (vap.AgreementBetweenParticipantsId = va.AgreementBetweenParticipantsId
													and vt.ProjectParticipantIdThatBookedTime = vap.ProjectParticipantIdContracted);
   