package mx.com.endtoend.infrastructure.company.common.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.EmailConfigurationEntity;

@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfigurationEntity, Long> {

	@Query("FROM EmailConfigurationEntity ec WHERE ec.isActiveConfiguration =:active AND ec.companyCode =:companyCode ")
	Optional<EmailConfigurationEntity> finActiveConfigurationByCompanyCode(@Param("active") boolean active,
			@Param("companyCode") CompanyCodes companyCode);

}
