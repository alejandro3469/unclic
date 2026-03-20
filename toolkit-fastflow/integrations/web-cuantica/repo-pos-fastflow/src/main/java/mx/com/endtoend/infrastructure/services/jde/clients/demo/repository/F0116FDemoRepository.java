package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0116;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0116Id;

@Repository
public interface F0116FDemoRepository extends JpaRepository<F0116, F0116Id> {
	
	F0116 findByIdNoClient(Long noClient);
	
	@Query("FROM F0116 WHERE id.noClient = :an8")
	F0116 findByAn8(@Param("an8") Long an8);
}
