package mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F42119;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F42119Id;

@Repository
public interface F42119FCarRepository extends JpaRepository<F42119, F42119Id> {

	@Query("SELECT f FROM F42119 f " 
			+ "WHERE " 
			+ "TRIM(f.id.sdkcoo)=:kcoo " 
			+ "AND f.id.sddoco=:doco "
			+ "AND f.id.sddcto=:dcto " 
			+ "AND f.sditm=:itm ")
	List<F42119> findByKcooAndDocoAndDctoAndItm(@Param("kcoo") String kcoo, @Param("doco") BigDecimal doco,
			@Param("dcto") String dcto, @Param("itm") Long itm);

}
