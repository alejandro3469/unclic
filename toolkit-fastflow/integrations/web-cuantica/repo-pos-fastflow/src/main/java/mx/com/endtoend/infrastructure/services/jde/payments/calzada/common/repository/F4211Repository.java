package mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4211;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4211Id;

@Repository
public interface F4211Repository extends JpaRepository<F4211, F4211Id> {

	@Query("SELECT f FROM F4211 f "
			+ "WHERE " 
			+ "TRIM(f.id.sdkcoo)=:kcoo " 
			+ "AND f.id.sddoco=:doco "
			+ "AND f.id.sddcto=:dcto " 
			+ "AND f.sditm=:itm " 
			+ "AND f.sdnxtr=:nxtr " 
			+ "AND f.sdlttr=:lttr")
	List<F4211> findByNxtrAndLttrAndKcooAndDocoAndDctoAndItm(@Param("kcoo") String kcoo, @Param("doco") BigDecimal doco,
			@Param("dcto") String dcto, @Param("itm") Long itm, @Param("nxtr") String nxtr, @Param("lttr") String lttr);
}
