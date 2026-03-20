package mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F41002;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F41002Id;


@Repository
public interface F41002FCarRepository extends JpaRepository<F41002, F41002Id> {

	@Query("FROM F41002 f WHERE f.id.umitm = :itm AND TRIM(f.id.umum) = TRIM(:um) AND TRIM(f.id.umrum) = TRIM(:rum)")
	F41002 findByUmitmAndUmumAndUmrum(@Param("itm") BigDecimal itm, @Param("um") String um, @Param("rum") String rum);

	@Query("FROM F41002 f WHERE f.id.umitm = :itm AND f.umexso = :exso ")
	List<F41002> findByUmitm(@Param("itm") BigDecimal itm, @Param("exso") String exso);

	@Query("FROM F41002 f WHERE f.id.umitm =:itm AND f.id.umrum =:rum")
	F41002 findByUmitmAndUmrum(@Param("itm") BigDecimal itm, @Param("rum") String rum);

}
