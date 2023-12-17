package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AssetType;
import net.integrategroup.ignite.persistence.repository.AssetTypeRepository;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	AssetTypeRepository assetTypeRepository;

	@Override
	public AssetType findByAssetTypeId(Long assetTypeId) {
		return assetTypeRepository.findByAssetTypeId(assetTypeId);
	}

	@Override
	public AssetType save(AssetType assetType) {
		return assetTypeRepository.save(assetType);
	}

	@Override
	public List<AssetType> findAll() {
		return assetTypeRepository.findAll();
	}

	@Override
	public List<AssetType> findAllOther() {
		return assetTypeRepository.findAllOther();
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
