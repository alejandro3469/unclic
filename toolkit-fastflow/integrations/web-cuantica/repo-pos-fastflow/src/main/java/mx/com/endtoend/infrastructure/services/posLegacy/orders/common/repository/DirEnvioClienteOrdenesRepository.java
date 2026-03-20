package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DirEnvioClienteOrdenes;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DirEnvioClienteOrdenesId;

@Repository
public interface DirEnvioClienteOrdenesRepository extends JpaRepository<DirEnvioClienteOrdenes, DirEnvioClienteOrdenesId>{
	
	@Transactional
	@Modifying
	@Query("DELETE FROM DirEnvioClienteOrdenes dec " 
			+ " WHERE " 
			+ "dec.id.doco =:DOCO " 
			+ "AND " 
			+ "dec.id.dcto =:DCTO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto);

}
