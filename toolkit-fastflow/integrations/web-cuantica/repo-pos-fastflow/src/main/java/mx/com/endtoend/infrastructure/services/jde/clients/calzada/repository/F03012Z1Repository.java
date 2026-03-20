package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F03012Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F03012Z1Id;

@Repository
public interface F03012Z1Repository extends JpaRepository<F03012Z1, F03012Z1Id>{

//	@Query("FROM F03012Z1 f WHERE TRIM(f.noClient) = :noClient")
	Optional<F03012Z1> findByNoClient (String noClient);
	
//	Optional<F03012Z1> findByNoClientAndVoedspNot(String noClient, String estatus);
	
}
