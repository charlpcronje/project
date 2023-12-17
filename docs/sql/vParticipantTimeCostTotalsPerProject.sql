CREATE OR REPLACE
VIEW vParticipantTimeCostTotalsPerProject AS
select 
	ANY_VALUE(iru.RowNumber) as RowNumber,
	ANY_VALUE(iru.ProjectId) as ProjectId,
    ANY_VALUE(iru.ProjectName) as ProjectName,

	ANY_VALUE(iru.SdName) as SdName,
	ANY_VALUE(iru.UnitTypeName) as UnitTypeName,
	ANY_VALUE(iru.AgreementBetweenParticipantsId) as AgreementBetweenParticipantsId,
	ANY_VALUE(iru.AgreementBetween) as AgreementBetween,
    
    
    ANY_VALUE(iru.AgreementParticipantIdPayer) as AgreementParticipantIdPayer,
	ANY_VALUE(iru.AgreementPayer) as AgreementPayer,
	ANY_VALUE(iru.AgreementParticipantIdBeneficiary) as AgreementParticipantIdBeneficiary,
	ANY_VALUE(iru.AgreementBeneficiary) as AgreementBeneficiary,

	ANY_VALUE(iru.ProjectSdId) as ProjectSdId,
	ANY_VALUE(iru.RemunerationTypeName) as RemunerationTypeName,

    ANY_VALUE(iru.ActivityDate) as ActivityDate,
	SUM(iru.NumberOfUnits) as SumNrOfUnits,
	sum(iru.NumberOfUnits * iru.RateForDate) as LineAmount,
	0 AS RatesMissing
from 
	ig_db.vPPIndividualRatesUpline iru
where 
    iru.RateForDate >= 0
group by 
		iru.ProjectId,
		iru.ProjectName,

        iru.SdName,
		iru.UnitTypeName,
		iru.AgreementBetweenParticipantsId,
		iru.AgreementBetween,

	 	iru.AgreementParticipantIdPayer,
		iru.AgreementParticipantIdBeneficiary,
		iru.ProjectSdId,
		iru.RemunerationTypeName,

        iru.ActivityDate
UNION all
select 
	ANY_VALUE(iru2.RowNumber) as RowNumber,
	ANY_VALUE(iru2.ProjectId) as ProjectId,
	ANY_VALUE(iru2.ProjectName) as ProjectName,

	ANY_VALUE(iru2.SdName) as SdName,
	ANY_VALUE(iru2.UnitTypeName) as UnitTypeName,
	ANY_VALUE(iru2.AgreementBetweenParticipantsId) as AgreementBetweenParticipantsId,
	ANY_VALUE(iru2.AgreementBetween) as AgreementBetween,

    ANY_VALUE(iru2.AgreementParticipantIdPayer) as AgreementParticipantIdPayer,
	ANY_VALUE(iru2.AgreementPayer) as AgreementPayer,
	ANY_VALUE(iru2.AgreementParticipantIdBeneficiary) as AgreementParticipantIdBeneficiary,
	ANY_VALUE(iru2.AgreementBeneficiary) as AgreementBeneficiary,

	ANY_VALUE(iru2.ProjectSdId) as ProjectSdId,
	ANY_VALUE(iru2.RemunerationTypeName) as RemunerationTypeName,

    ANY_VALUE(iru2.ActivityDate) as ActivityDate,
	SUM(iru2.NumberOfUnits) as SumNrOfUnits,
	null as LineAmount, -- "Remuneration rate(s) missing" as LineAmount
    1 as RatesMissing
from 
	ig_db.vPPIndividualRatesUpline iru2 
where 
    iru2.RateForDate < 0
group by 
		iru2.ProjectId,
		iru2.ProjectName,

        iru2.SdName,
		iru2.UnitTypeName,
		iru2.AgreementBetweenParticipantsId,
		iru2.AgreementBetween,

		iru2.AgreementParticipantIdPayer,
		iru2.AgreementParticipantIdBeneficiary,
		iru2.ProjectSdId,
		iru2.RemunerationTypeName,
        iru2.ActivityDate
	

