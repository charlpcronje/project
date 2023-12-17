SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vParticipantTimeCostTotals AS
select 
	ANY_VALUE(iru.RowNumber) as RowNumber,
    ANY_VALUE(iru.AgreementParticipantIdPayer) as AgreementParticipantIdPayer,
	ANY_VALUE(iru.AgreementPayer) as AgreementPayer,
	ANY_VALUE(iru.AgreementParticipantIdBeneficiary) as AgreementParticipantIdBeneficiary,
	ANY_VALUE(iru.AgreementBeneficiary) as AgreementBeneficiary,
    ANY_VALUE(iru.ActivityDate) as ActivityDate,
	SUM(iru.NumberOfUnits) as SumNrOfUnits,
	sum(iru.NumberOfUnits * iru.RateForDate) as LineAmount,
	0 AS RatesMissing
     
from 
	ig_db.vPPIndividualRatesUpline iru
where 
    iru.RateForDate >= 0
group by 
	 	iru.AgreementParticipantIdPayer,
		iru.AgreementParticipantIdBeneficiary,
        iru.ActivityDate
UNION all
select 
	ANY_VALUE(iru2.RowNumber) as RowNumber,
    ANY_VALUE(iru2.AgreementParticipantIdPayer) as AgreementParticipantIdPayer,
	ANY_VALUE(iru2.AgreementPayer) as AgreementPayer,
	ANY_VALUE(iru2.AgreementParticipantIdBeneficiary) as AgreementParticipantIdBeneficiary,
	ANY_VALUE(iru2.AgreementBeneficiary) as AgreementBeneficiary,
    ANY_VALUE(iru2.ActivityDate) as ActivityDate,
	SUM(iru2.NumberOfUnits) as SumNrOfUnits,
	null as LineAmount, -- "Remuneration rate(s) missing" as LineAmount
    1 as RatesMissing
from 
	ig_db.vPPIndividualRatesUpline iru2
where 
    iru2.RateForDate < 0
group by 
		iru2.AgreementParticipantIdPayer,
		iru2.AgreementParticipantIdBeneficiary,
        iru2.ActivityDate
	

