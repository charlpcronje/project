package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import net.integrategroup.ignite.persistence.model.ProjectOperationalServiceDisciplineView;
import net.integrategroup.ignite.persistence.model.ServiceDiscipline;
import net.integrategroup.ignite.persistence.model.VServiceDiscipline;

@Repository
public interface ServiceDisciplineRepository extends CrudRepository<ServiceDiscipline, Long> {

	@Query("SELECT s FROM VServiceDiscipline s order by s.serviceDisciplineCode")
	List<VServiceDiscipline> findAllSss();

	@Query("SELECT s FROM VServiceDiscipline s WHERE s.serviceDisciplineIdParent IS NULL order by s.rowOrderNo")
	List<VServiceDiscipline> findLevel0List();

	@Query("SELECT s FROM VServiceDiscipline s WHERE s.serviceDisciplineIdParent = ?1  order by s.rowOrderNo")
	List<VServiceDiscipline> findChildren(Long serviceDisciplineId);

	@Query("    SELECT vsd FROM VServiceDiscipline vsd "
			+ " WHERE vsd.serviceDisciplineIdParent IS NULL "
			+ " OR  vsd.rowOrderNo <= (select MAX(vsd2.rowOrderNo) from VServiceDiscipline vsd2 where vsd2.serviceDisciplineIdParent = ?1)")
	List<VServiceDiscipline> expandChildren(String serviceDisciplineArray);


	@Query("SELECT s FROM ServiceDiscipline s WHERE s.serviceDisciplineId = ?1")
	ServiceDiscipline findByServiceDisciplineId(Long serviceDisciplineId);

	@Query("SELECT s FROM VServiceDiscipline s WHERE s.serviceDisciplineId = ?1")
	VServiceDiscipline findByVServiceDisciplineId(Long serviceDisciplineId);

}
