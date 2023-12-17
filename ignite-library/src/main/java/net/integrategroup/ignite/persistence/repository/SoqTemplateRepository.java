package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.SoqTemplate;
import net.integrategroup.ignite.persistence.model.VParticipant;

@Repository
public interface SoqTemplateRepository extends CrudRepository<SoqTemplate, Long> {
	
	@Query("SELECT st FROM SoqTemplate st")
	List<SoqTemplate> findAll();
	
	@Query("SELECT st FROM SoqTemplate st WHERE st.soqTemplateId = ?1")
	SoqTemplate findByTemplateId(Long id);
}
