package mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F4106;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F4106Id;

@Repository
public interface F4106FCarRepository extends JpaRepository<F4106, F4106Id> {

	@Query("SELECT COUNT(f6.id.bpuom) FROM F4106 f6 WHERE f6.id.bpitm = :itm AND TRIM(f6.id.bpmcu) = TRIM(:mcu) AND f6.bpeftj <= :date AND f6.id.bpexdj >= :date")
	Integer findUnitMeasurementByBpitmAndBPmcuAndJulianDate(@Param("itm") BigDecimal itm, @Param("mcu") String mcu,
			@Param("date") BigDecimal date);
}
