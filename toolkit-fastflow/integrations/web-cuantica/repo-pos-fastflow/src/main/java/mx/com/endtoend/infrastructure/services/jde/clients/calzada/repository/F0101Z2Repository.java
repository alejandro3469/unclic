package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0101Z2;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0101Z2Id;

@Repository
public interface F0101Z2Repository extends JpaRepository<F0101Z2, F0101Z2Id> {
	public List<F0101Z2> getClient(HashMap<String,Object> conditions);
	Optional<F0101Z2> findByNoClient(String noClient);
	@Query("SELECT f FROM F0101Z2 f WHERE f.noClient =:noClient")
	List<F0101Z2> findRecordByNoClient(@Param("noClient") String noClient);

}
