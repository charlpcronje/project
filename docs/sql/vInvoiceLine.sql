CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vInvoiceLine AS
    SELECT 
        il.InvoiceLineId,
    	i.InvoiceId, 
        i.ParticipantIdFrom, 
        pf.SystemName as ParticipantFrom,
        i.ParticipantIdTo, 
        pt.SystemName as ParticipantTo,
        i.InvoiceNumber, 
        i.InvoiceTotalAmountInclTax as InvoiceAmount, 
        i.InvoiceDate,
        il.ProjectId,
        p.ProjectNameText,
		il.LineType, 
        il.RatesMissing, 
        il.TotalUnits, 
        il.LineAmount,
        il.AgreementBetweenParticipantsId,
        CONCAT(left('0000', 4 - length(p.ParticipantIdHost)), p.ParticipantIdHost,' - ',p.ProjectNumberText) as ProjectNumberOnly,
		p.Title as ProjectTitleOnly
    FROM
		Invoice i
        join InvoiceLine il on  i.InvoiceId = il.InvoiceId
        join vProject p	on 	il.ProjectId = p.ProjectId
        join Participant pf	on 	i.ParticipantIdFrom = pf.ParticipantId
        join Participant pt	on 	i.ParticipantIdTo = pt.ParticipantId
