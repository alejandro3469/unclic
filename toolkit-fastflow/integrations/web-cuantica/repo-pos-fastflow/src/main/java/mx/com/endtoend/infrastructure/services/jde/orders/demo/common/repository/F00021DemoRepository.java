package mx.com.endtoend.infrastructure.services.jde.orders.demo.common.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.orders.demo.common.entities.F00021;
import mx.com.endtoend.infrastructure.services.jde.orders.demo.common.entities.F00021Id;

@Repository
public interface F00021DemoRepository extends JpaRepository<F00021, F00021Id> {

	@Query("SELECT f FROM F00021 f WHERE TRIM(f.id.nlkco) = TRIM(:companyCode) AND TRIM(f.id.nldct) = TRIM(:orderType)")
	F00021 findOrderNumberByCompanyCodeAndOrderType(@Param("companyCode") String companyCode,
			@Param("orderType") String orderType);

	@Transactional
	@Modifying
	@Query("UPDATE F00021 f set f.nln001=:orderNumber WHERE f.id.nlkco=:companyCode AND f.id.nldct=:orderType")
	int updateOrderNumberByCompanyCodeAndOrderType(@Param("companyCode") String companyCode,
			@Param("orderType") String orderType, @Param("orderNumber") BigDecimal orderNumber);

}

