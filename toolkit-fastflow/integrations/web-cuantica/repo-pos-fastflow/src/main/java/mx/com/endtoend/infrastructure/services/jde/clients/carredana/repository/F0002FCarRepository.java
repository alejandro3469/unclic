package mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0002;

@Repository
public interface F0002FCarRepository extends JpaRepository<F0002, Serializable> {

	@Query("FROM F0002 f WHERE TRIM(f.nnsy) = :nnsy")
	F0002 NoClient(@Param("nnsy") String nnsy);

}
