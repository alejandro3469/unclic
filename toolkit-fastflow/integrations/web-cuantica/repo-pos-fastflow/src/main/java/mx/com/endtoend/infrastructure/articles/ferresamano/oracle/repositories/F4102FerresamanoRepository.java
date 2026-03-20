package mx.com.endtoend.infrastructure.articles.ferresamano.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.ferresamano.oracle.entities.F4102;
import mx.com.endtoend.infrastructure.articles.ferresamano.oracle.entities.F4102Id;

@Repository
public interface F4102FerresamanoRepository extends JpaRepository<F4102, F4102Id> {

	@Query("FROM F4102 WHERE id.ibitm = :ibitm AND id.ibmcu = :ibmcu")
	F4102 existApplyTaxes(@Param("ibitm") BigDecimal ibitm, @Param("ibmcu") String ibmcu);

}

