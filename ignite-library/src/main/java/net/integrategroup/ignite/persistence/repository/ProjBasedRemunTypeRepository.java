package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjBasedRemunType;

@Repository
public interface ProjBasedRemunTypeRepository extends CrudRepository<ProjBasedRemunType, Long> {

	@Override
	List<ProjBasedRemunType> findAll();

	@Query("SELECT r FROM ProjBasedRemunType r WHERE r.projBasedRemunTypeId = ?1")
	public ProjBasedRemunType findByProjBasedRemunTypeId(Long projBasedRemunTypeId);

	@Query("SELECT r FROM ProjBasedRemunType r "
			+ " WHERE r.unitTypeCode IN ('PER_DAY', 'PER_HOUR')")
	public List<ProjBasedRemunType> getTimeBasedProjBasedRemunType();

}
