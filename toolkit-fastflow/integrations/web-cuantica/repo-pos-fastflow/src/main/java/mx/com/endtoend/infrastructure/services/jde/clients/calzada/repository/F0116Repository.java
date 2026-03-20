package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0116;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0116Id;

@Repository
public interface F0116Repository extends JpaRepository<F0116, F0116Id> {
	
	F0116 findByIdNoClient(Long noClient);
	
	@Query("SELECT f FROM F0116 f WHERE f.id.noClient=:an8")
	Optional<F0116> findByAn8(@Param("an8") Long an8);

}
