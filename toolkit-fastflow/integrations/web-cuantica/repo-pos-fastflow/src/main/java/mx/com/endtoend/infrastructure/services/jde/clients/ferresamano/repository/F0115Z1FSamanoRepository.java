package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F0115Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F0115Z1Id;

@Repository
public interface F0115Z1FSamanoRepository extends JpaRepository<F0115Z1, F0115Z1Id> {

	@Query("FROM F0115Z1 f WHERE TRIM(f.noClient) = :noClient AND TRIM(f.cellHouse) = :cellHouse")
	F0115Z1 cellHouse(@Param("noClient") String noClient, @Param("cellHouse") String cellHouse);

//	List<F0115Z1> findByNoClientAndPiedspNot(String noClient, String estatus);

	List<F0115Z1> findByNoClient(String noClient);

}