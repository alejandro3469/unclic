package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F01151Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F01151Z1Id;

@Repository
public interface F01151Z1Repository extends JpaRepository<F01151Z1, F01151Z1Id>{
	
//	F01151Z1 findByNoClient(String noClient);
	
//	@Query("FROM F01151Z1 f WHERE TRIM(f.noClient) = :noClient ")
//	F01151Z1 findByNoClient(@Param("noClient") String noClient);
	
	List<F01151Z1> findByNoClient(String noClient);
	


	

}
