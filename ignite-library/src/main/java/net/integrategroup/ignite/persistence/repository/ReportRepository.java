package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {

	@Override
	List<Report> findAll();

	@Query("SELECT r FROM Report r WHERE r.activeInd = 'Y' AND r.fileExistsInd = 'Y'")
	List<Report> findActiveAndValid();

	@Query("SELECT r FROM Report r WHERE r.reportId = ?1")
	Report findByReportId(Long reportId);

	@Query("SELECT r FROM Report r WHERE r.reportName = ?1")
	Report findReportByName(String reportName);

}
