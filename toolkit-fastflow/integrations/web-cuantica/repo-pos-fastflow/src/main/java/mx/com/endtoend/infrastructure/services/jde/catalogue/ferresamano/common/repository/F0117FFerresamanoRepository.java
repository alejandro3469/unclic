package mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0117;
import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0117Id;

@Repository
public interface F0117FFerresamanoRepository extends JpaRepository<F0117, F0117Id>, CustomDirectionFFerresamanoRepository {

	@Query(value = "select * from F0117 join F0118 on A8ADDZ = A7ADDZ where (trim(A8ADDZ)) = :cp ORDER BY A7ADD4", nativeQuery = true)
	List<F0117> findByCp(@Param("cp") String cp);

	@Query(value = "select * from CRPDTA.F0117 inner join CRPDTA.F0118 on A8ADDZ = A7ADDZ where (trim(A7ADD4)) = :colony and (trim(A8ADDZ)) = :cp", nativeQuery = true)
	List<F0117> findByColony(@Param("colony") String colony, @Param("cp") String cp);

	@Query(value = "select * from CRPDTA.F0117 inner join CRPDTA.F0118 on A8ADDZ = A7ADDZ where (trim(A8ADDS)) = :state", nativeQuery = true)
	List<F0117> findByState(@Param("state") String state);

}

