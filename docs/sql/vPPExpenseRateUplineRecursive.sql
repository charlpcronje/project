SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
    SQL SECURITY DEFINER
VIEW vPpExpenseRateUplineRecursive AS 
WITH RECURSIVE vpp 
	(
			RowNumber,
			ProjectExpenseId,
			ProjectParticipantIdMadeOrigPayment, 
			ParticipantIdMadeOrigPayment, 
			ParticipantMadeOrigPayment,
            ParticipantInAgreementContracting,
            ParticipantInAgreementContracted,
            
			ExpenseTypeName, 
			ExpenseRateForDate, --  -4: no agreement, -2: no rates, -1: Rates overlap, otherwise the CurrentRate

			RecoverableExpenseId,
			ExpenseHandlingPerc,
			MaxExpenseAmtPerUnit,
			ExpenseTypeId, 
			PaymentDescription, 
			PurchaseDate, 
			NumberOfUnits, 
			AmountPerUnit, 
			ParticipantIdMadePurchase, 
			ParticipantMadePurchaseSystemName, 
			ExpenseTypeParentId, 
			ExpenseTypeParentName, 
			-- ExpenseTypeId, 
			ProjectId, 
			ProjectName, 
			UnitTypeCode, 
			UnitTypeName, 

			AgreementBetweenParticipantsId,
			ProjectParticipantIdAboveMe,
            
			ProjectParticipantIdContracting,
			ParticipantIdContracting,
            ProjectParticipantIdContracted,
			ParticipantIdContracted,
            LineTotal,
			ParticipantIdVendor,
			ParticipantVendorSystemName,
			AssetId,  					                    
			VehicleId,  			                        
			VehicleName,  		                        
			AssetDescription,
			
			PaymentMethodCode,  		                        
			PaymentMethodName,  	                        
			BankCardIdUsed,  			                    
			BankCardNumber,  		                    
			BankCardNameOnCard,                    
			BankCardDescription,                       
			ParticipantBankDetailsIdUsed,  			                    
			AccountNumber,  		                    
			ParticipantBankDetailsName,                    
			ParticipantBankDetailsDescription,  	  	                    
			TaxDeductableCategoryId,                       
			TaxDeductableCategoryName        


			
	)

