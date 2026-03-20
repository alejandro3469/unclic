package mx.com.endtoend.infrastructure.openings.common.repositories;


import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseOpeningOperationDetailRepository extends JpaRepository<OpeningOperationDetailEntity, Long> {


}
