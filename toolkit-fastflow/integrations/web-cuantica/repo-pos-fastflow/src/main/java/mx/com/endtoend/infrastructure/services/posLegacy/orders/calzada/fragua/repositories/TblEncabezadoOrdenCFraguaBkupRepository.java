package mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.fragua.repositories;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenBkup;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenBkupId;

@Repository
public interface TblEncabezadoOrdenCFraguaBkupRepository extends JpaRepository<TblEncabezadoOrdenBkup, TblEncabezadoOrdenBkupId>{
	
	@Transactional
	@Modifying
	@Query("DELETE FROM TblEncabezadoOrdenBkup eob "
			+ " WHERE "
			+ "eob.id.doco =:DOCO "
			+ "AND "
			+ "eob.id.dcto =:DCTO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto);

}
