package mx.com.endtoend.infrastructure.recharges.common.repository;

import mx.com.endtoend.infrastructure.recharges.calzada.entities.OperationTraceSeliaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseOperationTraceSeliaRepository extends JpaRepository<OperationTraceSeliaEntity, Long> {

}
