package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0101;

@Repository
public interface F0101Repository extends JpaRepository<F0101, Serializable>, CustomF0101Repository{

	List<F0101> getClient(HashMap<String,Object> conditions);
	
	@Query("SELECT f FROM F0101 f WHERE f.noClient=:an8")
	Optional<F0101> getByAn8(@Param("an8") Long an8);

	@Query("SELECT f FROM F0101 f WHERE f.noClient=:an8")
	List<F0101> getClientListByAn8(@Param("an8") Long an8);
}
