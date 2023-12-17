SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE

VIEW vProjectExpenseMin AS
    SELECT 
	
		pe.ProjectExpenseId,  			            
		pe.ProjectParticipantIdPayer,               
			pp.participantId AS ParticipantIdPayer ,  		                
			p1.SystemName AS ParticipantPayerSystemName,               
			pp.ProjectId,  			                	
			vp.ProjectNameText as ProjectName,  		
			
		pe.ExpenseTypeId,  			                   
			e.Name AS ExpenseTypeName,                      
			e.UnitTypeCode,
			ut.Name as UnitTypeName,
            e.AllowanceFlag,
		pe.AssetId,  					                    
			a.VehicleId,  			                        
			v.Name AS VehicleName,  		                        
			a.Description AS AssetDescription,  			                    
		pe.PaymentDescription,  		                    
		pe.PurchaseDate,  				            		
		pe.NumberOfUnits,  			                        
		pe.AmountPerUnit,
        (pe.NumberOfUnits * pe.AmountPerUnit ) as LineTotal
       
    FROM
		ProjectExpense pe 
        join ProjectParticipant pp on (pe.ProjectParticipantIdPayer = pp.ProjectParticipantId)
        join Participant p1 ON (pp.ParticipantId = p1.ParticipantId)
		join vProject vp ON (pp.ProjectId = vp.ProjectId)
        left join ExpenseType e ON (pe.ExpenseTypeId = e.ExpenseTypeId)	
        left join UnitType ut ON (e.UnitTypeCode = ut.UnitTypeCode)	
        left join Asset a ON (pe.AssetId = a.AssetId)
		left join Vehicle v On (a.VehicleId = v.VehicleId)
        
		
		
  