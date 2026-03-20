package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoRepositoryBean
public interface BaseSaleOrderDetailRepository extends JpaRepository<SaleOrderDetailEntity, Long> {

}
