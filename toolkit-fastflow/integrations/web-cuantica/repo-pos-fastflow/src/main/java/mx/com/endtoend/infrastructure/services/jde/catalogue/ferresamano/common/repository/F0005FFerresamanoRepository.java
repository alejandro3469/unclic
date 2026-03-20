package mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0005;
import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0005Id;

@Repository
public interface F0005FFerresamanoRepository extends JpaRepository<F0005, F0005Id> {

	// CONSULTA PARA VALIDACION DE CONEXION A BD
	@Query("FROM F0005 WHERE ROWNUM = 1")
	F0005 validConnection();

	// CONSULTA GENERICA PARA TODOS LOS CATALOGOS CON USO DE PARAMETROS
	@Query("FROM F0005 f WHERE TRIM(f.id.drsy) = :drsy AND TRIM(f.id.drrt) = :drrt ORDER BY f.drdl01 ASC")
	List<F0005> findByDrsyAndDrrt(@Param("drsy") String drsy, @Param("drrt") String drrt);

	@Query("FROM F0005 f WHERE TRIM(f.id.drsy) = :drsy AND TRIM(f.id.drrt) = :drrt AND f.drdl01 LIKE CONCAT('%',:drl01,'%') ORDER BY f.drdl01 ASC")
	List<F0005> findByDrsyAndDrrtAndDrdl01(@Param("drsy") String drsy, @Param("drrt") String drrt,
			@Param("drl01") String drl01);

	@Query("FROM F0005 f WHERE TRIM(f.id.drsy) =:paramOne AND TRIM(f.id.drrt) =:paramTwo AND TRIM(f.id.drky) =:drky")
	F0005 findDescriptionByDrsyAndDrrtAndDrky(@Param("paramOne") String drsy, @Param("paramTwo") String drrt,
			@Param("drky") String drky);

	@Query("FROM F0005 f WHERE TRIM(f.id.drsy) = :drsy AND TRIM(f.id.drrt) = :drrt  AND TRIM(f.drsphd) = :drsphd  ORDER BY f.drdl01 ASC")
	List<F0005> findByDrsyAndDrrtAndDrsphd(@Param("drsy") String drsy, @Param("drrt") String drrt,
			@Param("drsphd") String drsphd);

	@Query("FROM F0005 f WHERE TRIM(f.id.drsy) = :drsy AND TRIM(f.id.drrt) = :drrt  AND TRIM(f.drsphd) = :drsphd AND TRIM(f.drdl02) = :drdl02 ORDER BY f.drdl01 ASC")
	F0005 findByDrsyAndDrrtAndDrsphdDrdl02(@Param("drsy") String drsy, @Param("drrt") String drrt,
			@Param("drsphd") String drsphd, @Param("drdl02") String drdl02);

//	@Query("FROM F0005 f WHERE TRIM(f.id.drsy) = :drsy AND TRIM(f.id.drrt) = :drrt AND TRIM(f.drsphd = '1')")
//	List<F0005> findByDrsyAndDrrtAndDrshpd(@Param("drsy") String drsy, @Param("drrt") String drrt);
}