AS ( SELECT 
			(vpe.ProjectExpenseId * vpe.ProjectParticipantIdPayer * vpe.ParticipantIdPayer) AS RowNumber,
			vpe.ProjectExpenseId,
			vpe.ProjectParticipantIdPayer as ProjectParticipantIdMadeOrigPayment, 
			vpe.ParticipantIdPayer as ParticipantIdMadeOrigPayment, 
			vpe.ParticipantPayerSystemName as ParticipantMadeOrigPayment,
            
            pabove.SystemName as ParticipantInAgreementContracting,
            p.SystemName as ParticipantInAgreementContracted,
            
			vpe.ExpenseTypeName, 
			getExpenseRateForDate	(	
				  re.RecoverableExpenseId, 
				  vpe.PurchaseDate,
				  vpe.AmountPerUnit) AS ExpenseRateForDate, -- -4: no agreement, -2: no rates, -1: Rates overlap, otherwise the CurrentRate

			re.RecoverableExpenseId,
			er.ExpenseHandlingPerc,
			er.MaxExpenseAmtPerUnit,
			vpe.ExpenseTypeId, 

			vpe.PaymentDescription, 
			vpe.PurchaseDate, 
			vpe.NumberOfUnits, 
			vpe.AmountPerUnit, 
			vpe.ParticipantIdMadePurchase, 
			vpe.ParticipantMadePurchaseSystemName, 
			vpe.ExpenseTypeParentId, 
			vpe.ExpenseTypeParentName, 
			-- vpe.ExpenseTypeId, 
			vpe.ProjectId, 
			vpe.ProjectName, 
			vpe.UnitTypeCode, 
			vpe.UnitTypeName, 

			abp.AgreementBetweenParticipantsId,
			pp.ProjectParticipantIdAboveMe,	
            
            ppabove.ProjectParticipantId as ProjectParticipantIdContracting,
			ppabove.ParticipantId as ParticipantIdContracting,
            pp.ProjectParticipantId as ProjectParticipantIdContracted,
			pp.ParticipantId as ParticipantIdContracted,
			vpe.NumberOfUnits * (getExpenseRateForDate(re.RecoverableExpenseId, vpe.PurchaseDate, vpe.AmountPerUnit)) AS LineTotal,
			vpe.ParticipantIdVendor,
			vpe.ParticipantVendorSystemName,
			vpe.AssetId,  					                    
			vpe.VehicleId,  			                        
			vpe.VehicleName,  		                        
			vpe.AssetDescription,
			
			vpe.PaymentMethodCode,  		                        
			vpe.PaymentMethodName,  	                        
			vpe.BankCardIdUsed,  			                    
			vpe.BankCardNumber,  		                    
			vpe.BankCardNameOnCard,                    
			vpe.BankCardDescription,                       
			vpe.ParticipantBankDetailsIdUsed,  			                    
			vpe.AccountNumber,  		                    
			vpe.ParticipantBankDetailsName,                    
			vpe.ParticipantBankDetailsDescription,  	  	                    
			vpe.TaxDeductableCategoryId,                       
			vpe.TaxDeductableCategoryName
            

		from vProjectExpense vpe
		left join AgreementBetweenParticipants abp 	on 		abp.ProjectParticipantId = vpe.ProjectParticipantIdPayer
													and		abp.RemunerationModelCode = 'RECOVERABLE_EXPENSE'
		left join ProjectParticipant	pp			on 		abp.ProjectParticipantId = pp.ProjectParticipantId
        left join Participant p						on 		p.ParticipantId = pp.ParticipantId
		left join RecoverableExpense re 			on 		re.AgreementBetweenParticipantsId = 	abp.AgreementBetweenParticipantsId
													and 	re.ExpenseTypeId = vpe.ExpenseTypeId
		left join ExpenseRate er					on 		er.RecoverableExpenseId = re.RecoverableExpenseId                                            
        left join ProjectParticipant ppabove		on 		pp.ProjectParticipantIdAboveMe = ppabove.ProjectParticipantId
        left join Participant pabove				on 		pabove.ParticipantId = ppabove.ParticipantId

     UNION ALL
     SELECT 
			(vpe2.ProjectExpenseId * vpe2.ProjectParticipantIdPayer * vpe2.ParticipantIdPayer) AS RowNumber,
			vpe2.ProjectExpenseId,
			vpe2.ProjectParticipantIdPayer as ProjectParticipantIdMadeOrigPayment, 
			vpe2.ParticipantIdPayer as ParticipantIdMadeOrigPayment, 
			vpe2.ParticipantPayerSystemName as ParticipantMadeOrigPayment,

            pabove2.SystemName as ParticipantInAgreementContracting,
            p2.SystemName as ParticipantInAgreementContracted,

			vpe2.ExpenseTypeName, 
			getExpenseRateForDate	(	
			 	  re2.RecoverableExpenseId, 
				  vpe2.PurchaseDate,
				  vpe2.AmountPerUnit) AS ExpenseRateForDate, -- -4: no agreement, -2: no rates, -1: Rates overlap, otherwise the CurrentRate

			re2.RecoverableExpenseId,
			er2.ExpenseHandlingPerc,
			er2.MaxExpenseAmtPerUnit,

			vpe2.ExpenseTypeId, 
			
            vpe2.PaymentDescription, 
			vpe2.PurchaseDate, 
			vpe2.NumberOfUnits, 
			vpe2.AmountPerUnit, 
			vpe2.ParticipantIdMadePurchase, 
			vpe2.ParticipantMadePurchaseSystemName, 
			vpe2.ExpenseTypeParentId, 
			vpe2.ExpenseTypeParentName, 
			-- vpe2.ExpenseTypeId, 
			vpe2.ProjectId, 
			vpe2.ProjectName, 
			vpe2.UnitTypeCode, 
			vpe2.UnitTypeName, 

			abp2.AgreementBetweenParticipantsId,
			pp2.ProjectParticipantIdAboveMe,

            ppabove2.ProjectParticipantId as ProjectParticipantIdContracting,
			ppabove2.ParticipantId as ParticipantIdContracting,
            pp2.ProjectParticipantId as ProjectParticipantIdContracted,
			pp2.ParticipantId as ParticipantIdContracted,
			vpe2.NumberOfUnits * (getExpenseRateForDate	(re2.RecoverableExpenseId, vpe2.PurchaseDate,vpe2.AmountPerUnit)) AS LineTotal,
			vpe2.ParticipantIdVendor,
			vpe2.ParticipantVendorSystemName,
			vpe2.AssetId,  					                    
			vpe2.VehicleId,  			                        
			vpe2.VehicleName,  		                        
			vpe2.AssetDescription,
			
			vpe2.PaymentMethodCode,  		                        
			vpe2.PaymentMethodName,  	                        
			vpe2.BankCardIdUsed,  			                    
			vpe2.BankCardNumber,  		                    
			vpe2.BankCardNameOnCard,                    
			vpe2.BankCardDescription,                       
			vpe2.ParticipantBankDetailsIdUsed,  			                    
			vpe2.AccountNumber,  		                    
			vpe2.ParticipantBankDetailsName,                    
			vpe2.ParticipantBankDetailsDescription,  	  	                    
			vpe2.TaxDeductableCategoryId,                       
			vpe2.TaxDeductableCategoryName
			

		from 	 vpp yd
		join 	 vProjectExpense vpe2					on		yd.ProjectExpenseId = vpe2.ProjectExpenseId
		left join AgreementBetweenParticipants abp2 	on 			abp2.ProjectParticipantId = yd.ProjectParticipantIdAboveMe
														and		abp2.RemunerationModelCode = 'RECOVERABLE_EXPENSE'
		left join ProjectParticipant	pp2				on 			abp2.ProjectParticipantId = pp2.ProjectParticipantId
        left join Participant p2						on 			p2.ParticipantId = pp2.ParticipantId
		left join RecoverableExpense re2 				on 			re2.AgreementBetweenParticipantsId = 	abp2.AgreementBetweenParticipantsId
														and 	re2.ExpenseTypeId = vpe2.ExpenseTypeId
		left join ExpenseRate er2						on 			er2.RecoverableExpenseId = re2.RecoverableExpenseId                                            
        left join ProjectParticipant ppabove2			on 			pp2.ProjectParticipantIdAboveMe = ppabove2.ProjectParticipantId
        join Participant pabove2						on 			pabove2.ParticipantId = ppabove2.ParticipantId
        
        
        
	)

SELECT 
	*
FROM vpp
GO

