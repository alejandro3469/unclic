package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public interface BaseOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

    List<OrderDetailEntity> findByOrderOrderId(Long id);

}

