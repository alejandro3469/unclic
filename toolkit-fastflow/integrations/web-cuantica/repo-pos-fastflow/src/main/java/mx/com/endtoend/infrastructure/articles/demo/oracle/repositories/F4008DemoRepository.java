package mx.com.endtoend.infrastructure.articles.demo.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.demo.oracle.entities.F4008;
import mx.com.endtoend.infrastructure.articles.demo.oracle.entities.F4008Id;

@Repository
public interface F4008DemoRepository extends JpaRepository<F4008, F4008Id> {

	@Query("FROM F4008 f WHERE f.id.taitm =:itm AND f.id.taefdj >=:date AND f.taeftj <=:date")
	F4008 getTaxesByItm(@Param("itm") BigDecimal itm, @Param("date") BigDecimal  date);
	
	@Query("FROM F4008 f WHERE TRIM(f.id.tatxa1)=:taxCode AND f.id.taefdj >=:date AND f.taeftj <=:date AND rownum = 1")
	F4008 getTaxDefaultByTaxCode(@Param("taxCode") String taxCode, @Param("date") BigDecimal  date);

}
