package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Timesheet;
import net.integrategroup.ignite.persistence.model.TimesheetSummaryDto;
import net.integrategroup.ignite.persistence.model.VTimesheet;

@Repository
public interface TimesheetRepository extends CrudRepository<Timesheet, Long> {

	@Override
	List<Timesheet> findAll();

	@Query("SELECT t FROM Timesheet t WHERE t.timesheetId = ?1")
	Timesheet findByTimesheetId(Long timesheetId);

//	@Query(value = "select any_value(c.id), c.number, any_value(c.type) from Call c group by c.number"
//       , nativeQuery = true)
//	public List<Object[]> getCallsByType(Pageable pageable);


	// NOTE: change the package path to the package where you saved TimesheetDailySummaryDto
	@Query("SELECT new net.integrategroup.ignite.persistence.model.TimesheetSummaryDto( "
			+ "	tv.participantIdThatBookedTime, "
			+ "	tv.participantNameHost, "
			+ "	tv.projectId, "
			+ "	tv.sdCode, "
			+ " tv.sdRoleId, "
			+ "	tv.projectNameText,"
			+ "	CONCAT(tv.sdCode , '-'  ,tv.sdName , '-', tv.roleName),"
			+ "	tv.remunerationTypeName, "
			+ "	tv.unitTypeName,"
//			+ "	any_value(tv.unitTypeName),"
			+ "	SUM(tv.numberOfUnits), "
//			+ "	any_value(tv.systemNameThatBookedTime))"
			+ "	tv.systemNameThatBookedTime)"
			+ " FROM VTimesheet tv "
			+ " WHERE tv.participantIdThatBookedTime = ?1"
			+ " AND tv.activityDate BETWEEN ?2 AND ?3 "
			+ " GROUP BY "
			+ " tv.participantIdThatBookedTime,"
			+ " tv.participantIdHost,"
			+ "	tv.projectId, "
			+ "	tv.sdCode, "
			+ " tv.sdRoleId, "
			+ " tv.projectNameText,"
			+ " tv.sdName,"
			+ " tv.roleName,"
//			+ " tv.remunerationTypeName")
			+ " tv.remunerationTypeName,"
			+ " tv.unitTypeName,"
			+ "	tv.systemNameThatBookedTime")

	List<TimesheetSummaryDto> getParticipantTimesheetSummary(Long participantId, Date firstDay, Date lastDay);
//	List<TimesheetSummaryDto> getParticipantTimesheetSummary(Long participantId, Date firstDay, Date lastDay, Pageable pageable);



	@Query("SELECT tv FROM VTimesheet tv "
			+ " WHERE tv.participantIdThatBookedTime = ?1"
			+ " AND tv.activityDate BETWEEN ?2 AND ?3")
	List<VTimesheet> getParticipantTimesheetList(Long participantId, Date firstDay, Date lastDay);

	@Query("SELECT tv FROM VTimesheet tv "
			+ " WHERE tv.projectId = ?1"
			+ " AND tv.activityDate BETWEEN ?2 AND ?3")
	List<VTimesheet> getProjectTimesheetList(Long projectId, Date firstDay, Date lastDay);

	// NOTE: change the package path to the package where you saved TimesheetDailySummaryDto
	@Query("SELECT new net.integrategroup.ignite.persistence.model.TimesheetSummaryDto( "
			+ "	tv.participantIdThatBookedTime, "
			+ "	tv.participantNameHost, "
			+ "	tv.projectId, "
			+ "	tv.sdCode, "
			+ "	tv.sdRoleId, "
			+ "	tv.projectNameText,"
			+ "	CONCAT(tv.sdName , ' ', tv.roleName),"
			+ "	tv.remunerationTypeName, "
			+ "	tv.unitTypeName,"
			+ "	SUM(tv.numberOfUnits), "
			+ "	tv.systemNameThatBookedTime) "
			+ " FROM VTimesheet tv "
			+ " WHERE tv.projectId = ?1"
			+ " AND tv.activityDate BETWEEN ?2 AND ?3 "
			+ " GROUP BY "
			+ " tv.participantIdHost,"
			+ " tv.systemNameThatBookedTime,"
			+ " tv.sdName,"
			+ " tv.roleName,"
			+ " tv.remunerationTypeName, "
			+ "	tv.projectId, "
			+ "	tv.sdCode, "
			+ "	tv.sdRoleId ")

	List<TimesheetSummaryDto> getProjectTimesheetSummary(Long projectId, Date firstDay, Date lastDay);

	@Query("SELECT tv FROM VTimesheet tv "
			+ " WHERE tv.participantIdThatBookedTime = ?1"
			+ " AND tv.sdId = ?2"
			+ " AND tv.projectId = ?3"
			+ " AND tv.activityDate BETWEEN ?4 AND ?5")
	List<VTimesheet> getPerProjectSdTimesheetList(Long participantId, Long sdId, Long projectId, Date firstDay, Date lastDay);

}
