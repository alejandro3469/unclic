package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenBkup;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenBkupId;

@Repository
public interface TblDetalleOrdenBkupRepository extends JpaRepository<TblDetalleOrdenBkup, TblDetalleOrdenBkupId>{
	
	@Transactional
	@Modifying
	@Query("DELETE FROM TblDetalleOrdenBkup deb " 
			+ " WHERE " 
			+ "deb.id.doco =:DOCO " 
			+ "AND " 
			+ "deb.id.dcto =:DCTO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto);

}
