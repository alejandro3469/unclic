package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0111Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0111Z1Id;

@Repository
public interface F0111Z1Repository extends JpaRepository<F0111Z1, F0111Z1Id>{

//	F0111Z1 findByNoClient(Long noClient);
	
//	Optional<F0111Z1> findByNoClientAndBwedspNot(Long noClient, String estatus);
	
	List<F0111Z1> findByNoClient(Long noClient);
}
