package mx.com.endtoend.infrastructure.catalogue.client.common.repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.RegimeFiscalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoRepositoryBean
public interface BaseRegimeFiscalRepository extends JpaRepository<RegimeFiscalEntity, Long> {

}
