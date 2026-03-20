package mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.fragua.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrden;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenId;

@Repository
public interface TblEncabezadoOrdenCFraguaRepository extends JpaRepository<TblEncabezadoOrden, TblEncabezadoOrdenId> {

	@Transactional
	@Modifying
	@Query("UPDATE TblEncabezadoOrden eo SET eo.retenido =:retention "
			+ "WHERE "
			+ "eo.id.doco =:DOCO "
			+ "AND "
			+ "eo.id.dcto =:DCTO "
			+ "AND "
			+ "eo.id.kcooo =:KOOOO ")
	int approveOrderByDocoAndDctoAndKoooo(@Param("retention") String retention, @Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE TblEncabezadoOrden eo SET eo.estatus =:status"
			+ " WHERE "
			+ "eo.id.doco =:DOCO "
			+ "AND "
			+ "eo.id.dcto =:DCTO "
			+ "AND "
			+ "eo.id.kcooo =:KOOOO ")
	int cancelOrderByDocoAndDctoAndKoooo(@Param("status") int status, @Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo);
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE TblEncabezadoOrden eo SET eo.bloqueado =:bloqueado"
			+ " WHERE "
			+ "eo.id.doco =:DOCO "
			+ "AND "
			+ "eo.id.dcto =:DCTO "
			+ "AND "
			+ "eo.id.kcooo =:KOOOO ")
	int updateStatusOrderActiveByDocoAndDctoAndKoooo(@Param("bloqueado") boolean bloqueado, @Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo);
	
	@Transactional
	@Query("SELECT eo FROM TblEncabezadoOrden eo "			
			+ " WHERE "
			+ "eo.id.doco =:DOCO "
			+ "AND "
			+ "eo.id.dcto =:DCTO "
			+ "AND "
			+ "eo.id.kcooo =:KOOOO ")
	Optional<TblEncabezadoOrden> findOrderByDocoAndDctoAndKoooo(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto,
			@Param("KOOOO") String koooo);
	
	
	@Transactional
	@Modifying
	@Query("DELETE FROM TblEncabezadoOrden eo "
			+ " WHERE "
			+ "eo.id.doco =:DOCO "
			+ "AND "
			+ "eo.id.dcto =:DCTO ")
	void deleteToUpdate(@Param("DOCO") BigDecimal doco, @Param("DCTO") String dcto);
	
	//CONSULTA PARA VALIDACION DE CONEXION A BD
	@Query(value = "SELECT COUNT(*) FROM TblEncabezadoOrden", nativeQuery = true)
	Long validConnection();
}
