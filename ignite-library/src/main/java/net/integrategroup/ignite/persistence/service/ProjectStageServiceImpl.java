package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectStage;
import net.integrategroup.ignite.persistence.repository.ProjectStageRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class ProjectStageServiceImpl implements ProjectStageService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	ProjectStageRepository projectStageRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public ProjectStage findByProjectStageId(Long projectStageId) {
		return projectStageRepository.findByProjectStageId(projectStageId);
	}

	@Override
	public ProjectStage save(ProjectStage projectStage) {
		return projectStageRepository.save(projectStage);
	}

	@Override
	public List<ProjectStage> findAll() {
		// TODO Auto-generated method stub
		return (List<ProjectStage>) projectStageRepository.findAll();
	}

	/*
	@Override
	public void delete(ProjectStage projectStage) {
		projectStageRepository.delete(projectStage);
	}
	*/

	@Override
	public ProjectStage save(Long projectStageId, Long projectId, Long projectStageTypeId, String description,
			Date startDate, Date endDate) throws SQLException {

		Connection con = ((SessionImpl) entityManager.getDelegate()).connection();
		CallableStatement stmt = con.prepareCall("{call ig_db.saveProjectStage(?,?,?,?,?,?,?)}");

		// Check for null values
		if (projectStageId == null) {
			stmt.setNull(1, Types.BIGINT);
		} else {
			stmt.setLong(1, projectStageId);
		}

		stmt.setLong(2, projectId);
		stmt.setLong(3, projectStageTypeId);

		if (description == null) {
			stmt.setNull(4, Types.VARCHAR);
		} else {
			stmt.setString(4, description);
		}
		stmt.setDate(5, new java.sql.Date(startDate.getTime()));

		if (endDate == null) {
			stmt.setNull(6, Types.DATE);
		} else {
			stmt.setDate(6, new java.sql.Date(endDate.getTime()));
		}

		stmt.setString(7, securityUtils.getUsername());
		stmt.execute();

		return projectStageRepository.findByProjectStageId(projectStageId);
	}

}
