package mx.com.endtoend.infrastructure.company.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.company.common.entities.PermissionCompanyEntity;

@Repository
public interface PermissionCompanyRepository extends JpaRepository<PermissionCompanyEntity, Long> {
	
	@Query("SELECT pc FROM PermissionCompanyEntity pc WHERE pc.companyId=:companyId")
	List<PermissionCompanyEntity> findAllByCompanyId(@Param("companyId") Long companyId);

}
