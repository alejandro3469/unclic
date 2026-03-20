package mx.com.endtoend.infrastructure.catalogue.client.common.repository;

import mx.com.endtoend.infrastructure.catalogue.client.common.entities.WorkTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoRepositoryBean
public interface BaseWorkTypeRepository extends JpaRepository<WorkTypeEntity, Long> {

}
