CALL ig_db.saveNewIndividual('{"firstName":"Annie","lastName":"Appel","initials":"A","idNumber":"132456","passportNumber":"","countryCode":"","incomeTaxNumber":"","allowLoginFlag":"N",
"isActiveMember":"Y","pass":"$2a$10$P6wEBx52tuP.HyDGLNfDwusKORtD9FSh2vpOO44Cl.BOu4L9JMgAq","roleCode":"ROLE_USER","participantStatusCode":"NEW_MEMBER","participantTypeCode":"INDIVIDUAL",
"representativeIndividualId":null,"marketingIndividualId":null,"registeredName":"Annie Appel","tradingName":"A Appel","isIndividual":"Y","companyRegistrationNumber":null,"vatNumber":null,
"projectRefCode":null,"defaultInvoiceDay":null,"startDate":1656021600000,"endDate":null,"contactPointEmailAddress":"","contactPointPhysicalAddress":"","contactPointPostalAddress":"",
"contactPointCellphoneNumber":"","contactPointTelephoneNumber":"","systemName":"Annie Appel","contactPointName":"Annie Appel contact details"}', 'immarais@yahoo.com', 
 @individualId, @participantId, @systemMemberId, @contactPointId);
 
SELECT @individualId as individualId,  @participantId as participantId, @systemMemberId as systemMemberId, @contactPointId as contactPointId;
