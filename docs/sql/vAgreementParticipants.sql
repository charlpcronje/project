SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
     ALGORITHM = UNDEFINED 
     DEFINER = `root`@`localhost` 
     SQL SECURITY DEFINER
 VIEW vAgreementParticipants AS
WITH RECURSIVE agreeparts 

			(	RowNumber, 
                ProjectId, 
                ProjectName, 
				AgreementBetweenParticipantsId,
                Level, 
				RemunerationModelCode,
                
                ProjectParticipantIdContracting, 
                ParticipantIdContracting, 
                ParticipantNameContracting,
                IndividualIdContracting,
                IndividualNameContracting, 

                ProjectParticipantIdContracted, 
                ParticipantIdContracted, 
                ParticipantNameContracted,
                IndividualIdContracted,
                IndividualNameContracted, 

				AgreementDescription,
				AnyChildren, 
                IsIndividual
			)
AS ( 

SELECT (
		a.AgreementBetweenParticipantsId * pptree.ProjectParticipantIdContracted * pptree.ParticipantIdContracted * 
					(pptree.ProjectId + pptree.ParticipantIdContracted)) as RowNumber,
        pptree.ProjectId as ProjectId, 
        pptree.ProjectName as ProjectName, 
		a.AgreementBetweenParticipantsId as AgreementBetweenParticipantsId,
        pptree.Level, 
        a.RemunerationModelCode as RemunerationModelCode,

		pptree.ProjectParticipantIdContracting as ProjectParticipantIdContracting, 
		pptree.ParticipantIdContracting as ParticipantIdContracting, 
        pptree.ParticipantNameContracting as ParticipantNameContracting, 
        pptree.IndividualIdContracting as IndividualIdContracting,
        pptree.IndividualNameContracting as IndividualNameContracting,

		pptree.ProjectParticipantIdContracted as ProjectParticipantIdContracted, 
		pptree.ParticipantIdContracted as ParticipantIdContracted, 
        pptree.ParticipantNameContracted as ParticipantNameContracted, 
        pptree.IndividualIdContracted as IndividualIdContracted,
        pptree.IndividualNameContracted as IndividualNameContracted,

        a.Description AS AgreementDescription,
		pptree.AnyChildren, 
        pptree.IsIndividual 
     FROM   
		ig_db.vPPTree AS pptree,
        AgreementBetweenParticipants AS a
     WHERE 
        pptree.ProjectParticipantIdContracted = a.ProjectParticipantId
     UNION ALL
     SELECT 

        (yd.RowNumber + yd.ParticipantIdContracted + pptree2.ParticipantIdContracted) as RowNumber,
        pptree2.ProjectId, 
        pptree2.ProjectName, 
		yd.AgreementBetweenParticipantsId,
        pptree2.Level, 
        yd.RemunerationModelCode,

		pptree2.ProjectParticipantIdContracting as ProjectParticipantIdContracting, 
		pptree2.ParticipantIdContracting as ParticipantIdContracting, 
        pptree2.ParticipantNameContracting as ParticipantNameContracting, 
        pptree2.IndividualIdContracting as IndividualIdContracting,
        pptree2.IndividualNameContracting as IndividualNameContracting,

		pptree2.ProjectParticipantIdContracted as ProjectParticipantIdContracted, 
		pptree2.ParticipantIdContracted as ParticipantIdContracted, 
        pptree2.ParticipantNameContracted as ParticipantNameContracted, 
        pptree2.IndividualIdContracted as IndividualIdContracted,
        pptree2.IndividualNameContracted as IndividualNameContracted,

        yd.AgreementDescription AS AgreementDescription,
		pptree2.AnyChildren, 
        pptree2.IsIndividual
	 FROM
		agreeparts AS yd,
		vPPTree AS pptree2
     WHERE
-- 		pptree2.ProjectParticipantIdAboveMe = yd.ProjectParticipantId
   		pptree2.ProjectParticipantIdContracting = yd.ProjectParticipantIdContracted
     
	)

SELECT 
	*
FROM agreeparts;

