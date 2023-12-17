package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.integrategroup.ignite.persistence.model.Timesheet;
import net.integrategroup.ignite.persistence.model.TimesheetSummaryDto;
import net.integrategroup.ignite.persistence.model.VTimesheet;
import net.integrategroup.ignite.persistence.repository.TimesheetRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

// @author Ingrid Marais

@Service
public class TimesheetServiceImpl implements TimesheetService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TimesheetRepository timesheetRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<Timesheet> findAll() {
		return timesheetRepository.findAll();
	}

	@Override
	public Timesheet save(Timesheet timesheet) {
		return timesheetRepository.save(timesheet);
	}

	@Override
	public Timesheet findByTimesheetId(Long timesheetId) {
		return timesheetRepository.findByTimesheetId(timesheetId);
	}

	@Override
	public List<TimesheetSummaryDto> getParticipantTimesheetSummary(Long participantId, Date firstDay, Date lastDay) {
		return timesheetRepository.getParticipantTimesheetSummary(participantId, firstDay, lastDay);
	}

	@Override
	public List<VTimesheet> getParticipantTimesheetList(Long participantId, Date firstDay, Date lastDay) {
		return timesheetRepository.getParticipantTimesheetList(participantId, firstDay, lastDay);
	}

	@Override
	public List<VTimesheet> getProjectTimesheetList(Long projectId, Date firstDay, Date lastDay) {
		return timesheetRepository.getProjectTimesheetList(projectId, firstDay, lastDay);
	}

	@Override
	public List<TimesheetSummaryDto> getProjectTimesheetSummary(Long projectId, Date firstDay, Date lastDay) {
		return timesheetRepository.getProjectTimesheetSummary(projectId, firstDay, lastDay);
	}

	@Override
	public List<VTimesheet> getPerProjectSdTimesheetList(Long participantId, Long sdId, Long projectId, Date firstDay, Date lastDay) {
		return timesheetRepository.getPerProjectSdTimesheetList(participantId, sdId, projectId, firstDay, lastDay);
	}

	@Override
	@Transactional
	public Long saveTimesheetNew(String jsonString) throws SQLException {
		
		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long timesheetId = null; 
	
		try {
			String exampleSql = "CALL ig_db.saveNewTimesheet('"
					+ jsonString + "', '"
					+ securityUtils.getUsername()
					+ "' , timesheetId"
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging
		
			String sql = "CALL ig_db.saveNewTimesheet(?, ?, ?);";
	
			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
					
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
		
				cstm.execute();
				timesheetId = cstm.getLong(3);
			
				return  timesheetId;
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				// return ResponseEntity.badRequest().body(e.getMessage());
			}
			return  timesheetId;  ///???? Ingrid 
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
