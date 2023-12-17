CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = administrator@localhost 
    SQL SECURITY DEFINER
VIEW vAgreementsIncludingRecExpenseAgr AS;



	SELECT 
    	vabp.AgreementBetweenParticipantsId AS AgreementBetweenParticipantsId,
        CONCAT(vabp.SystemNamePayer, ' - ', vabp.SystemNameBeneficiary) as AgreementBetween,
    	vabp.AgreementBudget,
        vabp.ProjectId AS ProjectId,
        vabp.Level as Level,
		vabp.ParticipantIdPayer AS ParticipantIdPayer,
        vabp.SystemNamePayer AS SystemNamePayer,
        vabp.ParticipantIdBeneficiary AS ParticipantIdBeneficiary,
        vabp.SystemNameBeneficiary AS SystemNameBeneficiary,
		vabp.RemunerationModelCode AS RemunerationModelCode,
        ' ' AS ExpenseId,
        ' ' as ExpenseTypeName,
        (SELECT IF((COUNT(1) > 0), 'Rates missing', 'Rates OK') 
			FROM vPPIndividualRatesUpline ppp
            WHERE
			 vabp.AgreementBetweenParticipantsId = ppp.AgreementBetweenParticipantsId
             and vpp.RemunerationTypeName = ppp.RemunerationTypeName
             and ppp.RateForDate < 0) AS RateIssues,
		vpp.ProjectTitle,
		sum(vpp.NumberOfUnits), 
        vpp.UnitTypeName,
        sum(vpp.NumberOfUnits * vpp.RateForDate),
		vabp.Description AS Description
        
    FROM
		vAgreementBetweenParticipants vabp
		join vPPIndividualRatesUpline vpp on vabp.AgreementBetweenParticipantsId = vpp.AgreementBetweenParticipantsId
   WHERE vabp.RemunerationModelCode <> "RECOVERABLE_EXPENSE"
   GROUP BY 
		vpp.ProjectId, 
		vpp.AgreementBetweenParticipantsId,
		vpp.UnitTypeName;		



UNION;
 SELECT 
    	vre.AgreementBetweenParticipantsId AS AgreementBetweenParticipantsId,
        vre.Agreement as AgreementBetween,
    	vre.ExpenseBudget as AgreementBudget,
        vre.ProjectId AS ProjectId,
        vre.Level as Level,
		vre.ParticipantIdPayer,
        vre.SystemNamePayer AS SystemNamePayer,
        vre.ParticipantIdBeneficiary AS ParticipantIdBeneficiary,
        vre.SystemNameBeneficiary AS SystemNameBeneficiary,
		"RECOVERABLE_EXPENSE" AS RemunerationModelCode,
        vre.ExpenseTypeId,
        vre.ExpenseTypeName,
        (SELECT IF((COUNT(1) > 0), 'Rates missing', 'Rates OK') 
			FROM vPPExpenseRatesUpline vppe
           WHERE
			 vre.AgreementBetweenParticipantsId = vppe.AgreementBetweenParticipantsId
             and vre.UnitTypeCode = vppe.UnitTypeCode
             and vppe.ExpenseRateForDate < 0) AS RateIssues,
		 vre.ProjectTitle as ProjectTitle,
		sum(vpp.NumberOfUnits), 
		vpp.UnitTypeName,
        sum(vpp.NumberOfUnits * vpp.ExpenseRateForDate),
		vre.ExpenseAgreementDescription AS Description
    FROM
        vRecoverableExpenseAgreement vre

		join vPPExpenseRatesUpline vpp on vre.RecoverableExpenseId = vpp.RecoverableExpenseId
    GROUP BY 
		vpp.ProjectId, 
		vpp.AgreementBetweenParticipantsId,
        vpp.ExpenseTypeId,
        vpp.UnitTypeCode;