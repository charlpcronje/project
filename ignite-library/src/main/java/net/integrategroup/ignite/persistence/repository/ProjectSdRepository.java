package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectSd;
import net.integrategroup.ignite.persistence.model.VProjectSd;

@Repository
public interface ProjectSdRepository extends CrudRepository<ProjectSd, Long> {

	@Query("SELECT psd FROM ProjectSd psd WHERE psd.projectSdId = ?1")
	ProjectSd findByProjectSdId(Long projectSdId);

	@Query("	SELECT vpsd "
			+ "	FROM 	VProjectSd vpsd"
			+ "	WHERE 	vpsd.projectId = ?1")
	List<VProjectSd> findProjectOSDList(Long projectId);

}
