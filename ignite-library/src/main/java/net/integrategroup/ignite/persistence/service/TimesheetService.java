package net.integrategroup.ignite.persistence.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.Timesheet;
import net.integrategroup.ignite.persistence.model.TimesheetSummaryDto;
import net.integrategroup.ignite.persistence.model.VTimesheet;

public interface TimesheetService {

	List<Timesheet> findAll();

	Timesheet save(Timesheet timesheet);

	List<VTimesheet> getParticipantTimesheetList(Long participantId, Date firstDay, Date lastDay);

	List<TimesheetSummaryDto> getParticipantTimesheetSummary(Long participantId, Date firstDay, Date lastDay);

	Timesheet findByTimesheetId(Long timesheetId);

	List<VTimesheet> getProjectTimesheetList(Long projectId, Date firstDay, Date lastDay);

	List<TimesheetSummaryDto> getProjectTimesheetSummary(Long projectId, Date firstDay, Date lastDay);

	List<VTimesheet> getPerProjectSdTimesheetList(Long participantId, Long sdId, Long projectId,  Date firstDay, Date lastDay);

	Long saveTimesheetNew(String jsonString) throws SQLException;

}
