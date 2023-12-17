package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AssetStatus;
import net.integrategroup.ignite.persistence.repository.AssetStatusRepository;

@Service
public class AssetStatusServiceImpl implements AssetStatusService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	AssetStatusRepository assetStatusRepository;

	@Override
	public AssetStatus findByAssetStatusId(Long assetStatusId) {
		return assetStatusRepository.findByAssetStatusId(assetStatusId);
	}

	@Override
	public AssetStatus save(AssetStatus assetStatus) {
		return assetStatusRepository.save(assetStatus);
	}

	@Override
	public List<AssetStatus> findAll() {
		return assetStatusRepository.findAll();
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
