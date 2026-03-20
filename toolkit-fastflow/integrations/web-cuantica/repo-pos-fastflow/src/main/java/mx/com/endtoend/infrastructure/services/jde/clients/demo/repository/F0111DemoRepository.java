package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0111;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0111Id;

@Repository
public interface F0111DemoRepository extends JpaRepository<F0111, F0111Id> {

	@Query("FROM F0111 WHERE noClient = :noClient")
	F0111 findFirstByIdNoClient(@Param("noClient") Long noClient);
}
