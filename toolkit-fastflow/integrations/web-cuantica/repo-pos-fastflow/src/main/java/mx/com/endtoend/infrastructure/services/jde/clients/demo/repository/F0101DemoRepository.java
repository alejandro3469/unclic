package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0101;

@Repository
public interface F0101DemoRepository extends JpaRepository<F0101, Serializable>, CustomF0101DemoRepository {

	@Query("SELECT f FROM F0101 f WHERE f.noClient=:an8")
	Optional<F0101> getByAn8(@Param("an8") Long an8);

	@Query("SELECT f FROM F0101 f WHERE f.noClient=:an8")
	List<F0101> getClientListByAn8(@Param("an8") Long an8);
}
