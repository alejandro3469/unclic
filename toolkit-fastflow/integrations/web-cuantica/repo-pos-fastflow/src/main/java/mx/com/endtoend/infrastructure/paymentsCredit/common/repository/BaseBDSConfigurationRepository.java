package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.BDSServiceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoRepositoryBean
public interface BaseBDSConfigurationRepository extends JpaRepository<BDSServiceConfiguration, Long> {

}
