package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectExpenseFile;

@Repository
public interface ProjectExpenseFileRepository extends CrudRepository<ProjectExpenseFile, Long>{

	@Override
	List<ProjectExpenseFile> findAll();
	
	@Query("select p from ProjectExpenseFile p where p.projectExpenseId = ?1")
	List<ProjectExpenseFile> findListProjectExpenseFileForProjectExpenseId(Long projectExpenseId);

	@Query("select p from ProjectExpenseFile p where p.projectExpenseFileId = ?1")
	ProjectExpenseFile getByProjectExpenseFileId(Long projectExpenseFileId);

}
