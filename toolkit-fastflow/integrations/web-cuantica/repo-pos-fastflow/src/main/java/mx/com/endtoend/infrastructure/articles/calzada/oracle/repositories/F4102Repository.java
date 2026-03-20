package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4102;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4102Id;

@Repository
public interface F4102Repository extends JpaRepository<F4102, F4102Id>, CustomArticleRepository{

	@Query("FROM F4102 f WHERE f.id.ibitm=:itm AND TRIM(f.id.ibmcu) = TRIM(:mcu)")
	F4102 existApplyTaxes(@Param("itm") BigDecimal itm,  @Param("mcu") String  mcu);
	
}
