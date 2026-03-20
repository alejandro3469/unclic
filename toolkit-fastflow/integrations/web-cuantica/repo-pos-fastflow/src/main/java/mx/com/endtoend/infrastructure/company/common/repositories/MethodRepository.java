package mx.com.endtoend.infrastructure.company.common.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.MethodEntity;


/**
 * 
 * @author ddcasas
 *
 */

@Repository
public interface MethodRepository extends JpaRepository<MethodEntity, Serializable>{

	@Query("SELECT DISTINCT new MethodEntity(m.id, m.methodCode, m.module) FROM MethodEntity m WHERE m.module =:module")
	List<MethodEntity> findAllByModule(@Param("module") String module);
	
	@Query("SELECT DISTINCT new MethodEntity(m.id, m.methodCode, m.module) FROM MethodEntity m INNER JOIN m.companies c WHERE c.code=:companyCode AND m.module =:module")
	Optional<MethodEntity> findByCompanyCodeAndModule(@Param("companyCode") CompanyCodes companyCode, @Param("module") String module);
	
}
