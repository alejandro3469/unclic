package mx.com.endtoend.infrastructure.closings.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationDetailEntity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoRepositoryBean
public interface BaseClosingOperationDetailRepository extends JpaRepository<ClosingOperationDetailEntity, Long> {

}
