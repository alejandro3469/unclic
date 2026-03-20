package mx.com.endtoend.infrastructure.company.common.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Serializable>{

	boolean existsByCode(CompanyCodes code);
	
	boolean existsByName(String name);
		
	@Query("SELECT c FROM CompanyEntity c WHERE c.code=:companyCode")
	Optional<CompanyEntity> findByCompanyCode(@Param("companyCode") CompanyCodes companyCode);
	
	@Query("SELECT c FROM CompanyEntity c  WHERE c.id =:id")
	CompanyEntity findById(@Param("id") Long id);
	
	@Query("SELECT c FROM CompanyEntity c WHERE c.code=:companyCode AND c.id !=:id")
	Optional<CompanyEntity> findByCompanyCodeAndIdNot(@Param("companyCode") CompanyCodes companyCode, @Param("id") Long id);
	
	CompanyEntity findByCode(CompanyCodes code);
}
