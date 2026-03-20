package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.EncabezadoSolTraspaso;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.EncabezadoSolTraspasoId;

@Repository
public interface EncabezadoSolTraspasoRepository extends JpaRepository<EncabezadoSolTraspaso, EncabezadoSolTraspasoId>{

	@Transactional
	@Modifying
	@Query("UPDATE EncabezadoSolTraspaso est SET est.estatus =:status"
			+ " WHERE "
			+ "est.id.doco =:DOCO "
			+ "AND "
			+ "est.id.dcto =:DCTO "
			+ "AND "
			+ "est.id.kcooo =:KOOOO  "
			+ "AND "
			+ "est.id.mcu =:MCU " )
	int cancelOrderByDocoAndDctoAndKoooo(@Param("status") String status, @Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo, @Param("MCU") String mcu);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM EncabezadoSolTraspaso est "
			+ " WHERE "
			+ "est.id.doco =:DOCO "
			+ "AND "
			+ "est.id.dcto =:DCTO "
			+ "AND "
			+ "est.id.kcooo =:KOOOO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo);
}
