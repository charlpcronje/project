
-- FOR: ig_db  2023-12-10 10:57:28  
-- To delete all Database Objects, run this script.  --
-- -----------------------------------------  --
-- THIS SCRIPT DELETES ALL DATABASE OBJECTS!  --
-- =========================================  --
-- All Foreign Keys:
alter table ig_db.agreementbetweenparticipants DROP FOREIGN KEY ProjectParticipantID;
alter table ig_db.agreementbetweenparticipants DROP FOREIGN KEY RemunerationModelCodefk;
alter table ig_db.agreementstage DROP FOREIGN KEY fk_AgreementStage_AgreementBetweenParticipants1;
alter table ig_db.agreementstage DROP FOREIGN KEY fk_AgreementStage_ProjectStage1;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_AssetCondition1;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_AssetStatus1;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_AssetType1;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_Participant1;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_Participant2;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_Participant3;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_Participant4;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_ParticipantOffice1;
alter table ig_db.asset DROP FOREIGN KEY fk_Asset_Vehicle1;
alter table ig_db.assetfile DROP FOREIGN KEY fk_AssetFile_Asset1;
alter table ig_db.assetfile DROP FOREIGN KEY fk_AssetFile_Vehicle1;
alter table ig_db.assetuser DROP FOREIGN KEY fk_AssetUser_Asset1;
alter table ig_db.assetuser DROP FOREIGN KEY fk_AssetUser_Participant1;
alter table ig_db.assignment DROP FOREIGN KEY fk_Assignment_AssignmentGroup1;
alter table ig_db.assignment DROP FOREIGN KEY fk_Assignment_Individual1;
alter table ig_db.assignment DROP FOREIGN KEY fk_Assignment_Individual2;
alter table ig_db.assignment DROP FOREIGN KEY fk_Assignment_ProjectParticipant1;
alter table ig_db.assignment DROP FOREIGN KEY fk_Assignment_ProjectParticipant2;
alter table ig_db.assignmentgroup DROP FOREIGN KEY fk_AssignmentGroup_ProjectParticipant1;
alter table ig_db.bankcard DROP FOREIGN KEY fk_BankCard_CardType1;
alter table ig_db.bankcard DROP FOREIGN KEY fk_BankCard_IndJ;
alter table ig_db.bankcard DROP FOREIGN KEY fk_BankCard_ParticipantBankDetails1;
alter table ig_db.bankstatement DROP FOREIGN KEY fk_BankStatement_ParticipantBankDetails1;
alter table ig_db.banktransaction DROP FOREIGN KEY fk_BankStatementTransaction_BankStatement1;
alter table ig_db.banktransaction DROP FOREIGN KEY fk_StatementTransaction_Participant1;
alter table ig_db.banktransaction DROP FOREIGN KEY fk_StatementTransaction_ParticipantBankDetails1;
alter table ig_db.banktransactionlink DROP FOREIGN KEY fk_BankTransationLink_BankTransaction1;
alter table ig_db.banktransactionlink DROP FOREIGN KEY fk_BankTransationLink_Invoice1;
alter table ig_db.banktransactionlink DROP FOREIGN KEY fk_BankTransationLink_Participant1;
alter table ig_db.banktransactionlink DROP FOREIGN KEY fk_BankTransationLink_ProjectExpense1;
alter table ig_db.branch DROP FOREIGN KEY BankCode;
alter table ig_db.city DROP FOREIGN KEY fk_City_Country1;
alter table ig_db.city DROP FOREIGN KEY fk_City_Province1;
alter table ig_db.contactpoint DROP FOREIGN KEY fk_ParticipantOfficeId;
alter table ig_db.contactpoint DROP FOREIGN KEY fk_SuburbId;
alter table ig_db.deliverabletype DROP FOREIGN KEY fk_DeliverableType_ServiceDiscipline1;
alter table ig_db.expenserate DROP FOREIGN KEY fk_ExpenseRate_RecoverableExpense1;
alter table ig_db.expensetype DROP FOREIGN KEY fk_ExpenseTypeParent;
alter table ig_db.expensetype DROP FOREIGN KEY fk_ExpenseType_UnitType1;
alter table ig_db.genprocedure DROP FOREIGN KEY fk_Procedure_ProcedureStatus1;
alter table ig_db.genprocedure DROP FOREIGN KEY fk_ServDis_Gen_fk;
alter table ig_db.individual DROP FOREIGN KEY fk_CountryCode;
alter table ig_db.individual DROP FOREIGN KEY fk_Participant;
alter table ig_db.individualcompetency DROP FOREIGN KEY fk_IndividualCompetency_CompetencyLevel1;
alter table ig_db.individualcompetency DROP FOREIGN KEY fk_IndividualCompetency_IndividualSd1;
alter table ig_db.individualprofregistration DROP FOREIGN KEY fk_IndividualProfRegistration_ProfessionalInstitute1;
alter table ig_db.individualprofregistration DROP FOREIGN KEY fk_IndividualSD_Individual100;
alter table ig_db.individualqualification DROP FOREIGN KEY fk_IndividualSD_Individual10;
alter table ig_db.individualqualification DROP FOREIGN KEY fk_IndividuaQualification_InstituteQualification1;
alter table ig_db.individualsd DROP FOREIGN KEY fk_IndividualSD_Individual1;
alter table ig_db.individualsd DROP FOREIGN KEY fk_IndividualSD_SdRole1;
alter table ig_db.individualsdrole DROP FOREIGN KEY fk_IndividualSdRole_CompetencyLevel11111;
alter table ig_db.individualsdrole DROP FOREIGN KEY fk_IndividualSD_Individual1333;
alter table ig_db.individualsdrole DROP FOREIGN KEY fk_IndividualSD_SdRole12222;
alter table ig_db.institutequalification DROP FOREIGN KEY fk_Qualification_StudyInstitute1;
alter table ig_db.invoice DROP FOREIGN KEY fk_Invoice_Participant1;
alter table ig_db.invoice DROP FOREIGN KEY fk_Invoice_Participant2;
alter table ig_db.invoicefile DROP FOREIGN KEY fk_InvoiceFile_Invoice1;
alter table ig_db.invoiceline DROP FOREIGN KEY fk_InvoiceLine_AgreementBetweenParticipants1;
alter table ig_db.invoiceline DROP FOREIGN KEY fk_InvoiceLine_Invoice1;
alter table ig_db.invoiceline DROP FOREIGN KEY fk_InvoiceLine_Project1;
alter table ig_db.invoicelinedetail DROP FOREIGN KEY fk_InvoiceLineDetail_InvoiceLine1;
alter table ig_db.mainmembermedicalinsurance DROP FOREIGN KEY fk_IndividualMedicalInsurance_Individual1;
alter table ig_db.mainmembermedicalinsurance DROP FOREIGN KEY fk_IndividualMedicalInsurance_MedicalInsuranceCompany1;
alter table ig_db.mainmembermedicalinsurance DROP FOREIGN KEY fk_IndividualMedicalInsurance_MedicalInsurancePlan1;
alter table ig_db.medicaldependant DROP FOREIGN KEY fk_MedicalDependant_Individual1;
alter table ig_db.medicaldependant DROP FOREIGN KEY fk_MedicalDependant_MainMemberMedicalInsurance1;
alter table ig_db.medicalinsuranceplan DROP FOREIGN KEY fk_MedicalInsurancePlan_MedicalInsuranceCompany1;
alter table ig_db.nonprojectrelatedagreement DROP FOREIGN KEY fk_HumanResource_ResourceType10;
alter table ig_db.nonprojectrelatedagreement DROP FOREIGN KEY fk_NonProjectRelatedAgreement_Participant1;
alter table ig_db.nonprojectrelatedagreement DROP FOREIGN KEY fk_NonProjectRelatedAgreement_Participant2;
alter table ig_db.participant DROP FOREIGN KEY fk_MarketingIndividual;
alter table ig_db.participant DROP FOREIGN KEY fk_ParticipantBankDetailsIdDefault;
alter table ig_db.participant DROP FOREIGN KEY fk_ParticipantIdBUParent;
alter table ig_db.participant DROP FOREIGN KEY fk_ParticipantOfficeIdDefault;
alter table ig_db.participant DROP FOREIGN KEY fk_ParticipantType_01;
alter table ig_db.participant DROP FOREIGN KEY fk_ProjectSustenance;
alter table ig_db.participant DROP FOREIGN KEY fk_RepresentativeIndividual;
alter table ig_db.participant DROP FOREIGN KEY fk_TapSubscriptionCode;
alter table ig_db.participantbankdetails DROP FOREIGN KEY fk_AccountTypeJ;
alter table ig_db.participantbankdetails DROP FOREIGN KEY fk_bankCodeJ;
alter table ig_db.participantbankdetails DROP FOREIGN KEY fk_BranchCode;
alter table ig_db.participantbankdetails DROP FOREIGN KEY fk_ParticipantId_ParticipantBankDetails;
alter table ig_db.participantoffice DROP FOREIGN KEY fk_ContactPointIdDefault;
alter table ig_db.participantoffice DROP FOREIGN KEY fk_ParticipantOffice_Participant;
alter table ig_db.personresponsible DROP FOREIGN KEY fk_PersonResponsible_GenProcedure1;
alter table ig_db.personresponsible DROP FOREIGN KEY fk_PersonResponsible_RoleOnAProject1;
alter table ig_db.proceduredefinition DROP FOREIGN KEY fk_ProcedureDefinition_Definition1;
alter table ig_db.proceduredefinition DROP FOREIGN KEY fk_ProcedureDefinition_GenProcedure1;
alter table ig_db.procedurerelateddocs DROP FOREIGN KEY fk_ProcedureRelatedDocs_GenProcedure1;
alter table ig_db.procedurerelateddocs DROP FOREIGN KEY fk_ProcedureRelatedDocs_RelatedDocs1;
alter table ig_db.projbasedremuntype DROP FOREIGN KEY fk_ProjBasedRemunType_UnitType1;
alter table ig_db.projbasedremuntype DROP FOREIGN KEY fk_remun_int;
alter table ig_db.project DROP FOREIGN KEY fk_Project_Individual1;
alter table ig_db.project DROP FOREIGN KEY fk_Project_Project1;
alter table ig_db.project DROP FOREIGN KEY fk_Project_ProjectParticipant;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_Expense_BankCard10;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_PBD;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_Asset1;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_ExpenseType10;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_Participant1;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_Participant10;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_PaymentMethod;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_ProjectParticipant1;
alter table ig_db.projectexpense DROP FOREIGN KEY fk_ProjectExpense_TaxDeductableCategory10;
alter table ig_db.projectexpensefile DROP FOREIGN KEY fk_ProjectExpenseUpload_ProjectExpense1;
alter table ig_db.projectparticipant DROP FOREIGN KEY fk_ProjectParticipantRelationship_Project1;
alter table ig_db.projectparticipant DROP FOREIGN KEY fk_ProjectParticipant_Participant1;
alter table ig_db.projectparticipant DROP FOREIGN KEY fk_ProjectParticipant_ProjectParticipant1;
alter table ig_db.projectparticipantsdrole DROP FOREIGN KEY fk_ProjectParticipantSdRole_ProjectSd1;
alter table ig_db.projectparticipantsdrole DROP FOREIGN KEY fk_ProjectParticipantSd_copy1_ProjectParticipant1;
alter table ig_db.projectparticipantsdrole DROP FOREIGN KEY fk_ProjectParticipantSd_copy1_SdRole1;
alter table ig_db.projectsd DROP FOREIGN KEY fk_ProjectSd_Project1;
alter table ig_db.projectsd DROP FOREIGN KEY fk_ServDis_projSd_fk;
alter table ig_db.projectstage DROP FOREIGN KEY fk_ProjectStage_Project1;
alter table ig_db.projectteam DROP FOREIGN KEY fk_ProjectTeam_Project1;
alter table ig_db.province DROP FOREIGN KEY fk_Province_Country1;
alter table ig_db.recoverableexpense DROP FOREIGN KEY fk_RecoverableExpense_AgreementBetweenParticipants1;
alter table ig_db.recoverableexpense DROP FOREIGN KEY fk_RecoverableExpense_ExpenseType1;
alter table ig_db.remunerationrateupline DROP FOREIGN KEY fk_RemunerationRateUpline_AgreementBetweenParticipants1;
alter table ig_db.remunerationrateupline DROP FOREIGN KEY fk_RemunerationRateUpline_Participant1;
alter table ig_db.remunerationrateupline DROP FOREIGN KEY fk_RemunerationRateUpline_ProjBasedRemunType1;
alter table ig_db.remunerationrateupline DROP FOREIGN KEY fk_RemunerationRateUpline_ProjectParticipantSdRole1;
alter table ig_db.resourceremuneration DROP FOREIGN KEY fk_non;
alter table ig_db.resourceremuneration DROP FOREIGN KEY fk_ResourceRemuneration_ResourceRemunType1;
alter table ig_db.resourceremuntype DROP FOREIGN KEY fk_ResourceRemunType_UnitType1;
alter table ig_db.roleonaproject DROP FOREIGN KEY fk_serv_dis_role_fk;
alter table ig_db.rolepermission DROP FOREIGN KEY PermissionCode;
alter table ig_db.rolepermission DROP FOREIGN KEY RoleCode;
alter table ig_db.sdfolderstructure DROP FOREIGN KEY fk_SdFolderStructure_SdFolderStructure1;
alter table ig_db.sdfolderstructure DROP FOREIGN KEY fk_SdFolderStructure_ServiceDiscipline1;
alter table ig_db.sdfolderstructure DROP FOREIGN KEY fk_SdFolderStructure_TypicalFolderStructure1;
alter table ig_db.sdrole DROP FOREIGN KEY fk_SdRoleOnAProject_RoleOnAProject1;
alter table ig_db.sdrole DROP FOREIGN KEY fk_ServDis_SdRole;
alter table ig_db.servicediscipline DROP FOREIGN KEY fk_ServIndustry_fk;
alter table ig_db.servicediscipline DROP FOREIGN KEY fk_ServParent_fk;
alter table ig_db.soqtemplateitem DROP FOREIGN KEY fk_CotoEtcItem_SoqUnit1;
alter table ig_db.soqtemplateitem DROP FOREIGN KEY fk_SoqTemplateItem_SoqTemplateItem1;
alter table ig_db.soqtemplateitem DROP FOREIGN KEY fk_SoqTemplateItem_SoqTemplSubSchedule1;
alter table ig_db.soqtemplateprint DROP FOREIGN KEY fk_SoqPrintTemplate_SoqTemplate1;
alter table ig_db.soqtemplsubschedule DROP FOREIGN KEY fk_SoqTemplSubSchedule_SoqTemplate1;
alter table ig_db.suburb DROP FOREIGN KEY fk_Suburb_City1;
alter table ig_db.systemmember DROP FOREIGN KEY fk_SystemMember_Individual1;
alter table ig_db.systemmember DROP FOREIGN KEY fk_SystemMember_Role1;
alter table ig_db.task DROP FOREIGN KEY fk_Task_Assignment1;
alter table ig_db.task DROP FOREIGN KEY fk_Task_TaskImportance1;
alter table ig_db.taskrevision DROP FOREIGN KEY fk_TaskRevision_Task1;
alter table ig_db.tasksubmission DROP FOREIGN KEY fk_TaskSubmission_TaskRevision1;
alter table ig_db.tasktype DROP FOREIGN KEY fk_TaskType_AssignmentType1;
alter table ig_db.timesheet DROP FOREIGN KEY fk_Timesheet_Assignment1;
alter table ig_db.timesheet DROP FOREIGN KEY fk_Timesheet_Pp;
alter table ig_db.timesheet DROP FOREIGN KEY fk_Timesheet_ProjBasedRemunType1;
alter table ig_db.timesheet DROP FOREIGN KEY fk_Timesheet_ProjectParticipantSdRole1;
alter table ig_db.timesheet DROP FOREIGN KEY fk_Timesheet_Task1;
alter table ig_db.topdescriptionsused DROP FOREIGN KEY fk_TopDescriptionsUsed_ProjectParticipant1;
alter table ig_db.triplogger DROP FOREIGN KEY fk_TripLogger_Participant1;
alter table ig_db.triplogger DROP FOREIGN KEY fk_TripLogger_Participant2;
alter table ig_db.triplogger DROP FOREIGN KEY fk_TripLogger_Participant3;
alter table ig_db.triplogger DROP FOREIGN KEY fk_TripLogger_Project1;
alter table ig_db.triplogger DROP FOREIGN KEY fk_TripLogger_Vehicle1;
alter table ig_db.typicalfolderstructure DROP FOREIGN KEY fk_TypicalFolderStructure_ServiceDiscipline1;
alter table ig_db.typicalfolderstructure DROP FOREIGN KEY fk_TypicalFolderStructure_TypicalFolderStructure1;
alter table ig_db.uicomponent DROP FOREIGN KEY fk_UiComponent_Permission;
alter table ig_db.uicomponent DROP FOREIGN KEY fk_UiParent;
alter table ig_db.vehicle DROP FOREIGN KEY fk_Vehicle_VehicleModel1;
alter table ig_db.vehicle DROP FOREIGN KEY fk_Vehicle_VehicleTyreAndRimType1;
alter table ig_db.vehicleitem DROP FOREIGN KEY fk_VehicleItem_ItemType1;
alter table ig_db.vehicleitem DROP FOREIGN KEY fk_VehicleItem_Vehicle1;
alter table ig_db.vehiclemaintenance DROP FOREIGN KEY fk_VehicleMaintenance_ServiceType1;
alter table ig_db.vehiclemaintenance DROP FOREIGN KEY fk_VehicleServiceHistory_Vehicle1;
alter table ig_db.vehiclemodel DROP FOREIGN KEY fk_VehicleModel_VehicleMake1;
alter table ig_db.vehiclereading DROP FOREIGN KEY fk_VehicleReading_Vehicle1;
alter table ig_db.vehicleuser DROP FOREIGN KEY fk_VehicleUser_Participant1;
alter table ig_db.vehicleuser DROP FOREIGN KEY fk_VehicleUser_Vehicle1;
alter table ig_db.workflowdefinitionstep DROP FOREIGN KEY fk_WorkflowDefinitionStep_WorkflowDefinition1;
alter table ig_db.workflowprocess DROP FOREIGN KEY fk_WorkflowProcess_WorkflowDefinition1;
alter table ig_db.workflowprocesslog DROP FOREIGN KEY fk_WorkflowProcessLog_WorkflowProcess1;
--             --
-- All Functions:
DROP FUNCTION IF EXISTS ig_db.doDatesOverlapResourceRemuneration; --  ig_db Function with datatype: int
DROP FUNCTION IF EXISTS ig_db.getCostRateUplineIdForDate; --  ig_db Function with datatype: bigint
DROP FUNCTION IF EXISTS ig_db.getExpenseRateForDate; --  ig_db Function with datatype: decimal
DROP FUNCTION IF EXISTS ig_db.getProjectStageForDate; --  ig_db Function with datatype: varchar
DROP FUNCTION IF EXISTS ig_db.getRateForDate; --  ig_db Function with datatype: decimal
DROP FUNCTION IF EXISTS ig_db.getUnitTypeForDate; --  ig_db Function with datatype: varchar
--              --
-- All Procedures:
DROP PROCEDURE IF EXISTS ig_db.clearParticipantLogo; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.cloneParentAndOrSubProject; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAccountType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteActivity; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAgreementBetweenParticipants; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAsset; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssetCondition; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssetStatus; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssetType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssetUser; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssignment; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssignmentGroup; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteAssignmentType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBank; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBankAccountType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBankCard; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBankStatement; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBankTransaction; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBankTransactionLink; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteBranch; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteCardType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteCity; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteCompetencyLevel; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteContactPoint; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteCostRateUpline; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteCountry; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteDefinition; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteDeliverableType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteExpenseRate; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteExpenseType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteExpenseTypeParent; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteGenProcedure; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteHumanResource; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteIndividual; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteIndividualCompetency; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteIndividualProfRegistration; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteIndividualQualification; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteIndividualSdRole; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteInstituteQualification; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteInvoice; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteItemType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteMaintenanceType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteMedicalInsuranceCompany; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteMedicalInsurancePlan; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteNonProjectRelatedAgreement; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteParticipantBankDetails; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteParticipantOffice; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteParticipantStatus; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteParticipantType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deletePaymentMethod; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deletePaymentSchedule; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deletePaymentType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deletePermission; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deletePersonResponsible; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deletePpSdRoleStage; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProcedureDefinition; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProcedureRelatedDocs; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProcedureStatus; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProfessionalInstitute; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjBasedRemunType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProject; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectAgreement; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectExpense; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectExpenseAgreement; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectExpenseFile; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectParticipantSdRole; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectSd; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectSdStage; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectStage; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProjectStageType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteProvince; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteRecoverableExpense; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteRemunerationModel; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteRemunerationRateUpline; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteResource; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteResourceRemuneration; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteResourceRemunType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteRole; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteRoleOnAProject; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteRolePermission; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteSdRole; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteSdRole2; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteServiceDiscipline; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteStudyInstitute; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteSupportService; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTask; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTaskImportanceType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTaskRevision; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTaskSubmission; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTaskType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTaxDeductableCategory; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTimesheet; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTripLogger; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteTypicalFolderStructure; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteUiComponent; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteUnitType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleItem; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleMaintenance; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleMake; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleModel; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleReading; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleTyreAndRimType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.deleteVehicleUser; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.doInsertBankTransactionLink; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.doInsertTasksFromTaskType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.doLinkTransactionsToStatement; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.doMarkTaskAsComplete; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.doRemoveUiComponentFromPermission; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.generateDraftInvoice; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.getNextInvoiceNumber; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.quickDeleteProject; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveExpenseRate; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveIndividualCompetency; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveIndividualParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveIndividualSupportServices; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveLoginPass; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveNewIndividualParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveNewNonIndivParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveNewPPSdRole; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveNewTimesheet; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveNewVehicle; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveNonIndivParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveParticipantBankDetails; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveParticipantParticipationTypes; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveProjBasedRemunType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveProjectParentAndSubProject; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveProjectSdNew; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveProjectStage; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveProjectStageNew; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveRemoveLoginPass; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveRemunerationRateUpline; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveResourceRemuneration; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveRoleOnAProject; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveServiceDisciplineNew; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveServiceDisciplineUpdate; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveTimesheet; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveTopDescriptionsUsed; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.saveUnitType; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.setLastInvoiceNumberOnParticipant; --  ig_db Procedure
DROP PROCEDURE IF EXISTS ig_db.updatePPAboveMe; --  ig_db Procedure
--          --
-- All Tables:
DROP TABLE IF EXISTS ig_db.accounttype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.activity; --  ig_db Table
DROP TABLE IF EXISTS ig_db.agreementbetweenparticipants; --  ig_db Table
DROP TABLE IF EXISTS ig_db.agreementstage; --  ig_db Table
DROP TABLE IF EXISTS ig_db.api; --  ig_db Table
DROP TABLE IF EXISTS ig_db.asset; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assetcondition; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assetfile; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assetstatus; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assettype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assetuser; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assignment; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assignmentgroup; --  ig_db Table
DROP TABLE IF EXISTS ig_db.assignmenttype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.bank; --  ig_db Table
DROP TABLE IF EXISTS ig_db.bankcard; --  ig_db Table
DROP TABLE IF EXISTS ig_db.bankstatement; --  ig_db Table
DROP TABLE IF EXISTS ig_db.banktransaction; --  ig_db Table
DROP TABLE IF EXISTS ig_db.banktransactionlink; --  ig_db Table
DROP TABLE IF EXISTS ig_db.branch; --  ig_db Table
DROP TABLE IF EXISTS ig_db.calendar; --  ig_db Table
DROP TABLE IF EXISTS ig_db.cardtype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.city; --  ig_db Table
DROP TABLE IF EXISTS ig_db.competencylevel; --  ig_db Table
DROP TABLE IF EXISTS ig_db.contactpoint; --  ig_db Table
DROP TABLE IF EXISTS ig_db.country; --  ig_db Table
DROP TABLE IF EXISTS ig_db.definition; --  ig_db Table
DROP TABLE IF EXISTS ig_db.deliverabletype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.expenserate; --  ig_db Table
DROP TABLE IF EXISTS ig_db.expensetype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.expensetypeparent; --  ig_db Table
DROP TABLE IF EXISTS ig_db.genprocedure; --  ig_db Table
DROP TABLE IF EXISTS ig_db.health; --  ig_db Table
DROP TABLE IF EXISTS ig_db.individual; --  ig_db Table
DROP TABLE IF EXISTS ig_db.individualcompetency; --  ig_db Table
DROP TABLE IF EXISTS ig_db.individualprofregistration; --  ig_db Table
DROP TABLE IF EXISTS ig_db.individualqualification; --  ig_db Table
DROP TABLE IF EXISTS ig_db.individualsd; --  ig_db Table
DROP TABLE IF EXISTS ig_db.individualsdrole; --  ig_db Table
DROP TABLE IF EXISTS ig_db.institutequalification; --  ig_db Table
DROP TABLE IF EXISTS ig_db.invoice; --  ig_db Table
DROP TABLE IF EXISTS ig_db.invoicefile; --  ig_db Table
DROP TABLE IF EXISTS ig_db.invoiceline; --  ig_db Table
DROP TABLE IF EXISTS ig_db.invoicelinedetail; --  ig_db Table
DROP TABLE IF EXISTS ig_db.itemtype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.keyvaluepair; --  ig_db Table
DROP TABLE IF EXISTS ig_db.libraryfile; --  ig_db Table
DROP TABLE IF EXISTS ig_db.libraryfolder; --  ig_db Table
DROP TABLE IF EXISTS ig_db.mainmembermedicalinsurance; --  ig_db Table
DROP TABLE IF EXISTS ig_db.maintenancetype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.medicaldependant; --  ig_db Table
DROP TABLE IF EXISTS ig_db.medicalinsurancecompany; --  ig_db Table
DROP TABLE IF EXISTS ig_db.medicalinsuranceplan; --  ig_db Table
DROP TABLE IF EXISTS ig_db.nonprojectrelatedagreement; --  ig_db Table
DROP TABLE IF EXISTS ig_db.participant; --  ig_db Table
DROP TABLE IF EXISTS ig_db.participantbankdetails; --  ig_db Table
DROP TABLE IF EXISTS ig_db.participantoffice; --  ig_db Table
DROP TABLE IF EXISTS ig_db.participanttype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.paymentmethod; --  ig_db Table
DROP TABLE IF EXISTS ig_db.paymenttype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.permission; --  ig_db Table
DROP TABLE IF EXISTS ig_db.personresponsible; --  ig_db Table
DROP TABLE IF EXISTS ig_db.pma__relation; --  ig_db Table
DROP TABLE IF EXISTS ig_db.pma__table_info; --  ig_db Table
DROP TABLE IF EXISTS ig_db.pma__table_uiprefs; --  ig_db Table
DROP TABLE IF EXISTS ig_db.pma__tracking; --  ig_db Table
DROP TABLE IF EXISTS ig_db.pma__userconfig; --  ig_db Table
DROP TABLE IF EXISTS ig_db.ppsdrolestage; --  ig_db Table
DROP TABLE IF EXISTS ig_db.proceduredefinition; --  ig_db Table
DROP TABLE IF EXISTS ig_db.procedurerelateddocs; --  ig_db Table
DROP TABLE IF EXISTS ig_db.procedurestatus; --  ig_db Table
DROP TABLE IF EXISTS ig_db.professionalinstitute; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projbasedremuntype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.project; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectexpense; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectexpensefile; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectparticipant; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectparticipantsdrole; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectsd; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectsdstage; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectstage; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectstagetype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectstatustype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.projectteam; --  ig_db Table
DROP TABLE IF EXISTS ig_db.province; --  ig_db Table
DROP TABLE IF EXISTS ig_db.recoverableexpense; --  ig_db Table
DROP TABLE IF EXISTS ig_db.relateddocs; --  ig_db Table
DROP TABLE IF EXISTS ig_db.remunerationinterval; --  ig_db Table
DROP TABLE IF EXISTS ig_db.remunerationmodel; --  ig_db Table
DROP TABLE IF EXISTS ig_db.remunerationrateupline; --  ig_db Table
DROP TABLE IF EXISTS ig_db.report; --  ig_db Table
DROP TABLE IF EXISTS ig_db.reportparameter; --  ig_db Table
DROP TABLE IF EXISTS ig_db.resourceremuneration; --  ig_db Table
DROP TABLE IF EXISTS ig_db.resourceremuntype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.resourcetype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.role; --  ig_db Table
DROP TABLE IF EXISTS ig_db.roleonaproject; --  ig_db Table
DROP TABLE IF EXISTS ig_db.rolepermission; --  ig_db Table
DROP TABLE IF EXISTS ig_db.sdfolderstructure; --  ig_db Table
DROP TABLE IF EXISTS ig_db.sdrole; --  ig_db Table
DROP TABLE IF EXISTS ig_db.servicediscipline; --  ig_db Table
DROP TABLE IF EXISTS ig_db.soqtemplate; --  ig_db Table
DROP TABLE IF EXISTS ig_db.soqtemplateitem; --  ig_db Table
DROP TABLE IF EXISTS ig_db.soqtemplateprint; --  ig_db Table
DROP TABLE IF EXISTS ig_db.soqtemplsubschedule; --  ig_db Table
DROP TABLE IF EXISTS ig_db.soqunit; --  ig_db Table
DROP TABLE IF EXISTS ig_db.studyinstitute; --  ig_db Table
DROP TABLE IF EXISTS ig_db.suburb; --  ig_db Table
DROP TABLE IF EXISTS ig_db.systemmember; --  ig_db Table
DROP TABLE IF EXISTS ig_db.tapsubscription; --  ig_db Table
DROP TABLE IF EXISTS ig_db.task; --  ig_db Table
DROP TABLE IF EXISTS ig_db.taskimportancetype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.taskrevision; --  ig_db Table
DROP TABLE IF EXISTS ig_db.tasksubmission; --  ig_db Table
DROP TABLE IF EXISTS ig_db.tasktype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.taxdeductablecategory; --  ig_db Table
DROP TABLE IF EXISTS ig_db.timesheet; --  ig_db Table
DROP TABLE IF EXISTS ig_db.topdescriptionsused; --  ig_db Table
DROP TABLE IF EXISTS ig_db.triplogger; --  ig_db Table
DROP TABLE IF EXISTS ig_db.typicalfolderstructure; --  ig_db Table
DROP TABLE IF EXISTS ig_db.uicomponent; --  ig_db Table
DROP TABLE IF EXISTS ig_db.unittype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehicle; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehicleitem; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehiclemaintenance; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehiclemake; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehiclemodel; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehiclereading; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehicletyreandrimtype; --  ig_db Table
DROP TABLE IF EXISTS ig_db.vehicleuser; --  ig_db Table
DROP TABLE IF EXISTS ig_db.workflowdefinition; --  ig_db Table
DROP TABLE IF EXISTS ig_db.workflowdefinitionstep; --  ig_db Table
DROP TABLE IF EXISTS ig_db.workflowprocess; --  ig_db Table
DROP TABLE IF EXISTS ig_db.workflowprocesslog; --  ig_db Table
--         --
-- All Views:
DROP VIEW IF EXISTS ig_db.vagreementbetweenparticipants; --  ig_db View
DROP VIEW IF EXISTS ig_db.vagreementparticipants; --  ig_db View
DROP VIEW IF EXISTS ig_db.vasset; --  ig_db View
DROP VIEW IF EXISTS ig_db.vassignmentforindividuallist; --  ig_db View
DROP VIEW IF EXISTS ig_db.vassignmentlist; --  ig_db View
DROP VIEW IF EXISTS ig_db.vassignmentlistnewsubs; --  ig_db View
DROP VIEW IF EXISTS ig_db.vbankcard; --  ig_db View
DROP VIEW IF EXISTS ig_db.vbanktransaction; --  ig_db View
DROP VIEW IF EXISTS ig_db.vbanktransactionlink; --  ig_db View
DROP VIEW IF EXISTS ig_db.vcity; --  ig_db View
DROP VIEW IF EXISTS ig_db.vcitymin; --  ig_db View
DROP VIEW IF EXISTS ig_db.vcontactpoint; --  ig_db View
DROP VIEW IF EXISTS ig_db.vcountry; --  ig_db View
DROP VIEW IF EXISTS ig_db.vexpenserate; --  ig_db View
DROP VIEW IF EXISTS ig_db.vgeneratableinvoicetotalsnonnullvalues; --  ig_db View
DROP VIEW IF EXISTS ig_db.vhumanresourceunionlist; --  ig_db View
DROP VIEW IF EXISTS ig_db.vindividual; --  ig_db View
DROP VIEW IF EXISTS ig_db.vindividualparticipant; --  ig_db View
DROP VIEW IF EXISTS ig_db.vindividualprofregistration; --  ig_db View
DROP VIEW IF EXISTS ig_db.vindividualqualification; --  ig_db View
DROP VIEW IF EXISTS ig_db.vindividualroles; --  ig_db View
DROP VIEW IF EXISTS ig_db.vindividualsdrole; --  ig_db View
DROP VIEW IF EXISTS ig_db.vinstitutequalification; --  ig_db View
DROP VIEW IF EXISTS ig_db.vinvoicegeneratorlineamountsperprojectandexpense; --  ig_db View
DROP VIEW IF EXISTS ig_db.vinvoicegenlineamountsperprojecttotals; --  ig_db View
DROP VIEW IF EXISTS ig_db.vinvoiceline; --  ig_db View
DROP VIEW IF EXISTS ig_db.vlibraryfolders; --  ig_db View
DROP VIEW IF EXISTS ig_db.vnonprojectrelatedagreement; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipant; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipantbankdetails; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipantexpensecosttotals; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipantexpensecosttotalsperproject; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipantmin; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipanttimecosttotals; --  ig_db View
DROP VIEW IF EXISTS ig_db.vparticipanttimecosttotalsperproject; --  ig_db View
DROP VIEW IF EXISTS ig_db.vpersonresponsible; --  ig_db View
DROP VIEW IF EXISTS ig_db.vppexpenserateuplinerecursive; --  ig_db View
DROP VIEW IF EXISTS ig_db.vppindividualratesupline; --  ig_db View
DROP VIEW IF EXISTS ig_db.vppsdrolestage; --  ig_db View
DROP VIEW IF EXISTS ig_db.vpptree; --  ig_db View
DROP VIEW IF EXISTS ig_db.vproceduredefinition; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprocedurerelateddocs; --  ig_db View
DROP VIEW IF EXISTS ig_db.vproject; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectbankexpenses; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectexpense; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectexpensemin; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectlist; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectparticipant; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectparticipantlist; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectparticipantpayerben; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectparticipantsdroles; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectsd; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojectsdstage; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojecttimecost; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprojecttree; --  ig_db View
DROP VIEW IF EXISTS ig_db.vprovince; --  ig_db View
DROP VIEW IF EXISTS ig_db.vrecoverableexpense; --  ig_db View
DROP VIEW IF EXISTS ig_db.vrecoverableexpenseagreement; --  ig_db View
DROP VIEW IF EXISTS ig_db.vremunerationrateupline; --  ig_db View
DROP VIEW IF EXISTS ig_db.vroleonaproject; --  ig_db View
DROP VIEW IF EXISTS ig_db.vrolepermission; --  ig_db View
DROP VIEW IF EXISTS ig_db.vsdrole; --  ig_db View
DROP VIEW IF EXISTS ig_db.vservicediscipline; --  ig_db View
DROP VIEW IF EXISTS ig_db.vservicedisciplineroles; --  ig_db View
DROP VIEW IF EXISTS ig_db.vstatement; --  ig_db View
DROP VIEW IF EXISTS ig_db.vsuburb; --  ig_db View
DROP VIEW IF EXISTS ig_db.vsuburbmin; --  ig_db View
DROP VIEW IF EXISTS ig_db.vtfstree; --  ig_db View
DROP VIEW IF EXISTS ig_db.vtimesheet; --  ig_db View
DROP VIEW IF EXISTS ig_db.vtriplogger; --  ig_db View
DROP VIEW IF EXISTS ig_db.vtypicalfolderstructure; --  ig_db View
DROP VIEW IF EXISTS ig_db.vuicomponentmenu; --  ig_db View
-- --------------------------------------
-- Run all of the above in a new SQL Tab.
-- 'C:\Users\hugo\Downloads\ig_db Drop ALLES.sql' (this file)
-- THIS SCRIPT DELETES ALL DATABASE OBJECTS!
-- Remember to Refresh the Schema.
-- 2023-12-10 10:57:28 ------------------
-- -- -- -- -- -- -- -- -- -- ----
-- -- -- -- -- -- -- -- -- -- -- -
-- -- -- -- -- -- -- -- -- -- - --
-- The following is to only remove data from tables, before an import. --
/* 
USE ig_db;
SET foreign_key_checks = 0;
TRUNCATE ig_db.accounttype;
TRUNCATE ig_db.activity;
TRUNCATE ig_db.agreementbetweenparticipants;
TRUNCATE ig_db.agreementstage;
TRUNCATE ig_db.api;
TRUNCATE ig_db.asset;
TRUNCATE ig_db.assetcondition;
TRUNCATE ig_db.assetfile;
TRUNCATE ig_db.assetstatus;
TRUNCATE ig_db.assettype;
TRUNCATE ig_db.assetuser;
TRUNCATE ig_db.assignment;
TRUNCATE ig_db.assignmentgroup;
TRUNCATE ig_db.assignmenttype;
TRUNCATE ig_db.bank;
TRUNCATE ig_db.bankcard;
TRUNCATE ig_db.bankstatement;
TRUNCATE ig_db.banktransaction;
TRUNCATE ig_db.banktransactionlink;
TRUNCATE ig_db.branch;
TRUNCATE ig_db.calendar;
TRUNCATE ig_db.cardtype;
TRUNCATE ig_db.city;
TRUNCATE ig_db.competencylevel;
TRUNCATE ig_db.contactpoint;
TRUNCATE ig_db.country;
TRUNCATE ig_db.definition;
TRUNCATE ig_db.deliverabletype;
TRUNCATE ig_db.expenserate;
TRUNCATE ig_db.expensetype;
TRUNCATE ig_db.expensetypeparent;
TRUNCATE ig_db.genprocedure;
TRUNCATE ig_db.health;
TRUNCATE ig_db.individual;
TRUNCATE ig_db.individualcompetency;
TRUNCATE ig_db.individualprofregistration;
TRUNCATE ig_db.individualqualification;
TRUNCATE ig_db.individualsd;
TRUNCATE ig_db.individualsdrole;
TRUNCATE ig_db.institutequalification;
TRUNCATE ig_db.invoice;
TRUNCATE ig_db.invoicefile;
TRUNCATE ig_db.invoiceline;
TRUNCATE ig_db.invoicelinedetail;
TRUNCATE ig_db.itemtype;
TRUNCATE ig_db.keyvaluepair;
TRUNCATE ig_db.libraryfile;
TRUNCATE ig_db.libraryfolder;
TRUNCATE ig_db.mainmembermedicalinsurance;
TRUNCATE ig_db.maintenancetype;
TRUNCATE ig_db.medicaldependant;
TRUNCATE ig_db.medicalinsurancecompany;
TRUNCATE ig_db.medicalinsuranceplan;
TRUNCATE ig_db.nonprojectrelatedagreement;
TRUNCATE ig_db.participant;
TRUNCATE ig_db.participantbankdetails;
TRUNCATE ig_db.participantoffice;
TRUNCATE ig_db.participanttype;
TRUNCATE ig_db.paymentmethod;
TRUNCATE ig_db.paymenttype;
TRUNCATE ig_db.permission;
TRUNCATE ig_db.personresponsible;
TRUNCATE ig_db.ppsdrolestage;
TRUNCATE ig_db.proceduredefinition;
TRUNCATE ig_db.procedurerelateddocs;
TRUNCATE ig_db.procedurestatus;
TRUNCATE ig_db.professionalinstitute;
TRUNCATE ig_db.projbasedremuntype;
TRUNCATE ig_db.project;
TRUNCATE ig_db.projectexpense;
TRUNCATE ig_db.projectexpensefile;
TRUNCATE ig_db.projectparticipant;
TRUNCATE ig_db.projectparticipantsdrole;
TRUNCATE ig_db.projectsd;
TRUNCATE ig_db.projectsdstage;
TRUNCATE ig_db.projectstage;
TRUNCATE ig_db.projectstagetype;
TRUNCATE ig_db.projectstatustype;
TRUNCATE ig_db.projectteam;
TRUNCATE ig_db.province;
TRUNCATE ig_db.recoverableexpense;
TRUNCATE ig_db.relateddocs;
TRUNCATE ig_db.remunerationinterval;
TRUNCATE ig_db.remunerationmodel;
TRUNCATE ig_db.remunerationrateupline;
TRUNCATE ig_db.report;
TRUNCATE ig_db.reportparameter;
TRUNCATE ig_db.resourceremuneration;
TRUNCATE ig_db.resourceremuntype;
TRUNCATE ig_db.resourcetype;
TRUNCATE ig_db.role;
TRUNCATE ig_db.roleonaproject;
TRUNCATE ig_db.rolepermission;
TRUNCATE ig_db.sdfolderstructure;
TRUNCATE ig_db.sdrole;
TRUNCATE ig_db.servicediscipline;
TRUNCATE ig_db.soqtemplate;
TRUNCATE ig_db.soqtemplateitem;
TRUNCATE ig_db.soqtemplateprint;
TRUNCATE ig_db.soqtemplsubschedule;
TRUNCATE ig_db.soqunit;
TRUNCATE ig_db.studyinstitute;
TRUNCATE ig_db.suburb;
TRUNCATE ig_db.systemmember;
TRUNCATE ig_db.tapsubscription;
TRUNCATE ig_db.task;
TRUNCATE ig_db.taskimportancetype;
TRUNCATE ig_db.taskrevision;
TRUNCATE ig_db.tasksubmission;
TRUNCATE ig_db.tasktype;
TRUNCATE ig_db.taxdeductablecategory;
TRUNCATE ig_db.timesheet;
TRUNCATE ig_db.topdescriptionsused;
TRUNCATE ig_db.triplogger;
TRUNCATE ig_db.typicalfolderstructure;
TRUNCATE ig_db.uicomponent;
TRUNCATE ig_db.unittype;
TRUNCATE ig_db.vehicle;
TRUNCATE ig_db.vehicleitem;
TRUNCATE ig_db.vehiclemaintenance;
TRUNCATE ig_db.vehiclemake;
TRUNCATE ig_db.vehiclemodel;
TRUNCATE ig_db.vehiclereading;
TRUNCATE ig_db.vehicletyreandrimtype;
TRUNCATE ig_db.vehicleuser;
TRUNCATE ig_db.workflowdefinition;
TRUNCATE ig_db.workflowdefinitionstep;
TRUNCATE ig_db.workflowprocess;
TRUNCATE ig_db.workflowprocesslog;
SET foreign_key_checks = 1;
*/ 
