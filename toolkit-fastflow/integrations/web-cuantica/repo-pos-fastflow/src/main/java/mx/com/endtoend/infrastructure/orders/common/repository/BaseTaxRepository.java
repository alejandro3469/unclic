package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public interface BaseTaxRepository extends JpaRepository<TaxEntity, Long> {

    List<TaxEntity> findByOrderOrderId(Long id);

}

