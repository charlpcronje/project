package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AssetCondition;
import net.integrategroup.ignite.persistence.repository.AssetConditionRepository;

@Service
public class AssetConditionServiceImpl implements AssetConditionService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	AssetConditionRepository assetConditionRepository;

	@Override
	public AssetCondition findByAssetConditionId(Long assetConditionId) {
		return assetConditionRepository.findByAssetConditionId(assetConditionId);
	}

	@Override
	public AssetCondition save(AssetCondition assetCondition) {
		return assetConditionRepository.save(assetCondition);
	}

	@Override
	public List<AssetCondition> findAll() {
		return assetConditionRepository.findAll();
	}
	//@Override
	//public void delete(Long participantOfficeId) {
	//	ParticipantOffice participantOffice = participantOfficeRepository.findByParticipantOfficeId(participantOfficeId);

	//	participantOfficeRepository.delete(participantOffice);
	//}

//	@Override
//	public void delete(Long participantOfficeId) {
//		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("ig_db.deleteParticipantOffice");
//		storedProcedure.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN);     // participantOfficeId
//
//		storedProcedure.setParameter(0, participantOfficeId);
//
//		storedProcedure.execute();
//	}

}
