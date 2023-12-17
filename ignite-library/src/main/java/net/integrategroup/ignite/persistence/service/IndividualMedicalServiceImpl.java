package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.MainMemberMedicalInsurance;
import net.integrategroup.ignite.persistence.model.MedicalDependant;
import net.integrategroup.ignite.persistence.repository.MainMemberMedicalInsuranceRepository;
import net.integrategroup.ignite.persistence.repository.MedicalDependantRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class IndividualMedicalServiceImpl implements IndividualMedicalService {

	@Autowired
	MainMemberMedicalInsuranceRepository repository;

	@Autowired
	MedicalDependantRepository medicalDependantRepository;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public MainMemberMedicalInsurance getMainMemberMedical(Long individualId) {
		return repository.findByIndividualId(individualId);
	}

	@Override
	public List<MedicalDependant> getDependants(Long individualId) {
		return medicalDependantRepository.getDependants(individualId);
	}

	@Override
	public void saveMainMemberMedicalInsurance(Long individualId, Long medicalInsuranceCompanyId,
													Long medicalInsurancePlanId, String insuranceNumber,
													String description) throws Exception {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		try {


			String sql = "CALL ig_db.saveMainMemberMedicalInsurance(?, ?, ?, ?, ?, ?);";
	
			System.out.println ("CALL ig_db.saveMainMemberMedicalInsurance(?, ?, ?, ?, ?, ?);");
	
			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setLong(1, individualId);
				cstm.setLong(2, medicalInsuranceCompanyId);
				cstm.setLong(3, medicalInsurancePlanId);
				cstm.setString(4, insuranceNumber);
				cstm.setString(5, description);
				cstm.setString(6, securityUtils.getUsername());
	
				cstm.execute();
	//**//
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {							
			try {							
				if (cstm != null) {			
					cstm.close();			
				}							
											
				if (conn != null) {			
					conn.close();			
				}							
			} catch (SQLException e) {		
				e.printStackTrace();		
			}								
		}									
	}											
	//**//

	@Override
	public void deleteDependant(Long medicalDependantId) throws SQLException {
		String sql = "CALL ig_db.deleteMedicalDependant(?);";

		System.out.println ("CALL ig_db.deleteMedicalDependant(" + medicalDependantId +");");

		try {	//**//					
			Object[] params = {		
					medicalDependantId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			//**//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveDependant(Long medicalDependantId, Long mainMemberMedicalInsuranceId, Long individualIdDependant,
			String description) throws SQLException {
	

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		try {
		String sql = "CALL ig_db.saveMedicalDependant(?, ?, ?, ?, ?);";
	
			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
			
				cstm.setLong(1, medicalDependantId == null ? -1 : medicalDependantId);
				cstm.setLong(2, mainMemberMedicalInsuranceId);
				cstm.setLong(3, individualIdDependant);
				cstm.setString(4, description);
				cstm.setString(5, securityUtils.getUsername());
	
				cstm.execute();
	//**//
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {							
			try {							
				if (cstm != null) {			
					cstm.close();			
				}							
											
				if (conn != null) {			
					conn.close();			
				}							
			} catch (SQLException e) {		
				e.printStackTrace();		
			}								
		}									
	}											
	//**//

}
