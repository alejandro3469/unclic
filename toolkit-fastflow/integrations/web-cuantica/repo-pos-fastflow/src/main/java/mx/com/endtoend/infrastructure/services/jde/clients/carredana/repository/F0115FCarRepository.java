package mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0115;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0115Id;

@Repository
public interface F0115FCarRepository extends JpaRepository<F0115, F0115Id>{
	
	F0115 findFirstByIdNoClientAndIdWprck7(Long noClient, String number);

	@Query("SELECT f FROM F0115 f WHERE f.id.noClient=:an8")
	List<F0115> findByAn8(@Param("an8") Long an8);

}