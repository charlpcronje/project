SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
VIEW vIndivProjectExpense AS
    SELECT 
		ipe.IndivProjectExpenseId, 
        ipe.ProjectParticipantId, 
        ipe.ExpenseTypeId, 
        ipe.PaymentDescription, 
        ipe.PurchaseDate, 
        ipe.NumberOfUnits, 
        ipe.AmountPerUnit, 
        ipe.PhotoOfInvoice, 
        ipe.NoteToAccountant, 

        ip.ParticipantId AS ParticipantIdIndividual,
		ip.SystemName AS IndividualSystemName,
        
		-- Expense Type
        etp.ExpenseTypeParentId AS ExpenseTypeParentId,
        etp.Name AS ExpenseTypeParentName,
        -- et.ExpenseTypeId,
        et.Name AS ExpenseTypeName,
        
        -- Project and ProjectParticipant
        pv.ProjectId as ProjectId,
        pv.ParticipantIdHost, 
        pv.ProjectNumberBigInt as ProjectNumberBigInt,
        pv.Title as ProjectTitle,
        pv.ProjectNameText as ProjectName,
		ptl.SystemName as HostParticipant,
		
		-- Unit Type
        ut.UnitTypeCode as UnitTypeCode,
        ut.Name as UnitTypeName

    FROM
		IndivProjectExpense ipe 
        join ProjectParticipant pp on (ipe.ProjectParticipantId = pp.ProjectParticipantId)
        join Participant ip ON (pp.ParticipantId = ip.ParticipantId)
        join ExpenseType et ON (ipe.ExpenseTypeId = et.ExpenseTypeId)
        join ExpenseTypeParent etp ON (et.ExpenseTypeParentId = etp.ExpenseTypeParentId)
        join vProject pv on pp.ProjectId = pv.ProjectId	
        join Participant ptl on pv.ParticipantIdHost = ptl.ParticipantId
        join UnitType ut on et.UnitTypeCode = ut.UnitTypeCode

        