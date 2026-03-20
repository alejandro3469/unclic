package mx.com.endtoend.infrastructure.catalogue.client.common.repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.CFDIEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCFDIRepository extends JpaRepository<CFDIEntity, Long>{

}
