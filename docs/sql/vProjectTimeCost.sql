SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vProjectTimeCost AS
select 
	ANY_VALUE(iru.RowNumber) as RowNumber,
	iru.ProjectId, 
	ANY_VALUE(iru.ProjectName) as ProjectName,
	ANY_VALUE(iru.SdName) as SdName,
	iru.UnitTypeName, 
	iru.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId, 
	ANY_VALUE(iru.AgreementBetween) as AgreementBetween, 
    ANY_VALUE(iru.AgreementParticipantIdPayer) as AgreementParticipantIdPayer,
	ANY_VALUE(iru.AgreementPayer) as AgreementPayer,
	ANY_VALUE(iru.AgreementParticipantIdBeneficiary) as AgreementParticipantIdBeneficiary,
	ANY_VALUE(iru.AgreementBeneficiary) as AgreementBeneficiary,
    ANY_VALUE(iru.ActivityDate) as ActivityDate,

	iru.ProjectSdId, 
	ANY_VALUE(iru.RemunerationTypeName) as RemunerationTypeName, 
SUM(iru.NumberOfUnits) as SumNrOfUnits,
sum(iru.NumberOfUnits * iru.RateForDate) as LineAmount
from 
	ig_db.vPPIndividualRatesUpline iru
where 
    iru.RateForDate >= 0
group by 
	 	iru.ProjectId,
		iru.AgreementBetweenParticipantsId,		
        iru.ProjectSdId,
		iru.UnitTypeName
UNION all
select 
	ANY_VALUE(iru.RowNumber) as RowNumber,
	iru.ProjectId, 
	ANY_VALUE(iru.ProjectName) as ProjectName,
	ANY_VALUE(iru.SdName) as SdName,
	iru.UnitTypeName, 
	iru.AgreementBetweenParticipantsId, 
	ANY_VALUE(iru.AgreementBetween) as AgreementBetween, 
    
    ANY_VALUE(iru.AgreementParticipantIdPayer) as AgreementParticipantIdPayer,
	ANY_VALUE(iru.AgreementPayer) as AgreementPayer,
	ANY_VALUE(iru.AgreementParticipantIdBeneficiary) as AgreementParticipantIdBeneficiary,
	ANY_VALUE(iru.AgreementBeneficiary) as AgreementBeneficiary,
    ANY_VALUE(iru.ActivityDate) as ActivityDate,

	iru.ProjectSdId, 
	ANY_VALUE(iru.RemunerationTypeName) as RemunerationTypeName, 
SUM(iru.NumberOfUnits) as SumNrOfUnits,
null as LineAmount -- "Remuneration rate(s) missing" as LineAmount
from 
	ig_db.vPPIndividualRatesUpline iru 
where 
    iru.RateForDate < 0
group by 
 		iru.ProjectId,
		iru.AgreementBetweenParticipantsId,		
        iru.ProjectSdId,
		iru.UnitTypeName
	



