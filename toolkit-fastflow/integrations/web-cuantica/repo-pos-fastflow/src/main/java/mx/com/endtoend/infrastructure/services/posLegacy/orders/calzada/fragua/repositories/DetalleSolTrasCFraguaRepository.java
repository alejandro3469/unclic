package mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.fragua.repositories;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DetalleSolTras;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DetalleSolTrasId;

@Repository
public interface DetalleSolTrasCFraguaRepository extends JpaRepository<DetalleSolTras, DetalleSolTrasId>{

	@Transactional
	@Modifying
	@Query("DELETE FROM DetalleSolTras dst "
			+ " WHERE "
			+ "dst.id.doco =:DOCO "
			+ "AND "
			+ "dst.id.dcto =:DCTO "
			+ "AND "
			+ "dst.id.kcooo =:KOOOO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo);
}
