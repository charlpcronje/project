SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE

VIEW vProjectExpense AS
    SELECT 
	
		pe.ProjectExpenseId,  			            
		pe.ProjectParticipantIdPayer,               
			pp.participantId AS ParticipantIdPayer ,  		                
			p1.SystemName AS ParticipantPayerSystemName,               
			pp.ProjectId,  			                	
			vp.ProjectNameText as ProjectName,  		
			vp.SubProjNumber,  		
			
		pe.ParticipantIdMadePurchase,               	
			p2.SystemName AS ParticipantMadePurchaseSystemName, 		
		pe.ParticipantIdVendor,  		                    
			p3.SystemName AS ParticipantVendorSystemName,               
		pe.ExpenseTypeId,  			                   
			e.Name AS ExpenseTypeName,                      
			e.Description AS ExpenseTypeDescription,  	
			e.UnitTypeCode,
			ut.Name as UnitTypeName,
			e.ExpenseTypeParentId,
			ep.Name as ExpenseTypeParentName,
		pe.AssetId,  					                    
			a.VehicleId,  			                        
			v.Name AS VehicleName,  		                        
			a.Description AS AssetDescription,  			                    
		pe.PaymentMethodCode,  		                        
			pm.Name AS PaymentMethodName,  	                        
		pe.BankCardIdUsed,  			                    
			bc.CardNumber AS BankCardNumber,  		                    
			bc.NameOnCard AS BankCardNameOnCard,                    
			bc.Description AS BankCardDescription,                       
		pe.ParticipantBankDetailsIdUsed,  			                    
			pbd.AccountNumber  AS AccountNumber,  		                    
			pbd.Name   AS ParticipantBankDetailsName,                    
			pbd.Description  AS ParticipantBankDetailsDescription,  	  	                    
		pe.TaxDeductableCategoryId,                       
			tdc.Name  AS TaxDeductableCategoryName,          
		pe.PaymentDescription,  		                    
		pe.PurchaseDate,  				            		
		pe.NumberOfUnits,  			                        
		pe.AmountPerUnit,  			                		
 			                    
		pe.NoteToAccountant,
		pe.FullyLinked,
		pe.BankReference,
        e.AllowanceFlag,
        (pe.NumberOfUnits * pe.AmountPerUnit ) as LineTotal

       
    FROM
		ProjectExpense pe 
        join ProjectParticipant pp on (pe.ProjectParticipantIdPayer = pp.ProjectParticipantId)
        join Participant p1 ON (pp.ParticipantId = p1.ParticipantId)
		join vProject vp ON (pp.ProjectId = vp.ProjectId)
		join Participant p2 on (pe.ParticipantIdMadePurchase = p2.ParticipantId)
		left join Participant p3 on (pe.ParticipantIdVendor = p3.ParticipantId)	
        left join ExpenseType e ON (pe.ExpenseTypeId = e.ExpenseTypeId)	
        left join UnitType ut ON (e.UnitTypeCode = ut.UnitTypeCode)	
        left join ExpenseTypeParent ep ON (e.ExpenseTypeParentId = ep.ExpenseTypeParentId)
        left join Asset a ON (pe.AssetId = a.AssetId)
		left join Vehicle v On (a.VehicleId = v.VehicleId)
		left join PaymentMethod pm on (pe.PaymentMethodCode = pm.PaymentMethodCode)
		left join BankCard bc On  (pe.BankCardIdUsed = bc.BankCardId)
		left join ParticipantBankDetails pbd On  (pe.ParticipantBankDetailsIdUsed = pbd.ParticipantBankDetailsId)
		left join TaxDeductableCategory tdc ON pe.TaxDeductableCategoryId = tdc.TaxDeductableCategoryId
        
		
		
  