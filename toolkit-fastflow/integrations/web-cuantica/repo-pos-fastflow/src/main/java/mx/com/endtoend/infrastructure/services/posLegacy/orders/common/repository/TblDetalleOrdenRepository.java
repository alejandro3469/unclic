package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrden;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenId;

@Repository
public interface TblDetalleOrdenRepository extends JpaRepository<TblDetalleOrden, TblDetalleOrdenId> {

	@Transactional
	@Modifying
	@Query("DELETE FROM TblDetalleOrden de " 
			+ " WHERE " 
			+ "de.id.doco =:DOCO " 
			+ "AND " 
			+ "de.id.dcto =:DCTO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto);

}
